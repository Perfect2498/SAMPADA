/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BillPriority;
import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class BillPriorityDAO {
    
    public List<BillPriority> getBillPriorityDetails() {

	        Session session = null;                
	        try {
	            session = HibernateUtil.getInstance().getSession();
                    Query query = session.createQuery("from BillPriority s order by s.orderby ASC");
                   
                    List<BillPriority> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) {

	                return null;
	            } else {
	                System.out.println("list size" + queryList.size());
	                return (List<BillPriority>) queryList;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;

	        } finally {
	            session.close();
	        }

	    }
    
    public boolean NewDisburseBillPriorityDetails(BillPriority loc) {

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(loc);

            session.flush();

            tx.commit();

            session.close();

            return true;

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            if (session != null) {
                session.close();
            }

            e.printStackTrace();

        }
        return false;
    }
    
    
    
}
