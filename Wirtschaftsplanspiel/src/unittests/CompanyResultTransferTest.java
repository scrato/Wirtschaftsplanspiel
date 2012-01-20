package unittests;

import java.net.InetAddress;

import org.junit.Before;

import common.entities.CompanyResult;

import Client.Network.Client;
import NetworkCommunication.SendCompanyResultMessage;
import Server.Entities.Disposal;
import Server.Entities.PeriodBuffer;
import Server.Network.Server;

import junit.framework.TestCase;


public class CompanyResultTransferTest extends TestCase {
	private final int PORT = 5151;
	Server s;
	Client c1;
	Client c2;
	/**
	 * @throws java.lang.Exception
	 */
//	@Before
//	public void setUp() throws Exception {
//		s = new Server(PORT, 10);
//		InetAddress local = InetAddress.getByName("127.0.0.1");
//		
//		c1 = new Client("Scrato",local,PORT);
//		c2 = new Client("Vilya",local,PORT);
//		
//		PeriodBuffer.Disposals.put(0, new Disposal(0, 10, 1.5));
//		PeriodBuffer.Disposals.put(1, new Disposal(1, 15, 1.3));
//	}
//	
//	public void testSendCompanyResult(){
//		c1.SendMessage(new SendCompanyResultMessage(1337.42));
//		c2.SendMessage(new SendCompanyResultMessage(42.1337));
//		System.out.println("Test zuende ohne fehler");
//		while(true){
//			
//		}
//	}
//	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@org.junit.After
//	public void tearDown() throws Exception {
//		s.close();
//		c1.close();
//		c2.close();
//	}

}
