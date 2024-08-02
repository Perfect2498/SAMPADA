/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */
package sampada.DAO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.hibernate.Hibernate;

import org.hibernate.Query;

import org.hibernate.SQLQuery;

import org.hibernate.Session;

import org.hibernate.Transaction;

import sampada.pojo.BillPayableCorp;

import sampada.pojo.TempRefundBillCorp;

import sampada.util.HibernateUtil;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class TempRefundBillCorpDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(slno),0) FROM TEMP_REFUND_BILL_CORP ";
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

    public boolean NewTempRefundBillCorp(TempRefundBillCorp dic) {

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

    public List<TempRefundBillCorp> getAllPendingPayableTempRefundBillCorp() {
        Session session = null;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Pending' and s.billReceiveCorp.uniqueId IS NOT NULL ");
            queryList = query.list();
            if (queryList == null && queryList.isEmpty()) {
                return null;
            } else {
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getBillReceiveCorp());
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

    public List<TempRefundBillCorp> getPendingTempRefundBillCorp(int corpid) {
        Session session = null;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Pending' and s.corporates.corporateId=:corpid ");
            query.setParameter("corpid", corpid);
            queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getBillReceiveCorp());
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

    public int getUpdatePendingTempRefundBillPayableCorp(int corpid, int billpayuniqueid) {
        Session session = null;
        Transaction tx = null;
        int result = 0;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("update temp_refund_bill_corp set checker_status='Verified',checker_date=SYSDATE where corpId=" + corpid + " and billpay_uniqueid='" + billpayuniqueid + "' ");
            result = query.executeUpdate();
            session.flush();
            tx.commit();

//            session.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return result;
        } finally {
            session.close();
        }
    }

    public int getUpdatePendingTempRefundBillReceivableCorp(int corpid, int billrecvuniqueid) {
        Session session = null;
        int result = 0;
        Transaction tx = null;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("update temp_refund_bill_corp set checker_status='Verified',checker_date=SYSDATE where corpId=" + corpid + " and billrecv_uniqueid='" + billrecvuniqueid + "' ");
            result = query.executeUpdate();
            tx.commit();
            session.flush();
            //session.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return result;
        } finally {
            session.close();
        }
    }

    public int getDeletePendingTempRefundBillCorp(int corpid) {
        Session session = null;
        int result = 0;
        List<TempRefundBillCorp> queryList = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("delete temp_refund_bill_corp where checker_status='Pending' and corpId=" + corpid + " ");
            result = query.executeUpdate();
            tx.commit();
            session.flush();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return result;
        } finally {
            session.close();
        }
    }

    public List<TempRefundBillCorp> getPendingReceviableTempRefundBillcorpbyCorpid(int corpid) {
        Session session = null;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Pending' and s.corporates.corporateId=:corpid and s.billPayableCorp.uniqueId IS NOT NULL  ");
            query.setParameter("corpid", corpid);
            queryList = query.list();
            if (queryList == null && queryList.isEmpty()) {
                return null;
            } else {
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getBillReceiveCorp());
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

    public int getDeleteCheckerReceivableRefundbyCorpID(int corpID) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "delete FROM TEMP_REFUND_BILL_CORP where corpid='" + corpID + "' and checker_status='Pending' ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
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

    public List<Object[]> getAllPendingReceviableTempRefundBillCorpList() {
        Session session = null;
        List<Object[]> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from TEMP_REFUND_BILL_CORP s , corporates c  where s.CHECKER_STATUS='Pending' and s.corpid = c.corporate_id and billpay_uniqueid is not null ");
            queryList = query.list();
            if (queryList == null && queryList.isEmpty()) {
                return null;
            }
            return queryList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Object[]> getAllPendingPayableTempRefundBillCorpforChecker() {
        Session session = null;
        List<Object[]> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from TEMP_REFUND_BILL_CORP s , corporates c  where s.CHECKER_STATUS='Pending' and s.corpid = c.corporate_id and BILLRECV_UNIQUEID is not null");
            queryList = query.list();
            return queryList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<TempRefundBillCorp> getRefundDetailsbyDisburseID(BigDecimal slno) {
        Session session = null;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.slno=:slno and s.billPayableCorp.uniqueId IS NOT NULL  ");
            query.setParameter("slno", slno);
            queryList = query.list();
            if (queryList == null && queryList.isEmpty()) {
                return null;
            } else {
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getBillReceiveCorp());
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

    public List<TempRefundBillCorp> getRefundDetailsbyDisburseIDnotinbank(BigDecimal slno) {
        Session session = null;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.slno=:slno and s.billPayableCorp.uniqueId IS NOT NULL  and s.slno NOT IN (select disburseId from BankStatement where disburseType='Refund' ) ");
            query.setParameter("slno", slno);
            queryList = query.list();
            if (queryList == null && queryList.isEmpty()) {
                return null;
            } else {
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getBillReceiveCorp());
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

    public List<TempRefundBillCorp> getRefundBillCorpbyCorpforExport(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.billReceiveCorp.uniqueId IS NOT NULL and (TO_DATE(s.refundDate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Refund' )   ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);
            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());

                }
                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public int getUpdatePendingTempRefundBillReceivableCorpID(int corpid) {
        Session session = null;
        int result = 0;
        List<TempRefundBillCorp> queryList = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("update temp_refund_bill_corp set checker_status='Verified',checker_date=SYSDATE where corpId=" + corpid + " and checker_status='Pending' and billrecv_uniqueid IS NOT NULL ");
            result = query.executeUpdate();
            tx.commit();
            session.flush();
            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return result;

        } finally {

            session.close();

        }

    }

    public List<TempRefundBillCorp> getAllPendingReceviableTempRefundBillCorp() {

        Session session = null;

        List<TempRefundBillCorp> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Pending' and s.billPayableCorp.uniqueId IS NOT NULL ");

            queryList = query.list();

            if (queryList == null && queryList.isEmpty()) {

                return null;

            } else {

                for (TempRefundBillCorp e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillPayableCorp());

                    Hibernate.initialize(e.getBillReceiveCorp());

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

    public int deleteTempRefundDisburse() {

        int result = 0;
        BigDecimal temp = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "delete FROM TEMP_REFUND_BILL_CORP where CHECKER_STATUS='Pending' and billpay_uniqueid IS NOT NULL ";
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

        return 0;

    }

    public int getDeleteRefundbyMakerforCorpID(int corpid, String status) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "delete from TEMP_REFUND_BILL_CORP where CORPID=" + corpid + " and checker_status='" + status + "' AND BILLRECV_UNIQUEID IS NOT NULL";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
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

    public List<TempRefundBillCorp> getAllRefundPayablePendingTempRefundBillCorp(int corpid) {

        Session session = null;

        List<TempRefundBillCorp> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Pending' and s.corporates.corporateId=:corpid and s.billReceiveCorp.uniqueId IS NOT NULL and s.slno IN(select max(e.slno) from TempRefundBillCorp e where e.checkerStatus ='Pending' and e.corporates.corporateId=:corpid group by  e.billReceiveCorp.uniqueId) ");

            query.setParameter("corpid", corpid);

            queryList = query.list();

            //System.out.print("Liset TempRefund is :"+queryList.size());
            if (queryList != null && queryList.size() > 0) {

                for (TempRefundBillCorp e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillPayableCorp());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

                return queryList;

            } else {
                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return queryList;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getTemprefundofrecvbyCorpIDBillUNIQUEID(int corpid) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

            String hql = "select BILLRECV_UNIQUEID, sum(PAID_AMOUNT) from TEMP_REFUND_BILL_CORP where CHECKER_STATUS='Pending' and CORPID='" + corpid + "' and BILLRECV_UNIQUEID IS NOT NULL group by BILLRECV_UNIQUEID";

            System.out.println(" is " + hql);

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

    public List<TempRefundBillCorp> getAllRefundPayablePendingTempRefundBillCorpByChecker(int corpid) {

        Session session = null;

        List<TempRefundBillCorp> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Pending' and s.corporates.corporateId=:corpid and s.billReceiveCorp.uniqueId IS NOT NULL ");

            query.setParameter("corpid", corpid);

            queryList = query.list();

            //System.out.print("Liset TempRefund is :"+queryList.size());
            if (queryList != null && queryList.size() > 0) {

                for (TempRefundBillCorp e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillPayableCorp());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

                return queryList;

            } else {
                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return queryList;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getTempPendingRefundRecvCorpbyChecker() {
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select distinct (CORPID) from  TEMP_REFUND_BILL_CORP  where CHECKER_STATUS='Pending' and  BILLRECV_UNIQUEID IS NOT NULL";
            System.out.println("SQL is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
            System.out.println("getTempPendingRefundRecvCorpbyChecker list  size is" + list.size());
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

    public List<TempRefundBillCorp> getTempRefundPendingbyCorpListbychecker(String status) {
        List<TempRefundBillCorp> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "from TempRefundBillCorp s where s.checkerStatus =:status and s.billReceiveCorp.uniqueId IS NOT NULL";
            System.out.println("SQL is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            list = query.list();
            for (TempRefundBillCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
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

    public List<TempRefundBillCorp> getRefundBillDisbursementCorpbyFromdateTodates(Date fromdate, Date todate) {

        Session session = null;
        List<TempRefundBillCorp> queryList = null;

        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from TempRefundBillCorp s where TO_DATE(s.refundDate) between :fromdate and :todate and s.checkerStatus='Verified' and s.slno IN (select e.disburseId from BankStatement e where e.reconFlag='0' and e.disburseType='Refund' ) and  s.billPayableCorp.uniqueId IS NOT NULL");

            Query query = session.createQuery("from TempRefundBillCorp s where  s.checkerStatus='Verified' and s.slno IN (select e.disburseId from BankStatement e where TO_DATE(e.amountDate) between :fromdate and :todate and e.reconFlag='0' and e.disburseType='Refund' and e.creditDebitFlag='DR' ) and  s.billPayableCorp.uniqueId IS NOT NULL");

            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);
            queryList = query.list();
            System.out.println("list sizeRefund disbursement reconslica" + queryList.size());
            for (TempRefundBillCorp e : queryList) {
                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBillReceiveCorp());
                Hibernate.initialize(e.getBillPayableCorp());
            }
            return queryList;
        } catch (Exception e) {
            return queryList;
        } finally {
            session.close();
        }

    }

    public List<TempRefundBillCorp> getRefundBillPayCorporExport(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.paidAmount !=0 and s.billPayableCorp.uniqueId IS NOT NULL and (TO_DATE(s.refundDate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Refund' )   ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);
            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<TempRefundBillCorp> getremarkRefundBillPayCorporExport(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.paidAmount=0 and s.billPayableCorp.uniqueId IS NOT NULL and (TO_DATE(s.refundDate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Refund' )   ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);
            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<TempRefundBillCorp> getTempRefundBillCorpbySLNO(int slno) {

        Session session = null;

        List<TempRefundBillCorp> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from TempRefundBillCorp s where s.slno =:slno ");

            query.setParameter("slno", new BigDecimal(slno));

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                for (TempRefundBillCorp e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillPayableCorp());

                    Hibernate.initialize(e.getBillReceiveCorp());

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

    public List<Timestamp> getRefundbyCorpgroupbyEntryDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(s.REFUND_DATE)) from TEMP_REFUND_BILL_CORP s, CORPORATES c  where c.CORPORATE_ID=s.CORPID and s.CORPID='" + corpid + "' and s.CHECKER_STATUS='Verified' and extract(YEAR from s.REFUND_DATE)='" + year + "' order by TRUNC(s.REFUND_DATE) ASC";

//          String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and bill_type='" + billtype + "' )  order by TRUNC(s.billing_date) ASC";
            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.print("Size of group bydate is" + list.size());

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

    public List<TempRefundBillCorp> getAllRefundPayabledataTempRefundBillCorp(int corpid) {

        Session session = null;

        List<TempRefundBillCorp> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.corporates.corporateId=:corpid  ");

            query.setParameter("corpid", corpid);

            queryList = query.list();

            if (queryList != null && queryList.size() > 0) {

                for (TempRefundBillCorp e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillPayableCorp());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

                return queryList;

            } else {
                return queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return queryList;

        } finally {

            session.close();

        }

    }

    public List<TempRefundBillCorp> getdisRefundBillPayCorpbydates(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.paidAmount!=0 and s.billPayableCorp.uniqueId IS NOT NULL and (TO_DATE(s.refundDate) between :fromdate and :todate)   ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);
            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public BigDecimal getmaxpoolbalinrefund(int fromid, int toid) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select POOL_BAL+PAID_AMOUNT from TEMP_REFUND_BILL_CORP where  POOL_BAL=(select MAX(POOL_BAL) from TEMP_REFUND_BILL_CORP where SLNO BETWEEN '" + fromid + "' AND '" + toid + "' and  PAID_AMOUNT!=0 and CHECKER_STATUS= 'Verified' and BILLPAY_UNIQUEID IS NOT NULL)";
            SQLQuery query = session.createSQLQuery(hql);
            //int length = query.list().size();
            if (query.list() != null) {
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

    public List<TempRefundBillCorp> getdisRefundBillPayCorpbyfromandtoids(BigDecimal fromref, BigDecimal toref) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.paidAmount!=0 and s.billPayableCorp.uniqueId IS NOT NULL and (s.slno between :fromref and :toref) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Refund' )  ");
            query.setParameter("fromref", fromref);
            query.setParameter("toref", toref);
            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<TempRefundBillCorp> getdisRefundBillPayCorpbydatestypebyrev(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified'  and s.paidAmount!=0 and s.billPayableCorp.uniqueId IS NOT NULL and (TO_DATE(s.refundDate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Refund' )  ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);

            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<TempRefundBillCorp> getdisRefundBillPayCorpbydatestypebyrevIN(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified'  and s.paidAmount!=0 and s.billPayableCorp.uniqueId IS NOT NULL and (TO_DATE(s.refundDate) between :fromdate and :todate) and s.slno IN (select disburseId from BankStatement where disburseType='Refund' )  ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);

            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    //=================================================================================
    
    public TempRefundBillCorp getTempRefundByUniqueIDForPayable(BigDecimal uid) {
        Session session = null;
        List<TempRefundBillCorp> queryList = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.billPayableCorp.uniqueId=:uid and s.slno=(select max(slno) from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.billPayableCorp.uniqueId=:uid)");
            query.setParameter("uid", uid);
            
            queryList = query.list();
            if (queryList == null && queryList.isEmpty()) {
                return null;
            } 
            else {
                return queryList.get(0);
            }
        }
        catch (Exception e) {
            if(session!=null)
                session.close();
            e.printStackTrace();
            return null;
        } 
        finally {
            session.close();
        }
    }
    
    public List<TempRefundBillCorp> getdisRefundBillPayCorpbydatestypebyrevINforBtype(Date fromdate, Date todate, String btype) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified'  and s.paidAmount!=0 and s.billPayableCorp.uniqueId IS NOT NULL and s.billPayableCorp.billType='"+btype+"' and ((select b.amountDate from BankStatement b where b.corporates.corporateId=s.corporates.corporateId and b.creditDebitFlag='DR' and b.paidAmount=s.paidAmount and b.disburseId=s.slno) between :fromdate and :todate) and s.slno IN (select disburseId from BankStatement where disburseType='Refund' )  ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);

            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public List<TempRefundBillCorp> getRefundBillCorpbyCorpforExportforBtype(Date fromdate, Date todate, String btype) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.billReceiveCorp.uniqueId IS NOT NULL and s.billReceiveCorp.billType='"+btype+"' and ((select b.bankStatement.amountDate from MappingRefundBank b where b.tempRefundBillCorp.slno=s.slno) between :fromdate and :todate)");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);
            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());

                }
                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public List<TempRefundBillCorp> getRefundBillPayCorporExport2(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' and s.paidAmount !=0 and s.billPayableCorp.uniqueId IS NOT NULL and (TO_DATE(s.refundDate) between :fromdate and :todate) ");
            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);
            List<TempRefundBillCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("temprefund list size" + queryList.size());
                for (TempRefundBillCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getBillReceiveCorp());
                    Hibernate.initialize(e.getBillPayableCorp());
                }

                return (List<TempRefundBillCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
}
