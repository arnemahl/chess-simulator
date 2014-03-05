package program;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import view.MainWindow;

public class Main {
	private static MainWindow MF;

	public static void main(String[] args) {
		class WindowEventHandler extends WindowAdapter {
			public void windowClosing(WindowEvent evt) {
				MF.ClosingWindow();
			}
		}
		MF = new MainWindow();
		MF.addWindowListener(new WindowEventHandler());
		MF.setVisible(true);
	}

}
