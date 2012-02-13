package client.application;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import client.network.Client;
import client.presentation.MainWindow;
import networkCommunication.ChatMessageToClient;
import networkCommunication.ChatMessageToServer;


public class ChatController {

	public static void SendChatMessage(String Message) throws RuntimeException {
		try {
			ChatMessageToServer message = new ChatMessageToServer(Message);
			Client.getInstance().SendMessage(message);
		}
		catch (RuntimeException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
	}
	
	public static void ReceiveNetMessage(ChatMessageToClient Message) {
		
		SimpleDateFormat format = new SimpleDateFormat("kk:mm");
		String time = format.format(new Date());
		
		String displayString = time + " " + Message.get_SenderName() + "\n" + Message.get_Message();
		//send displayString to GUI.
		MainWindow wind = MainWindow.getInstance();
		wind.addChatMessage(displayString);
	}
}
