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
import sampada.pojo.AdjMapping;
import sampada.pojo.AdjPayment;
import sampada.util.HibernateUtil;

/**
 *
 * @author Administrator
 */
public class AdjMapingDAO {

    public boolean NewAdjMaping(AdjMapping dic) {

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

    public BigDecimal getMaxSlno() {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(SL_NO),0) from ADJ_MAPPING";

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

    public List<AdjMapping> getPendingAdjMappingListbyCorp(int corpid) {

        List<AdjMapping> list = null;

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            String hql = "from AdjMapping s where s.corporates.corporateId=:corpid and s.status='Pending' order by s.slNo ASC";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("corpid", corpid);

            list = query.list();

            for (AdjMapping e : list) {

                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBillPayableCorpByPayId());
                Hibernate.initialize(e.getBillPayableCorpByPayRefId());
                Hibernate.initialize(e.getBillReceiveCorpByRecRefId());
                Hibernate.initialize(e.getBillReceiveCorpByRecvId());
                Hibernate.initialize(e.getAdjPayment());
                Hibernate.initialize(e.getInterestDetails());
                Hibernate.initialize(e.getDisbursedInterestDetails());

            }

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
    
    public List<AdjMapping> getPendingAdjMappingListbyUniqueid(BigDecimal uniqueid) {

        List<AdjMapping> list = null;

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            String hql = "from AdjMapping s where s.adjPayment.uniqueId=:uniqueid  order by s.slNo ASC";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("uniqueid", uniqueid);

            list = query.list();

            for (AdjMapping e : list) {

                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBillPayableCorpByPayId());
                Hibernate.initialize(e.getBillPayableCorpByPayRefId());
                Hibernate.initialize(e.getBillReceiveCorpByRecRefId());
                Hibernate.initialize(e.getBillReceiveCorpByRecvId());
                Hibernate.initialize(e.getAdjPayment());
                Hibernate.initialize(e.getInterestDetails());
                Hibernate.initialize(e.getDisbursedInterestDetails());

            }

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

    
    
    public int deleteAdjMappingbycorpidbyChecker(int corpid) {
        int result = 0;
        Session session = null;
        Transaction tx;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "delete from ADJ_MAPPING where STATUS='Pending' and CORP_ID='"+corpid+"' ";
            System.out.println("deleteAdjMappingbycorpidbyChecker is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();
            System.out.println("result is " + result);
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
    
    
    public int getUpdateAdjMappingbyCheckerbySL_NO(int uniqueid) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update ADJ_MAPPING set STATUS='Confirmed'  where SL_NO=" + uniqueid + "";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();
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
}
