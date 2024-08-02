/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.UserDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class UserDetailsDAO {
    
     public boolean updatePasswordbyLoginID(String loginID,String passwd)
    {
        boolean flag=true;
        Session session = null;
        Transaction tx;
        int max=0;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx=session.beginTransaction();
            String hql = "update USER_DETAILS set PASSWORD='"+passwd+"' where login_id='"+loginID+"'";
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
 
 
 
 
 public UserDetails getLoginDetailsbyLoginID(String login) {
        UserDetails lstud = null;
        Session session = null;
        List list = null;
        List<UserDetails> list2;        
        System.out.print("loginid inside getlogin-->" + login);
        try {
            
            session = HibernateUtil.getInstance().getSession();
            String hql = " from UserDetails E where  E.id.loginId=:login";
            Query q = session.createQuery(hql);            
            q.setParameter("login", login);           
            list = q.list();
            list2=q.list();
             if (list.size() > 0) {
                lstud = (UserDetails) q.list().get(0);
            }
                      
            System.out.print("After query");
            System.out.print("List size in getlogin -->" + list.size());
           
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            if (session == null) {
                return null;
            }

        }
        return lstud;
    }
  
    
    public List<UserDetails> getUserDetails() {
        List<UserDetails> lstud = null;
        Session session = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = " from UserDetails U order by U.loginId";
            Query q = session.createQuery(hql);
            lstud = q.list();
            for (UserDetails cp : lstud) {
                Hibernate.initialize(cp.getCorporates().getCorporateName());
              
            }
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return lstud;
    }
    
    
     public int updateUserPassword(String loginID, String passwd) {
        Transaction tx = null;
        Session session = null;
        try {
            //session= HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "UPDATE UserDetails E set E.password=:passwd  WHERE E.loginId=:loginID";
            Query query = session.createQuery(hql);
            query.setParameter("loginID", loginID);
            query.setParameter("passwd", passwd);
            int result = query.executeUpdate();
            session.flush();
            tx.commit();
            session.close();
            return result;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return 0;
    }
     
      public UserDetails CheckUserDetails(String login) {
        Session session = null;
       // int result = 0;
        //List<UserDetails> list = null;
        UserDetails ud=null;
        try {
            session = HibernateUtil.getInstance().getSession();
            String hql = " From UserDetails E  WHERE E.loginId=:login_id";
            Query query = session.createQuery(hql);
            query.setParameter("login_id", login);            
           // list = (UserDetails) query.list();
            if ((query.list() != null) && (query.list().size() > 0)) {
                ud = (UserDetails) query.list().get(0);
            }
            return ud;
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            session.close();
        }
        return ud;
    }
     
}
