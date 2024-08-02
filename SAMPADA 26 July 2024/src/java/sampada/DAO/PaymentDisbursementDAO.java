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
import sampada.pojo.BankStatement;
import sampada.pojo.PaymentDisbursement;
import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class PaymentDisbursementDAO {

    public boolean NewPaymentDisbursement(PaymentDisbursement usr) {

        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(usr);

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

    public int getMaxUDISBURSE_ID() {

        int result = 0;
        BigDecimal temp = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(DISBURSE_ID) FROM PAYMENT_DISBURSEMENT ";
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

    public List<PaymentDisbursement> getDisbursementDetailsbyStatus(String status) {
        List<PaymentDisbursement> list = null;
        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from PaymentDisbursement s where s.checkerStatus=:status  ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            list = query.list();
            for (PaymentDisbursement e : list) {
                Hibernate.initialize(e.getCorporates());
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

    public List<PaymentDisbursement> getDisbursementDetailsforInterest() {
        List<PaymentDisbursement> list = null;
        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from PaymentDisbursement s where s.checkerStatus='Confirm' and (s.disburseStatus='PAID' OR s.disburseStatus='Partially')  ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);

            list = query.list();
            for (PaymentDisbursement e : list) {
                Hibernate.initialize(e.getCorporates());
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

    public BigDecimal getMaxWeekidPD(int year) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "select NVL(max(WEEK_ID),0) from PAYMENT_DISBURSEMENT where DISBURSE_YEAR='" + year + "'";
            String hql = "select NVL(max(b.WEEK_ID),0) from PAYMENT_DISBURSEMENT b INNER JOIN BILL_RECEIVE_CORP c \n"
                    + "ON b.UNIQUE_ID=c.UNIQUE_ID where b.DISBURSE_AMOUNT!=0 and c.BILL_YEAR='" + year + "'";

            System.out.println("hql is " + hql);
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

    public BigDecimal getmaxpoolbal(int fromid, int toid) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select POOL_BAL+DISBURSE_AMOUNT from PAYMENT_DISBURSEMENT where  POOL_BAL=(select MAX(POOL_BAL) from PAYMENT_DISBURSEMENT where DISBURSE_ID BETWEEN '" + fromid + "' AND '" + toid + "' and  DISBURSE_AMOUNT!=0 and CHECKER_STATUS= 'Confirm' )";

            System.out.println("hql is " + hql);
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

    public BigDecimal getmaxpoolopenbalfornotesheet(int fromid, int toid, int fromref, int toref, int frompsdf, int topsdf, int frommisc, int tomisc) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select max(bef)\n"
                    + "from(\n"
                    + "select (p.POOL_BAL+p.DISBURSE_AMOUNT) as  bef\n"
                    + "from PAYMENT_DISBURSEMENT p\n"
                    + "where  p.DISBURSE_ID='" + fromid + "'\n"
                    + "union\n"
                    + "select (r.POOL_BAL+r.PAID_AMOUNT)  as  bef from TEMP_REFUND_BILL_CORP r\n"
                    + "where  r.SLNO='" + fromref + "'\n"
                    + "union\n"
                    + "select (c.MAIN_POOL_BALANCE) as bef from CSDF_DETAILS c\n"
                    + "where  c.AMT_CATEGORY='Bills' and c.SLNO='" + frompsdf + "'\n"
                    + "union\n"
                    + "select (m.MAIN_POOL_BALANCE) as bef from MISC_DISBURSEMENT m\n"
                    + "where  m.AMT_CATEGORY='Bills' and m.UNIQUE_ID='" + frommisc + "'\n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getmaxINTpoolopenbalfornotesheet(int fromint, int toint, int frompsdf, int topsdf, int frommisc, int tomisc) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select max(bef)\n"
                    + "from(\n"
                    + "select (p.INT_POOL_BAL+p.INTEREST_PAIDAMOUNT) as  bef\n"
                    + "from PAYMENT_INTEREST_DISBURSEMENT p\n"
                    + "where  p.SLNO='" + fromint + "'\n"
                    + "union\n"
                    + "select (c.MAIN_POOL_BALANCE) as bef from CSDF_DETAILS c\n"
                    + "where c.AMT_CATEGORY='Interest' and c.SLNO='" + frompsdf + "'\n"
                    + "union\n"
                    + "select (m.MAIN_POOL_BALANCE) as bef from MISC_DISBURSEMENT m\n"
                    + "where m.AMT_CATEGORY='Interest' and m.UNIQUE_ID='" + frommisc + "'\n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getminINTpoolopenbalfornotesheet(int fromint, int toint, int frompsdf, int topsdf, int frommisc, int tomisc) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select min(bef)\n"
                    + "from(\n"
                    + "select (p.INT_POOL_BAL) as  bef\n"
                    + "from PAYMENT_INTEREST_DISBURSEMENT p\n"
                    + "where  p.SLNO='" + toint + "'\n"
                    + "union\n"
                    + "select (c.MAIN_POOL_BALANCE-c.CSDF_AMOUNT) as bef from CSDF_DETAILS c\n"
                    + "where c.AMT_CATEGORY='Interest' and c.SLNO='" + topsdf + "'\n"
                    + "union\n"
                    + "select (m.MAIN_POOL_BALANCE-m.REFUND_AMT) as bef from MISC_DISBURSEMENT m\n"
                    + "where m.AMT_CATEGORY='Interest' and m.UNIQUE_ID='" + tomisc + "'\n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getmaxpoolopenbalfornotesheetonlyINT(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from PAYMENT_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and DISBURSE_AMOUNT !=0 and CHECKER_STATUS='Confirm' and (POOL_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from TEMP_REFUND_BILL_CORP where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and PAID_AMOUNT !=0 and CHECKER_STATUS= 'Verified' and BILLPAY_UNIQUEID IS NOT NULL and (POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Bills' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from BANK_STATEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CREDIT_DEBIT_FLAG='CR' and STMT_STATUS='Verified' and (POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Confirmed' and (POOL_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from MAPPING_BILL_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from MAPPING_REFUND_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (MAIN_POOL_BALANCE is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE,0) as  bef,ENTRY_TIME from POOL_TO_POOL where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (MAIN_POOL_BALANCE is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-REFUND_AMT,0) as  bef,ENTRY_TIME from MISC_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and AMT_CATEGORY='Bills' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getmaxpoolopenbalfornotesheetonlyINTsras(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from PAYMENT_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and DISBURSE_AMOUNT !=0 and CHECKER_STATUS='Confirm' and (POOL_AGC_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from TEMP_REFUND_BILL_CORP where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and PAID_AMOUNT !=0 and CHECKER_STATUS= 'Verified' and BILLPAY_UNIQUEID IS NOT NULL and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Bills' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Confirmed' and (POOL_AGC_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_BILL_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_AGC_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_REFUND_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_AGC_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from POOL_TO_POOL where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getmaxpoolopenbalfornotesheetonlyINTtras(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from PAYMENT_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and DISBURSE_AMOUNT !=0 and CHECKER_STATUS='Confirm' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from TEMP_REFUND_BILL_CORP where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and PAID_AMOUNT !=0 and CHECKER_STATUS= 'Verified' and BILLPAY_UNIQUEID IS NOT NULL  and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Bills' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Confirmed' and (POOL_RRAS_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_BILL_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_REFUND_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_RRAS_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from POOL_TO_POOL where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getmaxpoolopenbalfornotesheetonlyINTsrasAfter(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from PAYMENT_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and DISBURSE_AMOUNT !=0 and CHECKER_STATUS='Confirm' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from TEMP_REFUND_BILL_CORP where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and PAID_AMOUNT !=0 and CHECKER_STATUS= 'Verified' and BILLPAY_UNIQUEID IS NOT NULL  and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Bills' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Confirmed' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_BILL_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_AGC_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_REFUND_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL,0) as  bef,ENTRY_TIME from POOL_TO_POOL where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getmaxpoolopenbalfornotesheetonlyINTtrasAfter(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from PAYMENT_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and DISBURSE_AMOUNT !=0 and CHECKER_STATUS='Confirm' and (POOL_RRAS_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from TEMP_REFUND_BILL_CORP where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and PAID_AMOUNT !=0 and CHECKER_STATUS= 'Verified' and BILLPAY_UNIQUEID IS NOT NULL and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Bills' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Confirmed' and (POOL_RRAS_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_BILL_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_REFUND_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_RRAS_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL,0) as  bef,ENTRY_TIME from POOL_TO_POOL where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getmaxpoolopenbalfornotesheetonlyINTAFTER(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from PAYMENT_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and DISBURSE_AMOUNT !=0 and CHECKER_STATUS='Confirm' and (POOL_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from TEMP_REFUND_BILL_CORP where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and PAID_AMOUNT !=0 and CHECKER_STATUS= 'Verified' and BILLPAY_UNIQUEID IS NOT NULL and (POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Bills' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from BANK_STATEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CREDIT_DEBIT_FLAG='CR' and STMT_STATUS='Verified' and (POOL_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Confirmed' and (POOL_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from MAPPING_BILL_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_BAL is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_BAL,0) as  bef,ENTRY_TIME from MAPPING_REFUND_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and (POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE,0) as  bef,ENTRY_TIME from POOL_TO_POOL where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-REFUND_AMT,0) as  bef,ENTRY_TIME from MISC_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and AMT_CATEGORY='Bills' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getinterestbalonlyBILLSbyentrytime(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_POOL_BAL,0) as  bef,ENTRY_TIME from PAYMENT_INTEREST_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and INTEREST_PAIDAMOUNT!=0 and CHECKER_STATUS= 'Confirmed' and (INT_POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(AFTER_INT_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS= 'Confirmed' and (AFTER_INT_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Interest'  and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_POOL_BALANCE,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (INT_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-REFUND_AMT,0) as  bef,ENTRY_TIME from MISC_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and AMT_CATEGORY='Interest' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getinterestbalonlyBILLSbyentrytimesras(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_AGC_BAL,0) as  bef,ENTRY_TIME from PAYMENT_INTEREST_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and INTEREST_PAIDAMOUNT!=0 and CHECKER_STATUS= 'Confirmed' and (INT_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS= 'Confirmed' and (INT_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Interest' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_AGC_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (INT_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getinterestbalonlyBILLSbyentrytimetras(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_RRAS_BAL,0) as  bef,ENTRY_TIME from PAYMENT_INTEREST_DISBURSEMENT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and INTEREST_PAIDAMOUNT!=0 and CHECKER_STATUS= 'Confirmed' and (INT_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS= 'Confirmed' and (INT_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Interest' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_RRAS_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <'" + entrytime + "'\n"
                    + "and STATUS='Checked' and (INT_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getinterestbalonlyBILLSbyentrytimetrasAfter(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_RRAS_BAL,0) as  bef,ENTRY_TIME from PAYMENT_INTEREST_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and INTEREST_PAIDAMOUNT!=0 and CHECKER_STATUS= 'Confirmed' and (INT_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_RRAS_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS= 'Confirmed' and (INT_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_RRAS_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Interest' and (POOL_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_RRAS_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (INT_RRAS_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getinterestbalonlyBILLSbyentrytimesrasAfter(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_AGC_BAL,0) as  bef,ENTRY_TIME from PAYMENT_INTEREST_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and INTEREST_PAIDAMOUNT!=0 and CHECKER_STATUS= 'Confirmed' and (INT_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_AGC_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS= 'Confirmed' and (INT_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(POOL_AGC_BAL-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Interest' and (POOL_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_AGC_BAL,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (INT_AGC_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getinterestbalonlyBILLSbyentrytimeAFTER(String entrytime) {
        BigDecimal result = BigDecimal.ZERO;;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select bef from(select bef from(\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_POOL_BAL,0) as  bef,ENTRY_TIME from PAYMENT_INTEREST_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and INTEREST_PAIDAMOUNT!=0 and CHECKER_STATUS= 'Confirmed' and (INT_POOL_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(AFTER_INT_BAL,0) as  bef,ENTRY_TIME from MAPPING_INTEREST_BANK where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS= 'Confirmed' and (AFTER_INT_BAL is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-CSDF_AMOUNT,0) as  bef,ENTRY_TIME from CSDF_DETAILS where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and CHECKER_STATUS='Verified' and AMT_CATEGORY='Interest' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(INT_POOL_BALANCE,0) as  bef,ENTRY_TIME from POOL_TO_INT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and (INT_POOL_BALANCE is not null)  order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1\n"
                    + "union\n"
                    + "select bef,ENTRY_TIME from(select NVL(MAIN_POOL_BALANCE-REFUND_AMT,0) as  bef,ENTRY_TIME from MISC_DISBURSEMENT where ENTRY_TIME <='" + entrytime + "'\n"
                    + "and STATUS='Checked' and AMT_CATEGORY='Interest' and (MAIN_POOL_BALANCE is not null) order by ENTRY_TIME desc)\n"
                    + "where ROWNUM = 1)\n"
                    + "order by ENTRY_TIME desc)where ROWNUM = 1";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public Timestamp getentrytimeforonlybills(int fromid, int toid, int fromref, int toref, int frompsdf, int topsdf, int frommisc, int tomisc) {
        Timestamp result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select max(time) from(\n"
                    + "select MAX(p.ENTRY_TIME) AS time from PAYMENT_DISBURSEMENT p \n"
                    + "where p.DISBURSE_ID BETWEEN '" + fromid + "' AND '" + toid + "' and  p.DISBURSE_AMOUNT!=0 and p.CHECKER_STATUS= 'Confirm' and p.DISBURSE_ID NOT IN (select DISBURSE_ID from BANK_STATEMENT where DISBURSE_TYPE='Bills')\n"
                    + "union \n"
                    + "select MAX(r.ENTRY_TIME) AS time from TEMP_REFUND_BILL_CORP r \n"
                    + "where r.SLNO BETWEEN '" + fromref + "' AND '" + toref + "' and  r.PAID_AMOUNT!=0 and r.CHECKER_STATUS= 'Verified' and r.BILLPAY_UNIQUEID IS NOT NULL and r.SLNO NOT IN (select DISBURSE_ID from BANK_STATEMENT where DISBURSE_TYPE='Refund')\n"
                    + "union\n"
                    + "select MAX(c.ENTRY_TIME) AS time from CSDF_DETAILS c\n"
                    + "where c.SLNO BETWEEN '" + frompsdf + "' AND '" + topsdf + "' and c.CHECKER_STATUS='Verified' and c.AMT_CATEGORY='Bills' and c.SLNO NOT IN (select DISBURSE_ID from BANK_STATEMENT where DISBURSE_TYPE='PSDF')\n"
                    + "union\n"
                    + "select MAX(m.ENTRY_TIME) AS time from MISC_DISBURSEMENT m\n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommisc + "' AND '" + tomisc + "' and m.STATUS='Checked' and m.AMT_CATEGORY='Bills' and m.UNIQUE_ID NOT IN (select DISBURSE_ID from BANK_STATEMENT where DISBURSE_TYPE='Misc')\n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (Timestamp) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public Timestamp getentrytimebeforedisburse(int fromid, int toid, int fromref, int toref, int frompsdf, int topsdf, int frommisc, int tomisc, int fromint, int toint) {
        Timestamp result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select MIN(time) from(\n"
                    + "select MIN(p.ENTRY_TIME) AS time from PAYMENT_DISBURSEMENT p \n"
                    + "where p.DISBURSE_ID BETWEEN '" + fromid + "' AND '" + toid + "' and  p.DISBURSE_AMOUNT!=0 and p.CHECKER_STATUS= 'Confirm' \n"
                    + "union \n"
                    + "select MIN(r.ENTRY_TIME) AS time from TEMP_REFUND_BILL_CORP r \n"
                    + "where r.SLNO BETWEEN '" + fromref + "' AND '" + toref + "' and  r.PAID_AMOUNT!=0 and r.CHECKER_STATUS= 'Verified' and r.BILLPAY_UNIQUEID IS NOT NULL \n"
                    + "union\n"
                    + "select MIN(c.ENTRY_TIME) AS time from CSDF_DETAILS c\n"
                    + "where c.SLNO BETWEEN '" + frompsdf + "' AND '" + topsdf + "' and c.CHECKER_STATUS='Verified' and c.AMT_CATEGORY='Bills' \n"
                    + "union\n"
                    + "select MIN(m.ENTRY_TIME) AS time from MISC_DISBURSEMENT m\n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommisc + "' AND '" + tomisc + "' and m.STATUS='Checked' and m.AMT_CATEGORY='Bills' \n"
                    + "union\n"
                    + "select MIN(p.ENTRY_TIME) AS time from PAYMENT_INTEREST_DISBURSEMENT p \n"
                    + "where p.SLNO BETWEEN '" + fromint + "' AND '" + toint + "' and p.CHECKER_STATUS='Confirmed' \n"
                    + "union\n"
                    + "select MIN(c.ENTRY_TIME) AS time from CSDF_DETAILS c\n"
                    + "where c.SLNO BETWEEN '" + frompsdf + "' AND '" + topsdf + "' and c.CHECKER_STATUS='Verified' and c.AMT_CATEGORY='Interest' \n"
                    + "union\n"
                    + "select MIN(m.ENTRY_TIME) AS time from MISC_DISBURSEMENT m\n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommisc + "' AND '" + tomisc + "' and m.STATUS='Checked' and m.AMT_CATEGORY='Interest' \n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (Timestamp) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public Timestamp getentrytimeafterdisburse(int fromid, int toid, int fromref, int toref, int frompsdf, int topsdf, int frommisc, int tomisc, int fromint, int toint) {
        Timestamp result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select MAX(time) from(\n"
                    + "select MAX(p.ENTRY_TIME) AS time from PAYMENT_DISBURSEMENT p \n"
                    + "where p.DISBURSE_ID BETWEEN '" + fromid + "' AND '" + toid + "' and  p.DISBURSE_AMOUNT!=0 and p.CHECKER_STATUS= 'Confirm' \n"
                    + "union \n"
                    + "select MAX(r.ENTRY_TIME) AS time from TEMP_REFUND_BILL_CORP r \n"
                    + "where r.SLNO BETWEEN '" + fromref + "' AND '" + toref + "' and  r.PAID_AMOUNT!=0 and r.CHECKER_STATUS= 'Verified' and r.BILLPAY_UNIQUEID IS NOT NULL \n"
                    + "union\n"
                    + "select MAX(c.ENTRY_TIME) AS time from CSDF_DETAILS c\n"
                    + "where c.SLNO BETWEEN '" + frompsdf + "' AND '" + topsdf + "' and c.CHECKER_STATUS='Verified' and c.AMT_CATEGORY='Bills' \n"
                    + "union\n"
                    + "select MAX(m.ENTRY_TIME) AS time from MISC_DISBURSEMENT m\n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommisc + "' AND '" + tomisc + "' and m.STATUS='Checked' and m.AMT_CATEGORY='Bills' \n"
                    + "union\n"
                    + "select MAX(p.ENTRY_TIME) AS time from PAYMENT_INTEREST_DISBURSEMENT p \n"
                    + "where p.SLNO BETWEEN '" + fromint + "' AND '" + toint + "' and p.CHECKER_STATUS='Confirmed' \n"
                    + "union\n"
                    + "select MAX(c.ENTRY_TIME) AS time from CSDF_DETAILS c\n"
                    + "where c.SLNO BETWEEN '" + frompsdf + "' AND '" + topsdf + "' and c.CHECKER_STATUS='Verified' and c.AMT_CATEGORY='Interest' \n"
                    + "union\n"
                    + "select MAX(m.ENTRY_TIME) AS time from MISC_DISBURSEMENT m\n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommisc + "' AND '" + tomisc + "' and m.STATUS='Checked' and m.AMT_CATEGORY='Interest' \n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (Timestamp) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public Timestamp getentrytimeforonlyInterest(int fromint, int toint, int frompsdf, int topsdf, int frommisc, int tomisc) {
        Timestamp result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select max(time) from(\n"
                    + "select MAX(p.ENTRY_TIME) AS time from PAYMENT_INTEREST_DISBURSEMENT p \n"
                    + "where p.SLNO BETWEEN '" + fromint + "' AND '" + toint + "' and p.CHECKER_STATUS='Confirmed' and p.SLNO NOT IN (select DISBURSE_ID from BANK_STATEMENT where DISBURSE_TYPE='Interest')\n"
                    + "union\n"
                    + "select MAX(c.ENTRY_TIME) AS time from CSDF_DETAILS c\n"
                    + "where c.SLNO BETWEEN '" + frompsdf + "' AND '" + topsdf + "' and c.CHECKER_STATUS='Verified' and c.AMT_CATEGORY='Interest' and c.SLNO NOT IN (select DISBURSE_ID from BANK_STATEMENT where DISBURSE_TYPE='PSDF')\n"
                    + "union\n"
                    + "select MAX(m.ENTRY_TIME) AS time from MISC_DISBURSEMENT m\n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommisc + "' AND '" + tomisc + "' and m.STATUS='Checked' and m.AMT_CATEGORY='Interest' and m.UNIQUE_ID NOT IN (select DISBURSE_ID from BANK_STATEMENT where DISBURSE_TYPE='Misc')\n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (Timestamp) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public BigDecimal getminpoolopenbalfornotesheet(int fromid, int toid, int fromref, int toref, int frompsdf, int topsdf, int frommisc, int tomisc) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select min(bef)\n"
                    + "from(\n"
                    + "select (p.POOL_BAL) as  bef\n"
                    + "from PAYMENT_DISBURSEMENT p\n"
                    + "where  p.DISBURSE_ID='" + toid + "'\n"
                    + "union\n"
                    + "select (r.POOL_BAL)  as  bef from TEMP_REFUND_BILL_CORP r\n"
                    + "where  r.SLNO='" + toref + "'\n"
                    + "union\n"
                    + "select (c.MAIN_POOL_BALANCE-c.CSDF_AMOUNT) as bef from CSDF_DETAILS c\n"
                    + "where c.AMT_CATEGORY='Bills' and c.SLNO='" + topsdf + "'\n"
                    + "union\n"
                    + "select (m.MAIN_POOL_BALANCE-m.REFUND_AMT) as bef from MISC_DISBURSEMENT m\n"
                    + "where m.AMT_CATEGORY='Bills' and m.UNIQUE_ID='" + tomisc + "'\n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal) query.list().get(0);
                System.out.println("openbalance is " + result);
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

    public String getbilltype(int fromid, int toid) {
        String result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select BILL_TYPE from PAYMENT_DISBURSEMENT where  DISBURSE_ID BETWEEN '" + fromid + "' AND '" + toid + "' and  DISBURSE_AMOUNT!=0 and CHECKER_STATUS= 'Confirm' group by BILL_TYPE";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (String) query.list().get(0);
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

//    public List<Timestamp> getPaymentDisbursementbyCorpgroupbyEntryDateTimestamp() {
//
//        List<Timestamp> list = null;
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
//           
//
//            String hql = "select distinct(TRUNC(entry_date)) from PAYMENT_DISBURSEMENT s where CHECKER_STATUS='Confirm'   order by TRUNC(s.entry_date) ASC";
//
////          String hql = "select distinct(TRUNC(billing_date)) from BILL_RECEIVE_CORP s where (DISBURSE_STATUS='NOT PAID' OR DISBURSE_STATUS='PARTIALLY') and revision_no = (select revision_no from MAX_RECEIVE_REVISION_NO where s.corporate_id=corporate_id and week_id=s.week_id and bill_type='" + billtype + "' )  order by TRUNC(s.billing_date) ASC";
//
//            System.out.println("hql is " + hql);
//
//            SQLQuery query = session.createSQLQuery(hql);
//
//            list = query.list();
//
//            System.out.print("Size of group bydate is" + list.size());
//
//            tx.commit();
//
//            session.flush();
//
//            session.close();
//
//            return list;
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
//        return list;
//
//    }
    public List<PaymentDisbursement> getDisbursementDetailsbyCorpStatus(String status, int corpid) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.checkerStatus=:status and s.disburseId !=0  and s.corporates.corporateId=:corpid  order by s.disburseId ASC ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("status", status);

            query.setParameter("corpid", corpid);

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

            e.printStackTrace();

        }

        return list;

    }

//    select * from PAYMENT_DISBURSEMENT s where s.CORPORATE_ID  = 4 and TO_DATE(s.DISBURSAL_DATE) between TO_DATE('2019-12-10','YYYY-MM-DD') AND TO_DATE('2019-12-10','YYYY-MM-DD') and s.CHECKER_STATUS='Confirm' and s.DISBURSE_ID NOT IN (select b.DISBURSE_ID from BANK_STATEMENT b );
    public List<Timestamp> getPaymentDisbursementbyCorpgroupbyEntryDateTimestamp() {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(entry_date)) from PAYMENT_DISBURSEMENT s where CHECKER_STATUS='Confirm'   order by TRUNC(s.entry_date) ASC";

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

    public List<PaymentDisbursement> getPaymentDisbursementbyFromdateTodates(Date fromdate, Date todate) {

        Session session = null;

        List<PaymentDisbursement> queryList = new ArrayList<>();

        try {

            session = HibernateUtil.getInstance().getSession();

//            Query query = session.createQuery("from PaymentDisbursement s where TO_DATE(s.disbursalDate) between :fromdate and :todate and s.checkerStatus='Confirm' and s.disburseId IN (select e.disburseId from BankStatement e where e.reconFlag='0' and  e.disburseType='Bills' )  ");
            Query query = session.createQuery("from PaymentDisbursement s where s.disburseAmount!=0 and  s.checkerStatus='Confirm' and s.disburseId IN (select e.disburseId from BankStatement e where TO_DATE(e.amountDate) between :fromdate and :todate and e.reconFlag='0' and  e.disburseType='Bills' and e.creditDebitFlag='DR' )  ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            System.out.println("list size paymentdisburse reconsialtion" + queryList.size());

            for (PaymentDisbursement e : queryList) {

                Hibernate.initialize(e.getCorporates());

                Hibernate.initialize(e.getBillReceiveCorp());

            }

            return queryList;

        } catch (Exception e) {

            System.out.println("error in DAO method");
            return queryList;

        } finally {

            session.close();

        }

    }

    public List<PaymentDisbursement> getDisbursementDetailsbyDisburseID(int disburseID) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.disburseId=:disburseID and s.checkerStatus='Confirm' ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("disburseID", new BigDecimal(disburseID));

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

            e.printStackTrace();

        }

        return list;

    }

    public List<PaymentDisbursement> getDisbursementDetailsbyDisburseIDnotinbankstmt(int disburseID) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.disburseId=:disburseID and s.checkerStatus='Confirm' and s.disburseId NOT IN (select disburseId from BankStatement where disburseType='Bills' ) ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("disburseID", new BigDecimal(disburseID));

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

            e.printStackTrace();

        }

        return list;

    }

    public List<PaymentDisbursement> getDisbursementDetailsbyFromDateTodate(Date fromdate, Date toDate) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.disburseAmount!=0 and TO_DATE(s.disbursalDate) between :fromdate and :toDate and s.checkerStatus='Confirm' and s.disburseId NOT IN (select disburseId from BankStatement where disburseType='Bills' ) ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (PaymentDisbursement e : list) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

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

    public List<PaymentDisbursement> getRemarksDisbursementDetailsbyFromDateTodate(Date fromdate, Date toDate) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.disburseAmount =0 and TO_DATE(s.disbursalDate) between :fromdate and :toDate and s.checkerStatus='Confirm' and s.disburseId NOT IN (select disburseId from BankStatement where disburseType='Bills' ) ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (PaymentDisbursement e : list) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

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

    public List<PaymentDisbursement> getbillDisbursementDetailsbyFromDateTodatetypebyrev(Date fromdate, Date toDate) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where  s.disburseAmount !=0 and TO_DATE(s.disbursalDate) between :fromdate and :toDate and s.checkerStatus='Confirm' and s.disburseId NOT IN (select disburseId from BankStatement where disburseType='Bills') ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (PaymentDisbursement e : list) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

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

    public List<PaymentDisbursement> getbillDisbursementDetailsbyFromDateTodatetypebyrevIN(Date fromdate, Date toDate) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where  s.disburseAmount !=0 and TO_DATE(s.disbursalDate) between :fromdate and :toDate and s.checkerStatus='Confirm' and s.disburseId IN (select disburseId from BankStatement where disburseType='Bills') ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (PaymentDisbursement e : list) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

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

    public List<PaymentDisbursement> getbillDisbursementDetailsbyFromDateTodate(Date fromdate, Date toDate) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.disburseAmount !=0 and TO_DATE(s.disbursalDate) between :fromdate and :toDate and s.checkerStatus='Confirm'  ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (PaymentDisbursement e : list) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

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

    public List<PaymentDisbursement> getbillDisbursementDetailsbyfromsndtoid(BigDecimal frombill, BigDecimal tobill) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.disburseAmount !=0 and s.disburseId between :frombill and :tobill and s.checkerStatus='Confirm' and s.disburseId NOT IN (select disburseId from BankStatement where disburseType='Bills') ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("frombill", frombill);

            query.setParameter("tobill", tobill);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (PaymentDisbursement e : list) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

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

    //======================================================================================
    public List<BankStatement> getbillDisbursementDetailsbyFromDateTodatetypebyBilltype(Date fromdate, Date toDate, String btype) {

        List<BankStatement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from BankStatement s where TO_DATE(s.amountDate) between :fromdate and :toDate and s.stmtStatus='Verified' and s.creditDebitFlag='DR' and s.disburseType='Bills' and s.disburseId IN (select disburseId from PaymentDisbursement where billType='" + btype + "') ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (BankStatement e : list) {

                    Hibernate.initialize(e.getCorporates());

                }

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

    public List<BankStatement> getMisDisbursesByDate(Date fromdate, Date toDate) {

        List<BankStatement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from BankStatement s where TO_DATE(s.amountDate) between :fromdate and :toDate and s.stmtStatus='Verified' and s.creditDebitFlag='DR' and s.disburseType='Misc' and s.stmtId IN (select z.stmtId from MiscDisbursement z)";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (BankStatement e : list) {

                    Hibernate.initialize(e.getCorporates());

                }

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

    public List<PaymentDisbursement> getDisbursementDetailsbyFromDateTodate2(Date fromdate, Date toDate) {

        List<PaymentDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentDisbursement s where s.disburseAmount!=0 and TO_DATE(s.disbursalDate) between :fromdate and :toDate and s.checkerStatus='Confirm' ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("fromdate", fromdate);

            query.setParameter("toDate", toDate);

            list = query.list();

            System.out.print("List size is " + list.size());

            if (list != null && list.size() > 0) {

                for (PaymentDisbursement e : list) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

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
}
