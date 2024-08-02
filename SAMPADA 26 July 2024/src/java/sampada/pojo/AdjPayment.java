package sampada.pojo;
// Generated Jan 19, 2021 2:14:19 PM by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AdjPayment generated by hbm2java
 */
public class AdjPayment  implements java.io.Serializable {


     private BigDecimal uniqueId;
     private Corporates corporates;
     private BigDecimal adjustAmt;
     private String corpName;
     private Date entryDate;
     private Date entryTime;
     private String remarks;
     private Date checkerDate;
     private String status;
     private BigDecimal totalPay;
     private BigDecimal totalRec;
     private Set adjMappings = new HashSet(0);

    public AdjPayment() {
    }

	
    public AdjPayment(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public AdjPayment(BigDecimal uniqueId, Corporates corporates, BigDecimal adjustAmt, String corpName, Date entryDate, Date entryTime, String remarks, Date checkerDate, String status, BigDecimal totalPay, BigDecimal totalRec, Set adjMappings) {
       this.uniqueId = uniqueId;
       this.corporates = corporates;
       this.adjustAmt = adjustAmt;
       this.corpName = corpName;
       this.entryDate = entryDate;
       this.entryTime = entryTime;
       this.remarks = remarks;
       this.checkerDate = checkerDate;
       this.status = status;
       this.totalPay = totalPay;
       this.totalRec = totalRec;
       this.adjMappings = adjMappings;
    }
   
    public BigDecimal getUniqueId() {
        return this.uniqueId;
    }
    
    public void setUniqueId(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public Corporates getCorporates() {
        return this.corporates;
    }
    
    public void setCorporates(Corporates corporates) {
        this.corporates = corporates;
    }
    public BigDecimal getAdjustAmt() {
        return this.adjustAmt;
    }
    
    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }
    public String getCorpName() {
        return this.corpName;
    }
    
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }
    public Date getEntryDate() {
        return this.entryDate;
    }
    
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public Serializable getEntryTime() {
        return this.entryTime;
    }
    
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Date getCheckerDate() {
        return this.checkerDate;
    }
    
    public void setCheckerDate(Date checkerDate) {
        this.checkerDate = checkerDate;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getTotalPay() {
        return this.totalPay;
    }
    
    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }
    public BigDecimal getTotalRec() {
        return this.totalRec;
    }
    
    public void setTotalRec(BigDecimal totalRec) {
        this.totalRec = totalRec;
    }
    public Set getAdjMappings() {
        return this.adjMappings;
    }
    
    public void setAdjMappings(Set adjMappings) {
        this.adjMappings = adjMappings;
    }




}


