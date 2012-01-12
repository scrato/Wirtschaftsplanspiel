package Client.Application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Client.Entities.Company;
import Client.Entities.Credit;
import Client.Entities.Machine;
import Client.Entities.MachineType;
import Client.Entities.Production;
import Client.Entities.Ressource;
import Client.Application.UserCanNotPayException;
import Client.Entities.Employee;
import Client.Entities.EmployeeType;
import Client.Entities.Ressource.RessourceType;

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
	public static void initRessource(Ressource.RessourceType type, int availableUnits, double pricePerUnit){
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
	 */
	public static void buyRessources(Ressource.RessourceType type, int amount) throws UserCanNotPayException{
		Company comp = Company.getInstance();
		Ressource res = comp.getRessource(type); 
		//Sind nicht genug Rohstoffe da, hol einfach den Rest
		if(res.getBuyableUnits() > amount)
			amount = res.getBuyableUnits();
			payItem((amount * res.getPricePerUnit()) + Ressource.getFixedCosts(res.getType()) );
		
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
		comp.incMoney(machine.getValue() / 2);
		comp.removeMachine(machine);
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
		comp.getActualPeriod().setDeprecation(deprecation);
		return deprecation;
	}
	
	//End of Machine-Abschnitt
	// ------------------------------------------------------------
	//Begin Produktionsabschnitt
	   /**
	    * Prüft, ob die mitgegebene Menge an Einheiten produziert werden kann.
	    * @param units Die Anzahl der Fertigprodukte, die produziert werden soll.
	    */
	   public static boolean canProduce(int units){
		   Company comp = Company.getInstance();
		   Production prod = comp.getProduction();
		   boolean canProduce = true;
		   
		   //Ressourcen prüfen
		   if(!(missingRessources(units).isEmpty()))
			   canProduce = false;
		   	
		   
		   //Maschinen prüfen
		   if(!(missingMachines(units).isEmpty()))
				canProduce = false;
		   	
		   	//Personal prüfen
		   if(!(missingEmployees(units).isEmpty()))
				canProduce = false;		   
		   
		   return canProduce;
	   }
	   
		public static Map<RessourceType, Integer> missingRessources(int units) 
		{
				Company comp = Company.getInstance();
			   Map<RessourceType, Ressource> ressources = comp.getAllRessources();
			   Map<RessourceType, Integer> missingUnitPerRessource = new HashMap<RessourceType,Integer>();
			   
			   while(ressources.values().iterator().hasNext()){
				   Ressource res = ressources.values().iterator().next();
					//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Rohstoffe fehlen.
				   int missingunits = (Ressource.getNeed(res.getType())* units)  - res.getStoredUnits();
				   if (missingunits > 0)
					   missingUnitPerRessource.put(res.getType(), missingunits);
			   }
			   return missingUnitPerRessource;
		}
	   
		public static Map<MachineType, Integer> missingMachines(int units) 
		{
			Company comp = Company.getInstance();
			Map<MachineType, Integer> missingUnitPerMachine = new HashMap<MachineType, Integer>();
		   	for(MachineType type: MachineType.values()){
		   		
		   		//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Maschinen fehlen.
		   		int missingunits = units - comp.getMachineCapacity(type);
		   		if (missingunits > 0)
		   			missingUnitPerMachine.put(type, missingunits);
		   	}
		   	return missingUnitPerMachine;	
		}
	 
		public static Map<EmployeeType, Integer> missingEmployees(int units) 
		{
			//TODO: Prüfen, ob genug Personal für die Produktion vorhanden ist
			throw new UnsupportedOperationException("Noch nicht implementiert");
		}
	   
	   /**
	    * Produziert die mitgegebene Anzahl an Fertigprodukten
	    * @param units Die Anzahl der Fertigprodukte, die produziert werden soll.	    
	    * @throws CannotProduceException - Wenn nicht produziert werden kan, 
	    * weil Maschinen/Employees/Ressources fehlen, wird diese Exception geworfen.
	    */
	   public static void produce(int units, int priceperunit) throws CannotProduceException
	   {
			if(!(canProduce(units)))
				throw new CannotProduceException();
			Company comp = Company.getInstance();
			Production prod = comp.getProduction();
			for(RessourceType t: Ressource.RessourceType.values()) {
				comp.getRessource(t).decStoredUnits(units*Ressource.getNeed(t));
			}
			comp.incFinishedProducts(units);
			prod.setPricePerUnit(priceperunit);
		}
	
	   /**
	    * Produziert die maximale Menge an Fertigprodukten, sucht sich selbst wieviele Fertigprodukte er produzieren kannst
	    * @param pricePerUnit
	    */
	   public static void produce(int pricePerUnit) 
	   {
		   
		   Company comp = Company.getInstance();
		   Production prod = comp.getProduction();
		   int units = prod.getMaxProducableUnits();
		   try
		   {
		   produce(units, pricePerUnit);
		   }
		   catch (ApplicationException ex)
		   {
			   throw new UnsupportedOperationException("In der getMaxProducableUnits ist was falsch.");
		   }
	   }
	 
	   
	   
	  //End of Produktionsabschnitt
	// ------------------------------------------------------------
		//Begin of Employee-Abschnitt
	
	public static void employSb(Employee newEmployee) throws UserCanNotPayException {
		Company comp = Company.getInstance();
		payItem(Employee.DISSMISSCOST);  	
		comp.addEmployee(newEmployee);	
	}
	
	public static void dismissSb(Employee oldEmployee) throws UserCanNotPayException { 
		Company comp = Company.getInstance();
		payItem(oldEmployee.getSeverancePay());
		comp.removeEmployee(oldEmployee);
		
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
	}
	
	public static void payCreditAmortisation() throws UserCanNotPayException{
		Company comp = Company.getInstance();
		Credit cred = comp.getCredit();
		payItem(cred.getAnuity());
		//Wenn Credit abbezahlt
		if(cred.payAmortisation())
			comp.removeCredit();
		
	}

}
