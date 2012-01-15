package Client.Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Period {
	private double productPrice;
	
	private double earnedMoney;
	private double paidMoney;
	private int finishedProductDelta;
	private double changeInStockRessources;
	private double ressourceDelta;
	private Map<Ressource, Integer> boughtRessources = new HashMap<Ressource, Integer> ();
	private Map<Ressource, Integer> usedRessources = new HashMap<Ressource, Integer> ();
	private List<Employee> hiredEmployees = new LinkedList<Employee>();
	private List<Machine> boughtMachines = new LinkedList<Machine>();
	private List<Employee> firedEmployees = new LinkedList<Employee>();
	private List<Machine> soldMachines = new LinkedList<Machine>();
	private double deprecationValue;
	private Credit takenCredit;
	private Credit paidCredit;
	private double interestPayment;
	
	public void setProductPrice(double price) {
		productPrice = price;
	}
	
	public double getProductPrice() {
		return productPrice;
	}
	
	
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
	
	
	public int getFinishedProductCountDelta() {
		return finishedProductDelta;
	}
	public void incFinishedProductCountDelta(int finishedProducts) {
		this.finishedProductDelta += finishedProducts;
	}
	
	public void decFinishedProductCountDelta(int finishedProductsLeft) {
		this.finishedProductDelta += finishedProductDelta;
	}

	
	//Ressource-Abschnitt
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
	
	public void incRessourcePriceDelta(double ressourceDelta) {
		this.ressourceDelta += ressourceDelta;
	}
	
	public void decRessourcePriceDelta(double ressourceDelta) {
		this.ressourceDelta -= ressourceDelta;
	}

	public double getRessourcePriceDelta() {
		return ressourceDelta;
	}

	//Employee-Abschnitt
	public List<Employee> getHiredEmployees() {
		return hiredEmployees;
	}
	public void addHiredEmployee(Employee emp) {
		//Mitarbeiter ist entweder gefeuert oder eingestellt
		if (firedEmployees.contains(emp))
			firedEmployees.remove(emp);
		this.hiredEmployees.add(emp);
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
	
	//Machine-Abschnitt
	public List<Machine> getBoughtMachines() {
		return boughtMachines;
	}
	public void addBoughtMachine(Machine mach) {
		this.boughtMachines.add(mach);
	}
	
	
	public List<Machine> getSoldMachines() {
		return soldMachines;
	}
	public void addSoldMachine(Machine mach) {
	soldMachines.add(mach);
	}
	
	public void setDeprecation(double value){
		deprecationValue = value;
	}
	
	public double getDeprecation(){
		return deprecationValue;
	}
	
	//Credit-Abschnitt
	public Credit getTakenCredit() {
		return takenCredit;
	}
	public void addTakenCredit(Credit cred) {
	takenCredit = cred;
	}
	
	public Credit getPaidCredit() {
		return paidCredit;
	}
	public void addPaidCredit(Credit cred) {
	paidCredit = cred;
	}

	public void setInterestPayment(double interestPayment) {
		this.interestPayment = interestPayment;
		
	}
	
	public double getInterestPayment() {
		return interestPayment;
		
	}

	public void setChangeInStockRessources(double changeInStockRessources) {
		this.changeInStockRessources = changeInStockRessources;
	}

	public double getChangeInStockRessources() {
		return changeInStockRessources;
	}



}
