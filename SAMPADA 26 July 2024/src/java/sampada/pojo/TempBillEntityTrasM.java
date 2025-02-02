package sampada.pojo;
// Generated 23 Aug, 2023 2:43:08 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * TempBillEntityTrasM generated by hbm2java
 */
public class TempBillEntityTrasM  implements java.io.Serializable {


     private BigDecimal uniqueId;
     private BigDecimal weekId;
  private Corporates corporates;
     private Entites entites;
     private BigDecimal trasUpClearedAhead;
     private BigDecimal trasUpScheduledAhead;
     private BigDecimal trasUpEnergyChargesAhead;
     private BigDecimal trasUpComChargesAhead;
     private BigDecimal trasUpClearedReal;
     private BigDecimal trasUpScheduledReal;
     private BigDecimal trasUpEnergyChargesReal;
     private BigDecimal trasUpComChargesReal;
     private BigDecimal trasUpTotalCharges;
     private BigDecimal trasDownScheduledAhead;
     private BigDecimal trasDownEnergyChargesAhead;
     private BigDecimal trasDownScheduledReal;
     private BigDecimal trasDownEnergyChargesReal;
     private BigDecimal netTras;
     private BigDecimal billYear;
     private BigDecimal revisionNo;
     private String remarks;
     private BigDecimal wrNetTras;
     private String billStatus;
     private BigDecimal prevWrNetTras;

