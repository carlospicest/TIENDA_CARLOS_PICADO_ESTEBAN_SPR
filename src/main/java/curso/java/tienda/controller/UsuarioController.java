package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

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
@RequestMapping(path = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolService rolService;

	@GetMapping(path = "")
	public String getIndex(HttpSession session, Model model) {
		
		Usuario user = (Usuario) session.getAttribute("userdata");

		if (user != null) {

			RoleData.rol rol = RoleData.rol.valueOf(user.getRol().getRol().toUpperCase());
			
			ArrayList<Usuario> usuarioList = usuarioService.getUsuariosByRolSession(rol);

			model.addAttribute("usuarioList", usuarioList);

			return "usuario/index";

		}
		
		return null;

	}

	@GetMapping(path = "/perfil/{id}")
	public String getPerfil(@PathVariable(name = "id", required = true) int id, Model model) {

		Usuario usuario = usuarioService.getUsuario(id);

		model.addAttribute("usuario", usuario);

		return "usuario/perfil";

	}

	@GetMapping(path = "/editar/{id}")
	public String getEditar(@PathVariable(name = "id", required = true) int id, Model model) {

		Usuario usuario = usuarioService.getUsuario(id);

		// Evitar la exposición de información sensible.
		usuario.setPassword(null);
		usuario.setSalt(null);

		ArrayList<String> provinciaList = SourceData.getProvincias();

		model.addAttribute("usuario", usuario);
		model.addAttribute("provinciaList", provinciaList);

		return "usuario/editar";

	}

	@PostMapping(path = "/editar/{id}")
	public String postEditar(@ModelAttribute("usuario") Usuario usuario,
			@PathVariable(name = "id", required = true) int id) {

		if (usuario != null) {
			usuario.setRol(rolService.getRol(RoleData.rol.CLIENTE.getId()));
			usuario = usuarioService.verifyPasswordChanges(usuario);
			usuarioService.addUsuario(usuario);
		}

		return "redirect:/usuarios";

	}

	@GetMapping(path = "/agregar")
	public String getAgregar(Model model) {

		ArrayList<String> provinciaList = SourceData.getProvincias();

		Usuario usuario = new Usuario();

		model.addAttribute("provinciaList", provinciaList);
		model.addAttribute("usuario", usuario);

		return "/usuario/agregar";

	}

	@PostMapping(path = "/agregar")
	public String postAgregar(@ModelAttribute("usuario") Usuario usuario) {

		if (usuario != null) {
			usuario.setRol(rolService.getRol(RoleData.rol.CLIENTE.getId()));
			usuario = usuarioService.setEncriptacion(usuario);
		}

		usuarioService.addUsuario(usuario);

		return "redirect:/usuarios";

	}

	@GetMapping(path = "/eliminar/{id}")
	public String getEliminar(@PathVariable(name = "id", required = true) int id) {

		usuarioService.deleteUsuario(id);

		return "redirect:/usuarios";

	}

}
