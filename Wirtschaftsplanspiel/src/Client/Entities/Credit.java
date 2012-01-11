package Client.Entities;

import Client.Application.UnableToTakeCreditException;
import Client.Network.Client;

public class Credit {
	
	//TODO: Weiterbauen (Micha)
	private double creditTaken;
	private double creditPaidBack;
	private double interestPercentage;
	
	public Credit(double creditHeight, int contractPeriod) throws UnableToTakeCreditException{
		this.interestPercentage = (contractPeriod * 0.5) + 5;
		this.creditTaken = creditHeight;
		CanTakeCredit(creditHeight, contractPeriod);
	}

	private void CanTakeCredit(double creditHeight, int contractPeriod) throws UnableToTakeCreditException {
		//TODO: Sind Kreditvorraussetzungen so ok?
		Client cli = Client.getInstance();
		if (creditHeight > 100.000)
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.CreditTooHigh);
		if (contractPeriod > (cli.getMaxSessions() - cli.getActSessions()))
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.PeriodLongerThanPlaytime);
		if (contractPeriod > 10)
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.PeriodTooLong);
		
	}
}
