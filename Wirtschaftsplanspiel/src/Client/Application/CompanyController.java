package Client.Application;

import Client.Entities.Company;
import Client.Entities.Ressource;
import Client.Application.UserCanNotPayException;

public abstract class CompanyController {
	
	/**
	 * Ein mitgegebener Preis wird vom Guthaben des Unternehmens abgezogen.
	 * Sollte das Unternehmen nicht mehr liquide sein, wird eine UserCanNotPayException geworfen
	 * @param price Der Preis
	 * @throws UserCanNotPayException
	 */
	public static void buyItem(double price) throws UserCanNotPayException{
		if(Company.getInstance().isLiquid(price))
			throw new UserCanNotPayException();
		Company.getInstance().decMoney(price);
	}
	
	
	
	// Begin Ressource-Abschnitt
	/**
	 * Initialisiert die Ressource des mitgegebenen typs 
	 * mit neuer Anzahl von vorhandenen Einheiten und dem Preis pro Einheit.
	 * Verwendbar für Festlegung nach neuer Periode
	 * @param type Der RessourceTyp der zu kaufenden Ressource
	 * @param availableUnits Die Anzahl der verfügbaren Ressorucen
	 * @param pricePerUnit Den preis pro Kilo
	 */
	public static void initRessource(Ressource.RessourceType type, int availableUnits, double pricePerUnit){
		Ressource.getInstance(type).setAvailableUnits(availableUnits);
		Ressource.getInstance(type).setPricePerUnit(pricePerUnit);
	}

	/**
	 * Kauft eine Anzahl von Ressourcen eines Typs ein. 
	 * Sollte der Nutzer nicht liquide sein, wird eine UserCanNotPayException geworfen
	 * @param type Der RessourceTyp der zu kaufenden Ressource
	 * @param amount Die Anzahl der zu kaufenden Ressourcen
	 * @throws UserCanNotPayException
	 */
	public static void buyRessources(Ressource.RessourceType type, int amount) throws UserCanNotPayException{
		Ressource res = Ressource.getInstance(type); 
		//Sind nicht genug Rohstoffe da, hol einfach den Rest
		if(res.getAvailableUnits() > amount)
			amount = res.getAvailableUnits();
			buyItem(amount * res.getPricePerUnit());
		
	}

	//End of Ressource-Abschnitt
}
