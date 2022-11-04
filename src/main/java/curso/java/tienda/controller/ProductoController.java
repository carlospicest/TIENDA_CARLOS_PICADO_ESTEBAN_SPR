package curso.java.tienda.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.ProductoService;

@Controller
@RequestMapping(path = "/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String getIndex(Model model) {
		
		ArrayList<Producto> productoList = productoService.getProductos();
		
		model.addAttribute("productoList", productoList);
		
		return "producto/index";
		
	}
	
	@GetMapping(path = "/editar/{id}")
	public String getEditar(@PathVariable(name="id", required=true) String id, Model model) {
		
		
		
		return "producto/editar";
		
	}
	
	
}
