package curso.java.tienda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.ProductoDAO;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoDAO productoDao;
	
	@Override
	public Producto getProducto(int id) {
		
		return productoDao.findById(id);
		
	}

}
