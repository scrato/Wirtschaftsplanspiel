package tests;

import Client.Application.CompanyController;
import Client.Entities.Company;
import Client.Entities.Ressource;
import Client.Entities.RessourceType;
import Client.Presentation.MainWindow;

public class LayoutTest {
	public static void main(String[] args){
		//CompanyController.initRessource(RessourceType.Rohfisch, Integer.MAX_VALUE, 1.50);
		//CompanyController.initRessource(RessourceType.Verpackungsmaterial, Integer.MAX_VALUE, 2.50);
		//Company.getInstance().incMoney(9000);
		Ressource sf = Company.getInstance().getRessource(RessourceType.Rohfisch);
		sf.incStoredUnits(4);

		Ressource vm = Company.getInstance().getRessource(RessourceType.Verpackungsmaterial);
		vm.incStoredUnits(7);
		
		Company.getInstance().incFinishedProducts(7000);
		
		 MainWindow m = new MainWindow();
		 m.setVisible(true);
	}

}
