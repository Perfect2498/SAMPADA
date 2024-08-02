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
import sampada.pojo.TempBillEntityAgc;
import sampada.pojo.TempBillEntitySras;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class TempBillEntityAgcDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_ENTITY_AGC ";
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
    
    public int getMaxUniqueIDtempbillentitysras() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_ENTITY_SRAS";
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

    public boolean NewTempBillAGCEntries(TempBillEntityAgc dic) {

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
    
    public boolean NewTempBillSRASEntries(TempBillEntitySras dic) {

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

    public boolean deleteTempBillPayableAGCDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillEntityAgc where weekId=:weekId and revisionNo=:revisionNo and billYear=:year";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("revisionNo", revisionNo);
            query.setBigDecimal("year", year);
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
    
    public boolean deleteTempBillPayableSRASDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillEntitySras where weekId=:weekId and revisionNo=:revisionNo and billYear=:year";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("revisionNo", revisionNo);
            query.setBigDecimal("year", year);
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

    public List<Object[]> getTempAGCBillDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select e.entitty_name,t.TOTAL_AGC_UPDOWN_MWH,t.MARKUP_CHARGES ,t.TOTALNET_AGC,t.AGC_ENERGYCHARGES,t.TOTALCHARGES, t.WR_TOTALCHARGES,t.PAY_RECVFLAG, t.REMARKS from entites e, temp_bill_entity_agc t where t.week_id=" + weekId + " and t.revision_no=" + revisionNo + " and t.bill_year=" + yearId + " and t.entity_id=e.entity_id ";

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
    
    public List<Object[]> getTempSRASBillDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select e.entitty_name,t.TOTAL_AGC_UPDOWN_MWH,t.TOTAL_AGC_DOWN_MWH,t.MARKUP_CHARGES ,t.TOTALNET_AGC,t.AGC_ENERGYCHARGES,t.TOTALCHARGES, t.WR_TOTALCHARGES,t.PAY_RECVFLAG, t.REMARKS from entites e, temp_bill_entity_sras t where t.week_id=" + weekId + " and t.revision_no=" + revisionNo + " and t.bill_year=" + yearId + " and t.entity_id=e.entity_id ";

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

    public int updatetempbillentityagcstatus() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_ENTITY_AGC set BILL_STATUS='Verified' where BILL_STATUS='Pending' ";
            System.out.println("updatetempbillentityagcstatus Query is " + hql);
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
    
    public int updatetempbillentitysrasstatus() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_ENTITY_SRAS set BILL_STATUS='Verified' where BILL_STATUS='Pending' ";
            System.out.println("updatetempbillentitysrasstatus Query is " + hql);
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

    public boolean deleteTempBillEntityAgcbystatus() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillEntityAgc where billStatus='Pending'";
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
    
    public boolean deleteTempBillEntitySrasbystatus() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillEntitySras where billStatus='Pending'";
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
