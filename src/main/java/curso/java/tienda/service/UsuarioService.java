package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.HashCrypt;

import curso.java.tienda.dao.UsuarioDAO;
import curso.java.tienda.pojo.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDao;

	public ArrayList<Usuario> getUsuarios(int idRol) {
		return usuarioDao.findAll(idRol);
	}
	
	public Usuario getUsuario(int id, int idRol) {
		return usuarioDao.findById(id, idRol);
	}

	public boolean addUsuario(Usuario usuario) {

		try {
			usuarioDao.save(usuario);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}

	public boolean deleteUsuario(int id) {

		try {
			usuarioDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}
	
	public Usuario setEncriptacion(Usuario usuario) {

		String salt = HashCrypt.generateSalt(32);

		String password = HashCrypt.generateHash(salt, usuario.getEmail() + usuario.getPassword());

		// Establecemos la password encriptada (email+password) en el usuario con su
		// respectiva salt.

		usuario.setSalt(salt);
		usuario.setPassword(password);

		return usuario;

	}
	
	/**
	 * Verificamos si al realizar la edición de un usuario se ha actualizado
	 * su contraseña.
	 * @param usuarioDb
	 * @param usuarioForm
	 * @return
	 */
	
	public Usuario verifyPasswordChanges(Usuario usuarioForm) {
		
		/* Primero comprobamos si se han producido cambios en la contraseña a través del
		 * formulario.
		 * 
		 * Si el campo de password del objeto usuarioForm es nulo es porque no los ha habido.
		 * En ese caso, simplemente introducimos la salt y password que ya tenía almacenado
		 * en la base de datos.
		 * 
		 * En caso contrario, generaremos una nueva salt y un password en base a la contraseña
		 * proporcionada mediante el formulario.
		 * */
		
		if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isEmpty()) { 
		
			String salt = HashCrypt.generateSalt(32);	
			String passwordUserForm = HashCrypt.generateHash(salt, usuarioForm.getEmail() + usuarioForm.getPassword());
		
			// Establecemos la información en el objeto usuarioForm.
			
			usuarioForm.setSalt(salt);
			usuarioForm.setPassword(passwordUserForm);
			
			// Verificación para ver
			
		} else {
			
			Usuario usuarioDb = getUsuario(usuarioForm.getId(), usuarioForm.getRol().getId());
			
			usuarioForm.setSalt(usuarioDb.getSalt());
			usuarioForm.setPassword(usuarioDb.getPassword());
			
		}
		
		
		return usuarioForm;
		
	}

}
