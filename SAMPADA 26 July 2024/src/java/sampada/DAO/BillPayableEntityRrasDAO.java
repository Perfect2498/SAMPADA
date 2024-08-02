/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.List;
import sampada.pojo.BillPayableEntityRras;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BillPayableEntityRrasDAO {

    public List<BillPayableEntityRras> BillPayableCorpRraslist(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableEntityRras s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo");

            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("revisionNo", revisionNo);
            List<BillPayableEntityRras> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableEntityRras e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillPayableEntityRras>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    //select sum(ENERGY_VAE), sum(VARIABLE_CHARGES), sum(NET_RRAS)  from SAMPADA_WRLDC.BILL_PAYABLE_ENTITY_RRAS WHERE WEEK_ID=5 and BILL_YEAR=2019 and REVISION_NO=0;
    public List<Object[]> getAllSumBillPayable(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select sum(ENERGY_VAE), sum(VARIABLE_CHARGES), sum(NET_RRAS)  from BILL_PAYABLE_ENTITY_RRAS WHERE WEEK_ID='" + weekId + "' and BILL_YEAR='" + yearId + "' and REVISION_NO='" + revNo + "' ";
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

    public List<Object[]> getDiffBillPay(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.ENERGY_VAE - PRV.ENERGY_VAE) ENERGY_VAE ,(CUR.VARIABLE_CHARGES - PRV.VARIABLE_CHARGES)VARIABLE_CHARGES,  (CUR.NET_RRAS - PRV.NET_RRAS)NET_RRAS FROM BILL_PAYABLE_ENTITY_RRAS PRV INNER JOIN BILL_PAYABLE_ENTITY_RRAS CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID";
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

    public List<Object[]> getTempRRASBillPayableDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year) {
        List<Object[]> list = null;

        Session session = null;
        try {
            
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql = "select * from temp_bill_payable_entity_rras where week_id=" + weekId + " and revision_no=" + revisionNo + " ";

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

    public boolean NewBillPayRrasEntries(BillPayableEntityRras dic) {

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
    
    public boolean deleteBillPayableEntityRRASbyWeekIdYearRevNo(BigDecimal weekId, BigDecimal revisionNo,BigDecimal yearId) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
         
            String hql = "delete from BillPayableEntityRras where weekId=:weekId and revisionNo=:revisionNo and billYear=:yearId";
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

}
