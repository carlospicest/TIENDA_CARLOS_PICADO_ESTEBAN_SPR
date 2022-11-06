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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import curso.java.tienda.pojo.CancelacionPedido;
import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.CancelacionPedidoService;
import datos.EstadoCancelacionPedido;
import datos.RoleData;

@Controller
@RequestMapping(path = "/cancelacion_pedidos")
public class CancelacionPedidoController {

	@Autowired
	private CancelacionPedidoService cancelacionPedidoService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String getIndex(HttpSession session, Model model) {

		Usuario user = (Usuario) session.getAttribute("userdata");

		if (user.getRol().getRol().toUpperCase().equals(RoleData.rol.ADMINISTRADOR.toString())) {

			ArrayList<CancelacionPedido> cancelacionPedidolist = cancelacionPedidoService.getCancelacionPedido();

			model.addAttribute("cancelacionPedidoList", cancelacionPedidolist);

			return "dashboard/cancelacion_pedido/index";

		} else {
			return null;
		}

	}

	@GetMapping(path = "/procesar/{id}")
	public String getEditar(@PathVariable(name = "id", required = true) int id, Model model, HttpSession session) {

		Usuario user = (Usuario) session.getAttribute("userdata");
		
		if (user.getRol().getRol().toUpperCase().equals(RoleData.rol.ADMINISTRADOR.toString())) {

			CancelacionPedido cancelacionPedido = cancelacionPedidoService.getCancelacionPedido(id);
			HashMap<String, String> estadoCancelacionPedidoList = EstadoCancelacionPedido.estado.getValues();

			model.addAttribute("cancelacionPedido", cancelacionPedido);
			model.addAttribute("estadoCancelacionPedidoList", estadoCancelacionPedidoList);

			return "dashboard/cancelacion_pedido/procesar";

		} else {
			return null;
		}

	}

	@PatchMapping(path = "/procesar/{id}")
	public String patchEditar(@ModelAttribute("producto") CancelacionPedido cancelacionPedido,
			@PathVariable(name = "id", required = true) int id) {

		CancelacionPedido cancelacionPedidoAux = cancelacionPedidoService.getCancelacionPedido(id);

		cancelacionPedidoAux.setEstado(cancelacionPedido.getEstado());

		cancelacionPedidoService.addCancelacionPedido(cancelacionPedidoAux);

		return "redirect:/cancelacion_pedidos";

	}

}
