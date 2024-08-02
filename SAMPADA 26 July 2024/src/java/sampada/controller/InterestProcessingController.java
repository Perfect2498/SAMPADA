/*



 * To change this license header, choose License Headers in Project Properties.



 * To change this template file, choose Tools | Templates



 * and open the template in the editor.




 */
package sampada.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import java.util.Date;

import java.util.List;
import java.util.TimeZone;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.AdjPaymentDAO;

import sampada.DAO.BankStatementDAO;

import sampada.DAO.BillReceiveCorpDAO;
import sampada.DAO.ConstantsMasterDAO;

import sampada.DAO.CorporatesDAO;

import sampada.DAO.DisbursedInterestDetailsDAO;
import sampada.DAO.DynReconciliationCropDAO;

import sampada.DAO.InterestDetailsDAO;

import sampada.DAO.InterestPoolAccountDetailsDAO;
import sampada.DAO.MappingInterestBankDAO;

import sampada.DAO.PoolAccountDetailsDAO;
import sampada.DAO.PoolToIntDAO;
import sampada.DAO.PoolToPoolDAO;
import sampada.DAO.RevertTempDisbInterestDAO;
import sampada.DAO.RevertTempInterestDetailsDAO;

import sampada.DAO.TempDisbInterestDetailsDAO;

import sampada.DAO.TempInterestDetailsDAO;

import sampada.DAO.TempMapBankStatementDAO;

import sampada.DAO.TempMappingBillBankDAO;
import sampada.DAO.TempRefundBillCorpDAO;
import sampada.DAO.ToDisbursedInterestDetailsDAO;
import sampada.DAO.ToInterestDetailsDAO;
import sampada.DAO.miscDisbursementDAO;
import sampada.pojo.AdjPayment;
import sampada.pojo.AgcInterestPool;
import sampada.pojo.AgcPoolAccountDetails;

import sampada.pojo.BankStatement;

import sampada.pojo.BillReceiveCorp;

import sampada.pojo.Corporates;

import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.DynReconciliationCorp;

import sampada.pojo.InterestDetails;

import sampada.pojo.InterestPoolAccountDetails;
import sampada.pojo.MappingInterestBank;
import sampada.pojo.MiscDisbursement;

import sampada.pojo.PoolAccountDetails;
import sampada.pojo.PoolToInt;
import sampada.pojo.PoolToPool;
import sampada.pojo.ReconciliationCorp;
import sampada.pojo.RevertTempDisbInterest;
import sampada.pojo.RevertTempInterestDetails;
import sampada.pojo.RrasInterestPool;
import sampada.pojo.RrasPoolAccountDetails;

import sampada.pojo.TempDisbInterestDetails;

import sampada.pojo.TempInterestDetails;

import sampada.pojo.TempMapBankStatement;
import sampada.pojo.TempMappingBillBank;
import sampada.pojo.TempRefundBillCorp;
import sampada.pojo.ToDisbursedInterestDetails;
import sampada.pojo.ToInterestDetails;

/**
 *
 *
 *
 *
 *
 *
 *
 * @author JaganMohan
 *
 *
 *
 */
public class InterestProcessingController extends MultiActionController {

    public ModelAndView viewInterestVerificationbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String getInterest = request.getParameter("getInterest");

        if (getInterest != null) {

            ModelAndView mv = new ModelAndView("viewInterestVerificationbyRLDC");
            String startdate = request.getParameter("date1");
            String enddate = request.getParameter("date2");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];
            System.out.println("startdate =" + startdate);
            System.out.println("enddate =" + enddate);
            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            List<TempDisbInterestDetails> list1 = null;
            TempDisbInterestDetailsDAO tempdisintedao = new TempDisbInterestDetailsDAO();
            list1 = tempdisintedao.getTempviewDisbInterestDetails("Pending", startDate, endDate);

            List<TempInterestDetails> list = null;
            TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
            list = tmepindao.getTempviewInterestDetails("Pending", startDate, endDate);
            System.out.println("list size=" + list.size());
            System.out.println("list1 size=" + list1.size());
            mv.addObject("interestList", list);
            mv.addObject("disbursedList", list1);
            mv.addObject("startdate", startdate);
            mv.addObject("enddate", enddate);

            return mv;
        }

             return mv1;
    }

   ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
    public ModelAndView viewInterestRevert(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewInterestRevortforverification");
        String bName = request.getParameter("bName");
        List<RevertTempInterestDetails> list = null;
        RevertTempInterestDetailsDAO tmepindao = new RevertTempInterestDetailsDAO();
        list = tmepindao.getrevTempInterestDetails("NOTREVERT");
        mv.addObject("revinterestList", list);
        List<RevertTempDisbInterest> list1 = null;
        RevertTempDisbInterestDAO tempdisintedao = new RevertTempDisbInterestDAO();
        list1 = tempdisintedao.getrevTempdisinterest("NOTREVERT");
        mv.addObject("revdisbursedList", list1);

        return mv;
    }

    public ModelAndView viewInterestMappingPayableCorporateList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewInterestMappingPayableCorporateList");
        String bname = request.getParameter("bname");
        InterestDetailsDAO indao = new InterestDetailsDAO();
        if (bname != null) {

            BankStatementDAO bankStmtDao = new BankStatementDAO();
            ModelAndView mv1 = new ModelAndView("viewInterestMappingPayableDetails");
            String corpid = request.getParameter("corparateID");
            CorporatesDAO d = new CorporatesDAO();
            int corporateid = d.getCorpIdbyName(corpid);
            corpid = String.valueOf(corporateid);
            TempMappingBillBankDAO tempMapBillBankdao = new TempMappingBillBankDAO();
            TempRefundBillCorpDAO tempRefundBillCorpDao = new TempRefundBillCorpDAO();
            List<TempMappingBillBank> pendingBillByChecker = tempMapBillBankdao.getTempMappingBillBankbyCorp(corporateid, "Pending");
            List<TempRefundBillCorp> pendingtRefundByChecker = tempRefundBillCorpDao.getAllRefundPayablePendingTempRefundBillCorpByChecker(corporateid);
            if ((pendingBillByChecker != null && pendingBillByChecker.size() > 0) || (pendingtRefundByChecker != null && pendingtRefundByChecker.size() > 0)) {
                ModelAndView mv9 = new ModelAndView("successMsg");
                String Msg = "Kindly ask Checker to verify the Pending in Mapping Bills!!";
                mv9.addObject("Msg", Msg);
                return mv9;
            }
            AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
            List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListbyCorp(corporateid);
            if (adjpatlist != null && adjpatlist.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
                mv2.addObject("Msg", Msg);
                return mv2;
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

            PoolToIntDAO pooltointerestdao = new PoolToIntDAO();
            List<PoolToInt> PoolToInt = pooltointerestdao.getPendingPoolToInt();
            if (PoolToInt != null && PoolToInt.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Pool to Interest Disbursement is Pending at Checker . Please Clear it and Try Again...");
                return mv2;
            }
            PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
            List<PoolToPool> PoolToPool = pooltopooldao.getPendingPoolToInt();
            if (PoolToPool != null && PoolToPool.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Pool to Pool Disbursement is Pending at Checker . Please Clear it and Try Again...");
                return mv2;
            }

            String corparatename = null;
            BankStatementDAO bankstmtdao = new BankStatementDAO();
            List<BankStatement> bnkstmtList = null;
            bnkstmtList = bankstmtdao.BankStatementOrderlist(corporateid);

            TempMappingBillBankDAO tempmapbilldao = new TempMappingBillBankDAO();
            List<TempMappingBillBank> listmapp = null;
            listmapp = tempmapbilldao.getTempMappingBillBankbyCorp(corporateid, "Pending");

            if (listmapp != null && listmapp.size() > 0) {
                System.out.println("Checker Pending in Mapping Bills Verification");
                ModelAndView mv2 = new ModelAndView("successMsg");
                String Msg = "Checker Pending in Interest Mapping for " + corparatename + "!";
                mv2.addObject("Msg", Msg);
                return mv2;
            } else {
                if (bnkstmtList != null && !(bnkstmtList.isEmpty())) {
                    System.out.println("BankSTmt List size is " + bnkstmtList.size());
                    CorporatesDAO corpdao = new CorporatesDAO();
                    corparatename = corpdao.geCorpNamebyId(Integer.parseInt(corpid));
                    List<TempMapBankStatement> list4 = null;
                    TempMapBankStatement tempbakst = new TempMapBankStatement();
                    TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
                    list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid);
                    List<BankStatement> list6 = null;

                    list6 = tempbakstdao.getBankStatementNotINTempMapBankStatement("Pending", corporateid);
                    /* Getting Refund Records from billReceivebale corp*/
                    List<InterestDetails> list = null;
                    list = indao.getInterestPayableDetailsbyCorp(corporateid);
                    mv1.addObject("corpName", corparatename);
                    mv1.addObject("bankList", list4);
                    mv1.addObject("CorpID", corpid);
                    mv1.addObject("interestPayableList", list);
                    mv1.addObject("bnkstmtList", bnkstmtList);
                    mv1.addObject("tempBankStmtlist", list6);

                    InterestDetailsDAO temprefbillcorpdao = new InterestDetailsDAO();
                    List<MappingInterestBank> listinterest = null;
                    List<TempMapBankStatement> listtempmapbankstmt = null;
                    listinterest = temprefbillcorpdao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
                    listtempmapbankstmt = tempbakstdao.getTempMapBankStatementbyStatusRemarks("Pending", Integer.parseInt(corpid), "Interest");
                    mv1.addObject("listinterest", listinterest);
                    mv1.addObject("listtempmapbankstmt", listtempmapbankstmt);
                    return mv1;
                } else {
                    System.out.println("Record not found");
                    ModelAndView mv2 = new ModelAndView("successMsg");
                    String Msg = "Please check-- there is no Verified Bank Transaction for Mapping for " + corparatename + "!";
                    mv2.addObject("Msg", Msg);
                    return mv2;
                }
            }

        }

        String interestconfirm = request.getParameter("interestconfirm");
        if (interestconfirm != null) {

            String refundbankrowcount = request.getParameter("refundbankrowcount");
            int refuniqueid = 0;
            InterestDetails temprefbillcorp = new InterestDetails();
            InterestDetailsDAO temprefbillcorpdao = new InterestDetailsDAO();
            String uniqueid1;
            String refweekid1;
            String recvrefundid1;
            String mappedamt;
            String uniqueId[] = request.getParameterValues("refuniqueNo");
            String interestrowcount = request.getParameter("interestrowcount");
            String corpid = request.getParameter("corpid");
            InterestDetailsDAO intedao = new InterestDetailsDAO();
            MappingInterestBankDAO mappintebankdao = new MappingInterestBankDAO();
            MappingInterestBank mappinteban = new MappingInterestBank();
            int maxid = 0;
            InterestDetails intede = new InterestDetails();
            BankStatement bankstmt = new BankStatement();
            BigDecimal bgmapped = new BigDecimal(0);
            BankStatement bankstmy = new BankStatement();
            String settleAmtBnk;
            String remainBal1;
            String totalamt1;
            String banktransbal;
            String bankremarks = null;
            BigDecimal bankpendamnt = new BigDecimal(0);

            if (Integer.parseInt(interestrowcount) >= 0) {

                String interestbankrowcount = request.getParameter("interestbankrowcount");
                System.out.println("interestbankrowcount is " + interestbankrowcount);
                int bankRowCounter = Integer.parseInt(interestbankrowcount);
                for (int k = 1; k <= bankRowCounter; k++) {
                    String refbankstmt1 = request.getParameter("refbankstmt" + k);
                    String refbankstmt[] = request.getParameterValues("refbankstmt");
                    System.out.println("bankstmt[0] is " + refbankstmt[0]);
                    System.out.println("bankstmt1 is " + refbankstmt1);
                    if (Integer.parseInt(refbankstmt[0]) == Integer.parseInt(refbankstmt1)) {
                        System.out.print("bank stmt id insert into temp mapped is " + refbankstmt1);
                        settleAmtBnk = request.getParameter("refsettleAmtBnk" + k);
                        banktransbal = request.getParameter("refremainBal" + k);
                        bankremarks = request.getParameter("refremarks" + k);
                        bankstmy.setStmtId(new BigDecimal(refbankstmt1));
                        bankpendamnt = new BigDecimal(settleAmtBnk).add(new BigDecimal(banktransbal));

                    }
                }
                for (int i = 1; i <= Integer.parseInt(interestrowcount); i++) {
                    for (int j = 0; j < uniqueId.length; j++) {
                        uniqueid1 = request.getParameter("recvuniqueid" + i);
                        System.out.println("uniqueid1 count " + uniqueid1 + " " + j);
                        if (Integer.parseInt(uniqueid1) == Integer.parseInt(uniqueId[j])) {

                            mappedamt = request.getParameter("mappedamt" + i);
                            bgmapped = new BigDecimal(mappedamt);

                            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
                            List<PoolAccountDetails> pool = pooldao.PoolAccountDetails();

                            BigDecimal totalmapped = mappintebankdao.getSummedMappingInterestBankbyAllPending();
                            totalmapped = totalmapped.add(bgmapped);

                            if (pool.get(0).getMainBalance().compareTo(totalmapped) == -1) {
                                ModelAndView mz = new ModelAndView("successMsg");
                                mz.addObject("Msg", "Cannot Map Amount > Pool balance.  Negative Balance Risk !!");
                                return mz;
                            }

                            if (bgmapped.compareTo(BigDecimal.ZERO) == 1) {
                                recvrefundid1 = request.getParameter("recvrefundid" + i);
                                System.out.println("recvrefundid1  " + recvrefundid1);
                                System.out.println("mappedamt  " + mappedamt);
                                BigDecimal bgamount = new BigDecimal(recvrefundid1);
                                BigDecimal paidamountmap = new BigDecimal(mappedamt);
                                List<InterestDetails> instdetlist = intedao.getInterestDetailsbyinterestid(new BigDecimal(uniqueid1));
                                BigDecimal paidamount = instdetlist.get(0).getInterestPaidamount();
                                System.out.println("paidamount is " + paidamount);
                                if (paidamount == null) {
                                    paidamount = new BigDecimal(BigInteger.ZERO);
                                }
                                paidamount = paidamount.add(paidamountmap);
                                intedao.getUpdateInterestPayablebyId(Integer.parseInt(uniqueid1), paidamount, bgamount);
                                intede.setInterestId(new BigDecimal(uniqueid1));
                                maxid = mappintebankdao.getMaxUniqueID();
                                maxid = maxid + 1;
                                mappinteban.setSlno(new BigDecimal(maxid));
                                mappinteban.setEntryDate(new Date());
                                //Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                                //mappinteban.setEntryTime(currentTimestamp);
                                mappinteban.setCheckerStatus("Pending");
                                mappinteban.setMappedAmount(bgmapped);
                                mappinteban.setInterestDetails(intede);
                                mappinteban.setPendingAmount(bgamount);

//                                bankRowCounter = Integer.parseInt(interestbankrowcount);
//                                for (int k = 1; k <= bankRowCounter; k++) {
//                                    String refbankstmt1 = request.getParameter("refbankstmt" + k);
//                                    String refbankstmt[] = request.getParameterValues("refbankstmt");
//                                    System.out.println("bankstmt[0] is " + refbankstmt[0]);
//                                    System.out.println("bankstmt1 is " + refbankstmt1);
//                                    if (Integer.parseInt(refbankstmt[0]) == Integer.parseInt(refbankstmt1)) {
//                                        System.out.print("bank stmt id insert into temp mapped is " + refbankstmt1);
//                                        bankstmy.setStmtId(new BigDecimal(refbankstmt1));
//
//                                    }
//                                }
                                bankpendamnt = bankpendamnt.subtract(paidamountmap);
                                mappinteban.setBankPendingAmount(bankpendamnt);
                                mappinteban.setRemarks(bankremarks);
                                mappinteban.setBankStatement(bankstmy);

                                mappintebankdao.NewMappingInterestBank(mappinteban);

                            }
                        }
                    }
                }
            }// updating Interest Details Table

            // updating Temp Map Bank Stamt  Table
            String interestbankrowcount = request.getParameter("interestbankrowcount");
            System.out.println("interestbankrowcount count " + interestbankrowcount);

            int maxid1 = 0;

            TempMapBankStatement tempbakst = new TempMapBankStatement();
            TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
            List<InterestDetails> listinterestbank = null;
            List<MappingInterestBank> listmapinban = null;
            BigDecimal transaMapped = new BigDecimal(0);
            BigDecimal totalbankbal = new BigDecimal(0);
            BigDecimal summedInterest = new BigDecimal(0);

            for (int k = 1; k <= Integer.parseInt(interestbankrowcount); k++) {
                String refbankstmt1 = request.getParameter("refbankstmt" + k);

                String refbankstmt[] = request.getParameterValues("refbankstmt");
                if (Integer.parseInt(refbankstmt[0]) == Integer.parseInt(refbankstmt1)) {
                    summedInterest = mappintebankdao.getSummedMappingInterestBankbyCorpID(Integer.parseInt(refbankstmt1));
                    if (summedInterest.intValue() == 0) {
                        summedInterest = mappintebankdao.getSummedMappingInterestBankbyCorpIDisZero();
                    }
                    System.out.println("summedInterest is " + summedInterest);
                    bankstmy.setStmtId(new BigDecimal(refbankstmt1));
                    maxid1 = tempbakstdao.getMaxUniqueID();
                    maxid1 = maxid1 + 1;
                    tempbakst.setTempStmtid(new BigDecimal(maxid1));
                    tempbakst.setBankStatement(bankstmy);
                    banktransbal = request.getParameter("refremainBal" + k);
                    settleAmtBnk = request.getParameter("refsettleAmtBnk" + k);
                    bankremarks = request.getParameter("refremarks" + k);
                    totalamt1 = request.getParameter("reftotalamt" + k);
                    totalbankbal = new BigDecimal(banktransbal);
                    tempbakst.setMappedAmount(new BigDecimal(settleAmtBnk));
                    tempbakst.setRemarks("Interest");
                    tempbakst.setBillCategory("Interest");
                    tempbakst.setTransactionBalance(totalbankbal);
                    tempbakst.setTransactionAmount(new BigDecimal(totalamt1));
                    tempbakst.setCorporateId(new BigDecimal(corpid));
                    tempbakst.setCheckerStatus("Pending");
                    tempbakstdao.NewTempMapBankStatement(tempbakst);
                    System.out.println("totalbankbal is " + totalbankbal);

//                  listinterestbank=intedao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
//                  for(InterestDetails e:listinterestbank)
//                   {
//                       listmapinban=mappintebankdao.getMappedAmountInterestBankbyInterstID(e.getInterestId());
//                       totalbankbal=totalbankbal.subtract(listmapinban.get(0).getMappedAmount());
//                       mappintebankdao.getBankSTmtIDforInterestID(e.getInterestId().intValue(),Integer.parseInt(refbankstmt1),totalbankbal);
//
//                       listmapinban=mappintebankdao.getMappedAmountInterestBankbyInterstID(e.getInterestId());
//                      System.out.println("Mapped intesrest MappedIntesrBank is: " + listmapinban.get(0).getMappedAmount());
//                    totalbankbal=totalbankbal.subtract(listmapinban.get(0).getMappedAmount());
//                     System.out.println("Substract value sbs is: " +totalbankbal);
//
//                     
//                  }
                }
            }

            List<MappingInterestBank> listinterest = null;
            List<TempMapBankStatement> listtempmapbankstmt = null;
            listinterest = temprefbillcorpdao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
            listtempmapbankstmt = tempbakstdao.getTempMapBankStatementbyStatusRemarks("Pending", Integer.parseInt(corpid), "Interest");
            BankStatementDAO bankStmtDao = new BankStatementDAO();
            ModelAndView mv1 = new ModelAndView("viewInterestMappingPayableDetails");
            int corporateid = Integer.parseInt(corpid);
            String corparatename = null;
            BankStatementDAO bankstmtdao = new BankStatementDAO();
            List<BankStatement> bnkstmtList = null;
            bnkstmtList = bankstmtdao.BankStatementOrderlist(corporateid);
            if (bnkstmtList != null && !(bnkstmtList.isEmpty())) {
                System.out.println("BankSTmt List size is " + bnkstmtList.size());
                CorporatesDAO corpdao = new CorporatesDAO();
                corparatename = corpdao.geCorpNamebyId(Integer.parseInt(corpid));
                List<TempMapBankStatement> list4 = null;
                list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid);
                List<BankStatement> list6 = null;
                TempMappingBillBankDAO tempmapbilldao = new TempMappingBillBankDAO();
                list6 = tempbakstdao.getBankStatementNotINTempMapBankStatement("Pending", corporateid);
                /* Getting Refund Records from billReceivebale corp*/
                List<InterestDetails> list = null;
                list = indao.getInterestPayableDetailsbyCorp(corporateid);
                mv1.addObject("corpName", corparatename);
                mv1.addObject("CorpID", corpid);
                mv1.addObject("bnkstmtList", bnkstmtList);
                mv1.addObject("bankList", list4);
                mv1.addObject("interestPayableList", list);
                mv1.addObject("tempBankStmtlist", list6);
                mv1.addObject("listinterest", listinterest);
                mv1.addObject("listtempmapbankstmt", listtempmapbankstmt);
                mv1.addObject("tempBankStmtlist", list6);
                return mv1;
            }

