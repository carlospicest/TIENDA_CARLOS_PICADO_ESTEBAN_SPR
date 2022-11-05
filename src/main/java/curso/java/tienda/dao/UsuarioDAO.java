package curso.java.tienda.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
	
	@Query("from usuarios u where u.id = :idUsuario and u.rol.id = :idRol")
	public Usuario findById(@Param("idUsuario") int idUsuario, @Param("idRol") int idRol);
	
	@Query("from usuarios u where u.rol.id = :idRol")
	public ArrayList<Usuario> findAll(@Param("idRol") int idRol);
	
}
