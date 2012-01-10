package Client.Presentation;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class SpecialFocusListener implements FocusListener{
	
	JTextField textField;
	
	public SpecialFocusListener(JTextField textField){
		this.textField = textField;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if(textField.getText().equals("IP Adresse") || textField.getText().equals("Username")){
			textField.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
