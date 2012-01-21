package Client.Entities;

import java.util.LinkedList;
import java.util.List;

public abstract class PeriodInfo {
   //private  List<Period> periods = new LinkedList<Period>();
   private static int maxPeriods;
   private static int actPeriod = 0;
   private static List<Period> periods;
   
   
    static {
       periods = new LinkedList<Period>();
	   periods.add(new Period());
   }
   
   public static Period getActualPeriod(){
	   return periods.get(actPeriod);
   }
   
   public static int getActualPeriodNumber(){
	   return actPeriod;
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

