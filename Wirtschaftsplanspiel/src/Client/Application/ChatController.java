package Client.Application;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Client.Network.Client;
import Client.Presentation.MainWindow;
import NetworkCommunication.ByteConverter;
import NetworkCommunication.ChatMessage;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;

public class ChatController {

	public static void SendChatMessage(String Message) throws RuntimeException {
		try {
			ChatMessage message = null;
			try {
				message = new ChatMessage(Message, "");
			} catch (UnsupportedEncodingException e) {
				// should never reach this point;
			}
			Client.getInstance().SendMessage(message);
		}
		catch (RuntimeException e) {
			throw e;
		}
	}
	
	public static void ReceiveNetMessage(NetMessage Message) {

//		String chatMessage = new String(Message.get_Content());
//		System.out.println("Nachricht erhalten: " + Message);			
		
		byte[] content = Message.get_Content();
		
		byte[] contentLengthBytes = new byte[4];
		System.arraycopy(content, 0, contentLengthBytes, 0, 4); 				
		int contentLength = ByteConverter.toInt(contentLengthBytes);
		
		byte[] messageBytes = new byte[contentLength];
		System.arraycopy(content, 4, messageBytes, 0, contentLength);
		String message = null;
		try {
			message = new String(messageBytes, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
		
		byte[] nameLengthBytes = new byte[4];
		System.arraycopy(content, 4 + contentLength, nameLengthBytes, 0, 4);
		int nameLength = ByteConverter.toInt(nameLengthBytes);
		
		byte[] nameBytes = new byte[nameLength];
		System.arraycopy(content, 8 + contentLength, nameBytes, 0, nameLength);
		String name = null;
		try {
			name = new String(nameBytes, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
		
		SimpleDateFormat format = new SimpleDateFormat("kk:mm");
		String time = format.format(new Date());
		
		String displayString = time + " " + name + "\n" + message;
		//send displayString to GUI.
		MainWindow wind = MainWindow.getInstance();
		wind.addChatMessage(displayString);
	}
}
