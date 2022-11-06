package curso.java.tienda.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;

@Controller
public class CatalogoController {

	@Autowired
	private ProductoService productoService;
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(path = "/catalogo")
	public String catalogoIndex(Model model) {
		
		ArrayList<Categoria> categoriaList = categoriaService.getCategorias();
		ArrayList<Producto> productoList = productoService.getProductos();
		
		model.addAttribute("categoriaList", categoriaList);
		model.addAttribute("productoList", productoList);
		
		return "index/catalogo.html";
		
	}
	
}
