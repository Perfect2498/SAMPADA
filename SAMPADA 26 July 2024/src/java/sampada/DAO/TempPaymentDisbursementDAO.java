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
import sampada.pojo.PoolAccountDetails;
import sampada.pojo.TempPaymentDisbursement;
import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class TempPaymentDisbursementDAO {
    
    public boolean NewTempPaymentDisbursement(TempPaymentDisbursement usr) {

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
            String hql = "select max(DISBURSE_ID) FROM TEMP_PAYMENT_DISBURSEMENT ";
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

    public  int deleteTempDisburse()
    {

        int result=0;
        BigDecimal temp=BigDecimal.ZERO;
        Session session=null;
        Transaction tx = null;
        try{
            
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "delete FROM TEMP_PAYMENT_DISBURSEMENT where CHECKER_STATUS='Pending' ";
            SQLQuery query = session.createSQLQuery(hql);
            result=query.executeUpdate();
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
    public List<TempPaymentDisbursement> getTempDisbursementDetailsbyStatus(String status)

    {

        List<TempPaymentDisbursement> list = null;        

        Session session=null;

        try{
            session = HibernateUtil.getInstance().getSession();

            String hql="from TempPaymentDisbursement s where s.checkerStatus=:status order by s.disburseId ASC";

            System.out.println("hql is "+hql);

            Query query = session.createQuery(hql); 

            System.out.println("In Query, status is "+status);

            query.setParameter("status", status);

            list= query.list(); 

            for(TempPaymentDisbursement e:list)

            {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillReceiveCorp());

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
    
    public  int getUpdateTempDisbursebyChecker(BigDecimal uniqueid,String status)
    {

        int result=0;
        BigDecimal temp=BigDecimal.ZERO;
        Session session=null;
        Transaction tx = null;
        try{
            
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_PAYMENT_DISBURSEMENT  set CHECKER_STATUS='"+status+"' where UNIQUE_ID='"+uniqueid+"' ";
            SQLQuery query = session.createSQLQuery(hql);
            result=query.executeUpdate();
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
    
    public int getPendingCheckerCountByWeekId(int weekId, String billType) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(count(*),0) FROM TEMP_PAYMENT_DISBURSEMENT where WEEK_ID='"+weekId+"' and BILL_TYPE='"+billType+"' and CHECKER_STATUS='Pending'";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal)query.list().get(0);
                System.out.print("count is "+bg);
                result=bg.intValueExact();
                System.out.print("count is INT "+bg);
            }
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
