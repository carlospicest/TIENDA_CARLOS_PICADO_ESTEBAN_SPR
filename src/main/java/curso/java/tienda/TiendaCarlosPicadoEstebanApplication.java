package curso.java.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import curso.java.tienda.utiles.ProcesadoPedidoThread;

@SpringBootApplication
public class TiendaCarlosPicadoEstebanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaCarlosPicadoEstebanApplication.class, args);
		// Hilos a ejecutar con la aplicación
		ProcesadoPedidoThread procesadoPedidos = new ProcesadoPedidoThread();
		procesadoPedidos.run();
	}

}
