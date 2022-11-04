package curso.java.tienda.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.pojo.Categoria;
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
	public String getEditar(@PathVariable(name="id", required=true) int id, Model model) {
		
		Producto producto = productoService.getProducto(id);
		
		Categoria c1 = new Categoria();
		c1.setId(1);
		c1.setNombre("Pijamas");
		
		Categoria c2 = new Categoria();
		c1.setId(2);
		c1.setNombre("Toallas");
		
		ArrayList<Categoria> lista = new ArrayList<>();
		lista.add(c1);
		lista.add(c2);
		
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", lista);
		
		return "producto/editar";
		
	}
	
	@PostMapping(path = "/editar")
	public String postEditar(@ModelAttribute Producto producto, Model model) {
		
		System.out.println(model);
		
		return "producto/index";
		
	}
	
	
}
