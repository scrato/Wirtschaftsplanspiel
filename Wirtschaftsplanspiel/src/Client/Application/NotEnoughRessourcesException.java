package Client.Application;

import java.util.HashMap;
import java.util.Map;

import Client.Entities.Ressource.RessourceType;

public class NotEnoughRessourcesException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<RessourceType,Integer> missingUnits = new HashMap<RessourceType, Integer> ();
	
	public void addNewRessource(RessourceType type, Integer missingUnits){
		this.missingUnits.put(type, missingUnits);
	}
	
	public boolean isFilled(){
		return !(missingUnits.isEmpty());
	}
	
	public int getMissingUnits(RessourceType type) {
		return missingUnits.get(type);
	}
}
