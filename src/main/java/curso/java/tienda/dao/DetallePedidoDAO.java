package curso.java.tienda.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.java.tienda.pojo.DetallePedido;

public interface DetallePedidoDAO extends JpaRepository<DetallePedido, Integer> {
	
	public DetallePedido findById(int id);
	
	@Query("from detalles_pedido dp where dp.pedido.id = :pedidoId")
	public ArrayList<DetallePedido> findByPedidoId(int pedidoId);
	
}