//          
//            ModelAndView mv3 = new ModelAndView("successMsg");
//            mv3.addObject("Msg","Succesfully Interest Details submitted for Checker");
//            return mv3;
        }//end of Interset Confirm

        List<Object[]> list1 = null;
        List<String> listcorp = new ArrayList<>();
        List<String> listcorpbynobnkstmt = new ArrayList<>();
        list1 = indao.getAllInterestPayableBillCorpObjectlist();
        Corporates corp = null;
        for (Object[] obj : list1) {

            corp = new Corporates();
            BankStatementDAO bnkdao = new BankStatementDAO();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            List<BankStatement> bnkstmtlist = bnkdao.BankStatementOrderlist(bg.intValue());
            if (bnkstmtlist != null) {
                listcorp.add(corp.getCorporateName());
            } else {
                listcorpbynobnkstmt.add(corp.getCorporateName());
            }

        }

        Collections.sort(listcorp);
        Collections.sort(listcorpbynobnkstmt);

        MappingInterestBankDAO mibdao = new MappingInterestBankDAO();
        List<MappingInterestBank> mib = mibdao.getPendingMappingInterestBankAtChecker();
        List<String> pendingAtCheckercorps = new ArrayList();

        for (MappingInterestBank o : mib) {
            if (!pendingAtCheckercorps.contains(o.getInterestDetails().getCorporates().getCorporateName())) {
                pendingAtCheckercorps.add(o.getInterestDetails().getCorporates().getCorporateName());
            }
        }

        mv.addObject("interestCorpList", listcorp);
        mv.addObject("listcorpbynobnkstmt", listcorpbynobnkstmt);
        mv.addObject("pendingAtCheckercorps", pendingAtCheckercorps);
        return mv;

    }

    public ModelAndView submitInterestdetailforProcessingbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        List<TempInterestDetails> list = null;
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        list = tmepindao.getTempInterestDetails("Pending");
        List<TempDisbInterestDetails> list1 = null;
        TempDisbInterestDetailsDAO tempdisintedao = new TempDisbInterestDetailsDAO();
        list1 = tempdisintedao.getTempDisbInterestDetails("Pending");
        InterestDetails intede = new InterestDetails();
        InterestDetailsDAO inrtedao = new InterestDetailsDAO();
        int maxid = 0;

        for (TempInterestDetails e : list) {
            maxid = inrtedao.getMaxInterestId();
            maxid = maxid + 1;
            BigDecimal bgmaxid = new BigDecimal(maxid);
            intede.setInterestId(bgmaxid);
            intede.setBillCategory(e.getBillCategory());
            intede.setBillType(e.getBillType());
            intede.setBilledAmount(e.getBilledAmount());
            intede.setBillingDate(e.getBillingDate());
            intede.setBillingDuedate(e.getBillingDuedate());
            intede.setCheckerStatus("Pending");
            intede.setCorporates(e.getCorporates());
            intede.setEntryDate(new Date());
            intede.setInterestAmount(e.getInterestAmount());
            intede.setInterestBilledamount(e.getInterestBilledamount());
            intede.setNoofdays(e.getNoofdays());
            intede.setPaidAmount(e.getPaidAmount());
            intede.setPaidDate(e.getPaidDate());
            intede.setRevisionNo(e.getRevisionNo());
            intede.setWeekId(e.getWeekId());
            intede.setBillYear(e.getBillYear());
            intede.setInterestPendingamount(e.getInterestAmount());
            inrtedao.NewInterestDetails(intede);

        }
        maxid = 0;
        DisbursedInterestDetails disinterest = new DisbursedInterestDetails();
        DisbursedInterestDetailsDAO disintedao = new DisbursedInterestDetailsDAO();
        for (TempDisbInterestDetails k : list1) {
            maxid = disintedao.getMaxDisInterestId();
            maxid = maxid + 1;
            BigDecimal bgmaxid = new BigDecimal(maxid);
            disinterest.setInterestId(bgmaxid);
            disinterest.setBillCategory(k.getBillCategory());
            disinterest.setBillType(k.getBillType());
            disinterest.setBilledAmount(k.getBilledAmount());
            disinterest.setBillingDate(k.getBillingDate());
            disinterest.setBillingDuedate(k.getBillingDuedate());
            disinterest.setCheckerStatus("Pending");
            disinterest.setCorporates(k.getCorporates());
            disinterest.setDisbursedAmount(k.getDisbursedAmount());
            disinterest.setDisbursedDate(k.getDisbursedDate());
            disinterest.setEntryDate(new Date());
            disinterest.setInterestAmount(k.getInterestAmount());
            disinterest.setInterestPendingamount(k.getInterestAmount());
            disinterest.setInterestBilledamount(k.getInterestBilledamount());
            disinterest.setInterestPaidamount(BigDecimal.ZERO);
            disinterest.setNoofdays(k.getNoofdays());
            disinterest.setRevisionNo(k.getRevisionNo());
            disinterest.setWeekId(k.getWeekId());
            disinterest.setBillYear(k.getBillYear());
            disintedao.NewDisbursedInterestDetails(disinterest);

        }

        tmepindao.getUpdateTempInterestDetails();
        tempdisintedao.getUpdateTempDisbInterestDetailsforprocessing();
        ModelAndView mv = new ModelAndView("successMsg");
        mv.addObject("Msg", "Succesfully Interest Details submitted for processing");
        return mv;
    }

