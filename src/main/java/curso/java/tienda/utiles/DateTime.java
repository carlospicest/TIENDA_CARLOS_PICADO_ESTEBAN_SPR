package curso.java.tienda.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

	/**
	 * Permite convertir una fecha Date con un determinado formato
	 * a Timestamp.
	 * @param date
	 * @param pattern
	 * @return
	 */
	
	public static long parseDateToTimestamp(String date, String pattern) {

		Date dateTransform = null;
		Timestamp ts = null;
		
		try {

			// DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
			DateFormat sdf = new SimpleDateFormat(pattern);

			dateTransform = sdf.parse(date);

			ts = new Timestamp(dateTransform.getTime() / 1000);

		} catch (ParseException e) {
			System.err.println("[ERROR] " + DateTime.class.getCanonicalName() + " => " + e.getMessage());
		}

		return ts.getTime();
		
	}

	/**
	 * Permite convertir una fecha Timestamp en Date con un formato
	 * específico.
	 * @param timestamp
	 * @param pattern
	 * @return
	 */
	
	public static String parseTimestampToDate(long timestamp, String pattern) {

        //DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        DateFormat sdf = new SimpleDateFormat(pattern);
        Timestamp ts = new Timestamp(timestamp * 1000);
        Date date = new Date(ts.getTime());

        return sdf.format(date);

    }
	
	public static java.sql.Timestamp getCurrentTime() {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp sqlTS = new java.sql.Timestamp(utilDate.getTime());
	    return sqlTS;
	}
	
	public static String getFormat(java.sql.Timestamp time, String pattern) {
		
		DateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(time);
	}
	
}
