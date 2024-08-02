/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import javafx.application.Application;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.BankStatementDAO;
import sampada.DAO.BillInterestRateDAO;
import sampada.DAO.BillPaymentDuedateDAO;
import sampada.DAO.BillPriorityDAO;
import sampada.DAO.ConstantsMasterDAO;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.CsdfDetailsDAO;
import sampada.DAO.DefaultLocDetailsDAO;
import sampada.DAO.DisbursedInterestDetailsDAO;
import sampada.DAO.EntityTypeDAO;
import sampada.DAO.InterestPoolAccountDetailsDAO;
import sampada.DAO.LetterOfCreditDAO;
import sampada.DAO.NewEntityRegistrationDAO;
import sampada.DAO.PoolAccountDetailsDAO;
import sampada.DAO.StateDAO;
import sampada.DAO.StatusDAO;
import sampada.DAO.UploadLcDocumentsDAO;
import sampada.DAO.UserDetailsDAO;
import sampada.DAO.DsnFileDetailsDAO;
import sampada.DAO.MappingInterestBankDAO;
import sampada.DAO.TempMappingBillBankDAO;
import sampada.DAO.TempPaymentDisbursementDAO;
import sampada.DAO.TempRefundBillCorpDAO;
import sampada.pojo.AgcInterestPool;
import sampada.pojo.AgcPoolAccountDetails;
import sampada.pojo.BankStatement;
import sampada.pojo.BillInterestRate;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPaymentDuedate;
import sampada.pojo.BillPriority;
import sampada.pojo.Corporates;
import sampada.pojo.CsdfDetails;
import sampada.pojo.DefaultLocDetails;
import sampada.pojo.Entites;
import sampada.pojo.EntityType;
import sampada.pojo.InterestPoolAccountDetails;
import sampada.pojo.LetterOfCredit;
import sampada.pojo.PoolAccountDetails;
import sampada.pojo.State;
import sampada.pojo.Status;
import sampada.pojo.UploadLcDocuments;
import sampada.pojo.UserDetails;
import sampada.pojo.DsnFileDetails;
import sampada.pojo.MappingInterestBank;
import sampada.pojo.PaymentInterestDisbursement;
import sampada.pojo.RrasInterestPool;
import sampada.pojo.RrasPoolAccountDetails;
import sampada.pojo.TempMappingBillBank;
import sampada.pojo.TempPaymentDisbursement;
import sampada.pojo.TempRefundBillCorp;
import sampada.util.DesEncrypter;

/**
 *
 * @author JaganMohan
 */
public class MasterController extends MultiActionController {

    public ModelAndView MasterIndex(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("MasterIndex");
        return mv;
    }

    public ModelAndView MasterMakerIndex(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("MasterMakerIndex");
        return mv;
    }

    public ModelAndView billUpload(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String btname = request.getParameter("btname");
        if (btname != null) {
            String filename = request.getParameter("filename");
            ModelAndView mv = new ModelAndView("successMsg");
            mv.addObject("Msg", filename);
            return mv;

        }

        ModelAndView mv = new ModelAndView("billUpload");
        return mv;
    }

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

    public ModelAndView newCommercialGroupRegistration(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        ModelAndView model = new ModelAndView("Masters/newCommercialGroupRegistration");

        CorporatesDAO corpdao = new CorporatesDAO();
        String bname = request.getParameter("bname");
        System.out.println("button name is : " + bname);
        if (bname != null) {
            if (bname.equalsIgnoreCase("Submit")) {
                System.out.println(" In approveCommercialGroupRegistration");

                ModelAndView mv = new ModelAndView("Masters/approveCommercialGroupRegistration");

                String statename = request.getParameter("state");
                String commercialname = request.getParameter("commercialname");
                String scommercialname = request.getParameter("scommercialname");

                String address = request.getParameter("address");
                String location = request.getParameter("location");//Added on Mar-22-2018
                String cpdsm = request.getParameter("cpdsm");
                String cprras = request.getParameter("cprras");
                String cpcong = request.getParameter("cpcong");
                String mobile = request.getParameter("mobile");
                String office = request.getParameter("office");
                String cpreact = request.getParameter("cpreact");
                String subAccNo = request.getParameter("subAccNo");
                String corpType = request.getParameter("corpType");
                String BankAccName = request.getParameter("BankAccName");
                System.out.println("corpType is " + corpType);

                String validity = "YES";
                String blockremarks = "ACTIVE";

                mv.addObject("statename", statename);
                mv.addObject("commercialname", commercialname);
                mv.addObject("scommercialname", scommercialname);

                mv.addObject("address", address);
                mv.addObject("location", location);
                mv.addObject("cpdsm", cpdsm);
                mv.addObject("cprras", cprras);
                mv.addObject("cpcong", cpcong);
                mv.addObject("cpreact", cpreact);
                mv.addObject("mobile", mobile);
                mv.addObject("office", office);
                mv.addObject("validity", validity);
                mv.addObject("blockremarks", blockremarks);
                mv.addObject("subAccNo", subAccNo);
                mv.addObject("corpType", corpType);
                mv.addObject("BankAccName", BankAccName);

                return mv;
            }
            if (bname.equalsIgnoreCase("Confirm")) {
                ModelAndView mav1 = new ModelAndView("successMsg");
                String statename = request.getParameter("state");
                String commercialname = request.getParameter("commercialname");
                String scommercialname = request.getParameter("scommercialname");
                String address = request.getParameter("address");
                String location = request.getParameter("location");//Added on Mar-22-2018
                String cpdsm = request.getParameter("cpdsm");
                String cprras = request.getParameter("cprras");
                String cpcong = request.getParameter("cpcong");
                String mobile = request.getParameter("mobile");
                String office = request.getParameter("office");
                String cpreact = request.getParameter("cpreact");
                String validity = request.getParameter("validity");
                String blockremarks = request.getParameter("blockremarks");
                String subAccNo = request.getParameter("subAccNo");
                String corpType = request.getParameter("corpType");
                String BankAccName = request.getParameter("BankAccName");

                System.out.println("corpType is " + corpType);

                Corporates corp = new Corporates();
                int maxid = 0;
                maxid = corpdao.getMaxCorporateID();
                maxid = maxid + 1;
                corp.setCorporateId(maxid);
                corp.setCorporateType(corpType);
                corp.setAddress(address);
                corp.setCorporateName(commercialname);
                corp.setSname(scommercialname);
                corp.setDsmContact(cpdsm);
                corp.setRrasContact(cprras);
                corp.setRectContact(cpreact);
                corp.setCongContact(cpcong);
                corp.setLocation(location);
                corp.setMobile(mobile);
                corp.setOffice(office);
                corp.setPartyCode(office);
                corp.setState(statename);
                corp.setEntryDate(new Date());
                corp.setValidity(validity);
                corp.setBlockRemarks(blockremarks);
                corp.setSubAccountNumber(subAccNo);
                corp.setBankAccName(BankAccName);
                corpdao.NewCorporateDetails(corp);
                mav1.addObject("Msg", "Commercial Group is Registered Successfully");
                return mav1;
            }

        }
        StateDAO stateDao = new StateDAO();
        List<State> stateList = stateDao.stateList();
        model.addObject("stateList", stateList);
        return model;
    }

