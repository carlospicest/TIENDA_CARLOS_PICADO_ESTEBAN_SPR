package curso.java.tienda;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class TiendaCarlosPicadoEstebanApplication {

	@Bean(name = "taskExecutorProcesadoPedidos")
	public Executor taskExecutorProcesadoPedidos() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("procesador-pedidos");
		executor.initialize();
		
		return executor;
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TiendaCarlosPicadoEstebanApplication.class, args);
	}

}
