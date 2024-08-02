/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.AdjPaymentDAO;
import sampada.DAO.BankStatementDAO;
import sampada.DAO.BillInterestRateDAO;
import sampada.DAO.BillPayableCorpDAO;
import sampada.DAO.BillPriorityDAO;
import sampada.DAO.BillReceiveCorpDAO;
import sampada.DAO.ConstantsMasterDAO;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.CsdfDetailsDAO;
import sampada.DAO.DisbursedInterestDetailsDAO;
import sampada.DAO.DsnFileDetailsDAO;
import sampada.DAO.InterestPoolAccountDetailsDAO;
import sampada.DAO.MappingInterestBankDAO;
import sampada.DAO.PaymentDisbursementDAO;
import sampada.DAO.PaymentInterestDisbursementDAO;
import sampada.DAO.PoolAccountDetailsDAO;
import sampada.DAO.PoolToIntDAO;
import sampada.DAO.PoolToPoolDAO;
import sampada.DAO.TempDisbInterestDetailsDAO;
import sampada.DAO.TempInterestDetailsDAO;
import sampada.DAO.TempMappingBillBankDAO;
import sampada.DAO.TempPaymentDisbursementDAO;
import sampada.DAO.TempRefundBillCorpDAO;
import sampada.DAO.miscDisbursementDAO;
import sampada.pojo.AdjPayment;
import sampada.pojo.AgcInterestPool;
import sampada.pojo.AgcPoolAccountDetails;
import sampada.pojo.BankStatement;
import sampada.pojo.BillInterestRate;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPriority;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.Corporates;
import sampada.pojo.CsdfDetails;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.DocumentSets;
import sampada.pojo.Documents;
import sampada.pojo.DsnFileDetails;
import sampada.pojo.InterestPoolAccountDetails;
import sampada.pojo.MappingInterestBank;
import sampada.pojo.MiscDisbursement;
import sampada.pojo.PaymentDisbursement;
import sampada.pojo.PaymentInterestDisbursement;
import sampada.pojo.PoolAccountDetails;
import sampada.pojo.PoolToInt;
import sampada.pojo.PoolToPool;
import sampada.pojo.RrasInterestPool;
import sampada.pojo.RrasPoolAccountDetails;
import sampada.pojo.TempDisbInterestDetails;
import sampada.pojo.TempInterestDetails;
import sampada.pojo.TempMappingBillBank;
import sampada.pojo.TempPaymentDisbursement;
import sampada.pojo.TempRefundBillCorp;
import sampada.util.PayDisbursementMapping;

/**
 *
 *
 *
 * @author JaganMohan
 *
 */
public class PaymentDisbursementController extends MultiActionController {

    public ModelAndView welcomeMessage(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("welcomeMessage");
        return mv;
    }

    public ModelAndView paymentDisbursementIndexRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("paymentDisbursementIndexRLDC");
        return mv;
    }

    public ModelAndView miscIndexRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("miscIndexRLDC");
        return mv;
    }

    public ModelAndView miscIndexFin(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("miscIndexFin");
        return mv;
    }

    public ModelAndView miscIndexMaker(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("miscIndexMaker");
        return mv;
    }

    public ModelAndView viewDocumentSets(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("fileDownload");

        String dirpath = "/DOCUMENT_SETS";
        ServletContext context = request.getServletContext();

        ConstantsMasterDAO consdao = new ConstantsMasterDAO();
        String interestfilepath = consdao.getFilePathbyName("INTEREST_EXCELFILE");

        String appPath = context.getRealPath("");
//        String fullPath = appPath + dirpath;
        String fullPath = interestfilepath;

        File dir = new File(fullPath);
        String files[] = dir.list();

        CsdfDetailsDAO dao = new CsdfDetailsDAO();
        List<DocumentSets> dset_details = dao.getDocumentSetsDetails();

        mv.addObject("dset_details", dset_details);
        mv.addObject("filenames", files);

        return mv;
    }

    public ModelAndView viewDocuments(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewDocuments");

        String documentset = request.getParameter("documentset");

        CsdfDetailsDAO dao = new CsdfDetailsDAO();
        List<Documents> list = dao.getDocumentDetails(documentset + "_");

        mv.addObject("doc_details", list);
        mv.addObject("dset_desc", dao.getDocumentSetDes(documentset));
        mv.addObject("curr_set", documentset);

        return mv;
    }

    public ModelAndView fileDownload(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        final int BUFFER_SIZE = 5120;
        String filename = request.getParameter("document");
        ServletContext context = request.getServletContext();

        String fullPath = filename;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.flush();
        outStream.close();

        ModelAndView mv = new ModelAndView("viewDocuments");

        return mv;
    }

//    public ModelAndView fileDownload(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        HttpSession session1 = request.getSession(false);
    public ModelAndView documentMaker(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("documentMaker");

        CsdfDetailsDAO dao = new CsdfDetailsDAO();
        List<DocumentSets> list = dao.getDocumentSetsforMaker();
        List<Documents> list2 = dao.getDocumentsforMaker();
        List<MiscDisbursement> list3 = dao.getMiscDisburseDetailsForMaker();
        List<MiscDisbursement> bankpays = new ArrayList();
        List<MiscDisbursement> directpays = new ArrayList();
        List<DsnFileDetails> unmappedlist = dao.getunmappedbankForMaker();
        PoolToIntDAO pooltointdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointdao.getPendingPoolToInt();

        for (MiscDisbursement r : list3) {
            if (r.getStmtId().compareTo(BigDecimal.ZERO) == -1) {
                directpays.add(r);
            } else {
                bankpays.add(r);
            }
        }

        mv.addObject("dset_details", list);
        mv.addObject("doc_details", list2);
        mv.addObject("bankpays", bankpays);
        mv.addObject("directpays", directpays);
        mv.addObject("unmappedlist", unmappedlist);
        mv.addObject("PoolToInt", PoolToInt);

        return mv;
    }

    public ModelAndView checkerVerifiesPayment(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("txuid");
        String stmtid = request.getParameter("stmtid");

        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
        List<MiscDisbursement> miscdislist = miscdao.getMiscDisbursementbyUniqueID(new BigDecimal(txuid));

        String category = miscdislist.get(0).getAmtCategory();
        if (category.equalsIgnoreCase("Bills")) {
            BigDecimal mainpool = miscdislist.get(0).getMainPoolBalance();
            BigDecimal csdfamt = miscdislist.get(0).getRefundAmt();
            csdfamt = mainpool.subtract(csdfamt);
            pooldao.getUpdatePoolAccountbyChecker(csdfamt);
        }
        if (category.equalsIgnoreCase("Interest")) {
            BigDecimal mainpool = miscdislist.get(0).getMainPoolBalance();
            BigDecimal csdfamt = miscdislist.get(0).getRefundAmt();
            csdfamt = mainpool.subtract(csdfamt);
            intepooldao.getUpdateInterestPoolAccountbyChecker(csdfamt);
        }
        miscdao.checkmiscdisburseBychecker(txuid);

        return documentChecker(request, response);
    }

    public ModelAndView checkerVerifiesbank(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("txuid");
        String stmtid = request.getParameter("stmtid");
        System.out.println("txuid is" + txuid);
        System.out.println("stmtid is" + stmtid);
        DsnFileDetailsDAO dsndetilsdao = new DsnFileDetailsDAO();

        dsndetilsdao.getUpdatedsndetailsbyChecker(txuid);
        return documentMaker(request, response);
    }

    public ModelAndView makerDeletesbank(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("txuid");
        String stmtid = request.getParameter("stmtid");
        System.out.println("txuid is" + txuid);
        System.out.println("stmtid is" + stmtid);
        BankStatementDAO bankdao = new BankStatementDAO();
        DsnFileDetailsDAO dsndetilsdao = new DsnFileDetailsDAO();
        List<DsnFileDetails> dsnfiledet = dsndetilsdao.DsnFileDetailsbyslno(new BigDecimal(txuid));

        bankdao.updateBankStmtmappedbalancebyid(new BigDecimal(stmtid), dsnfiledet.get(0).getMappedBalance());

        dsndetilsdao.deletedsndetailsByslno(txuid);

        return documentMaker(request, response);
    }

    public ModelAndView makerDeletesPayment(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("txuid");
        String stmtid = request.getParameter("stmtid");

        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        miscdao.deletemiscdisburseByMaker(txuid);

        return documentMaker(request, response);
    }

    public ModelAndView makerDeletesDocument(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String document = request.getParameter("document");
        CsdfDetailsDAO dao = new CsdfDetailsDAO();

        String filename = dao.getFilenameofDocument(document);
        String docset = dao.getDSetofDocument(document);
        File myObj = new File(filename);
        myObj.delete();

        dao.deleteDocsByMaker(document, docset);

        return documentMaker(request, response);
    }

    public ModelAndView makerDeletesSet(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String DOCUMENT_SET = request.getParameter("documentset");

        ConstantsMasterDAO consdao = new ConstantsMasterDAO();
        String interestfilepath = consdao.getFilePathbyName("INTEREST_EXCELFILE");

        File dir = new File(interestfilepath + "/" + DOCUMENT_SET);
        Path file_location = Paths.get(interestfilepath + "/" + DOCUMENT_SET);
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                children[i].delete();
            }
        }

        Files.deleteIfExists(file_location);

        CsdfDetailsDAO dao = new CsdfDetailsDAO();

        dao.deleteSetsByMaker(DOCUMENT_SET);

        return documentMaker(request, response);
    }

    public ModelAndView makerDeletesTransfer(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("f6uid");

        PoolToIntDAO PoolToIntDAO = new PoolToIntDAO();
        PoolToIntDAO.deleteTransfer(txuid);

        return documentMaker(request, response);
    }

    public ModelAndView makerDeletesTransfer1(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("f6uid1");

        PoolToPoolDAO PoolToIntDAO = new PoolToPoolDAO();
        PoolToIntDAO.deleteTransfer(txuid);

        return documentMaker(request, response);
    }

    public ModelAndView documentChecker(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("documentChecker");

        CsdfDetailsDAO dao = new CsdfDetailsDAO();
        List<DocumentSets> list = dao.getDocumentSetsforChecker();
        List<Documents> list2 = dao.getDocumentsforChecker();
        //List<DocumentSets> list3 = dao.getDocumentSetsforMaker();
        //List<Documents> list4 = dao.getDocumentsforMaker();
        //List<MiscDisbursement> list5 = dao.getMiscDisburseDetailsForMaker();
        List<MiscDisbursement> list6 = dao.getMiscDisburseDetailsForChecker();

//        if(!(list3.isEmpty()))
//            mv.addObject("msg1","Some Document Sets pending to be uploaded by Maker.");
//        
//        if(!(list4.isEmpty()))
//            mv.addObject("msg2","Some Documents pending to be uploaded by Maker.");
//        
//        if(!(list5.isEmpty()))
//            mv.addObject("msg3","Some transactions pending at Maker end.");
        List<MiscDisbursement> bankpays = new ArrayList();
        List<MiscDisbursement> directpays = new ArrayList();

        for (MiscDisbursement r : list6) {
            if (r.getStmtId().compareTo(BigDecimal.ZERO) == -1) {
                directpays.add(r);
            } else {
                bankpays.add(r);
            }
        }
        List<DsnFileDetails> unmappedlist = dao.getunmappedbankForMaker();
        PoolToIntDAO pooltointdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointdao.getPendingPoolToInt();

        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();

        mv.addObject("dset_details", list);
        mv.addObject("doc_details", list2);
        mv.addObject("bankpays", bankpays);
        mv.addObject("directpays", directpays);
        mv.addObject("unmappedlist", unmappedlist);
        mv.addObject("PoolToInt", PoolToInt);
        mv.addObject("PoolToPool", PoolToPool);

        return mv;
    }

    public ModelAndView checkerVerifiesDocument(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        CsdfDetailsDAO dao = new CsdfDetailsDAO();

        dao.checkDocsByChecker(request.getParameter("document"));

        return documentChecker(request, response);
    }

    public ModelAndView checkerVerifiesSet(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        CsdfDetailsDAO dao = new CsdfDetailsDAO();

        dao.checkSetsByChecker(request.getParameter("documentset"));

        return documentChecker(request, response);
    }

    public ModelAndView fileUpload(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("fileUpload");

        String dirpath = "/DOCUMENT_SETS";

        ConstantsMasterDAO consdao = new ConstantsMasterDAO();
        String interestfilepath = consdao.getFilePathbyName("INTEREST_EXCELFILE");
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
//        String fullPath = appPath + dirpath;
        String fullPath = interestfilepath;

        File dir = new File(fullPath);
        String files[] = dir.list();
//        System.out.println("files ="+files[0]);
        mv.addObject("dsets", files);

        return mv;
    }

    public ModelAndView storeFile(HttpServletRequest request,
            HttpServletResponse response) throws IOException, Exception {

        HttpSession session = request.getSession();
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = null;

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile multipartFile = null; // multipart file class depends on which class you use assuming you are using org.springframework.web.multipart.commons.CommonsMultipartFile       

        Iterator<String> iterator = multipartRequest.getFileNames();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            // create multipartFile array if you upload multiple files
            multipartFile = (CommonsMultipartFile) multipartRequest.getFile(key);

        }

        session.setAttribute("doc_desc", request.getParameter("doc_desc"));
        session.setAttribute("dset_desc", request.getParameter("dset_desc"));
        session.setAttribute("DSET_No", request.getParameter("d_set_no"));
        session.setAttribute("NEW_DSET", request.getParameter("new_d_set"));
        return storeFilee(multipartFile, session);
    }

    public ModelAndView storeFilee(CommonsMultipartFile file,
            HttpSession session) throws Exception {

        ModelAndView mv = null;
        String DOCUMENT_SET;
        short flag = 0;

        if (session.getAttribute("DSET_No").toString().equals("null")) {
            DOCUMENT_SET = session.getAttribute("NEW_DSET").toString();
        } else {
            DOCUMENT_SET = session.getAttribute("DSET_No").toString();
        }

        ConstantsMasterDAO consdao = new ConstantsMasterDAO();
        String interestfilepath = consdao.getFilePathbyName("INTEREST_EXCELFILE");

        File folders = new File(interestfilepath);
        String directories[] = folders.list();

        //Checks the file extension
        String fname = file.getOriginalFilename();
        int index = fname.lastIndexOf('.');
        System.out.println("Index is " + index);
        String fileextension = fname.substring(index);
        System.out.println("File extension is " + fileextension);

        if (!fileextension.equalsIgnoreCase(".pdf") && !fileextension.equalsIgnoreCase(".xls") && !fileextension.equalsIgnoreCase(".xlsx")) {
            mv = new ModelAndView("fileUpload");
            mv.addObject("dsets", directories);
            mv.addObject("warning", "Please upload files in Excel or PDF formats only !!");

            return mv;
        }

        //Checks the file size
        long size = file.getSize() / 1024;
        System.out.println("File size is : " + size);
        if (size > 5120.00) {
            mv = new ModelAndView("fileUpload");
            mv.addObject("dsets", directories);
            mv.addObject("warning", "File size cannot be > 5MB !!");

            return mv;
        }

        //If document-set does not exist
        if (directories.length == 0) {
            Path file_location = Paths.get(interestfilepath + "/" + DOCUMENT_SET);
            Files.createDirectory(file_location);
            System.out.println("Created new document-set");
            flag = 1;
        }

        if (directories.length > 0) {
            int found = 0;
            for (String directorie : directories) {
                if (directorie.equals(DOCUMENT_SET)) {
                    found = 1;
                }
            }
            if (found == 0) {
                Path file_location = Paths.get(interestfilepath + "/" + DOCUMENT_SET);
                Files.createDirectory(file_location);
                System.out.println("Created new document-set");
                flag = 1;
            }
        }

        //If uploaded file already exists
        File f = new File(interestfilepath + "/" + DOCUMENT_SET);
        String contents[] = f.list();
        for (String str : contents) {
            if (str.equals(file.getOriginalFilename())) {
                mv = new ModelAndView("fileUpload");
                mv.addObject("dsets", directories);
                mv.addObject("warning", "File already exists. Please rename file !!");

                return mv;
            }
        }

        final String UPLOAD_DIRECTORY = interestfilepath + File.separator + DOCUMENT_SET;

        try {
            ServletContext context = session.getServletContext();
//            String path = context.getRealPath(UPLOAD_DIRECTORY);
            String path = UPLOAD_DIRECTORY;

            String filename = file.getOriginalFilename();

            System.out.println(path + " " + filename);

            byte[] bytes = file.getBytes();

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(path + File.separator + filename)));
            System.out.println(path + File.separator + filename);

            stream.write(bytes);

            System.out.println(path + File.separator + filename);

            stream.flush();
            stream.close();

            //Storing data in database
            CsdfDetailsDAO dao = new CsdfDetailsDAO();
            BigDecimal no_of_docs = dao.getNoDocs(DOCUMENT_SET);
            no_of_docs = no_of_docs.add(BigDecimal.ONE);
            dao.putDocumentDetails(flag, DOCUMENT_SET, DOCUMENT_SET + "_" + String.valueOf((short) java.lang.Math.abs((short) file.getOriginalFilename().hashCode())), session.getAttribute("dset_desc").toString(), session.getAttribute("doc_desc").toString(), path + "\\" + filename, String.valueOf(size), fileextension, no_of_docs);
            mv = new ModelAndView("uploadSuccess");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }

    public ModelAndView unmappedpayment(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("unmappedpayment");

        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        List<PoolAccountDetails> poolacc = pooldao.getPoolAccountDetails();
        mv.addObject("Poolbal", "Pool account Main Balance is : " + poolacc.get(0).getMainBalance());

        BankStatementDAO bankdao = new BankStatementDAO();
        CsdfDetailsDAO dao = new CsdfDetailsDAO();

        List<DocumentSets> dsets = dao.getCheckedDocumentSetsDetails();
        List<String> folders = new ArrayList();

        for (DocumentSets dts : dsets) {
            folders.add(dts.getDocumentSetNo());
        }

        List<BankStatement> list = bankdao.getUnmappedPayments();
        List<Documents> list2 = dao.getCheckedDocumentNames();
        List<String> list3 = new ArrayList();
        int index;

        for (Documents obj : list2) {
            index = obj.getFilename().lastIndexOf('\\');
            list3.add(obj.getFilename().substring(index + 1));
        }

        mv.addObject("filenames", list3);
        mv.addObject("data", list);
        mv.addObject("folders", folders);

        return mv;
    }

    public ModelAndView removeunmapped(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String val = request.getParameter("amtvalue");
        BigDecimal amt = new BigDecimal(val);
        String dset = request.getParameter("Dset");
        int stmtid = Integer.parseInt(request.getParameter("stmtid"));
        int corpid = Integer.parseInt(request.getParameter("corpid"));

        BankStatementDAO bsdao = new BankStatementDAO();
        List<BankStatement> bklist = bsdao.getBankStatementbyStmtID(stmtid);
        Date date = bklist.get(0).getAmountDate();
        String[] date2 = date.toString().split("-");
        String crdate = date2[2] + date2[1] + date2[0];

        System.out.println("crdate is : " + crdate);

        DsnFileDetailsDAO dsnfiledetailsdao = new DsnFileDetailsDAO();
        DsnFileDetails dsnfile = new DsnFileDetails();
        int maxid = 0;
        maxid = dsnfiledetailsdao.getMaxSlno().intValue();
        maxid = maxid + 1;
        dsnfile.setSlno(new BigDecimal(maxid));
        dsnfile.setFileName(dset);
        BankStatement bnk = new BankStatement();
        bnk.setStmtId(new BigDecimal(stmtid));
        dsnfile.setBankStatement(bnk);
        dsnfile.setCheckerStatus("Pending");
        dsnfile.setMappedBalance(bklist.get(0).getMappedBalance());
        dsnfiledetailsdao.NewDsnFileDetails(dsnfile);

        bsdao.updateBankStmtmappedbalancebyid(bklist.get(0).getStmtId(), new BigDecimal(0));

//        miscDisbursementDAO miscdao = new miscDisbursementDAO();
//        CorporatesDAO corpdao = new CorporatesDAO();
//        CsdfDetailsDAO csdfDetaildao = new CsdfDetailsDAO();
//
//        
//        
//        MiscDisbursement csdfde = new MiscDisbursement();
////        int maxid = 0;
////        maxid = miscdao.getMaxUNIQUE_ID().intValue();
////        maxid = maxid + 1;
//        csdfde.setUniqueId(new BigDecimal(maxid));
//        String corparateID = String.valueOf(corpid);
//
//        String corpname = corpdao.geCorpNamebyId(Integer.parseInt(corparateID));
//
//        csdfde.setStatus("bankPending");
//        csdfde.setRefundAmt(bklist.get(0).getMappedBalance());
//        csdfde.setEntryDate(new Date());
//        csdfde.setStmtId(bklist.get(0).getStmtId());
//        csdfde.setAmtCategory("Bills");
//        csdfde.setCorpId(new BigDecimal(corparateID));
//        csdfde.setCorpName(corpname);
//        csdfde.setDocumentSet(dset);
//        csdfde.setMakerDate(new Date());
//
//        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
//        csdfde.setEntryTime(currentTimestamp);
//
//        miscdao.NewMiscDisbursement(csdfde);
//
//        bsdao.updateBankStmtmappedbalancebyid(bklist.get(0).getStmtId(), new BigDecimal(0));
        return unmappedpayment(request, response);
    }

    public ModelAndView paymentDisbursementIndexMaker(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("paymentDisbursementIndexMaker");
        return mv;
    }

    public ModelAndView paymentDisbursementIndexFinance(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("paymentDisbursementIndexFinance");
        return mv;
    }

