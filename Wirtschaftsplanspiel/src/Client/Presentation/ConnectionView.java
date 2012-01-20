package Client.Presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import Server.Application.ServerController;

import Client.Network.Client;
import Client.Presentation.*;
import Client.Entities.Player;
import Client.Application.ClientController;

public class ConnectionView extends JFrame{

	
	private static final long serialVersionUID = 1L;
	
	public ConnectionView(){
		
		//Layout
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,300);
		setLayout(new GridLayout(8,2));
	
		
		// UI Elemente
		JTextField ip = new JTextField("IP Adresse");
		JTextField username = new JTextField("Username");
		JButton connect = new JButton("Connect");
		JButton abbruch = new JButton("Abbrechen");
		JButton serverErstellen = new JButton("Spiel erstellen");
		JTextField anzahlRunden = new JTextField("10");
		
		
		add(new JLabel("Username:"));
		add(username);
		add(new JLabel());add(new JLabel());
		add(new JLabel("Verbindung zum Server aufbauen"));
		add(new JLabel());
		add(new JLabel("IP Adresse des Servers:"));
		add(ip);
		add(new JLabel());
		add(connect);
		add(new JLabel("Spiel erstellen:"));
		add(new JLabel());
		add(new JLabel("Anzahl Runden:"));
		add(anzahlRunden);
		add(abbruch);
		add(serverErstellen);
		
		ip.addMouseListener(new SpecialMouseListener(ip));
		ip.addFocusListener(new SpecialFocusListener(ip));
		ip.addKeyListener(new SpecialKeyListener(connect));
		
		username.addMouseListener(new SpecialMouseListener(username));
		username.addFocusListener(new SpecialFocusListener(username));
		username.addKeyListener(new SpecialKeyListener(connect));
		
		connect.addActionListener(new validateConnect(ip, username, this));
		connect.addKeyListener(new SpecialKeyListener(connect));
		
		abbruch.addKeyListener(new SpecialKeyListener(abbruch));
		abbruch.addActionListener(new close());
		
		serverErstellen.addActionListener(new startServer(anzahlRunden, username, this));
		
		//anzahlRunden.addMouseListener(new SpecialMouseListener(anzahlRunden));
			
		
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
					//Client c1 = new Client(this.username.getText(), inetAddress, 51515);
					ClientController.ConnectToServer(this.username.getText(), inetAddress, 51515);
				}catch(RuntimeException ex){
					System.err.println("Server nicht ereichbar!");
					JOptionPane.showMessageDialog(new JFrame(),"Server nicht gefunden.");
					error = true;
				}
			}catch(UnknownHostException ex){
				System.err.println("Fehler beim Verbindugsaufbau! Adresse überprüfen!");
				error = true;
			}
			
			if(!error && !this.username.getText().equals("") && !this.ip.getText().equals("")){
				connectionView.setVisible(false);
				mainWindow.setVisible(true);				
			}else if(this.username.getText().equals("")){
				JOptionPane.showMessageDialog(new JFrame(),"Bitte geben Sie einen Spielernamen ein.");
			}else if(this.ip.getText().equals("")){
				JOptionPane.showMessageDialog(new JFrame(),"Bitte geben Sie eine IP Adresse ein.");
			}		
			else{
				JOptionPane.showMessageDialog(new JFrame(),"Verbindungsdaten überprüfen.");
			}		
		}
		
	}
	private class close implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(3);
		}
		
	}
	private class startServer implements ActionListener{

		JTextField anzahlRunden;
		JTextField username;
		JFrame connectionView;
		
		public startServer(JTextField anzahlRunden, JTextField username, JFrame connectionView){
			this.anzahlRunden = anzahlRunden; 
			this.username = username;
			this.connectionView = connectionView;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try{
				if(Integer.parseInt(anzahlRunden.getText()) > 0){
					
					InetAddress address = null;
					
					try{
						address = InetAddress.getByName("localhost");
					}catch(UnknownHostException ex){
						System.err.println("Fehler beim Verbindugsaufbau! Adresse überprüfen!");
					}
					
					//new Server.Network.Server(51515, 12);
					try {
						ServerController.StartServer(51515, 12); //TODO anzahl runden aus textbox ziehen fehlt??
					} catch (RuntimeException exc) {
						JOptionPane.showMessageDialog(null, "Fehler bei Start des Servers.", "Der Server konnte nicht gestartet werden.", JOptionPane.OK_OPTION);
						return;
					}
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//Client c1 = new Client(this.username.getText(), address, 51515);
					ClientController.ConnectToServer(this.username.getText(), address, 51515);	
					
					connectionView.setVisible(false);
					MainWindow.getInstance().setVisible(true);
					Player.setHost(true);
					
				}else{
					JOptionPane.showMessageDialog(new JFrame(),"Rundenzahl überprüfen.");
				}
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(new JFrame(),"Rundenzahl überprüfen.");
			}
		}
		
	}
}
