package curso.java.tienda.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import curso.java.tienda.dao.PedidoDAO;
import curso.java.tienda.pojo.Pedido;
import curso.java.tienda.service.PedidoService;
import datos.EstadoPedido;

@Controller
@RequestMapping(path = "/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoDAO pedidoDao;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String getIndex(Model model) {
		
		ArrayList<Pedido> pedidoList = pedidoService.getPedidos();
		
		model.addAttribute("pedidoList", pedidoList);
		
		return "pedido/index";
		
	}
	
	@GetMapping(path = "/procesar/{id}")
	public String getEditar(@PathVariable(name="id", required=true) int id, Model model) {
		
		Pedido pedido = pedidoService.getPedido(id);
		HashMap<String, String> estadoPedidoList = EstadoPedido.estado.getV();
		
		model.addAttribute("pedido", pedido);
		model.addAttribute("estadoPedidoList", estadoPedidoList);
		
		return "pedido/procesar";
		
	}
	
	@PatchMapping(path = "/procesar/{id}")
	public String postEditar(@ModelAttribute("producto") Pedido pedido, @PathVariable(name="id", required=true) int id) {
		
		Pedido pedidoAux = pedidoService.getPedido(id);
		
		pedidoAux.setEstado(pedido.getEstado());
		
		pedidoService.addPedido(pedidoAux);
		
		return "redirect:/pedidos";
		
	}
	
}