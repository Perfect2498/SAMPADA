package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * BillReceiveEntityRras generated by hbm2java
 */
public class BillReceiveEntityRras  implements java.io.Serializable {


     private BigDecimal uniqueId;
     private Corporates corporates;
     private Entites entites;
     private BigDecimal weekId;
     private BigDecimal energyVae;
     private BigDecimal fixedCharges;
     private BigDecimal variableCharges;
     private BigDecimal markupCharges;
     private BigDecimal netRras;
     private Date billingDate;
     private BigDecimal billYear;
     private Date billDueDate;
     private BigDecimal revisionNo;
     private String remarks;

    public BillReceiveEntityRras() {
    }

	
    public BillReceiveEntityRras(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public BillReceiveEntityRras(BigDecimal uniqueId, Corporates corporates, Entites entites, BigDecimal weekId, BigDecimal energyVae, BigDecimal fixedCharges, BigDecimal variableCharges, BigDecimal markupCharges, BigDecimal netRras, Date billingDate, BigDecimal billYear, Date billDueDate, BigDecimal revisionNo, String remarks) {
       this.uniqueId = uniqueId;
       this.corporates = corporates;
       this.entites = entites;
       this.weekId = weekId;
       this.energyVae = energyVae;
       this.fixedCharges = fixedCharges;
       this.variableCharges = variableCharges;
       this.markupCharges = markupCharges;
       this.netRras = netRras;
       this.billingDate = billingDate;
       this.billYear = billYear;
       this.billDueDate = billDueDate;
       this.revisionNo = revisionNo;
       this.remarks = remarks;
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
    public BigDecimal getEnergyVae() {
        return this.energyVae;
    }
    
    public void setEnergyVae(BigDecimal energyVae) {
        this.energyVae = energyVae;
    }
    public BigDecimal getFixedCharges() {
        return this.fixedCharges;
    }
    
    public void setFixedCharges(BigDecimal fixedCharges) {
        this.fixedCharges = fixedCharges;
    }
    public BigDecimal getVariableCharges() {
        return this.variableCharges;
    }
    
    public void setVariableCharges(BigDecimal variableCharges) {
        this.variableCharges = variableCharges;
    }
    public BigDecimal getMarkupCharges() {
        return this.markupCharges;
    }
    
    public void setMarkupCharges(BigDecimal markupCharges) {
        this.markupCharges = markupCharges;
    }
    public BigDecimal getNetRras() {
        return this.netRras;
    }
    
    public void setNetRras(BigDecimal netRras) {
        this.netRras = netRras;
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




}


