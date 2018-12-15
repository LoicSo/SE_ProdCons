package jus.poc.prodcons.v3;

import java.util.ArrayList;
import java.util.List;

import jus.poc.prodcons.IMessage;
import jus.poc.prodcons.IProdConsBuffer;

public class ProdConsBufferV3 implements IProdConsBuffer {

	private List<MessageV3> buf;
	int size;
	int nbMsgMax = 0;
	int nbMsg = 0;

	public ProdConsBufferV3(int buffSize) {
		buf = new ArrayList<MessageV3>(buffSize);
		size = buffSize;
	}

	@Override
	public synchronized void put(IMessage m) throws InterruptedException {
		MessageV3 m3 = (MessageV3) m;
		while(nbMsg >= size) {
			wait();
		}
		
		buf.add(m3);
		nbMsg++;
		notifyAll();
		
		// On attend que tout les exemplaire du message ai ete consomme
		while(m3.isAvailable()) {
			wait();
		}
	}

	@Override
	public synchronized MessageV3 get() throws InterruptedException {
		nbMsgMax--;
		while(nbMsg <= 0) {
			wait();
		}
		
		// On recupere un exemplaire du message
		MessageV3 m = buf.get(0);		
		
		// On decremente le nombre d'exemplaire du message
		m.decrNbEx();
		m.traitement(Thread.currentThread().getId());
		
		// Si on ne prends pas le dernier exemplaire
		if(m.isAvailable()) {
			// On attend que tout les exemplaire du message ai ete consomme
			while(m.isAvailable()) {
				wait();
			}
		}
		else {
			// Sinon on retire le massage du buffer
			buf.remove(0);
			nbMsg--;
			
			// On notifie tout les threads en attentes
			notifyAll();
		}
		return m;
	}

	@Override
	public int nmsg() {
		return nbMsg;
	}

	public boolean endMsg() {
		return nbMsgMax == 0;
	}

	@Override
	public void incrTotMes(int n) {
		nbMsgMax += n;
		
	}
}
