package Client.Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.swing.*;
import Server.Application.ServerController;
import Client.Application.ChatController;
import Client.Entities.Company;
import Client.Entities.Player;
import Client.Network.Client;
import java.util.List;

import Client.Application.CannotProduceException;
import Client.Application.PeriodController;

public class MainWindow extends JFrame{
	
	private class NextActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(lastUsed.type){
			case ProdAndDistr:
				showOtherScreen(Pwerkstoffe);
				break;
			case Ressource:
				showOtherScreen(Pmaschinen);
				break;
			case Machine:
				showOtherScreen(Ppersonal);
				break;
			case Employee:
				showOtherScreen(Pdarlehen);
				break;
			case Credit:
				showOtherScreen(Pbericht);
				next.setEnabled(false);
				break;
			case Reporting:
				break;		
			case Startbildschirm:
				showOtherScreen(Ppreiskal);
				prev.setEnabled(false);
				break;		
			}

		}

	}

	private class PrevActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(lastUsed.type){
			case ProdAndDistr:
				break;
			case Ressource:
				showOtherScreen(Ppreiskal);
				prev.setEnabled(false);
				break;
			case Machine:
				showOtherScreen(Pwerkstoffe);
				break;
			case Employee:
				showOtherScreen(Pmaschinen);
				break;
			case Credit:
				showOtherScreen(Ppersonal);
				break;
			case Reporting:
				showOtherScreen(Pdarlehen);
				break;				
			}
		}

	}

	private static final long serialVersionUID = 1L;

	JFrame mainWindow = this;
	
	// Standardpanel
	JPanel north = new JPanel();
	JPanel east = new JPanel();
	JPanel west = new JPanel();
	JPanel south = new JPanel();
	JPanel mainframe = new JPanel();

	
	
	// Panel f�r diverse Screens
	TypedPanel Psplash = new TypedPanel(TypedPanel.PanelType.Startbildschirm);
	TypedPanel Pwerkstoffe = new RessourcePanel();
	TypedPanel Pmaschinen = new MachinePanel();
	TypedPanel Ppersonal = new EmployeePanel();
	TypedPanel Pdarlehen = new CreditPanel();
	TypedPanel Pbericht = new ReportingPanel();
	TypedPanel Ppreiskal = new ProductionAndDistributionPanel();
	
	// Panel das sich aktuell im CENTER befindet -> muss aus dem JFrame gel�scht werden, um anderes zu laden.
	TypedPanel lastUsed;
	
	boolean isServer;// = Player.isHost();
	
	// Playerliste
	DefaultListModel listModel = new DefaultListModel();
	JList ListPlayers = new JList(listModel);
		
	JTextArea chatOutput = new JTextArea(19,25);
	
	Company company;
	JTextField infoPanel = new JTextField(10);

	private JMenuBar menuBar;

	private JButton prev;

	private JButton next;

	

	
	// Singletonreferenz 
	static MainWindow instance;
	
	public MainWindow(){
		
		super("Business Basics");
		instance = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new specialWindowListener());
		initBasis();
		buildEast();
		buildWest();
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
		 menuBar = new JMenuBar();
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
		this.setMinimumSize(new Dimension(800,680));
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		this.add(mainframe, BorderLayout.CENTER);

		mainframe.setLayout(new BorderLayout());
		mainframe.add(Psplash, BorderLayout.CENTER);
		
		
		lastUsed = Psplash;
		
		// Willkommen Screen
		Psplash.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel welcome = new JLabel("Herzlich willkommen bei Business Basics!");
		JLabel LabelActivePlayers = new JLabel("Zur Zeit sind folgende Spieler verbunden:");
		welcome.setFont(new Font("Dialog", 0, 20));
		updateInfoPanel();
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 30;
		Psplash.add(welcome,c);
		
		
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 20;
		Psplash.add(LabelActivePlayers, c);
		
		
		ListPlayers.setFont(new Font("Dialog", 0,16));
		ListPlayers.setBackground(Color.LIGHT_GRAY);
		//ListPlayers.setPreferredSize( new Dimension((ListPlayers.getWidth()+200), (ListPlayers.getHeight()+200)));
		c.gridy = 2;
		c.gridx = 0;
		c.ipady = 0;
		Psplash.add(ListPlayers,c);
	}
	
 void serverMenuInit(){
		JMenu serverMenu = new JMenu("Server");
		JMenuItem MenuButtonSpielStarten = new JMenuItem("Spiel starten");
		MenuButtonSpielStarten.addActionListener(new startGame());
	    serverMenu.add(MenuButtonSpielStarten);
		menuBar.add(serverMenu);
 }
	public void buildEast(){
		
		east.setBackground(Color.LIGHT_GRAY);
		east.setPreferredSize(new Dimension(300, (int)east.getSize().getHeight()));
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		east.setLayout(layout);
		
		// UI-Elemente
		JLabel imgBusinessBa = new JLabel(new ImageIcon(this.getClass().getResource("logo.png")));
		JTextField chatInput = new JTextField();
		JPanel sendbar = new JPanel();
		JButton Jsend = new JButton("Send");
		JLabel chatLabel = new JLabel("Chat");
		JScrollPane scrollPane = new JScrollPane(chatOutput);
							
								
		
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
						
		// �bersicht Layout
		infoPanel.setBackground(Color.LIGHT_GRAY);
		
		infoPanel.validate();
		infoPanel.repaint();
						
		
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
		east.add(infoPanel,c); 
	}
	
	
	private static DecimalFormat format = new DecimalFormat("###,###,##0.00");
	private static String getValueString(double Value) {
		return format.format(Math.round(Value*100.0)/100.0);
	}
	
	
	public void updateInfoPanel(){
		infoPanel.setText("  Bank: " + MainWindow.getValueString(Company.getInstance().getMoney()));
	}
	
	public void buildWest(){
		west.setLayout(new GridLayout(18,1));
		JLabel lMenue = new JLabel("Men�");
		JButton werkstoffe = new JButton("Werkstoffe einkaufen");
		JButton maschinen = new JButton("Maschinenverwaltung");
		JButton personal = new JButton("Personalverwaltung");
		JButton darlehen = new JButton("Darlehen");
		JButton bericht = new JButton("Berichtserstattung");
		JButton preiskal = new JButton("Produktionsplanung");
		JButton periode = new JButton("Periode abschlie�en");
					
		// Action Listener
		werkstoffe.addActionListener(new showWerkstoffe());
		maschinen.addActionListener(new showMaschinen());
		personal.addActionListener(new showPersonal());
		darlehen.addActionListener(new showDarlehen());
		bericht.addActionListener(new showBericht());
		preiskal.addActionListener(new showPreiskal());
		periode.addActionListener(new periodeAbschliessen());
		
		west.add(lMenue);
		west.add(preiskal);
		west.add(werkstoffe);
		west.add(maschinen);
		west.add(personal);
		west.add(darlehen);
		west.add(bericht);
		west.add(new JSeparator());
		west.add(periode);
			
	}
		
	private void waitForOtherPlayers(){
		west.setVisible(false);
	}
	
	public void startGame(){
		west.setVisible(true);
		JOptionPane.showMessageDialog(new JFrame(),"Das Spiel wurde gestartet. Viel Erfolg!");
		 prev = new JButton("Zur�ck");
		 prev.setEnabled(false);
		prev.addActionListener(new PrevActionListener());
		 next = new JButton("Weiter");
		next.addActionListener(new NextActionListener());
		JPanel p = new JPanel();
		mainframe.add(p,BorderLayout.SOUTH);
		p.add(prev, BorderLayout.EAST);
		p.add(next,BorderLayout.WEST);
		mainframe.repaint();
		mainframe.validate();
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
		
		public void actionPerformed(ActionEvent arg0) {
			Pwerkstoffe = new RessourcePanel();
			showOtherScreen(Pwerkstoffe);
		}
		
	}
	
	private class showMaschinen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			((MachinePanel) Pmaschinen).refreshCapacity();
			showOtherScreen(Pmaschinen);
			
		}
		
	}
	
	private class showPersonal implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			Ppersonal = new EmployeePanel();
			showOtherScreen(Ppersonal);
			
		}
		
	}
	
	private class showDarlehen implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			showOtherScreen(Pdarlehen);
			
		}
		
	}
	
	private class showBericht implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			showOtherScreen(Pbericht);
			
		}
		
	}
	
	private class showPreiskal implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			showOtherScreen(Ppreiskal);
			
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
			try {
				Client.getInstance().close();
			} catch (Exception exc) { }
			isServer = Player.isHost();
 			if(isServer){
 				try {
 					Server.Network.Server.getInstance().close();
 				} catch(Exception exc) { }
			}
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
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
	
	public void notifyDisconnect() {
		JOptionPane.showMessageDialog(null, "Die Verbindung zum Server wurde unterbrochen.", "Verbindung getrennt", JOptionPane.CLOSED_OPTION);
		
		System.exit(3);
	}
	
	void showOtherScreen(TypedPanel t) {
		next.setEnabled(true);
		prev.setEnabled(true);
		mainframe.add(t, BorderLayout.CENTER);
		mainframe.remove(lastUsed);
		mainframe.repaint();
		mainframe.validate();
		lastUsed = t;
	}

	private class startGame implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ServerController.StartGame();
			//JOptionPane.showMessageDialog(new JFrame(),"Das Spiel wurde gestartet. Viel Erfolg!");
		}
		
	}
	
	private class periodeAbschliessen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				PeriodController.ClosePeriod();
			} catch (CannotProduceException e1) {
				//TODO: Anpassen und spezifieren.

				JOptionPane.showMessageDialog(null, "Es konnte nicht produziert werden.", "Zu wenig Produktionsfaktoren", JOptionPane.CANCEL_OPTION);
			}
		}
		
	}

}

