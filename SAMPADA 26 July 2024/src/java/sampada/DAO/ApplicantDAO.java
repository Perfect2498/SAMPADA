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
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityFras;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillPayableEntityRras;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.BillReceiveEntityDsm;
import sampada.pojo.BillReceiveEntityRras;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.InterestDetails;
import sampada.pojo.PaymentDisbursement;
import sampada.util.HibernateUtil;

/**
 *
 * @author shubham
 */
public class ApplicantDAO {

    public List<BillPayableCorp> getPendingBillPaybyCorpbyCorpId(int corpid) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.billType=billType and s.corporates.corporateId =:corpid) and (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("corpid", corpid);
            list = query.list();
            for (BillPayableCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
            tx.commit();
            session.flush();
            session.close();
            return list;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
             System.out.println("erroe in getPendingBillPaybyCorpbyCorpId() in ApplicantDAO");
           // e.printStackTrace();
        }
        return list;
    }

    public List<BillPayableEntityDsm> BillPayableCorpDsmlistforApplicant(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableEntityDsm s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo and s.corporates.corporateId =:corpid");

            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("corpid", corpid);
            query.setParameter("revisionNo", revisionNo);
            List<BillPayableEntityDsm> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableEntityDsm e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillPayableEntityDsm>) queryList;
            }

        } catch (Exception e) {

           System.out.println("erroe in BillPayableCorpDsmlistforApplicant() in ApplicantDAO");

            return null;

        } finally {

            session.close();

        }

    }

    public List<BillReceiveEntityDsm> BillReceiveEntityDsmListforApplicant(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveEntityDsm s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo and s.corporates.corporateId =:corpid");

            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("corpid", corpid);
            query.setParameter("revisionNo", revisionNo);
            List<BillReceiveEntityDsm> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillReceiveEntityDsm e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillReceiveEntityDsm>) queryList;
            }

        } catch (Exception e) {

            System.out.println("erroe in BillReceiveEntityDsmListforApplicant() in ApplicantDAO");

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getDiffBillPayforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.CAPPING_CHARGES - PRV.CAPPING_CHARGES) CAPPING_CHARGES ,(CUR.ADDITIONAL_CHARGES - PRV.ADDITIONAL_CHARGES)ADDITIONAL_CHARGES, (CUR.SIGN_CHARGES - PRV.SIGN_CHARGES) SIGN_CHARGES ,(CUR.PAYABLE_CHARGES - PRV.PAYABLE_CHARGES) PAYABLE_CHARGES ,  (CUR.NET_DSM - PRV.NET_DSM)NET_DSM FROM BILL_PAYABLE_ENTITY_DSM PRV INNER JOIN BILL_PAYABLE_ENTITY_DSM CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID AND CUR.CORPORATE_ID='" + corpid + "'  ";
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
            System.out.println("erroe in getDiffBillPayforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<Object[]> getDiffBillReceiveforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.CAPPING_CHARGES - PRV.CAPPING_CHARGES) CAPPING_CHARGES ,(CUR.ADDITIONAL_CHARGES - PRV.ADDITIONAL_CHARGES)ADDITIONAL_CHARGES, (CUR.SIGN_CHARGES - PRV.SIGN_CHARGES) SIGN_CHARGES ,(CUR.RECV_CHARGES - PRV.RECV_CHARGES) RECV_CHARGES ,  (CUR.NET_DSM - PRV.NET_DSM)NET_DSM FROM BILL_RECEIVE_ENTITY_DSM PRV INNER JOIN BILL_RECEIVE_ENTITY_DSM CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID AND CUR.CORPORATE_ID='" + corpid + "' ";
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
         //   e.printStackTrace();
          System.out.println("erroe in getDiffBillReceiveforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<BillPayableEntityRras> BillPayableCorpRraslistforApplicant(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableEntityRras s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo and s.corporates.corporateId =:corpid ");

            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("corpid", corpid);
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

          //  e.printStackTrace();
 System.out.println("erroe in BillPayableCorpRraslistforApplicant() in ApplicantDAO");
            return null;

        } finally {

            session.close();

        }

    }

    public List<BillReceiveEntityRras> BillReceiveEntityRrasListforApplicant(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveEntityRras s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo and s.corporates.corporateId =:corpid order by s.entites.entityId asc");

            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("corpid", corpid);
            query.setParameter("revisionNo", revisionNo);
            List<BillReceiveEntityRras> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillReceiveEntityRras e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillReceiveEntityRras>) queryList;
            }

        } catch (Exception e) {

           // e.printStackTrace();
 System.out.println("erroe in BillReceiveEntityRrasListforApplicant() in ApplicantDAO");

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getAllSumBillPayableforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select sum(ENERGY_VAE), sum(VARIABLE_CHARGES), sum(NET_RRAS)  from BILL_PAYABLE_ENTITY_RRAS WHERE WEEK_ID='" + weekId + "' and BILL_YEAR='" + yearId + "' and REVISION_NO='" + revNo + "' and CORPORATE_ID='" + corpid + "' ";
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
            System.out.println("erroe in getAllSumBillPayableforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<Object[]> getAllSumBillReceiveforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select sum(ENERGY_VAE), sum(FIXED_CHARGES), sum(VARIABLE_CHARGES), sum(MARKUP_CHARGES), sum(NET_RRAS)  from BILL_RECEIVE_ENTITY_RRAS WHERE WEEK_ID='" + weekId + "' and BILL_YEAR='" + yearId + "' and REVISION_NO='" + revNo + "' and CORPORATE_ID='" + corpid + "' ";
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
            System.out.println("erroe in getAllSumBillReceiveforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<Object[]> getDiffBillPayRRASforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.ENERGY_VAE - PRV.ENERGY_VAE) ENERGY_VAE ,(CUR.VARIABLE_CHARGES - PRV.VARIABLE_CHARGES)VARIABLE_CHARGES,  (CUR.NET_RRAS - PRV.NET_RRAS)NET_RRAS FROM BILL_PAYABLE_ENTITY_RRAS PRV INNER JOIN BILL_PAYABLE_ENTITY_RRAS CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID AND CUR.CORPORATE_ID='" + corpid + "' ";
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
             System.out.println("erroe in getDiffBillPayRRASforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<Object[]> getDiffBillReceiveRRASforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.ENERGY_VAE - PRV.ENERGY_VAE) ENERGY_VAE ,(CUR.FIXED_CHARGES - PRV.FIXED_CHARGES)FIXED_CHARGES, (CUR.VARIABLE_CHARGES - PRV.VARIABLE_CHARGES)VARIABLE_CHARGES, (CUR.MARKUP_CHARGES-PRV.MARKUP_CHARGES)MARKUP_CHARGES, (CUR.NET_RRAS - PRV.NET_RRAS)NET_RRAS FROM BILL_RECEIVE_ENTITY_RRAS PRV INNER JOIN BILL_RECEIVE_ENTITY_RRAS CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID AND CUR.CORPORATE_ID='" + corpid + "' ";
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
           System.out.println("erroe in getDiffBillReceiveRRASforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<BillEntityAgc> BillCorpAgclistforApplicant(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillEntityAgc s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo and s.corporates.corporateId =:corpid");
            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("corpid", corpid);
            query.setParameter("revisionNo", revisionNo);
            List<BillEntityAgc> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillEntityAgc e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillEntityAgc>) queryList;
            }

        } catch (Exception e) {

             System.out.println("erroe in BillCorpAgclistforApplicant() in ApplicantDAO");

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getAllSumBillPayableAGCforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select sum(TOTAL_AGC_UPDOWN_MWH), sum(MARKUP_CHARGES), sum(TOTALNET_AGC), sum(AGC_ENERGYCHARGES), sum(TOTALCHARGES)  from BILL_ENTITY_AGC WHERE WEEK_ID='" + weekId + "' and BILL_YEAR='" + yearId + "' and REVISION_NO='" + revNo + "' and CORPORATE_ID='" + corpid + "' ";
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
             System.out.println("erroe in getAllSumBillPayableAGCforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<Object[]> getDiffBillPayAgcforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.TOTAL_AGC_UPDOWN_MWH - PRV.TOTAL_AGC_UPDOWN_MWH) TOTAL_AGC_UPDOWN_MWH ,(CUR.MARKUP_CHARGES - PRV.MARKUP_CHARGES) MARKUP_CHARGES, (CUR.TOTALNET_AGC - PRV.TOTALNET_AGC) TOTALNET_AGC ,(CUR.AGC_ENERGYCHARGES - PRV.AGC_ENERGYCHARGES) AGC_ENERGYCHARGES ,  (CUR.TOTALCHARGES - PRV.TOTALCHARGES) TOTALCHARGES FROM BILL_ENTITY_AGC PRV INNER JOIN BILL_ENTITY_AGC CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID and CUR.CORPORATE_ID='" + corpid + "'";
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
            System.out.println("erroe in getDiffBillPayAgcforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<BillEntityFras> BillCorpFraslistforApplicant(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillEntityFras s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo and s.corporates.corporateId =:corpid ");

            query.setParameter("weekId", weekId);
            query.setParameter("year", year);
            query.setParameter("corpid", corpid);
            query.setParameter("revisionNo", revisionNo);
            List<BillEntityFras> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillEntityFras e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillEntityFras>) queryList;
            }

        } catch (Exception e) {

            System.out.println("erroe in BillCorpFraslistforApplicant() in ApplicantDAO");

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getAllSumBillFRASforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select sum(UP_REGULATION), sum(DOWN_REGULATION), sum(MARKUP_CHARGES) from BILL_ENTITY_FRAS WHERE WEEK_ID='" + weekId + "' and BILL_YEAR='" + yearId + "' and REVISION_NO='" + revNo + "' and CORPORATE_ID='" + corpid + "' ";
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
            System.out.println("erroe in getAllSumBillFRASforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<Object[]> getDiffBillPayFRASforApplicant(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo, int corpid) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT  CUR.ENTITY_ID, (CUR.UP_REGULATION - PRV.UP_REGULATION) UP_REGULATION ,(CUR.DOWN_REGULATION - PRV.DOWN_REGULATION) DOWN_REGULATION, (CUR.MARKUP_CHARGES - PRV.MARKUP_CHARGES) MARKUP_CHARGES FROM BILL_ENTITY_FRAS PRV INNER JOIN BILL_ENTITY_FRAS CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='" + weekId + "' AND CUR.BILL_YEAR='" + yearId + "' AND CUR.REVISION_NO='" + revNo + "' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID AND CUR.CORPORATE_ID='" + corpid + "' ";
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
           System.out.println("erroe in getDiffBillPayFRASforApplicant() in ApplicantDAO");
        }
        return list;
    }

    public List<Object[]> getPendingDisbursementbyCorpforApplicant(int corpid) {

        List<Object[]> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "select TRUNC(s.billing_date),s.WEEK_ID,s.BILL_TYPE,s.CORPORATE_ID,s.TOALNET,s.DISBURSE_AMOUNT,s.PENDING_AMOUNT,revision_no from BILL_RECEIVE_CORP s where CORPORATE_ID='" + corpid + "' and (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no=(select revision_no from MAX_RECEIVE_REVISION_NO where s.CORPORATE_ID=CORPORATE_ID and s.WEEK_ID=week_id and BILL_TYPE=s.BILL_TYPE group by revision_no,bill_type,week_id)   order by TRUNC(billing_date) ASC ";

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.println("Pending Dibusrsemnt list size is is " + list.size());

            session.flush();

            session.close();

            return list;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            System.out.println("erroe in getPendingDisbursementbyCorpforApplicant() in ApplicantDAO");

        }

        return list;

    }

    public List<PaymentDisbursement> getDisbursementDetailsbyFromDateTodateforApplicant(Date fromdate, Date toDate, int corpid) {
        List<PaymentDisbursement> list = null;
        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from PaymentDisbursement s where s.corporates.corporateId =:corpid and to_date(s.disbursalDate) between :fromdate and :toDate and s.checkerStatus='Confirm' and s.disburseId NOT IN (select e.paymentDisbursement.disburseId from BankStatement e) ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("corpid", corpid);
            query.setParameter("fromdate", fromdate);
            query.setParameter("toDate", toDate);
            list = query.list();
            for (PaymentDisbursement e : list) {
                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBillReceiveCorp());
            }
            session.flush();
            session.close();
            return list;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            System.out.println("erroe in getDisbursementDetailsbyFromDateTodateforApplicant() in ApplicantDAO");
        }
        return list;
    }
    
    
    public List<BillPayableCorp> BillPayableCorplistMonthlyApplicant(BigDecimal week_id1, BigDecimal week_id2,String billType,BigDecimal yeari, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billType=:billType and s.corporates.corporateId =:corpid order by billDueDate");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
              query.setParameter("corpid", corpid);
            query.setParameter("billType", billType);
            query.setParameter("yeari", yeari);
            List<BillPayableCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BillPayableCorp>) queryList;
            }

        } catch (Exception e) {

                   System.out.println("erroe in BillPayableCorplistMonthlyApplicant() in ApplicantDAO");


            return null;

        } finally {

            session.close();

        }

    }
    
    
      public List<BillReceiveCorp> BillReceiveCorplistMonthlyApplicant(BigDecimal week_id1, BigDecimal week_id2,String billType,BigDecimal yeari, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billType=:billType and s.corporates.corporateId =:corpid order by billingDate");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
             query.setParameter("corpid", corpid);
            query.setParameter("billType", billType);
            query.setParameter("yeari", yeari);
            List<BillReceiveCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillReceiveCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BillReceiveCorp>) queryList;
            }

        } catch (Exception e) {

           System.out.println("erroe in BillReceiveCorplistMonthlyApplicant() in ApplicantDAO");


            return null;

        } finally {

            session.close();

        }
}
      
        public List<BillPayableCorp> getRefundBillPayableCorplistforApplicant(BigDecimal week_id1 , BigDecimal week_id2, BigDecimal yeari, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where  s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billStatus='REFUND' and s.corporates.corporateId =:corpid order by s.weekId ASC ");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
             query.setParameter("corpid", corpid);
            query.setParameter("yeari", yeari);
            List<BillPayableCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BillPayableCorp>) queryList;
            }

        } catch (Exception e) {

           System.out.println("erroe in getRefundBillPayableCorplistforApplicant() in ApplicantDAO");


            return null;

        } finally {

            session.close();

        }

    }
    
         public List<BillReceiveCorp> getRefundBillReceivableCorplistforApplicant(BigDecimal week_id1 , BigDecimal week_id2, BigDecimal yeari, int corpid) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillReceiveCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.disburseStatus='REFUND' and s.corporates.corporateId =:corpid order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            
            Query query = session.createQuery(hql);
           query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
              query.setParameter("corpid", corpid);
            query.setParameter("yeari", yeari);
            
            list = query.list();
            
          
            
             if (list != null && list.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + list.size());
                for (BillReceiveCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
            }
                return (List<BillReceiveCorp>) list;
            
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            System.out.println("erroe in getRefundBillReceivableCorplistforApplicant() in ApplicantDAO");

        }
        return list;
    }
        
         public List<InterestDetails> getInterestPayableDetailsforApplicant(BigDecimal week_id1 , BigDecimal week_id2, String yeari, int corpid) {

        Session session = null;
        String Verified= "Verified";
        String crFlag= "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from InterestDetails s where   s.weekId between :week_id1 and :week_id2 and  to_char(s.billingDate,'YYYY')=:yeari and s.corporates.corporateId =:corpid ");          
            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            query.setParameter("corpid", corpid);
            query.setParameter("yeari", yeari);
            
            List<InterestDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (InterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<InterestDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
         
           public List<DisbursedInterestDetails> getInterestReceivableDetailsforApplicant(BigDecimal week_id1 , BigDecimal week_id2, String yeari, int corpid) {

        Session session = null;
        String Verified= "Verified";
        String crFlag= "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DisbursedInterestDetails s where   s.weekId between :week_id1 and :week_id2 and  to_char(s.billingDate,'YYYY')=:yeari and s.corporates.corporateId =:corpid  ");          
          query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
               query.setParameter("corpid", corpid);
            query.setParameter("yeari", yeari);
            
            List<DisbursedInterestDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (DisbursedInterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<DisbursedInterestDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    } 

}
