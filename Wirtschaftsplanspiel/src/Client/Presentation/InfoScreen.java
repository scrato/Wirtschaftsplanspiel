package Client.Presentation;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class InfoScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public InfoScreen(){
			super("Informationen über Business Basics");
			
			this.setSize(400,200);
			this.setVisible(true);
			this.setLocation(200, 200);
			
			this.add(new JTextField("Informationen über das Projekt und Business Basics"));
		}
}
