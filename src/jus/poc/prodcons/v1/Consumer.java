package jus.poc.prodcons.v1;

public class Consumer extends Thread {
	int consTime;
	ProdConsBufferV1 buff;
	
	public Consumer(int t, ProdConsBufferV1 b) {
		consTime = t;
		buff = b;
	}
	
	public void run() {
		MessageV1 m = null;
		
		// Tant que tout les messages possible n'ont pas été consommé
		while(!(buff.endMsg())) {
			try {
				m = buff.get();	// On recupere un message
				sleep(consTime * 1000);	// Sleep simule le temps de consommation du message
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin thread Consumer "+ this.getId());
	}
}
