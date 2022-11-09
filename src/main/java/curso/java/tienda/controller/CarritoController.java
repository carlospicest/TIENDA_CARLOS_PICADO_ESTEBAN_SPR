package curso.java.tienda.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import curso.java.tienda.pojo.DetallePedido;
import curso.java.tienda.service.CarritoService;
import curso.java.tienda.service.ProductoService;

@Controller
@RequestMapping(value = "/carrito")
public class CarritoController {

	@Autowired
	ProductoService productoService;
	@Autowired
	CarritoService carritoService;
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping(path = "")
	public String carritoIndex(Model model) {
		
		return "index/carrito";
		
	}
	
	@GetMapping(path = "/show", produces="application/json")
	public @ResponseBody ObjectNode getShowCarrito(HttpSession session) {
		
		HashMap<Integer, DetallePedido> cartList = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");

		return carritoService.getJSONCompleteCartInfo(cartList);
		
 	}

	@PostMapping(path = "/update", produces="application/json")
	public @ResponseBody String postUpdateCarrito(HttpSession session, int idProduct, int stack, String mode) {
		
		HashMap<Integer, DetallePedido> cartList = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");
		
		CarritoService.MODE modeStr = CarritoService.MODE.valueOf(mode);
		
		return carritoService.updateProductCart(idProduct, stack, modeStr, cartList);
		
	}
	
	@PostMapping(path = "/delete/{idProduct}", produces="application/json")
	public @ResponseBody ObjectNode postDeleteCarrito(HttpSession session, int idProduct) {
		
		HashMap<Integer, DetallePedido> cartList = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");
		boolean deleteResult = false;
		
		if (cartList != null) {
			DetallePedido detallePedido = cartList.get(idProduct);	
			deleteResult = cartList.remove(idProduct, detallePedido);
		}
		
		ObjectNode deleteInformation = mapper.createObjectNode();
		deleteInformation.put("result", deleteResult);
		
		return deleteInformation;
		
	}
	
}
