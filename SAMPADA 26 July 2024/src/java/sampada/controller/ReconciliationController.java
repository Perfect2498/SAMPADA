/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.AdjMapingDAO;
import sampada.DAO.AdjPaymentDAO;
import sampada.DAO.BillInterestRateDAO;
import sampada.DAO.BillPayableCorpDAO;
import sampada.DAO.BillReceiveCorpDAO;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.CsdfDetailsDAO;
import sampada.DAO.DisbursedInterestDetailsDAO;
import sampada.DAO.DynReconciliationCropDAO;
import sampada.DAO.InterestDetailsDAO;
import sampada.DAO.MappingInterestBankDAO;
import sampada.DAO.NewBillPayableCorpDAO;
import sampada.DAO.NewBillReceiveCorpDAO;
import sampada.DAO.TempDisbInterestDetailsDAO;
import sampada.DAO.TempInterestDetailsDAO;
import sampada.DAO.TempMappingBillBankDAO;
import sampada.DAO.TempPaymentDisbursementDAO;
import sampada.DAO.TempRefundBillCorpDAO;
import sampada.DAO.miscDisbursementDAO;
import sampada.pojo.AdjMapping;
import sampada.pojo.AdjPayment;
import sampada.pojo.BillInterestRate;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.Corporates;
import sampada.pojo.CsdfDetails;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.DynReconciliationCorp;
import sampada.pojo.InterestDetails;
import sampada.pojo.MappingInterestBank;
import sampada.pojo.MiscDisbursement;
import sampada.pojo.PaymentInterestDisbursement;
import sampada.pojo.ReconciliationCorp;
import sampada.pojo.TempDisbInterestDetails;
import sampada.pojo.TempInterestDetails;
import sampada.pojo.TempMappingBillBank;
import sampada.pojo.TempPaymentDisbursement;
import sampada.pojo.TempRefundBillCorp;

/**
 *
 * @author JaganMohan
 */
public class ReconciliationController extends MultiActionController {

    public ModelAndView reconciliationIndexRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("reconciliationIndexRLDC");
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

