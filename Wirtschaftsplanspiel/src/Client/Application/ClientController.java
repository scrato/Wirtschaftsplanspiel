package Client.Application;

import Client.Entities.Player;
import NetworkCommunication.ByteConverter;
import NetworkCommunication.NetMessage;

public abstract class ClientController {
	
	public static void PlayerListReceived(Player[] players) {
		//TODO Weitergabe zum WarteScreen im UI.
	}
	
	public static void NewPlayerJoined(NetMessage message) {
		byte[] content = message.get_Content();
		byte[] idBytes = new byte[4];
		System.arraycopy(content, 0, idBytes, 0, 4);
		int playerID = ByteConverter.toInt(idBytes);
		byte[] nameBytes = new byte[20];
		System.arraycopy(content, 4, nameBytes, 0, 20);
		String playerName = new String(nameBytes);
		
		Player newPlayer = new Player(playerID, playerName);
		
		//TODO Weitergabe zum UI.
	}
	
	public static void PlayerLeft(NetMessage message) {
		int ID = ByteConverter.toInt(message.get_Content());
		Player leftPlayer = Player.getPlayer(ID);		
		Player.removePlayer(ID);
		
		//TODO Weitergabe UI.
	}
	
}
