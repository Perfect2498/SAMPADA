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
import sampada.pojo.TempBillReceiveEntityDsm;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class TempBillReceivableEntityDAO {
  
    
    
     public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg=null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_RECEIVE_ENTITY_DSM ";
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
     
     
     
      public boolean NewTempBillDSMEntityReceive(TempBillReceiveEntityDsm dic) {
        
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
    
        public boolean deleteTempBillReceiveDSMDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
           
            String hql = "delete from TempBillReceiveEntityDsm where weekId=:weekId and revisionNo=:revisionNo and billYear=:year";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("year", year);
            query.setBigDecimal("revisionNo", revisionNo);
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
        
      public List<Object[]> getTempDSMBillReeiveDetailsbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal yearId)
    {
        List<Object[]> list = null;
        
        Session session=null;
        try{
           
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
           
          String hql="select e.entitty_name,'0' as payable_charges , t.recv_charges,t.capping_charges,t.additional_charges,t.sign_charges,t.net_dsm,t.wr_net_dsm,t.remarks from entites e, temp_bill_receive_entity_dsm t where t.week_id="+weekId+" and t.revision_no="+ revisionNo+" and t.bill_year="+ yearId+"and t.entity_id=e.entity_id ";

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
}
  

