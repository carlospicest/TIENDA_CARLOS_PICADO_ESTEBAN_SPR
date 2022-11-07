package curso.java.tienda.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import curso.java.tienda.pojo.Producto;
import curso.java.tienda.utiles.Parametrizar;
import datos.Ordenacion;

@Service
public class CatalogoService {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private EntityManager entityManager;
	
	/**
	 * Método que devuelve un String con un JSON que contiene los productos
	 * que cumplan con los criterios de filtrado establecidos.
	 * @param hql de consulta
	 * @return String en formato JSON.
	 */
	
	public String getProductoFilter(String criteria) {
		
		String jsonResult = null;
		
		if (criteria != null) {

			try {

				// Definición de los elementos para construir el hql necesario.

				StringBuilder hql = new StringBuilder();
				hql.append("select p from productos p ");
				Parametrizar parametrizar = new Parametrizar(); // Donde albergarán los parámetros proporcionados por el
																// usuario.
				JsonNode root = mapper.readTree(criteria);

				// Si el JSON de criterios no es nulo, comenzamos a construir el hql.

				if (root != null) {

					// Procesar los criterios.

					setCategoriasCriteria(hql, root, parametrizar); // Procesar categorías si las hubiera.
					setPrecioCriteria(hql, root, parametrizar);
					setOrdenacionCriteria(hql, root, parametrizar);

					System.out.println(hql.toString());

					ArrayList<Producto> productosList = upQuery(hql, parametrizar);

					if (productosList != null && !productosList.isEmpty()) {

						// Generamos JSON para devolver a la vista.
						jsonResult = buildJsonProductoCriteria(productosList);
					}

				}

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return jsonResult;
		
	}
	
	/**
	 * Agrega a un hql existente parámetros de consulta sobre las categorías
	 * escogidas en el criterio de filtrado.
	 * 
	 * @param hql
	 * @param root
	 * @param parametrizar
	 */
	
	private void setCategoriasCriteria(StringBuilder hql, JsonNode root, Parametrizar parametrizar) {

		if (!root.get("categories").isNull()) {
			if (root.get("categories").isArray()) {
				if (!root.get("categories").isEmpty()) {
					for (final JsonNode objNode : root.get("categories")) {

						String param = ":param" + parametrizar.getIndex();

						if (hql.indexOf("where") == -1) {
							hql.append("where p.categoria.id in (select c from categorias c where c.id = " + param);
							parametrizar.addValueParameter(objNode.asInt());
						} else {
							hql.append(" or c.id = " + param);
							// parameterValue.put(parameterValue.size(), objNode.asText());
							parametrizar.addValueParameter(objNode.asInt());
						}

					}

					hql.append(")");

					System.out.println(hql.toString());

				}

			}
		}

	}
	
	/**
	 * Agrega a un hql existente parámetros de consulta sobre los intervalos
	 * de precio establecidos en los criterios de filtrado.
	 * @param hql
	 * @param root
	 * @param parametrizar
	 */
	
	private void setPrecioCriteria(StringBuilder hql, JsonNode root, Parametrizar parametrizar) {

		if (!root.get("price").isNull()) {

			JsonNode priceNode = root.get("price");

			if (!priceNode.get("min").isNull() && !priceNode.get("max").isNull()) {

				String param = null;

				if (hql.indexOf("where") == -1) {

					param = ":param" + parametrizar.getIndex();
					hql.append("where p.precio between " + param);
					parametrizar.addValueParameter(priceNode.get("min").asDouble());

					param = ":param" + parametrizar.getIndex();
					hql.append(" and " + param);
					parametrizar.addValueParameter(priceNode.get("max").asDouble());

				} else {

					param = ":param" + parametrizar.getIndex();
					hql.append(" and p.precio between " + param);
					parametrizar.addValueParameter(priceNode.get("min").asDouble());

					param = ":param" + parametrizar.getIndex();
					hql.append(" and " + param);
					parametrizar.addValueParameter(priceNode.get("max").asDouble());

				}

			}

		}

	}

	/**
	 * Agrega a un hql existente parámetros de consulta sobre un determinado
	 * criterio de ordenación especificado.
	 * @param hql
	 * @param root
	 * @param parametrizar
	 */
	
	private void setOrdenacionCriteria(StringBuilder hql, JsonNode root, Parametrizar parametrizar) {

		if (!root.get("sort").isNull()) {

			int sortId = root.get("sort").asInt();

			switch (Ordenacion.criterio.valueOf(sortId)) {

			case LOWEST_PRICE:

				hql.append(" order by p.precio asc");
				break;

			case HIGHEST_PRICE:

				hql.append(" order by p.precio desc");
				break;

			case BEST_SELLERS:

				if (hql.indexOf("where") == -1) {
					hql.append("where p.stock < 20");
				} else {
					hql.append(" and p.stock < 20");
				}

				break;

			case TOP_RATED:

				if (hql.indexOf("where") == -1) {
					hql.append(" inner join valoraciones v on v.producto.id = p.id");
					hql.append(" group by p.id");
					hql.append(" order by avg(v.valoracion) desc");
				} else {

					int wherePosition = hql.indexOf("where");

					hql.insert(wherePosition - 1, " inner join valoraciones v on v.producto.id = p.id");
					hql.append(" group by p.id");
					hql.append(" order by avg(v.valoracion) desc");

				}

				break;

			}

		}

	}
	
	/**
	 * Forma un String que contiene un JSON con los productos proporcionados.
	 * @param productosList
	 * @return
	 */
	
	private String buildJsonProductoCriteria(ArrayList<Producto> productosList) {

		String jsonResult = null;

		if (productosList != null && !productosList.isEmpty()) {

			try {

				ObjectNode productosNode = mapper.createObjectNode();

				ArrayNode productosArrayNode = mapper.createArrayNode();

				for (Producto producto : productosList) {
					productosArrayNode.addPOJO(producto);
				}

				productosNode.set("products", productosArrayNode);

				jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(productosNode);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return jsonResult;

	}
	
	/**
	 * Ejecuta la consulta formada anteriormente por los criterios de
	 * filtrado establecidos.
	 * @param <T>
	 * @param hql
	 * @param parametrizar
	 * @return
	 */
	
	private <T> ArrayList<Producto> upQuery(StringBuilder hql, Parametrizar parametrizar) {

		Query query = entityManager.createQuery(hql.toString());

		ArrayList<T> parameterList = parametrizar.getParameters();

		// Settear los parámetros que se hayan proporcionado en el hql.

		for (int i = 0; i < parameterList.size(); i++) {
			query.setParameter("param" + i, parameterList.get(i));
		}

		ArrayList<Producto> productosResult = (ArrayList<Producto>) query.getResultList();

		System.out.println("Query => " + hql.toString() + " return " + productosResult.size() + " records.");

		return productosResult;

	}
	
}
