package curso.java.tienda.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.OpcionMenu;

@Repository
public interface OpcionMenuDAO extends JpaRepository<OpcionMenu, Integer> {

	public OpcionMenu findById(int id);
	
	@Query("from opciones_menu om where om.rol.id = :idRol")
	public ArrayList<OpcionMenu> findByRol(int idRol);
	
}
