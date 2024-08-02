/*

* To change this license header, choose License Headers in Project Properties.

* To change this template file, choose Tools | Templates

* and open the template in the editor.

 */
package sampada.DAO;

import java.math.BigDecimal;
import java.sql.Timestamp;

import java.util.List;
import java.util.Date;

import org.hibernate.Hibernate;

import org.hibernate.Query;

import org.hibernate.SQLQuery;

import org.hibernate.Session;

import org.hibernate.Transaction;
import sampada.pojo.DisbursedInterestDetails;

import sampada.pojo.InterestDetails;
import sampada.pojo.MappingInterestBank;

import sampada.util.HibernateUtil;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class InterestDetailsDAO {

    public boolean NewInterestDetails(InterestDetails dic) {

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

    public int getMaxInterestId() {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(INTEREST_ID),0) from INTEREST_DETAILS";

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

    public List<Object[]> getAllInterestPayableBillCorpObjectlist() {

        Session session = null;

        List<Object[]> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from INTEREST_DETAILS s , corporates c where s.CHECKER_STATUS='Pending' and s.corporate_id = c.corporate_id and s.INTEREST_PENDINGAMOUNT !=0 ");
            queryList = query.list();

            return queryList;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<Object[]> getAllInterestPayableBillCorpObjectlistforChecker() {

        Session session = null;

        List<Object[]> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

//            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from INTEREST_DETAILS s , corporates c  where s.CHECKER_STATUS='Pending' and s.corporate_id = c.corporate_id and s.INTEREST_PAIDAMOUNT IS NOT NULL ");        
            SQLQuery query = session.createSQLQuery("select distinct c.corporate_id,c.corporate_name from INTEREST_DETAILS s , corporates c, MAPPING_INTEREST_BANK m where m.INTEREST_ID=s.INTEREST_ID and m.CHECKER_STATUS='Pending' and s.corporate_id = c.corporate_id and s.INTEREST_PAIDAMOUNT IS NOT NULL ");

            queryList = query.list();

            return queryList;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public List<InterestDetails> getInterestPayableDetailsbyCorp(int corpid) {

        Session session = null;

        String Verified = "Verified";

        String crFlag = "CR";

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from InterestDetails s where s.corporates.corporateId=:corpid and s.checkerStatus='Pending' and s.interestPendingamount !=0 order by s.interestId ASC");

            query.setParameter("corpid", corpid);

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

    public int getUpdateInterestPayablebyId(int interestid, BigDecimal intrestamount, BigDecimal pendInterestamt) {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update INTEREST_DETAILS set INTEREST_PAIDAMOUNT=" + intrestamount + ",INTEREST_PENDINGAMOUNT=" + pendInterestamt + ",INTEREST_PAIDDATE=SYSDATE where INTEREST_ID=" + interestid + " ";

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
 public List<MappingInterestBank> getAllInterestPayableDetailsbyCheacker(int corpid) {

 

        Session session = null;

        String Verified= "Verified";

        String crFlag= "CR";

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from MappingInterestBank s where s.interestDetails.interestPaidamount IS NOT NULL and   s.checkerStatus='Pending' and s.interestDetails.corporates.corporateId=:corpid");         

            query.setParameter("corpid", corpid);

            List<MappingInterestBank> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

 

                return null;

            } else {

                System.out.println("list size" + queryList.size());

                for (MappingInterestBank e : queryList) {

                    Hibernate.initialize(e.getInterestDetails());
                    Hibernate.initialize(e.getInterestDetails().getCorporates());
                    Hibernate.initialize(e.getBankStatement());
                }

                return (List<MappingInterestBank>) queryList;

            }

 

        } catch (Exception e) {

 

            e.printStackTrace();

 

            return null;

 

        } finally {

 

            session.close();

 

        }

 

    }
//    public List<InterestDetails> getAllInterestPayableDetailsbyCheacker(int corpid) {
//
//        Session session = null;
//
//        String Verified = "Verified";
//
//        String crFlag = "CR";
//
//        try {
//
//            session = HibernateUtil.getInstance().getSession();
//
//            Query query = session.createQuery("from InterestDetails s where s.interestPaidamount IS NOT NULL and   s.checkerStatus='Pending' and s.corporates.corporateId=:corpid");
//
//            query.setParameter("corpid", corpid);
//
//            List<InterestDetails> queryList = query.list();
//
//            if (queryList != null && queryList.isEmpty()) {
//
//                return null;
//
//            } else {
//
//                System.out.println("list size" + queryList.size());
//
//                for (InterestDetails e : queryList) {
//
//                    Hibernate.initialize(e.getCorporates());
//
//                }
//
//                return (List<InterestDetails>) queryList;
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return null;
//
//        } finally {
//
//            session.close();
//
//        }
//
//    }

    public int getUpdateInterestPayableStatusbyChecker(int corpid) {

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update INTEREST_DETAILS set CHECKER_STATUS='Confirmed' where CORPORATE_ID='" + corpid + "' AND INTEREST_PENDINGAMOUNT=0 AND INTEREST_PAIDAMOUNT IS NOT NULL ";
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

    public BigDecimal getLatestPayableInterestAmount(String billtype) {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "select NVL(sum(Interest_amount),0) from INTEREST_DETAILS where interest_paiddate is null and BILL_TYPE='" + billtype + "'";

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

    public List<InterestDetails> getPendingInterestPayableList() {

        Session session = null;

        String Verified = "Verified";

        String crFlag = "CR";

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from InterestDetails s where  s.checkerStatus='Pending' and s.interestPendingamount !=0 order by s.interestId ASC");

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

    public List<Timestamp> getInterestDetailsbyCorpgroupbyEntryDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //          String hql = "select distinct(TRUNC(s.paid_date)) from INTEREST_DETAILS s,CORPORATES c where  s.CORPORATE_ID=c.CORPORATE_ID and s.CORPORATE_ID='"+corpid+"' and  CHECKER_STATUS='Verified'   order by TRUNC(s.paid_date) ASC";
//            String hql = "select distinct(TRUNC(s.INTEREST_PAIDDATE)) from INTEREST_DETAILS s,CORPORATES c where  s.CORPORATE_ID=c.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "' and  s.CHECKER_STATUS='Confirmed' and extract(YEAR from s.INTEREST_PAIDDATE)='"+year+"'  order by TRUNC(s.INTEREST_PAIDDATE) ASC";
            String hql = "select distinct(TRUNC(s.ENTRY_DATE)) from INTEREST_DETAILS s,CORPORATES c where  s.CORPORATE_ID=c.CORPORATE_ID and s.CORPORATE_ID='" + corpid + "'  and extract(YEAR from s.ENTRY_DATE)='"+year+"'  order by TRUNC(s.ENTRY_DATE) ASC";

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

    public List<InterestDetails> getInterestPayableDetailsbyCorpforRecon(int corpid) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from InterestDetails s where s.corporates.corporateId=:corpid and   s.checkerStatus='Verified'  order by s.interestId ASC");

            query.setParameter("corpid", corpid);

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

    public List<InterestDetails> getInterestDetailsbyinterestid(BigDecimal interestid) {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from InterestDetails s where s.interestId=:interestid order by s.interestId ASC");

            query.setParameter("interestid", interestid);

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
    
    public List<InterestDetails> getInterestdetailsByDate(String startdate, String enddate) {
        Session session = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query query = session.createQuery("from InterestDetails s where s.billingDate between '"+startdate+"' and '"+enddate+"' order by s.interestId");
            List<InterestDetails> list = query.list();
            
            for (InterestDetails e : list) {
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
    
    //===========================================================================================================
    
    public List<InterestDetails> getInterestdues(String date2) {
        Session session = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query query = session.createQuery("from InterestDetails s where s.billingDuedate <= '"+date2+"' and s.interestPendingamount!=0");
            List<InterestDetails> list = query.list();
            
            for (InterestDetails e : list) {
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

//==================================================================================================
    
    public List<InterestDetails> getInterestDetailsforChecker() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query query = session.createQuery("from InterestDetails s where s.checkerStatus=:checkerStatus order by s.interestId ASC");
            query.setParameter("checkerStatus", "ToChecker");

            List<InterestDetails> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {
                return null;
            } 
            else {
                System.out.println("list size" + queryList.size());
                
                for (InterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }

                return (List<InterestDetails>) queryList;
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
    
    public void updateInterestDetailsbyChecker() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            SQLQuery query = session.createSQLQuery("update INTEREST_DETAILS set CHECKER_STATUS='Pending' where CHECKER_STATUS='ToChecker'");

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
    
    public void rejectInterestDetailsbyChecker() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            SQLQuery query = session.createSQLQuery("delete from INTEREST_DETAILS where CHECKER_STATUS='ToChecker'");

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
    
    public List<InterestDetails> getAllInterestDetails() {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from InterestDetails s");

            List<InterestDetails> queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;
            } 
            else {

                System.out.println("list size" + queryList.size());

                for (InterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }

                return (List<InterestDetails>) queryList;
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

    public List<InterestDetails> getInterestDetailsByPublishDates(Date fromdate, Date todate) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from InterestDetails s where TO_DATE(s.billingDate) between :fromdate and :todate ");

            query.setDate("fromdate", fromdate);
            query.setDate("todate", todate);

            List<InterestDetails> queryList = query.list();
            
            for (InterestDetails e : queryList) {
                Hibernate.initialize(e.getCorporates());
            }

            return (List<InterestDetails>) queryList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }
    }
    
    public List<InterestDetails> getPendingInterestPayableListByCorpid(int id) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();

//            Query query = session.createQuery("from InterestDetails s where s.interestPendingamount !=0 and s.corporates.corporateId="+id+" and s.checkerStatus='Pending' order by s.interestId ASC");
            Query query = session.createQuery("from InterestDetails s where s.interestPendingamount !=0 and s.corporates.corporateId="+id+"  order by s.interestId ASC");

            List<InterestDetails> queryList = query.list();

                for (InterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }

                return (List<InterestDetails>) queryList;
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
