package Client.Application;

import java.io.UnsupportedEncodingException;

import Client.Entities.Player;
import Client.Presentation.MainWindow;
import NetworkCommunication.ByteConverter;
import NetworkCommunication.NetMessage;

public abstract class ClientController {
	
	public static void PlayerListReceived() {
		MainWindow wind = MainWindow.getInstance();
		wind.setPlayers(Player.getPlayers());
	}
	
	public static void NewPlayerJoined(NetMessage message) {
		byte[] content = message.get_Content();
		byte[] idBytes = new byte[4];
		System.arraycopy(content, 0, idBytes, 0, 4);
		int playerID = ByteConverter.toInt(idBytes);
		byte[] nameBytes = new byte[20];
		System.arraycopy(content, 4, nameBytes, 0, 20);
		String playerName = null;
		try {
			playerName = new String(nameBytes, "UTF-16");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
		
		new Player(playerID, playerName);
		
		MainWindow wind = MainWindow.getInstance();
		wind.setPlayers(Player.getPlayers());
		
		String displayString = playerName + " ist dem Spiel beigetreten.";
		wind.addChatMessage(displayString);
	}
	
	public static void PlayerLeft(NetMessage message) {
		int ID = ByteConverter.toInt(message.get_Content());
		Player leftPlayer = Player.getPlayer(ID);		
		Player.removePlayer(ID);
		
		MainWindow wind = MainWindow.getInstance();
		wind.setPlayers(Player.getPlayers());
		
		String displayString = leftPlayer.getName() + " hat das Spiel verlassen.";
		wind.addChatMessage(displayString);
		//TODO Weitergabe UI.
	}
	
	public static void GameStartet() {
		MainWindow wind = MainWindow.getInstance();
		wind.startGame();
	}
	
}
