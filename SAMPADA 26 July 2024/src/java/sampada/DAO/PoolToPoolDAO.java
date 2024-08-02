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
import sampada.pojo.PoolToInt;
import sampada.pojo.PoolToPool;
import sampada.util.HibernateUtil;

/**
 *
 * @author root
 */
public class PoolToPoolDAO {
    
    public BigDecimal getMaxUNIQUE_ID() {

        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select NVL(max(UNIQUE_ID),0) from POOL_TO_POOL";

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
        }
        catch (Exception e) {

            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }

        return result;
    }
    
    
    public boolean NewPoolToPool(PoolToPool dic) {

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
    
    public List<PoolToPool> getPendingPoolToInt() {

        Session session = null;

        List<PoolToPool> queryList = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from PoolToPool s where s.status='Pending'");
            queryList = query.list();
            //System.out.println("list size" + queryList.size());              
            
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
    
    public List<PoolToPool> getByUid(String uid) {

        Session session = null;

        List<PoolToPool> queryList = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from PoolToPool s where s.uniqueId='"+uid+"'");
            queryList = query.list();
            //System.out.println("list size" + queryList.size());              
            
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
    
    
    public List<PoolToPool> getPoolToIntbydates(Date fromdate, Date todate) {

        Session session = null;

        List<PoolToPool> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PoolToPool s where s.status ='Checked' and (TO_DATE(s.entryDate) between :fromdate and :todate)  ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("PoolToPool list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public List<PoolToPool> PoolToIntlistbyids(BigDecimal fromid, BigDecimal toid) {

        Session session = null;
        
        
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from PoolToPool s where s.uniqueId between :fromid and :toid  and s.status='Checked' order by s.uniqueId");

                 
            query.setParameter("fromid", fromid);
            query.setParameter("toid", toid);

            List<PoolToPool> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("PoolToPool list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public void deleteTransfer(String txuid) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("delete from PoolToPool s where s.uniqueId='" + txuid + "' ");
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
            Query query = session.createQuery("update PoolToPool s set s.checkerDate=TO_DATE('" + date1 + "','YYYY-MM-DD'), s.status='Checked' where s.uniqueId='" + txuid + "' and s.status='Pending'");
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
    
    public List<PoolToPool> getALLPoolToIntDetails() {

        Session session = null;

        List<PoolToPool> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from PoolToPool s");

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
    
    public List<PoolToPool> getByCheckerDate(Date amtFrmDate, Date amtToDate) {

        Session session = null;

        List<PoolToPool> queryList = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from PoolToPool s where s.checkerDate between :amtFrmDate and :amtToDate and s.status='Checked'");
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            
            queryList = query.list();
            //System.out.println("list size" + queryList.size());              
            
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
