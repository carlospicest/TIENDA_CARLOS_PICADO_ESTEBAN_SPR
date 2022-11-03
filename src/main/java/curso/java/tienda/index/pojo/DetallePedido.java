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

@Entity(name = "detalles_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne()
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	
	@ManyToOne()
	@JoinColumn(name = "id_producto")
	private Producto producto;
	
	@Column(name = "unidades")
	private int unidades;
	
	@Column(name = "precio_unidad")
	private double precio_unidad;
	
	@Column(name = "impuesto")
	private double impuesto;
	
	@Column(name = "total")
	private double total;
	
}