package curso.java.tienda.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;

@Controller
@RequestMapping(path = "/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String getIndex(Model model) {
		
		ArrayList<Producto> productoList = productoService.getProductos();
		
		model.addAttribute("productoList", productoList);
		
		return "producto/index";
		
	}
	
	@GetMapping(path = "/editar/{id}")
	public String getEditar(@PathVariable(name="id", required=true) int id, Model model) {
		
		Producto producto = productoService.getProducto(id);
		ArrayList<Categoria> categoriaList = categoriaService.getCategorias();
		
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categoriaList);
		
		return "producto/editar";
		
	}
	
	@PostMapping(path = "/editar/{id}")
	public String postEditar(@ModelAttribute("producto") Producto producto, @PathVariable(name="id", required=true) int id) {
		
		productoService.addProducto(producto);
		
		return "redirect:/productos";
		
	}
	
	@GetMapping(path = "/agregar")
	public String getAgregar(Model model) {
		
		ArrayList<Categoria> categoriaList = categoriaService.getCategorias();
		
		model.addAttribute("producto", new Producto());
		model.addAttribute("categorias", categoriaList);
		
		return "/producto/agregar";
		
	}
	
	@PostMapping(path = "/agregar")
	public String postAgregar(@ModelAttribute("producto") Producto producto) {
		
		productoService.addProducto(producto);
		
		return "redirect:/productos";
		
	}
	
	@GetMapping(path = "/eliminar/{id}")
	public String getEliminar(@PathVariable(name="id", required=true) int id) {
		
		productoService.deleteProducto(id);
		
		return "redirect:/productos";
		
	}
	
}
