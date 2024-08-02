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
import sampada.pojo.MappingRefundBank;
import sampada.pojo.RevertTempInterestDetails;
import sampada.pojo.TempInterestDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author abt
 */
public class RevertTempInterestDetailsDAO {

    public boolean NewRevertTempInterestDetails(RevertTempInterestDetails dic) {

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

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(SLNO),0) FROM REVERT_TEMP_INTEREST_DETAILS ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                result = bg.intValueExact();
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

    public List<RevertTempInterestDetails> getrevTempInterestDetails(String status) {
        List<RevertTempInterestDetails> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "from RevertTempInterestDetails s where s.checkerStatus=:status ";
            System.out.println(" is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            list = query.list();
            for (RevertTempInterestDetails e : list) {
                Hibernate.initialize(e.getTempInterestDetails());
                Hibernate.initialize(e.getTempInterestDetails().getCorporates());
            }
            session.close();
            return list;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
    
    public List<RevertTempInterestDetails> getRevertTempInterestDetailsbyid(int intid)

    {

        List<RevertTempInterestDetails> list = null;

       

        Session session=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            String hql="from RevertTempInterestDetails s where  s.slno=:intid  ";

            System.out.println(" hql  is "+hql);

            Query query = session.createQuery(hql);

           

            query.setParameter("intid", new BigDecimal(intid));
            

            list=query.list();

            for(RevertTempInterestDetails e:list)       

            {

                Hibernate.initialize(e.getTempInterestDetails());                  
                Hibernate.initialize(e.getTempInterestDetails().getCorporates());    
            }

            session.close();

            return list;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return list;

   }
    
    public int getUpdateRevertTempInterestDetailsbyidbyReverted(int interestid) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            String hql = "update REVERT_TEMP_INTEREST_DETAILS set CHECKER_STATUS='REVERT' where SLNO=" + interestid + "  ";
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
    
    public List<RevertTempInterestDetails> getRevertTempInterestDetailsbydates(String status, Date amtFrmDate, Date amtToDate) {
        List<RevertTempInterestDetails> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

//            String hql = "from TempInterestDetails s where s.checkerStatus=:status and s.paidDate between '"+date1+"' and '"+date2+"'";
            String hql = "from RevertTempInterestDetails s where s.checkerStatus=:status and TO_DATE(s.tempInterestDetails.paidDate) between :amtFrmDate and :amtToDate";

            System.out.println(" is " + hql);
            Query query = session.createQuery(hql);
            
            query.setParameter("status", status);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            list = query.list();
            for (RevertTempInterestDetails e : list) {
                Hibernate.initialize(e.getTempInterestDetails().getCorporates());
            }
            session.close();
            return list;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
}
