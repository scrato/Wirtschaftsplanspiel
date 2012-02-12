package Client.Application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import Client.Entities.Company;
import Client.Entities.Credit;
import Client.Entities.Machine;
import Client.Entities.MachineType;
import Client.Entities.Period;
import Client.Entities.PeriodInfo;
import Client.Entities.ProductionAndDistribution;
import Client.Entities.Ressource;
import Client.Application.UserCanNotPayException;
import Client.Entities.Employee;
import Client.Entities.EmployeeType;
import Client.Entities.RessourceType;
import Client.Presentation.MainWindow;

public abstract class CompanyController {
	
	/**
	 * Ein mitgegebener Preis wird vom Guthaben des Unternehmens abgezogen.
	 * Sollte das Unternehmen nicht mehr liquide sein, wird eine UserCanNotPayException geworfen
	 * @param price Der Preis
	 * @throws UserCanNotPayException Nutzer kann nicht zahlen
	 */
	public static void payItem(double price) throws UserCanNotPayException{
		if(Company.getInstance().isLiquid(price) == false)
			throw new UserCanNotPayException();
		Company.getInstance().decMoney(price);
		MainWindow.getInstance().updateInfoPanel();
	}
	
	
	
	// Begin Ressource-Abschnitt
	/**
	 * Initialisiert die Ressource des mitgegebenen typs (für neue Periode)
	 * mit neuer Anzahl von vorhandenen Einheiten und dem Preis pro Einheit.
	 * Verwendbar für Festlegung nach neuer Periode
	 * @param type Der RessourceTyp der zu kaufenden Ressource
	 * @param availableUnits Die Anzahl der verfügbaren Ressorucen
	 * @param pricePerUnit Den preis pro Kilo
	 */
	public static void initRessource(RessourceType type, int availableUnits, double pricePerUnit){
		Company comp = Company.getInstance();
		comp.getRessource(type).setBuyableUnits(availableUnits);
		comp.getRessource(type).setPricePerUnit(pricePerUnit);
	}

	/**
	 * Kauft eine Anzahl von Ressourcen eines Typs ein. 
	 * Sollte der Nutzer nicht liquide sein, wird eine UserCanNotPayException geworfen
	 * @param type Der RessourceTyp der zu kaufenden Ressource
	 * @param amount Die Anzahl der zu kaufenden Ressourcen
	 * @throws UserCanNotPayException
	 * @throws NotEnoughRessourcesException 
	 */
	public static void buyRessources(RessourceType type, int amount) throws UserCanNotPayException, NotEnoughRessourcesException{
		Company comp = Company.getInstance();
		Ressource res = comp.getRessource(type); 
		//Sind nicht genug Rohstoffe da, hol einfach den Rest
		res.decBuyableUnits(amount);

			
		payItem((amount * res.getPricePerUnit()) + Ressource.getFixedCosts(res.getType()) );
		
		res.incStoredUnits(amount);
	}

	//End of Ressource-Abschnitt
// ------------------------------------------------------------
	//Begin of Machine-Abschnitt
	
	public static void buyMachine(Machine machine) throws MachineAlreadyBoughtException, UserCanNotPayException {
		Company comp = Company.getInstance();
		if (comp.getMachines().contains(machine)) {
			throw new MachineAlreadyBoughtException();
		}
		payItem(machine.getValue());
		comp.addMachine(machine);
	}
	
	public static void sellMachine(Machine machine) throws MachineNotOwnedException {
		Company comp = Company.getInstance();
		if (!comp.getMachines().contains(machine)) {
			throw new MachineNotOwnedException();
		}
		comp.removeMachine(machine);
		comp.incMoney(machine.getValue() / 2);
		MainWindow.getInstance().updateInfoPanel();
	}
	
	public static double depcrecateMachines() {
		Company comp = Company.getInstance();
		List<Machine> machines = comp.getMachines();	
		
		double deprecation = 0d;		
		List<Machine> deprecatedMachines = new LinkedList<Machine>();
		
		for (Machine machine : machines) {
			deprecation += machine.deprecate();
			if (machine.isCompletelyDeprecated()) {
				deprecatedMachines.add(machine);
			}
		}
		for (Machine machine : deprecatedMachines) {
			comp.removeMachine(machine);
		}
		
		//Logging
		PeriodInfo.getActualPeriod().setDeprecation(deprecation);
		return deprecation;
	}
	
