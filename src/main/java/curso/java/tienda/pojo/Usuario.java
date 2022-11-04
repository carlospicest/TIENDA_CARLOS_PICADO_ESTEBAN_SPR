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


@Entity(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_rol")
	private Rol rol;

	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "salt")
	private String salt;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido1")
	private String apellido1;
	@Column(name = "apellido2")
	private String apellido2;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "provincia")
	private String provincia;
	@Column(name = "localidad")
	private String localidad;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "dni")
	private String dni;
	@Column(name = "imagen", nullable = true)
	private String imagen;
	@Column(name = "baja", columnDefinition = "TINYINT", nullable = true)
	private boolean baja;
	@Column(name = "fecha_alta")
	private Timestamp fecha_alta;
	
}