    public TempBillEntityTrasM() {
    }

	
    public TempBillEntityTrasM(BigDecimal uniqueId) {
        this.uniqueId = uniqueId;
    }
    public TempBillEntityTrasM(BigDecimal uniqueId, BigDecimal weekId, Corporates corporates, Entites entites, BigDecimal trasUpClearedAhead, BigDecimal trasUpScheduledAhead, BigDecimal trasUpEnergyChargesAhead, BigDecimal trasUpComChargesAhead, BigDecimal trasUpClearedReal, BigDecimal trasUpScheduledReal, BigDecimal trasUpEnergyChargesReal, BigDecimal trasUpComChargesReal, BigDecimal trasUpTotalCharges, BigDecimal trasDownScheduledAhead, BigDecimal trasDownEnergyChargesAhead, BigDecimal trasDownScheduledReal, BigDecimal trasDownEnergyChargesReal, BigDecimal netTras, BigDecimal billYear, BigDecimal revisionNo, String remarks, BigDecimal wrNetTras, String billStatus, BigDecimal prevWrNetTras) {
       this.uniqueId = uniqueId;
       this.weekId = weekId;
      this.entites = entites;
       this.corporates = corporates;
       this.trasUpClearedAhead = trasUpClearedAhead;
       this.trasUpScheduledAhead = trasUpScheduledAhead;
       this.trasUpEnergyChargesAhead = trasUpEnergyChargesAhead;
       this.trasUpComChargesAhead = trasUpComChargesAhead;
       this.trasUpClearedReal = trasUpClearedReal;
       this.trasUpScheduledReal = trasUpScheduledReal;
       this.trasUpEnergyChargesReal = trasUpEnergyChargesReal;
       this.trasUpComChargesReal = trasUpComChargesReal;
       this.trasUpTotalCharges = trasUpTotalCharges;
       this.trasDownScheduledAhead = trasDownScheduledAhead;
       this.trasDownEnergyChargesAhead = trasDownEnergyChargesAhead;
       this.trasDownScheduledReal = trasDownScheduledReal;
       this.trasDownEnergyChargesReal = trasDownEnergyChargesReal;
       this.netTras = netTras;
       this.billYear = billYear;
       this.revisionNo = revisionNo;
       this.remarks = remarks;
       this.wrNetTras = wrNetTras;
       this.billStatus = billStatus;
       this.prevWrNetTras = prevWrNetTras;
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
  
    public BigDecimal getTrasUpClearedAhead() {
        return this.trasUpClearedAhead;
    }
    
    public void setTrasUpClearedAhead(BigDecimal trasUpClearedAhead) {
        this.trasUpClearedAhead = trasUpClearedAhead;
    }
    public BigDecimal getTrasUpScheduledAhead() {
        return this.trasUpScheduledAhead;
    }
    
    public void setTrasUpScheduledAhead(BigDecimal trasUpScheduledAhead) {
        this.trasUpScheduledAhead = trasUpScheduledAhead;
    }
    public BigDecimal getTrasUpEnergyChargesAhead() {
        return this.trasUpEnergyChargesAhead;
    }
    
    public void setTrasUpEnergyChargesAhead(BigDecimal trasUpEnergyChargesAhead) {
        this.trasUpEnergyChargesAhead = trasUpEnergyChargesAhead;
    }
    public BigDecimal getTrasUpComChargesAhead() {
        return this.trasUpComChargesAhead;
    }
    
    public void setTrasUpComChargesAhead(BigDecimal trasUpComChargesAhead) {
        this.trasUpComChargesAhead = trasUpComChargesAhead;
    }
    public BigDecimal getTrasUpClearedReal() {
        return this.trasUpClearedReal;
    }
    
    public void setTrasUpClearedReal(BigDecimal trasUpClearedReal) {
        this.trasUpClearedReal = trasUpClearedReal;
    }
    public BigDecimal getTrasUpScheduledReal() {
        return this.trasUpScheduledReal;
    }
    
    public void setTrasUpScheduledReal(BigDecimal trasUpScheduledReal) {
        this.trasUpScheduledReal = trasUpScheduledReal;
    }
    public BigDecimal getTrasUpEnergyChargesReal() {
        return this.trasUpEnergyChargesReal;
    }
    
    public void setTrasUpEnergyChargesReal(BigDecimal trasUpEnergyChargesReal) {
        this.trasUpEnergyChargesReal = trasUpEnergyChargesReal;
    }
    public BigDecimal getTrasUpComChargesReal() {
        return this.trasUpComChargesReal;
    }
    
    public void setTrasUpComChargesReal(BigDecimal trasUpComChargesReal) {
        this.trasUpComChargesReal = trasUpComChargesReal;
    }
    public BigDecimal getTrasUpTotalCharges() {
        return this.trasUpTotalCharges;
    }
    
    public void setTrasUpTotalCharges(BigDecimal trasUpTotalCharges) {
        this.trasUpTotalCharges = trasUpTotalCharges;
    }
    public BigDecimal getTrasDownScheduledAhead() {
        return this.trasDownScheduledAhead;
    }
    
    public void setTrasDownScheduledAhead(BigDecimal trasDownScheduledAhead) {
        this.trasDownScheduledAhead = trasDownScheduledAhead;
    }
    public BigDecimal getTrasDownEnergyChargesAhead() {
        return this.trasDownEnergyChargesAhead;
    }
    
    public void setTrasDownEnergyChargesAhead(BigDecimal trasDownEnergyChargesAhead) {
        this.trasDownEnergyChargesAhead = trasDownEnergyChargesAhead;
    }
    public BigDecimal getTrasDownScheduledReal() {
        return this.trasDownScheduledReal;
    }
    
    public void setTrasDownScheduledReal(BigDecimal trasDownScheduledReal) {
        this.trasDownScheduledReal = trasDownScheduledReal;
    }
    public BigDecimal getTrasDownEnergyChargesReal() {
        return this.trasDownEnergyChargesReal;
    }
    
    public void setTrasDownEnergyChargesReal(BigDecimal trasDownEnergyChargesReal) {
        this.trasDownEnergyChargesReal = trasDownEnergyChargesReal;
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
    public BigDecimal getWrNetTras() {
        return this.wrNetTras;
    }
    
    public void setWrNetTras(BigDecimal wrNetTras) {
        this.wrNetTras = wrNetTras;
    }
    public String getBillStatus() {
        return this.billStatus;
    }
    
    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }
    public BigDecimal getPrevWrNetTras() {
        return this.prevWrNetTras;
    }
    
    public void setPrevWrNetTras(BigDecimal prevWrNetTras) {
        this.prevWrNetTras = prevWrNetTras;
    }




}


