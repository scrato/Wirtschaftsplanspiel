package Client.Application;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.List;

import Client.Entities.PeriodInfo;
import Client.Entities.Player;
import Client.Network.Client;
import Client.Presentation.MainWindow;
import Client.Presentation.ResultPanel;
import NetworkCommunication.ByteConverter;
import NetworkCommunication.NetMessage;

public abstract class ClientController {
	
	public static Client ConnectToServer(String Name, InetAddress Address, int Port) {
		return Client.connect(Name, Address, Port);
	}
	
	public static void Disconnected() {
		MainWindow wind = MainWindow.getInstance();
		wind.notifyDisconnect();
	}
	
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
			playerName = new String(nameBytes, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
		playerName.trim();
		new Player(playerID, playerName);
		
		MainWindow wind = MainWindow.getInstance();
		wind.setPlayers(Player.getPlayers());
		
		String displayString = playerName + " ist dem Spiel beigetreten.";
		wind.addChatMessage(displayString);
	}
	
	public static void PlayerLeft(NetMessage message) {
		int ID = ByteConverter.toInt(message.get_Content());
		Player leftPlayer = Player.getPlayer(ID);	
		if (PeriodInfo.gameStarted == false) {
			Player.removePlayer(ID);
			
			MainWindow.getInstance().setPlayers(Player.getPlayers());
		} else {
			leftPlayer.leaveGame();
			int activePlayers = 0;
			List<Player> players = Player.getPlayers();
			for (Player player : players) {
				if (!player.hasLeftGame() && !player.isInsolvent()) {
					activePlayers++;
				}
			}
			if (activePlayers <= 1 ) {
				MainWindow.getInstance().changeScreen(new ResultPanel(ResultPanel.FinishReason.OnePlayerLeft));
			}
		}
		//if (Player.getPlayers().isEmpty()) //TODO sinnvoll? müsste sein: Players.getPlayers().size <= 1. doof zum testen.
		//	MainWindow.getInstance().changeScreen(new ResultPanel(ResultPanel.FinishReason.OnePlayerLeft));
		
		String displayString = leftPlayer.getName() + " hat das Spiel verlassen.";
		MainWindow.getInstance().addChatMessage(displayString);
	}
	
	public static void GameStarted() {
		PeriodInfo.gameStarted = true;
		MainWindow.getInstance().startGame();
	}
	
}
