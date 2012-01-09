package Client.Entities;

import java.util.Dictionary;

public class Ressource {

	/**
	 * RessourceTyp enth�lt die verf�gbaren Typen von Ressourcen
	 * @author Scrato
	 *
	 */
	public enum RessourceType {
		Plastic,
		Color
	}
	
	

private double pricePerUnit;
private String unit;
private int storedUnits;
private int availableUnits;

/**
 * Konstruktor f�r einen neuen Typ, wird nur von getInstance benutzt.
 * @param type
 */
public Ressource(String unit) {
	this.unit = unit;
}

/**
 * Gibt den derzeit gesetzten preis pro Einheit (Unit) zur�ck
 * @return
 */
public double getPricePerUnit() {
	return pricePerUnit;
}


public void setPricePerUnit(double pricePerUnit) {
	this.pricePerUnit = pricePerUnit;
}

public int getStoredUnits() {
	return storedUnits;
}

public String getUnit(){
	return unit;
}


/**
 * Erh�ht die Ressourcen, die im Lager liegen
 * @param amount
 */
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
