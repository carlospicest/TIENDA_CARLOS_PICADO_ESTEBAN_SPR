package curso.java.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import curso.java.tienda.pojo.Producto;
import curso.java.tienda.service.ProductoService;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	ObjectMapper mapper;
	
	// Métodos para obtener información sobre un determinado producto en JSON.
	
	@GetMapping(path = "/producto/show/{idProduct}", produces="application/json")
	public @ResponseBody String getIndex(@PathVariable(name="idProduct", required=true) int idProduct) throws JsonProcessingException {
		
		Producto producto = productoService.getProducto(idProduct);
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(producto);
		
	}
	
}
