package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.MetodoPagoDAO;
import curso.java.tienda.pojo.MetodoPago;

@Service
public class MetodoPagoService {

	@Autowired
	private MetodoPagoDAO metodoPagoDao;
	
	public ArrayList<MetodoPago> getMetodosPago() {
		return (ArrayList<MetodoPago>) metodoPagoDao.findAll();
	}

	public MetodoPago getMetodoPago(int id) {
		return metodoPagoDao.findById(id);
	}

	public boolean addMetodoPago(MetodoPago metodoPago) {
		try {
			metodoPagoDao.save(metodoPago);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
	}


	public boolean deleteMetodoPago(int id) {
		try {
			metodoPagoDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
		
	}
	
	
	
}
