package networkCommunication;

public class NetMessage {
//TODO: also ne klasse die von netmessage erbt, braucht nen konstruktor wo du ne liste von erfolg entity reingibts und bytes daraus erzeugst und einen wo du aus nem byte array diese liste wieder erzeugst :D [:D] 
	protected int messageType;
	protected byte[] content;
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
