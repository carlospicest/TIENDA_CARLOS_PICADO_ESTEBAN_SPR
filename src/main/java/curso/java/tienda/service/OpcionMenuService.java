package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import curso.java.tienda.dao.OpcionMenuDAO;
import curso.java.tienda.pojo.OpcionMenu;
import curso.java.tienda.pojo.Usuario;
import datos.RoleData;

@Service
public class OpcionMenuService {

	@Autowired
	private OpcionMenuDAO opcionMenuDao;

	public ArrayList<OpcionMenu> getOpcionesMenu() {
		return (ArrayList<OpcionMenu>) opcionMenuDao.findAll();
	}

	public OpcionMenu getOpcionMenu(int id) {
		return opcionMenuDao.findById(id);
	}

	public boolean addOpcionMenu(OpcionMenu opcionMenu) {
		try {
			opcionMenuDao.save(opcionMenu);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}
	}

	public boolean deleteOpcionMenu(int id) {
		try {
			opcionMenuDao.deleteById(id);
			return true;
		} catch (DataAccessException ex) {
			return false;
		}

	}
	
	/**
	 * Obtiene todas las opciones del menú y las clasifica por roles.
	 * @return
	 */
	
	public HashMap<String, HashMap<Integer, OpcionMenu>> getOpcionMenu() {

		// Obtenemos todas las opciones del menú.
		
		ArrayList<OpcionMenu> opcionMenuList = getOpcionesMenu();

		/*
		 * Este HashMap contendrá como clave primaria un String con el alias de la
		 * opción del menú.
		 * 
		 * Como valor se le asigna otro HashMap cuya clave se compone por ID del rol
		 * y valor la OpcionMenu.
		 * 
		 * De esta manera, cada opción tendrá un HashMap con los roles habilitados para
		 * poder ver dicha opción.
		 * 
		 * */
		
		HashMap<String, HashMap<Integer, OpcionMenu>> opcionesMenu = new HashMap<>();

		for (OpcionMenu opcion : opcionMenuList) {
			
			// Si la opción no existe, se agrega.
			
			if (!opcionesMenu.containsKey(opcion.getOpcion().getAlias())) {
				
				// HashMap que se completará con el rol de esa opción.
				
				HashMap<Integer, OpcionMenu> rolesByOpcion = new HashMap<>();
				
				rolesByOpcion.put(opcion.getRol().getId(), opcion);
				
				// Vinculamos el HashMap con roles de la opción a su respectiva clave en el HashMap principal.
				
				opcionesMenu.put(opcion.getOpcion().getAlias(), rolesByOpcion);
				
			} else {
				
				// Si la opción ya existe, obtenemos el HashMap de roles para esa opción y le agregamos el rol.
				
				HashMap<Integer, OpcionMenu> rolesByOpcion = opcionesMenu.get(opcion.getOpcion().getAlias());
				
				rolesByOpcion.put(opcion.getRol().getId(), opcion);
				
			}
			
		}

		return opcionesMenu;

	}
	
	public HashMap<String, OpcionMenu> getOpcionMenuByRol(int idRol) {

		ArrayList<OpcionMenu> opcionMenuList = opcionMenuDao.findByRol(idRol);
		
		HashMap<String, OpcionMenu> opcionMenu = new HashMap<String, OpcionMenu>();
		
		for (OpcionMenu opcion : opcionMenuList) {
			opcionMenu.put(opcion.getOpcion().getAlias(), opcion);
		}
		
		return opcionMenu;
		
	}

}
