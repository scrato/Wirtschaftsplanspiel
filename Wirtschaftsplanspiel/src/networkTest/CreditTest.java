package networkTest;

import Client.Application.CompanyController;
import Client.Application.UnableToTakeCreditException;
import Client.Application.UserCanNotPayException;
import Client.Entities.Company;

public class CreditTest {
	public static void main(String[] args) throws Exception{
	 Company comp = Company.getInstance();
	 comp.incMoney(500000);
		CompanyController.takeCredit(50000, 6);
		CompanyController.payCreditAmortisation();
		CompanyController.payCreditAmortisation();
		CompanyController.payCreditAmortisation();
		CompanyController.payCreditAmortisation();
		CompanyController.payCreditAmortisation();
		CompanyController.payCreditAmortisation();
		
		
		
		if(comp.getCredit() != null)
			throw new Exception();
		
	}
}
