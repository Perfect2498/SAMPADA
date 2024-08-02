package sampada.pojo;
// Generated Jan 4, 2021 2:25:29 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * UploadLcDocuments generated by hbm2java
 */
public class UploadLcDocuments  implements java.io.Serializable {


     private BigDecimal slno;
     private LetterOfCredit letterOfCredit;
     private String fileCatergory;
     private String userFilename;
     private String savedFilename;
     private String fileType;
     private Date uploadDate;
     private String constituent;

    public UploadLcDocuments() {
    }

	
    public UploadLcDocuments(BigDecimal slno) {
        this.slno = slno;
    }
    public UploadLcDocuments(BigDecimal slno, LetterOfCredit letterOfCredit, String fileCatergory, String userFilename, String savedFilename, String fileType, Date uploadDate, String constituent) {
       this.slno = slno;
       this.letterOfCredit = letterOfCredit;
       this.fileCatergory = fileCatergory;
       this.userFilename = userFilename;
       this.savedFilename = savedFilename;
       this.fileType = fileType;
       this.uploadDate = uploadDate;
       this.constituent = constituent;
    }
   
    public BigDecimal getSlno() {
        return this.slno;
    }
    
    public void setSlno(BigDecimal slno) {
        this.slno = slno;
    }
    public LetterOfCredit getLetterOfCredit() {
        return this.letterOfCredit;
    }
    
    public void setLetterOfCredit(LetterOfCredit letterOfCredit) {
        this.letterOfCredit = letterOfCredit;
    }
    public String getFileCatergory() {
        return this.fileCatergory;
    }
    
    public void setFileCatergory(String fileCatergory) {
        this.fileCatergory = fileCatergory;
    }
    public String getUserFilename() {
        return this.userFilename;
    }
    
    public void setUserFilename(String userFilename) {
        this.userFilename = userFilename;
    }
    public String getSavedFilename() {
        return this.savedFilename;
    }
    
    public void setSavedFilename(String savedFilename) {
        this.savedFilename = savedFilename;
    }
    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public Date getUploadDate() {
        return this.uploadDate;
    }
    
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
    public String getConstituent() {
        return this.constituent;
    }
    
    public void setConstituent(String constituent) {
        this.constituent = constituent;
    }




}


