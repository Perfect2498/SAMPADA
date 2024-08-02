/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BankStatement;
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityFras;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillPayableEntityRras;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.BillReceiveEntityDsm;
import sampada.pojo.BillReceiveEntityRras;
import sampada.pojo.Corporates;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.InterestDetails;
import sampada.pojo.MappingBillBank;
import sampada.pojo.TempMapBankStatement;
import sampada.pojo.TempRefundBillCorp;
import sampada.util.HibernateUtil;

/**
 *
 * @author Administrator
 */
public class ReportDAO 
{
    
    
    
    
    
    
    
    
    public List<Object[]> getCorporatePaidrec(int week_id1,int week_id2,String type,int year)
    {
              
        Session session=null;
     
        try{
            session = HibernateUtil.getInstance().getSession(); 
             String sql;
          if(week_id1 > week_id2 )
          {
              
              
              
               sql=" SELECT  P.CORPORATE_NAME, sum(B.TOALNET), sum(B.REVISEDREFUND), sum(B.PENDING_AMOUNT), sum(B.DISBURSE_AMOUNT) \n" +
                        "FROM BILL_RECEIVE_CORP  B  INNER JOIN (\n" +
                        "select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO , BILL_YEAR , BILL_TYPE\n" +
                        "from BILL_RECEIVE_CORP\n" +
                        "group by corporate_id,week_id,BILL_YEAR, BILL_TYPE\n" +
                        ") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
                        "INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID \n" +
                        "WHERE UPPER(B.BILL_TYPE)='"+type+"' AND (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'  \n" +
                        "GROUP BY P.CORPORATE_NAME\n" +
                        "ORDER BY P.CORPORATE_NAME ";
           
          }
          else
          {
            sql=" SELECT  P.CORPORATE_NAME, sum(B.TOALNET), sum(B.REVISEDREFUND), sum(B.PENDING_AMOUNT),sum(B.DISBURSE_AMOUNT)\n" +
                        "FROM BILL_RECEIVE_CORP  B  INNER JOIN (\n" +
                        "select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO , BILL_YEAR, BILL_TYPE\n" +
                        "from BILL_RECEIVE_CORP\n" +
                        "group by corporate_id,week_id,BILL_YEAR, BILL_TYPE\n" +
                        ") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
                        "INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID \n" +
                        "WHERE UPPER(B.BILL_TYPE)='"+type+"' AND (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'  \n" +
                        "GROUP BY P.CORPORATE_NAME\n" +
                        "ORDER BY P.CORPORATE_NAME  ";
          }
            System.out.println("hql is "+sql);
            
           // Query query = session.createQuery(hql);  
            SQLQuery query = session.createSQLQuery(sql);
    
            List<Object[]> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) 
                    {
                        System.out.println("list size" + queryList.size());
	                return null;
	            }
                    else 
                    {
	                System.out.println("list size" + queryList.size());
	                return queryList;
	            }
                    
           
        }
        catch(Exception e)
        {
          
           
            
            e.printStackTrace();
             return null;
        }
        finally 
        {
	            session.close();
	        
        }
      
    }
    
    
   
    
    
    
           
           
            
           
     public List<Object[]> getpoolaccount(String fdate,String ldate,int year)
    {
              
        Session session=null;
     
        try{
            session = HibernateUtil.getInstance().getSession();  
            String sql;
         
               
          
            sql="select S.CORPORATE_NAME,sum(B.PAID_AMOUNT),sum(B.PAID_AMOUNT)-sum(B.MAPPED_BALANCE),sum(B.MAPPED_BALANCE), B.STMT_STATUS, B.CREDIT_DEBIT_FLAG\n" +
                "FROM BANK_STATEMENT  B INNER JOIN CORPORATES S\n" +
                "ON S.CORPORATE_ID = B.CORPORATE_ID\n" +
                "WHERE B.STMT_FROMDATE>='"+fdate+"' AND  B.STMT_TODATE<='"+ldate+"' \n" +
                "GROUP BY S.CORPORATE_NAME,B.STMT_STATUS,B.CREDIT_DEBIT_FLAG\n" +
                "ORDER BY S.CORPORATE_NAME";
          
            System.out.println("hql is "+sql);
            
           // Query query = session.createQuery(hql);  
            SQLQuery query = session.createSQLQuery(sql);
    
            List<Object[]> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) 
                    {
                        System.out.println("list size" + queryList.size());
	                return null;
	            }
                    else 
                    {
	                System.out.println("list size" + queryList.size());
	                return queryList;
	            }
                    
           
        }
        catch(Exception e)
        {
          
           
            
            e.printStackTrace();
             return null;
        }
        finally 
        {
	            session.close();
	        
        }
      
    }       
           
            
        public List<Object[]> getgroups()
    {
              
        Session session=null;
     
        try{
            session = HibernateUtil.getInstance().getSession();  
            String sql;
         
               
          
            sql="SELECT S.CORPORATE_NAME, LISTAGG(ENTITTY_NAME,  '<br />' ) WITHIN GROUP (ORDER BY ENTITTY_NAME) AS ENTITTY_NAMES\n" +
                "FROM   ENTITES E INNER JOIN CORPORATES S\n" +
                "ON S.CORPORATE_ID = E.CORPORATE_ID\n" +
                "GROUP BY S.CORPORATE_NAME\n" +
                "ORDER BY S.CORPORATE_NAME";
          
            System.out.println("hql is "+sql);
            
           // Query query = session.createQuery(hql);  
            SQLQuery query = session.createSQLQuery(sql);
    
            List<Object[]> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) 
                    {
                        System.out.println("list size" + queryList.size());
	                return null;
	            }
                    else 
                    {
	                System.out.println("list size" + queryList.size());
	                return queryList;
	            }
                    
           
        }
        catch(Exception e)
        {
          
           
            
            e.printStackTrace();
             return null;
        }
        finally 
        {
	            session.close();
	        
        }
      
    }
        
        
        
        
        
       
                
                
                
                 
                
                
    
     
    
                 
     
    
     
     
     
      
      
       
       
     
      public List<BillPayableEntityDsm> getentityfinal(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableEntityDsm s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillPayableEntityDsm> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableEntityDsm e : queryList) {
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
//      public List<Object[]> getentityfinalrras(int week_id1,int week_id2,int year)
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession();  
//            String sql;
//          if(week_id1 > week_id2 )
//          {
//              sql="SELECT  P.ENTITTY_NAME, sum(B.ENERGY_VAE), sum(B.VARIABLE_CHARGES), sum(B.NET_RRAS)\n" +
//"FROM BILL_PAYABLE_ENTITY_RRAS  B  INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO , BILL_YEAR\n" +
//"from BILL_PAYABLE_ENTITY_RRAS\n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR \n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//           
//          }
//          else
//          {
//            sql="SELECT  P.ENTITTY_NAME, sum(B.ENERGY_VAE), sum(B.VARIABLE_CHARGES), sum(B.NET_RRAS)\n" +
//"FROM BILL_PAYABLE_ENTITY_RRAS  B  INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
//"from BILL_PAYABLE_ENTITY_RRAS\n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR \n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//          }
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
       public List<BillPayableEntityRras> getentityfinalrras(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableEntityRras s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari  order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillPayableEntityRras> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableEntityRras e : queryList) {
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillPayableEntityRras>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }     
//      public List<Object[]> getentityfinalagc(int week_id1,int week_id2,int year)
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession();  
//            String sql;
//          if(week_id1 > week_id2 )
//          {
//              sql="SELECT P.ENTITTY_NAME,SUM(B.TOTAL_AGC_UPDOWN_MWH),SUM(B.MARKUP_CHARGES), SUM(B.TOTALNET_AGC),SUM(AGC_ENERGYCHARGES),SUM(TOTALCHARGES)\n" +
//"FROM BILL_ENTITY_AGC  B INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
//"FROM BILL_ENTITY_AGC\n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE UPPER(PAY_RECVFLAG)='PAYABLE' AND  (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//           
//          }
//          else
//          {
//            sql="SELECT P.ENTITTY_NAME,SUM(B.TOTAL_AGC_UPDOWN_MWH),SUM(B.MARKUP_CHARGES), SUM(B.TOTALNET_AGC),SUM(AGC_ENERGYCHARGES),SUM(TOTALCHARGES)\n" +
//"FROM BILL_ENTITY_AGC  B INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
//"FROM BILL_ENTITY_AGC\n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE UPPER(PAY_RECVFLAG)='PAYABLE' AND  (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//          }
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
  public List<BillEntityAgc> getentityfinalagc(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillEntityAgc s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and UPPER(s.payRecvflag)='PAYABLE' order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillEntityAgc> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillEntityAgc e : queryList) {
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillEntityAgc>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }     
//       public List<Object[]> getentityfinalfras(int week_id1,int week_id2,int year)
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession();  
//            String sql;
////          if(week_id1 > week_id2 )
////          {
////              sql="SELECT P.ENTITTY_NAME,SUM(B.UP_REGULATION),SUM(B.DOWN_REGULATION), SUM(B.MARKUP_CHARGES)\n" +
////"FROM BILL_ENTITY_FRAS B INNER JOIN (\n" +
////"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
////"FROM BILL_ENTITY_FRAS \n" +
////"group by ENTITY_ID,week_id, BILL_YEAR\n" +
////") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
////"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
////"WHERE  B.MARKUP_CHARGES<0 AND (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
////"GROUP BY P.ENTITTY_NAME\n" +
////"ORDER BY P.ENTITTY_NAME";
////           
//////          }
//////          else
//////          {
////            sql="SELECT P.ENTITTY_NAME,SUM(B.UP_REGULATION),SUM(B.DOWN_REGULATION), SUM(B.MARKUP_CHARGES)\n" +
////"FROM BILL_ENTITY_FRAS B INNER JOIN (\n" +
////"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
////"FROM BILL_ENTITY_FRAS \n" +
////"group by ENTITY_ID,week_id, BILL_YEAR\n" +
////") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
////"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
////"WHERE  B.MARKUP_CHARGES<0 AND (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
////"GROUP BY P.ENTITTY_NAME\n" +
////"ORDER BY P.ENTITTY_NAME";
////          }
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
       
      public List<BillEntityFras> getentityfinalfras(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillEntityFras s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.markupCharges<0 order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillEntityFras> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillEntityFras e : queryList) {
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
//       public List<Object[]> getentityfinalfrasrec(int week_id1,int week_id2,int year)
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession();  
//            String sql;
//          if(week_id1 > week_id2 )
//          {
//              sql="SELECT P.ENTITTY_NAME,SUM(B.UP_REGULATION),SUM(B.DOWN_REGULATION), SUM(B.MARKUP_CHARGES)\n" +
//"FROM BILL_ENTITY_FRAS B INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
//"FROM BILL_ENTITY_FRAS \n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE  B.MARKUP_CHARGES>=0 AND (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//           
//          }
//          else
//          {
//            sql="SELECT P.ENTITTY_NAME,SUM(B.UP_REGULATION),SUM(B.DOWN_REGULATION), SUM(B.MARKUP_CHARGES)\n" +
//"FROM BILL_ENTITY_FRAS B INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
//"FROM BILL_ENTITY_FRAS \n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE  B.MARKUP_CHARGES>=0 AND (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//          }
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
       public List<BillEntityFras> getentityfinalfrasrec(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillEntityFras s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.markupCharges>=0 order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillEntityFras> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillEntityFras e : queryList) {
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
               
               
               
                       
                       
    
    
  
     public List<BillReceiveEntityDsm> getentityfinalrec(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveEntityDsm s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari  order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillReceiveEntityDsm> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillReceiveEntityDsm e : queryList) {
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillReceiveEntityDsm>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }     
     
     
//      public List<Object[]> getentityfinalrrasrec(int week_id1,int week_id2,int year)
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession();  
//            String sql;
//          if(week_id1 > week_id2 )
//          {
//              sql="SELECT  P.ENTITTY_NAME, sum(B.ENERGY_VAE),sum(B.FIXED_CHARGES), sum(B.VARIABLE_CHARGES),sum(B.MARKUP_CHARGES), sum(B.NET_RRAS)\n" +
//"FROM BILL_RECEIVE_ENTITY_RRAS  B  INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO,BILL_YEAR\n" +
//"from BILL_RECEIVE_ENTITY_RRAS\n" +
//"group by ENTITY_ID,week_id,BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR \n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//           
//          }
//          else
//          {
//            sql="SELECT  P.ENTITTY_NAME, sum(B.ENERGY_VAE),sum(B.FIXED_CHARGES), sum(B.VARIABLE_CHARGES),sum(B.MARKUP_CHARGES), sum(B.NET_RRAS)\n" +
//"FROM BILL_RECEIVE_ENTITY_RRAS  B  INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO,BILL_YEAR\n" +
//"from BILL_RECEIVE_ENTITY_RRAS\n" +
//"group by ENTITY_ID,week_id,BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR \n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//          }
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
      public List<BillReceiveEntityRras> getentityfinalrrasrec(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveEntityRras s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari  order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillReceiveEntityRras> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillReceiveEntityRras e : queryList) {
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillReceiveEntityRras>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }      
//     public List<Object[]> getentityfinalagcrec(int week_id1,int week_id2,int year)
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession();  
//            String sql;
//          if(week_id1 > week_id2 )
//          {
//              sql="SELECT P.ENTITTY_NAME,SUM(B.TOTAL_AGC_UPDOWN_MWH),SUM(B.MARKUP_CHARGES), SUM(B.TOTALNET_AGC),SUM(AGC_ENERGYCHARGES),SUM(TOTALCHARGES)\n" +
//"FROM BILL_ENTITY_AGC  B INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
//"FROM BILL_ENTITY_AGC\n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE UPPER(PAY_RECVFLAG)='RECEIVABLE' AND  (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//           
//          }
//          else
//          {
//            sql="SELECT P.ENTITTY_NAME,SUM(B.TOTAL_AGC_UPDOWN_MWH),SUM(B.MARKUP_CHARGES), SUM(B.TOTALNET_AGC),SUM(AGC_ENERGYCHARGES),SUM(TOTALCHARGES)\n" +
//"FROM BILL_ENTITY_AGC  B INNER JOIN (\n" +
//"select  ENTITY_ID, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR \n" +
//"FROM BILL_ENTITY_AGC\n" +
//"group by ENTITY_ID,week_id, BILL_YEAR\n" +
//") s ON B.ENTITY_ID=s.ENTITY_ID AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR\n" +
//"INNER JOIN ENTITES  P ON P.ENTITY_ID = B.ENTITY_ID\n" +
//"WHERE UPPER(PAY_RECVFLAG)='RECEIVABLE' AND  (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
//"GROUP BY P.ENTITTY_NAME\n" +
//"ORDER BY P.ENTITTY_NAME";
//          }
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
    public List<BillEntityAgc> getentityfinalagcrec(BigDecimal week_id1, BigDecimal week_id2,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillEntityAgc s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and UPPER(s.payRecvflag)='RECEIVABLE' order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            
            query.setParameter("yeari", yeari);
            List<BillEntityAgc> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillEntityAgc e : queryList) {
                    Hibernate.initialize(e.getEntites());
                }
                return (List<BillEntityAgc>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }        
    
     
       public List<BillPayableCorp> getirfinal(BigDecimal week_id1, BigDecimal week_id2,String type ,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and UPPER(s.billType)=:type  and s.corporates.corporateType='IR' order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
             query.setParameter("type", type);
            
            query.setParameter("yeari", yeari);
            List<BillPayableCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BillPayableCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }        
      
//       public List<Object[]> getirfinalrec(int week_id1,int week_id2,String type,int year)
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession(); 
//             String sql;
//          if(week_id1 > week_id2 )
//          {
//              
//              
//              
//               sql=" SELECT  P.CORPORATE_NAME, sum(B.TOALNET), sum(B.REVISEDREFUND), sum(B.PENDING_AMOUNT), sum(B.DISBURSE_AMOUNT) \n" +
//                        "FROM BILL_RECEIVE_CORP  B  INNER JOIN (\n" +
//                        "select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR, BILL_TYPE\n" +
//                        "from BILL_RECEIVE_CORP\n" +
//                        "group by corporate_id,week_id,BILL_YEAR, BILL_TYPE\n" +
//                        ") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
//                        "INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID \n" +
//                        "WHERE UPPER(B.BILL_TYPE)='"+type+"' AND (B.WEEK_ID>='"+week_id1+"' OR B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"' AND P.CORPORATE_TYPE='IR' \n" +
//                        "GROUP BY P.CORPORATE_NAME\n" +
//                        "ORDER BY P.CORPORATE_NAME ";
//           
//          }
//          else
//          {
//            sql=" SELECT  P.CORPORATE_NAME, sum(B.TOALNET), sum(B.REVISEDREFUND), sum(B.PENDING_AMOUNT),sum(B.DISBURSE_AMOUNT)\n" +
//                        "FROM BILL_RECEIVE_CORP  B  INNER JOIN (\n" +
//                        "select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO, BILL_YEAR, BILL_TYPE\n" +
//                        "from BILL_RECEIVE_CORP\n" +
//                        "group by corporate_id,week_id,BILL_YEAR, BILL_TYPE\n" +
//                        ") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
//                        "INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID \n" +
//                        "WHERE UPPER(B.BILL_TYPE)='"+type+"' AND (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"' AND P.CORPORATE_TYPE='IR' \n" +
//                        "GROUP BY P.CORPORATE_NAME\n" +
//                        "ORDER BY P.CORPORATE_NAME  ";
//          }
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
    public List<BillReceiveCorp> getirfinalrec(BigDecimal week_id1, BigDecimal week_id2,String type ,BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and UPPER(s.billType)=:type  and s.corporates.corporateType='IR' order by weekId");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
             query.setParameter("type", type);
            
            query.setParameter("yeari", yeari);
            List<BillReceiveCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillReceiveCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BillReceiveCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }           
       
       
        
        public List<Object[]> getfinalpaypercorp(int week_id1,int week_id2,int corpId,int year)
    {
              
        Session session=null;
     
        try{
            session = HibernateUtil.getInstance().getSession(); 
             String sql;
  
           
       
      sql="SELECT  P.CORPORATE_NAME,B.WEEK_ID,B.BILL_TYPE, B.TOTALNET, B.REVISEDREFUND,m.PENDING_AMOUNT+m.MAPPED_AMOUNT,\n" +
"m.PENDING_AMOUNT,(m.MAPPED_AMOUNT)Paid,m.STMT_ID,s.AMOUNT_DATE,s.AMOUNT_TIME,s.PAID_AMOUNT,m.MAPPED_AMOUNT,m.PENDING_BANK_AMOUNT\n" +
"FROM bill_payable_corp  B full join MAPPING_BILL_BANK m\n" +
"on m.UNIQUE_BILLPAY=b.UNIQUE_ID\n" +
"full join BANK_STATEMENT s\n" +
"on s.STMT_ID=m.STMT_ID\n" +
"full JOIN CORPORATES  P \n" +
"ON P.CORPORATE_ID = B.CORPORATE_ID\n" +
"WHERE B.CORPORATE_ID='"+corpId+"' AND   (B.WEEK_ID>='"+week_id1+"' AND B.WEEK_ID<='"+week_id2+"') AND B.BILL_YEAR='"+year+"'\n" +
"ORDER BY P.CORPORATE_NAME";
          
            System.out.println("hql is "+sql);
            
           // Query query = session.createQuery(hql);  
            SQLQuery query = session.createSQLQuery(sql);
    
            List<Object[]> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) 
                    {
                        System.out.println("list size" + queryList.size());
	                return null;
	            }
                    else 
                    {
	                System.out.println("list size" + queryList.size());
	                return queryList;
	            }
                    
           
        }
        catch(Exception e)
        {
          
           
            
            e.printStackTrace();
             return null;
        }
        finally 
        {
	            session.close();
	        
        }
      
    }
        public List<Object[]> getmappedbankdetails(int week_id1,int week_id2,int corpId,int year)
    {
              
        Session session=null;
     
        try{
            session = HibernateUtil.getInstance().getSession(); 
             String sql;
          if(week_id1 > week_id2 )
          {
              
              
              
               sql="select  b.STMT_ID,C.WEEK_ID,s.AMOUNT_DATE,s.AMOUNT_TIME ,b.MAPPED_AMOUNT ,c.BILL_TYPE,s.MAPPED_BALANCE\n" +
"from MAPPING_BILL_BANK b inner join BILL_PAYABLE_CORP c\n" +
"on c.UNIQUE_ID=b.UNIQUE_BILLPAY\n" +
"inner join BANK_STATEMENT s\n" +
"on s.STMT_ID=b.STMT_ID\n" +
"where b.CORPORATE_ID='"+corpId+"' and (c.WEEK_ID>='"+week_id1+"' OR c.WEEK_ID<='"+week_id2+"') and c.BILL_YEAR='"+year+"'\n" +
"order by b.STMT_ID";
           
          }
          else
          {
      sql="select  b.STMT_ID,C.WEEK_ID,s.AMOUNT_DATE,s.AMOUNT_TIME ,b.MAPPED_AMOUNT ,c.BILL_TYPE,s.MAPPED_BALANCE\n" +
"from MAPPING_BILL_BANK b inner join BILL_PAYABLE_CORP c\n" +
"on c.UNIQUE_ID=b.UNIQUE_BILLPAY\n" +
"inner join BANK_STATEMENT s\n" +
"on s.STMT_ID=b.STMT_ID\n" +
"where b.CORPORATE_ID='"+corpId+"' and (c.WEEK_ID>='"+week_id1+"' AND c.WEEK_ID<='"+week_id2+"') and c.BILL_YEAR='"+year+"'\n" +
"order by b.STMT_ID";
          }
            System.out.println("hql is "+sql);
            
           // Query query = session.createQuery(hql);  
            SQLQuery query = session.createSQLQuery(sql);
    
            List<Object[]> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) 
                    {
                        System.out.println("list size" + queryList.size());
	                return null;
	            }
                    else 
                    {
	                System.out.println("list size" + queryList.size());
	                return queryList;
	            }
                    
           
        }
        catch(Exception e)
        {
          
           
            
            e.printStackTrace();
             return null;
        }
        finally 
        {
	            session.close();
	        
        }
      
    }
        
//        public List<Object[]> getallbillpayablereport()
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession(); 
//             String sql;
//        
//              
//     
//           
//        
//      sql="SELECT  P.CORPORATE_NAME,B.BILL_TYPE, B.WEEK_ID, B.REVISION_NO, B.BILL_STATUS\n" +
//"FROM bill_payable_corp  B  INNER JOIN (\n" +
//"select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO , BILL_YEAR, BILL_TYPE\n" +
//"from bill_payable_corp\n" +
//"group by corporate_id,week_id , BILL_YEAR, BILL_TYPE\n" +
//") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
//"INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID                     \n" +
//"ORDER BY P.CORPORATE_NAME";
//          
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
        public List<BillPayableCorp> getallbillpayablereport( BigDecimal week_id1 , BigDecimal week_id2, BigDecimal yeari ) {
        List<BillPayableCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillPayableCorp s where s.revisionNo=(select max(revisionNo) from BillPayableCorp where s.weekId=weekId and s.billType=billType and s.billYear=billYear and s.corporates.corporateId =corporates.corporateId) and s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            Query query = session.createQuery(hql);
            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            query.setParameter("yeari", yeari);
            list = query.list();
          
            
            if (list != null && list.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + list.size());
                for (BillPayableCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
                return (List<BillPayableCorp>) list;
            }
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
//        public List<Object[]> getallbillreceivablereport()
//    {
//              
//        Session session=null;
//     
//        try{
//            session = HibernateUtil.getInstance().getSession(); 
//             String sql;
//        
//              
//     
//           
//        
////      sql="SELECT  P.CORPORATE_NAME, B.WEEK_ID, B.REVISION_NO, B.DISBURSE_STATUS\n" +
////"FROM BILL_RECEIVE_CORP  B  INNER JOIN (\n" +
////"select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO , BILL_YEAR, BILL_TYPE\n" +
////"from BILL_RECEIVE_CORP\n" +
////"group by corporate_id,week_id , BILL_YEAR, BILL_TYPE\n" +
////") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
////"INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID                     \n" +
////"ORDER BY P.CORPORATE_NAME";
//                 sql="select coalesce(A.CORPORATE_NAME,B.CORPORATE_NAME)CORPORATE_NAME,A.aWEEK_ID, A.aREVISION_NO, A.aBILL_STATUS,B.bWEEK_ID, B.bREVISION_NO, B.bDISBURSE_STATUS\n" +
//"from\n" +
//"(SELECT (P.CORPORATE_NAME)CORPORATE_NAME, (B.WEEK_ID)aWEEK_ID, (B.REVISION_NO)aREVISION_NO, (B.BILL_STATUS)aBILL_STATUS\n" +
//"FROM bill_payable_corp  B  INNER JOIN (\n" +
//"select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO , BILL_YEAR, BILL_TYPE\n" +
//"from bill_payable_corp\n" +
//"group by corporate_id,week_id,BILL_YEAR,BILL_TYPE\n" +
//") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
//"INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID                     \n" +
//"ORDER BY P.CORPORATE_NAME)A\n" +
//"FULL OUTER JOIN\n" +
//"(SELECT (P.CORPORATE_NAME)CORPORATE_NAME, (B.WEEK_ID)bWEEK_ID, (B.REVISION_NO)bREVISION_NO, (B.DISBURSE_STATUS)bDISBURSE_STATUS\n" +
//"FROM BILL_RECEIVE_CORP  B  INNER JOIN (\n" +
//"select  corporate_id, week_id, MAX(REVISION_NO)REVISION_NO , BILL_YEAR, BILL_TYPE\n" +
//"from BILL_RECEIVE_CORP\n" +
//"group by corporate_id,week_id,BILL_YEAR,BILL_TYPE\n" +
//") s ON B.corporate_id=s.corporate_id AND B.REVISION_NO = s.REVISION_NO AND B.week_id=s.week_id AND B.BILL_YEAR=s.BILL_YEAR AND B.BILL_TYPE=s.BILL_TYPE\n" +
//"INNER JOIN CORPORATES  P ON P.CORPORATE_ID = B.CORPORATE_ID                     \n" +
//"ORDER BY P.CORPORATE_NAME)B\n" +
//"ON A.CORPORATE_NAME=B.CORPORATE_NAME and A.aWEEK_ID=B.bWEEK_ID ORDER BY CORPORATE_NAME ";
//          
//            System.out.println("hql is "+sql);
//            
//           // Query query = session.createQuery(hql);  
//            SQLQuery query = session.createSQLQuery(sql);
//    
//            List<Object[]> queryList = query.list();
//	            if (queryList != null && queryList.isEmpty()) 
//                    {
//                        System.out.println("list size" + queryList.size());
//	                return null;
//	            }
//                    else 
//                    {
//	                System.out.println("list size" + queryList.size());
//	                return queryList;
//	            }
//                    
//           
//        }
//        catch(Exception e)
//        {
//          
//           
//            
//            e.printStackTrace();
//             return null;
//        }
//        finally 
//        {
//	            session.close();
//	        
//        }
//      
//    }
        
      public List<BillReceiveCorp> getallbillreceivablereport(BigDecimal week_id1 , BigDecimal week_id2, BigDecimal yeari) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillReceiveCorp s where s.revisionNo=(select max(revisionNo) from BillReceiveCorp where s.weekId=weekId and s.billType=billType and s.billYear=billYear and s.corporates.corporateId =corporates.corporateId)and s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            
            Query query = session.createQuery(hql);
           query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            query.setParameter("yeari", yeari);
            
            list = query.list();
            
          
            
             if (list != null && list.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + list.size());
                for (BillReceiveCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
            }
                return (List<BillReceiveCorp>) list;
            
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
        public List<MappingBillBank> getdetailsBybankstmt(int corpid) {

Session session = null;
String Verified= "Verified";
// String crFlag= "CR";
try {
session = HibernateUtil.getInstance().getSession();
// Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");

Query query = session.createQuery("from MappingBillBank s where s.corporates.corporateId=:corpid order by s.corporates.corporateId");

query.setParameter("corpid", corpid);
// query.setParameter("Verified", Verified);
// query.setParameter("crFlag", crFlag);
// query.setParameter("amtFrmDate", amtFrmDate);
// query.setParameter("amtToDate", amtToDate);

List<MappingBillBank> queryList = query.list();
if (queryList != null && queryList.isEmpty()) {

return null;
} else {
System.out.println("list size" + queryList.size());
for (MappingBillBank e : queryList) {
Hibernate.initialize(e.getCorporates());
Hibernate.initialize(e.getBillPayableCorp());
Hibernate.initialize(e.getBankStatement());
}
return (List<MappingBillBank>) queryList;
}

} catch (Exception e) {

e.printStackTrace();

return null;

} finally {

session.close();

}

}
        
        
        
         public List<TempRefundBillCorp> getVerifiedRefundDetaila() {

        Session session = null;

        List<TempRefundBillCorp> queryList = null;     

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from TempRefundBillCorp s where s.checkerStatus ='Verified' ");           

           

            queryList = query.list();

            if (queryList == null && queryList.isEmpty()) {

                return null;

            } else {              

                for (TempRefundBillCorp e : queryList) {

                    Hibernate.initialize(e.getCorporates());

                    Hibernate.initialize(e.getBillPayableCorp());

                    Hibernate.initialize(e.getBillReceiveCorp());

                }

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }  
         }

        
        public List<BillPayableCorp> getRefundBillPayableCorplist(BigDecimal week_id1 , BigDecimal week_id2, BigDecimal yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where  s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billStatus='REFUND' order by s.weekId ASC ");

            query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            query.setParameter("yeari", yeari);
            List<BillPayableCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BillPayableCorp>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
        
        public List<BillReceiveCorp> getRefundBillReceivableCorplist(BigDecimal week_id1 , BigDecimal week_id2, BigDecimal yeari) {
        List<BillReceiveCorp> list = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "from BillReceiveCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.disburseStatus='REFUND' order by s.weekId ASC ";
            System.out.println("hql is " + hql);
            
            Query query = session.createQuery(hql);
           query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            query.setParameter("yeari", yeari);
            
            list = query.list();
            
          
            
             if (list != null && list.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + list.size());
                for (BillReceiveCorp e : list) {
                Hibernate.initialize(e.getCorporates());
            }
            }
                return (List<BillReceiveCorp>) list;
            
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return list;
    }
        
        
        public List<InterestDetails> getInterestPayableDetails(BigDecimal week_id1 , BigDecimal week_id2, String yeari) {

        Session session = null;
        String Verified= "Verified";
        String crFlag= "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from InterestDetails s where   s.weekId between :week_id1 and :week_id2 and  s.billYear=:yeari");          
          query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            query.setParameter("yeari", new BigDecimal(yeari));
            
            List<InterestDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (InterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<InterestDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
        public List<DisbursedInterestDetails> getInterestReceivableDetails(BigDecimal week_id1 , BigDecimal week_id2, String yeari) {

        Session session = null;
        String Verified= "Verified";
        String crFlag= "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DisbursedInterestDetails s where   s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari");          
          query.setParameter("week_id1", week_id1);
            query.setParameter("week_id2", week_id2);
            query.setParameter("yeari", new BigDecimal(yeari));
            
            List<DisbursedInterestDetails> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (DisbursedInterestDetails e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<DisbursedInterestDetails>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
        
        
        
        
         
        
       
  
    
    public List<BankStatement> getBankStatementByCorpAndYear(int corpid, Date amtFrmDate, Date amtToDate) {

        Session session = null;
        String Verified= "Verified";
        String crFlag= "CR";
        try {
            session = HibernateUtil.getInstance().getSession();
//            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and year(s.amountDate)=:year and s.stmtStatus=:Verified and s.creditDebitFlag=:crFlag order by s.amountDate");
            
            Query query = session.createQuery("from BankStatement s where s.corporates.corporateId=:corpid and s.amountDate between :amtFrmDate and :amtToDate and s.stmtStatus=:Verified  order by s.amountDate");
                                                                                                                        
            query.setParameter("corpid", corpid);
            query.setParameter("Verified", Verified);
//            query.setParameter("crFlag", crFlag);
            query.setParameter("amtFrmDate", amtFrmDate);
            query.setParameter("amtToDate", amtToDate);
            
            List<BankStatement> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BankStatement e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return (List<BankStatement>) queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    } 
    
    public List<Object[]> getentitygetBankStatementByCorpAndYearsql(int corpid, String amtFrmDate, String amtToDate)
    {
              
        Session session=null;
     
        try{
            session = HibernateUtil.getInstance().getSession();  
            String sql;
         
         
          
            sql="select b.STMT_ID,b.AMOUNT_DATE,b.AMOUNT_TIME,b.ENTRY_DESC,b.PAID_AMOUNT,m.PENDING_BANK_AMOUNT,m.WEEK_ID,m.BILL_TYPE,m.PAYMENT_CATEGORY,c.REVISION_NO,c.TOTALNET,m.MAPPED_AMOUNT+m.PENDING_AMOUNT,m.MAPPED_AMOUNT,m.PENDING_AMOUNT\n" +
"from BANK_STATEMENT b full join MAPPING_BILL_BANK m\n" +
"on b.STMT_ID=m.STMT_ID\n" +
"full join BILL_PAYABLE_CORP c\n" +
"on c.UNIQUE_ID=m.UNIQUE_BILLPAY\n" +
"where b.CORPORATE_ID='"+corpid+"' and b.AMOUNT_DATE between '"+amtFrmDate+"' and '"+amtToDate+"' and b.STMT_STATUS='Verified' and b.CREDIT_DEBIT_FLAG='CR'\n" +
"order by b.AMOUNT_DATE";
          
            System.out.println("hql is "+sql);
            
           // Query query = session.createQuery(hql);  
            SQLQuery query = session.createSQLQuery(sql);
    
            List<Object[]> queryList = query.list();
	            if (queryList != null && queryList.isEmpty()) 
                    {
                        System.out.println("list size" + queryList.size());
	                return null;
	            }
                    else 
                    {
	                System.out.println("list size" + queryList.size());
	                return queryList;
	            }
                    
           
        }
        catch(Exception e)
        {
          
           
            
            e.printStackTrace();
             return null;
        }
        finally 
        {
	            session.close();
	        
        }
      
    }
    
    //================================================================================================================
    
    
    
    
    
}
