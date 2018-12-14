package jus.poc.prodcons.v3;

import jus.poc.prodcons.IProdConsBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import jus.poc.prodcons.IMessage;

public class ProdConsBufferV3 implements IProdConsBuffer {

	private Semaphore mutex, notFull, notEmpty;
	private List<MessageV3> buf;
	int size;
	int nbMsgMax = 0;
	int nbMsg = 0;

	public ProdConsBufferV3(int buffSize) {
		mutex = new Semaphore(1);
		notFull = new Semaphore(buffSize);
		notEmpty = new Semaphore(0);
		buf = new ArrayList<MessageV3>(buffSize);
		size = buffSize;
	}

	@Override
	public void put(IMessage m) throws InterruptedException {
		notFull.acquire();

		mutex.acquire();
		buf.add((MessageV3) m);
		nbMsg++;
		mutex.release();

		notEmpty.release();
	}

	@Override
	public MessageV3 get() throws InterruptedException {
		nbMsgMax--;

		notEmpty.acquire();

		mutex.acquire();
		MessageV3 m = buf.remove(0);
		nbMsg--;
		mutex.release();

		notFull.release();

		return m;
	}

	@Override
	public int nmsg() {
		return nbMsg;
	}

	public void setMaxMsg(int nbMsgTot) {
		nbMsgMax = nbMsgTot;
	}

	public boolean endMsg() {
		return nbMsgMax == 0;
	}
}
