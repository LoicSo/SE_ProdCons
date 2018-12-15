package jus.poc.prodcons.v2;

import jus.poc.prodcons.IProdConsBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import jus.poc.prodcons.IMessage;

public class ProdConsBufferV2 implements IProdConsBuffer {

	// 3 Semaphore : 1 pour la protection des donnees (mutex), 1 pour representer les cases pleines (notEmpty) et 1 pour les cases vides (notFull)
	private Semaphore mutex, notFull, notEmpty;
	private List<MessageV2> buf;
	int size;
	int nbMsgMax = 0;
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
		// On acquiert une case vide
		notFull.acquire();

		// on acquiert les donnees protegees
		mutex.acquire();
		buf.add((MessageV2) m);
		nbMsg++;
		
		// on relache les donnees
		mutex.release();

		// On indique qu'une case est pleine
		notEmpty.release();
	}

	@Override
	public MessageV2 get() throws InterruptedException {
		nbMsgMax--;
		
		// On acquiert une case pleine
		notEmpty.acquire();

		// On acquiert les donnees protegees
		mutex.acquire();
		MessageV2 m = buf.remove(0);
		m.traitement(Thread.currentThread().getId());
		nbMsg--;
		
		// On relache les donnees
		mutex.release();

		// On indique qu'une cases est vide
		notFull.release();

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
