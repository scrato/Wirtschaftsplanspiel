package Client.Entities;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import Client.Entities.Ressource.RessourceType;



public class Company {
	
	private static Company company;
	private static Production prod;
	
	public static Company getInstance(){
		if (company != null)
			return company;
		company = new Company();
		return company;
	}
	
	private Company(){
		/* Falls ressources noch nicht gew�hlt ist, 
		 * wird f�r jeden Ressourcetype ein Eintrag im Dictionary angelegt
		 */
		for(RessourceType t:RessourceType.values()){
			ressources.put(t, new Ressource(t, Ressource.getUnit(t)));
		}
	}
	
	//Hier werden Variablen verwaltet
	private double money;
	
	//Hier liegen die Maschinen.
	private List<Machine> machines = new LinkedList<Machine>();
	private Dictionary<RessourceType, Ressource> ressources;
	
	//Hier sind die Mitarbeiter verwaltet
	private List<Employee> employee = new LinkedList<Employee>();	
	
	/*
	 *  Pr�ft, ob das Unternehmen noch liquide Mittel zur Verf�gung hat
	 */	
	public double getMoney(){
		return this.money;
	}
	
	public void incMoney(double amount){
		money += amount;
	}
	public void decMoney(double amount){
		money -= amount;
	}
	
	public boolean isLiquid(double amount) {
		if((this.money - amount) <= 0)
			return false;
		return true;
			
	}
	//Produktion
	public Production getProduction(){
		if (prod != null)
			return prod;
		
		prod = new Production();
		return prod;
	}
	
	
	//Maschinen
	public List<Machine> getMachines() {
		List<Machine> retList = new LinkedList<Machine>();
		retList.addAll(machines);
		return retList;
	}
	
	public int getMachineCapacity(MachineType type) {
		int capacity = 0;
		for (Machine mach : machines) {
			if (mach.getType() == type)
			{
				capacity += mach.getCapacity();
			}
		}
		return capacity;
	}
	
	public void addMachine(Machine machine) {
		machines.add(machine);
	}
	
	public void removeMachine(Machine machine) {
		machines.remove(machine);
	}
   
	//Ressourcen	
	public Ressource getRessource(RessourceType type){
		return ressources.get(type);
	}
	
	//Mitarbeiter
	public List<Employee> getEmployee() {
		return employee;
	}
	
	public void addEmployee(Employee newEmployee) {
		employee.add(newEmployee);
	}
	
	public void removeEmployee(Employee oldEmployee) {
		employee.remove(oldEmployee);
	}

}
