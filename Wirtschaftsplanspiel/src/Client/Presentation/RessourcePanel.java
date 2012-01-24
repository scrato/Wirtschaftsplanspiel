package Client.Presentation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.Application.CompanyController;
import Client.Application.NotEnoughRessourcesException;
import Client.Application.UserCanNotPayException;
import Client.Entities.Company;
import Client.Entities.Ressource;
import Client.Entities.RessourceType;

public class RessourcePanel extends JPanel {
	protected static JPanel me;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 253066089188179500L;

	public RessourcePanel(){
		me = this;
		GridBagConstraints c = new GridBagConstraints();
		
		// Werkstoffe
		this.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		this.add(new JLabel("Rohstoffe einkaufen"),c);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		int rowy = 1;
		
		for(RessourceType t: RessourceType.values()){
			c.ipady = 40;
			c.gridx = 0;
			c.gridy = rowy;
			rowy++;
			JLabel nlabel = new JLabel(t.name());
			nlabel.setFont(new Font("Serif", Font.BOLD, 14));
			
			this.add(nlabel,c);
			c.ipady = 0;
			c.gridy = rowy;
			this.add(new JLabel("Rohstoffe auf Lager: "),c);
			c.gridx++;
			TypeLabel<RessourceType, LabelTypes> tStored = new TypeLabel<RessourceType, LabelTypes>(t, LabelTypes.goodsInStock );
			this.add(tStored,c);
			rowy++;
			
			
			//Nextline
			//c.gridx = 0;
			//c.gridy = rowy;
			//this.add(new JLabel("Rohstoffe auf dem Markt: "),c);
			//c.gridx++;
			//TypeLabel<RessourceType, LabelTypes> tbuyable = new TypeLabel<RessourceType, LabelTypes>(t, LabelTypes.goodsBuyable);
			//this.add(tbuyable,c);
			//rowy++;
			
			
			//Nextline
			c.gridx = 0;
			c.gridy = rowy;
			this.add(new JLabel("Für geplante Produktion benötigt: "),c);
			c.gridx++;
			TypeLabel<RessourceType, LabelTypes> tNeed = new TypeLabel<RessourceType, LabelTypes>(t, LabelTypes.goodsNeeded);
			this.add(tNeed,c);
			rowy++;
			
			
			//Nextline
			c.gridx = 0;
			c.gridy = rowy;
			this.add(new JLabel("Preis pro Stück: "),c);
			c.gridx++;
			TypeLabel<RessourceType, LabelTypes> tPrice = new TypeLabel<RessourceType, LabelTypes>(t, LabelTypes.goodPrice);
			this.add(tPrice,c);
			rowy ++;
			
			//Nextline
			c.gridx = 0;
			c.gridy = rowy;
			this.add(new JLabel("Fixkosten pro Bestellung: "),c);
			c.gridx++;
			TypeLabel<RessourceType, LabelTypes> tfixPrice = new TypeLabel<RessourceType, LabelTypes>(t, LabelTypes.fixedPrice);
			this.add(tfixPrice,c);
			rowy ++;
			
			//Nextline
			c.gridx = 0;
			c.gridy = rowy;
			this.add(new JLabel("Geplanter Kauf: "),c);
			c.gridx++;
			TypeTextbox<RessourceType> tBuy = new TypeTextbox<RessourceType>(t);
			c.ipadx = 35;
			c.weightx = 0;
			tBuy.setText("0");
			this.add(tBuy,c);
			c.gridx++;
			this.add(new JLabel("  " + Ressource.getUnit(t)),c);
			rowy++;
			
			//Nextline
			c.gridx = 0;
			c.gridy = rowy;
			this.add(new JLabel("Derzeitige Kaufsumme: "),c);
			c.gridx++;
			JLabel tcosts = new JLabel();
			//tBuy sagen, die Kaufsumme direkt zu aktualisieren
			tBuy.addKeyListener(new RessourceBuyAmountListener(tBuy, tcosts));
			this.add(tcosts,c);
			rowy ++;
			
		
		}
		c.gridy = rowy;
		c.gridx = 0;
		JButton prev = new JButton("Zurück");
		JButton buy = new JButton("Kaufen");
		buy.addActionListener(new buyRessourceListener());
		JButton next = new JButton("Weiter");
		this.add(prev,c);
		c.gridx++;
		this.add(buy, c);
		c.gridx++;
		this.add(next, c);
		refreshRessources();
	}
	
	private class RessourceBuyAmountListener implements KeyListener {
		TypeTextbox<RessourceType> tBuy;
		JLabel tcosts;
		
		public RessourceBuyAmountListener(TypeTextbox<RessourceType> tBuy,
				JLabel tcosts) {
			this.tBuy = tBuy;
			this.tcosts = tcosts;
		}




		@Override
		public void keyPressed(KeyEvent arg0) {

		}



