package jus.poc.prodcons.v3;

public class Consumer extends Thread {
	int consTime;
	ProdConsBufferV3 buff;
	
	public Consumer(int t, ProdConsBufferV3 b) {
		consTime = t;
		buff = b;
	}
	
	public void run() {
		MessageV3 m = null;
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
