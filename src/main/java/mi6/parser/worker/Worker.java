package mi6.parser.worker;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import mi6.parser.readers.JsonParse;
import mi6.parser.readers.Parser;
import mi6.parser.writter.CsvWritter;

public abstract class Worker extends SwingWorker<Void, Void>{

	protected Parser parser;
	
	protected CsvWritter writter;
	
	protected File output;
	
	protected Worker() {
		parser = new JsonParse();
		writter = new CsvWritter();
	}
	
	public void stopParser() {
		parser.stop();
	}
	
	public void pause() {
		parser.pause();
	}
	
	public void addParserPropertyChangeListener(PropertyChangeListener l) {
		parser.addPropertyChangeListener(l);
		parser.addPropertyChangeListener(writter);
	}
	
	public void removeParserPropertyChangeListener(PropertyChangeListener l) {
		parser.removePropertyChangeListener(l);
		parser.removePropertyChangeListener(writter);
	}
	
	public void setOutput(File output) {
		this.output = output;
	}
	
	@Override
	protected void done() {
		try {
			get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
		
}
