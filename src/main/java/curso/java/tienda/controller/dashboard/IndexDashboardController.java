package curso.java.tienda.controller.dashboard;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.service.CategoriaService;

@Controller
public class IndexDashboardController {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(path = "/dashboard")
	public String getIndex(Model model) {
		
		// Mostramos un total de 3 categorías aleatorias.
		
		//HashMap<Integer, Categoria> categoriaList = categoriaService.getRandomCategorias(3);
		
		//model.addAttribute("categoriaList", categoriaList);
		
		return "/dashboard/index";
		
	}
	
	
	
}
