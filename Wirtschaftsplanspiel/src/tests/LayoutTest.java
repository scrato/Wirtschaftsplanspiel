package tests;

import Client.Application.CompanyController;
import Client.Entities.Company;
import Client.Entities.Ressource;
import Client.Entities.RessourceType;
import Client.Presentation.MainWindow;

public class LayoutTest {
	public static void main(String[] args){
		CompanyController.initRessource(RessourceType.Stockfisch, 50, 1.50);
		CompanyController.initRessource(RessourceType.Verpackungsmaterial, 100, 2.50);
		
		Ressource sf = Company.getInstance().getRessource(RessourceType.Stockfisch);
		sf.incStoredUnits(400);

		Ressource vm = Company.getInstance().getRessource(RessourceType.Verpackungsmaterial);
		vm.incStoredUnits(70);
		
		 MainWindow m = new MainWindow();
		 m.setVisible(true);
	}

}
