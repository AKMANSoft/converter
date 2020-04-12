package mi6.parser;

import javax.swing.SwingUtilities;

import mi6.parser.pane.MainWindowDialog;

/**
 *
 */
public class App 
{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			MainWindowDialog dialog = new MainWindowDialog();
			dialog.setVisible(true);
		});
	}
}
