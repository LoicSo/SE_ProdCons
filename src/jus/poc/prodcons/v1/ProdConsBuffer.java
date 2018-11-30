package jus.poc.prodcons.v1;

import jus.poc.prodcons.IProdConsBuffer;
import jus.poc.prodcons.IMessage;

public class ProdConsBuffer implements IProdConsBuffer {
	
	private IMessage[] buf = new IMessage[100];
	public int index = 0;

	@Override
	public synchronized void put(IMessage m) throws InterruptedException {
		buf[index] = m;
		index ++;
		notifyAll();
	}

	@Override
	public synchronized IMessage get() throws InterruptedException {
		while(index == 0) {
            try {
                //attente passive
                wait();
            } catch(InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return buf[0];
	}

	@Override
	public int nmsg() {
		return index;
	}

}
