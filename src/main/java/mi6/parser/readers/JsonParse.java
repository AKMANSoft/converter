package mi6.parser.readers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;

public class JsonParse extends Parser {

	private static final String NAME = "JSON Parser";
	private JsonFactory factory;
	private JsonParser parser;
	private int total;
	private Set<String> header;
	
	public JsonParse() {
		super();
		factory = new JsonFactory();
		header = new LinkedHashSet<>();
	}

	@Override
	public int count() {
		int count = 0;
		int indent = 0;
		try {
			parser = factory.createParser(file);

			while (!parser.isClosed()) {
				JsonToken token = parser.nextToken();
				
				if(token==null) continue;
				
				switch (token) {
				case START_OBJECT:
					indent++;
					break;
				case END_OBJECT:
					indent--;
					if(indent == 0) {
						if (count++ % 1000 == 0)
							count(count);
					}
					break;
				case FIELD_NAME:
					header.add(parser.getValueAsString());
					break;
				default:
					break;
				}
			}

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		total = count;
		
		header(header);
		
		return count;
	}

	@Override
	public void parse() {

		String key = "";
		String value = "";
		int indent = 0;
		
		try {
			parser = factory.createParser(file);
			Map<String, String> values = new HashMap<>();
			while (!parser.isClosed()) {
				
				JsonToken token = parser.nextToken();

				if(token==null) continue;
				
				switch (token) {
				case START_OBJECT:
					indent++;
					break;
				case START_ARRAY:
				case END_ARRAY:
				case VALUE_NULL:
				case VALUE_EMBEDDED_OBJECT:
				case NOT_AVAILABLE:
					break;
				case END_OBJECT:
					indent--;
					if(indent == 0) {
						progress(count.incrementAndGet(), total);
						line(values);
						values.clear();	
					}
					break;
				case FIELD_NAME:
					key = parser.getValueAsString();
					break;
				case VALUE_STRING:
				case VALUE_NUMBER_INT:
				case VALUE_NUMBER_FLOAT:
				case VALUE_FALSE:
				case VALUE_TRUE:
					value = parser.getValueAsString();
					object(key, value);
					values.put(key, value);
					break;
				default:
					break;
				}
				
			}
			
			complete();
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public String getName() {
		return NAME;
	}

	public static void main(String[] args) throws JsonProcessingException, IOException {
		String pathname = "C:\\Users\\LAPTOPCLUB3007119711\\Desktop\\AMAZON_FASHION.json";
		File file = new File(pathname);
		JsonParse parser = new JsonParse();
		parser.setFile(file);
		//parser.count();
		parser.parse();
	}

}
