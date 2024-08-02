/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import sampada.pojo.UserDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class LoginDAO {
    
    public List<UserDetails> checkLoginDetails(String userId,String passwd) {

	        Session session = null;  
                List<UserDetails> list; 
                //int passed=0;
	        try { 
                    
	            session = HibernateUtil.getInstance().getSession();
                    System.out.println("user " + session);
                    Query query = session.createQuery("from UserDetails s where s.loginId=:userId and s.password=:passwd"); 
                    
                    System.out.println("after query"); 
                    query.setParameter("userId", userId);
                    query.setParameter("passwd", passwd);
                    list = query.list();
                    System.out.println("after list"); 
                    System.out.println("list size is " + list.size());
                    System.out.println("list is "+ list.isEmpty());                   
                    return list;
	        } 
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.print("Exception Caught"+e);
                    return null;
                }
                    
 
            finally {
                    if(session != null) {
                        session.close();
                    }
	    }
     
        }
    
    
}
