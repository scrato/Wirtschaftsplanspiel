package networkTest;

import NetworkCommunication.RecieveCompanyResultMessage;
import common.entities.CompanyResult;
import common.entities.CompanyResultList;

public class SendCompanyResultTest {
	public static void main(String[] args){
		CompanyResultList cr = new CompanyResultList();
		cr.result.add(new CompanyResult(123, 1));
		cr.result.add(new CompanyResult(123, 2));
		
		
		RecieveCompanyResultMessage scr = new RecieveCompanyResultMessage(cr);
		
		RecieveCompanyResultMessage scr2 = new RecieveCompanyResultMessage(scr.get_Content());
		
		CompanyResultList cr2 = scr2.getCompanyResults();
	}
}