//    public ModelAndView disbursementPendingList(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        HttpSession session1 = request.getSession(false);
//        if (session1 == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/logout.htm");
//            return new ModelAndView(redirectView);
//        }
//        ModelAndView mv = new ModelAndView("disbursementPendingList");
//        BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
//        List<BillReceiveCorp> list = null;
//        list = billrecvdao.getPendingDisbursementbyCorp();
//        mv.addObject("notpaidList", list);
//        return mv;
//    }
    public ModelAndView newWeeklyDisbursableforCorpbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bname = request.getParameter("bname");
        if (bname != null) {
            ModelAndView mv1 = new ModelAndView("viewWeeklyDisbursableforCorpbyRLDC");
            String weeklynumber = request.getParameter("weeklynumber");
            String fromdate = request.getParameter("fromdate");
            String todate = request.getParameter("todate");
            String billtype = request.getParameter("billtype");
            BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
            List<BillReceiveCorp> list = null;
            list = billrecvdao.getDisbursementDetailsbyweekly(new BigDecimal(weeklynumber), billtype);
            mv1.addObject("weeklyList", list);
            mv1.addObject("weekno", weeklynumber);
            mv1.addObject("fromdate", fromdate);
            mv1.addObject("todate", todate);
            mv1.addObject("billtype", billtype);
            return mv1;
        }
        ModelAndView mv = new ModelAndView("newWeeklyDisbursableforCorpbyRLDC");
        return mv;
    }

    public ModelAndView newMonthlyDisbursableforCorpbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("newMonthlyDisbursableforCorpbyRLDC");
        return mv;
    }

    public ModelAndView viewPaymentDisbursementbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        PaymentDisbursementDAO disdao = new PaymentDisbursementDAO();
        ModelAndView mv = new ModelAndView("viewPaymentDisbursementbyRLDC");
        List<PaymentDisbursement> list = null;
        list = disdao.getDisbursementDetailsbyStatus("Confirm");
        mv.addObject("confirmList", list);
        return mv;
    }

    public ModelAndView viewCheckerDisbursement(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bconfirm = request.getParameter("bconfirm");
        String bcancel = request.getParameter("bcancel");
        System.out.println("bconfirm is " + bconfirm);
        System.out.println("bdelete is " + bcancel);
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list = null;
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefundDisbusrse = null;
        listrefundDisbusrse = temprefudndao.getAllPendingReceviableTempRefundBillCorp();
        if (bconfirm != null) {
            System.out.println("bconfirm is " + bconfirm);
            int maxid = 0;
            BigDecimal totaldisburse = new BigDecimal("0");
            BigDecimal totaldisburseagc = new BigDecimal("0");
            BigDecimal totaldisburserras = new BigDecimal("0");

            PaymentDisbursement paydis = new PaymentDisbursement();
            PaymentDisbursementDAO paydisdao = new PaymentDisbursementDAO();
            BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
            BillReceiveCorp billcorp = new BillReceiveCorp();
            BillReceiveCorpDAO billreceoprdao = new BillReceiveCorpDAO();
            String ststus;
            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
            List<PoolAccountDetails> list1;
            List<AgcPoolAccountDetails> list2;
            List<RrasPoolAccountDetails> list3;

            list = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
            TempDisbInterestDetails tempdisine = new TempDisbInterestDetails();
            TempDisbInterestDetailsDAO tempdusdao = new TempDisbInterestDetailsDAO();
            BillInterestRateDAO billindao = new BillInterestRateDAO();
            List<BillInterestRate> listinte = null;

            BillPayableCorpDAO billpaydao = new BillPayableCorpDAO();

            for (TempPaymentDisbursement e : list) {
                maxid = paydisdao.getMaxUDISBURSE_ID();
                maxid = maxid + 1;
                paydis.setDisburseId(new BigDecimal(maxid));
                paydis.setBillingDate(e.getBillingDate());
                paydis.setAdjustedAmount(e.getAdjustedAmount());
                paydis.setCheckerStatus("Confirm");
                paydis.setCorporates(e.getCorporates());
                paydis.setDisbursalDate(e.getDisbursalDate());
                paydis.setDisburseAmount(e.getDisburseAmount());
                paydis.setDisburseCategory(e.getDisburseCategory());
                paydis.setDisburseStatus(e.getDisburseStatus());
                paydis.setDisburseYear(e.getDisburseYear());
                paydis.setEntryDate(new Date());
                paydis.setRemarks(e.getRemarks());
                paydis.setTotalAmount(e.getTotalAmount());
                paydis.setWeekId(e.getWeekId());
                paydis.setPendingAmount(e.getPendingAmount());
                paydis.setBillType(e.getBillType());
                paydis.setPoolBal(e.getPoolBal());
                paydis.setPoolAgcBal(e.getPoolAgcBal());
                paydis.setPoolRrasBal(e.getPoolRrasBal());
                paydis.setBillDueDate(e.getBillDueDate());

                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                paydis.setEntryTime(e.getEntryTime());

                BigDecimal bg = null;
                bg = e.getPendingAmount().subtract(e.getDisburseAmount());

                if (e.getBillType().equalsIgnoreCase("SRAS")) {
                    totaldisburseagc = totaldisburseagc.add(e.getDisburseAmount());
                    System.out.print("totaldisburse agc  is :" + totaldisburseagc);
                } else if (e.getBillType().equalsIgnoreCase("TRASM")
                        || e.getBillType().equalsIgnoreCase("TRASS")
                        || e.getBillType().equalsIgnoreCase("TRASE")) {
                    totaldisburserras = totaldisburserras.add(e.getDisburseAmount());
                    System.out.print("totaldisburse rras is :" + totaldisburserras);
                } else {
                    totaldisburse = totaldisburse.add(e.getDisburseAmount());
                    System.out.print("totaldisburse is :" + totaldisburse);

                }
                billcorp.setUniqueId(e.getBillReceiveCorp().getUniqueId());
                paydis.setBillReceiveCorp(billcorp);
                paydisdao.NewPaymentDisbursement(paydis);
                if (e.getBillReceiveCorp().getRevisionNo().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal totaldicur = billreceoprdao.getTotalDisburseAmountbyCorpandWeek(e.getBillReceiveCorp().getCorporates().getCorporateId(), e.getWeekId().intValue(), e.getBillType(), e.getBillReceiveCorp().getUniqueId());
//                    int totaldicursement = totaldicur.intValueExact();
//                    System.out.print("if totaldicursement is :" + totaldicursement);
//                    int bg1234 = totaldicursement + e.getDisburseAmount().intValue();
                    BigDecimal bg1234 = totaldicur.add(e.getDisburseAmount());
                    bg = (e.getBillReceiveCorp().getRevisedpaybale()).subtract(bg1234);
                    System.out.print("if inside bg1234 is :" + bg1234);
                    billrecvdao.getUpdateBillReceiveCorpbyChecker1(e.getBillReceiveCorp().getUniqueId(), bg1234, bg, e.getDisburseStatus());
                    // billrecvdao.getUpdateBillReceiveCorpbyChecker1(e.getBillReceiveCorp().getUniqueId(), new BigDecimal(bg1234), bg, e.getDisburseStatus());
                } else {
                    //  int totaldicursement = billreceoprdao.getTotalDisburseAmountbyCorpandWeek(e.getBillReceiveCorp().getCorporates().getCorporateId(), e.getWeekId().intValue(), e.getBillType());
                    BigDecimal totaldicur = billreceoprdao.getTotalDisburseAmountbyCorpandWeek(e.getBillReceiveCorp().getCorporates().getCorporateId(), e.getWeekId().intValue(), e.getBillType(), e.getBillReceiveCorp().getUniqueId());
//                    int totaldicursement = totaldicur.intValueExact();
//                    System.out.print("## Else totaldicursement is :" + totaldicursement);
//                    int bg1234 = totaldicursement + e.getDisburseAmount().intValue();
                    BigDecimal bg1234 = totaldicur.add(e.getDisburseAmount());
                    bg = (e.getBillReceiveCorp().getToalnet()).subtract(bg1234);
                    System.out.print("Else inside bg1234 is :" + bg1234);
                    billrecvdao.getUpdateBillReceiveCorpbyChecker(e.getBillReceiveCorp().getUniqueId(), bg1234, bg, e.getDisburseStatus());
                }
                tempdisdao.getUpdateTempDisbursebyChecker(e.getBillReceiveCorp().getUniqueId(), "Confirm");
            }//end of loop

            for (TempRefundBillCorp e1 : listrefundDisbusrse) {
                BigDecimal paidamnt = new BigDecimal(0);
                BigDecimal pendamnt = new BigDecimal(0);

                if (e1.getBillPayableCorp().getBillType().equalsIgnoreCase("SRAS")) {
                    totaldisburseagc = totaldisburseagc.add(e1.getPaidAmount());
                    System.out.print("totaldisburse agc  is :" + totaldisburseagc);
                } else if (e1.getBillPayableCorp().getBillType().equalsIgnoreCase("TRASM")
                        || e1.getBillPayableCorp().getBillType().equalsIgnoreCase("TRASS")
                        || e1.getBillPayableCorp().getBillType().equalsIgnoreCase("TRASE")) {
                    totaldisburserras = totaldisburserras.add(e1.getPaidAmount());
                    System.out.print("totaldisburse rras is :" + totaldisburserras);
                } else {
                    totaldisburse = totaldisburse.add(e1.getPaidAmount());

                }
                if (e1.getBillPayableCorp().getRevisionNo().compareTo(BigDecimal.ZERO) > 0) {
                    paidamnt = e1.getPaidAmount().add(e1.getBillPayableCorp().getAdjustmentAmount());
                    pendamnt = e1.getBillPayableCorp().getRevisedrefund().subtract(paidamnt);
                    billpaydao.getUpdateRefundBillPayableCorpbyChecker(e1.getBillPayableCorp().getUniqueId().intValue(), paidamnt, pendamnt);
                } else {
                    paidamnt = e1.getPaidAmount().add(e1.getBillPayableCorp().getAdjustmentAmount());
                    pendamnt = e1.getBillPayableCorp().getTotalnet().subtract(paidamnt);
                    billpaydao.getUpdateRefundBillPayableCorpbyChecker(e1.getBillPayableCorp().getUniqueId().intValue(), paidamnt, pendamnt);
                }

                temprefudndao.getUpdatePendingTempRefundBillPayableCorp(e1.getBillPayableCorp().getCorporates().getCorporateId(), e1.getBillPayableCorp().getUniqueId().intValue());

            }

            System.out.print("outside totaldisburse is :" + totaldisburse);
            System.out.print("outside totaldisburse agc is :" + totaldisburseagc);
            System.out.print("outside totaldisburse rras is :" + totaldisburserras);

            list1 = pooldao.getPoolAccountDetails();
            list2 = pooldao.getAgcPoolAccountDetails();
            list3 = pooldao.getRrasPoolAccountDetails();

            BigDecimal bg1 = null;
            BigDecimal bg2 = null;
            BigDecimal bg3 = null;

            bg1 = list1.get(0).getMainBalance().subtract(totaldisburse);
            bg2 = list2.get(0).getMainBalance().subtract(totaldisburseagc);
            bg3 = list3.get(0).getMainBalance().subtract(totaldisburserras);

            pooldao.getUpdatePoolAccountbyChecker(bg1);
            pooldao.getUpdateAgcPoolAccountbyChecker(bg2);
            pooldao.getUpdateRrasPoolAccountbyChecker(bg3);

            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully Verified the Payment Disbursement .......");
            return mv1;
        }
        if (bcancel != null) {
            System.out.println("bdelete is " + bcancel);
            tempdisdao.deleteTempDisburse();
            temprefudndao.deleteTempRefundDisburse();
            ModelAndView mv1 = new ModelAndView("viewPendingMakerDisbursement");
            mv1.addObject("Msg", "Successfully deleted the payment disbursement details.....");
            return mv1;
        }
        ModelAndView mv = new ModelAndView("viewCheckerDisbursement");
        list = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");

        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        List<PoolAccountDetails> listpool = null;
        listpool = pooldao.getPoolAccountDetails();
        System.out.print("Main Pool bala" + listpool.get(0).getMainBalance());
        BigDecimal mainpool = listpool.get(0).getMainBalance();
        mv.addObject("pendingList", list);
        mv.addObject("mainpool", mainpool);
        mv.addObject("listrefundDisbusrse", listrefundDisbusrse);
        return mv;
    }

    public ModelAndView underConstruction(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("underConstruction");
        return mv;
    }

//    public ModelAndView viewCheckerReceviableRefundsList(HttpServletRequest request,
//
//            HttpServletResponse response) throws IOException {
//
//        HttpSession session1 = request.getSession(false);
//
//        if (session1 == null) {
//
//            RedirectView redirectView = new RedirectView();
//
//            redirectView.setContextRelative(true);
//
//            redirectView.setUrl("/logout.htm");
//
//            return new ModelAndView(redirectView);
//
//        }
//
//             
//
//        ModelAndView mv = new ModelAndView("viewCheckerReceviableRefundsList");
//
//       TempRefundBillCorpDAO temprefudndao=new TempRefundBillCorpDAO();
//
//        List<TempRefundBillCorp> list=null;
//
//        list=temprefudndao.getAllPendingReceviableTempRefundBillCorp();
//
//        mv.addObject("temprefundList",list); 
//
//        return mv;
//
//   }
//
//           
    public ModelAndView viewCheckerReceviableRefundsList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewCheckerReceviableRefundsList");
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        List<Object[]> list = null;
        List<Corporates> listcorp = new ArrayList<>();
        list = temprefudndao.getAllPendingReceviableTempRefundBillCorpList();
        Corporates corp = null;
        for (Object[] obj : list) {
            corp = new Corporates();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            listcorp.add(corp);
        }
        mv.addObject("temprefundList", listcorp);
        return mv;
    }

    public ModelAndView submitCorporateRefundReceivableDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
        BillPayableCorp billpaycorp = new BillPayableCorp();
        Corporates corp = new Corporates();
        String rowcount = request.getParameter("rowcount");
        System.out.print("rowcount length is :" + rowcount);
        TempRefundBillCorp temprefundbill = new TempRefundBillCorp();
        int maxid = 0;
        String[] chkSms = request.getParameterValues("items");
        System.out.print("chkSms length is :" + chkSms.length);
        for (int j = 0; j < chkSms.length; j++) {
            for (int i = 1; i <= Integer.parseInt(rowcount); i++) {
                String payudid = request.getParameter("uniqueID" + i);
                int cheno = Integer.parseInt(chkSms[j].toString());
                System.out.print("payudid is :" + cheno);
                System.out.print("cheno selcted is :" + cheno);
                if (Integer.parseInt(payudid) == cheno) {
                    maxid = temprefudndao.getMaxUniqueID();
                    maxid = maxid + 1;
                    temprefundbill.setSlno(new BigDecimal(maxid));
                    billpaycorp.setUniqueId(new BigDecimal(payudid));
                    temprefundbill.setBillPayableCorp(billpaycorp);
                    temprefundbill.setCheckerStatus("Pending");
                    String weekID = request.getParameter("weekID" + i);
                    temprefundbill.setWeekid(new BigDecimal(weekID));
                    String corpID = request.getParameter("corpID" + i);
                    corp.setCorporateId(Integer.parseInt(corpID));
                    String balamt = request.getParameter("balamt" + i);
                    temprefundbill.setCorporates(corp);
                    temprefundbill.setRefundDate(new Date());
                    temprefundbill.setTotalAmount(new BigDecimal(balamt));
                    temprefudndao.NewTempRefundBillCorp(temprefundbill);
                }
            }
        }
        ModelAndView mv = new ModelAndView("successMsg");
        mv.addObject("Msg", "Succesfully submitted for verification");
        return mv;
    }

    public ModelAndView viewCorporateRefundReceivableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
        String bname = request.getParameter("bname");
        if (bname != null) {
            String corparateID = request.getParameter("corparateID");
            System.out.print("corparateID is inside refind receveriable" + corparateID);
            List<BillPayableCorp> list1 = null;
            list1 = billpaycorpdao.getRefundReceivableBillbyCorp(Integer.parseInt(corparateID));
            List<PoolAccountDetails> list = null;
            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
            ModelAndView mv1 = new ModelAndView("viewBillRefundReceivableDetails");
            list = pooldao.getPoolAccountDetails();
            mv1.addObject("pooldetails", list);
            mv1.addObject("refundDetails", list1);
            return mv1;
        }
        ModelAndView mv = new ModelAndView("viewCorporateRefundReceivableList");
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> list = null;
        list = temprefudndao.getAllPendingReceviableTempRefundBillCorp();
        List<Object[]> list1 = null;
        List<Corporates> listcorp = new ArrayList<>();
        list1 = billpaycorpdao.getAllRefundReceivableBillCorpObjectlist();
        Corporates corp = null;
        for (Object[] obj : list1) {
            corp = new Corporates();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            listcorp.add(corp);
        }
        mv.addObject("refundList", listcorp);
        mv.addObject("temprefundList", list);
        return mv;
    }

    public ModelAndView viewCheckerReceivableRefundsDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> list = null;
        String bconfirm = request.getParameter("bconfirm");
        if (bconfirm != null) {
            PoolAccountDetailsDAO poolactdao = new PoolAccountDetailsDAO();
            List<PoolAccountDetails> listpool = null;
            listpool = poolactdao.getPoolAccountDetails();
            BigDecimal mainbal = listpool.get(0).getMainBalance();
            BigDecimal totaldisbusebal = new BigDecimal(0);
            String corparateID = request.getParameter("corpid");
            list = temprefudndao.getPendingReceviableTempRefundBillcorpbyCorpid(Integer.parseInt(corparateID));
            for (TempRefundBillCorp e : list) {
                totaldisbusebal = totaldisbusebal.add(e.getTotalAmount());
                billpaycorpdao.getUpdateRefundBillPayableCorpbyChecker(e.getBillPayableCorp().getUniqueId().intValue(), e.getTotalAmount());
                temprefudndao.getUpdatePendingTempRefundBillPayableCorp(Integer.parseInt(corparateID), e.getBillPayableCorp().getUniqueId().intValue());
            }
            mainbal = mainbal.subtract(totaldisbusebal);
            poolactdao.getUpdatePoolAccountbyChecker(mainbal);
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully Verified the Refund details for Disbursement");
            return mv1;
        }
        String bcancel = request.getParameter("bcancel");
        if (bcancel != null) {
            String corparateID = request.getParameter("corpid");
            list = temprefudndao.getPendingReceviableTempRefundBillcorpbyCorpid(Integer.parseInt(corparateID));
            for (TempRefundBillCorp e : list) {
                billpaycorpdao.getUpdateBillPayableCorpbyCheckeronDelete(e.getBillPayableCorp().getUniqueId().intValue());
            }
            temprefudndao.getDeleteCheckerReceivableRefundbyCorpID(Integer.parseInt(corparateID));
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully deleted the Refund details for Disbursement");
            return mv1;
        }
        ModelAndView mv = new ModelAndView("viewCheckerReceivableRefundsDetails");
        String corpID = request.getParameter("corpID");
        list = temprefudndao.getPendingReceviableTempRefundBillcorpbyCorpid(Integer.parseInt(corpID));
        mv.addObject("temprefundList", list);
        mv.addObject("corpid", corpID);
        return mv;
    }

    public ModelAndView mischomepage(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("mischome");
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        DsnFileDetailsDAO dsnfiledetdao = new DsnFileDetailsDAO();
        PoolToIntDAO pooltointdao = new PoolToIntDAO();
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<DsnFileDetails> dsnfiledetlist = null;
        List<MiscDisbursement> misclist = null;
        List<PoolToInt> pooltoint = null;
        List<PoolToPool> pooltopool = null;
        misclist = miscdao.getALLmiscDetails();
        dsnfiledetlist = dsnfiledetdao.getALLdsnfileDetails();
        pooltoint = pooltointdao.getALLPoolToIntDetails();
        pooltopool = pooltopooldao.getALLPoolToIntDetails();
        mv.addObject("misclist", misclist);
        mv.addObject("dsnfiledetlist", dsnfiledetlist);
        mv.addObject("pooltoint", pooltoint);
        mv.addObject("pooltopool", pooltopool);
        return mv;
    }

    public ModelAndView disbursementPendingList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("disbursementPendingList");
        BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
        List<Object[]> list = null;
        list = billrecvdao.getPendingDisbursementbyCorp();
        List<BillReceiveCorp> listbillcorp = new ArrayList<>();
        BillReceiveCorp billrecv = null;
        Corporates corp = null;
        CorporatesDAO corpdao = new CorporatesDAO();
        for (Object[] obj : list) {
            billrecv = new BillReceiveCorp();
            corp = new Corporates();
            billrecv.setBillingDate((Date) obj[0]);
            billrecv.setWeekId((BigDecimal) obj[1]);
            billrecv.setBillType((String) obj[2]);
            BigDecimal bg1 = (BigDecimal) obj[3];
            BigDecimal revNo = (BigDecimal) obj[7];
            billrecv.setRevisionNo(revNo);
            String corpname = corpdao.geCorpNamebyId(bg1.intValue());
            corp.setCorporateId((int) bg1.intValue());
            corp.setCorporateName(corpname);
            billrecv.setCorporates(corp);
            billrecv.setToalnet((BigDecimal) obj[4]);
            billrecv.setDisburseAmount((BigDecimal) obj[5]);
            billrecv.setPendingAmount((BigDecimal) obj[6]);
            billrecv.setBillDueDate((Date) obj[8]);
            listbillcorp.add(billrecv);
        }
        List<BillReceiveCorp> billrecpendlist = billrecvdao.BillReceiveCorppendinglisthql();
        mv.addObject("notpaidList", billrecpendlist);
        return mv;
    }

