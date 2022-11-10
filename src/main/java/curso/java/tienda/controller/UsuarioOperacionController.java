package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.pojo.Usuario;
import curso.java.tienda.service.ResultadoService;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;
import curso.java.tienda.service.ResultadoService.TipoResultado;
import curso.java.tienda.utiles.MensajeTemplate;
import curso.java.tienda.utiles.SourceData;
import datos.RoleData;

@Controller
public class UsuarioOperacionController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolService rolService;
	@Autowired
	ResultadoService resultadoService;

	// Alta de usuarios mediante el formulario del index.

	@GetMapping(path = "/registro")
	public String altaUsuarioGet(Model model) {

		model.addAttribute("provinciaList", SourceData.getProvincias());
		model.addAttribute("usuario", new Usuario());

		return "index/alta_usuario.html";

	}

	@PostMapping(path = "/registro")
	public String altaUsuarioPost(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,
			Model model, String repassword) {

		if (!bindingResult.hasErrors()) {

			if (!usuario.getPassword().equals(repassword)) {
				
				model.addAttribute("error_pw", "Las contraseñas no son iguales.");
				model.addAttribute("provinciaList", SourceData.getProvincias());
				return "/index/alta_usuario";
				
			} else {

				usuario.setRol(rolService.getRol(RoleData.rol.CLIENTE.getId()));
				usuario = usuarioService.setEncriptacion(usuario);
				boolean resultado = usuarioService.addUsuario(usuario);

				String json = null;

				/*if (resultado) {
					json = resultadoService.getResultado(TipoResultado.SUCCESS,
							MensajeTemplate.getTemplate("registro.success"));
				} else {
					json = resultadoService.getResultado(TipoResultado.ERROR,
							MensajeTemplate.getTemplate("registro.error"));
				}*/

				model.addAttribute("resultado", json);

				return "/index";

			}

		} else {
			model.addAttribute("provinciaList", SourceData.getProvincias());
			return "/index/alta_usuario";
		}

	}

	@GetMapping(path = "/perfil")
	public String perfilUsuarioGet(HttpSession session, Model model) {

		Usuario usuario = (Usuario) session.getAttribute("userdata");

		model.addAttribute("provinciaList", SourceData.getProvincias());
		model.addAttribute("usuario", usuario);

		return "index/perfil_usuario.html";

	}

	@PostMapping(path = "/perfil/{id}")
	public String perfilUsuarioPost(@ModelAttribute("usuario") Usuario usuario,
			@PathVariable(name = "id", required = true) int id, HttpSession session) {

		Usuario usuarioAux = usuarioService.getUsuario(id);

		// Esto tendrá mejor implementación.

		usuario.setDni(usuarioAux.getDni());
		usuario.setRol(usuarioAux.getRol());
		usuario.setPassword(usuarioAux.getPassword());
		usuario.setSalt(usuarioAux.getSalt());
		usuario.setImagen(usuarioAux.getImagen());
		usuario.setBaja(usuarioAux.isBaja());
		usuario.setFecha_alta(usuarioAux.getFecha_alta());

		usuarioService.addUsuario(usuario);

		Usuario usuarioSession = usuarioService.getUsuarioMixin(usuario);

		session.setAttribute("userdata", usuarioSession);

		return "redirect:/";

	}

	@GetMapping(path = "/modificar_password")
	public String modificarPasswordGet() {

		return "index/modificar_password.html";

	}

	@PostMapping(path = "/modificar_password/{id}")
	public String modificarPasswordPost(@PathVariable(name = "id", required = true) int id, String current_password,
			String current_password_repeat, String password, HttpSession session) {

		// Habría que validar esta información recibida, por ahora la damos por buena.

		Usuario usuario = (Usuario) session.getAttribute("userdata");
		usuario.setPassword(password);

		usuario = usuarioService.setEncriptacion(usuario); // Generamos el password hasheado y la salt.

		usuarioService.addUsuario(usuario);

		return "redirect:/";

	}

}
