package dsm;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class FabricaCerrojosImpl extends UnicastRemoteObject implements FabricaCerrojos {

	private static final long serialVersionUID = 1L;
	
	TreeMap<String, CerrojoImpl> cerrojos;
	
	public FabricaCerrojosImpl() throws RemoteException {
		cerrojos = new TreeMap<>();
    }
	
    public synchronized	Cerrojo iniciar(String s) throws RemoteException {
    	if(!cerrojos.containsKey(s)) {
    		cerrojos.put(s, new CerrojoImpl());
    	}
    	return cerrojos.get(s);
    }
    
}

