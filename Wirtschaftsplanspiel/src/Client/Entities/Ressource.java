package Client.Entities;



public class Ressource {

	/**
	 * RessourceTyp enthält die verfügbaren Typen von Ressourcen
	 * @author Scrato
	 *
	 */
	public enum RessourceType {
		Stockfisch,
		Verpackungsmaterial
	}
	
	public static String getUnit(RessourceType type){
		//TODO: Units aktuell halten
		switch(type){
		case Stockfisch:
			return " Stück";
		case Verpackungsmaterial:
			return " Einheiten";
		default:
			return "Einheiten";
		}
	}
	
	public static int getNeed(RessourceType type){
		switch(type){
		case Stockfisch:
			return 10;
		case Verpackungsmaterial:
			return 1;
		default:
			//Typ wurde noch nicht festeglegt;
			throw new UnsupportedOperationException();
		}
	}
	

private double pricePerUnit;
private String unit;
private RessourceType type;
private int storedUnits;
private int availableUnits;

/**
 * Konstruktor für einen neuen Typ, wird nur von getInstance benutzt.
 * @param type
 */
public Ressource(RessourceType type, String unit) {
	this.unit = unit;
}

/**
 * Gibt den derzeit gesetzten preis pro Einheit (Unit) zurück
 * @return
 */
public double getPricePerUnit() {
	return pricePerUnit;
}



public RessourceType getType(){
	return this.type;
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
 * Erhöht die Ressourcen, die im Lager liegen
 * @param amount
 */
public void incStoredUnits(int amount){
	storedUnits += amount;
	Company.getInstance().getActualPeriod().addBoughtRessource(this, amount);
}

public void decStoredUnits(int amount){
	storedUnits -= amount;
	Company.getInstance().getActualPeriod().addUsedRessource(this, amount);
}

public int getBuyableUnits() {
	return availableUnits;
}

public void setBuyableUnits(int units){
	availableUnits = units;
}

}