//    public ModelAndView viewCheckerInterestPayableDetails(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        HttpSession session1 = request.getSession(false);
//        if (session1 == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/logout.htm");
//            return new ModelAndView(redirectView);
//        }
//        ModelAndView mv = new ModelAndView("viewCheckerInterestPayableDetails");
//        String bconfirm = request.getParameter("bconfirm");
//        String bdelete = request.getParameter("bdelete");
//        InterestDetailsDAO tmepindao = new InterestDetailsDAO();
//
//        if (bconfirm != null) {
//
//            List<TempMapBankStatement> list1 = null;
//            List<InterestPoolAccountDetails> listInterestpool = null;
//            TempMapBankStatementDAO temobkstmtdao = new TempMapBankStatementDAO();
//            BankStatementDAO bankstmtdao = new BankStatementDAO();
//            InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
//            String corpid = request.getParameter("corpid");
//            list1 = temobkstmtdao.getTempMapBankStatementbyStatusRemarks("Pending", Integer.parseInt(corpid), "Interest");
//            BigDecimal mappedamount = new BigDecimal(0);
//            BigDecimal intesetPoolamount = new BigDecimal(0);
//            List<BankStatement> lsistmt = null;
//            for (TempMapBankStatement e : list1) {
//                BigDecimal smtid = e.getBankStatement().getStmtId();
//                BigDecimal bg = e.getMappedAmount();
//                System.out.print("Before ading" + bg);
//                BigDecimal bg1 = e.getTransactionBalance();
//                System.out.print("Before ading" + bg1);
//                mappedamount = bankstmtdao.getMappedAmountBankStmtbyStmtID(e.getBankStatement().getStmtId().intValue());
//                mappedamount = mappedamount.add(bg);
//                lsistmt = bankstmtdao.getBankStatementbyStmtID(smtid.intValue());
//                BigDecimal toalamt = lsistmt.get(0).getPaidAmount();
//                toalamt = toalamt.subtract(mappedamount);
//                bankstmtdao.getUpdateBankStmtbyCheckerforCorp(smtid, mappedamount, toalamt, "Remarks");
//            }
//
//            temobkstmtdao.getUpdatedTempMapBankStatementbyCorpBillCatergory(Integer.parseInt(corpid), "Verified", "Interest");
//
//            InterestDetailsDAO interesdao = new InterestDetailsDAO();
//            List<MappingInterestBank> listinterest = null;
//            listinterest = interesdao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
//            MappingInterestBankDAO mapIntBankDao = new MappingInterestBankDAO();
//            
//            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
//            List<PoolAccountDetails> listpool = null;
//            
//            for (MappingInterestBank e : listinterest) {
//                intesetPoolamount = intesetPoolamount.add(e.getMappedAmount());
//                mapIntBankDao.updateCheckerStatusMappingInterestBankbyCorp(e.getInterestDetails().getInterestId().intValue());
//                BigDecimal bal = intepooldao.getInterestPoolAccountDetails().get(0).getMainBalance();
//                listpool = pooldao.getPoolAccountDetails();
//                BigDecimal poolbal=listpool.get(0).getMainBalance();
//                mapIntBankDao.updateInterestPoolAmount(e.getSlno().intValue(), bal.add(intesetPoolamount),poolbal.subtract(intesetPoolamount));
//            }
//
//            listInterestpool = intepooldao.getInterestPoolAccountDetails();
//            System.out.println("interest pool account balanc is before : " + listInterestpool.get(0).getMainBalance());
//            BigDecimal bgpool = new BigDecimal(0);
//            
//            listpool = pooldao.getPoolAccountDetails();
//            bgpool = listpool.get(0).getMainBalance();
//            bgpool = bgpool.subtract(intesetPoolamount);
//            pooldao.getUpdatePoolAccountbyChecker(bgpool);
//
//            intesetPoolamount = intesetPoolamount.add(listInterestpool.get(0).getMainBalance());
//            intepooldao.getUpdateInterestPoolAccountbyChecker(intesetPoolamount);
//            tmepindao.getUpdateInterestPayableStatusbyChecker(Integer.parseInt(corpid));
//
////            listInterestpool = intepooldao.getInterestPoolAccountDetails();
////            intesetPoolamount.add(listInterestpool.get(0).getMainBalance());
////            intepooldao.getUpdateInterestPoolAccountbyChecker(intesetPoolamount);
////            tmepindao.getUpdateInterestPayableStatusbyChecker(Integer.parseInt(corpid));
////            BigDecimal bgpool = new BigDecimal(0);
////            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
////            List<PoolAccountDetails> listpool = null;
////            listpool = pooldao.getPoolAccountDetails();
////            bgpool = listpool.get(0).getMainBalance();
////            bgpool = bgpool.subtract(intesetPoolamount);
////            pooldao.getUpdatePoolAccountbyChecker(bgpool);
//            ModelAndView mv1 = new ModelAndView("successMsg");
//            mv1.addObject("Msg", "Succesfully Verified the Interest Mapping");
//            return mv1;
//        }
//        if (bdelete != null) {
//
//            List<TempMapBankStatement> list1 = null;
//            TempMapBankStatementDAO temobkstmtdao = new TempMapBankStatementDAO();
//            String corpid = request.getParameter("corpid");
//            list1 = temobkstmtdao.getTempMapBankStatementbyStatusRemarks("Pending", Integer.parseInt(corpid), "Interest");
//            InterestDetailsDAO interesdao = new InterestDetailsDAO();
//            List<MappingInterestBank> listinterest = null;
//            listinterest = interesdao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
//            MappingInterestBankDAO mapIntBankDao = new MappingInterestBankDAO();
//            BigDecimal mappedamount = new BigDecimal(0);
//            BigDecimal interestamount = new BigDecimal(0);
//            BigDecimal interestpending = new BigDecimal(0);
//            for (MappingInterestBank e : listinterest) {
//                mappedamount = e.getMappedAmount();
//                interestamount = e.getInterestDetails().getInterestPaidamount();
//                interestamount = interestamount.subtract(mappedamount);
//                interestpending = e.getInterestDetails().getInterestPendingamount();
//                interestpending = interestpending.add(mappedamount);
//                interesdao.getUpdateInterestPayablebyId(e.getInterestDetails().getInterestId().intValue(), interestamount, interestpending);
//                mapIntBankDao.getDeletedMappingInterestBankbyinterestid(e.getInterestDetails().getInterestId().intValue());
//            }
//            temobkstmtdao.getDeletedInterestTempMapBankStatementbyCorp(Integer.parseInt(corpid), "Pending");
//            ModelAndView mv1 = new ModelAndView("successMsg");
//            mv1.addObject("Msg", "Succesfully Deleted the Interest Mapping");
//            return mv1;
//        }
//
//        String corpid = request.getParameter("corpID");
//        List<MappingInterestBank> list = null;
//        list = tmepindao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
//        mv.addObject("interestCheckerList", list);
//        mv.addObject("corpid", corpid);
//        return mv;
//    }
    public ModelAndView viewCheckerInterestPayableDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewCheckerInterestPayableDetails");
        String bconfirm = request.getParameter("bconfirm");
        String bdelete = request.getParameter("bdelete");
        InterestDetailsDAO tmepindao = new InterestDetailsDAO();

        if (bconfirm != null) {

            List<TempMapBankStatement> list1 = null;
            List<InterestPoolAccountDetails> listInterestpool = null;
            TempMapBankStatementDAO temobkstmtdao = new TempMapBankStatementDAO();
            BankStatementDAO bankstmtdao = new BankStatementDAO();
            InterestPoolAccountDetailsDAO intepooldao = new InterestPoolAccountDetailsDAO();
            String corpid = request.getParameter("corpid");
            list1 = temobkstmtdao.getTempMapBankStatementbyStatusRemarks("Pending", Integer.parseInt(corpid), "Interest");
            BigDecimal mappedamount = new BigDecimal(0);
            BigDecimal intesetPoolamount = new BigDecimal(0);
            BigDecimal agcintesetPoolamount = new BigDecimal(0);
            BigDecimal rrasintesetPoolamount = new BigDecimal(0);
            List<BankStatement> lsistmt = null;
            for (TempMapBankStatement e : list1) {
                BigDecimal smtid = e.getBankStatement().getStmtId();
                BigDecimal bg = e.getMappedAmount();
                System.out.print("Before ading" + bg);
                BigDecimal bg1 = e.getTransactionBalance();
                System.out.print("Before ading" + bg1);
                mappedamount = bankstmtdao.getMappedAmountBankStmtbyStmtID(e.getBankStatement().getStmtId().intValue());
                mappedamount = mappedamount.add(bg);
                lsistmt = bankstmtdao.getBankStatementbyStmtID(smtid.intValue());
                BigDecimal toalamt = lsistmt.get(0).getPaidAmount();
                toalamt = toalamt.subtract(mappedamount);
                bankstmtdao.getUpdateBankStmtbyCheckerforCorp(smtid, mappedamount, toalamt, "Remarks");
            }

            temobkstmtdao.getUpdatedTempMapBankStatementbyCorpBillCatergory(Integer.parseInt(corpid), "Verified", "Interest");

            InterestDetailsDAO interesdao = new InterestDetailsDAO();
            List<MappingInterestBank> listinterest = null;
            listinterest = interesdao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
            MappingInterestBankDAO mapIntBankDao = new MappingInterestBankDAO();

            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
            List<PoolAccountDetails> listpool = null;
            List<AgcPoolAccountDetails> listpoolagc = null;
            List<RrasPoolAccountDetails> listpoolrras = null;
            listpool = pooldao.getPoolAccountDetails();

            BigDecimal poolbal = listpool.get(0).getMainBalance();
            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

            for (MappingInterestBank e : listinterest) {

                if (e.getInterestDetails().getBillType().equalsIgnoreCase("SRAS")) {

                    agcintesetPoolamount = agcintesetPoolamount.add(e.getMappedAmount());

                    BigDecimal bal = intepooldao.getInterestPoolAccountDetailsagc().get(0).getMainBalance();

                    mapIntBankDao.deleteobj(e.getSlno().intValue());
                    poolbal = poolbal.subtract(e.getMappedAmount());
                    e.setIntAgcBal(bal.add(agcintesetPoolamount));
                    e.setPoolBal(poolbal);
                    currentTimestamp = addMilliseconds(currentTimestamp, 1);

                    e.setEntryTime(currentTimestamp);

                    mapIntBankDao.NewMappingInterestBank(e);
                } else if (e.getInterestDetails().getBillType().equalsIgnoreCase("TRASM")
                        || e.getInterestDetails().getBillType().equalsIgnoreCase("TRASS")
                        || e.getInterestDetails().getBillType().equalsIgnoreCase("TRASE")) {

                    rrasintesetPoolamount = rrasintesetPoolamount.add(e.getMappedAmount());

                    BigDecimal bal = intepooldao.getInterestPoolAccountDetailsrras().get(0).getMainBalance();
//                    listpool = pooldao.getPoolAccountDetails();
//                    BigDecimal poolbal = listpool.get(0).getMainBalance();


                    mapIntBankDao.deleteobj(e.getSlno().intValue());
                    poolbal = poolbal.subtract(e.getMappedAmount());
                    e.setIntRrasBal(bal.add(rrasintesetPoolamount));
                    e.setPoolBal(poolbal);
                    currentTimestamp = addMilliseconds(currentTimestamp, 1);

                    e.setEntryTime(currentTimestamp);

                    mapIntBankDao.NewMappingInterestBank(e);
                } else {

                    intesetPoolamount = intesetPoolamount.add(e.getMappedAmount());

                    BigDecimal bal = intepooldao.getInterestPoolAccountDetails().get(0).getMainBalance();
//                    listpool = pooldao.getPoolAccountDetails();
//                    BigDecimal poolbal = listpool.get(0).getMainBalance();


                    mapIntBankDao.deleteobj(e.getSlno().intValue());
                    poolbal = poolbal.subtract(e.getMappedAmount());
                    e.setAfter_int_pool(bal.add(intesetPoolamount));
                    e.setPoolBal(poolbal);
                    currentTimestamp = addMilliseconds(currentTimestamp, 1);

                    e.setEntryTime(currentTimestamp);

                    mapIntBankDao.NewMappingInterestBank(e);
                }

                DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();
                DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                BigDecimal slno = new BigDecimal(0);
                slno = new BigDecimal(reconcorpdao.getMaxslno());
                slno = slno.add(BigDecimal.ONE);
                reconcorp.setSlno(slno);
                reconcorp.setCorporates(e.getInterestDetails().getCorporates());
                reconcorp.setWeekId(e.getInterestDetails().getWeekId());
                reconcorp.setBillEntryDate(e.getEntryDate());
                reconcorp.setBillType(e.getInterestDetails().getBillType());
                reconcorp.setBillingDate(e.getInterestDetails().getBillingDate());
                reconcorp.setRevisionNo(e.getInterestDetails().getRevisionNo());
                reconcorp.setBillDueDate(e.getInterestDetails().getBillingDuedate());
                reconcorp.setPayTotalnet(e.getInterestDetails().getInterestAmount());
                reconcorp.setEntryDate(new Date());
                Date dateyear = e.getInterestDetails().getBillingDate();
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(dateyear);
                int year = cal.get(Calendar.YEAR);
                reconcorp.setBillYear(e.getInterestDetails().getBillYear());
                reconcorp.setPayFinalamount(e.getMappedAmount().add(e.getPendingAmount()));
                reconcorp.setPayPendingamount(e.getPendingAmount());
                reconcorp.setCrDrDate(e.getBankStatement().getAmountDate());
                reconcorp.setCrAmount(e.getBankStatement().getPaidAmount());
                reconcorp.setCrSettledAmount(e.getMappedAmount());
                reconcorp.setCrAvailable(e.getBankPendingAmount());
                BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(e.getInterestDetails().getCorporates().getCorporateId());
                outstanding = outstanding.subtract(e.getMappedAmount());
                reconcorp.setOutstandingAmount(outstanding);
                reconcorp.setRemarks("Interest Mapping");
                reconcorpdao.NewReconciliationCorp(reconcorp);

                mapIntBankDao.updateCheckerStatusMappingInterestBankbyCorp(e.getInterestDetails().getInterestId().intValue());
            }

            listInterestpool = intepooldao.getInterestPoolAccountDetails();
            System.out.println("interest pool account balanc is before : " + listInterestpool.get(0).getMainBalance());
            BigDecimal bgpool = new BigDecimal(0);
            BigDecimal bgpoolnew = new BigDecimal(0);

            listpool = pooldao.getPoolAccountDetails();
            bgpool = listpool.get(0).getMainBalance();
            bgpoolnew = bgpoolnew.add(intesetPoolamount).add(agcintesetPoolamount).add(rrasintesetPoolamount);
            bgpool = bgpool.subtract(bgpoolnew);
            pooldao.getUpdatePoolAccountbyChecker(bgpool);

            intesetPoolamount = intesetPoolamount.add(listInterestpool.get(0).getMainBalance());
            intepooldao.getUpdateInterestPoolAccountbyChecker(intesetPoolamount);

            List<AgcInterestPool> listInterestpoolagc = null;
            listInterestpoolagc = intepooldao.getInterestPoolAccountDetailsagc();
            System.out.println("interest pool account balanc is before : " + listInterestpoolagc.get(0).getMainBalance());
//            BigDecimal bgpoolagc = new BigDecimal(0);

//            listpoolagc = pooldao.getAgcPoolAccountDetails();
//            bgpoolagc = listpoolagc.get(0).getMainBalance();
//            bgpoolagc = bgpoolagc.subtract(agcintesetPoolamount);
//            pooldao.getUpdateAgcPoolAccountbyChecker(bgpoolagc);
            agcintesetPoolamount = agcintesetPoolamount.add(listInterestpoolagc.get(0).getMainBalance());
            intepooldao.getUpdateInterestPoolAccountbyCheckeragc(agcintesetPoolamount);

            List<RrasInterestPool> listInterestpoolrras = null;
            listInterestpoolrras = intepooldao.getInterestPoolAccountDetailsrras();
            System.out.println("interest pool account balanc is before : " + listInterestpoolrras.get(0).getMainBalance());
//            BigDecimal bgpoolrras = new BigDecimal(0);

//            listpoolrras = pooldao.getRrasPoolAccountDetails();
//            bgpoolrras = listpoolrras.get(0).getMainBalance();
//            bgpoolrras = bgpoolrras.subtract(rrasintesetPoolamount);
//            pooldao.getUpdateRrasPoolAccountbyChecker(bgpoolrras);
            rrasintesetPoolamount = rrasintesetPoolamount.add(listInterestpoolrras.get(0).getMainBalance());
            intepooldao.getUpdateInterestPoolAccountbyCheckerrras(rrasintesetPoolamount);

            tmepindao.getUpdateInterestPayableStatusbyChecker(Integer.parseInt(corpid));

            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully Verified the Interest Mapping");
            return mv1;
        }
        if (bdelete != null) {

            List<TempMapBankStatement> list1 = null;
            TempMapBankStatementDAO temobkstmtdao = new TempMapBankStatementDAO();
            String corpid = request.getParameter("corpid");
            list1 = temobkstmtdao.getTempMapBankStatementbyStatusRemarks("Pending", Integer.parseInt(corpid), "Interest");
            InterestDetailsDAO interesdao = new InterestDetailsDAO();
            List<MappingInterestBank> listinterest = null;
            listinterest = interesdao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
            MappingInterestBankDAO mapIntBankDao = new MappingInterestBankDAO();
            BigDecimal mappedamount = new BigDecimal(0);
            BigDecimal interestamount = new BigDecimal(0);
            BigDecimal interestpending = new BigDecimal(0);
            for (MappingInterestBank e : listinterest) {
                mappedamount = e.getMappedAmount();
                interestamount = e.getInterestDetails().getInterestPaidamount();
                interestamount = interestamount.subtract(mappedamount);
                interestpending = e.getInterestDetails().getInterestPendingamount();
                interestpending = interestpending.add(mappedamount);
                interesdao.getUpdateInterestPayablebyId(e.getInterestDetails().getInterestId().intValue(), interestamount, interestpending);
                mapIntBankDao.getDeletedMappingInterestBankbyinterestid(e.getInterestDetails().getInterestId().intValue());
            }
            temobkstmtdao.getDeletedInterestTempMapBankStatementbyCorp(Integer.parseInt(corpid), "Pending");
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully Deleted the Interest Mapping");
            return mv1;
        }

        String corpid = request.getParameter("corpID");
        List<MappingInterestBank> list = null;
        list = tmepindao.getAllInterestPayableDetailsbyCheacker(Integer.parseInt(corpid));
        mv.addObject("interestCheckerList", list);
        mv.addObject("corpid", corpid);
        return mv;
    }

    public ModelAndView viewCheckerInterestPayableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewCheckerInterestPayableList");
        InterestDetailsDAO indao = new InterestDetailsDAO();
        List<Object[]> list1 = null;
        List<Corporates> listcorp = new ArrayList<>();
        list1 = indao.getAllInterestPayableBillCorpObjectlistforChecker();
        Corporates corp = null;
        for (Object[] obj : list1) {

            corp = new Corporates();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            listcorp.add(corp);
        }
        mv.addObject("interestCheckerList", listcorp);
        return mv;
    }

    public ModelAndView viewPendingInterestPayableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewPendingInterestPayableList");
        InterestDetailsDAO indao = new InterestDetailsDAO();
        List<InterestDetails> list1 = null;
        list1 = indao.getPendingInterestPayableList();

        mv.addObject("interestPendList", list1);
        return mv;

    }

    public ModelAndView submitPayInterestdetailforRevert(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        List<RevertTempInterestDetails> listrev = null;
        List<TempInterestDetails> list = null;
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        RevertTempInterestDetailsDAO revtempintdao = new RevertTempInterestDetailsDAO();

        String uniqueID1 = null;
        String rowcount = request.getParameter("rowcount");
        int rowc1 = Integer.parseInt(rowcount);
        String[] chkSms = request.getParameterValues("items");
        for (int k = 1; k <= rowc1; k++) {
            int checkedflag = 0;
            uniqueID1 = request.getParameter("uniqueID" + k);
            String remarks1 = request.getParameter("remarks" + k);
            if (chkSms != null) {
                for (int j = 0; j < chkSms.length; j++) {
                    if (uniqueID1.equalsIgnoreCase(chkSms[j].toString())) {
                        checkedflag = 1;
                        System.out.print("chkSms[j].toString() is " + chkSms[j].toString());
                    }
                }

            }
            if (checkedflag == 1) {
                listrev = revtempintdao.getRevertTempInterestDetailsbyid(Integer.parseInt(uniqueID1));
                for (RevertTempInterestDetails e : listrev) {
                    tmepindao.getUpdateTempInterestDetailsbyinterestidbyPending(e.getTempInterestDetails().getInterestId().intValueExact());
                    revtempintdao.getUpdateRevertTempInterestDetailsbyidbyReverted(Integer.parseInt(uniqueID1));
                }
            }

//            System.out.print("uniqueID1 is " + uniqueID1);
        }

        ModelAndView mv = new ModelAndView("successMsg");
        mv.addObject("Msg", "Succesfully Interest Details Reverted back for Verification");
        return mv;

    }

    public ModelAndView submitRecvInterestdetailforRevert(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        List<RevertTempDisbInterest> listrev = null;
        List<TempDisbInterestDetails> list1 = null;
        TempDisbInterestDetailsDAO tempdisintedao = new TempDisbInterestDetailsDAO();
        RevertTempDisbInterestDAO revtempintdao = new RevertTempDisbInterestDAO();

        String uniqueID21 = null;
        String rowcount = request.getParameter("rowcount1");
        int rowc1 = Integer.parseInt(rowcount);
        String[] chkSms = request.getParameterValues("items1");
        for (int k = 1; k <= rowc1; k++) {
            int checkedflag = 0;
            uniqueID21 = request.getParameter("uniqueID" + k);
            String remarks1 = request.getParameter("remarks" + k);
            if (chkSms != null) {
                for (int j = 0; j < chkSms.length; j++) {
                    if (uniqueID21.equalsIgnoreCase(chkSms[j].toString())) {
                        checkedflag = 1;
                        System.out.print("chkSms[j].toString() is " + chkSms[j].toString());
                    }
                }

            }
            if (checkedflag == 1) {
                listrev = revtempintdao.getRevertTempDisbInterestbyid(Integer.parseInt(uniqueID21));

                for (RevertTempDisbInterest e1 : listrev) {
                    tempdisintedao.getUpdateTempDisbInterestDetailsbyinterestidbyPending(e1.getTempDisbInterestDetails().getInterestId().intValueExact());
                    revtempintdao.getUpdateRevertTempDisbInterestbyidbyReverted(Integer.parseInt(uniqueID21));
                }
            }
        }

        ModelAndView mv = new ModelAndView("successMsg");
        mv.addObject("Msg", "Succesfully Interest Details Reverted back for Verification");
        return mv;

    }

    public ModelAndView submitPayInterestdetailforProcessingbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        List<TempInterestDetails> list = null;
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
//        list = tmepindao.getTempInterestDetails("Pending");
        List<TempDisbInterestDetails> list1 = null;
