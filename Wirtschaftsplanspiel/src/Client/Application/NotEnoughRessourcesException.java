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
	
	/**
	 * 
	 * @param type
	 * @return Die Anzahl der fehlenden Einheiten / Ist missingUnit nicht enthälten wird -1 zurückgegeben
	 */
	public int getMissingUnits(RessourceType type) {
		if(missingUnits.containsKey(type))
			return missingUnits.get(type);
		
		return -1;
	}
}
