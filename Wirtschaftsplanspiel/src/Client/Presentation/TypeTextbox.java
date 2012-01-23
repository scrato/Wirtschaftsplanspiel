package Client.Presentation;

import java.awt.Dimension;

import javax.swing.JTextField;

public class TypeTextbox<T> extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9153416311811856149L;
	private T type;

	public TypeTextbox(T t) {
		this.type = t;
		this.setSize(30, 100);
		this.setMinimumSize(new Dimension(30,100));
	}

	public T getType(){
		return type;
	}
}
