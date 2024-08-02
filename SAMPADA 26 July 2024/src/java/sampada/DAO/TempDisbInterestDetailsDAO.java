/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */
package sampada.DAO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import java.util.List;

import org.hibernate.Hibernate;

import org.hibernate.Query;

import org.hibernate.SQLQuery;

import org.hibernate.Session;

import org.hibernate.Transaction;
import sampada.pojo.DisbursedInterestDetails;

import sampada.pojo.TempDisbInterestDetails;

import sampada.util.HibernateUtil;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class TempDisbInterestDetailsDAO {

    public int getMaxDisInterestid() {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select max(interest_id) FROM TEMP_DISB_INTEREST_DETAILS ";

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

    public boolean NewTempDisbInterestDetails(TempDisbInterestDetails dic) {

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

    public List<TempDisbInterestDetails> getTempDisbInterestDetails(String status) {

        List<TempDisbInterestDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

            String hql = "from TempDisbInterestDetails s where s.checkerStatus=:status ";

            System.out.println(" is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("status", status);

            list = query.list();

            for (TempDisbInterestDetails e : list) {

                Hibernate.initialize(e.getCorporates());

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
    
    public List<TempDisbInterestDetails> getTempDisbInterestDetailsbyinterestid(int intid) {

        List<TempDisbInterestDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

            String hql = "from TempDisbInterestDetails s where s.interestId=:intid  ";

            System.out.println(" is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("intid", new BigDecimal(intid));

            list = query.list();

            for (TempDisbInterestDetails e : list) {

                Hibernate.initialize(e.getCorporates());

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

    public int getUpdateTempDisbInterestDetailsforprocessing() {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        Transaction tx;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update TEMP_DISB_INTEREST_DETAILS set checker_status='Verified' ";

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

    public int getUpdateTempDisbInterestDetailsforprocessingbyinterestid(int interestid) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            String hql = "update TEMP_DISB_INTEREST_DETAILS set checker_status='Verified' where INTEREST_ID=" + interestid + "  ";
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
    
    public int getUpdateTempDisbInterestDetailsforprocessingbyinterestidbyUNVerified(int interestid) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            String hql = "update TEMP_DISB_INTEREST_DETAILS set checker_status='UNVerified' where INTEREST_ID=" + interestid + "  ";
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
    
    public int getUpdateTempDisbInterestDetailsbyinterestidbyPending(int interestid) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            String hql = "update TEMP_DISB_INTEREST_DETAILS set checker_status='Pending' where INTEREST_ID=" + interestid + "  ";
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
    
    public List<Object[]> getAllInterestReceivableBillCorpObjectlist() {

        Session session = null;

        List<Object[]> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from DISBURSED_INTEREST_DETAILS s , corporates c  where s.INTEREST_PENDINGAMOUNT !=0 and s.corporate_id = c.corporate_id ");

            queryList = query.list();

            return queryList;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<Timestamp> getdisbInterestDetailsbyCorpgroupbyEntryDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(s.ENTRY_DATE)) from DISBURSED_INTEREST_DETAILS s,CORPORATES c where  s.CORPORATE_ID=c.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "' and extract(YEAR from s.ENTRY_DATE)='" + year + "'  order by TRUNC(s.ENTRY_DATE) ASC";

//          String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and bill_type='" + billtype + "' )  order by TRUNC(s.billing_date) ASC";
            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.print("Size of group bydate is" + list.size());

            tx.commit();

            session.flush();

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

    public List<DisbursedInterestDetails> getTempdisbInterestDetailsbyonlyCorpid(int Corpid, String year) {

        List<DisbursedInterestDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

//            String hql = "from TempDisbInterestDetails s where  s.corporates.corporateId=:Corpid and s.checkerStatus='Verified'";
            String hql = "from DisbursedInterestDetails s where  s.corporates.corporateId=:Corpid and to_char(s.entryDate,'YYYY')=:year";

            
            System.out.println(" hql  is " + hql);
            

            Query query = session.createQuery(hql);

            query.setParameter("Corpid", Corpid);
            query.setParameter("year", year);
            list = query.list();
            System.out.println(" hql size is " + list.size());
            for (DisbursedInterestDetails e : list) {

                Hibernate.initialize(e.getCorporates());

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
    
    public List<TempDisbInterestDetails> getTempviewDisbInterestDetails(String status, Date amtFrmDate, Date amtToDate) {

        List<TempDisbInterestDetails> list = null;
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
//            String hql = "from TempDisbInterestDetails s where s.checkerStatus=:status and s.disbursedDate between '"+date1+"' and '"+date2+"'";

            String hql = "from TempDisbInterestDetails s where s.checkerStatus=:status and TO_DATE(s.disbursedDate) between :amtFrmDate and :amtToDate ";
            System.out.println(" is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            list = query.list();
            for (TempDisbInterestDetails e : list) {
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
