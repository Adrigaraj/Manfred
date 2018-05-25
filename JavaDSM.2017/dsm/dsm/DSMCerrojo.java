package dsm;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DSMCerrojo {
	private static final Logger log = Logger.getLogger(DSMCerrojo.class.getName());

	CerrojoImpl cerrojo;
	AlmacenImpl almacen;
	List<ObjetoCompartido> asociados = new ArrayList<>();

	public DSMCerrojo(String nom) throws RemoteException {
		cerrojo = new CerrojoImpl(nom);
		almacen = new AlmacenImpl();
	}

	public void asociar(ObjetoCompartido o) {
		asociados.add(o);
	}

	public void desasociar(ObjetoCompartido o) {
		asociados.remove(o);
	}

	public boolean adquirir(boolean exc) throws RemoteException {
		try {
			cerrojo.adquirir(exc);
			this.cerrojo.exc = exc;
			List<CabeceraObjetoCompartido> lcab = this.getCabeceras();
			List<ObjetoCompartido> actualizados = almacen.leerObjetos(lcab);
			for (int i = 0; actualizados != null && i < actualizados.size(); i++)
				for (int j = 0; asociados != null && j < asociados.size(); j++)
					if (asociados.get(j).getCabecera().equals(actualizados.get(i).getCabecera()) && asociados.get(j)
							.getCabecera().getVersion() < actualizados.get(i).getCabecera().getVersion())
						asociados.get(j).getCabecera().setVersion(actualizados.get(i).getCabecera().getVersion());
			return true;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error al adquirir el cerrojo:" + e.getMessage() + e.getStackTrace());
		}
		return false;
	}

	public boolean liberar() throws RemoteException {
		if (cerrojo.exc) {
			for (ObjetoCompartido obj : asociados)
				obj.getCabecera().setVersion(obj.getCabecera().getVersion() + 1);
			almacen.escribirObjetos(asociados);
		}
		return cerrojo.liberar();
	}

	private List<CabeceraObjetoCompartido> getCabeceras() {
		List<CabeceraObjetoCompartido> lcab = new ArrayList<>();
		for (int i = 0; i < asociados.size(); i++)
			lcab.add(asociados.get(i).getCabecera());
		return lcab;

	}
}
