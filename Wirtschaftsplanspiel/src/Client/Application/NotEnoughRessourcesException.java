package Client.Application;

public class NotEnoughRessourcesException extends ApplicationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int missing;
	int unitsleft;
	public NotEnoughRessourcesException(int missing, int unitsleft){
		this.missing = missing;
		this.unitsleft = unitsleft;
	}
	public int getMissingUnits() {
		return missing;
	}
	public int getUnitsLeft() {
		return unitsleft;
	}

}
