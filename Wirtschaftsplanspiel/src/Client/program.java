package Client;

import Client.Application.AppContext;
import Client.Presentation.MainWindow;

public class program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AppContext context = new AppContext();
		MainWindow m = new MainWindow();
		m.setVisible(true);
	}

}
