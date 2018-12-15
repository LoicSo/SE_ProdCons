package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class TestProdConsV1 {
	
	
	public static void main(String[] args) throws InterruptedException {
		List<Thread> l = new ArrayList<Thread>();
		ProdConsBufferV1 buff; 
		Random r = new Random();
		
		// Lecture du fichier d'options
		String file = "/jus/poc/prodcons/options.xml";
		
		Properties properties = new Properties();
		try {
			properties.loadFromXML(
			TestProdConsV1.class.getResourceAsStream(file));
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
		
		// Creation du buffer
		buff = new ProdConsBufferV1(bufSz);
		
		// Creation de threads de production et ajout dans la liste de thread
		for (int i = 0; i < nbP; i++) {
			int nbmes = (int) (r.nextGaussian() + mavg);
			int temps_prod = (int) (r.nextGaussian() + prodTime);
			l.add(new Producer(nbmes, temps_prod, buff));
		}
		
		// Creation de threads de consommation et ajout dans la liste de thread
		for (int i = 0; i < nbC; i++) {
			int temps_cons = (int) (r.nextGaussian() + consTime);
			l.add(new Consumer(temps_cons, buff));
		}
		
		// Melange de la liste de thread pour les demarrer dans un ordre aleatoire
		Collections.shuffle(l);
		
		// Demmarage des threads
		for (Iterator<Thread> iterator = l.iterator(); iterator.hasNext();) {
			Thread t = iterator.next();
			t.start();	
		}
		
		System.out.println("Fin Thread Principal Niveau 1");
	}
}
