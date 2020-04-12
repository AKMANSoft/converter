package mi6.parser.worker;

import java.io.File;

import mi6.parser.utils.FileUtils;

public class JsonWorker extends Worker{

	private File[] files;
	
	public JsonWorker(File[] files) {
		this.files = files;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		for(File file: files) {
			
			parser.setFile(file);
			
			writter.setFile(new File(output, FileUtils.getBaseName(file.getName())+".csv"));
			
			firePropertyChange("countStart", null, null);
			
			parser.count();
			
			firePropertyChange("countEnd", null, null);
			
			parser.parse();
		}
		
		
		return null;
	}
	
	

}
