/**
 * 
 */
package unittests;


import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;

import Client.Application.CompanyController;
import Client.Application.MachineAlreadyBoughtException;
import Client.Application.NotEnoughRessourcesException;
import Client.Application.UnableToTakeCreditException;
import Client.Application.UserCanNotPayException;
import Client.Entities.Company;
import Client.Entities.Employee;
import Client.Entities.EmployeeType;
import Client.Entities.Machine;
import Client.Entities.MachineType;
import Client.Entities.Balance;
import Client.Entities.ProfitAndLoss;
import Client.Entities.PeriodInfo;
import Client.Entities.RessourceType;

/**
 * @author MicSch
 *
 */
public class PeriodInfoTest extends TestCase  {
	private Company comp;
	private ProfitAndLoss g1;
	private Balance b;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		comp = Company.getInstance();
		comp.incMoney(400000);
		CompanyController.takeCredit(40000, 5);
		CompanyController.initRessource(RessourceType.Stockfisch, 100, 40);
		CompanyController.initRessource(RessourceType.Verpackungsmaterial, 10, 375);
		
		CompanyController.buyRessources(RessourceType.Stockfisch, 100);
		CompanyController.buyRessources(RessourceType.Verpackungsmaterial, 8);

		CompanyController.buyMachine(new Machine(MachineType.Filitiermaschine, 40, 4000));
		CompanyController.buyMachine(new Machine(MachineType.Verpackungsmaschine, 70, 9000));
		
		CompanyController.employSb(new Employee(EmployeeType.Produktion));
		CompanyController.employSb(new Employee(EmployeeType.Verwaltung));
		
		
		
		CompanyController.payInterestAndRepayment();
		CompanyController.payEmployersSalery();
		
		CompanyController.payRent();
		CompanyController.produce();
		CompanyController.depcrecateMachines();
		PeriodInfo.nextPeriod();
		g1 = PeriodInfo.getPeriod(PeriodInfo.getNumberOfActPeriod() - 1).getGuV();
		
	}
	
	public void testPeriodInfo(){
		
		//Assert.assertEquals(0.0, g1.changeInStockRessources);
		//Assert.assertEquals(200.0, g1.changeInStockFinishedProducts);
		 System.out.println("BV Endprodukte: " + g1.changeInStockFinishedProducts);
		 System.out.println("BV Ressourcen: " + g1.changeInStockRessources);
		 System.out.println("Abschreibung: " + g1.deprecation);
		 System.out.println("Kosten für Feuerung MA: " + g1.employeeDismissalCosts);
		 System.out.println("Kosten für Einstellung MA: " + g1.employeeHiringCosts);
		 System.out.println("Unternehmerlohn: " + g1.employerSallery);
		 System.out.println("Zinsaufwand: " + g1.interest);
		 System.out.println("Miete: " + g1.rental);
		 System.out.println("Ressourcenaufwand: " + g1.ressourceCost);
		 System.out.println("Löhne und Gehälter: " + g1.wages);

		 b = PeriodInfo.getPeriod(PeriodInfo.getNumberOfActPeriod() - 1).getBalance();
		 
		 System.out.println("Bank: " + b.bank);
		 System.out.println("Verbindlichkeiten: " + b.credit);
		 System.out.println("Eigenkapital: " + b.equity);
		 System.out.println("Maschinen: " + b.machineValue);
		 System.out.println("Ressourcen: " + b.ressourceValue);
		 System.out.println("Gesamteinnahmen: " + b.totallyearned);
		 System.out.println("Gesamtausgaben: " + b.totallypaid);
	}

	
	public void testNextPeriod() throws UserCanNotPayException, NotEnoughRessourcesException, MachineAlreadyBoughtException{
		boolean tkE = false;
		try{
		CompanyController.takeCredit(40000, 5);}
		catch(UnableToTakeCreditException tCex){
			tkE =true;
		}
		Assert.assertEquals(true, tkE);
		CompanyController.initRessource(RessourceType.Stockfisch, 100, 40);
		CompanyController.initRessource(RessourceType.Verpackungsmaterial, 10, 375);
		
		CompanyController.buyRessources(RessourceType.Stockfisch, 100);
		CompanyController.buyRessources(RessourceType.Verpackungsmaterial, 8);

		CompanyController.buyMachine(new Machine(MachineType.Filitiermaschine, 40, 4000));
		CompanyController.buyMachine(new Machine(MachineType.Verpackungsmaschine, 70, 9000));
		
		CompanyController.employSb(new Employee(EmployeeType.Produktion));
		CompanyController.employSb(new Employee(EmployeeType.Verwaltung));
		
		
		
		CompanyController.payInterestAndRepayment();
		CompanyController.payEmployersSalery();
		
		CompanyController.payRent();
		CompanyController.produce();
		CompanyController.depcrecateMachines();
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.After
	public void tearDown() throws Exception {
		//comp.setCredit(null);
	}

}
