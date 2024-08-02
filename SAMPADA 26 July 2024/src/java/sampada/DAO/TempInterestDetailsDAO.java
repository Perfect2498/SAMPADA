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
import sampada.pojo.InterestDetails;
import sampada.pojo.TempInterestDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class TempInterestDetailsDAO {

    public int getMaxInterestid() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select max(interest_id) FROM TEMP_INTEREST_DETAILS ";
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

    public boolean NewTempInterestDetails(TempInterestDetails dic) {

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

    public List<TempInterestDetails> getTempInterestDetails(String status) {
        List<TempInterestDetails> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "from TempInterestDetails s where s.checkerStatus=:status ";
            System.out.println(" is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            list = query.list();
            for (TempInterestDetails e : list) {
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

    public int getUpdateTempInterestDetails() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_INTEREST_DETAILS set checker_status='Verified' ";
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

    public int getUpdateTempInterestDetailsbyinterestid(int interestid) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_INTEREST_DETAILS set checker_status='Verified' where INTEREST_ID=" + interestid + "  ";
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

    public int getUpdateTempInterestDetailsbyinterestidbyunvarified(int interestid) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_INTEREST_DETAILS set checker_status='UNVerified' where INTEREST_ID=" + interestid + "  ";
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

    public int getUpdateTempInterestDetailsbyinterestidbyPending(int interestid) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_INTEREST_DETAILS set checker_status='Pending' where INTEREST_ID=" + interestid + "  ";
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

    public List<TempInterestDetails> getTempInterestDetailsbyCorpid(String status, int Corpid) {

        List<TempInterestDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

            String hql = "from TempInterestDetails s where s.checkerStatus=:status and s.corporates.corporateId=:Corpid ";

            System.out.println(" is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("status", status);

            query.setParameter("Corpid", Corpid);

            list = query.list();

            for (TempInterestDetails e : list) {

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

    public List<InterestDetails> getTempInterestDetailsbyonlyCorpid(int Corpid, String year) {

        List<InterestDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

//            String hql = "from TempInterestDetails s where  s.checkerStatus='Verified' and s.corporates.corporateId=:Corpid and to_char(s.entryDate,'YYYY')=:year ";
            String hql = "from InterestDetails s where   s.corporates.corporateId=:Corpid and to_char(s.entryDate,'YYYY')=:year ";

            System.out.println(" hql  is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("Corpid", Corpid);
            query.setParameter("year", year);

            list = query.list();

            for (InterestDetails e : list) {

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

    public List<TempInterestDetails> getTempInterestDetailsbyinterestid(int intid) {

        List<TempInterestDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

            String hql = "from TempInterestDetails s where  s.interestId=:intid  ";

            System.out.println(" hql  is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("intid", new BigDecimal(intid));

            list = query.list();

            for (TempInterestDetails e : list) {

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

    public List<Object[]> getintnetvalues() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            String sql;

            sql = "select  coalesce(tablea.id, tableb.id) id,nvl(A,0),nvl(B,0),nvl(A,0)-nvl(B,0),coalesce(tablea.btype, tableb.btype) btype\n"
                    + "from\n"
                    + "(select (i.corporate_id)id,(i.BILL_TYPE)btype,sum(i.INTEREST_AMOUNT)A from TO_INTEREST_DETAILS i where i.CHECKER_STATUS='Pending' group by i.corporate_id,i.BILL_TYPE)tablea\n"
                    + "full join \n"
                    + "(select (d.corporate_id)id,(d.BILL_TYPE)btype,sum(d.INTEREST_AMOUNT)B from TO_DISBURSED_INTEREST_DETAILS d where d.CHECKER_STATUS='Pending' group by d.corporate_id,d.BILL_TYPE)tableb\n"
                    + "on tablea.id=tableb.id and tablea.btype=tableb.btype";

            System.out.println("hql is " + sql);

            // Query query = session.createQuery(hql);  
            SQLQuery query = session.createSQLQuery(sql);

            List<Object[]> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                System.out.println("list size" + queryList.size());
                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            session.close();

        }

    }
    
    
    public int getUpdateToInterestDetailsbtoverified() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TO_INTEREST_DETAILS set checker_status='Verified' where checker_status='ToChecker' ";
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
    
    public int getUpdateTodisburseInterestDetailsbtoverified() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TO_DISBURSED_INTEREST_DETAILS set checker_status='Verified' where checker_status='ToChecker' ";
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
    
    public List<TempInterestDetails> getTempviewInterestDetails(String status, Date amtFrmDate, Date amtToDate) {
        List<TempInterestDetails> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

//            String hql = "from TempInterestDetails s where s.checkerStatus=:status and s.paidDate between '"+date1+"' and '"+date2+"'";
            String hql = "from TempInterestDetails s where s.checkerStatus=:status and TO_DATE(s.paidDate) between :amtFrmDate and :amtToDate";

            System.out.println(" is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            list = query.list();
            for (TempInterestDetails e : list) {
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
    
}
