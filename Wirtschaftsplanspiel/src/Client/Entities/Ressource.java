package Client.Entities;



public class Ressource {

	/**
	 * RessourceTyp enth�lt die verf�gbaren Typen von Ressourcen
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
			return " St�ck";
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
 * Konstruktor f�r einen neuen Typ, wird nur von getInstance benutzt.
 * @param type
 */
public Ressource(RessourceType type, String unit) {
	this.unit = unit;
}

/**
 * Gibt den derzeit gesetzten preis pro Einheit (Unit) zur�ck
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
 * Erh�ht die Ressourcen, die im Lager liegen
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
