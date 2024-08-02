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
import sampada.pojo.AgcInterestPool;

import sampada.pojo.InterestPoolAccountDetails;
import sampada.pojo.RrasInterestPool;

 

import sampada.util.HibernateUtil;

 

/**

*

* @author JaganMohan

*/

public class InterestPoolAccountDetailsDAO {

  

    

    public List<InterestPoolAccountDetails> InterestPoolAccountDetails() {

 

                        Session session = null;               

                        try {

                            session = HibernateUtil.getInstance().getSession();

                    Query query = session.createQuery("from InterestPoolAccountDetails s");

                    List<InterestPoolAccountDetails> queryList = query.list();

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

   

    

     

      public List<InterestPoolAccountDetails> getInterestPoolAccountDetails()

    {

        List<InterestPoolAccountDetails> list = null;        

        Session session=null;

     

        try{

            

            session = HibernateUtil.getInstance().getSession();

            String hql="from InterestPoolAccountDetails ";

            System.out.println("hql is "+hql);

            Query query = session.createQuery(hql);              

            list= query.list(); 

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

   

    public int getUpdateInterestPoolAccountbyChecker(BigDecimal mainbal)

    {

        int result = 0;        

        Session session=null;

        Transaction tx = null;

        try{

            

            session = HibernateUtil.getInstance().getSession(); 

            tx = session.beginTransaction();           

            String hql="update INTEREST_POOL_ACCOUNT_DETAILS set MAIN_BALANCE='"+mainbal+"'";

            System.out.println("hql is "+hql);

            SQLQuery query = session.createSQLQuery(hql);               

            result= query.executeUpdate();        

           

            tx.commit();

            session.flush();

            session.close();

           

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return 0;

    }
    
    public List<AgcInterestPool> getInterestPoolAccountDetailsagc()

    {

        List<AgcInterestPool> list = null;        

        Session session=null;

     

        try{

            

            session = HibernateUtil.getInstance().getSession();

            String hql="from AgcInterestPool ";

            System.out.println("hql is "+hql);

            Query query = session.createQuery(hql);              

            list= query.list(); 

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
    
    public List<RrasInterestPool> getInterestPoolAccountDetailsrras()

    {

        List<RrasInterestPool> list = null;        

        Session session=null;

     

        try{

            

            session = HibernateUtil.getInstance().getSession();

            String hql="from RrasInterestPool ";

            System.out.println("hql is "+hql);

            Query query = session.createQuery(hql);              

            list= query.list(); 

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
    
    public int getUpdateInterestPoolAccountbyCheckeragc(BigDecimal mainbal)

    {

        int result = 0;        

        Session session=null;

        Transaction tx = null;

        try{

            

            session = HibernateUtil.getInstance().getSession(); 

            tx = session.beginTransaction();           

            String hql="update AGC_INTEREST_POOL set MAIN_BALANCE='"+mainbal+"'";

            System.out.println("hql is "+hql);

            SQLQuery query = session.createSQLQuery(hql);               

            result= query.executeUpdate();        

           

            tx.commit();

            session.flush();

            session.close();

           

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return 0;

    }
    
    public int getUpdateInterestPoolAccountbyCheckerrras(BigDecimal mainbal)

    {

        int result = 0;        

        Session session=null;

        Transaction tx = null;

        try{

            

            session = HibernateUtil.getInstance().getSession(); 

            tx = session.beginTransaction();           

            String hql="update RRAS_INTEREST_POOL set MAIN_BALANCE='"+mainbal+"'";

            System.out.println("hql is "+hql);

            SQLQuery query = session.createSQLQuery(hql);               

            result= query.executeUpdate();        

           

            tx.commit();

            session.flush();

            session.close();

           

        }catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return 0;

    }

}