//    public ModelAndView newDisbursement(HttpServletRequest request,
//            HttpServletResponse response) throws IOException, ParseException {
//        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
//        List<TempPaymentDisbursement> list1234 = null;
//        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
//        if (list1234 != null && list1234.size() > 0) {
//            ModelAndView mv = new ModelAndView("successMsg");
//            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
//            return mv;
//        }
//        ModelAndView mv = new ModelAndView("newDisbursement");
//        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
//        List<CsdfDetails> listpsdf = null;
//        String csdfSubmit = request.getParameter("csdfSubmit");
//        if (csdfSubmit != null) {
//            CsdfDetails csdfde = new CsdfDetails();
//            System.out.print("Inside PSDF button");
//            listpsdf = csdfdao.getCsdfDetails("Bills");
//            System.out.print("Inside PSDF button" + listpsdf.size());
//            if (listpsdf != null && listpsdf.size() > 0) {
//                ModelAndView mv2 = new ModelAndView("successMsg");
//                mv2.addObject("Msg", "Already Record pending with checker...Please clear it");
//                return mv2;
//
//            } else {
//                int maxid = 0;
//                PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
//                maxid = csdfdao.getMaxSlno().intValue();
//                maxid = maxid + 1;
//                csdfde.setSlno(new BigDecimal(maxid));
//                String csdfmonth = request.getParameter("csdfmonth");
//                String csdfamt = request.getParameter("csdfamt");
//                String mainbal = request.getParameter("mainbal");
//                String csdfremarks = request.getParameter("csdfremarks");
//                String csdfYear = request.getParameter("csdfYear");
//                BigDecimal bgcsdf = new BigDecimal(csdfamt);
//                BigDecimal poolbal = new BigDecimal(mainbal);
//                csdfde.setCheckerStatus("Pending");
//                csdfde.setCsdfAmount(bgcsdf);
//                csdfde.setEntryDate(new Date());
//                csdfde.setMainPoolBalance(poolbal);
//                csdfde.setRemarks(csdfremarks);
//                csdfde.setCsdfMonth(csdfmonth);
//                csdfde.setAmtCategory("Bills");
//                csdfde.setCsdfYear(new BigDecimal(csdfYear));
//
//                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
//                csdfde.setEntryTime(currentTimestamp);
//
//                csdfdao.NewCsdfDetails(csdfde);
//                ModelAndView mv2 = new ModelAndView("successMsg");
//                mv2.addObject("Msg", "CSDF Details has Sucessfully submiteed for Checker");
//                return mv2;
//
//            }
//        }
//        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
//        List<TempRefundBillCorp> listrefund = null;
//        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
//        if (listrefund != null && listrefund.size() > 0) {
//            ModelAndView mv2 = new ModelAndView("successMsg");
//            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
//            return mv2;
//        }
//
//        List<MiscDisbursement> listmiscdis = null;
//        miscDisbursementDAO miscdao = new miscDisbursementDAO();
//        listmiscdis = miscdao.getmiscDetails("Bills");
//        System.out.print("Inside misc button" + listmiscdis.size());
//        if (listmiscdis != null && listmiscdis.size() > 0) {
//            ModelAndView mv2 = new ModelAndView("successMsg");
//            mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
//            return mv2;
//
//        }
//
//        listpsdf = csdfdao.getCsdfDetails("Bills");
//        if (listpsdf != null && listpsdf.size() > 0) {
//            ModelAndView mv2 = new ModelAndView("successMsg");
//            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
//            return mv2;
//        }
//
//        String bname = request.getParameter("bname");
//        System.out.print("Button Name is null is :" + bname);
//        if (bname == null) {
//            System.out.print("Button Name is null ");
//        }
//        if (bname != null) {
//            String rowcount = request.getParameter("rowcount");
//            String billedDate = request.getParameter("billedDate");
//            String mainbal = request.getParameter("mainbal");
//            BigDecimal poolbalance = new BigDecimal(mainbal);
//            System.out.print("billedDate is :" + billedDate);
//            int row_count = Integer.parseInt(rowcount);
//            System.out.print("ROW COUNT is :" + row_count);
//            String uniqueID = "uniqueID";
//            String uniqueID1;
//            String weekID = "weekID";
//            String weekID1;
//            String totalamount = "totalamount";
//            String totalamount1;
//            String disamount = "disamount";
//            String disamount1;
//            String corpID = "corpID";
//            String corpID1;
//            String billdate = "billdate";
//            String billdate1;
//            String billtype = "billtype";
//            String billtype1;
//            String balamt1;
//            String remarks;
//            String billdue1;
//            String disamt1;
//
//            TempPaymentDisbursementDAO temppaydisdao = new TempPaymentDisbursementDAO();
//            int maxid = 0;
//            int pending = 0;
//            String disstatus;
//            Corporates corp = new Corporates();
//            BillReceiveCorp billrecv = new BillReceiveCorp();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            for (int i = 1; i <= row_count; i++) {
//                uniqueID1 = request.getParameter("uniqueID" + i);
//                weekID1 = request.getParameter("weekID" + i);
//                totalamount1 = request.getParameter("totalamount" + i);
//                disamount1 = request.getParameter("disamount" + i);
//                corpID1 = request.getParameter("corpID" + i);
//                billdate1 = request.getParameter("billdate" + i);
//                billtype1 = request.getParameter("billtype" + i);
//                balamt1 = request.getParameter("balamt" + i);
//                remarks = request.getParameter("remarks" + i);
//                billdue1 = request.getParameter("billdue" + i);
//                disamt1 = request.getParameter("disamt" + i);
//                System.out.print("billdate1 is :" + billdate1);
//                System.out.print("remarks is :" + remarks);
//                System.out.print("billdue1 is :" + billdue1);
//                int checkedflag = 0;
//                if (billdate1.equals(billedDate)) {
//                    String[] chkSms = request.getParameterValues("items");
//                    System.out.print("chkSms length is :" + chkSms.length);
//                    BillPayableCorp billpaydao = new BillPayableCorp();
//                    for (int j = 0; j < chkSms.length; j++) {
//                        if (uniqueID1.equalsIgnoreCase(chkSms[j].toString())) {
//                            checkedflag = 1;
//                        }
//                    }
//                    if (uniqueID1.substring(0, 1).equalsIgnoreCase("D")) {
//                        if (checkedflag == 1) {
//                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
//                            System.out.print("chkSms is :" + chkSms[0].toString());
//                            BigDecimal bg1 = new BigDecimal(balamt1);
//                            BigDecimal bg2 = new BigDecimal(disamount1);
//                            BigDecimal bgpending = new BigDecimal(0);
//                            bgpending = bg1.subtract(bg2);
//                            pending = bgpending.intValue();
//                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
//                            System.out.print("pending Bigdfecimal value is :" + pending);
//                            if (pending > 0) {
//                                disstatus = "PARTIALLY";
//                            } else {
//                                disstatus = "PAID";
//                            }
//                            corp.setCorporateId(Integer.parseInt(corpID1));
//                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
//                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
//                            maxid = maxid + 1;
//                            BigDecimal bD = new BigDecimal(maxid);
//                            tempaydis.setDisburseId(bD);
//                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
//                            tempaydis.setCheckerStatus("Pending");
//                            tempaydis.setCorporates(corp);
//                            tempaydis.setDisbursalDate(new Date());
//                            tempaydis.setDisburseAmount(new BigDecimal(disamount1));
//                            tempaydis.setDisburseStatus(disstatus);
//                            Date d = new Date();
//                            BigDecimal bgyear = new BigDecimal(Calendar.getInstance().get(Calendar.YEAR));
////                            BigDecimal bgyear = new BigDecimal(d.getYear());
//                            tempaydis.setDisburseYear(bgyear);
//                            tempaydis.setEntryDate(new Date());
//                            tempaydis.setTotalAmount(bg1);
//                            tempaydis.setBillReceiveCorp(billrecv);
//                            tempaydis.setWeekId(new BigDecimal(weekID1));
//                            tempaydis.setBillType(billtype1);
//                            tempaydis.setPendingAmount(bgpending);
//                            tempaydis.setDisburseCategory("Original");
//                            tempaydis.setRemarks(remarks);
//                            tempaydis.setBillDueDate(sdf.parse(billdue1));
//                            Date todaydate = sdf.parse(billedDate);
//                            tempaydis.setBillingDate(todaydate);
//                            tempaydis.setPoolBal(poolbalance);
//
//                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
//                        }//end of uniqueid
//                        else {
//                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
//                            BigDecimal bg1 = new BigDecimal(balamt1);
//                            BigDecimal bg2 = new BigDecimal(disamount1);
//                            BigDecimal bgpending = new BigDecimal(0);
//                            bgpending = bg1.subtract(bg2);
//                            pending = bgpending.intValue();
//                            System.out.print("pending Bigdfecimal value is :" + pending);
//                            if (pending > 0) {
//                                disstatus = "NOT PAID";
//                            } else {
//                                disstatus = "PAID";
//                            }
//                            corp.setCorporateId(Integer.parseInt(corpID1));
//                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
//                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
//                            maxid = maxid + 1;
//                            BigDecimal bD = new BigDecimal(maxid);
//                            tempaydis.setDisburseId(bD);
//                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
//                            tempaydis.setCheckerStatus("Pending");
//                            tempaydis.setCorporates(corp);
//                            tempaydis.setDisbursalDate(new Date());
//                            tempaydis.setDisburseAmount(new BigDecimal(0));
//                            tempaydis.setDisburseStatus(disstatus);
//                            Date d = new Date();
//                            BigDecimal bgyear = new BigDecimal(d.getYear());
//                            tempaydis.setDisburseYear(bgyear);
//                            tempaydis.setEntryDate(new Date());
//                            tempaydis.setTotalAmount(new BigDecimal(totalamount1));
//                            tempaydis.setBillReceiveCorp(billrecv);
//                            tempaydis.setWeekId(new BigDecimal(weekID1));
//                            tempaydis.setBillType(billtype1);
//                            tempaydis.setPendingAmount(new BigDecimal(balamt1));
//                            tempaydis.setDisburseCategory("Original");
//                            tempaydis.setRemarks(remarks);
//                            tempaydis.setBillDueDate(sdf.parse(billdue1));
//                            Date todaydate = sdf.parse(billedDate);
//                            tempaydis.setBillingDate(todaydate);
//                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
//                        }
//                    }//end of checking disbursement or refund
//                    else {
//                        if (checkedflag == 1) {
//                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
//                            corp.setCorporateId(Integer.parseInt(corpID1));
//                            int maxrefid = 0;
//                            System.out.println("Inside temp Rfudntotref amount ");
//                            maxrefid = temprefunddao.getMaxUniqueID();
//                            maxrefid = maxrefid + 1;
//                            temprefund.setSlno(new BigDecimal(maxrefid));
//                            temprefund.setCheckerStatus("Pending");
//                            temprefund.setPaidAmount(new BigDecimal(disamount1));
//                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
//                            temprefund.setPendingAmount(new BigDecimal(disamt1));
//                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
//                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
//                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
//                            temprefund.setBillPayableCorp(billpaydao);
//                            temprefund.setRefundDate(new Date());
//                            temprefund.setCorporates(corp);
//                            temprefund.setRemarks(remarks);
//                            temprefund.setWeekid(new BigDecimal(weekID1));
//                            temprefund.setPoolBal(poolbalance);
//                            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
//                            temprefund.setEntryTime(currentTimestamp);
//
//                            temprefunddao.NewTempRefundBillCorp(temprefund);
//                        } else {
//                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
//                            corp.setCorporateId(Integer.parseInt(corpID1));
//                            int maxrefid = 0;
//                            System.out.println("Inside temp Rfudntotref amount ");
//                            maxrefid = temprefunddao.getMaxUniqueID();
//                            maxrefid = maxrefid + 1;
//                            temprefund.setSlno(new BigDecimal(maxrefid));
//                            temprefund.setCheckerStatus("Pending");
//                            temprefund.setPaidAmount(new BigDecimal(0));
//                            temprefund.setPendingAmount(new BigDecimal(balamt1));
//                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
//                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
//                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
//                            temprefund.setBillPayableCorp(billpaydao);
//                            temprefund.setRefundDate(new Date());
//                            temprefund.setCorporates(corp);
//                            temprefund.setRemarks(remarks);
//                            temprefund.setWeekid(new BigDecimal(weekID1));
//                            temprefunddao.NewTempRefundBillCorp(temprefund);
//                        }
//                    }
//                }//end of checkbox
//            }//end of loop
//            ModelAndView mv1 = new ModelAndView("successMsg");
//            mv1.addObject("Msg", "Succesfully submitted the Payment Disbursement for Verification.......");
//            return mv1;
//        }//end of button
//        BillPayableCorpDAO billPayCorpDao = new BillPayableCorpDAO();
//        List<BillPayableCorp> listBillPayableCorp = new ArrayList<>();
//        List<String> listCorp = new ArrayList<>();
//        listBillPayableCorp = billPayCorpDao.getPendingBillCorpNameList();
//        for (BillPayableCorp temp : listBillPayableCorp) {
//            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
//            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
//            if (ans); else {
//                listCorp.add(temp.getCorporates().getCorporateName());
//            }
//        }
//        mv.addObject("corpList", listCorp);
//        List<PoolAccountDetails> list = null;
//        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
//        list = pooldao.getPoolAccountDetails();
//        List<BillReceiveCorp> list456 = new ArrayList<>();
//        BillReceiveCorp billrecorp = null;
//        List<Object[]> list1 = new ArrayList<Object[]>();
//        List<Object[]> list4 = new ArrayList<Object[]>();
//        List<Object[]> list7 = new ArrayList<Object[]>();
//        List<Object[]> list8 = new ArrayList<Object[]>();
//        Corporates corp = null;
//        CorporatesDAO corpdao = new CorporatesDAO();
//        List<Date> list2 = new ArrayList<Date>();
//        List<Date> listpaydates = new ArrayList<Date>();
//        List<BigDecimal> list3 = new ArrayList<BigDecimal>();
//        List<BigDecimal> listpayweek = new ArrayList<BigDecimal>();
//        List<Date> listnew = new ArrayList<Date>();
//        BillPayableCorpDAO billpacorpdao = new BillPayableCorpDAO();
//        BillReceiveCorpDAO billrecedao = new BillReceiveCorpDAO();
//        list1 = billrecedao.getAllPendingDisbursementbyCorpObject();
//        list3 = billrecedao.getPendingDisbursementbyCorpgroupbyWeekID();
//        listpayweek = billpacorpdao.getRefundPendingDisbursementbyCorpgroupbyWeekID();
//        list2 = billrecedao.getAllPendingDisbursementbyCorpgroupbyBillingDateTimestamp();
//        listpaydates = billpacorpdao.getAllRefundDisbursementbyCorpgroupbyBillingDateTimestamp();
//        if (listpayweek != null && !(listpayweek.isEmpty())) {
//            for (int n = 0; n < listpayweek.size(); n++) {
//                int flag = 0;
//                for (int y = 0; y < list3.size(); y++) {
//                    if (listpayweek.get(n).compareTo(list3.get(y)) == 0) {
//                        flag = 1;
//                    }
//                }
//                if (flag == 0) {
//                    list3.add(listpayweek.get(n));
//                }
//            }
//        }
//        for (int i = 0; i < list1.size(); i++) {
//            billrecorp = new BillReceiveCorp();
//            corp = new Corporates();
//            Object[] row = (Object[]) list1.get(i);
//            billrecorp.setUniqueId((BigDecimal) row[0]);
//            billrecorp.setBillingDate((Date) row[2]);
//            billrecorp.setBillType((String) row[1]);
//            BigDecimal bg = (BigDecimal) row[3];
//            String corpname = corpdao.geCorpNamebyId(bg.intValue());
//            corp.setCorporateId(bg.intValue());
//            corp.setCorporateName(corpname);
//            billrecorp.setCorporates(corp);
//            billrecorp.setWeekId((BigDecimal) row[4]);
//            billrecorp.setRevisionNo((BigDecimal) row[5]);
//            billrecorp.setBillPriority((String) row[6]);
//            billrecorp.setPendingAmount((BigDecimal) row[7]);
//            billrecorp.setToalnet((BigDecimal) row[8]);
//            billrecorp.setRevisedpaybale((BigDecimal) row[9]);
//            billrecorp.setRevisedrefund((BigDecimal) row[10]);
//            billrecorp.setBillDueDate((Date) row[11]);
//            list456.add(billrecorp);
//        }
//        if (list2 != null && !(list2.isEmpty())) {
//            for (int m = 0; m < list2.size(); m++) {
//                listnew.add(list2.get(m));
//            }
//        }
//        if (listpaydates != null && !(listpaydates.isEmpty())) {
//            for (int n = 0; n < listpaydates.size(); n++) {
//                int flag = 0;
//                for (int y = 0; y < listnew.size(); y++) {
//                    if (listpaydates.get(n).equals(listnew.get(y))) {
//                        flag = 1;
//                    }
//                }
//                if (flag == 0) {
//                    listnew.add(listpaydates.get(n));
//                }
//            }
//        }
//        Collections.sort(listnew);
//        Collections.sort(list3);
//        ArrayList list123 = new ArrayList();
//        BillPriorityDAO billpridao = new BillPriorityDAO();
//        List<BillPriority> listbillpriority = null;
//        listbillpriority = billpridao.getBillPriorityDetails();
//        for (BillPriority e : listbillpriority) {
//            list123.add(e.getPriorityname());
//        }
//        List<Corporates> listcorp = null;
//        listcorp = corpdao.getDefaultCorporateNamebyID();
//        List<Object[]> list1000 = new ArrayList<>();
//        List<Object[]> list2000 = new ArrayList<>();
//        list2000 = billpacorpdao.getPendingReceivableBillsbyallCorp();
//        list1000 = billpacorpdao.getPendingPayableBillsbyallCorp();
//        List<PayDisbursementMapping> listpaymappingPayable = new ArrayList<>();
//        List<PayDisbursementMapping> listpaymappingReceivable = new ArrayList<>();
//        PayDisbursementMapping paydismapp = null;
//        for (Object[] obj : list1000) {
//            paydismapp = new PayDisbursementMapping();
//            paydismapp.setCorporateName((String) obj[0]);
//            paydismapp.setBilltype((String) obj[1]);
//            paydismapp.setNetAmount((BigDecimal) obj[2]);
//            listpaymappingPayable.add(paydismapp);
//        }
//        for (Object[] obj : list2000) {
//            paydismapp = new PayDisbursementMapping();
//            paydismapp.setCorporateName((String) obj[0]);
//            paydismapp.setBilltype((String) obj[1]);
//            paydismapp.setNetAmount((BigDecimal) obj[2]);
//            listpaymappingReceivable.add(paydismapp);
//        }
//        List<BillPayableCorp> listrefundpayable = null;
//        listrefundpayable = billpacorpdao.getAllRefundReceivableBillCorplist();
//        mv.addObject("listrefundpayable", listrefundpayable);
//
//        System.out.println("############# List aise of refund Payable Weeks List is" + listrefundpayable.size());
//
//        for (int z = 0; z < listrefundpayable.size(); z++) {
//            System.out.println("dates iss" + listrefundpayable.get(z).getBillingDate());
//
//        }
//        mv.addObject("pendingPayablebilllist", list1000);
//        mv.addObject("pendingPayablebilllistMap", listpaymappingPayable);
//        mv.addObject("pendingReceivablebilllist", list2000);
//        mv.addObject("pendingReceivablebilllistMap", listpaymappingReceivable);
//        mv.addObject("corpdefaultList", listcorp);
//        mv.addObject("pooldetails", list);
//        mv.addObject("disburseList", list456);
//        mv.addObject("billdateList", listnew);
//        mv.addObject("weekList", list3);
//        mv.addObject("priorityList", list123);
//        mv.addObject("pendingbilllist", list1000);
//        return mv;
//    }
    public ModelAndView newDisbursement(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        ModelAndView mv = new ModelAndView("newDisbursement");
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        String csdfSubmit = request.getParameter("csdfSubmit");
        if (csdfSubmit != null) {
            CsdfDetails csdfde = new CsdfDetails();
            System.out.print("Inside PSDF button");
            listpsdf = csdfdao.getCsdfDetails("Bills");
            System.out.print("Inside PSDF button" + listpsdf.size());
            if (listpsdf != null && listpsdf.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
                maxid = csdfdao.getMaxSlno().intValue();
                maxid = maxid + 1;
                csdfde.setSlno(new BigDecimal(maxid));
                String csdfmonth = request.getParameter("csdfmonth");
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String csdfYear = request.getParameter("csdfYear");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                csdfde.setCheckerStatus("Pending");
                csdfde.setCsdfAmount(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setMainPoolBalance(poolbal);
                csdfde.setRemarks(csdfremarks);
                csdfde.setCsdfMonth(csdfmonth);
                csdfde.setAmtCategory("Bills");
                csdfde.setCsdfYear(new BigDecimal(csdfYear));

                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(currentTimestamp);

                csdfdao.NewCsdfDetails(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "CSDF Details has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        List<MiscDisbursement> listmiscdis = null;
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        listmiscdis = miscdao.getmiscDetails("Bills");
        System.out.print("Inside misc button" + listmiscdis.size());
        if (listmiscdis != null && listmiscdis.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
            return mv2;

        }
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }

        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempPendingMappingBillBankbyCorpListbyChecker("Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getTempRefundPendingbyCorpListbychecker("Pending");
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        String bname = request.getParameter("bname");
        System.out.print("Button Name is null is :" + bname);
        if (bname == null) {
            System.out.print("Button Name is null ");
        }
        if (bname != null) {
            String rowcount = request.getParameter("rowcount");
            String billedDate = request.getParameter("billedDate");
            String mainbal = request.getParameter("mainbal");
            BigDecimal poolbalance = new BigDecimal(mainbal);
            System.out.print("billedDate is :" + billedDate);
            int row_count = Integer.parseInt(rowcount);
            System.out.print("ROW COUNT is :" + row_count);
            String uniqueID = "uniqueID";
            String uniqueID1;
            String weekID = "weekID";
            String weekID1;
            String totalamount = "totalamount";
            String totalamount1;
            String disamount = "disamount";
            String disamount1;
            String corpID = "corpID";
            String corpID1;
            String billdate = "billdate";
            String billdate1;
            String billtype = "billtype";
            String billtype1;
            String balamt1;
            String remarks;
            String billdue1;
            String disamt1;

            TempPaymentDisbursementDAO temppaydisdao = new TempPaymentDisbursementDAO();
            int maxid = 0;
            int pending = 0;
            String disstatus;
            Corporates corp = new Corporates();
            BillReceiveCorp billrecv = new BillReceiveCorp();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

            for (int i = 1; i <= row_count; i++) {
                uniqueID1 = request.getParameter("uniqueID" + i);
                weekID1 = request.getParameter("weekID" + i);
                totalamount1 = request.getParameter("totalamount" + i);
                disamount1 = request.getParameter("disamount" + i);
                corpID1 = request.getParameter("corpID" + i);
                billdate1 = request.getParameter("billdate" + i);
                billtype1 = request.getParameter("billtype" + i);
                balamt1 = request.getParameter("balamt" + i);
                remarks = request.getParameter("remarks" + i);
                billdue1 = request.getParameter("billdue" + i);
                disamt1 = request.getParameter("disamt" + i);
                System.out.print("billdate1 is :" + billdate1);
                System.out.print("remarks is :" + remarks);
                System.out.print("billdue1 is :" + billdue1);
                int checkedflag = 0;
                if (billdate1.equals(billedDate)) {
                    String[] chkSms = request.getParameterValues("items");
                    System.out.print("chkSms length is :" + chkSms.length);
                    BillPayableCorp billpaydao = new BillPayableCorp();
                    for (int j = 0; j < chkSms.length; j++) {
                        if (uniqueID1.equalsIgnoreCase(chkSms[j].toString())) {
                            checkedflag = 1;
                        }
                    }
                    if (uniqueID1.substring(0, 1).equalsIgnoreCase("D")) {
                        if (checkedflag == 1) {
                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
                            System.out.print("chkSms is :" + chkSms[0].toString());
                            BigDecimal bg1 = new BigDecimal(balamt1);
                            BigDecimal bg2 = new BigDecimal(disamount1);
                            BigDecimal bgpending = new BigDecimal(0);
                            bgpending = bg1.subtract(bg2);
                            pending = bgpending.intValue();
                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
                            System.out.print("pending Bigdfecimal value is :" + bgpending);
                            if (bgpending.compareTo(BigDecimal.ZERO) == 1) {
                                disstatus = "PARTIALLY";
                            } else {
                                disstatus = "PAID";
                            }
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
                            maxid = maxid + 1;
                            BigDecimal bD = new BigDecimal(maxid);
                            tempaydis.setDisburseId(bD);
                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
                            tempaydis.setCheckerStatus("Pending");
                            tempaydis.setCorporates(corp);
                            tempaydis.setDisbursalDate(new Date());
                            tempaydis.setDisburseAmount(new BigDecimal(disamount1));
                            tempaydis.setDisburseStatus(disstatus);
                            Date d = new Date();
                            BigDecimal bgyear = new BigDecimal(Calendar.getInstance().get(Calendar.YEAR));
//                            BigDecimal bgyear = new BigDecimal(d.getYear());
                            tempaydis.setDisburseYear(bgyear);
                            tempaydis.setEntryDate(new Date());
                            tempaydis.setTotalAmount(bg1);
                            tempaydis.setBillReceiveCorp(billrecv);
                            tempaydis.setWeekId(new BigDecimal(weekID1));
                            tempaydis.setBillType(billtype1);
                            tempaydis.setPendingAmount(bgpending);
                            tempaydis.setDisburseCategory("Original");
                            tempaydis.setRemarks(remarks);
                            tempaydis.setBillDueDate(sdf.parse(billdue1));
                            Date todaydate = sdf.parse(billedDate);
                            tempaydis.setBillingDate(todaydate);
                            tempaydis.setPoolBal(poolbalance);
                            currentTimestamp = addMilliseconds(currentTimestamp, 1);

                            tempaydis.setEntryTime(currentTimestamp);

                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
                        }//end of uniqueid
                        else {
                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
                            BigDecimal bg1 = new BigDecimal(balamt1);
                            BigDecimal bg2 = new BigDecimal(disamount1);
                            BigDecimal bgpending = new BigDecimal(0);
                            bgpending = bg1.subtract(bg2);
                            pending = bgpending.intValue();
                            System.out.print("pending Bigdfecimal value is :" + pending);
//                            if (pending > 0) {
//                                disstatus = "NOT PAID";
//                            } else {
//                                disstatus = "PAID";
//                            }
                            disstatus = "NOT PAID";
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
                            maxid = maxid + 1;
                            BigDecimal bD = new BigDecimal(maxid);
                            tempaydis.setDisburseId(bD);
                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
                            tempaydis.setCheckerStatus("Pending");
                            tempaydis.setCorporates(corp);
                            tempaydis.setDisbursalDate(new Date());
                            tempaydis.setDisburseAmount(new BigDecimal(0));
                            tempaydis.setDisburseStatus(disstatus);
                            Date d = new Date();
                            BigDecimal bgyear = new BigDecimal(d.getYear());
                            tempaydis.setDisburseYear(bgyear);
                            tempaydis.setEntryDate(new Date());
                            tempaydis.setTotalAmount(new BigDecimal(totalamount1));
                            tempaydis.setBillReceiveCorp(billrecv);
                            tempaydis.setWeekId(new BigDecimal(weekID1));
                            tempaydis.setBillType(billtype1);
                            tempaydis.setPendingAmount(new BigDecimal(balamt1));
                            tempaydis.setDisburseCategory("Original");
                            tempaydis.setRemarks(remarks);
                            tempaydis.setBillDueDate(sdf.parse(billdue1));
                            Date todaydate = sdf.parse(billedDate);
                            tempaydis.setBillingDate(todaydate);
                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
                        }
                    }//end of checking disbursement or refund
                    else {
                        if (checkedflag == 1) {
                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            int maxrefid = 0;
                            System.out.println("Inside temp Rfudntotref amount ");
                            maxrefid = temprefunddao.getMaxUniqueID();
                            maxrefid = maxrefid + 1;
                            temprefund.setSlno(new BigDecimal(maxrefid));
                            temprefund.setCheckerStatus("Pending");
                            temprefund.setPaidAmount(new BigDecimal(disamount1));
                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
                            temprefund.setPendingAmount(new BigDecimal(disamt1));
                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
                            temprefund.setBillPayableCorp(billpaydao);
                            temprefund.setRefundDate(new Date());
                            temprefund.setCorporates(corp);
                            temprefund.setRemarks(remarks);
                            temprefund.setWeekid(new BigDecimal(weekID1));
                            temprefund.setPoolBal(poolbalance);
                            currentTimestamp = addMilliseconds(currentTimestamp, 1);

                            temprefund.setEntryTime(currentTimestamp);

                            temprefunddao.NewTempRefundBillCorp(temprefund);
                        } else {
                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            int maxrefid = 0;
                            System.out.println("Inside temp Rfudntotref amount ");
                            maxrefid = temprefunddao.getMaxUniqueID();
                            maxrefid = maxrefid + 1;
                            temprefund.setSlno(new BigDecimal(maxrefid));
                            temprefund.setCheckerStatus("Pending");
                            temprefund.setPaidAmount(new BigDecimal(0));
                            temprefund.setPendingAmount(new BigDecimal(balamt1));
                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
                            temprefund.setBillPayableCorp(billpaydao);
                            temprefund.setRefundDate(new Date());
                            temprefund.setCorporates(corp);
                            temprefund.setRemarks(remarks);
                            temprefund.setWeekid(new BigDecimal(weekID1));
                            temprefunddao.NewTempRefundBillCorp(temprefund);
                        }
                    }
                }//end of checkbox
            }//end of loop
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully submitted the Payment Disbursement for Verification.......");
            return mv1;
        }//end of button
        BillPayableCorpDAO billPayCorpDao = new BillPayableCorpDAO();
        List<BillPayableCorp> listBillPayableCorp = new ArrayList<>();
        List<String> listCorp = new ArrayList<>();
        listBillPayableCorp = billPayCorpDao.getPendingBillCorpNameListdsm();
        for (BillPayableCorp temp : listBillPayableCorp) {
            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
            if (ans); else {
                listCorp.add(temp.getCorporates().getCorporateName());
            }
        }
        mv.addObject("corpList", listCorp);
        List<PoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getPoolAccountDetails();
        List<BillReceiveCorp> list456 = new ArrayList<>();
        BillReceiveCorp billrecorp = null;
        List<Object[]> list1 = new ArrayList<Object[]>();
        List<Object[]> list4 = new ArrayList<Object[]>();
        List<Object[]> list7 = new ArrayList<Object[]>();
        List<Object[]> list8 = new ArrayList<Object[]>();
        Corporates corp = null;
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Date> list2 = new ArrayList<Date>();
        List<Date> listpaydates = new ArrayList<Date>();
        List<BigDecimal> list3 = new ArrayList<BigDecimal>();
        List<BigDecimal> listpayweek = new ArrayList<BigDecimal>();
        List<Date> listnew = new ArrayList<Date>();
        BillPayableCorpDAO billpacorpdao = new BillPayableCorpDAO();
        BillReceiveCorpDAO billrecedao = new BillReceiveCorpDAO();
        list1 = billrecedao.getAllPendingDisbursementbyCorpObjectdsm();
        list3 = billrecedao.getPendingDisbursementbyCorpgroupbyWeekIDdsm();
        listpayweek = billpacorpdao.getRefundPendingDisbursementbyCorpgroupbyWeekIDdsm();
        list2 = billrecedao.getAllPendingDisbursementbyCorpgroupbyBillingDateTimestampdsm();
        listpaydates = billpacorpdao.getAllRefundDisbursementbyCorpgroupbyBillingDateTimestampdsm();
        if (listpayweek != null && !(listpayweek.isEmpty())) {
            for (int n = 0; n < listpayweek.size(); n++) {
                int flag = 0;
                for (int y = 0; y < list3.size(); y++) {
                    if (listpayweek.get(n).compareTo(list3.get(y)) == 0) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    list3.add(listpayweek.get(n));
                }
            }
        }
        for (int i = 0; i < list1.size(); i++) {
            billrecorp = new BillReceiveCorp();
            corp = new Corporates();
            Object[] row = (Object[]) list1.get(i);
            billrecorp.setUniqueId((BigDecimal) row[0]);
            billrecorp.setBillingDate((Date) row[2]);
            billrecorp.setBillType((String) row[1]);
            BigDecimal bg = (BigDecimal) row[3];
            String corpname = corpdao.geCorpNamebyId(bg.intValue());
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName(corpname);
            billrecorp.setCorporates(corp);
            billrecorp.setWeekId((BigDecimal) row[4]);
            billrecorp.setRevisionNo((BigDecimal) row[5]);
            billrecorp.setBillPriority((String) row[6]);
            billrecorp.setPendingAmount((BigDecimal) row[7]);
            billrecorp.setToalnet((BigDecimal) row[8]);
            billrecorp.setRevisedpaybale((BigDecimal) row[9]);
            billrecorp.setRevisedrefund((BigDecimal) row[10]);
            billrecorp.setBillDueDate((Date) row[11]);
            billrecorp.setBillYear((BigDecimal) row[12]);
            list456.add(billrecorp);
        }
        if (list2 != null && !(list2.isEmpty())) {
            for (int m = 0; m < list2.size(); m++) {
                listnew.add(list2.get(m));
            }
        }
        if (listpaydates != null && !(listpaydates.isEmpty())) {
            for (int n = 0; n < listpaydates.size(); n++) {
                int flag = 0;
                for (int y = 0; y < listnew.size(); y++) {
                    if (listpaydates.get(n).equals(listnew.get(y))) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    listnew.add(listpaydates.get(n));
                }
            }
        }
        Collections.sort(listnew);

        String showAll = request.getParameter("showAll");
        if (showAll != null) {
            mv.addObject("warning", "Showing ALL Bill Issuance dates.");
        } else {
            System.out.println("------------------------------------------------------");
            for (Date d : listnew) {
                System.out.println(d);
            }
            System.out.println("------------------------------------------------------");
            while (listnew != null && listnew.size() > 5) {
                listnew.remove(0);
            }

            for (Date d : listnew) {
                System.out.println(d);
            }
            System.out.println("------------------------------------------------------");

            mv.addObject("warning", "Showing LAST 5 Bill Issuance dates.");
        }

        Collections.sort(list3);
        ArrayList list123 = new ArrayList();
        BillPriorityDAO billpridao = new BillPriorityDAO();
        List<BillPriority> listbillpriority = null;
        listbillpriority = billpridao.getBillPriorityDetails();
        for (BillPriority e : listbillpriority) {
            list123.add(e.getPriorityname());
        }
        List<Corporates> listcorp = null;
        listcorp = corpdao.getDefaultCorporateNamebyID();
        List<Object[]> list1000 = new ArrayList<>();
        List<Object[]> list2000 = new ArrayList<>();
        list2000 = billpacorpdao.getPendingReceivableBillsbyallCorpdsm();
        list1000 = billpacorpdao.getPendingPayableBillsbyallCorpdsm();
        List<PayDisbursementMapping> listpaymappingPayable = new ArrayList<>();
        List<PayDisbursementMapping> listpaymappingReceivable = new ArrayList<>();
        PayDisbursementMapping paydismapp = null;
        for (Object[] obj : list1000) {
            paydismapp = new PayDisbursementMapping();
            paydismapp.setCorporateName((String) obj[0]);
            paydismapp.setBilltype((String) obj[1]);
            paydismapp.setNetAmount((BigDecimal) obj[2]);
            listpaymappingPayable.add(paydismapp);
        }
        for (Object[] obj : list2000) {
            paydismapp = new PayDisbursementMapping();
            paydismapp.setCorporateName((String) obj[0]);
            paydismapp.setBilltype((String) obj[1]);
            paydismapp.setNetAmount((BigDecimal) obj[2]);
            listpaymappingReceivable.add(paydismapp);
        }
        List<BillPayableCorp> listrefundpayable = null;
        listrefundpayable = billpacorpdao.getAllRefundReceivableBillCorplistdsm();
        mv.addObject("listrefundpayable", listrefundpayable);

        System.out.println("############# List aise of refund Payable Weeks List is" + listrefundpayable.size());

        for (int z = 0; z < listrefundpayable.size(); z++) {
            System.out.println("dates iss" + listrefundpayable.get(z).getBillingDate());

        }
        mv.addObject("pendingPayablebilllist", list1000);
        mv.addObject("pendingPayablebilllistMap", listpaymappingPayable);
        mv.addObject("pendingReceivablebilllist", list2000);
        mv.addObject("pendingReceivablebilllistMap", listpaymappingReceivable);
        mv.addObject("corpdefaultList", listcorp);
        mv.addObject("pooldetails", list);
        mv.addObject("disburseList", list456);
        mv.addObject("billdateList", listnew);
        mv.addObject("weekList", list3);
        mv.addObject("priorityList", list123);
        mv.addObject("pendingbilllist", list1000);
        return mv;
    }

    public ModelAndView newDisbursementsras(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null || session1.getAttribute("loginid") == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        ModelAndView mv = new ModelAndView("newDisbursementsras");
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        String csdfSubmit = request.getParameter("csdfSubmit");
        if (csdfSubmit != null) {
            CsdfDetails csdfde = new CsdfDetails();
            System.out.print("Inside PSDF button");
            listpsdf = csdfdao.getCsdfDetails("Bills");
            System.out.print("Inside PSDF button" + listpsdf.size());
            if (listpsdf != null && listpsdf.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
                maxid = csdfdao.getMaxSlno().intValue();
                maxid = maxid + 1;
                csdfde.setSlno(new BigDecimal(maxid));
                String csdfmonth = request.getParameter("csdfmonth");
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String csdfYear = request.getParameter("csdfYear");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                csdfde.setCheckerStatus("Pending");
                csdfde.setCsdfAmount(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setPoolAgcBal(poolbal);
                csdfde.setRemarks(csdfremarks);
                csdfde.setCsdfMonth(csdfmonth);
                csdfde.setAmtCategory("Bills");
                csdfde.setCsdfYear(new BigDecimal(csdfYear));

                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(currentTimestamp);

                csdfdao.NewCsdfDetails(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "CSDF Details has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        List<MiscDisbursement> listmiscdis = null;
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        listmiscdis = miscdao.getmiscDetails("Bills");
        System.out.print("Inside misc button" + listmiscdis.size());
        if (listmiscdis != null && listmiscdis.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
            return mv2;

        }
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }

        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempPendingMappingBillBankbyCorpListbyChecker("Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getTempRefundPendingbyCorpListbychecker("Pending");
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        String bname = request.getParameter("bname");
        System.out.print("Button Name is null is :" + bname);
        if (bname == null) {
            System.out.print("Button Name is null ");
        }
        if (bname != null) {
            String rowcount = request.getParameter("rowcount");
            String billedDate = request.getParameter("billedDate");
            String mainbal = request.getParameter("mainbal");
            BigDecimal poolbalance = new BigDecimal(mainbal);
            System.out.print("billedDate is :" + billedDate);
            int row_count = Integer.parseInt(rowcount);
            System.out.print("ROW COUNT is :" + row_count);
            String uniqueID = "uniqueID";
            String uniqueID1;
            String weekID = "weekID";
            String weekID1;
            String totalamount = "totalamount";
            String totalamount1;
            String disamount = "disamount";
            String disamount1;
            String corpID = "corpID";
            String corpID1;
            String billdate = "billdate";
            String billdate1;
            String billtype = "billtype";
            String billtype1;
            String balamt1;
            String remarks;
            String billdue1;
            String disamt1;

            TempPaymentDisbursementDAO temppaydisdao = new TempPaymentDisbursementDAO();
            int maxid = 0;
            int pending = 0;
            String disstatus;
            Corporates corp = new Corporates();
            BillReceiveCorp billrecv = new BillReceiveCorp();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

            for (int i = 1; i <= row_count; i++) {
                uniqueID1 = request.getParameter("uniqueID" + i);
                weekID1 = request.getParameter("weekID" + i);
                totalamount1 = request.getParameter("totalamount" + i);
                disamount1 = request.getParameter("disamount" + i);
                corpID1 = request.getParameter("corpID" + i);
                billdate1 = request.getParameter("billdate" + i);
                billtype1 = request.getParameter("billtype" + i);
                balamt1 = request.getParameter("balamt" + i);
                remarks = request.getParameter("remarks" + i);
                billdue1 = request.getParameter("billdue" + i);
                disamt1 = request.getParameter("disamt" + i);
                System.out.print("billdate1 is :" + billdate1);
                System.out.print("remarks is :" + remarks);
                System.out.print("billdue1 is :" + billdue1);
                int checkedflag = 0;
                if (billdate1.equals(billedDate)) {
                    String[] chkSms = request.getParameterValues("items");
                    System.out.print("chkSms length is :" + chkSms.length);
                    BillPayableCorp billpaydao = new BillPayableCorp();
                    for (int j = 0; j < chkSms.length; j++) {
                        if (uniqueID1.equalsIgnoreCase(chkSms[j].toString())) {
                            checkedflag = 1;
                        }
                    }
                    if (uniqueID1.substring(0, 1).equalsIgnoreCase("D")) {
                        if (checkedflag == 1) {
                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
                            System.out.print("chkSms is :" + chkSms[0].toString());
                            BigDecimal bg1 = new BigDecimal(balamt1);
                            BigDecimal bg2 = new BigDecimal(disamount1);
                            BigDecimal bgpending = new BigDecimal(0);
                            bgpending = bg1.subtract(bg2);
                            pending = bgpending.intValue();
                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
                            System.out.print("pending Bigdfecimal value is :" + pending);
                            if (bgpending.compareTo(BigDecimal.ZERO) == 1) {
                                disstatus = "PARTIALLY";
                            } else {
                                disstatus = "PAID";
                            }
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
                            maxid = maxid + 1;
                            BigDecimal bD = new BigDecimal(maxid);
                            tempaydis.setDisburseId(bD);
                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
                            tempaydis.setCheckerStatus("Pending");
                            tempaydis.setCorporates(corp);
                            tempaydis.setDisbursalDate(new Date());
                            tempaydis.setDisburseAmount(new BigDecimal(disamount1));
                            tempaydis.setDisburseStatus(disstatus);
                            Date d = new Date();
                            BigDecimal bgyear = new BigDecimal(Calendar.getInstance().get(Calendar.YEAR));
//                            BigDecimal bgyear = new BigDecimal(d.getYear());
                            tempaydis.setDisburseYear(bgyear);
                            tempaydis.setEntryDate(new Date());
                            tempaydis.setTotalAmount(bg1);
                            tempaydis.setBillReceiveCorp(billrecv);
                            tempaydis.setWeekId(new BigDecimal(weekID1));
                            tempaydis.setBillType(billtype1);
                            tempaydis.setPendingAmount(bgpending);
                            tempaydis.setDisburseCategory("Original");
                            tempaydis.setRemarks(remarks);
                            tempaydis.setBillDueDate(sdf.parse(billdue1));
                            Date todaydate = sdf.parse(billedDate);
                            tempaydis.setBillingDate(todaydate);
                            tempaydis.setPoolAgcBal(poolbalance);
                            currentTimestamp = addMilliseconds(currentTimestamp, 1);

                            tempaydis.setEntryTime(currentTimestamp);

                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
                        }//end of uniqueid
                        else {
                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
                            BigDecimal bg1 = new BigDecimal(balamt1);
                            BigDecimal bg2 = new BigDecimal(disamount1);
                            BigDecimal bgpending = new BigDecimal(0);
                            bgpending = bg1.subtract(bg2);
                            pending = bgpending.intValue();
                            System.out.print("pending Bigdfecimal value is :" + pending);
//                            if (pending > 0) {
//                                disstatus = "NOT PAID";
//                            } else {
//                                disstatus = "PAID";
//                            }
                            disstatus = "NOT PAID";
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
                            maxid = maxid + 1;
                            BigDecimal bD = new BigDecimal(maxid);
                            tempaydis.setDisburseId(bD);
                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
                            tempaydis.setCheckerStatus("Pending");
                            tempaydis.setCorporates(corp);
                            tempaydis.setDisbursalDate(new Date());
                            tempaydis.setDisburseAmount(new BigDecimal(0));
                            tempaydis.setDisburseStatus(disstatus);
                            Date d = new Date();
                            BigDecimal bgyear = new BigDecimal(d.getYear());
                            tempaydis.setDisburseYear(bgyear);
                            tempaydis.setEntryDate(new Date());
                            tempaydis.setTotalAmount(new BigDecimal(totalamount1));
                            tempaydis.setBillReceiveCorp(billrecv);
                            tempaydis.setWeekId(new BigDecimal(weekID1));
                            tempaydis.setBillType(billtype1);
                            tempaydis.setPendingAmount(new BigDecimal(balamt1));
                            tempaydis.setDisburseCategory("Original");
                            tempaydis.setRemarks(remarks);
                            tempaydis.setBillDueDate(sdf.parse(billdue1));
                            Date todaydate = sdf.parse(billedDate);
                            tempaydis.setBillingDate(todaydate);
                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
                        }
                    }//end of checking disbursement or refund
                    else {
                        if (checkedflag == 1) {
                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            int maxrefid = 0;
                            System.out.println("Inside temp Rfudntotref amount ");
                            maxrefid = temprefunddao.getMaxUniqueID();
                            maxrefid = maxrefid + 1;
                            temprefund.setSlno(new BigDecimal(maxrefid));
                            temprefund.setCheckerStatus("Pending");
                            temprefund.setPaidAmount(new BigDecimal(disamount1));
                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
                            temprefund.setPendingAmount(new BigDecimal(disamt1));
                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
                            temprefund.setBillPayableCorp(billpaydao);
                            temprefund.setRefundDate(new Date());
                            temprefund.setCorporates(corp);
                            temprefund.setRemarks(remarks);
                            temprefund.setWeekid(new BigDecimal(weekID1));
                            temprefund.setPoolAgcBal(poolbalance);
                            currentTimestamp = addMilliseconds(currentTimestamp, 1);

                            temprefund.setEntryTime(currentTimestamp);

                            temprefunddao.NewTempRefundBillCorp(temprefund);
                        } else {
                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            int maxrefid = 0;
                            System.out.println("Inside temp Rfudntotref amount ");
                            maxrefid = temprefunddao.getMaxUniqueID();
                            maxrefid = maxrefid + 1;
                            temprefund.setSlno(new BigDecimal(maxrefid));
                            temprefund.setCheckerStatus("Pending");
                            temprefund.setPaidAmount(new BigDecimal(0));
                            temprefund.setPendingAmount(new BigDecimal(balamt1));
                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
                            temprefund.setBillPayableCorp(billpaydao);
                            temprefund.setRefundDate(new Date());
                            temprefund.setCorporates(corp);
                            temprefund.setRemarks(remarks);
                            temprefund.setWeekid(new BigDecimal(weekID1));
                            temprefunddao.NewTempRefundBillCorp(temprefund);
                        }
                    }
                }//end of checkbox
            }//end of loop
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully submitted the Payment Disbursement for Verification.......");
            return mv1;
        }//end of button
        BillPayableCorpDAO billPayCorpDao = new BillPayableCorpDAO();
        List<BillPayableCorp> listBillPayableCorp = new ArrayList<>();
        List<String> listCorp = new ArrayList<>();
        listBillPayableCorp = billPayCorpDao.getPendingBillCorpNameListsras();
        for (BillPayableCorp temp : listBillPayableCorp) {
            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
            if (ans); else {
                listCorp.add(temp.getCorporates().getCorporateName());
            }
        }
        mv.addObject("corpList", listCorp);
        List<AgcPoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getAgcPoolAccountDetails();
        List<BillReceiveCorp> list456 = new ArrayList<>();
        BillReceiveCorp billrecorp = null;
        List<Object[]> list1 = new ArrayList<Object[]>();
        List<Object[]> list4 = new ArrayList<Object[]>();
        List<Object[]> list7 = new ArrayList<Object[]>();
        List<Object[]> list8 = new ArrayList<Object[]>();
        Corporates corp = null;
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Date> list2 = new ArrayList<Date>();
        List<Date> listpaydates = new ArrayList<Date>();
        List<BigDecimal> list3 = new ArrayList<BigDecimal>();
        List<BigDecimal> listpayweek = new ArrayList<BigDecimal>();
        List<Date> listnew = new ArrayList<Date>();
        BillPayableCorpDAO billpacorpdao = new BillPayableCorpDAO();
        BillReceiveCorpDAO billrecedao = new BillReceiveCorpDAO();
        list1 = billrecedao.getAllPendingDisbursementbyCorpObjectsras();
        list3 = billrecedao.getPendingDisbursementbyCorpgroupbyWeekIDsras();
        listpayweek = billpacorpdao.getRefundPendingDisbursementbyCorpgroupbyWeekIDsras();
        list2 = billrecedao.getAllPendingDisbursementbyCorpgroupbyBillingDateTimestampsras();
        listpaydates = billpacorpdao.getAllRefundDisbursementbyCorpgroupbyBillingDateTimestampsras();
        if (listpayweek != null && !(listpayweek.isEmpty())) {
            for (int n = 0; n < listpayweek.size(); n++) {
                int flag = 0;
                for (int y = 0; y < list3.size(); y++) {
                    if (listpayweek.get(n).compareTo(list3.get(y)) == 0) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    list3.add(listpayweek.get(n));
                }
            }
        }
        for (int i = 0; i < list1.size(); i++) {
            billrecorp = new BillReceiveCorp();
            corp = new Corporates();
            Object[] row = (Object[]) list1.get(i);
            billrecorp.setUniqueId((BigDecimal) row[0]);
            billrecorp.setBillingDate((Date) row[2]);
            billrecorp.setBillType((String) row[1]);
            BigDecimal bg = (BigDecimal) row[3];
            String corpname = corpdao.geCorpNamebyId(bg.intValue());
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName(corpname);
            billrecorp.setCorporates(corp);
            billrecorp.setWeekId((BigDecimal) row[4]);
            billrecorp.setRevisionNo((BigDecimal) row[5]);
            billrecorp.setBillPriority((String) row[6]);
            billrecorp.setPendingAmount((BigDecimal) row[7]);
            billrecorp.setToalnet((BigDecimal) row[8]);
            billrecorp.setRevisedpaybale((BigDecimal) row[9]);
            billrecorp.setRevisedrefund((BigDecimal) row[10]);
            billrecorp.setBillDueDate((Date) row[11]);
            billrecorp.setBillYear((BigDecimal) row[12]);
            list456.add(billrecorp);
        }
        if (list2 != null && !(list2.isEmpty())) {
            for (int m = 0; m < list2.size(); m++) {
                listnew.add(list2.get(m));
            }
        }
        if (listpaydates != null && !(listpaydates.isEmpty())) {
            for (int n = 0; n < listpaydates.size(); n++) {
                int flag = 0;
                for (int y = 0; y < listnew.size(); y++) {
                    if (listpaydates.get(n).equals(listnew.get(y))) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    listnew.add(listpaydates.get(n));
                }
            }
        }
        Collections.sort(listnew);

        String showAll = request.getParameter("showAll");
        if (showAll != null) {
            mv.addObject("warning", "Showing ALL Bill Issuance dates.");
        } else {
            System.out.println("------------------------------------------------------");
            for (Date d : listnew) {
                System.out.println(d);
            }
            System.out.println("------------------------------------------------------");
            while (listnew != null && listnew.size() > 5) {
                listnew.remove(0);
            }

            for (Date d : listnew) {
                System.out.println(d);
            }
            System.out.println("------------------------------------------------------");

            mv.addObject("warning", "Showing LAST 5 Bill Issuance dates.");
        }

        Collections.sort(list3);
        ArrayList list123 = new ArrayList();
        BillPriorityDAO billpridao = new BillPriorityDAO();
        List<BillPriority> listbillpriority = null;
        listbillpriority = billpridao.getBillPriorityDetails();
        for (BillPriority e : listbillpriority) {
            list123.add(e.getPriorityname());
        }
        List<Corporates> listcorp = null;
        listcorp = corpdao.getDefaultCorporateNamebyID();
        List<Object[]> list1000 = new ArrayList<>();
        List<Object[]> list2000 = new ArrayList<>();
        list2000 = billpacorpdao.getPendingReceivableBillsbyallCorpsras();
        list1000 = billpacorpdao.getPendingPayableBillsbyallCorpsras();
        List<PayDisbursementMapping> listpaymappingPayable = new ArrayList<>();
        List<PayDisbursementMapping> listpaymappingReceivable = new ArrayList<>();
        PayDisbursementMapping paydismapp = null;
        for (Object[] obj : list1000) {
            paydismapp = new PayDisbursementMapping();
            paydismapp.setCorporateName((String) obj[0]);
            paydismapp.setBilltype((String) obj[1]);
            paydismapp.setNetAmount((BigDecimal) obj[2]);
            listpaymappingPayable.add(paydismapp);
        }
        for (Object[] obj : list2000) {
            paydismapp = new PayDisbursementMapping();
            paydismapp.setCorporateName((String) obj[0]);
            paydismapp.setBilltype((String) obj[1]);
            paydismapp.setNetAmount((BigDecimal) obj[2]);
            listpaymappingReceivable.add(paydismapp);
        }
        List<BillPayableCorp> listrefundpayable = null;
        listrefundpayable = billpacorpdao.getAllRefundReceivableBillCorplistsras();
        mv.addObject("listrefundpayable", listrefundpayable);

        System.out.println("############# List aise of refund Payable Weeks List is" + listrefundpayable.size());

        for (int z = 0; z < listrefundpayable.size(); z++) {
            System.out.println("dates iss" + listrefundpayable.get(z).getBillingDate());

        }
        mv.addObject("pendingPayablebilllist", list1000);
        mv.addObject("pendingPayablebilllistMap", listpaymappingPayable);
        mv.addObject("pendingReceivablebilllist", list2000);
        mv.addObject("pendingReceivablebilllistMap", listpaymappingReceivable);
        mv.addObject("corpdefaultList", listcorp);
        mv.addObject("pooldetails", list);
        mv.addObject("disburseList", list456);
        mv.addObject("billdateList", listnew);
        mv.addObject("weekList", list3);
        mv.addObject("priorityList", list123);
        mv.addObject("pendingbilllist", list1000);
        return mv;
    }

    public ModelAndView newDisbursementtras(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null || session1.getAttribute("loginid") == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        ModelAndView mv = new ModelAndView("newDisbursementtras");
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        String csdfSubmit = request.getParameter("csdfSubmit");
        if (csdfSubmit != null) {
            CsdfDetails csdfde = new CsdfDetails();
            System.out.print("Inside PSDF button");
            listpsdf = csdfdao.getCsdfDetails("Bills");
            System.out.print("Inside PSDF button" + listpsdf.size());
            if (listpsdf != null && listpsdf.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
                maxid = csdfdao.getMaxSlno().intValue();
                maxid = maxid + 1;
                csdfde.setSlno(new BigDecimal(maxid));
                String csdfmonth = request.getParameter("csdfmonth");
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String csdfYear = request.getParameter("csdfYear");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                csdfde.setCheckerStatus("Pending");
                csdfde.setCsdfAmount(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setPoolRrasBal(poolbal);
                csdfde.setRemarks(csdfremarks);
                csdfde.setCsdfMonth(csdfmonth);
                csdfde.setAmtCategory("Bills");
                csdfde.setCsdfYear(new BigDecimal(csdfYear));

                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(currentTimestamp);

                csdfdao.NewCsdfDetails(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "CSDF Details has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        List<MiscDisbursement> listmiscdis = null;
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        listmiscdis = miscdao.getmiscDetails("Bills");
        System.out.print("Inside misc button" + listmiscdis.size());
        if (listmiscdis != null && listmiscdis.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
            return mv2;

        }
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }

        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempPendingMappingBillBankbyCorpListbyChecker("Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getTempRefundPendingbyCorpListbychecker("Pending");
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        String bname = request.getParameter("bname");
        System.out.print("Button Name is null is :" + bname);
        if (bname == null) {
            System.out.print("Button Name is null ");
        }
        if (bname != null) {
            String rowcount = request.getParameter("rowcount");
            String billedDate = request.getParameter("billedDate");
            String mainbal = request.getParameter("mainbal");
            BigDecimal poolbalance = new BigDecimal(mainbal);
            System.out.print("billedDate is :" + billedDate);
            int row_count = Integer.parseInt(rowcount);
            System.out.print("ROW COUNT is :" + row_count);
            String uniqueID = "uniqueID";
            String uniqueID1;
            String weekID = "weekID";
            String weekID1;
            String totalamount = "totalamount";
            String totalamount1;
            String disamount = "disamount";
            String disamount1;
            String corpID = "corpID";
            String corpID1;
            String billdate = "billdate";
            String billdate1;
            String billtype = "billtype";
            String billtype1;
            String balamt1;
            String remarks;
            String billdue1;
            String disamt1;

            TempPaymentDisbursementDAO temppaydisdao = new TempPaymentDisbursementDAO();
            int maxid = 0;
            int pending = 0;
            String disstatus;
            Corporates corp = new Corporates();
            BillReceiveCorp billrecv = new BillReceiveCorp();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

            for (int i = 1; i <= row_count; i++) {
                uniqueID1 = request.getParameter("uniqueID" + i);
                weekID1 = request.getParameter("weekID" + i);
                totalamount1 = request.getParameter("totalamount" + i);
                disamount1 = request.getParameter("disamount" + i);
                corpID1 = request.getParameter("corpID" + i);
                billdate1 = request.getParameter("billdate" + i);
                billtype1 = request.getParameter("billtype" + i);
                balamt1 = request.getParameter("balamt" + i);
                remarks = request.getParameter("remarks" + i);
                billdue1 = request.getParameter("billdue" + i);
                disamt1 = request.getParameter("disamt" + i);
                System.out.print("billdate1 is :" + billdate1);
                System.out.print("remarks is :" + remarks);
                System.out.print("billdue1 is :" + billdue1);
                int checkedflag = 0;
                if (billdate1.equals(billedDate)) {
                    String[] chkSms = request.getParameterValues("items");
                    System.out.print("chkSms length is :" + chkSms.length);
                    BillPayableCorp billpaydao = new BillPayableCorp();
                    for (int j = 0; j < chkSms.length; j++) {
                        if (uniqueID1.equalsIgnoreCase(chkSms[j].toString())) {
                            checkedflag = 1;
                        }
                    }
                    if (uniqueID1.substring(0, 1).equalsIgnoreCase("D")) {
                        if (checkedflag == 1) {
                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
                            System.out.print("chkSms is :" + chkSms[0].toString());
                            BigDecimal bg1 = new BigDecimal(balamt1);
                            BigDecimal bg2 = new BigDecimal(disamount1);
                            BigDecimal bgpending = new BigDecimal(0);
                            bgpending = bg1.subtract(bg2);
                            pending = bgpending.intValue();
                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
                            System.out.print("pending Bigdfecimal value is :" + pending);
                            if (bgpending.compareTo(BigDecimal.ZERO) == 1) {
                                disstatus = "PARTIALLY";
                            } else {
                                disstatus = "PAID";
                            }
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
                            maxid = maxid + 1;
                            BigDecimal bD = new BigDecimal(maxid);
                            tempaydis.setDisburseId(bD);
                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
                            tempaydis.setCheckerStatus("Pending");
                            tempaydis.setCorporates(corp);
                            tempaydis.setDisbursalDate(new Date());
                            tempaydis.setDisburseAmount(new BigDecimal(disamount1));
                            tempaydis.setDisburseStatus(disstatus);
                            Date d = new Date();
                            BigDecimal bgyear = new BigDecimal(Calendar.getInstance().get(Calendar.YEAR));
//                            BigDecimal bgyear = new BigDecimal(d.getYear());
                            tempaydis.setDisburseYear(bgyear);
                            tempaydis.setEntryDate(new Date());
                            tempaydis.setTotalAmount(bg1);
                            tempaydis.setBillReceiveCorp(billrecv);
                            tempaydis.setWeekId(new BigDecimal(weekID1));
                            tempaydis.setBillType(billtype1);
                            tempaydis.setPendingAmount(bgpending);
                            tempaydis.setDisburseCategory("Original");
                            tempaydis.setRemarks(remarks);
                            tempaydis.setBillDueDate(sdf.parse(billdue1));
                            Date todaydate = sdf.parse(billedDate);
                            tempaydis.setBillingDate(todaydate);
                            tempaydis.setPoolRrasBal(poolbalance);
                            currentTimestamp = addMilliseconds(currentTimestamp, 1);

                            tempaydis.setEntryTime(currentTimestamp);

                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
                        }//end of uniqueid
                        else {
                            TempPaymentDisbursement tempaydis = new TempPaymentDisbursement();
                            BigDecimal bg1 = new BigDecimal(balamt1);
                            BigDecimal bg2 = new BigDecimal(disamount1);
                            BigDecimal bgpending = new BigDecimal(0);
                            bgpending = bg1.subtract(bg2);
                            pending = bgpending.intValue();
                            System.out.print("pending Bigdfecimal value is :" + pending);
//                            if (pending > 0) {
//                                disstatus = "NOT PAID";
//                            } else {
//                                disstatus = "PAID";
//                            }
                            disstatus = "NOT PAID";
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            billrecv.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            maxid = temppaydisdao.getMaxUDISBURSE_ID();
                            maxid = maxid + 1;
                            BigDecimal bD = new BigDecimal(maxid);
                            tempaydis.setDisburseId(bD);
                            tempaydis.setAdjustedAmount(BigDecimal.ZERO);
                            tempaydis.setCheckerStatus("Pending");
                            tempaydis.setCorporates(corp);
                            tempaydis.setDisbursalDate(new Date());
                            tempaydis.setDisburseAmount(new BigDecimal(0));
                            tempaydis.setDisburseStatus(disstatus);
                            Date d = new Date();
                            BigDecimal bgyear = new BigDecimal(d.getYear());
                            tempaydis.setDisburseYear(bgyear);
                            tempaydis.setEntryDate(new Date());
                            tempaydis.setTotalAmount(new BigDecimal(totalamount1));
                            tempaydis.setBillReceiveCorp(billrecv);
                            tempaydis.setWeekId(new BigDecimal(weekID1));
                            tempaydis.setBillType(billtype1);
                            tempaydis.setPendingAmount(new BigDecimal(balamt1));
                            tempaydis.setDisburseCategory("Original");
                            tempaydis.setRemarks(remarks);
                            tempaydis.setBillDueDate(sdf.parse(billdue1));
                            Date todaydate = sdf.parse(billedDate);
                            tempaydis.setBillingDate(todaydate);
                            temppaydisdao.NewTempPaymentDisbursement(tempaydis);
                        }
                    }//end of checking disbursement or refund
                    else {
                        if (checkedflag == 1) {
                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            int maxrefid = 0;
                            System.out.println("Inside temp Rfudntotref amount ");
                            maxrefid = temprefunddao.getMaxUniqueID();
                            maxrefid = maxrefid + 1;
                            temprefund.setSlno(new BigDecimal(maxrefid));
                            temprefund.setCheckerStatus("Pending");
                            temprefund.setPaidAmount(new BigDecimal(disamount1));
                            poolbalance = poolbalance.subtract(new BigDecimal(disamount1));
                            temprefund.setPendingAmount(new BigDecimal(disamt1));
                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
                            temprefund.setBillPayableCorp(billpaydao);
                            temprefund.setRefundDate(new Date());
                            temprefund.setCorporates(corp);
                            temprefund.setRemarks(remarks);
                            temprefund.setWeekid(new BigDecimal(weekID1));
                            temprefund.setPoolRrasBal(poolbalance);
                            currentTimestamp = addMilliseconds(currentTimestamp, 1);

                            temprefund.setEntryTime(currentTimestamp);

                            temprefunddao.NewTempRefundBillCorp(temprefund);
                        } else {
                            TempRefundBillCorp temprefund = new TempRefundBillCorp();
                            corp.setCorporateId(Integer.parseInt(corpID1));
                            int maxrefid = 0;
                            System.out.println("Inside temp Rfudntotref amount ");
                            maxrefid = temprefunddao.getMaxUniqueID();
                            maxrefid = maxrefid + 1;
                            temprefund.setSlno(new BigDecimal(maxrefid));
                            temprefund.setCheckerStatus("Pending");
                            temprefund.setPaidAmount(new BigDecimal(0));
                            temprefund.setPendingAmount(new BigDecimal(balamt1));
                            billpaydao.setUniqueId(new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            System.out.println("Unique dis :" + new BigDecimal(uniqueID1.substring(1, uniqueID1.length())));
                            temprefund.setTotalAmount(new BigDecimal(totalamount1));
                            temprefund.setBillPayableCorp(billpaydao);
                            temprefund.setRefundDate(new Date());
                            temprefund.setCorporates(corp);
                            temprefund.setRemarks(remarks);
                            temprefund.setWeekid(new BigDecimal(weekID1));
                            temprefunddao.NewTempRefundBillCorp(temprefund);
                        }
                    }
                }//end of checkbox
            }//end of loop
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully submitted the Payment Disbursement for Verification.......");
            return mv1;
        }//end of button
        BillPayableCorpDAO billPayCorpDao = new BillPayableCorpDAO();
        List<BillPayableCorp> listBillPayableCorp = new ArrayList<>();
        List<String> listCorp = new ArrayList<>();
        listBillPayableCorp = billPayCorpDao.getPendingBillCorpNameListtras();
        for (BillPayableCorp temp : listBillPayableCorp) {
            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
            if (ans); else {
                listCorp.add(temp.getCorporates().getCorporateName());
            }
        }
        mv.addObject("corpList", listCorp);
        List<RrasPoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getRrasPoolAccountDetails();
        List<BillReceiveCorp> list456 = new ArrayList<>();
        BillReceiveCorp billrecorp = null;
        List<Object[]> list1 = new ArrayList<Object[]>();
        List<Object[]> list4 = new ArrayList<Object[]>();
        List<Object[]> list7 = new ArrayList<Object[]>();
        List<Object[]> list8 = new ArrayList<Object[]>();
        Corporates corp = null;
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Date> list2 = new ArrayList<Date>();
        List<Date> listpaydates = new ArrayList<Date>();
        List<BigDecimal> list3 = new ArrayList<BigDecimal>();
        List<BigDecimal> listpayweek = new ArrayList<BigDecimal>();
        List<Date> listnew = new ArrayList<Date>();
        BillPayableCorpDAO billpacorpdao = new BillPayableCorpDAO();
        BillReceiveCorpDAO billrecedao = new BillReceiveCorpDAO();
        list1 = billrecedao.getAllPendingDisbursementbyCorpObjecttras();
        list3 = billrecedao.getPendingDisbursementbyCorpgroupbyWeekIDtras();
        listpayweek = billpacorpdao.getRefundPendingDisbursementbyCorpgroupbyWeekIDtras();
        list2 = billrecedao.getAllPendingDisbursementbyCorpgroupbyBillingDateTimestamptras();
        listpaydates = billpacorpdao.getAllRefundDisbursementbyCorpgroupbyBillingDateTimestamptras();
        if (listpayweek != null && !(listpayweek.isEmpty())) {
            for (int n = 0; n < listpayweek.size(); n++) {
                int flag = 0;
                for (int y = 0; y < list3.size(); y++) {
                    if (listpayweek.get(n).compareTo(list3.get(y)) == 0) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    list3.add(listpayweek.get(n));
                }
            }
        }
        for (int i = 0; i < list1.size(); i++) {
            billrecorp = new BillReceiveCorp();
            corp = new Corporates();
            Object[] row = (Object[]) list1.get(i);
            billrecorp.setUniqueId((BigDecimal) row[0]);
            billrecorp.setBillingDate((Date) row[2]);
            billrecorp.setBillType((String) row[1]);
            BigDecimal bg = (BigDecimal) row[3];
            String corpname = corpdao.geCorpNamebyId(bg.intValue());
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName(corpname);
            billrecorp.setCorporates(corp);
            billrecorp.setWeekId((BigDecimal) row[4]);
            billrecorp.setRevisionNo((BigDecimal) row[5]);
            billrecorp.setBillPriority((String) row[6]);
            billrecorp.setPendingAmount((BigDecimal) row[7]);
            billrecorp.setToalnet((BigDecimal) row[8]);
            billrecorp.setRevisedpaybale((BigDecimal) row[9]);
            billrecorp.setRevisedrefund((BigDecimal) row[10]);
            billrecorp.setBillDueDate((Date) row[11]);
            billrecorp.setBillYear((BigDecimal) row[12]);
            list456.add(billrecorp);
        }
        if (list2 != null && !(list2.isEmpty())) {
            for (int m = 0; m < list2.size(); m++) {
                listnew.add(list2.get(m));
            }
        }
        if (listpaydates != null && !(listpaydates.isEmpty())) {
            for (int n = 0; n < listpaydates.size(); n++) {
                int flag = 0;
                for (int y = 0; y < listnew.size(); y++) {
                    if (listpaydates.get(n).equals(listnew.get(y))) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    listnew.add(listpaydates.get(n));
                }
            }
        }
        Collections.sort(listnew);

        String showAll = request.getParameter("showAll");
        if (showAll != null) {
            mv.addObject("warning", "Showing ALL Bill Issuance dates.");
        } else {
            System.out.println("------------------------------------------------------");
            for (Date d : listnew) {
                System.out.println(d);
            }
            System.out.println("------------------------------------------------------");
            while (listnew != null && listnew.size() > 5) {
                listnew.remove(0);
            }

            for (Date d : listnew) {
                System.out.println(d);
            }
            System.out.println("------------------------------------------------------");

            mv.addObject("warning", "Showing LAST 5 Bill Issuance dates.");
        }

        Collections.sort(list3);
        ArrayList list123 = new ArrayList();
        BillPriorityDAO billpridao = new BillPriorityDAO();
        List<BillPriority> listbillpriority = null;
        listbillpriority = billpridao.getBillPriorityDetails();
        for (BillPriority e : listbillpriority) {
            list123.add(e.getPriorityname());
        }
        List<Corporates> listcorp = null;
        listcorp = corpdao.getDefaultCorporateNamebyID();
        List<Object[]> list1000 = new ArrayList<>();
        List<Object[]> list2000 = new ArrayList<>();
        list2000 = billpacorpdao.getPendingReceivableBillsbyallCorptras();
        list1000 = billpacorpdao.getPendingPayableBillsbyallCorptras();
        List<PayDisbursementMapping> listpaymappingPayable = new ArrayList<>();
        List<PayDisbursementMapping> listpaymappingReceivable = new ArrayList<>();
        PayDisbursementMapping paydismapp = null;
        for (Object[] obj : list1000) {
            paydismapp = new PayDisbursementMapping();
            paydismapp.setCorporateName((String) obj[0]);
            paydismapp.setBilltype((String) obj[1]);
            paydismapp.setNetAmount((BigDecimal) obj[2]);
            listpaymappingPayable.add(paydismapp);
        }
        for (Object[] obj : list2000) {
            paydismapp = new PayDisbursementMapping();
            paydismapp.setCorporateName((String) obj[0]);
            paydismapp.setBilltype((String) obj[1]);
            paydismapp.setNetAmount((BigDecimal) obj[2]);
            listpaymappingReceivable.add(paydismapp);
        }
        List<BillPayableCorp> listrefundpayable = null;
        listrefundpayable = billpacorpdao.getAllRefundReceivableBillCorplisttras();
        mv.addObject("listrefundpayable", listrefundpayable);

        System.out.println("############# List aise of refund Payable Weeks List is" + listrefundpayable.size());

        for (int z = 0; z < listrefundpayable.size(); z++) {
            System.out.println("dates iss" + listrefundpayable.get(z).getBillingDate());

        }
        mv.addObject("pendingPayablebilllist", list1000);
        mv.addObject("pendingPayablebilllistMap", listpaymappingPayable);
        mv.addObject("pendingReceivablebilllist", list2000);
        mv.addObject("pendingReceivablebilllistMap", listpaymappingReceivable);
        mv.addObject("corpdefaultList", listcorp);
        mv.addObject("pooldetails", list);
        mv.addObject("disburseList", list456);
        mv.addObject("billdateList", listnew);
        mv.addObject("weekList", list3);
        mv.addObject("priorityList", list123);
        mv.addObject("pendingbilllist", list1000);
        return mv;
    }

//     public ModelAndView viewInterestVerificationbyRLDC(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        HttpSession session1 = request.getSession(false);
//        if (session1 == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/logout.htm");
//            return new ModelAndView(redirectView);
//        }
//        ModelAndView mv = new ModelAndView("viewInterestVerificationbyRLDC");
//        String bName = request.getParameter("bName");
//        List<TempInterestDetails> list = null;
//        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
//        list = tmepindao.getTempInterestDetails("Pending");
//        mv.addObject("interestList", list);
//        List<TempDisbInterestDetails> list1=null;
//         TempDisbInterestDetailsDAO tempdisintedao=new TempDisbInterestDetailsDAO();
//         list1=tempdisintedao.getTempDisbInterestDetails("Pending");
//         
//        
////        PaymentDisbursementDAO paymedisdao=new PaymentDisbursementDAO();
////        List<PaymentDisbursement> list1=null;
////        list1=paymedisdao.getDisbursementDetailsforInterest();
//        mv.addObject("disbursedList", list1);
//        
//        return mv;
//    }
    public ModelAndView viewInterestVerificationbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewInterestVerificationbyRLDC");
        String bName = request.getParameter("bName");
        List<TempInterestDetails> list = null;
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        list = tmepindao.getTempInterestDetails("Pending");
        mv.addObject("interestList", list);
        List<TempDisbInterestDetails> list1 = null;
        TempDisbInterestDetailsDAO tempdisintedao = new TempDisbInterestDetailsDAO();
        list1 = tempdisintedao.getTempDisbInterestDetails("Pending");

//        PaymentDisbursementDAO paymedisdao=new PaymentDisbursementDAO();
//        List<PaymentDisbursement> list1=null;
//        list1=paymedisdao.getDisbursementDetailsforInterest();
        mv.addObject("disbursedList", list1);
        return mv;
    }

    public ModelAndView viewCheckerInterestDisburseCorporateList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        ModelAndView mv = new ModelAndView("viewCheckerInterestDisburseCorporateList");
        DisbursedInterestDetailsDAO tempdisinetedao = new DisbursedInterestDetailsDAO();
        List<TempDisbInterestDetails> list = null;
        List<Object[]> list1 = null;
        List<Corporates> listcorp = new ArrayList<>();
        list1 = tempdisinetedao.getAllInterestReceivableBillCorpObjectlistforCheacker();
        Corporates corp = null;
        for (Object[] obj : list1) {
            corp = new Corporates();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            listcorp.add(corp);
        }
        mv.addObject("disInterestDisList", listcorp);
        return mv;
    }

    public ModelAndView submitInterestReceivableDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        String rowcount = request.getParameter("rowcount");
        String billedDate = request.getParameter("billedDate");
        String maininterestpoolbal = request.getParameter("maininterestpoolbal");
        BigDecimal intpoolbal = new BigDecimal(maininterestpoolbal);
        BigDecimal bgPaid = new BigDecimal(0);
        int checkedflag = 0;
                            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        for (int i = 1; i <= Integer.parseInt(rowcount); i++) {
            String uniqueID = request.getParameter("uniqueID" + i);
            String billdate1 = request.getParameter("billdate" + i);

            if (billdate1.equals(billedDate)) {

                String[] chkSms = request.getParameterValues("items");
                System.out.print("chkSms length is :" + chkSms.length);
                checkedflag = 0;
                for (int j = 0; j < chkSms.length; j++) {
                    int uni = Integer.parseInt(uniqueID);
                    int cheno = Integer.parseInt(chkSms[j].toString());
                    if (uni == cheno) {
                        checkedflag = 1;
                        bgPaid = new BigDecimal(0);
                        bgPaid = disinterestdao.getInterestPaidAmountDisbursedInterestDetailsbyDisInterestID(Integer.parseInt(uniqueID));
                        System.out.print("chkSms length is :" + chkSms.length);
                    }
                }

                String mapped = request.getParameter("mapped" + i);
                String remarks = request.getParameter("remarks" + i);
                String balamt = request.getParameter("balamt" + i);
                BigDecimal bgbalamt = new BigDecimal(balamt);

                System.out.println("######### mapped is" + mapped);
                System.out.println("######### remarks is" + remarks);
                System.out.println("$$$$$$$$$$ bgPaid is " + bgPaid);
                if (bgPaid == null) {
                    bgPaid = new BigDecimal(0);
                    System.out.println("$$$$$$$$$$making 0 bgPaid is " + bgPaid);
                }
                bgPaid = bgPaid.add(new BigDecimal(mapped));
                String pending = request.getParameter("pending" + i);
                BigDecimal bgPend = new BigDecimal(pending);
                System.out.print("pending Bigdfecimal value is :" + pending);
                Date d = new Date();
                BigDecimal bgyear = new BigDecimal(d.getYear());
                if (checkedflag == 1) {
                    System.out.println("$$$$$$$$$$ uniqueID is " + uniqueID);
                    System.out.print("chkSms is :" + chkSms[0].toString());
                    disinterestdao.getUpdateDisburseInterestbyId(Integer.parseInt(uniqueID), bgPaid, bgPend);
                }
                if (checkedflag == 1) {
                    PaymentInterestDisbursementDAO payintdisdao = new PaymentInterestDisbursementDAO();
                    DisbursedInterestDetailsDAO disintdetdao = new DisbursedInterestDetailsDAO();
                    PaymentInterestDisbursement payintdis = new PaymentInterestDisbursement();
                    DisbursedInterestDetails disintdet = new DisbursedInterestDetails();

                    List<DisbursedInterestDetails> disintdetlist = disintdetdao.getDisbursedInterestDetailsListByinterestId(new BigDecimal(uniqueID));

                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(payintdisdao.getMaxUDISBURSE_ID());
                    slno = slno.add(BigDecimal.ONE);
                    payintdis.setSlno(slno);
                    payintdis.setInterestAmount(bgbalamt);
                    payintdis.setInterestPaidamount(new BigDecimal(mapped));

                    intpoolbal = intpoolbal.subtract(new BigDecimal(mapped));

                    if (disintdetlist.get(0).getBillType().equalsIgnoreCase("SRAS")) {
                        payintdis.setIntAgcBal(intpoolbal);

                    } else if (disintdetlist.get(0).getBillType().equalsIgnoreCase("TRASM")
                            || disintdetlist.get(0).getBillType().equalsIgnoreCase("TRASS")
                            || disintdetlist.get(0).getBillType().equalsIgnoreCase("TRASE")) {

                        payintdis.setIntRrasBal(intpoolbal);

                    } else {
                        payintdis.setIntPoolBal(intpoolbal);

                    }

                    payintdis.setInterestPendingamount(bgPend);
                    payintdis.setCheckerStatus("Pending");
                    payintdis.setEntryDate(d);
                    payintdis.setInterestPaiddate(d);
                    payintdis.setRemarks(remarks);
                    disintdet.setInterestId(new BigDecimal(uniqueID));
                    payintdis.setDisbursedInterestDetails(disintdet);
                    currentTimestamp = addMilliseconds(currentTimestamp, 1);

                    payintdis.setEntryTime(currentTimestamp);

                    payintdisdao.NewPaymentInterestDisbursement(payintdis);
                } else {
                    PaymentInterestDisbursementDAO payintdisdao = new PaymentInterestDisbursementDAO();
                    PaymentInterestDisbursement payintdis = new PaymentInterestDisbursement();
                    DisbursedInterestDetails disintdet = new DisbursedInterestDetails();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(payintdisdao.getMaxUDISBURSE_ID());
                    slno = slno.add(BigDecimal.ONE);
                    payintdis.setSlno(slno);
                    payintdis.setInterestAmount(bgbalamt);
                    payintdis.setInterestPaidamount(new BigDecimal(0));
                    payintdis.setInterestPendingamount(bgbalamt);
                    payintdis.setCheckerStatus("Pending");
                    payintdis.setEntryDate(d);
                    payintdis.setInterestPaiddate(d);
                    payintdis.setRemarks(remarks);
                    disintdet.setInterestId(new BigDecimal(uniqueID));
                    payintdis.setDisbursedInterestDetails(disintdet);

                    payintdisdao.NewPaymentInterestDisbursement(payintdis);
                }
                //end of uniqueid

            }//end of dateequals
        }//end of loop
        ModelAndView mv1 = new ModelAndView("successMsg");
        mv1.addObject("Msg", "Succesfully submited for Checker");
        return mv1;
    }

    public ModelAndView viewPendingInterestReceivableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<DisbursedInterestDetails> list = null;
        list = disinterestdao.getPendingDisbursedInterestDetailsList();
        ModelAndView mv1 = new ModelAndView("viewPendingInterestReceivableList");
        mv1.addObject("pendRecvInterestList", list);
        return mv1;
    }

    public ModelAndView viewPendingRefundReceivableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        BillReceiveCorpDAO billrecvcorpdao = new BillReceiveCorpDAO();
        List<BillReceiveCorp> list = null;
        list = billrecvcorpdao.getPendingRefundBillReceiveList();
        ModelAndView mv1 = new ModelAndView("viewPendingRefundReceivableList");
        mv1.addObject("pendRecvRefundList", list);
        return mv1;
    }

    public ModelAndView submitPSDFInterestReceivableDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        CsdfDetails csdfde = new CsdfDetails();
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> list = null;
        list = csdfdao.getCsdfDetails("Interest");
        if (list != null && list.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Already Record pending with checker...Please clear it");
            return mv2;

        } else {
            int maxid = 0;
            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
            maxid = csdfdao.getMaxSlno().intValue();
            maxid = maxid + 1;
            csdfde.setSlno(new BigDecimal(maxid));
            String csdfmonth = request.getParameter("csdfmonth");
            String csdfamt = request.getParameter("csdfamt");
            String mainbal = request.getParameter("maininterestpoolbal");
            String csdfremarks = request.getParameter("csdfremarks");
            String csdfYear = request.getParameter("csdfYear");
            String biltype = request.getParameter("biltype");

            BigDecimal bgcsdf = new BigDecimal(csdfamt);
            BigDecimal poolbal = new BigDecimal(mainbal);
            csdfde.setCheckerStatus("Pending");
            csdfde.setCsdfAmount(bgcsdf);
            csdfde.setEntryDate(new Date());

            if (biltype.equalsIgnoreCase("SRAS")) {
                csdfde.setPoolAgcBal(poolbal);

            } else if (biltype.equalsIgnoreCase("TRAS")) {
                csdfde.setPoolRrasBal(poolbal);

            } else {
                csdfde.setMainPoolBalance(poolbal);

            }

            csdfde.setRemarks(csdfremarks);
            csdfde.setAmtCategory("Interest");
            csdfde.setCsdfMonth(csdfmonth);

            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            csdfde.setEntryTime(currentTimestamp);
            csdfde.setCsdfYear(new BigDecimal(csdfYear));
            csdfdao.NewCsdfDetails(csdfde);
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "CSDF Details has Sucessfully submited for Checker");
            return mv2;

        }
    }

    public ModelAndView viewCheckerCSDF(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv1 = new ModelAndView("viewCheckerCSDF");
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        List<CsdfDetails> list = null;
        List<CsdfDetails> list1 = null;
        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
        String bconfirm = request.getParameter("bconfirm");
        if (bconfirm != null) {
            String count = request.getParameter("count");
            for (int i = 1; i <= Integer.parseInt(count); i++) {
                String category = request.getParameter("category" + i);
                if (category.equalsIgnoreCase("Bills")) {
                    list = csdfdao.getCsdfDetails("Bills");
                    System.out.println("viewCheckerCSDF list is " + list);
                    csdfdao.getUpdateCsdfDetailsbyChecker("Bills");
//                    BigDecimal mainpool = list.get(0).getMainPoolBalance();
//                    BigDecimal csdfamt = list.get(0).getCsdfAmount();
//                    csdfamt = mainpool.subtract(csdfamt);
//                    pooldao.getUpdatePoolAccountbyChecker(csdfamt);
                    if (list.get(0).getMainPoolBalance() != null) {
                        BigDecimal mainpool = list.get(0).getMainPoolBalance();
                        BigDecimal csdfamt = list.get(0).getCsdfAmount();
                        csdfamt = mainpool.subtract(csdfamt);
                        pooldao.getUpdatePoolAccountbyChecker(csdfamt);
                    }

                    if (list.get(0).getPoolAgcBal() != null) {
                        BigDecimal mainpool = list.get(0).getPoolAgcBal();
                        BigDecimal csdfamt = list.get(0).getCsdfAmount();
                        csdfamt = mainpool.subtract(csdfamt);
                        pooldao.getUpdateAgcPoolAccountbyChecker(csdfamt);
                    }
                    if (list.get(0).getPoolRrasBal() != null) {
                        BigDecimal mainpool = list.get(0).getPoolRrasBal();
                        BigDecimal csdfamt = list.get(0).getCsdfAmount();
                        csdfamt = mainpool.subtract(csdfamt);
                        pooldao.getUpdateRrasPoolAccountbyChecker(csdfamt);
                    }
                }
                if (category.equalsIgnoreCase("Interest")) {
                    list = csdfdao.getCsdfDetails("Interest");
                    csdfdao.getUpdateCsdfDetailsbyChecker("Interest");

                    if (list.get(0).getMainPoolBalance() != null) {
                        BigDecimal mainpool = list.get(0).getMainPoolBalance();
                        BigDecimal csdfamt = list.get(0).getCsdfAmount();
                        csdfamt = mainpool.subtract(csdfamt);
                        intepooldao.getUpdateInterestPoolAccountbyChecker(csdfamt);
                    }
                    if (list.get(0).getPoolAgcBal() != null) {
                        BigDecimal mainpool = list.get(0).getPoolAgcBal();
                        BigDecimal csdfamt = list.get(0).getCsdfAmount();
                        csdfamt = mainpool.subtract(csdfamt);
                        intepooldao.getUpdateInterestPoolAccountbyCheckeragc(csdfamt);
                    }
                    if (list.get(0).getPoolRrasBal() != null) {
                        BigDecimal mainpool = list.get(0).getPoolRrasBal();
                        BigDecimal csdfamt = list.get(0).getCsdfAmount();
                        csdfamt = mainpool.subtract(csdfamt);
                        intepooldao.getUpdateInterestPoolAccountbyCheckerrras(csdfamt);
                    }
//                    BigDecimal mainpool = list.get(0).getMainPoolBalance();
//                    BigDecimal csdfamt = list.get(0).getCsdfAmount();
//                    csdfamt = mainpool.subtract(csdfamt);
//                    intepooldao.getUpdateInterestPoolAccountbyChecker(csdfamt);
                }
            }
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Successfully Verified the PSDF records");
            return mv2;
        }
        String bcancel = request.getParameter("bcancel");
        if (bcancel != null) {

//            list = csdfdao.getAllCsdfDetails();
//            BigDecimal mainpool = list.get(0).getMainPoolBalance();
//            BigDecimal csdfamt = list.get(0).getCsdfAmount();
//            csdfamt = mainpool.add(csdfamt);
//            intepooldao.getUpdateInterestPoolAccountbyChecker(csdfamt);
            csdfdao.getDeletedCsdfDetailsbyChecker();
            ModelAndView mv3 = new ModelAndView("successMsg");
            mv3.addObject("Msg", "Successfully Deleted the records");
            return mv3;
        }
        list = csdfdao.getAllCsdfDetails();
        mv1.addObject("csdfList", list);

        return mv1;
    }

    public ModelAndView viewCheckerInterestReceivableDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        String bconfirm = request.getParameter("bconfirm");
        String bdelete = request.getParameter("bdelete");
        if (bconfirm != null) {
            InterestPoolAccountDetailsDAO intepooldisdao = new InterestPoolAccountDetailsDAO();
            PaymentInterestDisbursementDAO payintdisdao = new PaymentInterestDisbursementDAO();
            List<InterestPoolAccountDetails> listInpool = null;
            List<AgcInterestPool> listInpoolagc = null;
            List<RrasInterestPool> listInpoolrras = null;
            List<PaymentInterestDisbursement> list = null;
            List<PaymentInterestDisbursement> listintdis = null;
            list = disinterestdao.getDisbursedInterestDetailsbyCorpforChecker();
            listintdis = payintdisdao.getPaymentInterestDisbursedforChecker();
            String count = request.getParameter("count");
            for (int i = 1; i <= Integer.parseInt(count); i++) {
                String interestId = request.getParameter("interestId" + i);
                disinterestdao.getUpdateStatusDisburseInterestbyId("Pending", Integer.parseInt(interestId));
            }
            listInpool = intepooldisdao.getInterestPoolAccountDetails();
            listInpoolagc = intepooldisdao.getInterestPoolAccountDetailsagc();
            listInpoolrras = intepooldisdao.getInterestPoolAccountDetailsrras();
            BigDecimal totlInterestdisburs = new BigDecimal(0);
            BigDecimal totlInterestdisbursagc = new BigDecimal(0);
            BigDecimal totlInterestdisbursrras = new BigDecimal(0);
            for (PaymentInterestDisbursement e : listintdis) {
                BigDecimal bg = e.getInterestPaidamount();

                if (e.getDisbursedInterestDetails().getBillType().equalsIgnoreCase("sras")) {
                    totlInterestdisbursagc = totlInterestdisbursagc.add(bg);
                    BigDecimal mainpool = listInpoolagc.get(0).getMainBalance();
                    System.out.println("mainpool sras =" + mainpool);
                    mainpool = mainpool.subtract(totlInterestdisbursagc);
                    System.out.println("mainpool sras =" + mainpool);
                    System.out.println("totlInterestdisbursagc sras =" + totlInterestdisbursagc);

                    intepooldisdao.getUpdateInterestPoolAccountbyCheckeragc(mainpool);
                } else if (e.getDisbursedInterestDetails().getBillType().equalsIgnoreCase("trasm")
                        || e.getDisbursedInterestDetails().getBillType().equalsIgnoreCase("trass")
                        || e.getDisbursedInterestDetails().getBillType().equalsIgnoreCase("trase")) {
                    totlInterestdisbursrras = totlInterestdisbursrras.add(bg);
                    BigDecimal mainpool = listInpoolrras.get(0).getMainBalance();
                    mainpool = mainpool.subtract(totlInterestdisbursrras);
                    System.out.println("mainpool tras =" + mainpool);
                    System.out.println("totlInterestdisbursrras sras =" + totlInterestdisbursrras);
                    intepooldisdao.getUpdateInterestPoolAccountbyCheckerrras(mainpool);
                } else {
                    totlInterestdisburs = totlInterestdisburs.add(bg);
                    BigDecimal mainpool = listInpool.get(0).getMainBalance();
                    mainpool = mainpool.subtract(totlInterestdisburs);
                    System.out.println("mainpool dsm =" + mainpool);
                    System.out.println("totlInterestdisburs sras =" + totlInterestdisburs);
                    intepooldisdao.getUpdateInterestPoolAccountbyChecker(mainpool);
                }
            }
            payintdisdao.getUpdateStatusDisburseInterestbyId("Pending");
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Successfully Verfiied");
            return mv;
        }

        if (bdelete != null) {

            PaymentInterestDisbursementDAO payintdisdao = new PaymentInterestDisbursementDAO();
            List<PaymentInterestDisbursement> listintdis = null;
            listintdis = payintdisdao.getPaymentInterestDisbursedforChecker();
            BigDecimal interestamount = new BigDecimal(0);
            BigDecimal interestpending = new BigDecimal(0);
            for (PaymentInterestDisbursement e : listintdis) {
                BigDecimal bg = e.getInterestPaidamount();
                if (e.getDisbursedInterestDetails().getInterestPaidamount() != null) {
                    interestamount = e.getDisbursedInterestDetails().getInterestPaidamount();
                    interestamount = interestamount.subtract(bg);
                }
                interestpending = e.getDisbursedInterestDetails().getInterestPendingamount();
                interestpending = interestpending.add(bg);
                disinterestdao.getUpdateDisburseInterestbyId(e.getDisbursedInterestDetails().getInterestId().intValue(), interestamount, interestpending);
                payintdisdao.getDeletedPaymentInterestDisbursementbySLNO(e.getSlno().intValue());

            }
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully Deleted the Interest Mapping");
            return mv1;
        }
        ModelAndView mv = new ModelAndView("viewCheckerInterestReceivableDetails");
        List<PaymentInterestDisbursement> list = null;
        list = disinterestdao.getDisbursedInterestDetailsbyCorpforChecker();
        mv.addObject("disInterestDisList", list);
        return mv;
    }

    public ModelAndView viewBillInterestReceivableDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<InterestPoolAccountDetails> listinterestpool = null;
        List<DisbursedInterestDetails> list1 = null;
        List<PaymentInterestDisbursement> listcheck = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        listcheck = disinterestdao.getDisbursedInterestDetailsbyCorpforChecker();
        if (listcheck != null && listcheck.size() > 0) {

            List<String> pendingAtCheckerCorps = new ArrayList();

            for (PaymentInterestDisbursement l : listcheck) {
                if (l.getInterestPaidamount().compareTo(BigDecimal.ZERO) == 1) {
                    if (!pendingAtCheckerCorps.contains(l.getDisbursedInterestDetails().getCorporates().getCorporateName())) {
                        pendingAtCheckerCorps.add(l.getDisbursedInterestDetails().getCorporates().getCorporateName());
                    }
                }
            }

            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pending in Checker");
            mv2.addObject("pendingAtCheckerCorps", pendingAtCheckerCorps);
            mv2.addObject("flag", "999");
            return mv2;
        }
        List<MiscDisbursement> listmiscdis = null;
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        listmiscdis = miscdao.getmiscDetails("Interest");
        System.out.print("Inside misc button" + listmiscdis.size());
        if (listmiscdis != null && listmiscdis.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Already Misc Interest Disbursement Record pending with checker...Please clear it");
            return mv2;

        }
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempPendingMappingBillBankbyCorpListbyChecker("Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getTempRefundPendingbyCorpListbychecker("Pending");
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

//        else {
        List<Object[]> list2 = null;
        list1 = disinterestdao.getDisbursedInterestDetailsbyCorp();
//            if (list1 != null && list1.size() > 0) {
        list2 = disinterestdao.getDisbursedInterestDetailsGroupbyBillingdate();
        List<PoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
        listinterestpool = intepooldao.getInterestPoolAccountDetails();
        BigDecimal interestpoolbal = listinterestpool.get(0).getMainBalance();
        ModelAndView mv1 = new ModelAndView("viewBillInterestReceivableDetails");
        list = pooldao.getPoolAccountDetails();
        mv1.addObject("pooldetails", list);
        mv1.addObject("refundDetails", list1);
        mv1.addObject("InterestDisDates", list2);
        mv1.addObject("interestpoolbal", interestpoolbal);
        return mv1;
//            } 
//            else {
//                ModelAndView mv1 = new ModelAndView("successMsg");
//                mv1.addObject("Msg", "No Records found for Interest !!");
//                return mv1;
//            }
//        }
    }

    public ModelAndView viewBillInterestReceivableDetailssras(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null || session1.getAttribute("loginid") == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<AgcInterestPool> listinterestpool = null;
        List<DisbursedInterestDetails> list1 = null;
        List<PaymentInterestDisbursement> listcheck = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        listcheck = disinterestdao.getDisbursedInterestDetailsbyCorpforChecker();
        if (listcheck != null && listcheck.size() > 0) {

            List<String> pendingAtCheckerCorps = new ArrayList();

            for (PaymentInterestDisbursement l : listcheck) {
                if (l.getInterestPaidamount().compareTo(BigDecimal.ZERO) == 1) {
                    if (!pendingAtCheckerCorps.contains(l.getDisbursedInterestDetails().getCorporates().getCorporateName())) {
                        pendingAtCheckerCorps.add(l.getDisbursedInterestDetails().getCorporates().getCorporateName());
                    }
                }
            }

            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pending in Checker");
            mv2.addObject("pendingAtCheckerCorps", pendingAtCheckerCorps);
            mv2.addObject("flag", "999");
            return mv2;
        }
        List<MiscDisbursement> listmiscdis = null;
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        listmiscdis = miscdao.getmiscDetails("Interest");
        System.out.print("Inside misc button" + listmiscdis.size());
        if (listmiscdis != null && listmiscdis.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Already Misc Interest Disbursement Record pending with checker...Please clear it");
            return mv2;

        }
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempPendingMappingBillBankbyCorpListbyChecker("Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getTempRefundPendingbyCorpListbychecker("Pending");
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

//        else {
        List<Object[]> list2 = null;
        list1 = disinterestdao.getDisbursedInterestDetailsbyCorpbytype("SRAS");
//            if (list1 != null && list1.size() > 0) {
        list2 = disinterestdao.getDisbursedInterestDetailsGroupbyBillingdatebytype("SRAS");
        List<AgcPoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
        listinterestpool = intepooldao.getInterestPoolAccountDetailsagc();
        BigDecimal interestpoolbal = listinterestpool.get(0).getMainBalance();
        ModelAndView mv1 = new ModelAndView("viewBillInterestReceivableDetails");
        list = pooldao.getAgcPoolAccountDetails();
        mv1.addObject("pooldetails", list);
        mv1.addObject("refundDetails", list1);
        mv1.addObject("InterestDisDates", list2);
        mv1.addObject("interestpoolbal", interestpoolbal);
        mv1.addObject("type", "SRAS");

        return mv1;
//            } 
//            else {
//                ModelAndView mv1 = new ModelAndView("successMsg");
//                mv1.addObject("Msg", "No Records found for Interest !!");
//                return mv1;
//            }
//        }
    }

    public ModelAndView viewBillInterestReceivableDetailstras(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null || session1.getAttribute("loginid") == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<RrasInterestPool> listinterestpool = null;
        List<DisbursedInterestDetails> list1 = null;
        List<PaymentInterestDisbursement> listcheck = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        listcheck = disinterestdao.getDisbursedInterestDetailsbyCorpforChecker();
        if (listcheck != null && listcheck.size() > 0) {

            List<String> pendingAtCheckerCorps = new ArrayList();

            for (PaymentInterestDisbursement l : listcheck) {
                if (l.getInterestPaidamount().compareTo(BigDecimal.ZERO) == 1) {
                    if (!pendingAtCheckerCorps.contains(l.getDisbursedInterestDetails().getCorporates().getCorporateName())) {
                        pendingAtCheckerCorps.add(l.getDisbursedInterestDetails().getCorporates().getCorporateName());
                    }
                }
            }

            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pending in Checker");
            mv2.addObject("pendingAtCheckerCorps", pendingAtCheckerCorps);
            mv2.addObject("flag", "999");
            return mv2;
        }
        List<MiscDisbursement> listmiscdis = null;
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        listmiscdis = miscdao.getmiscDetails("Interest");
        System.out.print("Inside misc button" + listmiscdis.size());
        if (listmiscdis != null && listmiscdis.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Already Misc Interest Disbursement Record pending with checker...Please clear it");
            return mv2;

        }
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempPendingMappingBillBankbyCorpListbyChecker("Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getTempRefundPendingbyCorpListbychecker("Pending");
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

//        else {
        List<Object[]> list2 = null;
//        list1 = disinterestdao.getDisbursedInterestDetailsbyCorpbytype("TRAS");
        list1 = disinterestdao.getDisbursedInterestDetailsbyCorpbytypetras();

//            if (list1 != null && list1.size() > 0) {
//        list2 = disinterestdao.getDisbursedInterestDetailsGroupbyBillingdatebytype("TRAS");
        list2 = disinterestdao.getDisbursedInterestDetailsGroupbyBillingdatebytypetras();

        List<RrasPoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
        listinterestpool = intepooldao.getInterestPoolAccountDetailsrras();
        BigDecimal interestpoolbal = listinterestpool.get(0).getMainBalance();
        ModelAndView mv1 = new ModelAndView("viewBillInterestReceivableDetails");
        list = pooldao.getRrasPoolAccountDetails();
        mv1.addObject("pooldetails", list);
        mv1.addObject("refundDetails", list1);
        mv1.addObject("InterestDisDates", list2);
        mv1.addObject("interestpoolbal", interestpoolbal);
        mv1.addObject("type", "TRAS");

        return mv1;
//            } 
//            else {
//                ModelAndView mv1 = new ModelAndView("successMsg");
//                mv1.addObject("Msg", "No Records found for Interest !!");
//                return mv1;
//            }
//        }
    }

    public ModelAndView newPaymentDisbursedbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("newPaymentDisbursedbyRLDC");
        String bName = request.getParameter("bName");
        if (bName != null) {
            System.out.print("Inside bname button viewdisbursed");
            List<PaymentDisbursement> list1 = null;
            List<PaymentDisbursement> list2 = null;
            PaymentDisbursementDAO paydisdao = new PaymentDisbursementDAO();
            DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
            List<PaymentInterestDisbursement> list = null;
            List<PaymentInterestDisbursement> listremrk = null;
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            List<CsdfDetails> listpsdf = null;
            TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
            List<TempRefundBillCorp> listtemprefund = null;
            List<TempRefundBillCorp> listtemprefundremrk = null;
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            String reporttype = request.getParameter("reporttype");
            Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(startdate);
            Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(enddate);
            if (reporttype.equalsIgnoreCase("Bills")) {
                ModelAndView mv1 = new ModelAndView("viewPaymentDisbursedbyRLDC");
                list1 = paydisdao.getDisbursementDetailsbyFromDateTodate(date1, date2);
                list2 = paydisdao.getRemarksDisbursementDetailsbyFromDateTodate(date1, date2);
                mv1.addObject("paidList", list1);
                mv1.addObject("remarksList", list2);
                return mv1;
            }
            if (reporttype.equalsIgnoreCase("Interest")) {

                list = disinterestdao.getDisbursedInterestDetailsbyCorpforExport(date1, date2);
                listremrk = disinterestdao.getremarkDisbursedInterestDetailsbyCorpforExport(date1, date2);
                ModelAndView mv1 = new ModelAndView("viewInterestPaymentDisbursedbyRLDC");
                mv1.addObject("interestDisburseList", list);
                mv1.addObject("interestDisburseListremark", listremrk);
                return mv1;
            }
            if (reporttype.equalsIgnoreCase("Refund")) {

                listtemprefund = temprefunddao.getRefundBillPayCorporExport(date1, date2);
                listtemprefundremrk = temprefunddao.getremarkRefundBillPayCorporExport(date1, date2);
                ModelAndView mv1 = new ModelAndView("viewRefundPaymentDisbursedbyRLDC");
                mv1.addObject("refundDisburseList", listtemprefund);
                mv1.addObject("refundDisburseListremark", listtemprefundremrk);
                return mv1;
            }
            if (reporttype.equalsIgnoreCase("PSDF")) {

                listpsdf = csdfdao.getPSDFBillCorpbyforExport(date1, date2);
                ModelAndView mv1 = new ModelAndView("viewPSDFPaymentDisbursedbyRLDC");
                mv1.addObject("psdfDisburseList", listpsdf);
                return mv1;
            }

            if (reporttype.equalsIgnoreCase("All")) {

                ModelAndView mv1 = new ModelAndView("viewAllPaymentDisbursedbyRLDC");
                list1 = paydisdao.getDisbursementDetailsbyFromDateTodate(date1, date2);
                list2 = paydisdao.getRemarksDisbursementDetailsbyFromDateTodate(date1, date2);
                listpsdf = csdfdao.getPSDFBillCorpbyforExport(date1, date2);
                listtemprefund = temprefunddao.getRefundBillPayCorporExport(date1, date2);
                listtemprefundremrk = temprefunddao.getremarkRefundBillPayCorporExport(date1, date2);
                list = disinterestdao.getDisbursedInterestDetailsbyCorpforExport(date1, date2);
                listremrk = disinterestdao.getremarkDisbursedInterestDetailsbyCorpforExport(date1, date2);

                mv1.addObject("interestDisburseList", list);
                mv1.addObject("interestDisburseListremark", listremrk);
                mv1.addObject("refundDisburseList", listtemprefund);
                mv1.addObject("refundDisburseListremark", listtemprefundremrk);
                mv1.addObject("psdfDisburseList", listpsdf);
                mv1.addObject("paidList", list1);
                mv1.addObject("remarksList", list2);

                return mv1;
            }
        }
        return mv;
    }

    public ModelAndView viewPendingMakerDisbursement(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        String bdelete = request.getParameter("bdelete");
        if (bdelete != null) {
            tempdisdao.deleteTempDisburse();
            temprefunddao.deleteTempRefundDisburse();
            ModelAndView mv = new ModelAndView("viewPendingMakerDisbursement");
            mv.addObject("Msg", "Successfully deleted the payment disbursement details.....");
            return mv;
        }
        ModelAndView mv = new ModelAndView("viewPendingMakerDisbursement");
        List<TempPaymentDisbursement> list = null;
        list = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        List<TempRefundBillCorp> listrefund = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        mv.addObject("pendingList", list);
        mv.addObject("refundPendList", listrefund);
        return mv;
    }

    public ModelAndView corporaterefund(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }

        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<PaymentInterestDisbursement> listcheck = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");

        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }
        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        ModelAndView mav = new ModelAndView("corporaterefund");
        List<PoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getPoolAccountDetails();
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");
        if (csdfSubmit != null) {
            MiscDisbursement csdfde = new MiscDisbursement();
            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = miscdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String corparateID = request.getParameter("corparateID");
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                String corpname = corpdao.geCorpNamebyId(Integer.parseInt(corparateID));
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setMainPoolBalance(poolbal);
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {
                    if (!(dsndetlist.get(0).getBankStatement().getCorporates().getCorporateName().equals(corpname))) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to" + dsndetlist.get(0).getBankStatement().getCorporates().getCorporateName() + ", but you selected " + corpname + "...Please check and retry");
                        return mv2;

                    }
                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }

                    csdfde.setStmtId(dsndetlist.get(0).getBankStatement().getStmtId());

                } else {
                    csdfde.setStmtId(new BigDecimal(-1));

                }
                csdfde.setAmtCategory("Bills");
                csdfde.setCorpId(new BigDecimal(corparateID));
                csdfde.setCorpName(corpname);
                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(currentTimestamp);

                miscdao.NewMiscDisbursement(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Misc Pool Refund Details has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", list);
        mav.addObject("corporateList", corporateList);
        mav.addObject("doc_sets", doc_sets);

        return mav;
    }

    public ModelAndView interestrefund(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }

        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<InterestPoolAccountDetails> listinterestpool = null;
        List<DisbursedInterestDetails> list1 = null;
        List<PaymentInterestDisbursement> listcheck = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listpsdf = csdfdao.getCsdfDetails("Interest");

        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListofallcorpforvalidations();
        if (adjpatlist != null && adjpatlist.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }

        listcheck = disinterestdao.getDisbursedInterestDetailsbyCorpforChecker();
        if (listcheck != null && listcheck.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pending in Interest bill disbursement Checker");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
        listinterestpool = intepooldao.getInterestPoolAccountDetails();
//        BigDecimal interestpoolbal = listinterestpool.get(0).getMainBalance();

        ModelAndView mav = new ModelAndView("interestrefund");

        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");
        if (csdfSubmit != null) {
            MiscDisbursement csdfde = new MiscDisbursement();
            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Interest");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Interest Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = miscdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String corparateID = request.getParameter("corparateID");
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                String corpname = corpdao.geCorpNamebyId(Integer.parseInt(corparateID));
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setMainPoolBalance(poolbal);
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {
                    if (!(dsndetlist.get(0).getBankStatement().getCorporates().getCorporateName().equals(corpname))) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to" + dsndetlist.get(0).getBankStatement().getCorporates().getCorporateName() + ", but you selected " + corpname + "...Please check and retry");
                        return mv2;

                    }
                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }
                    csdfde.setStmtId(dsndetlist.get(0).getBankStatement().getStmtId());

                } else {
                    csdfde.setStmtId(new BigDecimal(-1));

                }
                csdfde.setAmtCategory("Interest");
                csdfde.setCorpId(new BigDecimal(corparateID));
                csdfde.setCorpName(corpname);
                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(currentTimestamp);

                miscdao.NewMiscDisbursement(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Misc Interest Refund Details has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", listinterestpool);
        mav.addObject("corporateList", corporateList);
        mav.addObject("doc_sets", doc_sets);

        return mav;
    }

    public ModelAndView pooltoint(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }

        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<PaymentInterestDisbursement> listcheck = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        ModelAndView mav = new ModelAndView("pooltointrefund");
        List<PoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getPoolAccountDetails();
        InterestPoolAccountDetailsDAO ipdao = new InterestPoolAccountDetailsDAO();
        List<InterestPoolAccountDetails> list2 = null;
        list2 = ipdao.getInterestPoolAccountDetails();
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");

        if (csdfSubmit != null) {
            PoolToInt csdfde = new PoolToInt();
            PoolToIntDAO pooltointdao = new PoolToIntDAO();

            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = pooltointdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String intbal = request.getParameter("intbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                BigDecimal intball = new BigDecimal(intbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setMainPoolBalance(poolbal.subtract(bgcsdf));
                csdfde.setIntPoolBalance(list2.get(0).getMainBalance().add(bgcsdf));
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {

                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }
                }

                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                //Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(new Date());

                pooltointdao.NewPoolToInt(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Pool transfer to Interest has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", list);
        mav.addObject("intbal", list2.get(0).getMainBalance());
        mav.addObject("corporateList", corporateList);
        mav.addObject("Intflagset", 111);
        mav.addObject("doc_sets", doc_sets);

        return mav;
    }

    public ModelAndView dsmtosras(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }

        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<PaymentInterestDisbursement> listcheck = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        ModelAndView mav = new ModelAndView("dsmtosras");
        List<PoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getPoolAccountDetails();
        List<AgcPoolAccountDetails> list2 = null;
        list2 = pooldao.getAgcPoolAccountDetails();
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");

        if (csdfSubmit != null) {
            PoolToPool csdfde = new PoolToPool();
            PoolToPoolDAO pooltointdao = new PoolToPoolDAO();

            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = pooltointdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String intbal = request.getParameter("intbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                BigDecimal intball = new BigDecimal(intbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setMainPoolBalance(poolbal.subtract(bgcsdf));
                csdfde.setPoolAgcBal(list2.get(0).getMainBalance().add(bgcsdf));
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {

                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }
                }

                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                //Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(new Date());
                csdfde.setTransId("1");

                pooltointdao.NewPoolToPool(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "DSM transfer to SRAS has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", list);
        mav.addObject("intbal", list2.get(0).getMainBalance());
        mav.addObject("corporateList", corporateList);
        mav.addObject("Intflagset", 111);
        mav.addObject("doc_sets", doc_sets);

        return mav;
    }

    public ModelAndView dsmtotras(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }

        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<PaymentInterestDisbursement> listcheck = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        ModelAndView mav = new ModelAndView("dsmtotras");
        List<PoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getPoolAccountDetails();
        List<RrasPoolAccountDetails> list2 = null;
        list2 = pooldao.getRrasPoolAccountDetails();
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");

        if (csdfSubmit != null) {
            PoolToPool csdfde = new PoolToPool();
            PoolToPoolDAO pooltointdao = new PoolToPoolDAO();

            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = pooltointdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String intbal = request.getParameter("intbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                BigDecimal intball = new BigDecimal(intbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setMainPoolBalance(poolbal.subtract(bgcsdf));
                csdfde.setPoolRrasBal(list2.get(0).getMainBalance().add(bgcsdf));
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {

                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }
                }

                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                //Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(new Date());
                csdfde.setTransId("2");

                pooltointdao.NewPoolToPool(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "DSM transfer to TRAS has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", list);
        mav.addObject("intbal", list2.get(0).getMainBalance());
        mav.addObject("corporateList", corporateList);
        mav.addObject("Intflagset", 111);
        mav.addObject("doc_sets", doc_sets);

        return mav;
    }

    public ModelAndView srastotras(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }

        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<PaymentInterestDisbursement> listcheck = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        if (PoolToPool != null && PoolToPool.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        ModelAndView mav = new ModelAndView("srastotras");
        List<AgcPoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getAgcPoolAccountDetails();
        List<RrasPoolAccountDetails> list2 = null;
        list2 = pooldao.getRrasPoolAccountDetails();
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");

        if (csdfSubmit != null) {
            PoolToPool csdfde = new PoolToPool();
            PoolToPoolDAO pooltointdao = new PoolToPoolDAO();

            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = pooltointdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String intbal = request.getParameter("intbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                BigDecimal intball = new BigDecimal(intbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setPoolAgcBal(poolbal.subtract(bgcsdf));
                csdfde.setPoolRrasBal(list2.get(0).getMainBalance().add(bgcsdf));
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {

                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }
                }

                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                //Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(new Date());
                csdfde.setTransId("3");

                pooltointdao.NewPoolToPool(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "SRAS transfer to TRAS has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", list);
        mav.addObject("intbal", list2.get(0).getMainBalance());
        mav.addObject("corporateList", corporateList);
        mav.addObject("Intflagset", 111);
        mav.addObject("doc_sets", doc_sets);

        return mav;
    }

    public ModelAndView pooltointagc(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginid") == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<PaymentInterestDisbursement> listcheck = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        ModelAndView mav = new ModelAndView("pooltointrefund");
        List<AgcPoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getAgcPoolAccountDetails();
        InterestPoolAccountDetailsDAO ipdao = new InterestPoolAccountDetailsDAO();
        List<AgcInterestPool> list2 = null;
        list2 = ipdao.getInterestPoolAccountDetailsagc();
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");

        if (csdfSubmit != null) {
            PoolToInt csdfde = new PoolToInt();
            PoolToIntDAO pooltointdao = new PoolToIntDAO();

            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = pooltointdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String intbal = request.getParameter("intbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                BigDecimal intball = new BigDecimal(intbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setPoolAgcBal(poolbal.subtract(bgcsdf));
                csdfde.setIntAgcBal(list2.get(0).getMainBalance().add(bgcsdf));
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {

                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }
                }

                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                //Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(new Date());

                pooltointdao.NewPoolToInt(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Pool transfer to Interest has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", list);
        mav.addObject("intbal", list2.get(0).getMainBalance());
        mav.addObject("corporateList", corporateList);
        mav.addObject("Intflagset", 111);
        mav.addObject("doc_sets", doc_sets);
        mav.addObject("billtype", "SRAS");

        return mav;
    }

    public ModelAndView pooltointrras(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginid") == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
        List<CsdfDetails> listpsdf = null;
        listrefund = temprefunddao.getAllPendingReceviableTempRefundBillCorp();
        TempPaymentDisbursementDAO tempdisdao = new TempPaymentDisbursementDAO();
        List<TempPaymentDisbursement> list1234 = null;
        DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
        List<PaymentInterestDisbursement> listcheck = null;
        list1234 = tempdisdao.getTempDisbursementDetailsbyStatus("Pending");
        PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
        List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
        if (PoolToInt != null && PoolToInt.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Interest");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest PSDF Pending in Checker. Please Clear IT");
            return mv2;
        }

        if (list1234 != null && list1234.size() > 0) {
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", "Please clear the pending Payment Disbusrment !!!!!! ");
            return mv;
        }
        if (listrefund != null && listrefund.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        listpsdf = csdfdao.getCsdfDetails("Bills");
        if (listpsdf != null && listpsdf.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "PSDF Disbursement is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }
        MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mapintlist = null;
        mapintlist = mapintdao.getPendingMappingInterestBank();
        if (mapintlist != null && mapintlist.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Interest Mapping is Pending at Checker . Please Clear it and Try Again...");
            return mv2;
        }

        ModelAndView mav = new ModelAndView("pooltointrefund");
        List<RrasPoolAccountDetails> list = null;
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        list = pooldao.getRrasPoolAccountDetails();
        InterestPoolAccountDetailsDAO ipdao = new InterestPoolAccountDetailsDAO();
        List<RrasInterestPool> list2 = null;
        list2 = ipdao.getInterestPoolAccountDetailsrras();
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();
        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<DocumentSets> doc_sets = miscdao.DocumentSetslist();

        List<MiscDisbursement> listmiscdis = null;
        String csdfSubmit = request.getParameter("csdfSubmit");

        if (csdfSubmit != null) {
            PoolToInt csdfde = new PoolToInt();
            PoolToIntDAO pooltointdao = new PoolToIntDAO();

            System.out.print("Inside misc button");
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Already Misc Disbursement Record pending with checker...Please clear it");
                return mv2;

            } else {
                int maxid = 0;
                maxid = pooltointdao.getMaxUNIQUE_ID().intValue();
                maxid = maxid + 1;
                csdfde.setUniqueId(new BigDecimal(maxid));
                String csdfamt = request.getParameter("csdfamt");
                String mainbal = request.getParameter("mainbal");
                String intbal = request.getParameter("intbal");
                String csdfremarks = request.getParameter("csdfremarks");
                String doc_set_num = request.getParameter("doc_sets");
                BigDecimal bgcsdf = new BigDecimal(csdfamt);
                BigDecimal poolbal = new BigDecimal(mainbal);
                BigDecimal intball = new BigDecimal(intbal);
                csdfde.setStatus("Pending");
                csdfde.setRefundAmt(bgcsdf);
                csdfde.setEntryDate(new Date());
                csdfde.setPoolRrasBal(poolbal.subtract(bgcsdf));
                csdfde.setIntRrasBal(list2.get(0).getMainBalance().add(bgcsdf));
                csdfde.setRemarks(csdfremarks);

                DsnFileDetailsDAO dsndetdao = new DsnFileDetailsDAO();
                List<DsnFileDetails> dsndetlist = dsndetdao.DsnFileDetailsbyfilename(doc_set_num);
                if (dsndetlist != null) {

                    if (dsndetlist.get(0).getMappedBalance().compareTo(bgcsdf) < 0) {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        mv2.addObject("Msg", "This DSN is linked to bank statement whose balance amount is " + dsndetlist.get(0).getMappedBalance() + ", but you enter " + bgcsdf + ", which is more than bank  mapped balance...Please check and retry");
                        return mv2;

                    }
                }

                csdfde.setDocumentSet(doc_set_num);
                csdfde.setMakerDate(new Date());

                //Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                csdfde.setEntryTime(new Date());

                pooltointdao.NewPoolToInt(csdfde);
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Pool transfer to Interest has Sucessfully submiteed for Checker");
                return mv2;

            }
        }
        mav.addObject("pooldetails", list);
        mav.addObject("intbal", list2.get(0).getMainBalance());
        mav.addObject("corporateList", corporateList);
        mav.addObject("Intflagset", 111);
        mav.addObject("doc_sets", doc_sets);
        mav.addObject("billtype", "TRAS");

        return mav;
    }

    public ModelAndView checkerVerifiesTransfer(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("form101uis");

        PoolToIntDAO pooltointdao = new PoolToIntDAO();
        PoolToInt ref = pooltointdao.getByUid(txuid).get(0);
        pooltointdao.checkmiscdisburseBychecker(txuid);

        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();

        if (ref.getMainPoolBalance() != null) {
            BigDecimal mainbal = pooldao.getPoolAccountDetails().get(0).getMainBalance();
            BigDecimal setmainbal = mainbal.subtract(ref.getRefundAmt());
            pooldao.getUpdatePoolAccountbyChecker(setmainbal);

            BigDecimal intbal = intepooldao.getInterestPoolAccountDetails().get(0).getMainBalance();
            intepooldao.getUpdateInterestPoolAccountbyChecker(intbal.add(ref.getRefundAmt()));
        }
        if (ref.getPoolAgcBal() != null) {
            BigDecimal mainbal = pooldao.getAgcPoolAccountDetails().get(0).getMainBalance();
            BigDecimal setmainbal = mainbal.subtract(ref.getRefundAmt());
            pooldao.getUpdatePoolAccountbyCheckeragc(setmainbal);

            BigDecimal intbal = intepooldao.getInterestPoolAccountDetailsagc().get(0).getMainBalance();
            intepooldao.getUpdateInterestPoolAccountbyCheckeragc(intbal.add(ref.getRefundAmt()));
        }
        if (ref.getPoolRrasBal() != null) {
            BigDecimal mainbal = pooldao.getRrasPoolAccountDetails().get(0).getMainBalance();
            BigDecimal setmainbal = mainbal.subtract(ref.getRefundAmt());
            pooldao.getUpdatePoolAccountbyCheckerrras(setmainbal);

            BigDecimal intbal = intepooldao.getInterestPoolAccountDetailsrras().get(0).getMainBalance();
            intepooldao.getUpdateInterestPoolAccountbyCheckerrras(intbal.add(ref.getRefundAmt()));
        }

        return documentChecker(request, response);
    }

    public ModelAndView checkerVerifiesTransfer1(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String txuid = request.getParameter("form101uis1");

        PoolToPoolDAO pooltointdao = new PoolToPoolDAO();
        PoolToPool ref = pooltointdao.getByUid(txuid).get(0);
        pooltointdao.checkmiscdisburseBychecker(txuid);

        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();

        if (ref.getTransId().equals("1")) {
            System.out.println("ref.getTransId() =" + ref.getTransId());
            BigDecimal mainbal = pooldao.getPoolAccountDetails().get(0).getMainBalance();
            BigDecimal setmainbal = mainbal.subtract(ref.getRefundAmt());
            pooldao.getUpdatePoolAccountbyChecker(setmainbal);

            BigDecimal intbal = pooldao.getAgcPoolAccountDetails().get(0).getMainBalance();
            pooldao.getUpdatePoolAccountbyCheckeragc(intbal.add(ref.getRefundAmt()));
        }
        if (ref.getTransId().equals("2")) {
            BigDecimal mainbal = pooldao.getPoolAccountDetails().get(0).getMainBalance();
            BigDecimal setmainbal = mainbal.subtract(ref.getRefundAmt());
            pooldao.getUpdatePoolAccountbyChecker(setmainbal);

            BigDecimal intbal = pooldao.getRrasPoolAccountDetails().get(0).getMainBalance();
            pooldao.getUpdatePoolAccountbyCheckerrras(intbal.add(ref.getRefundAmt()));
        }
        if (ref.getTransId().equals("3")) {
            BigDecimal mainbal = pooldao.getAgcPoolAccountDetails().get(0).getMainBalance();
            BigDecimal setmainbal = mainbal.subtract(ref.getRefundAmt());
            pooldao.getUpdatePoolAccountbyCheckeragc(setmainbal);

            BigDecimal intbal = pooldao.getRrasPoolAccountDetails().get(0).getMainBalance();
            pooldao.getUpdatePoolAccountbyCheckerrras(intbal.add(ref.getRefundAmt()));
        }

        return documentChecker(request, response);
    }

    public static Timestamp addMilliseconds(Timestamp timestamp, int milliseconds) {
        return add(timestamp, Calendar.MILLISECOND, milliseconds);
    }

    private static Timestamp add(Timestamp timestamp, int unit, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(timestamp);
        c.add(unit, amount);
        Timestamp ts = new Timestamp(c.getTimeInMillis());
        ts.setNanos(ts.getNanos() + timestamp.getNanos() % 1000000);
        return ts;
    }

    public static int getNanos(Timestamp timestamp) {
        return timestamp.getNanos();
    }

}
