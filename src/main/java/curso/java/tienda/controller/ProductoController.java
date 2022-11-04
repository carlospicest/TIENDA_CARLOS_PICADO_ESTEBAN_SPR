package curso.java.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.ProductoService;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String getIndex() {
		
		Producto producto = productoService.getProducto(1);
		
		System.out.println(producto.toString());
		
		return "index";
		
	}
	
}
