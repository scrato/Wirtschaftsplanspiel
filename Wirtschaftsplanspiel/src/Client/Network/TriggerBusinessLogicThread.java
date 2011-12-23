package Client.Network;

import Client.Application.ClientController;
import NetworkCommunication.NetMessage;

public class TriggerBusinessLogicThread extends Thread {
	public TriggerBusinessLogicThread(NetMessage Message) {
		super();	
		message = Message;
	}
	
	private NetMessage message;
	
	public void run() {
		ClientController.ReceiveNetMessage(message);
	}
	
}
