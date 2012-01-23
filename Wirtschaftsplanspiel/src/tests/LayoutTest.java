package tests;

import Client.Application.CompanyController;
import Client.Entities.Company;
import Client.Entities.Ressource;
import Client.Entities.RessourceType;
import Client.Presentation.MainWindow;

public class LayoutTest {
	public static void main(String[] args){
		CompanyController.initRessource(RessourceType.Rohfisch, 7400, 1.50);
		CompanyController.initRessource(RessourceType.Verpackungsmaterial, 1900, 25.00);
		Company.getInstance().incMoney(900000);
		Company.getInstance().getProduction().setUnitsToProduce(500);
		Ressource sf = Company.getInstance().getRessource(RessourceType.Rohfisch);
		sf.incStoredUnits(400);

		Ressource vm = Company.getInstance().getRessource(RessourceType.Verpackungsmaterial);
		vm.incStoredUnits(70);
		
		 MainWindow m = new MainWindow();
		 m.setVisible(true);
	}

}
