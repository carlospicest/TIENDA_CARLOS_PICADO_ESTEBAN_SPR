package curso.java.tienda.controller.dashboard;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import curso.java.tienda.dao.UsuarioDAO;
import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;
import curso.java.tienda.utiles.SourceData;
import datos.RoleData;
import mapping.WebPath;

@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioDashboardController {

	@Autowired
	private UsuarioDAO usuarioDao;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolService rolService;
	@Autowired
	private ObjectMapper mapper;

	@GetMapping(path = "")
	public String getIndex(HttpSession session, Model model) {
		
		Usuario user = (Usuario) session.getAttribute("userdata");

		if (user != null) {

			RoleData.rol rol = RoleData.rol.valueOf(user.getRol().getRol().toUpperCase());
			
			ArrayList<Usuario> usuarioList = usuarioService.getUsuariosByRolSession(rol);

			model.addAttribute("usuarioList", usuarioList);

			return "";

		}
		
		return "redirect:/";

	}

	@GetMapping(path = "/perfil/{id}")
	public String getPerfil(@PathVariable(name = "id", required = true) int id, Model model) {

		Usuario usuario = usuarioService.getUsuario(id);

		model.addAttribute("usuario", usuario);

		return WebPath.URL.DASHBOARD_USUARIO_PERFIL.toString();

	}

	@GetMapping(path = "/editar/{id}")
	public String getEditar(@PathVariable(name = "id", required = true) int id, Model model) {

		Usuario usuario = usuarioService.getUsuario(id);

		// Evitar la exposici??n de informaci??n sensible.
		usuario.setPassword(null);
		usuario.setSalt(null);

		ArrayList<String> provinciaList = SourceData.getProvincias();

		model.addAttribute("usuario", usuario);
		model.addAttribute("provinciaList", provinciaList);

		return "/dashboard/usuarios/editar";

	}

	@PostMapping(path = "/editar/{id}")
	public String postEditar(@ModelAttribute("usuario") Usuario usuario,
			@PathVariable(name = "id", required = true) int id) {

		if (usuario != null) {
			usuario.setRol(rolService.getRol(RoleData.rol.CLIENTE.getId()));
			usuario = usuarioService.verifyPasswordChanges(usuario);
			usuarioService.addUsuario(usuario);
		}

		return "/dashboard/usuarios/index";

	}

	@GetMapping(path = "/agregar")
	public String getAgregar(Model model) {

		ArrayList<String> provinciaList = SourceData.getProvincias();

		Usuario usuario = new Usuario();

		model.addAttribute("provinciaList", provinciaList);
		model.addAttribute("usuario", usuario);

		return "/dashboard/usuarios/agregar";

	}

	@PostMapping(path = "/agregar")
	public String postAgregar(@ModelAttribute("usuario") Usuario usuario) {

		if (usuario != null) {
			usuario.setRol(rolService.getRol(RoleData.rol.CLIENTE.getId()));
			usuario = usuarioService.setEncriptacion(usuario);
		}

		usuarioService.addUsuario(usuario);

		return "/dashboard/usuarios/index";

	}

	@GetMapping(path = "/baja/{id}")
	public String getBaja(@PathVariable(name = "id", required = true) int id) {

		Usuario usuario = usuarioService.getUsuario(id);
		usuario.setBaja(true);
		
		usuarioService.addUsuario(usuario);

		return "/dashboard/usuarios/index";

	}
	
	// JSON
	
	@GetMapping(path = "/show", produces="application/json")
	public @ResponseBody String getShowUsuario() throws JsonProcessingException {

		ArrayList<Usuario> usuarios = usuarioService.getUsuariosByRolSession(RoleData.rol.ADMINISTRADOR);
		
		usuarios = usuarioService.getMixinUsuarios(usuarios);
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(usuarios);
		
 	}

}
