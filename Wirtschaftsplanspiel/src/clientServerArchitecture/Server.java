package clientServerArchitecture;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class Server {

	ServerSocket listener;
	boolean stopListener;
	Thread listenerThread;
	
	Semaphore lock_clients = new Semaphore(1);
	
	Map<Integer, ClientHandler> clients;
	//List<ClientHandler> clients;
	
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
		BufferedReader reader;
		BufferedWriter writer;

		DataInputStream inputStream;
		DataOutputStream outputStream;
		
		int nameByteLength;
		byte[] nameBytes;
		
		String name;
		ClientHandler newClient;
		Integer newID;
		
		System.out.println("Warte auf eingehende Verbindungen.");
		
		while (!stopListener) {
			try {
				newSocket = listener.accept();
				
//				reader = new BufferedReader( new InputStreamReader( newSocket.getInputStream()));
//		        buffer = new char[16];
//		        countChars = reader.read(buffer, 0, 16);		        
//				name = new String(buffer, 0, countChars);
//				name.trim();
//				
				inputStream = new DataInputStream( newSocket.getInputStream());
				nameByteLength = inputStream.readInt();
				nameBytes = new byte[nameByteLength];
				inputStream.read(nameBytes, 0, nameByteLength);
				name = new String(nameBytes);
				
				newID = getNextFreePlayerID();
				
				outputStream = new DataOutputStream( newSocket.getOutputStream());
				outputStream.writeInt(newID);

				newClient = new ClientHandler(newID, name, newSocket, this);
				
				lock_clients.acquireUninterruptibly();
				clients.put(newID, newClient);
				lock_clients.release();
				
				System.out.println("Neuer Client: ID=" + newClient.get_ID() + ", Name=" + newClient.get_Name());
				
			} catch (IOException e) {
				System.err.println("Client konnte nicht aktzeptiert werden.");
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
				
				NetMessage sendMessage = new NetMessage(MessageType.CHATMESSAGE_TOCLIENT, message.get_Content());
				
				lock_clients.acquireUninterruptibly();
				for (ClientHandler client : clients.values()) {
					//if (client != sender) { // for test purposes removed
						client.SendMessage(sendMessage);
					//}
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
		clients.remove(client.get_ID());
		lock_clients.release();
	}

	public void close() {
		lock_clients.acquireUninterruptibly();
		for (ClientHandler handler : clients.values()) {
			handler.close();
		}
		clients.clear();
		lock_clients.release();
		System.out.println("Server wurde geschlossen.");
	}
	
}
