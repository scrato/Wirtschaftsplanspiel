package networkTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import clientServerArchitecture.*;

public class NetworkTestClient {

	public static void main(String[] args) {
		
		Client client = null;
		
		System.out.println("Verbinde zum Server localhost:51515");
		try {
			client = new Client("Lars", InetAddress.getLocalHost(), 51515);
			if (client != null) {
				System.out.println("Verbindung hergestellt.");
				System.out.println("Sende Nachricht 'Hallo Welt'.");
				String content = "Hallo Welt.";
				byte[] encoded = content.getBytes();
				
				NetMessage message = new NetMessage(MessageType.CHATMASSAGE_TOSERVER, encoded);
				
				client.SendMessage(message);
				System.out.println("Nachricht versendet.");						
				
				client.SendMessage(message);
				System.out.println("Nachricht versendet.");							
				
				
			}
		} catch (UnknownHostException e) {
			System.err.println("Konnte IP nicht auflösen.");
		}
		catch (RuntimeException e) {
			System.err.println("Konnte verbindung zum Server nicht herstellen.");
		}		
	}	
}

