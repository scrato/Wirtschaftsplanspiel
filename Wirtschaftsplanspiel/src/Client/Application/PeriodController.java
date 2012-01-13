package Client.Application;

import common.entities.Supply;

import Client.Entities.Company;
import Client.Network.Client;
import NetworkCommunication.SendSupplyMessage;

public abstract class PeriodController {

	public static void closePeriod(Supply supply) {
		//TODO closePeriod implementieren.
		
		//Company comp = Company.getInstance();

		Client client = Client.getInstance();
		client.SendMessage(new SendSupplyMessage(supply));
		
		//int finishedProducts = comp.getFinishedProducts();
	}
	
}
