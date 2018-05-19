package dsm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

	private static final long serialVersionUID = 1L;
	
	private boolean abierto;
	
	
	CerrojoImpl() throws RemoteException {
		this.abierto = true;
	}

	@Override
	public synchronized void adquirir(boolean exc) throws RemoteException {
	}

	@Override
	public synchronized boolean liberar() throws RemoteException {
		return abierto ? !(abierto = false) : false;
	}
}
