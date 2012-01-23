package Client.Presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.Entities.Company;
import Client.Entities.RessourceType;

public class EmployeePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public EmployeePanel(){
		setLayout(new GridBagLayout());
		Company company = Company.getInstance();

		
		
		JTextField employeeAvaPro = new JTextField();
		JTextField employeeAvaAdm = new JTextField();
		JTextField employeeNeedPro = new JTextField();
		JTextField employeeNeedAdm = new JTextField();

		//employeeAva.setText(company.getEmployees().size()+ "");
		//employeeNeed.setText(company.get)
		
		
		GridBagConstraints c = new GridBagConstraints();
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
		add(new JLabel("Benötigt:"), c);
		
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
		add(employeeNeedAdm, c);
		

		
		
	}

}
