<>package Client.Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import Client.Application.EmployeeNotEmployedException;
import Client.Application.CompanyController;
import Client.Entities.RessourceType;



public class Company {
	private static Company company;
	//Produktion
	private static ProductionAndDistribution prod;
	
	public static Company getInstance(){
		if (company != null)
			return company;
		company = new Company();
		initFirst();
		return company;
	}
	private static void initFirst() {
		Company.getInstance().incMoney(10000000.00);
		PeriodInfo.initRessources();
	}
	private Credit actCredit;
	private List<Employee> employee = new LinkedList<Employee>();
	public final double EMPLOYERSSALLERY = 45000d;
	
	public final double FACILITIESRENT = 30000d;
	private int finishedproducts;
	//Hier liegen die Maschinen.
	private List<Machine> machines = new LinkedList<Machine>();
	//Hier werden Variablen verwaltet
	private double money;
	
	private Map<RessourceType, Ressource> ressources;
	public final double WAREHOUSECOST_PER_STOCKFISCH = 0.1d;
	public final double WAREHOUSECOST_PER_VERPACKUNG = 0.05d;
	public final double WAREHOUSECOST_PER_PRODUCT = 0.2d;
	//private double warehouseCostPerProduct;
	

	private Company(){
		/* Falls ressources noch nicht gewählt ist, 
		 * wird für jeden Ressourcetype ein Eintrag im Dictionary angelegt
		 */
		ressources = new HashMap<RessourceType, Ressource>();
		for(RessourceType t:RessourceType.values()){
			ressources.put(t, new Ressource(t, Ressource.getUnit(t)));
		}
		
		//TODO: Dynamische Lösung finden
		PeriodInfo.setMaxPeriods(15);
	}
	
	//Mitarbeiter	
	public void addEmployee(Employee newEmployee) {
		employee.add(newEmployee);
		//Logging
		PeriodInfo.getActualPeriod().addHiredEmployee(newEmployee);
	}
	
	public void addMachine(Machine machine) {
		machines.add(machine);
		//Logging
		PeriodInfo.getActualPeriod().addBoughtMachine(machine);
	}
	
	public boolean creditExist(){
		return (actCredit != null);
	}
	
	public void decFinishedProducts(int prod){
		finishedproducts -= prod;
		//Logging
		PeriodInfo.getActualPeriod().decFinishedProductCountDelta(prod);
	}
	public void decMoney(double amount){
		money -= amount;
		//Logging
		PeriodInfo.getActualPeriod().incPaidMoney(amount);
	}
	
	public Map<RessourceType, Ressource> getAllRessources() {
		return ressources;
	}
	//Credit
	/**
	 * Gibt den aktuellen Credit zurück.
	 * Achtung: Wenn kein Credit gesetzt wird, kommt null zurück!
	 * @throws CreditNotExistsException Wird geworfen, wenn kein Kredit existiert
	 */
	public Credit getCredit() {
		return actCredit;
	}
	public int getEmployeeCapacity(EmployeeType type) {
		int capacity = 0;
		for (Employee empl : employee) {
			if (empl.getType() == type)
				capacity += empl.getCapacity();
		}
		return capacity;
	}
	
	
	public List<Employee> getEmployees() {
		List<Employee> employeeList = new LinkedList<Employee>();
		employeeList.addAll(employee);
		return employeeList;
	}
	
	public int getFinishedProducts(){
		return finishedproducts;
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
	
	//Maschinen
	public List<Machine> getMachines() {
		List<Machine> retList = new LinkedList<Machine>();
		retList.addAll(machines);
		return retList;
	}
   
	public double getMoney(){
		return this.money;
	}
	
	public ProductionAndDistribution getProdAndDistr(){
		if (prod != null)
			return prod;
		
		prod = new ProductionAndDistribution();
		return prod;
	}
	
	//Ressourcen	
	public Ressource getRessource(RessourceType type){
		return ressources.get(type);
	}
	
	public double getWages(){
		double totalWages = 0;
		for (Employee empl : employee) {
			totalWages += empl.getWage();
		}
		return totalWages;
	}
	
	public double getWarehouseCosts(){

		int stockfisch = getRessource(RessourceType.Rohfisch).getStoredUnits();
		int verpackung = getRessource(RessourceType.Verpackungsmaterial).getStoredUnits();
		int finishedProducts = getFinishedProducts();
		
		return                  stockfisch * WAREHOUSECOST_PER_STOCKFISCH 
							  + verpackung * WAREHOUSECOST_PER_VERPACKUNG  
							  + finishedProducts * WAREHOUSECOST_PER_PRODUCT;
	}
	
	public void incFinishedProducts(int prod){
	finishedproducts += prod;
	//Logging
	PeriodInfo.getActualPeriod().incFinishedProductCountDelta(prod);
	}
	
	public void incMoney(double amount){
		money += amount;
		//Logging
		PeriodInfo.getActualPeriod().incEarnedMoney(amount);
	}

	
	public boolean isLiquid(double amount) {
		if((this.money - amount) <= 0)
			return false;
		return true;
			
	}
	
	public void removeCredit() {
		//Logging
		PeriodInfo.getActualPeriod().addPaidCredit(actCredit);
		
		
		actCredit = null;
		
	}
	
	public void removeEmployee(EmployeeType type) throws EmployeeNotEmployedException {
		Employee empOfType = null;
		for (Employee emp : employee) {
			if (emp.getType() == type) {
				empOfType = emp;
				break;
			}
		}
		if (empOfType != null) {
			employee.remove(empOfType);
			//Logging
			PeriodInfo.getActualPeriod().addFiredEmployee(empOfType);
		} else {
			throw new EmployeeNotEmployedException();
		}
	}
	

	public void removeMachine(Machine machine) {
		machines.remove(machine);
		//Logging
		PeriodInfo.getActualPeriod().addSoldMachine(machine);
	}
	
	public void setCredit(Credit cred){
		actCredit = cred;
		//Logging
		PeriodInfo.getActualPeriod().addTakenCredit(cred);
	}

}
