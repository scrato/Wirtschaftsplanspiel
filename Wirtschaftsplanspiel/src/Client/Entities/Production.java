package Client.Entities;

import java.util.HashMap;
import java.util.Map;

import Client.Entities.Ressource.RessourceType;

public class Production {
private Map<RessourceType,Integer> ressourceNeeded;
	/**
	 * Erstellt eine neue Produktion und initialisiert die Mengen an Ressourcen
	 * die f�r die Produktion ben�tigt sind.
	 */
   public Production(){
	   //TODO: Personalbedarf f�r Produktion
	   ressourceNeeded =  new HashMap<RessourceType, Integer>();
	   ressourceNeeded.put(RessourceType.Color, 2);
	   ressourceNeeded.put(RessourceType.Plastic, 5);
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
    * @return Die Anzahl der G�ter, die produziert werden k�nnen
    */
   public int canProduce(RessourceType type){
	   Company comp = Company.getInstance();
	   Ressource res = comp.getRessource(type);
	   return res.getStoredUnits() / ressourceNeeded.get(type);
   }
   
}
