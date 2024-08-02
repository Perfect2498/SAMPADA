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
import sampada.pojo.BankStatement;
import sampada.pojo.CsdfDetails;
import sampada.pojo.DsnFileDetails;
import sampada.pojo.MiscDisbursement;
import sampada.util.HibernateUtil;

/**
 *
 * @author abt
 */
public class DsnFileDetailsDAO {

    public BigDecimal getMaxSlno() {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(SLNO),0) from DSN_FILE_DETAILS";

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

    public boolean NewDsnFileDetails(DsnFileDetails dic) {

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

    public List<DsnFileDetails> DsnFileDetailsbystmtid(BigDecimal stmtid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DsnFileDetails s where s.bankStatement.stmtId=:stmtid order by s.slno");
            query.setParameter("stmtid", stmtid);

            List<DsnFileDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (DsnFileDetails e : queryList) {
                    Hibernate.initialize(e.getBankStatement());
                    Hibernate.initialize(e.getBankStatement().getCorporates());
                }
                return (List<DsnFileDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public int getUpdatedsndetailsbyChecker(String txuid) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update DSN_FILE_DETAILS set CHECKER_STATUS='Verified' where CHECKER_STATUS='Pending' and SLNO='" + txuid + "' ";

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

    public void deletedsndetailsByslno(String txuid) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("delete from DsnFileDetails s where s.slno='" + txuid + "' ");
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

    public List<DsnFileDetails> DsnFileDetailsbyslno(BigDecimal stmtid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DsnFileDetails s where s.slno=:stmtid order by s.slno");
            query.setParameter("stmtid", stmtid);

            List<DsnFileDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (DsnFileDetails e : queryList) {
                    Hibernate.initialize(e.getBankStatement());
                    Hibernate.initialize(e.getBankStatement().getCorporates());
                }
                return (List<DsnFileDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<DsnFileDetails> DsnFileDetailsbystmtidbyfilename(BigDecimal stmtid, String filename) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DsnFileDetails s where s.bankStatement.stmtId=:stmtid and s.fileName=:filename order by s.slno");
            query.setParameter("stmtid", stmtid);
            query.setParameter("filename", filename);
            List<DsnFileDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
//                System.out.println("list size" + queryList.size());
                for (DsnFileDetails e : queryList) {
                    Hibernate.initialize(e.getBankStatement());
                    Hibernate.initialize(e.getBankStatement().getCorporates());
                }
                return (List<DsnFileDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<DsnFileDetails> getALLdsnfileDetails() {

        Session session = null;

        List<DsnFileDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DsnFileDetails s");

            queryList = query.list();
            //System.out.println("list size" + queryList.size());              
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
//                System.out.println("list size" + queryList.size());
                for (DsnFileDetails e : queryList) {
                    Hibernate.initialize(e.getBankStatement());
                    Hibernate.initialize(e.getBankStatement().getCorporates());
                }
                return (List<DsnFileDetails>) queryList;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return queryList;

        } finally {

            session.close();

        }

    }

    public List<DsnFileDetails> DsnFileDetailsbyfilename(String filename) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DsnFileDetails s where s.fileName=:filename order by s.slno");
            query.setParameter("filename", filename);
            List<DsnFileDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
//                System.out.println("list size" + queryList.size());
                for (DsnFileDetails e : queryList) {
                    Hibernate.initialize(e.getBankStatement());
                    Hibernate.initialize(e.getBankStatement().getCorporates());
                }
                return (List<DsnFileDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
}
