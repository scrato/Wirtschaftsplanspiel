package Client.Entities;

import java.util.Dictionary;

public class Ressource {
	private static Dictionary<RessourceType, Ressource> ressources;
	/**
	 * RessourceTyp enth�lt die verf�gbaren Typen von Ressourcen
	 * @author Scrato
	 *
	 */
	public enum RessourceType {
		Plastic,
		Color
	}
	
	
	


/**
 * Gibt das gew�nschte Ressourcenobjekt von dem mitgegebenen Typ zur�ck
 * @param type Der Typ der Ressource
 * @return
 */
public static Ressource getInstance(RessourceType type){
	if(ressources != null)
		return ressources.get(type);
	
	/* Falls ressources noch nicht gew�hlt ist, 
	 * wird f�r jeden Ressourcetype ein Eintrag im Dictionary angelegt
	 */
	for(RessourceType t:RessourceType.values()){
		//TODO: Dynamische Methode zum setzen der Einheit implementieren
		ressources.put(t, new Ressource("kilo"));
	}
	
	
	return ressources.get(type);
}


private double pricePerUnit;
private String unit;
private int storedUnits;
private int availableUnits;

/**
 * Konstruktor f�r einen neuen Typ, wird nur von getInstance benutzt.
 * @param type
 */
private Ressource(String unit) {
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
