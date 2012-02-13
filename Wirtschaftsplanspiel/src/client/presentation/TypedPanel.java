package client.presentation;

import javax.swing.JPanel;

public  class TypedPanel extends JPanel {

	protected enum PanelType {
		ProdAndDistr,
		Ressource,
		Machine,
		Employee,
		Credit,
		Reporting,
		Startbildschirm, Wartebildschirm, Endbildschirm
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PanelType type;
	public TypedPanel(PanelType t){
		type = t;
	}
	
	public  void refreshPanel(){
		//This method has to be overwritten
	}

}
