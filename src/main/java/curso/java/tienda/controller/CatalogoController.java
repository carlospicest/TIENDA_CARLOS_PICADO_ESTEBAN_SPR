package curso.java.tienda.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.CatalogoService;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;

@Controller
public class CatalogoController {

	@Autowired
	private ProductoService productoService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private CatalogoService catalogoService;
	
	@GetMapping(path = "/catalogo")
	public String catalogoIndex(Model model) {
		
		ArrayList<Categoria> categoriaList = categoriaService.getCategorias();
		ArrayList<Producto> productoList = productoService.getProductos();
		
		model.addAttribute("categoriaList", categoriaList);
		model.addAttribute("productoList", productoList);
		
		return "index/catalogo.html";
		
	}
	
	@PostMapping(path = "/catalogo_filtro", produces="application/json")
	@ResponseBody
	public String catalogoFiltroPost(@RequestParam String filter) {

		System.err.println("Mostrando criterios de filtrado => " + filter);
		
		return catalogoService.getProductoFilter(filter);
				
	}
	
}
