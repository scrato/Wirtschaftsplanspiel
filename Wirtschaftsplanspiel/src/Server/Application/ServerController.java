package Server.Application;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import Server.Model.Supply;


public class ServerController {

	/**
	 * determines contingents of total demand for all clients.
	 * @param Supplies: Map of Supplies for all Client(ID)s.
	 * @return Contingents: Map of Contingents for all Client(ID)s.
	 */
	public static Map<Integer, Integer> demandFunction(Map<Integer, Supply> Supplies) {
		
		// create a new map to keep input list unchanged -> faked call-by-value.
		Map<Integer, Supply> supplies = new TreeMap<Integer, Supply>();
		supplies.putAll(Supplies);
		
		int leftDemand = AppContext.totalDemand;
		
		// initialize assignedDemand
		Map<Integer, Integer> assignedDemand = new TreeMap<Integer, Integer>();
		for (Entry<Integer, Supply> sup : supplies.entrySet()) {
			assignedDemand.put(sup.getKey(), 0); 
		}
		
		while (leftDemand > 0 && supplies.size() > 0) {
			// calculate average price
			double averagePrice = 0d;
			for (Entry<Integer, Supply> sup : supplies.entrySet()) {
				averagePrice += sup.getValue().price;							
			}
			averagePrice /= supplies.size();
			
			Map<Integer, Double> factors = new TreeMap<Integer, Double>();
			double factorsSum = 0d;
			// calculate factor: Relation between average price and supplied price, powered by 1,5.
			for (Entry<Integer, Supply> sup : supplies.entrySet()) {
				Double factor = Math.pow(averagePrice / sup.getValue().price, 1.5);
				factors.put(sup.getKey(), factor);
				factorsSum += factor;
			}
			
			// calculate contingents by factors.
			Map<Integer, Integer> contingents = new TreeMap<Integer, Integer>();
			for (Entry<Integer, Double> factor : factors.entrySet()) {
				Integer contingent = (int) Math.round( leftDemand * factor.getValue() / factorsSum );
				contingents.put(factor.getKey(), contingent);
			}
			
			List<Integer> removedKeys = new LinkedList<Integer>();
			
			// split demand to players.
			for (Integer key : supplies.keySet()) {
				Integer availableDemand = contingents.get(key);
				if (supplies.get(key).quantity <= availableDemand) {
					// player wants to sell less-equal than he is able to.
					Integer demand = (Integer)assignedDemand.get(key);
					demand += supplies.get(key).quantity;
					assignedDemand.put(key, demand);
					
					removedKeys.add(key);
					
					leftDemand -= supplies.get(key).quantity;
				} else {
					// player wants to sell more than he is able to.
					Integer demand = (Integer)assignedDemand.get(key);
					demand += availableDemand;
					assignedDemand.put(key, demand);
					
					Supply sup = supplies.get(key);
					sup.quantity -= availableDemand;
					
					leftDemand -= availableDemand;
				}
			}
			
			for (Integer key : removedKeys) {
				supplies.remove(key);
			}
		
		}
		
		return assignedDemand;
	}
}
