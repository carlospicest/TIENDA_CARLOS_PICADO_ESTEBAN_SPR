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
import curso.java.tienda.service.ResultadoService;
import curso.java.tienda.service.ResultadoService.TipoResultado;
import curso.java.tienda.utiles.JsonDisplay;
import datos.EstadoPedido;

@Controller
public class IndexController {

	@Autowired
	ProductoService productoService;
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	ResultadoService resultadoService;
	
	@GetMapping(value = "/")
	public String getIndex(Model model) {
		
		HashMap<Categoria, ArrayList<Producto>> productosCategoria = productoService.getRandomCategorias(3);
		
		model.addAttribute("productosCategoria", productosCategoria);
		
		return "index/index";
		
	}
	
	@GetMapping(value = "/resultado")
	public String getResultado(Model model) {
		
		String[] resultado = resultadoService.getResultado(TipoResultado.SUCCESS, "<h2>Se ha producido algo bueno</h2>");
		model.addAttribute("resultado", resultado);

		return "/index/resultado";
		
	}
	
}
