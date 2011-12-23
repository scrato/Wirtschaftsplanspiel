package Client.Application;

import NetworkCommunication.ChatMessage;

public class ClientController {

	public static void SendChatMessage(String Message) throws RuntimeException {
		try {
			ChatMessage message = new ChatMessage(Message);
			appContext.client.SendMessage(message);
		}
		catch (RuntimeException e) {
			throw e;
		}
	}
	
}
