package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
	
	public Usuario findById(int id);
	
	@Query("from usuarios u where u.email = :email")
	public Usuario findByEmail(@Param("email") String email);
	
}
