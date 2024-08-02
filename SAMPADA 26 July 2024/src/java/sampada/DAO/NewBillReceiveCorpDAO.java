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
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillReceiveCorp;
import sampada.util.HibernateUtil;
/**
 *
 * @author Kaustubh
 */
public class NewBillReceiveCorpDAO {
    
    public List<BillReceiveCorp> getPendingRefundBillReceiveList() {
        List<BillReceiveCorp> list = null;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from BillReceiveCorp s where s.disburseStatus='REFUND' and s.pendingAmount!=0 order by s.uniqueId ASC";
            System.out.println("Refund hql is " + hql);

            Query query = session.createQuery(hql);

            list = query.list();

            for (BillReceiveCorp e : list) {

                Hibernate.initialize(e.getCorporates());

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
    
    public List<BillReceiveCorp> getDSMreceive(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillReceiveCorp s where s.billYear='"+year+"' and s.billType='DSM' and s.weekId IN (select b.weekId from BillDsmDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillReceiveCorp> list = queryList.list();
            
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
    
    public List<BillReceiveCorp> getRRASreceive(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillReceiveCorp s where s.billYear='"+year+"' and s.billType='RRAS' and s.weekId IN (select b.weekId from BillRrasDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillReceiveCorp> list = queryList.list();
            
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
    
    public List<BillReceiveCorp> getAGCreceive(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillReceiveCorp s where s.billYear='"+year+"' and s.billType='AGC' and s.weekId IN (select b.weekId from BillAgcDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillReceiveCorp> list = queryList.list();
            
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
    
    public List<BillReceiveCorp> getPendingRefundRecList() {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillReceiveCorp s where (s.disburseStatus='NOT PAID' OR s.disburseStatus='PARTIALLY') and s.pendingAmount>0 order by s.weekId ASC");
            List<BillReceiveCorp> list = queryList.list();
            
            for (BillReceiveCorp e : list) {
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
    
    //============================================================================
    
    public List<BillReceiveCorp> getAllBillsReceive(int fromweek, int toweek, int year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillReceiveCorp s where s.weekId between "+fromweek+" and "+toweek+" and s.billYear="+year+" order by s.weekId asc");
            List<BillReceiveCorp> list = queryList.list();
            
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
    
    //=======================================================================================
    
    public List<BillReceiveCorp> getFRASreceive(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillReceiveCorp s where s.billYear='"+year+"' and s.billType='FRAS' and s.weekId IN (select b.weekId from BillFrasDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillReceiveCorp> list = queryList.list();
            
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
    
    
    public List<BillReceiveCorp> getAlldues(String date2){
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillReceiveCorp s where s.billingDate <= '"+date2+"' and s.pendingAmount!=0";
            Query query = session.createQuery(hql);
            list = query.list();
            for (BillReceiveCorp e : list) {
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
    
    //=================================================================================
    
    public List<BillReceiveCorp> getBetweenDueDates(String date1, String date2, String btype) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillReceiveCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.disburseStatus!='REFUND' and s.billType='"+btype+"'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillReceiveCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
                
            tx.commit();
            session.flush();
            session.close();
            return list;
        } 
        catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<BillReceiveCorp> getRefundBetweenDueDates(String date1, String date2, String btype) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillReceiveCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.disburseStatus='REFUND' and s.billType='"+btype+"'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillReceiveCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
                
            tx.commit();
            session.flush();
            session.close();
            return list;
        } 
        catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
    
    public List<BillReceiveCorp> getBetweenDueDates2(String date1, String date2) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillReceiveCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.disburseStatus!='REFUND'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillReceiveCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
                
            tx.commit();
            session.flush();
            session.close();
            return list;
        } 
        catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
        
    public List<BillReceiveCorp> getRefundBetweenDueDates2(String date1, String date2) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillReceiveCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.disburseStatus='REFUND'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillReceiveCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
                
            tx.commit();
            session.flush();
            session.close();
            return list;
        } 
        catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<BillReceiveCorp> getPendingRefundBillReceiveListByCorpId(int id) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "from BillReceiveCorp s where s.disburseStatus='REFUND' and s.corporates.corporateId="+id+"  and s.pendingAmount!=0 order by s.uniqueId ASC";
            System.out.println("Refund hql is " + hql);

            Query query = session.createQuery(hql);

            list = query.list();

            for (BillReceiveCorp e : list) {

                Hibernate.initialize(e.getCorporates());

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
    
    
    public List<BillReceiveCorp> getPendingRefundRecListByCorpId(int id) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillReceiveCorp s where (s.disburseStatus='NOT PAID' OR s.disburseStatus='PARTIALLY') and s.corporates.corporateId="+id+" and s.pendingAmount>0 order by s.weekId ASC");
            List<BillReceiveCorp> list = queryList.list();
            
            for (BillReceiveCorp e : list) {
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
}
