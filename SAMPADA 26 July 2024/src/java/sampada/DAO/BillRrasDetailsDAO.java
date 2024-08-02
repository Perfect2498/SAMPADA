/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BillRrasDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BillRrasDetailsDAO {
    
    public List<BillRrasDetails> BillRrasDetailsList(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo) {

	        Session session = null;                
	        try {
	            session = HibernateUtil.getInstance().getSession();
                    Query query = session.createQuery("from BillRrasDetails s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo");
                    query.setParameter("weekId", weekId);
                    query.setParameter("year", year);
                    query.setParameter("revisionNo", revisionNo);
                    List<BillRrasDetails> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) {

	                return null;
	            } else {
	                System.out.println("list size" + queryList.size());
	                return (List<BillRrasDetails>) queryList;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;

	        } finally {
	            session.close();
	        }

	    }
    
    
    
     public BigDecimal getMaxRevisionNum(BigDecimal weekId, BigDecimal year) 
    { 
        BigDecimal result = BigDecimal.ZERO;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select NVL(max(REVISION_NO),0) from BILL_RRAS_DETAILS where WEEK_ID='"+weekId+"' and BILL_YEAR='"+year+"'"; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                result = (BigDecimal)query.list().get(0); 
                System.out.println("result is "+result);
            }
            tx.commit();
            session.flush();
            session.close();
            return result;
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return result; 
    } 
    
     
    public List<Object[]> getTempBillRRASDetailsbyWeekId(BigDecimal weekId, BigDecimal yearId, BigDecimal revisionNo, String category) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select * from temp_bill_rras_details where week_id=" + weekId + " and revision_no=" + revisionNo + " and BILL_YEAR=" + yearId + " and BILL_CATEGORY='" + category + "' ";
            System.out.println("temp_bill_rras_details hql is "+hql);
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
    
      public boolean NewBillRRASDetailsEntries(BillRrasDetails dic) {
        
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
            if(tx!=null)
                tx.rollback();
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return false;
        
    }
    
      
          public int checkExistenceOfWeekIDandRevisionNo(BigDecimal weekid, BigDecimal revno, BigDecimal yearid, String category) {

        Session session = null;
        Transaction tx = null;
        int result = 0;
        System.out.println("Inside checkExistenceOfTrid" + weekid);
        try {
            session = HibernateUtil.getInstance().getSession();
            String hql = "Select * from BILL_RRAS_DETAILS WHERE WEEK_ID=" + weekid + " and REVISION_NO=" + revno + " and BILL_YEAR=" + yearid + " and BILL_CATEGORY='" + category + "'";
            SQLQuery q = session.createSQLQuery(hql);

            try {

                if (q.list().size() > 0) {
                    result = 1;
                } else {
                    result = 0;
                }
                System.out.println("result in dao is" + result);
                return result;
            } catch (Exception e) {
                System.out.println("Exception is" + e.getMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;

    }
          
    public List<Object[]> getAvailRrasBillListByWeekYear(BigDecimal weekid, BigDecimal year, String category) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select WEEK_ID, REVISION_NO, BILL_YEAR, TO_CHAR(BILL_DUE_DATE , 'YYYY-MM-DD') AS BILL_DUE_DATE, TO_CHAR(BILLING_DATE , 'YYYY-MM-DD') AS BILLING_DATE, TO_CHAR(ENTRY_DATE , 'YYYY-MM-DD hh:mm:ss') AS ENTRY_DATE FROM BILL_RRAS_DETAILS WHERE WEEK_ID=" + weekid + " AND BILL_YEAR=" + year + "  AND BILL_CATEGORY='" + category + "' ORDER BY REVISION_NO";
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
    
    public BigDecimal getMaxWeekIDRRAS(int year) 
    { 
        BigDecimal result = BigDecimal.ZERO;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select NVL(max(WEEK_ID),0) from BILL_RRAS_DETAILS where BILL_YEAR='"+year+"'"; 
            SQLQuery query = session.createSQLQuery(hql);
             //int length = query.list().size();
            if (query.list() != null) {
                result = (BigDecimal)query.list().get(0); 
                System.out.println("result is "+result);
            }
            tx.commit();
            session.flush();
            session.close();
            return result;
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return result; 
    } 
    
    public boolean deleteBillDetailsRRASbyWeekIdYearRevNoCategory(BigDecimal weekId, BigDecimal revisionNo,BigDecimal year, String category ) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
           //                 
            String hql = "delete from BillRrasDetails where weekId=:weekId and revisionNo=:revisionNo and billYear=:year and billCategory=:category ";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("category", category);
            query.setParameter("year", year);
//            query.setBigDecimal("weekId", weekId);
//            query.setBigDecimal("revisionNo", revisionNo);
            System.out.println(query.executeUpdate());
            tx.commit();
            sess.flush();
            sess.close();
            return true;
        } catch (NumberFormatException e) {
            if (tx != null) {
                tx.rollback();
            }
            if (sess != null) {
                sess.close();
            }
            e.printStackTrace();
        }
        return false;
    }
    
    public BigDecimal getLatestPayableAmountRRAS()

    {

        BigDecimal result = BigDecimal.ZERO;        

        Session session=null;

       

        try{

            

            session = HibernateUtil.getInstance().getSession();         

          

            String hql="select NVL(VARIABLE_CHARGES,0) from BILL_PAYABLE_ENTITY_RRAS s where s.revision_no =  ( select revision_no from MAX_PAYABLE_REVISION_NO where bill_type='RRAS' and s.week_id=week_id ) and s.week_id = (select max(week_id) from BILL_RRAS_DETAILS)";

            SQLQuery query = session.createSQLQuery(hql);

             //int length = query.list().size();

            if (query.list() != null) {

                result = (BigDecimal)query.list().get(0);

                System.out.println("result is "+result);

            }

        

            session.flush();

            session.close();

            return result;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return result;

    }

 

    public BigDecimal getLatestRecvAmountRRAS()

    {

        BigDecimal result = BigDecimal.ZERO;        

        Session session=null;

       

        try{

            

            session = HibernateUtil.getInstance().getSession();         

          

            String hql="select NVL(VARIABLE_CHARGES,0) from BILL_RECEIVE_ENTITY_RRAS s where s.revision_no =  ( select revision_no from MAX_RECEIVE_REVISION_NO where bill_type='RRAS' and s.week_id=week_id ) and s.week_id = (select max(week_id) from BILL_RRAS_DETAILS)";

            SQLQuery query = session.createSQLQuery(hql);

             //int length = query.list().size();

            if (query.list() != null) {

                result = (BigDecimal)query.list().get(0);

                System.out.println("result is "+result);

            }

        

            session.flush();

            session.close();

            return result;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return result;

    }
}
