package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.MetodoPago;

@Repository
public interface MetodoPagoDAO extends JpaRepository<MetodoPago, Integer>{

	public MetodoPago findById(int id);
	
}
