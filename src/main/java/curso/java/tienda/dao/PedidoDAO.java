package curso.java.tienda.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import curso.java.tienda.pojo.Pedido;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Integer>{

	public Pedido findById(int id);
	
	@Query("from pedidos p where p.usuario.id = :usuarioId")
	public ArrayList<Pedido> findByUsuario(@Param("usuarioId") int usuarioId);
	
}
