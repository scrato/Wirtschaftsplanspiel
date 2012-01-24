package Client.Presentation;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import Client.Application.CompanyController;
import Client.Application.EmployeeNotEmployedException;
import Client.Application.UserCanNotPayException;
import Client.Entities.Company;
import Client.Entities.Employee;
import Client.Entities.EmployeeType;
import Client.Entities.RessourceType;

public class EmployeePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	JTextField employeeAvaPro = new JTextField(3);
	JTextField employeeAvaAdm = new JTextField(3);
	JTextField employeeNeedPro = new JTextField(3);
	JTextField employeeNeedAdm = new JTextField(3);
	
	Company company = Company.getInstance();
	
	public EmployeePanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		String[] types = { "Produktion", "Verwaltung"};
		// Test
		
		
		updateCapacity();
		
		
		
		employeeAvaPro.setEditable(false);
		employeeAvaAdm.setEditable(false);
		employeeNeedPro.setEditable(false);
		employeeNeedAdm.setEditable(false);
		// Employe
		JTextField anzahlEmploy = new JTextField(4);
		JComboBox typeListEmploy = new JComboBox(types);
		JButton employButton = new JButton("Einstellen");
		JTextField kostenEmploy = new JTextField(5);
		
		typeListEmploy.addItemListener(new costItemListener(anzahlEmploy, typeListEmploy, kostenEmploy));
		anzahlEmploy.addFocusListener(new costFocusListener(anzahlEmploy, typeListEmploy, kostenEmploy));
		employButton.addActionListener(new employ(anzahlEmploy, typeListEmploy));
		
		// Dimiss
		JTextField anzahlDismiss = new JTextField(4);
		JComboBox typeListDismiss = new JComboBox(types);
		JTextField kostenDismiss = new JTextField(5);
		JButton dismissButton = new JButton("Entlassen");
		
		anzahlDismiss.addFocusListener(new costFocusListener(anzahlDismiss, typeListDismiss, kostenDismiss));
		typeListDismiss.addItemListener(new costItemListener(anzahlDismiss, typeListDismiss, kostenDismiss));
		dismissButton.addActionListener(new dismiss(anzahlDismiss, typeListDismiss));
		
		kostenDismiss.setEditable(false);
		kostenEmploy.setEditable(false);
		
		
		
		// Capacityoverview
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,30,0);
		add(new JLabel("Personalverwaltung"), c);
		c.insets = new Insets(0,0,0,0);
		
		
		c.gridx = 0;
		c.gridy = 1;
		add(new JLabel("Verfügbar:"), c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(new JLabel("Produktion:"), c);
		
		c.gridx = 1;
		c.gridy = 2;
		add(employeeAvaPro, c);
		
		c.gridx = 2;
		c.gridy = 2;
		add(new JLabel("Verwaltung:"), c);
		
		c.gridx = 3;
		c.gridy = 2;
		add(employeeAvaAdm, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(20, 0,0,0);
		add(new JLabel("Benötigt:"), c);
		c.insets = new Insets(0, 0,0,0);
		
		c.gridx = 0;
		c.gridy = 4;
		add(new JLabel("Produktion:"), c);
		
		c.gridx = 1;
		c.gridy = 4;
		add(employeeNeedPro, c);
		
		c.gridx = 2;
		c.gridy = 4;
		add(new JLabel("Verwaltung:"), c);
		
		c.gridx = 3;
		c.gridy = 4;
		//c.ipady = 200;
		add(employeeNeedAdm, c);
		

		// Employ
		c.gridx = 0;
		c.gridy = 5;
		c.insets =  new Insets(30,0,0,0); 
		add(new JLabel("Einstellen:"), c);
		c.insets =  new Insets(0,0,0,0); 
		
		
		c.gridx = 0;
		c.gridy = 6;
		
		add(new JLabel("Anzahl:"), c);
		
		
		c.gridx = 1;
		c.gridy = 6;
		add(anzahlEmploy, c);
		
		c.gridx = 2;
		c.gridy = 6;
		add(new JLabel("Typ"), c);
		
		c.gridx = 3;
		c.gridy = 6;
		add(typeListEmploy, c);
		
		c.gridx = 4;
		c.gridy = 6;
		c.insets = new Insets(0,20,0,5);
		add(new JLabel("Kosten"), c);
		c.insets = new Insets(0,0,0,0);
		
		c.gridx = 5;
		c.gridy = 6;
		add(kostenEmploy, c);
		
		c.gridx = 6;
		c.gridy = 6;
		c.insets = new Insets(0,20,0,0);
		add(employButton, c);
		c.insets = new Insets(0,0,0,0);
		
		// Dismiss
		
		c.gridx = 0;
		c.gridy = 7;
		c.insets = new Insets(20, 0,0,0);
		add(new JLabel("Entlassen"),c);
		c.insets = new Insets(0, 0,0,0);

		
		
		c.gridx = 0;
		c.gridy= 8;
		add(new JLabel("Anzahl"), c);
		
		
		c.gridx = 1;
		c.gridy= 8;
		add(anzahlDismiss, c);
		
		c.gridx =2;
		c.gridy = 8;
		add(new JLabel("Typ"),c);
		
		c.gridx = 3;
		c.gridy = 8;
		add(typeListDismiss, c);
		
		c.gridx = 4;
		c.gridy = 8;
		c.insets = new Insets(0,20,0,5);
		add(new JLabel("Kosten"), c);
		c.insets = new Insets(0,0,0,0);
		
		c.gridx = 5;
		c.gridy = 8;
		add(kostenDismiss,c);
		
		c.gridx = 6;
		c.gridy = 8;
		c.insets = new Insets(0,20,0,0);
		add(dismissButton,c);
		c.insets = new Insets(0,00,0,0);
		
		
		
	}
	
	public void updateCapacity(){
		int employeePro = 0;
		int employeeAdm = 0;
		
		for(Employee e: company.getEmployees()){
			if(e.getType() == EmployeeType.Produktion){
				employeePro++;
			}
			
			if(e.getType() == EmployeeType.Verwaltung){
				employeeAdm++;
			}
		}
		
		employeeAvaPro.setText(employeePro+"");
		employeeAvaAdm.setText(employeeAdm+"");
		employeeNeedPro.setText(CompanyController.missingEmployees().get(EmployeeType.Produktion)+"");
		employeeNeedAdm.setText(CompanyController.missingEmployees().get(EmployeeType.Verwaltung)+ "");
		
	}

	private class employ implements ActionListener{

		JTextField anzahlField;
		JComboBox typeField;
		
		public employ( JTextField anzahl, JComboBox type){
			this.anzahlField = anzahl;
			this.typeField = type;
		}
 		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Employee admin = new Employee(EmployeeType.Verwaltung);
			Employee production = new Employee(EmployeeType.Produktion);
			int anzahl;
						
			anzahl = Integer.valueOf(anzahlField.getText());
			
			if(anzahl > 0){
				if(((String)typeField.getSelectedItem()).equals("Verwaltung")){
					int i = 0;
					while(i < anzahl){
						try {
							CompanyController.employSb(admin);
						} catch (UserCanNotPayException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(new JFrame(),"Nicht genügend liquide Mittel.");
							e.printStackTrace();
							break;
						}
						i++;
					}
				}else{
					int i = 0;
					while(i < anzahl){
						try {
							CompanyController.employSb(production);
						} catch (UserCanNotPayException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(new JFrame(),"Nicht genügend liquide Mittel.");
							e.printStackTrace();
							break;
						}
						i++;
					}
				}
				
				updateCapacity();
				anzahlField.setText(0+"");
			}
		}
		
	}
	
	private class dismiss implements ActionListener{

		JTextField anzahlField;
		JComboBox typeField;
		
		public dismiss(JTextField anzahl, JComboBox type){
			this.anzahlField = anzahl;
			this.typeField = type;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Employee admin = new Employee(EmployeeType.Verwaltung);
			Employee production = new Employee(EmployeeType.Produktion);
			int anzahl;
						
			anzahl = Integer.valueOf(anzahlField.getText());
			
			if(anzahl > 0){
				if(((String)typeField.getSelectedItem()).equals("Verwaltung")){
					int i = 0;
					while(i < anzahl){
						try {
							CompanyController.dismissSb(EmployeeType.Verwaltung);
						} catch (UserCanNotPayException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(new JFrame(),"Nicht genügend liquide Mittel.");
							e1.printStackTrace();
							break;
						} catch (EmployeeNotEmployedException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(new JFrame(),"Keine Mitarbeiter von diesem Typ angestellt.");
							e1.printStackTrace();
							break;
						}
						i++;
					}
				}else{
					int i = 0;
					while(i < anzahl){
						try {
							CompanyController.dismissSb(EmployeeType.Produktion);
						} catch (UserCanNotPayException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(new JFrame(),"Nicht genügend liquide Mittel.");
							e1.printStackTrace();
							break;
						} catch (EmployeeNotEmployedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(new JFrame(),"Keine Mitarbeiter von diesem Typ angestellt.");
							break;
						}
						
						i++;
					}
				}
				
				updateCapacity();
				anzahlField.setText(0+"");
			}
			
		}
		
	}

	private class costFocusListener implements FocusListener{

		JTextField anzahlField;
		JComboBox typeField;
		JTextField kostenOutput;
		public costFocusListener( JTextField anzahl, JComboBox type, JTextField kostenOutput){
			this.anzahlField = anzahl;
			this.typeField = type;
			this.kostenOutput = kostenOutput;
		}

		
		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			double cost = 0;
			int anzahl = Integer.valueOf(anzahlField.getText());
			
			if(anzahl > 0){
				cost = anzahl * Employee.EMPLOYCOST;
				kostenOutput.setText(cost+"");
			}
		}
		
	}
	
	private class costItemListener implements ItemListener{

		JTextField anzahlField;
		JComboBox typeField;
		JTextField kostenOutput;
		
		public costItemListener( JTextField anzahl, JComboBox type, JTextField kostenOutput){
			this.anzahlField = anzahl;
			this.typeField = type;
			this.kostenOutput = kostenOutput;
		}
		
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			double cost = 0;
			int anzahl = Integer.valueOf(anzahlField.getText());
			if(anzahl > 0){
				cost = anzahl * Employee.EMPLOYCOST;
				kostenOutput.setText(cost+"");
			}
		}
		
	}
}
