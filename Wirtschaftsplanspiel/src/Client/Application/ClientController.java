package Client.Application;

import java.text.SimpleDateFormat;
import java.util.Date;

import Client.Network.Client;
import NetworkCommunication.ByteConverter;
import NetworkCommunication.ChatMessage;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;

public class ClientController {

	public static void SendChatMessage(String Message) throws RuntimeException {
		try {
			ChatMessage message = new ChatMessage(Message, "");
			Client.getInstance().SendMessage(message);
		}
		catch (RuntimeException e) {
			throw e;
		}
	}
	
	public static void ReceiveNetMessage(NetMessage Message) {
		switch (Message.get_MessageType()) {
			case MessageType.CHATMESSAGE_TOCLIENT: {
//				String chatMessage = new String(Message.get_Content());
//				System.out.println("Nachricht erhalten: " + Message);			
				
				byte[] content = Message.get_Content();
				
				byte[] contentLengthBytes = new byte[4];
				System.arraycopy(content, 0, contentLengthBytes, 0, 4); 				
				int contentLength = ByteConverter.toInt(contentLengthBytes);
				
				byte[] messageBytes = new byte[contentLength];
				System.arraycopy(content, 4, messageBytes, 0, contentLength);
				String message = new String(messageBytes);
				
				byte[] nameLengthBytes = new byte[4];
				System.arraycopy(content, 4 + contentLength, nameLengthBytes, 0, 4);
				int nameLength = ByteConverter.toInt(nameLengthBytes);
				
				byte[] nameBytes = new byte[nameLength];
				System.arraycopy(content, 8 + contentLength, nameBytes, 0, nameLength);
				String name = new String(nameBytes);
				
				SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
				String time = format.format(new Date());
				
				String displayString = time + " " + name + " schreibt: " + message;
				//TODO send displayString to GUI.
			}				
		}
	}

	
}
