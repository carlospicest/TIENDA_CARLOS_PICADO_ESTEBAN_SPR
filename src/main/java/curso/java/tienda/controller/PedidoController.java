package curso.java.tienda.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import curso.java.tienda.dao.PedidoDAO;
import curso.java.tienda.pojo.CancelacionPedido;
import curso.java.tienda.pojo.Pedido;
import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.CancelacionPedidoService;
import curso.java.tienda.service.PedidoService;
import curso.java.tienda.service.ResultadoService;
import curso.java.tienda.service.ResultadoService.TipoResultado;
import curso.java.tienda.utiles.DateTime;
import datos.EstadoCancelacionPedido;
import datos.EstadoPedido;
import datos.RoleData;

@Controller
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoDAO pedidoDao;
	@Autowired
	private CancelacionPedidoService cancelacionPedidoService;
	@Autowired
	private ResultadoService resultadoService;
	@Autowired
	private ObjectMapper mapper;
	
	@RequestMapping(path = "/pedidos", method = RequestMethod.GET)
	public String getIndex(Model model) {
		
		ArrayList<Pedido> pedidoList = pedidoService.getPedidos();
		
		model.addAttribute("pedidoList", pedidoList);
		
		return "dashboard/pedidos/index";
		
	}
	
	@GetMapping(path = "/pedidos/procesar/{id}")
	public String getEditar(@PathVariable(name="id", required=true) int id, Model model) {
		
		Pedido pedido = pedidoService.getPedido(id);
		HashMap<String, String> estadoPedidoList = EstadoPedido.estado.getValues();
		
		model.addAttribute("pedido", pedido);
		model.addAttribute("estadoPedidoList", estadoPedidoList);
		
		return "/dashboard/pedidos/procesar";
		
	}
	
	@PatchMapping(path = "/pedidos/procesar/{id}")
	public String postEditar(@ModelAttribute("producto") Pedido pedido, @PathVariable(name="id", required=true) int id) {
		
		Pedido pedidoAux = pedidoService.getPedido(id);
		
		pedidoAux.setEstado(pedido.getEstado());
		
		pedidoService.addPedido(pedidoAux);
		
		return "/dashboard/pedidos/index";
		
	}
	
	// Vistas desde la tienda (index).
	
	@GetMapping(path = "/historial_pedidos")
	public String historialPedidosGet(HttpSession session, Model model) {
		
		Usuario usuario = (Usuario) session.getAttribute("userdata");
		
		ArrayList<Pedido> pedidoList = pedidoDao.findByUsuario(usuario.getId());
		
		model.addAttribute("pedidoList", pedidoList);
		
		return "index/historial_pedidos";
		
	}
	
	// Métodos para la vista del index.
	
	@GetMapping(value = "/cancelacion_pedido")
	public String cancelacionPedidosGet(@RequestParam int id, HttpSession session, Model model) {
		
		Pedido pedido = pedidoService.getPedido(id);
		
		model.addAttribute("pedido", pedido);
		
		return "index/cancelacion_pedido";
		
	}
	
	@PostMapping(value = "/cancelacion_pedido")
	public String cancelacionPedidoGet(@RequestParam(name = "id", required = true) int id, String motivo, HttpSession session, Model model) {

		Pedido pedido = pedidoService.getPedido(id);
		pedido.setEstado(EstadoPedido.estado.PENDIENTE_CANCELACION.getAlias());
		
		CancelacionPedido cancelacionPedido = new CancelacionPedido();
		cancelacionPedido.setPedido(pedido);
		cancelacionPedido.setMotivo(motivo);
		cancelacionPedido.setEstado(EstadoCancelacionPedido.estado.PENDIENTE_PROCESAR.getAlias());
		cancelacionPedido.setNum_solicitud("CAN_S0");
		cancelacionPedido.setFecha(DateTime.getCurrentTime());
		
		boolean resultado = cancelacionPedidoService.addCancelacionPedido(cancelacionPedido);
		
		if (resultado) {
			
			String resultadoDatos[] = resultadoService.getResultado(TipoResultado.SUCCESS,
					"<h2 class='text-center'>Solicitud de cancelación registrada</h2><p class='mt-3 text-left'>Hemos recibido su solicitud de cancelación.</p><p>Un agente evaluará su caso lo antes posible.</p>");
			
			model.addAttribute("resultado", resultadoDatos);
			
			return "/index/resultado";
			
		} else {
			
			String resultadoDatos[] = resultadoService.getResultado(TipoResultado.ERROR,
					"<h2 class='text-center'>Error al solicitar la cancelación</h2><p class='mt-3 text-left'>Hemos tenido inconvenientes procesando su solicitud de cancelación.</p><p>Intente el proceso de nuevo mas tarde.</p>");
			
			model.addAttribute("resultado", resultadoDatos);
			
			return "/index/resultado";
			
		}
		
	}
	
	// JSON
	
	@GetMapping(path = "/pedidos/show", produces="application/json")
	public @ResponseBody String getShowPedido() throws JsonProcessingException {

		ArrayList<Pedido> pedidos = pedidoService.getPedidos();
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedidos);
		
 	}
	
}