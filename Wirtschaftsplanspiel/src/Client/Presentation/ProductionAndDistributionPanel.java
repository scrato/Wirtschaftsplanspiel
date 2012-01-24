package Client.Presentation;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JPanel;

public class ProductionAndDistributionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2375380045275272220L;

	public ProductionAndDistributionPanel(){
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.ipady = 30;
		c.gridy = 0;
		this.setLayout(new GridBagLayout());
		//SellingScreen
		JLabel title = new JLabel("Gewünschte Verkaufsdaten festlegen");
		title.setFont(new Font("Serif", Font.BOLD, 14));
		this.add(title,c);
		c.ipady = 1;
		c.gridx = 0;
		c.gridy++;
		this.add(new JLabel("Geplante Verkaufsmenge: "),c);
		c.gridx++;
		JSlider x = new JSlider();
		this.add(x,c);
		c.gridx++;
		JLabel amountsell = new JLabel();
		this.add(amountsell, c);
		//ProductionScreen
		//refreshCount(amountsell, pricesell, amountproduce);
	}
}
