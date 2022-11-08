package curso.java.tienda.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import curso.java.tienda.pojo.DetallePedido;
import curso.java.tienda.pojo.OpcionMenu;
import curso.java.tienda.pojo.Rol;
import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.OpcionMenuService;
import curso.java.tienda.service.RolService;
import curso.java.tienda.utiles.RoleDataUtil;
import datos.RoleData;

@Component
public class DataProviderFilter implements Filter {

	@Autowired
	OpcionMenuService opcionMenuService;
	@Autowired
	RolService rolService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Comprobamos si hay en la sesión datos del carrito.
		
		HashMap<Integer, DetallePedido> cartList = (HashMap<Integer, DetallePedido>) ((HttpServletRequest) request)
				.getSession().getAttribute("cart");

		// Inicializamos el carrito si no existe y lo almacenamos en la sesión.
		if (cartList == null) {
			cartList = new HashMap<Integer, DetallePedido>();
			((HttpServletRequest) request).getSession().setAttribute("cart", cartList);
		}
		
		// Obtener las opciones disponibles en el menú en función del rol del usuario.
		
		if (RoleDataUtil.getOpcionMenuList() == null) {
			HashMap<String, HashMap<Integer, OpcionMenu>> opcionMenuList = opcionMenuService.getOpcionMenu();
			RoleDataUtil.fillOpcionMenu(opcionMenuList);
		}
		
		chain.doFilter(request, response);
		
	}

}
