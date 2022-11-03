package curso.java.tienda.util;

import java.util.HashMap;

import curso.java.tienda.index.pojo.OpcionMenu;
import curso.java.tienda.index.pojo.Rol;

public class RoleDataUtil {

	private static Rol userRol;
	private static HashMap<String, OpcionMenu> opcionMenuList;

	/**
	 * Se agregan todas las opciones que van a ser utilizadas por el
	 * sistema de roles.
	 * @param rol
	 * @param opcionMenu
	 */
	
	public static void fillOpcionMenu(Rol rol, HashMap<String, OpcionMenu> opcionMenu) {

		if (rol != null && opcionMenu != null) {

			userRol = rol;
			opcionMenuList = opcionMenu;
			
		}

	}

	public static void setRol(Rol rol) {
		userRol = rol;
	}
	
	public static boolean isVisible(String opcionAlias) {
		
		OpcionMenu opcion = opcionMenuList.get(opcionAlias);
		
		if (opcion.getRol().getId() == userRol.getId()) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
