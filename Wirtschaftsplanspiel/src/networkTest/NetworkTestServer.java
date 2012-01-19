package networkTest;

import java.util.Scanner;

import Server.Network.Server;


public class NetworkTestServer {

	public static void main(String[] args) {
		Server server = new Server(51515, 10);
		
		System.out.println("-1 zum Beenden.");
		Scanner scanner = new Scanner(System.in);		
		String input = "";	
		
		do {
			input = scanner.nextLine();
		} while (!input.equals("-1"));

		server.close();
	}
	
}
