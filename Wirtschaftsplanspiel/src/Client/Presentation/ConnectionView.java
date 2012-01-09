package Client.Presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import Client.Network.Client;

public class ConnectionView extends JFrame{

	
	private static final long serialVersionUID = 1L;
	
	public ConnectionView(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,200);
		
		setLayout(new GridLayout(4,2));
		add(new JLabel("Verbindung zum Server aufbauen"));
		add(new JSeparator());
		add(new JLabel("IP Adresse des Servers:"));
		JTextField ip = new JTextField("IP Address");
		ip.addMouseListener(new SpecialMouseListener(ip));
		add(ip);
		add(new JLabel("Username:"));
		JTextField username = new JTextField("Username");
		username.addMouseListener(new SpecialMouseListener(username));
		add(username);
		
		add(new JSeparator());
		JButton connect = new JButton("Connect");
		connect.addActionListener(new validateConnect(ip, username, this));
		add(connect);
		
		setVisible(true);
	}
	
	private class SpecialMouseListener implements MouseListener{

		public SpecialMouseListener(JTextField textField){
			this.textField = textField;
		}
		
		JTextField textField;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(textField.getText().equals("IP Address") || textField.getText().equals("Username"))
			{
				textField.setText("");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	private class validateConnect implements ActionListener{

		JTextField username;
		JTextField ip;
		JFrame connectionView;
		MainWindow mainWindow = new MainWindow();
		
		
		public validateConnect(JTextField ip, JTextField username, JFrame connectionView){
			this.ip = ip;
			this.username = username;
			this.connectionView = connectionView;
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			InetAddress inetAddress = null;
			boolean error = false;
			
			try{
				inetAddress = InetAddress.getByName(ip.getText());
				try{
					Client c1 = new Client(this.username.getText(), inetAddress, 51515);
				}catch(RuntimeException ex){
					System.err.println("Server nicht ereichbar!");
					error = true;
				}
			}catch(UnknownHostException ex){
				System.err.println("Fehler beim Verbindugsaufbau! Adresse überprüfen!");
				error = true;
			}
			
			if(!error){
				connectionView.setVisible(false);
				mainWindow.setVisible(true);				
			}else{
				System.err.println("Verbindungsdaten überprüfen!");
			}		
		}
		
	}
}
