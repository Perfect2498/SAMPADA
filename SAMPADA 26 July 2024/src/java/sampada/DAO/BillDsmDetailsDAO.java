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
import sampada.pojo.BillDsmDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BillDsmDetailsDAO {
    public List<BillDsmDetails> BillDsmDetailsList(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo) {

	        Session session = null;                
	        try {
	            session = HibernateUtil.getInstance().getSession();
                    Query query = session.createQuery("from BillDsmDetails s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo");
                    query.setParameter("weekId", weekId);
                    query.setParameter("year", year);
                    query.setParameter("revisionNo", revisionNo);
                    List<BillDsmDetails> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) {

	                return null;
	            } else {
	                System.out.println("list size" + queryList.size());
	                return (List<BillDsmDetails>) queryList;
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
            String hql="select NVL(max(REVISION_NO),0) from BILL_DSM_DETAILS where WEEK_ID='"+weekId+"' and BILL_YEAR='"+year+"'"; 
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
    
    public BigDecimal getMaxWeekIDDSM(int year) 
    { 
        BigDecimal result = BigDecimal.ZERO;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            System.out.println("year is "+year);
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select NVL(max(WEEK_ID),0) from BILL_DSM_DETAILS where BILL_YEAR='"+year+"'"; 
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
      public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM BILL_DSM_DETAILS ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal)query.list().get(0);
                System.out.print("Max UNIQUE ID is "+bg);
                result=bg.intValueExact();
                System.out.print("Max UNIQUE ID is INT "+bg);
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
      
      
    public List<Object[]> getTempDSMBillDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo,BigDecimal yearId )
    {
        List<Object[]> list = null;
        
        Session session=null;
        try{
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           // String hql = "FROM TempBillPayaleEntityRras E where E.weekId=:weekId and revisionNo=: revisionNo";
          String hql="select * from temp_bill_dsm_details where week_id="+weekId+" and revision_no="+ revisionNo+" and BILL_YEAR="+ yearId+"";

            SQLQuery query = session.createSQLQuery(hql); 
          //  SqlQuery query = session.createQuery(hql);
//            query.setParameter("weekId",weekId);
         //      query.setParameter("revisionNo",revisionNo);
            list=query.list();            
            session.close();
            return list;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return list;
    }
    
       
        public boolean NewBillDSMDetailsEntries(BillDsmDetails dic) {
        
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
        
    //    select WEEK_ID, REVISION_NO, BILL_YEAR, BILL_DUE_DATE, BILLING_DATE FROM SAMPADA_WRLDC.BILL_DSM_DETAILS WHERE WEEK_ID=26 AND BILL_YEAR=2019;

     public List<Object[]> getAvailDsmBillListByWeekYear(BigDecimal weekid,BigDecimal  year)
    {
        List<Object[]> list = null;
        
        Session session=null;
        try{
            
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
         
            String hql="select WEEK_ID, REVISION_NO, BILL_YEAR, TO_CHAR(BILL_DUE_DATE , 'YYYY-MM-DD') AS BILL_DUE_DATE, TO_CHAR(BILLING_DATE , 'YYYY-MM-DD') AS BILLING_DATE, TO_CHAR(ENTRY_DATE , 'YYYY-MM-DD hh:mm:ss') AS ENTRY_DATE FROM BILL_DSM_DETAILS WHERE WEEK_ID="+weekid+" AND BILL_YEAR="+year+" ORDER BY REVISION_NO";
            System.out.println("hql is "+hql);
            SQLQuery query = session.createSQLQuery(hql); 
       
            list=query.list();            
            session.close();
            return list;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return list;
    }
     
     public boolean deleteBillDSMDetailsbyWeekIdRevNoYear(BigDecimal weekId, BigDecimal revisionNo,BigDecimal yearid) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
                           
            String hql = "delete from BillDsmDetails where weekId=:weekId and billYear=:yearid and revisionNo=:revisionNo";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("yearid", yearid);
            query.setBigDecimal("revisionNo", revisionNo);
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
     
     public BigDecimal getLatestPayableAmountDSM()

    {

        BigDecimal result = BigDecimal.ZERO;        

        Session session=null;

       

        try{

            

            session = HibernateUtil.getInstance().getSession();         

          

            String hql="select PAYABLE_CHARGES from BILL_PAYABLE_ENTITY_DSM s where s.revision_no =  ( select revision_no from MAX_PAYABLE_REVISION_NO where bill_type='DSM' and s.week_id=week_id ) and s.week_id = (select max(week_id) from BILL_DSM_DETAILS)";

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
