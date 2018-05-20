package dsm;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class DSMCerrojo {
	
	CerrojoImpl cerrojo;
	AlmacenImpl almacen;
	
	
    public DSMCerrojo (String nom) throws RemoteException {
    	cerrojo = new CerrojoImpl();
    	almacen = new AlmacenImpl();
    }

    public void asociar(ObjetoCompartido o) {
    	
    }
    
    public void desasociar(ObjetoCompartido o) {
    	
    }
    
    public boolean adquirir(boolean exc) throws RemoteException {
    	
        return true;
    }
    
    public boolean liberar() throws RemoteException {
        return true;
   }
}
