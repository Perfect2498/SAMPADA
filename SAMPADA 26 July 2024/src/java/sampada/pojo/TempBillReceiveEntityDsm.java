package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * TempBillReceiveEntityDsm generated by hbm2java
 */
public class TempBillReceiveEntityDsm  implements java.io.Serializable {


     private BigDecimal uniqueId;
     private Corporates corporates;
     private Entites entites;
     private BigDecimal weekId;
     private BigDecimal cappingCharges;
     private BigDecimal additionalCharges;
     private BigDecimal signCharges;
     private BigDecimal recvCharges;
     private BigDecimal netDsm;
     private BigDecimal billYear;
     private BigDecimal revisionNo;
     private String remarks;
     private BigDecimal wrNetDsm;

    public TempBillReceiveEntityDsm() {
    }

	
    public TempBillReceiveEntityDsm(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public TempBillReceiveEntityDsm(BigDecimal uniqueId, Corporates corporates, Entites entites, BigDecimal weekId, BigDecimal cappingCharges, BigDecimal additionalCharges, BigDecimal signCharges, BigDecimal recvCharges, BigDecimal netDsm, BigDecimal billYear, BigDecimal revisionNo, String remarks, BigDecimal wrNetDsm) {
       this.uniqueId = uniqueId;
       this.corporates = corporates;
       this.entites = entites;
       this.weekId = weekId;
       this.cappingCharges = cappingCharges;
       this.additionalCharges = additionalCharges;
       this.signCharges = signCharges;
       this.recvCharges = recvCharges;
       this.netDsm = netDsm;
       this.billYear = billYear;
       this.revisionNo = revisionNo;
       this.remarks = remarks;
       this.wrNetDsm = wrNetDsm;
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
    public Entites getEntites() {
        return this.entites;
    }
    
    public void setEntites(Entites entites) {
        this.entites = entites;
    }
    public BigDecimal getWeekId() {
        return this.weekId;
    }
    
    public void setWeekId(BigDecimal weekId) {
        this.weekId = weekId;
    }
    public BigDecimal getCappingCharges() {
        return this.cappingCharges;
    }
    
    public void setCappingCharges(BigDecimal cappingCharges) {
        this.cappingCharges = cappingCharges;
    }
    public BigDecimal getAdditionalCharges() {
        return this.additionalCharges;
    }
    
    public void setAdditionalCharges(BigDecimal additionalCharges) {
        this.additionalCharges = additionalCharges;
    }
    public BigDecimal getSignCharges() {
        return this.signCharges;
    }
    
    public void setSignCharges(BigDecimal signCharges) {
        this.signCharges = signCharges;
    }
    public BigDecimal getRecvCharges() {
        return this.recvCharges;
    }
    
    public void setRecvCharges(BigDecimal recvCharges) {
        this.recvCharges = recvCharges;
    }
    public BigDecimal getNetDsm() {
        return this.netDsm;
    }
    
    public void setNetDsm(BigDecimal netDsm) {
        this.netDsm = netDsm;
    }
    public BigDecimal getBillYear() {
        return this.billYear;
    }
    
    public void setBillYear(BigDecimal billYear) {
        this.billYear = billYear;
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
    public BigDecimal getWrNetDsm() {
        return this.wrNetDsm;
    }
    
    public void setWrNetDsm(BigDecimal wrNetDsm) {
        this.wrNetDsm = wrNetDsm;
    }




}


