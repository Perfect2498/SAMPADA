/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.TempBillTrasEDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author root
 */
public class TempBillTRASEDetailsDAO {
    
    
    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_TRAS_E_DETAILS ";
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
    
    public boolean NewTempBillTRASEDetails(TempBillTrasEDetails dic) {

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
    
    public boolean deleteTempBillTRASEDetailsbystatus() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillTrasEDetails where billStatus='Pending'";
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
    
    
    
    public BigDecimal getMaxRevNoByWeekIdTRASE(BigDecimal weekNo, BigDecimal year) {
        BigDecimal revNo = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(REVISION_NO) from TEMP_BILL_TRAS_E_DETAILS WHERE WEEK_ID='" + weekNo + "' and BILL_YEAR='" + year + "'";
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
    
    
    public int checkExistenceOfWeekIDandRevisionNoTRASE(BigDecimal weekid, BigDecimal revno, BigDecimal year) {

        Session session = null;
        Transaction tx = null;
        int result = 0;
        System.out.println("Inside checkExistenceOfTrid" + weekid);
        try {
            session = HibernateUtil.getInstance().getSession();
            String hql = "Select * from TEMP_BILL_TRAS_E_DETAILS WHERE WEEK_ID=" + weekid + " and REVISION_NO=" + revno + " and BILL_YEAR=" + year + "";
            SQLQuery q = session.createSQLQuery(hql);

            try {

                if (q.list().size() > 0) {
                    result = 1;
                } else {
                    result = 0;
                }
                System.out.println("checkExistenceOfWeekIDandRevisionNo TRASE result in dao is" + result);
                return result;
            } catch (Exception e) {
                System.out.println("Exception is" + e.getMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;

    }
    
    public List<TempBillTrasEDetails> gettemptrasedetilsbyweekyearrev(BigDecimal weekid, BigDecimal yearid, BigDecimal rev) {

        List<TempBillTrasEDetails> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "from TempBillTrasEDetails s where  s.weekId=:weekid  and s.billYear=:yearid and s.revisionNo=:rev ";

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
    
    
    public int updatetempbilltrasedetailsstatus() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_TRAS_E_DETAILS set BILL_STATUS='Verified' where BILL_STATUS='Pending' ";
            System.out.println("updatetempbilltrasedetailsstatus Query is " + hql);
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
    
    public boolean deleteTempBillTRASEDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearid) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillTrasEDetails where weekId=:weekId and billYear=:yearid and revisionNo=:revisionNo";
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
    
}
