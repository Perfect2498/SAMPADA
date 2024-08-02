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
import sampada.pojo.MappingBillBank;
import sampada.pojo.MappingInterestBank;

import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class MappingBillBankDAO {

    public boolean NewMappingBillBank(MappingBillBank dic) {

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

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM MAPPING_BILL_BANK ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                result = bg.intValueExact();
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

    public List<MappingBillBank> getMappingBillBankDetailsbyCorpID(int corpid, String year) {

        Session session = null;

        int result = 0;

        List<MappingBillBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingBillBank s where s.corporates.corporateId=:corpid and s.checkerStatus='Verified'  and to_char(s.entryDate,'YYYY')=:year order by s.uniqueId ASC";
            System.out.println("hql  is " + hql);
            Query query = session.createQuery(hql);

            query.setParameter("corpid", corpid);
            query.setParameter("year", year);

            list = query.list();
            System.out.println("hql size is " + list.size());
            for (MappingBillBank e : list) {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillPayableCorp());

                Hibernate.initialize(e.getBankStatement());

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

    public List<MappingBillBank> getMappingBillBankbyentrydatesforsrasandtras(Date amtFrmDate, Date amtToDate) {
        Session session = null;
        int result = 0;
        List<MappingBillBank> list = null;
        BigDecimal bg = null;
        try {

            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingInterestBank s where  TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.interestDetails.billType=:type and s.checkerStatus='Verified' order by s.slno ASC";
            String hql = "from  MappingBillBank s where  TO_DATE(s.entryDate) between :amtFrmDate and :amtToDate  and s.checkerStatus='Verified' and (s.billType='SRAS' or s.billType='TRASM' or s.billType='TRASS' or s.billType='TRASE') order by s.uniqueId ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
//            query.setParameter("type", type);

            list = query.list();

            for (MappingBillBank e : list) {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillPayableCorp());

                Hibernate.initialize(e.getBankStatement());
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

    public List<MappingBillBank> getMappingBillBankDetailsbydatestype(Date amtFrmDate, Date amtToDate, String type) {

        Session session = null;

        int result = 0;

        List<MappingBillBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingBillBank s where TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.checkerStatus='Verified' and s.billType=:type order by s.uniqueId ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            query.setParameter("type", type);
            list = query.list();

            for (MappingBillBank e : list) {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillPayableCorp());

                Hibernate.initialize(e.getBankStatement());

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

    public List<MappingBillBank> getMappingBillBankDetailsbydates(Date amtFrmDate, Date amtToDate) {

        Session session = null;

        int result = 0;

        List<MappingBillBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingBillBank s where TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.checkerStatus='Verified' and s.billType=:type order by s.uniqueId ASC";
            String hql = "from  MappingBillBank s where TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.checkerStatus='Verified'  order by s.bankStatement.stmtId ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
//            query.setParameter("type", type);
            list = query.list();

            for (MappingBillBank e : list) {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillPayableCorp());

                Hibernate.initialize(e.getBankStatement());

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

    public List<MappingBillBank> getMappingBillBankDetailsbybankids(BigDecimal fromid, BigDecimal toid) {

        Session session = null;

        int result = 0;

        List<MappingBillBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingBillBank s where TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.checkerStatus='Verified' and s.billType=:type order by s.uniqueId ASC";
            String hql = "from  MappingBillBank s where s.bankStatement.stmtId between :fromid and :toid and s.checkerStatus='Verified' and (s.poolBal is null)  order by s.bankStatement.stmtId ASC";

            Query query = session.createQuery(hql);

            query.setParameter("fromid", fromid);
            query.setParameter("toid", toid);
//            query.setParameter("type", type);
            list = query.list();

            for (MappingBillBank e : list) {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillPayableCorp());

                Hibernate.initialize(e.getBankStatement());

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
    
    
    public List<MappingBillBank> getMappingBillBankDetailsbyids(BigDecimal fromid, BigDecimal toid) {

        Session session = null;

        int result = 0;

        List<MappingBillBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingBillBank s where TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.checkerStatus='Verified' and s.billType=:type order by s.uniqueId ASC";
            String hql = "from  MappingBillBank s where s.uniqueId between :fromid and :toid and s.checkerStatus='Verified' and (s.poolBal is not null)   order by s.uniqueId ASC";

            Query query = session.createQuery(hql);

            query.setParameter("fromid", fromid);
            query.setParameter("toid", toid);
//            query.setParameter("type", type);
            list = query.list();

            for (MappingBillBank e : list) {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillPayableCorp());

                Hibernate.initialize(e.getBankStatement());

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

    public List<Timestamp> getMappingBillBankbyCorpgroupbyEntryDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();
            String hql = "select distinct(TRUNC(s.entry_date)) from MAPPING_BILL_BANK s, CORPORATES c  where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "' and s.CHECKER_STATUS='Verified' and extract(YEAR from s.entry_date)='" + year + "' order by TRUNC(s.entry_date) ASC";

//            String hql = "select distinct(TRUNC(s.entry_date)) from MAPPING_BILL_BANK s, CORPORATES c  where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='"+corpid+"' and s.CHECKER_STATUS='Verified' order by TRUNC(s.entry_date) ASC"; 
            //          String hql = "select distinct(TRUNC(s.entry_date)) from MAPPING_BILL_BANK s, CORPORATES c  where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='"+corpid+"' and s.CHECKER_STATUS='Verified' order by TRUNC(s.entry_date) ASC";
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

    public List<Object[]> getcountofbillpayidinmap(int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            String sql;

            sql = "select UNIQUE_BILLPAY,count(UNIQUE_BILLPAY)\n"
                    + "from MAPPING_BILL_BANK where CORPORATE_ID='" + corpid + "'\n"
                    + "group by UNIQUE_BILLPAY\n"
                    + " HAVING COUNT(UNIQUE_BILLPAY)>1";

            System.out.println("hql is " + sql);

// Query query = session.createQuery(hql);
            SQLQuery query = session.createSQLQuery(sql);

            List<Object[]> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                System.out.println("list size" + queryList.size());
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

    public List<Object[]> getcountofbillrecidinmap(int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            String sql;

            sql = "select UNIQUE_ID,count(UNIQUE_ID)\n"
                    + "from PAYMENT_DISBURSEMENT where CORPORATE_ID='" + corpid + "'\n"
                    + "group by UNIQUE_ID\n"
                    + " HAVING COUNT(UNIQUE_ID)>1";

            System.out.println("hql is " + sql);

// Query query = session.createQuery(hql);
            SQLQuery query = session.createSQLQuery(sql);

            List<Object[]> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                System.out.println("list size" + queryList.size());
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

    public List<BigDecimal> getuniqueidfromuniquebillpayid(BigDecimal billpayid, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            String sql;

            sql = "select UNIQUE_ID from MAPPING_BILL_BANK where UNIQUE_BILLPAY='" + billpayid + "' and CORPORATE_ID='" + corpid + "' order by UNIQUE_ID";

            System.out.println("hql is " + sql);

// Query query = session.createQuery(hql);
            SQLQuery query = session.createSQLQuery(sql);

            List<BigDecimal> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                System.out.println("list size" + queryList.size());
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

    public List<BigDecimal> getdisburseidfromuniquebillrecid(BigDecimal billpayid, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            String sql;

            sql = "select DISBURSE_ID from PAYMENT_DISBURSEMENT where UNIQUE_ID='" + billpayid + "' and CORPORATE_ID='" + corpid + "' order by DISBURSE_ID";

            System.out.println("hql is " + sql);

// Query query = session.createQuery(hql);
            SQLQuery query = session.createSQLQuery(sql);

            List<BigDecimal> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                System.out.println("list size" + queryList.size());
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

}
