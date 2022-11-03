package datos;

public class RoleData {

	public enum rol {

		ANONIMO(1),
		CLIENTE(2),
		ADMINISTRADOR(3),
		EMPLEADO(4);

		private final int id;

		rol(final int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

	}

}
