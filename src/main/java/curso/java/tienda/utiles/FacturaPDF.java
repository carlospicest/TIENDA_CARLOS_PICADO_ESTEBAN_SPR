package curso.java.tienda.utiles;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import curso.java.tienda.pojo.Configuracion;
import curso.java.tienda.pojo.Usuario;

public class FacturaPDF {

	public static void generarInformePartida(HashMap<String, Configuracion> configuracion) {

		PdfWriter writer = null;
		Document documento = new Document(PageSize.A4, 20, 20, 70, 50);

		try {

			// Obtenemos la instancia del archivo a utilizar
			writer = PdfWriter.getInstance(documento, new FileOutputStream("./generador/factura/informe.pdf"));

			// Para insertar cabeceras/pies en todas las páginas
			writer.setPageEvent(new PDFHeaderFooter(new Usuario(), configuracion));

			// Abrimos el documento para edición
			documento.open();

			// PARRAFOS
			Paragraph paragraph = new Paragraph();
			paragraph.add("\n\n");

			documento.add(paragraph);

			// TABLAS

			// Instanciamos una tabla de X columnas
			PdfPTable tabla = new PdfPTable(3);

			// Definir columnas

			Phrase preguntaPh = new Phrase("Pregunta");
			PdfPCell preguntaCell = new PdfPCell(preguntaPh);

			Phrase respuestaJugadorPh = new Phrase("Respuesta jugador");
			PdfPCell respuestaJugadorCell = new PdfPCell(respuestaJugadorPh);

			Phrase respuestaCorrectaPh = new Phrase("Respuesta correcta");
			PdfPCell respuestaCorrectaCell = new PdfPCell(respuestaCorrectaPh);

			// Propiedades concretas de formato
			preguntaCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			preguntaCell.setBorderWidth(1);

			respuestaJugadorCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			respuestaJugadorCell.setBorderWidth(1);

			respuestaCorrectaCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			respuestaCorrectaCell.setBorderWidth(1);

			tabla.addCell(preguntaCell);
			tabla.addCell(respuestaJugadorCell);
			tabla.addCell(respuestaCorrectaCell);

			documento.add(tabla);

			// Aciertos

			Paragraph paragraphAciertosLbl = new Paragraph();
			paragraphAciertosLbl.add("\n\nAciertos: ");

			Paragraph paragraphAciertos = new Paragraph();
			paragraphAciertos.add("MVSP");

			documento.add(paragraphAciertosLbl);
			documento.add(paragraphAciertos);

			// Fallos

			Paragraph paragraphFallosLbl = new Paragraph();
			paragraphFallosLbl.add("\nFallos: ");

			Paragraph paragraphFallos = new Paragraph();
			paragraphFallos.add("P");

			documento.add(paragraphFallosLbl);
			documento.add(paragraphFallos);

			// Puntos

			Paragraph paragraphPuntosLbl = new Paragraph();
			paragraphPuntosLbl.add("\nPuntos: ");

			Paragraph paragraphPuntos = new Paragraph();
			paragraphPuntos.add("sss");

			documento.add(paragraphPuntosLbl);
			documento.add(paragraphPuntos);

			documento.close(); // Cerramos el documento
			writer.close(); // Cerramos writer

			try {
				File path = new File("./generador/factura/informe.pdf");
				Desktop.getDesktop().open(path);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.getMessage();
		}

	}

}

class PDFHeaderFooter extends PdfPageEventHelper {

	private Usuario usuario;
	private HashMap<String, Configuracion> configuracion;

	public PDFHeaderFooter(Usuario usuario, HashMap<String, Configuracion> configuracion) {
		this.usuario = usuario;
		this.configuracion = configuracion;
	}

	// Template para el número total de páginas
	PdfTemplate total;

	// CABECERA
	// Evento que se ejecuta en cada nueva pagina del pdf

