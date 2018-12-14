package jus.poc.prodcons.v2;

import jus.poc.prodcons.IProdConsBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import jus.poc.prodcons.IMessage;

public class ProdConsBufferV2 implements IProdConsBuffer {
	
	private Semaphore mutex, notFull, notEmpty;
	private List<MessageV2> buf;
	int size;
	int nbMsgCons = 0;
	int nbMsg = 0;

	public ProdConsBufferV2(int buffSize) {
		mutex = new Semaphore(1);
		notFull = new Semaphore(buffSize);
		notEmpty = new Semaphore(0);
		buf = new ArrayList<MessageV2>(buffSize);
		size = buffSize;
	}

	@Override
	public void put(IMessage m) throws InterruptedException {
		notFull.acquire();
		
		mutex.acquire();
		buf.add((MessageV2) m);
		nbMsg++;
		mutex.release();
		
		notEmpty.release();		
	}

	@Override
	public MessageV2 get() throws InterruptedException {
		if (nbMsgCons < TestProdCons.nbMsgTot) {
			nbMsgCons++;
			
			notEmpty.acquire();
			
			mutex.acquire();
			MessageV2 m = buf.remove(0);
			nbMsg--;
			mutex.release();
			
			notFull.release();
			
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
