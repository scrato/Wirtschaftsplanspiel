package Client.Presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import Client.Entities.Company;
import Client.Entities.PeriodInfo;

public class ProductionAndDistributionPanel extends JPanel {
	private class TextListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {			
		

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			try
			{
					refreshCount();
			}
			catch(NumberFormatException ex1)
			{
				JTextPane c = (JTextPane) arg0.getComponent();
				c.setText(String.valueOf(0));
				return;
			}
			catch(ClassCastException ex2)
			{
				return;
			}

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}

	}

	private static final int MAXIMUM = 9000;

	private TextListener tl = new TextListener();
	private JTextPane amountproduce;
	private JTextPane pricesell;
	private JTextPane amountsell;
	
	private JLabel l_finishedProducts;
	private JLabel l_lastPeriodSellingPrice;
	private JLabel l_maxProducableUnits;
	
	private int unitsToProduce;
	private double priceToSell;
	private int amountToSell;
	
	private Company comp = Company.getInstance();
	
	
	
	private int maxProducableUnits;
	private int maxSellableUnits;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2375380045275272220L;

	public ProductionAndDistributionPanel(){
		
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 30;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		this.setLayout(new GridBagLayout());
		
		//SellingScreen
		JLabel title1 = new JLabel("Absatzplanung");
		title1.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title1,c);
		c.ipady = 1;
		c.gridy++;
		
		//Verkaufsmenge
		this.add(new JLabel("Geplante Verkaufsmenge: "),c);
		
		c.gridx++;
		c.fill = GridBagConstraints.BOTH;
		amountsell = new JTextPane();
		amountsell.addKeyListener(tl);
		this.add(amountsell, c);  
		c.gridy++;
		c.gridx = 0;
		
		//Verkaufspreis
		this.add(new JLabel("Geplanter Verkaufspreis: "),c);
		c.gridx++;
		pricesell = new JTextPane();
		pricesell.addKeyListener(tl);
		this.add(pricesell, c);
		c.gridx = 0;		
		c.gridy++;
		
		//ProductionScreen

		c.ipady = 30;
		JLabel title2 = new JLabel("Produktionsplanung");
		title2.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title2,c);
		c.ipady = 1;
		c.gridy++;
		
		//Verkaufspreis
		this.add(new JLabel("Geplante Produktionsmenge: "),c);
		c.gridx++;		
		amountproduce = new JTextPane();
		amountproduce.setSize(100, 500);
		amountproduce.addKeyListener(tl);
		this.add(amountproduce, c);  
		c.gridx = 0;
		c.gridy++;
		
		
		
		
		
		
		maxProducableUnits = comp.getProdAndDistr().getMaxProducableUnits();
		maxSellableUnits = comp.getFinishedProducts();
		

		
		c.ipady = 30;
		JLabel title3 = new JLabel("Informationen");
		title3.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title3,c);
		c.ipady = 1;
		c.gridy++;
		//Neue Zeile
		this.add(new JLabel("Fertigprodukte auf Lager: "),c);
		c.gridx++;
		l_finishedProducts = new JLabel(String.valueOf(comp.getFinishedProducts()) + " Einheiten");
		this.add(l_finishedProducts ,c);
		c.gridx=0;
		c.gridy++;

		//Neue Zeile
		this.add(new JLabel("Verkaufspreis der letzen Periode: "),c);
		c.gridx++;
		if(PeriodInfo.getNumberOfActPeriod() == 0)
			l_lastPeriodSellingPrice = new JLabel("Bisher keine Produkte verkauft");
		else
			l_lastPeriodSellingPrice = new JLabel(String.valueOf(PeriodInfo.getActualPeriod().getProductPrice()) + "€");
		
		this.add(l_lastPeriodSellingPrice , c);
		c.gridx=0;
		c.gridy++;

		//Neue Zeile
		this.add(new JLabel("Aktuell produzierbare Fertigprodukte : "),c);
		c.gridx++;
		l_maxProducableUnits =new JLabel(String.valueOf(comp.getProdAndDistr().getMaxProducableUnits()) + " Einheiten");
		this.add(l_maxProducableUnits,c);
		c.gridx=0;
		c.gridy++;
	
	
		
		//Startwerte gleich dem vorher gespeicherten
		
		amountsell.setText(String.valueOf(comp.getProdAndDistr().getUnitsToSell()));
		amountproduce.setText(String.valueOf(comp.getProdAndDistr().getUnitsToProduce()));
		pricesell.setText(String.valueOf(comp.getProdAndDistr().getSellingPrice()));
		refreshCount();
	}

	/*public void update(){
		
		l_maxProducableUnits.setText(String.valueOf(comp.getProduction().getMaxProducableUnits()) + " Einheiten");
				
		if(PeriodInfo.getNumberOfActPeriod() == 0)
			l_lastPeriodSellingPrice.setText("Bisher keine Produkte verkauft");
		else
			l_lastPeriodSellingPrice.setText(String.valueOf(PeriodInfo.getActualPeriod().getProductPrice()) + "€");
		
		l_finishedProducts.setText(String.valueOf(String.valueOf(comp.getFinishedProducts()) + " Einheiten"));
	}*/
	
	private void refreshCount() {
		try{unitsToProduce = Integer.parseInt(amountproduce.getText().trim());}catch(NumberFormatException e){}
		try{priceToSell = Double.parseDouble(pricesell.getText().trim());}catch(NumberFormatException e){}
		try{amountToSell = Integer.parseInt(amountsell.getText().trim());}catch(NumberFormatException e){}

		comp.getProdAndDistr().setUnitsToProduce(unitsToProduce);
		comp.getProdAndDistr().setUnitsToSell(amountToSell);
		comp.getProdAndDistr().setSellingPrice(priceToSell);
		
		if(amountToSell > (maxSellableUnits + unitsToProduce))
			amountsell.setForeground(Color.RED);
		else
			amountsell.setForeground(Color.BLACK);
		
		if(unitsToProduce > maxProducableUnits)
			amountproduce.setForeground(Color.RED);
		else
			amountproduce.setForeground(Color.BLACK);
		
		
	}
}
