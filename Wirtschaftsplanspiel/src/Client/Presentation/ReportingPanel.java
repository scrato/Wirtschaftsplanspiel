package Client.Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Client.Entities.Period;
import Client.Entities.PeriodInfo;
import Client.Entities.Player;
import Client.Entities.ProfitAndLoss;
import Client.Entities.Balance;
import common.entities.CompanyResult;

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
		
		public String getName() {
			return name;
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
	
	private int period = 0;
	
	private JPanel bodyPanel;
	private JPanel navigationPanel;
	
	//private JPanel resultPanel;
	JTable resultTable;
	private JScrollPane resultScrollPane;
	//private JPanel resultPanel;
	
	private JButton nextPeriodButton;
	private JButton prevPeriodButton;
	
	private TAccount balancePanel;
	private TAccount guVPanel;
	
	public ReportingPanel() {
		super(PanelType.Reporting);
		
		this.setLayout(new BorderLayout());
		
		prevPeriodButton = new JButton("<<");
		prevPeriodButton.addActionListener(new PrevPeriodListener(this));
		nextPeriodButton = new JButton(">>");
		nextPeriodButton.addActionListener(new NextPeriodListener(this));
		
	}
	
	public void setPeriod(int Period) {
		this.period = Period;
		this.refreshPanel();
	}
	
	@Override
	public void refreshPanel() {
		this.removeAll();
	
		bodyPanel = new JPanel();
		GridBagLayout gbLay = new GridBagLayout();
		bodyPanel.setLayout(gbLay);
		GridBagConstraints c = new GridBagConstraints();		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		//create navigation panel
		
		navigationPanel = new JPanel(new GridLayout(3, 9));
		for (int o = 0; o<9; o++) {
			navigationPanel.add(new JPanel());
		}
		//JLabel reportingLabel = new JLabel("Berichterstattung");
		//reportingLabel.setFont(reportingLabel.getFont().deriveFont(Font.BOLD));
		//navigationPanel.add(reportingLabel);
		navigationPanel.add(new JPanel());
		navigationPanel.add(new JPanel());
		navigationPanel.add(new JPanel());
		if (period == 0) {
			prevPeriodButton.setEnabled(false);
		}
		else {
			prevPeriodButton.setEnabled(true);
		}
		navigationPanel.add(prevPeriodButton);
		JLabel perLabel = new JLabel("  Periode " + period);
		navigationPanel.add(perLabel);
		if (period > PeriodInfo.getActualPeriodNumber() - 2) {
			nextPeriodButton.setEnabled(false);
		}
		else {
			nextPeriodButton.setEnabled(true);
		}
		navigationPanel.add(nextPeriodButton);
		navigationPanel.add(new JPanel());
		navigationPanel.add(new JPanel());
		navigationPanel.add(new JPanel());
	
		for (int o = 0; o<9; o++) {
			navigationPanel.add(new JPanel());
		}
		
		this.add(navigationPanel, BorderLayout.NORTH);
		
		//fill ResultList with Data.
		String[][] tableValues = new String[Player.getPlayers().size()][4];
		int i = 0;
		for (Player player : Player.getPlayers()) {
			try{
				CompanyResult result = player.getCompanyResult(period);
				tableValues[i][0] = player.getName();
				tableValues[i][1] = getValueString(result.sales);
				tableValues[i][2] = getValueString(result.profit);
				tableValues[i][3] = getValueString(result.marketShare);
				i++;
			} catch (Exception e) {
				//do nothing.
			}
		}
		if (i == 0) {
			tableValues[0][0] = "Keine Daten verfügbar.";
		}
		
		String[] columnNames = { "Spieler", "Umsatz", "Gewinn", "Marktanteil" };
		
		DefaultTableModel tabModel = new DefaultTableModel(tableValues, columnNames);
		
		resultTable = new JTable(tabModel);
		resultTable.setEnabled(false);
		resultScrollPane = new JScrollPane(resultTable);
		
		//fill TAccounts with Data.
		balancePanel = new TAccount("Bilanz");
		guVPanel = new TAccount("Gewinn und Verlust");
		
		Period reportingPeriod = null;
		try {
			reportingPeriod = PeriodInfo.getPeriod(period); //PeriodInfo.getActualPeriodNumber() - 1);
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

		
		
		//Add panels to bodyPanel
		
		/*
		//c.anchor = GridBagConstraints.LINE_END;
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 10;
		c.gridheight = 5;
		//c.insets = new Insets(400,400,400,400);
		//resultScrollPane.setPreferredSize(new Dimension(600, 400));
		bodyPanel.add(resultScrollPane, c);	
		//bodyPanel.add(resultPanel);
		//c.insets = new Insets(0,0,0,0);
		 * 
		 */
		
		//resultScrollPane.set
		JPanel pufferLeft = new JPanel();
		pufferLeft.setSize(15, 10);
		this.add(pufferLeft, BorderLayout.WEST);
		JPanel pufferRight = new JPanel();
		pufferRight.setSize(15, 10);
		this.add(pufferRight, BorderLayout.EAST);
		
		this.add(resultScrollPane, BorderLayout.CENTER);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 8;
		c.gridheight = 4;
		c.insets = new Insets(15,0,0,0);
		bodyPanel.add(balancePanel, c);

		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 8;
		c.gridheight = 6;
		c.insets = new Insets(0,0,20,0);
		bodyPanel.add(guVPanel, c);
		
		this.add(bodyPanel, BorderLayout.SOUTH);
	}
	
	public class PrevPeriodListener implements ActionListener {
		public PrevPeriodListener(ReportingPanel panel) {
			repPanel = panel;
		}

		private ReportingPanel repPanel;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			repPanel.period--;
			repPanel.refreshPanel();
			repPanel.repaint();
			repPanel.validate();
		}
	}
	
	public class NextPeriodListener implements ActionListener {
		public NextPeriodListener(ReportingPanel panel) {
			repPanel = panel;
		}

		private ReportingPanel repPanel;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			repPanel.period++;
			repPanel.refreshPanel();
			repPanel.repaint();
			repPanel.validate();
		}
		
	}

}
