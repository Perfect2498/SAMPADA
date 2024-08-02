/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BillPayableCorp;
import sampada.util.HibernateUtil;

/**
 *
 * @author Kaustubh
 */
public class NewBillPayableCorpDAO {
    
    public List<BillPayableCorp> getDSMpayable(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillPayableCorp s where s.billYear='"+year+"' and s.billType='DSM' and s.weekId IN (select b.weekId from BillDsmDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillPayableCorp> list = queryList.list();
            
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
    
    public List<BillPayableCorp> getRRASpayable(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillPayableCorp s where s.billYear='"+year+"' and s.billType='RRAS' and s.weekId IN (select b.weekId from BillRrasDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillPayableCorp> list = queryList.list();
            
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
    
    public List<BillPayableCorp> getAGCpayable(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillPayableCorp s where s.billYear='"+year+"' and s.billType='AGC' and s.weekId IN (select b.weekId from BillAgcDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillPayableCorp> list = queryList.list();
            
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
    
        public List<BillPayableCorp> getPendingRefundPaylist() {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.pendingAmount!=0");
            List<BillPayableCorp> list = queryList.list();
            
            for (BillPayableCorp e : list) {
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
        
    public List<BillPayableCorp> getPendingBillPayableByCorp() {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and s.pendingAmount!=0 order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            list = query.list();
            for (BillPayableCorp e : list) {
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
    
    //===============================================================================================
    
    public List<BillPayableCorp> getAllBillsPayable(int fromweek, int toweek, int year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillPayableCorp s where s.weekId between "+fromweek+" and "+toweek+" and s.billYear="+year+" order by s.weekId asc");
            List<BillPayableCorp> list = queryList.list();
            
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
        
   //===========================================================================================
        
    public List<BillPayableCorp> getFRASpayable(String startdate, String enddate, String year) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillPayableCorp s where s.billYear='"+year+"' and s.billType='FRAS' and s.weekId IN (select b.weekId from BillFrasDetails b where b.weekFromdate between '"+startdate+"' and '"+enddate+"' or b.weekTodate between '"+startdate+"' and '"+enddate+"') order by s.weekId asc");
            List<BillPayableCorp> list = queryList.list();
            
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
    
    
    public List<BillPayableCorp> getAlldues(String date2) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where s.billingDate <= '"+date2+"' and s.pendingAmount!=0";
            Query query = session.createQuery(hql);
            list = query.list();
            for (BillPayableCorp e : list) {
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
    
    //====================================================================================
    
    public List<BillPayableCorp> getBetweenDueDates(String date1, String date2, String btype) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillPayableCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.billStatus!='REFUND' and s.billType='"+btype+"'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillPayableCorp e : list) {
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
    
    
    public List<BillPayableCorp> getRefundBetweenDueDates(String date1, String date2, String btype) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillPayableCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.billStatus='REFUND' and s.billType='"+btype+"'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillPayableCorp e : list) {
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
    
    public List<BigDecimal> getUniqueWeekIds(String billtype, String year) {
        
        List<BigDecimal> list = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            
            String hql = "select DISTINCT week_id from bill_payable_corp where bill_type='"+billtype+"' and bill_year='"+year+"'"
                    + "MINUS (select distinct week_id from bill_receive_corp where bill_type='"+billtype+"' and bill_year='"+year+"')"
                    + "UNION select DISTINCT week_id from bill_receive_corp where bill_type='"+billtype+"' and bill_year='"+year+"' order by week_id asc";
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
            
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
    
    public List<BillPayableCorp> getBetweenDueDates2(String date1, String date2) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillPayableCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.billStatus!='REFUND'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillPayableCorp e : list) {
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
    
    public List<BillPayableCorp> getRefundBetweenDueDates2(String date1, String date2) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String hql = "from BillPayableCorp s where s.billingDate between '"+date1+"' and '"+date2+"' and s.billStatus='REFUND'";
            Query query = session.createQuery(hql);
            list = query.list();
            
            for (BillPayableCorp e : list) {
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
    
    
    public List<BillPayableCorp> getPendingBillPayableByCorpId(int id) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where (s.billStatus='NOT PAID' OR s.billStatus='PARTIALLY') and s.pendingAmount!=0 and s.corporates.corporateId="+id+" order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            list = query.list();
            for (BillPayableCorp e : list) {
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
    
    public List<BillPayableCorp> getPendingRefundPaylistByCorpId(int id) {
        
        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            
            Query queryList = session.createQuery("from BillPayableCorp  e where e.billStatus='REFUND' and e.corporates.corporateId="+id+" and e.pendingAmount!=0");
            List<BillPayableCorp> list = queryList.list();
            
            for (BillPayableCorp e : list) {
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
