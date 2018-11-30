package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {

	public static void main(String[] args) {
		
		String file = "options.xml";
		
		Properties properties = new Properties();
		try {
			properties.loadFromXML(
			TestProdCons.class.getClassLoader().getResourceAsStream(file));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int nbP = Integer.parseInt(properties.getProperty("nbP"));
		int nbC = Integer.parseInt(properties.getProperty("nbC"));
		int bufSz = Integer.parseInt(properties.getProperty("BufSz"));
		int prodTime = Integer.parseInt(properties.getProperty("ProdTime"));
		int ConsTime = Integer.parseInt(properties.getProperty("ConsTime"));
		int Mavg = Integer.parseInt(properties.getProperty("Mavg"));
		
		
	}

}
