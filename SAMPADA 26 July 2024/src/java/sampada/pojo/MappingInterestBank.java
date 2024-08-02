package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * MappingInterestBank generated by hbm2java
 */
public class MappingInterestBank  implements java.io.Serializable {


     private BigDecimal slno;
     private InterestDetails interestDetails;
     private BankStatement bankStatement;
     private BigDecimal mappedAmount;
     private BigDecimal pendingAmount;
     private BigDecimal bankPendingAmount;
     private String checkerStatus;
     private Date entryDate;
     private String remarks;
     private BigDecimal after_int_pool;
     private Date entryTime;
     private BigDecimal poolBal;
       private BigDecimal poolAgcBal;
    private BigDecimal poolRrasBal;
    private BigDecimal intAgcBal;
    private BigDecimal intRrasBal;

    public MappingInterestBank() {
    }

	
    public MappingInterestBank(BigDecimal slno) {
        this.slno = slno;
    }
    public MappingInterestBank(BigDecimal slno, InterestDetails interestDetails, BankStatement bankStatement, BigDecimal mappedAmount, BigDecimal pendingAmount, BigDecimal bankPendingAmount, String checkerStatus, Date entryDate, String remarks, BigDecimal after_int_pool, Date entryTime, BigDecimal poolBal,BigDecimal poolAgcBal, BigDecimal poolRrasBal, BigDecimal intAgcBal, BigDecimal intRrasBal) {
       this.slno = slno;
       this.interestDetails = interestDetails;
       this.bankStatement = bankStatement;
       this.mappedAmount = mappedAmount;
       this.pendingAmount = pendingAmount;
       this.bankPendingAmount = bankPendingAmount;
       this.checkerStatus = checkerStatus;
       this.entryDate = entryDate;
       this.remarks = remarks;
       this.after_int_pool = after_int_pool;
       this.entryTime = entryTime;
       this.poolBal = poolBal;
        this.poolAgcBal = poolAgcBal;
        this.poolRrasBal = poolRrasBal;
        this.intAgcBal = intAgcBal;
        this.intRrasBal = intRrasBal;
    }
   
    public BigDecimal getSlno() {
        return this.slno;
    }
    
    public void setSlno(BigDecimal slno) {
        this.slno = slno;
    }
    public InterestDetails getInterestDetails() {
        return this.interestDetails;
    }
    
    public void setInterestDetails(InterestDetails interestDetails) {
        this.interestDetails = interestDetails;
    }
    public BankStatement getBankStatement() {
        return this.bankStatement;
    }
    
    public void setBankStatement(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }
    public BigDecimal getMappedAmount() {
        return this.mappedAmount;
    }
    
    public void setMappedAmount(BigDecimal mappedAmount) {
        this.mappedAmount = mappedAmount;
    }
    public BigDecimal getPendingAmount() {
        return this.pendingAmount;
    }
    
    public void setPendingAmount(BigDecimal pendingAmount) {
        this.pendingAmount = pendingAmount;
    }
    public BigDecimal getBankPendingAmount() {
        return this.bankPendingAmount;
    }
    
    public void setBankPendingAmount(BigDecimal bankPendingAmount) {
        this.bankPendingAmount = bankPendingAmount;
    }
    public String getCheckerStatus() {
        return this.checkerStatus;
    }
    
    public void setCheckerStatus(String checkerStatus) {
        this.checkerStatus = checkerStatus;
    }
    public Date getEntryDate() {
        return this.entryDate;
    }
    
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public BigDecimal getAfter_int_pool() {
        return this.after_int_pool;
    }
    
    public void setAfter_int_pool(BigDecimal after_int_pool) {
        this.after_int_pool = after_int_pool;
    }
    public Date getEntryTime() {
        return this.entryTime;
    }
    
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
    public BigDecimal getPoolBal() {
        return this.poolBal;
    }
    
    public void setPoolBal(BigDecimal poolBal) {
        this.poolBal = poolBal;
    }

    public BigDecimal getPoolAgcBal() {
        return poolAgcBal;
    }

    public void setPoolAgcBal(BigDecimal poolAgcBal) {
        this.poolAgcBal = poolAgcBal;
    }

    public BigDecimal getPoolRrasBal() {
        return poolRrasBal;
    }

    public void setPoolRrasBal(BigDecimal poolRrasBal) {
        this.poolRrasBal = poolRrasBal;
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


