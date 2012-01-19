package Client.Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import NetworkCommunication.StringOperation;

public class ReportingPanel extends JPanel {
	
	public class TAccountEntry {
		public static final boolean AKTIVA = true;
		public static final boolean PASSIVA = false;
		
		public String name;
		public String value;
		public boolean side;
		
		public TAccountEntry(String Name, String Value, boolean Side) {
			name = Name;
			value = Value;
			side = Side;
		}
	}
	
	public class TAccount extends JPanel {
				
		private static final long serialVersionUID = 1L;
		//private List<TAccountEntry> entries = new LinkedList<TAccountEntry>();
		private String name;
		
		private JPanel headerPanel;
		private JPanel bodyPanel;
		
		private JPanel aktivaPanel;
		private JPanel passivaPanel;
		
		public TAccount(String Name) {
			super(new BorderLayout());
			
			name = Name;
			
			headerPanel = new JPanel(new FlowLayout());
			this.add(headerPanel);
			headerPanel.add(new JLabel(Name), BorderLayout.CENTER);
			
			bodyPanel = new JPanel(new GridLayout(1, 2));
			this.add(bodyPanel, BorderLayout.SOUTH);
			
			//aktivaPanel = new JPanel(new BorderLayout());
			//passivaPanel = new JPanel(new BorderLayout());
			aktivaPanel = new JPanel(new GridLayout(0, 2));
			passivaPanel = new JPanel(new GridLayout(0, 2));
			
			bodyPanel.add(aktivaPanel);
			bodyPanel.add(passivaPanel);
		}
		
		public void addEntry(TAccountEntry entry) {
			//entries.add(entry);
			JLabel nameLabel = new JLabel(StringOperation.padRight(" " + entry.name, 20));
			JLabel valueLabel = new JLabel(StringOperation.padRight(" " + entry.value, 20));
			
//			JPanel namePanel = new JPanel(new BorderLayout());
//			namePanel.add(nameLabel, BorderLayout.CENTER);
//			
//			JPanel valuePanel = new JPanel(new BorderLayout());
//			valuePanel.add(valueLabel, BorderLayout.CENTER);
//			
//			JPanel entryPanel = new JPanel(new BorderLayout());
//			entryPanel.add(namePanel, BorderLayout.WEST);
//			entryPanel.add(valuePanel, BorderLayout.EAST);
//			
			if (entry.side == TAccountEntry.AKTIVA) {
				//aktivaPanel.add(entryPanel);
				aktivaPanel.add(nameLabel);
				aktivaPanel.add(valueLabel);
			}
			if (entry.side == TAccountEntry.PASSIVA) {
				//passivaPanel.add(entryPanel);
				passivaPanel.add(nameLabel);
				passivaPanel.add(valueLabel);
			}
			
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			//linien zeichnen
			int x1, x2, y1, y2 = 0; // coordinates for drawing lines.
			
			x1 = headerPanel.getX();
			x2 = headerPanel.getX() + headerPanel.getWidth();
			y1 = y2 = headerPanel.getY() + headerPanel.getHeight();
			g.setColor(Color.BLACK);
			g.drawLine(x1, y1, x2, y2);
			x1 = x2 = bodyPanel.getX() + aktivaPanel.getWidth();
			y1 = bodyPanel.getY();
			y2 = bodyPanel.getY() + aktivaPanel.getHeight();
			
			g.drawLine(x1, y1, x2, y2);
			
		}
		

		
	}

	private static final long serialVersionUID = 1L;
	private TAccount GuV;
	private TAccount Bilanz;
	
	public ReportingPanel() {
		super(new GridLayout(1,2));
		//this.setLayout(new BorderLayout());
		//this.setLayout(new GridLayout(1, 2));
		
		Bilanz = new TAccount("Bilanz");
		GuV = new TAccount("Gewinn und Verlust");
		
//		this.add(Bilanz, BorderLayout.WEST);
//		this.add(GuV, BorderLayout.EAST);
		this.add(Bilanz);
		this.add(GuV);
		
		this.addGuvEntry("tesasdasdasdasdadt", "100", TAccountEntry.AKTIVA);
		this.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
		this.addGuvEntry("test", "100", TAccountEntry.AKTIVA);
		this.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
		this.addGuvEntry("test", "100", TAccountEntry.AKTIVA);
		this.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
		this.addGuvEntry("test", "100", TAccountEntry.AKTIVA);
		this.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
		
		this.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
		this.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
		this.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
		this.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
		
		this.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
		this.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
		this.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
		this.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
		this.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
		this.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
		this.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
		this.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
		
		this.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
		this.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
		this.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
		this.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
		this.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
		

	}
	
	public void addBilanzEntry(String name, String value, boolean side) {
		Bilanz.addEntry(new TAccountEntry(name, value, side));
	}
	
	public void addGuvEntry(String name, String value, boolean side) {
		GuV.addEntry(new TAccountEntry(name, value, side));
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		ReportingPanel repPanel = new ReportingPanel();
//		repPanel.addGuvEntry("tesasdasdasdasdadt", "100", TAccountEntry.AKTIVA);
//		repPanel.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
//		repPanel.addGuvEntry("test", "100", TAccountEntry.AKTIVA);
//		repPanel.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
//		repPanel.addGuvEntry("test", "100", TAccountEntry.AKTIVA);
//		repPanel.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
//		repPanel.addGuvEntry("test", "100", TAccountEntry.AKTIVA);
//		repPanel.addGuvEntry("test2", "200", TAccountEntry.AKTIVA);
//		
//		repPanel.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
//		repPanel.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
//		repPanel.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
//		repPanel.addGuvEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addGuvEntry("test4", "400", TAccountEntry.PASSIVA);
//		
//		repPanel.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
//		repPanel.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
//		repPanel.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
//		repPanel.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
//		repPanel.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
//		repPanel.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
//		repPanel.addBilanzEntry("test", "100", TAccountEntry.AKTIVA);
//		repPanel.addBilanzEntry("test2", "200", TAccountEntry.AKTIVA);
//		
//		repPanel.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
//		repPanel.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
//		repPanel.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
//		repPanel.addBilanzEntry("test3", "300", TAccountEntry.PASSIVA);
//		repPanel.addBilanzEntry("test4", "400", TAccountEntry.PASSIVA);
//		
		//frame.setLayout(new BorderLayout());
		//frame.add(repPanel, BorderLayout.CENTER);
		frame.add(repPanel);
		
		frame.setVisible(true);
	}

}