    public ModelAndView newEntityRegistration(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        ModelAndView model = new ModelAndView("Masters/newEntityRegistration");

        CorporatesDAO corpDao = new CorporatesDAO();
        List<Corporates> corpList = corpDao.Corporateslist();
// System.out.println(" corpList size is " + corpList.size());
        String bname = request.getParameter("bname");
        System.out.println("button name is : " + bname);
        if (bname != null) {
            if (bname.equalsIgnoreCase("Submit")) {

                ModelAndView mv = new ModelAndView("Masters/approveEntityRegistration");

                String corpId = request.getParameter("corpId");
                System.out.println("corpId is: " + corpId);
                int corpid = Integer.parseInt(corpId);
                String corpName = corpDao.getCorporateNamebyID(corpid);
                String statename = request.getParameter("state");
                String entityname = request.getParameter("sentityname");
                String sentityname = request.getParameter("entityname");
                String bankname = request.getParameter("bankname");
                String branchname = request.getParameter("branchname");
                String accno = request.getParameter("accno");
                String ifsc = request.getParameter("ifsc");
                String rtgsneft = request.getParameter("rtgsneft");
                String address = request.getParameter("address");
                String location = request.getParameter("location");//Added on Mar-22-2018
                String cpdsm = request.getParameter("cpdsm");
                String cprras = request.getParameter("cprras");
                String cpcong = request.getParameter("cpcong");

                String mobile = request.getParameter("mobile");
                String office = request.getParameter("office");
                String entityType = request.getParameter("entityType");

                String cpreact = request.getParameter("cpreact");
                String rrasconsiderable = request.getParameter("rrasconsiderable");
                String agcconsiderable = request.getParameter("agcconsiderable");//Added on Mar-22-2018
                String frasconsiderable = request.getParameter("frasconsiderable");
                String cpfras = request.getParameter("cpfras");
                String srasconsiderable = request.getParameter("srasconsiderable");
                String trasconsiderable = request.getParameter("trasconsiderable");

                mv.addObject("corpName", corpName);
                mv.addObject("corpId", corpId);
                mv.addObject("entityType", entityType);
                mv.addObject("statename", statename);
                mv.addObject("entityname", entityname);
                mv.addObject("sentityname", sentityname);
                mv.addObject("bankname", bankname);
                mv.addObject("accno", accno);
                mv.addObject("branchname", branchname);
                mv.addObject("ifsc", ifsc);
                mv.addObject("rtgsneft", rtgsneft);
                mv.addObject("address", address);
                mv.addObject("location", location);
                mv.addObject("cpdsm", cpdsm);
                mv.addObject("cprras", cprras);
                mv.addObject("cpcong", cpcong);
                mv.addObject("cpreact", cpreact);
                mv.addObject("mobile", mobile);
                mv.addObject("office", office);
                mv.addObject("rrasconsiderable", rrasconsiderable);
                mv.addObject("agcconsiderable", agcconsiderable);
                mv.addObject("frasconsiderable", frasconsiderable);
                mv.addObject("srasconsiderable", srasconsiderable);
                mv.addObject("trasconsiderable", trasconsiderable);

                mv.addObject("cpfras", cpfras);

                return mv;
            }

            if (bname.equalsIgnoreCase("Confirm")) {
                ModelAndView mav1 = new ModelAndView("successMsg");
                String corpId = request.getParameter("corpId");
                System.out.println("corpId is: " + corpId);
                int corpid = Integer.parseInt(corpId);
                String statename = request.getParameter("state");
                String entityType = request.getParameter("entityType");
                String entityname = request.getParameter("entityname");
                String sentityname = request.getParameter("sentityname");
                String bankname = request.getParameter("bankname");
                String branchname = request.getParameter("branchname");
                String accno = request.getParameter("accno");
                String ifsc = request.getParameter("ifsc");
                String rtgsneft = request.getParameter("rtgsneft");
                String address = request.getParameter("address");
                String location = request.getParameter("location");//Added on Mar-22-2018
                String cpdsm = request.getParameter("cpdsm");
                String cprras = request.getParameter("cprras");
                String cpcong = request.getParameter("cpcong");
                String mobile = request.getParameter("mobile");
                String office = request.getParameter("office");
                String cpreact = request.getParameter("cpreact");
                String rrasconsiderable = request.getParameter("rrasconsiderable");
                String agcconsiderable = request.getParameter("agcconsiderable");//Added on Mar-22-2018
                String frasconsiderable = request.getParameter("frasconsiderable");
                String cpfras = request.getParameter("cpfras");
                String srasconsiderable = request.getParameter("srasconsiderable");
                String trasconsiderable = request.getParameter("trasconsiderable");

                BigDecimal reqNo = BigDecimal.ONE;
                NewEntityRegistrationDAO newtrader = new NewEntityRegistrationDAO();
                Entites newtra = new Entites();
                Corporates corp = new Corporates();
                corp.setCorporateId(corpid);
                newtra.setCorporates(corp);
                reqNo = newtrader.getMaxnewEntityID();
                reqNo = reqNo.add(BigDecimal.ONE);
                newtra.setEntityId(reqNo);
                newtra.setStateName(statename);
                newtra.setEntityType(entityType);
                newtra.setEntittyName(sentityname);
                newtra.setShortEntityname(entityname);
                newtra.setBankName(bankname);
                newtra.setBranchName(branchname);
                newtra.setAccountNumber(accno);
                newtra.setIfscCode(ifsc);
                newtra.setRtgsNeftFlag(rtgsneft);
                newtra.setAddress(address);
                newtra.setEntityLocation(location);//Added on Mar-22-2018
                newtra.setDsmContact(cpdsm);
                newtra.setRrasContact(cprras);
                newtra.setStateName(statename);
                newtra.setCongContact(cpcong);
                newtra.setRectContact(cpreact);

                newtra.setMobile(mobile);
                newtra.setOffice(office);
                newtra.setRrasFlag(rrasconsiderable);
                newtra.setAgcFlag(agcconsiderable);
                newtra.setFrasFlag(frasconsiderable);
                newtra.setFrasContact(cpfras);
                newtra.setSrasFlag(srasconsiderable);
                newtra.setTrasFlag(trasconsiderable);

                newtrader.NewEntity(newtra);
                mav1.addObject("Msg", "Entity is Registered Successfully");
                return mav1;
            }

        }
        StateDAO stateDao = new StateDAO();
        EntityTypeDAO entityTypeDao = new EntityTypeDAO();
        List<EntityType> entityTypeList = entityTypeDao.entityTypeList();
        List<State> stateList = stateDao.stateList();
        model.addObject("stateList", stateList);
        model.addObject("entityTypeList", entityTypeList);
        model.addObject("corpList", corpList);
        return model;
    }

    public ModelAndView viewCommercialDetailsList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("Masters/viewCommercialDetailsList");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        CorporatesDAO corporatedao = new CorporatesDAO();
        List<Corporates> corporateList = corporatedao.Corporateslist();

