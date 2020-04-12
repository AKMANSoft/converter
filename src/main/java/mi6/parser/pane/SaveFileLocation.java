package mi6.parser.pane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveFileLocation extends JPanel implements Panel, ActionListener{

	private static final long serialVersionUID = -1520911433353721694L;
	
	private JTextField textField;
	private JButton btnOpen;
	private JFileChooser fc;
	
	public SaveFileLocation() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	@Override
	public void initComponents() {
		textField = new JTextField();
		textField.setEditable(false);
		btnOpen = new JButton("Output Location");
		fc = new JFileChooser();
		fc.setDialogTitle("Select Directory to Save Output File");
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
	}

	@Override
	public void initLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 10;
		gbc.weighty = 1;
		gbc.ipady = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 2, 0);
		add(textField, gbc);
		
		gbc.gridx++;
		gbc.weightx = 1;
		add(btnOpen, gbc);
		
	}

	@Override
	public void initListeners() {
		btnOpen.addActionListener(this);
		
	}
	
	public File getSaveLocation() {
		String path = textField.getText();
		
		if(path == null || path.trim().isEmpty()) {
			String title = "Error: No Output Path/Directory Selected";
			String message = "Select Output Directory to Save CSV File. ";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		return new File(textField.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOpen) {
			int returnVal = fc.showSaveDialog(getParent());
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	textField.setText(fc.getSelectedFile().getAbsolutePath());
	        } 
		}
		
	}

}
