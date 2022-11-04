package curso.java.tienda.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.ProductoDAO;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoDAO productoDao;

	@Override
	public ArrayList<Producto> getProductos() {
		return (ArrayList<Producto>) productoDao.findAll();
	}

	@Override
	public Producto getProducto(int id) {
		return productoDao.findById(id);
	}

	@Override
	public boolean addProducto(Producto producto) {
		
		try {
			productoDao.save(producto);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}

	@Override
	public boolean updateProducto(Producto producto) {
		
		try {
			productoDao.save(producto);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
		
	}

	@Override
	public boolean deleteProducto(int id) {
		
		try {
			productoDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
		
	}

}
