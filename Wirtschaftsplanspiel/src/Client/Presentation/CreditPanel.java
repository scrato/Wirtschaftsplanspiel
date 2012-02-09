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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.Entities.Company;
import Client.Entities.Credit;
import Client.Entities.Employee;
import Client.Application.CompanyController;
import Client.Application.UnableToTakeCreditException;

public class CreditPanel extends TypedPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField interestHigh = new JTextField(4);
	JTextField creditHigh = new JTextField(7);
	JComboBox cb_contractPeriod;
	JButton takeCredit = new JButton ("Aufnehmen");

	

	
	
	public CreditPanel(){
		super(PanelType.Credit);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		takeCredit.addActionListener(new takeOutLoan());
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
			c.anchor = GridBagConstraints.LINE_START;
			c.insets = new Insets(10,10,10,100);
			add(creditHigh,c);
			c.insets = new Insets(0,0,0,0);
			c.anchor = GridBagConstraints.CENTER;
			
			c.insets = new Insets(10,10,10,30);
			c.gridx=0;
			c.gridy=1;
			add(new JLabel("Höhe des Zinssatzes:	"),c);
			c.insets = new Insets(0,0,0,0);
			
			c.insets = new Insets(10,10,10,100);
			c.gridx=1;
			c.gridy=1;
			c.anchor = GridBagConstraints.LINE_START;
			add(interestHigh,c);
			c.insets = new Insets(0,0,0,0);
			c.anchor = GridBagConstraints.CENTER;
			
			c.insets = new Insets(10,10,150,30);
			c.gridx=0;
			c.gridy=2;
			add(new JLabel("Laufzeit"),c);
			c.insets = new Insets(0,0,0,0);
			
			c.insets = new Insets(10,10,150,100);
			c.gridx++;
			c.anchor = GridBagConstraints.LINE_START;
			cb_contractPeriod = new JComboBox();
			
			for(int i=1; i<=5; i++){
				cb_contractPeriod.addItem(i+"");
			}
			interestHigh.setText((Credit.getPercentageForPeriods(1)*100) +"%");
			cb_contractPeriod.addItemListener(new interestsItemListener());
			this.add(cb_contractPeriod,c);
			cb_contractPeriod.setEditable(false);
			c.insets = new Insets(0,0,0,0);
			c.anchor = GridBagConstraints.CENTER;
			
			c.insets = new Insets(10,20,150,30);
			c.gridx=3;
			c.gridy=2;
			add(takeCredit,c);
			c.insets = new Insets(0,0,0,0);
		}
		else
		{
					
		}
		
		
	}
	
	public void refresh(){
		this.removeAll();
		revalidate();
		validate();
		repaint();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Sie haben bereits einen Kredit aufgenommen."),c);	
	}
	
	private class takeOutLoan implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int periodTime = Integer.valueOf((String) cb_contractPeriod.getSelectedItem());
			double creditHighN = Double.valueOf((String) creditHigh.getText());
			
			try {
				CompanyController.takeCredit(creditHighN, periodTime);
				refresh();
			} catch (UnableToTakeCreditException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(new JFrame(),"Kredit ist zu hoch.");
			}
		}
	}
	
	private class interestsItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
			int periodTime = Integer.valueOf((String) cb_contractPeriod.getSelectedItem()); 
			
			
			interestHigh.setText((Credit.getPercentageForPeriods(periodTime)*100) +"%");
		}

	}

	@Override
	public void refreshPanel() {
		refresh();
		
	}
	
	

		
		
	
	
}
	
	
