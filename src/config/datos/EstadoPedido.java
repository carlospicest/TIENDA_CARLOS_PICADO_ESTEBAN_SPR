package datos;

import java.util.HashMap;

public class EstadoPedido {

	public enum estado {

		PENDIENTE_ENVIO("PE"), PENDIENTE_CANCELACION("PC"), ENVIADO("E"), CANCELADO("C");

		private final String alias;

		estado(final String estado) {
			this.alias = estado;
		}

		public String getAlias() {
			return this.alias;
		}
		
		public static estado getValueFromAlias(String alias) {
			
			estado estado = null;
			int count = EstadoPedido.estado.values().length-1;
			boolean found = false;
			
			while (count >= 0 && !found) {
				
				if (EstadoPedido.estado.values()[count].alias.equals(alias)) {
					estado = EstadoPedido.estado.values()[count];
					found = true;
				}
				
				count--;
			}
			
			return estado;
			
		}
		
		public static HashMap<String, String> getV() {
			
			HashMap<String, String> v = new HashMap<>();
			
			for (EstadoPedido.estado estado : estado.values()) {
				
				String value = estado.toString();
				String alias = estado.getAlias();
				
				v.put(alias, value);
			}
			
			return v;
			
		}

	}

}
