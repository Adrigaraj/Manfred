package dsm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

	private static final long serialVersionUID = 1L;

	String nombre;
	boolean exc;
	int numLectores;
	int numEscritores;
	boolean adquirido;

	CerrojoImpl(String nombre) throws RemoteException {
		this.nombre = nombre;
		this.numEscritores = 0;
		this.numLectores = 0;
		this.adquirido = false;
	}

	@Override
	public synchronized void adquirir(boolean exc) throws RemoteException {
		synchronized (this.nombre) {
			if (exc) {
				while ((numLectores > 0 || numEscritores > 0)) {
					try {
						nombre.wait();
					} catch (InterruptedException e) {
						System.out.println(e.getStackTrace() + e.getMessage());
					}
				}
				numEscritores++;
			} else {
				while (numEscritores > 0) {
					try {
						nombre.wait();
					} catch (InterruptedException e) {
						System.out.println(e.getStackTrace() + e.getMessage());
					}
				}
				adquirido = true;
				numLectores++;
			}
		}
	}

	@Override
	public synchronized boolean liberar() throws RemoteException {
		synchronized (this.nombre) {
			if (exc) {
				if (numEscritores > 0) { // Está adquirido por un escritor
					numEscritores--;
					nombre.notify();
					return true;
				} else
					return false;
			} else if (numLectores > 0) { // Está adquirido por un lector
				numLectores--;
				nombre.notify();
				return true;
			} else
				return false;
		}
	}
}
