package Client.Entities;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import common.entities.CompanyResult;

public class Player {

	private int id;
	private String name;
	private List<CompanyResult> resultList = new LinkedList<CompanyResult>();
	
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
	private static TreeMap<Integer, Player> playerDict = new TreeMap<Integer, Player>();
	
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

	public CompanyResult getCompanyResult(int period) {
		return resultList.get(period);
	}

	public void addCompanyResult(CompanyResult result) {
		this.resultList.add(result);
	}
	
	private static boolean isHost;
	
	public static void setHost(boolean value){
		isHost = value;
	}
	
	public static boolean isHost(){
		return isHost;
	}
}
