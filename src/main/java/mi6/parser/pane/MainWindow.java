package mi6.parser.pane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import mi6.parser.controller.Processor;
import mi6.parser.utils.Utils;

public class MainWindow extends JPanel implements Panel, ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	private JButton btnConvert;
	private JButton btnCancel;
	private JProgressBar progress;
	private JTextArea textArea;
	private SaveFileLocation saveLocation;
	private Processor processor;

	public MainWindow(Processor processor) {
		this.processor = processor;
		initComponents();
		initLayout();
		initListeners();
	}

	@Override
	public void initComponents() {
		btnConvert = new JButton("Convert");
		btnCancel = new JButton("Close");
		progress = new JProgressBar();
		progress.setStringPainted(true);
		textArea = new JTextArea(25, 50);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		saveLocation = new SaveFileLocation();
	}

	@Override
	public void initLayout() {
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 10;
		gbc.weighty = 10;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 2, 2);
		add(textArea, gbc);
		
		
		gbc.gridy++;
		gbc.weightx = 10;
		gbc.weighty = 0;
		add(saveLocation, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.ipady = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(progress, gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		gbc.ipady = 0;
		add(btnCancel, gbc);

		gbc.gridx++;
		add(btnConvert, gbc);
	}

	@Override
	public void initListeners() {
		btnConvert.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			System.exit(0);
		}

		if (e.getSource() == btnConvert) {
			File file = saveLocation.getSaveLocation();
			processor.convert(this, file);
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("files")) {

			textArea.setText(null);

			File[] files = (File[]) evt.getNewValue();

			textArea.append("Number of Files(s) Selected to Convert is: " + files.length);
			textArea.append("\n");

			for (File file : files) {
				textArea.append(file.getAbsolutePath());
				textArea.append("\n");
			}

		}

		if (evt.getPropertyName().equals("count")) {
			int count = (int) evt.getNewValue();
			progress.setString("Counting ... " + count);
		}

		if (evt.getPropertyName().equals("progress")) {
			int count = (int) evt.getNewValue();
			Integer total = (Integer) evt.getOldValue();
			progress.setString("Progress: " + count + " of " + total);
			progress.setValue(Utils.getPercentage(count, total));
		}

		if (evt.getPropertyName().equals("complete")) {
			progress.setString("Completed");
			progress.setValue(100);
			textArea.append("Complete \n");
		}

		if (evt.getPropertyName().equals("countStart")) {
			progress.setIndeterminate(true);
			textArea.append("Counting... \n");
		}
		
		if (evt.getPropertyName().equals("countEnd")) {
			progress.setIndeterminate(false);
			textArea.append("Number of File Found ");
		}

	}

}
