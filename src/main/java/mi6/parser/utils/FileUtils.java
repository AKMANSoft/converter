package mi6.parser.utils;

import java.io.File;

public class FileUtils {

    public final static String JSON = "json";
    public final static String XML = "xml";
    public final static String TEXT = "txt";

    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    /*
     * Get the Name without extension of a file.
     */  
    public static String getBaseName(String name) {
        int endIndex = name.lastIndexOf('.');
        return name.substring(0, endIndex);
    }
}