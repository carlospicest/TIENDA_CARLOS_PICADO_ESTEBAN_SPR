package curso.java.tienda.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.HashCrypt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import curso.java.tienda.dao.UsuarioDAO;
import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.pojo.mixin.UsuarioMixin;
import datos.RoleData;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDao;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ObjectMapper mapper;

	public Usuario getUsuario(int id) {
		return usuarioDao.findById(id);
	}

	public Usuario getUsuarioByEmail(String email) {
		return usuarioDao.findByEmail(email);
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
					.setParameter("rolId", RoleData.rol.ADMINISTRADOR.getId()).getResultList();

			break;

		case EMPLEADO: // Solo puede gestionar clientes.

			usuarioList = (ArrayList<Usuario>) entityManager
					.createQuery("from usuarios u where u.rol.id = :rolId order by u.rol.id")
					.setParameter("rolId", RoleData.rol.CLIENTE.getId()).getResultList();

			break;

		}

		return usuarioList;

	}
	
	public ArrayList<Usuario> getMixinUsuarios(ArrayList<Usuario> usuarios) {
		
		ArrayList<Usuario> usuariosMixin = new ArrayList<>();
		
		for (Usuario usuario : usuarios) {
			Usuario usuarioMixin = getUsuarioMixin(usuario);
			usuariosMixin.add(usuarioMixin);
		}
		
		return usuariosMixin;
		
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

	public String getUserData(Usuario user) {

		String userDataJSON = null;

		try {

			mapper.addMixIn(Usuario.class, UsuarioMixin.class);

			userDataJSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);

		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}

		return userDataJSON;

	}

	/**
	 * Devuelve un objeto al que se le ha aplicado una clase MixIn mediante Jackson.
	 * 
	 * Una clase MixInes un nuevo POJO que se basa sobre un POJO existente
	 * permitiendo aplicar modificaciones a este sin afectar al POJO original.
	 * 
	 * En este caso, se emplea para ocultar información sensible, como por ejemplo:
	 * - Contraseña. - Salt. - Fecha de alta.
	 * 
	 * @param usuario
	 * @return
	 */

	public Usuario getUsuarioMixin(Usuario usuario) {

		Usuario usuarioProtegido = null;

		

		try {

			byte[] usuarioDataMixin = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(usuario);

			mapper.addMixIn(Usuario.class, UsuarioMixin.class);
			
			usuarioProtegido = mapper.readValue(usuarioDataMixin, Usuario.class);

			System.out.println("");

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuarioProtegido;

	}
	
	public boolean isDatosUsuarioValidos(Usuario usuario) {
		
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			return false;
		}
		
		if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
			return false;
		}
		
		if (usuario.getApellido1() == null || usuario.getApellido1().isEmpty()) {
			return false;
		}
		
		if (usuario.getApellido2() == null || usuario.getApellido2().isEmpty()) {
			return false;
		}
		
		if (usuario.getDireccion() == null || usuario.getDireccion().isEmpty()) {
			return false;
		}
		
		if (usuario.getProvincia() == null || usuario.getProvincia().isEmpty()) {
			return false;
		}
		
		if (usuario.getLocalidad() == null || usuario.getLocalidad().isEmpty()) {
			return false;
		}
		
		if (usuario.getTelefono() == null || usuario.getTelefono().isEmpty()) {
			return false;
		}
		
		if (usuario.getDni() == null || usuario.getDni().isEmpty()) {
			return false;
		}
		
		return true;
		
	}

}
