package curso.java.tienda.service;

import org.springframework.stereotype.Service;

@Service
public class ResultadoService {

	public enum TipoResultado {
		SUCCESS, ERROR;
	}
	
	public String[] getResultado(TipoResultado tipo, String msg) {
		
		String[] resultado = new String[] {tipo.toString(), msg};
		
		return resultado;
		
	}
	
}
