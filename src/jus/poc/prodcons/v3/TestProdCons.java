package jus.poc.prodcons.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class TestProdCons {
	
	
	public static void main(String[] args) throws InterruptedException {
		List<Thread> l = new ArrayList<Thread>();
		ProdConsBufferV3 buff; 
		int nbMsgTot = 0;
		
		String file = "/jus/poc/prodcons/options.xml";
		
		Properties properties = new Properties();
		try {
			properties.loadFromXML(
			TestProdCons.class.getResourceAsStream(file));
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
			l.add(new Producer(mavg, prodTime, buff));
			nbMsgTot += mavg;
		}
		
		for (int i = 0; i < nbC; i++) {
			l.add(new Consumer(consTime, buff));
		}
		
		buff.setMaxMsg(nbMsgTot);
		
		Collections.shuffle(l);
		
		for (Iterator<Thread> iterator = l.iterator(); iterator.hasNext();) {
			Thread t = iterator.next();
			t.start();	
		}
		
		System.out.println("Fin Thread Principal Niveau 2");
	}
}
