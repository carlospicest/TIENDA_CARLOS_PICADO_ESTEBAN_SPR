package curso.java.tienda.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "configuracion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuracion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "valor")
	private String valor;
	
	@Column(name = "tipo")
	private String tipo;
	
}
