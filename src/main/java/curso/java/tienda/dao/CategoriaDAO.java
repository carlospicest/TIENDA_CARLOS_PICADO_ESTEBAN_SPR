package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Categoria;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Integer> {
	
	public Categoria findById(int id);
	
}
