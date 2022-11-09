package curso.java.tienda.pojo.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import curso.java.tienda.pojo.Usuario;

@JsonIgnoreProperties({ "password", "salt" })
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public abstract class UsuarioMixin extends Usuario {

}