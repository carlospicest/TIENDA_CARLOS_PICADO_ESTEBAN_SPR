package curso.java.tienda.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.HashCrypt;

import curso.java.tienda.dao.UsuarioDAO;
import curso.java.tienda.pojo.Usuario;
import datos.RoleData;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDao;
	@Autowired
	private EntityManager entityManager;

	public Usuario getUsuario(int id) {
		return usuarioDao.findById(id);
	}

	public ArrayList<Usuario> getUsuarios() {
		return (ArrayList<Usuario>) usuarioDao.findAll();
	}

	public ArrayList<Usuario> getUsuariosByRolSession(RoleData.rol rol) {

		ArrayList<Usuario> usuarioList = new ArrayList<>();

		switch (rol) {

		case ADMINISTRADOR: // Solo puede gestionar clientes y empleados.

			usuarioList = (ArrayList<Usuario>) entityManager
					.createQuery("from usuarios u where not u.rol.id = :rolId order by u.rol.id")
					.setParameter("rolId", RoleData.rol.ADMINISTRADOR.getId())
					.getResultList();

			break;

		case EMPLEADO: // Solo puede gestionar clientes.

			usuarioList = (ArrayList<Usuario>) entityManager
					.createQuery("from usuarios u where u.rol.id = :rolId order by u.rol.id")
					.setParameter("rolId", RoleData.rol.CLIENTE.getId())
					.getResultList();

			break;

		}

		return usuarioList;

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
	 * Verificamos si al realizar la edición de un usuario se ha actualizado su
	 * contraseña.
	 * 
	 * @param usuarioDb
	 * @param usuarioForm
	 * @return
	 */

	public Usuario verifyPasswordChanges(Usuario usuarioForm) {

		/*
		 * Primero comprobamos si se han producido cambios en la contraseña a través del
		 * formulario.
		 * 
		 * Si el campo de password del objeto usuarioForm es nulo es porque no los ha
		 * habido. En ese caso, simplemente introducimos la salt y password que ya tenía
		 * almacenado en la base de datos.
		 * 
		 * En caso contrario, generaremos una nueva salt y un password en base a la
		 * contraseña proporcionada mediante el formulario.
		 */

		if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isEmpty()) {

			String salt = HashCrypt.generateSalt(32);
			String passwordUserForm = HashCrypt.generateHash(salt, usuarioForm.getEmail() + usuarioForm.getPassword());

			// Establecemos la información en el objeto usuarioForm.

			usuarioForm.setSalt(salt);
			usuarioForm.setPassword(passwordUserForm);

			// Verificación para ver

		} else {

			Usuario usuarioDb = getUsuario(usuarioForm.getId());

			usuarioForm.setSalt(usuarioDb.getSalt());
			usuarioForm.setPassword(usuarioDb.getPassword());

		}

		return usuarioForm;

	}

}
