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
import sampada.pojo.BankStatement;
import sampada.pojo.ReconciliationCorp;
import sampada.util.HibernateUtil;

/**
 *
 * @author Administrator
 */
public class ReconciliationCropDAO {
    
    
    public boolean NewReconciliationCorp(ReconciliationCorp usr) {

        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(usr);

            session.flush();

            tx.commit();

            session.close();
            System.out.println("In ReconciliationCropDAO  dao");
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
    public  int getMaxslno()
    {

        int result=0;
        BigDecimal temp=BigDecimal.ZERO;
        Session session=null;
        Transaction tx = null;
        try{

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(SLNO) FROM RECONCILIATION_CORP ";
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
    
    public boolean deleteReconciliationCorpbycorpid(int corpid,String year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
            
            String hql = "delete from ReconciliationCorp s where s.corporates.corporateId=:corpid and to_char(s.billEntryDate,'YYYY')=:year ";
            Query query = sess.createQuery(hql);
           
            query.setParameter("corpid", corpid);
            query.setParameter("year", year);
            System.out.println(query.executeUpdate());
            tx.commit();
            sess.flush();
            sess.close();
            return true;
        } catch (NumberFormatException e) {
            if (tx != null) {
                tx.rollback();
            }
            if (sess != null) {
                sess.close();
            }
            e.printStackTrace();

        }
        return false;
    }
    
    
    public List<ReconciliationCorp> getReconreportByCorpAnddatesYear( Date amtFrmDate, Date amtToDate) {

        Session session = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            Query query = session.createQuery("from ReconciliationCorp s where s.billEntryDate between :amtFrmDate and :amtToDate order by s.slno");

         
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<ReconciliationCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (ReconciliationCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<ReconciliationCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    
    public  int getcountslno()
    {

        int result=0;
        BigDecimal temp=BigDecimal.ZERO;
        Session session=null;
        Transaction tx = null;
        try{

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select count(SLNO) FROM RECONCILIATION_CORP ";
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
    
    
    public List<ReconciliationCorp> getFullReconreport() {

        Session session = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            Query query = session.createQuery("from ReconciliationCorp s order by s.slno ");

//         
//            query.setParameter("amtFrmDate", amtFrmDate);
//            query.setParameter("amtToDate", amtToDate);

            List<ReconciliationCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (ReconciliationCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<ReconciliationCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    
    public int getUpdateslnoforrecontable(int slno, int i) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "update BANK_STATEMENT set MAPPED_AMOUNT='" + mappedamount + "', MAPPED_BALANCE='" + mappedbalance + "',REMARKS='" + remarks + "' where STMT_ID=" + stmtid + " ";

            String hql = "update RECONCILIATION_CORP set SLNO='" + i + "' where SLNO=" + slno + " ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
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
