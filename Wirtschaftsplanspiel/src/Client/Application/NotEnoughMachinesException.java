package Client.Application;
import java.util.HashMap;
import java.util.Map;

import Client.Entities.MachineType;
public class NotEnoughMachinesException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<MachineType, Integer> missingMachines = new HashMap<MachineType, Integer>();
	
	public void AddMachine(MachineType type, int missingUnits){
		missingMachines.put(type, missingUnits);
	}
	
	public boolean isFilled(){
		return !(missingMachines.isEmpty());
	}
	
	public int GetMissingUnits(MachineType type){
		return missingMachines.get(type);
	}

}
