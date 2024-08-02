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
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityFras;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BillEntityFrasDAO {
    
    public List<BillEntityFras> BillCorpFraslist(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo) {

	        Session session = null; 
               
	        try {
	            session = HibernateUtil.getInstance().getSession();
                    Query query = session.createQuery("from BillEntityFras s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo");
                     
                    query.setParameter("weekId", weekId);
                    query.setParameter("year", year);
                    query.setParameter("revisionNo", revisionNo);
                    List<BillEntityFras> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) {

	                return null;
	            } else {
	                System.out.println("list size" + queryList.size());
                        for(BillEntityFras e:queryList)
                        {
                            Hibernate.initialize(e.getCorporates());
                            Hibernate.initialize(e.getEntites());
                        }
	                return (List<BillEntityFras>) queryList;
	            }

	        } catch (Exception e) {

	            e.printStackTrace();

	            return null;

	        } finally {

	            session.close();

	        }

	    }
    
    public List<Object[]> getDiffBillPay(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo)
    {
        List<Object[]> list = null;        
        Session session=null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getInstance().getSession();         
            tx = session.beginTransaction();
            String hql="SELECT  CUR.ENTITY_ID, (CUR.UP_REGULATION - PRV.UP_REGULATION) UP_REGULATION ,(CUR.DOWN_REGULATION - PRV.DOWN_REGULATION) DOWN_REGULATION, (CUR.MARKUP_CHARGES - PRV.MARKUP_CHARGES) MARKUP_CHARGES FROM BILL_ENTITY_FRAS PRV INNER JOIN BILL_ENTITY_FRAS CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='"+weekId+"' AND CUR.BILL_YEAR='"+yearId+"' AND CUR.REVISION_NO='"+revNo+"' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID";
            System.out.println("hql is "+hql);
            SQLQuery query = session.createSQLQuery(hql);             
            list=query.list(); 
            tx.commit();           
            session.flush();
            session.close();
            return list;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return list;
    }
    

    
    public List<Object[]> getTempFRASBillDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo,BigDecimal yearid)
    {
        List<Object[]> list = null;
        
        Session session=null;
        try{
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
          String hql="select * from temp_bill_entity_fras where week_id="+weekId+" and revision_no="+ revisionNo+"  and BILL_YEAR="+ yearid+"";

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
    
       
      
       
         public boolean NewBillFRASEntries(BillEntityFras dic) {
        
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
         
      public int getMaxUniqueIDFras() {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM BILL_ENTITY_FRAS ";
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
      
      public List<Object[]> getAllSumBill(BigDecimal weekId, BigDecimal yearId, BigDecimal revNo) {
        List<Object[]> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select sum(UP_REGULATION), sum(DOWN_REGULATION), sum(MARKUP_CHARGES) from BILL_ENTITY_FRAS WHERE WEEK_ID='" + weekId + "' and BILL_YEAR='" + yearId + "' and REVISION_NO='" + revNo + "' ";
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
      
      public boolean deleteBillEntityFRASbyWeekIdRevNoYear(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
                 
            String hql = "delete from BillEntityFras where weekId=:weekId and revisionNo=:revisionNo and billYear=:year";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("revisionNo", revisionNo);
            query.setBigDecimal("year", year);
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
    
}
