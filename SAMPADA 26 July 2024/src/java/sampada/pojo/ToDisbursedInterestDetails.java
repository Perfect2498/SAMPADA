package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ToDisbursedInterestDetails generated by hbm2java
 */
public class ToDisbursedInterestDetails  implements java.io.Serializable {


     private BigDecimal interestId;
     private Corporates corporates;
     private BigDecimal weekId;
     private Date billingDate;
     private Date billingDuedate;
     private BigDecimal billedAmount;
     private BigDecimal disbursedAmount;
     private Date disbursedDate;
     private BigDecimal interestBilledamount;
     private BigDecimal interestAmount;
     private BigDecimal interestPaidamount;
     private BigDecimal interestPendingamount;
     private Date interestPaiddate;
     private String billType;
     private String billCategory;
     private BigDecimal revisionNo;
     private Date entryDate;
     private String checkerStatus;
     private BigDecimal noofdays;
     private BigDecimal billYear;
     private String remarks;
     private Set paymentInterestDisbursements = new HashSet(0);

    public ToDisbursedInterestDetails() {
    }

	
    public ToDisbursedInterestDetails(BigDecimal interestId) {
        this.interestId = interestId;
    }
    public ToDisbursedInterestDetails(BigDecimal interestId, Corporates corporates, BigDecimal weekId, Date billingDate, Date billingDuedate, BigDecimal billedAmount, BigDecimal disbursedAmount, Date disbursedDate, BigDecimal interestBilledamount, BigDecimal interestAmount, BigDecimal interestPaidamount, BigDecimal interestPendingamount, Date interestPaiddate, String billType, String billCategory, BigDecimal revisionNo, Date entryDate, String checkerStatus, BigDecimal noofdays, BigDecimal billYear, String remarks, Set paymentInterestDisbursements) {
       this.interestId = interestId;
       this.corporates = corporates;
       this.weekId = weekId;
       this.billingDate = billingDate;
       this.billingDuedate = billingDuedate;
       this.billedAmount = billedAmount;
       this.disbursedAmount = disbursedAmount;
       this.disbursedDate = disbursedDate;
       this.interestBilledamount = interestBilledamount;
       this.interestAmount = interestAmount;
       this.interestPaidamount = interestPaidamount;
       this.interestPendingamount = interestPendingamount;
       this.interestPaiddate = interestPaiddate;
       this.billType = billType;
       this.billCategory = billCategory;
       this.revisionNo = revisionNo;
       this.entryDate = entryDate;
       this.checkerStatus = checkerStatus;
       this.noofdays = noofdays;
       this.billYear = billYear;
       this.remarks = remarks;
       this.paymentInterestDisbursements = paymentInterestDisbursements;
    }
   
    public BigDecimal getInterestId() {
        return this.interestId;
    }
    
    public void setInterestId(BigDecimal interestId) {
        this.interestId = interestId;
    }
    public Corporates getCorporates() {
        return this.corporates;
    }
    
    public void setCorporates(Corporates corporates) {
        this.corporates = corporates;
    }
    public BigDecimal getWeekId() {
        return this.weekId;
    }
    
    public void setWeekId(BigDecimal weekId) {
        this.weekId = weekId;
    }
    public Date getBillingDate() {
        return this.billingDate;
    }
    
    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }
    public Date getBillingDuedate() {
        return this.billingDuedate;
    }
    
    public void setBillingDuedate(Date billingDuedate) {
        this.billingDuedate = billingDuedate;
    }
    public BigDecimal getBilledAmount() {
        return this.billedAmount;
    }
    
    public void setBilledAmount(BigDecimal billedAmount) {
        this.billedAmount = billedAmount;
    }
    public BigDecimal getDisbursedAmount() {
        return this.disbursedAmount;
    }
    
    public void setDisbursedAmount(BigDecimal disbursedAmount) {
        this.disbursedAmount = disbursedAmount;
    }
    public Date getDisbursedDate() {
        return this.disbursedDate;
    }
    
    public void setDisbursedDate(Date disbursedDate) {
        this.disbursedDate = disbursedDate;
    }
    public BigDecimal getInterestBilledamount() {
        return this.interestBilledamount;
    }
    
    public void setInterestBilledamount(BigDecimal interestBilledamount) {
        this.interestBilledamount = interestBilledamount;
    }
    public BigDecimal getInterestAmount() {
        return this.interestAmount;
    }
    
    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }
    public BigDecimal getInterestPaidamount() {
        return this.interestPaidamount;
    }
    
    public void setInterestPaidamount(BigDecimal interestPaidamount) {
        this.interestPaidamount = interestPaidamount;
    }
    public BigDecimal getInterestPendingamount() {
        return this.interestPendingamount;
    }
    
    public void setInterestPendingamount(BigDecimal interestPendingamount) {
        this.interestPendingamount = interestPendingamount;
    }
    public Date getInterestPaiddate() {
        return this.interestPaiddate;
    }
    
    public void setInterestPaiddate(Date interestPaiddate) {
        this.interestPaiddate = interestPaiddate;
    }
    public String getBillType() {
        return this.billType;
    }
    
    public void setBillType(String billType) {
        this.billType = billType;
    }
    public String getBillCategory() {
        return this.billCategory;
    }
    
    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }
    public BigDecimal getRevisionNo() {
        return this.revisionNo;
    }
    
    public void setRevisionNo(BigDecimal revisionNo) {
        this.revisionNo = revisionNo;
    }
    public Date getEntryDate() {
        return this.entryDate;
    }
    
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public String getCheckerStatus() {
        return this.checkerStatus;
    }
    
    public void setCheckerStatus(String checkerStatus) {
        this.checkerStatus = checkerStatus;
    }
    public BigDecimal getNoofdays() {
        return this.noofdays;
    }
    
    public void setNoofdays(BigDecimal noofdays) {
        this.noofdays = noofdays;
    }
    public BigDecimal getBillYear() {
        return this.billYear;
    }
    
    public void setBillYear(BigDecimal billYear) {
        this.billYear = billYear;
    }
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Set getPaymentInterestDisbursements() {
        return this.paymentInterestDisbursements;
    }
    
    public void setPaymentInterestDisbursements(Set paymentInterestDisbursements) {
        this.paymentInterestDisbursements = paymentInterestDisbursements;
    }




}


