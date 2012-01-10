package Client.Network;

import Client.Application.ChatController;
import Client.Application.ClientController;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;

public class TriggerBusinessLogicThread extends Thread {
	public TriggerBusinessLogicThread(NetMessage Message) {
		super();	
		message = Message;
	}
	
	private NetMessage message;
	
	public void run() {
		switch (message.get_MessageType()) {
			case MessageType.CHATMESSAGE_TOCLIENT: {
				ChatController.ReceiveNetMessage(message);
			}
			case MessageType.PLAYER_JOINED: {
				ClientController.NewPlayerJoined(message);
			}
			case MessageType.PLAYER_LEFT: {
				ClientController.PlayerLeft(message);
			}
		}
	}
	
}
