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
import sampada.pojo.TempBillPayableEntityRras;
import sampada.util.HibernateUtil;

/**
 *
 * @author shubham
 */
public class TempBillPayableEntityRRASDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_PAYABLE_ENTITY_RRAS ";
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

    public boolean NewTempBillPayableRRASEntries(TempBillPayableEntityRras dic) {

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

    public int checkExistenceOfWeekIDandRevisionNo(BigDecimal weekid, BigDecimal revno) {

        Session session = null;
        Transaction tx = null;
        int result = 0;
        System.out.println("Inside checkExistenceOfTrid" + weekid);
        try {
            session = HibernateUtil.getInstance().getSession();
            String hql = "Select * from TEMP_BILL_PAYABLE_ENTITY_RRAS WHERE WEEK_ID=" + weekid + " and REVISION_NO=" + revno + "";
            SQLQuery q = session.createSQLQuery(hql);

            try {

                if (q.list().size() > 0) {
                    result = 1;
                } else {
                    result = 0;
                }
                System.out.println("result in dao is" + result);
                return result;
            } catch (Exception e) {
                System.out.println("Exception is" + e.getMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;

    }

    public List<Object[]> getTempRRASBillDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select e.entitty_name, t.ENERGY_VAE,t.VARIABLE_CHARGES,t.NET_RRAS, t.WR_NET_RRAS,t.remarks from entites e, corporates c ,temp_bill_payable_entity_rras t where t.week_id=" + weekId + " and t.revision_no=" + revisionNo + " and t.BILL_YEAR=" + yearId + " and t.revision_no=" + revisionNo + " and t.entity_id=e.entity_id and c.corporate_id=t.corporate_id";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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

    public boolean deleteTempBillPayableEntityRRASbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillPayableEntityRras where weekId=:weekId and revisionNo=:revisionNo and billYear=:yearId";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("revisionNo", revisionNo);
            query.setBigDecimal("yearId", yearId);
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

    public int updatetempbillpayableentityrrasstatus() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_PAYABLE_ENTITY_RRAS set BILL_STATUS='Verified' where BILL_STATUS='Pending' ";
            System.out.println("updatetempbillpayableentityrrasstatus Query is " + hql);
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
    
    public boolean deleteTempBillPayEntityRrasbystatus() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillPayableEntityRras where billStatus='Pending'";
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
