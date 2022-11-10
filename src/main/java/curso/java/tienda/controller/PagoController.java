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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.pojo.DetallePedido;
import curso.java.tienda.pojo.MetodoPago;
import curso.java.tienda.pojo.Pedido;
import curso.java.tienda.pojo.Producto;
import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.CarritoService;
import curso.java.tienda.service.DetallePedidoService;
import curso.java.tienda.service.MetodoPagoService;
import curso.java.tienda.service.PedidoService;
import curso.java.tienda.service.ProductoService;
import curso.java.tienda.service.ResultadoService;
import curso.java.tienda.service.ResultadoService.TipoResultado;
import curso.java.tienda.utiles.DateTime;

@Controller
public class PagoController {

	@Autowired
	private MetodoPagoService metodoPagoService;
	@Autowired
	private CarritoService carritoService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private DetallePedidoService detallePedidoService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ResultadoService resultadoService;

	@GetMapping(value = "/pago")
	public String pagoIndexGet(HttpSession session, Model model) {

		Usuario user = (Usuario) session.getAttribute("userdata");

		if (user != null) {

			// Obtenemos los métodos de pago, productos del carrito.

			ArrayList<MetodoPago> metodoPagoList = metodoPagoService.getMetodosPago();
			HashMap<Integer, DetallePedido> cart = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");

			// Información de impuestos y precios para enviar a la vista.

			final double IVA = 21;
			final double IVA_OPERABLE = IVA / 100;
			final double IMPORTE_TOTAL_SIN_IVA = carritoService.getTotalStackAmmountDetalleCarrito(cart);
			final double IMPORTE_TOTAL_IVA = IMPORTE_TOTAL_SIN_IVA * IVA_OPERABLE;
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

			return "index/pago";

		} else {

			String resultado[] = resultadoService.getResultado(TipoResultado.ERROR,
					"<h2 class='text-center'>No se puede continuar el proceso de compra</h2><p class='mt-3 text-left'>Hemos detectado que usted no está identificado.</p><p class='mt-3 text-left'>Para poder finalizar el proceso de compra será necesario que se identifique o puede crearse una nueva cuenta.</p>");
			model.addAttribute("resultado", resultado);
			
			return "/index/resultado";
		}

	}

	@PostMapping(value = "/checkout")
	public String checkoutPost(HttpSession session, @ModelAttribute("metodo_pago") String metodo_pago, Model model) {

		Usuario usuario = (Usuario) session.getAttribute("userdata");

		if (usuario != null) {

			// Detalles del pedido.

			HashMap<Integer, DetallePedido> cart = (HashMap<Integer, DetallePedido>) session.getAttribute("cart");

			double total = carritoService.getTotalStackAmmountDetalleCarrito(cart);

			Pedido pedido = new Pedido();
			pedido.setUsuario(usuario);
			pedido.setFecha(DateTime.getCurrentTime());
			pedido.setMetodo_pago(metodo_pago);
			pedido.setEstado("PE");
			pedido.setNum_factura("AF2");
			pedido.setTotal(total);

			// Damos de alta el pedido

			pedidoService.addPedido(pedido);

			// Agregamos los productos del carrito.

			for (DetallePedido detalle : cart.values()) {

				// Implementar validación del stock!

				Producto producto = detalle.getProducto();
				producto.setStock(producto.getStock() - 1);
				productoService.addProducto(producto);

				detalle.setPedido(pedido);
				detallePedidoService.addDetallePedido(detalle);
			}

			// Limpiamos el carrito de la sesión una vez finalizada la compra.

			cart.clear();

			String resultado[] = resultadoService.getResultado(TipoResultado.SUCCESS,
					"<h2 class='text-center'>Compra realizada correctamente</h2><p class='mt-3 text-left'>Gracias por confiar en nosotros.</p><p class='mt-3'>Nuestros agentes se pondrán a procesar su pedido a la mayor brevedad.</p>");
			model.addAttribute("resultado", resultado);
			
			return "/index/resultado";

		} else {

			String resultado[] = resultadoService.getResultado(TipoResultado.ERROR,
					"<h2 class='text-center'>No se puede continuar el proceso de compra</h2><p class='mt-3 text-left'>Hemos detectado que usted no está identificado.</p><p class='mt-3 text-left'>Para poder finalizar el proceso de compra será necesario que se identifique o puede crearse una nueva cuenta.</p>");
			model.addAttribute("resultado", resultado);
			
			return "/index/resultado";
		}

	}

}
