package curso.java.tienda.utiles;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class MensajeTemplate {

	public static String getTemplate(String key) {
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	URL appResourceURL = loader.getResource("./plantillas_mensaje/msg.prop");
    	String filePath = appResourceURL.getPath();
		
		try (InputStream input = new FileInputStream(filePath)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            return prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
		return null;
		
	}
	
}
