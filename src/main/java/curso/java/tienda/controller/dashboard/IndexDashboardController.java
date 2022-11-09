package curso.java.tienda.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexDashboardController {
	
	@GetMapping(path = "/dashboard")
	public String getIndex(Model model) {
		return "/dashboard/index";
	}
	
	@GetMapping(path = "/dashboard/usuarios")
	public String getUsuarios(Model model) {
		return "/dashboard/usuarios";
	}
	
}
