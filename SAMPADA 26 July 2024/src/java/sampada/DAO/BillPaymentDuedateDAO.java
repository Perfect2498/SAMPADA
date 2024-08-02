/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BillEntityTrasE;
import sampada.pojo.BillPaymentDuedate;

import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class BillPaymentDuedateDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(SLNO),0) FROM BILL_PAYMENT_DUEDATE ";
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

    public boolean NewBillPaymentDuedate(BillPaymentDuedate dic) {

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

    public List<BillPaymentDuedate> getBillPaymentDuedate(String billtype, String category) {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPaymentDuedate s where s.billtype=:billtype and s.category=:category ");
            query.setParameter("billtype", billtype);
            query.setParameter("category", category);

            List<BillPaymentDuedate> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return (List<BillPaymentDuedate>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }

    }

    public List<BillPaymentDuedate> getBillPaymentDuedatebyfromdate(String billtype, String category, Date fromdate) {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPaymentDuedate s where s.billtype=:billtype and s.category=:category and s.fromDate<=:fromdate and coalesce(s.toDate, sysdate())>:fromdate");
            query.setParameter("billtype", billtype);
            query.setParameter("category", category);
            query.setParameter("fromdate", fromdate);

            List<BillPaymentDuedate> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return (List<BillPaymentDuedate>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }

    }

    public List<BillPaymentDuedate> getpendingBillPaymentDuedate() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPaymentDuedate s where s.status='Pending'");

            List<BillPaymentDuedate> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return (List<BillPaymentDuedate>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }

    }

    public int updatestatusbychecker(Date d) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("update BillPaymentDuedate s set s.status='Verified',s.checkerDate=:d where s.status='Pending' ");
            query.setParameter("d", d);

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

    public boolean deleteBillPaymentDuedatebystatus() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from BillPaymentDuedate where status='Pending'";
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

    public int updatetodatebytypebycategory(String billtype, String category, Date todate) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("update BillPaymentDuedate s set s.toDate=:todate where s.billtype=:billtype and s.category=:category and (s.toDate IS NULL) and s.status='Verified'");
            query.setParameter("billtype", billtype);
            query.setParameter("category", category);
            query.setParameter("todate", todate);

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

    public List<BillPaymentDuedate> getBillPaymentDuedateDetails() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPaymentDuedate s where s.status='Verified'");

            List<BillPaymentDuedate> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return (List<BillPaymentDuedate>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }

    }

    public List<BigDecimal> getdistinctyearbytypebycategory(String billtype, String category) {
        List<BigDecimal> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
//            String hql = "select distinct week_id from BILL_PAYABLE_CORP where (BILL_STATUS='NOT PAID' OR BILL_STATUS='PARTIALLY')  order by week_id ASC ";
            String hql = "select distinct BILL_YEAR from BILL_PAYMENT_DUEDATE where  BILLTYPE='" + billtype + "' and CATEGORY='" + category + "'  order by BILL_YEAR DESC;";

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

    public List<BillPaymentDuedate> getBillPaymentDuedateforfromdate(String billtype, String category) {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPaymentDuedate s where s.billtype=:billtype and s.category=:category and (s.toDate IS NULL) order by slno desc");
            query.setParameter("billtype", billtype);
            query.setParameter("category", category);

            List<BillPaymentDuedate> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return (List<BillPaymentDuedate>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }

    }

}
