package jus.poc.prodcons.v1;

import jus.poc.prodcons.IProdConsBuffer;
import jus.poc.prodcons.IMessage;

public class ProdConsBuffer implements IProdConsBuffer {

	@Override
	public void put(IMessage m) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMessage get() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nmsg() {
		// TODO Auto-generated method stub
		return 0;
	}

}
