package tests;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import common.entities.Supply;

import Server.Application.AppContext;
import Server.Application.ServerController;
import Server.Entities.Disposal;

public class DemandFunctionTest {

	public static void main(String[] args) {		
		
		Map<Integer, Supply> supplies = new TreeMap<Integer, Supply>();
		
		Supply A = new Supply(11000, 3.532d);//0.621d);
		supplies.put(1, A);
		
		Supply B = new Supply(10000, 3.514d);
		supplies.put(2, B);
		
		Supply C = new Supply(10000, 3.488d);
		supplies.put(3, C);
		
		Supply D = new Supply(15000, 3.391d);
		supplies.put(4, D);
		
		AppContext.standardDemand = supplies.size() * AppContext.STANDARD_DEMAND_PER_PLAYER;
		
		System.out.println("Standardnachfrage (zu 0,5 pro Einheit): " + AppContext.standardDemand);
		
		System.out.println("Angebot:");
		//for (Supply sup : supplies.values()) {
		for (Entry<Integer, Supply> sup : supplies.entrySet()) {	
			System.out.println("   " + sup.getKey() + ": Menge: " + sup.getValue().quantity + "; Preis: " + sup.getValue().price);
		}
		System.out.println("Kalkuliere Absätze");
		Map<Integer, Disposal> disposals = ServerController.demandFunction(supplies);
		
		int totalDisposalQuantity = 0;
		for (Disposal disposal : disposals.values()) {
			totalDisposalQuantity += disposal.quantity;
		}
		System.out.println("Erzielter Gesamtabsatz: " + totalDisposalQuantity);
		
		System.out.println("Absätze:");
		for (Disposal disposal : disposals.values()) {
			System.out.println("   " + disposal.clientID + ": Absatz: " + disposal.quantity + "; Wert: " + disposal.quantity * disposal.price);
			
		}
		
	}
	
}
