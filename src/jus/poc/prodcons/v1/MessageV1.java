package jus.poc.prodcons.v1;

import jus.poc.prodcons.IMessage;

public class MessageV1 implements IMessage {
	long contenu;
	
	public MessageV1(long c) {
		contenu = c;
	}
	
	@Override
	public boolean isAvailable() {
		return true;
	}

	@Override
	public void traitement(long idCons) {
		System.out.println("Thread consommateur : " + idCons + " Thread Producteur : " + String.valueOf(contenu));
		
	}

}
