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
	 * die f�r die Produktion ben�tigt sind.
	 */
   public Production(){
	   //TODO: Personalbedarf f�r Produktion
	   //TODO: Produktionsverh�ltnisse aktuell halten / GetNeedForProduction implementieren
	   ressourceNeeded =  new HashMap<RessourceType, Integer>();
	   ressourceNeeded.put(RessourceType.Color, 2);
	   ressourceNeeded.put(RessourceType.Plastic, 5);
	   
	   maxProdPerMach = new HashMap<MachineType, Integer>();
	   maxProdPerMach.put(MachineType.test1, 1);
	   maxProdPerMach.put(MachineType.test2, 2);
   }
   
   /**
    * Gibt zur�ck, wieviel Einheiten vom Ressourcentyp ben�tigt werden, um eine Fertigeinheit zu erstellen
    * @param type Der Ressourcetyp, der abgefragt werden soll
    * @return Die Anzahl der Ressourcen, die gebraucht werden um ein Gut zu produzieren
    */
   public int getNeedForRessource(RessourceType type){
	   return ressourceNeeded.get(type);
   }
   
   /**
    * Gibt zur�ck wieviele G�ter, die derzeit auf Lager sind, produziert werden k�nnen. 
    * @param type Der Ressourcetyp, der abgefragt werden soll
    * @throws NotEnoughRessourcesException Wirft eine Exception, die die Information enth�lt, wieviele Units noch von welcher Ressource fehlen.
 * @throws NotEnoughMachinesException Wenn nicht genug Machinen eines Types f�r die Produktion da sind, wird diese Exception geworfen.
    */
   public void canProduce(int units) throws NotEnoughRessourcesException, NotEnoughMachinesException{
	   Company comp = Company.getInstance();
	   
	   //Ressourcen pr�fen
	   Dictionary<RessourceType, Ressource> ressources = comp.getAllRessources();
	   NotEnoughRessourcesException resExc = new NotEnoughRessourcesException();
	   while(ressources.elements().hasMoreElements()){
		   Ressource res = ressources.elements().nextElement();
			//MissingUnits sind die Einheiten, die nicht produziert werden k�nnen, weil Maschinen fehlen.
		   int missingunits = (ressourceNeeded.get(res.getType())* units)  - res.getAvailableUnits();
		   if (missingunits > 0)
			   resExc.addNewRessource(res.getType(), missingunits);
	   }
	   if(resExc.isFilled())
		   throw resExc;
	   
	   //Maschinen pr�fen
	   NotEnoughMachinesException  macExc = new NotEnoughMachinesException();
	   	for(MachineType type: MachineType.values()){
	   		
	   		//MissingUnits sind die Einheiten, die nicht produziert werden k�nnen, weil Maschinen fehlen.
	   		int missingunits = units - comp.getMachineCapacity(type);
	   		if (missingunits > 0)
	   			macExc.AddMachine(type, missingunits);
	   	}
	   	if(macExc.isFilled())
	   		throw macExc;
	   	
	   	
	   	//TODO: Personal pr�fen
   }
   
 
}
