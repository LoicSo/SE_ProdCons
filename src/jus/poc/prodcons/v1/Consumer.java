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
				sleep(consTime * 300);
				m = buff.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(m.print(this.getId()));
		}
	}
}
