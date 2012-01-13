package Server.Network;

import Server.Application.ServerController;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;

public class TriggerBusinessLogicThread extends Thread {
	public TriggerBusinessLogicThread(ClientHandler Sender, NetMessage Message) {
		super();	
		message = Message;
		sender = Sender;
	}
	
	private NetMessage message;
	private ClientHandler sender;
	
	public void run() {
		switch (message.get_MessageType()) {
			case MessageType.CHATMESSAGE_TOCLIENT: {
				ServerController.redirectChatMessage(sender, message);
				break;
			}
			case MessageType.SEND_SUPPLY : {
				ServerController.receiveSupply(sender, message);
			}
		}
	}
	
}
