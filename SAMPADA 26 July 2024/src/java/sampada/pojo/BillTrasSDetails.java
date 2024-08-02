package sampada.pojo;
// Generated 30 Aug, 2023 10:25:57 AM by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * BillTrasSDetails generated by hbm2java
 */
public class BillTrasSDetails  implements java.io.Serializable {


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
     private String billCategory;
     private Date entryDate;

    public BillTrasSDetails() {
    }

	
    public BillTrasSDetails(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public BillTrasSDetails(BigDecimal uniqueId, BigDecimal weekId, Date weekFromdate, Date weekTodate, Date billingDate, BigDecimal billYear, Date billDueDate, BigDecimal revisionNo, String remarks, String billNo, String billCategory, Date entryDate) {
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
       this.billCategory = billCategory;
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
    public String getBillCategory() {
        return this.billCategory;
    }
    
    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    




}


