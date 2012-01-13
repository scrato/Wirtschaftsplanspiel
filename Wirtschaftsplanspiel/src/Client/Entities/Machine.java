package Client.Entities;

public class Machine {

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
	
}
