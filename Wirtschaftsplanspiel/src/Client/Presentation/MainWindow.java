package Client.Presentation;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;

import Client.Application.ChatController;
import Client.Application.ClientController;
import Client.Network.Client;
import NetworkCommunication.*;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	JPanel north = new JPanel();
	JPanel east = new JPanel();
	JPanel west = new JPanel();
	JPanel south = new JPanel();
	JPanel center = new JPanel();
	
	// Panel für diverse Screens
	JPanel Pwerkstoffe = new JPanel();
	JPanel Pmaschinen = new JPanel();
	JPanel Ppersonal = new JPanel();
	JPanel Pdarlehen = new JPanel();
	JPanel Pbericht = new JPanel();
	JPanel Ppreiskal = new JPanel();
	
	// Panel das sich aktuell im CENTER befindet -> muss aus dem JFrame gelöscht werden, um neues zu laden.
	JPanel lastUsed;
	
	JTextArea chatOutput = new JTextArea("Chat:\n", 6,15);
	static MainWindow instance;
	
	
	
	
	public MainWindow(){
		super("Business Basics");
		instance = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initBasis();
		buildScreens();
			
		// North
		//north.add(new JButton("Button in the North"));
					
		// EAST
		east.setPreferredSize(new Dimension(200,(int)east.getSize().getHeight()));
		east.setLayout(new GridLayout(4,1));
				
		// UI-Elemente
		JLabel imgBusinessBa = new JLabel(new ImageIcon(this.getClass().getResource("logo.png")));
		JTextArea uebersicht = new JTextArea();
					
		uebersicht.setText("Bank \nForderungen \nVerbindlichkeiten \nGebäude");
						
		// Chat
		chatOutput.setLineWrap(true);
		chatOutput.setPreferredSize(new Dimension(150, 500));
		chatOutput.setEditable(false);
			
		JTextField chatInput = new JTextField("Message");
		chatInput.setPreferredSize(new Dimension(125, 30));	
							
		east.add(imgBusinessBa);
		JPanel sendbar = new JPanel();
							
		sendbar.add(new JScrollPane(chatOutput));
		chatOutput.setPreferredSize(new Dimension(200,250));
		sendbar.add(chatInput);
		JButton Jsend = new JButton("Send");
		Jsend.addActionListener(new sendChatMessage(chatInput));
		sendbar.add(Jsend);
							
		//east.add(chatOutput);
		east.add(sendbar);
		east.add(uebersicht);
				
		// WEST
		west.setLayout(new GridLayout(14,1));
		JLabel lMenue = new JLabel("Menü");
		JButton werkstoffe = new JButton("Werkstoffe einkaufen");
		JButton maschinen = new JButton("Maschinenverwaltung");
		JButton personal = new JButton("Personalverwaltung");
		JButton darlehen = new JButton("Darlehen");
		JButton bericht = new JButton("Berichtserstattung");
		JButton preiskal = new JButton("Preiskalkulation");
			
		// Action Listener
		werkstoffe.addActionListener(new showWerkstoffe(this));
		maschinen.addActionListener(new showMaschinen(this));
		personal.addActionListener(new showPersonal(this));
		darlehen.addActionListener(new showDarlehen(this));
		bericht.addActionListener(new showBericht(this));
		preiskal.addActionListener(new showPreiskal(this));
							
		west.add(lMenue);
		west.add(werkstoffe);
		west.add(maschinen);
		west.add(personal);
		west.add(darlehen);
		west.add(bericht);
		west.add(preiskal);
							
							
		// SOUTH
			
		// CENTER
					
	}
	
	
	public static MainWindow getInstance(){
		if(instance != null){
			return instance;
		}else{
			new MainWindow();
			return instance;
		}
	}

	public void initBasis(){
		// Menü
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu( "Datei" );
		
		// Menü-Items
		JMenuItem MenuButtonBeenden = new JMenuItem("Beenden");
		JMenuItem MenuButtonInfo = new JMenuItem("Info");
		MenuButtonBeenden.addActionListener(new closeWindow());
		MenuButtonInfo.addActionListener(new showInfo());
		
		menuBar.add( fileMenu );
		this.setJMenuBar( menuBar );
		fileMenu.add( MenuButtonBeenden );
		fileMenu.add( MenuButtonInfo );
			
		// Layout
		this.setLayout(new BorderLayout() );

			
		this.add(north, BorderLayout.NORTH);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		this.add(south, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		lastUsed = center;
				

		this.setSize(1200,768);		
	}
	
	public void buildScreens(){
		// Werkstoffe
		Pwerkstoffe.add(new JLabel("Werkstoffe einkaufen"));
		Pwerkstoffe.add(new JButton("Werkstoffe einkaufen"));
		
		// Maschinen
		Pmaschinen.add(new JLabel("Maschinen einkaufen und verkaufen"));
		Pmaschinen.add(new JButton("Maschinen einkaufen"));
		Pmaschinen.add(new JButton("Maschinen verkaufen"));
		
		// Personal
		Ppersonal.add(new JLabel("Personalverwaltung"));
		
		// Darlehen
		Pdarlehen.add(new JLabel("Darlehen aufnehmen und tilgen."));
		
		// Bericht
		Pbericht.add(new JLabel("Berich einsehen."));
		
		// Preiskalkulation
		Ppreiskal.add(new JLabel("Verkaufspreis für Produkte bestimmen."));
		
	}
	
	public void addChatMessage(String message){
		chatOutput.append(message + "\n"); 
	}
	
	private class closeWindow implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(3);
		}	
	}
	
	private class showInfo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			InfoScreen infoScreen = new InfoScreen();
			infoScreen.setVisible(true);
			}
	}
	
	private class showWerkstoffe implements ActionListener{
		
		JFrame frame;
		
		public showWerkstoffe(JFrame frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent arg0) {
			frame.add(Pwerkstoffe, BorderLayout.CENTER);
			frame.remove(lastUsed);
			frame.repaint();
			frame.validate();
			lastUsed = Pwerkstoffe;
		}
		
	}
	
	private class showMaschinen implements ActionListener{
		
		JFrame frame;
		
		public showMaschinen(JFrame frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent arg0) {
			frame.add(Pmaschinen, BorderLayout.CENTER);
			frame.remove(lastUsed);
			frame.validate();
			frame.repaint();
			lastUsed = Pmaschinen;
			
		}
		
	}
	
	private class showPersonal implements ActionListener{
		
		JFrame frame;
		
		public showPersonal(JFrame frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent arg0) {
			frame.add(Ppersonal, BorderLayout.CENTER);
			frame.remove(lastUsed);
			frame.validate();
			frame.repaint();
			lastUsed = Ppersonal;
			
		}
		
	}
	
	private class showDarlehen implements ActionListener{
		
		JFrame frame;
		
		public showDarlehen(JFrame frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent arg0) {
			frame.add(Pdarlehen, BorderLayout.CENTER);
			frame.remove(lastUsed);
			frame.validate();
			frame.repaint();
			lastUsed = Pdarlehen;
			
		}
		
	}
	
	private class showBericht implements ActionListener{
		
		JFrame frame;
		
		public showBericht(JFrame frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent arg0) {
			frame.add(Pbericht, BorderLayout.CENTER);
			frame.remove(lastUsed);
			frame.validate();
			frame.repaint();
			lastUsed = Pbericht;
			
		}
		
	}
	
	private class showPreiskal implements ActionListener{
		
		JFrame frame;
		
		public showPreiskal(JFrame frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent arg0) {
			frame.add(Ppreiskal, BorderLayout.CENTER);
			frame.remove(lastUsed);
			frame.validate();
			frame.repaint();
			lastUsed = Ppreiskal;
			
		}
		
	}
	
	private class sendChatMessage implements ActionListener{
		
		JTextField s;
		
		public sendChatMessage(JTextField s){
			this.s = s;
		}
		public void actionPerformed(ActionEvent arg0) {
			ChatController.SendChatMessage(s.getText());
		}
		
	}

}