        mav.addObject("corporateList", corporateList);
        return mav;
    }

    public ModelAndView uploaddsn(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("Masters/dsncorpselect");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        BankStatementDAO bankdao = new BankStatementDAO();
        CorporatesDAO corpDao = new CorporatesDAO();
        String bName = request.getParameter("bName");
        if (bName != null) {
            String corpname = request.getParameter("corpId");
            int corporateid = corpDao.getCorpIdbyName(corpname);
            List<BankStatement> bankstmtlist = bankdao.BankStatementlistforDSN(corporateid);
            ModelAndView mav1 = new ModelAndView("Masters/dsncorpview");
            mav1.addObject("bankstmtlist", bankstmtlist);
            return mav1;
        }

        List<BankStatement> bnkstmtListforallcorp = bankdao.BankStatementlistofallcorp();
        List<String> corpnames = new ArrayList<>();
        if (bnkstmtListforallcorp != null && !(bnkstmtListforallcorp.isEmpty())) {
            for (BankStatement e : bnkstmtListforallcorp) {
                if (!corpnames.contains(e.getCorporates().getCorporateName())) {
                    corpnames.add(e.getCorporates().getCorporateName());
                }

            }
        }
        mav.addObject("corpnames", corpnames);
        return mav;
    }

    public ModelAndView disbursedsn(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("Masters/dsndisburseview");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        BankStatementDAO bankdao = new BankStatementDAO();

        List<BankStatement> bnkstmtdsn = bankdao.BankStatementlistbydsndetails();

        mav.addObject("bnkstmtdsn", bnkstmtdsn);
        return mav;
    }

    public ModelAndView disbursedsnconfirm(HttpServletRequest request,
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

        String stmtId = request.getParameter("stmtId");
        BankStatementDAO bankdao = new BankStatementDAO();
        BigDecimal corporateid = bankdao.getcorpidbyStmtID(Integer.parseInt(stmtId));
        MappingInterestBankDAO mapIntDao = new MappingInterestBankDAO();
        List<MappingInterestBank> pendingCheckerInterest = mapIntDao.getCheckerPendingInterestCountForCorporate(corporateid.intValue());
        System.out.println("$$$$$$$$$$ Outside if pendingCheckerInterest is " + pendingCheckerInterest.size());
        if (pendingCheckerInterest != null && pendingCheckerInterest.size() > 0) {
            ModelAndView mv1 = new ModelAndView("successMsg");
            System.out.println(" Inside if pendingCheckerInterest is " + pendingCheckerInterest.size());
            String Msg = "Kindly ask Checker to verify Interest Mapping at Bill Payable Side!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }
        TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempMappingBillBankbyCorp(corporateid.intValue(), "Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getAllRefundPayablePendingTempRefundBillCorpByChecker(corporateid.intValue());
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }
        List<BankStatement> bnkstmtdsn = bankdao.BankStatementlistbydsndetails();
        ModelAndView mav = new ModelAndView("successMsg");
        mav.addObject("Msg", stmtId);

        return mav;
    }

//    public ModelAndView viewdsn(HttpServletRequest request,
//            HttpServletResponse response) throws Exception {
//        ModelAndView mav = new ModelAndView("Masters/dsnfileview");
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/sessionlogout.htm");
//            return new ModelAndView(redirectView);
//        }
//        BankStatementDAO bankdao = new BankStatementDAO();
//        CorporatesDAO corpDao = new CorporatesDAO();
//        DsnFileDetailsDAO dsnfiledetlsdao = new DsnFileDetailsDAO();
//        List<DsnFileDetails> dsnfileslist = null;
//        String submitBtn = request.getParameter("submitBtn");
//        String stmtId = request.getParameter("stmtId");
//        if (submitBtn != null) {
//            String docname = request.getParameter("docname");
//            String filecatergory = request.getParameter("filecatergory");
//            String usrfilename = request.getParameter("usrfilename");
//            String stmtId1 = request.getParameter("stmtId");
//            String remarks = request.getParameter("remarks");
////
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            MultipartFile multipartFile = multipartRequest.getFile("docname");
//            ConstantsMasterDAO constadao = new ConstantsMasterDAO();
//            String filepath = constadao.getFilePathbyName("INTEREST_EXCELFILE");
//            System.out.print("File path is " + filepath);
//            String fileExt = request.getParameter("fileExt");  //add this line
//            System.out.print("File Ext is " + fileExt);
//            String fullFileName = stmtId1 + "_" + usrfilename;
//            byte[] bytes = multipartFile.getBytes();
//            File path = new File(filepath);
//            System.out.print("File path is " + filepath);
//            String uploadPath = filepath;
//            String f = multipartFile.getOriginalFilename();
//            //  File originalfilename = new File(uploadPath + fullFileName + ".pdf");
//            File originalfilename = new File(uploadPath + fullFileName + "." + fileExt);
//            FileCopyUtils.copy(multipartFile.getBytes(), originalfilename);
//
//            DsnFileDetails dsnfiledet = new DsnFileDetails();
//            BankStatement bnkstmt = new BankStatement();
//            BigDecimal slno = new BigDecimal(0);
//            slno = dsnfiledetlsdao.getMaxSlno();
//            System.out.println("slno is" + slno);
//            slno = slno.add(BigDecimal.ONE);
//            bnkstmt.setStmtId(new BigDecimal(stmtId1));
//            dsnfiledet.setBankStatement(bnkstmt);
//            dsnfiledet.setSlno(slno);
//            dsnfiledet.setFileType("." + fileExt);
//            dsnfiledet.setFileName(usrfilename);
//            dsnfiledet.setSavedFileName(fullFileName);
//            dsnfiledet.setRemarks(remarks);
//            dsnfiledet.setCheckerStatus("Pending");
//            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
//            dsnfiledet.setEntryTime(currentTimestamp);
//
//            dsnfileslist = dsnfiledetlsdao.DsnFileDetailsbystmtidbyfilename(new BigDecimal(stmtId1), usrfilename);
//            if (dsnfileslist == null) {
//                dsnfiledetlsdao.NewDsnFileDetails(dsnfiledet);
//            }
//            dsnfileslist = dsnfiledetlsdao.DsnFileDetailsbystmtid(new BigDecimal(stmtId1));
//            ModelAndView mav1 = new ModelAndView("Masters/dsnfileview");
//            mav1.addObject("stmtId", stmtId1);
//            mav1.addObject("dsnfileslist", dsnfileslist);
//            return mav1;
//        }
//        dsnfileslist = dsnfiledetlsdao.DsnFileDetailsbystmtid(new BigDecimal(stmtId));
//        mav.addObject("stmtId", stmtId);
//        mav.addObject("dsnfileslist", dsnfileslist);
//        return mav;
//    }
    public ModelAndView viewDocument(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView("successMsg");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("sessionlogout.htm");
            return new ModelAndView(redirectView);
        }

        String filename = request.getParameter("savedFilename");
        ConstantsMasterDAO constadao = new ConstantsMasterDAO();
        String filepath = constadao.getFilePathbyName("INTEREST_EXCELFILE");
        System.out.print("File path " + filepath);
        String fileExt = request.getParameter("ext");  //add this line
        System.out.print("File Ext is " + fileExt);
        //   filepath=filepath+filename+".pdf";
        filepath = filepath + filename + fileExt;
        File file = new File(filepath, URLDecoder.decode(filepath, "UTF-8"));
        //response.setContentType("application/pdf"); 
        response.setContentType("application/" + fileExt);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        ServletOutputStream out1;
        out1 = response.getOutputStream();
        FileInputStream fin = new FileInputStream(filepath);
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out1);
        int ch = 0;;
        while ((ch = bin.read()) != -1) {
            bout.write(ch);
        }

        bin.close();
        fin.close();
        bout.close();
        out1.close();

        return null;
    }

    public ModelAndView viewEntityDetailsList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("Masters/viewEntityDetailsList");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        NewEntityRegistrationDAO newtrader = new NewEntityRegistrationDAO();
        List<Entites> entityList = newtrader.Entitieslist();

        mav.addObject("entityList", entityList);
        return mav;
    }