		@Override
		public void keyReleased(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
				buy();
				return;
			}
			try
			{			
				int amount = Integer.parseInt(tBuy.getText().trim());
				if (amount<= 0){
					tcosts.setText("0€");
					return;
				}
				Ressource res = Company.getInstance().getRessource(tBuy.getType());
				
				double costs = (res.getPricePerUnit() * amount) + Ressource.getFixedCosts(tBuy.getType());
				tcosts.setText(costs + "€");
				
				
			}
			catch(NumberFormatException e)
			{
				tcosts.setText("0€");
				return;
					
			}
			
		}



		@Override
		public void keyTyped(KeyEvent arg0) {
				
					
		}

	}

	private class buyRessourceListener implements ActionListener {

		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			buy();
		}

	}

	public void refreshRessources() {
		// Schau dir die Entitäten von Pwerkstoffe an
		for(Component t : RessourcePanel.me.getComponents()){
			
			//Die Klassen, die vom Typ "Type-Label" sind...
			if(t.getClass().equals(TypeLabel.class)){
				@SuppressWarnings("unchecked")
				//Merke dir die Ressource, für die dieses Label steht
				TypeLabel<RessourceType, LabelTypes> label = (TypeLabel<RessourceType, LabelTypes>) t;
				Ressource res = Company.getInstance().getRessource(label.getType());
				RessourceType type = res.getType();
				String unitname = " " + res.getUnit();
				//Was für einen Sinn stellt dieses Label dar
				switch (label.getSense()){
				
					//Label der den Güterpreis darstellt
					case goodPrice:
						label.setText(String.valueOf(res.getPricePerUnit()) + "€");
						break;
						
					//Label der Ressourcen auf Lager
					case goodsInStock:
						label.setText(String.valueOf(res.getStoredUnits()) + unitname);
						break;
						
					//Label der benötigten Ressourcen
					case goodsNeeded:
						//TODO: Production-Units: (Lars) FIXED: missingBlaBla methoden umgeschrieben, lesen jetzt aus Company.Production.
													  // diese wird im Absatz und Prod. Planungs Screen gefüllt.
						label.setText(String.valueOf(CompanyController.missingRessources().get(type)) + unitname);
						break;
						
					//Label der Ressourcen auf dem Markt
					case goodsBuyable:
						label.setText(String.valueOf(res.getBuyableUnits()) + unitname);
						break;
						
					//Label der Fixkosten für den Ressourcentyp
					case fixedPrice:
						label.setText(String.valueOf(Ressource.getFixedCosts(type)) + "€");
						break;
				}
					
						
			}
				
		}
		
	}
	
	public void buy() {
		for(Component c: RessourcePanel.me.getComponents()){
			//Die Klassen, die vom Typ "Type-Label" sind...
			if(c.getClass().equals(TypeTextbox.class)){
				@SuppressWarnings("unchecked")
				//Merke dir die Ressource, für die dieses Label steht
				TypeTextbox<RessourceType> tb = (TypeTextbox<RessourceType>) c;
				RessourceType t = tb.getType();
				String text = tb.getText().trim();
				//Kaufe für jede Ressource die angegebene Anzahl ein
				try {
				int amount = Integer.parseInt(text);
				if(amount>0)
						CompanyController.buyRessources(t, amount);
						//Kein Geld -> Mit Messagebox warnen
					} catch (UserCanNotPayException ex1) 
					{
						MessageBox mb = new MessageBox("Zu wenig Geld", "Sie haben zu wenig Geld um diese " +
								"Ressource zu kaufen.");
						mb.setVisible(true);
						return;
					} catch (NumberFormatException ex3)
					{
						MessageBox mb = new MessageBox("Keine valide Eingabe", 
								"Bitte geben sie eine zulässige Zahl an.");
						mb.setVisible(true);
						
					} catch (NotEnoughRessourcesException ex2) {
						MessageBox mb = new MessageBox("Nicht genug Ressourcen auf dem Markt", 
								"Es gibt nicht genug " + t.name() +
								" auf dem Markt.");
						mb.setVisible(true);
						
					} finally
					{
						refreshRessources();
						tb.setText("0");
					}
			}
		}
		
	}
	private enum LabelTypes{ goodsInStock, goodsBuyable, fixedPrice, priceToPay, goodsNeeded, goodPrice } 
	private class TypeTextbox<T> extends JTextField {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9153416311811856149L;
		private T type;

		public TypeTextbox(T t) {
			this.type = t;
			this.setSize(30, 100);
			this.setMinimumSize(new Dimension(30,100));
		}

		public T getType(){
			return type;
		}
	}
	

	/**
	 * Ein Label, dass auch später noch weißzu welchem Typ (MachinenTyp etc) es gehört
	 * @author MicSch
	 *
	 * @param <E>
	 */
	private class TypeLabel<E,V> extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5643264779608954467L;
		private E type;
		private V sense;
		
		public TypeLabel(E type, V sense){
		this.type = type;
		this.sense = sense;
		}
		
		public E getType(){
			return type;
		}
		
		public V getSense(){
			return sense;
		}
		
	}
	
}
