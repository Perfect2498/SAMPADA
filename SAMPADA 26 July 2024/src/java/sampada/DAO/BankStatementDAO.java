/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BankStatement;
import sampada.pojo.MakerBankStatement;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BankStatementDAO {

    public List<BankStatement> BankStatementlist(int corpid) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and s.mappedBalance >='1' and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");
            query.setParameter("corpid", corpid);
            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> getUnmappedPayments() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement bs where bs.mappedBalance>'0' and bs.creditDebitFlag='CR' and (bs.stmtStatus ='Verified') and bs.stmtId NOT IN (select md.bankStatement.stmtId from DsnFileDetails md where md.bankStatement.stmtId IS NOT NULL) order by bs.corporates");

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

    }

    public List<BankStatement> BankStatementlistforDSN(int corpid) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and s.mappedBalance !='0' and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");
            query.setParameter("corpid", corpid);
            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> BankStatementOrderlist(int corpid) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and s.mappedBalance >='1' and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.stmtId");
            query.setParameter("corpid", corpid);
            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> BankStatementnotmappedlist(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.mappedAmount='0' and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.stmtId");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> BankStatementnotmappedlistbyids(BigDecimal fromid, BigDecimal toid) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where s.stmtId between :fromid and :toid and s.mappedAmount='0' and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.stmtId");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("fromid", fromid);
            query.setParameter("toid", toid);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> CRBankStatementlistbydates(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate  and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.stmtId");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> CRBankStatementlistbyentrydates(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.entryDate) between :amtFrmDate and :amtToDate  and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.stmtId");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> DRBankStatementlistbydatesreconzero(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "DR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate  and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.stmtId");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public int getUpdateBankStmtbyCheckerforCorp(BigDecimal stmtid, BigDecimal mappedamount, BigDecimal mappedbalance, String remarks) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql = "update BANK_STATEMENT set MAPPED_AMOUNT='" + mappedamount + "', MAPPED_BALANCE='" + mappedbalance + "',REMARKS='" + remarks + "' where STMT_ID=" + stmtid + " ";

            String hql = "update BANK_STATEMENT set MAPPED_AMOUNT='" + mappedamount + "', MAPPED_BALANCE='" + mappedbalance + "' where STMT_ID=" + stmtid + " ";
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

    public int getUpdateTempBankStmtbyCheckerforCorp(int Corpid, String status) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_MAP_BANK_STATEMENT set CHECKER_STATUS='" + status + "' where CORPORATE_ID=" + Corpid + " ";
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

    public boolean NewBankStatementEntries(BankStatement dic) {

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

    public boolean NewBankStatementEntriesmaker(MakerBankStatement dic) {

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

    public int getMaxStmtId() {
        int result = 0;
        BigDecimal bg = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(max(STMT_ID),0) from BANK_STATEMENT";
            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);
            result = bg.intValueExact();
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

    public int getMaxStmtIdmaker() {
        int result = 0;
        BigDecimal bg = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(max(STMT_ID),0) from MAKER_BANK_STATEMENT";
            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);
            result = bg.intValueExact();
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

    public Date getMaxStmtFromdateRRAS(int year) {
        Date date = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
//            String hql="select max(STMT_FROMDATE) from BANK_STATEMENT where EXTRACT(month FROM STMT_FROMDATE)='"+year+"'"; 
            String hql = "select max(STMT_FROMDATE) from BANK_STATEMENT where EXTRACT(year FROM STMT_FROMDATE)='" + year + "'";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            //int length = query.list().size();
            if (query.list() != null) {
                date = (Date) query.list().get(0);
                System.out.println("result is " + date);
            }
            tx.commit();
            session.flush();
            session.close();
            return date;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return date;
    }

    public Date getMaxStmtTodate(int year) {
        Date date = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(STMT_TODATE) from BANK_STATEMENT where EXTRACT(year FROM STMT_TODATE)='" + year + "'";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            //int length = query.list().size();
            if (query.list() != null) {
                date = (Date) query.list().get(0);
                System.out.println("result is " + date);
            }
            tx.commit();
            session.flush();
            session.close();
            return date;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return date;
    }

    //    select distinct(STMT_FROMDATE) from SAMPADA_WRLDC.BANK_STATEMENT where STMT_STATUS='Pending';
    public List<Date> getListFromDatebyStmtList() {

        Session session = null;

        try {
            String Pending = "Pending";
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select distinct(E.stmtFromdate) FROM MakerBankStatement E where E.stmtStatus=:Pending";
            Query query = session.createQuery(hql);
            query.setParameter("Pending", Pending);

            List<Date> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());

                return (List<Date>) queryList;
            }
        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<Date> getListFromDatebyStmtListmaker() {

        Session session = null;

        try {
            String mPending = "mPending";
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select distinct(E.stmtFromdate) FROM MakerBankStatement E where E.stmtStatus=:mPending";
            Query query = session.createQuery(hql);
            query.setParameter("mPending", mPending);

            List<Date> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());

                return (List<Date>) queryList;
            }
        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    //    select * from SAMPADA_WRLDC.BANK_STATEMENT where STMT_STATUS='Pending' and trunc(STMT_FROMDATE)=TO_DATE('2019-07-01', 'yyyy-MM-dd');
    public List<Object[]> getStmtInfoByStatusFrmDate(String frmDate) {
        List<Object[]> list = null;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
//makerPending
            String hql = "select * from MAKER_BANK_STATEMENT where STMT_STATUS='Pending' and trunc(STMT_FROMDATE)=TO_DATE('" + frmDate + "', 'yyyy-MM-dd') ";
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

    public List<Object[]> getStmtInfoByStatusFrmDatemaker(String frmDate) {
        List<Object[]> list = null;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
//makerPending
            String hql = "select * from MAKER_BANK_STATEMENT where STMT_STATUS='mPending' and trunc(STMT_FROMDATE)=TO_DATE('" + frmDate + "', 'yyyy-MM-dd') ";
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

    public List<BankStatement> getStmtByStmtStatusFromDate(String fromDate) {
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where s.stmtStatus=:Pending and s.stmtFromdate=:date1");
            String Pending = "Pending";
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = formatter1.parse(fromDate);
            System.out.println("fromDate is " + fromDate);
            System.out.println("date1 is " + date1);
            query.setParameter("Pending", Pending);
            query.setParameter("date1", date1);
            List<BankStatement> queryList = query.list();

            System.out.println("In getStmtByStmtStatusFromDate%%%%%%%%%%%");
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }
        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public int updateBankStmtbyCheckerFromDate(String frmDate) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BANK_STATEMENT set STMT_STATUS='Verified' where STMT_STATUS='Pending' and  trunc(STMT_FROMDATE)=TO_DATE('" + frmDate + "', 'yyyy-MM-dd') ";
            System.out.println("updateBankStmtbyCheckerFromDate Query is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
            System.out.println("length is " + length);
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

    public int updatemakerBankStmtbyCheckerFromDate(String frmDate) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update MAKER_BANK_STATEMENT set STMT_STATUS='Verified' where STMT_STATUS='Pending' and  trunc(STMT_FROMDATE)=TO_DATE('" + frmDate + "', 'yyyy-MM-dd') ";
            System.out.println("updateBankStmtbyCheckerFromDate Query is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
            System.out.println("length is " + length);
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

    public int updateBankStmtmappedbalancebyid(BigDecimal stmtid, BigDecimal amount) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update BANK_STATEMENT set MAPPED_BALANCE='" + amount + "' where STMT_ID='" + stmtid + "' ";
            System.out.println("updateBankStmtmappedbalancebyid Query is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
            System.out.println("length is " + length);
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

    public int updateBankStmtbyMakerFromDate(String frmDate) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update MAKER_BANK_STATEMENT set STMT_STATUS='Pending' where STMT_STATUS='mPending' and trunc(STMT_FROMDATE)=TO_DATE('" + frmDate + "', 'yyyy-MM-dd') ";
            System.out.println("updateBankStmtbyCheckerFromDate Query is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
            System.out.println("length is " + length);
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

    public int deleteBankStatementbyCheckerFromDate(String frmDate) {
        int result = 0;
        Session session = null;
        Transaction tx;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "delete from MAKER_BANK_STATEMENT where STMT_STATUS='Pending' and trunc(STMT_FROMDATE)=TO_DATE('" + frmDate + "', 'yyyy-MM-dd') ";
            System.out.println("deleteBankStatementbyCheckerFromDate is " + hql);
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

    public List<Date> getVerifiedStmtListFromDateby() {

        Session session = null;

        try {
            String Verified = "Verified";
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select distinct(E.stmtFromdate) FROM BankStatement E where E.stmtStatus=:Verified";
            Query query = session.createQuery(hql);
            query.setParameter("Verified", Verified);
            List<Date> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());

                return (List<Date>) queryList;
            }
        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getCheckedStmtInfoByStatusFrmDate(String frmDate) {
        List<Object[]> list = null;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select * from BANK_STATEMENT where STMT_STATUS='Verified' and trunc(STMT_FROMDATE)=TO_DATE('" + frmDate + "', 'yyyy-MM-dd') ";
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

    public int getViabilityOfFromDate(String frmDate) {
        int result = 0;
        BigDecimal bg = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(max(STMT_ID),0) from BANK_STATEMENT where (select  MAX(trunc(STMT_TODATE)) from BANK_STATEMENT) >= TO_DATE('" + frmDate + "', 'yyyy-MM-dd')";
            System.out.println("getViabilityOfFromDate is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);
            result = bg.intValueExact();
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

    public Date getToDateForFromDate(String fromDate) {
        Date date = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select STMT_TODATE from MAKER_BANK_STATEMENT where trunc(STMT_FROMDATE)=TO_DATE('" + fromDate + "', 'yyyy-MM-dd') and (STMT_STATUS='mPending' or STMT_STATUS='Pending') ";
//            String hql = "select STMT_TODATE from MAKER_BANK_STATEMENT where trunc(STMT_FROMDATE)=TO_DATE('" + fromDate + "', 'yyyy-MM-dd') and STMT_STATUS='mPending' ";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list() != null) {
                date = (Date) query.list().get(0);
                System.out.println("result is " + date);
            }
            tx.commit();
            session.flush();
            session.close();
            return date;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return date;
    }

    public int getMaxStmtYear() {
        Session session = null;
        int result = 0;

        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(EXTRACT(year FROM STMT_TODATE)) from BANK_STATEMENT";
            SQLQuery query = session.createSQLQuery(hql);
            if (query.list() != null) {
                BigDecimal bg = BigDecimal.ZERO;
                bg = (BigDecimal) query.list().get(0);
                result = bg.intValueExact();
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

    //    select COUNT(STMT_ID) from BANK_STATEMENT where CORPORATE_ID=19 AND trunc(AMOUNT_DATE)=TO_DATE('2019-08-30', 'yyyy-MM-dd') AND AMOUNT_TIME='03:20:03 PM';
    public int getBankEntryExistance(int corpId, String amtDate, String amtTime) {
        int result = 0;
        BigDecimal bg = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select COUNT(STMT_ID) from BANK_STATEMENT where CORPORATE_ID= " + corpId + " and trunc(AMOUNT_DATE) = TO_DATE('" + amtDate + "', 'yyyy-MM-dd') AND AMOUNT_TIME='" + amtTime + "'";
            System.out.println("getBankEntryExistance is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);
            result = bg.intValueExact();
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

// select * from SAMPADA_WRLDC.BANK_STATEMENT where STMT_STATUS='Verified' and trunc(AMOUNT_DATE) BETWEEN TO_DATE ('2019-08-14', 'yyyy-MM-dd')
//AND TO_DATE ('2019-09-01', 'yyyy-MM-dd');
    public List<Object[]> getVerifiedStmtInfoByAmountFrmToDate(String amtFrmDate, String amtToDate) {
        List<Object[]> list = null;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select * from BANK_STATEMENT where STMT_STATUS='Verified' and trunc(AMOUNT_DATE) BETWEEN TO_DATE('" + amtFrmDate + "', 'yyyy-MM-dd') AND TO_DATE ('" + amtToDate + "', 'yyyy-MM-dd') ORDER BY ENTRY_DATE ASC";
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

    public BigDecimal getMappedAmountBankStmtbyStmtID(int stmtid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select mapped_amount from BANK_STATEMENT where STMT_ID='" + stmtid + "' ";

            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);

            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public BigDecimal getcorpidbyStmtID(int stmtid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select CORPORATE_ID from BANK_STATEMENT where STMT_ID='" + stmtid + "' ";

            SQLQuery query = session.createSQLQuery(hql);

            bg = (BigDecimal) query.list().get(0);

            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }
//select sum(PAID_AMOUNT) FROM BANK_STATEMENT WHERE STMT_STATUS='Pending' AND CREDIT_DEBIT_FLAG='CR';

    public BigDecimal getCRSumAmountBankStmtbyPendingStatusInt() {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(SUM(PAID_AMOUNT),0) FROM BANK_STATEMENT WHERE STMT_STATUS='Pending' AND CREDIT_DEBIT_FLAG='CR'";
            System.out.println("getCRSumAmountBankStmtbyPendingStatus hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public BigDecimal getCRSumAmountBankStmtbyverifieddates(String amtFrmDate, String amtToDate) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(SUM(PAID_AMOUNT),0) FROM BANK_STATEMENT WHERE STMT_STATUS='Verified' AND CREDIT_DEBIT_FLAG='CR' and trunc(AMOUNT_DATE) BETWEEN TO_DATE('" + amtFrmDate + "', 'yyyy-MM-dd') AND TO_DATE ('" + amtToDate + "', 'yyyy-MM-dd')";
            System.out.println("getCRSumAmountBankStmtbyPendingStatus hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public BigDecimal getCRSumAmountBankStmtbybankids(int fromid, int toid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(SUM(PAID_AMOUNT),0) FROM BANK_STATEMENT WHERE STMT_STATUS='Verified' AND CREDIT_DEBIT_FLAG='CR' and STMT_ID between '" + fromid + "' and '" + toid + "'";
            System.out.println("getCRSumAmountBankStmtbyPendingStatus hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public Timestamp getamountdatebybankids(int fromid, int toid) {

        Session session = null;

        int result = 0;

        Timestamp bg = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select AMOUNT_DATE FROM BANK_STATEMENT WHERE STMT_STATUS='Verified' AND CREDIT_DEBIT_FLAG='CR' and STMT_ID = '" + fromid + "'";
            System.out.println("getCRSumAmountBankStmtbyPendingStatus hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (Timestamp) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public Timestamp getentrytimebeforestmt(int fromid, int toid, int fromimapid, int toimapid, int frompid, int topid, int frommapbil, int tomapbil, int frommapref, int tomapref,int frompidp, int topidp) {
        Timestamp result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select MIN(time) from(\n"
                    + "select MIN(p.ENTRY_TIME) AS time from BANK_STATEMENT p \n"
                    + "where p.STMT_ID BETWEEN '" + fromid + "' AND '" + toid + "' and p.STMT_STATUS= 'Verified' and p.CREDIT_DEBIT_FLAG= 'CR'\n"
                    + "union \n"
                    + "select MIN(m.ENTRY_TIME) AS time from MAPPING_INTEREST_BANK m \n"
                    + "where m.SLNO BETWEEN '" + fromimapid + "' AND '" + toimapid + "' and m.CHECKER_STATUS= 'Confirmed' \n"
                    + "union \n"
                    + "select MIN(m.ENTRY_TIME) AS time from MAPPING_BILL_BANK m \n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommapbil + "' AND '" + tomapbil + "' and m.CHECKER_STATUS= 'Verified' \n"
                    + "union \n"
                    + "select MIN(m.ENTRY_TIME) AS time from MAPPING_REFUND_BANK m \n"
                    + "where m.SLNO BETWEEN '" + frommapref + "' AND '" + tomapref + "' and m.CHECKER_STATUS= 'Verified' \n"
                    + "union \n"
                    + "select MIN(i.ENTRY_TIME) AS time from POOL_TO_INT i \n"
                    + "where i.UNIQUE_ID BETWEEN '" + frompid + "' AND '" + topid + "' and i.STATUS= 'Checked' \n"
                    + "union \n"
                    + "select MIN(i.ENTRY_TIME) AS time from POOL_TO_POOL i \n"
                    + "where i.UNIQUE_ID BETWEEN '" + frompidp + "' AND '" + topidp + "' and i.STATUS= 'Checked' \n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (Timestamp) query.list().get(0);
                System.out.println("entrytimebeforestmt is " + result);
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

    public Timestamp getentrytimeafterstmt(int fromid, int toid, int fromimapid, int toimapid, int frompid, int topid, int frommapbil, int tomapbil, int frommapref, int tomapref,int frompidp, int topidp) {
        Timestamp result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select MAX(time) from(\n"
                    + "select MAX(p.ENTRY_TIME) AS time from BANK_STATEMENT p \n"
                    + "where p.STMT_ID BETWEEN '" + fromid + "' AND '" + toid + "' and p.STMT_STATUS= 'Verified' and p.CREDIT_DEBIT_FLAG= 'CR'\n"
                    + "union \n"
                    + "select MAX(m.ENTRY_TIME) AS time from MAPPING_INTEREST_BANK m \n"
                    + "where m.SLNO BETWEEN '" + fromimapid + "' AND '" + toimapid + "' and m.CHECKER_STATUS= 'Confirmed' \n"
                    + "union \n"
                    + "select MAX(m.ENTRY_TIME) AS time from MAPPING_BILL_BANK m \n"
                    + "where m.UNIQUE_ID BETWEEN '" + frommapbil + "' AND '" + tomapbil + "' and m.CHECKER_STATUS= 'Verified' \n"
                    + "union \n"
                    + "select MAX(m.ENTRY_TIME) AS time from MAPPING_REFUND_BANK m \n"
                    + "where m.SLNO BETWEEN '" + frommapref + "' AND '" + tomapref + "' and m.CHECKER_STATUS= 'Verified' \n"
                    + "union \n"
                    + "select MAX(i.ENTRY_TIME) AS time from POOL_TO_INT i \n"
                    + "where i.UNIQUE_ID BETWEEN '" + frompid + "' AND '" + topid + "' and i.STATUS= 'Checked' \n"
                    + "union \n"
                    + "select MAX(i.ENTRY_TIME) AS time from POOL_TO_POOL i \n"
                    + "where i.UNIQUE_ID BETWEEN '" + frompidp + "' AND '" + topidp + "' and i.STATUS= 'Checked' \n"
                    + ")";

            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            //int length = query.list().size();
            if (query.list() != null) {
                result = (Timestamp) query.list().get(0);
                System.out.println("entrytimebeforestmt is " + result);
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

    public BigDecimal getopenbalBankStmtbyverifieddates(String amtFrmDate, String amtToDate) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select POOL_BAL-PAID_AMOUNT from bank_statement where  POOL_BAL=(select MIN(POOL_BAL) from bank_statement where trunc(AMOUNT_DATE) BETWEEN TO_DATE('" + amtFrmDate + "', 'yyyy-MM-dd') AND TO_DATE ('" + amtToDate + "', 'yyyy-MM-dd') and  CREDIT_DEBIT_FLAG='CR') and trunc(AMOUNT_DATE) BETWEEN TO_DATE('" + amtFrmDate + "', 'yyyy-MM-dd') AND TO_DATE ('" + amtToDate + "', 'yyyy-MM-dd') and  CREDIT_DEBIT_FLAG='CR'";
            System.out.println("getopenbalBankStmtbyverifieddates hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public BigDecimal getopenbalBankStmtbyid(int fromid, int toid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select NVL(POOL_BAL-PAID_AMOUNT,0) from bank_statement where STMT_ID='" + fromid + "' and  CREDIT_DEBIT_FLAG='CR'";
            System.out.println("getopenbalBankStmtbyverifieddates hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public BigDecimal getCRSumAmountBankStmtbyPendingStatus() {

        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        List list1 = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "select sum(PAID_AMOUNT) as paidamount from BANK_STATEMENT where STMT_STATUS='Pending' and CREDIT_DEBIT_FLAG='CR'";

            SQLQuery query = session.createSQLQuery(hql);
            list1 = query.list();

            System.out.print("List Paid Amount is " + list1.get(0));

            result = (BigDecimal) list1.get(0);

            System.out.print("indied get Paid Amount is " + result);

            System.out.print("Sum Paid Amount is " + result);

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

    public List<BankStatement> getBankStatementByCorpAndYear(int corpid, Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and s.amountDate between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            query.setParameter("corpid", corpid);
            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> getBankStatementbyStmtID(int stmtid) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from BankStatement s where s.stmtId=:stmtid ");

            query.setParameter("stmtid", new BigDecimal(stmtid));

            List<BankStatement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (BankStatement e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                }

                return (List<BankStatement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> getBankStatementbyFromdateTodates(Date fromdate, Date todate) {

        Session session = null;

        List<BankStatement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            System.out.println("getBankStatementbyFromdateTodates fromdate is " + fromdate);
            System.out.println("getBankStatementbyFromdateTodates todate is " + todate);

//            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :fromdate and :todate and s.creditDebitFlag='DR' and s.reconFlag='0'");
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :fromdate and :todate and s.creditDebitFlag='DR' and s.disburseType='Bills' and s.reconFlag='0'");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            System.out.println("list size Bankstmt for reconcialtion" + queryList.size());

            for (BankStatement e : queryList) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList;

        } catch (Exception e) {

            return queryList;

        } finally {

            session.close();

        }

    }

    public List<Timestamp> getBankStatementbygroupbyEntryDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select distinct(TRUNC(s.amount_date)) from BANK_STATEMENT s, CORPORATES c  where c.CORPORATE_ID=s.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "' and s.STMT_STATUS='Verified' and  s.CREDIT_DEBIT_FLAG='DR' and extract(YEAR from s.amount_date)='" + year + "' order by TRUNC(s.amount_date) ASC";

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

    public List<BankStatement> BankStatementlistbyDR(int corpid, String year) {

        Session session = null;

        String Verified = "Verified";

        String crFlag = "DR";

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag and to_char(s.amountDate,'YYYY')=:year order by s.amountDate,s.amountTime,s.stmtId");

//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");
            query.setParameter("corpid", corpid);

            query.setParameter("Verified", Verified);

            query.setParameter("year", year);

            query.setParameter("crFlag", crFlag);

            List<BankStatement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (BankStatement e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                }

                return (List<BankStatement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public int getUpdateReconflagBankStmtforbillsbydisburseID(int disburseid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BANK_STATEMENT set RECON_FLAG='1' where DISBURSE_ID=" + disburseid + "  and DISBURSE_TYPE='Bills'";

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

    public List<BankStatement> getBankStatementbyFromdateTodatesForRefundDR(Date fromdate, Date todate) {

        Session session = null;

        List<BankStatement> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :fromdate and :todate and s.creditDebitFlag='DR' and s.reconFlag='0' and s.disburseType='Refund'");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            System.out.println("list size" + queryList.size());

            for (BankStatement e : queryList) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList;

        } catch (Exception e) {

            return queryList;

        } finally {

            session.close();

        }

    }

    public List<MakerBankStatement> getBankStatementbymakerpending() {

        Session session = null;

        List<MakerBankStatement> queryList = null;
        String Pending = "Pending";
        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from MakerBankStatement s where  s.stmtStatus=:Pending order by s.poolBal");
            query.setParameter("Pending", Pending);

            queryList = query.list();

            System.out.println("list size" + queryList.size());

            for (MakerBankStatement e : queryList) {

                Hibernate.initialize(e.getCorporates());

            }

            return queryList;

        } catch (Exception e) {

            return queryList;

        } finally {

            session.close();

        }

    }

    public int deleteBankStatementbyChecker() {
        int result = 0;
        Session session = null;
        Transaction tx;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "delete from MAKER_BANK_STATEMENT where STMT_STATUS='mPending' ";
            System.out.println("deleteBankStatementbyCheckerFromDate is " + hql);
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

    public int getUpdateReconflagBankStmtforrefundbydisburseID(int disburseid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update BANK_STATEMENT set RECON_FLAG='1' where DISBURSE_ID=" + disburseid + "  and DISBURSE_TYPE='Refund'";

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

    public List<BankStatement> BankStatementlistofallcorp() {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where  s.mappedBalance !='0' and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<BankStatement> BankStatementlistbydsndetails() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where  s.stmtId IN (select d.bankStatement.stmtId from DsnFileDetails d) ");

            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public BankStatement getBankStatementbyDisburseId(BigDecimal dis_id, String disburse_type) {
        Session session = null;
        List<BankStatement> list = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            String hql = "from BankStatement s where s.stmtStatus='Verified' and s.creditDebitFlag='DR' and s.disburseType='" + disburse_type + "' and s.disburseId=" + dis_id;
            Query query = session.createQuery(hql);
            list = query.list();

            if (list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return list.get(0);
    }
}
