package mi6.parser.writter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CsvWritter implements PropertyChangeListener{
	
	private static final char DEFAULT_SEPARATOR = ',';
	
	private Set<String> header;
	private List<Map<String, String>> data;
	private FileWriter w;
	
	public CsvWritter() {
		header = new LinkedHashSet<>();
		data = new LinkedList<>();
	}
	
	public void setFile(File file) {
		try {
			w = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLine() {
		data.parallelStream().forEach(values->{
			List<String> value = header.stream().map(header-> values.get(header)).collect(Collectors.toList());
			System.err.println(value.toString());
			try {
				writeLine(w, value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void writeLine(Map<String, String> data) {
		List<String> value = header.stream().map(header -> data.get(header)).collect(Collectors.toList());
		try {
			writeLine(w, value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private String followCVSformat(String value) {

    	if(value == null) return "";
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        if (result.contains(",")) {
        	result = result.replace(",", "");
        }
        if (result.contains("\n")) {
        	result = result.replaceAll("\n", "  ");
        }
        return result;

    }

    public void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }
    
    public void close() {
    	try {
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("header")) {
			header = (Set<String>) evt.getNewValue();
			try {
				writeLine(w, new ArrayList<>(header));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(evt.getPropertyName().equals("line")) {
			Map<String, String> data = (Map<String, String>) evt.getNewValue();
			if(data.isEmpty()) return;
			writeLine(data);
		}
		
		if(evt.getPropertyName().equals("complete")) {
			close();
		}
		
	}
}