//        TempDisbInterestDetailsDAO tempdisintedao = new TempDisbInterestDetailsDAO();
//        list1 = tempdisintedao.getTempDisbInterestDetails("Pending");
        RevertTempInterestDetailsDAO revtempintdao = new RevertTempInterestDetailsDAO();

        ToInterestDetailsDAO inrtedao = new ToInterestDetailsDAO();
        int maxid = 0;

//        for (TempInterestDetails e : list) {
//            maxid = inrtedao.getMaxInterestId();
//            maxid = maxid + 1;
//            BigDecimal bgmaxid = new BigDecimal(maxid);
//            intede.setInterestId(bgmaxid);
//            intede.setBillCategory(e.getBillCategory());
//            intede.setBillType(e.getBillType());
//            intede.setBilledAmount(e.getBilledAmount());
//            intede.setBillingDate(e.getBillingDate());
//            intede.setBillingDuedate(e.getBillingDuedate());
//            intede.setCheckerStatus("Pending");
//            intede.setCorporates(e.getCorporates());
//            intede.setEntryDate(new Date());
//            intede.setInterestAmount(e.getInterestAmount());
//            intede.setInterestBilledamount(e.getInterestBilledamount());
//            intede.setNoofdays(e.getNoofdays());
//            intede.setPaidAmount(e.getPaidAmount());
//            intede.setPaidDate(e.getPaidDate());
//            intede.setRevisionNo(e.getRevisionNo());
//            intede.setWeekId(e.getWeekId());
//            intede.setBillYear(e.getBillYear());
//            intede.setInterestPendingamount(e.getInterestAmount());
//            inrtedao.NewInterestDetails(intede);
//
//        }
        String uniqueID1 = null;
        String rowcount = request.getParameter("rowcount");
        int rowc1 = Integer.parseInt(rowcount);
        String[] chkSms = request.getParameterValues("items");
        for (int k = 1; k <= rowc1; k++) {
            int checkedflag = 0;
            uniqueID1 = request.getParameter("uniqueID" + k);
            String remarks1 = request.getParameter("remarks" + k);
            if (chkSms != null) {
                for (int j = 0; j < chkSms.length; j++) {
                    if (uniqueID1.equalsIgnoreCase(chkSms[j].toString())) {
                        checkedflag = 1;
                        System.out.print("chkSms[j].toString() is " + chkSms[j].toString());
                    }
                }

            }
            if (checkedflag == 1) {
                list = tmepindao.getTempInterestDetailsbyinterestid(Integer.parseInt(uniqueID1));
                for (TempInterestDetails e : list) {
                    ToInterestDetails intede = new ToInterestDetails();
                    maxid = inrtedao.getMaxInterestId();
                    maxid = maxid + 1;
                    BigDecimal bgmaxid = new BigDecimal(maxid);
                    intede.setInterestId(bgmaxid);
                    intede.setBillCategory(e.getBillCategory());
                    intede.setBillType(e.getBillType());
                    intede.setBilledAmount(e.getBilledAmount());
                    intede.setBillingDate(e.getBillingDate());
                    intede.setBillingDuedate(e.getBillingDuedate());
                    intede.setCheckerStatus("Pending");
                    intede.setCorporates(e.getCorporates());
                    intede.setEntryDate(new Date());
                    intede.setInterestAmount(e.getInterestAmount());
                    intede.setInterestBilledamount(e.getInterestBilledamount());
                    intede.setNoofdays(e.getNoofdays());
                    intede.setPaidAmount(e.getPaidAmount());
                    intede.setPaidDate(e.getPaidDate());
                    intede.setRevisionNo(e.getRevisionNo());
                    intede.setWeekId(e.getWeekId());
                    intede.setBillYear(e.getBillYear());
                    intede.setInterestPendingamount(e.getInterestAmount());
                    intede.setRemarks(remarks1);
                    inrtedao.NewToInterestDetails(intede);
                    tmepindao.getUpdateTempInterestDetailsbyinterestid(Integer.parseInt(uniqueID1));
                }

            } else {
                list = tmepindao.getTempInterestDetailsbyinterestid(Integer.parseInt(uniqueID1));
                for (TempInterestDetails e : list) {
                    RevertTempInterestDetails ravintdet = new RevertTempInterestDetails();
                    TempInterestDetails tempintdet = new TempInterestDetails();
                    maxid = revtempintdao.getMaxUniqueID();
                    maxid = maxid + 1;
                    BigDecimal bgmaxid = new BigDecimal(maxid);
                    ravintdet.setSlno(bgmaxid);
                    tempintdet.setInterestId(e.getInterestId());
                    ravintdet.setTempInterestDetails(tempintdet);
                    ravintdet.setRemarks(remarks1);
                    ravintdet.setCheckerStatus("NOTREVERT");
                    ravintdet.setEntryDate(new Date());
                    revtempintdao.NewRevertTempInterestDetails(ravintdet);
                    tmepindao.getUpdateTempInterestDetailsbyinterestidbyunvarified(Integer.parseInt(uniqueID1));
                }
            }
//            System.out.print("uniqueID1 is " + uniqueID1);
        }

//        List<String> corpnamelist = new ArrayList<String>();
//        String corpname = null;
//
//        String[] columns = {"Week ID", "Pool Member Name", "Revision No", "Bill Type", "Total Bill Amount", "Bill Due Date", "Bill Paid Date", "No of Days", "Interest Billed Amt", "Interest Amt"};
//        for (int k = 1; k <= rowc1; k++) {
//            corpname = request.getParameter("poolname" + k);
//            System.out.print("POOL Name is" + corpname);
//            int flag = 0;
//            if (corpnamelist != null) {
//                for (int y = 0; y < corpnamelist.size(); y++) {
//                    if (corpname.equalsIgnoreCase(corpnamelist.get(y))) {
//                        flag = 1;
//                    }
//                }
//                if (flag == 0) {
//                    corpnamelist.add(corpname);
//                }
//            } else {
//                corpnamelist.add(corpname);
//            }
//        }
//
//        Collections.sort(corpnamelist);
//        int a = 1;
//        String week = null;
//        String poolname = null;
//        String rev = null;
//        String billtype = null;
//        String billedamt = null;
//        String billeduedate = null;
//        String bankpaiddate = null;
//        String noofdays = null;
//        String interestbillamt = null;
//        String interestamt = null;
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = null;
//        Row headerRow = null;
//        for (int i = 0; a <= corpnamelist.size(); i++, a++) {
//
//            sheet = workbook.createSheet(corpnamelist.get(i));
//            headerRow = sheet.createRow(0);
//            Cell cell = null;
//            for (int j = 0; j < columns.length; j++) {
//                cell = headerRow.createCell(j);
//                cell.setCellValue(columns[j]);
//            }
//
//            int rowflag = 0;
//            for (int k = 1; k <= rowc1; k++) {
//                week = request.getParameter("week" + k);
//                poolname = request.getParameter("poolname" + k);
//                if (corpnamelist.get(i).equalsIgnoreCase(poolname)) {
//
//                    rowflag = sheet.getLastRowNum();
//                    rowflag = rowflag + 1;
//                    System.out.print("Last RowNum  is" + rowflag);
//                    headerRow = sheet.createRow(rowflag);
//                    rev = request.getParameter("rev" + k);
//                    billtype = request.getParameter("billtype" + k);
//                    billedamt = request.getParameter("billedamt" + k);
//                    billeduedate = request.getParameter("billeduedate" + k);
//                    bankpaiddate = request.getParameter("bankpaiddate" + k);
//                    noofdays = request.getParameter("noofdays" + k);
//                    interestbillamt = request.getParameter("interestbillamt" + k);
//                    interestamt = request.getParameter("interestamt" + k);
//
//                    //having column 10 in Column[] so if condition for column length
//                    for (int j = 0; j < columns.length; j++) {
//
//                        switch (j) {
//
//                            case 0:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(week);
//                                break;
//                            case 1:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(poolname);
//                                break;
//                            case 2:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(rev);
//                                break;
//                            case 3:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(billtype);
//                                break;
//                            case 4:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(billedamt);
//                                break;
//                            case 5:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(billeduedate);
//                                break;
//                            case 6:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(bankpaiddate);
//                                break;
//                            case 7:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(noofdays);
//                                break;
//                            case 8:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(interestbillamt);
//                                break;
//                            case 9:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(interestamt);
//                                break;
//
//                        }//end of switch                                                 
//
//                    }//end of columns
//                }//end of checking/matching corprate name
//
//            }//end of rowcount                              
//
//        }//end of distinct corpname
//        ConstantsMasterDAO consdao = new ConstantsMasterDAO();
//        String interestfilepath = consdao.getFilePathbyName("INTEREST_EXCELFILE");
//
//        String pattern = "MM-dd-yyyy";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        String filedate = simpleDateFormat.format(new Date());
//        FileOutputStream outFile = new FileOutputStream(new File(interestfilepath + "interest_mapping_" + filedate + ".xlsx"));
//        //FileOutputStream outFile =new FileOutputStream(new File("D:\\SampadaExcel\\data.xlsx"));
//        workbook.write(outFile);
//        outFile.close();
//        workbook.close();
//        tmepindao.getUpdateTempInterestDetails();
        ModelAndView mv = new ModelAndView("successMsg");
        mv.addObject("Msg", "Succesfully Interest Details submitted for processing");
        return mv;

    }

    public ModelAndView submitRecvInterestdetailforProcessingbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        List<TempInterestDetails> list = null;
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        list = tmepindao.getTempInterestDetails("Pending");
        List<TempDisbInterestDetails> list1 = null;
        TempDisbInterestDetailsDAO tempdisintedao = new TempDisbInterestDetailsDAO();
//        list1 = tempdisintedao.getTempDisbInterestDetails("Pending");
        InterestDetails intede = new InterestDetails();
        InterestDetailsDAO inrtedao = new InterestDetailsDAO();
        RevertTempDisbInterestDAO revtempintdao = new RevertTempDisbInterestDAO();

        int maxid = 0;
        maxid = 0;

        ToDisbursedInterestDetailsDAO disintedao = new ToDisbursedInterestDetailsDAO();
