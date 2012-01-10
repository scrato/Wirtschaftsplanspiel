package Client.Entities;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Client.Application.NotEnoughRessourcesException;
import Client.Application.NotEnoughMachinesException;
import Client.Entities.Ressource.RessourceType;

public class Production {
private Map<RessourceType,Integer> ressourceNeeded;
private Map<MachineType, Integer> maxProdPerMach;
	/**
	 * Erstellt eine neue Produktion und initialisiert die Mengen an Ressourcen
	 * die für die Produktion benötigt sind.
	 */
   public Production(){
	   //TODO: Personalbedarf für Produktion
	   //TODO: Produktionsverhältnisse aktuell halten / GetNeedForProduction implementieren
	   ressourceNeeded =  new HashMap<RessourceType, Integer>();
	   ressourceNeeded.put(RessourceType.Color, 2);
	   ressourceNeeded.put(RessourceType.Plastic, 5);
	   
	   maxProdPerMach = new HashMap<MachineType, Integer>();
	   maxProdPerMach.put(MachineType.test1, 1);
	   maxProdPerMach.put(MachineType.test2, 2);
   }
   
   /**
    * Gibt zurück, wieviel Einheiten vom Ressourcentyp benötigt werden, um eine Fertigeinheit zu erstellen
    * @param type Der Ressourcetyp, der abgefragt werden soll
    * @return Die Anzahl der Ressourcen, die gebraucht werden um ein Gut zu produzieren
    */
   public int getNeedForRessource(RessourceType type){
	   return ressourceNeeded.get(type);
   }
   
   /**
    * Gibt zurück wieviele Güter, die derzeit auf Lager sind, produziert werden können. 
    * @param type Der Ressourcetyp, der abgefragt werden soll
    * @throws NotEnoughRessourcesException Wirft eine Exception, die die Information enthält, wieviele Units noch von welcher Ressource fehlen.
 * @throws NotEnoughMachinesException Wenn nicht genug Machinen eines Types für die Produktion da sind, wird diese Exception geworfen.
    */
   public void canProduce(int units) throws NotEnoughRessourcesException, NotEnoughMachinesException{
	   Company comp = Company.getInstance();
	   
	   //Ressourcen prüfen
	   Dictionary<RessourceType, Ressource> ressources = comp.getAllRessources();
	   NotEnoughRessourcesException resExc = new NotEnoughRessourcesException();
	   while(ressources.elements().hasMoreElements()){
		   Ressource res = ressources.elements().nextElement();
			//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Maschinen fehlen.
		   int missingunits = (ressourceNeeded.get(res.getType())* units)  - res.getAvailableUnits();
		   if (missingunits > 0)
			   resExc.addNewRessource(res.getType(), missingunits);
	   }
	   if(resExc.isFilled())
		   throw resExc;
	   
	   //Maschinen prüfen
	   NotEnoughMachinesException  macExc = new NotEnoughMachinesException();
	   	for(MachineType type: MachineType.values()){
	   		
	   		//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Maschinen fehlen.
	   		int missingunits = units - comp.getMachineCapacity(type);
	   		if (missingunits > 0)
	   			macExc.AddMachine(type, missingunits);
	   	}
	   	if(macExc.isFilled())
	   		throw macExc;
	   	
	   	
	   	//TODO: Personal prüfen
   }
   
 
}
