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

import sampada.pojo.MappingInterestBank;

import sampada.pojo.MappingRefundBank;

import sampada.util.HibernateUtil;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class MappingRefundBankDAO {

    public boolean NewMappingRefundBank(MappingRefundBank dic) {

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
            String hql = "select NVL(max(SLNO),0) FROM MAPPING_REFUND_BANK ";
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

    public List<MappingRefundBank> getMappingRefundBankbyCorpID(int corpid) {

        Session session = null;

        int result = 0;

        List<MappingRefundBank> list = null;

        BigDecimal bg = null;

        try {

           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingRefundBank s where s.checkerStatus='Pending' and s.tempRefundBillCorp.corporates.corporateId=:corpid order by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("corpid", corpid);
            list = query.list();

            for (MappingRefundBank e : list) {

                Hibernate.initialize(e.getTempRefundBillCorp());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());
                Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());

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

    public List<MappingRefundBank> getMappingRefundBankbydatestype(Date amtFrmDate, Date amtToDate , String type) {

        Session session = null;

        int result = 0;

        List<MappingRefundBank> list = null;

        BigDecimal bg = null;

        try {

           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified' and s.tempRefundBillCorp.billReceiveCorp.billType=:type and TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate order by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            query.setParameter("type", type);
            list = query.list();

            for (MappingRefundBank e : list) {

                Hibernate.initialize(e.getTempRefundBillCorp());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());
                Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());

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
    
    public List<MappingRefundBank> getMappingRefundBankbyentrydatesforsrasandtras(Date amtFrmDate, Date amtToDate) {

        Session session = null;

        int result = 0;

        List<MappingRefundBank> list = null;

        BigDecimal bg = null;

        try {

           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified' and TO_DATE(s.entryDate) between :amtFrmDate and :amtToDate  and (s.tempRefundBillCorp.billReceiveCorp.billType='SRAS'  or s.tempRefundBillCorp.billReceiveCorp.billType='TRASM'  or s.tempRefundBillCorp.billReceiveCorp.billType='TRASS' or s.tempRefundBillCorp.billReceiveCorp.billType='TRASE'     )order  by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            list = query.list();

            for (MappingRefundBank e : list) {

                Hibernate.initialize(e.getTempRefundBillCorp());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());
                Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());

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


    public List<MappingRefundBank> getMappingRefundBankbydates(Date amtFrmDate, Date amtToDate ) {

        Session session = null;

        int result = 0;

        List<MappingRefundBank> list = null;

        BigDecimal bg = null;

        try {

           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified' and s.tempRefundBillCorp.billReceiveCorp.billType=:type and TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate order by s.slno ASC";
            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified'  and TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate order by s.bankStatement.stmtId ASC";

            
            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
//            query.setParameter("type", type);
            list = query.list();

            for (MappingRefundBank e : list) {

                Hibernate.initialize(e.getTempRefundBillCorp());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());
                Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());

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
    
    public List<MappingRefundBank> getMappingRefundBankbybankids(BigDecimal Fromid, BigDecimal toid ) {

        Session session = null;

        int result = 0;

        List<MappingRefundBank> list = null;

        BigDecimal bg = null;

        try {

           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified' and s.tempRefundBillCorp.billReceiveCorp.billType=:type and TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate order by s.slno ASC";
            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified'  and (s.bankStatement.stmtId between :Fromid and :toid) and (s.poolBal is null) order by s.bankStatement.stmtId ASC";

            
            Query query = session.createQuery(hql);

            query.setParameter("Fromid", Fromid);
            query.setParameter("toid", toid);
//            query.setParameter("type", type);
            list = query.list();

            for (MappingRefundBank e : list) {

                Hibernate.initialize(e.getTempRefundBillCorp());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());
                Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());

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
    
    
    public List<MappingRefundBank> getMappingRefundBankbyids(BigDecimal Fromid, BigDecimal toid ) {

        Session session = null;

        int result = 0;

        List<MappingRefundBank> list = null;

        BigDecimal bg = null;

        try {

           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified' and s.tempRefundBillCorp.billReceiveCorp.billType=:type and TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate order by s.slno ASC";
            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified'  and (s.slno between :Fromid and :toid) and (s.poolBal is not null)  order by s.slno ASC";

            
            Query query = session.createQuery(hql);

            query.setParameter("Fromid", Fromid);
            query.setParameter("toid", toid);
//            query.setParameter("type", type);
            list = query.list();

            for (MappingRefundBank e : list) {

                Hibernate.initialize(e.getTempRefundBillCorp());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());
                Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());

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
    
    public int getUpdatedMappingRefundBankbyCorp(int refundId, String status) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update  MAPPING_REFUND_BANK set checker_Status='" + status + "' where SLNO='" + refundId + "' ";
            System.out.println(" is " + hql);
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

    public List<BigDecimal> getSTmtIDMappingRefundBankbyCorpID() {

        Session session = null;

        int result = 0;

        List<BigDecimal> list = null;

        BigDecimal bg = null;

        try {

           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select distinct BANK_STMTID from  Mapping_Refund_Bank  where checker_Status='Pending'  order by BANK_STMTID ASC";

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

    public int getDeleteRefundbyCheckerforCorpID(int refundis) {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "delete from Mapping_Refund_Bank  where refund_id='" + refundis + "' and checker_Status ='Pending'";

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

    public List<MappingRefundBank> getMappingRefundbyCorpID(int corpid, String year) {
        Session session = null;
        int result = 0;
        List<MappingRefundBank> list = null;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();

            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified' and s.tempRefundBillCorp.corporates.corporateId=:corpid and to_char(s.tempRefundBillCorp.checkerDate,'YYYY')=:year order by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("corpid", corpid);
            query.setParameter("year", year);

            list = query.list();
            for (MappingRefundBank e : list) {
                Hibernate.initialize(e.getTempRefundBillCorp());

                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());
                Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());

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
    
    //=======================================================================================
    
    public MappingRefundBank getMappingRefundBankbyUid(BigDecimal uid) {

        Session session = null;
        List<MappingRefundBank> list = null;

        try {
           //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingRefundBank s where s.checkerStatus='Verified' and s.tempRefundBillCorp.billReceiveCorp.uniqueId=:uid";

            Query query = session.createQuery(hql);

            query.setParameter("uid", uid);
            list = query.list();

            for (MappingRefundBank e : list) {
                //Hibernate.initialize(e.getTempRefundBillCorp());
                Hibernate.initialize(e.getBankStatement());
                //Hibernate.initialize(e.getTempRefundBillCorp().getBillReceiveCorp());
                //Hibernate.initialize(e.getTempRefundBillCorp().getCorporates());
            }

            return list.get(0);
        }
        catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return list.get(0);

    }

    
    public int getUpdatedMappingRefundBankbypoolagcbal(BigDecimal mainpool, BigDecimal typebal,int refundId) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update  MAPPING_REFUND_BANK set POOL_AGC_BAL='" + typebal + "' ,POOL_BAL='" + mainpool + "', ENTRY_TIME=systimestamp where SLNO='" + refundId + "' ";
            System.out.println(" is " + hql);
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
    
    public int getUpdatedMappingRefundBankbypoolrrasbal(BigDecimal mainpool, BigDecimal typebal,int refundId) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update  MAPPING_REFUND_BANK set POOL_RRAS_BAL='" + typebal + "' ,POOL_BAL='" + mainpool + "', ENTRY_TIME=systimestamp where SLNO='" + refundId + "' ";
            System.out.println(" is " + hql);
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
    
}
