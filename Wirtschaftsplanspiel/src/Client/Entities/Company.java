package Client.Entities;

import java.util.LinkedList;
import java.util.List;

import Client.Entities.Ressource.RessourceType;


public class Company {
	
	private static Company company;
	
	public static Company getInstance(){
		if (company != null)
			return company;
		company = new Company();
		return company;
	}
	
	
	//Hier werden Variablen verwaltet
	private double money;
	
	//Hier liegen die Maschinen.
	private List<Machine> machines = new LinkedList<Machine>();
	
	/*
	 *  Prüft, ob das Unternehmen noch liquide Mittel zur Verfügung hat
	 */	
	public double getMoney(){
		return this.money;
	}
	
	public void incMoney(double amount){
		money += amount;
	}
	public void decMoney(double amount){
		money -= amount;
	}
	
	public boolean isLiquid(double amount) {
		if((this.money - amount) <= 0)
			return false;
		return true;
			
	}
	
	public List<Machine> getMachines() {
		return machines;
	}
	
	public void addMachine(Machine machine) {
		machines.add(machine);
	}
	
	public void removeMachine(Machine machine) {
		machines.remove(machine);
	}

}
