package client.presentation;

import java.awt.Font;
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

import client.entities.Company;
import client.entities.Credit;
import client.entities.Employee;
import client.application.CompanyController;
import client.application.UnableToTakeCreditException;

public class CreditPanel extends TypedPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField interestHigh = new JTextField(4);
	JTextField creditHigh = new JTextField(7);
	JComboBox cb_contractPeriod;
	JButton takeCredit = new JButton ("Aufnehmen");
	JLabel title1 = new JLabel("Darlehen aufnehmen");
	
	public CreditPanel(){
		super(PanelType.Credit);
		setLayout(new GridBagLayout());
		takeCredit.addActionListener(new takeOutLoan());
	}
	
	public void refresh(){
		this.removeAll();
		if(Company.getInstance().getCredit() == null){
			buildWantCredit();
		}else{
			buildhasCredit();
		}
		repaint();
		revalidate();
		validate();
		
	}
	
	public void buildhasCredit(){
		GridBagConstraints c = new GridBagConstraints();
		JTextField creditLeft = new JTextField (7);
		creditLeft.setEditable(false);
		
		JTextField periodLeft = new JTextField (3);
		periodLeft.setEditable(false);
		
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Sie haben bereits einen Kredit aufgenommen."),c); //company.getcredit
		
		c.gridx=0;
		c.gridy=1;
		add(new JLabel("H�he des noch zu bezahlenden Kredits:"),c);
		
		c.gridx=1;
		c.gridy=1;
		add(creditLeft,c);
		double creditLeftValue = Company.getInstance().getCredit().getCreditLeft();
		creditLeftValue = (Math.round(creditLeftValue * 100.0))/100.0;
		
		creditLeft.setText(creditLeftValue+"");
		
		c.gridx=0;
		c.gridy=2;
		add(new JLabel("Perioden verbleibend:"),c);
		
		c.gridx=1;
		c.gridy=2;
		add(periodLeft,c);
		periodLeft.setText((int)Company.getInstance().getCredit().getLeftPeriods()+"");
	}
	
	public void buildWantCredit(){
		GridBagConstraints c = new GridBagConstraints();
		
		interestHigh.setEditable(false);		
		
		c.insets = new Insets(0,0,40,0);
		title1.setFont(new Font("Serif", Font.BOLD, 14));
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=3;
		add(title1,c);
		c.insets = new Insets(0,0,0,0);
		c.gridwidth=1;
		
		c.gridx=0;		
		c.gridy=1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10,10,10,30);	//top,left,bottom,right
		add(new JLabel("H�he des Kredits:"),c);
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.CENTER; 
		
		c.gridx=1;
		c.gridy=1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10,10,10,0);
		add(creditHigh,c);
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridx=2;
		c.gridy=1;
		c.anchor = GridBagConstraints.LINE_START;
		add(new JLabel("*"),c);
		c.anchor = GridBagConstraints.CENTER;
		
		c.insets = new Insets(10,10,10,30);
		c.gridx=0;
		c.gridy=2;
		add(new JLabel("H�he des Zinssatzes:	"),c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(10,10,10,100);
		c.gridx=1;
		c.gridy=2;
		c.anchor = GridBagConstraints.LINE_START;
		add(interestHigh,c);
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		
		c.insets = new Insets(10,10,150,30);
		c.gridx=0;
		c.gridy=3;
		c.anchor = GridBagConstraints.LINE_START;
		add(new JLabel("Laufzeit"),c);
		c.anchor = GridBagConstraints.CENTER;
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
		
		c.insets = new Insets(10,0,20,30);
		c.gridx=2;
		c.gridy=3;
		add(takeCredit,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(10,20,0,30);
		c.gridx=0;
		c.gridy=4;
		c.gridwidth =3;
		add(new JLabel ("* Es ist nur m�glich einen Kredit gleichzeitig aufzunehmen."),c);
		c.insets = new Insets(0,0,0,0);
		c.gridwidth =1;
		
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
			double interestHighValue = Credit.getPercentageForPeriods(periodTime)*100;
			
			interestHighValue = (Math.round(interestHighValue * 100.0))/100.0;
			
			interestHigh.setText(interestHighValue +"%");
		}

	}

	@Override
	public void refreshPanel() {
		refresh();
		
	}
	
}
	
	
