package Client.Application;

import java.util.Iterator;

import common.entities.CompanyResult;
import common.entities.CompanyResultList;
import common.entities.Supply;

import Client.Entities.Company;
import Client.Entities.Period;
import Client.Entities.ProductionAndDistribution;
import Client.Entities.ProfitAndLoss;
import Client.Entities.PeriodInfo;
import Client.Entities.Player;
import Client.Entities.Ressource;
import Client.Entities.RessourceType;
import Client.Network.Client;
import NetworkCommunication.BroadcastCompanyResultMessage;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;
import NetworkCommunication.SendAssignedDisposalMessage;
import NetworkCommunication.SendCompanyResultMessage;
import NetworkCommunication.SendSupplyMessage;
import Client.Presentation.MainWindow;
import Client.Presentation.ResultPanel;

public abstract class PeriodController {

	
	public static void ClosePeriod() throws CannotProduceException {
		CompanyController.produce();
		SendSupply();
	}
	
	/**
	 * Sendet den Supply an den Server, Supplydaten werden aus der Klasse prodAndDistr gezogen
	 */
	private static void SendSupply() {
		Period period = PeriodInfo.getActualPeriod();
		ProductionAndDistribution pad = Company.getInstance().getProdAndDistr();
		period.setProductPrice(pad.getSellingPrice());
		Supply s = new Supply(pad.getUnitsToSell(), pad.getSellingPrice());
		
		Client client = Client.getInstance();
		client.SendMessage(new SendSupplyMessage(s));
	}

	public static void RecieveAssignedDisposal(SendAssignedDisposalMessage sendAssignedDemandMessage) {

		int quantity = sendAssignedDemandMessage.getQuantity();
		
		Period actPeriod = PeriodInfo.getActualPeriod();
		double price = actPeriod.getProductPrice();
		
		double revenue = quantity * price;		
		CompanyController.receiveSalesRevenue(revenue, quantity);
		
		try {
			//double wages = CompanyController.payEmployeesSallery();
			//double deprecation = CompanyController.depcrecateMachines();
			CompanyController.paySallery();
			CompanyController.depcrecateMachines();
			CompanyController.payInterestAndRepayment();
			
			
			//CompanyController.payEmployersSalery(); //integrated in paySallery.
			CompanyController.payRent();
			
			//Company comp = Company.getInstance();
			//double produceCostPerProdukt = wages / Math.min(comp.getEmployeeCapacity(EmployeeType.Produktion), comp.getEmployeeCapacity(EmployeeType.Verwaltung)) 
			//							 + deprecation /  Math.min(comp.getMachineCapacity(MachineType.Filitiermaschine), comp.getMachineCapacity(MachineType.Verpackungsmaschine)) 
			//							 + Ressource.getNeed(RessourceType.Rohfisch) * Ressource.getFixedCosts(RessourceType.Rohfisch) 
			//							 + Ressource.getNeed(RessourceType.Verpackungsmaterial) * Ressource.getFixedCosts(RessourceType.Verpackungsmaterial);		
			//PeriodInfo.getActualPeriod().setFinishedProductsValue(produceCostForLeftFinishedProducts);
			//comp.setWarehouseCostPerProduct(produceCostPerProdukt);
			CompanyController.payWarehouseCosts();
			
			ProfitAndLoss guv = actPeriod.makeGuV();	
			
			SendCompanyResultMessage message = new SendCompanyResultMessage(guv.profit);
			Client.getInstance().SendMessage(message);
			
		} catch (UserCanNotPayException e) {
			//TODO: Bescheid sagen
		}
	}

	
	public static void RecieveCompanyResult(
		BroadcastCompanyResultMessage Message) {
		CompanyResultList crl = Message.getCompanyResults();
		
		try {
			for(Iterator<CompanyResult> it = crl.profitList.values().iterator(); it.hasNext();){
				CompanyResult result = it.next();
				try {
					Player.getPlayer(result.clientid).addCompanyResult(result);
				} catch (Exception e2) {
					// Player not found. Do nothing.
				}
			}
		} catch (Exception e1) {
			// list is buggy.
			throw new RuntimeException("Ergebnisliste nicht valide.");
		}
		
		if(PeriodInfo.getNumberOfActPeriod() + 1 == PeriodInfo.getMaxPeriods()){
			MainWindow.getInstance().changeScreen(new ResultPanel(ResultPanel.FinishReason.EndOfRoundsReached));
			return;
		}
				
		PeriodInfo.nextPeriod();
		MainWindow.getInstance().reactiviateAfterPeriod();
	}
	
}
