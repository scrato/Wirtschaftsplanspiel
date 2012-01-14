package Client.Network;

import Client.Application.ChatController;
import Client.Application.ClientController;
import Client.Application.PeriodController;
import NetworkCommunication.ChatMessageToClient;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;
import NetworkCommunication.SendAssignedDemandMessage;

public class TriggerBusinessLogicThread extends Thread {
	public TriggerBusinessLogicThread(NetMessage Message) {
		super();	
		message = Message;
	}
	
	private NetMessage message;
	
	public void run() {
		switch (message.get_MessageType()) {
			case MessageType.CHATMESSAGE_TOCLIENT: {				
				ChatController.ReceiveNetMessage(new ChatMessageToClient(message.get_Content()));
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
			case MessageType.SEND_ASSIGNED_DEMAND: {
				PeriodController.RecieveAssignedDemand(new SendAssignedDemandMessage(message.get_Content()));
			}
		}
	}
	
}
