package NetworkCommunication;

public class SendAssignedDemandMessage extends NetMessage {
	
	public SendAssignedDemandMessage(int Quantity) {
		super(MessageType.SEND_ASSIGNED_DEMAND, ByteConverter.toBytes(Quantity));
		quantity = Quantity;
	}
	
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}
}
