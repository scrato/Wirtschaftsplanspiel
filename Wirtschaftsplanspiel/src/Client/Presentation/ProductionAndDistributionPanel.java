package Client.Presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Currency;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;
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

/**
 * Die Übersicht der geplanten Produktion und des geplanten Absatzes
 * @author Scrato
 *
 */
public class ProductionAndDistributionPanel extends TypedPanel {
	
	

	/**
	 * Aktualisiert die Daten nach dem Verändern der Produktions/Preisdaten
	 * @author Scrato
	 *
	 */
	private class TextListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {			
		

		}

		@Override
		public void keyReleased(KeyEvent arg0) {			
			//Wenn es eine Zahl ist
			if ((arg0.getKeyCode() >= KeyEvent.VK_0 && arg0.getKeyCode() <= KeyEvent.VK_9) || 
					(arg0.getKeyCode() >= KeyEvent.VK_NUMPAD0 && arg0.getKeyCode() <= KeyEvent.VK_NUMPAD9)	||
					(arg0.getKeyCode() == KeyEvent.VK_SPACE)){
			
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

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}

	}
	private TextListener tl = new TextListener();
	private JTextField amountproduce;
	private JTextField pricesell;
	private JTextField amountsell;
	
	private JLabel l_finishedProducts;
	private JLabel l_lastPeriodSellingPrice;
	private JLabel l_maxProducableUnits;
	
	private int unitsToProduce;
	private double priceToSell;
	private int amountToSell;
	
	private Company comp = Company.getInstance();
	
	
	
	private int maxProducableUnits;
	private int maxSellableUnits;


	private JLabel l_missingRessource1;

	private JLabel l_missingEmployee0;

	private JLabel l_missingRessource0;

	private JLabel l_missingEmployee1;
	
	private Insets inset_standard = new Insets(0,0,0,0);
	private Insets inset_newLine = new Insets(15,0,5,0);
	
	private DecimalFormat decformat;
	private DecimalFormat curformat;
	private JLabel l_standardPrice;
	private JLabel l_standardNeedPerPlayer;
	private JLabel l_missingRessourceCapital1;
	private JLabel l_missingRessourceCapital0;
	private JLabel l_missingEmployeeCapital0;
	private JLabel l_missingEmployeeCapital1;
	private JLabel l_missingMachine0;
	private JLabel l_missingMachineCapital0;
	private JLabel l_missingMachine1;
	private JLabel l_missingMachineCapital1;
	private JLabel l_sum;
	private JLabel l_lastPeriodSellingAmount;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2375380045275272220L;

	public ProductionAndDistributionPanel(){
		super(PanelType.ProdAndDistr);
		/*public Insets(int top,
      int left,
      int bottom,
      int right)*/
		decformat = new DecimalFormat();
		curformat = new DecimalFormat();
		curformat.setCurrency(Currency.getInstance(getDefaultLocale()));
		curformat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(getDefaultLocale()));
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		this.setLayout(new GridBagLayout());
		
		createInfoPart(c);
		
		createDistributionPart(c);
		
		createProductionPart(c);
		
