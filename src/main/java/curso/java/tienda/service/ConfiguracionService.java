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
