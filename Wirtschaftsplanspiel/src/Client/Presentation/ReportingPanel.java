package Client.Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import NetworkCommunication.StringOperation;
import Client.Entities.GuV;
import Client.Entities.Balance;

public class ReportingPanel extends JPanel {
	
	public class TAccountEntry {	
	
		public String entryName;
		public String entryValue;
		
		public TAccountEntry(String Name, String Value)  {
			//super(new GridLayout(1, 2));
			entryName = Name;
			entryValue = Value;
		}
		
		public TAccountEntry(String Name, double Value) {			
			this(Name, getValueString(Value));
		}
	}
	
	private static DecimalFormat format = new DecimalFormat("###,###,##0.00");
	private static String getValueString(double Value) {
		return format.format(Math.round(Value*100.0)/100.0);
	}
	
	public enum TAccountSides {
		left,
		right
	}
	
	public class TAccountSide extends JPanel {
		private static final long serialVersionUID = 1L;
		
		int itemCounter = 0;
		
		public TAccountSide() {
			this.setLayout(new GridLayout(0, 2));
		}
		
//		@Override
//		public Component add(Component comp) {
//			if (comp instanceof TAccountEntry) {
//				TAccountEntry entry = (TAccountEntry)comp;
//
//				JLabel nameLabel = new JLabel(" " + entry.entryName);
//				JLabel valueLabel = new JLabel(" " + entry.entryValue);
//				
//				this.add(nameLabel);
//				this.add(valueLabel);
//				
//				itemCounter ++;
//				
//				return comp;
//			} else {
//				return super.add(comp); 
//			}
//		}
		
		public void addEntry(TAccountEntry entry) {
			JLabel nameLabel = new JLabel(" " + entry.entryName, JLabel.LEFT);
			JLabel valueLabel = new JLabel(entry.entryValue + " ", JLabel.RIGHT);
			//nameLabel.setHorizontalTextPosition();
			this.add(nameLabel);
			this.add(valueLabel);
			
			itemCounter ++;
		}
//		
		public int getItemCounter() {
			return itemCounter;
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			//Buchhalternasen
			
			//Unterstrich
			
			//Unterstrich unter Summe.
		}
	}
	
	public class TAccount extends JPanel {
				
		private static final long serialVersionUID = 1L;
		//private List<TAccountEntry> entries = new LinkedList<TAccountEntry>();
		private String name;
		
		private JPanel headerPanel;
		private JPanel bodyPanel;
		
		private TAccountSide leftSide;
		private TAccountSide rightSide;
		
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
			leftSide = new TAccountSide();//JPanel(new GridLayout(0, 2));
			rightSide = new TAccountSide(); //JPanel(new GridLayout(0, 2));
			
			bodyPanel.add(leftSide);
			bodyPanel.add(rightSide);
		}
		
		public void addEntry(TAccountEntry entry, TAccountSides side) {
			//entries.add(entry);
			
			if (side == TAccountSides.left) {
				leftSide.addEntry(entry);
			}
			if (side == TAccountSides.right) {
				rightSide.addEntry(entry);
			}
			
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			//T zeichnen
			int x1, x2, y1, y2 = 0; // coordinates for drawing lines.
			
			x1 = headerPanel.getX();
			x2 = headerPanel.getX() + headerPanel.getWidth();
			y1 = y2 = headerPanel.getY() + headerPanel.getHeight();
			g.setColor(Color.BLACK);
			g.drawLine(x1, y1, x2, y2);
			x1 = x2 = bodyPanel.getX() + leftSide.getWidth();
			y1 = bodyPanel.getY();
			y2 = bodyPanel.getY() + leftSide.getHeight();
			
			g.drawLine(x1, y1, x2, y2);
			
//			int countAktiva = aktivaPanel.getComponentCount();
//			int countPassiva = passivaPanel.getComponentCount();
//			
//			Component lastAktivaPanel = aktivaPanel.getComponents()[countAktiva-1];
//			Component lastPassivaPanel = aktivaPanel.getComponents()[countPassiva-1];
//			
//			Component lastPanel;
//			Component leastPanel;
//			if (countAktiva >= countPassiva) {
//				lastPanel = lastAktivaPanel;
//				leastPanel = lastPassivaPanel;
//			} else {
//				lastPanel = lastPassivaPanel;
//				leastPanel = lastAktivaPanel;
//			}
//			//Buchhalternase zeichnen
//			
//			
//			//Unterstrich zeichnen			
//			x1 = bodyPanel.getX();
//			x2 = bodyPanel.getX()+bodyPanel.getWidth();
//			
//			y1 = y2 = lastPanel.getY() + lastPanel.getHeight();
//			
//			g.drawLine(x1, y1, x2, y2);
//			
//			//Unterstrich unter Summen
		}
		

		
	}

	private static final long serialVersionUID = 1L;
	private TAccount balancePanel;
	private TAccount guVPanel;
	
	public ReportingPanel(Balance balance, GuV guv) {
		super(new GridLayout(2,1));
		//this.setLayout(new BorderLayout());
		//this.setLayout(new GridLayout(1, 2));
		
		balancePanel = new TAccount("Bilanz");
		guVPanel = new TAccount("Gewinn und Verlust");
		
//		this.add(Bilanz, BorderLayout.WEST);
//		this.add(GuV, BorderLayout.EAST);
		this.add(guVPanel);
		this.add(balancePanel);
		
		//DecimalFormat format = new DecimalFormat("###,###,###.##");
		//NumberFormat format = DecimalFormat.getInstance(Locale.GERMAN);
		balancePanel.addEntry(new TAccountEntry("Maschinen", balance.machineValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Rohstoffe", balance.ressourceValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Produkte", balance.finishedProductsValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Bank", balance.bank), TAccountSides.left);
		
		balancePanel.addEntry(new TAccountEntry("Eigenkapital", balance.equity), TAccountSides.right);
		balancePanel.addEntry(new TAccountEntry("Kredit", balance.credit), TAccountSides.right);
		
		guVPanel.addEntry(new TAccountEntry("Rohstoffaufwand", guv.ressourceCost), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Löhne/Gehälter", guv.wages), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Afa auf SA", guv.deprecation), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Miete", guv.rental), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Zinsaufwand", guv.interest), TAccountSides.left);

		guVPanel.addEntry(new TAccountEntry("Umsatzerlöse", guv.sales), TAccountSides.right);
		
		guVPanel.addEntry(new TAccountEntry("Gewinn", guv.profit), TAccountSides.right);
	}
	
//	public void addBilanzEntry(String name, String value, boolean side) {
//		Bilanz.addEntry(new TAccountEntry(name, value, side));
//	}
//	
//	public void addGuvEntry(String name, String value, boolean side) {
//		GuV.addEntry(new TAccountEntry(name, value, side));
//	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Balance balance = new Balance();
		balance.machineValue = 		  254312131.779d;
		balance.ressourceValue =		 153512.252d;
		balance.finishedProductsValue =  263242.362d;
		balance.bank = 				     475152.268d;
		
		balance.credit = 			   151324252.27d;
		balance.calculateEquity();
		
		GuV guv = new GuV();
		guv.ressourceCost = 		   52463524.252d;
		guv.wages = 				   96342315.643d;
		guv.deprecation = 			   36235123.433d; 
		guv.rental = 				      50000.000d;
		guv.interest = 					 743513.256d;
		guv.sales = 				  325648144.787d;
		
		guv.calculateResult();
		
		ReportingPanel repPanel = new ReportingPanel(balance, guv);
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
