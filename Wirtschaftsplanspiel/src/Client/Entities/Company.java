package Client.Entities;

public class Company {
	private static Company company;
private double money;

public static Company get_instance(){
	if (company != null)
		return company;
	company = new Company();
	return company;
	
}

/*
 *  Pr�ft, ob das Unternehmen noch liquide Mittel zur Verf�gung hat
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
