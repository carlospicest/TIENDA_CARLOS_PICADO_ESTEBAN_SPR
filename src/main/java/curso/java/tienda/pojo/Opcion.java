package curso.java.tienda.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "opciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opcion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "alias")
	private String alias;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "url")
	private String url;
	
}