//        for (TempDisbInterestDetails k : list1) {
//            maxid = disintedao.getMaxDisInterestId();
//            maxid = maxid + 1;
//            BigDecimal bgmaxid = new BigDecimal(maxid);
//            disinterest.setInterestId(bgmaxid);
//            disinterest.setBillCategory(k.getBillCategory());
//            disinterest.setBillType(k.getBillType());
//            disinterest.setBilledAmount(k.getBilledAmount());
//            disinterest.setBillingDate(k.getBillingDate());
//            disinterest.setBillingDuedate(k.getBillingDuedate());
//            disinterest.setCheckerStatus("Pending");
//            disinterest.setCorporates(k.getCorporates());
//            disinterest.setDisbursedAmount(k.getDisbursedAmount());
//            disinterest.setDisbursedDate(k.getDisbursedDate());
//            disinterest.setEntryDate(new Date());
//            disinterest.setInterestAmount(k.getInterestAmount());
//            disinterest.setInterestPendingamount(k.getInterestAmount());
//            disinterest.setInterestBilledamount(k.getInterestBilledamount());
//            //disinterest.setInterestPaidamount(BigDecimal.ZERO);
//            disinterest.setNoofdays(k.getNoofdays());
//            disinterest.setRevisionNo(k.getRevisionNo());
//            disinterest.setWeekId(k.getWeekId());
//            disinterest.setBillYear(k.getBillYear());
//            disintedao.NewDisbursedInterestDetails(disinterest);
//
//        }

        String uniqueID21 = null;
        String rowcount = request.getParameter("rowcount1");
        int rowc1 = Integer.parseInt(rowcount);
        String[] chkSms = request.getParameterValues("items1");
        for (int k = 1; k <= rowc1; k++) {
            int checkedflag = 0;
            uniqueID21 = request.getParameter("uniqueID" + k);
            String remarks1 = request.getParameter("remarks" + k);
            if (chkSms != null) {
                for (int j = 0; j < chkSms.length; j++) {
                    if (uniqueID21.equalsIgnoreCase(chkSms[j].toString())) {
                        checkedflag = 1;
                        System.out.print("chkSms[j].toString() is " + chkSms[j].toString());
                    }
                }

            }
            if (checkedflag == 1) {
                list1 = tempdisintedao.getTempDisbInterestDetailsbyinterestid(Integer.parseInt(uniqueID21));

                for (TempDisbInterestDetails e1 : list1) {
                    ToDisbursedInterestDetails disinterest = new ToDisbursedInterestDetails();
                    maxid = disintedao.getMaxDisInterestId();
                    maxid = maxid + 1;
                    BigDecimal bgmaxid = new BigDecimal(maxid);
                    disinterest.setInterestId(bgmaxid);
                    disinterest.setBillCategory(e1.getBillCategory());
                    disinterest.setBillType(e1.getBillType());
                    disinterest.setBilledAmount(e1.getBilledAmount());
                    disinterest.setBillingDate(e1.getBillingDate());
                    disinterest.setBillingDuedate(e1.getBillingDuedate());
                    disinterest.setCheckerStatus("Pending");
                    disinterest.setCorporates(e1.getCorporates());
                    disinterest.setDisbursedAmount(e1.getDisbursedAmount());
                    disinterest.setDisbursedDate(e1.getDisbursedDate());
                    disinterest.setEntryDate(new Date());
                    disinterest.setInterestAmount(e1.getInterestAmount());
                    disinterest.setInterestPendingamount(e1.getInterestAmount());
                    disinterest.setInterestBilledamount(e1.getInterestBilledamount());
                    //disinterest.setInterestPaidamount(BigDecimal.ZERO);
                    disinterest.setNoofdays(e1.getNoofdays());
                    disinterest.setRevisionNo(e1.getRevisionNo());
                    disinterest.setWeekId(e1.getWeekId());
                    disinterest.setBillYear(e1.getBillYear());
                    disinterest.setRemarks(remarks1);
                    disintedao.NewToDisbursedInterestDetails(disinterest);
                    tempdisintedao.getUpdateTempDisbInterestDetailsforprocessingbyinterestid(Integer.parseInt(uniqueID21));
                }
            } else {
                list1 = tempdisintedao.getTempDisbInterestDetailsbyinterestid(Integer.parseInt(uniqueID21));

                for (TempDisbInterestDetails e1 : list1) {
                    RevertTempDisbInterest ravdisint = new RevertTempDisbInterest();
                    TempDisbInterestDetails tempintdet = new TempDisbInterestDetails();
                    maxid = revtempintdao.getMaxUniqueID();
                    maxid = maxid + 1;
                    BigDecimal bgmaxid = new BigDecimal(maxid);
                    ravdisint.setSlno(bgmaxid);
                    tempintdet.setInterestId(e1.getInterestId());
                    ravdisint.setTempDisbInterestDetails(tempintdet);
                    ravdisint.setRemarks(remarks1);
                    ravdisint.setCheckerStatus("NOTREVERT");
                    ravdisint.setEntryDate(new Date());
                    revtempintdao.NewRevertTempDisbInterest(ravdisint);
                    tempdisintedao.getUpdateTempDisbInterestDetailsforprocessingbyinterestidbyUNVerified(Integer.parseInt(uniqueID21));
                }
            }
        }

//        List<String> corpnamelist = new ArrayList<String>();
//        String corpname = null;
////        String rowcount = request.getParameter("rowcount1");
////        int rowc1 = Integer.parseInt(rowcount);
//        String[] columns = {"Week ID", "Pool Member Name", "Revision No", "Bill Type", "Total Bill Amount", "Bill Due Date", "Bill Paid Date", "No of Days", "Interest Billed Amt", "Interest Amt"};
//        for (int k = 1; k <= rowc1; k++) {
//            corpname = request.getParameter("poolname" + k);
//            System.out.print("POOL Name is" + corpname);
//            int flag = 0;
//            if (corpnamelist != null) {
//                for (int y = 0; y < corpnamelist.size(); y++) {
//                    if (corpname.equalsIgnoreCase(corpnamelist.get(y))) {
//                        flag = 1;
//                    }
//                }
//                if (flag == 0) {
//                    corpnamelist.add(corpname);
//                }
//            } else {
//                corpnamelist.add(corpname);
//            }
//        }
//
//        Collections.sort(corpnamelist);
//        int a = 1;
//        String week = null;
//        String poolname = null;
//        String rev = null;
//        String billtype = null;
//        String billedamt = null;
//        String billeduedate = null;
//        String bankpaiddate = null;
//        String noofdays = null;
//        String interestbillamt = null;
//        String interestamt = null;
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = null;
//        Row headerRow = null;
//        for (int i = 0; a <= corpnamelist.size(); i++, a++) {
//
//            sheet = workbook.createSheet(corpnamelist.get(i));
//            headerRow = sheet.createRow(0);
//            Cell cell = null;
//            for (int j = 0; j < columns.length; j++) {
//                cell = headerRow.createCell(j);
//                cell.setCellValue(columns[j]);
//            }
//
//            int rowflag = 0;
//            for (int k = 1; k <= rowc1; k++) {
//                week = request.getParameter("week" + k);
//                poolname = request.getParameter("poolname" + k);
//                if (corpnamelist.get(i).equalsIgnoreCase(poolname)) {
//
//                    rowflag = sheet.getLastRowNum();
//                    rowflag = rowflag + 1;
//                    System.out.print("Last RowNum  is" + rowflag);
//                    headerRow = sheet.createRow(rowflag);
//                    rev = request.getParameter("rev" + k);
//                    billtype = request.getParameter("billtype" + k);
//                    billedamt = request.getParameter("billedamt" + k);
//                    billeduedate = request.getParameter("billeduedate" + k);
//                    bankpaiddate = request.getParameter("bankpaiddate" + k);
//                    noofdays = request.getParameter("noofdays" + k);
//                    interestbillamt = request.getParameter("interestbillamt" + k);
//                    interestamt = request.getParameter("interestamt" + k);
//
//                    //having column 10 in Column[] so if condition for column length
//                    for (int j = 0; j < columns.length; j++) {
//
//                        switch (j) {
//
//                            case 0:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(week);
//                                break;
//                            case 1:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(poolname);
//                                break;
//                            case 2:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(rev);
//                                break;
//                            case 3:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(billtype);
//                                break;
//                            case 4:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(billedamt);
//                                break;
//                            case 5:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(billeduedate);
//                                break;
//                            case 6:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(bankpaiddate);
//                                break;
//                            case 7:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(noofdays);
//                                break;
//                            case 8:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(interestbillamt);
//                                break;
//                            case 9:
//                                cell = headerRow.createCell(j);
//                                cell.setCellValue(interestamt);
//                                break;
//
//                        }//end of switch                                                 
//
//                    }//end of columns
//                }//end of checking/matching corprate name
//
//            }//end of rowcount                              
//
//        }//end of distinct corpname
//        ConstantsMasterDAO consdao = new ConstantsMasterDAO();
//        String interestfilepath = consdao.getFilePathbyName("INTEREST_EXCELFILE");
//        String pattern = "MM-dd-yyyy";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        String filedate = simpleDateFormat.format(new Date());
//        // FileOutputStream outFile =new FileOutputStream(new File(interestfilepath+"interest_mapping_"+filedate+".xlsx"));
//        FileOutputStream outFile = new FileOutputStream(new File(interestfilepath + "interest_disburse" + filedate + ".xlsx"));
//        workbook.write(outFile);
//        outFile.close();
//        workbook.close();
//        tempdisintedao.getUpdateTempDisbInterestDetailsforprocessing();
        ModelAndView mv = new ModelAndView("successMsg");
        mv.addObject("Msg", "Succesfully Interest Details submitted for processing");
        return mv;

    }

    public ModelAndView interestPaidExcelExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        List<TempInterestDetails> list = null;
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        System.out.println("startdate =" + startdate);
        System.out.println("enddate =" + enddate);
        list = tmepindao.getTempviewInterestDetails("Pending", startDate, endDate);

//        list = tmepindao.getTempInterestDetails("Pending");
        if (list.size() > 0) {
            List<String> corpnamesinlist = new ArrayList<String>();

            for (TempInterestDetails corpname : list) {
                if (!corpnamesinlist.contains(corpname.getCorporates().getCorporateName())) {
                    corpnamesinlist.add(corpname.getCorporates().getCorporateName());
                }
            }
            System.out.println("Size is " + corpnamesinlist.size());

            Collections.sort(corpnamesinlist);

            String[] columns = {"INTEREST ID", "WEEK ID", "CORPORATE ID", "REVISION NO", "BILL TYPE", "BILL AMOUNT", "BILLING DUEDATE", "BANK PAID DATE", "NO OF DAYS", "BILL AMOUNT FOR INTEREST", "INTEREST AMOUNT"};

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            Row headerrow = null;

            for (int i = 0; i < corpnamesinlist.size(); i++) {
                sheet = workbook.createSheet(corpnamesinlist.get(i));
                headerrow = sheet.createRow(0);
                Cell cell = null;

                for (int j = 0; j < columns.length; j++) {
                    cell = headerrow.createCell(j);
                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    headerrow.getCell(j).setCellStyle(style);
                    cell.setCellValue(columns[j]);
                }

                int rownum = 0;
                for (TempInterestDetails idlist : list) {
                    if (corpnamesinlist.get(i).equalsIgnoreCase(idlist.getCorporates().getCorporateName())) {
                        rownum = sheet.getLastRowNum();
                        rownum = rownum + 1;
                        headerrow = sheet.createRow(rownum);

                        for (int z = 0; z < columns.length; z++) {
                            switch (z) {
                                case 0:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue("I" + idlist.getInterestId().toString());
                                    break;
                                case 1:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getWeekId().toString());
                                    break;
                                case 2:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getCorporates().getCorporateId());
                                    break;
                                case 3:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRevisionNo().toString());
                                    break;
                                case 4:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillType());
                                    break;
                                case 5:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBilledAmount().toString());
                                    break;
                                case 6:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillingDuedate().toString());
                                    break;
                                case 7:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getPaidDate().toString());
                                    break;
                                case 8:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getNoofdays().toString());
                                    break;
                                case 9:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestBilledamount().toString());
                                    break;
                                case 10:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestAmount().toString());
                                    break;
                            }
                        }
                    }
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String filedate = sdf.format(new Date());
            String filename = "Interest_Paid_Report_by_" + filedate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
        }

        ModelAndView mv = new ModelAndView("viewInterestVerificationbyRLDC");
        return mv;
    }

    public ModelAndView interestProcessingExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        List<InterestDetails> intdetlist = new ArrayList<>();
        List<Object[]> queryList1 = tmepindao.getintnetvalues();
        if (queryList1 != null) {
            for (Object[] row : queryList1) {
                InterestDetails intdet = new InterestDetails();
                Corporates corp = new Corporates();
                CorporatesDAO corpdao = new CorporatesDAO();
                BigDecimal bg = (BigDecimal) row[0];
                corp.setCorporateId(bg.intValue());
                corp.setCorporateName(corpdao.getCorpNamebyId(bg.intValue()));
                intdet.setCorporates(corp);
                intdet.setInterestAmount((BigDecimal) row[1]);
                intdet.setInterestBilledamount((BigDecimal) row[2]);
                intdet.setInterestPaidamount((BigDecimal) row[3]);
                intdet.setBillType((String) row[4]);

                intdetlist.add(intdet);
            }
        }

        if (intdetlist.size() > 0) {

            String[] columns = {"POOL MEMBER NAME", "BILL TYPE", "PAYABLE INTEREST", "RECEIVABLE INTEREST", "NET INTEREST"};

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            Row headerrow = null;

            sheet = workbook.createSheet("Interest Publishing");
            headerrow = sheet.createRow(0);
            Cell cell = null;

            for (int j = 0; j < columns.length; j++) {
                cell = headerrow.createCell(j);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                headerrow.getCell(j).setCellStyle(style);
                cell.setCellValue(columns[j]);
            }

            int rownum = 0;
            for (InterestDetails idlist : intdetlist) {

                rownum = sheet.getLastRowNum();
                rownum = rownum + 1;
                headerrow = sheet.createRow(rownum);

                for (int z = 0; z < columns.length; z++) {
                    switch (z) {
                        case 0:
                            cell = headerrow.createCell(z);
                            cell.setCellValue(idlist.getCorporates().getCorporateName());
                            break;
                        case 1:
                            cell = headerrow.createCell(z);
                            cell.setCellValue(idlist.getBillType());
                            break;
                        case 2:
                            cell = headerrow.createCell(z);
                            cell.setCellValue(idlist.getInterestAmount().toPlainString());
                            break;
                        case 3:
                            cell = headerrow.createCell(z);
                            cell.setCellValue(idlist.getInterestBilledamount().toPlainString());
                            break;
                        case 4:
                            cell = headerrow.createCell(z);
                            cell.setCellValue(idlist.getInterestPaidamount().toPlainString());
                            break;
                    }
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String filedate = sdf.format(new Date());
            String filename = "Interest_Publishing_Export_" + filedate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
            out.flush();
            out.close();
        }

        ModelAndView mv = new ModelAndView("interestPrecessingbyRLDC");
        return mv;
    }

    public ModelAndView interestDisburseExcelExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        List<TempDisbInterestDetails> list1 = null;
        TempDisbInterestDetailsDAO tempdisintedao = new TempDisbInterestDetailsDAO();
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        System.out.println("startdate =" + startdate);
        System.out.println("enddate =" + enddate);
        list1 = tempdisintedao.getTempviewDisbInterestDetails("Pending", startDate, endDate);
//        list1 = tempdisintedao.getTempDisbInterestDetails("Pending");

        if (list1.size() > 0) {
            List<String> corpnamesinlist = new ArrayList<String>();

            for (TempDisbInterestDetails corpname : list1) {
                if (!corpnamesinlist.contains(corpname.getCorporates().getCorporateName())) {
                    corpnamesinlist.add(corpname.getCorporates().getCorporateName());
                }
            }
            System.out.println("Size is " + corpnamesinlist.size());

            Collections.sort(corpnamesinlist);

            String[] columns = {"INTEREST ID", "WEEK ID", "CORPORATE ID", "REVISION NO", "BILL TYPE", "BILL AMOUNT", "BILLING DUEDATE", "DISBURSED DATE", "NO OF DAYS", "BILL AMOUNT FOR INTEREST", "INTEREST AMOUNT"};

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            Row headerrow = null;

            for (int i = 0; i < corpnamesinlist.size(); i++) {
                sheet = workbook.createSheet(corpnamesinlist.get(i));
                headerrow = sheet.createRow(0);
                Cell cell = null;

                for (int j = 0; j < columns.length; j++) {
                    cell = headerrow.createCell(j);
                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    headerrow.getCell(j).setCellStyle(style);
                    cell.setCellValue(columns[j]);
                }

                int rownum = 0;
                for (TempDisbInterestDetails idlist : list1) {
                    if (corpnamesinlist.get(i).equalsIgnoreCase(idlist.getCorporates().getCorporateName())) {
                        rownum = sheet.getLastRowNum();
                        rownum = rownum + 1;
                        headerrow = sheet.createRow(rownum);

                        for (int z = 0; z < columns.length; z++) {
                            switch (z) {
                                case 0:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue("I" + idlist.getInterestId().toString());
                                    break;
                                case 1:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getWeekId().toString());
                                    break;
                                case 2:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getCorporates().getCorporateId());
                                    break;
                                case 3:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRevisionNo().toString());
                                    break;
                                case 4:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillType());
                                    break;
                                case 5:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBilledAmount().toString());
                                    break;
                                case 6:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillingDuedate().toString());
                                    break;
                                case 7:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getDisbursedDate().toString());
                                    break;
                                case 8:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getNoofdays().toString());
                                    break;
                                case 9:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestBilledamount().toString());
                                    break;
                                case 10:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestAmount().toString());
                                    break;
                            }
                        }
                    }
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String filedate = sdf.format(new Date());
            String filename = "Interest_Disburse_Report_by_" + filedate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
        }

        ModelAndView mv = new ModelAndView("viewInterestVerificationbyRLDC");
        return mv;
    }

    public ModelAndView interestPreocessingbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("interestPrecessingbyRLDC");

        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        List<InterestDetails> intdetlist = new ArrayList<>();
        List<Object[]> queryList1 = tmepindao.getintnetvalues();
        if (queryList1 != null) {
            for (Object[] row : queryList1) {
                InterestDetails intdet = new InterestDetails();
                Corporates corp = new Corporates();
                CorporatesDAO corpdao = new CorporatesDAO();
                BigDecimal bg = (BigDecimal) row[0];
                corp.setCorporateId(bg.intValue());
                corp.setCorporateName(corpdao.getCorpNamebyId(bg.intValue()));
                intdet.setCorporates(corp);
                intdet.setInterestAmount((BigDecimal) row[1]);
                intdet.setInterestBilledamount((BigDecimal) row[2]);
                intdet.setInterestPaidamount((BigDecimal) row[3]);
                intdet.setBillType((String) row[4]);

                intdetlist.add(intdet);
            }
        }
        mv.addObject("intdetlist", intdetlist);

        return mv;
    }

