/**
 * 
 */
package unittests;


import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;

import Client.Application.CompanyController;
import Client.Entities.Company;
import Client.Entities.Employee;
import Client.Entities.EmployeeType;
import Client.Entities.Machine;
import Client.Entities.MachineType;
import Client.Entities.PeriodInfo.GuV;
import Client.Entities.Ressource.RessourceType;

/**
 * @author MicSch
 *
 */
public class PeriodInfoTest extends TestCase  {
	private Company comp;
	private GuV g;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		comp = Company.getInstance();
		comp.incMoney(40000000);
		CompanyController.takeCredit(40000, 5);
		CompanyController.initRessource(RessourceType.Stockfisch, 100, 1.0);
		CompanyController.initRessource(RessourceType.Verpackungsmaterial, 10, 10);
		
		CompanyController.buyRessources(RessourceType.Stockfisch, 100);
		CompanyController.buyRessources(RessourceType.Verpackungsmaterial, 10);

		CompanyController.buyMachine(new Machine(MachineType.Filitiermaschine, 40, 4000));
		CompanyController.buyMachine(new Machine(MachineType.Verpackungsmaschine, 70, 9000));
		
		CompanyController.employSb(new Employee(EmployeeType.Produktion));
		CompanyController.employSb(new Employee(EmployeeType.Verwaltung));
		
		
		
		CompanyController.payCreditAmortisation();
		CompanyController.payEmployersSalery();
		CompanyController.payRent();
		CompanyController.produce();
	}
	
	public void testGuV(){
		g = comp.getPeriodInfo().getGuV();
		Assert.assertEquals(0.0, g.changeInStockRessources);
		Assert.assertEquals(200.0, g.changeInStockFinishedProducts);
	}

}
