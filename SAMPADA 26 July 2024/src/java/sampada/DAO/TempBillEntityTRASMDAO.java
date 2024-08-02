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
import sampada.pojo.TempBillEntityTrasM;
import sampada.util.HibernateUtil;

/**
 *
 * @author root
 */
public class TempBillEntityTRASMDAO { 

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_ENTITY_TRAS_M ";
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

    public boolean NewTempBillEntityTRASM(TempBillEntityTrasM dic) {

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

    public boolean deleteTempBillEntityTRASMbystatus() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillEntityTrasM where billStatus='Pending'";
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

    public List<Object[]> getTempTRASMBillDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select e.entitty_name,t.TRAS_UP_CLEARED_AHEAD,\n"
                    + "t.TRAS_UP_SCHEDULED_AHEAD,t.TRAS_UP_ENERGY_CHARGES_AHEAD ,t.TRAS_UP_COM_CHARGES_AHEAD,t.TRAS_UP_CLEARED_REAL,t.TRAS_UP_SCHEDULED_REAL,t.TRAS_UP_ENERGY_CHARGES_REAL,\n"
                    + "t.TRAS_UP_COM_CHARGES_REAL,t.TRAS_UP_TOTAL_CHARGES, t.TRAS_DOWN_SCHEDULED_AHEAD, \n"
                    + "t.TRAS_DOWN_ENERGY_CHARGES_AHEAD,t.TRAS_DOWN_SCHEDULED_REAL, t.TRAS_DOWN_ENERGY_CHARGES_REAL,\n"
                    + "t.NET_TRAS,t.WR_NET_TRAS,t.REMARKS\n"
                    + "from entites e, TEMP_BILL_ENTITY_TRAS_M t\n"
                    + "where t.week_id=" + weekId + " and t.revision_no=" + revisionNo + " and t.bill_year=" + yearId + " and t.entity_id=e.entity_id ";

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

    
    public int updatetempbillentitytrasmstatus() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_ENTITY_TRAS_M set BILL_STATUS='Verified' where BILL_STATUS='Pending' ";
            System.out.println("updatetempbillentitytrasmstatus Query is " + hql);
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
    
    public boolean deleteTempBillPayableTRASMDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillEntityTrasM where weekId=:weekId and revisionNo=:revisionNo and billYear=:year";
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
}
