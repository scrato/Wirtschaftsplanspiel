package server.entities;

public class Disposal {

	public Disposal (int ClientID, int Quantity, double Price) {
		clientID = ClientID;
		quantity = Quantity;
		price = Price;
	}
	
	public Disposal (int ClientID) {
		this(ClientID, 0, 0.0d);
	}
	
	public int clientID;
	public int quantity;
	public double price;
	
}
