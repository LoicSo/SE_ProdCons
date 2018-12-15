package jus.poc.prodcons.v1;

import jus.poc.prodcons.IProdConsBuffer;

import java.util.ArrayList;
import java.util.List;

import jus.poc.prodcons.IMessage;

public class ProdConsBufferV1 implements IProdConsBuffer {

	private List<MessageV1> buf;
	int size;
	int nbMsg = 0;
	int nbMsgMax = 0;

	public ProdConsBufferV1(int buffSize) {
		buf = new ArrayList<MessageV1>(buffSize);
		size = buffSize;
	}

	@Override
	public synchronized void put(IMessage m) throws InterruptedException {
		// On verifie qu'il y a de la place dans le buffer
		while (nbMsg >= size) {
			wait();
		}
		// On ajoute le message a la fin du buffer
		buf.add((MessageV1) m);
		nbMsg++;
		
		// Notification aux threads en attentes
		notifyAll();
	}

	@Override
	public synchronized MessageV1 get() throws InterruptedException {
		// Si l'on entre dans cette methode alors on consommera obligatoirement un message
		// On décrémente donc le nombre total de message restant pour que les autres threads ne viennent pas se bloquer
		// si il ne este plus de message
		nbMsgMax--;
		while (nbMsg <= 0) {
			// attente passive
			wait();
		}
		
		// On retire le premier message du buffer
		MessageV1 m = buf.remove(0);
		
		// traitement du message
		m.traitement(Thread.currentThread().getId());
		
		// Notification des threads en attente
		nbMsg--;
		notifyAll();
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
