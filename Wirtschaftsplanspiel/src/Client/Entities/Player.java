package Client.Entities;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Player {

	private int id;
	private String name;
	
	public Player(int ID, String Name) {
		id = ID;
		name = Name;
		
		playerDict.put(id, this);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	
	//PlayerList
	private static TreeMap<Integer, Player> playerDict;
	
	public static List<Player> getPlayers() {
		List<Player> retList = new LinkedList<Player>();
		retList.addAll(playerDict.values());
		return retList;
	}
	
	public static Player getPlayer(Integer ID) {
		return playerDict.get(ID);
	}
	
	public static void removePlayer(Integer ID) {
		playerDict.remove(ID);
	}
	
}
