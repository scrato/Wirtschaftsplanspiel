package NetworkCommunication;

public class SendCompanyResultMessage extends NetMessage {
	public SendCompanyResultMessage (double Profit) {
		super(MessageType.SEND_COMPANYRESULTS, ByteConverter.toBytes(Profit));
		profit = Profit;
	}
	
	public SendCompanyResultMessage(byte[] Content) {
		super(MessageType.SEND_COMPANYRESULTS, Content);
		profit = ByteConverter.toDouble(Content);
	}
	
	private double profit;
	
	public double getProfit() {
		return profit;
	}
	
}
