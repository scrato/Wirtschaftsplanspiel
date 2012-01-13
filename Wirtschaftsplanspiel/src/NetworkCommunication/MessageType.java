package NetworkCommunication;

public abstract class MessageType {		
	
	public final static int CHATMASSAGE_TOSERVER = 100;	
	
	public final static int CHATMESSAGE_TOCLIENT = 200;
	
	public final static int PLAYER_JOINED        = 210;
	public final static int PLAYER_LEFT          = 211;
	
	public static final int GAME_STARTED		 = 220;
	
	public static final int SEND_SUPPLY 		 = 230;
	
}
