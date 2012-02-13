package networkCommunication;

import java.io.UnsupportedEncodingException;

public class ChatMessageToClient extends NetMessage {

	public ChatMessageToClient(String Message, String Sender) throws UnsupportedEncodingException {
		super(MessageType.CHATMESSAGE_TOCLIENT, getBytes(Message, Sender));
		this.message = Message;
		this.sender = Sender;
	}
	
	public ChatMessageToClient(byte[] content) {
		super(MessageType.CHATMESSAGE_TOCLIENT, content);
		
		byte[] contentLengthBytes = new byte[4];
		System.arraycopy(content, 0, contentLengthBytes, 0, 4); 				
		int contentLength = ByteConverter.toInt(contentLengthBytes);
		
		byte[] messageBytes = new byte[contentLength];
		System.arraycopy(content, 4, messageBytes, 0, contentLength);
		String recMessage = null;
		try {
			recMessage = new String(messageBytes, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
		message = recMessage;
		
		byte[] nameLengthBytes = new byte[4];
		System.arraycopy(content, 4 + contentLength, nameLengthBytes, 0, 4);
		int nameLength = ByteConverter.toInt(nameLengthBytes);
		
		byte[] nameBytes = new byte[nameLength];
		System.arraycopy(content, 8 + contentLength, nameBytes, 0, nameLength);
		String name = null;
		try {
			name = new String(nameBytes, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
		sender = name;
	}
	
	private String message;
	private String sender;
	
	private static byte[] getBytes(String Message, String Sender) {
		byte[] messageBytes = null;
		byte[] nameBytes = null;
		try {
			messageBytes = Message.getBytes("UTF-16LE");
			nameBytes = Sender.getBytes("UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
		
		byte[] contentLength = ByteConverter.toBytes(messageBytes.length);
		byte[] nameLength = ByteConverter.toBytes(nameBytes.length);
		
		byte[] retBytes = new byte[messageBytes.length + 8 + nameBytes.length];
		
		System.arraycopy(contentLength, 0, retBytes, 0, 4);
		System.arraycopy(messageBytes, 0, retBytes, 4, messageBytes.length);
		System.arraycopy(nameLength, 0, retBytes, 4 +messageBytes.length, 4);
		System.arraycopy(nameBytes, 0, retBytes, 8 + messageBytes.length, nameBytes.length);	
		
		return retBytes;
	}
	
	public String get_Message() {
		return message;
	}
	
	public String get_SenderName() {
		return sender;
	}
}