//    public ModelAndView editSingleCommercialGroup(HttpServletRequest request,
//            HttpServletResponse response) throws IOException, ParseException {
//
//        ModelAndView mv = new ModelAndView("Masters/editSingleCommercialGroup");
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/sessionlogout.htm");
//            return new ModelAndView(redirectView);
//        }
//        String sCorporateId = request.getParameter("corporateId");
//        int corporateId = Integer.parseInt(sCorporateId);
//        CorporatesDAO corporatedao = new CorporatesDAO();
//        Corporates corporate = new Corporates();
//        String bName = request.getParameter("bName");
//
//        if (bName != null) {
//            if (bName.equalsIgnoreCase("Modify")) {
//                ModelAndView mv1 = new ModelAndView("Masters/successPage");
//
//                sCorporateId = request.getParameter("corporateId");
//                corporateId = Integer.parseInt(sCorporateId);
//                String corporateName = request.getParameter("corporateName");
//                String sname = request.getParameter("sname");
//                String location = request.getParameter("location");
//                String state = request.getParameter("state");
//                String address = request.getParameter("address");
//                String cpdsm = request.getParameter("cpdsm");
//                String cprras = request.getParameter("cprras");
//                String cpcong = request.getParameter("cpcong");
//                String cpreact = request.getParameter("cpreact");
//                String mobile = request.getParameter("mobile");
//                String office = request.getParameter("office");
//                String sEntryDate = request.getParameter("entryDate");
//                System.out.println("Date is :" + sEntryDate);
//
//                // DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
//                Date entryDate = sdf.parse(sEntryDate);
//
//                //  Date entryDate=new SimpleDateFormat("dd-MMM-yy").parse(sEntryDate); 
//                System.out.println(" Parsed Date is :" + entryDate);
//                corporate.setCorporateName(corporateName);
//                corporate.setSname(sname);
//                corporate.setLocation(location);
//                corporate.setCorporateId(corporateId);
//                corporate.setState(state);
//                corporate.setAddress(address);
//                corporate.setCongContact(cpcong);
//                corporate.setDsmContact(cpdsm);
//                corporate.setRrasContact(cprras);
//                corporate.setRectContact(cpreact);
//                corporate.setMobile(mobile);
//                corporate.setOffice(office);
//                corporate.setEntryDate(entryDate);
//                corporatedao.UpdateCorporates(corporate);
//                mv1.addObject("msg", "Commercial Group Details Modified");
//
//                return mv1;
//            }
//        }
//        List<Corporates> corporateInfo = corporatedao.getCorporatesbyCorporateId(corporateId);
//        StateDAO stateDao = new StateDAO();
//        List<State> stateList = stateDao.stateList();
//        mv.addObject("stateList", stateList);
//        mv.addObject("corporateInfo", corporateInfo);
//        return mv;
//    }
    public ModelAndView editSingleCommercialGroup(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Masters/editSingleCommercialGroup");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        String sCorporateId = request.getParameter("corporateId");
        int corporateId = Integer.parseInt(sCorporateId);
        CorporatesDAO corporatedao = new CorporatesDAO();
        Corporates corporate = new Corporates();
        String bName = request.getParameter("bName");

        if (bName != null) {

            ModelAndView mv1 = new ModelAndView("Masters/successPage");

            sCorporateId = request.getParameter("corporateId");
            corporateId = Integer.parseInt(sCorporateId);
            String corporateName = request.getParameter("corporateName");
            String corporateType = request.getParameter("corporateType");
            String sname = request.getParameter("sname");
            String location = request.getParameter("location");
            String state = request.getParameter("state");
            String address = request.getParameter("address");
            String cpdsm = request.getParameter("cpdsm");
            String cprras = request.getParameter("cprras");
            String cpcong = request.getParameter("cpcong");
            String cpreact = request.getParameter("cpreact");
            String mobile = request.getParameter("mobile");
            String office = request.getParameter("office");
            String sEntryDate = request.getParameter("entryDate");
            String validity = request.getParameter("validity");
            String blockremarks = request.getParameter("blockremarks");
            String subAccNo = request.getParameter("subAccNo");
            String BankAccName = request.getParameter("BankAccName");

            System.out.println("Date is :" + sEntryDate);

// DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
            Date entryDate = sdf.parse(sEntryDate);

// Date entryDate=new SimpleDateFormat("dd-MMM-yy").parse(sEntryDate);
            System.out.println(" Parsed Date is :" + entryDate);
            corporate.setCorporateName(corporateName);
            corporate.setSname(sname);
            corporate.setLocation(location);
            corporate.setCorporateId(corporateId);
            corporate.setState(state);
            corporate.setAddress(address);
            corporate.setCongContact(cpcong);
            corporate.setDsmContact(cpdsm);
            corporate.setRrasContact(cprras);
            corporate.setRectContact(cpreact);
            corporate.setMobile(mobile);
            corporate.setOffice(office);
            corporate.setPartyCode(office);
            corporate.setEntryDate(entryDate);
            corporate.setValidity(validity);
            corporate.setBlockRemarks(blockremarks);
            corporate.setCorporateType(corporateType);
            corporate.setSubAccountNumber(subAccNo);
            corporate.setBankAccName(BankAccName);
            corporatedao.UpdateCorporates(corporate);
            mv1.addObject("msg", "Commercial Group Details Modified !!");

            return mv1;
        }

        List<Corporates> corporateInfo = corporatedao.getCorporatesbyCorporateId(corporateId);
        StateDAO stateDao = new StateDAO();
        List<State> stateList = stateDao.stateList();
        mv.addObject("stateList", stateList);
        mv.addObject("corporateInfo", corporateInfo);
        return mv;
    }

    public ModelAndView editSingleEntity(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Masters/editSingleEntity");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        String entityId = request.getParameter("entityId");
        BigDecimal entityid = new BigDecimal(entityId);
        NewEntityRegistrationDAO corporatedao = new NewEntityRegistrationDAO();
        Entites entity = new Entites();
        String bName = request.getParameter("bName");

        if (bName != null) {

            ModelAndView mv1 = new ModelAndView("Masters/successPage");

            entityId = request.getParameter("entityId");
//entityid = Integer.parseInt(entityId);

            BigDecimal Entityid = new BigDecimal(entityId);
            String entityName = request.getParameter("entityName");
            String entityType = request.getParameter("entityType");

            String sname = request.getParameter("sname");
            String location = request.getParameter("location");
            String state = request.getParameter("state");
            String address = request.getParameter("address");
            String cpdsm = request.getParameter("cpdsm");
            String cprras = request.getParameter("cprras");
            String cpcong = request.getParameter("cpcong");
            String cpreact = request.getParameter("cpreact");
            String mobile = request.getParameter("mobile");
            String office = request.getParameter("office");
            String corporateId = request.getParameter("corporateId");
            int corpid = Integer.parseInt(corporateId);
            String branchName = request.getParameter("branchName");
            String accno = request.getParameter("accno");
            String ifsc = request.getParameter("ifsc");
            String rtgsneft = request.getParameter("rtgsneft");

            String rrasconsiderable = request.getParameter("rrasconsiderable");
            String agcconsiderable = request.getParameter("agcconsiderable");
            String bankName = request.getParameter("bankName");
            String frasconsiderable = request.getParameter("frasconsiderable");
            String cpfras = request.getParameter("cpfras");
            String srasconsiderable = request.getParameter("srasconsiderable");
            String trasconsiderable = request.getParameter("trasconsiderable");

            Corporates corp = new Corporates();
            corp.setCorporateId(corpid);
            entity.setCorporates(corp);
            entity.setEntittyName(entityName);
            entity.setEntityType(entityType);
            entity.setShortEntityname(sname);
            entity.setEntityLocation(location);
            entity.setEntityId(Entityid);
            entity.setStateName(state);
            entity.setAddress(address);
            entity.setCongContact(cpcong);
            entity.setDsmContact(cpdsm);
            entity.setRrasContact(cprras);
            entity.setRectContact(cpreact);
            entity.setMobile(mobile);
            entity.setOffice(office);
            entity.setRrasFlag(rrasconsiderable);
            entity.setFrasFlag(frasconsiderable);
            entity.setFrasContact(cpfras);
            entity.setAgcFlag(agcconsiderable);
            entity.setBankName(bankName);
            entity.setBranchName(branchName);
            entity.setAccountNumber(accno);
            entity.setIfscCode(ifsc);
            entity.setRtgsNeftFlag(rtgsneft);
            entity.setSrasFlag(srasconsiderable);
            entity.setTrasFlag(trasconsiderable);
            
            corporatedao.UpdateEntites(entity);
            mv1.addObject("msg", "Entity Details Modified");

            return mv1;

        }
        List<Entites> corporateInfo = corporatedao.getEntitiesbyEntityId(entityid);
        StateDAO stateDao = new StateDAO();
        StatusDAO statusDao = new StatusDAO();
        EntityTypeDAO entityTypeDao = new EntityTypeDAO();
        List<EntityType> entityTypeList = entityTypeDao.entityTypeList();
        List<State> stateList = stateDao.stateList();
        List<Status> statusList = statusDao.statusList();
        mv.addObject("statusList", statusList);
        mv.addObject("stateList", stateList);
        mv.addObject("entityTypeList", entityTypeList);
        mv.addObject("corporateInfo", corporateInfo);
        return mv;
    }

    public ModelAndView viewPoolAccountDetails(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("Masters/viewPoolAccountDetails");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        PoolAccountDetailsDAO poolDao = new PoolAccountDetailsDAO();
        List<PoolAccountDetails> poolDetails = poolDao.PoolAccountDetails();
        List<AgcPoolAccountDetails> listagc = poolDao.getAgcPoolAccountDetails();
        List<RrasPoolAccountDetails> listrras = poolDao.getRrasPoolAccountDetails();

        InterestPoolAccountDetailsDAO intPoolDao = new InterestPoolAccountDetailsDAO();
        List<InterestPoolAccountDetails> intAccDetails = intPoolDao.InterestPoolAccountDetails();
        List<AgcInterestPool> listinterestpoolagc = intPoolDao.getInterestPoolAccountDetailsagc();
        List<RrasInterestPool> listinterestpoolrras = intPoolDao.getInterestPoolAccountDetailsrras();

        mav.addObject("poolDetails", poolDetails);
        mav.addObject("listagc", listagc);
        mav.addObject("listrras", listrras);
        mav.addObject("intAccDetails", intAccDetails);
        mav.addObject("listinterestpoolagc", listinterestpoolagc);
        mav.addObject("listinterestpoolrras", listinterestpoolrras);
        return mav;
    }

    //
    public ModelAndView viewLetterOfCredit(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        System.out.print(" Inside viewLetterOfCredit method");
        ModelAndView model = new ModelAndView("Masters/viewLcDetails");

        LetterOfCreditDAO lcDAO = new LetterOfCreditDAO();
        List<LetterOfCredit> list1;
        list1 = lcDAO.letterOfCreditList();

        model.addObject("lclist", list1);
        model.addObject("elelist", list1);

        return model;

    }

