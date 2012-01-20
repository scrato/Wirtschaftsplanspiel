package Client.Presentation;

import javax.swing.JLabel;

/**
 * Ein Label, dass auch später noch weißzu welchem Typ (MachinenTyp etc) es gehört
 * @author MicSch
 *
 * @param <E>
 */
public class TypeLabel<E,V> extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5643264779608954467L;
	private E type;
	private V sense;
	
	public TypeLabel(E type, V sense){
	this.type = type;
	this.sense = sense;
	}
	
	public E getType(){
		return type;
	}
	
	public V getSense(){
		return sense;
	}
	
}
