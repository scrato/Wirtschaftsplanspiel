package Client.Entities;

import Client.Application.CompanyController;
import Client.Application.UnableToTakeCreditException;
import Client.Application.UserCanNotPayException;
import Client.Network.Client;

public class Credit {
	
	//TODO: Weiterbauen (Micha)
	private double creditLeft;
	private double anuity;
	
	private int contractPeriod;
	
	private double interestPercentage;
	private double baseInterestPercentage = 5;
	
	public Credit(double creditHeight, int contractPeriod) throws UnableToTakeCreditException{
		//Dynamische Anpassung des Zinsatzes -> (Laufzeit * 0,5) + Basissatz von 5%... hei�t Kredit �ber 10 Jahre hat 10% Zinsen 
		this.interestPercentage = (contractPeriod * 0.5) + baseInterestPercentage;
		this.creditLeft = creditHeight;
		this.contractPeriod = contractPeriod;
		setAnuity(creditHeight);
		CanTakeCredit(creditHeight, contractPeriod);
	}

	/**
	 * Setzt die Annuit�t (periodische Abzugsrate) gem�� folgender Logik:
	 * Jahreswert: K * ((q^n)*(q-1))/((q^n)-1)
	 * 
	 * K -> Barwert des Darlehens
	 * q -> Zinsatz + 1 (100% + Zinssatz) 
	 * n -> Laufzeit des Darlehens pro Periode
	 * @param creditHeight -> K
	 * @param contractPeriod -> n
	 */
	private void setAnuity(double creditHeight) {
		
		this.anuity = creditHeight * (Math.pow(1+interestPercentage,contractPeriod)*interestPercentage)/(Math.pow(1+interestPercentage,contractPeriod)-1);
	}

	public Credit(double creditHeight, int contractPeriod, double baseInterestPercentage) throws UnableToTakeCreditException{
		this.interestPercentage = (contractPeriod * baseInterestPercentage) + 5;
		this.creditLeft = creditHeight;
		CanTakeCredit(creditHeight, contractPeriod);
		this.baseInterestPercentage = baseInterestPercentage;
	}
	
	private void CanTakeCredit(double creditHeight, int contractPeriod) throws UnableToTakeCreditException {
		//TODO: Sind Kreditvorraussetzungen so ok?
		Company comp = Company.getInstance();
		PeriodInfo perInf = comp.getPeriodInfo();
		if (creditHeight > 900000)
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.CreditTooHigh);
		if (contractPeriod > (perInf.getMaxPeriods()- perInf.getNumberOfActPeriod()))
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.PeriodLongerThanPlaytime);
		if (contractPeriod > 10)
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.PeriodTooLong);
		
	}

	/**
	 * Senkt den genommenen Kredit um die errechnete Tilgung und gibt zur�ck ob die Gesamtsumme getilgt wurde
	 * @return true => Kredit wurde vollst�ndig zur�ckbezahlt
	 */
	public boolean payAmortisation(){
		double amortisation = (anuity - (creditLeft * interestPercentage));
		if(amortisation >= creditLeft){
			creditLeft = 0;
			return true;
		}
		
		creditLeft -= amortisation;
		return false;
	}
	
	/**
	 * Gibt den zu zahlenen Betrag (Anuit�t) zur�ck
	 * @return Anuit�t
	 */
	public double getAnuity(){
		return anuity;
	}
}
