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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "usuarios")
@DynamicUpdate
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

	@NotBlank(message = "El email no puede estar en blanco.")
	@NotNull(message = "El email no puede ser vacío.")
	@Email(message = "Debe proporcionar un email válido.")
	@Column(name = "email")
	private String email;
	

	@Size(min = 4, message = "La contraseña debe tener mínimo cuatro caracteres.")
	@NotBlank(message = "La contraseña no puede estar en blanco.")
	@NotNull(message = "La contraseña no puede estar vacía.")
	@Column(name = "password")
	private String password;
	
	@Column(name = "salt")
	private String salt;
	
	@NotBlank(message = "El nombre no puede estar en blanco.")
	@NotNull(message = "El nombre no puede estar vacío.")
	@Column(name = "nombre")
	private String nombre;
	
	@NotBlank(message = "El primer apellido no puede estar en blanco.")
	@NotNull(message = "El primer apellido no puede estar vacío.")
	@Column(name = "apellido1")
	private String apellido1;
	
	@NotBlank(message = "El segundo apellido no puede estar en blanco.")
	@NotNull(message = "El segundo apellido no puede estar vacío.")
	@Column(name = "apellido2")
	private String apellido2;
	
	@NotBlank(message = "La dirección no puede estar en blanco.")
	@NotNull(message = "La dirección no puede estar vacía.")
	@Column(name = "direccion")
	private String direccion;
	
	@NotBlank(message = "La provincia no puede estar en blanco.")
	@NotNull(message = "La provincia no puede estar vacía.")
	@Column(name = "provincia")
	private String provincia;
	
	@NotBlank(message = "La localidad no puede estar en blanco.")
	@NotNull(message = "La localidad no puede estar vacía.")
	@Column(name = "localidad")
	private String localidad;
	
	@NotBlank(message = "El teléfono no puede estar en blanco.")
	@NotNull(message = "El teléfono no puede estar vacío.")
	@Column(name = "telefono")
	private String telefono;
	
	@NotBlank(message = "El dni no puede estar en blanco.")
	@NotNull(message = "El dni no puede estar vacío.")
	@Column(name = "dni")
	private String dni;
	
	@Column(name = "imagen", nullable = true)
	private String imagen;
	@Column(name = "baja", columnDefinition = "TINYINT", nullable = true)
	private boolean baja;
	@Column(name = "fecha_alta")
	private Timestamp fecha_alta;
	
}
