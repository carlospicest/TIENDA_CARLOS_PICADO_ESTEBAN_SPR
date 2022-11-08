package curso.java.tienda.controller.dashboard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.pojo.Categoria;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;

@Controller
public class ProductoDashboardController {

	@Autowired
	private ProductoService productoService;
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(path = "/dashboard/productos")
	public String getIndex(Model model) {
		
		ArrayList<Producto> productoList = productoService.getProductos();
		
		model.addAttribute("productoList", productoList);
		
		return "dashboard/producto/index";
		
	}
	
	@GetMapping(path = "/dashboard/productos/editar/{id}")
	public String getEditar(@PathVariable(name="id", required=true) int id, Model model) {
		
		Producto producto = productoService.getProducto(id);
		ArrayList<Categoria> categoriaList = categoriaService.getCategorias();
		
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categoriaList);
		
		return "/dashboard/producto/editar";
		
	}
	
	@PostMapping(path = "/dashboard/productos/editar/{id}")
	public String postEditar(@ModelAttribute("producto") Producto producto, @PathVariable(name="id", required=true) int id) {
		
		productoService.addProducto(producto);
		
		return "/dashboard/producto/index";
		
	}
	
	@GetMapping(path = "/dashboard/productos/agregar")
	public String getAgregar(Model model) {
		
		ArrayList<Categoria> categoriaList = categoriaService.getCategorias();
		
		model.addAttribute("producto", new Producto());
		model.addAttribute("categorias", categoriaList);
		
		return "/dashboard/producto/agregar";
		
	}
	
	@PostMapping(path = "/dashboard/productos/agregar")
	public String postAgregar(@ModelAttribute("producto") Producto producto) {
		// Revisar la forma en la que se hace esto, modifica toda la fila de la bd.
		productoService.addProducto(producto);
		
		return "/dashboard/producto/index";
		
	}
	
	@GetMapping(path = "/dashboard/productos/eliminar/{id}")
	public String getEliminar(@PathVariable(name="id", required=true) int id) {
		
		productoService.deleteProducto(id);
		
		return "/dashboard/producto/index";
		
	}
	
}
