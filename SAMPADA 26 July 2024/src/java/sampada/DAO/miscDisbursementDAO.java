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
import sampada.pojo.Corporates;
import sampada.pojo.CsdfDetails;
import sampada.pojo.DocumentSets;
import sampada.pojo.DsnDisbursement;
import sampada.util.HibernateUtil;
import sampada.pojo.MiscDisbursement;

/**
 *
 * @author abt
 */
public class miscDisbursementDAO {

    public BigDecimal getMaxUNIQUE_ID() {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(UNIQUE_ID),0) from MISC_DISBURSEMENT";

            SQLQuery query = session.createSQLQuery(hql);

            int length = query.list().size();

            if (length != 0) {

                result = (BigDecimal) query.list().get(0);

                System.out.println("result is " + result);

            }

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

    public boolean NewMiscDisbursement(MiscDisbursement dic) {

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

    public List<DocumentSets> DocumentSetslist() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DocumentSets s where s.checkDateByChecker IS NOT NULL and s.documentSetNo NOT IN (select md.documentSet from MiscDisbursement md) and s.documentSetNo NOT IN (select pd.documentSet from PoolToInt pd) and s.documentSetNo NOT IN (select pp.documentSet from PoolToPool pp) ");
            List<DocumentSets> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

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

    public List<MiscDisbursement> getmiscDetails(String category) {

        Session session = null;

        List<MiscDisbursement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from MiscDisbursement s where s.status='Pending' and s.amtCategory=:category");
            query.setParameter("category", category);
            queryList = query.list();
            //System.out.println("list size" + queryList.size());              

            return queryList;

        } catch (Exception e) {
            e.printStackTrace();
            return queryList;

        } finally {

            session.close();

        }

    }

    public List<MiscDisbursement> getALLmiscDetails() {

        Session session = null;

        List<MiscDisbursement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from MiscDisbursement s");

            queryList = query.list();
            //System.out.println("list size" + queryList.size());              

            return queryList;

        } catch (Exception e) {
            e.printStackTrace();
            return queryList;

        } finally {

            session.close();

        }

    }

    public void deletemiscdisburseByMaker(String txuid) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("delete from MiscDisbursement s where s.uniqueId='" + txuid + "' ");
            query.executeUpdate();

            tx.commit();
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }
    


    public void checkmiscdisburseBychecker(String txuid) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String date1 = java.time.LocalDate.now().toString();
            String date2[] = date1.split("-");
            String date = date2[2] + date2[1] + date2[0];
            System.out.println("date is " + date);
//            TO_DATE('date1','YYYY-MM-DD')
            Query query = session.createQuery("update MiscDisbursement s set s.checkerDate=TO_DATE('" + date1 + "','YYYY-MM-DD'), s.status='Checked' where s.uniqueId='" + txuid + "' and s.status='Pending'");
            query.executeUpdate();

            tx.commit();
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }

    public List<MiscDisbursement> getMiscDisbursementbyUniqueID(BigDecimal disburseID) {

        List<MiscDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from MiscDisbursement s where uniqueId=:disburseID ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("disburseID", disburseID);

            list = query.list();

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

    public List<MiscDisbursement> getmiscbilllistbydates(Date fromdate, Date todate) {

        Session session = null;

        List<MiscDisbursement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from MiscDisbursement s where s.status ='Checked' and s.amtCategory='Bills' and (TO_DATE(s.entryDate) between :fromdate and :todate)   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("misc list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public List<MiscDisbursement> getmiscintlistbydates(Date fromdate, Date todate) {

        Session session = null;

        List<MiscDisbursement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from MiscDisbursement s where s.status ='Checked' and s.amtCategory='Interest' and (TO_DATE(s.entryDate) between :fromdate and :todate)   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("misc list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public List<MiscDisbursement> getmiscdetailsbydates(Date fromdate, Date todate) {

        Session session = null;

        List<MiscDisbursement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from MiscDisbursement s where s.status ='Checked' and (TO_DATE(s.entryDate) between :fromdate and :todate) and s.uniqueId NOT IN (select disburseId from BankStatement where disburseType='Misc' )   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("misc list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<MiscDisbursement> getMiscdetailsbydatesIN(Date fromdate, Date todate) {

        Session session = null;

        List<MiscDisbursement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from MiscDisbursement s where s.status ='Checked' and (TO_DATE(s.entryDate) between :fromdate and :todate) and s.uniqueId IN (select disburseId from BankStatement where disburseType='Misc' )    ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<MiscDisbursement> getMiscDisbursementbyfromandtoids(BigDecimal frombill, BigDecimal tobill) {

        Session session = null;

        List<MiscDisbursement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from MiscDisbursement s where s.status ='Checked' and s.uniqueId between :frombill and :tobill and s.uniqueId NOT IN (select disburseId from BankStatement where disburseType='Misc' )   ");

            query.setParameter("frombill", frombill);

            query.setParameter("tobill", tobill);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("MiscDisbursement list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<MiscDisbursement> getmiscDisbursementbyDisburseID(BigDecimal disburseID) {

        List<MiscDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from MiscDisbursement s where s.uniqueId=:disburseID and s.status ='Checked' ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("disburseID", disburseID);

            list = query.list();

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

    public List<MiscDisbursement> getmiscdisbursementbyDisburseIDnotinbank(BigDecimal disburseID) {

        List<MiscDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from MiscDisbursement s where s.status ='Checked' and s.uniqueId=:disburseID  and s.uniqueId NOT IN (select disburseId from BankStatement where disburseType='Misc' )";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("disburseID", disburseID);

            list = query.list();

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
    
    //===============================================================================
    
    public List<MiscDisbursement> getmiscDetailsBetweenDates(Date fromdate, Date todate) {

        Session session = null;
        List<MiscDisbursement> queryList = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from MiscDisbursement s where TO_DATE(s.makerDate) between :fromdate and :toDate and s.status='Checked' ");
            
            query.setParameter("fromdate", fromdate);
            query.setParameter("toDate", todate);
            
            queryList = query.list();

            return queryList;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return queryList;
        } 
        finally {
            session.close();
        }
    }

}
