/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.ToDisbursedInterestDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class ToDisbursedInterestDetailsDAO {
    
    
    
    public boolean NewToDisbursedInterestDetails(ToDisbursedInterestDetails dic) {

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(dic);

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

    public int getMaxDisInterestId() {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(INTEREST_ID),0) from TO_DISBURSED_INTEREST_DETAILS";

            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);

            result = bg.intValueExact();

            System.out.println("result is " + result);

            tx.commit();

            session.flush();

            session.close();

            return result;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return result;

    }
    
//==============================================================================================
    
    public void sendToDisburse_Interest_DetailstoChecker() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "update TO_DISBURSED_INTEREST_DETAILS set CHECKER_STATUS='ToChecker' where CHECKER_STATUS='Pending'";

            SQLQuery query = session.createSQLQuery(hql);

            int bg = query.executeUpdate();

            tx.commit();
            session.flush();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
    
    public void rejectToDisburseInterest_DetailsfromChecker() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "update TO_DISBURSED_INTEREST_DETAILS set CHECKER_STATUS='Pending' where CHECKER_STATUS='ToChecker'";

            SQLQuery query = session.createSQLQuery(hql);

            int bg = query.executeUpdate();

            tx.commit();
            session.flush();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
    
    public void acceptToDisburseInterest_DetailsfromChecker() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "update TO_DISBURSED_INTEREST_DETAILS set CHECKER_STATUS='Verified' where CHECKER_STATUS='ToChecker'";

            SQLQuery query = session.createSQLQuery(hql);

            int bg = query.executeUpdate();

            tx.commit();
            session.flush();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
    
    public List<ToDisbursedInterestDetails> getAllToDisbursedInterestDetails() {
        
        Session session = null;
        List<ToDisbursedInterestDetails> list = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query query = session.createQuery("from ToDisbursedInterestDetails t");           
            list = query.list();
            
            for(ToDisbursedInterestDetails e : list)
                Hibernate.initialize(e.getCorporates());
            
            return list;
        }
        catch(Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        finally {
            session.flush();
            session.close();
        }
        
        return list;
    }
    
    public List<ToDisbursedInterestDetails> getToDisbInterestDetailsbydates(Date amtFrmDate, Date amtToDate) {

        List<ToDisbursedInterestDetails> list = null;
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
//            String hql = "from TempDisbInterestDetails s where s.checkerStatus=:status and s.disbursedDate between '"+date1+"' and '"+date2+"'";

            String hql = "from ToDisbursedInterestDetails s where TO_DATE(s.disbursedDate) between :amtFrmDate and :amtToDate ";
            System.out.println(" is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            list = query.list();
            for (ToDisbursedInterestDetails e : list) {
                Hibernate.initialize(e.getCorporates());
            }
            session.close();
            return list;

        } 
        catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
}
