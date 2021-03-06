package server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;


import networkCommunication.ByteConverter;
import networkCommunication.MessageType;
import networkCommunication.NetMessage;
import networkCommunication.StringOperation;
import server.application.ServerController;
import server.entities.PeriodInfo;


public class Server {
	
	private static Server instance;
	
	public static Server getInstance() {
		if (instance == null) throw new RuntimeException("Kein Server aktiv");
		return instance;
	}

	ServerSocket listener;
	boolean stopListener;
	Thread listenerThread;
	
	Semaphore lock_clients = new Semaphore(1);
	
	Map<Integer, ClientHandler> clients;
	//List<ClientHandler> clients;
	
	boolean isClosed;
	boolean gameStartet;
	
	public static Server StartServer(int port, int maxPeriods) {
		if (instance != null) throw new RuntimeException("Bereits verbunden");
		Server server = new Server(port, maxPeriods);
		instance = server;
		return server;
	}
	
	private Server(int port, int maxPeriods) throws RuntimeException {
		PeriodInfo.maxPeriods = maxPeriods;
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
			throw new RuntimeException("Server konnte nicht initialisiert werden.");
		}
		
	}
	
	private void StartListener()
	{
		Socket newSocket;

		DataInputStream inputStream;
		DataOutputStream outputStream;
		
		byte[] nameBytes = new byte[20];
		
		String name;
		ClientHandler newClient;
		Integer newID;
		
		System.out.println("Warte auf eingehende Verbindungen.");
		
		while (!stopListener) {
			try {
				newSocket = listener.accept();
				newSocket.setSoTimeout(5000);
		
				inputStream = new DataInputStream( newSocket.getInputStream());
				outputStream = new DataOutputStream( newSocket.getOutputStream());
				
				//outputStream.writeInt(gameStartet ? 0 : 1); // blocks Client if game already has started
				outputStream.writeBoolean(!gameStartet); // blocks Client if game already has started
				if (gameStartet) {
					try {
						if (!newSocket.isInputShutdown()) {
							newSocket.getInputStream().close();
						}
						if (!newSocket.isOutputShutdown()) {
							newSocket.getOutputStream().close();
						}
						if (!newSocket.isClosed()) {
							newSocket.close();
						}
					} catch (IOException e) {
						// should never reach this point!
					}
					continue;
				}
				
				outputStream.writeInt(PeriodInfo.maxPeriods);
				inputStream.read(nameBytes, 0, 20);

				name = new String(nameBytes, "UTF-16LE");
				
				lock_clients.acquireUninterruptibly();
				try {
					newID = getNextFreePlayerID();
					
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
						playerName = StringOperation.padRight(handler.get_Name(), 10);
						playerNameBytes = playerName.getBytes("UTF-16LE");
						
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
				if (!this.isClosed) System.err.println("Client konnte nicht aktzeptiert werden.");
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
		ServerController.checkSupplies(); // Pr�ft, ob alle �brigen Spieler Angebote abgegeben haben.
		ServerController.checkResultsCollected();
	}
	
	public void startGame() { 
		gameStartet = true;
		lock_clients.acquireUninterruptibly();
		try {
			for (ClientHandler client : clients.values()) {
				client.SendMessage(new NetMessage(MessageType.GAME_STARTED, new byte[0]));
			}
			System.out.println("Spiel wurdegestartet.");
		} catch (Exception exc) { 			
		} finally {
			lock_clients.release();
		}
	}

	public void close() {
		instance = null;
		this.isClosed = true;
		
		lock_clients.acquireUninterruptibly();
		try {
			for (ClientHandler handler : clients.values()) {
				handler.close();
			}
			clients.clear();
		} catch (Exception e) {
		} finally {
			lock_clients.release();
		}
		stopListener = true;
		try {
			if (!listener.isClosed()) {
				listener.close();
			}
		} catch (IOException e) {
			// should never reach this point!
		} 
		System.out.println("Server wurde geschlossen.");
	}
	
	public List<ClientHandler> getClients() {
		List<ClientHandler> retList = new LinkedList<ClientHandler>();
		lock_clients.acquireUninterruptibly();
		try {
			retList.addAll(clients.values());
		} catch (Exception e) {
		} finally {
			lock_clients.release();
		}
		return retList;
	}
	
}
