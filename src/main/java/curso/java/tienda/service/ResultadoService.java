package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ResultadoService {

	@Autowired
	private ObjectMapper mapper;
	
	public static enum TipoResultado {
		SUCCESS, ERROR;
	}
	
	public String getResultado(TipoResultado tipo, String msg) {
		
		ObjectNode informacion = mapper.createObjectNode();
		
		informacion.put("result", tipo.toString());

		informacion.put("msg", msg);
		
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(informacion);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
