package Client.Application;

public class UnableToTakeCreditException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum TakeCreditReason{
		CannotAffordPayBack,
		CreditTooHigh,
		PeriodLongerThanPlaytime,
		PeriodTooLong
	}
	
	public UnableToTakeCreditException(TakeCreditReason reason){
		
	}

}
