package clientServerArchitecture;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ClientHandler implements Comparable<ClientHandler> {
	
	private Server parent;
	
	private Integer id;
	private String name;
	private Socket socket;
	
	private Thread listenerThread;
	private boolean stopListener;
	
	Semaphore lock_send = new Semaphore(1);
	
	public ClientHandler(Integer ID, String Name, Socket Socket, Server Parent) {
		id = ID;
		name = Name;
		socket = Socket;
		parent = Parent;
		
		listenerThread = new Thread() {
			public void run() {
				ReceiveMessage();
			}
		};		
		StartReceivingMessages();
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
		        			System.err.println("Unvollstaendige Nachricht erhalten von Client " + this.id + ".");
		        			stream.skip(stream.available());
		        			break;
		        		}

		        		NetMessage message = new NetMessage(messageType, messageContent);
		        		parent.receiveMessage(message, this);
		        	}		
		        }		        
			} catch (IOException e) {
				// Verbindung zum Client verloren. TODO darauf reagieren.
				System.err.println("Verbindung zu Client "+ this.id + " verloren.");
				parent.RemoveClient(this);
				this.close();
			}
		}
	}
	
	public void SendMessage(NetMessage message) {
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
			System.err.println("Nachricht an Client " + this.id + " konnte nicht versendet werden.");
		}
	}
	
	public void close() {
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
		System.out.println("Client " + id + " hat das Spiel verlassen.");
	}
	
	public String get_Name() {
		return name;
	}
	
	public Integer get_ID() {
		return id;
	}

	@Override
	public int compareTo(ClientHandler handler) {
		try {
			if (this.get_ID() > handler.get_ID()) { return 1; } else {
				if (this.get_ID() < handler.get_ID()) { return -1; } else {
					return 0;
				}
			}				
		} catch (RuntimeException e) {
			return -1;
		}
	}
	
}
