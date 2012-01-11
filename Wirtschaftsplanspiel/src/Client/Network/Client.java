package Client.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import Client.Application.ClientController;
import Client.Entities.Player;
import NetworkCommunication.ByteConverter;
import NetworkCommunication.NetMessage;
import NetworkCommunication.StringOperation;


public class Client {
	
	private static Client client;
	
	public static Client getInstance() {
		 return client;
	}
	
	
	private String name;
	private Integer id;
	
	private Socket socket;
	private Thread listenerThread;
	private boolean stopListener;
	
	private Semaphore lock_send = new Semaphore(1);

	
	public Client(String Name, InetAddress Address, int Port) throws RuntimeException {
		try {
			if (Name.length() > 10) {
				throw new RuntimeException("Name ist zu lang.");
			}
			
			socket = new Socket(Address, Port);	
			listenerThread = new Thread() {
				public void run() {
					ReceiveMessage();
				}
			};			
			name = Name;
//			char[] nameChars = name.toCharArray();
//			char[] sendChars = new char[16];
//			System.arraycopy(nameChars, 0, sendChars, 0, nameChars.length);
//			
//			DataOutputStream outputStream = new DataOutputStream( socket.getOutputStream());
//			outputStream.writeChars(name);
			
			//byte[] nameBytes = Name.getBytes();
			//byte[] nameBytesLength = ByteConverter.toBytes(nameBytes.length);
			
			//byte[] nameMessage = new byte[nameBytes.length + 4];
			//System.arraycopy(nameBytesLength, 0, nameMessage, 0, 4);
			//System.arraycopy(nameBytes, 0, nameMessage, 4, nameBytes.length);
			
			String sendName = StringOperation.padRight(name, 10);
			byte[] sendNameBytes = sendName.getBytes("UTF-16");
			
			DataOutputStream outputStream = new DataOutputStream( socket.getOutputStream());
			outputStream.write(sendNameBytes);
			
			DataInputStream inputStream = new DataInputStream( socket.getInputStream());
			id = inputStream.readInt();
			
			int playersCount = inputStream.readInt();
			
			int playerID;
			byte[] playerNameBytes = new byte[20];
			String playerName;
			
			for (int i = 0; i < playersCount; i++) {
				playerID = inputStream.readInt();
				inputStream.read(playerNameBytes, 0, 20);
				playerName = new String(playerNameBytes, "UTF-16");
				new Player(playerID, playerName);
			}
			
			ClientController.PlayerListReceived();
			
			StartReceivingMessages();
			
			client = this; // static member for GetInstance
		} catch (IOException e) {
			throw new RuntimeException("Verbindung konnte nicht hergestellt werden.");
		}		
	}
	
	
	public void StartReceivingMessages() {
		if (!listenerThread.isAlive()) {	
			stopListener = false;
			listenerThread.start();
		}
	}
	
	public void StopReceivingMessages() {
		stopListener = true;
	}
	
	private void ReceiveMessage() {
		while (!stopListener) {
			byte[] messageContent;
	        int messageType;        
	        int messageLength;
	        int receivedBytes;
	        int checkint;
		        
		    try {
				DataInputStream stream = new DataInputStream( socket.getInputStream());		
				
		        while (!stopListener) {
		        	messageType = stream.readInt();			        	
		        	messageLength = stream.readInt();
		        	if (messageLength > 0) {
		        		messageContent = new byte[messageLength];
		        		receivedBytes = stream.read(messageContent, 0, messageLength);
		        		checkint = stream.readInt();
		        		if (receivedBytes != messageLength || checkint != NetMessage.MESSAGE_END) {
		        			System.err.println("Unvollstaendige Nachricht vom Server erhalten.");
		        			stream.skip(stream.available());
		        			break;
		        		}
		        		NetMessage message = new NetMessage(messageType, messageContent);
//		        		// for test purpose:
//		        		switch (messageType) {
//			        		case MessageType.CHATMESSAGE_TOCLIENT: {
//			        			String Message = new String(message.get_Content());
//			        			System.out.println("Nachricht erhalten: " + Message);			        			
//			        		}			        		
//		        		}
//		        		// end test
		        		 
		        		TriggerBusinessLogicThread triggerBusLogic = new TriggerBusinessLogicThread(message);		        				
		        		triggerBusLogic.start();
		        	}      
		        }		        
			} catch (IOException e) {
				System.err.println("Inputstream zum Server konnte nicht aufgebaut werden.");
				// Verbindung zum Server verloren. TODO Darauf reagieren.
				this.close();
			}
		}
	}

	public void SendMessage(NetMessage message) throws RuntimeException {
		try {
			byte[] typeBytes = ByteConverter.toBytes(message.get_MessageType());
			byte[] lengthBytes = ByteConverter.toBytes(message.get_Content().length);
			byte[] newMessage = new byte[12 + message.get_Content().length];
			System.arraycopy(typeBytes, 0, newMessage, 0, 4);
			System.arraycopy(lengthBytes, 0, newMessage, 4, 4);
			System.arraycopy(message.get_Content(), 0, newMessage, 8, message.get_Content().length);
			System.arraycopy(NetMessage.MESSAGE_END_BYTES, 0, newMessage, 8 + message.get_Content().length, 4);
			
			lock_send.acquireUninterruptibly();
			DataOutputStream stream = new DataOutputStream( socket.getOutputStream());
			stream.write(newMessage);
			lock_send.release();
			
		} catch (IOException e) {
			//System.err.println("Nachricht an Server konnte nicht versendet werden.");
			throw new RuntimeException("Nachricht an Server konnte nicht versendet werden.");
		}
	}
	
	public void close()
	{
		stopListener = true;
		try {
			if (!socket.isInputShutdown()) {
				socket.getInputStream().close();
			}
			if (!socket.isOutputShutdown()) {
				socket.getOutputStream().close();
			}
			if (socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			// should never reach this point!
		} 
		System.out.println("Verbindung zum Server getrennt.");
	}
	
	public String get_Name() {
		return name;
	}
	
	public Integer get_ID() {
		return id;
	}

}
