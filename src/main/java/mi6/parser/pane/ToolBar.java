package mi6.parser.pane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;

import mi6.parser.utils.Filter;

public class ToolBar extends JToolBar  implements Panel, ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton chooser;
	JFileChooser fc;
	
	public ToolBar() {
		initComponents();
		initLayout();
		initListeners();
	}

	@Override
	public void initComponents() {
		chooser = new JButton("File(s)");
		fc = new JFileChooser();
		fc.setDialogTitle("SELECT JSON, XML or TEXT File(s)");
		Filter filter= new Filter();
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
	}

	@Override
	public void initLayout() {
		add(chooser);
		
	}

	@Override
	public void initListeners() {
		chooser.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooser) {
			int returnVal = fc.showOpenDialog(getParent());
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File[] files = fc.getSelectedFiles();
	            firePropertyChange("files", null, files);
	        } 
		}
		
	}
	
}
