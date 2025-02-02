package sampada.pojo;
// Generated 3 Apr, 2023 11:54:06 AM by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * RrasPoolAccountDetails generated by hbm2java
 */
public class RrasPoolAccountDetails  implements java.io.Serializable {


     private String accountNumber;
     private String accountName;
     private String ifscCode;
     private String branchName;
     private String branchAddress;
     private String branchNumber;
     private String rldcContactperson;
     private String rldcContactnumber;
     private BigDecimal mainBalance;

    public RrasPoolAccountDetails() {
    }

	
    public RrasPoolAccountDetails(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public RrasPoolAccountDetails(String accountNumber, String accountName, String ifscCode, String branchName, String branchAddress, String branchNumber, String rldcContactperson, String rldcContactnumber, BigDecimal mainBalance) {
       this.accountNumber = accountNumber;
       this.accountName = accountName;
       this.ifscCode = ifscCode;
       this.branchName = branchName;
       this.branchAddress = branchAddress;
       this.branchNumber = branchNumber;
       this.rldcContactperson = rldcContactperson;
       this.rldcContactnumber = rldcContactnumber;
       this.mainBalance = mainBalance;
    }
   
    public String getAccountNumber() {
        return this.accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountName() {
        return this.accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getIfscCode() {
        return this.ifscCode;
    }
    
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public String getBranchName() {
        return this.branchName;
    }
    
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    public String getBranchAddress() {
        return this.branchAddress;
    }
    
    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }
    public String getBranchNumber() {
        return this.branchNumber;
    }
    
    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }
    public String getRldcContactperson() {
        return this.rldcContactperson;
    }
    
    public void setRldcContactperson(String rldcContactperson) {
        this.rldcContactperson = rldcContactperson;
    }
    public String getRldcContactnumber() {
        return this.rldcContactnumber;
    }
    
    public void setRldcContactnumber(String rldcContactnumber) {
        this.rldcContactnumber = rldcContactnumber;
    }
    public BigDecimal getMainBalance() {
        return this.mainBalance;
    }
    
    public void setMainBalance(BigDecimal mainBalance) {
        this.mainBalance = mainBalance;
    }




}


