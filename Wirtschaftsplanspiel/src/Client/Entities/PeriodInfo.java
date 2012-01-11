package Client.Entities;

import java.util.List;

public abstract class PeriodInfo {
   private  List<Period> periods;
   private int maxPeriods;
   private int actPeriod;
   
   public PeriodInfo(int maxPeriods){
	   this.maxPeriods = maxPeriods;
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
	   incActPeriod();
	   periods.add(new Period());
   }

   public int getMaxPeriods() {
	return maxPeriods;
   }

   public int getActPeriod() {
	return actPeriod;
}

   private void incActPeriod(){
	actPeriod++;
   }
	
}
