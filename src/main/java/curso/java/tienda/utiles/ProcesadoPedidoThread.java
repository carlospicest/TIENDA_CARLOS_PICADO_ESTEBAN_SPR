package curso.java.tienda.utiles;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import curso.java.tienda.pojo.Pedido;
import curso.java.tienda.service.PedidoService;
import datos.EstadoPedido;

@Component
public class ProcesadoPedidoThread implements Runnable {

	@Autowired
	private PedidoService pedidoService;
	private boolean status = true;
	private final int DEMORA_ENVIOS = 30000;

	@PostConstruct
	@Override
	public void run() {

		while (status) {
			verServicio();
		}

	}

	private void verServicio() {

		if (pedidoService != null) {

			ArrayList<Pedido> pedidosPendientesEnvio = pedidoService
					.getPedidosByEstado(EstadoPedido.estado.PENDIENTE_ENVIO.getAlias());

			if (pedidosPendientesEnvio != null && !pedidosPendientesEnvio.isEmpty()) {

				for (Pedido pedido : pedidosPendientesEnvio) {
					System.out.println("Pedido " + pedido.getId() + " embalado correctamente.");
					colocarPedidoEnEstacionDeEnvioPaquetario(pedido);
					pedido.setEstado(EstadoPedido.estado.ENVIADO.getAlias());
					pedidoService.addPedido(pedido);
					System.out.println("El pedido " + pedido.getId() + " ha sido enviado!");
				}

			}

		}

	}

	private void colocarPedidoEnEstacionDeEnvioPaquetario(Pedido pedido) {

		try {
			System.out.println("Transportando el pedido => " + pedido.getId() + " al punto de envio...");
			Thread.sleep(DEMORA_ENVIOS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
