package Client.Application;

import Client.Model.Company;
import Client.Model.Ressource;
import Client.Network.Client;

public class AppContext {

	public AppContext(){
		InitializeController();
	}
	
	public static Client client;
	public static Company company;
	public static Ressource[] ressources;
	
	//Controller
	public static RessourceController ressourceController;
	public static ChatController chatController;
	public static ClientController clientController;
	public static CompanyController companyController;
	
	/**
	 * Initialisiert den Controller mit den vorgegebenen Startwerten
	 */
	protected void InitializeController(){
		//RessourceController initialisieren
		String[] resNames = new String[] {"Rohfisch", "Salz"};
		ressourceController = new RessourceController(resNames);
		ressourceController.initRessource(0, 200, 12.50);
		ressourceController.initRessource(1, 120, 4.00);
		
		companyController = new CompanyController(client.get_Name());
	}
	
}
