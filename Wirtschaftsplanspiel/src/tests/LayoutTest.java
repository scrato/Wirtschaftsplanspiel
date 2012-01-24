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
		sf.incStoredUnits(400);

		Ressource vm = Company.getInstance().getRessource(RessourceType.Verpackungsmaterial);
		vm.incStoredUnits(70);
		
		 MainWindow m = new MainWindow();
		 m.setVisible(true);
	}

}
