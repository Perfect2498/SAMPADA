/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author JaganMohan
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory; 
            private static HibernateUtil me;	  
	    private HibernateUtil() throws HibernateException {	      
               // String   filepath="D:\\HibernateConfiguration\\MeghaHMIABT\\hibernate.cfg.xml";
              //  File f = new File(filepath);
                Configuration con = new Configuration().configure("hibernate.cfg.xml");               
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(con.getProperties());
                ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();                               
                sessionFactory = con.buildSessionFactory(serviceRegistry);

	    }

	    public static synchronized HibernateUtil getInstance() throws HibernateException {
	        if (me == null) {

	            me = new HibernateUtil();

	        }
	        return me;
            }

	 

	    public Session getSession() throws HibernateException {

	        Session session = sessionFactory.openSession();

	        if (!session.isConnected()) {

	            this.reconnect();

	        }

	        return session;

	    }

	    private void reconnect() throws HibernateException {

	     //   String   filepath="D:\\HibernateConfiguration\\MeghaHMIABT\\hibernate.cfg.xml";
            //    File f = new File(filepath);
                Configuration con = new Configuration().configure("hibernate.cfg.xml");               
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(con.getProperties());
                ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();                               
                this.sessionFactory = con.buildSessionFactory(serviceRegistry);

	    }

}
