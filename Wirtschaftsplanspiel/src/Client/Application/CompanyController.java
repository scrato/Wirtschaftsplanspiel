package Client.Application;

public abstract class CompanyController {
	
	public static void buyItem(double amount) throws UserCanNotPayException{
		if(!AppContext.company.isLiquid())
			throw new UserCanNotPayException();
		AppContext.company.decMoney(amount);
	}
}
