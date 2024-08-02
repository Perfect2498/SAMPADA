package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entites generated by hbm2java
 */
public class Entites  implements java.io.Serializable {


     private BigDecimal entityId;
     private Corporates corporates;
     private String entittyName;
     private String entityType;
     private String shortEntityname;
     private String bankName;
     private String branchName;
     private String accountNumber;
     private String ifscCode;
     private String rtgsNeftFlag;
     private String address;
     private String entityLocation;
     private String stateName;
     private String dsmContact;
     private String rrasContact;
     private String congContact;
     private String rectContact;
     private String mobile;
     private String office;
     private String rrasFlag;
     private String agcFlag;
     private String frasFlag;
     private String frasContact;
     private String srasFlag;
     private String trasFlag;
     private Set tempBillEntityAgcs = new HashSet(0);
     private Set tempBillPayableEntityRrases = new HashSet(0);
     private Set billReceiveEntityRrases = new HashSet(0);
     private Set billEntityAgcs = new HashSet(0);
     private Set billPayableEntityRrases = new HashSet(0);
     private Set tempBillReceiveEntityDsms = new HashSet(0);
     private Set tempBillPayableEntityDsms = new HashSet(0);
     private Set tempBillReceiveEntityRrases = new HashSet(0);
     private Set billPayableEntityDsms = new HashSet(0);
     private Set billReceiveEntityDsms = new HashSet(0);
     private Set tempBillEntityFrases = new HashSet(0);
     private Set billEntityFrases = new HashSet(0);

    public Entites() {
    }

	
    public Entites(BigDecimal entityId) {
        this.entityId = entityId;
    }
    public Entites(BigDecimal entityId, Corporates corporates, String entittyName, String entityType, String shortEntityname, String bankName, String branchName, String accountNumber, String ifscCode, String rtgsNeftFlag, String address, String entityLocation, String stateName, String dsmContact, String rrasContact, String congContact, String rectContact, String mobile, String office, String rrasFlag, String agcFlag, String frasFlag, String frasContact,String srasFlag,String trasFlag, Set tempBillEntityAgcs, Set tempBillPayableEntityRrases, Set billReceiveEntityRrases, Set billEntityAgcs, Set billPayableEntityRrases, Set tempBillReceiveEntityDsms, Set tempBillPayableEntityDsms, Set tempBillReceiveEntityRrases, Set billPayableEntityDsms, Set billReceiveEntityDsms, Set tempBillEntityFrases, Set billEntityFrases) {
       this.entityId = entityId;
       this.corporates = corporates;
       this.entittyName = entittyName;
       this.entityType = entityType;
       this.shortEntityname = shortEntityname;
       this.bankName = bankName;
       this.branchName = branchName;
       this.accountNumber = accountNumber;
       this.ifscCode = ifscCode;
       this.rtgsNeftFlag = rtgsNeftFlag;
       this.address = address;
       this.entityLocation = entityLocation;
       this.stateName = stateName;
       this.dsmContact = dsmContact;
       this.rrasContact = rrasContact;
       this.congContact = congContact;
       this.rectContact = rectContact;
       this.mobile = mobile;
       this.office = office;
       this.rrasFlag = rrasFlag;
       this.agcFlag = agcFlag;
       this.frasFlag = frasFlag;
       this.frasContact = frasContact;
       this.srasFlag=srasFlag;
       this.trasFlag=trasFlag;
       this.tempBillEntityAgcs = tempBillEntityAgcs;
       this.tempBillPayableEntityRrases = tempBillPayableEntityRrases;
       this.billReceiveEntityRrases = billReceiveEntityRrases;
       this.billEntityAgcs = billEntityAgcs;
       this.billPayableEntityRrases = billPayableEntityRrases;
       this.tempBillReceiveEntityDsms = tempBillReceiveEntityDsms;
       this.tempBillPayableEntityDsms = tempBillPayableEntityDsms;
       this.tempBillReceiveEntityRrases = tempBillReceiveEntityRrases;
       this.billPayableEntityDsms = billPayableEntityDsms;
       this.billReceiveEntityDsms = billReceiveEntityDsms;
       this.tempBillEntityFrases = tempBillEntityFrases;
       this.billEntityFrases = billEntityFrases;
    }
   
    public BigDecimal getEntityId() {
        return this.entityId;
    }
    
    public void setEntityId(BigDecimal entityId) {
        this.entityId = entityId;
    }
    public Corporates getCorporates() {
        return this.corporates;
    }
    
    public void setCorporates(Corporates corporates) {
        this.corporates = corporates;
    }
    public String getEntittyName() {
        return this.entittyName;
    }
    
    public void setEntittyName(String entittyName) {
        this.entittyName = entittyName;
    }
    public String getEntityType() {
        return this.entityType;
    }
    
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    public String getShortEntityname() {
        return this.shortEntityname;
    }
    
