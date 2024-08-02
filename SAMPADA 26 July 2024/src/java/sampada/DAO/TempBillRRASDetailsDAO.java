/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.TempBillAgcDetails;
import sampada.pojo.TempBillRrasDetails;
import sampada.util.HibernateUtil;

/**
 *
 * @author shubham
 */
public class TempBillRRASDetailsDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_RRAS_DETAILS ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Max UNIQUE ID is " + bg);
                result = bg.intValueExact();
                System.out.print("Max UNIQUE ID is INT " + bg);
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

    public boolean NewTempBillRRASDetails(TempBillRrasDetails dic) {
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
            if (tx != null) {
                tx.rollback();
            }
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return false;
    }

    public int checkExistenceOfWeekIDandRevisionNo(BigDecimal weekid, BigDecimal revno, BigDecimal year, String category) {
        Session session = null;
        Transaction tx = null;
        int result = 0;
        System.out.println("Inside checkExistenceOfWeekIDandRevisionNo " + weekid);
        try {
            session = HibernateUtil.getInstance().getSession();
            String hql = "select * from TEMP_BILL_RRAS_DETAILS WHERE WEEK_ID=" + weekid + " and REVISION_NO=" + revno + " and BILL_YEAR=" + year + " and BILL_CATEGORY='" + category + "'";
            SQLQuery q = session.createSQLQuery(hql);
            try {
                if (q.list().size() > 0) {
                    result = 1;
                } else {
                    result = 0;
                }
                System.out.println("result in dao is" + result);
                return result;
            } catch (Exception e) {
                System.out.println("Exception is" + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public TempBillRrasDetails getRevisionNoByWeekId(BigDecimal revisionNo) {
        List<TempBillRrasDetails> list = null;
        TempBillRrasDetails approvedList = new TempBillRrasDetails();
        Session session = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "FROM TempBillRrasDetails E where E.revisionNo=:revisionNo ";
            Query query = session.createQuery(hql);
            query.setParameter("revisionNo", revisionNo);
            list = query.list();
            if (list.size() > 0) {
                approvedList = (TempBillRrasDetails) query.list().get(0);
            }
            session.close();
            return approvedList;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return approvedList;
    }

    public boolean deleteTempBillEntityRRASbyWeekId(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year, String category) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
            //                 
            String hql = "delete from TempBillRrasDetails where weekId=:weekId and revisionNo=:revisionNo and billYear=:year and billCategory=:category ";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("category", category);
            query.setParameter("year", year);
//            query.setBigDecimal("weekId", weekId);
//            query.setBigDecimal("revisionNo", revisionNo);
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

    public Date getBillingDatebyWeekIdandRevisionNo(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year, String category) {
        Session session = null;
        Date maxreplydate = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.billingDate FROM TempBillRrasDetails E where E.weekId=:weekId and E.revisionNo=:revisionNo and E.billYear=:year and E.billCategory=:category";
            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("category", category);
            query.setParameter("year", year);
            maxreplydate = (Date) query.list().get(0);
            session.close();
            return maxreplydate;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return maxreplydate;
    }

    public Date getFromDatebyWeekIdandRevisionNo(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year, String category) {
        Session session = null;
        Date maxreplydate = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.weekFromdate FROM TempBillRrasDetails E where E.weekId=:weekId and E.revisionNo=:revisionNo and E.billYear=:year and E.billCategory=:category";
            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("year", year);
            query.setParameter("category", category);
            maxreplydate = (Date) query.list().get(0);
            session.close();
            return maxreplydate;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return maxreplydate;
    }

    public Date getToDatebyWeekIdandRevisionNo(BigDecimal weekId, BigDecimal revisionNo, BigDecimal year, String category) {
        Session session = null;
        Date maxreplydate = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.weekTodate FROM TempBillRrasDetails E where E.weekId=:weekId and E.revisionNo=:revisionNo and E.billYear=:year and E.billCategory=:category";
            Query query = session.createQuery(hql);
            query.setParameter("weekId", weekId);
            query.setParameter("revisionNo", revisionNo);
            query.setParameter("year", year);
            query.setParameter("category", category);
            maxreplydate = (Date) query.list().get(0);
            session.close();
            return maxreplydate;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return maxreplydate;
    }

    public BigDecimal getMaxRevNoByWeekId(BigDecimal weekNo, BigDecimal year, String category) {
        BigDecimal revNo = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select max(REVISION_NO) from TEMP_BILL_RRAS_DETAILS WHERE WEEK_ID='" + weekNo + "' AND BILL_YEAR='" + year + "' AND BILL_CATEGORY='" + category + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                revNo = (BigDecimal) query.list().get(0);
                System.out.println("entityName is " + revNo);
            }
            tx.commit();
            session.flush();
            session.close();
            return revNo;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return revNo;
    }

    public List<TempBillRrasDetails> gettemprrasdetilsbyweekyearrev(BigDecimal weekid, BigDecimal yearid,BigDecimal rev, String billcat) {

        List<TempBillRrasDetails> list = null;

        Session session = null;

        Transaction tx = null;

        try {

 

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "from TempBillRrasDetails s where  s.weekId=:weekid  and s.billYear=:yearid and s.revisionNo=:rev and s.billCategory=:billcat ";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("weekid", weekid);
            query.setParameter("yearid", yearid);
            query.setParameter("rev", rev);
            query.setParameter("billcat", billcat);

            list = query.list();

//            for (BillPayableCorp e : list) {
//
//                Hibernate.initialize(e.getCorporates());
//
//               
//
//            }

            tx.commit();

            session.flush();

            session.close();

            return list;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return list;

    }
    
    public int getminyearofrrasbillsbygivenyear(int year) {
        int result = Calendar.getInstance().get(Calendar.YEAR);
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select min(extract(YEAR from s.BILLING_DATE)) from TEMP_BILL_RRAS_DETAILS s\n"
                    + "where extract(YEAR from s.ENTRY_DATE)='"+year+"'\n"
                    + "group by extract(YEAR from s.ENTRY_DATE)";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
           if (query.list() != null && !(query.list().isEmpty())) {
            result = Integer.parseInt(query.list().get(0).toString()) ;
            System.out.println("result is " + result);
           }
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
    
    public int updatetempbillrrasdetailsstatusbybillcategory(String billcategory) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_RRAS_DETAILS set BILL_STATUS='Verified' where BILL_STATUS='Pending' and BILL_CATEGORY='"+billcategory+"'";
            System.out.println("updatetempbillrrasdetailsstatusbybillcategory Query is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.executeUpdate();
            System.out.println("length is " + length);
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
    
    public boolean deleteTempBillRRASDetailsbystatus(String billcat) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillRrasDetails s where s.billStatus='Pending' and s.billCategory=:billcat ";
            
            
            Query query = sess.createQuery(hql);
            query.setParameter("billcat", billcat);
            
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
}
