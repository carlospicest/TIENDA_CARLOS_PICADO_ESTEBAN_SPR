package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.CancelacionPedido;

@Repository
public interface CancelacionPedidoDAO extends JpaRepository<CancelacionPedido, Integer>{

	public CancelacionPedido findById(int id);
	
}
