package jus.poc.prodcons.v2;

import jus.poc.prodcons.IMessage;

public class MessageV2 implements IMessage {
	long contenu;
	
	public MessageV2(long c) {
		contenu = c;
	}
	
	@Override
	public boolean isAvailable() {
		return true;
	}

	public String print(long idCons) {
		return ("Thread consommateur : " + idCons + " Thread Producteur : " + String.valueOf(contenu));
	}

}
