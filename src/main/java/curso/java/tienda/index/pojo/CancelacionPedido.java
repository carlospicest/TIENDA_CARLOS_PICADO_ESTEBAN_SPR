package curso.java.tienda.index.pojo;

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

@Entity(name = "cancelaciones_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelacionPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne()
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	
	@Column(name = "motivo")
	private String motivo;
	
	@Column(name = "estado")
	private String estado;
	
}
