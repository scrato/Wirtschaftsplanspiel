package Client.Model;

public class Company {
private double money;
private String name;

public Company(String name){
	this.name = name;
}

public String getName()
{ 
	return name;
}

/*
 *  Prüft, ob das Unternehmen noch liquide Mittel zur Verfügung hat
 */
public boolean isLiquid(){
	//TODO: Bessere Abfrage finden
	if(money < 0)
	{
		return false;
	}
	return true;
}

public void decMoney(double amount){
	money -= amount;
}

}