    public ModelAndView BillAdjustIndexRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("BillAdjustments/BillAdjustIndexRLDC");
        return mv;
    }

    public ModelAndView BillAdjustIndexMaker(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("BillAdjustments/BillAdjustIndexMaker");
        return mv;
    }

    public ModelAndView selectBillDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corps = corpdao.Corporateslist();

        ModelAndView mv = new ModelAndView("BillAdjustments/selectBillDetails");
        mv.addObject("corps", corps);
        return mv;
    }

    public ModelAndView viewAllCorpDetailsForAdjustments(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String bname = request.getParameter("badj");
        System.out.println("bname is :" + bname);
        if (bname != null) {

            String corpname = request.getParameter("corpname");
            CorporatesDAO corpdao = new CorporatesDAO();
            int corpid = corpdao.getCorpIdbyName(corpname);
            Corporates corp = new Corporates();
            corp.setCorporateId(corpid);
            String rowcount = request.getParameter("rowcount");
            int row_count = Integer.parseInt(rowcount);
            String rowcountrec = request.getParameter("rowcountrec");
            int row_countrec = Integer.parseInt(rowcountrec);
            String payable = request.getParameter("payable");
            String receivable = request.getParameter("receivable");
            String net = request.getParameter("net");
            String adjust = request.getParameter("adjust");
            String remarks = request.getParameter("remarks");
            System.out.println("rowcount is :" + rowcount);
            System.out.println("rowcountrec is :" + rowcountrec);
            System.out.println("payable is :" + payable);
            System.out.println("receivable is :" + receivable);
            System.out.println("net is :" + net);
            System.out.println("remarks is :" + remarks);

            AdjPaymentDAO adjpaymentdao = new AdjPaymentDAO();
            AdjMapingDAO adjmappingdao = new AdjMapingDAO();
            AdjPayment adjpay = new AdjPayment();

            int maxid = 0;
            maxid = adjpaymentdao.getMaxSlno().intValue();
            maxid = maxid + 1;
            adjpay.setUniqueId(new BigDecimal(maxid));
            adjpay.setAdjustAmt(new BigDecimal(adjust));
            adjpay.setCorporates(corp);
            adjpay.setCorpName(corpname);
            adjpay.setEntryDate(new Date());
            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            adjpay.setEntryTime(currentTimestamp);
            adjpay.setRemarks(remarks);
            adjpay.setStatus("Pending");
            adjpay.setTotalPay(new BigDecimal(payable));
            adjpay.setTotalRec(new BigDecimal(receivable));

            adjpaymentdao.NewAdjPayment(adjpay);

            for (int i = 1; i <= row_count; i++) {

                int checkedflag = 0;

                String uniqueID = request.getParameter("uniqueID" + i);
                String balamt = request.getParameter("balamt" + i);
                String balamtaft = request.getParameter("balamtaft" + i);
                String[] chkSms = request.getParameterValues("items");
                for (int j = 0; j < chkSms.length; j++) {
                    if (uniqueID.equalsIgnoreCase(chkSms[j].toString())) {

                        checkedflag = 1;
                    }
                }
                if (checkedflag == 1) {
                    System.out.println("uniqueID is :" + uniqueID);
                    AdjMapping adjmap = new AdjMapping();
                    int maxidrec = 0;
                    maxidrec = adjmappingdao.getMaxSlno().intValue();
                    maxidrec = maxidrec + 1;
                    adjmap.setSlNo(new BigDecimal(maxidrec));
                    adjmap.setCorporates(corp);
                    adjmap.setCorpName(corpname);
                    adjmap.setAdjPayment(adjpay);
                    adjmap.setStatus("Pending");

                    if (uniqueID.substring(0, 1).equalsIgnoreCase("B")) {
                        BillPayableCorp billpaycorp = new BillPayableCorp();
                        billpaycorp.setUniqueId(new BigDecimal(uniqueID.substring(1, uniqueID.length())));
                        adjmap.setBillPayableCorpByPayId(billpaycorp);
                        adjmap.setPayBal(new BigDecimal(balamt));
                        adjmap.setAdjustAmt(new BigDecimal(balamtaft).subtract(new BigDecimal(balamt)));

                    }
                    if (uniqueID.substring(0, 1).equalsIgnoreCase("R")) {
                        BillReceiveCorp billreccorp = new BillReceiveCorp();
                        billreccorp.setUniqueId(new BigDecimal(uniqueID.substring(1, uniqueID.length())));
                        adjmap.setBillReceiveCorpByRecRefId(billreccorp);
                        adjmap.setRecRefBal(new BigDecimal(balamt));
                        adjmap.setAdjustAmt(new BigDecimal(balamtaft).subtract(new BigDecimal(balamt)));
                    }
                    if (uniqueID.substring(0, 1).equalsIgnoreCase("I")) {
                        InterestDetails intdet = new InterestDetails();
                        intdet.setInterestId(new BigDecimal(uniqueID.substring(1, uniqueID.length())));
                        adjmap.setInterestDetails(intdet);
                        adjmap.setPayIntBal(new BigDecimal(balamt));
                        adjmap.setAdjustAmt(new BigDecimal(balamtaft).subtract(new BigDecimal(balamt)));
                    }

                    adjmappingdao.NewAdjMaping(adjmap);
                }

            }
            for (int i = 1; i <= row_countrec; i++) {

                int checkedflag = 0;

                String uniquerecID = request.getParameter("uniquerecID" + i);
                String balamtrec = request.getParameter("balamtrec" + i);
                String balamtrecaft = request.getParameter("balamtrecaft" + i);
                String[] chkSms = request.getParameterValues("itemsrec");

                for (int j = 0; j < chkSms.length; j++) {
                    if (uniquerecID.equalsIgnoreCase(chkSms[j].toString())) {

                        checkedflag = 1;
                    }
                }
                if (checkedflag == 1) {
                    System.out.println("uniquerecID is :" + uniquerecID);
                    AdjMapping adjmap = new AdjMapping();
                    int maxidrec = 0;
                    maxidrec = adjmappingdao.getMaxSlno().intValue();
                    maxidrec = maxidrec + 1;
                    adjmap.setSlNo(new BigDecimal(maxidrec));
                    adjmap.setCorporates(corp);
                    adjmap.setCorpName(corpname);
                    adjmap.setAdjPayment(adjpay);
                    adjmap.setStatus("Pending");

                    if (uniquerecID.substring(0, 1).equalsIgnoreCase("B")) {
                        BillReceiveCorp billreccorp = new BillReceiveCorp();
                        billreccorp.setUniqueId(new BigDecimal(uniquerecID.substring(1, uniquerecID.length())));
                        adjmap.setBillReceiveCorpByRecvId(billreccorp);
                        adjmap.setRecBal(new BigDecimal(balamtrec));
                        adjmap.setAdjustAmt(new BigDecimal(balamtrecaft).subtract(new BigDecimal(balamtrec)));
                    }
                    if (uniquerecID.substring(0, 1).equalsIgnoreCase("R")) {
                        BillPayableCorp billpaycorp = new BillPayableCorp();
                        billpaycorp.setUniqueId(new BigDecimal(uniquerecID.substring(1, uniquerecID.length())));
                        adjmap.setBillPayableCorpByPayRefId(billpaycorp);
                        adjmap.setPayRefBal(new BigDecimal(balamtrec));
                        adjmap.setAdjustAmt(new BigDecimal(balamtrecaft).subtract(new BigDecimal(balamtrec)));
                    }
                    if (uniquerecID.substring(0, 1).equalsIgnoreCase("I")) {
                        DisbursedInterestDetails disintdet = new DisbursedInterestDetails();
                        disintdet.setInterestId(new BigDecimal(uniquerecID.substring(1, uniquerecID.length())));
                        adjmap.setDisbursedInterestDetails(disintdet);
                        adjmap.setRecIntBal(new BigDecimal(balamtrec));
                        adjmap.setAdjustAmt(new BigDecimal(balamtrecaft).subtract(new BigDecimal(balamtrec)));
                    }

                    adjmappingdao.NewAdjMaping(adjmap);
                }

            }

            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully submitted the Adjustment transactions for Verification.......");
            return mv1;
        }

        String corpname = request.getParameter("corpname");

        CorporatesDAO corpdao = new CorporatesDAO();
        int corpid = corpdao.getCorpIdbyName(corpname);

        NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
        NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();
        InterestDetailsDAO iddao = new InterestDetailsDAO();
        DisbursedInterestDetailsDAO diddao = new DisbursedInterestDetailsDAO();
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();

        List<BillPayableCorp> allpend1pay = bpdao.getPendingBillPayableByCorpId(corpid);
        List<BillReceiveCorp> allpend2pay = brdao.getPendingRefundBillReceiveListByCorpId(corpid);

        List<BillPayableCorp> allpend1rec = bpdao.getPendingRefundPaylistByCorpId(corpid);
        List<BillReceiveCorp> allpend2rec = brdao.getPendingRefundRecListByCorpId(corpid);

        List<InterestDetails> intpay = iddao.getPendingInterestPayableListByCorpid(corpid);
        List<DisbursedInterestDetails> intrec = diddao.getPendingDisbursedInterestDetailsListByCorpId(corpid);

        List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListbyCorp(corpid);

        System.out.println("allpend1pay size = " + allpend1pay.size());
        System.out.println("allpend2pay size = " + allpend2pay.size());
        System.out.println("allpend1rec size = " + allpend1rec.size());
        System.out.println("allpend2rec size = " + allpend2rec.size());
        System.out.println("intpay size = " + intpay.size());
        System.out.println("intrec size = " + intrec.size());
        if ((allpend1pay.size() == 0 && allpend2pay.size() == 0 && intpay.size() == 0)) {
            return new ModelAndView("successMsg").addObject("Msg", "PAYABLE SIDE bills NOT present. Cannot adjust bills for " + corpname);
        }
        if ((allpend1rec.size() == 0 && allpend2rec.size() == 0 && intrec.size() == 0)) {
            return new ModelAndView("successMsg").addObject("Msg", "RECEIVABLE side bills NOT present. Cannot adjust bills for " + corpname);
        }
        if (adjpatlist.size() != 0) {
            return new ModelAndView("successMsg").addObject("Msg", "Adjust transactions are pending at checker, please clear for " + corpname);

        }

        MappingInterestBankDAO mapIntDao = new MappingInterestBankDAO();
        List<MappingInterestBank> pendingCheckerInterest = mapIntDao.getCheckerPendingInterestCountForCorporate(corpid);
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

        List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempMappingBillBankbyCorp(corpid, "Pending");
        List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getAllRefundPayablePendingTempRefundBillCorpByChecker(corpid);
        if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
            ModelAndView mv9 = new ModelAndView("successMsg");
            String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
            mv9.addObject("Msg", Msg);
            return mv9;
        }

        miscDisbursementDAO miscdao = new miscDisbursementDAO();
        List<MiscDisbursement> listmiscdis = null;
        listmiscdis = miscdao.getmiscDetails("Bills");
