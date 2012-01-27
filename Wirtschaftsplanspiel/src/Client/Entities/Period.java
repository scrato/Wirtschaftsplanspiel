package Client.Entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Client.Entities.RessourceType;

public class Period {

	private Balance balance;
	private List<Machine> boughtMachines = new LinkedList<Machine>();
	private Map<Ressource, Integer> boughtRessources = new HashMap<Ressource, Integer>();
	private double changeInStockRessources;
	private double deprecationValue;
	private double earnedMoney;
	private double finishedProductsValue;
	private int finishedProductDelta;
	private List<Employee> firedEmployees = new LinkedList<Employee>();
	
	private ProfitAndLoss guv;
	private List<Employee> hiredEmployees = new LinkedList<Employee>();
	private double interestPayment;
	private Credit paidCredit;
	private double paidMoney;
	private double productPrice;
	private double ressourceDelta;
	private double revenue;

	private List<Machine> soldMachines = new LinkedList<Machine>();

	private Credit takenCredit;

	private Map<Ressource, Integer> usedRessources = new HashMap<Ressource, Integer>();
	
	public void setFinishedProductsValue(Double Value) {
		finishedProductsValue = Value;
	}

	public void addBoughtMachine(Machine mach) {
		this.boughtMachines.add(mach);
	}

	public void addBoughtRessource(Ressource res, int count) {
		this.boughtRessources.put(res, count);
	}

	public void addFiredEmployee(Employee emp) {
		// Mitarbeiter ist entweder gefeuert oder eingestellt
		if (hiredEmployees.contains(emp))
			hiredEmployees.remove(emp);
		this.firedEmployees.add(emp);
	}

	public void addHiredEmployee(Employee emp) {
		// Mitarbeiter ist entweder gefeuert oder eingestellt
		if (firedEmployees.contains(emp))
			firedEmployees.remove(emp);
		this.hiredEmployees.add(emp);
	}

	public void addPaidCredit(Credit cred) {
		paidCredit = cred;
	}

	public void addSoldMachine(Machine mach) {
		soldMachines.add(mach);
	}

	public void addTakenCredit(Credit cred) {
		takenCredit = cred;
	}

	public void addUsedRessource(Ressource res, int count) {
		this.usedRessources.put(res, count);
	}

	public void decFinishedProductCountDelta(int finishedProductsLeft) {
		this.finishedProductDelta += finishedProductDelta;
	}

	public void decRessourcePriceDelta(double ressourceDelta) {
		this.ressourceDelta -= ressourceDelta;
	}

	public Balance getBalance() {
		return balance;
	}

	// Machine-Abschnitt
	public List<Machine> getBoughtMachines() {
		return boughtMachines;
	}

	// Ressource-Abschnitt
	public Map<Ressource, Integer> getBoughtRessources() {
		return boughtRessources;
	}

	public double getChangeInStockRessources() {
		return changeInStockRessources;
	}

	public double getDeprecation() {
		return deprecationValue;
	}

	public double getEarnedMoney() {
		return earnedMoney;
	}

	public int getFinishedProductCountDelta() {
		return finishedProductDelta;
	}

	public List<Employee> getFiredEmployees() {
		return firedEmployees;
	}
	
	

	public ProfitAndLoss getGuV() {
		return guv;
	}

	// Employee-Abschnitt
	public List<Employee> getHiredEmployees() {
		return hiredEmployees;
	}

	public double getInterestPayment() {
		return interestPayment;

	}

	public Credit getPaidCredit() {
		return paidCredit;
	}

