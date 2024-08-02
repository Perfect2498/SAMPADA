/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampada.pojo.BankStatement;
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityFras;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillPayableEntityRras;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.BillReceiveEntityDsm;
import sampada.pojo.BillReceiveEntityRras;
import sampada.pojo.Corporates;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.InterestDetails;
import sampada.pojo.MappingBillBank;
import sampada.pojo.TempMapBankStatement;
import sampada.pojo.TempRefundBillCorp;
import sampada.util.HibernateUtil;
/**
 *
 * @author Kaustubh
 */
public class NewReportDAO {
    
    public List<BillPayableCorp> getPayableWeeks2(int week_id1, int week_id2,String type ,int yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billType=:type order by weekId");

            query.setParameter("week_id1", new BigDecimal(week_id1));
            query.setParameter("week_id2", new BigDecimal(week_id2));
            query.setParameter("type", type);
            query.setParameter("yeari", new BigDecimal(yeari));
            
            List<BillPayableCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return queryList;
            }

        } catch (Exception e) {   
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    public List<BillPayableCorp> getPayableWeeks(int week_id1, int week_id2,String type ,int yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillPayableCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billType=:type and s.billStatus!='REFUND' order by weekId");

            query.setParameter("week_id1", new BigDecimal(week_id1));
            query.setParameter("week_id2", new BigDecimal(week_id2));
            query.setParameter("type", type);
            query.setParameter("yeari", new BigDecimal(yeari));
            
            List<BillPayableCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillPayableCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return queryList;
            }

        } catch (Exception e) {   
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    public List<BillReceiveCorp> getPayableWeeksInReceive(int week_id1, int week_id2,String type ,int yeari) {

        Session session = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from BillReceiveCorp s where s.weekId between :week_id1 and :week_id2 and s.billYear=:yeari and s.billType=:type and s.disburseStatus='REFUND' order by weekId");

            query.setParameter("week_id1", new BigDecimal(week_id1));
            query.setParameter("week_id2", new BigDecimal(week_id2));
            query.setParameter("type", type);
            query.setParameter("yeari", new BigDecimal(yeari));
            
            List<BillReceiveCorp> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                for (BillReceiveCorp e : queryList) {
                    Hibernate.initialize(e.getCorporates());
                }
                return queryList;
            }

        } catch (Exception e) {   
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    
    
}
