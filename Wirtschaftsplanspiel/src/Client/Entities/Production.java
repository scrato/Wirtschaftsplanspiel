package Client.Entities;

import Client.Entities.Ressource.RessourceType;

public class Production {
    private double pricePerUnit;

	
	public void setPricePerUnit(double price){
		this.pricePerUnit = price;
	}

	/**
	 * Gibt den festgelegten PreisPerEinheit zur�ck, die maximal festgelegt werden kann
	 * @return
	 */
	public double getPricePerUnit(){
		return pricePerUnit;
	}
	
	/**
	 * Gibt die Menge an Fertigprodukten zur�ck, die maximal produziert werden kann
	 * @return Menge an Fertigprodukten, die maximal produziert werden kann 
	 */
	public int getMaxProducableUnits() {
		int units=Integer.MAX_VALUE;
		Company comp = Company.getInstance();
		
		//Wieviel k�nnen mit den Ressourcen produziert werden
		for(RessourceType type: RessourceType.values()){
			Ressource res = comp.getRessource(type);

			int unitsWhichCanBeProduced = Ressource.getNeed(type) / res.getStoredUnits();
			if (units > unitsWhichCanBeProduced)
					units = unitsWhichCanBeProduced;
		}
		
		//Wieviel k�nnen mit den Ressourcen produziert werden
		for(MachineType type: MachineType.values()){
			int unitsWhichCanBeProduced = comp.getMachineCapacity(type);
			if (units > unitsWhichCanBeProduced)
					units = unitsWhichCanBeProduced;
		}
		
		//TODO: Wieviel k�nnen mit dem Person produziert werden
		return units;
	}
}
