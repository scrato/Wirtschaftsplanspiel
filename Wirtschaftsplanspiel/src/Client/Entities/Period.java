package Client.Entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Client.Entities.Ressource.RessourceType;

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
	private GuV guv;
	private Balance balance;

	private double revenue;
	
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

	//Bestandsveränderungen
	public void setChangeInStockRessources(double changeInStockRessources) {
		this.changeInStockRessources = changeInStockRessources;
	}

	public double getChangeInStockRessources() {
		return changeInStockRessources;
	}


	
	//GUV
	/**
	 * Erstellt die Bilianz der Periode
	 */
	   public void makeBalance(){
		   Period p = this;
		   balance = new Balance();
		   
		   balance.totallypaid = p.getPaidMoney();
		   balance.totallyearned = p.getEarnedMoney();
		   
		   Company comp = Company.getInstance();
		   
		   for(Iterator<Machine> i = comp.getMachines().iterator(); i.hasNext();){
			   Machine next = i.next();
			   balance.machineValue += next.getValue();
		   }

		   
		   for(Iterator<Ressource> i = comp.getAllRessources().values().iterator(); i.hasNext();){
			   Ressource next = i.next();
			   balance.ressourceValue += next.getStoredUnits() * next.getPricePerUnit();
		   }
		   
		   
		   balance.credit = comp.getCredit().getCreditLeft();
		   
		   balance.bank = comp.getMoney();
		   
		   balance.calculateEquity();
	   }
	   
	   public Balance getBalance(){
		   return balance;
	   }

	   public double getRevenue() {
			
			return revenue;
		}
	   
	   public void setRevenue(double revenue){
		   this.revenue = revenue;
	   }
	   
	   /**
	    * Erstellt die GuV am Ende der Periode. 
	    * (Wenn diese Methode vor Ende der Periode ausgeführt wird, gibt es keine Abschreibung und keine verkauften Fertigprodukte)
	    * @return
	    */
	   public void makeGuV(){
		   Period p = this;
		   guv = new GuV();
		   
		   Company comp = Company.getInstance();

		   
		   //AfA an SA 
			guv.deprecation += p.getDeprecation();


			//Aufwendungen für Ressourcen
		   for(Iterator<Entry<Ressource, Integer>> it = p.getBoughtRessources().entrySet().iterator(); it.hasNext();){
			   Entry<Ressource, Integer> next = it.next();
			   guv.ressourceCost += next.getValue() * next.getKey().getPricePerUnit();
			   guv.ressourceCost += Ressource.getFixedCosts(next.getKey().getType());
		   }
		   
		   
		   //Gehaltszahlungen
		   for(Iterator<Employee> it = comp.getEmployees().iterator(); it.hasNext();){
			   Employee next = it.next();
			   guv.wages += next.getWage();
		   }
		   
		   
		   //Sonstige tarifliche oder vertragliche Aufwendungen für Lohnempfäner
			   guv.employeeDismissalCosts = p.getFiredEmployees().size() * Employee.getDismisscost();
		   
		   
		   //Aufwendungen für Personaleinstellungen
			   //TODO: DISSMISSCOST bei einstellung seltsam ^^
			  guv.employeeHiringCosts = Employee.EMPLOYCOST * p.getHiredEmployees().size();
		   
		   
		   //Zinszahlungen
		   if(comp.creditExist()){
			   guv.interest = p.getInterestPayment();
		   }
		   
		   //Aufwendungen aus Abgang von Anlagevermögen
		   for(Iterator<Machine> i = p.getSoldMachines().iterator(); i.hasNext();){
			   Machine next = i.next();
			   //TODO: Verkaufsaufwand für Maschinen berechnen
			   //g.machineSellingCharge += g.getSellingValue();
		   }

		   //Miete
		   guv.rental = comp.FACILITIESRENT;
		   //Unternehmerlohn
		   guv.employerSallery = comp.EMPLOYERSSALLERY;
		   
		   //Bestandsveränderungen Endprodukte
		   double productionPrice = 0;
		   for(RessourceType t: RessourceType.values()){
			   productionPrice += Ressource.getNeed(t) * comp.getRessource(t).getPricePerUnit();
		   }
		   guv.changeInStockFinishedProducts = p.getFinishedProductCountDelta() * productionPrice;
		   
		   //Bestandsveränderungen Ressourcen
		   guv.changeInStockRessources = p.getRessourcePriceDelta();
		   
		   //Umsatzerlöse
		   guv.sales = p.getRevenue();
	
	   }
	 

	public GuV getGuV(){
		   return guv;
	   }
	   
	   
	   
	   public class GuV {

			public double sales;

			public double employerSallery;

			public double rental;

			public double changeInStockRessources;

			public double changeInStockFinishedProducts;

			public double employeeHiringCosts;

			public double wages;

			public double employeeDismissalCosts;

			public double deprecation;

			public double ressourceCost;
			public double interest;
			
			public double result;
			
			public void calculateResult(){
				double  expenditures = employerSallery + rental + employeeHiringCosts + wages + employeeDismissalCosts + deprecation + interest;
				double  earnings = sales;
				
				if(changeInStockRessources > 0)
					earnings += changeInStockRessources;
				else
					expenditures = Math.abs(changeInStockRessources);
				
				if(changeInStockFinishedProducts > 0)
					earnings += changeInStockFinishedProducts;
				else
					expenditures = Math.abs(changeInStockFinishedProducts);
				
				
				
				this.result = earnings - expenditures;
			}

		}
   
	   public class Balance {

			public double machineValue;
			public double ressourceValue;
			public double credit;
			public double bank;
			public double equity;
			public double totallypaid;
			public double totallyearned;
			
			public void calculateEquity() {
				equity = machineValue + ressourceValue + bank - credit;
				
			}

		}

}
