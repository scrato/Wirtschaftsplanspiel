package Client.Entities;

import java.util.Dictionary;

import Client.Application.NotEnoughMachinesException;
import Client.Application.NotEnoughPersonalException;
import Client.Application.NotEnoughRessourcesException;
import Client.Entities.Ressource.RessourceType;

public class Production {
    private double pricePerUnit;
	public void enoughRessources(int units) throws NotEnoughRessourcesException
	{
			Company comp = Company.getInstance();
		   Dictionary<RessourceType, Ressource> ressources = comp.getAllRessources();
		   NotEnoughRessourcesException resExc = new NotEnoughRessourcesException();
		   while(ressources.elements().hasMoreElements()){
			   Ressource res = ressources.elements().nextElement();
				//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Maschinen fehlen.
			   int missingunits = (Ressource.getNeed(res.getType())* units)  - res.getAvailableUnits();
			   if (missingunits > 0)
				   resExc.addNewRessource(res.getType(), missingunits);
		   }
		   if(resExc.isFilled())
			   throw resExc;
	}
   
	public void enoughMachines(int units) throws NotEnoughMachinesException
	{
		Company comp = Company.getInstance();
		NotEnoughMachinesException  macExc = new NotEnoughMachinesException();
	   	for(MachineType type: MachineType.values()){
	   		
	   		//MissingUnits sind die Einheiten, die nicht produziert werden können, weil Maschinen fehlen.
	   		int missingunits = units - comp.getMachineCapacity(type);
	   		if (missingunits > 0)
	   			macExc.AddMachine(type, missingunits);
	   	}
	   	if(macExc.isFilled())
	   		throw macExc;		
	}
 
	public void enoughPersonal(int units) throws NotEnoughPersonalException
	{
		//TODO: Prüfen, ob genug Personal für die Produktion vorhanden ist
		NotEnoughPersonalException perExc = new NotEnoughPersonalException();
		throw new UnsupportedOperationException("Noch nicht implementiert");
	}
	
	public void setPricePerUnit(double price){
		this.pricePerUnit = price;
	}

	public int getMaxProducableUnits() {
		int units=Integer.MAX_VALUE;
		Company comp = Company.getInstance();
		
		//Wieviel können mit den Ressourcen produziert werden
		for(RessourceType type: RessourceType.values()){
			Ressource res = comp.getRessource(type);

			int unitsWhichCanBeProduced = Ressource.getNeed(type) / res.getAvailableUnits();
			if (units > unitsWhichCanBeProduced)
					units = unitsWhichCanBeProduced;
		}
		
		//Wieviel können mit den Ressourcen produziert werden
		for(MachineType type: MachineType.values()){
			int unitsWhichCanBeProduced = comp.getMachineCapacity(type);
			if (units > unitsWhichCanBeProduced)
					units = unitsWhichCanBeProduced;
		}
		
		//TODO: Wieviel können mit dem Person produziert werden
		return units;
	}
}
