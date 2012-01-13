package Server.Application;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import common.entities.Supply;

import Server.Application.ServerController;

public class DemandFunctionTest {

	public static void main(String[] args) {		
		
		Map<Integer, Supply> supplies = new TreeMap<Integer, Supply>();
		
		Supply A = new Supply(510, 6.21d);
		supplies.put(1, A);
		
		Supply B = new Supply(500, 5.14d);
		supplies.put(2, B);
		
		Supply C = new Supply(600, 4.88d);
		supplies.put(3, C);
		
		Supply D = new Supply(750, 3.91d);
		supplies.put(4, D);
		
		System.out.println("Nachfrage: " + AppContext.totalDemand);
		
		System.out.println("Angebot:");
		//for (Supply sup : supplies.values()) {
		for (Entry<Integer, Supply> sup : supplies.entrySet()) {	
			System.out.println("   " + sup.getKey() + ": Menge: " + sup.getValue().quantity + "; Preis: " + sup.getValue().price);
		}
		System.out.println("Kalkuliere Absätze");
		Map<Integer, Integer> Sales = ServerController.demandFunction(supplies);
		
		System.out.println("Absätze:");
		for (Entry<Integer, Supply> sup : supplies.entrySet()) {
			System.out.println("   " + sup.getKey() + ": Absatz: " + Sales.get(sup.getKey()) + "; Wert: " + Sales.get(sup.getKey()) * sup.getValue().price);
			
		}
		
	}
	
}
