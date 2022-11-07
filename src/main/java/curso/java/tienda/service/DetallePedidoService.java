package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.DetallePedidoDAO;
import curso.java.tienda.pojo.DetallePedido;

@Service
public class DetallePedidoService {

	@Autowired
	private DetallePedidoDAO detallePedidoDao;

	public ArrayList<DetallePedido> getDetallePedidos() {
		return (ArrayList<DetallePedido>) detallePedidoDao.findAll();
	}

	public DetallePedido getDetallePedido(int id) {
		return detallePedidoDao.findById(id);
	}

	public boolean addDetallePedido(DetallePedido detallePedido) {

		try {
			detallePedidoDao.save(detallePedido);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}

	public boolean deleteDetallePedido(int id) {

		try {
			detallePedidoDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}
	
}
