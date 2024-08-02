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
 * @author Kaustubh
 */
public class NewBankStatementDAO {
    
    public List<BankStatement> CRBankStmttlistbydates(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.stmtId");

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
    
    public List<BankStatement> CRBankStmtbydates(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.entryDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.entryTime asc");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            List<BankStatement> queryList = query.list();
                
            for (BankStatement e : queryList) {
                Hibernate.initialize(e.getCorporates());
            }
                
            return (List<BankStatement>) queryList;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }
    }
    
    public List<BankStatement> DRBankStmttlistbydatesreconzero(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "DR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and (s.disburseType='Bills' or s.disburseType='Refund' or (s.disburseType='Misc' and s.disburseId IN (select v.uniqueId from MiscDisbursement v where v.amtCategory='Bills'))) and s.creditDebitFlag=:crFlag order by s.stmtId");

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
    
    public List<BankStatement> getDisbursedInterest(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "DR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and (s.disburseType='Interest' or (s.disburseType='Misc' and s.disburseId IN (select v.uniqueId from MiscDisbursement v where v.amtCategory='Interest'))) and s.creditDebitFlag=:crFlag order by s.stmtId");

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
    
    public List<BankStatement> getPrincipletransPSDF(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "DR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and (s.disburseType='PSDF' and s.disburseId IN (select v.slno from CsdfDetails v where v.amtCategory='Bills')) and s.creditDebitFlag=:crFlag order by s.stmtId");

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
    
    public List<BankStatement> getInteresttransPSDF(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "DR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and (s.disburseType='PSDF' and s.disburseId IN (select v.slno from CsdfDetails v where v.amtCategory='Interest')) and s.creditDebitFlag=:crFlag order by s.stmtId");

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
    
    public List<BankStatement> getForopenbalaccYear(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        List<BankStatement> obj = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified order by s.stmtId");

            query.setParameter("Verified", Verified);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            obj = query.list();
            
            tx.commit();
            session.flush();
            session.close();

            return obj;

        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }

        return obj;
    }
    
    public List<BankStatement> getForopenIntbalaccYear(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        List<BankStatement> list = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();

            String hql = "from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and ((s.creditDebitFlag='DR' and s.disburseType='Interest') or (s.creditDebitFlag='CR' and s.stmtId IN (select b.bankStatement.stmtId from MappingInterestBank b))) order by s.entryTime";
            Query query = session.createQuery(hql);
            query.setParameter("Verified", Verified);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            
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
    
    //===================================================================================
    
    public List<BankStatement> NewUnmappedBankStmtbydates(Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified = "Verified";
        String crFlag = "CR";
        
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BankStatement s where TO_DATE(s.amountDate) between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag and s.mappedAmount=:mappedAmount");

            query.setParameter("Verified", Verified);
            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            query.setParameter("mappedAmount", BigDecimal.ZERO);

            List<BankStatement> queryList = query.list();
                
            for (BankStatement e : queryList) {
                Hibernate.initialize(e.getCorporates());
            }
                
            return (List<BankStatement>) queryList;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
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
}
