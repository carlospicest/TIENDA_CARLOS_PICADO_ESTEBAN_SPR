package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.SesionUsuarioService;

@Controller
public class SesionUsuarioController {
	
	@Autowired
	private SesionUsuarioService sesionUsuarioService;
	
	
	@GetMapping(path = "/login")
	public String loginIndex(Model model) {
		
		Usuario usuario = new Usuario();
		
		//model.addAttribute("error", "s");
		model.addAttribute("usuario", usuario);
		
		return "index/login";
		
	}
	
	@PostMapping(path = "/login")
	public String loginForm(@ModelAttribute("usuario") Usuario usuario, Model model, HttpSession session) {
		
		Usuario usuarioComprobacion = sesionUsuarioService.validarCredenciales(usuario);
		
		if (usuarioComprobacion != null) {
			session.setAttribute("userdata", usuarioComprobacion);
			return "redirect:/";
		} else {
			model.addAttribute("error", "Las credenciales introducidas no son correctas");
			return "index/login";
		}
		
	}
	
	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		
		session.setAttribute("userdata", null);
		
		return "redirect:/";
		
	}
	
}
