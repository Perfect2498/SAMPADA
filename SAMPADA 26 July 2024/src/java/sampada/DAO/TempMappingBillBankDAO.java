/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.TempMappingBillBank;

import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class TempMappingBillBankDAO {
    
    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_MAPPING_BILL_BANK ";
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
    
    public int getUpdatebyCheckerforCorp(int corpid,String status) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        Transaction tx=null;
        try {
            
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            String hql = "update TEMP_MAPPING_BILL_BANK set checker_status='"+status+"' where CORPORATE_ID="+corpid+" ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
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
    
    public int getDeletebyMakerforCorpID(int corpid,String status) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        Transaction tx=null;
        try {
            
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            String hql = "delete from TEMP_MAPPING_BILL_BANK where CORPORATE_ID="+corpid+" and checker_status='"+status+"' ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
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
      public boolean NewTempMappingBillBank(TempMappingBillBank dic) {
        
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
      
    public List<TempMappingBillBank> getTempMappingBillBank(String status)
    {
        List<TempMappingBillBank> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMappingBillBank s where s.checkerStatus=:status ";
            System.out.println(" is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("status", status);
            list=query.list();
            for(TempMappingBillBank e:list)        
            {
                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getBillPayableCorp());
            }
            session.close();
            return list;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<TempMappingBillBank> getTempMappingBillBankbyCorpID(int corpid,String status)
    {
        List<TempMappingBillBank> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMappingBillBank s where s.checkerStatus=:status and s.corporates.corporateId=:corpid ";
            System.out.println(" is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("status", status);
            query.setParameter("corpid", corpid);           
            list=query.list();
            for(TempMappingBillBank e:list)        
            {
                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getBillPayableCorp());
            }
            session.close();
            return list;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<TempMappingBillBank> getMaxTempMappingBillBankbyCorpID(int corpid,String status)
    {
        List<TempMappingBillBank> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMappingBillBank s where s.checkerStatus=:status and s.corporates.corporateId=:corpid and uniqueId in (select max(e.uniqueId) from TempMappingBillBank e where e.corporates.corporateId=:corpid group by e.billPayableCorp.uniqueId) ";
            System.out.println(" is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("status", status);
            query.setParameter("corpid", corpid);           
            list=query.list();
            for(TempMappingBillBank e:list)        
            {
                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getBillPayableCorp());
            }
            session.close();
            return list;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return list;
    }
    
//    public List<BigDecimal> getTempMappingBillBankForBillPayableCorpIDs(int corpid,String status)
//    {
//        List<BigDecimal> list = new ArrayList<>();
//        
//        Session session=null;
//        try{
//           
//            session = HibernateUtil.getInstance().getSession();
//            session.beginTransaction();
//           
//            String hql="select distinct s.billPayableCorp.uniqueId from from TempMappingBillBank s where s.checkerStatus=:status and s.corporates.corporateId=:corpid ";
//            System.out.println(" is "+hql);
//            Query query = session.createQuery(hql); 
//            query.setParameter("status", status);
//            query.setParameter("corpid", corpid);           
//            list=query.list();
//            for(TempMappingBillBank e:list)        
//            {
//                Hibernate.initialize(e.getCorporates());
//                Hibernate.initialize(e.getBankStatement());
//                Hibernate.initialize(e.getBillPayableCorp());
//            }
//            session.close();
//            return list;
//        }catch(Exception e){
//            if(session!=null)
//                session.close();
//            e.printStackTrace();
//        }
//        return list;
//    }
    
    public List<TempMappingBillBank> getTempMappingBillBankbyCorpID(String status,int corpid)
    {
        List<TempMappingBillBank> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMappingBillBank s where s.checkerStatus=:status and s.corporates.corporateId=:corpid ";
            System.out.println(" is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("status", status);
            query.setParameter("corpid", corpid);
            list=query.list();
            for(TempMappingBillBank e:list)        
            {
                Hibernate.initialize(e.getCorporates());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getBillPayableCorp());
            }
            session.close();
            return list;
        }catch(Exception e){
            if(session!=null)
                session.close();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<TempMappingBillBank> getTempMappingBillBankbyCorp(int corpid,String status)
    {
        List<TempMappingBillBank> list = null;
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMappingBillBank s where s.checkerStatus=:status and s.corporates.corporateId=:corpid ";
            System.out.println(" is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("status", status);
            query.setParameter("corpid", corpid);
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
    
    public List<BigDecimal> getTempMappingBillBankbyCorpIds(int corpid,String status)
    {
        List<BigDecimal> list = null;
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="select distinct s.bankStatement.stmtId from TempMappingBillBank s where s.checkerStatus=:status and s.corporates.corporateId=:corpid ";
            System.out.println(" HQL is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("status", status);
            query.setParameter("corpid", corpid);
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
    
    public List<Object[]> getTempMappingBillBankbyCorpList(String status)
    {
        List<Object[]> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="select distinct corporate_id from TEMP_MAPPING_BILL_BANK where checker_status='"+status+"' ";
            System.out.println(" is "+hql);
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
    
    public List<BigDecimal> getTempMappingBillBankbyPendingCorpList(String status)
    {
        List<BigDecimal> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="select distinct corporate_id from TEMP_MAPPING_BILL_BANK where checker_status='"+status+"' ";
            System.out.println(" is "+hql);
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
    
//select distinct c.CORPORATE_ID,distinct d.Corporate_name from CORPORATES d, TEMP_MAPPING_BILL_BANK c where c.CORPORATE_ID=d.CORPORATE_ID AND c.CHECKER_STATUS='Pending';

public List<Object[]> getTempPendingMappingBillBankbyCorpList(String status)
    {
        List<Object[]> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="select distinct (c.CORPORATE_ID), d.Corporate_name from CORPORATES d, TEMP_MAPPING_BILL_BANK c  where c.CORPORATE_ID=d.CORPORATE_ID and c.CHECKER_STATUS='"+status+"' ";
            System.out.println("SQL is "+hql);
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

//select count(*) from SAMPADA_WRLDC.TEMP_MAPPING_BILL_BANK where week_id=35 and checker_status='Pending';
public int getPendingCheckerCountByWeekId(int weekId, String billType) {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(count(*),0) FROM TEMP_MAPPING_BILL_BANK where WEEK_ID='"+weekId+"' and BILL_TYPE='"+billType+"' and CHECKER_STATUS='Pending'";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal)query.list().get(0);
                System.out.print("count is "+bg);
                result=bg.intValueExact();
                System.out.print("count is INT "+bg);
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

public List<Object[]> getTempMappingBillBankbyCorpIDBillUNIQUEID(String status,int corpid)

    {
        List<Object[]> list = null;
    

        Session session=null;
        try{
        

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();          

            String hql="select UNIQUE_BILLPAYCORP, sum(MAPPED_AMOUNT) from TEMP_MAPPING_BILL_BANK where checker_status='Pending' and CORPORATE_ID='"+corpid+"' group by UNIQUE_BILLPAYCORP";

            System.out.println(" is "+hql);

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

public List<TempMappingBillBank> getTempPendingMappingBillBankbyCorpListbyChecker(String status)

    {

        List<TempMappingBillBank> list = null;

       

        Session session=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            //String hql="select distinct (c.CORPORATE_ID) from CORPORATES d, TEMP_MAPPING_BILL_BANK c  where c.CORPORATE_ID=d.CORPORATE_ID and c.CHECKER_STATUS='"+status+"' ";

            String hql="from TempMappingBillBank s where s.checkerStatus=:status ";

            System.out.println("SQL is "+hql);

            Query query = session.createQuery(hql);  

            query.setParameter("status", status);

            list=query.list();    

            for(TempMappingBillBank e:list)

            {

                Hibernate.initialize(e.getCorporates());

            }

            session.close();

            return list;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

       return list;

    }

}
