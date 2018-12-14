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
		while(true) {
			try {
				m = buff.get();
				sleep(consTime * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(m != null) {
				System.out.println(m.print(this.getId()));
			}
			else {
				break;
			}
		}
		System.out.println("Fin thread Consumer "+ this.getId());
	}
}
