package Client.Presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.table.DefaultTableModel;

import Client.Entities.Company;
import Client.Entities.Machine;
import Client.Entities.MachineType;

import Client.Application.CompanyController;

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
		JComboBox type = new JComboBox(types);
		JTextField costOutput = new JTextField(4);
		
		machineTabModel = new DefaultTableModel(machineData, machineColumnNames);
		machineTable = new JTable(machineTabModel);
		machineScrollPane = new JScrollPane(machineTable);
		
		refreshMachineTable();
		
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
		c.gridwidth = 3;
		add(machineScrollPane,c);
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 2;
		add(verkaufen, c);
		
		c.gridx = 0;
		c.gridy = 3;
		add(new JLabel("Machinen kaufen"),c);
		
		c.gridx = 0;
		c.gridy = 4;
		add(new JLabel("Typ"),c);
		
		c.gridx = 0;
		c.gridy = 5;
		add(type,c);
		
		c.gridx = 1;
		c.gridy = 4;
		add(new JLabel("Kapazität"),c);
		
		c.gridx = 1;
		c.gridy = 5;
		add(new JTextField(4),c);
		
		c.gridx = 2;
		c.gridy = 4;
		add(new JLabel("Preis"),c);
		
		c.gridx = 2;
		c.gridy = 5;
		add(costOutput, c);
		
		
			
	}
			
		public void refreshMachineTable(){
			System.out.println("Machine Table aktualisiert!");
			
			
			machineData =  new String[company.getMachines().size()][5];
				
			Iterator<Machine> machineItr = company.getMachines().iterator();
			Machine machine;

			int i = 0;
			while(machineItr.hasNext()){
				 machine = machineItr.next();
				 machineData[i][0] = machine.getType().toString();
				 machineData[i][1] = ""+ machine.getCapacity();
				 machineData[i][2] = machine.getInitialValue() + "";
				 machineData[i][3] = machine.getRemaininTime()+ "";
				 machineData[i][4] = machine.getValue() + "";
				 i++;
				 System.out.println(i + "Maschinen");
			}
			
			
			
			
			machineTabModel.fireTableDataChanged();
			machineTabModel.fireTableStructureChanged();			

			
			//machineTable.tableChanged(new TableModelEvent(machineTable.getModel()));
			

			machineScrollPane.invalidate();
			machineScrollPane.validate();
			machineScrollPane.repaint();
		}
			
			private class MachineVerkaufen implements ActionListener{
				
				Machine machine;
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (machineTable.getSelectedRow() != -1) {
						//tabModel.removeRow(table.getSelectedRow());
						machine = company.getMachines().get(machineTable.getSelectedRow());
						company.removeMachine(machine);
						
						System.out.println("Maschine verkauft");
						System.out.println(machine.toString());
						System.out.println("Zeile" + machineTable.getSelectedRow() + " gelöscht");
						refreshMachineTable();
					}
					
				}
			}
}
