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
import sampada.pojo.BankStatement;
import sampada.pojo.TempMapBankStatement;
import sampada.util.HibernateUtil;

/**
 *
 * @author JaganMohan
 */
public class TempMapBankStatementDAO {
    
    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(TEMP_STMTID),0) FROM TEMP_MAP_BANK_STATEMENT ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal)query.list().get(0);
                result=bg.intValueExact();
               
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
      public boolean NewTempMapBankStatement(TempMapBankStatement dic) {
        
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
      
    public List<TempMapBankStatement> getTempMapBankStatementbySTMTID(BigDecimal stmtid)
    {
        List<TempMapBankStatement> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMapBankStatement s where s.tempStmtid=(select max(tempStmtid) from TempMapBankStatement where  bankStatement.stmtId=:stmtid)  ";
            System.out.println(" is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("stmtid", stmtid);
            list=query.list();
            for(TempMapBankStatement e:list)
            {
                Hibernate.initialize(e.getBankStatement());
                       
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
    
//    public TempBankStatement getTemporaryBankStatementDetailsByStmtId(BigDecimal stmtid)
//    {
//        TempBankStatement list = new TempBankStatement();
//        
//        Session session=null;
//        try{
//           
//            session = HibernateUtil.getInstance().getSession();
//            session.beginTransaction();
//           
//            String hql="from TempBankStatement s where s.tempStmtid=(select max(tempStmtid) from TempBankStatement where  bankStatement.stmtId=:stmtid)  ";
//            System.out.println(" is "+hql);
//            Query query = session.createQuery(hql); 
//            query.setParameter("stmtid", stmtid);
//            list = query.list();
//            Hibernate.initialize(list.getBankStatement());
//            session.close();
//            return list;
//        }catch(Exception e){
//            if(session!=null)
//                session.close();
//            e.printStackTrace();
//        }
//        return list;
//    }
//    
    
    public int getDeletedTempMapBankStatementbyCorp(int corpid,String status)
    {
        int result=0;
        Session session=null;
        Transaction tx;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            
            String hql="delete from Temp_MAP_Bank_Statement where checker_Status='"+status+"' and corporate_ID='"+corpid+"' ";
            System.out.println(" is "+hql);
            SQLQuery query = session.createSQLQuery(hql);            
            result=query.executeUpdate();
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
    
    public int getUpdatedTempBankStatementbyCorp(int corpid,String status)
    {
       int result=0;
        Session session=null;
        Transaction tx=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();           
            String hql="update  Temp_map_Bank_Statement set checker_Status='"+status+"' where corporate_Id='"+corpid+"' ";
            System.out.println(" is "+hql);
            SQLQuery query = session.createSQLQuery(hql);            
            result=query.executeUpdate();
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
    
    
    public List<TempMapBankStatement> getMaxTempMapBankStatementbyStatus(String Status,int corid,String category)
    {
        List<TempMapBankStatement> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMapBankStatement s where s.checkerStatus=:Status and s.billCategory=:category and s.corporateId=:corid and s.tempStmtid IN (select max(e.tempStmtid) from TempMapBankStatement e where e.corporateId=:corid group by e.bankStatement.stmtId ) ";
            System.out.println(" is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("Status", Status);
            query.setParameter("corid", new BigDecimal(corid));
            query.setParameter("category", category);
            list=query.list();
            for(TempMapBankStatement e:list)
            {
                Hibernate.initialize(e.getBankStatement());
                       
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
    
    public List<TempMapBankStatement> getTempMapBankStatementbyStatus(String Status,int corid,String category)
    {
        List<TempMapBankStatement> list = new ArrayList<>();
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
            String hql="from TempMapBankStatement s where s.checkerStatus=:Status and s.billCategory=:category and s.corporateId=:corid order by s.tempStmtid , s.bankStatement.stmtId ";
            System.out.println(" getTempMapBankStatementbyStatus is "+hql);
            Query query = session.createQuery(hql); 
            query.setParameter("Status", Status);
            query.setParameter("corid", new BigDecimal(corid));
            query.setParameter("category", category);
            
            list=query.list();
            for(TempMapBankStatement e:list)
            {
                Hibernate.initialize(e.getBankStatement());
                       
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
    
    
    public int getUpdatedTempBankStatementbyCorpRemarksSTmtid(int corpid,String status,String remarks)
    {
       int result=0;
        Session session=null;
        Transaction tx=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();           
            String hql="update  Temp_map_Bank_Statement set checker_Status='"+status+"' where corporate_Id='"+corpid+"' and remarks='"+remarks+"' ";
            System.out.println(" is "+hql);
            SQLQuery query = session.createSQLQuery(hql);            
            result=query.executeUpdate();
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
    
    public List<TempMapBankStatement> getTempMapBankStatementbyStatusRemarks(String Status,int corid,String billCategory)

    {

        List<TempMapBankStatement> list = null;

       

        Session session=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            String hql="from TempMapBankStatement s where s.checkerStatus=:Status and s.corporateId=:corid and s.billCategory=:billCategory order by s.bankStatement.stmtId ";

            System.out.println(" is "+hql);

            Query query = session.createQuery(hql);

            query.setParameter("Status", Status);

            query.setParameter("corid", new BigDecimal(corid));

             query.setParameter("billCategory", billCategory);

            list=query.list();

            for(TempMapBankStatement e:list)

            {

                Hibernate.initialize(e.getBankStatement());

                      

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
    
    public List<BankStatement> getBankStatementNotINTempMapBankStatement(String Status,int corid)

    {

        List<BankStatement> list = null;

       

        Session session=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            String hql="from BankStatement s where s.corporates.corporateId=:intcorid and s.mappedBalance >=1 and s.stmtId NOT IN (select e.bankStatement.stmtId from TempMapBankStatement e where e.corporateId=:corid and e.checkerStatus=:Status ) and s.stmtStatus='Verified' and s.creditDebitFlag='CR' ";

            System.out.println(" is "+hql);

            Query query = session.createQuery(hql);

            query.setParameter("Status", Status);

            query.setParameter("corid", new BigDecimal(corid));

            query.setParameter("intcorid", corid);

            list=query.list();

            for(BankStatement e:list)

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
    
    public List<TempMapBankStatement> getTempMapBankStatementbyStatus(String Status,int corid)

    {

        List<TempMapBankStatement> list = null;

       

        Session session=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            String hql="from TempMapBankStatement s where s.checkerStatus=:Status and s.corporateId=:corid order by s.bankStatement.stmtId ";

            System.out.println(" is "+hql);

            Query query = session.createQuery(hql);

            query.setParameter("Status", Status);

            query.setParameter("corid", new BigDecimal(corid));

            list=query.list();

            for(TempMapBankStatement e:list)

            {

                Hibernate.initialize(e.getBankStatement());

                      

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
    
    public List<TempMapBankStatement> getMaxTempMapBankStatementbyStatus(String Status,int corid)

    {

        List<TempMapBankStatement> list = null;

       

        Session session=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            String hql="from TempMapBankStatement s where s.checkerStatus=:Status and s.corporateId=:corid and s.tempStmtid IN (select max(e.tempStmtid) from TempMapBankStatement e where e.corporateId=:corid group by e.bankStatement.stmtId ) ";

            System.out.println(" is "+hql);

            Query query = session.createQuery(hql);

            query.setParameter("Status", Status);

            query.setParameter("corid", new BigDecimal(corid));

            list=query.list();

            for(TempMapBankStatement e:list)

            {

                Hibernate.initialize(e.getBankStatement());

                      

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
    
    public int getUpdatedTempMapBankStatementbyCorpBillCatergory(int corpid,String status,String billcatergory)

    {

       int result=0;

        Session session=null;

        Transaction tx=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            tx=session.beginTransaction();          

            String hql="update  Temp_map_Bank_Statement set checker_Status='"+status+"' where corporate_Id='"+corpid+"' and bill_category ='"+billcatergory+"' ";

            System.out.println(" is "+hql);

            SQLQuery query = session.createSQLQuery(hql);           

            result=query.executeUpdate();

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
    
   
    public BigDecimal getSumMappedAmtTempMapBankStatementbySTMTID(BigDecimal stmtid)

    {

        BigDecimal totmapped=new BigDecimal(0);

       

        Session session=null;

        try{

          

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            String hql="select NVL(sum(MAPPED_AMOUNT),0) from Temp_map_Bank_Statement  where bank_stmtid= '"+stmtid+"' and checker_status='Pending' and bill_Category='Bills'";

            System.out.println(" is "+hql);

            SQLQuery query = session.createSQLQuery(hql);            

            totmapped=(BigDecimal)query.list().get(0);         

            session.close();

            return totmapped;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return totmapped;

    }

   

    public BigDecimal getVerifiedSumMappedAmtTempMapBankStatementbySTMTID(BigDecimal stmtid)

    {

        BigDecimal totmapped=new BigDecimal(0);

       

        Session session=null;

        try{

           

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

          

            String hql="select NVL(sum(MAPPED_AMOUNT),0) from Temp_map_Bank_Statement  where bank_stmtid= '"+stmtid+"' and checker_status='Verified' and bill_Category='Bills'";

            System.out.println(" is "+hql);

            SQLQuery query = session.createSQLQuery(hql);           

            totmapped=(BigDecimal)query.list().get(0);         

            session.close();

            return totmapped;

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return totmapped;

    }
    
    public int getDeletedInterestTempMapBankStatementbyCorp(int corpid,String status)
    {
        int result=0;
        Session session=null;
        Transaction tx;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            
            String hql="delete from Temp_MAP_Bank_Statement where checker_Status='"+status+"' and corporate_ID='"+corpid+"' and BILL_CATEGORY='Interest' ";
            System.out.println(" is "+hql);
            SQLQuery query = session.createSQLQuery(hql);            
            result=query.executeUpdate();
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
}
