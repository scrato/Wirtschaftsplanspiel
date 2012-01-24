package Client.Entities;

import Client.Entities.RessourceType;

public class Production {
	
	int unitsToProduce;
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
			if(res.getStoredUnits() <= 0)
				return 0;
			int unitsWhichCanBeProduced = res.getStoredUnits() / Ressource.getNeed(type);
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
	public int getUnitsToProduce() {
		return unitsToProduce;
	}


	public void setUnitsToProduce(int unitsToProduce) {
		this.unitsToProduce = unitsToProduce;
	}
}
