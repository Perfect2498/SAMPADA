/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.PaymentInterestDisbursement;
import sampada.util.HibernateUtil;
/**
 *
 * @author Administrator
 */
public class PaymentInterestDisbursementDAO {
    
    
    

    
    
    public boolean NewPaymentInterestDisbursement(PaymentInterestDisbursement usr) {

        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(usr);

            session.flush();

            tx.commit();

            session.close();

            return true;

        } catch (Exception e) {

            if(tx!=null)

                tx.rollback();

            if(session!=null)

                session.close();

            e.printStackTrace();

        }
        return false;
    }
    
    public  int getMaxUDISBURSE_ID()
    {

        int result=0;
        BigDecimal temp=BigDecimal.ZERO;
        Session session=null;
        Transaction tx = null;
        try{

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(SLNO) FROM PAYMENT_INTEREST_DISBURSEMENT ";
            SQLQuery query = session.createSQLQuery(hql);
            if((query.list()!=null) && (query.list().size() > 0) && (query.list().get(0)!=null))
            {
     //          result =(Integer)query.list().get(0);
               temp =(BigDecimal)query.list().get(0);
               result=temp.intValue();
            }
            tx.commit();           
            session.flush();
            session.close();

            return result;

        }

        catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return 0;

    }
    
    public List<PaymentInterestDisbursement> getPaymentInterestDisbursedforChecker() {

 

       Session session = null;   

     

       try {

           session = HibernateUtil.getInstance().getSession();

           Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Pending' ");        

          

           List<PaymentInterestDisbursement> queryList = query.list();

           if (queryList != null && queryList.isEmpty()) {

 

               return null;

           } else {

               System.out.println("list size" + queryList.size());

               for (PaymentInterestDisbursement e : queryList) {

                   Hibernate.initialize(e.getDisbursedInterestDetails());              

               }

               return (List<PaymentInterestDisbursement>) queryList;

           }

 

       } catch (Exception e) {

 

           e.printStackTrace();

 

           return null;

 

       } finally {

 

           session.close();

 

       }

 

   }
    
    public int getUpdateStatusDisburseInterestbyId(String status) {

       int result = 0;

       BigDecimal bg = BigDecimal.ZERO;

       Session session = null;

       Transaction tx = null;

       try {

 

           session = HibernateUtil.getInstance().getSession();

           tx = session.beginTransaction();

           String hql = "update PAYMENT_INTEREST_DISBURSEMENT set CHECKER_STATUS='Confirmed' where CHECKER_STATUS='"+status+"'  ";

           SQLQuery query = session.createSQLQuery(hql);

           result=query.executeUpdate();         

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
    
     public int getDeletedPaymentInterestDisbursementbySLNO(int corpid)
    {
        int result=0;
        Session session=null;
        Transaction tx;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            
            String hql="delete from PAYMENT_INTEREST_DISBURSEMENT where SLNO='"+corpid+"' ";
            System.out.println(" is "+hql);
            SQLQuery query = session.createSQLQuery(hql);            
            result=query.executeUpdate();
            tx.commit();
            session.flush();
            session.close();
            return result;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return result;
    }
     
     //=====================================================================================
     
     public List<PaymentInterestDisbursement> getPaymentInterestDisbursed(Date fromdate, Date todate) {

       Session session = null;   
     
        try {
           session = HibernateUtil.getInstance().getSession();

           Query query = session.createQuery("from PaymentInterestDisbursement s where TO_DATE(s.entryDate) between :fromdate and :toDate and s.checkerStatus='Confirmed' ");
         
           query.setParameter("fromdate", fromdate);

           query.setParameter("toDate", todate);
           
           List<PaymentInterestDisbursement> queryList = query.list();

            for (PaymentInterestDisbursement e : queryList) {
                Hibernate.initialize(e.getDisbursedInterestDetails());              
                Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());              
            }

            return (List<PaymentInterestDisbursement>) queryList;
       }
       catch (Exception e) {
           e.printStackTrace();
           return null;
       }
       finally {
           session.close();
       }

   }
    
}
