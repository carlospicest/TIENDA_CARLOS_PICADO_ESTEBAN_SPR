package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.PedidoDAO;
import curso.java.tienda.pojo.Pedido;

@Service
public class PedidoService {

	@Autowired
	private PedidoDAO pedidoDao;

	public ArrayList<Pedido> getPedidos() {
		return (ArrayList<Pedido>) pedidoDao.findAll();
	}

	public ArrayList<Pedido> getPedidosByEstado(String estado) {
		return pedidoDao.findByEstado(estado);
	}
	
	public Pedido getPedido(int id) {
		return pedidoDao.findById(id);
	}

	public boolean addPedido(Pedido pedido) {

		try {
			pedidoDao.save(pedido);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}

	public boolean deletePedido(int id) {

		try {
			pedidoDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}
	
}
