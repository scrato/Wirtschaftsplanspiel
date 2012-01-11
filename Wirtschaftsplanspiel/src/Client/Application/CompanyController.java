package Client.Application;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import Client.Entities.Company;
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
		if(Company.getInstance().isLiquid(price))
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
		comp.getRessource(type).setAvailableUnits(availableUnits);
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
		if(res.getAvailableUnits() > amount)
			amount = res.getAvailableUnits();
			payItem(amount * res.getPricePerUnit());
		
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
		return deprecation;
	}
	
	//End of Machine-Abschnitt
	// ------------------------------------------------------------
	//Begin Produktionsabschnitt
	   /**
	    * Prüft, ob die mitgegebene Menge an Einheiten produziert werden kann.
	    * @param units Die Anzahl der Fertigprodukte, die produziert werden soll.
	    * @throws NotEnoughRessourcesException Wirft eine Exception, die die Information enthält, wieviele Units noch von welcher Ressource fehlen.
	    * @throws NotEnoughMachinesException Wenn nicht genug Machinen eines Types für die Produktion da sind, wird diese Exception geworfen.
	 * @throws NotEnoughPersonalException Wenn nicht genug Personal für die Produktion vorhanden ist, wird diese geworfen
	    */
	   public static void canProduce(int units) throws NotEnoughRessourcesException, NotEnoughMachinesException, NotEnoughPersonalException{
		   Company comp = Company.getInstance();
		   Production prod = comp.getProduction();
		   
		   //Ressourcen prüfen
		   prod.enoughRessources(units);
		   
		   //Maschinen prüfen
		   prod.enoughMachines(units);  	
		   	
		   	//Personal prüfen
		   prod.enoughPersonal(units);
	   }
	   
	   /**
	    * Produziert die mitgegebene Anzahl an Fertigkprodukten
	    * @param units Die Anzahl der Fertigprodukte, die produziert werden soll.
	    * @throws NotEnoughRessourcesException Wirft eine Exception, die die Information enthält, wieviele Units noch von welcher Ressource fehlen.
	    * @throws NotEnoughMachinesException Wenn nicht genug Machinen eines Types für die Produktion da sind, wird diese Exception geworfen.
	    * @throws NotEnoughPersonalException Wenn nicht genug Personal für die Produktion vorhanden ist, wird diese geworfen
	    */
	   public static void produce(int units, int priceperunit) throws NotEnoughRessourcesException, NotEnoughMachinesException, NotEnoughPersonalException {
			canProduce(units);
			Company comp = Company.getInstance();
			Production prod = comp.getProduction();
			for(RessourceType t: Ressource.RessourceType.values()) {
				comp.getRessource(t).decStoredUnits(units*Ressource.getNeed(t));
			}
			comp.incFinishedProducts(units);
			prod.setPricePerUnit(priceperunit);
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
	public static void takeCredit(double heigh){
		Company comp = Company.getInstance();
		
	}

}
