package curso.java.tienda.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import curso.java.tienda.pojo.DetallePedido;
import curso.java.tienda.pojo.MetodoPago;
import curso.java.tienda.service.CarritoService;
import curso.java.tienda.service.MetodoPagoService;

@Controller
public class PagoController {

	@Autowired
	private MetodoPagoService metodoPagoService;
	@Autowired
	private CarritoService carritoService;

	@GetMapping(value = "/pago")
	public String pagoIndexGet(HttpSession session, Model model) {
		
		// Obtenemos los métodos de pago, productos del carrito.
		
		ArrayList<MetodoPago> metodoPagoList = metodoPagoService.getMetodosPago();
		HashMap<Integer, DetallePedido> cart = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");	
		
		// Información de impuestos y precios para enviar a la vista.
		
		final double IVA = 21;
		final double IVA_OPERABLE = IVA/100;
		final double IMPORTE_TOTAL_SIN_IVA = carritoService.getTotalStackAmmountDetalleCarrito(cart);
		final double IMPORTE_TOTAL_IVA = IMPORTE_TOTAL_SIN_IVA*IVA_OPERABLE;
		final double IMPORTE_TOTAL = IMPORTE_TOTAL_SIN_IVA + IMPORTE_TOTAL_IVA;
		
		// Creamos el formateador decimal.
		
		DecimalFormat df = new DecimalFormat("#,###.##");
		df.setRoundingMode(RoundingMode.FLOOR);
		
		// Agregar al modelo la información.
		
		model.addAttribute("IVA_valor", IVA);
		model.addAttribute("importe_sin_iva", df.format(IMPORTE_TOTAL_SIN_IVA));
		model.addAttribute("importe_con_iva", df.format(IMPORTE_TOTAL_IVA));
		model.addAttribute("importe_total", df.format(IMPORTE_TOTAL));
		
		model.addAttribute("metodosPagoList", metodoPagoList);
		
		return "redirect:/";
		
	}

}
