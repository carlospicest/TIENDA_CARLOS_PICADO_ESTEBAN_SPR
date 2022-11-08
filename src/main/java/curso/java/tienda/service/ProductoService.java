package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.ProductoDAO;
import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.pojo.Producto;

@Service
public class ProductoService {

	@Autowired
	private ProductoDAO productoDao;
	@Autowired
	private CategoriaService categoriaService;

	public ArrayList<Producto> getProductos() {
		return (ArrayList<Producto>) productoDao.findAll();
	}

	public ArrayList<Producto> getProductosByCategoria(int id) {
		return productoDao.findByCategoria(id);
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

	public HashMap<Categoria, ArrayList<Producto>> getRandomCategorias(int longitud) {
		

		HashMap<Categoria, ArrayList<Producto>> randomCategorias = new HashMap<>();
		ArrayList<Categoria> categoriasList = categoriaService.getCategorias();
		
		
		if (longitud > categoriasList.size()) {
			return null;
		} else {

			Random random = new Random();

			do {

				int number = random.nextInt(categoriasList.size());
				Categoria categoria = categoriasList.get(number);

				if (randomCategorias.get(categoria) == null) {
					
					ArrayList<Producto> productosByCategoria = getProductosByCategoria(categoria.getId());
					
					if (productosByCategoria != null && !productosByCategoria.isEmpty()) {
						
						for (Producto pro : productosByCategoria) {
							
							System.err.println("Categoria => " + pro.getCategoria().getNombre() + " Articulo => " + pro.getNombre());
							
						}
						
						randomCategorias.put(categoria, productosByCategoria);
					}
				}
			
			} while(randomCategorias.size() < longitud);
			
			return randomCategorias;
			
		}
		
	}

}
