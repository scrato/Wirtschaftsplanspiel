package Client.Presentation;

import javax.swing.JTextField;

public class TypeTextbox<T> extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9153416311811856149L;
	private T type;

	public TypeTextbox(T t) {
		this.type = t;
	}

	public T getType(){
		return type;
	}
}
