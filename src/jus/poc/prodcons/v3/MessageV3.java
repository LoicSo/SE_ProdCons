package jus.poc.prodcons.v3;

import jus.poc.prodcons.IMessage;

public class MessageV3 implements IMessage {
	long contenu;
	int nbMsg;
	
	public MessageV3(long c, int nb) {
		contenu = c;
		nbMsg = nb;
	}
	
	@Override
	public boolean isAvailable() {
		return nbMsg != 0;
	}

	public String print(long idCons) {
		return ("Thread consommateur : " + idCons + " Thread Producteur : " + String.valueOf(contenu));
	}

}
