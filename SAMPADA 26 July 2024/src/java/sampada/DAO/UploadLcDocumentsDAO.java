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
import sampada.pojo.LetterOfCredit;
import sampada.pojo.UploadLcDocuments;
import sampada.util.HibernateUtil;

/**
 *
 * @author shubham
 */
public class UploadLcDocumentsDAO {
//     public long getMaxnewReqID() {
//        
//        long maxid=1;
//        Session session = null;
//        try {
//            session = HibernateUtil.getInstance().getSession();
//            String hql = "select max(s.slno) from UploadLcDocuments s";
//            Query q = session.createQuery(hql);
//            int length = q.list().size();
//            if (length > 0) {
//                System.out.println("max(s.slno) Class; "+q.list().get(0).getClass()+" Value: "+q.list().get(0));
//                maxid = (long) q.list().get(0);
//            }
//            session.close();
//            return maxid;
//            
//        } catch (Exception e) {
//            if(session!=null)
//                session.close();
//            e.printStackTrace();
//        }
//        
//        return maxid;
//        
//    }
//     
     
       public int getMaxnewReqID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select max(SLNO) FROM UPLOAD_LC_DOCUMENTS ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Max Serial No ID is " + bg);
                result = bg.intValueExact();
                System.out.print("Max Serial No ID is INT " + bg);
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
    
         public boolean NewUploadLoCDocumentsDetails(UploadLcDocuments uploadLcDoc) {
        
        Session session = null;
        Transaction tx = null;
        try {
             System.out.println("Inside NewUploadLoCDocumentsDetails method ");
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            session.save(uploadLcDoc);
            
             System.out.println("After session save ");
          session.flush();
           System.out.println("After session save ");
            tx.commit();
             System.out.println("After commit save ");
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
 
          public List<UploadLcDocuments> getDocumentslist() {

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from UploadLcDocuments s");
            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (List<UploadLcDocuments>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
