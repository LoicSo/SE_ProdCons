package jus.poc.prodcons;

public interface IProdConsBuffer {

	/**
	 * put m in the prodcons buffer
	 **/
	public void put(IMessage m) throws InterruptedException;

	/**
	 * retrieve a message from the prodcons buffer, following a fifo order
	 **/
	public IMessage get() throws InterruptedException;

	/**
	 * returns the number of messages currently available in the prodcons buffer
	 **/
	public int nmsg();
	
	/**
	 * Increase the total number of messages that the buffer will have during all the application 
	 */
	public void incrTotMes (int n);
	
}