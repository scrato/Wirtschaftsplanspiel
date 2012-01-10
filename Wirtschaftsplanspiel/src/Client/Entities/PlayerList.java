package Client.Entities;

import java.util.LinkedList;
import java.util.List;

public abstract class PlayerList {

	private static List<Player> playerList;
	
	public static List<Player> getInstance() {
		if (playerList == null) {
			playerList = new LinkedList<Player>();
		}
		return playerList;
	}
	
}
