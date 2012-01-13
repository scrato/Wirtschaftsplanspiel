package Client.Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Client.Entities.Ressource.RessourceType;



public class Company {
	private static Company company;
	public static Company getInstance(){
		if (company != null)
			return company;
		company = new Company();
		return company;
	}
	
	private Company(){
		/* Falls ressources noch nicht gewählt ist, 
		 * wird für jeden Ressourcetype ein Eintrag im Dictionary angelegt
		 */
		ressources = new HashMap<RessourceType, Ressource>();
		for(RessourceType t:RessourceType.values()){
			ressources.put(t, new Ressource(t, Ressource.getUnit(t)));
		}
		
		//TODO: Dynamische Lösung finden
		periodInfo.setMaxPeriods(15);
	}
	
	//Hier werden Variablen verwaltet
	private double money;
	private int finishedproducts;
	public final double EMPLOYERSSALLERY = 45000d;
	public final double FACILITIESRENT = 30000d;
	
	//Hier liegen die Maschinen.
	private List<Machine> machines = new LinkedList<Machine>();
	private Map<RessourceType, Ressource> ressources;
	private List<Employee> employee = new LinkedList<Employee>();	
	private PeriodInfo periodInfo = new PeriodInfo();
	private Credit actCredit;
	

	public void incFinishedProducts(int prod){
	finishedproducts += prod;
	//Logging
	periodInfo.getActualPeriod().incFinishedProductCountDelta(prod);
	}
	
	public void decFinishedProducts(int prod){
		finishedproducts -= prod;
		//Logging
		periodInfo.getActualPeriod().decFinishedProductCountDelta(prod);
	}
	
	public int getFinishedProducts(){
		return finishedproducts;
	}
	
	public double getMoney(){
		return this.money;
	}
	
	public void incMoney(double amount){
		money += amount;
		//Logging
		periodInfo.getActualPeriod().incEarnedMoney(amount);
	}
	public void decMoney(double amount){
		money -= amount;
		//Logging
		periodInfo.getActualPeriod().incPaidMoney(amount);
	}
	
	public boolean isLiquid(double amount) {
		if((this.money - amount) <= 0)
			return false;
		return true;
			
	}
	//Produktion
	private static Production prod;
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
		//Logging
		periodInfo.getActualPeriod().addBoughtMachine(machine);
	}
	
	public void removeMachine(Machine machine) {
		machines.remove(machine);
		//Logging
		periodInfo.getActualPeriod().addSoldMachine(machine);
	}
   
	//Ressourcen	
	public Ressource getRessource(RessourceType type){
		return ressources.get(type);
	}
	
	public Map<RessourceType, Ressource> getAllRessources() {
		return ressources;
	}
	
	//Mitarbeiter
	public List<Employee> getEmployee() {
		return employee;
	}
	
	public void addEmployee(Employee newEmployee) {
		employee.add(newEmployee);
		//Logging
		periodInfo.getActualPeriod().addHiredEmployee(newEmployee);
	}
	
	public void removeEmployee(Employee oldEmployee) {
		employee.remove(oldEmployee);
		//Logging
		periodInfo.getActualPeriod().addFiredEmployee(oldEmployee);
	}
	
	public double getWages(){
		double totalWages = 0;
		for (Employee empl : employee) {
			totalWages += empl.getWage();
		}
		return totalWages;
	}
	
	public List<Employee> getEmployees() {
		List<Employee> employeeList = new LinkedList<Employee>();
		employeeList.addAll(employee);
		return employeeList;
	}
	
	public int getEmployeeCapacity(EmployeeType type) {
		int capacity = 0;
		for (Employee empl : employee) {
			if (empl.getType() == type)
				capacity += empl.getCapacity();
		}
		return capacity;
	}

	//Periodeninfo
	public Period getActualPeriod(){
		return periodInfo.getActualPeriod();
	}
	
	public PeriodInfo getPeriodInfo(){
		return periodInfo;
	}
	
	//Credit
	/**
	 * Gibt den aktuellen Credit zurück.
	 * Achtung: Wenn kein Credit gesetzt wird, kommt null zurück!
	 */
	public Credit getCredit(){
		return actCredit;
	}
	
	public boolean creditExist(){
		return (actCredit != null);
	}
	
	public void setCredit(Credit cred){
		actCredit = cred;
		//Logging
		periodInfo.getActualPeriod().addTakenCredit(cred);
	}
	

	public void removeCredit() {
		//Logging
		Company.getInstance().getActualPeriod().addPaidCredit(actCredit);
		
		
		actCredit = null;
		
	}

}
