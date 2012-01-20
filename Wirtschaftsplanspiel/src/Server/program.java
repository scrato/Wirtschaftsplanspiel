package Server;

import java.util.Scanner;

import Server.Network.Server;
import Server.Application.ServerController;

public class program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = ServerController.StartServer(51515, 10); //new Server(51515, 10);
		
		System.out.println("-help f�r Hilfe./n -1 zum Beenden,/n 0 zum Spielstart.");
		Scanner scanner = new Scanner(System.in);		
		String input = "";	
		
		while (true) {
			input = scanner.nextLine();
			if (input.equals("-1")) break;
			if (input.equals("-help")) System.out.println("-help f�r Hilfe. -1 zum Beenden, 0 zum Spielstart.");
			if (input.equals("1"))  {
				ServerController.StartGame();
			}
		} 

		server.close();
	}

}
