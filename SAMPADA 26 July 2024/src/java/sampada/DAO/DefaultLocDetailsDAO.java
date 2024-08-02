/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package sampada.DAO;
 
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.DefaultLocDetails;
import sampada.util.HibernateUtil;
 
/**
*
* @author JaganMohan
*/
public class DefaultLocDetailsDAO {
   
    
    public List<Object[]> getListForDefaultLC() {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select a.BILL_YEAR,a.TOTALNET,a.UNIQUE_ID,a.BILL_DUE_DATE,a.WEEK_ID,a.BILL_CATEGORY, a.BILL_PRIORITY ,max(b.entry_date),a.CORPORATE_ID from BILL_PAYABLE_CORP a , MAPPING_BILL_BANK b where a.UNIQUE_ID=b.UNIQUE_BILLPAY group by a.BILL_YEAR,a.TOTALNET,a.UNIQUE_ID,a.BILL_DUE_DATE,a.WEEK_ID,a.BILL_CATEGORY, a.BILL_PRIORITY ,b.entry_date,a.CORPORATE_ID ";
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
 
     public int getMaxSLNO() {
        int result = 0;
        BigDecimal bg = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {
 
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select NVL(max(SLNO),0) from DEFAULT_LOC_DETAILS";
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
 
     public boolean NewDefaultLoCDetails(DefaultLocDetails loc) {
 
        Session session = null;
 
        Transaction tx = null;
 
        try {
 
            session = HibernateUtil.getInstance().getSession();
 
            tx = session.beginTransaction();
 
            session.save(loc);
 
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
    
     public List<DefaultLocDetails> getAllPendingListForDefaultLC() {
        List<DefaultLocDetails> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from DefaultLocDetails s where s.locFlag='0' ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            list = query.list();
            if(list.size()>0)
            {
                for(DefaultLocDetails e:list)
                {
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getCorporates());
                }
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
 
     public int getUpdateLOCFlag(int defaulid) {
        int result = 0;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update DEFAULT_LOC_DETAILS set LOC_FLAG='1' where slno='"+defaulid+"'";
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
    
     
     public List<DefaultLocDetails> getListForDefaultLCbyUniqueID(int billpayUniuqeID) {
        List<DefaultLocDetails> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from DefaultLocDetails s where s.billPayableCorp.uniqueId=:baiipaunid";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("baiipaunid", new BigDecimal(billpayUniuqeID));
            list = query.list();
            if(list.size()>0)
            {
                for(DefaultLocDetails e:list)
                {
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getCorporates());
                }
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
     public List<DefaultLocDetails> getAllListForDefaultLC() {
        List<DefaultLocDetails> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from DefaultLocDetails ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            list = query.list();
            if(list.size()>0)
            {
                for(DefaultLocDetails e:list)
                {
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getCorporates());
                }
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
     
     //================================================================================
     
     public List<DefaultLocDetails> getLCDetailsByWeek(int weekfrom, int weekto, int year, String billtype) {
        List<DefaultLocDetails> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from DefaultLocDetails s where s.weekid between "+weekfrom+" and "+weekto+" and s.billYear="+year+" and s.billType='"+billtype+"'";
            Query query = session.createQuery(hql);
            
            list = query.list();
            if(list.size()>0)
            {
                for(DefaultLocDetails e:list)
                {
                    Hibernate.initialize(e.getBillPayableCorp());
                    Hibernate.initialize(e.getCorporates());
                }
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
    
}