	public void onStartPage(PdfWriter writer, Document document) {

		// Declaramos la imagen y texto de la cabecera
		Phrase linea;
		Phrase txtCabecera;

		Phrase lblJugador;
		// Phrase lblNombreJugador;

		Phrase txtFecha;

		try {
			// Creamos un objeto PdfContentByte donde se guarda el contenido de una página
			// en el pdf. Gráficos y texto.
			PdfContentByte cb = writer.getDirectContent();

			Image imagen = Image.getInstance("./generador/assets/logo.png");
			// Convertimos la imagen a un Chunck (Chunck es la parte mas peque�a que puede
			// ser a�adida a un documento)
			Chunk chunk = new Chunk(imagen, -35, -50);
			// Convertimos el Chunk en un Phrase (Phrase es una serie de Chunks)
			Phrase imgCabecera = new Phrase(chunk);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, imgCabecera, document.rightMargin() + 90,
					document.top() + 60, 0);

			// Nombre empresa.

			String nombreEmpresa = configuracion.get("nombre_tienda").getValor();
			Phrase lblNombreEmpresa = new Phrase(nombreEmpresa,
					new Font(FontFactory.getFont("Sans", 10, Font.BOLD, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblNombreEmpresa, (document.right() - 5),
					document.top() + 45, 0);

			// Dirección empresa.
			// Label.
			String direccionEmpresa = "Dirección: ";
			Phrase lblDireccionEmpresa = new Phrase(direccionEmpresa,
					new Font(FontFactory.getFont("Sans", 10, Font.BOLD, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblDireccionEmpresa, (document.right() - 155),
					document.top() + 30, 0);

			// Dirección.

			String direccion = configuracion.get("direccion_tienda").getValor();
			Phrase lblDireccion = new Phrase(direccion, new Font(FontFactory.getFont("Sans", 10, Font.NORMAL, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblDireccion, (document.right() - 5), document.top() + 30, 0);

			// CIF/NIF empresa.
			// Label.
			String cifEmpresa = "CIF/NIF: ";
			Phrase lblCifEmpresa = new Phrase(cifEmpresa,
					new Font(FontFactory.getFont("Sans", 10, Font.BOLD, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblCifEmpresa, (document.right() - 60),
					document.top() + 15, 0);

			// CIF.

			String cif = configuracion.get("cif_tienda").getValor();
			Phrase lblCif = new Phrase(cif, new Font(FontFactory.getFont("Sans", 10, Font.NORMAL, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblCif, (document.right() - 5), document.top() + 15, 0);

			// Email
			// Label.
			String emailEmpresa = "Email: ";
			Phrase lblEmailEmpresa = new Phrase(emailEmpresa,
					new Font(FontFactory.getFont("Sans", 10, Font.BOLD, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblEmailEmpresa, (document.right() - 190),
					document.top() + 0, 0);

			String email = configuracion.get("email_tienda").getValor();
			Phrase lblEmail = new Phrase(email,
					new Font(FontFactory.getFont("Sans", 10, Font.NORMAL, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblEmail, (document.right() - 115), document.top() + 0,
					0);

			// Telefono
			// Label.
			String telefonoEmpresa = "Teléfono: ";
			Phrase lblTelefonoEmpresa = new Phrase(telefonoEmpresa,
					new Font(FontFactory.getFont("Sans", 10, Font.BOLD, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblTelefonoEmpresa, (document.right() - 60),
					document.top() + 0, 0);

			String telefono = configuracion.get("telefono_tienda").getValor();
			Phrase lblTelefono = new Phrase(telefono,
					new Font(FontFactory.getFont("Sans", 10, Font.NORMAL, BaseColor.BLACK)));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, lblTelefono, (document.right() - 5), document.top() + 0,
					0);

			// fecha
			/*
			 * SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy"); String
			 * fecha = "Fecha de la partida: " + formateador.format(new Date()); txtFecha =
			 * new Phrase(fecha, new Font(FontFactory.getFont("Sans", 10, Font.NORMAL,
			 * BaseColor.BLACK))); ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
			 * txtFecha, (document.right() - document.left()), document.top() + 30, 0);
			 */

			// linea de arriba
			linea = new Phrase();
			linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, -5));
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, document.rightMargin() - 4, document.top() - 5,
					0);

			// linea de abajo
			linea = new Phrase();
			linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, 0));
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, document.rightMargin(), document.top() - 720, 0);

			// Para dejar un margen debajo de la linea
			// document.add(new Paragraph(" "));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// PIE
	public void onEndPage(PdfWriter writer, Document document) {

		System.out.println("Ejecutando ondEndPage!");

		// Creamos un objeto PdfContentByte donde se guarda el contenido
		// de una página en el pdf. Gráficos y texto.
		PdfContentByte cb = writer.getDirectContent();

		// Asignamos el contenido al pie de página
		// getCurrentPageNumber() regresa la página actual
		Phrase pie = new Phrase(String.format("Página %d", writer.getCurrentPageNumber()));

		// Agregamos el pie a la página
		// con la siguiente nomenclaruta
		// ColumnText.showTextAligned(lienzo, alineacion, Phrase, posicion x, posicion
		// y, rotacion);
		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, pie,
				(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 20, 0);
	}

	public static void main(String[] args) {

		Configuracion c1 = new Configuracion();
		c1.setValor("Carlo's Shop S.A");

		Configuracion c2 = new Configuracion();
		c2.setValor("Calle del amanecer, 21 (Zamora)");

		Configuracion c3 = new Configuracion();
		c3.setValor("F10013647");

		Configuracion c4 = new Configuracion();
		c4.setValor("980547888");

		Configuracion c5 = new Configuracion();
		c5.setValor("carlos@shop.es");

		HashMap<String, Configuracion> configuracion = new HashMap<>();
		configuracion.put("nombre_tienda", c1);
		configuracion.put("direccion_tienda", c2);
		configuracion.put("cif_tienda", c3);
		configuracion.put("telefono_tienda", c4);
		configuracion.put("email_tienda", c5);

		FacturaPDF.generarInformePartida(configuracion);
	}

}
