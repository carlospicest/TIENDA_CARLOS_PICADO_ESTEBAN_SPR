package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Producto;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Integer> {
	
	public Producto findById(int id);
	
}
