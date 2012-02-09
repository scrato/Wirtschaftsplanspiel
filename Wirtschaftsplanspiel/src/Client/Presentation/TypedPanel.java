package Client.Presentation;

import javax.swing.JPanel;

public class TypedPanel extends JPanel {

	protected enum PanelType {
		ProdAndDistr,
		Ressource,
		Machine,
		Employee,
		Credit,
		Reporting,
		Startbildschirm
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PanelType type;
	public TypedPanel(PanelType t){
		type = t;
	}

}
