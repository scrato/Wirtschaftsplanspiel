package Client.Entities;

public class Ressource {
	
	public Ressource(String name){
		this.name = name;
	}
private String name;
private double pricePerKilo;
private int storedUnits;
private int availableUnits;
public double getPricePerKilo() {
	return pricePerKilo;
}
public void setPricePerKilo(double pricePerKilo) {
	this.pricePerKilo = pricePerKilo;
}
public String getName() {
	return name;
}
public int getStoredUnits() {
	return storedUnits;
}

public void incStoredUnits(int amount){
	storedUnits += amount;
}

public void decStoredUnits(int amount){
	storedUnits -= amount;
}

public int getAvailableUnits() {
	return availableUnits;
}

public void setAvailableUnits(int units){
	availableUnits = units;
}

}
