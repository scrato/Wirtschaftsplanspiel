package Client.Entities;

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






}
