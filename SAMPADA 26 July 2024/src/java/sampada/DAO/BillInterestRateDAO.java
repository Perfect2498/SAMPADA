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
import sampada.pojo.BillInterestRate;

import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class BillInterestRateDAO {
    
    public List<BillInterestRate> getBillInterestRate(String billtype,String category) {

	        Session session = null;                
	        try {
	            session = HibernateUtil.getInstance().getSession();
                    Query query = session.createQuery("from BillInterestRate s where s.billtype=:billtype and s.category=:category");
                    query.setParameter("billtype", billtype);
                    query.setParameter("category", category);
                   
                    List<BillInterestRate> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) {

	                return null;
	            } else {
	                System.out.println("list size" + queryList.size());
	                return (List<BillInterestRate>) queryList;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;

	        } finally {
	            session.close();
	        }

	    }
    
    public boolean NewLoCDetails(BillInterestRate loc) {

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
    
    public List<BillInterestRate> billInterestList() {
        Session session = null;
        List<BillInterestRate> queryList;
        try {
            System.out.print("Inside Bill Interest Rate DAO");
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillInterestRate s ");
            queryList = query.list();
            return queryList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
}
