/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillReceiveCorp;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BillReceiveCorpDAO {

    public List<Object[]> getTempBillReceiveCorpDetailsbyWeekId(BigDecimal weekId, BigDecimal yearId, BigDecimal revisionNo, String billType) {
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select * from temp_bill_receive_corp where week_id=" + weekId + " and BILL_YEAR=" + yearId + " and revision_no=" + revisionNo + "  and bill_type='" + billType + "' ";
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

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM BILL_RECEIVE_CORP ";
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

    public boolean NewBillReceiveCorpEntries(BillReceiveCorp dic) {
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

    public List<Object[]> getPendingDisbursementbyCorpObject(String billtype) {

        List<Object[]> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,bill_due_date from BILL_RECEIVE_CORP s where s.revision_No=(select revision_No from MAX_RECEIVE_REVISION_NO where s.week_Id=week_Id and s.corporate_Id=corporate_Id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "') and (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') AND s.bill_type='" + billtype + "' order by s.billing_date ASC ";
            String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,BILL_DUE_DATE from BILL_RECEIVE_CORP s where  (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') AND s.bill_type='" + billtype + "' order by s.billing_date ASC ";

            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.println("Pending Dibusrsemnt list size is is " + list.size());

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

    public List<BillReceiveCorp> getPaidDisbursementbyCorp() {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillReceiveCorp s where (s.disburseStatus='PAID' OR s.disburseStatus='PARTIALLY') order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            list = query.list();
            for (BillReceiveCorp e : list) {
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
            e.printStackTrace();
        }
        return list;
    }

    public List<BillReceiveCorp> getDisbursementDetailsbyweekly(BigDecimal weekNo, String billtype) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        String hql = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            if (billtype.equalsIgnoreCase("ALL")) {
                hql = "from BillReceiveCorp s where s.weekId=:weekNo  order by s.corporates.corporateName ASC ";
                Query query = session.createQuery(hql);
                query.setParameter("weekNo", weekNo);
                list = query.list();
            } else {
                hql = "from BillReceiveCorp s where s.weekId=:weekNo and s.billType=:billtype  order by s.corporates.corporateName ASC ";
                Query query = session.createQuery(hql);
                query.setParameter("weekNo", weekNo);
                query.setParameter("billtype", billtype);
                list = query.list();
            }
            System.out.println("hql is " + hql);
            for (BillReceiveCorp e : list) {
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
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getPendingDisbursementbyCorpgroupbyBillingDate(String billtype) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) and s.bill_type='" + billtype + "' order by TRUNC(billing_date) ASC";

//            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and bill_type='" + billtype + "' )  and s.bill_type='" + billtype + "' order by TRUNC(billing_date) ASC";
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

    public List<Timestamp> getPendingDisbursementbyCorpgroupbyBillingDateTimestamp(String billtype) {
        List<Timestamp> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY')  order by TRUNC(s.billing_date) ASC";
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

    public List<BigDecimal> getPendingDisbursementbyCorpgroupbyWeekID() {
        List<BigDecimal> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_RECEIVE_CORP where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY')  order by week_id ASC ";
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
    
    public List<BigDecimal> getPendingDisbursementbyCorpgroupbyWeekIDdsm() {
        List<BigDecimal> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_RECEIVE_CORP where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and (lower(BILL_TYPE)='dsm' or lower(BILL_TYPE)='rras'  or lower(BILL_TYPE)='agc') order by week_id ASC ";
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
    
    public List<BigDecimal> getPendingDisbursementbyCorpgroupbyWeekIDsras() {
        List<BigDecimal> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_RECEIVE_CORP where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and (lower(BILL_TYPE)='sras' ) order by week_id ASC ";
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
    
    public List<BigDecimal> getPendingDisbursementbyCorpgroupbyWeekIDtras() {
        List<BigDecimal> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_RECEIVE_CORP where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and (lower(BILL_TYPE)='trasm' or lower(BILL_TYPE)='trass' or lower(BILL_TYPE)='trase') order by week_id ASC ";
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

    public int getUpdateBillReceiveCorpbyChecker(BigDecimal uniqueid, BigDecimal disbusramt, BigDecimal pendingamt, String disbursestatus) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update   BILL_RECEIVE_CORP set DISBURSE_AMOUNT='" + disbusramt + "', PENDING_AMOUNT='" + pendingamt + "',DISBURSE_STATUS='" + disbursestatus + "'  where unique_id='" + uniqueid + "' ";
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

    public int getUpdateBillReceiveCorpbyChecker1(BigDecimal uniqueid, BigDecimal disbusramt, BigDecimal revisedpayable, String disbursestatus) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update   BILL_RECEIVE_CORP set DISBURSE_AMOUNT='" + disbusramt + "',PENDING_AMOUNT='" + revisedpayable + "', DISBURSE_STATUS='" + disbursestatus + "'  where unique_id='" + uniqueid + "' ";
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

    public List<Object[]> getPendingDisbursementbyCorpgroupbyBillingDate() {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and s.BILL_TYPE=BILL_TYPE) order by TRUNC(billing_date) ASC";
//          String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id)  order by TRUNC(billing_date) ASC";
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

    public BigDecimal getDisburseAmtToCorpByPoolWeekYearRevNo(BigDecimal weekId, BigDecimal year, int Corporate, BigDecimal revNo, String billType) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(DISBURSE_AMOUNT,0) from BILL_RECEIVE_CORP where WEEK_ID='" + weekId + "' and BILL_YEAR='" + year + "' and CORPORATE_ID='" + Corporate + "' and REVISION_NO='" + revNo + "' and BILL_TYPE='" + billType + "' ";
            //String hql="select PAID_AMOUNT from BILL_PAYABLE_CORP where WEEK_ID='"+weekId+"' and BILL_YEAR='"+year+"' and CORPORATE_ID='"+Corporate+"' and REVISION_NO='"+revNo+"'"; 
            System.out.println("####### getDisburseAmtToCorpByPoolWeekYearRevNo hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            System.out.println("query.list() is " + query.list());
            if (query.list() != null && !(query.list().isEmpty())) {
                System.out.println("result is " + query.list().get(0));
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

    public List<BillReceiveCorp> getOverallPendingReceiveAmountbyCorp(int corpId) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillReceiveCorp s where s.revisionNo=(select max(revisionNo) from BillReceiveCorp where s.weekId=weekId and s.corporates.corporateId =corporates.corporateId) and (s.disburseStatus='NOT PAID' OR s.disburseStatus='PARTIALLY') and s.corporates.corporateId=:corpId order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("corpId", corpId);
            list = query.list();
            for (BillReceiveCorp e : list) {
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
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalDisburseAmountbyCorpandWeek(int corpID, int weekid, String billtype) {
        int result = 0;
        BigDecimal bg = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select disburse_amount from BILL_RECEIVE_CORP a where a.revision_no= (select revision_no from MAX_RECEIVE_REVISION_NO  where CORPORATE_ID=" + corpID + " and week_id=" + weekid + " and BILL_TYPE='" + billtype + "') and CORPORATE_ID=" + corpID + " and week_id=" + weekid + " and BILL_TYPE='" + billtype + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Disburse amount is " + bg);
                result = bg.intValueExact();
                System.out.print("Disburse amount is INT " + bg);
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

    public BigDecimal getTotalDisburseAmountbyCorpandWeek(int corpID, int weekid, String billtype, BigDecimal uniqueid) {
        BigDecimal result = new BigDecimal(0);
        BigDecimal bg = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select disburse_amount from BILL_RECEIVE_CORP a where unique_id=" + uniqueid + " and CORPORATE_ID=" + corpID + " and week_id=" + weekid + " and BILL_TYPE='" + billtype + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Disburse amount is " + bg);
                result = bg;
                System.out.print("Disburse amount is INT " + bg);
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

    public List<BillReceiveCorp> getRefundPendingBillReceiveAmountbyCorp(int corpId) {

        List<BillReceiveCorp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

//            String hql="from BillReceiveCorp s where s.disburseStatus='REFUND' and s.adjustmentflag=0 and s.corporates.corporateId=:corpId order by s.weekId ASC ";
//            String hql = "from BillReceiveCorp s where s.disburseStatus='REFUND' and s.pendingAmount!=0 and s.corporates.corporateId=:corpId order by s.weekId ASC ";
            String hql = "from BillReceiveCorp s where s.disburseStatus='REFUND' and s.pendingAmount>=1 and s.corporates.corporateId=:corpId order by s.weekId ASC ";

            System.out.println("Refund hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("corpId", corpId);

            list = query.list();

            for (BillReceiveCorp e : list) {

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

            e.printStackTrace();

        }

        return list;

    }

    public int getUpdateAdjustFlagbyUniqueID(int unqeID, BigDecimal Adjustamount) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BILL_RECEIVE_CORP set  adjustmentflag=1 , adjustment_amount=" + Adjustamount + " where unique_id=" + unqeID + " ";

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

    public List<Object[]> getAllRefundPendingBillReceiveAmountbyCorp() {

        List<Object[]> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "select distinct c.corporate_id,c.corporate_name from BILL_RECEIVE_CORP s , corporates c  where s.DISBURSE_STATUS='REFUND' and s.corporate_id = c.corporate_id and s.ADJUSTMENTFLAG='0' and s.revision_no =(select revision_no from MAX_RECEIVE_REVISION_NO where week_id=s.week_id and corporate_id=s.corporate_id and bill_year=s.bill_year and bill_type=s.bill_type)";

            System.out.println("Refund hql is " + hql);

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

    public int getUpdateAdjustFlagbyUniqueIDOnDelete(int unqeID) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BILL_RECEIVE_CORP set  adjustmentflag=0 , adjustment_amount=0 where unique_id=" + unqeID + " ";

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

    public List<BillReceiveCorp> BillReceiveCorplistMonthly(BigDecimal week_id1, BigDecimal week_id2, String billType, BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billType=:billType order by billingDate");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }
    }

    public List<BillReceiveCorp> BillReceiveCorppendinglisthql() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveCorp s where (s.disburseStatus='NOT PAID' OR s.disburseStatus='PARTIALLY') and s.pendingAmount>10");

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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }
    }

    public List<Object[]> getPendingDisbursementbyCorp() {

        List<Object[]> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "select TRUNC(s.billing_date),s.WEEK_ID,s.BILL_TYPE,s.CORPORATE_ID,s.TOALNET,s.DISBURSE_AMOUNT,s.PENDING_AMOUNT,s.revision_no,s.BILL_DUE_DATE from BILL_RECEIVE_CORP s where (s.DISBURSE_STATUS='NOT PAID' OR s.DISBURSE_STATUS='PARTIALLY') and s.PENDING_AMOUNT!=0  ";

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

            e.printStackTrace();

        }

        return list;

    }

    public List<BillReceiveCorp> getBillRecvCorpDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, String billType, BigDecimal yearId, int corpid) {
        List<BillReceiveCorp> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            System.out.print("Inside getBillRecvCorpDetailsbyWeekId " + weekId + "RevsioNO:" + revisionNo + "billtype:" + billType + "Year:" + yearId + "corpid:" + corpid);
            String hql = " from BillReceiveCorp s where s.weekId=:weekId and s.billYear=:yearId and s.revisionNo =:revisionNo and s.billType=:billType and s.corporates.corporateId=:corpid";

            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("yearId", yearId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("billType", billType);
            query.setParameter("corpid", corpid);

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

    public BigDecimal getTotalDisbursedAmtByCorpWeekYearRevNo(BigDecimal weekId, BigDecimal year, int Corporate, String billType) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select SUM(DISBURSE_AMOUNT) from BILL_RECEIVE_CORP where WEEK_ID='" + weekId + "' and BILL_YEAR='" + year + "' and CORPORATE_ID='" + Corporate + "'  and BILL_TYPE='" + billType + "' ";
            //String hql="select PAID_AMOUNT from BILL_PAYABLE_CORP where WEEK_ID='"+weekId+"' and BILL_YEAR='"+year+"' and CORPORATE_ID='"+Corporate+"' and REVISION_NO='"+revNo+"'"; 

            System.out.println("####### getPaidAmtByCorpWeekYearRevNo hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            System.out.println("query.list() is " + query.list());

            if (query.list() != null && !(query.list().isEmpty())) {
                System.out.println("result is " + query.list().get(0));
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

    public int getUpdatePrevBillReceiveCorponUpload(int uniqueid, BigDecimal pendingamount, String billStatus) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BILL_RECEIVE_CORP set PENDING_AMOUNT=" + pendingamount + ", DISBURSE_STATUS='" + billStatus + "' where UNIQUE_ID=" + uniqueid + "";
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

    public BigDecimal getUpdatePaidAmtbyCorpWeekYearRevNoOnupload(BigDecimal weekId, BigDecimal year, int Corporate, String billType, int revNo, BigDecimal disburseamt) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BILL_RECEIVE_CORP set  DISBURSE_STATUS='PAID' where WEEK_ID='" + weekId + "' and BILL_YEAR='" + year + "' and REVISION_NO='" + revNo + "' and CORPORATE_ID='" + Corporate + "'  and BILL_TYPE='" + billType + "' ";

            System.out.println("####### getPaidAmtByCorpWeekYearRevNo hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            query.executeUpdate();

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

    public boolean deleteBillReceiveCorpbyWeekIdYearRevNoBillType(BigDecimal weekId, BigDecimal yearid, BigDecimal revisionNo, String billType) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from BillReceiveCorp where weekId=:weekId and billYear=:yearid and revisionNo=:revisionNo and billType=:billType";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("yearid", yearid);
            query.setBigDecimal("revisionNo", revisionNo);
            query.setString("billType", billType);
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

    public BigDecimal getLatestReceivableAmountDSM() {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "select RECV_CHARGES from BILL_RECEIVE_ENTITY_DSM s where s.revision_no =  ( select revision_no from MAX_RECEIVE_REVISION_NO where bill_type='DSM' and s.week_id=week_id ) and s.week_id = (select max(week_id) from BILL_DSM_DETAILS)";

            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {

                result = (BigDecimal) query.list().get(0);

                System.out.println("result is " + result);

            }

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

    public List<Object[]> getLatestBillsReceivableAllTypes() {

        List<Object[]> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

//            String hql="select bill_type,week_id,toalnet,bill_due_date from bill_receive_corp s where revision_no IN (select revision_no from MAX_RECEIVE_REVISION_NO where bill_type=s.bill_type and s.week_id=week_id) and week_id=(select max(week_id) from bill_receive_corp where bill_type=s.bill_type)" ;
            String hql = "select s.BILL_TYPE,s.WEEK_ID,sum(s.PENDING_AMOUNT),s.BILL_DUE_DATE from BILL_RECEIVE_CORP s where (s.DISBURSE_STATUS='NOT PAID' OR s.DISBURSE_STATUS='PARTIALLY') and s.PENDING_AMOUNT>=100 group by s.WEEK_ID,s.BILL_TYPE,s.BILL_DUE_DATE";

            System.out.println("hql is " + hql);

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

    public List<Object[]> getReceivableBillRecvbyCorpGroupbyWeekforBarChart() {

        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "select week_id,bill_type,toalnet from bill_receive_corp s where revision_no =(select max(revision_no) from bill_receive_corp where bill_type=s.bill_type and s.week_id=week_id) and week_id=(select max(week_id) from bill_receive_corp where bill_type=s.bill_type)";
            String hql = "select s.BILL_TYPE,s.WEEK_ID,sum(s.PENDING_AMOUNT),s.BILL_DUE_DATE from BILL_RECEIVE_CORP s where (s.DISBURSE_STATUS='NOT PAID' OR s.DISBURSE_STATUS='PARTIALLY') and s.PENDING_AMOUNT>=100 group by s.WEEK_ID,s.BILL_TYPE,s.BILL_DUE_DATE";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
            System.out.print("Group by lis sizeis" + list.size());
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

    public List<BillReceiveCorp> getPendingRefundBillReceiveList() {
        List<BillReceiveCorp> list = null;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from BillReceiveCorp s where s.disburseStatus='REFUND' and s.pendingAmount !=0 order by s.uniqueId ASC";
            System.out.println("Refund hql is " + hql);

            Query query = session.createQuery(hql);

            list = query.list();

            for (BillReceiveCorp e : list) {

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

    public List<BillReceiveCorp> getRefundPendingBillReceiveAmountNotINTempRefundbyCorp(int corpId) {

        List<BillReceiveCorp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

//            String hql = "from BillReceiveCorp s where s.disburseStatus='REFUND' and s.pendingAmount !=0 and s.corporates.corporateId=:corpId and s.uniqueId NOT IN ( select e.billReceiveCorp.uniqueId from TempRefundBillCorp e where e.checkerStatus='Pending') order by s.weekId ASC ";
            String hql = "from BillReceiveCorp s where s.disburseStatus='REFUND' and s.pendingAmount >=1 and s.corporates.corporateId=:corpId and s.uniqueId NOT IN ( select e.billReceiveCorp.uniqueId from TempRefundBillCorp e where e.checkerStatus='Pending') order by s.weekId ASC ";

            System.out.println("Refund hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("corpId", corpId);

            list = query.list();

            for (BillReceiveCorp e : list) {

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

            e.printStackTrace();

        }

        return list;

    }

    public int getUpdateRefundBillReceivableCorpbyChecker(int uniqueid, BigDecimal paidamount, BigDecimal pendingamount) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BILL_RECEIVE_CORP set ADJUSTMENT_AMOUNT=" + paidamount + ",PENDING_AMOUNT=" + pendingamount + " where UNIQUE_ID=" + uniqueid + "";

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

    public List<Date> getPendingReceivablebyCorpgroupbyBillingDate(int corpid) {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
//            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP where (DISBURSE_STATUS='REFUND' and PENDING_AMOUNT != 0) and CORPORATE_ID='" + corpid + "'  order by TRUNC(billing_date) ASC ";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP where (DISBURSE_STATUS='REFUND' and PENDING_AMOUNT >= 1) and CORPORATE_ID='" + corpid + "'  order by TRUNC(billing_date) ASC ";

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

    public List<BigDecimal> getPendingReceivebyCorpgroupbyWeekID(int corpid) {

        List<BigDecimal> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
//            String hql = "select distinct week_id from BILL_RECEIVE_CORP where (DISBURSE_STATUS='REFUND' and PENDING_AMOUNT != 0) and  CORPORATE_ID='" + corpid + "' order by week_id ASC ";
            String hql = "select distinct week_id from BILL_RECEIVE_CORP where (DISBURSE_STATUS='REFUND' and PENDING_AMOUNT >= 1) and  CORPORATE_ID='" + corpid + "' order by week_id ASC ";

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

    public List<Date> getAllPendingDisbursementbyCorpgroupbyBillingDateTimestamp() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and TOALNET !=0 order by TRUNC(s.billing_date) ASC";

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
    
    public List<Date> getAllPendingDisbursementbyCorpgroupbyBillingDateTimestampdsm() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and TOALNET !=0 and (lower(s.BILL_TYPE)='dsm' or lower(s.BILL_TYPE)='rras'  or lower(s.BILL_TYPE)='agc') order by TRUNC(s.billing_date) ASC";

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
    
    public List<Date> getAllPendingDisbursementbyCorpgroupbyBillingDateTimestampsras() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and TOALNET !=0 and (lower(s.BILL_TYPE)='sras' ) order by TRUNC(s.billing_date) ASC";

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
    
    public List<Date> getAllPendingDisbursementbyCorpgroupbyBillingDateTimestamptras() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and TOALNET !=0 and (lower(s.BILL_TYPE)='trasm' or lower(s.BILL_TYPE)='trass' or lower(s.BILL_TYPE)='trase') order by TRUNC(s.billing_date) ASC";

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

    public List<Object[]> getAllPendingDisbursementbyCorpObject() {

        List<Object[]> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,bill_due_date from BILL_RECEIVE_CORP s where s.revision_No=(select revision_No from MAX_RECEIVE_REVISION_NO where s.week_Id=week_Id and s.corporate_Id=corporate_Id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "') and (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') AND s.bill_type='" + billtype + "' order by s.billing_date ASC ";
            String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,BILL_DUE_DATE,BILL_YEAR from BILL_RECEIVE_CORP s where  (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY')  order by s.billing_date ASC ";

            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.println("Pending Dibusrsemnt list size is is " + list.size());

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
    
     public List<Object[]> getAllPendingDisbursementbyCorpObjectdsm() {

        List<Object[]> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,bill_due_date from BILL_RECEIVE_CORP s where s.revision_No=(select revision_No from MAX_RECEIVE_REVISION_NO where s.week_Id=week_Id and s.corporate_Id=corporate_Id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "') and (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') AND s.bill_type='" + billtype + "' order by s.billing_date ASC ";
            String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,BILL_DUE_DATE,BILL_YEAR from BILL_RECEIVE_CORP s where  (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') and (lower(s.BILL_TYPE)='dsm' or lower(s.BILL_TYPE)='rras'  or lower(s.BILL_TYPE)='agc') order by s.billing_date ASC ";

            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.println("Pending Dibusrsemnt list size is is " + list.size());

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
     
     public List<Object[]> getAllPendingDisbursementbyCorpObjectsras() {

        List<Object[]> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,bill_due_date from BILL_RECEIVE_CORP s where s.revision_No=(select revision_No from MAX_RECEIVE_REVISION_NO where s.week_Id=week_Id and s.corporate_Id=corporate_Id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "') and (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') AND s.bill_type='" + billtype + "' order by s.billing_date ASC ";
            String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,BILL_DUE_DATE,BILL_YEAR from BILL_RECEIVE_CORP s where  (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') and (lower(s.BILL_TYPE)='sras' ) order by s.billing_date ASC ";

            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.println("Pending Dibusrsemnt list size is is " + list.size());

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
     
     
     
     public List<Object[]> getAllPendingDisbursementbyCorpObjecttras() {

        List<Object[]> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,bill_due_date from BILL_RECEIVE_CORP s where s.revision_No=(select revision_No from MAX_RECEIVE_REVISION_NO where s.week_Id=week_Id and s.corporate_Id=corporate_Id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "') and (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') AND s.bill_type='" + billtype + "' order by s.billing_date ASC ";
            String hql = "select unique_id,bill_type, billing_date ,CORPORATE_ID, week_id,revision_no, bill_priority , pending_amount,toalnet,REVISEDPAYBALE,REVISEDREFUND,BILL_DUE_DATE,BILL_YEAR from BILL_RECEIVE_CORP s where  (s.disburse_Status='NOT PAID' OR s.disburse_Status='PARTIALLY') and (lower(s.BILL_TYPE)='trasm' or lower(s.BILL_TYPE)='trass' or lower(s.BILL_TYPE)='trase') order by s.billing_date ASC ";

            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();

            System.out.println("Pending Dibusrsemnt list size is is " + list.size());

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
    public List<BillReceiveCorp> getPendingBillCorpRefundList() {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.billType=billType and s.corporates.corporateId =corporates.corporateId) and (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.corporates.corporateName ASC ";
            String hql = "from BillReceiveCorp s where  s.disburseStatus='REFUND' and pendingAmount!=0 order by s.corporates.corporateName ASC ";

            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            list = query.list();
            for (BillReceiveCorp e : list) {
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
            e.printStackTrace();
        }
        return list;
    }

    public List<Timestamp> getBillReceivablebyCorpgroupbyBillingDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(s.billing_date)) from BILL_RECEIVE_CORP s, CORPORATES c  where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "'  and s.UNIQUE_ID !=0 and extract(YEAR from s.billing_date)='" + year + "' order by TRUNC(s.billing_date) ASC";

// String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
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

    public List<BillReceiveCorp> getReconPendingBillCorpNameList(int corpid, String year) {
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveCorp s where s.corporates.corporateId=:corpid and s.uniqueId!=0 and to_char(s.billingDate,'YYYY')=:year order by s.billingDate , s.revisionNo ");

            query.setParameter("corpid", corpid);
            query.setParameter("year", year);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }
    }

    public List<Timestamp> getRefundBillReceivablebyCorpgroupbyBillingDateTimestamp(int corpid) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(s.billing_date)) from BILL_RECEIVE_CORP s, CORPORATES c where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "' and s.DISBURSE_STATUS='REFUND' and s.ADJUSTMENT_AMOUNT=0 and s.UNIQUE_ID !=0 order by TRUNC(s.billing_date) ASC";

// String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
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

    public List<BillReceiveCorp> getReconrefundPendingBillCorpNameList(int corpid) {
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveCorp s where s.corporates.corporateId=:corpid and s.disburseStatus='REFUND' and s.adjustmentAmount=0 and s.uniqueId !=0 order by s.billingDate ");

            query.setParameter("corpid", corpid);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }
    }

    public List<BillReceiveCorp> getBillReceiveCorplistbyUniqueID(int unqID) {

        Session session = null;

        List<BillReceiveCorp> queryList = null;

        System.out.println("Insdei DAO UNIEOQES ID is" + unqID);

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from BillReceiveCorp s where s.uniqueId =:unqID ");

            query.setBigDecimal("unqID", new BigDecimal(unqID));

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                System.out.println("Paid is  size" + queryList.get(0).getAdjustmentAmount());

                for (BillReceiveCorp e : queryList) {

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
}
