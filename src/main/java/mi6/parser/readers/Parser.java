package mi6.parser.readers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Parser {
	
	/**
	 * File to Parse. 
	 */
	protected File file;
	
	/**
	 * Cancel the count or parsing process. 
	 */
	protected AtomicBoolean cancel;
	
	/**
	 * Pause the counting or parsing processing. 
	 */
	protected AtomicBoolean pause;
	
	/**
	 * Number of Record in the File. 
	 */
	protected AtomicInteger count;
	
	
	private PropertyChangeSupport support;
	
	public Parser() {
		cancel = new AtomicBoolean(false);
		pause = new AtomicBoolean(false);
		count = new AtomicInteger(0);
		support  = new  PropertyChangeSupport(this);
	}
	
	public Parser(File file) {
		this();
		this.file = file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		support.removePropertyChangeListener(l);
	}
	
	protected void count(int count) {
		support.firePropertyChange("count", null, count);
	}
	
	public void object(String key, String value) {
		support.firePropertyChange("object", key, value);
	}
	
	public void progress(int count, int total) {
		support.firePropertyChange("progress", total, count);
	}
	
	public void complete() {
		support.firePropertyChange("complete", null, null);
	}
	
	public void header(Set<String> header) {
		support.firePropertyChange("header", null, header);
	}
	
	public void line(Map<String, String> line) {
		support.firePropertyChange("line", null, line);
	}
	
	public void stop() {
		cancel.set(true);
	}
	
	public void pause() {
		pause.set(!pause.get());
	}

	/**
	 * Count number of object found in the parsing file. 
	 * 
	 * @return count 
	 */
	public abstract int count();
	
	/**
	 * Parse the File. 
	 */
	public abstract void parse();
	
	/**
	 * Name of the parser. 
	 * 
	 * @return name of the parser. 
	 */
	public abstract String getName();
}
