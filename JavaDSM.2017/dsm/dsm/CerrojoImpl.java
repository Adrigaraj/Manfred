package dsm;
import java.rmi.*;
import java.rmi.server.*;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

	private static final long serialVersionUID = 1L;

	CerrojoImpl() throws RemoteException {
    }

    public synchronized void adquirir (boolean exc) throws RemoteException {
    }
    public synchronized boolean liberar() throws RemoteException {
        return true;
    }
}
