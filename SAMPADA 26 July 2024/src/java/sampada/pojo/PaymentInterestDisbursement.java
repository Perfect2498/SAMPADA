package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * PaymentInterestDisbursement generated by hbm2java
 */
public class PaymentInterestDisbursement  implements java.io.Serializable {


     private BigDecimal slno;
     private DisbursedInterestDetails disbursedInterestDetails;
     private BigDecimal interestAmount;
     private BigDecimal interestPaidamount;
     private BigDecimal interestPendingamount;
     private Date interestPaiddate;
     private Date entryDate;
     private String checkerStatus;
     private String remarks;
     private BigDecimal intPoolBal;
     private Date entryTime;
      private BigDecimal intAgcBal;
    private BigDecimal intRrasBal;

    public PaymentInterestDisbursement() {
    }

	
    public PaymentInterestDisbursement(BigDecimal slno) {
        this.slno = slno;
    }
    public PaymentInterestDisbursement(BigDecimal slno, DisbursedInterestDetails disbursedInterestDetails, BigDecimal interestAmount, BigDecimal interestPaidamount, BigDecimal interestPendingamount, Date interestPaiddate, Date entryDate, String checkerStatus, String remarks, BigDecimal intPoolBal, Date entryTime,BigDecimal intAgcBal, BigDecimal intRrasBal) {
       this.slno = slno;
       this.disbursedInterestDetails = disbursedInterestDetails;
       this.interestAmount = interestAmount;
       this.interestPaidamount = interestPaidamount;
       this.interestPendingamount = interestPendingamount;
       this.interestPaiddate = interestPaiddate;
       this.entryDate = entryDate;
       this.checkerStatus = checkerStatus;
       this.remarks = remarks;
       this.intPoolBal = intPoolBal;
       this.entryTime = entryTime;
       this.intAgcBal = intAgcBal;
        this.intRrasBal = intRrasBal;
    }
   
    public BigDecimal getSlno() {
        return this.slno;
    }
    
    public void setSlno(BigDecimal slno) {
        this.slno = slno;
    }
    public DisbursedInterestDetails getDisbursedInterestDetails() {
        return this.disbursedInterestDetails;
    }
    
    public void setDisbursedInterestDetails(DisbursedInterestDetails disbursedInterestDetails) {
        this.disbursedInterestDetails = disbursedInterestDetails;
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
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public BigDecimal getIntPoolBal() {
        return this.intPoolBal;
    }
    
    public void setIntPoolBal(BigDecimal intPoolBal) {
        this.intPoolBal = intPoolBal;
    }
    public Date getEntryTime() {
        return this.entryTime;
    }
    
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public BigDecimal getIntAgcBal() {
        return intAgcBal;
    }

    public void setIntAgcBal(BigDecimal intAgcBal) {
        this.intAgcBal = intAgcBal;
    }

    public BigDecimal getIntRrasBal() {
        return intRrasBal;
    }

    public void setIntRrasBal(BigDecimal intRrasBal) {
        this.intRrasBal = intRrasBal;
    }




}


