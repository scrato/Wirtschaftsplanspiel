package Client.Entities;

public class Player {

	private int id;
	private String name;
	
	public Player(int ID, String Name) {
		id = ID;
		name = Name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
