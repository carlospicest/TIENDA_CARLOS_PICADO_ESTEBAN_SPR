package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HashCrypt;

import curso.java.tienda.pojo.Usuario;

@Service
public class SesionUsuarioService {

	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Comprueba los campos de email y password recibidos por formulario.
	 * 
	 * Si los datos son correctos, se devuelve un objeto de tipo Usuario pero sin la
	 * información sensible del usuario.
	 * 
	 * @param email
	 * @param password
	 * @return
	 */

	public Usuario validarCredenciales(Usuario usuario) {

		Usuario user = usuarioService.getUsuarioByEmail(usuario.getEmail());

		if (user != null) {

			String saltStored = user.getSalt();
			String passwordStored = user.getPassword();

			if (HashCrypt.isSame(saltStored, passwordStored, usuario.getEmail() + usuario.getPassword())) {
				
				user = usuarioService.getUsuarioMixin(user);
				
				return user; // Credenciales correcta.
			} else {
				return null; // Credenciales incorrectas.
			}

		}

		return null; // El usuario no existe.

	}
	
}
