package Client.Entities;

import java.util.LinkedList;
import java.util.List;

import Client.Application.CompanyController;

public abstract class PeriodInfo {
   //private  List<Period> periods = new LinkedList<Period>();
   private static int maxPeriods;
   private static int actPeriod = 0;
   private static List<Period> periods;
   
   
    static {
       periods = new LinkedList<Period>();
	   periods.add(new Period());
	   
		//TODO: Besseren Platz für Startwerte finden
		CompanyController.initRessource(RessourceType.Stockfisch, 400, 6.00);
		CompanyController.initRessource(RessourceType.Verpackungsmaterial, 75, 25.00);
		Company.getInstance().incMoney(50000.00);
   }
   
   public static Period getActualPeriod(){
	   return periods.get(actPeriod);
   }
   
   public static Period getPeriod(int period){
	   return periods.get(period);
   }

   public static void nextPeriod(){
	   getActualPeriod().makeBalance();
	   getActualPeriod().makeGuV();
	   incNumberOfActPeriod();
	   periods.add(new Period());
   }

   public static int getMaxPeriods() {
	return maxPeriods;
   }
   
   public static void setMaxPeriods(int countOfPeriods){
	   maxPeriods = countOfPeriods;
   }

   public static int getNumberOfActPeriod() {
	return actPeriod;
}

   private static void incNumberOfActPeriod(){
	actPeriod++;
   }

   

}

