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
import sampada.pojo.Corporates;
import sampada.pojo.LetterOfCredit;
import sampada.util.HibernateUtil;

/**
 *
 * @author shubham
 */
public class LetterOfCreditDAO {
    
    
     public boolean NewLoCDetails(LetterOfCredit loc) {

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
    
     public int getMaxLCID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select max(LC_ID) FROM LETTER_OF_CREDIT ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Max LC ID is " + bg);
                result = bg.intValueExact();
                System.out.print("Max LC ID is INT " + bg);
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
    
     
      public int getMaxLCCondition(String consname,String finYear) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select max(LC_CONDITION) FROM LETTER_OF_CREDIT WHERE CONSTITUENT='"+consname+"' and FIN_YEAR='"+finYear+"'";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Max LC Condition ID is " + bg);
                result = bg.intValueExact();
                System.out.print("Max LC  Condition ID is INT " + bg);
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
    
     
     
     
      public int checkExistenceOfLCamount(String consname,String finYear) {

        Session session = null;
        Transaction tx = null;
       
        int result=0;
        System.out.println("Inside check Existence Of LCamount");
        try {
            session = HibernateUtil.getInstance().getSession();            
            String hql = "Select * from LETTER_OF_CREDIT WHERE CONSTITUENT='"+consname+"' and FIN_YEAR='"+finYear+"'";            
            SQLQuery q = session.createSQLQuery(hql);
           
            try {

                if(q.list().size()>0){
                    result=1;
                }
                else{
                    result=0;
                }
                System.out.println("result in dao is"+result);
                return result;
            } catch (Exception e) {
                System.out.println("Exception is" + e.getMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;

    }
     
      
       public boolean updateExpFlagLOCNameandFinYear(String consname,String finYear)
    {
        boolean flag=true;
        Session session = null;
        Transaction tx;
        int max=0;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            System.out.println("Inside Update Exp Flag of Letter of Credit");
            String hql = "update LETTER_OF_CREDIT set EXP_FLAG='1' where CONSTITUENT='"+consname+"' and FIN_YEAR='"+finYear+"'";            
            SQLQuery q = session.createSQLQuery(hql);
            q.executeUpdate();
            session.flush();
            tx.commit();
            session.close();
            return flag;
            
        } catch (Exception e) {
            if(session!=null)
                session.close();
            e.printStackTrace();
        }        
        return flag;
    }
       
       
        public List<LetterOfCredit> letterOfCreditList() {
        Session session = null;
        List<LetterOfCredit> queryList;
        try {
            System.out.print("Inside Letter of credit DAO");
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from LetterOfCredit s order by s.constituent");
            queryList = query.list();
            return queryList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
        
        public List<LetterOfCredit> lcListbyid(BigDecimal lcId) {
        Session sess = null;
        List<LetterOfCredit> list = null;

        try {
            sess = HibernateUtil.getInstance().getSession();
            Query query = sess.createQuery("from LetterOfCredit where lcId= :lc_id order by constituent ");
            query.setParameter("lc_id", lcId);
            list = query.list();
//            for (Utilities cp : list) {
//               Hibernate.initialize(cp.getUtilityType());
//                 Hibernate.initialize(cp.getSldcStu());
//                Hibernate.initialize(cp.getSldcStu().getElementsBySldcElementid());
//                Hibernate.initialize(cp.getSldcStu().getElementsByStuElementid());
//                Hibernate.initialize(cp.getOwners());
//                   Hibernate.initialize(cp.getRegion());
//                
//                
//
//            }

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Exception is" + e.getMessage());

            return null;

        } finally {

            sess.close();

        }
        return list;
    }

        
         public BigDecimal getPresentLCAmountbyId(int corpId,String year) 
    { 
        BigDecimal entityName = BigDecimal.ZERO;          
        Session session=null; 
        Transaction tx = null; 
        try{ 
             
            session = HibernateUtil.getInstance().getSession();           
            tx = session.beginTransaction(); 
           // String hql="select ENTITTY_NAME from ENTITES where ENTITY_ID='"+entityId+"' "; 
            String hql="select sum(LC_AMOUNT) from LETTER_OF_CREDIT where CORPORATE_ID='"+corpId+"' and YEAR='"+year+"'  "; 
           // select lc_amount  from letter_of_credit where corporate_id=1 and exp_flag=0 and year=2019 and lc_id=(select max(lc_id) from letter_of_credit where corporate_id=1 ) 
            SQLQuery query = session.createSQLQuery(hql); 
             int length = query.list().size(); 
            if (length != 0) { 
                entityName = (BigDecimal)query.list().get(0); 
                System.out.println("LC Amount is "+entityName); 
            } 
            tx.commit(); 
            session.flush(); 
            session.close(); 
            return entityName; 
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return entityName; 
    } 
   
        
}
