package jus.poc.prodcons.v3;

import jus.poc.prodcons.IMessage;

public class MessageV3 implements IMessage {
	long contenu;
	private int nbMsg;
	
	public MessageV3(long c, int nb) {
		contenu = c;
		nbMsg = nb;
	}
	
	@Override
	public boolean isAvailable() {
		return nbMsg != 0; // Le message est disponible tant qu'il reste des exemplaires
	}
	
	public int nbEx () {
		return nbMsg;
	}

	public void decrNbEx() {
		nbMsg--;
	}

	@Override
	public void traitement(long idCons) {
		System.out.println("Thread consommateur : " + idCons + " Thread Producteur : " + String.valueOf(contenu));
		
	}
}
