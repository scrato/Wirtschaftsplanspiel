package Client.Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;

	public Main(){
		super("Business Basics");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
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

		JPanel north = new JPanel();
		JPanel east = new JPanel();
		JPanel west = new JPanel();
		JPanel south = new JPanel();
		JPanel center = new JPanel();
		
		this.add(north, BorderLayout.NORTH);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		this.add(south, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		
		// North
		//north.add(new JButton("Button in the North"));
		
		// EAST

		
		// WEST
		west.setLayout(new GridLayout(8,1));
		JLabel lMenue = new JLabel("Menü");
		lMenue.setLocation(100, (int) lMenue.getLocation().getY());
		JButton werkstoffe = new JButton("Werkstoffe einkaufen");
		JButton maschinen = new JButton("Maschinenverwaltung");
		JButton personal = new JButton("Personalverwaltung");
		JButton darlehen = new JButton("Darlehen");
		JButton bericht = new JButton("Berichtserstattung");
		JButton preiskal = new JButton("Preiskalkulation");
		
		west.add(lMenue);
		west.add(werkstoffe);
		west.add(maschinen);
		west.add(personal);
		west.add(darlehen);
		west.add(bericht);
		west.add(preiskal);
		
		
		// SOUTH
		//south.add(new JButton("Button in the South"));
		
		// CENTER
		
		//center.add(new JButton("Center ist hier"));
		
		this.setVisible(true);
		this.setSize(1024,768);
	}
	
	
	public static void main(String[] args){
		Main main = new Main();
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

}

