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
import sampada.pojo.BankStatement;

import sampada.pojo.DisbursedInterestDetails;

import sampada.pojo.InterestDetails;
import sampada.pojo.PaymentInterestDisbursement;

import sampada.util.HibernateUtil;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class DisbursedInterestDetailsDAO {

    public boolean NewDisbursedInterestDetails(DisbursedInterestDetails dic) {

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

    public int getMaxDisInterestId() {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(INTEREST_ID),0) from DISBURSED_INTEREST_DETAILS";

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

    public List<DisbursedInterestDetails> getDisbursedInterestDetailsbyCorp() {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestPendingamount!=0 and s.checkerStatus!='ToChecker' and (s.billType='DSM' or s.billType='AGC' or s.billType='RRAS')");

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

    public List<DisbursedInterestDetails> getDisbursedInterestDetailsbyCorpforChecker(int corpid) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s where s.corporates.corporateId=:corpid and s.checkerStatus='Pending' ");

            query.setParameter("corpid", corpid);

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

    public int getUpdateDisburseInterestbyId(int interestid, BigDecimal IntesrestAmount, BigDecimal pendamt) {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update DISBURSED_INTEREST_DETAILS set INTEREST_PAIDAMOUNT=" + IntesrestAmount + ",INTEREST_PENDINGAMOUNT=" + pendamt + " , INTEREST_PAIDDATE=SYSDATE, CHECKER_STATUS='Pending' where INTEREST_ID=" + interestid + " ";

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

    public List<Object[]> getAllInterestReceivableBillCorpObjectlistforCheacker() {

        Session session = null;

        List<Object[]> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from DISBURSED_INTEREST_DETAILS s , corporates c  where s.CHECKER_STATUS='Pending' and s.INTEREST_PAIDAMOUNT IS NOT NULL and s.corporate_id = c.corporate_id ");

            queryList = query.list();

            return queryList;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public int getUpdateStatusDisburseInterestbyId(int corpid) {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update DISBURSED_INTEREST_DETAILS set CHECKER_STATUS='Confirmed' where CORPORATE_ID=" + corpid + " ";

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

    public BigDecimal getInterestPaidAmountDisbursedInterestDetailsbyDisInterestID(int disinterestid) {

        Session session = null;

        BigDecimal paidintestamt = new BigDecimal(0);

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("select interestPaidamount from DisbursedInterestDetails s where s.interestId=:disinterestid ");

            query.setParameter("disinterestid", new BigDecimal(disinterestid));

            paidintestamt = (BigDecimal) query.list().get(0);

            return paidintestamt;

        } catch (Exception e) {

            e.printStackTrace();

            return paidintestamt;

        } finally {

            session.close();

        }

    }

    public BigDecimal getLatestDisburseInterestAmount(String billtype) {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "select NVL(sum(Interest_amount),0) from DISBURSED_INTEREST_DETAILS where interest_paiddate is null and BILL_TYPE='" + billtype + "'";

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

    public List<DisbursedInterestDetails> getPendingDisbursedInterestDetailsList() {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestPendingamount !=0 ");

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

    public int getUpdateStatusDisburseInterestbyId(String status, int interestid) {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update DISBURSED_INTEREST_DETAILS set CHECKER_STATUS='Confirmed' where CHECKER_STATUS='" + status + "' and INTEREST_ID='" + interestid + "' ";

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

    public List<PaymentInterestDisbursement> getDisbursedInterestDetailsbyCorpforChecker() {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Pending' and s.disbursedInterestDetails.interestPaidamount IS NOT NULL ");
            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Pending'  ");

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<DisbursedInterestDetails> getDisbursedInterestDetailsbyCorpbytype(String type) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestPendingamount!=0 and s.checkerStatus!='ToChecker' and s.billType=:type");

            query.setParameter("type", type);
            
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
    
    public List<DisbursedInterestDetails> getDisbursedInterestDetailsbyCorpbytypetras( ) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestPendingamount!=0 and s.checkerStatus!='ToChecker' and (lower(s.billType)='trasm' or lower(s.billType)='trass' or lower(s.billType)='trase') ");

           
            
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
    
    public List<Object[]> getDisbursedInterestDetailsGroupbyBillingdatebytype(String type) {

        Session session = null;

        List<Object[]> listobj = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            SQLQuery query = session.createSQLQuery("select distinct billing_date from Disbursed_Interest_Details  where  interest_Pendingamount!=0 and BILL_TYPE = '" + type + "' order by billing_Date ASC");

            listobj = query.list();

            return listobj;

        } catch (Exception e) {

            e.printStackTrace();

            return listobj;

        } finally {

            session.close();

        }

    }
    
    public List<Object[]> getDisbursedInterestDetailsGroupbyBillingdatebytypetras() {

        Session session = null;

        List<Object[]> listobj = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            SQLQuery query = session.createSQLQuery("select distinct billing_date from Disbursed_Interest_Details  where  interest_Pendingamount!=0 and (lower(BILL_TYPE) = 'trasm' or  lower(BILL_TYPE) = 'trass' or lower(BILL_TYPE) = 'trase') order by billing_Date ASC");

            listobj = query.list();

            return listobj;

        } catch (Exception e) {

            e.printStackTrace();

            return listobj;

        } finally {

            session.close();

        }

    }
    
    public List<Object[]> getDisbursedInterestDetailsGroupbyBillingdate() {

        Session session = null;

        List<Object[]> listobj = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            SQLQuery query = session.createSQLQuery("select distinct billing_date from Disbursed_Interest_Details  where  interest_Pendingamount!=0  and (BILL_TYPE='DSM' or BILL_TYPE='AGC' or BILL_TYPE='RRAS') order by billing_Date ASC");

            listobj = query.list();

            return listobj;

        } catch (Exception e) {

            e.printStackTrace();

            return listobj;

        } finally {

            session.close();

        }

    }

    public List<PaymentInterestDisbursement> getDisbursementInterestDetailsbyDisburseID(int interestId) {

        List<PaymentInterestDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentInterestDisbursement s where s.slno=:interestId and s.checkerStatus='Confirmed'";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("interestId", new BigDecimal(interestId));

            list = query.list();

            for (PaymentInterestDisbursement e : list) {

                Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());
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

    public List<PaymentInterestDisbursement> getDisbursementInterestDetailsbyDisburseIDnotinbank(int interestId) {

        List<PaymentInterestDisbursement> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from PaymentInterestDisbursement s where s.slno=:interestId and s.checkerStatus='Confirmed' and s.slno NOT IN (select disburseId from BankStatement where disburseType='Interest' ) ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("interestId", new BigDecimal(interestId));

            list = query.list();

            for (PaymentInterestDisbursement e : list) {

                Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());
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

    public List<PaymentInterestDisbursement> getDisbursedInterestDetailsbyCorpforExport(Date fromdate, Date todate) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Confirmed' and s.interestPaidamount !=0 and (TO_DATE(s.interestPaiddate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Interest' ) ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<PaymentInterestDisbursement> getremarkDisbursedInterestDetailsbyCorpforExport(Date fromdate, Date todate) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Confirmed' and s.interestPaidamount =0 and (TO_DATE(s.interestPaiddate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Interest' ) ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<PaymentInterestDisbursement> getDisbursedInterestlistbydates(Date fromdate, Date todate) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Confirmed' and s.interestPaidamount !=0 and (TO_DATE(s.interestPaiddate) between :fromdate and :todate)   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
    public List<PaymentInterestDisbursement> getDisbursedInterestDetailsbydates(Date fromdate, Date todate) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Confirmed' and s.interestPaidamount !=0 and (TO_DATE(s.interestPaiddate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Interest' )   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<PaymentInterestDisbursement> getDisbursedInterestDetailsbydatesIN(Date fromdate, Date todate) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Confirmed' and s.interestPaidamount !=0 and (TO_DATE(s.interestPaiddate) between :fromdate and :todate) and s.slno IN (select disburseId from BankStatement where disburseType='Interest' )   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<PaymentInterestDisbursement> getDisbursedInterestDetailsbyfromandtoids(BigDecimal fromint, BigDecimal toint) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.checkerStatus='Confirmed' and s.interestPaidamount !=0 and (s.slno between :fromint and :toint) and s.slno NOT IN (select disburseId from BankStatement where disburseType='Interest' )  ");

            query.setParameter("fromint", fromint);

            query.setParameter("toint", toint);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<PaymentInterestDisbursement> getDisbursedInterestDetailsbyonlyCorp(int corpid) {
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.disbursedInterestDetails.corporates.corporateId=:corpid and s.checkerStatus='Confirmed' ");

            query.setParameter("corpid", corpid);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size of PaymentInterestDisbursement" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<PaymentInterestDisbursement> getPaymentInterestdisbursementbyslno(BigDecimal slno) {
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from PaymentInterestDisbursement s where s.slno=:slno ");

            query.setParameter("slno", slno);

            List<PaymentInterestDisbursement> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("list size of PaymentInterestDisbursement" + queryList.size());

                for (PaymentInterestDisbursement e : queryList) {

                    Hibernate.initialize(e.getDisbursedInterestDetails());
                    Hibernate.initialize(e.getDisbursedInterestDetails().getCorporates());

                }

                return (List<PaymentInterestDisbursement>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
//===================================================================================================

    
    public List<DisbursedInterestDetails> getInterestdues(String date2) {
        Session session = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query query = session.createQuery("from DisbursedInterestDetails s where s.billingDuedate <= '"+date2+"' and s.interestPendingamount!=0");
            List<DisbursedInterestDetails> list = query.list();
            
            for (DisbursedInterestDetails e : list) {
                Hibernate.initialize(e.getCorporates());
            }
            
            return list;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        
        return null;
    }

//======================================================================================================
    
    public List<DisbursedInterestDetails> getDisbursedInterestDetailsforChecker() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s where s.checkerStatus='ToChecker'");
            List<DisbursedInterestDetails> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {
                return null;
            } 
            else {
                System.out.println("list size" + queryList.size());
                for (DisbursedInterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<DisbursedInterestDetails>) queryList;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }

    }
    
    public void updateDisbursedInterestDetailsbyChecker() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            SQLQuery query = session.createSQLQuery("update DISBURSED_INTEREST_DETAILS set CHECKER_STATUS='Pending' where CHECKER_STATUS='ToChecker'");
            int queryList = query.executeUpdate();
            
            tx.commit();
            session.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
    
    public void rejectDisbursedInterestDetailsbyChecker() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            SQLQuery query = session.createSQLQuery("delete from DISBURSED_INTEREST_DETAILS where CHECKER_STATUS='ToChecker'");
            int queryList = query.executeUpdate();
            
            tx.commit();
            session.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
    
    public List<DisbursedInterestDetails> getAllDisbursedInterestDetails() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s");

            List<DisbursedInterestDetails> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;
            } 
            else {

                System.out.println("list size" + queryList.size());

                for (DisbursedInterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }

                return (List<DisbursedInterestDetails>) queryList;
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
    
    //=================================================================================
    
    public List<DisbursedInterestDetails> getDisbursedInterestDetailsByPublishDates(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from DisbursedInterestDetails s where TO_DATE(s.billingDate) between :fromdate and :todate ");

            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);

            List<DisbursedInterestDetails> queryList = query.list();
            
            for (DisbursedInterestDetails e : queryList) {
                Hibernate.initialize(e.getCorporates());
            }

            return (List<DisbursedInterestDetails>) queryList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }
    }
    
    
    public List<DisbursedInterestDetails> getPendingDisbursedInterestDetailsListByCorpId(int id) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestPendingamount !=0 and s.corporates.corporateId="+id+" and s.checkerStatus='Pending' ");
            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestPendingamount !=0 and s.corporates.corporateId="+id+"  ");

            List<DisbursedInterestDetails> queryList = query.list();

                for (DisbursedInterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }

                return (List<DisbursedInterestDetails>) queryList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }
    }
    
    
     public List<DisbursedInterestDetails> getDisbursedInterestDetailsListByinterestId(BigDecimal id) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestPendingamount !=0 and s.corporates.corporateId="+id+" and s.checkerStatus='Pending' ");
            Query query = session.createQuery("from DisbursedInterestDetails s where s.interestId="+id+"  ");

            List<DisbursedInterestDetails> queryList = query.list();

                for (DisbursedInterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }

                return (List<DisbursedInterestDetails>) queryList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }
    }

}
