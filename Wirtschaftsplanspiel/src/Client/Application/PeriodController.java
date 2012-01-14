package Client.Application;

import common.entities.Supply;

import Client.Entities.Company;
import Client.Network.Client;
import NetworkCommunication.SendAssignedDemandMessage;
import NetworkCommunication.SendSupplyMessage;

public abstract class PeriodController {

	public static void SendSupply(Supply supply) {
		Client client = Client.getInstance();
		client.SendMessage(new SendSupplyMessage(supply));
	}

	public static void RecieveAssignedDemand(SendAssignedDemandMessage sendAssignedDemandMessage) {
		//TODO Erfolgsermittlung und übertrageung des erfolgs zum Server. Dannach ErgebnisScreen (Bilanz, GuV) aufbauen und anzeigen.
		
	}
	
}
