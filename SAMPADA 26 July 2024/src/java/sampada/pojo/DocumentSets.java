package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * DocumentSets generated by hbm2java
 */
public class DocumentSets  implements java.io.Serializable {


     private String documentSetNo;
     private String description;
     private String status;
     private Date uploadDateByMaker;
     private Date checkDateByChecker;
     private BigDecimal no_of_docs;

    public DocumentSets() {
    }

	
    public DocumentSets(String documentSetNo) {
        this.documentSetNo = documentSetNo;
    }
    public DocumentSets(String documentSetNo, String description, String status, Date uploadDateByMaker, Date checkDateByChecker, BigDecimal no_of_docs) {
       this.documentSetNo = documentSetNo;
       this.description = description;
       this.status = status;
       this.uploadDateByMaker = uploadDateByMaker;
       this.checkDateByChecker = checkDateByChecker;
       this.no_of_docs = no_of_docs;
    }
   
    public String getDocumentSetNo() {
        return this.documentSetNo;
    }
    
    public void setDocumentSetNo(String documentSetNo) {
        this.documentSetNo = documentSetNo;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getUploadDateByMaker() {
        return this.uploadDateByMaker;
    }
    
    public void setUploadDateByMaker(Date uploadDateByMaker) {
        this.uploadDateByMaker = uploadDateByMaker;
    }
    public Date getCheckDateByChecker() {
        return this.checkDateByChecker;
    }
    
    public void setCheckDateByChecker(Date checkDateByChecker) {
        this.checkDateByChecker = checkDateByChecker;
    }
    public BigDecimal getNo_of_docs() {
        return this.no_of_docs;
    }
    
    public void setNo_of_docs(BigDecimal no_of_docs) {
        this.no_of_docs = no_of_docs;
    }




}


