package jus.poc.prodcons;


public interface IMessage {
	
	/**
	 * Indique si le message peut être consommé
	 */
	public boolean isAvailable();
	
	/**
	 * Fonction de traitement du message
	 */
	public void traitement(long idCons);
	
}