	//End of Machine-Abschnitt
	// ------------------------------------------------------------
	//Begin Produktionsabschnitt
	   /**
	    * Prüft, ob die mitgegebene Menge an Einheiten produziert werden kann.
	    * @param units Die Anzahl der Fertigprodukte, die produziert werden soll.
	    */
	   public static boolean canProduce(){
		   //Company comp = Company.getInstance();
		   //Production prod = comp.getProduction();
		   boolean canProduce = true;
		   
		   //Ressourcen prüfen
		   if(!(missingUnitsOnRessources().isEmpty()))
			   canProduce = false;
		   	
		   
		   //Maschinen prüfen
		   if(!(missingUnitsOnMachines().isEmpty()))
				canProduce = false;
		   	
		   	//Personal prüfen
		   if(!(missingUnitsOnEmployees().isEmpty()))
				canProduce = false;		   
		   
		   return canProduce;
	   }
	   
		public static Map<RessourceType, Integer> missingUnitsOnRessources() 
		{			
			Company comp = Company.getInstance();			
			int units = comp.getProdAndDistr().getUnitsToProduce();
			
			Map<RessourceType, Ressource> ressources = comp.getAllRessources();
			Map<RessourceType, Integer> missingUnitPerRessource = new HashMap<RessourceType,Integer>();
		   
			for(Iterator<Ressource> it = ressources.values().iterator(); it.hasNext();){
				Ressource res = it.next();
				//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Rohstoffe fehlen.
				int missingunits = (Ressource.getNeed(res.getType())* units)  - res.getStoredUnits();
				if (missingunits > 0)
				   missingUnitPerRessource.put(res.getType(), missingunits);
			}
			return missingUnitPerRessource;
		}
	   
		public static Map<MachineType, Integer> missingUnitsOnMachines() 
		{
			Company comp = Company.getInstance();			
			int units = comp.getProdAndDistr().getUnitsToProduce();
			
			Map<MachineType, Integer> missingUnitPerMachine = new HashMap<MachineType, Integer>();
		   	for(MachineType type: MachineType.values()){
		   		
		   		//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Maschinen fehlen.
		   		int missingunits = units - comp.getMachineCapacity(type);
		   		if (missingunits > 0)
		   			missingUnitPerMachine.put(type, missingunits);
		   	}
		   	return missingUnitPerMachine;	
		}
	 
		public static Map<EmployeeType, Integer> missingUnitsOnEmployees() 
		{
			Company comp = Company.getInstance();			
			int units = comp.getProdAndDistr().getUnitsToProduce();
			
			Map<EmployeeType, Integer> missingEmployees = new HashMap<EmployeeType, Integer>();
		   	for(EmployeeType type: EmployeeType.values()){
		   		
		   		//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Mitarbeiter fehlen.
		   		int missingunits = units - comp.getEmployeeCapacity(type);
		   		if (missingunits > 0)
		   			missingEmployees.put(type, missingunits);
		   	}
		   	return missingEmployees;
		}
		
		public static int missingRessources(RessourceType type){
			int missingUnits = 0;
			Map<RessourceType, Integer> misUn = CompanyController.missingUnitsOnRessources();
			if(misUn.containsKey(type))
				missingUnits = CompanyController.missingUnitsOnRessources().get(type);
			else
				return 0;

				return missingUnits;
		}
		
		public static int missingEmployees(EmployeeType type){
			
			int capacity = 0;
			int missingUnits = 0;
			Map<EmployeeType, Integer> misUn = CompanyController.missingUnitsOnEmployees();
			if(misUn.containsKey(type))
				missingUnits = CompanyController.missingUnitsOnEmployees().get(type);
			else
				return 0;
			switch(type){
				case Produktion:
					capacity = Employee.PRODUCTIONUNITS;
					break;
				case Verwaltung:
					capacity = Employee.ADMINUNITS;
					break;
				}
				
			//Anzahl der noch nicht gedeckten Einheiten durch die gegebene Kapazität + 1 
			
				int missEmpl = (int) ((missingUnits / capacity) + 1);

				return missEmpl;
		}
	   
