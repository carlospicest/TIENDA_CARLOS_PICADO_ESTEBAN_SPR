package curso.java.tienda.service;

import java.util.ArrayList;

import curso.java.tienda.pojo.Producto;

public interface ProductoService {

	public ArrayList<Producto> getProductos();
	
	public Producto getProducto(int id);
	
	public boolean addProducto(Producto producto);
	
	public boolean updateProducto(Producto producto);
	
	public boolean deleteProducto(int id);
	
}
