package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Rol;

@Repository
public interface RolDAO extends JpaRepository<Rol, Integer> {
	
	public Rol findById(int id);
	
	public Rol findByRol(int id);
	
}
