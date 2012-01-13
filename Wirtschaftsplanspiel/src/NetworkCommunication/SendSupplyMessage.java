package NetworkCommunication;

import common.entities.Supply;

public class SendSupplyMessage extends NetMessage {

	public SendSupplyMessage(Supply Supply) {
		super(MessageType.SEND_SUPPLY, getSupplyBytes(Supply));
		supply = Supply;
	}
	
	public SendSupplyMessage(byte[] content) {
		super(MessageType.SEND_SUPPLY, content);
		supply = getSupply(content);
	}
	
	private Supply supply;
	
	public Supply getSupply() {
		return supply;
	}
		
	private static byte[] getSupplyBytes(Supply supply) {
		byte[] supBytes = new byte[12];
		byte[] quantityBytes = ByteConverter.toBytes(supply.quantity);
		byte[] priceBytes = ByteConverter.toBytes(supply.price);
		
		System.arraycopy(quantityBytes, 0, supBytes, 0, 4);
		System.arraycopy(priceBytes, 0, supBytes, 4, 8);
		
		return supBytes;
	}
	
	private static Supply getSupply(byte[] supBytes) {
		byte[] quantityBytes = new byte[4];
		byte[] priceBytes = new byte[8];
		
		System.arraycopy(supBytes, 0, quantityBytes, 0, 4);
		System.arraycopy(supBytes, 4, priceBytes, 0, 8);
		
		int quantity = ByteConverter.toInt(quantityBytes);
		double price = ByteConverter.toDouble(priceBytes);
		
		return new Supply(quantity, price);
	}

}
