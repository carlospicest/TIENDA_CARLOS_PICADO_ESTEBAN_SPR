package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.CategoriaDAO;
import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.service.CategoriaService;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaDAO categoriaDao;

	public ArrayList<Categoria> getCategorias() {
		return (ArrayList<Categoria>) categoriaDao.findAll();
	}


	public Categoria getCategoria(int id) {
		return categoriaDao.findById(id);
	}


	public boolean addCategoria(Categoria categoria) {
		try {
			categoriaDao.save(categoria);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
	}


	public boolean deleteCategoria(int id) {
		try {
			categoriaDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
		
	}
	
	public HashMap<Integer, Categoria> getRandomCategorias(int longitud) {
		
		ArrayList<Categoria> categorias = getCategorias();
		
		if (longitud > categorias.size() ) {
			return null;
		} else {
			
			HashMap<Integer, Categoria> categoriasRandom = new HashMap<>();
			Random random = new Random();

			do {

				int number = random.nextInt(categorias.size());
				
				if (categoriasRandom.get(number) == null) {
					Categoria categoria = categorias.get(number);
					categoriasRandom.put(number, categoria);
				}
			
			} while(categoriasRandom.size() < longitud);
			
			return categoriasRandom;
			
		}
		
	}

}
