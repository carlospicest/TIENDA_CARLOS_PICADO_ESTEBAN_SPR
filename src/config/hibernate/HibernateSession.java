package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {
	
	private final static String FILE_NAME = "hibernate.cfg.xml";
	private final static String XML_PATH = "hibernate/";
	
	public static SessionFactory makeSessionFactory() {
		
		return new Configuration()
								 .configure(XML_PATH + FILE_NAME)
								 .buildSessionFactory();
		
	}
	
}
