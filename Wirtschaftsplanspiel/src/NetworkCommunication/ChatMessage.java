package NetworkCommunication;

import java.io.UnsupportedEncodingException;

public class ChatMessage extends NetMessage {

	public ChatMessage(String Message, String Sender) throws UnsupportedEncodingException {
		super(MessageType.CHATMASSAGE_TOSERVER, Message.getBytes("UTF-16LE"));
		this.message = Message;
		this.sender = Sender;
	}
	
	private String message;
	private String sender;
	
	public String get_Message() {
		return message;
	}
	
	public String get_SenderName() {
		return sender;
	}
}
