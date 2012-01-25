package Client.Presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
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
	JTextField filOutput = new JTextField(5);
	JTextField verpOutput = new JTextField(5);
	
	public MachinePanel(){
	
		Border border = BorderFactory.createLineBorder(Color.blue);
		String[] types = { "Filitiermaschine", "Verpackungsmaschine"};
		String[] capacites = Machine.capacites;
		JComboBox type = new JComboBox(types);
		JComboBox capacity = new JComboBox(capacites);
		JTextField costOutput = new JTextField(6);
		JButton kaufen = new JButton("Kaufen");
		kaufen.addActionListener(new buyMachine(capacity, type));
		
		costOutput.setEditable(false);
		filOutput.setEditable(false);
		verpOutput.setEditable(false);
		
		capacity.addItemListener(new customItemListener(type, capacity, costOutput));
		type.addItemListener(new customItemListener(type, capacity, costOutput));
		
		machineTabModel = new DefaultTableModel(machineData, machineColumnNames);
		machineTable = new JTable(machineTabModel){
		  public boolean isCellEditable(int rowIndex, int colIndex) {
		  return false; //Disallow the editing of any cell
		  }};
			
		  machineScrollPane = new JScrollPane(machineTable);
	
		
		
		
		JButton verkaufen = new JButton("verkaufen");
		verkaufen.setPreferredSize(new Dimension(100,25));
		kaufen.setPreferredSize(new Dimension(100,25));
			
		// Listener
		verkaufen.addActionListener(new MachineVerkaufen());
		
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
		c.fill = GridBagConstraints.BOTH;
		add(new JLabel("Kapazität"),c);
		c.fill = GridBagConstraints.NONE;
		
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
		
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.LINE_START;
		add(new JLabel("benötigt laut Prodktionsplanung:"),c);
		c.insets = new Insets(0,0,0,0);
		
		c.gridx = 0;
		c.gridy = 7;
		add(new JLabel("Filitiermaschine"),c);
		
		c.gridx = 1;
		c.gridy = 7;
		add(filOutput, c);
		
		c.gridx = 0;
		c.gridy = 8;
		add(new JLabel("Verpackungsmaschine"),c);
		
		c.gridx = 1;
		c.gridy = 8;
		add(verpOutput,c);
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
		public void refreshCapacity(){
			filOutput.setText(CompanyController.missingMachines(MachineType.Filitiermaschine, 200) + "");
			verpOutput.setText(CompanyController.missingMachines(MachineType.Filitiermaschine, 200) + "");
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
						refreshCapacity();
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
						CompanyController.buyMachine(new Machine(MachineType.Verpackungsmaschine, capacitySize, capacitySize*2));
					} catch (MachineAlreadyBoughtException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UserCanNotPayException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				refreshMachineTable();
				refreshCapacity();
			}
			
		}

		private class customItemListener implements ItemListener{

			JComboBox type;
			JComboBox capacity;
			JTextField costOutput;
			
			public customItemListener( JComboBox type, JComboBox capacity, JTextField costOutput){
				this.type = type;
				this.capacity = capacity;
				this.costOutput = costOutput;
			}
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				int capacityInt = Integer.valueOf((String)capacity.getSelectedItem());
				
				if(type.getSelectedItem().equals("Filitiermaschine")){
					costOutput.setText((capacityInt * 1.5) + "");
				}else{
					// Verpackungsmaschine
					costOutput.setText((capacityInt * 2) + "");
				}
			}
			
		}

		
}
