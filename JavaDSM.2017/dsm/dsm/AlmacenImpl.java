package dsm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AlmacenImpl extends UnicastRemoteObject implements Almacen {

	private static final long serialVersionUID = 1L;
	List<ObjetoCompartido> objetosAlmacenados;

	public AlmacenImpl() throws RemoteException {
		objetosAlmacenados = new ArrayList<>();
	}

	@Override
	public synchronized List<ObjetoCompartido> leerObjetos(List<CabeceraObjetoCompartido> lcab) throws RemoteException {
		List<ObjetoCompartido> dev = new ArrayList<>();
		for (int i = 0; i < lcab.size(); i++) {
			for (int j = 0; j < objetosAlmacenados.size(); j++) {
				if (lcab.get(i).getNombre().equals(objetosAlmacenados.get(j).getCabecera().getNombre())
						&& lcab.get(i).getVersion() < objetosAlmacenados.get(j).getCabecera().getVersion())
					dev.add(objetosAlmacenados.get(j));
			}
		}
		if (dev.isEmpty())
			return null;
		return dev;
	}

	@Override
	public synchronized void escribirObjetos(List<ObjetoCompartido> loc) throws RemoteException {
		objetosAlmacenados.addAll(loc);
	}
}
