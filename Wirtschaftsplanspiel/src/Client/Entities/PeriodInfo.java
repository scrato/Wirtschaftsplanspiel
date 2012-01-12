package Client.Entities;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import Client.Entities.Ressource.RessourceType;

public  class PeriodInfo {
   private  List<Period> periods = new LinkedList<Period>();
   private int maxPeriods;
   private int actPeriod;
   
   public PeriodInfo(){
	   this.actPeriod = 0;
	   periods.add(new Period());
   }
   
   public Period getActualPeriod(){
	   return periods.get(actPeriod);
   }
   
   public Period getPeriod(int period){
	   return periods.get(period);
   }

   public void nextPeriod(){
	   incNumberOfActPeriod();
	   periods.add(new Period());
   }

   public int getMaxPeriods() {
	return maxPeriods;
   }
   
   public void setMaxPeriods(int maxPeriods){
	   this.maxPeriods = maxPeriods;
   }

   public int getNumberOfActPeriod() {
	return actPeriod;
}

   private void incNumberOfActPeriod(){
	actPeriod++;
   }

   
   public Balance getBalance(){
	   Period p = periods.get(actPeriod);
	   Balance b = new Balance();
	   
	   b.totallypaid = p.getPaidMoney();
	   b.totallyearned = p.getEarnedMoney();
	   
	   Company comp = Company.getInstance();
	   
	   for(Iterator<Machine> i = comp.getMachines().iterator(); i.hasNext();){
		   Machine next = i.next();
		   b.machineValue += next.getValue();
	   }

	   
	   for(Iterator<Ressource> i = comp.getAllRessources().values().iterator(); i.hasNext();){
		   Ressource next = i.next();
		   b.ressourceValue += next.getStoredUnits() * next.getPricePerUnit();
	   }
	   
	   
	   b.credit = comp.getCredit().getCreditLeft();
	   
	   b.bank = comp.getMoney();
	   
	   b.calculateEquity();
	   
	   return b;
   }
   /**
    * Gibt die GuV am Ende der Periode zurück. 
    * (Wenn diese Methode vor Ende der Periode ausgeführt wird, gibt es keine Abschreibung und keine verkauften Fertigprodukte)
    * @return
    */
   public GuV getGuV(){
	   Period p = periods.get(actPeriod);
	   GuV g = new GuV();
	   
	   Company comp = Company.getInstance();

	   

	   
	   //AfA an SA 
		g.deprecation += p.getDeprecation();


		//Aufwendungen für Ressourcen
	   for(Iterator<Entry<Ressource, Integer>> it = p.getBoughtRessources().entrySet().iterator(); it.hasNext();){
		   Entry<Ressource, Integer> next = it.next();
		   g.ressourceProduced += next.getValue() * next.getKey().getPricePerUnit();
		   g.ressourceProduced += Ressource.getFixedCosts(next.getKey().getType());
	   }
	   
	   
	   //Gehaltszahlungen
	   for(Iterator<Employee> it = comp.getEmployee().iterator(); it.hasNext();){
		   Employee next = it.next();
		   g.wages += next.getWage();
	   }
	   
	   
	   //Sonstige tarifliche oder vertragliche Aufwendungen für Lohnempfäner
	   for(Iterator<Employee> it = p.getFiredEmployees().iterator(); it.hasNext();){
		   Employee next = it.next();
		   g.employeeDismissalCosts += next.getSeverancePay();
	   }
	   
	   
	   //Aufwendungen für Personaleinstellungen
	   for(Iterator<Employee> it = p.getHiredEmployees().iterator(); it.hasNext();){
		   Employee next = it.next();
		   //TODO: Einstellugnskosten
		  // g.employeeHiringCosts += next.getHiringCost();
	   }
	   
	   
	   //Zinszahlungen
	   if(comp.creditExist()){
		   g.interest = p.getInterestPayment();
	   }
	   
	   //Aufwendungen aus Abgang von Anlagevermögen
	   for(Iterator<Machine> i = p.getSoldMachines().iterator(); i.hasNext();){
		   Machine next = i.next();
		   //TODO: Verkaufsaufwand für Maschinen berechnen
		   //g.machineSellingCharge += (next.getValue() - next.getPrice)
	   }

	   //TODO: Fertigprodukte verkaufen
	   
	   //TODO: Miete + Energiekosten + ?
	   
	   //TODO: Bestandsveränderungen
	   
	   //TODO: Rückstellungen?
	   return g;
   }
   
   public class GuV {

		public double wages;

		public double employeeDismissalCosts;

		public double deprecation;

		public double ressourceProduced;
		public double interest;

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

