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
				break;
			}
			case MessageType.PLAYER_JOINED: {
				ClientController.NewPlayerJoined(message);
				break;
			}
			case MessageType.PLAYER_LEFT: {
				ClientController.PlayerLeft(message);
				break;
			}
			case MessageType.GAME_STARTED: {
				ClientController.GameStartet();
				break;
			}
		}
	}
	
}
