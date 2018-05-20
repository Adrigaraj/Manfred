package dsm;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public class AlmacenImpl extends UnicastRemoteObject implements Almacen {
	
	private static final long serialVersionUID = 1L;
	List<ObjetoCompartido> almacen;
	
	public AlmacenImpl() throws RemoteException {
		almacen = new ArrayList<>();
	}
    
	public synchronized	List<ObjetoCompartido> leerObjetos(List<CabeceraObjetoCompartido> lcab)
      throws RemoteException {
		List<ObjetoCompartido> dev = new ArrayList<>();
		for(int i = 0; i < lcab.size(); i++) {
			for(int j = 0; j < almacen.size(); j++) {
				if( lcab.get(i).getNombre().equals(almacen.get(j).getCabecera().getNombre()) 
						&& lcab.get(i).getVersion() < almacen.get(j).getCabecera().getVersion()) 
					dev.add(almacen.get(j));
			}
		}
		if(dev.isEmpty())
		 return null;
		return dev;
    }
	
    public synchronized void escribirObjetos(List<ObjetoCompartido> loc)
     throws RemoteException  {
    	almacen.addAll(loc);
    }
}