//    public ModelAndView submitnewInterestforProcessingbyRLDC(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        HttpSession session1 = request.getSession(false);
//        if (session1 == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/logout.htm");
//            return new ModelAndView(redirectView);
//        }
//        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
//        String rowcount = request.getParameter("rowcount");
//        System.out.println("row count is =" + rowcount);
//        String remarks = request.getParameter("remarks");
//        System.out.println("remarks is " + remarks);
//        String revno = request.getParameter("revno");
//        System.out.println("revno is " + revno);
//        String week = request.getParameter("week");
//        System.out.println("week is " + week);
//        String year = request.getParameter("year");
//        System.out.println("year is " + year);
//        
//        int rowc1 = Integer.parseInt(rowcount);
//        for (int k = 1; k <= rowc1; k++) {
//            String poolname = request.getParameter("poolname" + k);
//            String billtype = request.getParameter("billtype" + k);
//            String interestpay = request.getParameter("interestpay" + k);
//            String interestrec = request.getParameter("interestrec" + k);
//            String interestnet = request.getParameter("interestnet" + k);
//            String billCat = request.getParameter("billCat" + k);
//            String remark2 = request.getParameter("rem" + k);
//            String payInterest = request.getParameter("interestpay" + k);
//            String recInterest = request.getParameter("interestrec" + k);
//            
//            BigDecimal bgrec = new BigDecimal(interestrec);
//            BigDecimal bgpay = new BigDecimal(interestpay);
//            BigDecimal bgnet = new BigDecimal(interestnet);
//            BigDecimal payint = new BigDecimal(payInterest);
//            BigDecimal recint = new BigDecimal(recInterest);
//            
//            if (billCat.equals("NET")) {
//                if (bgnet.compareTo(BigDecimal.ZERO) >= 0) {
//                    System.out.println("bgnet >=0 is =" + bgnet);
//                    ToInterestDetailsDAO tointdao = new ToInterestDetailsDAO();
//                    InterestDetails intede = new InterestDetails();
//                    InterestDetailsDAO inrtedao = new InterestDetailsDAO();
//                    Corporates corp = new Corporates();
//                    CorporatesDAO corpdao = new CorporatesDAO();
//                    int corpid = corpdao.getCorpIdbyName(poolname);
//                    corp.setCorporateId(corpid);
//                    int maxid = 0;
//                    maxid = inrtedao.getMaxInterestId();
//                    maxid = maxid + 1;
//                    BigDecimal bgmaxid = new BigDecimal(maxid);
//                    intede.setInterestId(bgmaxid);
//                    intede.setBillCategory(billCat);
//                    intede.setRemarks2(remark2);
//                    intede.setBillType(billtype);
//                    intede.setBilledAmount(bgnet);
//                    intede.setBillingDate(new Date());
//                    intede.setBillingDuedate(new Date());
//                    intede.setCheckerStatus("ToChecker");
//                    intede.setCorporates(corp);
//                    intede.setEntryDate(new Date());
//                    intede.setInterestAmount(bgnet);
//                    intede.setInterestBilledamount(bgnet);
//                    intede.setNoofdays(new BigDecimal("90"));
//                    intede.setPaidAmount(bgnet);
//                    intede.setPaidDate(new Date());
//                    intede.setRevisionNo(new BigDecimal(revno));
//                    intede.setWeekId(new BigDecimal(week));
//                    intede.setBillYear(new BigDecimal(year));
//                    intede.setInterestPendingamount(bgnet);
//                    intede.setRemarks(remarks);
//                    intede.setPayInterest(payint);
//                    intede.setRecInterest(recint);
//                    inrtedao.NewInterestDetails(intede);
//                    tointdao.sendToInterest_DetailstoChecker();
//                } else {
//                    System.out.println("bgnet <0 is =" + bgnet);
//                    BigDecimal mul = new BigDecimal("-1");
//                    bgnet = bgnet.multiply(mul);
//                    ToDisbursedInterestDetailsDAO todintdao = new ToDisbursedInterestDetailsDAO();
//                    DisbursedInterestDetails intede = new DisbursedInterestDetails();
//                    DisbursedInterestDetailsDAO inrtedao = new DisbursedInterestDetailsDAO();
//                    Corporates corp = new Corporates();
//                    CorporatesDAO corpdao = new CorporatesDAO();
//                    int corpid = corpdao.getCorpIdbyName(poolname);
//                    corp.setCorporateId(corpid);
//                    int maxid = 0;
//                    maxid = inrtedao.getMaxDisInterestId();
//                    maxid = maxid + 1;
//                    BigDecimal bgmaxid = new BigDecimal(maxid);
//                    intede.setInterestId(bgmaxid);
//                    intede.setBillCategory(billCat);
//                    intede.setRemarks2(remark2);
//                    intede.setBillType(billtype);
//                    intede.setBilledAmount(bgnet);
//                    intede.setBillingDate(new Date());
//                    intede.setBillingDuedate(new Date());
//                    intede.setCheckerStatus("ToChecker");
//                    intede.setCorporates(corp);
//                    intede.setEntryDate(new Date());
//                    intede.setInterestAmount(bgnet);
//                    intede.setInterestBilledamount(bgnet);
//                    intede.setNoofdays(new BigDecimal("90"));
//                    intede.setDisbursedAmount(bgnet);
//                    intede.setDisbursedDate(new Date());
//                    intede.setRevisionNo(new BigDecimal(revno));
//                    intede.setWeekId(new BigDecimal(week));
//                    intede.setBillYear(new BigDecimal(year));
//                    intede.setInterestPendingamount(bgnet);
//                    intede.setRemarks(remarks);
//                    intede.setPayInterest(payint);
//                    intede.setRecInterest(recint);
//                    inrtedao.NewDisbursedInterestDetails(intede);
//                    todintdao.sendToDisburse_Interest_DetailstoChecker();
//                }
//            } else {
//                System.out.println("cat is seperate" + poolname);
//                
//                InterestDetails intede = new InterestDetails();
//                InterestDetailsDAO inrtedao = new InterestDetailsDAO();
//                Corporates corp = new Corporates();
//                CorporatesDAO corpdao = new CorporatesDAO();
//                int corpid = corpdao.getCorpIdbyName(poolname);
//                corp.setCorporateId(corpid);
//                int maxid = 0;
//                maxid = inrtedao.getMaxInterestId();
//                maxid = maxid + 1;
//                BigDecimal bgmaxid = new BigDecimal(maxid);
//                intede.setInterestId(bgmaxid);
//                intede.setBillCategory(billCat);
//                intede.setRemarks2(remark2);
//                intede.setBillType(billtype);
//                intede.setBilledAmount(bgpay);
//                intede.setBillingDate(new Date());
//                intede.setBillingDuedate(new Date());
//                intede.setCheckerStatus("ToChecker");
//                intede.setCorporates(corp);
//                intede.setEntryDate(new Date());
//                intede.setInterestAmount(bgpay);
//                intede.setInterestBilledamount(bgpay);
//                intede.setNoofdays(new BigDecimal("90"));
//                intede.setPaidAmount(bgpay);
//                intede.setPaidDate(new Date());
//                intede.setRevisionNo(new BigDecimal(revno));
//                intede.setWeekId(new BigDecimal(week));
//                intede.setBillYear(new BigDecimal(year));
//                intede.setInterestPendingamount(bgpay);
//                intede.setRemarks(remarks);
//                intede.setPayInterest(payint);
//                intede.setRecInterest(recint);
//                inrtedao.NewInterestDetails(intede);
//                
//                DisbursedInterestDetails intede1 = new DisbursedInterestDetails();
//                DisbursedInterestDetailsDAO inrtedao1 = new DisbursedInterestDetailsDAO();
//                
//                int maxid1 = 0;
//                maxid1 = inrtedao1.getMaxDisInterestId();
//                maxid1 = maxid1 + 1;
//                BigDecimal bgmaxid1 = new BigDecimal(maxid1);
//                intede1.setInterestId(bgmaxid1);
//                intede1.setBillCategory(billCat);
//                intede1.setRemarks2(remark2);
//                intede1.setBillType(billtype);
//                intede1.setBilledAmount(bgrec);
//                intede1.setBillingDate(new Date());
//                intede1.setBillingDuedate(new Date());
//                intede1.setCheckerStatus("ToChecker");
//                intede1.setCorporates(corp);
//                intede1.setEntryDate(new Date());
//                intede1.setInterestAmount(bgrec);
//                intede1.setInterestBilledamount(bgrec);
//                intede1.setNoofdays(new BigDecimal("90"));
//                intede1.setDisbursedAmount(bgrec);
//                intede1.setDisbursedDate(new Date());
//                intede1.setRevisionNo(new BigDecimal(revno));
//                intede1.setWeekId(new BigDecimal(week));
//                intede1.setBillYear(new BigDecimal(year));
//                intede1.setInterestPendingamount(bgrec);
//                intede1.setRemarks(remarks);
//                intede1.setPayInterest(payint);
//                intede1.setRecInterest(recint);
//                inrtedao1.NewDisbursedInterestDetails(intede1);
//                
//            }
//
////            tmepindao.getUpdateToInterestDetailsbtoverified();
////            tmepindao.getUpdateTodisburseInterestDetailsbtoverified();
//        }
//        ModelAndView mv = new ModelAndView("successMsg");
//        mv.addObject("Msg", "Succesfully Interest Details submitted for processing");
//        return mv;
//    }
    public ModelAndView submitnewInterestforProcessingbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        String rowcount = request.getParameter("rowcount");
        System.out.println("row count is =" + rowcount);
        String remarks = request.getParameter("remarks");
        System.out.println("remarks is " + remarks);
        String revno = request.getParameter("revno");
        System.out.println("revno is " + revno);
        String week = request.getParameter("week");
        System.out.println("week is " + week);
        String year = request.getParameter("year");
        System.out.println("year is " + year);
        ToInterestDetailsDAO tointdao = new ToInterestDetailsDAO();
        ToDisbursedInterestDetailsDAO todintdao = new ToDisbursedInterestDetailsDAO();

        int rowc1 = Integer.parseInt(rowcount);
        for (int k = 1; k <= rowc1; k++) {
            String poolname = request.getParameter("poolname" + k);
            String billtype = request.getParameter("billtype" + k);
            String interestpay = request.getParameter("interestpay" + k);
            String interestrec = request.getParameter("interestrec" + k);
            String interestnet = request.getParameter("interestnet" + k);
            String billCat = request.getParameter("billCat" + k);
            String remark2 = request.getParameter("rem" + k);
            String payInterest = request.getParameter("interestpay" + k);
            String recInterest = request.getParameter("interestrec" + k);

            BigDecimal bgrec = new BigDecimal(interestrec);
            BigDecimal bgpay = new BigDecimal(interestpay);
            BigDecimal bgnet = new BigDecimal(interestnet);
            BigDecimal payint = new BigDecimal(payInterest);
            BigDecimal recint = new BigDecimal(recInterest);

            if (billCat.equals("NET")) {
                if (bgnet.compareTo(BigDecimal.ZERO) >= 0) {
                    System.out.println("bgnet >=0 is =" + bgnet);

                    InterestDetails intede = new InterestDetails();
                    InterestDetailsDAO inrtedao = new InterestDetailsDAO();
                    Corporates corp = new Corporates();
                    CorporatesDAO corpdao = new CorporatesDAO();
                    int corpid = corpdao.getCorpIdbyName(poolname);
                    corp.setCorporateId(corpid);
                    int maxid = 0;
                    maxid = inrtedao.getMaxInterestId();
                    maxid = maxid + 1;
                    BigDecimal bgmaxid = new BigDecimal(maxid);
                    intede.setInterestId(bgmaxid);
                    intede.setBillCategory(billCat);
                    intede.setRemarks2(remark2);
                    intede.setBillType(billtype);
                    intede.setBilledAmount(bgnet);
                    intede.setBillingDate(new Date());
                    intede.setBillingDuedate(new Date());
                    intede.setCheckerStatus("ToChecker");
                    intede.setCorporates(corp);
                    intede.setEntryDate(new Date());
                    intede.setInterestAmount(bgnet);
                    intede.setInterestBilledamount(bgnet);
                    intede.setNoofdays(new BigDecimal("90"));
                    intede.setPaidAmount(bgnet);
                    intede.setPaidDate(new Date());
                    intede.setRevisionNo(new BigDecimal(revno));
                    intede.setWeekId(new BigDecimal(week));
                    intede.setBillYear(new BigDecimal(year));
                    intede.setInterestPendingamount(bgnet);
                    intede.setRemarks(remarks);
                    intede.setPayInterest(payint);
                    intede.setRecInterest(recint);
                    inrtedao.NewInterestDetails(intede);

                } else {
                    System.out.println("bgnet <0 is =" + bgnet);
                    BigDecimal mul = new BigDecimal("-1");
                    bgnet = bgnet.multiply(mul);

                    DisbursedInterestDetails intede = new DisbursedInterestDetails();
                    DisbursedInterestDetailsDAO inrtedao = new DisbursedInterestDetailsDAO();
                    Corporates corp = new Corporates();
                    CorporatesDAO corpdao = new CorporatesDAO();
                    int corpid = corpdao.getCorpIdbyName(poolname);
                    corp.setCorporateId(corpid);
                    int maxid = 0;
                    maxid = inrtedao.getMaxDisInterestId();
                    maxid = maxid + 1;
                    BigDecimal bgmaxid = new BigDecimal(maxid);
                    intede.setInterestId(bgmaxid);
                    intede.setBillCategory(billCat);
                    intede.setRemarks2(remark2);
                    intede.setBillType(billtype);
                    intede.setBilledAmount(bgnet);
                    intede.setBillingDate(new Date());
                    intede.setBillingDuedate(new Date());
                    intede.setCheckerStatus("ToChecker");
                    intede.setCorporates(corp);
                    intede.setEntryDate(new Date());
                    intede.setInterestAmount(bgnet);
                    intede.setInterestBilledamount(bgnet);
                    intede.setNoofdays(new BigDecimal("90"));
                    intede.setDisbursedAmount(bgnet);
                    intede.setDisbursedDate(new Date());
                    intede.setRevisionNo(new BigDecimal(revno));
                    intede.setWeekId(new BigDecimal(week));
                    intede.setBillYear(new BigDecimal(year));
                    intede.setInterestPendingamount(bgnet);
                    intede.setRemarks(remarks);
                    intede.setPayInterest(payint);
                    intede.setRecInterest(recint);
                    inrtedao.NewDisbursedInterestDetails(intede);

                }
            } else {
                System.out.println("cat is seperate" + poolname);

                InterestDetails intede = new InterestDetails();
                InterestDetailsDAO inrtedao = new InterestDetailsDAO();
                Corporates corp = new Corporates();
                CorporatesDAO corpdao = new CorporatesDAO();
                int corpid = corpdao.getCorpIdbyName(poolname);
                corp.setCorporateId(corpid);
                int maxid = 0;
                maxid = inrtedao.getMaxInterestId();
                maxid = maxid + 1;
                BigDecimal bgmaxid = new BigDecimal(maxid);
                intede.setInterestId(bgmaxid);
                intede.setBillCategory(billCat);
                intede.setRemarks2(remark2);
                intede.setBillType(billtype);
                intede.setBilledAmount(bgpay);
                intede.setBillingDate(new Date());
                intede.setBillingDuedate(new Date());
                intede.setCheckerStatus("ToChecker");
                intede.setCorporates(corp);
                intede.setEntryDate(new Date());
                intede.setInterestAmount(bgpay);
                intede.setInterestBilledamount(bgpay);
                intede.setNoofdays(new BigDecimal("90"));
                intede.setPaidAmount(bgpay);
                intede.setPaidDate(new Date());
                intede.setRevisionNo(new BigDecimal(revno));
                intede.setWeekId(new BigDecimal(week));
                intede.setBillYear(new BigDecimal(year));
                intede.setInterestPendingamount(bgpay);
                intede.setRemarks(remarks);
                intede.setPayInterest(payint);
                intede.setRecInterest(recint);
                inrtedao.NewInterestDetails(intede);

                DisbursedInterestDetails intede1 = new DisbursedInterestDetails();
                DisbursedInterestDetailsDAO inrtedao1 = new DisbursedInterestDetailsDAO();

                int maxid1 = 0;
                maxid1 = inrtedao1.getMaxDisInterestId();
                maxid1 = maxid1 + 1;
                BigDecimal bgmaxid1 = new BigDecimal(maxid1);
                intede1.setInterestId(bgmaxid1);
                intede1.setBillCategory(billCat);
                intede1.setRemarks2(remark2);
                intede1.setBillType(billtype);
                intede1.setBilledAmount(bgrec);
                intede1.setBillingDate(new Date());
                intede1.setBillingDuedate(new Date());
                intede1.setCheckerStatus("ToChecker");
                intede1.setCorporates(corp);
                intede1.setEntryDate(new Date());
                intede1.setInterestAmount(bgrec);
                intede1.setInterestBilledamount(bgrec);
                intede1.setNoofdays(new BigDecimal("90"));
                intede1.setDisbursedAmount(bgrec);
                intede1.setDisbursedDate(new Date());
                intede1.setRevisionNo(new BigDecimal(revno));
                intede1.setWeekId(new BigDecimal(week));
                intede1.setBillYear(new BigDecimal(year));
                intede1.setInterestPendingamount(bgrec);
                intede1.setRemarks(remarks);
                intede1.setPayInterest(payint);
                intede1.setRecInterest(recint);
                inrtedao1.NewDisbursedInterestDetails(intede1);

            }

        }

        tointdao.sendToInterest_DetailstoChecker();
        todintdao.sendToDisburse_Interest_DetailstoChecker();
        ModelAndView mv = new ModelAndView("successMsg");
        mv.addObject("Msg", "Succesfully Interest Details submitted for processing");
        return mv;
    }

