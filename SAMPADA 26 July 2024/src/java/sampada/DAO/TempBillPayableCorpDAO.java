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
import sampada.pojo.TempBillPayableCorp;

import sampada.util.HibernateUtil;

/**
 *
 * @author shubham
 */
public class TempBillPayableCorpDAO {

    public int getMaxUniqueID() {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();
            String hql = "select NVL(max(UNIQUE_ID),0) FROM TEMP_BILL_PAYABLE_CORP ";
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

    public boolean NewTempBillRRASDetails(TempBillPayableCorp dic) {

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

    public List<Object[]> getTempBillPayableRRAS(BigDecimal weekid, BigDecimal revno, BigDecimal year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
//            String hql = "select t.week_id,t.corporate_id,sum(t.net_rras),tr.billing_date,tr.bill_year,tr.revision_no,tr.remarks,'RRAS' as type from temp_bill_payable_entity_rras t, temp_bill_rras_details tr where t.week_id=tr.week_id and t.revision_no=tr.revision_no and t.week_id=" + weekid + " and t.revision_no=" + revno + " and t.bill_year=" + year + " and tr.BILL_CATEGORY='PAYABLE' group by t.week_id,t.corporate_id,tr.billing_date,tr.bill_year,tr.remarks,tr.revision_no";
            String hql = "select t.week_id,t.corporate_id,sum(t.WR_NET_RRAS),tr.billing_date,tr.bill_year,tr.revision_no,tr.remarks,'RRAS' as type from temp_bill_payable_entity_rras t, temp_bill_rras_details tr where t.week_id=tr.week_id and t.revision_no=tr.revision_no and t.bill_year=tr.bill_year and t.week_id=" + weekid + " and t.revision_no=" + revno + " and t.bill_year=" + year + " and tr.BILL_CATEGORY='PAYABLE' group by t.week_id,t.corporate_id,tr.billing_date,tr.bill_year,tr.remarks,tr.revision_no";

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

    public List<Object[]> getTempBillPayableDSM(BigDecimal weekid, BigDecimal revno, BigDecimal year) {
        List<Object[]> list = null;

        Session session = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();

//            String hql = "select t.week_id,t.corporate_id,sum(t.net_dsm),tr.billing_date,tr.bill_year,tr.revision_no,tr.remarks,'DSM' as type from temp_bill_payable_entity_dsm t, temp_bill_dsm_details tr where t.week_id=tr.week_id and t.revision_no=tr.revision_no and t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " group by t.bill_year, t.week_id,t.corporate_id,tr.billing_date,tr.bill_year,tr.remarks,tr.revision_no";
            String hql = "select t.week_id,t.corporate_id,sum(t.WR_NET_DSM),tr.billing_date,tr.bill_year,tr.revision_no,tr.remarks,'DSM' as type from temp_bill_payable_entity_dsm t, temp_bill_dsm_details tr where t.week_id=tr.week_id and t.revision_no=tr.revision_no and t.bill_year=tr.bill_year and t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " group by t.bill_year, t.week_id,t.corporate_id,tr.billing_date,tr.bill_year,tr.remarks,tr.revision_no";

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

    public BigDecimal getTempBillPayableDSMforprevnet(BigDecimal weekid, BigDecimal revno, BigDecimal year, int corpid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select sum(t.PREV_WR_NET_DSM)\n"
                    + "from temp_bill_payable_entity_dsm t\n"
                    + "where t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " and t.CORPORATE_ID=" + corpid + "\n"
                    + "group by t.CORPORATE_ID";
            System.out.println("getTempBillPayableDSMforprevnet hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public BigDecimal getTempBillPayableRRASforprevnet(BigDecimal weekid, BigDecimal revno, BigDecimal year, int corpid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select sum(t.PREV_WR_NET_RRAS)\n"
                    + "from TEMP_BILL_PAYABLE_ENTITY_RRAS t\n"
                    + "where t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " and t.CORPORATE_ID=" + corpid + "\n"
                    + "group by t.CORPORATE_ID";
            System.out.println("getTempBillPayableDSMforprevnet hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public BigDecimal getTempBillPayableAGCforprevnet(BigDecimal weekid, BigDecimal revno, BigDecimal year, int corpid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select sum(t.PREV_WR_TOTALCHARGES)\n"
                    + "from TEMP_BILL_ENTITY_AGC t\n"
                    + "where t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " and t.CORPORATE_ID=" + corpid + "\n"
                    + "group by t.CORPORATE_ID";
            System.out.println("getTempBillPayableDSMforprevnet hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }
    
    public BigDecimal getTempBillPayableSRASforprevnet(BigDecimal weekid, BigDecimal revno, BigDecimal year, int corpid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select sum(t.PREV_WR_TOTALCHARGES)\n"
                    + "from TEMP_BILL_ENTITY_SRAS t\n"
                    + "where t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " and t.CORPORATE_ID=" + corpid + "\n"
                    + "group by t.CORPORATE_ID";
            System.out.println("getTempBillPayablesrasforprevnet hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }
    
    public BigDecimal getTempBillPayableFRASforprevnet(BigDecimal weekid, BigDecimal revno, BigDecimal year, int corpid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select sum(t.PREV_WR_MARKUP_CHARGES)\n"
                    + "from TEMP_BILL_ENTITY_FRAS t\n"
                    + "where t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " and t.CORPORATE_ID=" + corpid + "\n"
                    + "group by t.CORPORATE_ID";
            System.out.println("getTempBillPayableFRASforprevnet hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }


    public BigDecimal getTempBillReceivableRRASforprevnet(BigDecimal weekid, BigDecimal revno, BigDecimal year, int corpid) {

        Session session = null;

        int result = 0;

        BigDecimal bg = BigDecimal.ZERO;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String hql = "select sum(t.PREV_WR_NET_RRAS)\n"
                    + "from TEMP_BILL_RECEIVE_ENTITY_RRAS t\n"
                    + "where t.week_id=" + weekid + " and t.bill_year=" + year + " and t.revision_no=" + revno + " and t.CORPORATE_ID=" + corpid + "\n"
                    + "group by t.CORPORATE_ID";
            System.out.println("getTempBillPayableDSMforprevnet hql " + hql);
            SQLQuery query = session.createSQLQuery(hql);
            bg = (BigDecimal) query.list().get(0);
//            bg = new BigDecimal(result);
            System.out.println("bg is " + bg);
            tx.commit();

            session.flush();

            session.close();

            return bg;

        } catch (Exception e) {

            if (session != null) {

                session.close();

            }

            e.printStackTrace();

        }

        return bg;

    }

    public boolean deleteTempBillPayableCorpbyWeekId(BigDecimal weekId, BigDecimal yearId, BigDecimal revisionNo, String billType) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillPayableCorp where weekId=:weekId and billYear=:yearId and revisionNo=:revisionNo and billType=:billType ";
            Query query = sess.createQuery(hql);
            System.out.println("week id is" + weekId);
            query.setBigDecimal("weekId", weekId);
            query.setBigDecimal("yearId", yearId);
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

//select TOTALNET from SAMPADA_WRLDC.TEMP_BILL_PAYABLE_CORP where WEEK_ID=13 AND CORPORATE_iD=38 AND BILL_YEAR=2019 AND REVISION_NO=0 AND BILL_TYPE='DSM';
    public int checkCorpIdExistForWeekCorpIdBillYearRevNoBillType(BigDecimal weekId, BigDecimal yearId, BigDecimal revisionNo, String billType, int corpId) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "select TOTALNET FROM TEMP_BILL_PAYABLE_CORP WHERE WEEK_ID='" + weekId + "' AND CORPORATE_ID='" + corpId + "' AND BILL_YEAR='" + yearId + "' AND REVISION_NO='" + revisionNo + "' AND BILL_TYPE='" + billType + "'";
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
            String hql = "update TEMP_BILL_PAYABLE_CORP set TOTALNET ='" + totalNet + "' WHERE  WEEK_ID='" + weekId + "' AND CORPORATE_ID='" + corpId + "' AND BILL_YEAR='" + yearId + "' AND REVISION_NO='" + revisionNo + "' AND BILL_TYPE='" + billType + "'";
            SQLQuery query = session.createSQLQuery(hql);
            result = query.executeUpdate();
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

    public int updatetempbillpayablecorpstatus(String billType) {
        Session session = null;
        int result = 0;
        BigDecimal bg = null;
        Transaction tx = null;
        try {

            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            String hql = "update TEMP_BILL_PAYABLE_CORP set BILL_STATUS='Verified' where BILL_STATUS='Pending' and BILL_TYPE='" + billType + "'";
            System.out.println("updatetempbillpayablecorpstatus Query is " + hql);
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

    public boolean deleteTempBillPayableCorpbystatus(String billType) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getInstance().getSession();
            tx = sess.beginTransaction();

            String hql = "delete from TempBillPayableCorp where billStatus='Pending' and billType=:billType";
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
