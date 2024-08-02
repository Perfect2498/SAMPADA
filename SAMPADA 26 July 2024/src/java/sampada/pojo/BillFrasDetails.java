package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * BillFrasDetails generated by hbm2java
 */
public class BillFrasDetails  implements java.io.Serializable {


     private BigDecimal uniqueId;
     private BigDecimal weekId;
     private Date weekFromdate;
     private Date weekTodate;
     private Date billingDate;
     private BigDecimal billYear;
     private Date billDueDate;
     private BigDecimal revisionNo;
     private String remarks;
     private String billNo;
     private Date entryDate;

    public BillFrasDetails() {
    }

	
    public BillFrasDetails(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public BillFrasDetails(BigDecimal uniqueId, BigDecimal weekId, Date weekFromdate, Date weekTodate, Date billingDate, BigDecimal billYear, Date billDueDate, BigDecimal revisionNo, String remarks, String billNo, Date entryDate) {
       this.uniqueId = uniqueId;
       this.weekId = weekId;
       this.weekFromdate = weekFromdate;
       this.weekTodate = weekTodate;
       this.billingDate = billingDate;
       this.billYear = billYear;
       this.billDueDate = billDueDate;
       this.revisionNo = revisionNo;
       this.remarks = remarks;
       this.billNo = billNo;
       this.entryDate = entryDate;
    }
   
    public BigDecimal getUniqueId() {
        return this.uniqueId;
    }
    
    public void setUniqueId(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public BigDecimal getWeekId() {
        return this.weekId;
    }
    
    public void setWeekId(BigDecimal weekId) {
        this.weekId = weekId;
    }
    public Date getWeekFromdate() {
        return this.weekFromdate;
    }
    
    public void setWeekFromdate(Date weekFromdate) {
        this.weekFromdate = weekFromdate;
    }
    public Date getWeekTodate() {
        return this.weekTodate;
    }
    
    public void setWeekTodate(Date weekTodate) {
        this.weekTodate = weekTodate;
    }
    public Date getBillingDate() {
        return this.billingDate;
    }
    
    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }
    public BigDecimal getBillYear() {
        return this.billYear;
    }
    
    public void setBillYear(BigDecimal billYear) {
        this.billYear = billYear;
    }
    public Date getBillDueDate() {
        return this.billDueDate;
    }
    
    public void setBillDueDate(Date billDueDate) {
        this.billDueDate = billDueDate;
    }
    public BigDecimal getRevisionNo() {
        return this.revisionNo;
    }
    
    public void setRevisionNo(BigDecimal revisionNo) {
        this.revisionNo = revisionNo;
    }
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getBillNo() {
        return this.billNo;
    }
    
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    public Date getEntryDate() {
        return this.entryDate;
    }
    
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }




}


