/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import java.util.List;

import org.hibernate.Hibernate;

import org.hibernate.Query;

import org.hibernate.SQLQuery;

import org.hibernate.Session;

import org.hibernate.Transaction;

import sampada.pojo.MappingInterestBank;

import sampada.util.HibernateUtil;
/**
 *
 * @author Kaustubh
 */
public class NewMappingInterestBankDAO {
    
    public List<MappingInterestBank> getMappingInterests(Date frmdate, Date todate) {
        
        Session session = null;
        List<MappingInterestBank> list = null;
        
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "from MappingInterestBank s where TO_DATE(s.entryDate) between :amtFrmDate and :amtToDate and s.bankStatement.stmtId IN (select b.stmtId from BankStatement b)";
            Query query = session.createQuery(hql);
            query.setParameter("amtFrmDate", frmdate);
            query.setParameter("amtToDate", todate);
            
            list = query.list();

            for (MappingInterestBank e : list) {
                Hibernate.initialize(e.getInterestDetails());
                Hibernate.initialize(e.getBankStatement());
                Hibernate.initialize(e.getInterestDetails().getCorporates());
            }

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
    
    public BigDecimal getInterestOpenBal(Date frmdate, String todate) {
        
        Session session = null;
        
        try {
            //session = HibernateUtil.getSldcSessionFactory(sldcid).openSession();
            session = HibernateUtil.getInstance().getSession();

            String hql = "select * from (\n" +
            "select * from (\n" +
            "select after_int_bal as ibal, entry_time, row_number() over (order by entry_time desc) as row_no from mapping_interest_bank where entry_time < '"+todate+"'\n" +
            ") where row_no=1\n" +
            "union\n" +
            "select * from (\n" +
            "select int_pool_bal as ibal, entry_time, row_number() over (order by entry_time desc) as row_no from payment_interest_disbursement where entry_time < '"+todate+"'\n" +
            ") where row_no=1\n" +
            ") order by entry_time desc";
            SQLQuery query = session.createSQLQuery(hql);
            
            List<Object[]> objarr = query.list();
            
            session.close();
            if(objarr.size()>0)
                return (BigDecimal) objarr.get(0)[0];
            else
                return new BigDecimal(0);
        }
        catch (Exception e) {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        
        return new BigDecimal(0);
    }
    
}
