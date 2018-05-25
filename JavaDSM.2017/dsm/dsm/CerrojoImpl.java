package dsm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

	private static final long serialVersionUID = 1L;

	String nombre;
	ReentrantLock cerrojo;
	boolean exc;
	int numLectores;
	int numEscritores;
	Condition cond;

	CerrojoImpl(String nombre) throws RemoteException {
		this.nombre = nombre;
		this.cerrojo = new ReentrantLock();
		this.numEscritores = 0;
		this.numLectores = 0;
		cond = this.cerrojo.newCondition();
	}

	@Override
	public synchronized void adquirir(boolean exc) throws RemoteException {
		if (exc) {
			while ((numLectores > 0 || numEscritores > 0)) {
				try {
					cond.await();
				} catch (InterruptedException e) {
					System.out.println(e.getStackTrace() + e.getMessage());
				}
			}
			numEscritores++;
			cerrojo.lock();
		} else {
			while (numEscritores > 0)
				try {
					cond.await();
				} catch (InterruptedException e) {
					System.out.println(e.getStackTrace() + e.getMessage());
				}
			numLectores++;
		}
	}

	@Override
	public synchronized boolean liberar() throws RemoteException {
		if (exc) {
			if (!cerrojo.isLocked())
				return false;
			else
				numEscritores--;
		} else
			numLectores--;
		cond.signal();
		return true;
	}
}
