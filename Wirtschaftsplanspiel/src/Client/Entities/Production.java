package Client.Entities;

import java.util.HashMap;
import java.util.Map;

import Client.Entities.Ressource.RessourceType;

public class Production {
private Map<RessourceType,Integer> ressourceNeeded;
	/**
	 * Erstellt eine neue Produktion und initialisiert die Mengen an Ressourcen
	 * die für die Produktion benötigt sind.
	 */
   public Production(){
	   //TODO: Personalbedarf für Produktion
	   ressourceNeeded =  new HashMap<RessourceType, Integer>();
	   ressourceNeeded.put(RessourceType.Color, 2);
	   ressourceNeeded.put(RessourceType.Plastic, 5);
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
    * @return Die Anzahl der Güter, die produziert werden können
    */
   public int canProduce(RessourceType type){
	   Company comp = Company.getInstance();
	   Ressource res = comp.getRessource(type);
	   return res.getStoredUnits() / ressourceNeeded.get(type);
   }
   
}
