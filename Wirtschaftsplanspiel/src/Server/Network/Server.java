package Server.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

import NetworkCommunication.ByteConverter;
import NetworkCommunication.MessageType;
import NetworkCommunication.NetMessage;
import NetworkCommunication.StringOperation;


public class Server {

	ServerSocket listener;
	boolean stopListener;
	Thread listenerThread;
	
	Semaphore lock_clients = new Semaphore(1);
	
	Map<Integer, ClientHandler> clients;
	//List<ClientHandler> clients;
	
	private boolean isClosed;
	
	public Server(int port) {
		
		try {
			listener = new ServerSocket(port);
			listenerThread = new Thread() {
			    public void run() {
			        StartListener();
			    }
			};
			clients = new TreeMap<Integer, ClientHandler>();
			StartAcceptClients();
		} catch (IOException e) {
			System.err.println("Server konnte nicht initialisiert werden.");
		}
		
	}
	
	private void StartListener()
	{
		Socket newSocket;

		DataInputStream inputStream;
		DataOutputStream outputStream;
		
		int nameByteLength;
		byte[] nameBytes = new byte[20];
		
		String name;
		ClientHandler newClient;
		Integer newID;
		
		System.out.println("Warte auf eingehende Verbindungen.");
		
		while (!stopListener) {
			try {
				newSocket = listener.accept();
		
				inputStream = new DataInputStream( newSocket.getInputStream());
				//nameByteLength = inputStream.readInt();
				//nameBytes = new byte[nameByteLength];
				//inputStream.read(nameBytes, 0, nameByteLength);
				//name = new String(nameBytes);
				inputStream.read(nameBytes, 0, 20);
				name = new String(nameBytes);
				
				lock_clients.acquireUninterruptibly();
				try {
					newID = getNextFreePlayerID();
					
					outputStream = new DataOutputStream( newSocket.getOutputStream());
					outputStream.writeInt(newID);
					
					newClient = new ClientHandler(newID, name, newSocket, this);
					
					//Create List of all existing players and send it to the new client.
					byte[] playerListBytes = new byte[4 + 24*clients.size()];
					
					int playersCount = clients.size();
					byte[] playersCountBytes = ByteConverter.toBytes(playersCount);
					System.arraycopy(playersCountBytes, 0, playerListBytes, 0, 4);
					
					byte[] playerIdBytes;
					String playerName;
					byte[] playerNameBytes;
					
					int i = 0;
					for (ClientHandler handler : clients.values()) {
						playerIdBytes = ByteConverter.toBytes(handler.get_ID());
						playerName = StringOperation.padRight(name, 10);
						playerNameBytes = playerName.getBytes();
						
						System.arraycopy(playerIdBytes, 0, playerListBytes, 4 + i * 24, 4);
						System.arraycopy(playerNameBytes, 0, playerListBytes, 8 + i * 24, 20);
						i++;	
					}
					
					outputStream.write(playerListBytes);
					
					//Send NewPlayerDetail to other Players.
					byte[] newPlayerInfo = new byte[24];
					
					byte[] newPlayerIDBytes = ByteConverter.toBytes(newID);
					System.arraycopy(newPlayerIDBytes, 0, newPlayerInfo, 0, 4);
					System.arraycopy(nameBytes, 0, newPlayerInfo, 4, 20);
					
					for (ClientHandler handler : clients.values()) {
						handler.SendMessage(new NetMessage(MessageType.PLAYER_JOINED, newPlayerInfo));
					}
					
					//Add new Client to clientMap.
					clients.put(newID, newClient);
					
					System.out.println("Neuer Client: ID=" + newClient.get_ID() + ", Name=" + newClient.get_Name());
				} catch (Exception exc) { }
				finally {
					lock_clients.release();
				}
				
			} catch (IOException e) {
				if (this.isClosed) System.err.println("Client konnte nicht aktzeptiert werden.");
			}
		}
	}
	
	private Integer getNextFreePlayerID() {
		Integer i = 0;
		while (clients.get(i) != null)
		{
			i++;
		}
		return i;
	}

	
	void receiveMessage(NetMessage message, ClientHandler sender) {
		switch (message.get_MessageType()) {
			case MessageType.CHATMASSAGE_TOSERVER: {
				
				System.out.println("Chatnachricht gesendet von Client " + sender.get_ID());
				
				byte[] contentLength = ByteConverter.toBytes(message.get_Content().length);
				byte[] nameBytes = sender.get_Name().getBytes();
				byte[] nameLength = ByteConverter.toBytes(nameBytes.length);
				byte[] sendBytes = new byte[message.get_Content().length + 8 + nameBytes.length];
				
				System.arraycopy(contentLength, 0, sendBytes, 0, 4);
				System.arraycopy(message.get_Content(), 0, sendBytes, 4, message.get_Content().length);
				System.arraycopy(nameLength, 0, sendBytes, 4 + message.get_Content().length, 4);
				System.arraycopy(nameBytes, 0, sendBytes, 8 + message.get_Content().length, nameBytes.length);				
				
				NetMessage sendMessage = new NetMessage(MessageType.CHATMESSAGE_TOCLIENT, sendBytes);
				
				lock_clients.acquireUninterruptibly();
				for (ClientHandler client : clients.values()) {
					client.SendMessage(sendMessage);
				}
				lock_clients.release();
				break;
			}
		}
	}
	
	
	public void StartAcceptClients() {
		if (!listenerThread.isAlive()) {
			stopListener = false;
			listenerThread.start();
		}
	}
	
	public void StopAcceptClients() {
		stopListener = true;
	}
	
	void RemoveClient(ClientHandler client) {
		lock_clients.acquireUninterruptibly();
		try {
			clients.remove(client.get_ID());
			for (ClientHandler otherClient : clients.values()) {
				otherClient.SendMessage(new NetMessage(MessageType.PLAYER_LEFT, ByteConverter.toBytes(client.get_ID())));
			}
			System.out.println(client.get_Name() + " hat das Spiel verlassen.");
		} catch (Exception exc) { 			
		} finally {
			lock_clients.release();
		}
	}

	public void close() {
		lock_clients.acquireUninterruptibly();
		for (ClientHandler handler : clients.values()) {
			handler.close();
		}
		clients.clear();
		lock_clients.release();
		
		stopListener = true;
		try {
			if (!listener.isClosed()) {
				listener.close();
			}
		} catch (IOException e) {
			// should never reach this point!
		} 
		this.isClosed = true;
		System.out.println("Server wurde geschlossen.");
	}
	
}
