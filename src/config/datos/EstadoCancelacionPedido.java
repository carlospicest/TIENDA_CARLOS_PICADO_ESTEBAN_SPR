package datos;

public class EstadoCancelacionPedido {

	public enum estado {

		PENDIENTE_PROCESAR("PP"), CANCELACION_ACEPTADA("CA"), CANCELACION_DENEGADA("CD");

		private final String estado;

		estado(final String estado) {
			this.estado = estado;
		}

		public String toString() {
			return estado;
		}

	}

}
