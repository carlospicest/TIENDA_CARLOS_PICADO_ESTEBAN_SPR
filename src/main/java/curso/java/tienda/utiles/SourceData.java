package curso.java.tienda.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase que contiene métodos que permiten obtener información de diversas
 * fuentes.
 * 
 * @author Carlos
 *
 */

public class SourceData {

	public static ArrayList<String> getProvincias() {

		final String URL = "https://raw.githubusercontent.com/IagoLast/pselect/master/data/provincias.json";

		ArrayList<String> provincias = new ArrayList<>();

		String data = getDataFromURL(URL);
		ObjectMapper mapper = new ObjectMapper();

		try {

			JsonNode node = mapper.readTree(data);

			provincias = (ArrayList<String>) node.findValuesAsText("nm");

			Collections.sort(provincias, new StringComparator());

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return provincias;

	}

	public static String getDataFromURL(String url) {

		URL urlData;
		StringBuilder data = new StringBuilder();

		try {

			urlData = new URL(url);

			BufferedReader in = new BufferedReader(new InputStreamReader(urlData.openStream(), StandardCharsets.UTF_8));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				data.append(inputLine);

			in.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data.toString();

	}

}

class StringComparator implements Comparator<String> {

	private Collator collator;

	public StringComparator() {
		this.collator = Collator.getInstance(new Locale("es"));
		collator.setStrength(Collator.TERTIARY);
	}

	@Override
	public int compare(String str1, String str2) {
		return collator.compare(str1, str2);
	}

}
