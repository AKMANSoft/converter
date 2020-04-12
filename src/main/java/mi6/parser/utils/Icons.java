package mi6.parser.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Icons {

	public static BufferedImage converter;
	
	public static BufferedImage getConverter() {
		if(converter!=null) return converter;
		return converter = loadConverter();
	}

	private static BufferedImage loadConverter() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(Icons.class.getResource("/mi6/parser/icons/converter.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
