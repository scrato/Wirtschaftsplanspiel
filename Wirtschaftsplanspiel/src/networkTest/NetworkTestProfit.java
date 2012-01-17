package networkTest;
public class NetworkTestProfit {

	public void start(){
		ServerThread st = new ServerThread();
		ClientThread ct1 = new ClientThread();
		ClientThread ct2 = new ClientThread();
		

		ct1.run();
		ct2.run();
		st.run();
	}
	
	
	public static void main(String[] args){
		NetworkTestProfit t = new NetworkTestProfit();
		t.start();
	}

	private class ServerThread implements Runnable{

		@Override
		public void run() {
			Server.program.main(null);
		}
		
	}

	private class ClientThread implements Runnable{

		@Override
		public void run() {
			Client.program.main(null);
		}
		
	}

	
}
