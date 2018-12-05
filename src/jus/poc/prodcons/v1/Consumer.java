package jus.poc.prodcons.v1;

public class Consumer extends Thread {
	int consTime;
	ProdConsBuffer buff;
	
	public Consumer(int t, ProdConsBuffer b) {
		consTime = t;
		buff = b;
	}
	
	public void run() {
		MessageV1 m = null;
		while(true) {
			try {
				sleep((long)(Math.random() * consTime * 1000));
				m = buff.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(m.print());
		}
	}
}
