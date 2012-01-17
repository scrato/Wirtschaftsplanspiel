package NetworkCommunication;

public class SendCompanyResultMessage extends NetMessage {
	private double profit;

	public SendCompanyResultMessage(byte[] Content) {
		super(MessageType.SEND_COMPANYRESULT, Content);
		profit = ByteConverter.toDouble(Content);
	}

	public SendCompanyResultMessage (double Profit) {
		super(MessageType.SEND_COMPANYRESULT, ByteConverter.toBytes(Profit));
		profit = Profit;
	}

	public double getProfit() {
		return profit;
	}

}
