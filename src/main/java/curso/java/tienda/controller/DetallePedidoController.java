package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.dao.DetallePedidoDAO;
import curso.java.tienda.pojo.DetallePedido;
import curso.java.tienda.pojo.Pedido;
import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.PedidoService;

@Controller
public class DetallePedidoController {

	@Autowired
	private DetallePedidoDAO detallePedidoDao;
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping(value = "/detalles_pedido")
	//public String detallePedidosGet(@PathVariable(name="pedido", required=true) int pedido, HttpSession session, Model model) {
	public String detallePedidosGet(@RequestParam int id, HttpSession session, Model model) {
		
		Pedido pedido = pedidoService.getPedido(id);
		ArrayList<DetallePedido> detallePedidoList = detallePedidoDao.findByPedidoId(id);
		Usuario usuario = (Usuario) session.getAttribute("userdata");
		
		model.addAttribute("pedido", pedido);
		model.addAttribute("detallePedidoList", detallePedidoList);
		model.addAttribute("usuario", usuario);
		
		return "index/detalles_pedido";
		
	}
	
}
