package Client.Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Period {
	private double earnedMoney;
	private double paidMoney;
	private int finishedProducts;
	private double pricePerFinishedProduct;
	private Map<Ressource, Integer> boughtRessources = new HashMap<Ressource, Integer> ();
	private Map<Ressource, Integer> usedRessources = new HashMap<Ressource, Integer> ();
	private List<Employee> hiredEmployees = new LinkedList<Employee>();
	private List<Machine> boughtMachines = new LinkedList<Machine>();
	private List<Employee> firedEmployees = new LinkedList<Employee>();
	private List<Machine> soldMachines = new LinkedList<Machine>();
	private List<Credit> takenCredits = new LinkedList<Credit>();
	
	public double getEarnedMoney() {
		return earnedMoney;
	}

	public void incEarnedMoney(double earnedMoney) {
		this.earnedMoney += earnedMoney;
	}
	
	
	public double getPaidMoney() {
		return paidMoney;
	}
	
	public void incPaidMoney(double paidMoney) {
		this.paidMoney += paidMoney;
	}
	
	
	public int getFinishedProducts() {
		return finishedProducts;
	}
	public void incFinishedProducts(int finishedProducts) {
		this.finishedProducts += finishedProducts;
	}
	
	public void deccFinishedProducts(int finishedProducts) {
		this.finishedProducts -= finishedProducts;
	}
	
	
	public double getPricePerFinishedProduct() {
		return pricePerFinishedProduct;
	}
	public void setPricePerFinishedProduct(double pricePerFinishedProduct) {
		this.pricePerFinishedProduct = pricePerFinishedProduct;
	}
	
	
	public Map<Ressource, Integer> getBoughtRessources() {
		return boughtRessources;
	}
	public void addBoughtRessource(Ressource res, int count) {
		this.boughtRessources.put(res, count);
	}
	
	public Map<Ressource, Integer> getUsedRessources() {
		return usedRessources;
	}
	public void addUsedRessource(Ressource res, int count) {
		this.usedRessources.put(res, count);
	}
	
	public List<Employee> getHiredEmployees() {
		return hiredEmployees;
	}
	public void addHiredEmployee(Employee emp) {
		//Mitarbeiter ist entweder gefeuert oder eingestellt
		if (firedEmployees.contains(emp))
			firedEmployees.remove(emp);
		this.hiredEmployees.add(emp);
	}
	
	public List<Machine> getBoughtMachines() {
		return boughtMachines;
	}
	public void addBoughtMachine(Machine mach) {
		this.boughtMachines.add(mach);
	}
	
	
	public List<Employee> getFiredEmployees() {
		return firedEmployees;
	}
	public void addFiredEmployee(Employee emp) {
		//Mitarbeiter ist entweder gefeuert oder eingestellt
		if (hiredEmployees.contains(emp))
			hiredEmployees.remove(emp);
		this.firedEmployees.add(emp);
	}
	
	public List<Machine> getSoldMachines() {
		return soldMachines;
	}
	public void addSoldMachine(Machine mach) {
	soldMachines.add(mach);
	}

	public List<Credit> getTakenCredits() {
		return takenCredits;
	}
	public void addTakenCredit(Credit cred) {
	takenCredits.add(cred);
	}

}
