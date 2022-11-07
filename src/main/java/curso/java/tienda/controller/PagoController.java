package curso.java.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagoController {

	@GetMapping(value = "/pago")
	public String pagoIndexGet() {
		return "index/pago";
	}
	
}
