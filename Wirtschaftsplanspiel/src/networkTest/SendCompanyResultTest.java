package networkTest;

import NetworkCommunication.SendCompanyResultMessage;
import common.entities.CompanyResult;
import common.entities.CompanyResults;

public class SendCompanyResultTest {
	public static void main(String[] args){
		CompanyResults cr = new CompanyResults();
		cr.result.add(new CompanyResult(123, 1));
		cr.result.add(new CompanyResult(123, 2));
		
		
		SendCompanyResultMessage scr = new SendCompanyResultMessage(cr);
		
		SendCompanyResultMessage scr2 = new SendCompanyResultMessage(scr.get_Content());
		
		CompanyResults cr2 = scr2.getCompanyResults();
	}
}
