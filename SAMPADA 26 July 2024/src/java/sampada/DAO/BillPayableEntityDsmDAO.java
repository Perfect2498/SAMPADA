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
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillReceiveEntityDsm;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class BillPayableEntityDsmDAO {
    
    
    public List<BillPayableEntityDsm> BillPayableCorpDsmlist(BigDecimal weekId, BigDecimal year, BigDecimal revisionNo) {

	        Session session = null; 
               
	        try {
	            session = HibernateUtil.getInstance().getSession();
                    Query query = session.createQuery("from BillPayableEntityDsm s where s.weekId=:weekId and s.billYear=:year and s.revisionNo=:revisionNo");
                     
                    query.setParameter("weekId", weekId);
                    query.setParameter("year", year);
                    query.setParameter("revisionNo", revisionNo);
                    List<BillPayableEntityDsm> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) {

	                return null;
	            } else {
	                System.out.println("list size" + queryList.size());
                        for(BillPayableEntityDsm e:queryList)
                        {
                            Hibernate.initialize(e.getCorporates());
                            Hibernate.initialize(e.getEntites());
                        }
	                return (List<BillPayableEntityDsm>) queryList;
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
            String hql="SELECT  CUR.ENTITY_ID, (CUR.CAPPING_CHARGES - PRV.CAPPING_CHARGES) CAPPING_CHARGES ,(CUR.ADDITIONAL_CHARGES - PRV.ADDITIONAL_CHARGES)ADDITIONAL_CHARGES, (CUR.SIGN_CHARGES - PRV.SIGN_CHARGES) SIGN_CHARGES ,(CUR.PAYABLE_CHARGES - PRV.PAYABLE_CHARGES) PAYABLE_CHARGES ,  (CUR.NET_DSM - PRV.NET_DSM)NET_DSM, (CUR.RECEIVABLE_CHARGES - PRV.RECEIVABLE_CHARGES) RECEIVABLE_CHARGES  FROM BILL_PAYABLE_ENTITY_DSM PRV INNER JOIN BILL_PAYABLE_ENTITY_DSM CUR ON PRV.REVISION_NO = CUR.REVISION_NO-1 WHERE CUR.WEEK_ID='"+weekId+"' AND CUR.BILL_YEAR='"+yearId+"' AND CUR.REVISION_NO='"+revNo+"' AND CUR.WEEK_ID=PRV.WEEK_ID AND CUR.ENTITY_ID=PRV.ENTITY_ID AND CUR.BILL_YEAR=PRV.BILL_YEAR";
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
    

    
       public List<Object[]> getTempDSMBillPayableDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo,BigDecimal yearid)
    {
        List<Object[]> list = null;
        
        Session session=null;
        try{
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           // String hql = "FROM TempBillPayaleEntityRras E where E.weekId=:weekId and revisionNo=: revisionNo";
          String hql="select * from temp_bill_payable_entity_dsm where week_id="+weekId+" and revision_no="+ revisionNo+"  and BILL_YEAR="+ yearid+"";

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
    
       
      
       
         public boolean NewBillPayDSMEntries(BillPayableEntityDsm dic) {
        
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
         
      public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM BILL_PAYABLE_ENTITY_DSM ";
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
      
      public boolean deleteBillPayableDSMEntitybyWeekIdRevNoYear(BigDecimal weekId, BigDecimal revisionNo,  BigDecimal year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
            //Owners p = (Owners) sess.load(Owners.class, new Integer(ownerid));
//              Query q=sess.createQuery("from owners");
//              sess.delete(q);
//                 
            String hql = "delete from BillPayableEntityDsm where weekId=:weekId and revisionNo=:revisionNo and billYear=:year";
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
