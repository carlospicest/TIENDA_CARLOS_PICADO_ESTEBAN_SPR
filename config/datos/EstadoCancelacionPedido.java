package datos;

import java.util.HashMap;

public class EstadoCancelacionPedido {

	public enum estado {

		PENDIENTE_PROCESAR("PP"), CANCELACION_ACEPTADA("CA"), CANCELACION_DENEGADA("CD");

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
				
				if (EstadoCancelacionPedido.estado.values()[count].alias.equals(alias)) {
					estado = EstadoCancelacionPedido.estado.values()[count];
					found = true;
				}
				
				count--;
			}
			
			return estado;
			
		}
		
		public static HashMap<String, String> getValues() {
			
			HashMap<String, String> v = new HashMap<>();
			
			for (EstadoCancelacionPedido.estado estado : estado.values()) {
				
				String value = estado.toString();
				String alias = estado.getAlias();
				
				v.put(alias, value);
			}
			
			return v;
			
		}

	}

}
