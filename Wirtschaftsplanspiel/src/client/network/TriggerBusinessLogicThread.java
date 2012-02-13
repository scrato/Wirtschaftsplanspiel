package client.network;

import client.application.ChatController;
import client.application.ClientController;
import client.application.PeriodController;
import networkCommunication.BroadcastCompanyResultMessage;
import networkCommunication.ChatMessageToClient;
import networkCommunication.MessageType;
import networkCommunication.NetMessage;
import networkCommunication.SendAssignedDisposalMessage;

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
				ClientController.GameStarted();
				break;
			}
			case MessageType.SEND_ASSIGNED_DEMAND: {
				PeriodController.RecieveAssignedDisposal(new SendAssignedDisposalMessage(message.get_Content()));
				break;
			}
			case MessageType.BROADCAST_COMPANYRESULT: {
				PeriodController.RecieveCompanyResult(new BroadcastCompanyResultMessage(message.get_Content()));
				break;
			}
		}
	}
	
}
