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
//import Server.Entities.PeriodInfo;
import Client.Application.ChatController;
import Client.Entities.Company;
import Client.Entities.Period;
import Client.Entities.PeriodInfo;
import Client.Entities.Player;
import Client.Network.Client;
import Client.Presentation.TypedPanel.PanelType;

import java.util.List;

import Client.Application.CannotProduceException;
import Client.Application.CannotSaleException;
import Client.Application.PeriodController;

public class MainWindow extends JFrame{
	

	private static final long serialVersionUID = 1L;

	JFrame mainWindow = this;
	
	// Standardpanel
	JPanel north = new JPanel();
	JPanel east = new JPanel();
	JPanel west = new JPanel();
	JPanel south = new JPanel();
	JPanel mainframe = new JPanel();

	
	
	// Panel für diverse Screens
	TypedPanel Psplash = new TypedPanel(TypedPanel.PanelType.Startbildschirm);
	TypedPanel Pwerkstoffe = new RessourcePanel();
	TypedPanel Pmaschinen = new MachinePanel();
	TypedPanel Ppersonal = new EmployeePanel();
	TypedPanel Pdarlehen = new CreditPanel();
	TypedPanel Pbericht = new ReportingPanel();
	TypedPanel Ppreiskal = new ProductionAndDistributionPanel();
	TypedPanel Pwaiting = new TypedPanel(TypedPanel.PanelType.Wartebildschirm);
	
	// Panel das sich aktuell im CENTER befindet -> muss aus dem JFrame gelöscht werden, um anderes zu laden.
	TypedPanel lastUsed;
	
	boolean isServer;// = Player.isHost();
	boolean isPlayerInsolvent;
	boolean gameOver;
	
	// Playerliste
	DefaultListModel listModel = new DefaultListModel();
	JList ListPlayers = new JList(listModel);
		
	JTextArea chatOutput = new JTextArea(19,25);
	
	Company company;
	JTextField infoPanel = new JTextField(10);

	private JMenuBar menuBar;

	 JButton prev = new JButton("Zurück");
	 JButton next = new JButton("Weiter");



	public void changeScreen(TypedPanel t) {
		
		if (lastUsed == t){
			t.refreshPanel();
			return;
		}
		
		if (t.type == PanelType.Endbildschirm && gameOver) {
			return;
		}
		
		if (t.type == PanelType.Endbildschirm) {
			gameOver = true;
			( (ReportingPanel)Pbericht ).setPeriod(PeriodInfo.getNumberOfActPeriod() - 1);
			berichtButton.setEnabled(true);
		}
		
		next.setEnabled(true);
		prev.setEnabled(true);
		if (t.type == PanelType.Startbildschirm || 
				t.type == PanelType.ProdAndDistr || 
				t.type == PanelType.Reporting)
			prev.setEnabled(false);
		if (t.type == PanelType.Employee)
			next.setEnabled(false);
		
		if (t.type == PanelType.Credit){
			prev.setEnabled(false);
			next.setEnabled(false);
		}
		
		t.refreshPanel();
		mainframe.add(t, BorderLayout.CENTER);
		mainframe.remove(lastUsed);
		mainframe.repaint();
		mainframe.validate();
		lastUsed = t;
	}

	
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
		// Menü
		 menuBar = new JMenuBar();
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
		createAndShowSplashScreen();
		createWaitingScreen();
		
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

	private void createWaitingScreen() {
		Pwaiting.setLayout(new BorderLayout());
		JLabel wait = new JLabel("Die Periode ist abgeschlossen. Bitte warten sie, bis alle anderen Spieler fertig sind.");
		wait.setHorizontalAlignment(JLabel.CENTER);
		Pwaiting.add(wait, BorderLayout.CENTER);	
	}

	/**
	 * 
	 */
	private void createAndShowSplashScreen() {
		this.setLayout(new BorderLayout() );
		setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(800,680));
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		this.add(mainframe, BorderLayout.CENTER);

