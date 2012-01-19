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

import Client.Network.Client;
import Client.Presentation.*;

public class ConnectionView extends JFrame{

	
	private static final long serialVersionUID = 1L;
	
	public ConnectionView(){
		
		//Layout
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,200);
		setLayout(new GridLayout(4,2));
		addWindowListener(new specialWindowListener());
		
		
		
		// UI Elemente
		JTextField ip = new JTextField("IP Adresse");
		JTextField username = new JTextField("Username");
		JButton connect = new JButton("Connect");
		JButton abbruch = new JButton("Abbrechen");
		
		
		add(new JLabel("Verbindung zum Server aufbauen"));
		add(new JSeparator());
		add(new JLabel("IP Adresse des Servers:"));
		add(ip);
		add(new JLabel("Username:"));
		add(username);
		add(abbruch);
		add(connect);
		
		
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
	
	private class specialWindowListener implements WindowListener{

		
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("AnmeldeScreen geschlossen!");
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("AnmeldeScreen geschlossen!");
			// NOT
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
			Client.getInstance().close();
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("AnmeldeScreen geschlossen!");
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("AnmeldeScreen geschlossen!");
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
}
