package curso.java.tienda.utiles;

import java.net.URL;

public class Util {

	public static boolean intToBoolean(int value) {	
		return (value == 1) ? true : false;
	}

	public static int booleanToInt(boolean value) {
		return (!value) ? 0 : 1;
	}
	
	public static String getResource(String name) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resourceURL = loader.getResource(name);
		return resourceURL.getFile();
	}
	
}
