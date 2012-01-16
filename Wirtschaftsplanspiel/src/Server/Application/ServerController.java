package Server.Application;


import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

import NetworkCommunication.ChatMessageToClient;
import NetworkCommunication.ChatMessageToServer;
import NetworkCommunication.SendAssignedDemandMessage;
import NetworkCommunication.SendSupplyMessage;
import Server.Network.Server;
import Server.Network.ClientHandler;
import common.entities.Supply;



public class ServerController {
	
	// BEGIN OF Chatnachricht weiterleiten.
	
	public static void redirectChatMessage(ClientHandler sender, ChatMessageToServer message) {
		System.out.println("Chatnachricht gesendet von Client " + sender.get_ID());
		
		ChatMessageToClient sendMessage = null;
		try {
			sendMessage = new ChatMessageToClient(message.get_Message(), sender.get_Name());
		} catch (UnsupportedEncodingException e1) {
			// should never reach this point.
		}
		
		List<ClientHandler> clients = Server.getInstance().getClients();
		for (ClientHandler client : clients) {
			try {
				client.SendMessage(sendMessage);
			} catch (Exception e) { }
		}
	}
	
	// END OF Chatnachricht weiterleiten.
	// -----------------------------------------
	// BEGIN OF Perdiodenabschluss.
	
	private static Semaphore lockSupplies = new Semaphore(1);
	
	private static Map<Integer, Supply> supplies = new TreeMap<Integer, Supply>();
	
	public static void receiveSupply(ClientHandler Sender, SendSupplyMessage Message) {		
		lockSupplies.acquireUninterruptibly();
		try {
			supplies.put(Sender.get_ID(), Message.getSupply());
		} catch (Exception e) {
		} finally {
			lockSupplies.release();
		}
		checkSupplies();
	}
	
	/**
	 * Prüft, ob alle Spieler ihre Angebote abgegeben haben, führt die Verteilung aus und übermittelt die zugewiesene Nachfrage an die Spieler.
	 */
	public static void checkSupplies() {
		List<ClientHandler> clients = Server.getInstance().getClients();		
		if (clients.isEmpty()) return;
		
		Map<Integer, Integer> assignedDemands = null;
		lockSupplies.acquireUninterruptibly();
		try {
			for (ClientHandler client : clients) {
				if (!supplies.containsKey(client.get_ID())) {
					lockSupplies.release();
					return;
				}
			}
			// alle Angebote wurden abgebgeben. Führe demandFunction aus.
			assignedDemands = demandFunction(supplies);
		
			int quantity;
			for (ClientHandler client : clients) {
				// TODO assigned Demand an Client übergeben.
				try {
					quantity = assignedDemands.get(client.get_ID());
					client.SendMessage(new SendAssignedDemandMessage(quantity));
				} catch (Exception e) { }
			}
			// Nachfrageanteile wurden an Clients verschickt, Angebote werden zurückgesetzt.
			supplies.clear();
			
		} catch (Exception e) {
		} finally {
			lockSupplies.release();
		}
	}
	
	/**
	 * determines contingents of total demand for all clients.
	 * @param Supplies: Map of Supplies for all Client(ID)companyResult.
	 * @return Contingents: Map of Contingents for all Client(ID)companyResult.
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
	
	// END OF Pediodenabschluss.
}
