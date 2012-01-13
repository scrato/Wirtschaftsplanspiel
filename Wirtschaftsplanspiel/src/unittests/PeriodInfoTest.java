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
import Client.Entities.PeriodInfo.Balance;
import Client.Entities.PeriodInfo.GuV;
import Client.Entities.Ressource.RessourceType;

/**
 * @author MicSch
 *
 */
public class PeriodInfoTest extends TestCase  {
	private Company comp;
	private GuV g;
	private Balance b;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		comp = Company.getInstance();
		comp.incMoney(400000);
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
	
	public void testPeriodInfo(){
		g = comp.getPeriodInfo().getGuV();
		Assert.assertEquals(0.0, g.changeInStockRessources);
		Assert.assertEquals(200.0, g.changeInStockFinishedProducts);
		 System.out.println("BV Endprodukte: " + g.changeInStockFinishedProducts);
		 System.out.println("BV Ressourcen: " + g.changeInStockRessources);
		 System.out.println("Abschreibung: " + g.deprecation);
		 System.out.println("Kosten für Feuerung MA: " + g.employeeDismissalCosts);
		 System.out.println("Kosten für Einstellung MA: " + g.employeeHiringCosts);
		 System.out.println("Unternehmerlohn: " + g.employerSallery);
		 System.out.println("Zinsaufwand: " + g.interest);
		 System.out.println("Miete: " + g.rental);
		 System.out.println("Ressourcenaufwand: " + g.ressourceCost);
		 System.out.println("Löhne und Gehälter: " + g.wages);

		 b = comp.getPeriodInfo().getBalance();
		 System.out.println("Bank: " + b.bank);
		 System.out.println("Verbindlichkeiten: " + b.credit);
		 System.out.println("Eigenkapital: " + b.equity);
		 System.out.println("Maschinen: " + b.machineValue);
		 System.out.println("Ressourcen: " + b.ressourceValue);
		 System.out.println("Gesamteinnahmen: " + b.totallyearned);
		 System.out.println("Gesamtausgaben: " + b.totallypaid);
	}

	
	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.After
	public void tearDown() throws Exception {
		comp.setCredit(null);
	}

}
