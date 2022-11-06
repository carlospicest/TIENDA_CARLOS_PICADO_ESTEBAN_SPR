package curso.java.tienda.service;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import curso.java.tienda.dao.ProductoDAO;
import curso.java.tienda.pojo.DetallePedido;
import curso.java.tienda.pojo.Producto;

@Service
public class CarritoService {
	
	@Autowired
	private ProductoService productoService;
	
	public static enum MODE {

		MINUS("MINUS"), PLUS("PLUS"), MASIVE("MASIVE");

		private final String url;

		MODE(final String url) {
			this.url = url;
		}

		@Override
		public String toString() {
			return url;
		}

	}

	public static ObjectNode getJSONCompleteCartInfo(HashMap<Integer, DetallePedido> cart) {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode cartInformation = mapper.createObjectNode();

		// Extraemos el número de productos en total del carrito.

		int totalProduct = getStackCountDetalleCarrito(cart);

		cartInformation.put("totalProduct", totalProduct);

		// Agregamos los productos que existen en el carrito.

		ArrayNode productCartList = mapper.createArrayNode();

		for (DetallePedido detail : cart.values()) {
			productCartList.addPOJO(detail);
		}

		cartInformation.set("products", productCartList);

		// Extraemos el precio total de los artículos del carrito.

		double totalAmmount = getTotalStackAmmountDetalleCarrito(cart);

		cartInformation.put("totalAmmount", totalAmmount);

		return cartInformation;

	}

	public JsonGenerator updateProductCart(int idProd, int stack, MODE mode,
			HashMap<Integer, DetallePedido> cartList) {

		Producto product = null;
		JsonGenerator cartJson = null;
		
		if (product != null) {

			// Comprobamos si el producto ya ha sido agregado al carrito.

			DetallePedido cartDetail = cartList.get(idProd);

			if (cartDetail != null) {

				// Ya existe el producto, modificamos unidades.

				switch (mode) {

				case MINUS:

					setMinusDetallePedido(cartDetail);
					break;

				case PLUS:

					setPlusDetallePedido(cartDetail);
					break;

				case MASIVE:

					setMasiveDetallePedido(cartDetail, stack);
					break;

				}

				cartDetail.setTotal(cartDetail.getUnidades() * cartDetail.getPrecio_unidad());

			} else {

				// No existe, lo agregamos.

				cartDetail = new DetallePedido();
				cartDetail.setProducto(product);
				cartDetail.setUnidades(stack);
				cartDetail.setTotal(stack * product.getPrecio());
				cartDetail.setPrecio_unidad(product.getPrecio());
				cartDetail.setImpuesto(product.getImpuesto());

				cartList.put(product.getId(), cartDetail);

			}

			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.writeValue(cartJson, DetallePedido.class);
			} catch (StreamWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return cartJson;

	}

	public static double getTotalStackAmmountDetalleCarrito(HashMap<Integer, DetallePedido> cart) {

		double totalAmmount = 0;

		if (cart != null && !cart.isEmpty()) {

			for (DetallePedido product : cart.values()) {
				totalAmmount += (product.getPrecio_unidad() * product.getUnidades());
			}

		}

		return totalAmmount;

	}

	private static int getStackCountDetalleCarrito(HashMap<Integer, DetallePedido> cart) {

		int totalProducts = 0;

		if (cart != null && !cart.isEmpty()) {

			for (DetallePedido product : cart.values()) {
				totalProducts += product.getUnidades();
			}

		}

		return totalProducts;

	}

	private static void setMinusDetallePedido(DetallePedido detallePedido) {

		int stack = detallePedido.getUnidades();

		if (stack > 1) {
			Producto product = detallePedido.getProducto();
			detallePedido.setUnidades(stack - 1);
			detallePedido.setTotal(product.getPrecio() * stack);
		}

	}

	private static void setPlusDetallePedido(DetallePedido detallePedido) {

		int stack = detallePedido.getUnidades();

		Producto product = detallePedido.getProducto();
		detallePedido.setUnidades(stack + 1);
		detallePedido.setTotal(product.getPrecio() * stack);

	}

	private static void setMasiveDetallePedido(DetallePedido detallePedido, int stack) {

		if (stack >= 1) {
			Producto product = detallePedido.getProducto();
			detallePedido.setUnidades(stack);
			detallePedido.setTotal(product.getPrecio() * stack);
		}

	}

}
