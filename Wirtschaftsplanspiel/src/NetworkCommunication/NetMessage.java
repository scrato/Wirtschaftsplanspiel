package NetworkCommunication;

public class NetMessage {

	private int messageType;
	private byte[] content;
	public static final int MESSAGE_END = 1337;
	public static final byte[] MESSAGE_END_BYTES = ByteConverter.toBytes(MESSAGE_END);
	
	public NetMessage(int MessageType, byte[] Content) {
		messageType = MessageType;
		content = Content;
	}
	
	public byte[] get_Content() {
		return content;
	}
	
	public int get_MessageType() {
		return messageType;
	}
}
