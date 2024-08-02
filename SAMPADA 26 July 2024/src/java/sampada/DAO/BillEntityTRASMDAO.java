/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityTrasM;
import sampada.pojo.TempBillEntityTrasM;
import sampada.util.HibernateUtil;

/**
 *
 * @author root
 */
public class BillEntityTRASMDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM BILL_ENTITY_TRAS_M ";
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

    public List<Object[]> getDiffBillPay(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.TRAS_UP_CLEARED_AHEAD - PRV.TRAS_UP_CLEARED_AHEAD) TRAS_UP_CLEARED_AHEAD ,(CUR.TRAS_UP_SCHEDULED_AHEAD - PRV.TRAS_UP_SCHEDULED_AHEAD) TRAS_UP_SCHEDULED_AHEAD, (CUR.TRAS_UP_ENERGY_CHARGES_AHEAD - PRV.TRAS_UP_ENERGY_CHARGES_AHEAD) TRAS_UP_ENERGY_CHARGES_AHEAD, (CUR.TRAS_UP_COM_CHARGES_AHEAD - PRV.TRAS_UP_COM_CHARGES_AHEAD) TRAS_UP_COM_CHARGES_AHEAD, (CUR.TRAS_UP_CLEARED_REAL - PRV.TRAS_UP_CLEARED_REAL) TRAS_UP_CLEARED_REAL, (CUR.TRAS_UP_SCHEDULED_REAL - PRV.TRAS_UP_SCHEDULED_REAL) TRAS_UP_SCHEDULED_REAL, (CUR.TRAS_UP_ENERGY_CHARGES_REAL - PRV.TRAS_UP_ENERGY_CHARGES_REAL) TRAS_UP_ENERGY_CHARGES_REAL, (CUR.TRAS_UP_COM_CHARGES_REAL - PRV.TRAS_UP_COM_CHARGES_REAL) TRAS_UP_COM_CHARGES_REAL, (CUR.TRAS_UP_TOTAL_CHARGES - PRV.TRAS_UP_TOTAL_CHARGES) TRAS_UP_TOTAL_CHARGES, (CUR.TRAS_DOWN_SCHEDULED_AHEAD - PRV.TRAS_DOWN_SCHEDULED_AHEAD) TRAS_DOWN_SCHEDULED_AHEAD, (CUR.TRAS_DOWN_ENERGY_CHARGES_AHEAD - PRV.TRAS_DOWN_ENERGY_CHARGES_AHEAD) TRAS_DOWN_ENERGY_CHARGES_AHEAD, (CUR.TRAS_DOWN_SCHEDULED_REAL - PRV.TRAS_DOWN_SCHEDULED_REAL) TRAS_DOWN_SCHEDULED_REAL, (CUR.TRAS_DOWN_ENERGY_CHARGES_REAL - PRV.TRAS_DOWN_ENERGY_CHARGES_REAL) TRAS_DOWN_ENERGY_CHARGES_REAL, (CUR.NET_TRAS - PRV.NET_TRAS) NET_TRAS FROM BILL_ENTITY_TRAS_M PRV INNER JOIN BILL_ENTITY_TRAS_M CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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
    
     public List<Object[]> getAllSumBill(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select sum(TRAS_UP_CLEARED_AHEAD), sum(TRAS_UP_SCHEDULED_AHEAD), sum(TRAS_UP_ENERGY_CHARGES_AHEAD),sum(TRAS_UP_COM_CHARGES_AHEAD),sum(TRAS_UP_CLEARED_REAL),sum(TRAS_UP_SCHEDULED_REAL),sum(TRAS_UP_ENERGY_CHARGES_REAL),sum(TRAS_UP_COM_CHARGES_REAL),sum(TRAS_UP_TOTAL_CHARGES),sum(TRAS_DOWN_SCHEDULED_AHEAD),sum(TRAS_DOWN_ENERGY_CHARGES_AHEAD),sum(TRAS_DOWN_SCHEDULED_REAL),sum(TRAS_DOWN_ENERGY_CHARGES_REAL),sum(NET_TRAS) from BILL_ENTITY_TRAS_M WHERE WEEK_ID='" + weekId + "' and BILL_YEAR='" + yearId + "' and REVISION_NO='" + revNo + "' ";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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

    public boolean NewBillEntityTRASM(BillEntityTrasM dic) {

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

    public List<Object[]> getTempTRASMBillPayableDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearid) {
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select * from BILL_ENTITY_TRAS_M where week_id=" + weekId + " and revision_no=" + revisionNo + "  and BILL_YEAR=" + yearid + "";
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

    public List<TempBillEntityTrasM> getTempBillEntityTrasM(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempBillEntityTrasM s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo");
            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("revisionNo", revisionNo);
            List<TempBillEntityTrasM> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (TempBillEntityTrasM e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<TempBillEntityTrasM>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BillEntityTrasM> getBillEntityTrasM(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillEntityTrasM s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo");
            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("revisionNo", revisionNo);
            List<BillEntityTrasM> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillEntityTrasM e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillEntityTrasM>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public boolean deleteBillEntityTRASMbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from BillEntityTrasM where weekId=:weekId and revisionNo=:revisionNo and billYear=:year";
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
