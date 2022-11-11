package curso.java.tienda.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Producto;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Integer> {
	
	public Producto findById(int id);
	
	@Query("from productos p")
	public ArrayList<Producto> findAllWithoutFilter();
	
	@Query("from productos p where p.stock > 0 and p.baja is false")
	public ArrayList<Producto> findAll();
	
	@Query("from productos p where p.categoria.id = :id")
	public ArrayList<Producto> findByCategoria(int id);
	
}
