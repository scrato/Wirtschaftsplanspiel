package Client.Application;

import Client.Entities.Ressource;

/**
 * @author Scrato
 *
 */
public class RessourceController {
		Ressource[] ressources;
		
	public RessourceController(String[] names){
		// So viele Ressourcen wie Namen initialisiert
		ressources = new Ressource[names.length];
		for(int i = 0; i < names.length; i++){
			ressources[i] = new Ressource(names[i]);
		}
		AppContext.ressources = ressources;
	}

	/**
	 * @param id Die ID der Ressource (erste, zweite,..)
	 * @param availableUnits Wie viele Einheiten der Ressource sind vom Client zu kaufen
	 * @param pricePerKilo Wie hoch ist der derzeitige Preis pro Kilo
	 */
	public void initRessource(int id, int availableUnits, double pricePerKilo){
		ressources[id].setAvailableUnits(availableUnits);
		ressources[id].setPricePerKilo(pricePerKilo);
	}
	
	/**
	 * Kauft die angegebene Anzahl von Ressourcen
	 * @param id Die ID der Ressource (0 oder 1)
	 * @param amount Anzahl der zu kaufenden Ressourcen
	 * @throws UserCanNotPayException Sind keine liquiden Mittel zur Verfügung, 
	 * liegt es am Spieler zu entscheiden ob er nichts kauft oder das, 
	 * was er noch kaufen kann
	 */
	public void buyRessources(int id, int amount) throws UserCanNotPayException{
		Ressource res = ressources[id]; 
		//Sind nicht genug Rohstoffe da, hol einfach den Rest
		if(res.getAvailableUnits() > amount)
			amount = res.getAvailableUnits();
		AppContext.companyController.buyItem(amount * res.getPricePerKilo());
	}
	
}
