package Client.Presentation;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.Border;

import Client.Application.ChatController;
import Client.Application.ClientController;
import Client.Entities.Player;
import Client.Network.Client;
import NetworkCommunication.*;

import java.util.List;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	// Standardpanel
	JPanel north = new JPanel();
	JPanel east = new JPanel();
	JPanel west = new JPanel();
	JPanel south = new JPanel();
	JPanel center = new JPanel();
	
	// Panel f�r diverse Screens
	JPanel Pwerkstoffe = new JPanel();
	JPanel Pmaschinen = new JPanel();
	JPanel Ppersonal = new JPanel();
	JPanel Pdarlehen = new JPanel();
	JPanel Pbericht = new JPanel();
	JPanel Ppreiskal = new JPanel();
	
	// Panel das sich aktuell im CENTER befindet -> muss aus dem JFrame gel�scht werden, um anderes zu laden.
	JPanel lastUsed;
	
	// Playerliste
	DefaultListModel listModel = new DefaultListModel();
	JList ListPlayers = new JList(listModel);
	
	
	JTextArea chatOutput = new JTextArea(12,26);
	
	// Singletonreferenz 
	static MainWindow instance;
	
	public MainWindow(){
		super("Business Basics");
		instance = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initBasis();
		buildScreens();
		buildNorth();
		buildEast();
		buildWest();
		buildSouth();
		waitForOtherPlayers();
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
		// Men�
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu( "Datei" );
		
		// Men�-Items
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
		this.setSize(1200,768);
		this.add(north, BorderLayout.NORTH);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		this.add(south, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		lastUsed = center;
		
		// Willkommen Screen
		center.add(new JLabel("Herzlich willkommen bei Business Basics!"));
		
		center.add(ListPlayers);
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
		Ppreiskal.add(new JLabel("Verkaufspreis f�r Produkte bestimmen."));
		
	}
	
	public void buildNorth(){
		
	}
	
	public void buildEast(){
		
		east.setBackground(Color.LIGHT_GRAY);
		//east.setBorder(BorderFactory.createLineBorder(Color.black));
		east.setPreferredSize(new Dimension(300, (int)east.getSize().getHeight()));
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		east.setLayout(layout);
		
		// UI-Elemente
		JLabel imgBusinessBa = new JLabel(new ImageIcon(this.getClass().getResource("logo.png")));
		JTextArea uebersicht = new JTextArea();
		JTextField chatInput = new JTextField();
		JPanel sendbar = new JPanel();
		JButton Jsend = new JButton("Send");
		JLabel chatLabel = new JLabel("Chat");
		JScrollPane scrollPane = new JScrollPane(chatOutput);
							
		uebersicht.setText("Bank \nForderungen \nVerbindlichkeiten \nGeb�ude");
						
		
		// Chat
		chatInput.setPreferredSize(new Dimension(210, 30));
		sendbar.setBackground(Color.LIGHT_GRAY);		
		chatOutput.setLineWrap(true);
		chatOutput.setEditable(false);
		sendbar.setPreferredSize(new Dimension(300, 260));
		sendbar.add(chatLabel);
		sendbar.add(scrollPane);
		sendbar.add(chatInput);
		sendbar.add(Jsend);			
			
		// Listener
		Jsend.addActionListener(new sendChatMessage(chatInput));
						
		// �bersicht Layout
		uebersicht.setSize(200,200);
		
		uebersicht.validate();
		uebersicht.repaint();
						
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 20;
		east.add(imgBusinessBa, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		east.add(sendbar,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,10,0,5);  //top padding
		east.add(uebersicht,c); 
	}
	
	public void buildWest(){
		west.setLayout(new GridLayout(14,1));
		JLabel lMenue = new JLabel("Men�");
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
	}
	
	public void buildSouth(){
		
	}
	
	private void waitForOtherPlayers(){
		west.setVisible(false);
	}
	
	public void startGame(){
		west.setVisible(true);
	}
	
	public void setPlayers(List<Player> players){
		listModel.clear();
		System.out.println("an den Mitspielern hat sich etwas ver�ndert!");
		
		Iterator<Player> i = players.iterator();
		
		while(i.hasNext()){
			listModel.addElement(i.next().getName());
		}
		ListPlayers.repaint();
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
			s.setText("");
		}
		
	}

}

