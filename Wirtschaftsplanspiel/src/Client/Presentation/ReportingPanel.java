package Client.Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Entities.Period;
import Client.Entities.PeriodInfo;
import Client.Entities.ProfitAndLoss;
import Client.Entities.Balance;

public class ReportingPanel extends TypedPanel {
	
	public class TAccountEntry extends JPanel {	

		private static final long serialVersionUID = 1L;
		
		public String entryName;
		public String entryValue;
		
		public TAccountEntry(String Name, String Value) {
			this (Name, Value, Color.black);
		}
		
		public TAccountEntry(String Name, double Value) {			
			this(Name, getValueString(Value));
		}
		
		public TAccountEntry(String Name, double Value, Color color) {			
			this(Name, getValueString(Value), color);
		}
		
		public TAccountEntry(String Name, String Value, Color color) {
			entryName = Name;
			entryValue = Value;
			
			this.setLayout(new GridLayout(0, 2));
			
			JLabel nameLabel = new JLabel(" " + Name, JLabel.LEFT);
			nameLabel.setForeground(color);
			
			JLabel valueLabel = new JLabel(Value + " ", JLabel.RIGHT);
			valueLabel.setForeground(color);
			
			this.add(nameLabel);
			this.add(valueLabel);
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
		JPanel lastItemPanel;
		JPanel sumPanel;
		
		public TAccountSide() {
			this.setLayout(new GridLayout(0, 1));
		}
		
		public void addEntry(TAccountEntry entry) {
			this.add(entry);			
			itemCounter ++;
			lastItemPanel = entry;
		}
		
		public void addSum(double Value) {
			sumPanel = new JPanel(new GridLayout(1,2));
			sumPanel.add(new JPanel());
			sumPanel.add(new JLabel(getValueString(Value) + " ", JLabel.RIGHT));
			this.add(sumPanel);
		}
//		
		public int getItemCounter() {
			return itemCounter;
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			Graphics2D g2 = (Graphics2D) g;
			RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					                     RenderingHints.VALUE_ANTIALIAS_ON);
			renderHints.put(RenderingHints.KEY_RENDERING,
					        RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHints(renderHints);
			
			int x1, x2, y1, y2 = 0;
			
			//Buchhalternasen
			x1 = sumPanel.getX();
			x2 = x1 + (int) (sumPanel.getWidth() / 3);
			y1 = y2 = sumPanel.getY() - 1;
					
			g2.drawLine(x1, y1, x2, y2);	
			
			x1 = (int) (sumPanel.getX() + sumPanel.getWidth() / 3);
			x2 = (int) (sumPanel.getX() + 2 * sumPanel.getWidth() / 3);
			y1 = sumPanel.getY() - 1;
			y2 = lastItemPanel.getY() + lastItemPanel.getHeight() - 1;
			
			g2.drawLine(x1, y1, x2, y2);	
			
			x1 = (int) (sumPanel.getX() + 2 * sumPanel.getWidth() / 3);
			x2 = (int) (sumPanel.getX() + sumPanel.getWidth());
			y1 = lastItemPanel.getY() + lastItemPanel.getHeight() - 1;
			
			g2.drawLine(x1, y1, x2, y2);	
			
//			//Unterstrich			
			x1 = sumPanel.getX();
			x2 = x1 + sumPanel.getWidth();
			y1 = y2 = sumPanel.getY() + 1;
			
			g2.drawLine(x1, y1, x2, y2);	
			
			//Unterstrich unter Summe.
			x1 = sumPanel.getX();
			x2 = x1 + sumPanel.getWidth();
			y1 = y2 = sumPanel.getY() + sumPanel.getHeight() -1;
			
			g2.drawLine(x1, y1, x2, y2);	
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
			headerPanel.setMaximumSize(new Dimension(500, 25));
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
		
		public void addSum(double sum) {
			if (leftSide.getItemCounter() == rightSide.getItemCounter()) {
				leftSide.addSum(sum);
				rightSide.addSum(sum);
			} else {
				if (leftSide.getItemCounter() >= rightSide.getItemCounter()) {
					leftSide.addSum(sum);
					for (int i = 0; i < (leftSide.getItemCounter() - rightSide.getItemCounter()); i++) {
						rightSide.add(new JPanel());
					}
					rightSide.addSum(sum);
				} else {
					rightSide.addSum(sum);
					for (int i = 0; i < (rightSide.getItemCounter() - leftSide.getItemCounter()); i++) {
						leftSide.add(new JPanel());
					}
					leftSide.addSum(sum);
				}
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

			g.drawLine(x1, y1, x2, y2);
			x1 = x2 = bodyPanel.getX() + leftSide.getWidth();
			y1 = bodyPanel.getY();
			y2 = bodyPanel.getY() + leftSide.getHeight();
			
			g.drawLine(x1, y1, x2, y2);
		}	
	
	}

	private static final long serialVersionUID = 1L;
	private TAccount balancePanel;
	private TAccount guVPanel;
	
	public ReportingPanel() {
		super(PanelType.Reporting);
		//super(new GridLayout(2,1));
		
		//this.setMaximumSize(new Dimension(500, 300));
		
//		Balance balance = new Balance();
//		balance.machineValue = 		  254312131.779d;
//		balance.ressourceValue =		 153512.252d;
//		balance.finishedProductsValue =  263242.362d;
//		balance.bank = 				     475152.268d;
//		
//		balance.credit = 			   151324252.27d;
//		balance.calculateEquity();
//		
//		ProfitAndLoss guv = new ProfitAndLoss();
//		guv.ressourceCost = 		   52463524.252d;
//		guv.wages = 				   96342315.643d;
//		guv.deprecation = 			   36235123.433d; 
//		guv.rental = 				      50000.000d;
//		guv.warehouseCosts =			  15226.251d;
//		guv.changeInStockExpenditures =		  0.0d;
//		guv.lossDueDisposalOfAssets =     50000.0d;
//		guv.employeeDismissalCosts = 		  0.0d;
//		guv.employeeHiringCosts = 		   1000.0d;
//		guv.interest = 					 743513.256d;
//		
//		guv.sales = 				    5648144.787d;
//		guv.changeInStockEarning = 		 155126.211d;
//		
//		guv.calculateResult();
//		
		
		Period reportingPeriod = null;
		try {
			reportingPeriod = PeriodInfo.getPeriod(PeriodInfo.getActualPeriodNumber() - 1);
		} catch (Exception e) {
			//do nothing.
		}
		ProfitAndLoss guv;
		Balance balance;
		if (reportingPeriod != null && reportingPeriod.getGuV() != null && reportingPeriod.getBalance() != null) {
			guv = reportingPeriod.getGuV();
			balance = reportingPeriod.getBalance();
		} else {
			guv = new ProfitAndLoss();
			balance = new Balance();
		}
	
		
		balancePanel = new TAccount("Bilanz");
		guVPanel = new TAccount("Gewinn und Verlust");
		
		this.add(balancePanel);
		this.add(guVPanel);
		
		//DecimalFormat format = new DecimalFormat("###,###,###.##");
		//NumberFormat format = DecimalFormat.getInstance(Locale.GERMAN);
		balancePanel.addEntry(new TAccountEntry("Maschinen", balance.machineValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Rohstoffe", balance.ressourceValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Produkte", balance.finishedProductsValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Bank", balance.bank), TAccountSides.left);
		
		balancePanel.addEntry(new TAccountEntry("Eigenkapital", balance.equity), TAccountSides.right);
		balancePanel.addEntry(new TAccountEntry("Kredit", balance.credit), TAccountSides.right);
		
		balancePanel.addSum(balance.getTAccountSum());
		
		guVPanel.addEntry(new TAccountEntry("Rohstoffaufwand", guv.ressourceCost), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Löhne/Gehälter", guv.wages), TAccountSides.left);
		if (guv.employeeHiringCosts != 0) 
			guVPanel.addEntry(new TAccountEntry("Aufwand für Einstellungen", guv.employeeHiringCosts), TAccountSides.left);
		if (guv.employeeDismissalCosts != 0) 
			guVPanel.addEntry(new TAccountEntry("Aufwand für Entlassungen", guv.employeeDismissalCosts), TAccountSides.left);
		
		guVPanel.addEntry(new TAccountEntry("Abschreibungen", guv.deprecation), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Miete", guv.rental), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Lageraufwand", guv.warehouseCosts), TAccountSides.left);
		if (guv.changeInStockExpenditures != 0) 
			guVPanel.addEntry(new TAccountEntry("Minderbestand", guv.changeInStockExpenditures), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Zinsaufwand", guv.interest), TAccountSides.left);

		guVPanel.addEntry(new TAccountEntry("Umsatzerlöse", guv.sales), TAccountSides.right);
		if (guv.changeInStockEarning != 0) 
			guVPanel.addEntry(new TAccountEntry("Mehrbestand", guv.changeInStockEarning), TAccountSides.right);
	
		
		if (guv.profit >= 0) {
			guVPanel.addEntry(new TAccountEntry("Gewinn", guv.profit, Color.green), TAccountSides.left);
		} else {
			guVPanel.addEntry(new TAccountEntry("Verlust", -guv.profit, Color.red), TAccountSides.right);
		}
		
		guVPanel.addSum(guv.getTAccountSum());
		
		
		
	}
	
//	public void addBilanzEntry(String name, String value, boolean side) {
//		Bilanz.addEntry(new TAccountEntry(name, value, side));
//	}
//	
//	public void addGuvEntry(String name, String value, boolean side) {
//		ProfitAndLoss.addEntry(new TAccountEntry(name, value, side));
//	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		ReportingPanel repPanel = new ReportingPanel();
		frame.add(repPanel);
		
		frame.setVisible(true);
	}

	@Override
	public void refreshPanel() {
		this.removeAll();
	
		
		Period reportingPeriod = null;
		try {
			reportingPeriod = PeriodInfo.getPeriod(PeriodInfo.getActualPeriodNumber() - 1);
		} catch (Exception e) {
			//do nothing.
		}
		ProfitAndLoss guv;
		Balance balance;
		if (reportingPeriod != null && reportingPeriod.getGuV() != null && reportingPeriod.getBalance() != null) {
			guv = reportingPeriod.getGuV();
			balance = reportingPeriod.getBalance();
		} else {
			guv = new ProfitAndLoss();
			balance = new Balance();
		}
	
		
		balancePanel = new TAccount("Bilanz");
		guVPanel = new TAccount("Gewinn und Verlust");
		
		this.add(balancePanel);
		this.add(guVPanel);
		
		//DecimalFormat format = new DecimalFormat("###,###,###.##");
		//NumberFormat format = DecimalFormat.getInstance(Locale.GERMAN);
		balancePanel.addEntry(new TAccountEntry("Maschinen", balance.machineValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Rohstoffe", balance.ressourceValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Produkte", balance.finishedProductsValue), TAccountSides.left);
		balancePanel.addEntry(new TAccountEntry("Bank", balance.bank), TAccountSides.left);
		
		balancePanel.addEntry(new TAccountEntry("Eigenkapital", balance.equity), TAccountSides.right);
		balancePanel.addEntry(new TAccountEntry("Kredit", balance.credit), TAccountSides.right);
		
		balancePanel.addSum(balance.getTAccountSum());
		
		guVPanel.addEntry(new TAccountEntry("Rohstoffaufwand", guv.ressourceCost), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Löhne/Gehälter", guv.wages), TAccountSides.left);
		if (guv.employeeHiringCosts != 0) 
			guVPanel.addEntry(new TAccountEntry("Aufwand für Einstellungen", guv.employeeHiringCosts), TAccountSides.left);
		if (guv.employeeDismissalCosts != 0) 
			guVPanel.addEntry(new TAccountEntry("Aufwand für Entlassungen", guv.employeeDismissalCosts), TAccountSides.left);
		
		guVPanel.addEntry(new TAccountEntry("Abschreibungen", guv.deprecation), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Verl. aus AV-Abgang", guv.lossDueDisposalOfAssets), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Miete", guv.rental), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Lageraufwand", guv.warehouseCosts), TAccountSides.left);
		if (guv.changeInStockExpenditures != 0) 
			guVPanel.addEntry(new TAccountEntry("Minderbestand", guv.changeInStockExpenditures), TAccountSides.left);
		guVPanel.addEntry(new TAccountEntry("Zinsaufwand", guv.interest), TAccountSides.left);

		guVPanel.addEntry(new TAccountEntry("Umsatzerlöse", guv.sales), TAccountSides.right);
		if (guv.changeInStockEarning != 0) 
			guVPanel.addEntry(new TAccountEntry("Mehrbestand", guv.changeInStockEarning), TAccountSides.right);
	
		
		if (guv.profit >= 0) {
			guVPanel.addEntry(new TAccountEntry("Gewinn", guv.profit, Color.green), TAccountSides.left);
		} else {
			guVPanel.addEntry(new TAccountEntry("Verlust", -guv.profit, Color.red), TAccountSides.right);
		}
		
		guVPanel.addSum(guv.getTAccountSum());
	}

}
