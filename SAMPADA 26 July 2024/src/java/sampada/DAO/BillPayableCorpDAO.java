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
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BillPayableCorpDAO {

    public List<BillPayableCorp> BillPayableCorplist(int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.corporates.corporateId=:corpid order by billDueDate, case billType when 'DSM' then 1 when 'AGC' then 2 when 'RRAS' then 3");

            query.setParameter("corpid", corpid);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getTempBillPayableCorpDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, String billType, BigDecimal yearId) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select * from temp_bill_payable_corp where week_id=" + weekId + " and revision_no=" + revisionNo + "  and bill_type='" + billType + "' and bill_year='" + yearId + "'";

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
            String hql = "select NVL(max(UNIQUE_ID),0) FROM BILL_PAYABLE_CORP ";
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

    public boolean NewBillPayCorpEntries(BillPayableCorp dic) {

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

    public List<Object[]> getPayableCorpList(int corpId, String billStatus, int yearId) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "SELECT UNIQUE_ID, BILL_CATEGORY, BILL_TYPE, WEEK_ID, TO_CHAR(BILL_DUE_DATE , 'YYYY-MM-DD') AS BILL_DUE_DATE, TOTALNET,PENDING_AMOUNT   FROM BILL_PAYABLE_CORP WHERE BILL_STATUS='" + billStatus + "' AND BILL_YEAR='" + yearId + "' AND CORPORATE_ID='" + corpId + "' ORDER BY BILL_DUE_DATE, CASE BILL_TYPE WHEN 'DSM' THEN 1 WHEN 'AGC' THEN 2 WHEN 'RRAS' THEN 3 END";
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
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getPendingPayableCorpList(BigDecimal corpId, String BILLTYPE) {

        Session session = null;
        List<Object[]> list = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            String hql1 = "select unique_id,BILL_TYPE,BILL_CATEGORY,BILLING_DATE,BILL_DUE_DATE,TOTALNET,PENDING_AMOUNT,corporate_id,week_id,a.REVISION_NO from BILL_PAYABLE_CORP a  where a.corporate_id='" + corpId + "' and (a.BILL_STATUS='NOT PAID' OR a.BILL_STATUS='PARTIALLY') AND a.BILL_TYPE='" + BILLTYPE + "' ";
            //String hql1 = "select unique_id,BILL_TYPE,BILL_CATEGORY,BILLING_DATE,BILL_DUE_DATE,TOTALNET,PENDING_AMOUNT,corporate_id,week_id,a.REVISION_NO from BILL_PAYABLE_CORP a  where a.corporate_id='" + corpId + "' and revision_no = (select b.revision_no from MAX_PAYABLE_REVISION_NO b where b.CORPORATE_ID = a.CORPORATE_ID  and a.WEEK_ID=b.WEEK_ID and b.BILL_TYPE='" + BILLTYPE + "') and (a.BILL_STATUS='NOT PAID' OR a.BILL_STATUS='PARTIALLY') AND a.BILL_TYPE='" + BILLTYPE + "' ";
            SQLQuery q = session.createSQLQuery(hql1);
            System.out.print("HDQL1" + hql1);
            list = q.list();
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

    public List<BillPayableCorp> getPendingBillPaybyCorp() {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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
            e.printStackTrace();
        }
        return list;
    }

    public List<BillPayableCorp> getPendingBillPayAllCorp() {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and s.pendingAmount>0 order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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
            e.printStackTrace();
        }
        return list;
    }

    public List<BillPayableCorp> getOverallPendingAmountBillPaybyCorp(int corpId) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.corporates.corporateId =corporates.corporateId) and (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and s.corporates.corporateId=:corpId order by s.weekId ASC ";
            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);
            query.setParameter("corpId", corpId);
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
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getPendingBillPaybyCorpGroupbyWeek() {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select distinct s.weekId from BillPayableCorp s group by s.weekId ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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

    public List<Object[]> getPendingBillPaybyCorpGroupbyWeekforBarChart() {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "select  s.weekId as weekid,s.billType as billtype,sum(s.totalnet) as total from BillPayableCorp s group by s.weekId,s.billType order by s.weekId";
            String hql = "select s.WEEK_ID,s.BILL_TYPE,sum(s.PENDING_AMOUNT) from BILL_PAYABLE_CORP s where (s.BILL_STATUS='NOT PAID' OR s.BILL_STATUS='PARTIALLY') and s.PENDING_AMOUNT>=100 group by s.WEEK_ID,s.BILL_TYPE,s.BILL_DUE_DATE";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
//            Query query = session.createQuery(hql);
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

    public List<Date> getPendingPayablebyCorpgroupbyBillingDate(int corpid) {
        List<Date> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
//            String hql = "select distinct(TRUNC(billing_date)) from BILL_PAYABLE_CORP where (BILL_STATUS='NOT PAID' OR BILL_STATUS='PARTIALLY')  order by TRUNC(billing_date) ASC ";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_PAYABLE_CORP where (BILL_STATUS='NOT PAID' OR BILL_STATUS='PARTIALLY') and PENDING_AMOUNT >=1 and CORPORATE_ID='" + corpid + "'   order by TRUNC(billing_date) ASC ";

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

    public List<BigDecimal> getPendingPaymentbyCorpgroupbyWeekID(int corpid) {
        List<BigDecimal> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
//            String hql = "select distinct week_id from BILL_PAYABLE_CORP where (BILL_STATUS='NOT PAID' OR BILL_STATUS='PARTIALLY')  order by week_id ASC ";
            String hql = "select distinct week_id from BILL_PAYABLE_CORP where (BILL_STATUS='NOT PAID' OR BILL_STATUS='PARTIALLY') and PENDING_AMOUNT >=1 and  CORPORATE_ID='" + corpid + "'  order by week_id ASC ";

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

    public int getUpdateBillPayableCorpbyChecker(int uniqueid, BigDecimal paidamount, BigDecimal pendingamount, String billStatus) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BILL_PAYABLE_CORP set PAID_AMOUNT=" + paidamount + ",PENDING_AMOUNT=" + pendingamount + ", BILL_STATUS='" + billStatus + "' where UNIQUE_ID=" + uniqueid + "";
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

    public int getUpdateBillPayableCorpbyChecker1(int uniqueid, BigDecimal paidamount, BigDecimal pendingamount, String billStatus) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BILL_PAYABLE_CORP set PAID_AMOUNT=" + paidamount + ",PENDING_AMOUNT=" + pendingamount + ", BILL_STATUS='" + billStatus + "' where UNIQUE_ID=" + uniqueid + "";
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

    public BigDecimal getPaidAmtByCorpWeekYearRevNo(BigDecimal weekId, BigDecimal year, int Corporate, BigDecimal revNo, String billType) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(PAID_AMOUNT,0) from BILL_PAYABLE_CORP where WEEK_ID='" + weekId + "' and BILL_YEAR='" + year + "' and CORPORATE_ID='" + Corporate + "' and REVISION_NO='" + revNo + "' and BILL_TYPE='" + billType + "' ";
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

    public List<BillPayableCorp> getPendingBillCorpNameList() {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.billType=billType and s.corporates.corporateId =corporates.corporateId) and (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.corporates.corporateName ASC ";
            String hql = "from BillPayableCorp s where  (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.corporates.corporateName ASC ";

            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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
            e.printStackTrace();
        }
        return list;
    }
    
    public List<BillPayableCorp> getPendingBillCorpNameListdsm() {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.billType=billType and s.corporates.corporateId =corporates.corporateId) and (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.corporates.corporateName ASC ";
            String hql = "from BillPayableCorp s where  (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and (lower(s.billType)='dsm' or lower(s.billType)='rras'  or lower(s.billType)='agc') order by s.corporates.corporateName ASC ";

            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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
            e.printStackTrace();
        }
        return list;
    }
    public List<BillPayableCorp> getPendingBillCorpNameListsras() {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.billType=billType and s.corporates.corporateId =corporates.corporateId) and (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.corporates.corporateName ASC ";
            String hql = "from BillPayableCorp s where  (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and (lower(s.billType)='sras') order by s.corporates.corporateName ASC ";

            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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
            e.printStackTrace();
        }
        return list;
    }
    
    public List<BillPayableCorp> getPendingBillCorpNameListtras() {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.billType=billType and s.corporates.corporateId =corporates.corporateId) and (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') order by s.corporates.corporateName ASC ";
            String hql = "from BillPayableCorp s where  (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and (lower(s.billType)='trasm' or lower(s.billType)='trass' or lower(s.billType)='trase') order by s.corporates.corporateName ASC ";

            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
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
            e.printStackTrace();
        }
        return list;
    }

    public List<BillPayableCorp> BillPayableCorpNotInTemplist(int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BillPayableCorp s where s.corporates.corporateId=:corpid and  s.uniqueId NOT IN (select e.billPayableCorp.uniqueId from TempMappingBillBank e where e.checkerStatus='Pending' and e.corporates.corporateId=:corpid)");
            Query query = session.createQuery("from BillPayableCorp s where (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and s.pendingAmount >=1 and s.corporates.corporateId=:corpid and  s.uniqueId NOT IN (select e.billPayableCorp.uniqueId from TempMappingBillBank e where e.checkerStatus='Pending' and e.corporates.corporateId=:corpid)");

            query.setParameter("corpid", corpid);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BillPayableCorp> getBillPayableCorplistbyUniqueID(int unqID) {

        Session session = null;

        List<BillPayableCorp> queryList = null;

        System.out.println("Insdei DAO UNIEOQES ID is" + unqID);

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from BillPayableCorp s where s.uniqueId =:unqID ");

            query.setBigDecimal("unqID", new BigDecimal(unqID));

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                System.out.println("Paid is  size" + queryList.get(0).getPaidAmount());

                for (BillPayableCorp e : queryList) {

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

    public List<Object[]> getPendingPayableBillsbyallCorp() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_PAYABLE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql=" select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n" +
//" from BILL_PAYABLE_CORP a inner join CORPORATES c \n" +
//" on a.CORPORATE_ID=c.CORPORATE_ID \n" +
//" where a.REVISION_NO in (select REVISION_NO from MAX_PAYABLE_REVISION_NO where CORPORATE_ID=a.CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE ) \n" +
//" and (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY')\n" +
//" group by a.bill_type,c.CORPORATE_NAME";
            String hql = " select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n"
                    + " from BILL_PAYABLE_CORP a inner join CORPORATES c \n"
                    + " on a.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY')\n"
                    + " group by a.bill_type,c.CORPORATE_NAME UNION \n"
                    + " select c.CORPORATE_NAME,r.bill_type,sum(r.pending_amount) \n"
                    + " from BILL_RECEIVE_CORP r inner join CORPORATES c \n"
                    + " on r.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where r.disburse_status='REFUND' \n"
                    + " group by r.bill_type,c.CORPORATE_NAME";

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

            e.printStackTrace();

        }

        return list;

    }
    
    public List<Object[]> getPendingPayableBillsbyallCorpdsm() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_PAYABLE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql=" select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n" +
//" from BILL_PAYABLE_CORP a inner join CORPORATES c \n" +
//" on a.CORPORATE_ID=c.CORPORATE_ID \n" +
//" where a.REVISION_NO in (select REVISION_NO from MAX_PAYABLE_REVISION_NO where CORPORATE_ID=a.CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE ) \n" +
//" and (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY')\n" +
//" group by a.bill_type,c.CORPORATE_NAME";
            String hql = " select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n"
                    + " from BILL_PAYABLE_CORP a inner join CORPORATES c \n"
                    + " on a.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY') and (lower(a.BILL_TYPE)='dsm' or lower(a.BILL_TYPE)='rras'  or lower(a.BILL_TYPE)='agc')\n"
                    + " group by a.bill_type,c.CORPORATE_NAME UNION \n"
                    + " select c.CORPORATE_NAME,r.bill_type,sum(r.pending_amount) \n"
                    + " from BILL_RECEIVE_CORP r inner join CORPORATES c \n"
                    + " on r.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where r.disburse_status='REFUND' and (lower(r.BILL_TYPE)='dsm' or lower(r.BILL_TYPE)='rras'  or lower(r.BILL_TYPE)='agc')\n"
                    + " group by r.bill_type,c.CORPORATE_NAME";

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

            e.printStackTrace();

        }

        return list;

    }
    
    public List<Object[]> getPendingPayableBillsbyallCorpsras() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_PAYABLE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql=" select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n" +
