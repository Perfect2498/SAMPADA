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
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.Corporates;
import sampada.pojo.Entites;
import sampada.util.HibernateUtil;

/**
 *
 * @author superusr
 */
public class NewEntityRegistrationDAO {

    public boolean NewEntity(Entites usr) {

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            session.save(usr);
            // session.flush();
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

    public BigDecimal getMaxnewEntityID() {

        BigDecimal maxid = BigDecimal.ONE;
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            String hql = "select max(s.entityId) from Entites s";
            Query q = session.createQuery(hql);
            int length = q.list().size();
            if (length > 0) {
                maxid = (BigDecimal) q.list().get(0);
            }
            session.close();
            return maxid;

        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }

        return maxid;

    }

    public List<Entites> Entitieslist() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Entites s");
            List<Entites> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (Entites e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<Entites> getEntitiesbyEntityId(BigDecimal entityId) {

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Entites s where s.entityId =:entityId");
            query.setParameter("entityId", entityId);
            List queryList = query.list();
            System.out.println("queryList size is " + queryList.size());
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                for (Entites e : (List<Entites>) queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<Entites>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public boolean UpdateEntites(Entites corporate) {

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(corporate);
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

}