		public static int missingMachines(MachineType type, int capacity){
			int missingUnits = 0;
			Map<MachineType, Integer> misUn = CompanyController.missingUnitsOnMachines();
			if(misUn.containsKey(type))
				missingUnits = CompanyController.missingUnitsOnMachines().get(type);
			else
				return 0;
			if (missingUnits == 0)
				return 0;
				int missMach = (int) StrictMath.ceil(missingUnits / (double) capacity);

				return missMach;
		}
		
		
		/**
	    * Produziert die in Company.Production festgelegte Anzahl an Fertigprodukten	    
	    * @throws CannotProduceException - Wenn nicht produziert werden kan, 
	    * weil Maschinen/Employees/Ressources fehlen, wird diese Exception geworfen.
	    */
	   public static void produce() throws CannotProduceException
	   {
		   Company comp = Company.getInstance();
		   int units = comp.getProdAndDistr().getUnitsToProduce();
			if(!(canProduce())) {
				// TODO: Spezifizieren, was fehlt, damits im UI angezeigt werden kann.
				throw new CannotProduceException();
			}
			
			for(RessourceType t: RessourceType.values()) {
				comp.getRessource(t).decStoredUnits(units*Ressource.getNeed(t));
			}
			comp.incFinishedProducts(units);
		}
	
//	   /**
//	    * Produziert die maximale Menge an Fertigprodukten, sucht sich selbst wieviele Fertigprodukte er produzieren kannst
//	    * @param pricePerUnit
//	    */
//	   public static void produce() 
//	   {
//		   
//		   Company comp = Company.getInstance();
//		   Production prod = comp.getProduction();
//		   int units = prod.getMaxProducableUnits();
//		   try
//		   {
//		   produce(units);
//		   }
//		   catch (ApplicationException ex)
//		   {
//			   throw new UnsupportedOperationException("In der getMaxProducableUnits ist was falsch.");
//		   }
//	   }
	 
	   
	   
	  //End of Produktionsabschnitt
	// ------------------------------------------------------------
		//Begin of Employee-Abschnitt
	
	public static boolean canSale() {
			Company comp = Company.getInstance();
			ProductionAndDistribution proddis = comp.getProdAndDistr();
			
			if(!canProduce())		
				return false;
			
			if(proddis.getUnitsToSell() <= proddis.getUnitsToProduce() + comp.getFinishedProducts())
				return true;
			else
				return false;
		}



	public static void employSb(Employee newEmployee) throws UserCanNotPayException {
		Company comp = Company.getInstance();
		payItem(Employee.getEmploycost());  	
		comp.addEmployee(newEmployee);	
	}
	
	public static void dismissSb(EmployeeType type) throws UserCanNotPayException, EmployeeNotEmployedException { 
		Company comp = Company.getInstance();
		payItem(Employee.getDismisscost());
		comp.removeEmployee(type);
	}
	
	public static double paySallery() throws UserCanNotPayException {
		Company comp = Company.getInstance();
		double wages = comp.getWages();
		wages += comp.EMPLOYERSSALLERY;
		payItem(wages);
		return wages;
	}
	
	//End of Employee-Abschnitt
	// -------------------------------------------------------------
	//Begin Darlehensabschnitt
	public static void takeCredit(double height, int periods) throws UnableToTakeCreditException{
		Company comp = Company.getInstance();
		if(comp.creditExist())
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.CreditAlreadyExists);
		
		Credit credit = new Credit(height, periods);
		comp.setCredit(credit);
		comp.incMoney(height);
		MainWindow.getInstance().updateInfoPanel();
	}
	
	public static void payInterestAndRepayment() throws UserCanNotPayException{
		Company comp = Company.getInstance();
		Credit cred = comp.getCredit();
		if (cred == null) return;
		payItem(cred.getAnuity());
		//Wenn Credit abbezahlt
		if(cred.payInterestAndRepayment())
			comp.removeCredit();	
	}
	
	//end of Darlehensabschnitt
	//-------------------------------------------------
	//begin of fixtkostenabschnitt :)
	
	public static void payRent() throws UserCanNotPayException {
		payItem(Company.getInstance().FACILITIESRENT);
	}
	
	//end of fixtkostenabschnitt
	//------------------------------------------------------
	
	//Verkaufserlöse	
	public static void receiveSalesRevenue(double Revenue, int SoldProducts) {
		Company comp = Company.getInstance();
		comp.decFinishedProducts(SoldProducts);
		comp.incMoney(Revenue);
		
		Period period = PeriodInfo.getActualPeriod();
		period.setRevenue(Revenue);
		MainWindow.getInstance().updateInfoPanel();
	}
	
	//Lagekosten
	public static void payWarehouseCosts() throws UserCanNotPayException {
		Company comp = Company.getInstance();
		payItem(comp.getWarehouseCosts());
	}
	
	
}
