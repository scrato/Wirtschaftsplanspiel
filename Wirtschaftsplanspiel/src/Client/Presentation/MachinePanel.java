package Client.Presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import Client.Entities.Company;
import Client.Entities.Machine;
import Client.Entities.MachineType;

import Client.Application.CompanyController;
import Client.Application.MachineAlreadyBoughtException;
import Client.Application.MachineNotOwnedException;
import Client.Application.UserCanNotPayException;

public class MachinePanel extends JPanel {
	
	
	// MachineScreen
	String[] machineColumnNames = {"Typ", "Kapazität", "Anschaffungswert", "Restlaufzeit", "Restwert"};
	
	DefaultTableModel machineTabModel = new DefaultTableModel();
	JTable machineTable = new JTable();
	Object[][] machineData = null;
	JScrollPane machineScrollPane;
	Company company = Company.getInstance();
	
	public MachinePanel(){
	
		String[] types = { "Filitiermaschine", "Verpackungsmaschine"};
		String[] capacites = Machine.capacites;
		JComboBox type = new JComboBox(types);
		JComboBox capacity = new JComboBox(capacites);
		JTextField costOutput = new JTextField(6);
		JButton kaufen = new JButton("Kaufen");
		kaufen.addActionListener(new buyMachine(capacity, type));
		costOutput.setEditable(false);
						
		machineTabModel = new DefaultTableModel(machineData, machineColumnNames);
		machineTable = new JTable(machineTabModel);
		machineScrollPane = new JScrollPane(machineTable);
		
		
		
		JButton verkaufen = new JButton("verkaufen");
			
		// Listener
		verkaufen.addActionListener(new MachineVerkaufen());
		verkaufen.setAlignmentX(RIGHT_ALIGNMENT);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Maschinen einkaufen und verkaufen"), c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;
		add(machineScrollPane,c);
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.LINE_END;
		add(verkaufen, c);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LINE_START;
		add(new JLabel("Machinen kaufen"),c);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.LINE_START;
		add(new JLabel("Typ"),c);
		c.insets = new Insets(0,0,0,0);
		
		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		add(type,c);
				
		c.gridx = 1;
		c.gridy = 4;
		add(new JLabel("Kapazität"),c);
		
		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		add(capacity,c);
		
		
		c.gridx = 2;
		c.gridy = 4;
		c.insets = new Insets(0,10,0,0);
		add(new JLabel("Preis"),c);
		
		c.gridx = 2;
		c.gridy = 5;
		add(costOutput, c);
		c.insets = new Insets(0,0,0,0);
		
		c.gridx = 3;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0,0,0,0);
		add(kaufen,c);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;			
	}
			
		public void refreshMachineTable(){
			DefaultTableModel tableModel = new DefaultTableModel(machineData, machineColumnNames); //(DefaultTableModel) machineTable.getModel();
			
				
			Iterator<Machine> machineItr = company.getMachines().iterator();
			Machine machine;

			while(machineItr.hasNext()){
				 machine = machineItr.next();
				 String[] row = new String[5];
				 row[0] = machine.getType().toString();
				 row[1] = ""+ machine.getCapacity();
				 row[2] = machine.getInitialValue() + "";
				 row[3] = machine.getRemaininTime()+ "";
				 row[4] = machine.getValue() + "";
				 tableModel.addRow(row);
			}
			machineTable.setModel(tableModel);		
			}
			
		private class MachineVerkaufen implements ActionListener{
				
				Machine machine;
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (machineTable.getSelectedRow() != -1) {
						machine = company.getMachines().get(machineTable.getSelectedRow());
						//company.removeMachine(machine);
						try {
							CompanyController.sellMachine(machine);
						} catch (MachineNotOwnedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						refreshMachineTable();
					}
					
				}
			}
		private class buyMachine implements ActionListener{

			JComboBox capacity;
			JComboBox type;
			
			public buyMachine(JComboBox capacity, JComboBox type)
			{
				this.type = type;
				this.capacity = capacity;
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				int capacitySize = Integer.valueOf((String)capacity.getSelectedItem());
				
				if(((String)type.getSelectedItem()).equals("Filitiermaschine")){
					try {
						CompanyController.buyMachine(new Machine(MachineType.Filitiermaschine, capacitySize, capacitySize*1.5));
					} catch (MachineAlreadyBoughtException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UserCanNotPayException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					// Verpackungsmaschine
					try {
						CompanyController.buyMachine(new Machine(MachineType.Verpackungsmaschine, capacitySize, capacitySize*1.5));
					} catch (MachineAlreadyBoughtException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UserCanNotPayException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				refreshMachineTable();
			}
			
		}
}