	public double getPaidMoney() {
		return paidMoney;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public double getRessourcePriceDelta() {
		return ressourceDelta;
	}

	public double getRevenue() {

		return revenue;
	}

	public List<Machine> getSoldMachines() {
		return soldMachines;
	}

	// Credit-Abschnitt
	public Credit getTakenCredit() {
		return takenCredit;
	}

	public Map<Ressource, Integer> getUsedRessources() {
		return usedRessources;
	}

	public void incEarnedMoney(double earnedMoney) {
		this.earnedMoney += earnedMoney;
	}

	public void incFinishedProductCountDelta(int finishedProducts) {
		this.finishedProductDelta += finishedProducts;
	}

	public void incPaidMoney(double paidMoney) {
		this.paidMoney += paidMoney;
	}

	public void incRessourcePriceDelta(double ressourceDelta) {
		this.ressourceDelta += ressourceDelta;
	}

	// GUV
	/**
	 * Erstellt die Bilianz der Periode
	 */
	public Balance makeBalance() {
		Period p = this;
		balance = new Balance();

		balance.totallypaid = p.getPaidMoney();
		balance.totallyearned = p.getEarnedMoney();

		Company comp = Company.getInstance();

		for (Iterator<Machine> i = comp.getMachines().iterator(); i.hasNext();) {
			Machine next = i.next();
			balance.machineValue += next.getValue();
		}

		for (Iterator<Ressource> i = comp.getAllRessources().values()
				.iterator(); i.hasNext();) {
			Ressource next = i.next();
			balance.ressourceValue += next.getStoredUnits()
					* next.getPricePerUnit();
		}
		
		balance.finishedProductsValue = p.finishedProductsValue;

		balance.credit = comp.getCredit().getCreditLeft();

		balance.bank = comp.getMoney();

		balance.calculateEquity();
		return balance;
	}

	/**
	 * Erstellt die ProfitAndLoss am Ende der Periode. (Wenn diese Methode vor Ende der
	 * Periode ausgeführt wird, gibt es keine Abschreibung und keine verkauften
	 * Fertigprodukte)
	 * 
	 * @return
	 */
	public ProfitAndLoss makeGuV() {
		Period p = this;
		guv = new ProfitAndLoss();

		Company comp = Company.getInstance();

		// AfA an SA
		guv.deprecation += p.getDeprecation();

		// Aufwendungen für Ressourcen
		for (Iterator<Entry<Ressource, Integer>> it = p.getBoughtRessources()
				.entrySet().iterator(); it.hasNext();) {
			Entry<Ressource, Integer> next = it.next();
			guv.ressourceCost += next.getValue()
					* next.getKey().getPricePerUnit();
			guv.ressourceCost += Ressource.getFixedCosts(next.getKey()
					.getType());
		}

		// Gehaltszahlungen
		for (Iterator<Employee> it = comp.getEmployees().iterator(); it
				.hasNext();) {
			Employee next = it.next();
			guv.wages += next.getWage();
			// Unternehmerlohn
			guv.wages += comp.EMPLOYERSSALLERY;
		}

		// Sonstige tarifliche oder vertragliche Aufwendungen für Lohnempfäner
		guv.employeeDismissalCosts = p.getFiredEmployees().size()
				* Employee.getDismisscost();

		// Aufwendungen für Personaleinstellungen
		guv.employeeHiringCosts = Employee.EMPLOYCOST
				* p.getHiredEmployees().size();

		// Zinszahlungen
		if (comp.creditExist()) {
			guv.interest = p.getInterestPayment();
		}

		// Aufwendungen aus Abgang von Anlagevermögen
		guv.lossDueDisposalOfAssets = 0;
		for (Iterator<Machine> i = p.getSoldMachines().iterator(); i.hasNext();) {
			Machine next = i.next();
			guv.lossDueDisposalOfAssets += next.getLossDueSelling();
		}

		// Miete
		guv.rental = comp.FACILITIESRENT;

		// Bestandsveränderungen Endprodukte
		double productionPrice = 0;
		for (RessourceType t : RessourceType.values()) {
			productionPrice += Ressource.getNeed(t)
					* comp.getRessource(t).getPricePerUnit();
		}
//		guv.changeInStockFinishedProducts = p.getFinishedProductCountDelta()
//				* productionPrice;
//
//		// Bestandsveränderungen Ressourcen
//		guv.changeInStockRessources = p.getRessourcePriceDelta();
		guv.changeInStock = p.getFinishedProductCountDelta() * productionPrice + p.getRessourcePriceDelta();

		guv.warehouseCosts = comp.getWarehouseCosts();

		// Umsatzerlöse
		guv.sales = p.getRevenue();
		return guv;
	}

	// Bestandsveränderungen
	public void setChangeInStockRessources(double changeInStockRessources) {
		this.changeInStockRessources = changeInStockRessources;
	}

	public void setDeprecation(double value) {
		deprecationValue = value;
	}

	public void setInterestPayment(double interestPayment) {
		this.interestPayment = interestPayment;

	}

	public void setProductPrice(double price) {
		productPrice = price;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	
}



