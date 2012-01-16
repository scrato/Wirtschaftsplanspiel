package Client.Application;

import java.util.Iterator;

import common.entities.CompanyResult;
import common.entities.CompanyResultList;
import common.entities.Supply;

import Client.Entities.Company;
import Client.Entities.Period;
import Client.Entities.PeriodInfo;
import Client.Entities.Player;
import Client.Network.Client;
import NetworkCommunication.SendAssignedDemandMessage;
import NetworkCommunication.RecieveCompanyResultMessage;
import NetworkCommunication.SendSupplyMessage;

public abstract class PeriodController {

	public static void SendSupply(Supply supply) {
		Period period = PeriodInfo.getActualPeriod();
		period.setProductPrice(supply.price);
		
		Client client = Client.getInstance();
		client.SendMessage(new SendSupplyMessage(supply));
	}

	public static void RecieveAssignedDemand(SendAssignedDemandMessage sendAssignedDemandMessage) {
		//TODO Erfolgsermittlung und übertrageung des erfolgs zum Server. Dannach ErgebnisScreen (Bilanz, GuV) aufbauen und anzeigen.
		int quantity = sendAssignedDemandMessage.getQuantity();
		double price = PeriodInfo.getActualPeriod().getProductPrice();
		
		double revenue = quantity * price;		
		CompanyController.receiveSalesRevenue(revenue, quantity);
		
		try {
			CompanyController.payEmployeesSallery();
			CompanyController.depcrecateMachines();
			CompanyController.payWarehouseCosts();
			CompanyController.payInterestAndRepayment();
			CompanyController.payEmployersSalery();
			CompanyController.payRent();
			
			//TODO Gewinn ermitteln und an Server senden.
			
		} catch (UserCanNotPayException e) {
			// TODO reagieren auf zahlungsunfähigkeit.
				// Nachricht an Server senden "Ich bin pleite".
		}
	}

	public static void RecieveCompanyResult(
			RecieveCompanyResultMessage sendCompanyResultMessage) {
		CompanyResultList crl = sendCompanyResultMessage.getCompanyResults();
		
		for(Iterator<CompanyResult> it = crl.result.iterator(); it.hasNext();){
			CompanyResult result = it.next();
			Player.getPlayer(result.clientid).addCompanyResult(result.result);
		}
		
		//TODO: Im UI die Ergebnisliste anzeigen
		
	}
	
}
