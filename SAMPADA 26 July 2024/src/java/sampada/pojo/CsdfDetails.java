package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

/**
 * CsdfDetails generated by hbm2java
 */
public class CsdfDetails implements java.io.Serializable {

    private BigDecimal slno;
    private BigDecimal csdfAmount;
    private BigDecimal mainPoolBalance;
    private String checkerStatus;
    private String csdfMonth;
    private String remarks;
    private String amtCategory;
    private Date entryDate;
    private BigDecimal csdfYear;
    private Date entryTime;
    private BigDecimal poolAgcBal;
    private BigDecimal poolRrasBal;

    public CsdfDetails() {
    }

    public CsdfDetails(BigDecimal slno) {
        this.slno = slno;
    }

    public CsdfDetails(BigDecimal slno, BigDecimal csdfAmount, BigDecimal mainPoolBalance, String checkerStatus, String csdfMonth, String remarks, String amtCategory, Date entryDate, BigDecimal csdfYear, Date entryTime, BigDecimal poolAgcBal, BigDecimal poolRrasBal) {
        this.slno = slno;
        this.csdfAmount = csdfAmount;
        this.mainPoolBalance = mainPoolBalance;
        this.checkerStatus = checkerStatus;
        this.csdfMonth = csdfMonth;
        this.remarks = remarks;
        this.amtCategory = amtCategory;
        this.entryDate = entryDate;
        this.csdfYear = csdfYear;
        this.entryTime = entryTime;
        this.poolAgcBal = poolAgcBal;
        this.poolRrasBal = poolRrasBal;
    }

    public BigDecimal getSlno() {
        return this.slno;
    }

    public void setSlno(BigDecimal slno) {
        this.slno = slno;
    }

    public BigDecimal getCsdfAmount() {
        return this.csdfAmount;
    }

    public void setCsdfAmount(BigDecimal csdfAmount) {
        this.csdfAmount = csdfAmount;
    }

    public BigDecimal getMainPoolBalance() {
        return this.mainPoolBalance;
    }

    public void setMainPoolBalance(BigDecimal mainPoolBalance) {
        this.mainPoolBalance = mainPoolBalance;
    }

    public String getCheckerStatus() {
        return this.checkerStatus;
    }

    public void setCheckerStatus(String checkerStatus) {
        this.checkerStatus = checkerStatus;
    }

    public String getCsdfMonth() {
        return this.csdfMonth;
    }

    public void setCsdfMonth(String csdfMonth) {
        this.csdfMonth = csdfMonth;
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

    public Date getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getCsdfYear() {
        return this.csdfYear;
    }

    public void setCsdfYear(BigDecimal csdfYear) {
        this.csdfYear = csdfYear;
    }

    public Date getEntryTime() {
        return this.entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
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

    
}