    public void setShortEntityname(String shortEntityname) {
        this.shortEntityname = shortEntityname;
    }
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBranchName() {
        return this.branchName;
    }
    
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    public String getAccountNumber() {
        return this.accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getIfscCode() {
        return this.ifscCode;
    }
    
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public String getRtgsNeftFlag() {
        return this.rtgsNeftFlag;
    }
    
    public void setRtgsNeftFlag(String rtgsNeftFlag) {
        this.rtgsNeftFlag = rtgsNeftFlag;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEntityLocation() {
        return this.entityLocation;
    }
    
    public void setEntityLocation(String entityLocation) {
        this.entityLocation = entityLocation;
    }
    public String getStateName() {
        return this.stateName;
    }
    
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    public String getDsmContact() {
        return this.dsmContact;
    }
    
    public void setDsmContact(String dsmContact) {
        this.dsmContact = dsmContact;
    }
    public String getRrasContact() {
        return this.rrasContact;
    }
    
    public void setRrasContact(String rrasContact) {
        this.rrasContact = rrasContact;
    }
    public String getCongContact() {
        return this.congContact;
    }
    
    public void setCongContact(String congContact) {
        this.congContact = congContact;
    }
    public String getRectContact() {
        return this.rectContact;
    }
    
    public void setRectContact(String rectContact) {
        this.rectContact = rectContact;
    }
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getOffice() {
        return this.office;
    }
    
    public void setOffice(String office) {
        this.office = office;
    }
    public String getRrasFlag() {
        return this.rrasFlag;
    }
    
    public void setRrasFlag(String rrasFlag) {
        this.rrasFlag = rrasFlag;
    }
    public String getAgcFlag() {
        return this.agcFlag;
    }
    
    public void setAgcFlag(String agcFlag) {
        this.agcFlag = agcFlag;
    }
    public String getFrasFlag() {
        return this.frasFlag;
    }
    
    public void setFrasFlag(String frasFlag) {
        this.frasFlag = frasFlag;
    }
    public String getFrasContact() {
        return this.frasContact;
    }
    
    public void setFrasContact(String frasContact) {
        this.frasContact = frasContact;
    }

    public String getSrasFlag() {
        return srasFlag;
    }

    public void setSrasFlag(String srasFlag) {
        this.srasFlag = srasFlag;
    }

    public String getTrasFlag() {
        return trasFlag;
    }

    public void setTrasFlag(String trasFlag) {
        this.trasFlag = trasFlag;
    }
    
    
    public Set getTempBillEntityAgcs() {
        return this.tempBillEntityAgcs;
    }
    
    public void setTempBillEntityAgcs(Set tempBillEntityAgcs) {
        this.tempBillEntityAgcs = tempBillEntityAgcs;
    }
    public Set getTempBillPayableEntityRrases() {
        return this.tempBillPayableEntityRrases;
    }
    
    public void setTempBillPayableEntityRrases(Set tempBillPayableEntityRrases) {
        this.tempBillPayableEntityRrases = tempBillPayableEntityRrases;
    }
    public Set getBillReceiveEntityRrases() {
        return this.billReceiveEntityRrases;
    }
    
    public void setBillReceiveEntityRrases(Set billReceiveEntityRrases) {
        this.billReceiveEntityRrases = billReceiveEntityRrases;
    }
    public Set getBillEntityAgcs() {
        return this.billEntityAgcs;
    }
    
    public void setBillEntityAgcs(Set billEntityAgcs) {
        this.billEntityAgcs = billEntityAgcs;
    }
    public Set getBillPayableEntityRrases() {
        return this.billPayableEntityRrases;
    }
    
    public void setBillPayableEntityRrases(Set billPayableEntityRrases) {
        this.billPayableEntityRrases = billPayableEntityRrases;
    }
    public Set getTempBillReceiveEntityDsms() {
        return this.tempBillReceiveEntityDsms;
    }
    
    public void setTempBillReceiveEntityDsms(Set tempBillReceiveEntityDsms) {
        this.tempBillReceiveEntityDsms = tempBillReceiveEntityDsms;
    }
    public Set getTempBillPayableEntityDsms() {
        return this.tempBillPayableEntityDsms;
    }
    
    public void setTempBillPayableEntityDsms(Set tempBillPayableEntityDsms) {
        this.tempBillPayableEntityDsms = tempBillPayableEntityDsms;
    }
    public Set getTempBillReceiveEntityRrases() {
        return this.tempBillReceiveEntityRrases;
    }
    
    public void setTempBillReceiveEntityRrases(Set tempBillReceiveEntityRrases) {
        this.tempBillReceiveEntityRrases = tempBillReceiveEntityRrases;
    }
    public Set getBillPayableEntityDsms() {
        return this.billPayableEntityDsms;
    }
    
    public void setBillPayableEntityDsms(Set billPayableEntityDsms) {
        this.billPayableEntityDsms = billPayableEntityDsms;
    }
    public Set getBillReceiveEntityDsms() {
        return this.billReceiveEntityDsms;
    }
    
    public void setBillReceiveEntityDsms(Set billReceiveEntityDsms) {
        this.billReceiveEntityDsms = billReceiveEntityDsms;
    }
    public Set getTempBillEntityFrases() {
        return this.tempBillEntityFrases;
    }
    
    public void setTempBillEntityFrases(Set tempBillEntityFrases) {
        this.tempBillEntityFrases = tempBillEntityFrases;
    }
    public Set getBillEntityFrases() {
        return this.billEntityFrases;
    }
    
    public void setBillEntityFrases(Set billEntityFrases) {
        this.billEntityFrases = billEntityFrases;
    }




}


