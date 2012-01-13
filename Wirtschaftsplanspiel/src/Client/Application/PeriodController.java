package Client.Application;

import Client.Entities.Company;

public abstract class PeriodController {

	public static void closePeriod() {
		//TODO closePeriod implementieren.
		
		Company comp = Company.getInstance();

		
		
		int finishedProducts = comp.getFinishedProducts();
	}
	
}
