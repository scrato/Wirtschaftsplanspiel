package Client.Application;

import Client.Model.Company;

public class CompanyController {
	private Company company;
	
	public CompanyController(String name){
		company = new Company(name);
		AppContext.company = company;
	}
	public void buyItem(double amount) throws UserCanNotPayException{
		if(!AppContext.company.isLiquid())
			throw new UserCanNotPayException();
		AppContext.company.decMoney(amount);
	}
}
