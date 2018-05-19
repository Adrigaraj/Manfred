package dsm;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class FabricaCerrojosImpl extends UnicastRemoteObject implements FabricaCerrojos {

	private static final long serialVersionUID = 1L;

	public FabricaCerrojosImpl() throws RemoteException {
		
    }
	
    public synchronized	Cerrojo iniciar(String s) throws RemoteException {
    	
    	return null;
    
    }
    
}

