package curso.java.tienda.util;

import java.util.ArrayList;

public class Parametrizar<T> {

	public Parametrizar() {
		parametros = new ArrayList<>();
	}
	
	private ArrayList<T> parametros;
	
	public void addValueParameter(T value) {
		parametros.add(value);
	}
	
	public int getIndex() {
		return parametros.size();
	}
	
	public ArrayList<T> getParameters() {
		return parametros;
	}
	
}
