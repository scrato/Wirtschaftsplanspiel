package Client.Entities;

import Client.Entities.Ressource.RessourceType;

public class Production {
    private double pricePerUnit;

	
	public void setPricePerUnit(double price){
		this.pricePerUnit = price;
	}

	/**
	 * Gibt den festgelegten PreisPerEinheit zurück, die maximal festgelegt werden kann
	 * @return
	 */
	public double getPricePerUnit(){
		return pricePerUnit;
	}
	
	/**
	 * Gibt die Menge an Fertigprodukten zurück, die maximal produziert werden kann
	 * @return Menge an Fertigprodukten, die maximal produziert werden kann 
	 */
	public int getMaxProducableUnits() {
		int units=Integer.MAX_VALUE;
		Company comp = Company.getInstance();
		
		//Wieviel können mit den Ressourcen produziert werden
		for(RessourceType type: RessourceType.values()){
			Ressource res = comp.getRessource(type);

			int unitsWhichCanBeProduced = Ressource.getNeed(type) / res.getStoredUnits();
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
