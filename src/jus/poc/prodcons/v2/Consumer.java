package jus.poc.prodcons.v2;

public class Consumer extends Thread {
	int consTime;
	ProdConsBufferV2 buff;
	
	public Consumer(int t, ProdConsBufferV2 b) {
		consTime = t;
		buff = b;
	}
	
	public void run() {
		MessageV2 m = null;
		while(!(buff.endMsg())) {
			try {
				m = buff.get();
				sleep(consTime * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin thread Consumer "+ this.getId());
	}
}