//" from BILL_PAYABLE_CORP a inner join CORPORATES c \n" +
//" on a.CORPORATE_ID=c.CORPORATE_ID \n" +
//" where a.REVISION_NO in (select REVISION_NO from MAX_PAYABLE_REVISION_NO where CORPORATE_ID=a.CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE ) \n" +
//" and (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY')\n" +
//" group by a.bill_type,c.CORPORATE_NAME";
            String hql = " select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n"
                    + " from BILL_PAYABLE_CORP a inner join CORPORATES c \n"
                    + " on a.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY') and (lower(a.BILL_TYPE)='sras')\n"
                    + " group by a.bill_type,c.CORPORATE_NAME UNION \n"
                    + " select c.CORPORATE_NAME,r.bill_type,sum(r.pending_amount) \n"
                    + " from BILL_RECEIVE_CORP r inner join CORPORATES c \n"
                    + " on r.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where r.disburse_status='REFUND' and (lower(r.BILL_TYPE)='sras' )\n"
                    + " group by r.bill_type,c.CORPORATE_NAME";

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

            e.printStackTrace();

        }

        return list;

    }
    public List<Object[]> getPendingPayableBillsbyallCorptras() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_PAYABLE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql=" select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n" +
//" from BILL_PAYABLE_CORP a inner join CORPORATES c \n" +
//" on a.CORPORATE_ID=c.CORPORATE_ID \n" +
//" where a.REVISION_NO in (select REVISION_NO from MAX_PAYABLE_REVISION_NO where CORPORATE_ID=a.CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE ) \n" +
//" and (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY')\n" +
//" group by a.bill_type,c.CORPORATE_NAME";
            String hql = " select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) \n"
                    + " from BILL_PAYABLE_CORP a inner join CORPORATES c \n"
                    + " on a.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where (a.bill_status='NOT PAID' OR a.bill_status='PARTIALLY') and (lower(a.BILL_TYPE)='trasm' or lower(a.BILL_TYPE)='trass' or lower(a.BILL_TYPE)='trase')\n"
                    + " group by a.bill_type,c.CORPORATE_NAME UNION \n"
                    + " select c.CORPORATE_NAME,r.bill_type,sum(r.pending_amount) \n"
                    + " from BILL_RECEIVE_CORP r inner join CORPORATES c \n"
                    + " on r.CORPORATE_ID=c.CORPORATE_ID \n"
                    + " where r.disburse_status='REFUND' and (lower(r.BILL_TYPE)='trasm' or lower(r.BILL_TYPE)='trass' or lower(r.BILL_TYPE)='trase' )\n"
                    + " group by r.bill_type,c.CORPORATE_NAME";

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

            e.printStackTrace();

        }

        return list;

    }

    public List<Object[]> getPendingReceivableBillsbyallCorp() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where a.REVISION_NO In (select REVISION_NO from MAX_RECEIVE_REVISION_NO where a.CORPORATE_ID=CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE) and (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') group by a.bill_type,c.CORPORATE_NAME";           
            String hql = "select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') group by a.bill_type,c.CORPORATE_NAME UNION select c.CORPORATE_NAME, r.bill_type, sum(r.pending_amount) from BILL_PAYABLE_CORP r inner join CORPORATES c on r.CORPORATE_ID=c.CORPORATE_ID where r.bill_status='REFUND' group by r.bill_type, c.CORPORATE_NAME ";


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

            e.printStackTrace();

        }

        return list;

    }
    
    public List<Object[]> getPendingReceivableBillsbyallCorpdsm() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where a.REVISION_NO In (select REVISION_NO from MAX_RECEIVE_REVISION_NO where a.CORPORATE_ID=CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE) and (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') group by a.bill_type,c.CORPORATE_NAME";           

            String hql = "select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') and (lower(a.BILL_TYPE)='dsm' or lower(a.BILL_TYPE)='rras'  or lower(a.BILL_TYPE)='agc') group by a.bill_type,c.CORPORATE_NAME UNION select c.CORPORATE_NAME, r.bill_type, sum(r.pending_amount) from BILL_PAYABLE_CORP r inner join CORPORATES c on r.CORPORATE_ID=c.CORPORATE_ID where r.bill_status='REFUND' and (lower(r.BILL_TYPE)='dsm' or lower(r.BILL_TYPE)='rras'  or lower(r.BILL_TYPE)='agc') group by r.bill_type, c.CORPORATE_NAME ";

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

            e.printStackTrace();

        }

        return list;

    }
    
    public List<Object[]> getPendingReceivableBillsbyallCorpsras() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where a.REVISION_NO In (select REVISION_NO from MAX_RECEIVE_REVISION_NO where a.CORPORATE_ID=CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE) and (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') group by a.bill_type,c.CORPORATE_NAME";           

            String hql = "select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') and (lower(a.BILL_TYPE)='sras' ) group by a.bill_type,c.CORPORATE_NAME UNION select c.CORPORATE_NAME, r.bill_type, sum(r.pending_amount) from BILL_PAYABLE_CORP r inner join CORPORATES c on r.CORPORATE_ID=c.CORPORATE_ID where r.bill_status='REFUND' and (lower(r.BILL_TYPE)='sras' ) group by r.bill_type, c.CORPORATE_NAME ";

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

            e.printStackTrace();

        }

        return list;

    }
    
    public List<Object[]> getPendingReceivableBillsbyallCorptras() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        List<Object[]> list = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a ,CORPORATES c  where a.CORPORATE_ID=c.CORPORATE_ID and a.REVISION_NO In (select REVISION_NO from MAX_PAYABLE_REVISION_NO where  a.CORPORATE_ID=CORPORATE_ID) and a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY' group by a.bill_type,c.CORPORATE_NAME";
