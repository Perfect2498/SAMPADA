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
import sampada.pojo.OutstandingYearCorp;
import sampada.pojo.ReconciliationCorp;
import sampada.pojo.TempRefundBillCorp;
import sampada.util.HibernateUtil;

/**
 *
 * @author abt
 */
public class OutstandingYearCorpDAO {
    
    
    public boolean NewOutstandingYearCorp(OutstandingYearCorp usr) {

        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(usr);

            session.flush();

            tx.commit();

            session.close();
            System.out.println("In OutstandingYearCorpDAO  dao");
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
    public  int getMaxslno()
    {

        int result=0;
        BigDecimal temp=BigDecimal.ZERO;
        Session session=null;
        Transaction tx = null;
        try{

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(SLNO) FROM OUTSTANDING_YEAR_CORP ";
            SQLQuery query = session.createSQLQuery(hql);
            if((query.list()!=null) && (query.list().size() > 0) && (query.list().get(0)!=null))
            {
     //          result =(Integer)query.list().get(0);
               temp =(BigDecimal)query.list().get(0);
               result=temp.intValue();
            }
            tx.commit();           
            session.flush();
            session.close();

            return result;

        }

        catch(Exception e){

            if(session!=null)

                session.close();

            e.printStackTrace();

        }

        return 0;

    }
    public  List<BigDecimal> getoutstandingbycorpidbyyear(int year, int corpid) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            String sql;

            sql = "select OUT_STANDING from OUTSTANDING_YEAR_CORP where CORP_ID='" + corpid + "' and BILL_YEAR='" + year + "' order by SLNO";

            System.out.println("hql is " + sql);

// Query query = session.createQuery(hql);
            SQLQuery query = session.createSQLQuery(sql);

            List<BigDecimal> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                System.out.println("list size" + queryList.size());
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
    
    public List<OutstandingYearCorp> getcheckstatusAllOutstandingYearCorp(BigDecimal year,int corpid) {

       Session session = null;

       List<OutstandingYearCorp> queryList = null;    

       try {

           session = HibernateUtil.getInstance().getSession();

           Query query = session.createQuery("from OutstandingYearCorp s where s.billYear =:year and s.corporates.corporateId=:corpid");         

           query.setParameter("corpid", corpid);
           query.setParameter("year", year);

           queryList = query.list();

           //System.out.print("Liset TempRefund is :"+queryList.size());
           if (queryList != null && queryList.size()>0) {

                for (OutstandingYearCorp e : queryList) {

                   Hibernate.initialize(e.getCorporates());

                  

               }

               return queryList;
            

           } else {             
                 return queryList;
             

           }

       } catch (Exception e) {

           e.printStackTrace();

           return queryList;

       } finally {

           session.close();

       }

   }
    
    public int updateoutstandbycorpbyyear(int year, int corpid, BigDecimal outstand) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update OUTSTANDING_YEAR_CORP set OUT_STANDING='" + outstand + "' where CORP_ID=" + corpid + "  and BILL_YEAR='"+year+"'";
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
}
