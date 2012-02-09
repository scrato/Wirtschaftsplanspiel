package Client.Presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.Entities.Company;
import Client.Entities.Credit;
import Client.Entities.Employee;
import Client.Application.CompanyController;

public class CreditPanel extends JPanel {
	
	JTextField interestHigh = new JTextField(4);
	JTextField creditHigh = new JTextField(4);
	JComboBox cb_contractPeriod;
	

	
	
	public CreditPanel (){
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		cb_contractPeriod.addItemListener(new interestsItemListener());
		
		
		interestHigh.setEditable(false);
	//		cb_contractPeriod.addItemListener(new java.awt.event.ItemListener());
			
		if (Company.getInstance().getCredit()==null)
		{
			c.gridx=0;		
			c.gridy=0;
			c.insets = new Insets(10,10,10,30);	//top,left,bottom,right
			add(new JLabel("Höhe des Kredits:	"),c);
			c.insets = new Insets(0,0,0,0);
			
			c.gridx=1;
			c.gridy=0;
			c.insets = new Insets(10,10,10,100);
			add(creditHigh,c);
			c.insets = new Insets(0,0,0,0);
			
			c.insets = new Insets(10,10,10,30);
			c.gridx=0;
			c.gridy=1;
			add(new JLabel("Höhe des Zinssatzes:	"),c);
			c.insets = new Insets(0,0,0,0);
			
			c.insets = new Insets(10,10,10,100);
			c.gridx=1;
			c.gridy=1;
			add(interestHigh,c);
			c.insets = new Insets(0,0,0,0);
			
			c.insets = new Insets(10,10,150,30);
			c.gridx=0;
			c.gridy=2;
			add(new JLabel("Laufzeit"),c);
			c.insets = new Insets(0,0,0,0);
			
			c.insets = new Insets(10,10,150,100);
			c.gridx++;
			cb_contractPeriod = new JComboBox();
			for(int i=1; i<=5; i++){
				cb_contractPeriod.addItem(i);
			}
	//		cb_contractPeriod.addItemListener(new CapacityItemListener());
			this.add(cb_contractPeriod,c);
			cb_contractPeriod.setEditable(false);
			c.insets = new Insets(0,0,0,0);
			
			c.insets = new Insets(10,20,150,30);
			c.gridx=3;
			c.gridy=2;
			add(new JButton("Aufnehmen"),c);
			c.insets = new Insets(0,0,0,0);
		}
		else
		{
			c.gridx=0;
			c.gridy=0;
			add(new JLabel("Sie haben bereits einen Kredit aufgenommen."),c);			
		}
		
		
	}
	
	private class takeOutLoan implements ActionListener {

		JTextField anzahlField;
		JComboBox typeField;
		
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class interestsItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
			int periodTime = Integer.valueOf((String) cb_contractPeriod.getSelectedItem()); 
			
			interestHigh.setText(Credit.getPercentageForPeriods(periodTime) +"");
		}

	}
	
	
	private class focusItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
	}
	

}
	
	
