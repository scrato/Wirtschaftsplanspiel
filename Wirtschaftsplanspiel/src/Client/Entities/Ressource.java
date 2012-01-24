package Client.Entities;

import Client.Application.NotEnoughRessourcesException;



public class Ressource {

	/**
	 * RessourceTyp enth�lt die verf�gbaren Typen von Ressourcen
	 * @author Scrato
	 *
	 */

	
	public static String getUnit(RessourceType type){
		//TODO: Units aktuell halten
		switch(type){
		case Rohfisch:
			return "Kilo";
		case Verpackungsmaterial:
			return "Tonnen";
		default:
			return "Einheiten";
		}
	}
	
	public static double getFixedCosts(RessourceType type){
		switch(type){
		case Rohfisch:
			return 295.00;
		case Verpackungsmaterial:
			return 140.00;
		default:
			//Typ wurde noch nicht festeglegt;
			throw new UnsupportedOperationException();
		}
	}
	
	
	public static int getNeed(RessourceType type){
		switch(type){
		case Rohfisch:
			return 120;
		case Verpackungsmaterial:
			return 30;
		default:
			//Typ wurde noch nicht festeglegt;
			throw new UnsupportedOperationException();
		}
	}
	

private double pricePerUnit;
private String unit;
private RessourceType type;
private int storedUnits = 0;
private int availableUnits = Integer.MAX_VALUE;

/**
 * Konstruktor f�r einen neuen Typ, wird nur von getInstance benutzt.
 * @param type
 */
public Ressource(RessourceType type, String unit) {
	this.type = type;
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
	
	
	//Logging
	Period p = PeriodInfo.getActualPeriod();
	

	
	//Bestandsver�nderung
	p.incRessourcePriceDelta(amount * this.getPricePerUnit());
	
	//Kopie der Ressource-Klasse wird in gekaufte Ressource mitgegeben
	Ressource res = new Ressource(this.type, getUnit(this.type));
	res.setPricePerUnit(this.getPricePerUnit());
	res.setStoredUnits(storedUnits);
	
	if(p.getBoughtRessources().get(res) != null)
		p.addBoughtRessource(res, p.getBoughtRessources().get(res) + amount);
	else
		p.addBoughtRessource(res, amount);
}

private void setStoredUnits(int storedUnits2) {
	storedUnits = storedUnits2;
	
}

public void decStoredUnits(int amount){
	storedUnits -= amount;
	
	//Logging
	
	Period p = PeriodInfo.getActualPeriod();
	

	//Bestandsver�nderung
	p.decRessourcePriceDelta(amount * this.getPricePerUnit());

	//Kopie der Ressource-Klasse wird in verkaufte Ressource mitgegeben
	Ressource res = new Ressource(this.type, getUnit(this.type));
	res.setPricePerUnit(this.getPricePerUnit());
	res.setStoredUnits(this.getStoredUnits());
	//p.addUsedRessource(this, p.getUsedRessources().get(res) + amount);
	
	if(p.getUsedRessources().get(res) != null)
		p.addUsedRessource(res, p.getUsedRessources().get(res) + amount);
	else
		p.addUsedRessource(res, amount);
}

public int getBuyableUnits() {
	return availableUnits;
}

public void setBuyableUnits(int units){
	availableUnits = units;
}

public void incBuyableUnits(int units){
	availableUnits += units;
}

public void decBuyableUnits(int units) throws NotEnoughRessourcesException{
	if((availableUnits - units) < 0)
		throw new NotEnoughRessourcesException(units - availableUnits, availableUnits);
	availableUnits -= units;
}

}