//    public ModelAndView newLetterofCredit(HttpServletRequest request,
//            HttpServletResponse response) throws IOException, ParseException {
//
//        HttpSession session = request.getSession(false);
//        ModelAndView model = new ModelAndView("Masters/newLetterofCredit");
//
//        LetterOfCreditDAO lcDao = new LetterOfCreditDAO();
//        LetterOfCredit loc = new LetterOfCredit();
//        UploadLcDocumentsDAO uploadLcDAO = new UploadLcDocumentsDAO();
//        UploadLcDocuments uploadLcDoc = new UploadLcDocuments();
//
//        Date start_date = null;
//        Date end_date = null;
//        CorporatesDAO corpDao = new CorporatesDAO();
//        List<Corporates> corpList = corpDao.Corporateslist();
////        System.out.println(" corpList size is " + corpList.size());
//        String bname = request.getParameter("bname");
//        System.out.println("button name is : " + bname);
//        if (bname != null) {
//            if (bname.equalsIgnoreCase("Submit")) {
//                ModelAndView mav1 = new ModelAndView("successMsg");
//                String finyear = request.getParameter("finyear");
//                String consname = request.getParameter("consname");
//                String finavg = request.getParameter("finavg");
//                String lcamount = request.getParameter("lcamount");
//                String branchname = request.getParameter("branchname");
//                String bankname = request.getParameter("bankname");
//                String startdate = request.getParameter("startdate");
//                String enddate = request.getParameter("enddate");
//                String remarks = request.getParameter("remarks");
//                String lcfilename = request.getParameter("lcfilename");
//                System.out.println("lc file name is :" + lcfilename);
//                start_date = new SimpleDateFormat("dd-MM-yy").parse(startdate);
//                end_date = new SimpleDateFormat("dd-MM-yy").parse(enddate);
//
//                Date entryDate = new Date();
//                int reqNo = 0;
//
//                reqNo = lcDao.getMaxLCID();
//                reqNo = reqNo + 1;
//
//                String saved_lcfilename = null;
//                saved_lcfilename = reqNo + "_" + lcfilename;
//
//                loc.setLcId(new BigDecimal(reqNo));
//                loc.setLcIssueBank(bankname);
//                loc.setLcIssueBranch(branchname);
//                loc.setConstituent(consname);
//                loc.setFinYear(finyear);
//                loc.setLastFyAvg(finavg);
//                loc.setLcFromdate(start_date);
//                loc.setLcTodate(end_date);
//                loc.setEntryDate(entryDate);
//                loc.setLcAmount(lcamount);
//                loc.setRemarks(remarks);
//                loc.setFileName(saved_lcfilename);
//
//                int tridflag1 = lcDao.checkExistenceOfLCamount(consname, finyear);
//                if (tridflag1 == 0) {
//                    loc.setLcCondition(BigDecimal.ONE);
//                    loc.setExpFlag(BigDecimal.ZERO);
//                }
//                if (tridflag1 > 0) {
//
//                    int maxid = 0;
//                    maxid = lcDao.getMaxLCCondition(consname, finyear);
//                    maxid = maxid + 1;
//                    lcDao.updateExpFlagLOCNameandFinYear(consname, finyear);
//                    loc.setLcCondition(new BigDecimal(maxid));
//                    loc.setExpFlag(BigDecimal.ZERO);
//                }
//                lcDao.NewLoCDetails(loc);
//
//                int slNo = 0;
//                slNo = uploadLcDAO.getMaxnewReqID();
////
//                slNo = slNo + 1;
//
//                System.out.println("saved lc file name is: " + saved_lcfilename);
//                uploadLcDoc.setConstituent(consname);
//                uploadLcDoc.setFileCatergory("Original");
//                uploadLcDoc.setFileType(".pdf");
//                uploadLcDoc.setSavedFilename(saved_lcfilename);
//                uploadLcDoc.setSlno(new BigDecimal(slNo));
//                uploadLcDoc.setUploadDate(entryDate);
//                System.out.println("Before LC iD print ");
//                loc.setLcId(new BigDecimal(reqNo));
//                uploadLcDoc.setLetterOfCredit(loc);
//                System.out.println("#######After LC iD print ");
//                uploadLcDoc.setUserFilename(lcfilename);
//
//                boolean sucflg = uploadLcDAO.NewUploadLoCDocumentsDetails(uploadLcDoc);
//                System.out.println("sucflag value is: " + sucflg);
//                if (sucflg) {
//                    String filename = request.getParameter("lcfilename");
//                    filename = slNo + "_" + filename;
//                    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//                    MultipartFile multipartFile = multipartRequest.getFile("lcfile");
////ServletContext context = request.getServletContext();
//// String filepath = context.getInitParameter("file-upload");
//                    ConstantsMasterDAO constadao = new ConstantsMasterDAO();
//                    String filepath = constadao.getFilePathbyName("LOC_TEMPLATE");
//
//                    System.out.print("File path is " + filepath);
//                    String uploadPath = filepath;
//                    String f = multipartFile.getOriginalFilename();
//                    File originalfilename = new File(uploadPath + filename + ".pdf");
//                    FileCopyUtils.copy(multipartFile.getBytes(), originalfilename);
//// String msg = "Applicant Registered successfully! Please quote the Req No -> " + reqNo + " \t in future";
//                    System.out.println("Letter of Credit file uploaded successfully");
//
//                } else {
//
//                    ModelAndView mv1 = new ModelAndView("errorMsg");
//                    mv1.addObject("msg", "Error in Uploading Details");
//                    return mv1;
//                }
//                mav1.addObject("Msg", "Letter of Credit Details Registered Successfully");
//                return mav1;
//            }
//
//        }
//
//        model.addObject("corpList", corpList);
//        return model;
//    }
    public ModelAndView newLetterofCredit(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session = request.getSession(false);
        ModelAndView model = new ModelAndView("Masters/newLetterofCredit");

        LetterOfCreditDAO lcDao = new LetterOfCreditDAO();
        LetterOfCredit loc = new LetterOfCredit();
        UploadLcDocumentsDAO uploadLcDAO = new UploadLcDocumentsDAO();
        UploadLcDocuments uploadLcDoc = new UploadLcDocuments();

        Date start_date = null;
        Date end_date = null;
        CorporatesDAO corpDao = new CorporatesDAO();
        DefaultLocDetailsDAO defLCDAO = new DefaultLocDetailsDAO();
        List<Corporates> corpList = corpDao.Corporateslist();
//        System.out.println(" corpList size is " + corpList.size());
        String bname = request.getParameter("bname");
        System.out.println("button name is : " + bname);
        if (bname != null) {
            if (bname.equalsIgnoreCase("Submit")) {
                ModelAndView mav1 = new ModelAndView("successMsg");
                String finyear = request.getParameter("finyear");
                String consname = request.getParameter("consname");
                String finavg = request.getParameter("finavg");
                String lcamount = request.getParameter("lcamount");
                String branchname = request.getParameter("branchname");
                String bankname = request.getParameter("bankname");
                String startdate = request.getParameter("startdate");
                String enddate = request.getParameter("enddate");
                String remarks = request.getParameter("remarks");
                String lcfilename = request.getParameter("lcfilename");
                String dafaultid = request.getParameter("dafaultid");
                System.out.println("lc file name is :" + lcfilename);
                start_date = new SimpleDateFormat("dd-MM-yy").parse(startdate);
                end_date = new SimpleDateFormat("dd-MM-yy").parse(enddate);

                DefaultLocDetails deaulloc = new DefaultLocDetails();
                deaulloc.setSlno(new BigDecimal(dafaultid));
                Date entryDate = new Date();
                int reqNo = 0;

                reqNo = lcDao.getMaxLCID();
                reqNo = reqNo + 1;

                String saved_lcfilename = null;
                saved_lcfilename = reqNo + "_" + lcfilename;

                loc.setLcId(new BigDecimal(reqNo));
                loc.setLcIssueBank(bankname);
                loc.setLcIssueBranch(branchname);
                loc.setConstituent(consname);
                loc.setFinYear(finyear);
                loc.setLastFyAvg(finavg);
                loc.setLcFromdate(start_date);
                loc.setLcTodate(end_date);
                loc.setEntryDate(entryDate);
                loc.setLcAmount(lcamount);
                loc.setRemarks(remarks);
                loc.setFileName(saved_lcfilename);
                loc.setDefaultLocDetails(deaulloc);

                int tridflag1 = lcDao.checkExistenceOfLCamount(consname, finyear);
                if (tridflag1 == 0) {
                    loc.setLcCondition(BigDecimal.ONE);
                    loc.setExpFlag(BigDecimal.ZERO);
                }
                if (tridflag1 > 0) {

                    int maxid = 0;
                    maxid = lcDao.getMaxLCCondition(consname, finyear);
                    maxid = maxid + 1;
                    lcDao.updateExpFlagLOCNameandFinYear(consname, finyear);
                    loc.setLcCondition(new BigDecimal(maxid));
                    loc.setExpFlag(BigDecimal.ZERO);
                }
                lcDao.NewLoCDetails(loc);

                int slNo = 0;
                slNo = uploadLcDAO.getMaxnewReqID();
//
                slNo = slNo + 1;

                System.out.println("saved lc file name is: " + saved_lcfilename);
                uploadLcDoc.setConstituent(consname);
                uploadLcDoc.setFileCatergory("Original");
                uploadLcDoc.setFileType(".pdf");
                uploadLcDoc.setSavedFilename(saved_lcfilename);
                uploadLcDoc.setSlno(new BigDecimal(slNo));
                uploadLcDoc.setUploadDate(entryDate);
                System.out.println("Before LC iD print ");
                loc.setLcId(new BigDecimal(reqNo));
                uploadLcDoc.setLetterOfCredit(loc);
                System.out.println("#######After LC iD print ");
                uploadLcDoc.setUserFilename(lcfilename);

                boolean sucflg = uploadLcDAO.NewUploadLoCDocumentsDetails(uploadLcDoc);
                System.out.println("sucflag value is: " + sucflg);
                if (sucflg) {
                    String filename = request.getParameter("lcfilename");
                    filename = slNo + "_" + filename;
                    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                    MultipartFile multipartFile = multipartRequest.getFile("lcfile");

                    ConstantsMasterDAO constadao = new ConstantsMasterDAO();
                    String filepath = constadao.getFilePathbyName("LOC_TEMPLATE");

                    System.out.print("File path is " + filepath);
                    String uploadPath = filepath;
                    String f = multipartFile.getOriginalFilename();
                    File originalfilename = new File(uploadPath + filename + ".pdf");
                    FileCopyUtils.copy(multipartFile.getBytes(), originalfilename);
                    System.out.println("Letter of Credit file uploaded successfully");

                } else {

                    ModelAndView mv1 = new ModelAndView("errorMsg");
                    mv1.addObject("msg", "Error in Uploading Details");
                    return mv1;
                }

                defLCDAO.getUpdateLOCFlag(Integer.parseInt(dafaultid));
                mav1.addObject("Msg", "Letter of Credit Details Registered Successfully");
                return mav1;
            }

        }

        List<DefaultLocDetails> list = null;
        list = defLCDAO.getAllPendingListForDefaultLC();
        model.addObject("UserLCList", list);
        model.addObject("corpList", corpList);
        return model;
    }

    public ModelAndView viewLcDetails(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        System.out.println("In viewLCdetails method of master controller");
        ModelAndView model = new ModelAndView("Masters/viewLcList");
        LetterOfCreditDAO utildao = new LetterOfCreditDAO();
        List<LetterOfCredit> list;
        String lcId = request.getParameter("lcId");
        System.out.println("lcId without parsing" + lcId);
        list = utildao.lcListbyid(new BigDecimal(lcId));
        if (list.size() > 0) {
            System.out.println("Size of list in viewLcDetails controller is" + list.size());
        }
        model.addObject("lclist", list);
        return model;
    }

    public ModelAndView viewDownloadLcFiles(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        String filename = request.getParameter("lcfilename");
//String filename=request.getParameter("filename");
// ServletContext context = request.getServletContext();
//String filepath = context.getInitParameter("file-upload");

        ConstantsMasterDAO cdao = new ConstantsMasterDAO();
        String filepath = cdao.getFilePathbyName("LOC_TEMPLATE");
        System.out.print("File path " + filepath);
        filepath = filepath + filename + ".pdf";//"C:\\OnlineABTFiles\\Village Culture.pdf";
        File file = new File(filepath, URLDecoder.decode(filepath, "UTF-8"));

        File file1 = new File(filepath);

        if (!file1.exists()) {
            ModelAndView mv = new ModelAndView("errorMsg");
            String msg = "The system cannot find the file specified";
            mv.addObject("msg", msg);
            return mv;
        }
        response.setContentType("application/pdf");

// response.setContentType("image/jpeg");
// response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        ServletOutputStream out1;
        out1 = response.getOutputStream();
        FileInputStream fin = new FileInputStream(filepath);
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out1);
        int ch = 0;
        while ((ch = bin.read()) != -1) {
            bout.write(ch);
        }
        bin.close();
        fin.close();
        bout.close();
        out1.close();

        return null;
    }

    public ModelAndView viewInterestDetails(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        System.out.println("In viewInterestDetails method of master controller");
        ModelAndView model = new ModelAndView("Masters/viewInterestRate");
        BillInterestRateDAO interestDao = new BillInterestRateDAO();
        List<BillInterestRate> list;

        list = interestDao.billInterestList();
        if (list.size() > 0) {
            System.out.println("Size of list in viewInterestDetails in master controller is" + list.size());
        }
        model.addObject("interestList", list);
        return model;
    }

    public ModelAndView viewNoInterestPeriodList(HttpServletRequest request,
            HttpServletResponse response) throws ParseException {
        HttpSession session = request.getSession(false);

        System.out.println("In viewNoInterestPeriodList method of master controller");
        ModelAndView model = new ModelAndView("Masters/viewNoInterestPeriodList");
        BillPaymentDuedateDAO paymentDuedateDao = new BillPaymentDuedateDAO();
        if (request.getParameter("bName") != null) {

            List<BillPaymentDuedate> list = paymentDuedateDao.getpendingBillPaymentDuedate();
            if (list != null && list.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "No Interest timeline is Pending at Checker . Please Clear it and Try Again...");
                return mv2;
            } else {
                ModelAndView mav1 = new ModelAndView("Masters/newinterestimeline");

                return mav1;
            }

        }
        if (request.getParameter("bName1") != null) {

            String startdate = request.getParameter("startdate");
            String billtype = request.getParameter("billtype");
            String category = request.getParameter("category");
            String noofduedates = request.getParameter("noofduedates");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
//            paymentDuedateDao.updatetodatebytypebycategory(billtype, category, startDate);

            BillPaymentDuedate billpayduedate = new BillPaymentDuedate();

            int uniqueId = 0;
            uniqueId = paymentDuedateDao.getMaxUniqueID();
            uniqueId = uniqueId + 1;
            billpayduedate.setSlno(new BigDecimal(uniqueId));
            billpayduedate.setBilltype(billtype);
            billpayduedate.setNoofdays(new BigDecimal(noofduedates));
            billpayduedate.setCategory(category);
            billpayduedate.setFromDate(startDate);
            Date d = new Date();
            billpayduedate.setEntryDate(d);
            billpayduedate.setStatus("Pending");
            paymentDuedateDao.NewBillPaymentDuedate(billpayduedate);

            ModelAndView mav1 = new ModelAndView("successMsg");
            mav1.addObject("Msg", "Successfully new interest timeline added by Maker");
            return mav1;

        }

        List<BillPaymentDuedate> list;

        list = paymentDuedateDao.getBillPaymentDuedateDetails();
        if (list.size() > 0) {
            System.out.println("Size of list in viewNoInterestPeriodList in master controller is" + list.size());
        }
        model.addObject("noInterestList", list);
        return model;
    }

    public ModelAndView checkerconfirmNoInterestPeriodList(HttpServletRequest request,
            HttpServletResponse response) throws ParseException {
        HttpSession session = request.getSession(false);

        ModelAndView model = new ModelAndView("Masters/viewcheckerNoInterestPeriodList");
        BillPaymentDuedateDAO paymentDuedateDao = new BillPaymentDuedateDAO();

        if (request.getParameter("bconfirm") != null) {

            List<BillPaymentDuedate> list = paymentDuedateDao.getpendingBillPaymentDuedate();
            if (list != null) {
                for (BillPaymentDuedate e : list) {
                    paymentDuedateDao.updatetodatebytypebycategory(e.getBilltype(), e.getCategory(), e.getFromDate());

                }
                Date d = new Date();

                paymentDuedateDao.updatestatusbychecker(d);
            }

            ModelAndView mav1 = new ModelAndView("successMsg");
            mav1.addObject("Msg", "Successfully new interest timeline added by checker");
            return mav1;

        }
        if (request.getParameter("bdelete") != null) {

            paymentDuedateDao.deleteBillPaymentDuedatebystatus();

            ModelAndView mav1 = new ModelAndView("successMsg");
            mav1.addObject("Msg", "Successfully new interest timeline deleted by checker");
            return mav1;

        }

        List<BillPaymentDuedate> list;

        list = paymentDuedateDao.getpendingBillPaymentDuedate();
//        if (list.size() > 0) {
//            System.out.println("Size of list in viewNoInterestPeriodList in master controller is" + list.size());
//        }
        model.addObject("noInterestList", list);
        return model;
    }

    public ModelAndView checkFromdateinttimeline(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        String datepicker = request.getParameter("datepicker");
        String billtype = request.getParameter("billtype");
        String category = request.getParameter("category");
        System.out.println("datepicker =" + datepicker);
        System.out.println("billtype =" + billtype);
        System.out.println("category =" + category);
        Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(datepicker);
        BillPaymentDuedateDAO paymentDuedateDao = new BillPaymentDuedateDAO();
        List<BillPaymentDuedate> billpayduelist = paymentDuedateDao.getBillPaymentDuedateforfromdate(billtype, category);
        System.out.println("from date =" + billpayduelist.get(0).getFromDate());
        Date date2 = billpayduelist.get(0).getFromDate();
        System.out.println(date2.before(startDate));
        System.out.println(date2.equals(startDate));
        System.out.println(date2.after(startDate));
        if (date2.after(startDate)) {
            response.getOutputStream().print("Selected date is not before the existing timeline date:" + date2);

        } else {
            response.getOutputStream().print("1");

        }

        return null;
    }

    public ModelAndView viewDisburseBillPriority(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        System.out.println("In viewDisburseBillPriorityDetails method of master controller");
        ModelAndView model = new ModelAndView("Masters/viewDisburseBillPriority");
        BillPriorityDAO billPriorityDao = new BillPriorityDAO();
        List<BillPriority> list;

        list = billPriorityDao.getBillPriorityDetails();
        if (list.size() > 0) {
            System.out.println("Size of list in viewDisburseBillPriorityDetails in master controller is" + list.size());
        }
        model.addObject("priorityList", list);
        return model;
    }

    public ModelAndView changePassword(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("changePassword");
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        if (request.getParameter("bname") != null) {
            ModelAndView mav1 = new ModelAndView("successMsg");
            String loginid = (String) request.getSession(false).getAttribute("loginid");
            System.out.println("Login id inside change password is: " + loginid);
            String passwd = request.getParameter("newpasswd");
            DesEncrypter desen = new DesEncrypter();
            UserDetailsDAO uddedao = new UserDetailsDAO();
            String enpasswd = desen.encrypt(passwd);
            uddedao.updatePasswordbyLoginID(loginid, enpasswd);
            mav1.addObject("Msg", "Successfully Password changed");
            return mav1;
        }
        return mav;
    }

    public ModelAndView checkForOldpasswd(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        String sname = null;
        List<Application> list = null;

        String oldpasswd = request.getParameter("elementName");
        System.out.println("old password  is :" + oldpasswd);
        String loginid = (String) request.getSession(false).getAttribute("loginid");

        System.out.println("@@@@@@@@@login id is :" + loginid);
        UserDetailsDAO utildao = new UserDetailsDAO();
        // int tridflag= utildao.checkforOldPasswd(loginid,oldpasswd);  
        UserDetails ud = new UserDetails();
        ud = utildao.getLoginDetailsbyLoginID(loginid);
        // System.out.print("old passwd Corpid:"+tridflag);
        DesEncrypter encrypter = new DesEncrypter();
        String decrypted = encrypter.decrypt(ud.getPassword());
        System.out.print("desc Password ID is : " + decrypted);
        // if(decrypted.equals(password))
        if ((decrypted.equals(oldpasswd))) {
            response.getOutputStream().print("Available");

        } else {
            response.getOutputStream().print("Check the Old password.");

        }
        return null;
    }

    public ModelAndView updatePasswordbySysAdmin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);

        ModelAndView mav1 = new ModelAndView("viewResetPasswordbySysAdmin");

        if (request.getParameter("bName") != null) {
            ModelAndView model = new ModelAndView("successMsg");
            String newpasswd = request.getParameter("newpasswd");
            String loginid = request.getParameter("loginid");
            DesEncrypter encrypter = new DesEncrypter();
            String encryptedPWD = encrypter.encrypt(newpasswd);
            UserDetailsDAO usedao = new UserDetailsDAO();
            System.out.print("loginid" + loginid);
            System.out.print("newpasswd" + newpasswd);

            usedao.updateUserPassword(loginid, encryptedPWD);
            model.addObject("Msg", "Password updated Succesfully for login id :" + loginid);
            return model;

        }
        List<UserDetails> list;
        UserDetailsDAO usdao = new UserDetailsDAO();
        list = usdao.getUserDetails();
        mav1.addObject("UserList", list);

        return mav1;
    }

    public ModelAndView viewOutDatedLetterOfCredit(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);

        ModelAndView mav1 = new ModelAndView("Masters/viewOutDatedLetterOfCredit");
        DefaultLocDetailsDAO defLCDAO = new DefaultLocDetailsDAO();

        List<Object[]> defaultLCList = new ArrayList<>();
        defaultLCList = defLCDAO.getListForDefaultLC();
        DefaultLocDetails defDetails = new DefaultLocDetails();
        BillPayableCorp billPayCorp = new BillPayableCorp();
        List<DefaultLocDetails> list = null;
        Corporates corp = new Corporates();
        int maxid = 0;
        for (Object[] result : defaultLCList) {
            BigDecimal billYear = (BigDecimal) result[0];
            BigDecimal totalNet = (BigDecimal) result[1];
            BigDecimal uniqueId = (BigDecimal) result[2];

            list = defLCDAO.getListForDefaultLCbyUniqueID(uniqueId.intValue());
            if (list != null && list.size() > 0) {

            } else {

                Date billDueDate = (Date) result[3];
                BigDecimal weekId = (BigDecimal) result[4];
                String bill_catregory = (String) result[5];
                String bill_priority = (String) result[6];
                Date paidDate = (Date) result[7];
                BigDecimal corpId = (BigDecimal) result[8];

                if (paidDate.compareTo(billDueDate) > 0) {

                    maxid = defLCDAO.getMaxSLNO();
                    maxid = maxid + 1;
                    defDetails.setSlno(new BigDecimal(maxid));
                    defDetails.setBillCategory(bill_catregory);
                    defDetails.setBillAmount(totalNet);
                    defDetails.setBillType(bill_priority);
                    defDetails.setBillYear(billYear);
                    defDetails.setWeekid(weekId);
                    defDetails.setLocFlag("0");
                    billPayCorp.setUniqueId(uniqueId);
                    defDetails.setEntryDate(new Date());
                    defDetails.setBillPayableCorp(billPayCorp);
                    corp.setCorporateId(corpId.intValueExact());
                    defDetails.setCorporates(corp);
                    defLCDAO.NewDefaultLoCDetails(defDetails);

                }
            }//end of checking existing of uniqueid

        }

        list = defLCDAO.getAllListForDefaultLC();
        mav1.addObject("UserLCList", list);
        return mav1;

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
