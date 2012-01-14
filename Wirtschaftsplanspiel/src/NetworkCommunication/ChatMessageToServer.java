package NetworkCommunication;

import java.io.UnsupportedEncodingException;

public class ChatMessageToServer extends NetMessage {

	public ChatMessageToServer(String Message) throws UnsupportedEncodingException {
		super(MessageType.CHATMASSAGE_TOSERVER, Message.getBytes("UTF-16LE"));
		message = Message;
	}
	
	public ChatMessageToServer(byte[] content) {
		super(MessageType.CHATMASSAGE_TOSERVER, content);
		try {
			message = new String(content, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}
	}
	
	private String message;
	
	public String get_Message() {
		return message;
	}
	
}
