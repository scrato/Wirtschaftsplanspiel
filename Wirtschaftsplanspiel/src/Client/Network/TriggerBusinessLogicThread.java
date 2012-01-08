package Client.Network;

import Client.Application.ChatController;
import NetworkCommunication.NetMessage;

public class TriggerBusinessLogicThread extends Thread {
	public TriggerBusinessLogicThread(NetMessage Message) {
		super();	
		message = Message;
	}
	
	private NetMessage message;
	
	public void run() {
		ChatController.ReceiveNetMessage(message);
	}
	
}
