package Client.Entities;

import Client.Entities.RessourceType;

public class ProductionAndDistribution {
	
	int unitsToProduce;
	double sellingPrice;
	int unitsToSell;
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
			if(res.getStoredUnits() <= 0)
				return 0;
			int unitsWhichCanBeProduced = res.getStoredUnits() / Ressource.getNeed(type);
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
		for(EmployeeType type : EmployeeType.values()){
			int unitsWhichCanBeProduced = comp.getEmployeeCapacity(type);
			if (units > unitsWhichCanBeProduced)
				units = unitsWhichCanBeProduced;
		}
		
		return units;
	}
	public int getUnitsToProduce() {
		return unitsToProduce;
	}


	public void setUnitsToProduce(int unitsToProduce) {
		this.unitsToProduce = unitsToProduce;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public int getUnitsToSell() {
		return unitsToSell;
	}
	public void setUnitsToSell(int unitsToSell) {
		this.unitsToSell = unitsToSell;
	}
}