		//Startwerte gleich dem vorher gespeicherten
		amountsell.setText(decformat.format(comp.getProdAndDistr().getUnitsToSell()));
		amountproduce.setText(decformat.format(comp.getProdAndDistr().getUnitsToProduce()));
		pricesell.setText(curformat.format(comp.getProdAndDistr().getSellingPrice()));
		refreshCount();
	}


	/**
	 * @param c
	 * @param hjl
	 * @param inset_newLine
	 */
	private void createProductionPart(GridBagConstraints c) {
		//ProductionScreen

		
		c.ipady = 30;
		JLabel title2 = new JLabel("Produktionsplanung");
		title2.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title2,c);
		c.ipady = 1;
		c.gridy++;
		
		this.add(new JLabel("Geplante Produktionsmenge: "),c);
		c.gridx++;		
		amountproduce = new JTextField();
		amountproduce.setSize(100, 500);
		amountproduce.addKeyListener(tl);
		//amountproduce.setText(String.valueOf(comp.getProdAndDistr().getUnitsToProduce()));
		this.add(amountproduce, c);  
		c.gridx = 0;
		c.gridy++;
		
		

		maxProducableUnits = comp.getProdAndDistr().getMaxProducableUnits();
		maxSellableUnits = comp.getFinishedProducts();
		
		c.anchor = GridBagConstraints.NORTHWEST;
		//Neue Zeile
		c.insets = inset_newLine;
		this.add(new JLabel("Fehlende Ressourcen : "),c);
		c.gridy++;
		c.insets = inset_standard;

		RessourceType t0 = RessourceType.values()[0];
		this.add(new JLabel(t0.name() + ": "),c);
		c.gridx++;
		l_missingRessource0 =new JLabel(getMissingRessource(t0));
		this.add(l_missingRessource0,c);
		c.gridx++;
		l_missingRessourceCapital0 = new JLabel(getMissingRessourceCapital(t0));
		this.add(l_missingRessourceCapital0,c);
		
		c.gridx=0;
		c.gridy ++;
		
		RessourceType t1 = RessourceType.values()[1];
		this.add(new JLabel(t1.name() + ": "),c);
		c.gridx++;
		l_missingRessource1 =new JLabel(getMissingRessource(t1));
		this.add(l_missingRessource1,c);
		
		c.gridx++;
		l_missingRessourceCapital1 = new JLabel(getMissingRessourceCapital(t1));
		this.add(l_missingRessourceCapital1,c);
		
		c.gridx=0;
		c.gridy ++;
		

		
		//Neue Zeile
		c.insets = inset_newLine;
		this.add(new JLabel("Fehlende Mitarbeiter : "),c);
		c.gridy++;
		c.insets = inset_standard;
		EmployeeType e0 = EmployeeType.values()[0];
		JLabel le0 = new JLabel(e0.name() + ": ");
		this.add(le0,c);
		c.gridx++;
		l_missingEmployee0 =new JLabel(getMissingEmployee(e0));
		this.add(l_missingEmployee0,c);
		c.gridx++;
		l_missingEmployeeCapital0 = new JLabel(getMissingEmployeeCapital(e0));
		this.add(l_missingEmployeeCapital0,c);
		
		c.gridx=0;
		c.gridy ++;
		
		EmployeeType e1 = EmployeeType.values()[1];
		this.add(new JLabel(e1.name() + ": "),c);
		c.gridx++;
		l_missingEmployee1 =new JLabel(getMissingEmployee(e1));
		this.add(l_missingEmployee1,c);
		c.gridx++;
		l_missingEmployeeCapital1 = new JLabel(getMissingEmployeeCapital(e1));
		this.add(l_missingEmployeeCapital1,c);
		
		c.gridx=0;
		c.gridy ++;

		
		//Neue Zeile
		c.insets = inset_newLine;
		this.add(new JLabel("Fehlende Maschinenkapazität: "),c);
		c.gridy++;
		c.insets = inset_standard;

		MachineType m0 = MachineType.values()[0];
		this.add(new JLabel(m0.name() + ": "),c);
		c.gridx++;
		l_missingMachine0 =new JLabel(getMissingMachineCapacity(m0));
		this.add(l_missingMachine0,c);
		c.gridx++;
		l_missingMachineCapital0 = new JLabel(getMissingMachineCapacityCapital(m0));
		this.add(l_missingMachineCapital0,c);
		
		c.gridx=0;
		c.gridy ++;
		
		MachineType m1 = MachineType.values()[1];
		this.add(new JLabel(m1.name() + ": "),c);
		c.gridx++;
		l_missingMachine1 =new JLabel(getMissingMachineCapacity(m1));
		this.add(l_missingMachine1,c);
		c.gridx++;
		l_missingMachineCapital1 = new JLabel(getMissingMachineCapacityCapital(m1));
		this.add(l_missingMachineCapital1,c);
		
		c.gridx=0;
		c.gridy ++;

		
		//Neue Zeile
		c.gridx = 1;
		this.add(new JLabel("Kapitalbedarf gesamt: "),c);
		c.gridx++;
		l_sum = new JLabel(calcNeed());
		this.add(l_sum,c);
	}


	
	/**
	 * @param c
	 */
	private void createDistributionPart(GridBagConstraints c) {
		//SellingScreen
		c.ipady = 30;
		JLabel title1 = new JLabel("Absatzplanung");
		title1.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title1,c);
		c.ipady = 1;
		c.gridy++;
		
		//Verkaufsmenge
		this.add(new JLabel("Geplante Verkaufsmenge: "),c);
		
		c.gridx++;
		c.fill = GridBagConstraints.BOTH;
		amountsell = new JTextField();
		amountsell.addKeyListener(tl);
		this.add(amountsell, c);  
		c.gridy++;
		c.gridx = 0;
		
		//Verkaufspreis
		this.add(new JLabel("Geplanter Verkaufspreis: "),c);
		c.gridx++;
		pricesell = new JTextField();
		pricesell.addKeyListener(tl);
		this.add(pricesell, c);
		
		c.gridx = 0;		
		c.gridy++;
	}


	/**
	 * @param c
	 */
	private void createInfoPart(GridBagConstraints c) {
		c.ipady = 30;
		JLabel title3 = new JLabel("Informationen");
		title3.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title3,c);
		c.ipady = 1;
		c.gridy++;
		//Neue Zeile
		this.add(new JLabel("Fertigprodukte auf Lager: "),c);
		c.gridx++;
		l_finishedProducts = new JLabel(String.valueOf(comp.getFinishedProducts()) + " Paletten");
		this.add(l_finishedProducts ,c);
		c.gridx=0;
		c.gridy++;
		
		//Neue Zeile
		this.add(new JLabel("Aktuell produzierbare Fertigprodukte: "),c);
		c.gridx++;
		l_maxProducableUnits =new JLabel(String.valueOf(comp.getProdAndDistr().getMaxProducableUnits()) + " Paletten");
		this.add(l_maxProducableUnits,c);
		c.gridx=0;
		c.gridy++;

		//Neue Zeile
		c.insets = inset_newLine;
		this.add(new JLabel("Verkaufspreis der letzen Periode: "),c);

		c.gridx++;
		if(PeriodInfo.getNumberOfActPeriod() == 0)
			l_lastPeriodSellingPrice = new JLabel("Bisher keine Produkte verkauft");
		else
			l_lastPeriodSellingPrice = new JLabel();
		
		this.add(l_lastPeriodSellingPrice , c);
		c.insets = inset_standard;
		c.gridx=0;
		c.gridy++;
		
		//Neue Zeile
		this.add(new JLabel("Absatz der letzen Periode: "),c);
		c.gridx++;
		if(PeriodInfo.getNumberOfActPeriod() == 0)
			l_lastPeriodSellingAmount = new JLabel("Bisher keine Produkte verkauft");
		else
			l_lastPeriodSellingAmount = new JLabel();
		
		this.add(l_lastPeriodSellingAmount , c);
		c.gridx=0;
		c.gridy++;

		
		//Neue Zeile
		c.insets = inset_newLine;
		this.add(new JLabel("Mindestnachfrage pro Spieler: "),c);
		c.gridx++;
		l_standardNeedPerPlayer =new JLabel(decformat.format(Server.Application.AppContext.STANDARD_DEMAND_PER_PLAYER) + " Paletten");
		this.add(l_standardNeedPerPlayer,c);
		c.insets = inset_standard;
		c.gridx=0;
		c.gridy++;
		
		//Neue Zeile
		this.add(new JLabel("Durchschnittlicher Preis: "),c);
		c.gridx++;
		l_standardPrice =new JLabel(curformat.format(Server.Application.AppContext.STANDARD_PRICE_PER_UNIT) + "€ pro Palette");
		this.add(l_standardPrice,c);
		c.gridx=0;
		c.gridy++;
	}


	private String getMissingEmployee(EmployeeType type) {
		return	decformat.format(CompanyController.missingEmployees(type));	

	}




	private String getMissingRessource(RessourceType type) {
			return decformat.format(CompanyController.missingRessources(type)) + " " + Ressource.getUnit(type);
	}
	
	private String getMissingEmployeeCapital(EmployeeType type) {		
		return	curformat.format(CompanyController.missingEmployees(type) * Employee.EMPLOYCOST) + "€";	

	}


	private String getMissingRessourceCapital(RessourceType type) {
		int missRes = CompanyController.missingRessources(type);
		if (missRes==0){
			return "0€";
		}	
		return curformat.format((missRes * comp.getRessource(type).getPricePerUnit()) + Ressource.getFixedCosts(type)) + "€";
	}


	private String calcNeed() {
		double calc = 0;
		try {calc += curformat.parse(l_missingRessourceCapital0.getText()).doubleValue();
		calc += curformat.parse(l_missingRessourceCapital1.getText()).doubleValue();
		calc += curformat.parse(l_missingMachineCapital0.getText()).doubleValue();
		calc += curformat.parse(l_missingMachineCapital1.getText()).doubleValue();
		calc += curformat.parse(l_missingEmployeeCapital0.getText()).doubleValue();
		calc += curformat.parse(l_missingEmployeeCapital1.getText()).doubleValue();
		} catch (ParseException e) {}
		return curformat.format(calc);
	}


	private String getMissingMachineCapacity(MachineType m0) {
		Map<MachineType, Integer> missing = CompanyController.missingUnitsOnMachines();
		int capa = 0;
		if(missing.containsKey(m0))		
			capa = missing.get(m0);
		return decformat.format(capa);
	}


	private String getMissingMachineCapacityCapital(MachineType m0) {
		Map<MachineType, Integer> missing = CompanyController.missingUnitsOnMachines();
		double capa = 0;
		if(missing.containsKey(m0))		
			capa = missing.get(m0);
		switch(m0){
		case Verpackungsmaschine:
			capa = (capa * Machine.COST_PER_CAPACITY_VERPACKUNGSMASCHINE) * 5; //Anzahl Perioden
			break;
		case Filitiermaschine:
			capa = capa * Machine.COST_PER_CAPACITY_FILETIERMASCHINE * 5; //Anzahl Perioden
			break;
		}
		
		
		
		String erg = curformat.format(capa) +  "€";
		return erg;
	}

	
	private void refreshCount() {
		

		pricesell.setText(cutAndTrim(pricesell.getText()));
		amountproduce.setText(cutAndValidate(amountproduce.getText()));
		amountsell.setText(cutAndValidate(amountsell.getText()));
		
		try{unitsToProduce = decformat.parse(amountproduce.getText()).intValue();}catch(ParseException e){}
		try{priceToSell = decformat.parse(pricesell.getText()).doubleValue();}catch(ParseException e){}
		try{amountToSell =  decformat.parse(amountsell.getText()).intValue();}catch(ParseException e){}
				
		comp.getProdAndDistr().setUnitsToProduce(unitsToProduce);
		comp.getProdAndDistr().setUnitsToSell(amountToSell);
		comp.getProdAndDistr().setSellingPrice(priceToSell);
		
		maxProducableUnits = comp.getProdAndDistr().getMaxProducableUnits();
		maxSellableUnits = comp.getFinishedProducts() + unitsToProduce;
		
		if(amountToSell > (maxSellableUnits))
			amountsell.setForeground(Color.RED);
		else
			amountsell.setForeground(Color.BLACK);
		
		if(unitsToProduce > maxProducableUnits)
			amountproduce.setForeground(Color.RED);
		else
			amountproduce.setForeground(Color.BLACK);
		
		
		
		l_missingRessource0.setText(getMissingRessource(RessourceType.values()[0]));
		l_missingRessource1.setText(getMissingRessource(RessourceType.values()[1]));
		l_missingMachine0.setText(getMissingMachineCapacity(MachineType.values()[0]));
		l_missingMachine1.setText(getMissingMachineCapacity(MachineType.values()[1]));
		l_missingEmployee0.setText(getMissingEmployee(EmployeeType.values()[0]));
		l_missingEmployee1.setText(getMissingEmployee(EmployeeType.values()[1]));
		
		
	
		l_missingRessourceCapital0.setText(getMissingRessourceCapital(RessourceType.values()[0]));
		l_missingRessourceCapital1.setText(getMissingRessourceCapital(RessourceType.values()[1]));
		
		l_missingEmployeeCapital0.setText(getMissingEmployeeCapital(EmployeeType.values()[0]));
		l_missingEmployeeCapital1.setText(getMissingEmployeeCapital(EmployeeType.values()[1]));

		l_missingMachineCapital0.setText(getMissingMachineCapacityCapital(MachineType.values()[0]));
		l_missingMachineCapital1.setText(getMissingMachineCapacityCapital(MachineType.values()[1]));
		l_sum.setText(calcNeed());
	}
	
	private String cutAndValidate(String text) {
		try {
			return decformat.format(decformat.parse(text).intValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return text;
	}


	private String cutAndTrim(String text) {
		try {
			return curformat.format(curformat.parse(text).doubleValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return text;
	}


	@Override
	public void refreshPanel() {
		refreshCount();
		int pernum = PeriodInfo.getNumberOfActPeriod();
		if(pernum == 0){
			l_lastPeriodSellingAmount.setText("Bisher keine Produkte verkauft");
			l_lastPeriodSellingPrice.setText("Bisher keine Produkte verkauft");
		}else{
			l_lastPeriodSellingAmount.setText(String.valueOf(PeriodInfo.getPeriod(pernum - 1).getSoldProducts())+ " Paletten");
			l_lastPeriodSellingPrice.setText(String.valueOf(PeriodInfo.getPeriod(pernum - 1).getProductPrice()) + "€");
		}
		
		
		
		l_finishedProducts.setText(String.valueOf(comp.getFinishedProducts()) + " Paletten");
		l_maxProducableUnits.setText(String.valueOf(comp.getProdAndDistr().getMaxProducableUnits()) + " Paletten");
		l_standardNeedPerPlayer.setText(decformat.format(Server.Application.AppContext.STANDARD_DEMAND_PER_PLAYER) + " Paletten");
		l_standardPrice.setText(curformat.format(Server.Application.AppContext.STANDARD_PRICE_PER_UNIT) + "€ pro Palette");
	}


}
