package jus.poc.prodcons.v3;

import jus.poc.prodcons.IProdConsBuffer;

public class Producer extends Thread {
	int nbProd; // Nb de messages a produire
	int prodTime; // Temps de production
	IProdConsBuffer buff; // buffer où stocker les messages
	int nb; // Nb d'exemplaire max d'un message
	int[] nbEx;	// Tableau contenant le nombre d'exemplaire de chaque message
	
	public Producer (int n, int t, IProdConsBuffer b, int nb) {
		nbProd = n;
		prodTime =t;
		buff = b;
		this.nb = nb;
		nbEx = new int[nbProd];
		
		// Definition de nombre d'exemplaire de chaque message et augmentation du nombre de message total du buffer
		for (int i = 0; i < nbProd; i++) {
			nbEx[i] = (int) (Math.random() * nb + 1);
			buff.incrTotMes(nbEx[i]);
		}
	}
	
	public void run() {
		int i = 0;
		MessageV3 m;
		for(i = 0; i < nbProd; i++) {
			try {
				sleep(prodTime * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			m = new MessageV3(this.getId(), nbEx[i]);
			System.out.println("Nb Message Producer n°" + this.getId() + " : " + nbEx[i]);
			
			try {
				buff.put(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin thread Producer " + this.getId());
	}
}
