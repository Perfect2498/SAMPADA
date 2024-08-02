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
import sampada.pojo.Entites;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class EntityDAO {
    
    public String getEntityNameById(BigDecimal entityId) 
    { 
        String entityName = null;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select ENTITTY_NAME from ENTITES where ENTITY_ID='"+entityId+"' "; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                entityName = (String)query.list().get(0); 
                System.out.println("entityName is "+entityName);
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
    
    public String getEntityTypeById(BigDecimal entityId) 
    { 
        String entityName = null;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select ENTITY_TYPE from ENTITES where ENTITY_ID='"+entityId+"' "; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                entityName = (String)query.list().get(0); 
                System.out.println("entityType is "+entityName);
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
    
    public int getEntityIDbyName(String entityName) {
        int dicid = 0;
        BigDecimal Dic_id = BigDecimal.ZERO;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "Select NVL(entity_id,0) FROM ENTITES where entitty_name='" + entityName + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list().get(0) != null) {
                Dic_id = (BigDecimal) query.list().get(0);
                dicid = Dic_id.intValue();
            }
            session.close();
            return dicid;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return dicid;
    }

public int getCorporateIDbyName(String entityName) {
        int corporateid = 0;
        BigDecimal Corporate_id = BigDecimal.ZERO;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "Select corporate_id FROM ENTITES where entitty_name='" + entityName + "'";
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list().get(0) != null) {
                Corporate_id = (BigDecimal) query.list().get(0);
                corporateid = Corporate_id.intValue();

            }
            session.close();
            return corporateid;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return corporateid;
    }
   
    
    
    public int checkExistenceOfEntityName(String entityName) {

        Session session = null;
        int result=0;
        
        try {
            session = HibernateUtil.getInstance().getSession();  
            System.out.println("Inside checkExistenceOfEntityName: entity Name is: "+ entityName);
 
            String hql = "select * from ENTITES WHERE ENTITTY_NAME='"+entityName+"'";            
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
    
    public List<Entites> Entiteslist() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Entites s order by entittyName ");
            List<Entites> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
     public String getRrasFlagByEntityId(BigDecimal entityId) 
    { 
        String rrasFlag = null;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select RRAS_FLAG from ENTITES where ENTITY_ID='"+entityId+"' "; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                rrasFlag = (String)query.list().get(0); 
                System.out.println("########## In dao, rrasFlag is "+rrasFlag);
            }
            tx.commit();
            session.flush();
            session.close();
            return rrasFlag;
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return rrasFlag; 
    } 
     
     public String getFrasFlagByEntityId(BigDecimal entityId) 
    { 
        String frasFlag = null;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select FRAS_FLAG from ENTITES where ENTITY_ID='"+entityId+"' "; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                frasFlag = (String)query.list().get(0); 
                System.out.println("########## In dao, frasFlag is "+frasFlag);
            }
            tx.commit();
            session.flush();
            session.close();
            return frasFlag;
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return frasFlag; 
    } 
     
     public String getAgcFlagByEntityId(BigDecimal entityId) 
    { 
        String agcFlag = null;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select AGC_FLAG from ENTITES where ENTITY_ID='"+entityId+"' "; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                agcFlag = (String)query.list().get(0); 
                System.out.println("########## In dao, agcFlag is "+agcFlag);
            }
            tx.commit();
            session.flush();
            session.close();
            return agcFlag;
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return agcFlag; 
    } 
     
     public String getSrasFlagByEntityId(BigDecimal entityId) 
    { 
        String agcFlag = null;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select SRAS_FLAG from ENTITES where ENTITY_ID='"+entityId+"' "; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                agcFlag = (String)query.list().get(0); 
                System.out.println("########## In dao, srasFlag is "+agcFlag);
            }
            tx.commit();
            session.flush();
            session.close();
            return agcFlag;
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return agcFlag; 
    } 
     
     public String getTrasFlagByEntityId(BigDecimal entityId) 
    { 
        String agcFlag = null;         
        Session session=null; 
        Transaction tx = null;
        try{ 
            
            session = HibernateUtil.getInstance().getSession();          
            tx = session.beginTransaction();
            String hql="select TRAS_FLAG from ENTITES where ENTITY_ID='"+entityId+"' "; 
            SQLQuery query = session.createSQLQuery(hql);
             int length = query.list().size();
            if (length != 0) {
                agcFlag = (String)query.list().get(0); 
                System.out.println("########## In dao, TRAS_FLAG is "+agcFlag);
            }
            tx.commit();
            session.flush();
            session.close();
            return agcFlag;
        }catch(Exception e){ 
            if(session!=null) 
                session.close(); 
            e.printStackTrace(); 
        } 
        return agcFlag; 
    } 
    


}
