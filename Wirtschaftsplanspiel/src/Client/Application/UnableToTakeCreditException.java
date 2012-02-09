package Client.Application;

public class UnableToTakeCreditException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum TakeCreditReason{
		CreditTooHigh,
		PeriodTooLong,
		CreditAlreadyExists
	}
	
	public TakeCreditReason reason;
	public UnableToTakeCreditException(TakeCreditReason reason){
		this.reason = reason;
	}

}
