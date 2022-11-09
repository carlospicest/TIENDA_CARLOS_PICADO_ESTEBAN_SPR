package curso.java.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TiendaCarlosPicadoEstebanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaCarlosPicadoEstebanApplication.class, args);
		//Hilos a ejecutar con la aplicación
		/*ProcesadoPedidoThread procesadoPedidos = new ProcesadoPedidoThread();
		procesadoPedidos.run();*/
	}

}
