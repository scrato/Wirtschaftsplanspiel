package Client.Application;

import Client.Entities.Company;
import Client.Entities.Ressource;
import Client.Application.UserCanNotPayException;

public abstract class CompanyController {
	
	public static void buyItem(double amount) throws UserCanNotPayException{
		if(Company.getInstance().isLiquid(amount))
			throw new UserCanNotPayException();
		Company.getInstance().decMoney(amount);
	}
	
	
	
	// Begin Ressource-Abschnitt
	/**
	 * Initialisiert die Ressource des mitgegebenen typs 
	 * mit neuer Anzahl von vorhandenen Einheiten und dem Preis pro Kilo.
	 * Verwendbar für festlegung nach neuer Periode
	 * @param type
	 * @param availableUnits
	 * @param pricePerKilo
	 */
	public static void initRessource(Ressource.RessourceType type, int availableUnits, double pricePerKilo){
		Ressource.getInstance(type).setAvailableUnits(availableUnits);
		Ressource.getInstance(type).setPricePerKilo(pricePerKilo);
	}

	public static void buyRessources(Ressource.RessourceType type, int amount) throws UserCanNotPayException{
		Ressource res = Ressource.getInstance(type); 
		//Sind nicht genug Rohstoffe da, hol einfach den Rest
		if(res.getAvailableUnits() > amount)
			amount = res.getAvailableUnits();
		buyItem(amount * res.getPricePerKilo());
	}

	//End of Ressource-Abschnitt
}
