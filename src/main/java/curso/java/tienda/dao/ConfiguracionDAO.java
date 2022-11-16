package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Configuracion;

@Repository
public interface ConfiguracionDAO extends JpaRepository<Configuracion, Integer> {

	public Configuracion findById(int id);
	
	@Query("from configuracion c where c.clave = :clave")
	public Configuracion findByClave(String clave);
	
}