//============================================================================================
//    public ModelAndView interestProcessingChecker(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session1 = request.getSession(false);
//        if (session1 == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/logout.htm");
//            return new ModelAndView(redirectView);
//        }
//        
//        String yes = request.getParameter("yes");
//        String no = request.getParameter("no");
//        if (yes == null) {
//            yes = "";
//        }
//        if (no == null) {
//            no = "";
//        }
//        
//        System.out.println("Yes is :" + yes + " No is :" + no);
//        
//        InterestDetailsDAO iddao = new InterestDetailsDAO();
//        DisbursedInterestDetailsDAO didao = new DisbursedInterestDetailsDAO();
//        ToInterestDetailsDAO toiddao = new ToInterestDetailsDAO();
//        ToDisbursedInterestDetailsDAO todidao = new ToDisbursedInterestDetailsDAO();
//        
//        if (yes.equalsIgnoreCase("yes")) {
//            
//            List<InterestDetails> tempintdetlis = iddao.getInterestDetailsforChecker();
//            List<DisbursedInterestDetails> tempdisintlist = didao.getDisbursedInterestDetailsforChecker();
//            
//            if (tempintdetlis != null && !(tempintdetlis.isEmpty())) {
//                for (InterestDetails eleint : tempintdetlis) {
//                    DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();
//                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
//                    BigDecimal slno = new BigDecimal(0);
//                    slno = new BigDecimal(reconcorpdao.getMaxslno());
//                    slno = slno.add(BigDecimal.ONE);
//                    reconcorp.setSlno(slno);
//                    reconcorp.setCorporates(eleint.getCorporates());
//                    reconcorp.setWeekId(eleint.getWeekId());
//                    reconcorp.setBillEntryDate(eleint.getEntryDate());
//                    reconcorp.setBillType(eleint.getBillType());
//                    reconcorp.setBillingDate(eleint.getBillingDate());
//                    reconcorp.setRevisionNo(eleint.getRevisionNo());
//                    reconcorp.setBillDueDate(eleint.getBillingDuedate());
//                    reconcorp.setPayTotalnet(eleint.getInterestAmount());
//                    reconcorp.setEntryDate(new Date());
//                    Date dateyear = eleint.getBillingDate();
//                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
//                    cal.setTime(dateyear);
//                    int year = cal.get(Calendar.YEAR);
//                    reconcorp.setBillYear(eleint.getBillYear());
//                    reconcorp.setPayFinalamount(eleint.getInterestAmount());
//                    reconcorp.setPayPendingamount(eleint.getInterestAmount());
//                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(eleint.getCorporates().getCorporateId());
//                    outstanding = eleint.getInterestAmount().add(outstanding);
//                    reconcorp.setOutstandingAmount(outstanding);
//                    reconcorp.setRemarks("Interest Pending");
//                    reconcorpdao.NewReconciliationCorp(reconcorp);
//                    
//                }
//            }
//            
//            if (tempdisintlist != null && !(tempdisintlist.isEmpty())) {
//                for (DisbursedInterestDetails eleintdis : tempdisintlist) {
//                    
//                    DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();
//                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
//                    BigDecimal slno = new BigDecimal(0);
//                    slno = new BigDecimal(reconcorpdao.getMaxslno());
//                    slno = slno.add(BigDecimal.ONE);
//                    reconcorp.setSlno(slno);
//                    reconcorp.setCorporates(eleintdis.getCorporates());
//                    reconcorp.setWeekId(eleintdis.getWeekId());
//                    reconcorp.setBillEntryDate(eleintdis.getBillingDate());
//                    reconcorp.setBillType(eleintdis.getBillType());
//                    reconcorp.setBillingDate(eleintdis.getBillingDate());
//                    reconcorp.setRevisionNo(eleintdis.getRevisionNo());
//                    reconcorp.setBillDueDate(eleintdis.getBillingDuedate());
//                    reconcorp.setRecTotalnet(eleintdis.getInterestAmount());
//                    reconcorp.setEntryDate(new Date());
//                    Date dateyear = eleintdis.getBillingDate();
//                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
//                    cal.setTime(dateyear);
//                    int year = cal.get(Calendar.YEAR);
//                    reconcorp.setBillYear(eleintdis.getBillYear());
//                    reconcorp.setRecFinalamount(eleintdis.getInterestAmount());
//                    reconcorp.setRecPendingamount(eleintdis.getInterestAmount());
//                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(eleintdis.getCorporates().getCorporateId());
//                    outstanding = outstanding.subtract(eleintdis.getInterestAmount());
//                    reconcorp.setOutstandingAmount(outstanding);
//                    reconcorp.setRemarks("Disbursed Interest Pending");
//                    reconcorpdao.NewReconciliationCorp(reconcorp);
//                    
//                }
//            }
//            
//            iddao.updateInterestDetailsbyChecker();
//            didao.updateDisbursedInterestDetailsbyChecker();
//        }
//        
//        if (no.equalsIgnoreCase("no")) {
//            iddao.rejectInterestDetailsbyChecker();
//            didao.rejectDisbursedInterestDetailsbyChecker();
//            
//            toiddao.rejectToInterest_DetailsfromChecker();
//            todidao.rejectToDisburseInterest_DetailsfromChecker();
//        }
//        
//        List<InterestDetails> idlist = iddao.getInterestDetailsforChecker();
//        List<DisbursedInterestDetails> didlist = didao.getDisbursedInterestDetailsforChecker();
//        
//        String loginid = (String) session1.getAttribute("loginid");
//        int flag = 00;
//        
//        if (loginid.equalsIgnoreCase("sysadmin") || loginid.equalsIgnoreCase("CHECKER")) {
//            flag = 111;
//        }
//        
//        System.out.println("Login is : flag is :" + loginid + " " + flag);
//        
//        ModelAndView mv = new ModelAndView("interestProcessingChecker");
//        
//        mv.addObject("idlist", idlist);
//        mv.addObject("didlist", didlist);
//        mv.addObject("flag", flag);
//        return mv;
//    }
//    
    public ModelAndView interestProcessingChecker(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String yes = request.getParameter("yes");
        String no = request.getParameter("no");
        if (yes == null) {
            yes = "";
        }
        if (no == null) {
            no = "";
        }

        System.out.println("Yes is :" + yes + " No is :" + no);

        InterestDetailsDAO iddao = new InterestDetailsDAO();
        DisbursedInterestDetailsDAO didao = new DisbursedInterestDetailsDAO();
        ToInterestDetailsDAO toiddao = new ToInterestDetailsDAO();
        ToDisbursedInterestDetailsDAO todidao = new ToDisbursedInterestDetailsDAO();
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();

        if (yes.equalsIgnoreCase("yes")) {

            List<InterestDetails> tempintdetlis = iddao.getInterestDetailsforChecker();
            List<DisbursedInterestDetails> tempdisintlist = didao.getDisbursedInterestDetailsforChecker();

            if (tempintdetlis != null && !(tempintdetlis.isEmpty())) {
                for (InterestDetails eleint : tempintdetlis) {
                    DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(eleint.getCorporates());
                    reconcorp.setWeekId(eleint.getWeekId());
                    reconcorp.setBillEntryDate(eleint.getEntryDate());
                    reconcorp.setBillType(eleint.getBillType());
                    reconcorp.setBillingDate(eleint.getBillingDate());
                    reconcorp.setRevisionNo(eleint.getRevisionNo());
                    reconcorp.setBillDueDate(eleint.getBillingDuedate());
                    reconcorp.setPayTotalnet(eleint.getInterestAmount());
                    reconcorp.setEntryDate(new Date());
                    Date dateyear = eleint.getBillingDate();
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                    cal.setTime(dateyear);
                    int year = cal.get(Calendar.YEAR);
                    reconcorp.setBillYear(eleint.getBillYear());
                    reconcorp.setPayFinalamount(eleint.getInterestAmount());
                    reconcorp.setPayPendingamount(eleint.getInterestAmount());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(eleint.getCorporates().getCorporateId());
                    outstanding = eleint.getInterestAmount().add(outstanding);
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Interest Pending");
                    reconcorpdao.NewReconciliationCorp(reconcorp);

                }
            }

            if (tempdisintlist != null && !(tempdisintlist.isEmpty())) {
                for (DisbursedInterestDetails eleintdis : tempdisintlist) {

                    DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();
                    DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                    BigDecimal slno = new BigDecimal(0);
                    slno = new BigDecimal(reconcorpdao.getMaxslno());
                    slno = slno.add(BigDecimal.ONE);
                    reconcorp.setSlno(slno);
                    reconcorp.setCorporates(eleintdis.getCorporates());
                    reconcorp.setWeekId(eleintdis.getWeekId());
                    reconcorp.setBillEntryDate(eleintdis.getBillingDate());
                    reconcorp.setBillType(eleintdis.getBillType());
                    reconcorp.setBillingDate(eleintdis.getBillingDate());
                    reconcorp.setRevisionNo(eleintdis.getRevisionNo());
                    reconcorp.setBillDueDate(eleintdis.getBillingDuedate());
                    reconcorp.setRecTotalnet(eleintdis.getInterestAmount());
                    reconcorp.setEntryDate(new Date());
                    Date dateyear = eleintdis.getBillingDate();
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                    cal.setTime(dateyear);
                    int year = cal.get(Calendar.YEAR);
                    reconcorp.setBillYear(eleintdis.getBillYear());
                    reconcorp.setRecFinalamount(eleintdis.getInterestAmount());
                    reconcorp.setRecPendingamount(eleintdis.getInterestAmount());
                    BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(eleintdis.getCorporates().getCorporateId());
                    outstanding = outstanding.subtract(eleintdis.getInterestAmount());
                    reconcorp.setOutstandingAmount(outstanding);
                    reconcorp.setRemarks("Disbursed Interest Pending");
                    reconcorpdao.NewReconciliationCorp(reconcorp);

                }
            }

            iddao.updateInterestDetailsbyChecker();
            didao.updateDisbursedInterestDetailsbyChecker();
            tmepindao.getUpdateToInterestDetailsbtoverified();
            tmepindao.getUpdateTodisburseInterestDetailsbtoverified();
        }

        if (no.equalsIgnoreCase("no")) {
            iddao.rejectInterestDetailsbyChecker();
            didao.rejectDisbursedInterestDetailsbyChecker();

            toiddao.rejectToInterest_DetailsfromChecker();
            todidao.rejectToDisburseInterest_DetailsfromChecker();
        }

        List<InterestDetails> idlist = iddao.getInterestDetailsforChecker();
        List<DisbursedInterestDetails> didlist = didao.getDisbursedInterestDetailsforChecker();

        String loginid = (String) session1.getAttribute("loginid");
        int flag = 00;

        if (loginid.equalsIgnoreCase("sysadmin") || loginid.equalsIgnoreCase("CHECKER")) {
            flag = 111;
        }

        System.out.println("Login is : flag is :" + loginid + " " + flag);

        ModelAndView mv = new ModelAndView("interestProcessingChecker");

        mv.addObject("idlist", idlist);
        mv.addObject("didlist", didlist);
        mv.addObject("flag", flag);
        return mv;
    }

    public ModelAndView getInterestVerifyReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String getInterest = request.getParameter("getInterest");
        if (getInterest != null) {

            String startdate = request.getParameter("date1");
            String enddate = request.getParameter("date2");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];
            System.out.println("startdate =" + startdate);
            System.out.println("enddate =" + enddate);
            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            ModelAndView mv = new ModelAndView("Report/InterestVerifyReport");

            ToInterestDetailsDAO tointddao = new ToInterestDetailsDAO();
            ToDisbursedInterestDetailsDAO todisbintddao = new ToDisbursedInterestDetailsDAO();
            RevertTempInterestDetailsDAO revtempintddao = new RevertTempInterestDetailsDAO();
            RevertTempDisbInterestDAO revtempdisbintddao = new RevertTempDisbInterestDAO();

//            List<ToInterestDetails> paylist = tointddao.getAllToInterestDetails();
//            List<ToDisbursedInterestDetails> reclist = todisbintddao.getAllToDisbursedInterestDetails();
//            List<RevertTempInterestDetails> revpaylist = revtempintddao.getrevTempInterestDetails("NOTREVERT");
//            List<RevertTempDisbInterest> revreclist = revtempdisbintddao.getrevTempdisinterest("NOTREVERT");
            List<ToInterestDetails> paylist = tointddao.getToInterestDetailsbydates(startDate, endDate);
            List<ToDisbursedInterestDetails> reclist = todisbintddao.getToDisbInterestDetailsbydates(startDate, endDate);

            mv.addObject("paylist", paylist);
            mv.addObject("reclist", reclist);
            mv.addObject("startdate", startdate);
            mv.addObject("enddate", enddate);
