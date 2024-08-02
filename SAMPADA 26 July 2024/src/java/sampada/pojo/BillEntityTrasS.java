package sampada.pojo;
// Generated 30 Aug, 2023 10:25:57 AM by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * BillEntityTrasS generated by hbm2java
 */
public class BillEntityTrasS  implements java.io.Serializable {


     private BigDecimal uniqueId;
     private BigDecimal weekId;
       private Corporates corporates;
     private Entites entites;
     private BigDecimal trasUpEnergyShort;
     private BigDecimal trasUpCharges;
     private BigDecimal trasDownEnergyShort;
     private BigDecimal trasDownCharges;
     private BigDecimal netTras;
     private BigDecimal billYear;
     private BigDecimal revisionNo;
     private String remarks;
     private String payRecvflag;

    public BillEntityTrasS() {
    }

	
    public BillEntityTrasS(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public BillEntityTrasS(BigDecimal uniqueId, BigDecimal weekId, Corporates corporates, Entites entites, BigDecimal trasUpEnergyShort, BigDecimal trasUpCharges, BigDecimal trasDownEnergyShort, BigDecimal trasDownCharges, BigDecimal netTras, BigDecimal billYear, BigDecimal revisionNo, String remarks, String payRecvflag) {
       this.uniqueId = uniqueId;
       this.weekId = weekId;
      this.entites = entites;
       this.corporates = corporates;
       this.trasUpEnergyShort = trasUpEnergyShort;
       this.trasUpCharges = trasUpCharges;
       this.trasDownEnergyShort = trasDownEnergyShort;
       this.trasDownCharges = trasDownCharges;
       this.netTras = netTras;
       this.billYear = billYear;
       this.revisionNo = revisionNo;
       this.remarks = remarks;
       this.payRecvflag = payRecvflag;
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

    public Corporates getCorporates() {
        return corporates;
    }

    public void setCorporates(Corporates corporates) {
        this.corporates = corporates;
    }

    public Entites getEntites() {
        return entites;
    }

    public void setEntites(Entites entites) {
        this.entites = entites;
    }
  
    public BigDecimal getTrasUpEnergyShort() {
        return this.trasUpEnergyShort;
    }
    
    public void setTrasUpEnergyShort(BigDecimal trasUpEnergyShort) {
        this.trasUpEnergyShort = trasUpEnergyShort;
    }
    public BigDecimal getTrasUpCharges() {
        return this.trasUpCharges;
    }
    
    public void setTrasUpCharges(BigDecimal trasUpCharges) {
        this.trasUpCharges = trasUpCharges;
    }
    public BigDecimal getTrasDownEnergyShort() {
        return this.trasDownEnergyShort;
    }
    
    public void setTrasDownEnergyShort(BigDecimal trasDownEnergyShort) {
        this.trasDownEnergyShort = trasDownEnergyShort;
    }
    public BigDecimal getTrasDownCharges() {
        return this.trasDownCharges;
    }
    
    public void setTrasDownCharges(BigDecimal trasDownCharges) {
        this.trasDownCharges = trasDownCharges;
    }
    public BigDecimal getNetTras() {
        return this.netTras;
    }
    
    public void setNetTras(BigDecimal netTras) {
        this.netTras = netTras;
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
    public String getPayRecvflag() {
        return this.payRecvflag;
    }
    
    public void setPayRecvflag(String payRecvflag) {
        this.payRecvflag = payRecvflag;
    }




}


