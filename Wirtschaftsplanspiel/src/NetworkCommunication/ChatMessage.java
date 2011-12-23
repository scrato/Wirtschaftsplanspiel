package NetworkCommunication;

public class ChatMessage extends NetMessage {

	public ChatMessage(String Message) {
		super(MessageType.CHATMASSAGE_TOSERVER, Message.getBytes());
		this.message = Message;
	}
	
	private String message;
	
	public String get_Message() {
		return message;
	}
}
