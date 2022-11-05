package curso.java.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Pedido;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Integer>{

	public Pedido findById(int id);
	
}
