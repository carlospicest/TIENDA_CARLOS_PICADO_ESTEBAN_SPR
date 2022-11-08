package curso.java.tienda.utiles.hilos;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;

import curso.java.tienda.pojo.Pedido;
import curso.java.tienda.service.PedidoService;
import datos.EstadoPedido;

@Component
public class ProcesadoPedidoThread implements Runnable {

	@Autowired
	private PedidoService pedidoService;
	private boolean status = true;
	private final int DEMORA_COMPROBACION = 30000;

	private static Logger log = Logger.getLogger(ProcesadoPedidoThread.class);
	
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
					log.info("Pedido ID => " + pedido.getId() + " embalado correctamente.");
					log.info("Pedido ID => " + pedido.getId() + " transportándose a la estación de envíos.");
					momentoDeOperaciones();
					pedido.setEstado(EstadoPedido.estado.ENVIADO.getAlias());
					pedidoService.addPedido(pedido);
					log.info("Pedido ID => " + pedido.getId() + " ha sido colocado en la estación de envío");
				}
				
				log.info("Los pedidos han sido enviados correctamente.");

			}
			
			momentoDeOperaciones();

		}

	}

	private void momentoDeOperaciones() {

		try {
			Thread.sleep(DEMORA_COMPROBACION);
		} catch (InterruptedException e) {
			log.error(e);
		}

	}
	
}
