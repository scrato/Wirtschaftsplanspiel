package Server.Entities;

import java.util.Map;
import java.util.TreeMap;

import Server.Entities.Disposal;

public class PeriodBuffer {
	
	public static Map<Integer, Disposal> Disposals = new TreeMap<Integer, Disposal>();
	public static int getTotalDisposal () {
		int total = 0;
		for (Disposal dis : Disposals.values()) {
			total += dis.quantity;
		}
		return total;
	}
	
}
