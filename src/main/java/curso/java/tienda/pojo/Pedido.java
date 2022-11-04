package curso.java.tienda.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne()
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "fecha")
	private Timestamp fecha;
	
	@Column(name = "metodo_pago")
	private String metodo_pago;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "num_factura")
	private String num_factura;
	
	@Column(name = "total")
	private double total;
	
}
