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
import sampada.pojo.TempBillReceiveCorp;
import sampada.util.HibernateUtil;

/**
 *
 * @author shubham
 */
public class TempBillReceiveCorpDAO {

    public List<Object[]> getTempBillReceiveRRAS(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select t.week_id,t.corporate_id,sum(t.WR_NET_RRAS),tr.billing_date,tr.bill_year,tr.revision_no,tr.remarks,'RRAS' as type from temp_bill_receive_entity_rras t, temp_bill_rras_details tr where t.week_id=tr.week_id and t.week_id=" + weekid + " and t.revision_no=tr.revision_no and t.revision_no=" + revno + " and t.bill_year=tr.bill_year and t.bill_year=" + bill_year + " and tr.BILL_CATEGORY='RECEIVABLE' group by t.week_id,t.corporate_id,tr.billing_date,tr.bill_year,tr.remarks,tr.revision_no";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);

            list = query.list();
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

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_RECEIVE_CORP ";
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

    public boolean NewTempBillRRASDetails(TempBillReceiveCorp dic) {

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

    public boolean deleteTempBillReceiveCorpbyWeekId(BigDecimal weekId, BigDecimal yearid, BigDecimal revisionNo, String billType) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();
            //Owners p = (Owners) sess.load(Owners.class, new Integer(ownerid));
//              Query q=sess.createQuery("from owners");
//              sess.delete(q);
//                 
            String hql = "delete from TempBillReceiveCorp where weekId=:weekId and billYear=:yearid and revisionNo=:revisionNo and billType=:billType";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("yearid", yearid);
            query.setBigDecimal("revisionNo", revisionNo);
            query.setString("billType", billType);
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

    public List<Object[]> getTempBillReceiveDSM(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

            String hql = "select t.week_id,t.corporate_id,sum(t.net_dsm),tr.billing_date,tr.bill_year,tr.revision_no,tr.remarks,'DSM' as type from temp_bill_receive_entity_dsm t, temp_bill_dsm_details tr where t.week_id=tr.week_id and t.bill_year=tr.bill_year and t.revision_no=tr.revision_no and t.week_id=" + weekid + " and t.bill_year=" + bill_year + " and t.revision_no=" + revno + " group by t.week_id,t.corporate_id,tr.billing_date,tr.bill_year,tr.remarks,tr.revision_no";
            System.out.println("getTempBillReceiveDSM is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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

    public int checkCorpIdExistForWeekCorpIdBillYearRevNoBillType(BigDecimal weekId, BigDecimal yearId, BigDecimal revisionNo, String billType, int corpId) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select TOALNET FROM TEMP_BILL_RECEIVE_CORP WHERE WEEK_ID='" + weekId + "' AND CORPORATE_ID='" + corpId + "' AND BILL_YEAR='" + yearId + "' AND REVISION_NO='" + revisionNo + "' AND BILL_TYPE='" + billType + "'";
            System.out.println("hql is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            query.list();
            int length = query.list().size();
            System.out.println("length is " + length);
            if (length != 0) {
                bg = (BigDecimal) query.list().get(0);
                System.out.print("TOTAL NET  is " + bg);
                result = bg.intValueExact();
                System.out.print(" TOTAL is INT " + bg);
            }
            session.flush();
            tx.commit();
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

    public int updateNetAmtForMatchedCorp(BigDecimal totalNet, BigDecimal weekId, BigDecimal yearId, BigDecimal revisionNo, String billType, int corpId) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_RECEIVE_CORP set TOALNET ='" + totalNet + "' WHERE  WEEK_ID='" + weekId + "' AND CORPORATE_ID='" + corpId + "' AND BILL_YEAR='" + yearId + "' AND REVISION_NO='" + revisionNo + "' AND BILL_TYPE='" + billType + "'";
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

    public List<Object[]> getTempBillAGC(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            //            String hql="select t.WEEK_ID,t.CORPORATE_ID,sum(t.TOTALCHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_AGC t, TEMP_BILL_AGC_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID="+weekid+" and t.BILL_YEAR="+bill_year+" and t.REVISION_NO="+revno+" group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";
            String hql = "select t.WEEK_ID,t.CORPORATE_ID,sum(t.WR_TOTALCHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_AGC t, TEMP_BILL_AGC_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID=" + weekid + " and t.BILL_YEAR=" + bill_year + " and t.REVISION_NO=" + revno + " group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";

            System.out.println("getTempBillAGC is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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
    
    public List<Object[]> getTempBillSRAS(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            //            String hql="select t.WEEK_ID,t.CORPORATE_ID,sum(t.TOTALCHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_AGC t, TEMP_BILL_AGC_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID="+weekid+" and t.BILL_YEAR="+bill_year+" and t.REVISION_NO="+revno+" group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";
            String hql = "select t.WEEK_ID,t.CORPORATE_ID,sum(t.WR_TOTALCHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_SRAS t, TEMP_BILL_SRAS_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID=" + weekid + " and t.BILL_YEAR=" + bill_year + " and t.REVISION_NO=" + revno + " group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";

            System.out.println("getTempBillAGC is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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
    
    public List<Object[]> getTempBillTRASM(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            //            String hql="select t.WEEK_ID,t.CORPORATE_ID,sum(t.TOTALCHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_AGC t, TEMP_BILL_AGC_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID="+weekid+" and t.BILL_YEAR="+bill_year+" and t.REVISION_NO="+revno+" group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";
            String hql = "select t.WEEK_ID,t.CORPORATE_ID,sum(t.WR_NET_TRAS),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_TRAS_M t, TEMP_BILL_TRAS_M_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID=" + weekid + " and t.BILL_YEAR=" + bill_year + " and t.REVISION_NO=" + revno + " group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";

            System.out.println("getTempBillAGC is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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
    
    public List<Object[]> getTempBillTRASS(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            //            String hql="select t.WEEK_ID,t.CORPORATE_ID,sum(t.TOTALCHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_AGC t, TEMP_BILL_AGC_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID="+weekid+" and t.BILL_YEAR="+bill_year+" and t.REVISION_NO="+revno+" group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";
            String hql = "select t.WEEK_ID,t.CORPORATE_ID,sum(t.WR_NET_TRAS),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_TRAS_S t, TEMP_BILL_TRAS_S_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID=" + weekid + " and t.BILL_YEAR=" + bill_year + " and t.REVISION_NO=" + revno + " group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";

            System.out.println("getTempBillAGC is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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
    
    public List<Object[]> getTempBillTRASE(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            //            String hql="select t.WEEK_ID,t.CORPORATE_ID,sum(t.TOTALCHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_AGC t, TEMP_BILL_AGC_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID="+weekid+" and t.BILL_YEAR="+bill_year+" and t.REVISION_NO="+revno+" group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";
            String hql = "select t.WEEK_ID,t.CORPORATE_ID,sum(t.WR_NET_TRAS),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_TRAS_E t, TEMP_BILL_TRAS_E_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID=" + weekid + " and t.BILL_YEAR=" + bill_year + " and t.REVISION_NO=" + revno + " group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";

            System.out.println("getTempBillAGC is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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

    public List<Object[]> getTempBillFRAS(BigDecimal weekid, BigDecimal revno, BigDecimal bill_year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

//            String hql = "select t.WEEK_ID,t.CORPORATE_ID,sum(t.MARKUP_CHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_FRAS t, TEMP_BILL_FRAS_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID=" + weekid + " and t.BILL_YEAR=" + bill_year + " and t.REVISION_NO=" + revno + " group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";
            String hql = "select t.WEEK_ID,t.CORPORATE_ID,sum(t.WR_MARKUP_CHARGES),tr.BILLING_DATE,tr.BILL_YEAR,tr.REVISION_NO,tr.REMARKS from TEMP_BILL_ENTITY_FRAS t, TEMP_BILL_FRAS_DETAILS tr where t.WEEK_ID=tr.WEEK_ID and t.BILL_YEAR=tr.BILL_YEAR and t.REVISION_NO=tr.REVISION_NO and t.WEEK_ID=" + weekid + " and t.BILL_YEAR=" + bill_year + " and t.REVISION_NO=" + revno + " group by t.WEEK_ID,t.CORPORATE_ID,tr.BILLING_DATE,tr.BILL_YEAR,tr.REMARKS,tr.REVISION_NO";

            System.out.println("getTempBillFRAS is " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            list = query.list();
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

    public int updatetempbillreceivablecorpstatus(String billType) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_RECEIVE_CORP set BILL_STATUS='Verified' where BILL_STATUS='Pending' and BILL_TYPE='"+billType+"' ";
            System.out.println("updatetempbillreceivablecorpstatus Query is " + hql);
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
    
    public boolean deleteTempBillReceiveCorpbystatus(String billType) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillReceiveCorp where billStatus='Pending' and billType=:billType";
            Query query = sess.createQuery(hql);
            query.setString("billType", billType);
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
