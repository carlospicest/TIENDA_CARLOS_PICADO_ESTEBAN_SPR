package curso.java.tienda.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;
import curso.java.tienda.utiles.SourceData;
import datos.RoleData;

@Controller
@RequestMapping(path = "/clientes")
public class ClienteController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolService rolService;
	
	@GetMapping(path = "")
	public String getIndex(Model model) {
		
		ArrayList<Usuario> usuarioList = usuarioService.getUsuarios(RoleData.rol.CLIENTE.getId());
		
		model.addAttribute("usuarioList", usuarioList);
		
		return "cliente/index";
		
	}
	
	@GetMapping(path = "/perfil/{id}")
	public String getPerfil(@PathVariable(name="id", required=true) int id, Model model) {
		
		Usuario cliente = usuarioService.getUsuario(id, RoleData.rol.CLIENTE.getId());
		
		model.addAttribute("cliente", cliente);
		
		return "cliente/perfil";
		
	}
	
	@GetMapping(path = "/editar/{id}")
	public String getEditar(@PathVariable(name="id", required=true) int id, Model model) {
		
		Usuario cliente = usuarioService.getUsuario(id, RoleData.rol.CLIENTE.getId());
		
		// Evitar la exposición de información sensible.
		cliente.setPassword(null);
		cliente.setSalt(null);
		
		ArrayList<String> provinciaList = SourceData.getProvincias();
		
		model.addAttribute("cliente", cliente);
		model.addAttribute("provinciaList", provinciaList);
		
		return "cliente/editar";
		
	}
	
	@PostMapping(path = "/editar/{id}")
	public String postEditar(@ModelAttribute("cliente") Usuario cliente, @PathVariable(name="id", required=true) int id) {
		
		if (cliente != null) {
			cliente.setRol(rolService.getRol(RoleData.rol.CLIENTE.getId()));
			cliente = usuarioService.verifyPasswordChanges(cliente);
			usuarioService.addUsuario(cliente);
		}

		return "redirect:/clientes";
		
	}
	
	@GetMapping(path = "/agregar")
	public String getAgregar(Model model) {
		
		ArrayList<String> provinciaList = SourceData.getProvincias();
		
		Usuario cliente = new Usuario();
		
		model.addAttribute("provinciaList", provinciaList);
		model.addAttribute("cliente", cliente);
		
		return "/cliente/agregar";
		
	}
	
	@PostMapping(path = "/agregar")
	public String postAgregar(@ModelAttribute("cliente") Usuario cliente) {
		
		if (cliente != null) {
			cliente.setRol(rolService.getRol(RoleData.rol.CLIENTE.getId()));
			cliente = usuarioService.setEncriptacion(cliente);
		}
		
		usuarioService.addUsuario(cliente);
		
		return "redirect:/clientes";
		
	}
	
	@GetMapping(path = "/eliminar/{id}")
	public String getEliminar(@PathVariable(name="id", required=true) int id) {
		
		usuarioService.deleteUsuario(id);
		
		return "redirect:/clientes";
		
	}
	
}
