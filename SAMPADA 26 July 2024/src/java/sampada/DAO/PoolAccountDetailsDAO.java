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
import sampada.pojo.AgcPoolAccountDetails;
import sampada.pojo.PoolAccountDetails;
import sampada.pojo.RrasPoolAccountDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author superusr
 */
public class PoolAccountDetailsDAO {

    public List<PoolAccountDetails> PoolAccountDetails() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from PoolAccountDetails s");
            List<PoolAccountDetails> queryList = query.list();
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

    public List<PoolAccountDetails> getPoolAccountDetails() {
        List<PoolAccountDetails> list = null;
        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from PoolAccountDetails ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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

    public List<AgcPoolAccountDetails> getAgcPoolAccountDetails() {
        List<AgcPoolAccountDetails> list = null;
        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from AgcPoolAccountDetails ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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
    
    public int getUpdatePoolAccountbyCheckeragc(BigDecimal mainbal) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update AGC_POOL_ACCOUNT_DETAILS set MAIN_BALANCE='" + mainbal + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();

            tx.commit();
            session.flush();
            session.close();

        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return 0;
    }

    
    public int getUpdatePoolAccountbyCheckerrras(BigDecimal mainbal) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update RRAS_POOL_ACCOUNT_DETAILS set MAIN_BALANCE='" + mainbal + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();

            tx.commit();
            session.flush();
            session.close();

        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return 0;
    }
    public List<RrasPoolAccountDetails> getRrasPoolAccountDetails() {
        List<RrasPoolAccountDetails> list = null;
        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from RrasPoolAccountDetails ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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

    public int getUpdateRrasPoolAccountbyChecker(BigDecimal mainbal) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update RRAS_POOL_ACCOUNT_DETAILS set MAIN_BALANCE='" + mainbal + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();

            tx.commit();
            session.flush();
            session.close();

        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return 0;
    }

    public int getUpdatePoolAccountbyChecker(BigDecimal mainbal) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update POOL_ACCOUNT_DETAILS set MAIN_BALANCE='" + mainbal + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();

            tx.commit();
            session.flush();
            session.close();

        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return 0;
    }

    public int getUpdateAgcPoolAccountbyChecker(BigDecimal mainbal) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update AGC_POOL_ACCOUNT_DETAILS set MAIN_BALANCE='" + mainbal + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();

            tx.commit();
            session.flush();
            session.close();

        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return 0;
    }
}
