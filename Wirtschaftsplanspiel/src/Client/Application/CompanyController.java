package Client.Application;

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

public abstract class CompanyController {
	
	/**
	 * Ein mitgegebener Preis wird vom Guthaben des Unternehmens abgezogen.
	 * Sollte das Unternehmen nicht mehr liquide sein, wird eine UserCanNotPayException geworfen
	 * @param price Der Preis
	 * @throws UserCanNotPayException
	 */
	public static void payItem(double price) throws UserCanNotPayException{
		if(Company.getInstance().isLiquid(price))
			throw new UserCanNotPayException();
		Company.getInstance().decMoney(price);
	}
	
	
	
	// Begin Ressource-Abschnitt
	/**
	 * Initialisiert die Ressource des mitgegebenen typs 
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
	
	//Begin Produktionsabschnitt
	public static void produceGoods(int Count){
		Production prod = Company.getInstance().getProduction();
		//TODO: Produktion weiterprogrammieren
	}
	
	//End of Produktionsabschnitt
	// ------------------------------------------------------------
		//Begin of Employee-Abschnitt
	
	public void employSb(Employee newEmployee) throws UserCanNotPayException {
		Company comp = Company.getInstance();
			payItem(1000);  				// habe jetzt mal angenommen jm einstellen kostet 1000 GE
			comp.addEmployee(newEmployee);	// soll man wählen können welchem Mitarbeiter gekündigt werden soll?
											// --> der der am längsten da ist kostet am meisten (Abfindung)
	}
	
	public void dismissSb(Employee oldEmployee) throws UserCanNotPayException { //wie kann ich entscheiden was für ein Typ eingestellt werden soll?
		Company comp = Company.getInstance();
			payItem(oldEmployee.getSeverancePay());
			comp.removeEmployee(oldEmployee);
		
	}
	
}
