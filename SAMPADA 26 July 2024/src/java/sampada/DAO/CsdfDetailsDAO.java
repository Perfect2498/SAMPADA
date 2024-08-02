/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */
package sampada.DAO;

import java.math.BigDecimal;
import java.util.Date;

import java.util.List;
import org.hibernate.Hibernate;

import org.hibernate.Query;

import org.hibernate.SQLQuery;

import org.hibernate.Session;

import org.hibernate.Transaction;

import sampada.pojo.CsdfDetails;
import sampada.pojo.DocumentSets;
import sampada.pojo.Documents;
import sampada.pojo.DsnFileDetails;
import sampada.pojo.MappingBillBank;
import sampada.pojo.MiscDisbursement;

import sampada.util.HibernateUtil;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class CsdfDetailsDAO {

    public BigDecimal getMaxSlno() {

        BigDecimal result = BigDecimal.ZERO;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "select NVL(max(SLNO),0) from CSDF_DETAILS";

            SQLQuery query = session.createSQLQuery(hql);

            int length = query.list().size();

            if (length != 0) {

                result = (BigDecimal) query.list().get(0);

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

    public boolean NewCsdfDetails(CsdfDetails dic) {

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

    public List<CsdfDetails> getCsdfDetails(String category) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus='Pending' and s.amtCategory=:category");
            query.setParameter("category", category);
            queryList = query.list();
            //System.out.println("list size" + queryList.size());              

            return queryList;

        } catch (Exception e) {
            e.printStackTrace();
            return queryList;

        } finally {

            session.close();

        }

    }

    public int getUpdateCsdfDetailsbyChecker(String category) {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "update CSDF_DETAILS set checker_status='Verified' where checker_status='Pending' and amt_category='" + category + "' ";

            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            result = query.executeUpdate();

            tx.commit();

            session.flush();

            session.close();

        } catch (Exception e) {

            if (session != null) {
                session.close();
            }

            e.printStackTrace();

        }

        return 0;

    }

    public int getDeletedCsdfDetailsbyChecker() {

        int result = 0;

        Session session = null;

        Transaction tx = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            tx = session.beginTransaction();

            String hql = "delete from CSDF_DETAILS where checker_status='Pending' ";

            System.out.println("hql is " + hql);

            SQLQuery query = session.createSQLQuery(hql);

            result = query.executeUpdate();

            tx.commit();

            session.flush();

            session.close();

        } catch (Exception e) {

            if (session != null) {
                session.close();
            }

            e.printStackTrace();

        }

        return 0;

    }
    
    public List<DocumentSets> getCheckedDocumentSetsDetails()
    {
        Session session = null;
        List<DocumentSets> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DocumentSets s where s.status='Checked' and s.documentSetNo NOT IN (select md.documentSet from MiscDisbursement md) and s.documentSetNo NOT IN (select pd.documentSet from PoolToInt pd) and s.documentSetNo NOT IN (select dd.fileName from DsnFileDetails dd) order by s.documentSetNo asc");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    public List<Documents> getCheckedDocumentNames()
    {
        Session session = null;
        List<Documents> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Documents s where s.status='Checked' order by s.filename asc");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    public List<MiscDisbursement> pendingForMakerDisburse()
    {
        Session session = null;
        Transaction tx = null;
        List<MiscDisbursement> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from MiscDisbursement md where md.checkerDate IS NOT NULL and md.disburseCheckerDate IS NULL");
            queryList = query.list();
                    
            tx.commit();
            session.close();
            
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        
        return queryList;
    }
    
    public List<MiscDisbursement> pendingForMakerBankblock()
    {
        Session session = null;
        Transaction tx = null;
        List<MiscDisbursement> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from MiscDisbursement md where md.status='bankPending'");
            queryList = query.list();
                    
            tx.commit();
            session.close();
            
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        
        return queryList;
    }
    
    
    public void putMiscDisburseDetails(int stmtid,BigDecimal amt,int corpid,String corpName,String dset,String doc,String remarks,String crdate)
    {
        Session session = null;
        Transaction tx = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String date1 = java.time.LocalDate.now().toString();
            String date2[] = date1.split("-");
            String date = date2[2]+date2[1]+date2[0];
            
            BigDecimal maxid = getMaxUniqueIdMiscDisbursement();
            BigDecimal uid = maxid.add(BigDecimal.ONE);
            SQLQuery query = session.createSQLQuery("insert into MISC_DISBURSEMENT VALUES("+stmtid+","+amt+","+corpid+",'"+corpName+"','"+dset+"','"+doc+"','"+crdate+"','Pending','"+date+"',null,"+uid+",'"+remarks+"','PoolMain',null,null)");
            query.executeUpdate();
            
            SQLQuery query2 = session.createSQLQuery("update BANK_STATEMENT set STMT_STATUS='Onhold' where STMT_ID='"+stmtid+"'");
            query2.executeUpdate();
                    
            tx.commit();
            session.close();
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }

    public List<CsdfDetails> getCSDFDetailsbyDisburseID(BigDecimal disburseID) {

        List<CsdfDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from CsdfDetails s where s.slno=:disburseID and s.checkerStatus ='Verified'";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("disburseID", disburseID);

            list = query.list();

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

    public List<CsdfDetails> getCSDFDetailsbyDisburseIDnotinbank(BigDecimal disburseID) {

        List<CsdfDetails> list = null;

        Session session = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            String hql = "from CsdfDetails s where s.checkerStatus ='Verified' and s.slno=:disburseID  and s.slno NOT IN (select disburseId from BankStatement where disburseType='PSDF' )";

            System.out.println("hql is " + hql);

            Query query = session.createQuery(hql);

            query.setParameter("disburseID", disburseID);

            list = query.list();

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

    public List<CsdfDetails> getPSDFBillCorpbyforExport(Date fromdate, Date todate) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus ='Verified' and (TO_DATE(s.entryDate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='PSDF' )   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    
     public List<CsdfDetails> getPSDFbilldetailsbydates(Date fromdate, Date todate) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus ='Verified' and s.amtCategory='Bills' and (TO_DATE(s.entryDate) between :fromdate and :todate)  ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
     
     public List<CsdfDetails> getPSDFintdetailsbydates(Date fromdate, Date todate) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus ='Verified' and s.amtCategory='Interest' and (TO_DATE(s.entryDate) between :fromdate and :todate)  ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    
     public List<CsdfDetails> getPSDFdetailsbydates(Date fromdate, Date todate) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus ='Verified' and (TO_DATE(s.entryDate) between :fromdate and :todate) and s.slno NOT IN (select disburseId from BankStatement where disburseType='PSDF' )   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
     
     public List<CsdfDetails> getPSDFdetailsbyfromandtoids(BigDecimal frombill, BigDecimal tobill) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus ='Verified' and s.slno between :frombill and :tobill and s.slno NOT IN (select disburseId from BankStatement where disburseType='PSDF' )   ");

            query.setParameter("frombill", frombill);

            query.setParameter("tobill", tobill);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
     
     public List<CsdfDetails> getPSDFdetailsbydatesIN(Date fromdate, Date todate) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus ='Verified' and (TO_DATE(s.entryDate) between :fromdate and :todate) and s.slno IN (select disburseId from BankStatement where disburseType='PSDF' )   ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    public List<CsdfDetails> getPSDFdetailsbyfromandtodates(Date fromdate, Date todate) {

        Session session = null;

        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();

            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus ='Verified' and (TO_DATE(s.entryDate) between :fromdate and :todate) ");

            query.setDate("fromdate", fromdate);

            query.setDate("todate", todate);

            queryList = query.list();

            if (queryList != null && queryList.isEmpty()) {

                return null;

            } else {

                System.out.println("psdf list size" + queryList.size());

                return queryList;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }
    public List<CsdfDetails> getAllCsdfDetails() {

        Session session = null;
        List<CsdfDetails> queryList = null;

        try {

            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from CsdfDetails s where s.checkerStatus='Pending'");

            queryList = query.list();
            System.out.print("PSDF List size is" + queryList.size());
            return queryList;

        } catch (Exception e) {

            e.printStackTrace();

            return queryList;

        } finally {

            session.close();

        }

    }

    
    public BigDecimal getNoDocs(String Dsetname)
    {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            SQLQuery query = session.createSQLQuery("select NVL(max(NO_OF_DOCS),0) from DOCUMENT_SETS where DOCUMENT_SET_NO='"+Dsetname+"'");

            int length = query.list().size();
            if (length != 0) {
                result = (BigDecimal) query.list().get(0);
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
    
    public void putDocumentDetails(short flag,String documentSetNo,String documentNo ,String dset_desc,String doc_desc,String filename,String filesize,String filetype,BigDecimal no_of_docs)
    {
        Session session = null;
        Transaction tx = null;

        try {
            
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            String date1 = java.time.LocalDate.now().toString();
            String date2[] = date1.split("-");
            String date = date2[2]+date2[1]+date2[0];
            
            SQLQuery query = session.createSQLQuery("insert into DOCUMENTS VALUES('"+documentNo+"','"+doc_desc+"','Check Pending',TO_DATE('" + date1 + "','YYYY-MM-DD'),null,'"+filename+"','"+filesize+"','"+filetype+"','"+documentSetNo+"')");
            query.executeUpdate();
            
            Query query3 = session.createQuery("update DocumentSets s set s.no_of_docs='"+no_of_docs+"' where s.documentSetNo='"+documentSetNo+"'");
            query3.executeUpdate();   

            if(flag==1)
            {
                SQLQuery query2 = session.createSQLQuery("insert into DOCUMENT_SETS VALUES('"+documentSetNo+"','"+dset_desc+"','Check Pending',TO_DATE('" + date1 + "','YYYY-MM-DD'),null,"+no_of_docs+")");
                query2.executeUpdate();      
            }
                
            tx.commit();
            session.flush();
            session.close();
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }
    
    public List<DocumentSets> getDocumentSetsDetails()
    {
        Session session = null;
        List<DocumentSets> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DocumentSets s order by s.documentSetNo asc");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    public List<Documents> getDocumentDetails(String Docno)
    {
        Session session = null;
        List<Documents> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Documents s where s.documentNo LIKE '"+Docno+"%' order by filename asc");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    
    public String getDocumentSetDes(String DSetno)
    {
        Session session = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DocumentSets s where s.documentSetNo=:val");
            query.setParameter("val", DSetno);
            List<DocumentSets> list = query.list();

            session.close();
            return list.get(0).getDescription();

        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return null;
        }
    }
    
    public List<DocumentSets> getDocumentSetsforMaker()
    {
        Session session = null;
        List<DocumentSets> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DocumentSets s where s.checkDateByChecker IS NULL order by s.documentSetNo");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    public List<Documents> getDocumentsforMaker()
    {
        Session session = null;
        List<Documents> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Documents s where s.checkDateByChecker IS NULL order by s.documentNo");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    public List<MiscDisbursement> getMiscDisburseDetailsForMaker()
    {
        Session session = null;
        Transaction tx = null;
        List<MiscDisbursement> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from MiscDisbursement md where md.status='Pending' and md.checkerDate IS NULL");
            queryList = query.list();
                    
            tx.commit();
            session.close();
            
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        
        return queryList;
    }
    
    public List<DsnFileDetails> getunmappedbankForMaker()
    {
        Session session = null;
        Transaction tx = null;
        List<DsnFileDetails> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from DsnFileDetails md where md.checkerStatus='Pending'");
            queryList = query.list();
               
            for (DsnFileDetails e : queryList) {

                Hibernate.initialize(e.getBankStatement());

                Hibernate.initialize(e.getBankStatement().getCorporates());
                
            }

            
            tx.commit();
            session.close();
            
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        
        return queryList;
    }

    public String getFilenameofDocument(String documentno)
    {
        Session session = null;
        Transaction tx = null;
        List<Documents> list = null;
        String filename = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            Query query = session.createQuery("from Documents d where d.documentNo='"+documentno+"'");
            list = query.list();
            filename = list.get(0).getFilename();
                    
            tx.commit();
            session.close();
            
            return filename;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return filename;
    }
    
    public String getDSetofDocument(String documentno)
    {
        Session session = null;
        Transaction tx = null;
        List<Documents> list = null;
        String filename = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            Query query = session.createQuery("from Documents d where d.documentNo='"+documentno+"'");
            list = query.list();
            filename = list.get(0).getDocumentSetNo();
                    
            tx.commit();
            session.close();
            
            return filename;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        return filename;
    }
    
    public void deleteSetsByMaker(String dsetno)
    {
        Session session = null;
        Transaction tx = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("delete from DocumentSets s where s.documentSetNo='"+dsetno+"'");
            query.executeUpdate();
            
            Query query2 = session.createQuery("delete from Documents s where s.documentSetNo='"+dsetno+"'");
            query2.executeUpdate();
                    
            tx.commit();
            session.close();
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }
    
    public void deleteDocsByMaker(String docno,String dsetno)
    {
        Session session = null;
        Transaction tx = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            Query query = session.createQuery("delete from Documents s where s.documentNo='"+docno+"'");
            query.executeUpdate();
            
            BigDecimal temp = getNoDocs(dsetno);
            BigDecimal num = temp.subtract(BigDecimal.ONE);
            
            SQLQuery query2 = session.createSQLQuery("update DOCUMENT_SETS set NO_OF_DOCS="+num+" where DOCUMENT_SET_NO='"+dsetno+"'");
            query2.executeUpdate();
                    
            tx.commit();
            session.close();
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }
    
    public List<DocumentSets> getDocumentSetsforChecker()
    {
        Session session = null;
        List<DocumentSets> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from DocumentSets s where s.uploadDateByMaker IS NOT NULL AND s.checkDateByChecker IS NULL order by s.documentSetNo");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    public List<Documents> getDocumentsforChecker()
    {
        Session session = null;
        List<Documents> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from Documents s where s.uploadDateByMaker IS NOT NULL AND s.checkDateByChecker IS NULL order by s.documentNo");
            queryList = query.list();
            
            session.close();
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
            return queryList;
        }
    }
    
    public List<MiscDisbursement> getMiscDisburseDetailsForChecker()
    {
        Session session = null;
        Transaction tx = null;
        List<MiscDisbursement> queryList = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from MiscDisbursement md where md.checkerDate IS NULL");
            queryList = query.list();
                    
            tx.commit();
            session.close();
            
            return queryList;
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
        
        return queryList;
    }
    
    public BigDecimal getMaxUniqueIdMiscDisbursement()
    {
        BigDecimal result = BigDecimal.ZERO;
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            SQLQuery query = session.createSQLQuery("select NVL(max(UNIQUE_ID),0) from MISC_DISBURSEMENT");

            int length = query.list().size();
            if (length != 0) {
                result = (BigDecimal) query.list().get(0);
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
    
    public void checkDocsByChecker(String docno)
    {
        Session session = null;
        Transaction tx = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String date1 = java.time.LocalDate.now().toString();
            String date2[] = date1.split("-");
            String date = date2[2]+date2[1]+date2[0];
            
            Query query = session.createQuery("update Documents s set s.checkDateByChecker=TO_DATE('" + date1 + "','YYYY-MM-DD'), s.status='Checked' where s.documentNo='"+docno+"'");
            query.executeUpdate();
                    
            tx.commit();
            session.close();
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }
    
    
    public void checkSetsByChecker(String dsetno)
    {
        Session session = null;
        Transaction tx = null;
        
        try{
            session = HibernateUtil.getInstance().getSession();
            tx = session.beginTransaction();
            
            String date1 = java.time.LocalDate.now().toString();
            String date2[] = date1.split("-");
            String date = date2[2]+date2[1]+date2[0];
            
            Query query = session.createQuery("update DocumentSets s set s.checkDateByChecker=TO_DATE('" + date1 + "','YYYY-MM-DD'), s.status='Checked' where s.documentSetNo='"+dsetno+"'");
            query.executeUpdate();
                    
            tx.commit();
            session.close();
        }
        catch(Exception e)
        {
            if (session != null) {
                session.close();
            }
            e.printStackTrace();
        }
    }
    
    
}
