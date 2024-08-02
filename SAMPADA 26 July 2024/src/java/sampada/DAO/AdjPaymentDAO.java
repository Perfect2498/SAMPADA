/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.AdjPayment;
import sampada.pojo.TempPaymentDisbursement;
import sampada.util.HibernateUtil;

/**
 *
 * @author Administrator
 */
public class AdjPaymentDAO {
    
    
     public boolean NewAdjPayment(AdjPayment dic) {

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
     
     
     
     public BigDecimal getMaxSlno() {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(UNIQUE_ID),0) from ADJ_PAYMENT";

            SQLQuery query = session.createSQLQuery(hql);

            int length = query.list().size();

            if (length != 0) {

                result = (BigDecimal) query.list().get(0);

                System.out.println("result is " + result);

            }

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
     
     
     
     public List<AdjPayment> getPendingAdjPaymentListbyCorp(int corpid)

    {

        List<AdjPayment> list = null;        

        Session session=null;

        try{
            session = HibernateUtil.getInstance().getSession();

            String hql="from AdjPayment s where s.corporates.corporateId=:corpid and s.status='Pending' order by s.uniqueId ASC";

            System.out.println("hql is "+hql);

            Query query = session.createQuery(hql);            

           
            query.setParameter("corpid", corpid);

            list= query.list(); 

            for(AdjPayment e:list)

            {

                Hibernate.initialize(e.getCorporates());               

            }

            session.flush();

            session.close();

            return list;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return list;

    }
     
    public List<AdjPayment> getPendingAdjPaymentListofallcorpforvalidations()

    {

        List<AdjPayment> list = null;        

        Session session=null;

        try{
            session = HibernateUtil.getInstance().getSession();

            String hql="from AdjPayment s where s.status='Pending' order by s.uniqueId ASC";

            System.out.println("hql is "+hql);

            Query query = session.createQuery(hql);  

            list= query.list(); 

            for(AdjPayment e:list)

            {

                Hibernate.initialize(e.getCorporates());               

            }

            session.flush();

            session.close();

            return list;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return list;

    }
     
     public List<AdjPayment> getPendingAdjPaymentListofallcorp()

    {

        List<AdjPayment> list = null;        

        Session session=null;

        try{
            session = HibernateUtil.getInstance().getSession();

            String hql="from AdjPayment s  order by s.uniqueId ASC";

            System.out.println("hql is "+hql);

            Query query = session.createQuery(hql);  

            list= query.list(); 

            for(AdjPayment e:list)

            {

                Hibernate.initialize(e.getCorporates());               

            }

            session.flush();

            session.close();

            return list;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return list;

    }
     
     public List<Object[]> getAllPendingAdjPaymentObjectlistforChecker() {

        Session session = null;

        List<Object[]> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from INTEREST_DETAILS s , corporates c  where s.CHECKER_STATUS='Pending' and s.corporate_id = c.corporate_id and s.INTEREST_PAIDAMOUNT IS NOT NULL ");        
            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from ADJ_PAYMENT s , corporates c where  s.STATUS='Pending' and s.CORP_ID = c.corporate_id ");

            queryList = query.list();

            return queryList;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
     
     
     public int deleteADJPAYMENTbycorpidbyChecker(int corpid) {
        int result = 0;
        Session session = null;
        Transaction tx;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "delete from ADJ_PAYMENT where STATUS='Pending' and CORP_ID='"+corpid+"' ";
            System.out.println("deleteAdjMappingbycorpidbyChecker is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();
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
     
     
     public BigDecimal getPendingAdjPaymentIDforChecker(int corpid) {

        Session session = null;

        BigDecimal result= BigDecimal.ZERO;
        try {

            session = HibernateUtil.getInstance().getSession();

//            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from INTEREST_DETAILS s , corporates c  where s.CHECKER_STATUS='Pending' and s.corporate_id = c.corporate_id and s.INTEREST_PAIDAMOUNT IS NOT NULL ");        
            SQLQuery query = session.createSQLQuery("select UNIQUE_ID from ADJ_PAYMENT s where  s.STATUS='Pending' and s.CORP_ID ='"+corpid+"'");

            result = (BigDecimal)query.list().get(0);;

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return result;

        } finally {

            session.close();

        }

    }
     
     public int getUpdateADJPAYMENTbyCheckerbyuniqueid(int uniqueid) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update ADJ_PAYMENT set STATUS='Confirmed', CHECKER_DATE=SYSDATE where UNIQUE_ID=" + uniqueid + "";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();
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
}
