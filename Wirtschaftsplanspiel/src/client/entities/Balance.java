package client.entities;

public class Balance {

	public double bank;
	public double credit;
	public double equity;
	public double machineValue;
	public double ressourceValue;
	public double finishedProductsValue;
	public double totallyearned;
	public double totallypaid;

	public void calculateEquity() {
		equity = machineValue + ressourceValue + bank + finishedProductsValue - credit;

	}
	
	public double getTAccountSum() {
		return machineValue + ressourceValue + bank + finishedProductsValue;
	}

}
