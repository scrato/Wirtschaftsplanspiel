package Client.Presentation;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

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
		
		
		// Employe
		JTextField anzahlEmploy = new JTextField(4);
		JComboBox typeListEmploy = new JComboBox(types);
		JButton employButton = new JButton("Einstellen");
		JTextField kostenEmploy = new JTextField(5);
		
		
		employButton.addActionListener(new employ(anzahlEmploy, typeListEmploy));
		
		// Dimiss
		JTextField anzahlDismiss = new JTextField(4);
		JComboBox typeListDismiss = new JComboBox(types);
		JTextField kostenDismiss = new JTextField(5);
		JButton dismissButton = new JButton("Entlassen");
		
		
		dismissButton.addActionListener(new dismiss(anzahlDismiss, typeListDismiss));
		
		kostenDismiss.setEditable(false);
		kostenEmploy.setEditable(false);
		
		
		
		// Capacityoverview
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		add(new JLabel("Personalverwaltung"), c);
		c.gridwidth = 1;
		
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
		add(new JLabel("Kosten:"), c);
		
		c.gridx = 5;
		c.gridy = 6;
		add(kostenEmploy, c);
		
		c.gridx = 6;
		c.gridy = 6;
		add(employButton, c);
		
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
		add(new JLabel("Kosten"), c);
		
		c.gridx = 5;
		c.gridy = 8;
		add(kostenDismiss,c);
		
		c.gridx = 6;
		c.gridy = 8;
		add(dismissButton,c);
		
		
		
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
			
			
			if(((String)typeField.getSelectedItem()).equals("Verwaltung")){
				int i = 0;
				while(i < anzahl){
					company.addEmployee(admin);
					i++;
				}
			}else{
				int i = 0;
				while(i < anzahl){
					company.addEmployee(production);
					i++;
				}
			}
			
			updateCapacity();
			
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
			
			
			if(((String)typeField.getSelectedItem()).equals("Verwaltung")){
				int i = 0;
				while(i < anzahl){
					//company.
					i++;
				}
			}else{
				int i = 0;
				while(i < anzahl){
					//company.addEmployee(production);
					i++;
				}
			}
			
			updateCapacity();
			
			
		}
		
	}
}
