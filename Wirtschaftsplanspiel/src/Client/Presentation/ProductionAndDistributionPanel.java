package Client.Presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Client.Entities.Company;

public class ProductionAndDistributionPanel extends JPanel {
	private class TextListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {			
		

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			try
			{
				 JTextField c = (JTextField) arg0.getComponent();
				 double value = Integer.parseInt(c.getText());
					if(c == amountproduce){
						prodUnits.setValue((int) value);
					}else if(c == pricesell){
						sellPrice.setValue((int) (value * 100));
					}else if(c == amountsell){
						sellUnits.setValue((int) value);
					}
					refreshCount();
			}
			catch(NumberFormatException ex1)
			{
				JTextField c = (JTextField) arg0.getComponent();
				c.setText(String.valueOf(0));
				return;
			}
			catch(ClassCastException ex2)
			{
				return;
			}
		}

	}

	private static final int MAXIMUM = 9000;

	private class SliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			refreshCount();
		}

	}

	private JSlider prodUnits;
	private JSlider sellUnits;
	private JSlider sellPrice;
	private SliderListener sl = new SliderListener();
	private TextListener tl = new TextListener();
	private JTextField amountproduce;
	private JTextField pricesell;
	private JTextField amountsell;
	
	private int unitsToProduce;
	private double priceToSell;
	private int amountToSell;
	
	
	private int maxProducableUnits;
	private int maxSellableUnits;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2375380045275272220L;

	public ProductionAndDistributionPanel(){
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.ipady = 30;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		this.setLayout(new GridBagLayout());
		//SellingScreen
		JLabel title1 = new JLabel("Absatzplanung");
		title1.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title1,c);
		c.ipady = 1;
		c.gridx = 0;
		c.gridy++;
		
		//Verkaufsmenge
		this.add(new JLabel("Geplante Verkaufsmenge: "),c);
		c.gridx++;
		sellUnits = new JSlider();
		sellUnits.setValue(0);
		sellUnits.setMaximum(MAXIMUM);
		sellUnits.addChangeListener(sl);
		this.add(sellUnits,c);
		c.gridx++;
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		amountsell = new JTextField();
		amountsell.setSize(10, 10);
		amountsell.addKeyListener(tl);
		this.add(amountsell, c);  
		c.weightx = 0.1;
		
		//Verkaufspreis
		c.gridy++;
		c.gridx = 0;
		this.add(new JLabel("Geplanter Verkaufspreis: "),c);
	
		c.gridx++;
		sellPrice = new JSlider();
		sellPrice.setValue(0);
		sellPrice.setExtent(100);
		sellPrice.setMajorTickSpacing(100);		
		sellPrice.setMaximum(MAXIMUM);
		sellPrice.addChangeListener(sl);
		
		c.gridx++;
		c.weightx = 0.6;
		pricesell = new JTextField();
		pricesell.addKeyListener(tl);
		this.add(pricesell, c);
		c.weightx = 0.1;
		
		//ProductionScreen
		c.gridy++;
		c.ipady = 30;
		c.gridx = 1;
		JLabel title2 = new JLabel("Produktionsplanung");
		title2.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title2,c);
		c.ipady = 1;
		
		//Verkaufspreis
		c.gridy++;
		c.gridx = 0;
		this.add(new JLabel("Geplante Produktionsmenge: "),c);
		c.gridx++;
		prodUnits = new JSlider();
		prodUnits.setValue(0);
		prodUnits.setMaximum(MAXIMUM);
		prodUnits.addChangeListener(sl);
		this.add(prodUnits,c);
		c.gridx++;
		
		c.weightx = 0.6;
		amountproduce = new JTextField();
		amountproduce.setSize(100, 500);
		amountproduce.addKeyListener(tl);
		this.add(amountproduce, c);  
		c.weightx = 0.1;
		
		
		maxProducableUnits = Company.getInstance().getProduction().getMaxProducableUnits();
		maxSellableUnits = Company.getInstance().getFinishedProducts();
		refreshCount();
		
		
	}

	private void refreshCount() {
		try
		{
			unitsToProduce = Integer.parseInt(amountproduce.getText());
		}
		catch(NumberFormatException ex)
		{
			unitsToProduce = 0;
		}
		Company.getInstance().getProduction().setUnitsToProduce(unitsToProduce);
		amountsell.setText(String.valueOf(sellUnits.getValue())); 
		amountproduce.setText(String.valueOf(prodUnits.getValue())); 
		pricesell.setText(String.valueOf((sellPrice.getValue() / 100)));
		
		if(sellUnits.getValue() > maxSellableUnits)
			amountsell.setForeground(Color.RED);
		else
			amountsell.setForeground(Color.BLACK);
		
		if(prodUnits.getValue() > maxProducableUnits)
			amountproduce.setForeground(Color.RED);
		else
			amountproduce.setForeground(Color.BLACK);
		
	}
}
