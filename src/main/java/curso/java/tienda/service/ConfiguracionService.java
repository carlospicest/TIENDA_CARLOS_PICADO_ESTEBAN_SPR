package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.ConfiguracionDAO;
import curso.java.tienda.pojo.Configuracion;

@Service
public class ConfiguracionService {

	@Autowired
	ConfiguracionDAO configuracionDao;
	
	public HashMap<String, Configuracion> getConfiguraciones() {
	
		HashMap<String, Configuracion> configuracionList = new HashMap<>();
		
		ArrayList<Configuracion> configuraciones = (ArrayList<Configuracion>) configuracionDao.findAll();
		
		for (Configuracion configuracion : configuraciones) {
			configuracionList.put(configuracion.getClave(), configuracion);
		}
		
		return configuracionList;
		
	}

	public Configuracion getConfiguracion(int id) {
		return configuracionDao.findById(id);
	}

	public Configuracion getConfiguracion(String clave) {
		return configuracionDao.findByClave(clave);
	}
	
	public String generarNumeroFactura() {
		
		Configuracion configuracion = getConfiguracion("num_factura");
		String factura = null;
		
		if (configuracion.getValor() != null && !configuracion.getValor().isEmpty()) {
			// Obtenemos el número actual. 
			int numFactura = Integer.parseInt(configuracion.getValor());
			factura = String.valueOf(numFactura);
			// Incrementamos para la próxima factura.
			numFactura++;
			configuracion.setValor(String.valueOf(numFactura));
			addConfiguracion(configuracion);
		}
		
		return factura;
		
	}
	
	public boolean addConfiguracion(Configuracion configuracion) {
		try {
			configuracionDao.save(configuracion);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
	}

	public boolean deleteConfiguracion(int id) {
		try {
			configuracionDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
		
	}
	
}
