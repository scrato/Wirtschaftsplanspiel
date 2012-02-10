package Client.Entities;

import java.text.DecimalFormat;
import java.text.ParseException;

public class Machine {
	public static String[] capacites = { "300", "500", "1000", "2000", "3000"};
	public static final double STANDARD_CAPACITY_FILETIERMASCHINE = 3000;
	public static final double STANDARD_CAPACITY_VERPACKUNGSMASCHINE = 3000;
	public static final double COST_PER_CAPACITY_FILETIERMASCHINE = 50;
	public static final double COST_PER_CAPACITY_VERPACKUNGSMASCHINE = 50;
	public static double calculatePrice(MachineType t, int capacitySize){
		double price = 0;
		switch(t){
			case Filitiermaschine:
				price = capacitySize * COST_PER_CAPACITY_FILETIERMASCHINE * (1+((STANDARD_CAPACITY_FILETIERMASCHINE / capacitySize - 1) / 20)) * 5; // 5 = Nutzungsdauer
				break;
			case Verpackungsmaschine:
				price = capacitySize * COST_PER_CAPACITY_VERPACKUNGSMASCHINE * (1+((STANDARD_CAPACITY_VERPACKUNGSMASCHINE / capacitySize - 1) / 20)) * 5; // 5 = Nutzungsdauer
				break;
		}
		DecimalFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(2);
		
		try {
			return format.parse(format.format(price)).doubleValue();
		} catch (ParseException e) {
			return price;
		}
		
		
	}
	public Machine(MachineType Type, int Capacity, double Value) { //, int Lifetime) {
		type = Type;
		capacity = Capacity;
		initialValue = Value;
		value = Value;
		//lifetime = Lifetime;
	}
	
	private MachineType type;
	private int capacity;
	
	private double initialValue;
	private double value;
	private int lifetime = 5; 	// in years
	private int age; 		    // in years
	
	public double deprecate() { // Abschreiben.		
		age++;
		double deprecation = initialValue / lifetime;
		value -= deprecation;
		return deprecation;
	}
	
	
	public boolean isCompletelyDeprecated() {
		return age == lifetime ? true : false;
	}
	
	public MachineType getType() {
		return type;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public double getInitialValue() {
		return initialValue;
	}
	
	public double getValue() {
		return value;
	}
	
	public int getLifetime() {
		return lifetime;
	}
	
	public int getAge() {
		return age;
	}
	
	public int getRemaininTime() {
		return lifetime - age;
	}
	
	public double getSellingValue() {
		return value / 2;
	}
	
	public double getLossDueSelling() {
		return value - getSellingValue(); 
	}
	
}
