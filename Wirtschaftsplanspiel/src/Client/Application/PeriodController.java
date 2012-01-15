package Client.Application;

import common.entities.Supply;

import Client.Entities.Company;
import Client.Entities.Period;
import Client.Network.Client;
import NetworkCommunication.SendAssignedDemandMessage;
import NetworkCommunication.SendSupplyMessage;

public abstract class PeriodController {

	public static void SendSupply(Supply supply) {
		Period period = Company.getInstance().getActualPeriod();
		period.setProductPrice(supply.price);
		
		Client client = Client.getInstance();
		client.SendMessage(new SendSupplyMessage(supply));
	}

	public static void RecieveAssignedDemand(SendAssignedDemandMessage sendAssignedDemandMessage) {
		//TODO Erfolgsermittlung und übertrageung des erfolgs zum Server. Dannach ErgebnisScreen (Bilanz, GuV) aufbauen und anzeigen.
		int quantity = sendAssignedDemandMessage.getQuantity();
		double price = Company.getInstance().getActualPeriod().getProductPrice();
		
		double revenue = quantity * price;		
		CompanyController.receiveSalesRevenue(revenue, quantity);
		
		try {
			CompanyController.payEmployeesSallery();
			CompanyController.depcrecateMachines();
			CompanyController.payWarehouseCosts();
			CompanyController.payCreditAmortisation();
			CompanyController.payEmployersSalery();
			CompanyController.payRent();
			
			//TODO Gewinn ermitteln und an Server senden.
			
		} catch (UserCanNotPayException e) {
			// TODO reagieren auf zahlungsunfähigkeit.
				// Nachricht an Server senden "Ich bin pleite".
		}
	}
	
}
