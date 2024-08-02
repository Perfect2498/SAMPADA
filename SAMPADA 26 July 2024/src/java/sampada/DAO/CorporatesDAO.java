/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.Corporates;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class CorporatesDAO {

    public List<Corporates> Corporateslist() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Corporates s order by corporateName ");
            List<Corporates> queryList = query.list();
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

    public String geCorpNamebyId(int corpId) {
        String result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select CORPORATE_NAME from CORPORATES where CORPORATE_ID='" + corpId + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list() != null) {
                result = (String) query.list().get(0);
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
    
    
            
            public String getBankSubAccNumberbyId(int corpId) {
        String result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select SUB_ACCOUNT_NUMBER from CORPORATES where CORPORATE_ID='" + corpId + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list() != null) {
                result = (String) query.list().get(0);
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

    public int getMaxCorporateID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select max(CORPORATE_ID) FROM CORPORATES ";
            SQLQuery query = session.createSQLQuery(hql);
            int length = query.list().size();
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("Max CORPORATE ID is " + bg);
                result = bg.intValueExact();
                System.out.print("Max CORPORATE ID is INT " + bg);
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

    public boolean NewCorporateDetails(Corporates corporate) {

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            session.save(corporate);

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

    public List<Corporates> getCorporatesbyCorporateId(int corporateId) {

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Corporates s where s.corporateId =:corporateId");
            query.setParameter("corporateId", corporateId);
            List queryList = query.list();
            System.out.println("queryList size is " + queryList.size());
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {

                return (List<Corporates>) queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    public boolean UpdateCorporates(Corporates corporate) {

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(corporate);
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

    public String getCorpNamebyId(int corpId) {
        String result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select CORPORATE_NAME from CORPORATES where CORPORATE_ID='" + corpId + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result = query.list().get(0).toString();
            System.out.println("result is " + result);
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

    public String getCorporateNamebyID(int Corpid) {
        String corpname = null;
        Session session = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.corporateName FROM Corporates E where E.corporateId=:Corpid ";
            Query query = session.createQuery(hql);
            query.setParameter("Corpid", Corpid);
            corpname = (String) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return corpname;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return corpname;
    }


    public int getCorpIdbyName(String corpName) {
        int dicid = 0;
        BigDecimal Dic_id = BigDecimal.ZERO;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "Select NVL(CORPORATE_ID,0) FROM CORPORATES where CORPORATE_NAME='" + corpName + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list().get(0) != null) {
                Dic_id = (BigDecimal) query.list().get(0);
                dicid = Dic_id.intValue();
            }
            session.close();
            return dicid;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return dicid;
    }
    
    
    public List<Corporates> getDefaultCorporateNamebyID() {

        String corpname = null;

        Session session = null;

        List<Corporates> list=null;

        try {

            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();

            session = HibernateUtil.getInstance().getSession();

            session.beginTransaction();

            String hql = "FROM Corporates E where E.validity='NO' ";

            Query query = session.createQuery(hql);          

            list = query.list();          

            session.close();

            return list;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }           

        }

        return list;

    }
    
    public int getCorpIdbySubAccNum(String subAccNum) {
        int dicid = 0;
        BigDecimal Dic_id = BigDecimal.ZERO;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "Select NVL(CORPORATE_ID,0) FROM CORPORATES where SUB_ACCOUNT_NUMBER='" + subAccNum + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list().get(0) != null) {
                Dic_id = (BigDecimal) query.list().get(0);
                dicid = Dic_id.intValue();
            }
            session.close();
            return dicid;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return dicid;
    }
    
    public String getCorporateTypebyCorpID(int Corpid) {
        String corpname = null;
        Session session = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            String hql = "select E.corporateType FROM Corporates E where E.corporateId=:Corpid ";
            Query query = session.createQuery(hql);
            query.setParameter("Corpid", Corpid);
            corpname = (String) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return corpname;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return corpname;
    }

    public int getCorpIdbyUserName(String userName) {
        int dicid = 0;
        BigDecimal Dic_id = BigDecimal.ZERO;
        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            String hql = "Select NVL(CORPORATE_ID,0) FROM USER_DETAILS where LOGIN_ID='" + userName + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            if (query.list().get(0) != null) {
                Dic_id = (BigDecimal) query.list().get(0);
                dicid = Dic_id.intValue();
            }
            session.close();
            return dicid;
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return dicid;
    }
    
    
    public BigDecimal getoutstandingbyId(int corpId) {
        BigDecimal result = null;
        Session session = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select OUT_STANDING from CORPORATES where CORPORATE_ID='" + corpId + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            result =new BigDecimal(query.list().get(0).toString());
            System.out.println("result is " + result);
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
