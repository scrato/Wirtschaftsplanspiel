package Client.Presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MessageBox extends JFrame{

	private class OkCloseHandler implements ActionListener {
		
		JFrame close;
		
		public OkCloseHandler(JFrame close){
			this.close = close;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			close.dispose();

		}

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public MessageBox(String title, String message){
			super(title);
			
			this.setSize(400,200);
			this.setVisible(true);
			this.setLocation(200, 200);
			GridBagConstraints c = new GridBagConstraints();
			this.setLayout(new GridBagLayout());
			
			c.gridx = 0;
			c.gridy = 0;
			this.add(new JLabel(message),c);
			
			
			c.gridy = 1;
			JButton b = new JButton("Ok");
			this.add(b,c);
			b.addActionListener(new OkCloseHandler(this));
		}
}