//            System.out.print("Inside misc button" + listmiscdis.size());
        if (listmiscdis != null && listmiscdis.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Misc Bill Disbursement Record is pending with checker...Please clear it");
            return mv2;

        }
        List<MiscDisbursement> listmiscdisint = null;
        listmiscdisint = miscdao.getmiscDetails("Interest");
//            System.out.print("Inside misc button" + listmiscdisint.size());
        if (listmiscdisint != null && listmiscdisint.size() > 0) {
            ModelAndView mv2 = new ModelAndView("successMsg");
            mv2.addObject("Msg", "Misc Interest Disbursement Record is pending with checker...Please clear it");
            return mv2;

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

        ModelAndView mv = new ModelAndView("BillAdjustments/viewAllCorpDetailsForAdjustments");
        mv.addObject("corpname", corpname);
        mv.addObject("allpend1pay", allpend1pay);
        mv.addObject("allpend2pay", allpend2pay);
        mv.addObject("allpend1rec", allpend1rec);
        mv.addObject("allpend2rec", allpend2rec);
        mv.addObject("intpay", intpay);
        mv.addObject("intrec", intrec);
        return mv;
    }

    public ModelAndView adjustchecker(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        AdjPaymentDAO adjpaymentdao = new AdjPaymentDAO();

        ModelAndView mv = new ModelAndView("BillAdjustments/viewPendingCorpList");

        List<Object[]> list1 = null;
        List<Corporates> listcorp = new ArrayList<>();
        list1 = adjpaymentdao.getAllPendingAdjPaymentObjectlistforChecker();
        Corporates corp = null;
        for (Object[] obj : list1) {

            corp = new Corporates();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            listcorp.add(corp);
        }
        mv.addObject("listcorp", listcorp);
        return mv;
    }

    public ModelAndView viewCheckerAdjustmentDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        AdjPaymentDAO adjpaymentdao = new AdjPaymentDAO();
        AdjMapingDAO adjmappingdao = new AdjMapingDAO();
        String corpid = request.getParameter("corpID");
        String bconfirm = request.getParameter("bconfirm");
        String bdelete = request.getParameter("bdelete");
        if (bconfirm != null) {

            String corp = request.getParameter("corpid");
            CorporatesDAO corpdao = new CorporatesDAO();
            BillPayableCorpDAO bipaydao = new BillPayableCorpDAO();
            BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
            DisbursedInterestDetailsDAO disinterestdao = new DisbursedInterestDetailsDAO();
            InterestDetailsDAO interesdao = new InterestDetailsDAO();
            TempInterestDetailsDAO tempintesdao = new TempInterestDetailsDAO();
            TempDisbInterestDetailsDAO tempdusdao = new TempDisbInterestDetailsDAO();
            DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();
            List<AdjMapping> adjmaplist = null;

            String corpname = corpdao.getCorpNamebyId(Integer.parseInt(corp));
            System.out.println("corpname =" + corpname);
            BigDecimal bg = adjpaymentdao.getPendingAdjPaymentIDforChecker(Integer.parseInt(corp));
            System.out.println("bg =" + bg);
            adjmaplist = adjmappingdao.getPendingAdjMappingListbyUniqueid(bg);

            for (AdjMapping adjmap : adjmaplist) {

                if (adjmap.getBillPayableCorpByPayId() != null && adjmap.getPayBal() != adjmap.getBillPayableCorpByPayId().getPendingAmount()) {
                    System.out.println("pay_id==" + adjmap.getBillPayableCorpByPayId().getUniqueId());
                    BigDecimal paid = adjmap.getBillPayableCorpByPayId().getPendingAmount().subtract(adjmap.getPayBal());
                    BigDecimal totalmapped = adjmap.getBillPayableCorpByPayId().getPaidAmount().add(paid);
                    String status1 = null;
                    if (adjmap.getPayBal().compareTo(BigDecimal.ZERO) == 0) {
                        status1 = "PAID";

                    } else {
                        status1 = "PARTIALLY";
                    }
                    bipaydao.getUpdateBillPayableCorpbyChecker(adjmap.getBillPayableCorpByPayId().getUniqueId().intValue(), totalmapped, adjmap.getPayBal(), status1);
//#############################################################dyn recon
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(adjmap.getBillPayableCorpByPayId().getCorporates());
                    reconcorp.setWeekId(adjmap.getBillPayableCorpByPayId().getWeekId());
                    reconcorp.setBillEntryDate(adjmap.getAdjPayment().getEntryDate());
                    reconcorp.setBillType(adjmap.getBillPayableCorpByPayId().getBillType());
                    reconcorp.setBillingDate(adjmap.getBillPayableCorpByPayId().getBillingDate());
                    reconcorp.setRevisionNo(adjmap.getBillPayableCorpByPayId().getRevisionNo());
                    reconcorp.setBillDueDate(adjmap.getBillPayableCorpByPayId().getBillDueDate());
                    reconcorp.setPayTotalnet(adjmap.getBillPayableCorpByPayId().getTotalnet());
                    reconcorp.setEntryDate(new Date());
                    reconcorp.setBillYear(adjmap.getBillPayableCorpByPayId().getBillYear());
                    reconcorp.setPayFinalamount(adjmap.getPayBal().add(adjmap.getAdjustAmt()));
                    reconcorp.setPayPendingamount(adjmap.getPayBal());
                    reconcorp.setCrDrDate(new Date());
                    reconcorp.setCrAmount(adjmap.getAdjustAmt());
                    reconcorp.setCrSettledAmount(adjmap.getAdjustAmt());
//                    reconcorp.setCrAvailable(ele1.getPendingBankAmount());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(adjmap.getBillPayableCorpByPayId().getCorporates().getCorporateId());
                    outstanding = outstanding.subtract(adjmap.getAdjustAmt());
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Adjustment Mapping");
                    reconcorpdao.NewReconciliationCorp(reconcorp);
//#############################################################           
                    Date bill_duedate = adjmap.getBillPayableCorpByPayId().getBillDueDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date3 = sdf.format(bill_duedate);
                    Date creditdate = new Date();
                    String date4 = sdf.format(creditdate);
                    Date date1 = sdf.parse(date3);
                    Date date2 = sdf.parse(date4);
                    int interestflag = 0;
                    float daysBetween = 0;
                    BillInterestRateDAO billintedao = new BillInterestRateDAO();
                    List<BillInterestRate> listintes = null;
                    listintes = billintedao.getBillInterestRate("DSM", "PAYABLE");
                    if (date1.compareTo(date2) < 0 && paid.compareTo(BigDecimal.ONE) >= 0) {
                        long difference = date2.getTime() - date1.getTime();
                        daysBetween = (difference / (1000 * 60 * 60 * 24));
                        System.out.println("daysBetween" + daysBetween);
                        BigDecimal inbg = paid.multiply(new BigDecimal(daysBetween)).multiply(listintes.get(0).getInterestRate());
                        if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {
                            interestflag = 1;
                        }
                        System.out.println("inbg" + inbg);
                    }
                    System.out.println("interestflag" + interestflag);
                    if (interestflag == 1) {
                        TempInterestDetails tempintes = new TempInterestDetails();
                        Corporates corpint = new Corporates();
                        int maxintesid = tempintesdao.getMaxInterestid();
                        maxintesid = maxintesid + 1;
                        BigDecimal noofday = new BigDecimal(daysBetween);
                        tempintes.setNoofdays(new BigDecimal(daysBetween));
                        tempintes.setInterestId(new BigDecimal(maxintesid));
                        tempintes.setBillCategory(adjmap.getBillPayableCorpByPayId().getBillCategory());
                        tempintes.setBillType(adjmap.getBillPayableCorpByPayId().getBillType());

                        tempintes.setBilledAmount(adjmap.getBillPayableCorpByPayId().getTotalnet());

                        tempintes.setBillingDate(adjmap.getBillPayableCorpByPayId().getBillingDate());
                        tempintes.setBillingDuedate(adjmap.getBillPayableCorpByPayId().getBillDueDate());
                        tempintes.setCheckerStatus("Pending");
                        corpint.setCorporateId(adjmap.getBillPayableCorpByPayId().getCorporates().getCorporateId());
                        tempintes.setCorporates(corpint);
                        tempintes.setEntryDate(new Date());
                        tempintes.setInterestBilledamount(paid);
                        BigDecimal bgInrate = listintes.get(0).getInterestRate();
                        System.out.println("Interest rate is:" + bgInrate);
                        BigDecimal inbg = paid.multiply(noofday).multiply(bgInrate);
                        System.out.println("Interest amount is:" + inbg);
                        tempintes.setInterestAmount(inbg);
                        tempintes.setPaidAmount(paid);
                        tempintes.setPaidDate(creditdate);
                        tempintes.setWeekId(adjmap.getBillPayableCorpByPayId().getWeekId());
                        tempintes.setBillYear(adjmap.getBillPayableCorpByPayId().getBillYear());
                        tempintes.setRevisionNo(adjmap.getBillPayableCorpByPayId().getRevisionNo());
                        tempintesdao.NewTempInterestDetails(tempintes);
                    }

                }
                if (adjmap.getBillReceiveCorpByRecvId() != null && adjmap.getRecBal() != adjmap.getBillReceiveCorpByRecvId().getPendingAmount()) {
                    System.out.println("rec_Id()==" + adjmap.getBillReceiveCorpByRecvId().getUniqueId());
                    BigDecimal paid = adjmap.getBillReceiveCorpByRecvId().getPendingAmount().subtract(adjmap.getRecBal());
                    BigDecimal totalmapped = adjmap.getBillReceiveCorpByRecvId().getDisburseAmount().add(paid);
                    String status1 = null;
                    if (adjmap.getRecBal().compareTo(BigDecimal.ZERO) == 0) {
                        status1 = "PAID";

                    } else {
                        status1 = "PARTIALLY";
                    }
                    billrecvdao.getUpdateBillReceiveCorpbyChecker(adjmap.getBillReceiveCorpByRecvId().getUniqueId(), totalmapped, adjmap.getRecBal(), status1);

//#############################################################dyn recon
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(adjmap.getBillReceiveCorpByRecvId().getCorporates());
                    reconcorp.setWeekId(adjmap.getBillReceiveCorpByRecvId().getWeekId());
                    reconcorp.setBillEntryDate(adjmap.getAdjPayment().getEntryDate());
                    reconcorp.setBillType(adjmap.getBillReceiveCorpByRecvId().getBillType());
                    reconcorp.setBillingDate(adjmap.getBillReceiveCorpByRecvId().getBillingDate());
                    reconcorp.setRevisionNo(adjmap.getBillReceiveCorpByRecvId().getRevisionNo());
                    reconcorp.setBillDueDate(adjmap.getBillReceiveCorpByRecvId().getBillDueDate());
                    reconcorp.setRecTotalnet(adjmap.getBillReceiveCorpByRecvId().getToalnet());
                    reconcorp.setEntryDate(new Date());
                    reconcorp.setBillYear(adjmap.getBillReceiveCorpByRecvId().getBillYear());
                    reconcorp.setRecFinalamount(adjmap.getRecBal().add(adjmap.getAdjustAmt()));
                    reconcorp.setRecPendingamount(adjmap.getRecBal());
                    reconcorp.setCrDrDate(new Date());
                    reconcorp.setDrAmount(adjmap.getAdjustAmt());
                    reconcorp.setDrSettledAmount(adjmap.getAdjustAmt());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(adjmap.getBillReceiveCorpByRecvId().getCorporates().getCorporateId());
                    outstanding = outstanding.add(adjmap.getAdjustAmt());
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Adjustment Disbursed");
                    reconcorpdao.NewReconciliationCorp(reconcorp);
//#############################################################   

                    Date bill_duedate = adjmap.getBillReceiveCorpByRecvId().getBillDueDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date3 = sdf.format(bill_duedate);
                    Date creditdate = new Date();
                    String date4 = sdf.format(creditdate);
                    Date date1 = sdf.parse(date3);
                    Date date2 = sdf.parse(date4);
                    int interestflag = 0;
                    float daysBetween = 0;
                    BillInterestRateDAO billintedao = new BillInterestRateDAO();
                    List<BillInterestRate> listintes = null;
                    listintes = billintedao.getBillInterestRate("DSM", "RECEIVABLE");
                    if (date1.compareTo(date2) < 0 && paid.compareTo(BigDecimal.ONE) >= 0) {
                        long difference = date2.getTime() - date1.getTime();
                        daysBetween = (difference / (1000 * 60 * 60 * 24));
                        System.out.println("daysBetween" + daysBetween);
                        BigDecimal inbg = paid.multiply(new BigDecimal(daysBetween)).multiply(listintes.get(0).getInterestRate());
                        if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {
                            interestflag = 1;
                        }
                        System.out.println("inbg" + inbg);
                    }
                    System.out.println("interestflag" + interestflag);
                    if (interestflag == 1) {
                        TempDisbInterestDetails tempdisine = new TempDisbInterestDetails();
                        int maxid = 0;

                        maxid = tempdusdao.getMaxDisInterestid();

                        maxid = maxid + 1;

                        BigDecimal bgmax = new BigDecimal(maxid);

                        tempdisine.setInterestId(bgmax);

                        tempdisine.setWeekId(adjmap.getBillReceiveCorpByRecvId().getWeekId());

                        tempdisine.setRevisionNo(adjmap.getBillReceiveCorpByRecvId().getRevisionNo());

                        tempdisine.setBillYear(adjmap.getBillReceiveCorpByRecvId().getBillYear());

                        tempdisine.setBillCategory(adjmap.getBillReceiveCorpByRecvId().getBillCategory());

                        tempdisine.setBillType(adjmap.getBillReceiveCorpByRecvId().getBillType());

                        tempdisine.setBilledAmount(adjmap.getBillReceiveCorpByRecvId().getToalnet());

                        tempdisine.setBillingDate(adjmap.getBillReceiveCorpByRecvId().getBillingDate());

                        tempdisine.setBillingDuedate(adjmap.getBillReceiveCorpByRecvId().getBillDueDate());

                        tempdisine.setCheckerStatus("Pending");

                        tempdisine.setCorporates(adjmap.getBillReceiveCorpByRecvId().getCorporates());

                        tempdisine.setDisbursedAmount(paid);

                        tempdisine.setDisbursedDate(date2);

                        tempdisine.setEntryDate(new Date());

                        BigDecimal bginrate = listintes.get(0).getInterestRate();

                        BigDecimal inteamt = paid.multiply(bginrate);

                        BigDecimal days = new BigDecimal(daysBetween);

                        inteamt = inteamt.multiply(days);

                        tempdisine.setInterestAmount(inteamt);

                        tempdisine.setNoofdays(days);

                        tempdisine.setInterestBilledamount(paid);

                        tempdusdao.NewTempDisbInterestDetails(tempdisine);
                    }

                }
                if (adjmap.getBillPayableCorpByPayRefId() != null && adjmap.getPayRefBal() != adjmap.getBillPayableCorpByPayRefId().getPendingAmount()) {
                    System.out.println("pay_ref_Id()==" + adjmap.getBillPayableCorpByPayRefId().getUniqueId());
                    BigDecimal paid = adjmap.getBillPayableCorpByPayRefId().getPendingAmount().subtract(adjmap.getPayRefBal());
                    BigDecimal totalmapped = adjmap.getBillPayableCorpByPayRefId().getAdjustmentAmount().add(paid);
                    bipaydao.getUpdateRefundBillPayableCorpbyChecker(adjmap.getBillPayableCorpByPayRefId().getUniqueId().intValue(), totalmapped, adjmap.getPayRefBal());

//###########################################################dyn recon
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(adjmap.getBillPayableCorpByPayRefId().getCorporates());
                    reconcorp.setWeekId(adjmap.getBillPayableCorpByPayRefId().getWeekId());
                    reconcorp.setBillEntryDate(adjmap.getAdjPayment().getEntryDate());
                    reconcorp.setBillType(adjmap.getBillPayableCorpByPayRefId().getBillType());
                    reconcorp.setBillingDate(adjmap.getBillPayableCorpByPayRefId().getBillingDate());
                    reconcorp.setRevisionNo(adjmap.getBillPayableCorpByPayRefId().getRevisionNo());
                    reconcorp.setBillDueDate(adjmap.getBillPayableCorpByPayRefId().getBillDueDate());
                    reconcorp.setRecTotalnet(adjmap.getBillPayableCorpByPayRefId().getTotalnet());
                    reconcorp.setEntryDate(new Date());
                    reconcorp.setBillYear(adjmap.getBillPayableCorpByPayRefId().getBillYear());
                    reconcorp.setRecFinalamount(adjmap.getPayRefBal().add(adjmap.getAdjustAmt()));
                    reconcorp.setRecPendingamount(adjmap.getPayRefBal());
                    reconcorp.setCrDrDate(new Date());
                    reconcorp.setDrAmount(adjmap.getAdjustAmt());
                    reconcorp.setDrSettledAmount(adjmap.getAdjustAmt());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(adjmap.getBillPayableCorpByPayRefId().getCorporates().getCorporateId());
                    outstanding = outstanding.add(adjmap.getAdjustAmt());
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Adjustment Refund Disbursed");
                    reconcorpdao.NewReconciliationCorp(reconcorp);
//#############################################################                              

                    Date bill_duedate = adjmap.getBillPayableCorpByPayRefId().getBillDueDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date3 = sdf.format(bill_duedate);
                    Date creditdate = new Date();
                    String date4 = sdf.format(creditdate);
                    Date date1 = sdf.parse(date3);
                    Date date2 = sdf.parse(date4);
                    int interestflag = 0;
                    float daysBetween = 0;
                    BillInterestRateDAO billintedao = new BillInterestRateDAO();
                    List<BillInterestRate> listintes = null;
                    listintes = billintedao.getBillInterestRate("DSM", "PAYABLE");
                    if (date1.compareTo(date2) < 0 && paid.compareTo(BigDecimal.ONE) >= 0) {
                        long difference = date2.getTime() - date1.getTime();
                        daysBetween = (difference / (1000 * 60 * 60 * 24));
                        System.out.println("daysBetween" + daysBetween);
                        BigDecimal inbg = paid.multiply(new BigDecimal(daysBetween)).multiply(listintes.get(0).getInterestRate());
                        if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {
                            interestflag = 1;
                        }
                        System.out.println("inbg" + inbg);
                    }
                    System.out.println("interestflag" + interestflag);
                    if (interestflag == 1) {

                        TempDisbInterestDetails tempdisine = new TempDisbInterestDetails();
                        int maxid = 0;

                        maxid = tempdusdao.getMaxDisInterestid();

                        maxid = maxid + 1;

                        BigDecimal bgmax = new BigDecimal(maxid);

                        tempdisine.setInterestId(bgmax);

                        tempdisine.setWeekId(adjmap.getBillPayableCorpByPayRefId().getWeekId());

                        tempdisine.setRevisionNo(adjmap.getBillPayableCorpByPayRefId().getRevisionNo());

                        tempdisine.setBillYear(adjmap.getBillPayableCorpByPayRefId().getBillYear());

                        tempdisine.setBillCategory(adjmap.getBillPayableCorpByPayRefId().getBillCategory());

                        tempdisine.setBillType(adjmap.getBillPayableCorpByPayRefId().getBillType());

                        tempdisine.setBilledAmount(adjmap.getBillPayableCorpByPayRefId().getTotalnet());

                        tempdisine.setBillingDate(adjmap.getBillPayableCorpByPayRefId().getBillingDate());

                        tempdisine.setBillingDuedate(adjmap.getBillPayableCorpByPayRefId().getBillDueDate());

                        tempdisine.setCheckerStatus("Pending");

                        tempdisine.setCorporates(adjmap.getBillPayableCorpByPayRefId().getCorporates());

                        tempdisine.setDisbursedAmount(paid);

                        tempdisine.setDisbursedDate(date2);

                        tempdisine.setEntryDate(new Date());

                        BigDecimal bginrate = listintes.get(0).getInterestRate();

                        BigDecimal inteamt = paid.multiply(bginrate);

                        BigDecimal days = new BigDecimal(daysBetween);

                        inteamt = inteamt.multiply(days);

                        tempdisine.setInterestAmount(inteamt);

                        tempdisine.setNoofdays(days);

                        tempdisine.setInterestBilledamount(paid);

                        tempdusdao.NewTempDisbInterestDetails(tempdisine);
                    }

                }
                if (adjmap.getBillReceiveCorpByRecRefId() != null && adjmap.getRecRefBal() != adjmap.getBillReceiveCorpByRecRefId().getPendingAmount()) {
                    System.out.println("rec_ref_Id()==" + adjmap.getBillReceiveCorpByRecRefId().getUniqueId());
                    //System.out.println("adjmap.getBillReceiveCorpByRecRefId().getPendingAmount()==" + adjmap.getBillReceiveCorpByRecRefId().getPendingAmount());
                    //System.out.println("adjmap.getRecRefBal()==" + adjmap.getRecRefBal());
                    BigDecimal paid = adjmap.getBillReceiveCorpByRecRefId().getPendingAmount().subtract(adjmap.getRecRefBal());
                    BigDecimal totalmapped = adjmap.getBillReceiveCorpByRecRefId().getAdjustmentAmount().add(paid);
                    billrecvdao.getUpdateRefundBillReceivableCorpbyChecker(adjmap.getBillReceiveCorpByRecRefId().getUniqueId().intValue(), totalmapped, adjmap.getRecRefBal());

//#############################################################dyn recon
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(adjmap.getBillReceiveCorpByRecRefId().getCorporates());
                    reconcorp.setWeekId(adjmap.getBillReceiveCorpByRecRefId().getWeekId());
                    reconcorp.setBillEntryDate(adjmap.getAdjPayment().getEntryDate());
                    reconcorp.setBillType(adjmap.getBillReceiveCorpByRecRefId().getBillType());
                    reconcorp.setBillingDate(adjmap.getBillReceiveCorpByRecRefId().getBillingDate());
                    reconcorp.setRevisionNo(adjmap.getBillReceiveCorpByRecRefId().getRevisionNo());
                    reconcorp.setBillDueDate(adjmap.getBillReceiveCorpByRecRefId().getBillDueDate());
                    reconcorp.setPayTotalnet(adjmap.getBillReceiveCorpByRecRefId().getToalnet());
                    reconcorp.setEntryDate(new Date());
                    reconcorp.setBillYear(adjmap.getBillReceiveCorpByRecRefId().getBillYear());
                    reconcorp.setPayFinalamount(adjmap.getRecRefBal().add(adjmap.getAdjustAmt()));
                    reconcorp.setPayPendingamount(adjmap.getRecRefBal());
                    reconcorp.setCrDrDate(new Date());
                    reconcorp.setCrAmount(adjmap.getAdjustAmt());
                    reconcorp.setCrSettledAmount(adjmap.getAdjustAmt());
//                    reconcorp.setCrAvailable(ele1.getPendingBankAmount());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(adjmap.getBillReceiveCorpByRecRefId().getCorporates().getCorporateId());
                    outstanding = outstanding.subtract(adjmap.getAdjustAmt());
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Adjustment Refund Mapping");
                    reconcorpdao.NewReconciliationCorp(reconcorp);
//#############################################################                    

                    Date bill_duedate = adjmap.getBillReceiveCorpByRecRefId().getBillDueDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date3 = sdf.format(bill_duedate);
                    Date creditdate = new Date();
                    String date4 = sdf.format(creditdate);
                    Date date1 = sdf.parse(date3);
                    Date date2 = sdf.parse(date4);
                    int interestflag = 0;
                    float daysBetween = 0;
                    BillInterestRateDAO billintedao = new BillInterestRateDAO();
                    List<BillInterestRate> listintes = null;
                    listintes = billintedao.getBillInterestRate("DSM", "RECEIVABLE");
                    if (date1.compareTo(date2) < 0 && paid.compareTo(BigDecimal.ONE) >= 0) {
                        long difference = date2.getTime() - date1.getTime();
                        daysBetween = (difference / (1000 * 60 * 60 * 24));
                        System.out.println("daysBetween" + daysBetween);
                        BigDecimal inbg = paid.multiply(new BigDecimal(daysBetween)).multiply(listintes.get(0).getInterestRate());
                        if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {
                            interestflag = 1;
                        }
                        System.out.println("inbg" + inbg);
                    }
                    System.out.println("interestflag" + interestflag);
                    if (interestflag == 1) {
                        TempInterestDetails tempintes = new TempInterestDetails();
                        Corporates corpint = new Corporates();
                        int maxintesid = tempintesdao.getMaxInterestid();
                        maxintesid = maxintesid + 1;
                        BigDecimal noofday = new BigDecimal(daysBetween);
                        tempintes.setNoofdays(new BigDecimal(daysBetween));
                        tempintes.setInterestId(new BigDecimal(maxintesid));
                        tempintes.setBillCategory(adjmap.getBillReceiveCorpByRecRefId().getBillCategory());
                        tempintes.setBillType(adjmap.getBillReceiveCorpByRecRefId().getBillType());

                        tempintes.setBilledAmount(adjmap.getBillReceiveCorpByRecRefId().getToalnet());

                        tempintes.setBillingDate(adjmap.getBillReceiveCorpByRecRefId().getBillingDate());
                        tempintes.setBillingDuedate(adjmap.getBillReceiveCorpByRecRefId().getBillDueDate());
                        tempintes.setCheckerStatus("Pending");
                        corpint.setCorporateId(adjmap.getBillReceiveCorpByRecRefId().getCorporates().getCorporateId());
                        tempintes.setCorporates(corpint);
                        tempintes.setEntryDate(new Date());
                        tempintes.setInterestBilledamount(paid);
                        BigDecimal bgInrate = listintes.get(0).getInterestRate();
                        System.out.println("Interest rate is:" + bgInrate);
                        BigDecimal inbg = paid.multiply(noofday).multiply(bgInrate);
                        System.out.println("Interest amount is:" + inbg);
                        tempintes.setInterestAmount(inbg);
                        tempintes.setPaidAmount(paid);
                        tempintes.setPaidDate(creditdate);
                        tempintes.setWeekId(adjmap.getBillReceiveCorpByRecRefId().getWeekId());
                        tempintes.setBillYear(adjmap.getBillReceiveCorpByRecRefId().getBillYear());
                        tempintes.setRevisionNo(adjmap.getBillReceiveCorpByRecRefId().getRevisionNo());
                        tempintesdao.NewTempInterestDetails(tempintes);
                    }
                }
                if (adjmap.getInterestDetails() != null && adjmap.getPayIntBal() != adjmap.getInterestDetails().getInterestPendingamount()) {
                    System.out.println("pay_int_Id()==" + adjmap.getInterestDetails().getInterestId());
                    BigDecimal paid = adjmap.getInterestDetails().getInterestPendingamount().subtract(adjmap.getPayIntBal());
                    BigDecimal paidamount = BigDecimal.ZERO;
                    if (adjmap.getInterestDetails().getInterestPaidamount() != null) {
                        paidamount = adjmap.getInterestDetails().getInterestPaidamount();
                    }
                    BigDecimal totalmapped = paidamount.add(paid);
                    interesdao.getUpdateInterestPayablebyId(adjmap.getInterestDetails().getInterestId().intValue(), totalmapped, adjmap.getPayIntBal());
                    interesdao.getUpdateInterestPayableStatusbyChecker(adjmap.getCorporates().getCorporateId());

//#############################################################dyn recon
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(adjmap.getCorporates());
                    reconcorp.setWeekId(adjmap.getInterestDetails().getWeekId());
                    reconcorp.setBillEntryDate(adjmap.getAdjPayment().getEntryDate());
                    reconcorp.setBillType(adjmap.getInterestDetails().getBillType());
                    reconcorp.setBillingDate(adjmap.getInterestDetails().getBillingDate());
                    reconcorp.setRevisionNo(adjmap.getInterestDetails().getRevisionNo());
                    reconcorp.setBillDueDate(adjmap.getInterestDetails().getBillingDuedate());
                    reconcorp.setPayTotalnet(adjmap.getInterestDetails().getInterestAmount());
                    reconcorp.setEntryDate(new Date());
                    Date dateyear = adjmap.getInterestDetails().getBillingDate();
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                    cal.setTime(dateyear);
                    int year = cal.get(Calendar.YEAR);
                    reconcorp.setBillYear(adjmap.getInterestDetails().getBillYear());
                    reconcorp.setPayFinalamount(adjmap.getPayIntBal().add(adjmap.getAdjustAmt()));
                    reconcorp.setPayPendingamount(adjmap.getPayIntBal());
                    reconcorp.setCrDrDate(new Date());
                    reconcorp.setCrAmount(adjmap.getAdjustAmt());
                    reconcorp.setCrSettledAmount(adjmap.getAdjustAmt());
//                    reconcorp.setCrAvailable(elemapint.getBankPendingAmount());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(adjmap.getCorporates().getCorporateId());
                    outstanding = outstanding.subtract(adjmap.getAdjustAmt());
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Adjustment Interest Mapping");
                    reconcorpdao.NewReconciliationCorp(reconcorp);
//#############################################################                             

                }
                if (adjmap.getDisbursedInterestDetails() != null && adjmap.getRecIntBal() != adjmap.getDisbursedInterestDetails().getInterestPendingamount()) {
                    System.out.println("rec_int_id==" + adjmap.getDisbursedInterestDetails().getInterestId());
                    BigDecimal paid = adjmap.getDisbursedInterestDetails().getInterestPendingamount().subtract(adjmap.getRecIntBal());
                    BigDecimal paidamount = BigDecimal.ZERO;
                    if (adjmap.getDisbursedInterestDetails().getInterestPaidamount() != null) {
                        paidamount = adjmap.getDisbursedInterestDetails().getInterestPaidamount();
                    }
                    BigDecimal totalmapped = paidamount.add(paid);
                    disinterestdao.getUpdateDisburseInterestbyId(adjmap.getDisbursedInterestDetails().getInterestId().intValue(), totalmapped, adjmap.getRecIntBal());
                    disinterestdao.getUpdateStatusDisburseInterestbyId(adjmap.getCorporates().getCorporateId());

//#############################################################dyn recon
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(adjmap.getCorporates());
                    reconcorp.setWeekId(adjmap.getDisbursedInterestDetails().getWeekId());
                    reconcorp.setBillEntryDate(adjmap.getAdjPayment().getEntryDate());
                    reconcorp.setBillType(adjmap.getDisbursedInterestDetails().getBillType());
                    reconcorp.setBillingDate(adjmap.getDisbursedInterestDetails().getBillingDate());
                    reconcorp.setRevisionNo(adjmap.getDisbursedInterestDetails().getRevisionNo());
                    reconcorp.setBillDueDate(adjmap.getDisbursedInterestDetails().getBillingDuedate());
                    reconcorp.setRecTotalnet(adjmap.getDisbursedInterestDetails().getInterestAmount());
                    reconcorp.setEntryDate(new Date());
                    Date dateyear = adjmap.getDisbursedInterestDetails().getBillingDate();
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                    cal.setTime(dateyear);
                    int year = cal.get(Calendar.YEAR);
                    reconcorp.setBillYear(adjmap.getDisbursedInterestDetails().getBillYear());
                    reconcorp.setRecFinalamount(adjmap.getRecIntBal().add(adjmap.getAdjustAmt()));
                    reconcorp.setRecPendingamount(adjmap.getRecIntBal());
                    reconcorp.setCrDrDate(new Date());
                    reconcorp.setDrAmount(adjmap.getAdjustAmt());
                    reconcorp.setDrSettledAmount(adjmap.getAdjustAmt());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(adjmap.getCorporates().getCorporateId());
                    outstanding = outstanding.add(adjmap.getAdjustAmt());
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Adjustment Disbursed Interest");
                    reconcorpdao.NewReconciliationCorp(reconcorp);
//#############################################################                        

                }
                adjmappingdao.getUpdateAdjMappingbyCheckerbySL_NO(adjmap.getSlNo().intValue());
            }
            adjpaymentdao.getUpdateADJPAYMENTbyCheckerbyuniqueid(bg.intValue());
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully Verified the Adjustment transactions for " + corpname);
            return mv1;
        }
        if (bdelete != null) {

            String corp = request.getParameter("corpid");
            CorporatesDAO corpdao = new CorporatesDAO();
            String corpname = corpdao.getCorpNamebyId(Integer.parseInt(corp));
            System.out.println("corpname =" + corpname);
            ModelAndView mv1 = new ModelAndView("successMsg");
            adjmappingdao.deleteAdjMappingbycorpidbyChecker(Integer.parseInt(corp));
            adjpaymentdao.deleteADJPAYMENTbycorpidbyChecker(Integer.parseInt(corp));
            mv1.addObject("Msg", "Succesfully Deleted the Adjustment transactions for " + corpname);
            return mv1;
        }

        System.out.println("corpid=" + corpid);
        ModelAndView mv = new ModelAndView("BillAdjustments/viewPendingCorpListdetails");
        List<AdjPayment> adjpaylist = adjpaymentdao.getPendingAdjPaymentListbyCorp(Integer.parseInt(corpid));

        mv.addObject("corpid", corpid);
        mv.addObject("adjpaylist", adjpaylist);
        return mv;
    }

    public ModelAndView viewAdjustmentMappingDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String uniqueId = request.getParameter("uniqueId");
        ModelAndView mv = new ModelAndView("BillAdjustments/viewAdjMappingListtdetails");
        AdjMapingDAO adjmapdao = new AdjMapingDAO();
        List<AdjMapping> adjmaplist = adjmapdao.getPendingAdjMappingListbyUniqueid(new BigDecimal(uniqueId));

        mv.addObject("adjmaplist", adjmaplist);
        return mv;
    }

    public ModelAndView report(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("BillAdjustments/viewalladjustreports");
        AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
        List<AdjPayment> adjpaylist = adjpaydao.getPendingAdjPaymentListofallcorp();
        mv.addObject("adjpaylist", adjpaylist);
        return mv;
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
