package datos;

public class Ordenacion {

	public enum criterio {

		LOWEST_PRICE(0),
		HIGHEST_PRICE(1),
		BEST_SELLERS(2),
		TOP_RATED(3),
		UNKNOWN(4);

		private final int id;

		criterio(final int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
		
		public static criterio valueOf(int id) {
			
			int length = criterio.values().length;
			
			if (id < 0 || id == criterio.values().length) {
				return UNKNOWN;
			} else {
				return criterio.values()[id];
			}
			
		}

	}

}
