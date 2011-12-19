package networkTest;

import java.net.InetAddress;
import java.util.Scanner;

import Client.Network.Client;
import NetworkCommunication.*;


public class NetworkTestClient {

	public static void main(String[] args) {
		
		Client client = null;
		
		Scanner scanner = new Scanner(System.in);
		
		String ipAddressString;
		InetAddress ipAddress = null;
		
		do {
			System.out.println("Geben Sie die IP an:");
			ipAddressString = scanner.nextLine();
			try {
				ipAddress = InetAddress.getByName(ipAddressString);		
			} catch (Exception e) { 
				System.err.println("IP-Adresse ungueltig.");
			}
		} while (ipAddress == null);
		
		System.out.println("Verbinde zum Server " + ipAddressString + ":51515");
		try {
			client = new Client("test", ipAddress, 51515);
			if (client != null) {
				System.out.println("Verbindung hergestellt.");
				System.out.println("Sende Nachricht 'Hallo Welt' 2 mal.");
				String content = "Hallo Welt.";
				byte[] encoded = content.getBytes();
				
				NetMessage message = new NetMessage(MessageType.CHATMASSAGE_TOSERVER, encoded);
				
				client.SendMessage(message);
				System.out.println("Nachricht versendet.");						
				
				client.SendMessage(message);
				System.out.println("Nachricht versendet.");		
			}
		}
		catch (RuntimeException e) {
			System.err.println("Konnte verbindung zum Server nicht herstellen.");
		}		
	}	
}

