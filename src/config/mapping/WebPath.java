package mapping;

public class WebPath {
	
	public static final String[] P = {
			"s"
	};
	
	public enum URL {
		
		DASHBOARD_USUARIOS_ROOT("redirect:/usuarios"),
		DASHBOARD_USUARIOS_GET_INDEX("dashboard/usuario/index"),
		DASHBOARD_USUARIO_PERFIL("dashboard/usuario/perfil"),
		DASHBOARD_USUARIO_EDITAR("dashboard/usuario/editar"),
		DASHBOARD_USUARIO_GET_AGREGAR("dashboard/usuario/agregar"),
		DASHBOARD_REDIRECT_("dashboard/usuario/editar");
		
		
		
	    private final String url;

	    URL(final String url) {
	        this.url = url;
	    }

	    @Override
	    public String toString() {
	        return url;
	    }
	    
	}
	
}
