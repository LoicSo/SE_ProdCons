package jus.poc.prodcons.v1;

import jus.poc.prodcons.IProdConsBuffer;
import jus.poc.prodcons.IMessage;

public class ProdConsBuffer implements IProdConsBuffer {
	
	private MessageV1[] buf;
	public int index = 0;
	public int nbMCons = 0;

	public ProdConsBuffer (int buffSize) {
		buf = new MessageV1[buffSize];
	}
	
	@Override
	public synchronized void put(IMessage m) throws InterruptedException {
		while(index > buf.length) {
			wait();
		}
		buf[index] = (MessageV1) m;
		index ++;
		notifyAll();
	}

	@Override
	public synchronized MessageV1 get() throws InterruptedException {
		while(index == 0) {
            try {
                //attente passive
                wait();
            } catch(InterruptedException ie) {
                ie.printStackTrace();
            }
        }
		index--;
		nbMCons++;
		notifyAll();
        return buf[0];
	}

	@Override
	public int nmsg() {
		return index;
	}
	
	public int nbMesageCons() {
		return nbMCons;
	}



}
