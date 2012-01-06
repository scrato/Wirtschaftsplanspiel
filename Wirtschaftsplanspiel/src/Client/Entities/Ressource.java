package Client.Entities;

import java.util.Dictionary;

public class Ressource {
	private static Dictionary<RessourceType, Ressource> ressources;
	/**
	 * RessourceTyp enthält die verfügbaren Typen von Ressourcen
	 * @author Scrato
	 *
	 */
	public enum RessourceType {
		Plastic,
		Color
	}
	


/**
 * Gibt das gewünschte Ressourcenobjekt von dem mitgegebenen Typ zurück
 * @param type Der Typ der Ressource
 * @return
 */
public static Ressource getInstance(RessourceType type){
	if(ressources != null)
		return ressources.get(type);
	
	/* Falls ressources noch nicht gewählt ist, 
	 * wird für jeden Ressourcetype ein Eintrag im Dictionary angelegt
	 */
	for(RessourceType t:RessourceType.values()){
		ressources.put(t, new Ressource(t));
	}
	
	
	return ressources.get(type);
}


private RessourceType type;
private double pricePerKilo;
private int storedUnits;
private int availableUnits;

public Ressource(RessourceType type) {
	this.type = type;
}
public RessourceType getType() {
	return type;
}

public double getPricePerKilo() {
	return pricePerKilo;
}
public void setPricePerKilo(double pricePerKilo) {
	this.pricePerKilo = pricePerKilo;
}

public int getStoredUnits() {
	return storedUnits;
}

/**
 * Erhöht die Ressourcen, die im Lager liegen
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