//String hql="select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where a.REVISION_NO In (select REVISION_NO from MAX_RECEIVE_REVISION_NO where a.CORPORATE_ID=CORPORATE_ID AND week_id=a.week_id AND BILL_TYPE=a.BILL_TYPE) and (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') group by a.bill_type,c.CORPORATE_NAME";           

            String hql = "select c.CORPORATE_NAME,a.bill_type,sum(a.pending_amount) from BILL_RECEIVE_CORP a inner join CORPORATES c on a.CORPORATE_ID=c.CORPORATE_ID where (a.disburse_status='NOT PAID' OR a.disburse_status='PARTIALLY') and (lower(a.BILL_TYPE)='trasm' or lower(a.BILL_TYPE)='trass' or lower(a.BILL_TYPE)='trase' ) group by a.bill_type,c.CORPORATE_NAME UNION select c.CORPORATE_NAME, r.bill_type, sum(r.pending_amount) from BILL_PAYABLE_CORP r inner join CORPORATES c on r.CORPORATE_ID=c.CORPORATE_ID where r.bill_status='REFUND' and (lower(r.BILL_TYPE)='trasm' or lower(r.BILL_TYPE)='trass' or lower(r.BILL_TYPE)='trase') group by r.bill_type, c.CORPORATE_NAME ";

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

            e.printStackTrace();

        }

        return list;

    }

