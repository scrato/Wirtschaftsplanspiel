package Client.Presentation;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class InfoScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public InfoScreen(){
			super("Informationen �ber Business Basics");
			
			this.setSize(400,200);
			this.setVisible(true);
			this.setLocation(200, 200);
			
			this.add(new JTextField("Informationen �ber das Projekt und Business Basics"));
		}
}
