package curso.java.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {

	@RequestMapping("/")
	public String getIndex() {
		
		System.out.println("Prueba!");
		
		return null;
	}
	
}
