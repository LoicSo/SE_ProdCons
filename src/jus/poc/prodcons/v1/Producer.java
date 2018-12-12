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
	}
	
	public void run() {
		int i = 0;
		MessageV1 m;
		for(i = 0; i < nbProd; i++) {
			try {
				sleep(prodTime * 300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			m = new MessageV1(this.getId());
			
			try {
				buff.put(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin thread " + this.getId());
	}
}
