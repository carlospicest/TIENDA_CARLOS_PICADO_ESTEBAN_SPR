package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.CancelacionPedidoDAO;
import curso.java.tienda.pojo.CancelacionPedido;

@Service
public class CancelacionPedidoService {

	@Autowired
	private CancelacionPedidoDAO cancelacionPedidoDao;

	public ArrayList<CancelacionPedido> getCancelacionPedido() {
		return (ArrayList<CancelacionPedido>) cancelacionPedidoDao.findAll();
	}

	public CancelacionPedido getCancelacionPedido(int id) {
		return cancelacionPedidoDao.findById(id);
	}

	public boolean addCancelacionPedido(CancelacionPedido cancelacionPedido) {

		try {
			cancelacionPedidoDao.save(cancelacionPedido);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}

	public boolean deleteCancelacionPedido(int id) {

		try {
			cancelacionPedidoDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}
	
}