//            mv.addObject("revpaylist", revpaylist);
//            mv.addObject("revreclist", revreclist);

            return mv;
        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        return mv1;
    }

    public ModelAndView getInterestUNVerifyReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String getInterest = request.getParameter("getInterest");
        if (getInterest != null) {

            String startdate = request.getParameter("date1");
            String enddate = request.getParameter("date2");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];
            System.out.println("startdate =" + startdate);
            System.out.println("enddate =" + enddate);
            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            ModelAndView mv = new ModelAndView("Report/InterestunVerifyReport");

            ToInterestDetailsDAO tointddao = new ToInterestDetailsDAO();
            ToDisbursedInterestDetailsDAO todisbintddao = new ToDisbursedInterestDetailsDAO();
            RevertTempInterestDetailsDAO revtempintddao = new RevertTempInterestDetailsDAO();
            RevertTempDisbInterestDAO revtempdisbintddao = new RevertTempDisbInterestDAO();

            List<RevertTempInterestDetails> revpaylist = revtempintddao.getRevertTempInterestDetailsbydates("NOTREVERT", startDate, endDate);
            List<RevertTempDisbInterest> revreclist = revtempdisbintddao.getRevertTempDisbInterestbydates("NOTREVERT", startDate, endDate);

            mv.addObject("revpaylist", revpaylist);
            mv.addObject("revreclist", revreclist);
            mv.addObject("startdate", startdate);
            mv.addObject("enddate", enddate);
            return mv;
        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        return mv1;
    }

    public ModelAndView getInterestPublishReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String getInterest = request.getParameter("getInterest");
        if (getInterest != null) {

            String startdate = request.getParameter("date1");
            String enddate = request.getParameter("date2");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];
            System.out.println("startdate =" + startdate);
            System.out.println("enddate =" + enddate);

            ModelAndView mv = new ModelAndView("Report/InterestPublishReport");

            InterestDetailsDAO iddao = new InterestDetailsDAO();
            DisbursedInterestDetailsDAO diddao = new DisbursedInterestDetailsDAO();

            List<InterestDetails> intd = iddao.getInterestDetailsByPublishDates(startDate, endDate);
            List<DisbursedInterestDetails> dintd = diddao.getDisbursedInterestDetailsByPublishDates(startDate, endDate);
//            List<InterestDetails> intd = iddao.getAllInterestDetails();
//            List<DisbursedInterestDetails> dintd = diddao.getAllDisbursedInterestDetails();

            mv.addObject("intd", intd);
            mv.addObject("dintd", dintd);
            return mv;

        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        return mv1;
    }

    //===============================================================================================
    public ModelAndView interestPaidVerifiedExcelExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        System.out.println("startdate =" + startdate);
        System.out.println("enddate =" + enddate);

        List<ToInterestDetails> list = null;
        ToInterestDetailsDAO tmepindao = new ToInterestDetailsDAO();
        list = tmepindao.getToInterestDetailsbydates(startDate, endDate);

        if (list.size() > 0) {
            List<String> corpnamesinlist = new ArrayList<String>();

            for (ToInterestDetails corpname : list) {
                if (!corpnamesinlist.contains(corpname.getCorporates().getCorporateName())) {
                    corpnamesinlist.add(corpname.getCorporates().getCorporateName());
                }
            }
            System.out.println("Size is " + corpnamesinlist.size());

            Collections.sort(corpnamesinlist);

            String[] columns = {"INTEREST ID", "WEEK ID", "CORPORATE ID", "REVISION NO", "BILL TYPE", "BILL AMOUNT", "BILLING DUEDATE", "BANK PAID DATE", "NO OF DAYS", "BILL AMOUNT FOR INTEREST", "INTEREST AMOUNT", "BILL YEAR", "REMARKS", "DATE OF VERIFICATION"};

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            Row headerrow = null;

            for (int i = 0; i < corpnamesinlist.size(); i++) {
                sheet = workbook.createSheet(corpnamesinlist.get(i));
                headerrow = sheet.createRow(0);
                Cell cell = null;

                for (int j = 0; j < columns.length; j++) {
                    cell = headerrow.createCell(j);
                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    headerrow.getCell(j).setCellStyle(style);
                    cell.setCellValue(columns[j]);
                }

                int rownum = 0;
                for (ToInterestDetails idlist : list) {
                    if (corpnamesinlist.get(i).equalsIgnoreCase(idlist.getCorporates().getCorporateName())) {
                        rownum = sheet.getLastRowNum();
                        rownum = rownum + 1;
                        headerrow = sheet.createRow(rownum);

                        for (int z = 0; z < columns.length; z++) {
                            switch (z) {
                                case 0:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue("I" + idlist.getInterestId().toString());
                                    break;
                                case 1:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getWeekId().toString());
                                    break;
                                case 2:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getCorporates().getCorporateId());
                                    break;
                                case 3:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRevisionNo().toString());
                                    break;
                                case 4:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillType());
                                    break;
                                case 5:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBilledAmount().toString());
                                    break;
                                case 6:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillingDuedate().toString());
                                    break;
                                case 7:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getPaidDate().toString());
                                    break;
                                case 8:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getNoofdays().toString());
                                    break;
                                case 9:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestBilledamount().toString());
                                    break;
                                case 10:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestAmount().toString());
                                    break;
                                case 11:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillYear().toString());
                                    break;
                                case 12:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRemarks());
                                    break;
                                case 13:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getEntryDate().toString());
                                    break;
                            }
                        }
                    }
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String filedate = sdf.format(new Date());
            String filename = "Interest_Amounts_by_" + filedate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
        }

        ModelAndView mv = new ModelAndView("Report/InterestVerifyReport");
        return mv;
    }

    public ModelAndView interestPaidUNVerifiedExcelExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        System.out.println("startdate =" + startdate);
        System.out.println("enddate =" + enddate);

        List<RevertTempInterestDetails> list = null;
        RevertTempInterestDetailsDAO tmepindao = new RevertTempInterestDetailsDAO();
        list = tmepindao.getRevertTempInterestDetailsbydates("NOTREVERT", startDate, endDate);

        if (list.size() > 0) {
            List<String> corpnamesinlist = new ArrayList<String>();

            for (RevertTempInterestDetails corpname : list) {
                if (!corpnamesinlist.contains(corpname.getTempInterestDetails().getCorporates().getCorporateName())) {
                    corpnamesinlist.add(corpname.getTempInterestDetails().getCorporates().getCorporateName());
                }
            }
            System.out.println("Size is " + corpnamesinlist.size());

            Collections.sort(corpnamesinlist);

            String[] columns = {"INTEREST ID", "WEEK ID", "CORPORATE ID", "REVISION NO", "BILL TYPE", "BILL AMOUNT", "BILLING DUEDATE", "BANK PAID DATE", "NO OF DAYS", "BILL AMOUNT FOR INTEREST", "INTEREST AMOUNT", "BILL YEAR", "REMARKS", "DATE OF VERIFICATION"};

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            Row headerrow = null;

            for (int i = 0; i < corpnamesinlist.size(); i++) {
                sheet = workbook.createSheet(corpnamesinlist.get(i));
                headerrow = sheet.createRow(0);
                Cell cell = null;

                for (int j = 0; j < columns.length; j++) {
                    cell = headerrow.createCell(j);
                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    headerrow.getCell(j).setCellStyle(style);
                    cell.setCellValue(columns[j]);
                }

                int rownum = 0;
                for (RevertTempInterestDetails idlist : list) {
                    if (corpnamesinlist.get(i).equalsIgnoreCase(idlist.getTempInterestDetails().getCorporates().getCorporateName())) {
                        rownum = sheet.getLastRowNum();
                        rownum = rownum + 1;
                        headerrow = sheet.createRow(rownum);

                        for (int z = 0; z < columns.length; z++) {
                            switch (z) {
                                case 0:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue("I" + idlist.getTempInterestDetails().getInterestId().toString());
                                    break;
                                case 1:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getWeekId().toString());
                                    break;
                                case 2:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getCorporates().getCorporateId());
                                    break;
                                case 3:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getRevisionNo().toString());
                                    break;
                                case 4:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getBillType());
                                    break;
                                case 5:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getBilledAmount().toString());
                                    break;
                                case 6:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getBillingDuedate().toString());
                                    break;
                                case 7:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getPaidDate().toString());
                                    break;
                                case 8:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getNoofdays().toString());
                                    break;
                                case 9:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getInterestBilledamount().toString());
                                    break;
                                case 10:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getInterestAmount().toString());
                                    break;
                                case 11:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempInterestDetails().getBillYear().toString());
                                    break;
                                case 12:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRemarks());
                                    break;
                                case 13:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getEntryDate().toString());
                                    break;
                            }
                        }
                    }
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String filedate = sdf.format(new Date());
            String filename = "Interest_Amounts_by_" + filedate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
        }

        ModelAndView mv = new ModelAndView("Report/InterestunVerifyReport");
        return mv;
    }

    public ModelAndView interestDisburseVerifiedExcelExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        System.out.println("startdate =" + startdate);
        System.out.println("enddate =" + enddate);
        List<ToDisbursedInterestDetails> list1 = null;
        ToDisbursedInterestDetailsDAO tempdisintedao = new ToDisbursedInterestDetailsDAO();
        list1 = tempdisintedao.getToDisbInterestDetailsbydates(startDate, endDate);

        if (list1.size() > 0) {
            List<String> corpnamesinlist = new ArrayList<String>();

            for (ToDisbursedInterestDetails corpname : list1) {
                if (!corpnamesinlist.contains(corpname.getCorporates().getCorporateName())) {
                    corpnamesinlist.add(corpname.getCorporates().getCorporateName());
                }
            }
            System.out.println("Size is " + corpnamesinlist.size());

            Collections.sort(corpnamesinlist);

            String[] columns = {"INTEREST ID", "WEEK ID", "CORPORATE ID", "REVISION NO", "BILL TYPE", "BILL AMOUNT", "BILLING DUEDATE", "DISBURSED DATE", "NO OF DAYS", "BILL AMOUNT FOR INTEREST", "INTEREST AMOUNT", "BILL YEAR", "REMARKS", "DATE OF VERIFICATION"};

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            Row headerrow = null;

            for (int i = 0; i < corpnamesinlist.size(); i++) {
                sheet = workbook.createSheet(corpnamesinlist.get(i));
                headerrow = sheet.createRow(0);
                Cell cell = null;

                for (int j = 0; j < columns.length; j++) {
                    cell = headerrow.createCell(j);
                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    headerrow.getCell(j).setCellStyle(style);
                    cell.setCellValue(columns[j]);
                }

                int rownum = 0;
                for (ToDisbursedInterestDetails idlist : list1) {
                    if (corpnamesinlist.get(i).equalsIgnoreCase(idlist.getCorporates().getCorporateName())) {
                        rownum = sheet.getLastRowNum();
                        rownum = rownum + 1;
                        headerrow = sheet.createRow(rownum);

                        for (int z = 0; z < columns.length; z++) {
                            switch (z) {
                                case 0:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue("I" + idlist.getInterestId().toString());
                                    break;
                                case 1:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getWeekId().toString());
                                    break;
                                case 2:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getCorporates().getCorporateId());
                                    break;
                                case 3:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRevisionNo().toString());
                                    break;
                                case 4:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillType());
                                    break;
                                case 5:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBilledAmount().toString());
                                    break;
                                case 6:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillingDuedate().toString());
                                    break;
                                case 7:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getDisbursedDate().toString());
                                    break;
                                case 8:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getNoofdays().toString());
                                    break;
                                case 9:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestBilledamount().toString());
                                    break;
                                case 10:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getInterestAmount().toString());
                                    break;
                                case 11:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getBillYear().toString());
                                    break;
                                case 12:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRemarks());
                                    break;
                                case 13:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getEntryDate().toString());
                                    break;
                            }
                        }
                    }
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String filedate = sdf.format(new Date());
            String filename = "Interest_Disburse_Amounts_by_" + filedate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
        }

        ModelAndView mv = new ModelAndView("Report/InterestVerifyReport");
        return mv;
    }

    public ModelAndView interestDisburseUNVerifiedExcelExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        System.out.println("startdate =" + startdate);
        System.out.println("enddate =" + enddate);

        List<RevertTempDisbInterest> list1 = null;
        RevertTempDisbInterestDAO tempdisintedao = new RevertTempDisbInterestDAO();
        list1 = tempdisintedao.getRevertTempDisbInterestbydates("NOTREVERT", startDate, endDate);

        if (list1.size() > 0) {
            List<String> corpnamesinlist = new ArrayList<String>();

            for (RevertTempDisbInterest corpname : list1) {
                if (!corpnamesinlist.contains(corpname.getTempDisbInterestDetails().getCorporates().getCorporateName())) {
                    corpnamesinlist.add(corpname.getTempDisbInterestDetails().getCorporates().getCorporateName());
                }
            }
            System.out.println("Size is " + corpnamesinlist.size());

            Collections.sort(corpnamesinlist);

            String[] columns = {"INTEREST ID", "WEEK ID", "CORPORATE ID", "REVISION NO", "BILL TYPE", "BILL AMOUNT", "BILLING DUEDATE", "DISBURSED DATE", "NO OF DAYS", "BILL AMOUNT FOR INTEREST", "INTEREST AMOUNT", "BILL YEAR", "REMARKS", "DATE OF VERIFICATION"};

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            Row headerrow = null;

            for (int i = 0; i < corpnamesinlist.size(); i++) {
                sheet = workbook.createSheet(corpnamesinlist.get(i));
                headerrow = sheet.createRow(0);
                Cell cell = null;

                for (int j = 0; j < columns.length; j++) {
                    cell = headerrow.createCell(j);
                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    headerrow.getCell(j).setCellStyle(style);
                    cell.setCellValue(columns[j]);
                }

                int rownum = 0;
                for (RevertTempDisbInterest idlist : list1) {
                    if (corpnamesinlist.get(i).equalsIgnoreCase(idlist.getTempDisbInterestDetails().getCorporates().getCorporateName())) {
                        rownum = sheet.getLastRowNum();
                        rownum = rownum + 1;
                        headerrow = sheet.createRow(rownum);

                        for (int z = 0; z < columns.length; z++) {
                            switch (z) {
                                case 0:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue("I" + idlist.getTempDisbInterestDetails().getInterestId().toString());
                                    break;
                                case 1:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getWeekId().toString());
                                    break;
                                case 2:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getCorporates().getCorporateId());
                                    break;
                                case 3:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getRevisionNo().toString());
                                    break;
                                case 4:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getBillType());
                                    break;
                                case 5:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getBilledAmount().toString());
                                    break;
                                case 6:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getBillingDuedate().toString());
                                    break;
                                case 7:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getDisbursedDate().toString());
                                    break;
                                case 8:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getNoofdays().toString());
                                    break;
                                case 9:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getInterestBilledamount().toString());
                                    break;
                                case 10:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getInterestAmount().toString());
                                    break;
                                case 11:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getTempDisbInterestDetails().getBillYear().toString());
                                    break;
                                case 12:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getRemarks());
                                    break;
                                case 13:
                                    cell = headerrow.createCell(z);
                                    cell.setCellValue(idlist.getEntryDate().toString());
                                    break;
                            }
                        }
                    }
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String filedate = sdf.format(new Date());
            String filename = "Interest_Disburse_Amounts_by_" + filedate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
        }

        ModelAndView mv = new ModelAndView("Report/InterestunVerifyReport");
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
