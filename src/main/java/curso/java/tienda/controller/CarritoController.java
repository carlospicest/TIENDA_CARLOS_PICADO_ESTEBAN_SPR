package curso.java.tienda.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.node.ObjectNode;

import curso.java.tienda.pojo.DetallePedido;
import curso.java.tienda.service.CarritoService;
import curso.java.tienda.service.ProductoService;

@Controller
@RequestMapping(value = "/carrito", produces="application/json")
public class CarritoController {

	@Autowired
	ProductoService productoService;
	
	@Autowired
	CarritoService carritoService;
	
	@GetMapping(path = "/show")
	@ResponseBody
	public ObjectNode getShowCarrito(HttpSession session) {
		
		HashMap<Integer, DetallePedido> cartList = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");

		return CarritoService.getJSONCompleteCartInfo(cartList);
		
		
 	}

	@PostMapping(path = "/update")
	@ResponseBody
	public JsonGenerator postUpdateCarrito(HttpSession session, int idProduct, int stack, String mode) {
		
		HashMap<Integer, DetallePedido> cartList = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");
		
		CarritoService.MODE modeStr = CarritoService.MODE.valueOf(mode);
		
		return carritoService.updateProductCart(idProduct, stack, modeStr, cartList);
		
		//return new CarritoService().updateProductCart(idProduct, stack, modeStr, cartList);
				
	}
	
}
