package Client.Application;

public class UnableToTakeCreditException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum TakeExceptionReason{
		CannotAffordPayBack,
		CreditTooHigh,
		PeriodLongerThanPlaytime,
		PeriodTooLong
	}
	
	public UnableToTakeCreditException(TakeExceptionReason reason){
		
	}

}
