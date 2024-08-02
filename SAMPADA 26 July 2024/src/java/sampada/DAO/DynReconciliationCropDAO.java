/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.DynReconciliationCorp;
import sampada.util.HibernateUtil;

/**
 *
 * @author Administrator
 */
public class DynReconciliationCropDAO {

    public boolean NewReconciliationCorp(DynReconciliationCorp usr) {

        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(usr);

            session.flush();

            tx.commit();

            session.close();
            System.out.println("In DynReconciliationCropDAO  dao");
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

    public int getMaxslno() {

        int result = 0;
        BigDecimal bg = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {
            
            
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(max(SLNO),0) from DYN_RECONCILIATION_CORP";
            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);
            result = bg.intValueExact();
            System.out.println("result is " + result);

            tx.commit();
            session.flush();
            session.close();
            return result;
            
            
            
//            session = HibernateUtil.getInstance().getSession();
//            tx = session.beginTransaction();
//            String hql = "select max(SLNO) FROM DYN_RECONCILIATION_CORP ";
//            SQLQuery query = session.createSQLQuery(hql);
//             System.out.println("query.list().size() ="+query.list().size());
//            if ((query.list() != null) && !(query.list().isEmpty())) {
//                //          result =(Integer)query.list().get(0);
//                
//                temp = (BigDecimal) query.list().get(0);
//                System.out.println("temp ="+query.list().get(0));
//                result = temp.intValue();
//            }
//            
//            tx.commit();
//            session.flush();
//            session.close();
//
//            return result;

        } catch (Exception e) {

            if (session != null) {
                session.close();
            }

            e.printStackTrace();

        }

        return result;

    }

    public BigDecimal getLatestOutstandingbyCorpId(int Corporate) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select OUTSTANDING_AMOUNT\n"
                    + "from (select SLNO, OUTSTANDING_AMOUNT\n"
                    + "      from DYN_RECONCILIATION_CORP\n"
                    + "      where CORP_ID = '" + Corporate + "'\n"
                    + "      order by SLNO desc)\n"
                    + "where rownum = 1 ";

            System.out.println("####### getLatestOutstandingbyCorpId hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            System.out.println("query.list() is " + query.list());

            if (query.list() != null && !(query.list().isEmpty())) {
                //System.out.println("result is " + query.list().get(0));
                result = (BigDecimal) query.list().get(0);
                System.out.println("result is " + result);
            } else {
                result = BigDecimal.ZERO;
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

    public List<DynReconciliationCorp> getReconreportByCorpAnddatesYear(Date amtFrmDate, Date amtToDate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            Query query = session.createQuery("from DynReconciliationCorp s where TO_DATE(s.billEntryDate) between :amtFrmDate and :amtToDate order by s.slno");

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<DynReconciliationCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (DynReconciliationCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<DynReconciliationCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<DynReconciliationCorp> getReconreportByCorpAnddatesYearbycorpid(Date amtFrmDate, Date amtToDate, int corp) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            Query query = session.createQuery("from DynReconciliationCorp s where s.corporates.corporateId=" + corp + " and  TO_DATE(s.billEntryDate) between :amtFrmDate and :amtToDate order by s.slno");

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<DynReconciliationCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (DynReconciliationCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<DynReconciliationCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public int getcountslno() {

        int result = 0;
        BigDecimal temp = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select count(SLNO) FROM DYN_RECONCILIATION_CORP ";
            SQLQuery query = session.createSQLQuery(hql);
            if ((query.list() != null) && (query.list().size() > 0) && (query.list().get(0) != null)) {
                //          result =(Integer)query.list().get(0);
                temp = (BigDecimal) query.list().get(0);
                result = temp.intValue();
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

        return 0;

    }

    public List<DynReconciliationCorp> getFullReconreport() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            Query query = session.createQuery("from DynReconciliationCorp s order by s.slno ");

//         
//            query.setParameter("amtFrmDate", amtFrmDate);
//            query.setParameter("amtToDate", amtToDate);
            List<DynReconciliationCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (DynReconciliationCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<DynReconciliationCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

}