		mainframe.setLayout(new BorderLayout());
		mainframe.add(Psplash, BorderLayout.CENTER);
		
		
		lastUsed = Psplash;
	}
	
 void serverMenuInit(){
		JMenu serverMenu = new JMenu("Server");
		JMenuItem MenuButtonSpielStarten = new JMenuItem("Spiel starten");
		MenuButtonSpielStarten.addActionListener(new startGame(menuBar, serverMenu));
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
						
		// Übersicht Layout
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
	
	JButton berichtButton;	
	public void buildWest(){
		west.setLayout(new GridLayout(18,1));
		JLabel lMenue = new JLabel("Menü");
		JButton werkstoffe = new JButton("Werkstoffe einkaufen");
		JButton maschinen = new JButton("Maschinenverwaltung");
		JButton personal = new JButton("Personalverwaltung");
		JButton darlehen = new JButton("Darlehen");
		berichtButton = new JButton("Berichtserstattung");
		berichtButton.setEnabled(false); // wird erst nach erstem periodenabschluss erlaubt.
		JButton preiskal = new JButton("Absatz- und Produktionsplanung");
		JButton periode = new JButton("Periode abschließen");
					
		// Action Listener
		werkstoffe.addActionListener(new showWerkstoffe());
		maschinen.addActionListener(new showMaschinen());
		personal.addActionListener(new showPersonal());
		darlehen.addActionListener(new showDarlehen());
		berichtButton.addActionListener(new showBericht());
		preiskal.addActionListener(new showPreiskal());
		periode.addActionListener(new periodeAbschliessen());
		
		west.add(lMenue);
		west.add(preiskal);
		west.add(werkstoffe);
		west.add(maschinen);
		west.add(personal);
		west.add(darlehen);
		west.add(new JSeparator());
		west.add(periode);
		west.add(new JSeparator());
		west.add(berichtButton);
			
	}
		
	private void waitForOtherPlayers(){
		west.setVisible(false);
	}
	
	public void startGame(){
		west.setVisible(true);
		JOptionPane.showMessageDialog(new JFrame(),"Das Spiel wurde gestartet. Viel Erfolg!");

		prev.setEnabled(false);
		prev.addActionListener(new PrevActionListener());
		next.addActionListener(new NextActionListener());
		JPanel p = new JPanel();
		mainframe.add(p,BorderLayout.SOUTH);
		p.add(prev, BorderLayout.EAST);
		p.add(next,BorderLayout.WEST);
//		mainframe.repaint();
//		mainframe.validate();
		changeScreen(Ppreiskal);
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
	
	public void showInsolvency() {
		isPlayerInsolvent = true;
		this.west.setVisible(false);
		infoPanel.setVisible(false);
		JOptionPane.showMessageDialog(null, "Sie konnten Ihre Rechnungen nicht bezahlen.", "Insolvenz", JOptionPane.OK_OPTION);
	}
	
	public void reactiviateAfterPeriod(){
		//Pbericht = new ReportingPanel(); 
		( (ReportingPanel)Pbericht ).setPeriod(PeriodInfo.getNumberOfActPeriod() - 1);
		berichtButton.setEnabled(true);
		changeScreen(Pbericht);
		if (isPlayerInsolvent) {
			return;
		}
		west.setVisible(true);
		next.setVisible(true);
		prev.setVisible(true);
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
			changeScreen(Pwerkstoffe);
		}
		
	}
	
	private class showMaschinen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			((MachinePanel) Pmaschinen).refreshCapacity();
			changeScreen(Pmaschinen);
			
		}
		
	}
	
	private class showPersonal implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			Ppersonal = new EmployeePanel();
			changeScreen(Ppersonal);
			
		}
		
	}
	
	private class showDarlehen implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			changeScreen(Pdarlehen);
			
		}
		
	}
	
	private class showBericht implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			changeScreen(Pbericht);
			
		}
		
	}
	
	private class showPreiskal implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			changeScreen(Ppreiskal);
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
	


	private class startGame implements ActionListener{

		JMenuBar menuBar;
		JMenu serverMenu;
		public startGame(JMenuBar menuBar, JMenu serverMenu)
		{
			this.menuBar = menuBar;
			this.serverMenu = serverMenu;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ServerController.StartGame();
			menuBar.remove(serverMenu);
			menuBar.revalidate();
			menuBar.repaint();
			//JOptionPane.showMessageDialog(new JFrame(),"Das Spiel wurde gestartet. Viel Erfolg!");
		}
		
	}
	
	private class periodeAbschliessen implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				PeriodController.ClosePeriod();
				west.setVisible(false);
				next.setVisible(false);
				prev.setVisible(false);
				changeScreen(Pwaiting);
			} catch (CannotProduceException e1) {
				JOptionPane.showMessageDialog(null, "Es konnte nicht produziert werden.", "Zu wenig Produktionsfaktoren", JOptionPane.CANCEL_OPTION);
			} catch (CannotSaleException e2) {
				JOptionPane.showMessageDialog(null, "Periode konnte nicht abgeschlossen werden, da mehr Güter verkauft werden als möglich ist.", "Zu wenig Produktionsfaktoren", JOptionPane.CANCEL_OPTION);
			}
		}
		
	}

	private class NextActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(lastUsed.type){
			case ProdAndDistr:
				changeScreen(Pwerkstoffe);
				break;
			case Ressource:
				changeScreen(Pmaschinen);
				break;
			case Machine:
				changeScreen(Ppersonal);
				break;
			case Employee:
				break;
			case Credit:
				//changeScreen(Pbericht);
				break;
			case Reporting:
				changeScreen(Ppreiskal);
				break;		
			case Startbildschirm:
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
				changeScreen(Ppreiskal);
				break;
			case Machine:
				changeScreen(Pwerkstoffe);
				break;
			case Employee:
				changeScreen(Pmaschinen);
				break;
			case Credit:
				//changeScreen(Ppersonal);
				break;
			case Reporting:
				//changeScreen(Pdarlehen);
				break;				
			}
		}

	}

}

