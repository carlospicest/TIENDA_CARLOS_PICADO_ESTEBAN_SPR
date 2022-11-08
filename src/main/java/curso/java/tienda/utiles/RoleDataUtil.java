package curso.java.tienda.utiles;

import java.util.HashMap;

import curso.java.tienda.pojo.OpcionMenu;
import curso.java.tienda.pojo.Usuario;
import datos.RoleData;

public class RoleDataUtil {

	private static HashMap<String, HashMap<Integer, OpcionMenu>> opcionMenuList;

	/**
	 * Se agregan todas las opciones que van a ser utilizadas por el sistema de
	 * roles.
	 * 
	 * @param rol
	 * @param opcionMenu
	 */

	public static void fillOpcionMenu(HashMap<String, HashMap<Integer, OpcionMenu>> opcionMenu) {

		if (opcionMenu != null) {
			opcionMenuList = opcionMenu;
		}

	}

	public static boolean isVisible(String opcionAlias, Usuario usuario) {

		boolean visible = false;
		HashMap<Integer, OpcionMenu> opcionRoles = opcionMenuList.get(opcionAlias);

		if (usuario == null) { // Si no hay usuario es por que se navega en anónimo.
			
			if (opcionRoles.get(RoleData.rol.ANONIMO.getId()) != null) {
				visible = true;
			}
			
			
		} else {

			if (opcionRoles.get(usuario.getRol().getId()) != null) {
				visible = true;
			}

		}
		
		return visible;

	}

	public static HashMap<String, HashMap<Integer, OpcionMenu>> getOpcionMenuList() {
		return opcionMenuList;
	}

}
