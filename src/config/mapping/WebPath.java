package mapping;

public class WebPath {
	
	public enum URL {
		
		// Parte P�blica.
		
		INDEX_CONTROLLER(""),
	    INDEX_JSP("index/index.jsp"),
	    LOGIN_CONTROLLER("login"),
	    LOGIN_JSP("index/login.jsp"),
	    LOGOUT_CONTROLLER("logout"),
	    ALTA_USUARIO("index/alta_usuario.jsp"),
	    CATALOGO_CONTROLLER("catalogo"),
	    CATALOGO_JSP("index/catalogo.jsp"),
	    RESULTADO("WEB-INF/resultado/index.jsp");

		// Parte Dashboard (privada).
		
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
