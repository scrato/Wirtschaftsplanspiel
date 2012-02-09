package Client.Entities;

	import Client.Application.UnableToTakeCreditException;

public class Credit {
	
	private double initialCreditHeight;	
	private double creditLeft;

	private int contractPeriod;
	private int leftPeriods;
	
	private double anuity;
	
	private double interestPercentage;
	public final static double INTERESTPERPERIODINPERCENT = 5d;
	public final static double BASEINTERESTINPERCENT = 5d;
	
	public Credit(double creditHeight, int contractPeriod) throws UnableToTakeCreditException{
		//Dynamische Anpassung des Zinsatzes -> (Laufzeit * 0,5) + Basissatz von 5%... hei�t Kredit �ber 10 Jahre hat 10% Zinsen 
		this.interestPercentage = ((contractPeriod * INTERESTPERPERIODINPERCENT) + BASEINTERESTINPERCENT) / 100;
		
		this.initialCreditHeight = creditHeight;
		this.leftPeriods = contractPeriod;
		
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

	private void CanTakeCredit(double creditHeight, int contractPeriod) throws UnableToTakeCreditException {
		//TODO: Sind Kreditvorraussetzungen so ok?
		//comment(lars): �ber nen maximalwert m�sste man sich nochmal gedanken machen.
		//comment(lars): ich w�rde sagen man kann auch 2 runden vor schluss nen kredit �ber 10 runden aufnehmen, sonst muss man ja in der 2. periode die h�lfte zur�ckzahlen wenn man ne maschine f�r 5 jahre finanzieren will.
		if (creditHeight > 900000)
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.CreditTooHigh);
		if (contractPeriod > (PeriodInfo.getMaxPeriods()- PeriodInfo.getNumberOfActPeriod()))
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.PeriodLongerThanPlaytime);
		if (contractPeriod > 10)
			throw new UnableToTakeCreditException(UnableToTakeCreditException.TakeCreditReason.PeriodTooLong);
		
	}

	/**
	 * Senkt den genommenen Kredit um die errechnete Tilgung und gibt zur�ck ob die Gesamtsumme getilgt wurde
	 * @return true => Kredit wurde vollst�ndig zur�ckbezahlt
	 */
	public boolean payInterestAndRepayment(){
		double interestPayment = getInterestPayment();
		//Logging
		PeriodInfo.getActualPeriod().setInterestPayment(interestPayment);
		
		double repayment = (anuity - interestPayment);
		leftPeriods--;
		//if((int) repayment >=  (int) creditLeft - 1){
		if (leftPeriods == 0) {
			creditLeft = 0;
			return true;
		}
		
		creditLeft -= repayment;
		return false;
	}
	
	/**
	 * Gibt den zu zahlenen Betrag (Anuit�t) zur�ck
	 * @return Anuit�t
	 */
	public double getInitialCreditHeight(){
		return initialCreditHeight;
	}
	
	public double getAnuity(){
		return anuity;
	}
	
	public double getInterestPercentage() {
		return interestPercentage;
	}
	
	public double getInterestPayment() {
		return (creditLeft * interestPercentage);
	}

	public double getCreditLeft() {
		return creditLeft;
	}
	
	public double getLeftPeriods() {
		return leftPeriods;
	}

}
