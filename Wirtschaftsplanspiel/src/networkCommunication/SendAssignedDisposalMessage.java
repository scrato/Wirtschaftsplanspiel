package networkCommunication;

public class SendAssignedDisposalMessage extends NetMessage {
	
	public SendAssignedDisposalMessage(int Quantity) {
		super(MessageType.SEND_ASSIGNED_DEMAND, ByteConverter.toBytes(Quantity));
		quantity = Quantity;
	}
	
	public SendAssignedDisposalMessage(byte[] Content) {
		super(MessageType.SEND_ASSIGNED_DEMAND, Content);
		quantity = ByteConverter.toInt(Content);
	}
	
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}
}
