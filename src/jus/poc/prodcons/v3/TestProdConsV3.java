package jus.poc.prodcons.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class TestProdConsV3 {
	
	
	public static void main(String[] args) throws InterruptedException {
		List<Thread> l = new ArrayList<Thread>();
		ProdConsBufferV3 buff; 
		Random r = new Random();
		
		String file = "/jus/poc/prodcons/options.xml";
		
		Properties properties = new Properties();
		try {
			properties.loadFromXML(
			TestProdConsV3.class.getResourceAsStream(file));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int nbP = Integer.parseInt(properties.getProperty("nbP"));
		int nbC = Integer.parseInt(properties.getProperty("nbC"));
		int bufSz = Integer.parseInt(properties.getProperty("BufSz"));
		int prodTime = Integer.parseInt(properties.getProperty("ProdTime"));
		int consTime = Integer.parseInt(properties.getProperty("ConsTime"));
		int mavg = Integer.parseInt(properties.getProperty("Mavg"));
		
		buff = new ProdConsBufferV3(bufSz);
		
		for (int i = 0; i < nbP; i++) {
			int nmes = (int)(r.nextGaussian() + mavg);
			int temps_prod = (int)(r.nextGaussian() + prodTime);
			l.add(new Producer(nmes, temps_prod, buff, nbC));
		}
		
		for (int i = 0; i < nbC; i++) {
			int temps_cons = (int)(r.nextGaussian() + consTime);
			Thread t = new Consumer(temps_cons, buff);
			t.setDaemon(true);
			l.add(t);
		}
		
		Collections.shuffle(l);
		
		for (Iterator<Thread> iterator = l.iterator(); iterator.hasNext();) {
			Thread t = iterator.next();
			t.start();	
		}
		
		System.out.println("Fin Thread Principal Niveau 3");
	}
}
