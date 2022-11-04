package curso.java.tienda.pojo;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
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

@Entity(name = "valoraciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Valoracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_producto")
	private Producto producto;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "valoracion")
	private int valoracion;
	
	@Column(name = "comentario")
	private String comentario;
	
	@Column(name = "fecha")
	private Timestamp fecha;
	
}
