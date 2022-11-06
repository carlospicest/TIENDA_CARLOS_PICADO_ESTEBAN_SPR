package curso.java.tienda.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import curso.java.tienda.pojo.DetallePedido;

@Component
public class DataProviderFilter implements Filter {

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
		
		chain.doFilter(request, response);
		
	}

}
