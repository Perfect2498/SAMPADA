/*

* To change this license header, choose License Headers in Project Properties.

* To change this template file, choose Tools | Templates

* and open the template in the editor.

*/

package sampada.util;


import java.math.BigDecimal;

import java.util.Date;

import java.util.Set;

import sampada.pojo.Corporates;


/**

*

* @author JaganMohan

*/

public class PayDisbursementMapping {

   private int corpoarateID;
   private String corporateName;
   private String billtype;
   private BigDecimal netAmount;
  
    public PayDisbursementMapping() {
   }

                 
   public PayDisbursementMapping(int corpoarateID, String corporateName, String billtype, BigDecimal netAmount) {
      this.corpoarateID = corpoarateID;
      this.corporateName = corporateName;
      this.billtype = billtype;
      this.netAmount = netAmount;     
   }
 

   public int getCorpoarateID() {
       return corpoarateID;
   }

   public void setCorpoarateID(int corpoarateID) {
       this.corpoarateID = corpoarateID;
   }

   public String getCorporateName() {
       return corporateName;
   }

   public void setCorporateName(String corporateName) {
       this.corporateName = corporateName;
   }

   public String getBilltype() {
       return billtype;
   }

   public void setBilltype(String billtype) {
       this.billtype = billtype;
   }

   public BigDecimal getNetAmount() {
       return netAmount;
   }

   public void setNetAmount(BigDecimal netAmount) {
       this.netAmount = netAmount;
   }

}