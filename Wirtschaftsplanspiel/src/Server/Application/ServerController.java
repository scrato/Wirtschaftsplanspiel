package Server.Application;


import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import NetworkCommunication.ByteConverter;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;
import NetworkCommunication.SendAssignedDemandMessage;
import Server.Network.Server;
import Server.Network.ClientHandler;
import common.entities.Supply;



public class ServerController {
	
	// BEGIN OF Chatnachricht weiterleiten.
	
	public static void redirectChatMessage(ClientHandler sender, NetMessage message) {
		System.out.println("Chatnachricht gesendet von Client " + sender.get_ID());
		
		byte[] contentLength = ByteConverter.toBytes(message.get_Content().length);
		byte[] nameBytes = null;
		try {
			nameBytes = sender.get_Name().getBytes("UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			//should never reach this point.
		}
		byte[] nameLength = ByteConverter.toBytes(nameBytes.length);
		byte[] sendBytes = new byte[message.get_Content().length + 8 + nameBytes.length];
		
		System.arraycopy(contentLength, 0, sendBytes, 0, 4);
		System.arraycopy(message.get_Content(), 0, sendBytes, 4, message.get_Content().length);
		System.arraycopy(nameLength, 0, sendBytes, 4 + message.get_Content().length, 4);
		System.arraycopy(nameBytes, 0, sendBytes, 8 + message.get_Content().length, nameBytes.length);				
		
		NetMessage sendMessage = new NetMessage(MessageType.CHATMESSAGE_TOCLIENT, sendBytes);
		
		//lock_clients.acquireUninterruptibly();
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
	
	private static Map<Integer, Supply> supplies = new TreeMap<Integer, Supply>();
	
	public static void receiveSupply(ClientHandler Sender, NetMessage Message) {
		byte[] supBytes = Message.get_Content();
		
		byte[] quantityBytes = new byte[4];
		byte[] priceBytes = new byte[8];
		
		System.arraycopy(supBytes, 0, quantityBytes, 0, 4);
		System.arraycopy(supBytes, 4, priceBytes, 0, 8);
		
		int quantity = ByteConverter.toInt(quantityBytes);
		double price = ByteConverter.toDouble(priceBytes);		
		Supply supply = new Supply(quantity, price);
		
		supplies.put(Sender.get_ID(), supply);
		checkSupplies();
	}
	
	/**
	 * Prüft, ob alle Spieler ihre Angebote abgegeben haben, führt die Verteilung aus und übermittelt die zugewiesene Nachfrage an die Spieler.
	 */
	public static void checkSupplies() {
		List<ClientHandler> clients = Server.getInstance().getClients();		
		if (clients.isEmpty()) return;
		
		for (ClientHandler client : clients) {
			if (!supplies.containsKey(client.get_ID())) return;
		}
		// alle Angebote wurden abgebgeben. Führe demandFunction aus.
		Map<Integer, Integer> assignedDemands = demandFunction(supplies);
		
		int quantity;
		for (ClientHandler client : clients) {
			// TODO assigned Demand an Client übergeben.
			quantity = assignedDemands.get(client.get_ID());
			client.SendMessage(new SendAssignedDemandMessage(quantity));
		}
	}
	
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
	
	// END OF Pediodenabschluss.
}
