package jus.poc.prodcons.v1;

import jus.poc.prodcons.IProdConsBuffer;

public class Producer extends Thread {
	int nbProd; // Nb de messages a produire
	int prodTime; // Temps de production
	IProdConsBuffer buff; // buffer où stocker les messages
	
	public Producer (int n, int t, IProdConsBuffer b) {
		nbProd = n;
		prodTime =t;
		buff = b;
		
		// On augmente le nombre total de message que le buffer contiendra pendant toute la duree de l'application
		b.incrTotMes(nbProd);
	}
	
	public void run() {
		int i = 0;
		MessageV1 m;
		
		// On produit le nombre de message definie
		for(i = 0; i < nbProd; i++) {
			try {
				sleep(prodTime * 1000); // On simule le temps de production
				m = new MessageV1(this.getId()); // Création du message
				buff.put(m);	// Ajout dans le buffer
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin thread Producer " + this.getId());
	}
}
