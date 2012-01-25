package Client.Presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import Client.Application.CompanyController;
import Client.Entities.Company;
import Client.Entities.Employee;
import Client.Entities.EmployeeType;
import Client.Entities.Machine;
import Client.Entities.MachineType;
import Client.Entities.PeriodInfo;
import Client.Entities.Ressource;
import Client.Entities.RessourceType;

public class ProductionAndDistributionPanel extends JPanel {
	private class CapacityItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			actualCapacity = Integer.parseInt((String) cb_capacities.getSelectedItem());
			refreshCount();
		}

	}


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
	
	private int actualCapacity = 1;
	private int unitsToProduce;
	private double priceToSell;
	private int amountToSell;
	
	private final double PRICEMIN = 5.00;
	private final double PRICEMAX = 20.00;
	
	private Company comp = Company.getInstance();
	
	
	
	private int maxProducableUnits;
	private int maxSellableUnits;

	private JTextArea tf_missingRessources;

	private JTextArea tf_missingMachines;

	private JTextArea tf_missingEmployee;

	private JComboBox cb_capacities;

	private JLabel l_priceRating;
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
		
		c.gridx++;
		l_priceRating = new JLabel();
		this.add(l_priceRating, c);
		
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
		
		c.anchor = GridBagConstraints.NORTHWEST;
		//Neue Zeile
		c.gridheight = RessourceType.values().length;
		this.add(new JLabel("Fehlende Ressourcen : "),c);
		c.gridx++;
		
		
		tf_missingRessources =new JTextArea(getMissingRessources());
		tf_missingRessources.setEditable(false);
		this.add(tf_missingRessources,c);
		c.gridx=0;
		c.gridy += c.gridheight;
		
		//Neue Zeile
		c.gridheight = MachineType.values().length;
		this.add(new JLabel("Fehlende Maschinen"),c);
		c.gridx++;
		
		
		tf_missingMachines =new JTextArea(getMissingMachines());
		tf_missingMachines.setEditable(false);
		this.add(tf_missingMachines,c);
		
		c.gridx++;
		cb_capacities = new JComboBox();
		for(String capa: Machine.capacites){
			cb_capacities.addItem(capa);
		}
		cb_capacities.addItemListener(new CapacityItemListener());
		this.add(new JLabel("Maschinenkapazität: "),c);
		c.gridx++;
		this.add(cb_capacities,c);
		cb_capacities.setEditable(false);
		actualCapacity = Integer.parseInt((String) cb_capacities.getSelectedItem());
		
		c.gridx=0;
		c.gridy += c.gridheight;
		
		//Neue Zeile
		c.gridheight = MachineType.values().length;
		this.add(new JLabel("Fehlende Mitarbeiter : "),c);
		c.gridx++;
		
		
		tf_missingEmployee =new JTextArea(getMissingEmployee());
		tf_missingEmployee.setEditable(false);
		this.add(tf_missingEmployee,c);
		c.gridx=0;
		c.gridy += c.gridheight;
		c.gridheight = 1;
		c.gridy++;

		
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


	private String getMissingEmployee() {
		String missEmpl = "";
		
		for(EmployeeType type: EmployeeType.values()){
			missEmpl += type.name() + ": " + CompanyController.missingEmployees(type) + "\n";	
		}

		
		if(missEmpl.length() > 0)
			return missEmpl.substring(0, missEmpl.length() - 1);
		else
			return missEmpl;
	}


	private String getMissingMachines() {
		String missMach = "";
		for(MachineType type : MachineType.values()){
		missMach += type.name() + ": " + CompanyController.missingMachines(type,actualCapacity) + "\n";
		}
		if(missMach.length() > 0)
			return missMach.substring(0, missMach.length() - 1);
		else
		return missMach;
	}


	private String getMissingRessources() {
		String missRes = "";
		for(RessourceType type : RessourceType.values()){
			missRes += type.name() + ": " + CompanyController.missingRessources(type) + " " + Ressource.getUnit(type) + "\n";
		}
		if(missRes.length() > 0)
			return missRes.substring(0, missRes.length() - 1);
		else
			return missRes;
	}


	/*public void update(){
		
		l_maxProducableUnits.setText(String.valueOf(comp.getProduction().getMaxProducableUnits()) + " Einheiten");
				
		if(PeriodInfo.getNumberOfActPeriod() == 0)
			l_lastPeriodSellingPrice.setText("Bisher keine Produkte verkauft");
		else
			l_lastPeriodSellingPrice.setText(String.valueOf(PeriodInfo.getActualPeriod().getProductPrice()) + "€");
		
		l_finishedProducts.setText(String.valueOf(String.valueOf(comp.getFinishedProducts()) + " Einheiten"));
		
		for(Iterator<Entry<RessourceType, Integer>> it = CompanyController.missingRessources().entrySet().iterator(); it.hasNext();){
			Entry<RessourceType, Integer> next = it.next();
			missRes += next.getKey().name() + ": " + next.getValue() + " " + Ressource.getUnit(next.getKey()) + "; ";
		}
		return missRes;
	}*/
	
	private void refreshCount() {
		pricesell.setText(cutAndTrim(pricesell.getText()));
		try{unitsToProduce = Integer.parseInt(amountproduce.getText().trim());}catch(NumberFormatException e){}
		try{priceToSell = Double.parseDouble(pricesell.getText());}catch(NumberFormatException e){}
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
		
		
		tf_missingRessources.setText(getMissingRessources());
		tf_missingMachines.setText(getMissingMachines());
		tf_missingEmployee.setText(getMissingEmployee());
		
		if(priceToSell>PRICEMAX){
			l_priceRating.setText("Preis ist zu hoch");
			l_priceRating.setForeground(Color.RED);
		}else if(priceToSell<PRICEMIN){
			l_priceRating.setText("Preis ist zu niedrig");
			l_priceRating.setForeground(Color.BLUE);
		}else{
			l_priceRating.setText("Preis liegt im Rahmen");
			l_priceRating.setForeground(Color.decode("418831"));
		}
	}

	private String cutAndTrim(String text) {
		return text.replace(',', '.').trim();
	}


}
