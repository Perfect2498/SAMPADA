package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * MiscDisbursement generated by hbm2java
 */
public class MiscDisbursement  implements java.io.Serializable {


     private BigDecimal uniqueId;
     private BigDecimal stmtId;
     private BigDecimal refundAmt;
     private BigDecimal corpId;
     private String corpName;
     private String documentSet;
     private String document;
     private Date entryDate;
     private String status;
     private Date makerDate;
     private Date checkerDate;
     private String remarks;
     private String amtCategory;
     private Date entryTime;
     private BigDecimal mainPoolBalance;

    public MiscDisbursement() {
    }

	
    public MiscDisbursement(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public MiscDisbursement(BigDecimal uniqueId, BigDecimal stmtId, BigDecimal refundAmt, BigDecimal corpId, String corpName, String documentSet, String document, Date entryDate, String status, Date makerDate, Date checkerDate, String remarks, String amtCategory, Date entryTime, BigDecimal mainPoolBalance) {
       this.uniqueId = uniqueId;
       this.stmtId = stmtId;
       this.refundAmt = refundAmt;
       this.corpId = corpId;
       this.corpName = corpName;
       this.documentSet = documentSet;
       this.document = document;
       this.entryDate = entryDate;
       this.status = status;
       this.makerDate = makerDate;
       this.checkerDate = checkerDate;
       this.remarks = remarks;
       this.amtCategory = amtCategory;
       this.entryTime = entryTime;
       this.mainPoolBalance = mainPoolBalance;
    }
   
    public BigDecimal getUniqueId() {
        return this.uniqueId;
    }
    
    public void setUniqueId(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public BigDecimal getStmtId() {
        return this.stmtId;
    }
    
    public void setStmtId(BigDecimal stmtId) {
        this.stmtId = stmtId;
    }
    public BigDecimal getRefundAmt() {
        return this.refundAmt;
    }
    
    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }
    public BigDecimal getCorpId() {
        return this.corpId;
    }
    
    public void setCorpId(BigDecimal corpId) {
        this.corpId = corpId;
    }
    public String getCorpName() {
        return this.corpName;
    }
    
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }
    public String getDocumentSet() {
        return this.documentSet;
    }
    
    public void setDocumentSet(String documentSet) {
        this.documentSet = documentSet;
    }
    public String getDocument() {
        return this.document;
    }
    
    public void setDocument(String document) {
        this.document = document;
    }
    public Date getEntryDate() {
        return this.entryDate;
    }
    
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getMakerDate() {
        return this.makerDate;
    }
    
    public void setMakerDate(Date makerDate) {
        this.makerDate = makerDate;
    }
    public Date getCheckerDate() {
        return this.checkerDate;
    }
    
    public void setCheckerDate(Date checkerDate) {
        this.checkerDate = checkerDate;
    }
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getAmtCategory() {
        return this.amtCategory;
    }
    
    public void setAmtCategory(String amtCategory) {
        this.amtCategory = amtCategory;
    }
    public Date getEntryTime() {
        return this.entryTime;
    }
    
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
    public BigDecimal getMainPoolBalance() {
        return this.mainPoolBalance;
    }
    
    public void setMainPoolBalance(BigDecimal mainPoolBalance) {
        this.mainPoolBalance = mainPoolBalance;
    }




}


