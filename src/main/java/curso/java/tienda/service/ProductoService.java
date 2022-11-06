package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.ProductoDAO;
import curso.java.tienda.pojo.Producto;

@Service
public class ProductoService {

	@Autowired
	private ProductoDAO productoDao;
	
	public ArrayList<Producto> getProductos() {
		return (ArrayList<Producto>) productoDao.findAll();
	}

	public Producto getProducto(int id) {
		return productoDao.findById(id);
	}

	public boolean addProducto(Producto producto) {

		try {
			productoDao.save(producto);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}

	public boolean deleteProducto(int id) {

		try {
			productoDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}

}