//    public int getUpdateBillPayableCorpbyChecker(int uniqueid, BigDecimal paidamount, BigDecimal pendingamount, String billStatus) {
//
//        int result = 0;
//
//        Session session = null;
//
//        Transaction tx = null;
//
//        try {
//
//            session = HibernateUtil.getInstance().getSession();
//
//            tx = session.beginTransaction();
//
//            String hql = "update BILL_PAYABLE_CORP set PAID_AMOUNT=" + paidamount + ",PENDING_AMOUNT=" + pendingamount + ", BILL_STATUS='" + billStatus + "' where UNIQUE_ID=" + uniqueid + "";
//
//            System.out.println("hql is " + hql);
//
//            SQLQuery query = session.createSQLQuery(hql);
//
//            result = query.executeUpdate();
//
//            tx.commit();
//
//            session.flush();
//
//            session.close();
//
//            return result;
//
//        } catch (Exception e) {
//
//            if (session != null) {
//
//                session.close();
//
//            }
//
//            e.printStackTrace();
//
//        }
//
//        return result;
//
//    }
//
//    public int getUpdateBillPayableCorpbyChecker1(int uniqueid, BigDecimal paidamount, BigDecimal pendingamount, String billStatus) {
//
//        int result = 0;
//
//        Session session = null;
//
//        Transaction tx = null;
//
//        try {
//
//            session = HibernateUtil.getInstance().getSession();
//
//            tx = session.beginTransaction();
//
//            String hql = "update BILL_PAYABLE_CORP set PAID_AMOUNT=" + paidamount + ",PENDING_AMOUNT=" + pendingamount + ", REVISEDPAYBALE=" + pendingamount + ", BILL_STATUS='" + billStatus + "' where UNIQUE_ID=" + uniqueid + "";
//
//            System.out.println("hql is " + hql);
//
//            SQLQuery query = session.createSQLQuery(hql);
//
//            result = query.executeUpdate();
//
//            tx.commit();
//
//            session.flush();
//
//            session.close();
//
//            return result;
//
//        } catch (Exception e) {
//
//            if (session != null) {
//
//                session.close();
//
//            }
//
//            e.printStackTrace();
//
//        }
//
//        return result;
//
//    }
    public List<BillPayableCorp> getAllRefundReceivableBillCorplist() {

        Session session = null;

        List<BillPayableCorp> queryList = null;

        List<BillPayableCorp> queryList1 = null;

        List<Integer> list1 = new ArrayList();

        int corpid = 0;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query1 = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.pendingAmount !='0' ");

            queryList1 = query1.list();

            for (BillPayableCorp e : queryList1) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList1;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public List<BillPayableCorp> getAllRefundReceivableBillCorplistdsm() {

        Session session = null;

        List<BillPayableCorp> queryList = null;

        List<BillPayableCorp> queryList1 = null;

        List<Integer> list1 = new ArrayList();

        int corpid = 0;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query1 = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.pendingAmount !='0' and (lower(e.billType)='dsm' or lower(e.billType)='rras'  or lower(e.billType)='agc')");

            queryList1 = query1.list();

            for (BillPayableCorp e : queryList1) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList1;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
     public List<BillPayableCorp> getAllRefundReceivableBillCorplistsras() {

        Session session = null;

        List<BillPayableCorp> queryList = null;

        List<BillPayableCorp> queryList1 = null;

        List<Integer> list1 = new ArrayList();

        int corpid = 0;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query1 = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.pendingAmount !='0' and (lower(e.billType)='sras')");

            queryList1 = query1.list();

            for (BillPayableCorp e : queryList1) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList1;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
     
     public List<BillPayableCorp> getAllRefundReceivableBillCorplisttras() {

        Session session = null;

        List<BillPayableCorp> queryList = null;

        List<BillPayableCorp> queryList1 = null;

        List<Integer> list1 = new ArrayList();

        int corpid = 0;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query1 = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.pendingAmount !='0' and (lower(e.billType)='trasm' or lower(e.billType)='trass' or lower(e.billType)='trase')");

            queryList1 = query1.list();

            for (BillPayableCorp e : queryList1) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList1;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BillPayableCorp> getRefundReceivableBillbyCorp(int corpid) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//             Query query = session.createQuery("from BillPayableCorp s where s.billStatus='REFUND' and s.corporates.corporateId=:corpid and s.adjustmentFlag=0 and s.revisionNo=(select max(e.revisionNo) from BillPayableCorp e where e.corporates.corporateId=s.corporates.corporateId and e.weekId=s.weekId and e.billType=s.billType group by e.corporates.corporateId,e.weekId) ");         
            Query query = session.createQuery("from BillPayableCorp s where s.billStatus='REFUND' and s.corporates.corporateId=:corpid and s.adjustmentFlag=0 ");

            query.setParameter("corpid", corpid);

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

            e.printStackTrace();

            return null;

        } finally {
            session.close();
        }

    }

    public int getUpdateRefundBillPayableCorpbyChecker(int uniqueid, BigDecimal paidamount) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BILL_PAYABLE_CORP set ADJUSTMENT_AMOUNT=" + paidamount + ",ADJUSTMENT_FLAG=" + 1 + " where UNIQUE_ID=" + uniqueid + "";

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

    public int getUpdateBillPayableCorpbyCheckeronDelete(int uniqueid) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BILL_PAYABLE_CORP set ADJUSTMENT_FLAG='0',ADJUSTMENT_AMOUNT=0.0 where UNIQUE_ID=" + uniqueid + "";

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

    public List<Object[]> getAllRefundReceivableBillCorpObjectlist() {

        Session session = null;

        List<Object[]> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from BILL_PAYABLE_CORP s , corporates c  where s.BILL_STATUS='REFUND' and s.corporate_id = c.corporate_id and adjustment_flag='0' and s.revision_no = (select max(revision_no) from MAX_PAYABLE_REVISION_NO where s.corporate_id=corporate_id and s.week_id=week_id and s.bill_type=bill_type)");       
            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from BILL_PAYABLE_CORP s , corporates c  where s.BILL_STATUS='REFUND' and s.corporate_id = c.corporate_id and adjustment_flag='0' ");

            queryList = query.list();

            return queryList;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BillPayableCorp> BillPayableCorplistMonthly(BigDecimal week_id1, BigDecimal week_id2, String billType, BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billType=:billType order by billDueDate");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BillPayableCorp> getBillPayableCorpDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, String billType, BigDecimal yearId, int corpid) {
        List<BillPayableCorp> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = " from BillPayableCorp s where s.weekId=:weekId and s.billYear=:yearId and s.revisionNo =:revisionNo and s.billType=:billType and s.corporates.corporateId=:corpid";

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

    public BigDecimal getTotalPaidAmtByCorpWeekYearRevNo(BigDecimal weekId, BigDecimal year, int Corporate, String billType) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select SUM(PAID_AMOUNT) from BILL_PAYABLE_CORP where WEEK_ID='" + weekId + "' and BILL_YEAR='" + year + "' and CORPORATE_ID='" + Corporate + "'  and BILL_TYPE='" + billType + "' ";
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

    public int getUpdatePrevBillPayableCorponUpload(int uniqueid, BigDecimal pendingamount, String billStatus) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BILL_PAYABLE_CORP set PENDING_AMOUNT=" + pendingamount + ", BILL_STATUS='" + billStatus + "' where UNIQUE_ID=" + uniqueid + "";
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

    public BigDecimal getUpdatePaidAmtbyCorpWeekYearRevNoOnupload(BigDecimal weekId, BigDecimal year, int Corporate, String billType, int revNo) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BILL_PAYABLE_CORP set  BILL_STATUS='PAID' where WEEK_ID='" + weekId + "' and BILL_YEAR='" + year + "' and REVISION_NO='" + revNo + "' and CORPORATE_ID='" + Corporate + "'  and BILL_TYPE='" + billType + "' ";

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

    public boolean deleteBillPayableCorpbyWeekIdYearRevNoBillType(BigDecimal weekId, BigDecimal yearId, BigDecimal revisionNo, String billType) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from BillPayableCorp where weekId=:weekId and billYear=:yearId and revisionNo=:revisionNo and billType=:billType ";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("yearId", yearId);
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

    public List<BillPayableCorp> getAllBillPaybyCorp(int corpid) {

        List<BillPayableCorp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "from BillPayableCorp s where  s.corporates.corporateId =:corpid  order by s.uniqueId ASC ";

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

            e.printStackTrace();

        }

        return list;

    }

    public List<Object[]> getLatestBillsPayableAllTypes() {

        List<Object[]> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

//            String hql="select bill_type,week_id,sum(total),bill_due_date from max_pending_bills_payable s where week_id=(select max(week_id) from max_pending_bills_payable where s.bill_type=bill_type ) group by week_id,bill_type,bill_due_date";
            String hql = "select s.BILL_TYPE,s.WEEK_ID,sum(s.PENDING_AMOUNT),s.BILL_DUE_DATE from BILL_PAYABLE_CORP s where (s.BILL_STATUS='NOT PAID' OR s.BILL_STATUS='PARTIALLY') and s.PENDING_AMOUNT>=100 group by s.WEEK_ID,s.BILL_TYPE,s.BILL_DUE_DATE";

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

//public List<BillPayableCorp> getPendingRefundPayablelist() {
//
// 
//
//        Session session = null;
//
//        List<BillPayableCorp> queryList1=null;  
//
// 
//
//     
//
//        try {
//
// 
//
//            session = HibernateUtil.getInstance().getSession();
//
// 
//
//            Query query1 = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.adjustmentFlag='0' ");        
//
// 
//
//            queryList1=query1.list();
//
// 
//
//            for (BillPayableCorp e : queryList1) {
//
// 
//
//                Hibernate.initialize(e.getCorporates());
//
// 
//
//            }
//
// 
//
//            return queryList1;         
//
// 
//
//        } catch (Exception e) {
//
// 
//
//            e.printStackTrace();
//
// 
//
//            return null;
//
// 
//
//        } finally {
//
// 
//
//            session.close();
//
// 
//
//        }
//
// 
//
//    }
    public BigDecimal getNetByCorpWeekYearRevNo(BigDecimal weekId, BigDecimal year, int Corporate, BigDecimal revNo, String billType) {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(TOTALNET,0) from BILL_PAYABLE_CORP where WEEK_ID='" + weekId + "' and BILL_YEAR='" + year + "' and CORPORATE_ID='" + Corporate + "' and REVISION_NO='" + revNo + "' and BILL_TYPE='" + billType + "' ";
            //String hql="select PAID_AMOUNT from BILL_PAYABLE_CORP where WEEK_ID='"+weekId+"' and BILL_YEAR='"+year+"' and CORPORATE_ID='"+Corporate+"' and REVISION_NO='"+revNo+"'"; 

            System.out.println("####### getnetAmtByCorpWeekYearRevNo hql is " + hql);
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

    public List<BillPayableCorp> getPendingRefundPayablelist() {

        Session session = null;

        List<BillPayableCorp> queryList1 = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            Query query1 = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.adjustmentFlag='0' ");        
            Query query1 = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.pendingAmount != '0' ");

            queryList1 = query1.list();

            for (BillPayableCorp e : queryList1) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList1;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getAllPendingPayableCorpList(BigDecimal corpId) {

        Session session = null;

        List<Object[]> list = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            String hql1 = "select unique_id,BILL_TYPE,BILL_CATEGORY,BILLING_DATE,BILL_DUE_DATE,TOTALNET,PENDING_AMOUNT,corporate_id,week_id,a.REVISION_NO from BILL_PAYABLE_CORP a  where a.corporate_id='" + corpId + "' and (a.BILL_STATUS='NOT PAID' OR a.BILL_STATUS='PARTIALLY') ";
            String hql1 = "select unique_id,BILL_TYPE,BILL_CATEGORY,BILLING_DATE,BILL_DUE_DATE,TOTALNET,PENDING_AMOUNT,corporate_id,week_id,a.REVISION_NO from BILL_PAYABLE_CORP a  where a.corporate_id='" + corpId + "' and (a.BILL_STATUS='NOT PAID' OR a.BILL_STATUS='PARTIALLY') and a.PENDING_AMOUNT >=1 ";

            //String hql1 = "select unique_id,BILL_TYPE,BILL_CATEGORY,BILLING_DATE,BILL_DUE_DATE,TOTALNET,PENDING_AMOUNT,corporate_id,week_id,a.REVISION_NO from BILL_PAYABLE_CORP a  where a.corporate_id='" + corpId + "' and revision_no = (select b.revision_no from MAX_PAYABLE_REVISION_NO b where b.CORPORATE_ID = a.CORPORATE_ID  and a.WEEK_ID=b.WEEK_ID and b.BILL_TYPE='" + BILLTYPE + "') and (a.BILL_STATUS='NOT PAID' OR a.BILL_STATUS='PARTIALLY') AND a.BILL_TYPE='" + BILLTYPE + "' ";
            SQLQuery q = session.createSQLQuery(hql1);

            System.out.print("HDQL1" + hql1);

            list = q.list();

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

    public int getUpdateRefundBillPayableCorpbyChecker(int uniqueid, BigDecimal paidamount, BigDecimal pendingamount) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BILL_PAYABLE_CORP set ADJUSTMENT_AMOUNT=" + paidamount + ",PENDING_AMOUNT=" + pendingamount + " where UNIQUE_ID=" + uniqueid + "";

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

    public List<Date> getAllRefundDisbursementbyCorpgroupbyBillingDateTimestamp() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_PAYABLE_CORP s where (BILL_STATUS='REFUND') and PENDING_AMOUNT !=0 order by TRUNC(s.billing_date) ASC";

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
    
    public List<Date> getAllRefundDisbursementbyCorpgroupbyBillingDateTimestampdsm() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_PAYABLE_CORP s where (BILL_STATUS='REFUND') and PENDING_AMOUNT !=0 and (lower(s.BILL_TYPE)='dsm' or lower(s.BILL_TYPE)='rras'  or lower(s.BILL_TYPE)='agc') order by TRUNC(s.billing_date) ASC";

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
    
    public List<Date> getAllRefundDisbursementbyCorpgroupbyBillingDateTimestampsras() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_PAYABLE_CORP s where (BILL_STATUS='REFUND') and PENDING_AMOUNT !=0 and (lower(s.BILL_TYPE)='sras' ) order by TRUNC(s.billing_date) ASC";

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
    
    public List<Date> getAllRefundDisbursementbyCorpgroupbyBillingDateTimestamptras() {

        List<Date> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //             String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and s.BILL_YEAR=BILL_YEAR and bill_type='" + billtype + "' ) order by TRUNC(s.billing_date) ASC";
            String hql = "select distinct(TRUNC(billing_date)) from BILL_PAYABLE_CORP s where (BILL_STATUS='REFUND') and PENDING_AMOUNT !=0 and (lower(s.BILL_TYPE)='trasm' or lower(s.BILL_TYPE)='trass' or lower(s.BILL_TYPE)='trase') order by TRUNC(s.billing_date) ASC";

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

    public List<BigDecimal> getRefundPendingDisbursementbyCorpgroupbyWeekID() {

        List<BigDecimal> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_PAYABLE_CORP where BILL_STATUS='REFUND' and PENDING_AMOUNT !=0  order by week_id ASC ";

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
    
    public List<BigDecimal> getRefundPendingDisbursementbyCorpgroupbyWeekIDdsm() {

        List<BigDecimal> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_PAYABLE_CORP where BILL_STATUS='REFUND' and PENDING_AMOUNT !=0 and (lower(BILL_TYPE)='dsm' or lower(BILL_TYPE)='rras'  or lower(BILL_TYPE)='agc') order by week_id ASC ";

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
    
    public List<BigDecimal> getRefundPendingDisbursementbyCorpgroupbyWeekIDsras() {

        List<BigDecimal> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_PAYABLE_CORP where BILL_STATUS='REFUND' and PENDING_AMOUNT !=0 and (lower(BILL_TYPE)='sras') order by week_id ASC ";

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
    
    public List<BigDecimal> getRefundPendingDisbursementbyCorpgroupbyWeekIDtras() {

        List<BigDecimal> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            // String hql="from BillReceiveCorp e where e.billingDate IN (select distinct trunc(m.billingDate) from BillReceiveCorp m  WHERE (m.disburseStatus='NOT PAID' OR m.disburseStatus='PARTIALLY')  )";
            String hql = "select distinct week_id from BILL_PAYABLE_CORP where BILL_STATUS='REFUND' and PENDING_AMOUNT !=0 and (lower(BILL_TYPE)='trasm' or lower(BILL_TYPE)='trass' or lower(BILL_TYPE)='trase') order by week_id ASC ";

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

    public List<Timestamp> getBillPayablebyCorpgroupbyBillingDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(s.billing_date)) from BILL_PAYABLE_CORP s, CORPORATES c  where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "' and extract(YEAR from s.billing_date)='" + year + "' order by TRUNC(s.billing_date) ASC";

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

    public List<BillPayableCorp> getReconPendingBillCorpNameList(int corpid, String year) {
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.corporates.corporateId=:corpid and to_char(s.billingDate,'YYYY')=:year order by s.billingDate, s.revisionNo ");

            query.setParameter("corpid", corpid);
            query.setParameter("year", year);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }
    }

    public List<Timestamp> getRefundBillPayablebyCorpgroupbyBillingDateTimestamp(int corpid) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(s.billing_date)) from BILL_PAYABLE_CORP s, CORPORATES c where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "' and s.BILL_STATUS='REFUND' and s.ADJUSTMENT_AMOUNT=0 order by TRUNC(s.billing_date) ASC";

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

    public List<BillPayableCorp> getReconrefundPendingBillCorpNameList(int corpid) {
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.corporates.corporateId=:corpid and s.billStatus='REFUND' and s.adjustmentAmount=0 order by s.billingDate ");

            query.setParameter("corpid", corpid);
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

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }
    }

}
