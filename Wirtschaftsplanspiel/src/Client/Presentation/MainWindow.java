package Client.Presentation;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Client.Application.ChatController;
import Client.Application.ClientController;
import Client.Entities.Company;
import Client.Entities.Machine;
import Client.Entities.Player;
import Client.Entities.MachineType;
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
	
	// Panel für diverse Screens
	JPanel Pwerkstoffe = new JPanel();
	JPanel Pmaschinen = new JPanel();
	JPanel Ppersonal = new JPanel();
	JPanel Pdarlehen = new JPanel();
	JPanel Pbericht = new JPanel();
	JPanel Ppreiskal = new JPanel();
	
	// Panel das sich aktuell im CENTER befindet -> muss aus dem JFrame gelöscht werden, um anderes zu laden.
	JPanel lastUsed;
	
	// Playerliste
	DefaultListModel listModel = new DefaultListModel();
	JList ListPlayers = new JList(listModel);
		
	JTextArea chatOutput = new JTextArea(19,25);
	
	// MachineScreen
	String[] machineColumnNames = {"Typ", "Kapazität", "Anschaffungswert", "Restlaufzeit", "Restwert"};
	DefaultTableModel machineTabModel = new DefaultTableModel();
	JTable machineTable = null;
	Company company;
	Object[][] machineData = null;
	JScrollPane machineScrollPane;
	
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
		//waitForOtherPlayers();
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
		this.setSize(1200,768);
		this.setMinimumSize(new Dimension(800,680));
		//this.add(north, BorderLayout.NORTH);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		//this.add(south, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		lastUsed = center;
		
		// Willkommen Screen
		center.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel welcome = new JLabel("Herzlich willkommen bei Business Basics!");
		JLabel LabelActivePlayers = new JLabel("Zur Zeit sind folgende Spieler verbunden:");
		welcome.setFont(new Font("Dialog", 0, 20));
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 30;
		center.add(welcome,c);
		
		
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 20;
		center.add(LabelActivePlayers, c);
		
		
		ListPlayers.setFont(new Font("Dialog", 0,16));
		ListPlayers.setBackground(Color.LIGHT_GRAY);
		//ListPlayers.setPreferredSize( new Dimension((ListPlayers.getWidth()+200), (ListPlayers.getHeight()+200)));
		c.gridy = 2;
		c.gridx = 0;
		c.ipady = 0;
		center.add(ListPlayers,c);
	}
	
	public void buildScreens(){

		GridBagConstraints c = new GridBagConstraints();
		
		// Werkstoffe
		Pwerkstoffe.add(new JLabel("Werkstoffe einkaufen"));
		Pwerkstoffe.add(new JButton("Werkstoffe einkaufen"));
		
		// Maschinen
		Company company = Company.getInstance();
		company.addMachine(new Machine(MachineType.Filitiermaschine, 100, 2000.0));
		company.addMachine(new Machine(MachineType.Verpackungsmaschine, 240, 1000.0));
		
		JButton verkaufen = new JButton("verkaufen");
		
		refreshMachineTable();
		
		//machineTabModel = new DefaultTableModel(machineData, machineColumnNames);
		//machineTable = new JTable(machineTabModel);
		//JScrollPane scrollPane = new JScrollPane(machineTable);
		

		
		// Listener
		verkaufen.addActionListener(new MachineVerkaufen());	
		
		Pmaschinen.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		Pmaschinen.add(new JLabel("Maschinen einkaufen und verkaufen"), c);
		
		c.gridx = 0;
		c.gridy = 1;
		Pmaschinen.add(machineScrollPane,c);
		
		c.gridx = 0;
		c.gridy = 2;
		Pmaschinen.add(verkaufen, c);
		
		//Pmaschinen.add(new JButton("Einkaufen"));
		//Pmaschinen.add(new JButton("Verkaufen"));
		
		// Personal
		Ppersonal.add(new JLabel("Personalverwaltung"));
		
		// Darlehen
		Pdarlehen.add(new JLabel("Darlehen aufnehmen und tilgen."));
		
		// Bericht
		Pbericht.add(new JLabel("Berich einsehen."));
		
		// Preiskalkulation
		Ppreiskal.add(new JLabel("Verkaufspreis für Produkte bestimmen."));
		
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
							
		uebersicht.setText("Bank \nForderungen \nVerbindlichkeiten \nGebäude");
						
		
		// Chat
		chatInput.setPreferredSize(new Dimension(210, 30));
		chatInput.addKeyListener(new chatKeyListener(Jsend));
		sendbar.setBackground(Color.LIGHT_GRAY);		
		chatOutput.setLineWrap(true);
		chatOutput.setEditable(false);
		sendbar.setPreferredSize(new Dimension(290, 360));
		sendbar.add(chatLabel);
		sendbar.add(scrollPane);
		sendbar.add(chatInput);
		sendbar.add(Jsend);			
			
		// Listener
		Jsend.addActionListener(new sendChatMessage(chatInput));
						
		// Übersicht Layout
		uebersicht.setSize(200,200);
		uebersicht.setBackground(Color.LIGHT_GRAY);
		
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
		west.setLayout(new GridLayout(18,1));
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
	}
	
	public void buildSouth(){
		
	}
	
	public void refreshMachineTable(){
		System.out.println("Machine Table aktualisiert!");
		
		company = Company.getInstance();
		machineData =  new String[company.getMachines().size()][5];
			
		Iterator<Machine> machineItr = company.getMachines().iterator();
		Machine machine;
		int i = 0;
		while(machineItr.hasNext()){
			 machine = machineItr.next();
			 machineData[i][0] = machine.getType().toString();
			 machineData[i][1] = ""+ machine.getCapacity();
			 machineData[i][2] = machine.getInitialValue() + "";
			 machineData[i][3] = machine.getRemaininTime()+ "";
			 machineData[i][4] = machine.getValue() + "";
			 i++;
			 System.out.println(i + "Maschinen");
		}
		
		machineTabModel = new DefaultTableModel(machineData, machineColumnNames);
		machineTable = new JTable(machineTabModel);
		machineScrollPane = new JScrollPane(machineTable);
		
		machineTable.validate();
		machineTable.repaint();

	}
	
	private void waitForOtherPlayers(){
		west.setVisible(false);
	}
	
	public void startGame(){
		west.setVisible(true);
	}
	
	public void setPlayers(List<Player> players){
		listModel.clear();
		System.out.println("an den Mitspielern hat sich etwas verändert!");
		
		Iterator<Player> i = players.iterator();
		
		while(i.hasNext()){
			listModel.addElement(i.next().getName());
		}
		ListPlayers.repaint();
	}
	
	public void addChatMessage(String message){
		chatOutput.append(message + "\n"); 
		chatOutput.setCaretPosition( chatOutput.getDocument().getLength());
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
		
		JTextField input;

		
		public sendChatMessage(JTextField input){
			this.input = input;
		}
		public void actionPerformed(ActionEvent arg0) {
			ChatController.SendChatMessage(input.getText());
			input.setText("");
			chatOutput.setCaretPosition( chatOutput.getDocument().getLength());
		}
		
	}
	
	private class MachineVerkaufen implements ActionListener{
		
		Machine machine;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (machineTable.getSelectedRow() != -1) {
				//tabModel.removeRow(table.getSelectedRow());
				machine = company.getMachines().get(machineTable.getSelectedRow());
				company.removeMachine(machine);
				refreshMachineTable();
				machineTable.repaint();
				System.out.println("Maschine verkauft");
				System.out.println(machine.toString());
				System.out.println("Zeile" + machineTable.getSelectedRow() + " gelöscht");
				refreshMachineTable();
			}
			
		}
	}
	
	private class chatKeyListener implements KeyListener{

		JButton button;
		
		public chatKeyListener(JButton button){
			this.button = button;
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == 10){
				button.doClick();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}

