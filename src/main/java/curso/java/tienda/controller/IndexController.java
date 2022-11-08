package curso.java.tienda.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;

@Controller
public class IndexController {

	@Autowired
	ProductoService productoService;
	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping(value = "/")
	public String getIndex(Model model) {
		
		ArrayList<Producto> productoList = productoService.getProductos();
		HashMap<Integer, Categoria> categoriaList = categoriaService.getRandomCategorias(3);
		
		model.addAttribute("productoList", productoList);
		model.addAttribute("categoriaList", categoriaList);
		
		return "index/index";
		
	}
	
}
