package jus.poc.prodcons.v1;

import jus.poc.prodcons.IProdConsBuffer;

import java.util.ArrayList;
import java.util.List;

import jus.poc.prodcons.IMessage;

public class ProdConsBufferV1 implements IProdConsBuffer {

	private List<MessageV1> buf;
	int size;
	int nbMsg = 0;
	int nbMsgCons = 0;

	public ProdConsBufferV1(int buffSize) {
		buf = new ArrayList<MessageV1>(buffSize);
		size = buffSize;
	}

	@Override
	public synchronized void put(IMessage m) throws InterruptedException {
		while (nbMsg >= size) {
			wait();
		}
		buf.add((MessageV1) m);
		nbMsg++;
		notifyAll();
	}

	@Override
	public synchronized MessageV1 get() throws InterruptedException {
		if (nbMsgCons < TestProdCons.nbMsgTot) {
			nbMsgCons++;
			while (nbMsg <= 0) {
				// attente passive
				wait();
			}
			MessageV1 m = buf.remove(0);
			nbMsg--;
			notifyAll();
			return m;
		}
		else {
			return null;
		}
	}

	@Override
	public int nmsg() {
		return nbMsg;
	}
}
