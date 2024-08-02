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

import sampada.pojo.MappingInterestBank;

import sampada.util.HibernateUtil;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class MappingInterestBankDAO {

    public boolean NewMappingInterestBank(MappingInterestBank dic) {

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

            String hql = "select NVL(max(SLNO),0) FROM MAPPING_INTEREST_BANK ";

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

    public List<MappingInterestBank> getMappingInterestBankbyCorpID(int interestid) {

        Session session = null;

        int result = 0;

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingInterestBank s where s.interestid=:interestid and s.checkerStatus='Confirmed' order by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("interestid", interestid);

            list = query.list();

            for (MappingInterestBank e : list) {

                Hibernate.initialize(e.getInterestDetails());

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

    public List<MappingInterestBank> getPendingMappingInterestBank() {

        Session session = null;

        int result = 0;

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingInterestBank s where  s.checkerStatus='Pending' order by s.slno ASC";

            Query query = session.createQuery(hql);


            list = query.list();

            for (MappingInterestBank e : list) {

                Hibernate.initialize(e.getInterestDetails());

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

    
    public List<MappingInterestBank> getMappingInterestBankbydatestype(Date amtFrmDate, Date amtToDate, String type) {
        Session session = null;
        int result = 0;
        List<MappingInterestBank> list = null;
        BigDecimal bg = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingInterestBank s where  TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.interestDetails.billType=:type and s.checkerStatus='Confirmed' order by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            query.setParameter("type", type);

            list = query.list();

            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());

                Hibernate.initialize(e.getBankStatement());

                Hibernate.initialize(e.getInterestDetails().getCorporates());

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

    public List<MappingInterestBank> getMappingInterestBankbydates(Date amtFrmDate, Date amtToDate) {
        Session session = null;
        int result = 0;
        List<MappingInterestBank> list = null;
        BigDecimal bg = null;
        try {

            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingInterestBank s where  TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.interestDetails.billType=:type and s.checkerStatus='Verified' order by s.slno ASC";
            String hql = "from  MappingInterestBank s where  TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate  and s.checkerStatus='Confirmed' order by s.bankStatement.stmtId ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
//            query.setParameter("type", type);

            list = query.list();

            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());

                Hibernate.initialize(e.getBankStatement());

                Hibernate.initialize(e.getInterestDetails().getCorporates());

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

    public List<MappingInterestBank> getMappingInterestBankbyentrydates(Date amtFrmDate, Date amtToDate) {
        Session session = null;
        int result = 0;
        List<MappingInterestBank> list = null;
        BigDecimal bg = null;
        try {

            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingInterestBank s where  TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.interestDetails.billType=:type and s.checkerStatus='Verified' order by s.slno ASC";
            String hql = "from  MappingInterestBank s where  TO_DATE(s.entryDate) between :amtFrmDate and :amtToDate  and s.checkerStatus='Confirmed' order by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
//            query.setParameter("type", type);

            list = query.list();

            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());

                Hibernate.initialize(e.getBankStatement());

                Hibernate.initialize(e.getInterestDetails().getCorporates());

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
    
    public List<MappingInterestBank> getMappingInterestBankbybankids(BigDecimal fromid, BigDecimal toid) {
        Session session = null;
        int result = 0;
        List<MappingInterestBank> list = null;
        BigDecimal bg = null;
        try {

            session = HibernateUtil.getInstance().getSession();

//            String hql = "from  MappingInterestBank s where  TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate and s.interestDetails.billType=:type and s.checkerStatus='Verified' order by s.slno ASC";
//            String hql = "from  MappingInterestBank s where  s.bankStatement.stmtId between :fromid and :toid  and s.checkerStatus='Confirmed' order by s.bankStatement.stmtId ASC";
            String hql = "from  MappingInterestBank s where  s.slno between :fromid and :toid  and s.checkerStatus='Confirmed' order by s.slno ASC";

            Query query = session.createQuery(hql);

            query.setParameter("fromid", fromid);
            query.setParameter("toid", toid);
//            query.setParameter("type", type);

            list = query.list();

            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());

                Hibernate.initialize(e.getBankStatement());

                Hibernate.initialize(e.getInterestDetails().getCorporates());

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
    
    public int getBankSTmtIDforInterestID(int interestid, int stmtid, BigDecimal bankamt) {

        Session session = null;

        int result = 0;

        BigDecimal bg = null;

        Transaction tx;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update MAPPING_INTEREST_BANK set BANK_STMTID='" + stmtid + "', BANK_PENDING_AMOUNT='" + bankamt + "' where INTEREST_ID='" + interestid + "'";

            System.out.print("Update Mapping SQL :" + hql);

            SQLQuery query = session.createSQLQuery(hql);

            int length = query.executeUpdate();

            session.flush();

            tx.commit();

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

    public List<MappingInterestBank> getMappedAmountInterestBankbyInterstID(BigDecimal interestid) {

        Session session = null;

        int result = 0;

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingInterestBank s where s.interestDetails.interestId=:interestid and s.checkerStatus='Pending' ";

            Query query = session.createQuery(hql);

            query.setParameter("interestid", interestid);

            list = query.list();

            for (MappingInterestBank e : list) {

                Hibernate.initialize(e.getInterestDetails());

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

    public BigDecimal getSummedMappingInterestBankbyCorpID(int stmtid) {

        Session session = null;

        BigDecimal result = new BigDecimal(0);

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select NVL(sum(mapped_amount),0) from  Mapping_Interest_Bank s where s.checker_Status='Pending' and bank_stmtid='" + stmtid + "' ";

            SQLQuery query = session.createSQLQuery(hql);

            result = (BigDecimal) query.list().get(0);

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
    
    public BigDecimal getSummedMappingInterestBankbyAllPending() {

        Session session = null;

        BigDecimal result = new BigDecimal(0);

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select NVL(sum(mapped_amount),0) from  Mapping_Interest_Bank s where s.checker_Status='Pending'";

            SQLQuery query = session.createSQLQuery(hql);

            result = (BigDecimal) query.list().get(0);

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

    public BigDecimal getSummedMappingInterestBankbydates(Date amtFrmDate, Date amtToDate) {

        Session session = null;

        BigDecimal result = new BigDecimal(0);

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select NVL(sum(s.mappedAmount),0) from  MappingInterestBank s where s.checkerStatus='Confirmed' and TO_DATE(s.bankStatement.amountDate) between :amtFrmDate and :amtToDate  ";

//            SQLQuery query = session.createSQLQuery(hql);
            Query query = session.createQuery(hql);

            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);

            result = (BigDecimal) query.list().get(0);
            
            
            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());

                Hibernate.initialize(e.getBankStatement());

                Hibernate.initialize(e.getInterestDetails().getCorporates());

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

    public BigDecimal getSummedMappingInterestBankbybankids(BigDecimal fromid, BigDecimal toid) {

        Session session = null;

        BigDecimal result = new BigDecimal(0);

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select NVL(sum(s.mappedAmount),0) from  MappingInterestBank s where s.checkerStatus='Confirmed' and s.bankStatement.stmtId between :fromid and :toid  ";

//            SQLQuery query = session.createSQLQuery(hql);
            Query query = session.createQuery(hql);

            query.setParameter("fromid", fromid);
            query.setParameter("toid", toid);

            result = (BigDecimal) query.list().get(0);
            
            if(list!=null)
            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());

                Hibernate.initialize(e.getBankStatement());

                Hibernate.initialize(e.getInterestDetails().getCorporates());

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
    
    public BigDecimal getSummedMappingInterestBankbyCorpIDisZero() {

        Session session = null;

        BigDecimal result = new BigDecimal(0);

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select NVL(sum(mapped_amount),0) from  Mapping_Interest_Bank s where s.checker_Status='Pending' and bank_stmtid IS NULL ";

            SQLQuery query = session.createSQLQuery(hql);

            result = (BigDecimal) query.list().get(0);

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

    public List<MappingInterestBank> getCheckerPendingInterestCountForCorporate(int corpId) {

        Session session = null;

        int result = 0;

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from MappingInterestBank s where s.bankStatement.corporates.corporateId=:corpId and s.checkerStatus='Pending' ";

            Query query = session.createQuery(hql);

            query.setParameter("corpId", corpId);

            list = query.list();

            for (MappingInterestBank e : list) {

                Hibernate.initialize(e.getInterestDetails());

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

    public void deleteobj(int slno) { 

        Session session = null; 

        Transaction tx = null; 

        try { 

            session = HibernateUtil.getInstance().getSession(); 
            tx = session.beginTransaction(); 

            String hql = "delete from  MappingInterestBank where slno=" + slno + " "; 

            System.out.println(" is " + hql); 

            Query query = session.createQuery(hql); 

            query.executeUpdate(); 

            tx.commit(); 

            session.flush(); 
            session.close(); 

            return; 

        } catch (Exception e) { 

            if (session != null) { 
                session.close(); 
            } 

            e.printStackTrace(); 

        } 

    }
    
    public int updateCheckerStatusMappingInterestBankbyCorp(int interestID) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update  Mapping_Interest_Bank set checker_Status='Confirmed' where interest_Id='" + interestID + "'  ";

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

    public List<MappingInterestBank> getMappingInterestBankbyonlyCorpID(int interestid, String year) {

        Session session = null;

        int result = 0;

        List<MappingInterestBank> list = null;

        BigDecimal bg = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from  MappingInterestBank s where s.interestDetails.corporates.corporateId=:interestid and s.checkerStatus='Confirmed' and to_char(s.entryDate,'YYYY')=:year order by s.slno ASC";
            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("interestid", interestid);
            query.setParameter("year", year);

            list = query.list();
            System.out.println("hql size is " + list.size());
            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getInterestDetails().getCorporates());
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

    public int getDeletedMappingInterestBankbyinterestid(int corpid) {
        int result = 0;
        Session session = null;
        Transaction tx;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "delete from MAPPING_INTEREST_BANK where INTEREST_ID='" + corpid + "' ";
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

    public List<Timestamp> getInterestmappingbyCorpgroupbyEntryDateTimestamp(int corpid, int year) {

        List<Timestamp> list = null;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            //          String hql = "select distinct(TRUNC(s.paid_date)) from INTEREST_DETAILS s,CORPORATES c where  s.CORPORATE_ID=c.CORPORATE_ID and s.CORPORATE_ID='"+corpid+"' and  CHECKER_STATUS='Verified'   order by TRUNC(s.paid_date) ASC";
            String hql = "select distinct(TRUNC(s.ENTRY_DATE)) from MAPPING_INTEREST_BANK s,INTEREST_DETAILS c where  s.INTEREST_ID=c.INTEREST_ID and c.CORPORATE_ID='" + corpid + "' and  s.CHECKER_STATUS='Confirmed' and extract(YEAR from s.ENTRY_DATE)='" + year + "'  order by TRUNC(s.ENTRY_DATE) ASC";

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
    
    //====================================================================================
    
    public BigDecimal getopenIntBalStmtbyid(int fromid, int toid) {

        Session session = null;
        BigDecimal bg = BigDecimal.ZERO;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "from MappingInterestBank where bankStatement.stmtId='"+fromid+"' and CREDIT_DEBIT_FLAG='CR'";
            
            System.out.println("getopenbalBankStmtbyverifieddates hql " + hql);
            Query query = session.createQuery(hql);
            MappingInterestBank obj = (MappingInterestBank) query.list().get(0);

            bg = bg.add(obj.getAfter_int_pool().subtract(obj.getMappedAmount()));
            
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
    
    public int updateInterestPoolAmount(int interestID, BigDecimal amt,BigDecimal poolamt) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update  Mapping_Interest_Bank set AFTER_INT_BAL="+amt+" , POOL_BAL="+poolamt+" where SLNO='" + interestID + "'  ";

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
    
    //===============================================================================
    
    public List<MappingInterestBank> getPendingMappingInterestBankAtChecker() {

        Session session = null;

        List<MappingInterestBank> list = null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from MappingInterestBank s where s.checkerStatus='Pending' and s.after_int_pool=null order by s.slno ASC";

            Query query = session.createQuery(hql);

            list = query.list();

            for (MappingInterestBank e : list) {

                Hibernate.initialize(e.getInterestDetails());
                Hibernate.initialize(e.getInterestDetails().getCorporates());
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

}
