package curso.java.tienda.controller.dashboard;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import curso.java.tienda.pojo.Usuario;

@Controller
public class IndexDashboardController {
	
	@GetMapping(path = "/dashboard")
	public String getIndex(HttpSession session, Model model) {
		
		Usuario user = (Usuario) session.getAttribute("userdata");
		
		if (user != null) {
			return "/dashboard/index";
		} else {
			return "redirect:/";
		}
		
	}
	
	@GetMapping(path = "/dashboard/usuarios")
	public String getUsuarios(HttpSession session, Model model) {
		
		Usuario user = (Usuario) session.getAttribute("userdata");
		
		if (user != null) {
			return "/dashboard/usuarios/usuarios.html";
		} else {
			return "redirect:/";
		}

	}
	
	@GetMapping(path = "/dashboard/productos")
	public String getProductos(HttpSession session, Model model) {
		
		Usuario user = (Usuario) session.getAttribute("userdata");
		
		if (user != null) {
			return "/dashboard/productos/productos.html";
		} else {
			return "redirect:/";
		}

	}
	
}
