/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.TempBillDsmDetails;
import sampada.pojo.TempBillFrasDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class TempBillFRASDetailsDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_FRAS_DETAILS ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Max UNIQUE ID is " + bg);
                result = bg.intValueExact();
                System.out.print("Max UNIQUE ID is INT " + bg);
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

    public boolean NewTempBillFRASDetails(TempBillFrasDetails dic) {

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

    public int checkExistenceOfWeekIDandRevisionNo(BigDecimal weekid, BigDecimal revno, BigDecimal year) {

        Session session = null;
        Transaction tx = null;
        int result = 0;
        System.out.println("Inside checkExistenceOfTrid" + weekid);
        try {
            session = HibernateUtil.getInstance().getSession();
            String hql = "Select * from TEMP_BILL_FRAS_DETAILS WHERE WEEK_ID=" + weekid + " and REVISION_NO=" + revno + " and BILL_YEAR=" + year + "";
            SQLQuery q = session.createSQLQuery(hql);

            try {

                if (q.list().size() > 0) {
                    result = 1;
                } else {
                    result = 0;
                }
                System.out.println("checkExistenceOfWeekIDandRevisionNo FRAS result in dao is" + result);
                return result;
            } catch (Exception e) {
                System.out.println("Exception is" + e.getMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;

    }

    public TempBillFrasDetails getRevisionNoByWeekId(BigDecimal revisionNo) {
        List<TempBillFrasDetails> list = null;
        TempBillFrasDetails approvedList = new TempBillFrasDetails();
        Session session = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "FROM TempBillAgcDetails E where E.revisionNo=:revisionNo ";
            Query query = session.createQuery(hql);
            query.setParameter("revisionNo", revisionNo);
            list = query.list();
            if (list.size() > 0) {
                approvedList = (TempBillFrasDetails) query.list().get(0);
            }
            session.close();
            return approvedList;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return approvedList;
    }

    public boolean deleteTempBillFRASDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearid) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillFrasDetails where weekId=:weekId and billYear=:yearid and revisionNo=:revisionNo";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("yearid", yearid);
            query.setBigDecimal("revisionNo", revisionNo);
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

    public String getBillNobyWeekIdandRevisionNo(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {
        String elemname = null;
        Session session = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.billNo FROM TempBillFrasDetails E where E.weekId=:weekId and E.billYear=:yearId and E.revisionNo=:revisionNo";
            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("yearId", yearId);

            elemname = (String) query.list().get(0);
            session.close();
            return elemname;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return elemname;
    }

    public Date getBillingDatebyWeekIdandRevisionNo(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {

        Session session = null;
        Date maxreplydate = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.billingDate FROM TempBillFrasDetails E where E.weekId=:weekId and E.revisionNo=:revisionNo and E.billYear=:yearId";
            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("yearId", yearId);
            maxreplydate = (Date) query.list().get(0);

            session.close();
            return maxreplydate;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return maxreplydate;
    }

    public Date getFromDatebyWeekIdandRevisionNo(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {

        Session session = null;
        Date maxreplydate = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.weekFromdate FROM TempBillFrasDetails E where E.weekId=:weekId and E.revisionNo=:revisionNo and E.billYear=:yearId";
            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("yearId", yearId);
            maxreplydate = (Date) query.list().get(0);

            session.close();
            return maxreplydate;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return maxreplydate;
    }

    public Date getToDatebyWeekIdandRevisionNo(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {

        Session session = null;
        Date maxreplydate = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.weekTodate FROM TempBillFrasDetails E where E.weekId=:weekId and E.revisionNo=:revisionNo and E.billYear=:yearId";
            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("yearId", yearId);
            maxreplydate = (Date) query.list().get(0);

            session.close();
            return maxreplydate;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return maxreplydate;
    }

    public BigDecimal getMaxRevNoByWeekId(BigDecimal weekNo, BigDecimal year) {
        BigDecimal revNo = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(REVISION_NO) from TEMP_BILL_FRAS_DETAILS WHERE WEEK_ID='" + weekNo + "' and BILL_YEAR='" + year + "'";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                revNo = (BigDecimal) query.list().get(0);
                System.out.println("entityName is " + revNo);
            }
            tx.commit();
            session.flush();
            session.close();
            return revNo;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return revNo;
    }

    public List<TempBillFrasDetails> gettempfrasdetilsbyweekyearrev(BigDecimal weekid, BigDecimal yearid, BigDecimal rev) {

        List<TempBillFrasDetails> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "from TempBillFrasDetails s where  s.weekId=:weekid  and s.billYear=:yearid and s.revisionNo=:rev ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("weekid", weekid);
            query.setParameter("yearid", yearid);
            query.setParameter("rev", rev);

            list = query.list();

//            for (BillPayableCorp e : list) {
//
//                Hibernate.initialize(e.getCorporates());
//
//               
//
//            }
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

    public int getminyearoffrasbillsbygivenyear(int year) {
        int result = Calendar.getInstance().get(Calendar.YEAR);
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select min(extract(YEAR from s.BILLING_DATE)) from TEMP_BILL_FRAS_DETAILS s\n"
                    + "where extract(YEAR from s.ENTRY_DATE)='" + year + "'\n"
                    + "group by extract(YEAR from s.ENTRY_DATE)";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = Integer.parseInt(query.list().get(0).toString());
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

    public int updatetempbillfrasdetailsstatus() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_FRAS_DETAILS set BILL_STATUS='Verified' where BILL_STATUS='Pending' ";
            System.out.println("updatetempbillfrasdetailsstatus Query is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
            System.out.println("length is " + length);
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

    public boolean deleteTempBillFRASDetailsbystatus() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillFrasDetails where billStatus='Pending'";
            Query query = sess.createQuery(hql);

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
}
