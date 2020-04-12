
package mi6.parser.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class Filter extends FileFilter {

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = FileUtils.getExtension(f);
		if (extension != null) {
			if (extension.equalsIgnoreCase(FileUtils.JSON) 
					|| extension.equalsIgnoreCase(FileUtils.XML)
					|| extension.equalsIgnoreCase(FileUtils.TEXT)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	// The description of this filter
	public String getDescription() {
		return "JSON, XML, TEXT File(s) Only";
	}
}