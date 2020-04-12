package mi6.parser.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JOptionPane;

import mi6.parser.worker.JsonWorker;
import mi6.parser.worker.Worker;

public class Processor implements PropertyChangeListener{

	private Worker worker;
	
	public Processor() {

	}
	
	public void convert(PropertyChangeListener l, File file) {
		if(file == null) return;
		
		if (worker == null) {
			String title = "Error: No File Selected";
			String message = "Select JSON, XML or TEXT File to Convert to CSV.";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		worker.setOutput(file);
		worker.addParserPropertyChangeListener(l);
		worker.execute();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("files")) {

			File[] files = (File[]) evt.getNewValue();

			worker = new JsonWorker(files);
			worker.addPropertyChangeListener(this);

		}
		
	}
}
