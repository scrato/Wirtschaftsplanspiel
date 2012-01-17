package networkTest;

import NetworkCommunication.BroadcastCompanyResultMessage;
import common.entities.CompanyResult;
import common.entities.CompanyResultList;

public class SendCompanyResultTest {
	public static void main(String[] args){
		CompanyResultList cr = new CompanyResultList();
		cr.profitList.put(1,new CompanyResult(123, 1));
		cr.profitList.put(2,new CompanyResult(123, 2));
		
		
		BroadcastCompanyResultMessage scr = new BroadcastCompanyResultMessage(cr);
		
		BroadcastCompanyResultMessage scr2 = new BroadcastCompanyResultMessage(scr.get_Content());
		
		CompanyResultList cr2 = scr2.getCompanyResults();
	}
}
