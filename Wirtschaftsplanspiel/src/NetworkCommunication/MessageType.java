package NetworkCommunication;

public abstract class MessageType {		
	
	public final static int CHATMASSAGE_TOSERVER = 100;	
	public final static int SEND_ASSIGNED_DEMAND = 130;
	
	public final static int CHATMESSAGE_TOCLIENT = 200;
	
	public final static int PLAYER_JOINED        = 210;
	public final static int PLAYER_LEFT          = 211;
	
	public final static  int GAME_STARTED		 = 220;
	
	public final static int SEND_SUPPLY 		 = 230;
	
	public final static int SEND_COMPANYRESULTS		 = 240;	
	public final static int RECIEVE_COMPANYRESULT	 = 250;
	
}
