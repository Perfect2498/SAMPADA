/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.AdjPaymentDAO;
import sampada.DAO.BankStatementDAO;
import sampada.DAO.BillAgcDetailsDAO;
import sampada.DAO.BillDsmDetailsDAO;
import sampada.DAO.BillEntityAgcDAO;
import sampada.DAO.BillEntityFrasDAO;
import sampada.DAO.BillEntityTRASEDAO;
import sampada.DAO.BillEntityTRASMDAO;
import sampada.DAO.BillEntityTRASSDAO;
import sampada.DAO.BillFrasDetailsDAO;
import sampada.DAO.BillInterestRateDAO;
import sampada.DAO.BillPayableCorpDAO;
import sampada.DAO.BillPayableEntityDsmDAO;
import sampada.DAO.BillPayableEntityRrasDAO;
import sampada.DAO.BillPriorityDAO;
import sampada.DAO.BillReceiveCorpDAO;
import sampada.DAO.BillReceiveEntityDsmDAO;
import sampada.DAO.BillReceiveEntityRrasDAO;
import sampada.DAO.BillRrasDetailsDAO;
import sampada.DAO.BillTRASEDetailsDAO;
import sampada.DAO.BillTRASMDetailsDAO;
import sampada.DAO.BillTRASSDetailsDAO;
import sampada.DAO.ConstantsMasterDAO;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.DisbursedInterestDetailsDAO;
import sampada.DAO.DynReconciliationCropDAO;
import sampada.DAO.EntityDAO;
import sampada.DAO.InterestDetailsDAO;
import sampada.DAO.MappingBillBankDAO;
import sampada.DAO.MappingInterestBankDAO;
import sampada.DAO.MappingRefundBankDAO;
import sampada.DAO.PaymentDisbursementDAO;
import sampada.DAO.PoolAccountDetailsDAO;
import sampada.DAO.PoolToIntDAO;
import sampada.DAO.PoolToPoolDAO;
import sampada.DAO.TempMapBankStatementDAO;
import sampada.DAO.TempDisbInterestDetailsDAO;
import sampada.DAO.TempInterestDetailsDAO;
import sampada.DAO.TempMappingBillBankDAO;
import sampada.DAO.TempRefundBillCorpDAO;
import sampada.DAO.miscDisbursementDAO;
import sampada.pojo.AdjPayment;
import sampada.pojo.AgcPoolAccountDetails;
import sampada.pojo.BankStatement;
import sampada.pojo.BillAgcDetails;
import sampada.pojo.BillDsmDetails;
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityFras;
import sampada.pojo.BillEntitySras;
import sampada.pojo.BillEntityTrasE;
import sampada.pojo.BillEntityTrasM;
import sampada.pojo.BillEntityTrasS;
import sampada.pojo.BillFrasDetails;
import sampada.pojo.BillInterestRate;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillPayableEntityRras;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.BillReceiveEntityDsm;
import sampada.pojo.BillReceiveEntityRras;
import sampada.pojo.BillRrasDetails;
import sampada.pojo.BillSrasDetails;
import sampada.pojo.BillTrasEDetails;
import sampada.pojo.BillTrasMDetails;
import sampada.pojo.BillTrasSDetails;
import sampada.pojo.Corporates;
import sampada.pojo.DynReconciliationCorp;
import sampada.pojo.InterestDetails;
import sampada.pojo.MappingBillBank;
import sampada.pojo.MappingInterestBank;
import sampada.pojo.MappingRefundBank;
import sampada.pojo.MiscDisbursement;
import sampada.pojo.PaymentDisbursement;
import sampada.pojo.PoolAccountDetails;
import sampada.pojo.PoolToInt;
import sampada.pojo.PoolToPool;
import sampada.pojo.ReconciliationCorp;
import sampada.pojo.RrasPoolAccountDetails;
import sampada.pojo.TempMapBankStatement;
import sampada.pojo.TempDisbInterestDetails;
import sampada.pojo.TempInterestDetails;
import sampada.pojo.TempMappingBillBank;
import sampada.pojo.TempRefundBillCorp;
import sampada.pojo.UserDetails;

/**
 *
 * @author JaganMohan
 */
public class BillProcessingController extends MultiActionController {

    public ModelAndView billProcessingPendingPayableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/billProcessingPendingPayableList");
        List<BillPayableCorp> list = null;
        List<BigDecimal> proint = new ArrayList<BigDecimal>();
        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
        list = billpaycorpdao.getPendingBillPayAllCorp();
        for (BillPayableCorp e : list) {
            Date date1 = e.getBillDueDate();
            Date date2 = new Date();
            long difference = date2.getTime() - date1.getTime();
            float daysBetween = 0;
            daysBetween = (difference / (1000 * 60 * 60 * 24));
//            System.out.println("daysBetween" + daysBetween);
            BigDecimal prointbg = new BigDecimal(0);
            BigDecimal rate = new BigDecimal(0.0004);
            prointbg = e.getPendingAmount().multiply(rate).multiply(new BigDecimal(daysBetween));
            proint.add(prointbg);

        }
        mv.addObject("proint", proint);
        mv.addObject("PendningList", list);
        return mv;
    }

//    public ModelAndView billVerification(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//
//        HttpSession session1 = request.getSession(false);
//        if (session1 == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/logout.htm");
//            return new ModelAndView(redirectView);
//        }
//
//        ModelAndView mv = new ModelAndView("BillProcessing/billVerification");
//        CorporatesDAO corpDao = new CorporatesDAO();
//        TempBankStatementDAO tempbakstdao = new TempBankStatementDAO();
//        String Submit = request.getParameter("confirm");
//        if (Submit != null) {
//
//            TempMappingBillBank tempmapbank = new TempMappingBillBank();
//            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
//
//            String uniqueId[] = request.getParameterValues("uniqueNo");
//            int maxid = 0;
//            String weekid1;
//            String category1;
//            String totalAmt1;
//            String settleAmt1;
//            String pendingAmt1;
//            String billtype1;
//            String uniqueid1;
//            String status;
//            String banktransbal;
//            String bankremarks;
//            BillPayableCorp billcorp = new BillPayableCorp();
//            Corporates corp = new Corporates();
//            UserDetails usr = new UserDetails();
//            usr.setLoginId("sysadmin");
//            BankStatement bankstmy = new BankStatement();
//            String corpid = request.getParameter("corpid");
//            String rowcount = request.getParameter("rowcount");
//            String bankrowcount1 = request.getParameter("bankrowcount");
//            System.out.println("bankrowcount1 " + bankrowcount1);
//            System.out.println("uniqueId[0] " + uniqueId[0]);
//            System.out.println("uniqueId.length is " + uniqueId.length);
//            System.out.println("rowcount " + rowcount);
//            if (Integer.parseInt(rowcount) >= 0) {
//
//                for (int i = 1; i <= Integer.parseInt(rowcount); i++) {
//
//                    for (int j = 0; j < uniqueId.length; j++) {
//                        uniqueid1 = request.getParameter("uniqueid" + i);
//                        System.out.println("uniqueid1 count " + uniqueid1 + " " + j);
//                        if (Integer.parseInt(uniqueid1) == Integer.parseInt(uniqueId[j])) {
//
//                            weekid1 = request.getParameter("weekid" + i);
//                            category1 = request.getParameter("category" + i);
//                            totalAmt1 = request.getParameter("totalAmt" + i);
//                            settleAmt1 = request.getParameter("settleAmt" + i);
//                            System.out.println("weekid1 " + weekid1);
//                            System.out.println("category1 " + category1);
//                            System.out.println("totalAmt1 " + totalAmt1);
//                            System.out.println("settleAmt1 " + settleAmt1);
//                            pendingAmt1 = request.getParameter("pendingAmt" + i);
//                            billtype1 = request.getParameter("billtype" + i);
//                            maxid = tempbillmapdao.getMaxUniqueID();
//                            maxid = maxid + 1;
//                            tempmapbank.setUniqueId(new BigDecimal(maxid));
//                            tempmapbank.setBillType(billtype1);
//                            tempmapbank.setCheckerStatus("Pending");
//                            tempmapbank.setEntryDate(new Date());
//                            tempmapbank.setMappedAmount(new BigDecimal(settleAmt1));
//                            tempmapbank.setOriginalAmount(new BigDecimal(totalAmt1));
//                            tempmapbank.setPaymentCategory(category1);
//                            tempmapbank.setPendingAmount(new BigDecimal(pendingAmt1));
//                            if (Integer.parseInt(pendingAmt1) == 0) {
//                                status = "PAID";
//                            } else {
//                                status = "PARTIALLY";
//                            }
//                            tempmapbank.setStatus(status);
//                            tempmapbank.setWeekId(new BigDecimal(weekid1));
//                            billcorp.setUniqueId(new BigDecimal(uniqueid1));
//                            tempmapbank.setBillPayableCorp(billcorp);
//                            corp.setCorporateId(Integer.parseInt(corpid));
//                            tempmapbank.setCorporates(corp);
//                            tempmapbank.setUserDetails(usr);
//
//                            String bankrowcount = request.getParameter("bankrowcount");
//                            System.out.println("bankrowcount is " + bankrowcount);
//                            int bankRowCounter = Integer.parseInt(bankrowcount);
//                            for (int k = 1; k <= bankRowCounter; k++) {
//                                String bankstmt1 = request.getParameter("bankstmt" + k);
//                                String bankstmt[] = request.getParameterValues("bankstmt");
//                                System.out.println("bankstmt[0] is " + bankstmt[0]);
//                                System.out.println("bankstmt1 is " + bankstmt1);
//                                if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
//                                    System.out.print("bank stmt id insert into temp mapped is " + bankstmt1);
//                                    bankstmy.setStmtId(new BigDecimal(bankstmt1));
//                                }
//
//                            }
//                            tempmapbank.setBankStatement(bankstmy);
//                            tempbillmapdao.NewTempMappingBillBank(tempmapbank);
//                        }
//                    }
//
//                }
//
//                String bankrowcount = request.getParameter("bankrowcount");
//                int maxid1 = 0;
//                String settleAmtBnk;
//                String remainBal1;
//                String totalamt1;
//                TempBankStatement tempbakst = new TempBankStatement();
//
//                for (int k = 1; k <= Integer.parseInt(bankrowcount); k++) {
//                    String bankstmt1 = request.getParameter("bankstmt" + k);
//                    String bankstmt[] = request.getParameterValues("bankstmt");
//
//                    if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
//                        bankstmy.setStmtId(new BigDecimal(bankstmt1));
//                        maxid1 = tempbakstdao.getMaxUniqueID();
//                        maxid1 = maxid1 + 1;
//                        tempbakst.setTempStmtid(new BigDecimal(maxid1));
//                        tempbakst.setBankStatement(bankstmy);
//
//                        banktransbal = request.getParameter("remainBal" + k);
//                        settleAmtBnk = request.getParameter("settleAmtBnk" + k);
//                        bankremarks = request.getParameter("remarks" + k);
//                        totalamt1 = request.getParameter("totalamt" + k);
//
//                        tempbakst.setMappedAmount(new BigDecimal(settleAmtBnk));
//                        tempbakst.setRemarks(bankremarks);
//                        tempbakst.setTransactionBalance(new BigDecimal(banktransbal));
//                        tempbakst.setTransactionAmount(new BigDecimal(totalamt1));
//                        tempbakst.setCorporateId(new BigDecimal(corpid));
//                        tempbakst.setCheckerStatus("Pending");
//                        tempbakstdao.NewTempBankStatement(tempbakst);
//
//                    }
//
//                }
//
//                //////////////////to preview the payment verfication mapping
//                ModelAndView mv1 = new ModelAndView("BillProcessing/viewMakerPendingPayableBillbyRLDC");
//                TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
//                List<TempBankStatement> list123 = new ArrayList<>();
//                List<TempBankStatement> list9 = new ArrayList<>();
//                List<TempMappingBillBank> list = new ArrayList<>();
//                List<BigDecimal> bankStmtIds = new ArrayList<>();
//
//                list = tmpmapdao.getTempMappingBillBankbyCorpID(Integer.parseInt(corpid), "Pending");
//                System.out.println("list size is " + list.size());
//                if (list.size() > 0) {
//
//                    for (TempMappingBillBank temp : list) {
//                        System.out.println("BankStmtID is " + temp.getBankStatement().getStmtId());
//                        bankStmtIds.add(temp.getBankStatement().getStmtId());
//                    }
//                    for (BigDecimal stmtId : bankStmtIds) {
//                        list9 = tempbakstdao.getTempBankStatementbySTMTID(stmtId);
//                        for (TempBankStatement temp : list9) {
//                            list123.add(temp);
//                        }
//
//                    }
//
////                    list123 = tempbakstdao.getTempBankStatementbySTMTID(list.get(0).getBankStatement().getStmtId());
//                }
//
//                CorporatesDAO cordao = new CorporatesDAO();
//                String corpname = cordao.getCorporateNamebyID(Integer.parseInt(corpid));
//
//                mv1.addObject("tempmaplist", list);
//
//                mv1.addObject("bankList", list123);
//                mv1.addObject("CorpID", Integer.parseInt(corpid));
//                mv1.addObject("CorpName", corpname);
//                mv1.addObject("successMSG", "Sucessfully Submited ....");
//                return mv1;
//
//            }
//        }// end of adding into temptables/Confirm button
//
//        String bdelete = request.getParameter("bdelete");
//        if (bdelete != null) {
//            String CorpID = request.getParameter("CorpID");
//            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
//            tempbillmapdao.getDeletebyMakerforCorpID(Integer.parseInt(CorpID), "Pending");
//            tempbakstdao.getDeletedTempBankStatementbyCorp(Integer.parseInt(CorpID), "Pending");
//            ModelAndView mv1 = new ModelAndView("successMsg");
//            String Msg = "Successfully Deleted for the records!!";
//            mv1.addObject("Msg", Msg);
//            return mv1;
//        }
//        String bName = request.getParameter("bName");
//        if (bName != null) {
//            List<TempMappingBillBank> list = null;
//            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
//
//            System.out.println("bName NOT NULL & is " + bName);
//            String corpName = request.getParameter("corpId");
//            int corpid = corpDao.getCorpIdbyName(corpName);
//            int corpId = corpid;
//            System.out.println("corpName is " + corpName);
//
//            BankStatementDAO bankStmtDao = new BankStatementDAO();
//            BillPayableCorpDAO billPayCorp = new BillPayableCorpDAO();
//            BillPayableCorp billpaycorp = null;
//
//            List<Object[]> billpayObjlist = new ArrayList<Object[]>();
//            List<Object[]> list123 = new ArrayList<Object[]>();
//            List<Object[]> list456 = new ArrayList<Object[]>();
//
//            List<BillPayableCorp> billPayList = new ArrayList();
//            list123 = billPayCorp.getPendingPayablebyCorpgroupbyBillingDate();
//            list456 = billPayCorp.getPendingPaymentbyCorpgroupbyWeekID();
//            billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId), new BigDecimal(2019));
//            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//
//            for (Object[] result : billpayObjlist) {
//                billpaycorp = new BillPayableCorp();
//                BigDecimal uniuqeid = (BigDecimal) result[0];
//                BigDecimal weekid = (BigDecimal) result[8];
//                BigDecimal totalnet = (BigDecimal) result[5];
//                BigDecimal pending = (BigDecimal) result[6];
//                BigDecimal revisionno = (BigDecimal) result[9];
//
//                System.out.println("uniuqeid is " + uniuqeid);
//                System.out.println("weekid is " + weekid);
//                System.out.println("totalnet is " + totalnet);
//                System.out.println("pending is " + pending);
//                System.out.println("revisionno is " + revisionno);
//                billpaycorp.setUniqueId(uniuqeid);
//                billpaycorp.setBillType((String) result[1]);
//                billpaycorp.setBillCategory((String) result[2]);
//                billpaycorp.setWeekId(weekid);
//                billpaycorp.setBillDueDate((Date) result[4]);
//                billpaycorp.setTotalnet(totalnet);
//                billpaycorp.setPendingAmount(pending);
//                billpaycorp.setBillingDate((Date) result[3]);
//                billpaycorp.setRevisionNo(revisionno);
//                billPayList.add(billpaycorp);
//                uniqueIdListBillPayCorp.add(uniuqeid);
//            }
//            System.out.println("uniqueIdListBillPayCorp size is " + uniqueIdListBillPayCorp.size());
//            System.out.println(" billPayList is " + billPayList.size());
//            List<BankStatement> bnkstmtList = bankStmtDao.BankStatementlist(corpid);
//            List<BankStatement> bnkstmtOrderList = bankStmtDao.BankStatementOrderlist(corpid);
//
//           
//            
//            if (billpayObjlist != null && !(billpayObjlist.isEmpty()) && bnkstmtList != null && !(bnkstmtList.isEmpty())) {
//                System.out.println("billPayList size is " + billPayList.size());
//                System.out.println("bnkstmtList size is " + bnkstmtList.size());
//                ArrayList list999 = new ArrayList();
//                Map author1 = new HashMap();
//                Map author2 = new HashMap();
//                Map author3 = new HashMap();
//
//                author1.put("Priority", "DSM");
//                list999.add(author1);
//                author2.put("Priority", "RRAS");
//                list999.add(author2);
//                author3.put("Priority", "AGC");
//                list999.add(author3);
//
//               
//                ModelAndView mv1 = new ModelAndView("BillProcessing/billPayableDisplay");
//
//                mv1.addObject("corpName", corpName);
//                mv1.addObject("corpid", corpid);
//                mv1.addObject("bnkstmtList", bnkstmtList);
//               // mv1.addObject("bnkstmtOrderList", bnkstmtOrderList);
//               
//                mv1.addObject("billPayList", billPayList);
//                mv1.addObject("billdateList", list123);
//                mv1.addObject("weekList", list456);
//                mv1.addObject("priorityList", list999);
//               
//
//               
//                List<BillPayableCorp> billPayNotinTempList = null;
//                 BillPayableCorpDAO billpaydao=new BillPayableCorpDAO();
//                 billPayNotinTempList=billpaydao.BillPayableCorpNotInTemplist(corpId);
//                 mv1.addObject("tempbillPayList", billPayNotinTempList);
//                
//                TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
//                System.out.println("CorpName is " + corpName);
//                List<TempBankStatement> list4 = null;
//                List<TempMappingBillBank> list5 = null;
//                list5 = tmpmapdao.getTempMappingBillBankbyCorpID(corpid, "Pending");
//            
//                String flag="NoPending";
//                HashMap<Integer,String> hm=new HashMap<Integer,String>();
//                int pendcount=0;               
//                for(TempMappingBillBank e:list5)
//                {
//                    if(e.getPendingAmount().compareTo(BigDecimal.ZERO)==0)
//                    {
//                       hm.put(e.getBillPayableCorp().getUniqueId().intValue(), flag);
//                    }                
//                   
//                }
//                                            
//                if (list5.size() > 0) {
//                  //  list4 = tempbakstdao.getTempBankStatementbyStatus("Pending",corpid);
//                   
//                    list4 = tempbakstdao.getMaxTempBankStatementbyStatus("Pending",corpid);
//                   
//                   
//                }
//                
//                List<BankStatement> list6 = null;
//                TempMappingBillBankDAO tempmapbilldao=new TempMappingBillBankDAO();
//                list6 = tempbakstdao.getBankStatementNotINTempBankStatement("Pending", corpId);
//                List<TempMappingBillBank> list7=null;
//                list7=tempmapbilldao.getMaxTempMappingBillBankbyCorpID(corpId,"Pending");
//              
//                mv1.addObject("tempmaplist", list5);
//                mv1.addObject("tempmapMaxlist", list7);
//                mv1.addObject("tempBankStmtlist", list6);
//                mv1.addObject("bankList", list4);
//                mv1.addObject("CorpID", corpid);
//                mv1.addObject("hm", hm);
//                return mv1;
//
//              
//            } else {
//                System.out.println("Record not found");
//                ModelAndView mv1 = new ModelAndView("successMsg");
//                String Msg = "Please check whether - No Pending Bills OR No Bank Transaction for Verification!";
//                mv1.addObject("Msg", Msg);
//                return mv1;
//            }
////            } //end of button
////            else {
////                ModelAndView mv1 = new ModelAndView("successMsg");
////                String Msg = "Some Pending Payment Verification by Checker. Please clear it....";
////                mv1.addObject("Msg", Msg);
////                return mv1;
////
////            }
//        }
//        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
//        List<BigDecimal> list = new ArrayList<>();
//        list = tmpmapdao.getTempMappingBillBankbyPendingCorpList("Pending");
//        System.out.println("list is " + list);
//        List<String> pendingVerifyCorpList = new ArrayList<>();
//        for (BigDecimal result : list) {
//            System.out.println("corporate is " + result);
//            Integer corpId = result.intValueExact();
//            String corp = corpDao.getCorpNamebyId(corpId);
//            System.out.println("corp is " + corp);
//            pendingVerifyCorpList.add(corp);
//        }
//
//        mv.addObject("pendingVerifyCorpList", pendingVerifyCorpList);
//        List<BillPayableCorp> listBillPayableCorp = new ArrayList<>();
//        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
//        List<String> listCorp = new ArrayList<>();
//        listBillPayableCorp = billpaycorpdao.getPendingBillCorpNameList();
//        for (BillPayableCorp temp : listBillPayableCorp) {
//            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
//            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
//
//            if (ans); else {
//                listCorp.add(temp.getCorporates().getCorporateName());
//            }
//        }
//
//        mv.addObject("PendningList", list);
//        mv.addObject("corpList", listCorp);
//
////        mv.addObject("corpList", corpList);
//        return mv;
//    }
//   public ModelAndView billVerification(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        HttpSession session1 = request.getSession(false);
//        if (session1 == null) {
//            RedirectView redirectView = new RedirectView();
//            redirectView.setContextRelative(true);
//            redirectView.setUrl("/logout.htm");
//            return new ModelAndView(redirectView);
//        }
//        ModelAndView mv = new ModelAndView("BillProcessing/billVerification");
//        CorporatesDAO corpDao = new CorporatesDAO();
//        TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
//        String Submit = request.getParameter("confirm");
//        if (Submit != null) {
//            System.out.println("on Click of bName button  from billPayableDisplay.jsp");
//            TempMappingBillBank tempmapbank = new TempMappingBillBank();
//            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
//            String uniqueId[] = request.getParameterValues("uniqueNo");
//            int maxid = 0;
//            String weekid1;
//            String category1;
//            String totalAmt1;
//            String settleAmt1;
//            String pendingAmt1;
//            String billtype1;
//            String uniqueid1;
//            String status;
//            String banktransbal;
//            String bankremarks;
//            BillPayableCorp billcorp = new BillPayableCorp();
//            Corporates corp = new Corporates();
//            UserDetails usr = new UserDetails();
//            usr.setLoginId("sysadmin");
//            BankStatement bankstmy = new BankStatement();
//            String corpid = request.getParameter("corpid");
//            String rowcount = request.getParameter("rowcount");
//            String bankrowcount1 = request.getParameter("bankrowcount");
//            System.out.println("bankrowcount1 " + bankrowcount1);
//            System.out.println("uniqueId[0] " + uniqueId[0]);
//            System.out.println("uniqueId.length is " + uniqueId.length);
//            System.out.println("rowcount " + rowcount);
//            if (Integer.parseInt(rowcount) >= 0) {
//                for (int i = 1; i <= Integer.parseInt(rowcount); i++) {
//                    for (int j = 0; j < uniqueId.length; j++) {
//                        uniqueid1 = request.getParameter("uniqueid" + i);
//                        System.out.println("uniqueid1 count " + uniqueid1 + " " + j);
//                        if (Integer.parseInt(uniqueid1) == Integer.parseInt(uniqueId[j])) {
//                            weekid1 = request.getParameter("weekid" + i);
//                            category1 = request.getParameter("category" + i);
//                            totalAmt1 = request.getParameter("totalAmt" + i);
//                            settleAmt1 = request.getParameter("settleAmt" + i);
//                            System.out.println("weekid1 " + weekid1);
//                            System.out.println("category1 " + category1);
//                            System.out.println("totalAmt1 " + totalAmt1);
//                            System.out.println("settleAmt1 " + settleAmt1);
//                            pendingAmt1 = request.getParameter("pendingAmt" + i);
//                            billtype1 = request.getParameter("billtype" + i);
////                            if(Integer.parseInt(settleAmt1) !=0 )
//                            if (new BigDecimal(settleAmt1).compareTo(BigDecimal.ZERO) != 0) {
//                                maxid = tempbillmapdao.getMaxUniqueID();
//                                maxid = maxid + 1;
//                                tempmapbank.setUniqueId(new BigDecimal(maxid));
//                                tempmapbank.setBillType(billtype1);
//                                tempmapbank.setCheckerStatus("Pending");
//                                tempmapbank.setEntryDate(new Date());
//                                tempmapbank.setMappedAmount(new BigDecimal(settleAmt1));
//                                tempmapbank.setOriginalAmount(new BigDecimal(totalAmt1));
//                                tempmapbank.setPaymentCategory(category1);
//                                tempmapbank.setPendingAmount(new BigDecimal(pendingAmt1));
//                                if (new BigDecimal(pendingAmt1).compareTo(BigDecimal.ZERO) == 0) {
//                                    status = "PAID";
//                                } else {
//                                    status = "PARTIALLY";
//                                }
//                                tempmapbank.setStatus(status);
//                                tempmapbank.setWeekId(new BigDecimal(weekid1));
//                                billcorp.setUniqueId(new BigDecimal(uniqueid1));
//                                tempmapbank.setBillPayableCorp(billcorp);
//                                corp.setCorporateId(Integer.parseInt(corpid));
//                                tempmapbank.setCorporates(corp);
//                                tempmapbank.setUserDetails(usr);
//                                
//                                String bankrowcount = request.getParameter("bankrowcount");
//                                System.out.println("bankrowcount is " + bankrowcount);
//                                int bankRowCounter = Integer.parseInt(bankrowcount);
//                                for (int k = 1; k <= bankRowCounter; k++) {
//                                    String bankstmt1 = request.getParameter("bankstmt" + k);
//                                    String bankstmt[] = request.getParameterValues("bankstmt");
//                                    System.out.println("bankstmt[0] is " + bankstmt[0]);
//                                    System.out.println("bankstmt1 is " + bankstmt1);
//                                    if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
//                                        System.out.print("bank stmt id insert into temp mapped is " + bankstmt1);
//                                        bankstmy.setStmtId(new BigDecimal(bankstmt1));
//                                    }
//                                }
//                                tempmapbank.setBankStatement(bankstmy);
//                                tempbillmapdao.NewTempMappingBillBank(tempmapbank);
//                            }//end of sttle 0
//                        }
//                    }
//                }
//                String bankrowcount = request.getParameter("bankrowcount");
//                int maxid1 = 0;
//                String settleAmtBnk;
//                String remainBal1;
//                String totalamt1;
//                TempMapBankStatement tempbakst = new TempMapBankStatement();
//                for (int k = 1; k <= Integer.parseInt(bankrowcount); k++) {
//                    String bankstmt1 = request.getParameter("bankstmt" + k);
//                    String bankstmt[] = request.getParameterValues("bankstmt");
//                    if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
//                        bankstmy.setStmtId(new BigDecimal(bankstmt1));
//                        maxid1 = tempbakstdao.getMaxUniqueID();
//                        maxid1 = maxid1 + 1;
//                        tempbakst.setTempStmtid(new BigDecimal(maxid1));
//                        tempbakst.setBankStatement(bankstmy);
//                        banktransbal = request.getParameter("remainBal" + k);
//                        settleAmtBnk = request.getParameter("settleAmtBnk" + k);
//                        bankremarks = request.getParameter("remarks" + k);
//                        totalamt1 = request.getParameter("totalamt" + k);
//                        tempbakst.setMappedAmount(new BigDecimal(settleAmtBnk));
//                        tempbakst.setRemarks(bankremarks);
//                        tempbakst.setTransactionBalance(new BigDecimal(banktransbal));
//                        tempbakst.setTransactionAmount(new BigDecimal(totalamt1));
//                        tempbakst.setCorporateId(new BigDecimal(corpid));
//                        tempbakst.setCheckerStatus("Pending");
//                        tempbakst.setBillCategory("Bills");
//                      
//                        tempbakstdao.NewTempMapBankStatement(tempbakst);
//                    }
//                }
//                //////////////////to preview the payment verfication mapping
////                ModelAndView mv1 = new ModelAndView("BillProcessing/viewMakerPendingPayableBillbyRLDC");
////                TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
////                List<TempBankStatement> list123 = new ArrayList<>();
////                List<TempBankStatement> list9 = new ArrayList<>();
////                List<TempMappingBillBank> list = new ArrayList<>();
////                List<BigDecimal> bankStmtIds = new ArrayList<>();
////
////                list = tmpmapdao.getTempMappingBillBankbyCorpID(Integer.parseInt(corpid), "Pending");
////                System.out.println("list size is " + list.size());
////                if (list.size() > 0) {
////
////                    for (TempMappingBillBank temp : list) {
////                        System.out.println("BankStmtID is " + temp.getBankStatement().getStmtId());
////                        bankStmtIds.add(temp.getBankStatement().getStmtId());
////                    }
////                    for (BigDecimal stmtId : bankStmtIds) {
////                        list9 = tempbakstdao.getTempBankStatementbySTMTID(stmtId);
////                        for (TempBankStatement temp : list9) {
////                            list123.add(temp);
////                        }
////
////                    }
////
////                }
////
////                CorporatesDAO cordao = new CorporatesDAO();
////                String corpname = cordao.getCorporateNamebyID(Integer.parseInt(corpid));
////                mv1.addObject("tempmaplist", list);
////                mv1.addObject("bankList", list123);
////                mv1.addObject("CorpID", Integer.parseInt(corpid));
////                mv1.addObject("CorpName", corpname);
////                mv1.addObject("successMSG", "Sucessfully Submited ....");
////                return mv1;
////////////////////////////////Return to same page for the corpiD///////////////////////////////////////////////
//                List<TempMappingBillBank> list = null;
//                int corporateid = Integer.parseInt(corpid);
//                int corpId = corporateid;
//                CorporatesDAO corpDao1 = new CorporatesDAO();
//                BankStatementDAO bankStmtDao = new BankStatementDAO();
//                BillPayableCorpDAO billPayCorp = new BillPayableCorpDAO();
//                BillPayableCorp billpaycorp = null;
//                String corpName = corpDao1.geCorpNamebyId(corpId);
//                List<Object[]> billpayObjlist = new ArrayList<Object[]>();
//                List<Object[]> list123 = new ArrayList<Object[]>();
//                List<Object[]> list456 = new ArrayList<Object[]>();
//                List<BillPayableCorp> billPayList = new ArrayList();
//                list123 = billPayCorp.getPendingPayablebyCorpgroupbyBillingDate();
//                list456 = billPayCorp.getPendingPaymentbyCorpgroupbyWeekID();
//                billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId),  "DSM");
//                // List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//                for (Object[] result : billpayObjlist) {
//                    billpaycorp = new BillPayableCorp();
//                    BigDecimal uniuqeid = (BigDecimal) result[0];
//                    BigDecimal weekid = (BigDecimal) result[8];
//                    BigDecimal totalnet = (BigDecimal) result[5];
//                    BigDecimal pending = (BigDecimal) result[6];
//                    BigDecimal revisionno = (BigDecimal) result[9];
//                    System.out.println("uniuqeid is " + uniuqeid);
//                    System.out.println("weekid is " + weekid);
//                    System.out.println("totalnet is " + totalnet);
//                    System.out.println("pending is " + pending);
//                    System.out.println("revisionno is " + revisionno);
//                    billpaycorp.setUniqueId(uniuqeid);
//                    billpaycorp.setBillType((String) result[1]);
//                    billpaycorp.setBillCategory((String) result[2]);
//                    billpaycorp.setWeekId(weekid);
//                    billpaycorp.setBillDueDate((Date) result[4]);
//                    billpaycorp.setTotalnet(totalnet);
//                    billpaycorp.setPendingAmount(pending);
//                    billpaycorp.setBillingDate((Date) result[3]);
//                    billpaycorp.setRevisionNo(revisionno);
//                    billPayList.add(billpaycorp);
//                    //  uniqueIdListBillPayCorp.add(uniuqeid);
//                }
//                billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId), "RRAS");
////            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//                for (Object[] result : billpayObjlist) {
//                    billpaycorp = new BillPayableCorp();
//                    BigDecimal uniuqeid = (BigDecimal) result[0];
//                    BigDecimal weekid = (BigDecimal) result[8];
//                    BigDecimal totalnet = (BigDecimal) result[5];
//                    BigDecimal pending = (BigDecimal) result[6];
//                    BigDecimal revisionno = (BigDecimal) result[9];
//                    System.out.println("uniuqeid is " + uniuqeid);
//                    System.out.println("weekid is " + weekid);
//                    System.out.println("totalnet is " + totalnet);
//                    System.out.println("pending is " + pending);
//                    System.out.println("revisionno is " + revisionno);
//                    billpaycorp.setUniqueId(uniuqeid);
//                    billpaycorp.setBillType((String) result[1]);
//                    billpaycorp.setBillCategory((String) result[2]);
//                    billpaycorp.setWeekId(weekid);
//                    billpaycorp.setBillDueDate((Date) result[4]);
//                    billpaycorp.setTotalnet(totalnet);
//                    billpaycorp.setPendingAmount(pending);
//                    billpaycorp.setBillingDate((Date) result[3]);
//                    billpaycorp.setRevisionNo(revisionno);
//                    billPayList.add(billpaycorp);
//                    //  uniqueIdListBillPayCorp.add(uniuqeid);
//                }
//                // System.out.println("uniqueIdListBillPayCorp size is " + uniqueIdListBillPayCorp.size());
//                billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId),  "AGC");
////            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//                for (Object[] result : billpayObjlist) {
//                    billpaycorp = new BillPayableCorp();
//                    BigDecimal uniuqeid = (BigDecimal) result[0];
//                    BigDecimal weekid = (BigDecimal) result[8];
//                    BigDecimal totalnet = (BigDecimal) result[5];
//                    BigDecimal pending = (BigDecimal) result[6];
//                    BigDecimal revisionno = (BigDecimal) result[9];
//                    System.out.println("uniuqeid is " + uniuqeid);
//                    System.out.println("weekid is " + weekid);
//                    System.out.println("totalnet is " + totalnet);
//                    System.out.println("pending is " + pending);
//                    System.out.println("revisionno is " + revisionno);
//                    billpaycorp.setUniqueId(uniuqeid);
//                    billpaycorp.setBillType((String) result[1]);
//                    billpaycorp.setBillCategory((String) result[2]);
//                    billpaycorp.setWeekId(weekid);
//                    billpaycorp.setBillDueDate((Date) result[4]);
//                    billpaycorp.setTotalnet(totalnet);
//                    billpaycorp.setPendingAmount(pending);
//                    billpaycorp.setBillingDate((Date) result[3]);
//                    billpaycorp.setRevisionNo(revisionno);
//                    billPayList.add(billpaycorp);
//                    //  uniqueIdListBillPayCorp.add(uniuqeid);
//                }
//                billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId), "FRAS");
////            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//                for (Object[] result : billpayObjlist) {
//                    billpaycorp = new BillPayableCorp();
//                    BigDecimal uniuqeid = (BigDecimal) result[0];
//                    BigDecimal weekid = (BigDecimal) result[8];
//                    BigDecimal totalnet = (BigDecimal) result[5];
//                    BigDecimal pending = (BigDecimal) result[6];
//                    BigDecimal revisionno = (BigDecimal) result[9];
//                    System.out.println("uniuqeid is " + uniuqeid);
//                    System.out.println("weekid is " + weekid);
//                    System.out.println("totalnet is " + totalnet);
//                    System.out.println("pending is " + pending);
//                    System.out.println("revisionno is " + revisionno);
//                    billpaycorp.setUniqueId(uniuqeid);
//                    billpaycorp.setBillType((String) result[1]);
//                    billpaycorp.setBillCategory((String) result[2]);
//                    billpaycorp.setWeekId(weekid);
//                    billpaycorp.setBillDueDate((Date) result[4]);
//                    billpaycorp.setTotalnet(totalnet);
//                    billpaycorp.setPendingAmount(pending);
//                    billpaycorp.setBillingDate((Date) result[3]);
//                    billpaycorp.setRevisionNo(revisionno);
//                    billPayList.add(billpaycorp);
//                    //  uniqueIdListBillPayCorp.add(uniuqeid);
//                }
//                System.out.println(" billPayList is " + billPayList.size());
//                List<BankStatement> bnkstmtList = bankStmtDao.BankStatementlist(corporateid);
//                List<BankStatement> bnkstmtOrderList = bankStmtDao.BankStatementOrderlist(corporateid);
//                if (billPayList != null && !(billPayList.isEmpty()) && bnkstmtList != null && !(bnkstmtList.isEmpty())) {
//                    System.out.println("billPayList size is " + billPayList.size());
//                    System.out.println("bnkstmtList size is " + bnkstmtList.size());
//                    ArrayList list999 = new ArrayList();
//                    Map author1 = new HashMap();
//                    Map author2 = new HashMap();
//                    Map author3 = new HashMap();
//                    Map author4 = new HashMap();
//
//                    author1.put("Priority", "DSM");
//                    list999.add(author1);
//                    author2.put("Priority", "RRAS");
//                    list999.add(author2);
//                    author3.put("Priority", "AGC");
//                    list999.add(author3);
//                    author4.put("Priority", "FRAS");
//                    list999.add(author4);
//                    ModelAndView mv1 = new ModelAndView("BillProcessing/billPayableDisplay");
//                    mv1.addObject("corpName", corpName);
//                    mv1.addObject("corpid", corpid);
//                    mv1.addObject("bnkstmtList", bnkstmtList);
//                    mv1.addObject("billPayList", billPayList);
//                    mv1.addObject("billdateList", list123);
//                    mv1.addObject("weekList", list456);
//                    mv1.addObject("priorityList", list999);
//                    List<BillPayableCorp> billPayNotinTempList = null;
//                    BillPayableCorpDAO billpaydao = new BillPayableCorpDAO();
//                    billPayNotinTempList = billpaydao.BillPayableCorpNotInTemplist(corpId);
//                    mv1.addObject("tempbillPayList", billPayNotinTempList);
//                    TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
//                    List<TempMapBankStatement> list4 = null;
//                    List<TempMappingBillBank> list5 = null;
//                    list5 = tmpmapdao.getTempMappingBillBankbyCorpID(corporateid, "Pending");
//                    String flag = "NoPending";
//                    HashMap<Integer, String> hm = new HashMap<Integer, String>();
//                    int pendcount = 0;
//                    for (TempMappingBillBank e : list5) {
//                        if (e.getPendingAmount().compareTo(BigDecimal.ZERO) == 0) {
//                            hm.put(e.getBillPayableCorp().getUniqueId().intValue(), flag);
//                        }
//                    }
//                    if (list5.size() > 0) {
//                        list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid,"Bills");
//                    }
//                    List<BankStatement> list6 = null;
//                    TempMappingBillBankDAO tempmapbilldao = new TempMappingBillBankDAO();
//                    list6 = tempbakstdao.getBankStatementNotINTempMapBankStatement("Pending", corpId);
//                    List<TempMappingBillBank> list7 = null;
//                    list7 = tempmapbilldao.getMaxTempMappingBillBankbyCorpID(corpId, "Pending");
//                    List<TempMapBankStatement> list8 = null;
//                    list8 = tempbakstdao.getTempMapBankStatementbyStatus("Pending", corpId,"Bills");
//                    mv1.addObject("tempmaplist", list5);
//                    mv1.addObject("tempmapMaxlist", list7);
//                    mv1.addObject("tempBankStmtlist", list6);
//                    mv1.addObject("tempbankstmtList", list8);
//                    mv1.addObject("bankList", list4);
//                    mv1.addObject("CorpID", corpid);
//                    mv1.addObject("hm", hm);
//                    TempInterestDetailsDAO tempintedao=new TempInterestDetailsDAO();               
//
//                    List<TempInterestDetails> listtempinterest=new ArrayList<>();
//
//                    listtempinterest=tempintedao.getTempInterestDetailsbyCorpid("Pending",corpId);
//
//                     System.out.println("TempInterest size is : "+listtempinterest.size());
//
//                    if(listtempinterest !=null && listtempinterest.size()>0)
//
//                    {
//
//                         mv1.addObject("listtempinterest", listtempinterest);
//
//                    }
//
//                    else
//
//                    {
//
//                        System.out.println("TempInterest Not Record not found");
//
//                        InterestDetailsDAO intedao=new InterestDetailsDAO();
//
//                        List<InterestDetails> listInteres=new ArrayList<>();
//
//                        listInteres=intedao.getInterestPayableDetailsbyCorp(corpId);
//
//                        mv1.addObject("listInterest", listInteres);
//
// 
//
//                    }
//                    return mv1;
//                }
//            }
//        }// end of adding into temptables/Confirm button
//        String bdelete = request.getParameter("bdelete");
//        if (bdelete != null) {
//            String CorpID = request.getParameter("CorpID");
//            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
//            tempbillmapdao.getDeletebyMakerforCorpID(Integer.parseInt(CorpID), "Pending");
//            tempbakstdao.getDeletedTempMapBankStatementbyCorp(Integer.parseInt(CorpID), "Pending");
//            ModelAndView mv1 = new ModelAndView("successMsg");
//            String Msg = "Successfully Deleted for the records!!";
//            mv1.addObject("Msg", Msg);
//            return mv1;
//        }
//        String bName = request.getParameter("bName");
//        
//        if (bName != null) {
//            System.out.println("on Click of bName button  from billVerification.jsp");
//            List<TempMappingBillBank> list = null;
//            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
//            System.out.println("bName NOT NULL & is " + bName);
//            String corpName = request.getParameter("corpId");
//            int corporateid = corpDao.getCorpIdbyName(corpName);
//            int corpId = corporateid;
//            System.out.println("corpName is " + corpName);
//            BankStatementDAO bankStmtDao = new BankStatementDAO();
//            BillPayableCorpDAO billPayCorp = new BillPayableCorpDAO();
//            BillPayableCorp billpaycorp = null;
//            List<Object[]> billpayObjlist = new ArrayList<Object[]>();
//            List<Object[]> list123 = new ArrayList<Object[]>();
//            List<Object[]> list456 = new ArrayList<Object[]>();
//            List<BillPayableCorp> billPayList = new ArrayList();
//            list123 = billPayCorp.getPendingPayablebyCorpgroupbyBillingDate();
//            list456 = billPayCorp.getPendingPaymentbyCorpgroupbyWeekID();
//            billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId), "DSM");
//            System.out.println("$$$$$$$$$$$$$$$$$$$ %%%%%%%%%%%billpayObjlist size is " + billpayObjlist.size());
////            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//            for (Object[] result : billpayObjlist) {
//                billpaycorp = new BillPayableCorp();
//                BigDecimal uniuqeid = (BigDecimal) result[0];
//                BigDecimal weekid = (BigDecimal) result[8];
//                BigDecimal totalnet = (BigDecimal) result[5];
//                BigDecimal pending = (BigDecimal) result[6];
//                BigDecimal revisionno = (BigDecimal) result[9];
//                System.out.println("uniuqeid is " + uniuqeid);
//                System.out.println("weekid is " + weekid);
//                System.out.println("totalnet is " + totalnet);
//                System.out.println("pending is " + pending);
//                System.out.println("revisionno is " + revisionno);
//                billpaycorp.setUniqueId(uniuqeid);
//                billpaycorp.setBillType((String) result[1]);
//                billpaycorp.setBillCategory((String) result[2]);
//                billpaycorp.setWeekId(weekid);
//                billpaycorp.setBillDueDate((Date) result[4]);
//                billpaycorp.setTotalnet(totalnet);
//                billpaycorp.setPendingAmount(pending);
//                billpaycorp.setBillingDate((Date) result[3]);
//                billpaycorp.setRevisionNo(revisionno);
//                billPayList.add(billpaycorp);
////                uniqueIdListBillPayCorp.add(uniuqeid);
//            }
////            System.out.println("uniqueIdListBillPayCorp size is " + uniqueIdListBillPayCorp.size());
//            billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId),  "RRAS");
////            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//            for (Object[] result : billpayObjlist) {
//                billpaycorp = new BillPayableCorp();
//                BigDecimal uniuqeid = (BigDecimal) result[0];
//                BigDecimal weekid = (BigDecimal) result[8];
//                BigDecimal totalnet = (BigDecimal) result[5];
//                BigDecimal pending = (BigDecimal) result[6];
//                BigDecimal revisionno = (BigDecimal) result[9];
//                System.out.println("uniuqeid is " + uniuqeid);
//                System.out.println("weekid is " + weekid);
//                System.out.println("totalnet is " + totalnet);
//                System.out.println("pending is " + pending);
//                System.out.println("revisionno is " + revisionno);
//                billpaycorp.setUniqueId(uniuqeid);
//                billpaycorp.setBillType((String) result[1]);
//                billpaycorp.setBillCategory((String) result[2]);
//                billpaycorp.setWeekId(weekid);
//                billpaycorp.setBillDueDate((Date) result[4]);
//                billpaycorp.setTotalnet(totalnet);
//                billpaycorp.setPendingAmount(pending);
//                billpaycorp.setBillingDate((Date) result[3]);
//                billpaycorp.setRevisionNo(revisionno);
//                billPayList.add(billpaycorp);
//                //  uniqueIdListBillPayCorp.add(uniuqeid);
//            }
//            billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId), "AGC");
////            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//            for (Object[] result : billpayObjlist) {
//                billpaycorp = new BillPayableCorp();
//                BigDecimal uniuqeid = (BigDecimal) result[0];
//                BigDecimal weekid = (BigDecimal) result[8];
//                BigDecimal totalnet = (BigDecimal) result[5];
//                BigDecimal pending = (BigDecimal) result[6];
//                BigDecimal revisionno = (BigDecimal) result[9];
//                System.out.println("uniuqeid is " + uniuqeid);
//                System.out.println("weekid is " + weekid);
//                System.out.println("totalnet is " + totalnet);
//                System.out.println("pending is " + pending);
//                System.out.println("revisionno is " + revisionno);
//                billpaycorp.setUniqueId(uniuqeid);
//                billpaycorp.setBillType((String) result[1]);
//                billpaycorp.setBillCategory((String) result[2]);
//                billpaycorp.setWeekId(weekid);
//                billpaycorp.setBillDueDate((Date) result[4]);
//                billpaycorp.setTotalnet(totalnet);
//                billpaycorp.setPendingAmount(pending);
//                billpaycorp.setBillingDate((Date) result[3]);
//                billpaycorp.setRevisionNo(revisionno);
//                billPayList.add(billpaycorp);
//                //  uniqueIdListBillPayCorp.add(uniuqeid);
//            }
//            billpayObjlist = billPayCorp.getPendingPayableCorpList(new BigDecimal(corpId), "FRAS");
////            List<BigDecimal> uniqueIdListBillPayCorp = new ArrayList<>();
//            for (Object[] result : billpayObjlist) {
//                billpaycorp = new BillPayableCorp();
//                
//                BigDecimal uniuqeid = (BigDecimal) result[0];
//                BigDecimal weekid = (BigDecimal) result[8];
//                BigDecimal totalnet = (BigDecimal) result[5];
//                BigDecimal pending = (BigDecimal) result[6];
//                BigDecimal revisionno = (BigDecimal) result[9];
//                System.out.println("uniuqeid is " + uniuqeid);
//                System.out.println("weekid is " + weekid);
//                System.out.println("totalnet is " + totalnet);
//                System.out.println("pending is " + pending);
//                System.out.println("revisionno is " + revisionno);
//                billpaycorp.setUniqueId(uniuqeid);
//                billpaycorp.setBillType((String) result[1]);
//                billpaycorp.setBillCategory((String) result[2]);
//                billpaycorp.setWeekId(weekid);
//                billpaycorp.setBillDueDate((Date) result[4]);
//                billpaycorp.setTotalnet(totalnet);
//                billpaycorp.setPendingAmount(pending);
//                billpaycorp.setBillingDate((Date) result[3]);
//                billpaycorp.setRevisionNo(revisionno);
//                billPayList.add(billpaycorp);
//                //  uniqueIdListBillPayCorp.add(uniuqeid);
//            }
//            System.out.println(" billPayList is " + billPayList.size());
//            List<BankStatement> bnkstmtList = bankStmtDao.BankStatementlist(corporateid);
//            List<BankStatement> bnkstmtOrderList = bankStmtDao.BankStatementOrderlist(corporateid);
//            if (billPayList != null && !(billPayList.isEmpty()) && bnkstmtList != null && !(bnkstmtList.isEmpty())) {
//                System.out.println("billPayList size is " + billPayList.size());
//                System.out.println("bnkstmtList size is " + bnkstmtList.size());
//                ArrayList list999 = new ArrayList();
//                Map author1 = new HashMap();
//                Map author2 = new HashMap();
//                Map author3 = new HashMap();
//                Map author4 = new HashMap();
//
//                author1.put("Priority", "DSM");
//                list999.add(author1);
//                author2.put("Priority", "RRAS");
//                list999.add(author2);
//                author3.put("Priority", "AGC");
//                list999.add(author3);
//                author4.put("Priority", "FRAS");
//                list999.add(author4);
//                ModelAndView mv1 = new ModelAndView("BillProcessing/billPayableDisplay");
//                mv1.addObject("corpName", corpName);
//                mv1.addObject("corpid", corporateid);
//                mv1.addObject("bnkstmtList", bnkstmtList);
//                mv1.addObject("billPayList", billPayList);
//                mv1.addObject("billdateList", list123);
//                mv1.addObject("weekList", list456);
//                mv1.addObject("priorityList", list999);
//                List<BillPayableCorp> billPayNotinTempList = null;
//                BillPayableCorpDAO billpaydao = new BillPayableCorpDAO();
//                billPayNotinTempList = billpaydao.BillPayableCorpNotInTemplist(corpId);
//                mv1.addObject("tempbillPayList", billPayNotinTempList);
//                TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
//                System.out.println("CorpName is " + corpName);
//                List<TempMapBankStatement> list4 = null;
//                List<TempMappingBillBank> list5 = null;
//                list5 = tmpmapdao.getTempMappingBillBankbyCorpID(corporateid, "Pending");
//                String flag = "NoPending";
//                HashMap<Integer, String> hm = new HashMap<Integer, String>();
//                int pendcount = 0;
//                for (TempMappingBillBank e : list5) {
//                    if (e.getPendingAmount().compareTo(BigDecimal.ZERO) == 0) {
//                        hm.put(e.getBillPayableCorp().getUniqueId().intValue(), flag);
//                    }
//                }
//                if (list5.size() > 0) {
//                    list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid,"Bills");
//                }
//                List<BankStatement> list6 = null;
//                TempMappingBillBankDAO tempmapbilldao = new TempMappingBillBankDAO();
//                list6 = tempbakstdao.getBankStatementNotINTempMapBankStatement("Pending", corpId);
//                List<TempMappingBillBank> list7 = null;
//                list7 = tempmapbilldao.getMaxTempMappingBillBankbyCorpID(corpId, "Pending");
//                List<TempMapBankStatement> list8 = null;
//                list8 = tempbakstdao.getTempMapBankStatementbyStatus("Pending", corpId,"Bills");
//                /* Getting Refund Records from billReceivebale corp*/
//                BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
//                List<BillReceiveCorp> listrefund = null;
//                listrefund = billrecvdao.getRefundPendingBillReceiveAmountbyCorp(corpId);
//                mv1.addObject("tempmaplist", list5);
//                mv1.addObject("tempmapMaxlist", list7);
//                mv1.addObject("tempBankStmtlist", list6);
//                mv1.addObject("tempbankstmtList", list8);
//                mv1.addObject("bankList", list4);
//                mv1.addObject("CorpID", corporateid);
//                mv1.addObject("hm", hm);
//                mv1.addObject("refundList", listrefund);
//                TempInterestDetailsDAO tempintedao=new TempInterestDetailsDAO();               
//
//                    List<TempInterestDetails> listtempinterest=null;
//
//                    listtempinterest=tempintedao.getTempInterestDetailsbyCorpid("Pending",corpId);
//
//                     System.out.println("TempInterest size is : "+listtempinterest.size());
//
//                    if(listtempinterest !=null && listtempinterest.size()>0)
//
//                    {
//
//                         mv1.addObject("listtempinterest", listtempinterest);
//
//                    }
//
//                    else
//
//                    {
//
//                        System.out.println("TempInterest Not Record not found");
//
//                        InterestDetailsDAO intedao=new InterestDetailsDAO();
//
//                        List<InterestDetails> listInteres=null;
//
//                        listInteres=intedao.getInterestPayableDetailsbyCorp(corpId);
//
//                        mv1.addObject("listInterest", listInteres);
//
// 
//
//                    }
//                return mv1;
//            } else {
//                System.out.println("Record not found");
//                ModelAndView mv1 = new ModelAndView("successMsg");
//                String Msg = "Please check-- there is no Verified Bank Transaction for Mapping for " + corpName + "!";
//                mv1.addObject("Msg", Msg);
//                return mv1;
//            }
//        }
//        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
//        List<BigDecimal> list = new ArrayList<>();
//        list = tmpmapdao.getTempMappingBillBankbyPendingCorpList("Pending");
//        System.out.println("list is " + list);
//        List<String> pendingVerifyCorpList = new ArrayList<>();
//        for (BigDecimal result : list) {
//            System.out.println("corporate is " + result);
//            Integer corpId = result.intValueExact();
//            String corpname = corpDao.getCorpNamebyId(corpId);
//            System.out.println("corp is " + corpname);
//            pendingVerifyCorpList.add(corpname);
//        }
//        mv.addObject("pendingVerifyCorpList", pendingVerifyCorpList);
//        List<BillPayableCorp> listBillPayableCorp = new ArrayList<>();
//        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
//        List<String> listCorp = new ArrayList<>();
//        listBillPayableCorp = billpaycorpdao.getPendingBillCorpNameList();
//        for (BillPayableCorp temp : listBillPayableCorp) {
//            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
//            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
//            if (ans); else {
//                listCorp.add(temp.getCorporates().getCorporateName());
//            }
//        }
//        mv.addObject("PendningList", list);
//        mv.addObject("corpList", listCorp);
//        return mv;
//    }
    public ModelAndView billVerification(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/billVerification");
        CorporatesDAO corpDao = new CorporatesDAO();
        TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
        String Submit = request.getParameter("confirm");
        if (Submit != null) {
            System.out.println("on Click of bName button  from billPayableDisplay.jsp");
            TempMappingBillBank tempmapbank = new TempMappingBillBank();
            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
            TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
            TempRefundBillCorp temprefund = new TempRefundBillCorp();
            int maxid = 0;
            String weekid1;
            String category1;
            String totalAmt1;
            String settleAmt1;
            String pendingAmt1;
            String billtype1;
            String uniqueid1;
            String status;
            String banktransbal;
            String bankremarks = null;
            String settleAmtBnk = null;
            String remainBal1 = null;
            BigDecimal bankpendamnt = new BigDecimal(0);

            String transactiontype;
            BillPayableCorp billcorp = new BillPayableCorp();
            Corporates corp = new Corporates();
            UserDetails usr = new UserDetails();
            usr.setLoginId("sysadmin");
            BankStatement bankstmy = new BankStatement();
            String corpid = request.getParameter("corpid");
            String rowcount = request.getParameter("rowcount");
            String bankrowcount1 = request.getParameter("bankrowcount");
            String uniqueId[] = request.getParameterValues("uniqueNo");
            System.out.println("bankrowcount1 " + bankrowcount1);
            System.out.println("uniqueId[0] " + uniqueId[0]);
            System.out.println("uniqueId.length is " + uniqueId.length);
            System.out.println("rowcount " + rowcount);
            BigDecimal totref = new BigDecimal(0);
            corp.setCorporateId(Integer.parseInt(corpid));
            BillReceiveCorp binnrecvcorp = new BillReceiveCorp();
            if (Integer.parseInt(rowcount) >= 0) {
                String bankrowcount = request.getParameter("bankrowcount");
                int bankRowCounter = Integer.parseInt(bankrowcount);
                for (int k = 1; k <= bankRowCounter; k++) {
                    String bankstmt1 = request.getParameter("bankstmt" + k);
                    String bankstmt[] = request.getParameterValues("bankstmt");
                    System.out.println("bankstmt[0] is " + bankstmt[0]);
                    System.out.println("bankstmt1 is " + bankstmt1);
                    if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
                        System.out.print("bank stmt id insert into temp mapped is " + bankstmt1);
                        bankremarks = request.getParameter("remarks" + k);
                        settleAmtBnk = request.getParameter("settleAmtBnk" + k);
                        remainBal1 = request.getParameter("remainBal" + k);
                        bankpendamnt = new BigDecimal(settleAmtBnk).add(new BigDecimal(remainBal1));
                        System.out.print("bankremarks, settleAmtBnk,remainBal1,bankpendamnt" + bankremarks + "," + settleAmtBnk + "," + remainBal1 + "," + bankpendamnt);
                    }
                }

                for (int j = 0; j < uniqueId.length; j++) {

                    for (int i = 1; i <= Integer.parseInt(rowcount); i++) {
                        uniqueid1 = request.getParameter("uniqueid" + i);
                        transactiontype = request.getParameter("transactiontype" + i);
                        System.out.println("transactiontype " + transactiontype);
                        System.out.println("uniqueid1 count " + uniqueid1 + " " + j);
                        if (transactiontype.equals(uniqueId[j])) {
                            weekid1 = request.getParameter("weekid" + i);
                            category1 = request.getParameter("category" + i);
                            totalAmt1 = request.getParameter("totalAmt" + i);
                            settleAmt1 = request.getParameter("settleAmt" + i);
                            System.out.println("weekid :" + weekid1 + i);
                            System.out.println("category " + category1);
                            System.out.println("totalAmt " + totalAmt1);
                            System.out.println("settleAmt " + settleAmt1);
                            pendingAmt1 = request.getParameter("pendingAmt" + i);
                            billtype1 = request.getParameter("billtype" + i);
                            int maxrefid = 0;
                            totref = new BigDecimal(totalAmt1);
                            System.out.println("totref amount " + totref);
                            if (transactiontype.substring(0, 1).equals("R")) {
                                if (new BigDecimal(settleAmt1).compareTo(BigDecimal.ZERO) != 0) {
                                    System.out.println("Inside temp Refundtotref amount ");
                                    maxrefid = temprefunddao.getMaxUniqueID();
                                    maxrefid = maxrefid + 1;
                                    temprefund.setSlno(new BigDecimal(maxrefid));
                                    temprefund.setCheckerStatus("Pending");
                                    temprefund.setPaidAmount(new BigDecimal(settleAmt1));
                                    temprefund.setPendingAmount(new BigDecimal(pendingAmt1));
                                    binnrecvcorp.setUniqueId(new BigDecimal(uniqueid1));
                                    temprefund.setTotalAmount(totref);
                                    temprefund.setBillReceiveCorp(binnrecvcorp);
                                    temprefund.setRefundDate(new Date());
                                    temprefund.setCorporates(corp);
                                    temprefund.setWeekid(new BigDecimal(weekid1));
                                    temprefunddao.NewTempRefundBillCorp(temprefund);

                                    System.out.println("bankrowcount is " + bankrowcount);

                                    for (int k = 1; k <= bankRowCounter; k++) {
                                        String bankstmt1 = request.getParameter("bankstmt" + k);
                                        String bankstmt[] = request.getParameterValues("bankstmt");
                                        System.out.println("bankstmt[0] is " + bankstmt[0]);
                                        System.out.println("bankstmt1 is " + bankstmt1);
                                        if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
                                            System.out.print("bank stmt id insert into temp mapped is " + bankstmt1);
                                            bankstmy.setStmtId(new BigDecimal(bankstmt1));
                                        }
                                    }
                                    MappingRefundBankDAO mapprefbankdao = new MappingRefundBankDAO();
                                    MappingRefundBank mapprefban = new MappingRefundBank();
                                    int maxrefslno = mapprefbankdao.getMaxUniqueID();
                                    maxrefslno = maxrefslno + 1;
                                    mapprefban.setSlno(new BigDecimal(maxrefslno));
                                    mapprefban.setMappedAmount(new BigDecimal(settleAmt1));
                                    bankpendamnt = bankpendamnt.subtract(new BigDecimal(settleAmt1));
                                    mapprefban.setBankPendingAmount(bankpendamnt);
                                    mapprefban.setPendingAmount(new BigDecimal(pendingAmt1));
                                    mapprefban.setEntryDate(new Date());
                                    mapprefban.setTempRefundBillCorp(temprefund);
                                    mapprefban.setCheckerStatus("Pending");
                                    mapprefban.setBankStatement(bankstmy);
                                    mapprefban.setRemarks(bankremarks);
                                    mapprefbankdao.NewMappingRefundBank(mapprefban);
                                }
                            } else {
                                if (new BigDecimal(settleAmt1).compareTo(BigDecimal.ZERO) != 0) {
                                    maxid = tempbillmapdao.getMaxUniqueID();
                                    maxid = maxid + 1;
                                    tempmapbank.setUniqueId(new BigDecimal(maxid));
                                    tempmapbank.setBillType(billtype1);
                                    tempmapbank.setCheckerStatus("Pending");
                                    tempmapbank.setEntryDate(new Date());
                                    tempmapbank.setMappedAmount(new BigDecimal(settleAmt1));
                                    tempmapbank.setOriginalAmount(new BigDecimal(totalAmt1));
                                    tempmapbank.setPaymentCategory(category1);
                                    tempmapbank.setPendingAmount(new BigDecimal(pendingAmt1));
                                    if (new BigDecimal(pendingAmt1).compareTo(BigDecimal.ZERO) == 0) {
                                        status = "PAID";
                                    } else {
                                        status = "PARTIALLY";
                                    }
                                    tempmapbank.setStatus(status);
                                    tempmapbank.setWeekId(new BigDecimal(weekid1));
                                    billcorp.setUniqueId(new BigDecimal(uniqueid1));
                                    tempmapbank.setBillPayableCorp(billcorp);
                                    corp.setCorporateId(Integer.parseInt(corpid));
                                    tempmapbank.setCorporates(corp);
                                    tempmapbank.setUserDetails(usr);
//                                    String bankrowcount = request.getParameter("bankrowcount");
                                    System.out.println("bankrowcount is " + bankrowcount);
//                                    int bankRowCounter = Integer.parseInt(bankrowcount);
                                    for (int k = 1; k <= bankRowCounter; k++) {
                                        String bankstmt1 = request.getParameter("bankstmt" + k);
                                        String bankstmt[] = request.getParameterValues("bankstmt");
                                        System.out.println("bankstmt[0] is " + bankstmt[0]);
                                        System.out.println("bankstmt1 is " + bankstmt1);
                                        if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
                                            System.out.print("bank stmt id insert into temp mapped is " + bankstmt1);
                                            bankstmy.setStmtId(new BigDecimal(bankstmt1));

                                        }
                                    }
                                    tempmapbank.setBankStatement(bankstmy);
                                    bankpendamnt = bankpendamnt.subtract(new BigDecimal(settleAmt1));
                                    tempmapbank.setBankPendingAmount(bankpendamnt);
                                    tempmapbank.setRemarks(bankremarks);
                                    tempbillmapdao.NewTempMappingBillBank(tempmapbank);
                                }//end of sttle 0
                            }// end of checking BillRecevei for Refund
                        }
                    }
                }
//                String bankrowcount = request.getParameter("bankrowcount");
                int maxid1 = 0;

                String totalamt1;
                TempMapBankStatement tempbakst = new TempMapBankStatement();
                for (int k = 1; k <= Integer.parseInt(bankrowcount); k++) {
                    String bankstmt1 = request.getParameter("bankstmt" + k);
                    String bankstmt[] = request.getParameterValues("bankstmt");
                    if (Integer.parseInt(bankstmt[0]) == Integer.parseInt(bankstmt1)) {
                        bankstmy.setStmtId(new BigDecimal(bankstmt1));
                        maxid1 = tempbakstdao.getMaxUniqueID();
                        maxid1 = maxid1 + 1;
                        tempbakst.setTempStmtid(new BigDecimal(maxid1));
                        tempbakst.setBankStatement(bankstmy);
                        banktransbal = request.getParameter("remainBal" + k);
                        settleAmtBnk = request.getParameter("settleAmtBnk" + k);
                        bankremarks = request.getParameter("remarks" + k);
                        totalamt1 = request.getParameter("totalamt" + k);
                        tempbakst.setMappedAmount(new BigDecimal(settleAmtBnk));
                        tempbakst.setRemarks(bankremarks);
                        tempbakst.setTransactionBalance(new BigDecimal(banktransbal));
                        tempbakst.setTransactionAmount(new BigDecimal(totalamt1));
                        tempbakst.setCorporateId(new BigDecimal(corpid));
                        tempbakst.setCheckerStatus("Pending");
                        tempbakst.setBillCategory("Bills");
                        tempbakstdao.NewTempMapBankStatement(tempbakst);
                    }
                }

//////////////////////////////Return to same page for the corpiD///////////////////////////////////////////////
                List<TempMappingBillBank> list = null;
                int corporateid = Integer.parseInt(corpid);
                int corpId = corporateid;
                CorporatesDAO corpDao1 = new CorporatesDAO();
                BankStatementDAO bankStmtDao = new BankStatementDAO();
                BillPayableCorpDAO billPayCorp = new BillPayableCorpDAO();
                BillPayableCorp billpaycorp = null;
                String corpName = corpDao1.geCorpNamebyId(corpId);
                List<Object[]> billpayObjlist = new ArrayList<Object[]>();
                List<Date> list123 = new ArrayList<Date>();
                List<BigDecimal> list456 = new ArrayList<BigDecimal>();
                List<BillPayableCorp> billPayList = new ArrayList();
                list123 = billPayCorp.getPendingPayablebyCorpgroupbyBillingDate(corpId);
                list456 = billPayCorp.getPendingPaymentbyCorpgroupbyWeekID(corpId);
                /*Getting details from Bill Receive Corp*/
                BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
                List<BigDecimal> list789 = new ArrayList<BigDecimal>();
                List<Date> list786 = new ArrayList<Date>();
                list786 = billrecvdao.getPendingReceivablebyCorpgroupbyBillingDate(corpId);
                list789 = billrecvdao.getPendingReceivebyCorpgroupbyWeekID(corpId);
                if (list786 != null && !(list786.isEmpty())) {
                    for (int n = 0; n < list786.size(); n++) {
                        int flag = 0;
                        System.out.println("Refund Dates  " + list786.get(n));
                        for (int y = 0; y < list123.size(); y++) {
                            if (list786.get(n).compareTo(list123.get(y)) == 0) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            list123.add(list786.get(n));
                        }
                    }
                }
                if (list789 != null && !(list789.isEmpty())) {
                    for (int n = 0; n < list789.size(); n++) {
                        int flag = 0;
                        for (int y = 0; y < list456.size(); y++) {
                            if (list789.get(n).compareTo(list456.get(y)) == 0) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            list456.add(list789.get(n));
                            System.out.println("Week ID in list is " + list456.get(n));
                        }
                    }
                }
                Collections.sort(list456);
                Collections.sort(list123);
                billpayObjlist = billPayCorp.getAllPendingPayableCorpList(new BigDecimal(corpId));
                for (Object[] result : billpayObjlist) {
                    billpaycorp = new BillPayableCorp();
                    BigDecimal uniuqeid = (BigDecimal) result[0];
                    BigDecimal weekid = (BigDecimal) result[8];
                    BigDecimal totalnet = (BigDecimal) result[5];
                    BigDecimal pending = (BigDecimal) result[6];
                    BigDecimal revisionno = (BigDecimal) result[9];
                    System.out.println("uniuqeid is " + uniuqeid);
                    System.out.println("weekid is " + weekid);
                    System.out.println("totalnet is " + totalnet);
                    System.out.println("pending is " + pending);
                    System.out.println("revisionno is " + revisionno);
                    billpaycorp.setUniqueId(uniuqeid);
                    billpaycorp.setBillType((String) result[1]);
                    billpaycorp.setBillCategory((String) result[2]);
                    billpaycorp.setWeekId(weekid);
                    billpaycorp.setBillDueDate((Date) result[4]);
                    billpaycorp.setTotalnet(totalnet);
                    billpaycorp.setPendingAmount(pending);
                    billpaycorp.setBillingDate((Date) result[3]);
                    billpaycorp.setRevisionNo(revisionno);
                    billPayList.add(billpaycorp);
                }
                System.out.println(" billPayList is " + billPayList.size());
                List<BankStatement> bnkstmtList = bankStmtDao.BankStatementlist(corporateid);
                List<BankStatement> bnkstmtOrderList = bankStmtDao.BankStatementOrderlist(corporateid);
                if (billPayList != null && !(billPayList.isEmpty()) && bnkstmtList != null && !(bnkstmtList.isEmpty())) {
                    System.out.println("billPayList size is " + billPayList.size());
                    System.out.println("bnkstmtList size is " + bnkstmtList.size());
                    ArrayList list999 = new ArrayList();
                    Map author1 = new HashMap();
                    Map author2 = new HashMap();
                    Map author3 = new HashMap();
                    Map author4 = new HashMap();
                    Map author5 = new HashMap();
                    Map author6 = new HashMap();
                    Map author7 = new HashMap();
                    Map author8 = new HashMap();
                    author1.put("Priority", "DSM");
                    list999.add(author1);
                    author2.put("Priority", "RRAS");
                    list999.add(author2);
                    author3.put("Priority", "AGC");
                    list999.add(author3);
                    author4.put("Priority", "FRAS");
                    list999.add(author4);
                    author5.put("Priority", "SRAS");
                    list999.add(author5);
                    author6.put("Priority", "TRASM");
                    list999.add(author6);
                    author7.put("Priority", "TRASS");
                    list999.add(author7);
                    author8.put("Priority", "TRASE");
                    list999.add(author8);
                    ModelAndView mv1 = new ModelAndView("BillProcessing/billPayableDisplay");
                    mv1.addObject("corpName", corpName);
                    mv1.addObject("corpid", corpid);
                    mv1.addObject("bnkstmtList", bnkstmtList);
                    mv1.addObject("billPayList", billPayList);
                    mv1.addObject("billdateList", list123);
                    mv1.addObject("weekList", list456);
                    mv1.addObject("priorityList", list999);
                    List<BillPayableCorp> billPayNotinTempList = null;
                    BillPayableCorpDAO billpaydao = new BillPayableCorpDAO();
                    billPayNotinTempList = billpaydao.BillPayableCorpNotInTemplist(corpId);
                    mv1.addObject("tempbillPayList", billPayNotinTempList);
                    TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
                    List<TempMapBankStatement> list4 = null;
                    List<TempMappingBillBank> list5 = null;
                    list5 = tmpmapdao.getTempMappingBillBankbyCorpID(corporateid, "Pending");
                    String flag = "NoPending";
                    //HashMap<Integer, String> hm = new HashMap<Integer, String>();
                    int pendcount = 0;

//                    for (TempMappingBillBank e : list5) {
//                        if (e.getPendingAmount().compareTo(BigDecimal.ZERO) == 0) {
//                            hm.put(e.getBillPayableCorp().getUniqueId().intValue(), flag);
//                        }
//                    }
//                    if (list5.size() > 0) {
//                        list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid,"Bills");
//                    }
                    list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid, "Bills");
                    List<BankStatement> list6 = null;
                    TempMappingBillBankDAO tempmapbilldao = new TempMappingBillBankDAO();
                    list6 = tempbakstdao.getBankStatementNotINTempMapBankStatement("Pending", corpId);
                    List<TempMappingBillBank> list7 = null;
                    list7 = tempmapbilldao.getMaxTempMappingBillBankbyCorpID(corpId, "Pending");
                    List<TempMapBankStatement> list8 = null;
                    list8 = tempbakstdao.getTempMapBankStatementbyStatus("Pending", corpId, "Bills");
                    List<TempRefundBillCorp> list9 = null;
                    list9 = temprefunddao.getAllRefundPayablePendingTempRefundBillCorpByChecker(corpId);
                    List<BillReceiveCorp> listrefund = null;
                    List<BillReceiveCorp> listNotINtemprefund = null;
                    listNotINtemprefund = billrecvdao.getRefundPendingBillReceiveAmountNotINTempRefundbyCorp(corpId);
                    listrefund = billrecvdao.getRefundPendingBillReceiveAmountbyCorp(corpId);
                    mv1.addObject("refundList", listrefund);
                    mv1.addObject("tempmaplist", list5);
                    mv1.addObject("tempmapMaxlist", list7);
                    mv1.addObject("tempBankStmtlist", list6);
                    mv1.addObject("tempbankstmtList", list8);
                    mv1.addObject("temprefundList", list9);
                    mv1.addObject("listNotINtemprefund", listNotINtemprefund);
                    mv1.addObject("bankList", list4);
                    mv1.addObject("CorpID", corpid);
                    //mv1.addObject("hm", hm);
                    TempInterestDetailsDAO tempintedao = new TempInterestDetailsDAO();
                    List<TempInterestDetails> listtempinterest = null;
                    listtempinterest = tempintedao.getTempInterestDetailsbyCorpid("Pending", corpId);
                    System.out.println("TempInterest size is : " + listtempinterest.size());
                    if (listtempinterest != null && listtempinterest.size() > 0) {
                        mv1.addObject("listtempinterest", listtempinterest);
                    } else {
                        System.out.println("TempInterest Not Record not found");
                        InterestDetailsDAO intedao = new InterestDetailsDAO();
                        List<InterestDetails> listInteres = null;
                        listInteres = intedao.getInterestPayableDetailsbyCorp(corpId);
                        mv1.addObject("listInterest", listInteres);
                    }
                    return mv1;
                }
            }
        }// end of adding into temptables/Confirm button
        String bdelete = request.getParameter("bdelete");
        if (bdelete != null) {
            String CorpID = request.getParameter("CorpID");
            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
            tempbillmapdao.getDeletebyMakerforCorpID(Integer.parseInt(CorpID), "Pending");
            tempbakstdao.getDeletedTempMapBankStatementbyCorp(Integer.parseInt(CorpID), "Pending");
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Successfully Deleted for the records!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }
        String bName = request.getParameter("bName");
        if (bName != null) {
            System.out.println("on Click of bName button  from billVerification.jsp");
            List<TempMappingBillBank> list = null;
            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
            System.out.println("bName NOT NULL & is " + bName);
            String corpName = request.getParameter("corpId");
            int corporateid = corpDao.getCorpIdbyName(corpName);
            MappingInterestBankDAO mapIntDao = new MappingInterestBankDAO();
            List<MappingInterestBank> pendingCheckerInterest = mapIntDao.getCheckerPendingInterestCountForCorporate(corporateid);
            System.out.println("$$$$$$$$$$ Outside if pendingCheckerInterest is " + pendingCheckerInterest.size());

            AdjPaymentDAO adjpaydao = new AdjPaymentDAO();
            List<AdjPayment> adjpatlist = adjpaydao.getPendingAdjPaymentListbyCorp(corporateid);
            if (adjpatlist != null && adjpatlist.size() > 0) {
                ModelAndView mv1 = new ModelAndView("successMsg");
                String Msg = "Kindly ask Checker to verify Adjustment payments pending at adjustment checker!!";
                mv1.addObject("Msg", Msg);
                return mv1;
            }
            if (pendingCheckerInterest != null && pendingCheckerInterest.size() > 0) {
                ModelAndView mv1 = new ModelAndView("successMsg");
                System.out.println(" Inside if pendingCheckerInterest is " + pendingCheckerInterest.size());
                String Msg = "Kindly ask Checker to verify Interest Mapping at Bill Payable Side!!";
                mv1.addObject("Msg", Msg);
                return mv1;
            }
            List<MiscDisbursement> listmiscdis = null;
            miscDisbursementDAO miscdao = new miscDisbursementDAO();
            listmiscdis = miscdao.getmiscDetails("Bills");
            System.out.print("Inside misc button" + listmiscdis.size());
            if (listmiscdis != null && listmiscdis.size() > 0) {
                ModelAndView mv2 = new ModelAndView("successMsg");
                mv2.addObject("Msg", "Misc Disbursement Record pending with checker...Please clear it");
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

            int corpId = corporateid;
            System.out.println("corpName is " + corpName);
            BankStatementDAO bankStmtDao = new BankStatementDAO();
            BillPayableCorpDAO billPayCorp = new BillPayableCorpDAO();
            BillPayableCorp billpaycorp = null;
            BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
            List<Object[]> billpayObjlist = new ArrayList<Object[]>();
            List<Date> list123 = new ArrayList<Date>();
            List<BigDecimal> list456 = new ArrayList<BigDecimal>();
            List<BillPayableCorp> billPayList = new ArrayList();
            list123 = billPayCorp.getPendingPayablebyCorpgroupbyBillingDate(corpId);
            list456 = billPayCorp.getPendingPaymentbyCorpgroupbyWeekID(corpId);
            /*Getting details from Bill Receive Corp*/
            List<BigDecimal> list789 = new ArrayList<BigDecimal>();
            List<Date> list786 = new ArrayList<Date>();
            list786 = billrecvdao.getPendingReceivablebyCorpgroupbyBillingDate(corpId);
            list789 = billrecvdao.getPendingReceivebyCorpgroupbyWeekID(corpId);
            if (list786 != null && !(list786.isEmpty())) {
                for (int n = 0; n < list786.size(); n++) {
                    int flag = 0;
                    System.out.println("Refund Dates  " + list786.get(n));
                    for (int y = 0; y < list123.size(); y++) {
                        if (list786.get(n).compareTo(list123.get(y)) == 0) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        list123.add(list786.get(n));
                    }
                }
            }
            if (list789 != null && !(list789.isEmpty())) {
                for (int n = 0; n < list789.size(); n++) {
                    int flag = 0;
                    for (int y = 0; y < list456.size(); y++) {
                        if (list789.get(n).compareTo(list456.get(y)) == 0) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        list456.add(list789.get(n));
                        System.out.println("Week ID in list is " + list456.get(n));
                    }
                }
            }
            Collections.sort(list456);
            Collections.sort(list123);
            billpayObjlist = billPayCorp.getAllPendingPayableCorpList(new BigDecimal(corpId));
            System.out.println("$$$$$$$$$$$$$$$$$$$ %%%%%%%%%%%billpayObjlist size is " + billpayObjlist.size());
            for (Object[] result : billpayObjlist) {
                billpaycorp = new BillPayableCorp();
                BigDecimal uniuqeid = (BigDecimal) result[0];
                BigDecimal weekid = (BigDecimal) result[8];
                BigDecimal totalnet = (BigDecimal) result[5];
                BigDecimal pending = (BigDecimal) result[6];
                BigDecimal revisionno = (BigDecimal) result[9];
                System.out.println("uniuqeid is " + uniuqeid);
                System.out.println("weekid is " + weekid);
                System.out.println("totalnet is " + totalnet);
                System.out.println("pending is " + pending);
                System.out.println("revisionno is " + revisionno);
                billpaycorp.setUniqueId(uniuqeid);
                billpaycorp.setBillType((String) result[1]);
                billpaycorp.setBillCategory((String) result[2]);
                billpaycorp.setWeekId(weekid);
                billpaycorp.setBillDueDate((Date) result[4]);
                billpaycorp.setTotalnet(totalnet);
                billpaycorp.setPendingAmount(pending);
                billpaycorp.setBillingDate((Date) result[3]);
                billpaycorp.setRevisionNo(revisionno);
                billPayList.add(billpaycorp);
            }
            System.out.println(" billPayList is " + billPayList.size());
            List<BankStatement> bnkstmtList = bankStmtDao.BankStatementlist(corporateid);
            List<BankStatement> bnkstmtOrderList = bankStmtDao.BankStatementOrderlist(corporateid);

//            if (billPayList != null && !(billPayList.isEmpty()) && bnkstmtList != null && !(bnkstmtList.isEmpty())) {
            if (bnkstmtList != null && !(bnkstmtList.isEmpty())) {

//                System.out.println("billPayList size is " + billPayList.size());
                System.out.println("bnkstmtList size is " + bnkstmtList.size());
                ArrayList list999 = new ArrayList();
                Map author1 = new HashMap();
                Map author2 = new HashMap();
                Map author3 = new HashMap();
                Map author4 = new HashMap();
                Map author5 = new HashMap();
                Map author6 = new HashMap();
                Map author7 = new HashMap();
                Map author8 = new HashMap();
                author1.put("Priority", "DSM");
                list999.add(author1);
                author2.put("Priority", "RRAS");
                list999.add(author2);
                author3.put("Priority", "AGC");
                list999.add(author3);
                author4.put("Priority", "FRAS");
                list999.add(author4);
                author5.put("Priority", "SRAS");
                list999.add(author5);

                author6.put("Priority", "TRASM");
                list999.add(author6);
                author7.put("Priority", "TRASS");
                list999.add(author7);
                author8.put("Priority", "TRASE");
                list999.add(author8);
                ModelAndView mv1 = new ModelAndView("BillProcessing/billPayableDisplay");
                mv1.addObject("corpName", corpName);
                mv1.addObject("corpid", corporateid);
                mv1.addObject("bnkstmtList", bnkstmtList);
                mv1.addObject("billPayList", billPayList);
                mv1.addObject("billdateList", list123);
                mv1.addObject("weekList", list456);
                mv1.addObject("priorityList", list999);
                List<BillPayableCorp> billPayNotinTempList = null;
                BillPayableCorpDAO billpaydao = new BillPayableCorpDAO();
                billPayNotinTempList = billpaydao.BillPayableCorpNotInTemplist(corpId);
                mv1.addObject("tempbillPayList", billPayNotinTempList);
                TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
                System.out.println("CorpName is " + corpName);
                List<TempMapBankStatement> list4 = null;
                List<TempMappingBillBank> list5 = null;
                list5 = tmpmapdao.getTempMappingBillBankbyCorpID(corporateid, "Pending");
                String flag = "NoPending";
                // HashMap<Integer, String> hm = new HashMap<Integer, String>();
                int pendcount = 0;

//                for (TempMappingBillBank e : list5) {
//                    if (e.getPendingAmount().compareTo(BigDecimal.ZERO) == 0) {
//                        hm.put(e.getBillPayableCorp().getUniqueId().intValue(), flag);
//                    }
//                }
//                if (list5.size() > 0) {
//                    list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid,"Bills");
//                }
                list4 = tempbakstdao.getMaxTempMapBankStatementbyStatus("Pending", corporateid, "Bills");
                List<BankStatement> list6 = null;
                TempMappingBillBankDAO tempmapbilldao = new TempMappingBillBankDAO();
                list6 = tempbakstdao.getBankStatementNotINTempMapBankStatement("Pending", corpId);
                List<TempMappingBillBank> list7 = null;
                list7 = tempmapbilldao.getMaxTempMappingBillBankbyCorpID(corpId, "Pending");
                List<TempMapBankStatement> list8 = null;
                list8 = tempbakstdao.getTempMapBankStatementbyStatus("Pending", corpId, "Bills");
                TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
                List<TempRefundBillCorp> list9 = null;
                list9 = temprefunddao.getAllRefundPayablePendingTempRefundBillCorpByChecker(corpId);
                /* Getting Refund Records from billReceivebale corp*/
                List<BillReceiveCorp> listNotINtemprefund = null;
                listNotINtemprefund = billrecvdao.getRefundPendingBillReceiveAmountNotINTempRefundbyCorp(corpId);
                List<BillReceiveCorp> listrefund = null;
                listrefund = billrecvdao.getRefundPendingBillReceiveAmountbyCorp(corpId);
                mv1.addObject("listNotINtemprefund", listNotINtemprefund);
                mv1.addObject("refundList", listrefund);
                mv1.addObject("tempmaplist", list5);
                mv1.addObject("tempmapMaxlist", list7);
                mv1.addObject("tempBankStmtlist", list6);
                mv1.addObject("tempbankstmtList", list8);
                mv1.addObject("bankList", list4);
                mv1.addObject("CorpID", corporateid);
                //mv1.addObject("hm", hm);
                mv1.addObject("temprefundList", list9);
                TempInterestDetailsDAO tempintedao = new TempInterestDetailsDAO();
                List<TempInterestDetails> listtempinterest = null;
                listtempinterest = tempintedao.getTempInterestDetailsbyCorpid("Pending", corpId);
                System.out.println("TempInterest size is : " + listtempinterest.size());
                if (listtempinterest != null && listtempinterest.size() > 0) {
                    mv1.addObject("listtempinterest", listtempinterest);
                } else {
                    System.out.println("TempInterest Not Record not found");
                    InterestDetailsDAO intedao = new InterestDetailsDAO();
                    List<InterestDetails> listInteres = null;
                    listInteres = intedao.getInterestPayableDetailsbyCorp(corpId);
                    mv1.addObject("listInterest", listInteres);
                }
                return mv1;
            } else {
                System.out.println("Record not found");
                ModelAndView mv1 = new ModelAndView("successMsg");
                String Msg = "Please check-- there is no Verified Bank Transaction for Mapping for " + corpName + "!";
                mv1.addObject("Msg", Msg);
                return mv1;
            }
        }
        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
        List<BigDecimal> list = new ArrayList<>();
        list = tmpmapdao.getTempMappingBillBankbyPendingCorpList("Pending");
        System.out.println("list is " + list);
        List<String> pendingVerifyCorpList = new ArrayList<>();
        for (BigDecimal result : list) {
            System.out.println("corporate is " + result);
            Integer corpId = result.intValueExact();
            String corpname = corpDao.getCorpNamebyId(corpId);
            System.out.println("corp is " + corpname);
            pendingVerifyCorpList.add(corpname);
        }

        //=================================================================================
        TempRefundBillCorpDAO temrefcorpdao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> reflist = null;
        reflist = temrefcorpdao.getTempRefundPendingbyCorpListbychecker("Pending");

        for (TempRefundBillCorp o : reflist) {
            if (!pendingVerifyCorpList.contains(o.getCorporates().getCorporateName())) {
                pendingVerifyCorpList.add(o.getCorporates().getCorporateName());
            }
        }

        //=================================================================================
        List<BillPayableCorp> listBillPayableCorp = new ArrayList<>();
        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
        List<String> listCorp = new ArrayList<>();
        List<BillReceiveCorp> listCorpRefund = new ArrayList<>();
        BillReceiveCorpDAO billRecvCorpDao = new BillReceiveCorpDAO();
        listBillPayableCorp = billpaycorpdao.getPendingBillCorpNameList();
        listCorpRefund = billRecvCorpDao.getPendingBillCorpRefundList();
        for (BillPayableCorp temp : listBillPayableCorp) {
            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
            if (ans); else {
                listCorp.add(temp.getCorporates().getCorporateName());
            }
        }
        for (BillReceiveCorp temp : listCorpRefund) {

//            System.out.println(temp.getRevisionNo() + " " + temp.getBillDueDate() + " " + temp.getBillStatus() + " " + temp.getBillType() + " " + temp.getCorporates().getCorporateName() + " " + temp.getWeekId() + " ");
            boolean ans = listCorp.contains(temp.getCorporates().getCorporateName());
            if (ans); else {
                listCorp.add(temp.getCorporates().getCorporateName());
            }
        }
        BankStatementDAO bankStmtDao = new BankStatementDAO();
        List<BankStatement> bnkstmtListforallcorp = bankStmtDao.BankStatementlistofallcorp();
        List<String> corpnames = new ArrayList<>();
        if (bnkstmtListforallcorp != null && !(bnkstmtListforallcorp.isEmpty())) {
            for (BankStatement e : bnkstmtListforallcorp) {
                if (!corpnames.contains(e.getCorporates().getCorporateName())) {
                    corpnames.add(e.getCorporates().getCorporateName());
                }

            }
        } else {
            corpnames.add("dummy");
        }
//        for (String stg : corpnames) {
//            System.out.println("corpnames are " + stg);
//        }
//
//        for (String stg : listCorp) {
//            System.out.println("listCorp are " + stg);
//        }
        mv.addObject("corpnames", corpnames);
        mv.addObject("bnkstmtListforallcorp", bnkstmtListforallcorp);
        mv.addObject("pendingVerifyCorpList", pendingVerifyCorpList);
        mv.addObject("PendningList", list);
        mv.addObject("corpList", listCorp);
        return mv;
    }

    public ModelAndView viewMakerPendingPayableListbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/viewMakerPendingPayableListbyRLDC");
        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
        List<Object[]> list = new ArrayList<Object[]>();
//        list = tmpmapdao.getTempMappingBillBankbyCorpList("Pending");
        list = tmpmapdao.getTempPendingMappingBillBankbyCorpList("Pending");
        System.out.println("list is " + list);
        mv.addObject("corpPendingList", list);
        return mv;
    }

    public ModelAndView viewCheckerPendingPayableBillbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/viewCheckerPendingPayableBillbyRLDC");
        String bverify = request.getParameter("bverify");
        List<TempMappingBillBank> list = null;
        BankStatementDAO banstdao = new BankStatementDAO();
        if (bverify != null) {
            String corp_ID = request.getParameter("corp_ID");
            System.out.println("@@@@@@@@@@ corp_ID is " + corp_ID);
            BillPayableCorpDAO bipaydao = new BillPayableCorpDAO();
            TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
            DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();

            list = tmpmapdao.getTempMappingBillBankbyCorpID("Pending", Integer.parseInt(corp_ID));
            ////////////////Updating the BILL_PAYABLE_CORP from TEMP_MAPPING_BILL

            MappingBillBankDAO mpbillbankdao = new MappingBillBankDAO();
            TempInterestDetailsDAO tempintesdao = new TempInterestDetailsDAO();
            TempInterestDetails tempintes = new TempInterestDetails();
            Corporates corp = new Corporates();
            int maxintesid = 0;
            int uniquemappid = 0;
            BillInterestRateDAO billintedao = new BillInterestRateDAO();
            List<BillInterestRate> listintes = null;
            listintes = billintedao.getBillInterestRate("DSM", "PAYABLE");
            List<TempMapBankStatement> list2 = null;
            List<BigDecimal> liststmtid = new ArrayList<BigDecimal>();
            TempMapBankStatementDAO tempbanstdao = new TempMapBankStatementDAO();
            BigDecimal poolsum = new BigDecimal(0);
            int uqiid = 0;
            String status;
            int stmtidflag = 0;
            int stmfidvar = 0;

            List<PoolAccountDetails> mainpoollist = null;
            List<AgcPoolAccountDetails> agcpoollist = null;
            List<RrasPoolAccountDetails> rraspoollist = null;

            PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
            mainpoollist = pooldao.getPoolAccountDetails();
            agcpoollist = pooldao.getAgcPoolAccountDetails();
            rraspoollist = pooldao.getRrasPoolAccountDetails();

            BigDecimal agcsum = new BigDecimal(0);

            poolsum = mainpoollist.get(0).getMainBalance();

            agcsum = agcpoollist.get(0).getMainBalance();
            BigDecimal rrassum = new BigDecimal(0);
            rrassum = rraspoollist.get(0).getMainBalance();

            BigDecimal totalmapped = new BigDecimal(0);
            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

            for (TempMappingBillBank e : list) {
                BigDecimal bg = e.getBillPayableCorp().getUniqueId();
                uqiid = bg.intValue();
                uniquemappid = mpbillbankdao.getMaxUniqueID();
                uniquemappid = uniquemappid + 1;
                MappingBillBank mpbill = new MappingBillBank();
                mpbill.setUniqueId(new BigDecimal(uniquemappid));
                mpbill.setCheckerStatus("Verified");
                mpbill.setBillType(e.getBillType());
                mpbill.setCorporates(e.getCorporates());
                mpbill.setBankStatement(e.getBankStatement());
                mpbill.setBillPayableCorp(e.getBillPayableCorp());
                mpbill.setEntryDate(new Date());
                mpbill.setMappedAmount(e.getMappedAmount());
                mpbill.setOriginalAmount(e.getOriginalAmount());
                mpbill.setPaymentCategory(e.getPaymentCategory());
                mpbill.setPendingAmount(e.getPendingAmount());
                mpbill.setRemarks(e.getRemarks());
                mpbill.setWeekId(e.getWeekId());
                BigDecimal stmtid = e.getBankStatement().getStmtId();
                System.out.println("stmtid is " + stmtid);
                int flag = 0;
                if (liststmtid.isEmpty()) {
                    liststmtid.add(stmtid);
                    System.out.println(" Inside  liststmtid " + liststmtid.size());
                }

                for (int i = 0; i < liststmtid.size(); i++) {
                    if (liststmtid.get(i).equals(stmtid)) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    liststmtid.add(stmtid);
                    System.out.println("*************** ID ADDING flag is of size " + liststmtid.size() + "and flag is " + flag);
                }
                System.out.println(" After Mapping bill liststmtid size is " + liststmtid.size());
                if (stmtidflag == 0) {
                    stmfidvar = stmtid.intValue();
                    stmtidflag = 1;
                    BigDecimal summmapedverified = tempbanstdao.getVerifiedSumMappedAmtTempMapBankStatementbySTMTID(stmtid);
                    totalmapped = totalmapped.add(summmapedverified);
                } else {
                    if (stmfidvar != stmtid.intValue()) {
                        stmtidflag = 0;
                        totalmapped = BigDecimal.ZERO;
                        BigDecimal summmapedverified = tempbanstdao.getVerifiedSumMappedAmtTempMapBankStatementbySTMTID(stmtid);
                        totalmapped = totalmapped.add(summmapedverified);
                    }
                }
                System.out.println("stmtid is " + stmtid);
                list2 = tempbanstdao.getTempMapBankStatementbySTMTID(stmtid); //getting max stmtid details
                BigDecimal tansbal = list2.get(0).getTransactionAmount();
                System.out.println("tansbal is " + tansbal);
                totalmapped = totalmapped.add(e.getMappedAmount());
                System.out.println("totalmapped is " + totalmapped);
                BigDecimal availbal = tansbal.subtract(totalmapped);
                System.out.println("availbal is " + availbal);
//                mpbill.setPendingBankAmount(availbal);
                mpbill.setPendingBankAmount(e.getBankPendingAmount());

                if (e.getBillPayableCorp().getBillType().equalsIgnoreCase("sras")) {
                    agcsum = agcsum.add(e.getMappedAmount());
                    poolsum = poolsum.subtract(e.getMappedAmount());
                    mpbill.setPoolBal(poolsum);
                    mpbill.setPoolAgcBal(agcsum);
                    currentTimestamp = addMilliseconds(currentTimestamp, 1);

                    mpbill.setEntryTime(currentTimestamp);
                    pooldao.getUpdatePoolAccountbyChecker(poolsum);
                    pooldao.getUpdateAgcPoolAccountbyChecker(agcsum);
                }
                if (e.getBillPayableCorp().getBillType().equalsIgnoreCase("trasm")
                        || e.getBillPayableCorp().getBillType().equalsIgnoreCase("trass")
                        || e.getBillPayableCorp().getBillType().equalsIgnoreCase("trase")) {
                    rrassum = rrassum.add(e.getMappedAmount());
                    poolsum = poolsum.subtract(e.getMappedAmount());
                    mpbill.setPoolBal(poolsum);
                    mpbill.setPoolRrasBal(rrassum);
                    currentTimestamp = addMilliseconds(currentTimestamp, 1);

                    mpbill.setEntryTime(currentTimestamp);
                    pooldao.getUpdatePoolAccountbyChecker(poolsum);
                    pooldao.getUpdateRrasPoolAccountbyChecker(rrassum);
                }

                mpbillbankdao.NewMappingBillBank(mpbill);

                //#################################################### dyn recon 
                DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                BigDecimal slno = new BigDecimal(0);
                slno = new BigDecimal(reconcorpdao.getMaxslno());
                slno = slno.add(BigDecimal.ONE);
                reconcorp.setSlno(slno);
                reconcorp.setCorporates(e.getCorporates());
                reconcorp.setWeekId(e.getBillPayableCorp().getWeekId());
                reconcorp.setBillEntryDate(new Date());
                reconcorp.setBillType(e.getBillPayableCorp().getBillType());
                reconcorp.setBillingDate(e.getBillPayableCorp().getBillingDate());
                reconcorp.setRevisionNo(e.getBillPayableCorp().getRevisionNo());
                reconcorp.setBillDueDate(e.getBillPayableCorp().getBillDueDate());
                reconcorp.setPayTotalnet(e.getBillPayableCorp().getTotalnet());
                reconcorp.setEntryDate(new Date());
                reconcorp.setBillYear(e.getBillPayableCorp().getBillYear());
                reconcorp.setPayFinalamount(e.getPendingAmount().add(e.getMappedAmount()));
                reconcorp.setPayPendingamount(e.getPendingAmount());
                reconcorp.setCrDrDate(e.getBankStatement().getAmountDate());
                reconcorp.setCrAmount(e.getBankStatement().getPaidAmount());
                reconcorp.setCrSettledAmount(e.getMappedAmount());
                reconcorp.setCrAvailable(e.getBankPendingAmount());
                BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(e.getCorporates().getCorporateId());
                outstanding = outstanding.subtract(e.getMappedAmount());
                reconcorp.setOutstandingAmount(outstanding);
                reconcorp.setRemarks("payable Mapping");
                reconcorpdao.NewReconciliationCorp(reconcorp);

                String remarks = list2.get(0).getRemarks();
                Date bill_duedate = e.getBillPayableCorp().getBillDueDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date3 = sdf.format(bill_duedate);
//                Date creditdate = list2.get(0).getBankStatement().getAmountDate();
                Date creditdate = e.getBankStatement().getAmountDate();

                String date4 = sdf.format(creditdate);
                System.out.print("creditdate datwe in bank  " + creditdate);
                System.out.print("bill_duedate " + bill_duedate);
                Date date1 = sdf.parse(date3);
                Date date2 = sdf.parse(date4);
                int interestflag = 0;
                float daysBetween = 0;
//                if (date1.compareTo(date2) < 0) {
//                    interestflag = 1;
//                    long difference = date2.getTime() - date1.getTime();
//                    daysBetween = (difference / (1000 * 60 * 60 * 24));
//                    System.out.println("daysBetween" + daysBetween);
//                }
                if (date1.compareTo(date2) < 0 && e.getMappedAmount().compareTo(BigDecimal.ONE) >= 0) {

                    long difference = date2.getTime() - date1.getTime();
                    daysBetween = (difference / (1000 * 60 * 60 * 24));
                    System.out.println("daysBetween" + daysBetween);
                    BigDecimal inbg = e.getMappedAmount().multiply(new BigDecimal(daysBetween)).multiply(listintes.get(0).getInterestRate());
                    if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {
                        interestflag = 1;
                    }
                    System.out.println("inbg" + inbg);
                }
                System.out.println("interestflag" + interestflag);
                if (interestflag == 1) {
                    maxintesid = tempintesdao.getMaxInterestid();
                    maxintesid = maxintesid + 1;
                    BigDecimal noofday = new BigDecimal(daysBetween);
                    tempintes.setNoofdays(new BigDecimal(daysBetween));
                    tempintes.setInterestId(new BigDecimal(maxintesid));
                    tempintes.setBillCategory(e.getPaymentCategory());
                    tempintes.setBillType(e.getBillType());

                    tempintes.setBilledAmount(e.getOriginalAmount());

                    tempintes.setBillingDate(e.getBillPayableCorp().getBillingDate());
                    tempintes.setBillingDuedate(e.getBillPayableCorp().getBillDueDate());
                    tempintes.setCheckerStatus("Pending");
                    corp.setCorporateId(e.getCorporates().getCorporateId());
                    tempintes.setCorporates(corp);
                    tempintes.setEntryDate(new Date());
                    tempintes.setInterestBilledamount(e.getMappedAmount());
                    BigDecimal bgInrate = listintes.get(0).getInterestRate();
                    System.out.println("Interest rate is:" + bgInrate);
                    BigDecimal inbg = e.getMappedAmount().multiply(noofday).multiply(bgInrate);
                    System.out.println("Interest amount is:" + inbg);
                    tempintes.setInterestAmount(inbg);
                    tempintes.setPaidAmount(e.getMappedAmount());
                    tempintes.setPaidDate(e.getBankStatement().getAmountDate());
                    tempintes.setWeekId(e.getWeekId());
                    tempintes.setBillYear(e.getBillPayableCorp().getBillYear());
                    tempintes.setRevisionNo(e.getBillPayableCorp().getRevisionNo());
                    tempintesdao.NewTempInterestDetails(tempintes);
                }
            }
//            int flag99 = 0;

            List<BillPayableCorp> list456 = null;
            List<Object[]> billPay2 = new ArrayList<>();
            billPay2 = tmpmapdao.getTempMappingBillBankbyCorpIDBillUNIQUEID("Pending", Integer.parseInt(corp_ID));
            BigDecimal pendamt = null;
            for (Object[] result : billPay2) {
                BigDecimal billid = (BigDecimal) result[0];
                BigDecimal mappedamt = (BigDecimal) result[1];
                System.out.print("UNIQ IS" + billid);
                System.out.print("mappedamt IS" + mappedamt);
                list456 = bipaydao.getBillPayableCorplistbyUniqueID(billid.intValue());
                System.out.print("Inside Bill Paybale UNIQUE ID is " + list456.get(0).getUniqueId());
                mappedamt = mappedamt.add(list456.get(0).getPaidAmount());
                System.out.print("after addition mappedamt IS" + mappedamt);
                if (list456.get(0).getRevisionNo().intValue() > 0) {
                    pendamt = list456.get(0).getRevisedpaybale().subtract(mappedamt);
                } else {
                    pendamt = list456.get(0).getTotalnet().subtract(mappedamt);
                }
                String status1 = null;
                if (pendamt.compareTo(BigDecimal.ZERO) == 0) {
                    status1 = "PAID";
                    //mappedamt = list456.get(0).getTotalnet();
                } else {
                    status1 = "PARTIALLY";
                }
                if (list456.get(0).getUniqueId().intValue() == billid.intValue() && list456.get(0).getRevisionNo().intValue() == 0) {
                    bipaydao.getUpdateBillPayableCorpbyChecker(billid.intValue(), mappedamt, pendamt, status1);
                }
                if (list456.get(0).getUniqueId().intValue() == billid.intValue() && list456.get(0).getRevisionNo().intValue() > 0) {
                    bipaydao.getUpdateBillPayableCorpbyChecker1(billid.intValue(), mappedamt, pendamt, status1);
                }
            }
            TempRefundBillCorpDAO temprefunddao = new TempRefundBillCorpDAO();
            List<TempRefundBillCorp> listrefund = new ArrayList<>();
            BillReceiveCorpDAO billrecdao = new BillReceiveCorpDAO();
            listrefund = temprefunddao.getAllRefundPayablePendingTempRefundBillCorp(Integer.parseInt(corp_ID));

            List<BillReceiveCorp> list328 = null;
            List<Object[]> refundrec = new ArrayList<>();
            refundrec = temprefunddao.getTemprefundofrecvbyCorpIDBillUNIQUEID(Integer.parseInt(corp_ID));
            BigDecimal recrefundpendamt = null;
            for (Object[] result1 : refundrec) {
                BigDecimal billrecid = (BigDecimal) result1[0];
                BigDecimal maprefundamt = (BigDecimal) result1[1];
                System.out.print("Recv_UNIQ IS" + billrecid);
                System.out.print("mapped refund amt IS" + maprefundamt);
                list328 = billrecdao.getBillReceiveCorplistbyUniqueID(billrecid.intValue());
                System.out.print("Inside Bill receive UNIQUE ID is " + list328.get(0).getUniqueId());
                maprefundamt = maprefundamt.add(list328.get(0).getAdjustmentAmount());
                System.out.print("after addition map refund amt IS" + maprefundamt);
                recrefundpendamt = list328.get(0).getRevisedrefund().subtract(maprefundamt);
                billrecdao.getUpdateRefundBillReceivableCorpbyChecker(billrecid.intValue(), maprefundamt, recrefundpendamt);

            }

            temprefunddao.getUpdatePendingTempRefundBillReceivableCorpID(Integer.parseInt(corp_ID));

            //System.out.println("$$$$$$$$$$$$$$listrefund  is "+listrefund.size());
//            if (listrefund != null && listrefund.size() > 0) {
//                for (TempRefundBillCorp e : listrefund) {
//                    billrecdao.getUpdateRefundBillReceivableCorpbyChecker(e.getBillReceiveCorp().getUniqueId().intValue(), e.getPaidAmount(), e.getPendingAmount());
//                }
//                temprefunddao.getUpdatePendingTempRefundBillReceivableCorpID(Integer.parseInt(corp_ID));
//            }
            MappingRefundBankDAO mapprefudndao = new MappingRefundBankDAO();
            List<MappingRefundBank> listmapprefundbank = null;
            listmapprefundbank = mapprefudndao.getMappingRefundBankbyCorpID(Integer.parseInt(corp_ID));
            List<BigDecimal> listsmt = new ArrayList<BigDecimal>();
            listsmt = mapprefudndao.getSTmtIDMappingRefundBankbyCorpID();
            for (MappingRefundBank e1 : listmapprefundbank) {

                if (e1.getTempRefundBillCorp().getBillReceiveCorp().getBillType().equalsIgnoreCase("sras")) {
                    agcsum = agcsum.add(e1.getMappedAmount());
                    poolsum = poolsum.subtract(e1.getMappedAmount());
                    mapprefudndao.getUpdatedMappingRefundBankbypoolagcbal(poolsum, agcsum, e1.getSlno().intValue());

                    pooldao.getUpdatePoolAccountbyChecker(poolsum);
                    pooldao.getUpdateAgcPoolAccountbyChecker(agcsum);
                }
                if (e1.getTempRefundBillCorp().getBillReceiveCorp().getBillType().equalsIgnoreCase("trasm")
                        || e1.getTempRefundBillCorp().getBillReceiveCorp().getBillType().equalsIgnoreCase("trass")
                        || e1.getTempRefundBillCorp().getBillReceiveCorp().getBillType().equalsIgnoreCase("trase")) {
                    rrassum = rrassum.add(e1.getMappedAmount());
                    poolsum = poolsum.subtract(e1.getMappedAmount());
                    mapprefudndao.getUpdatedMappingRefundBankbypoolrrasbal(poolsum, rrassum, e1.getSlno().intValue());

                    pooldao.getUpdatePoolAccountbyChecker(poolsum);
                    pooldao.getUpdateRrasPoolAccountbyChecker(rrassum);
                }

                BigDecimal stmtid = e1.getBankStatement().getStmtId();

                System.out.println(" Before Mapping Refund liststmtid size is " + liststmtid.size());
                System.out.println("stmtid is " + stmtid);
                int flag = 0;
                if (liststmtid.isEmpty()) {
                    liststmtid.add(stmtid);
                    System.out.println(" Inside  liststmtid " + liststmtid.size());
                }
                System.out.println(" After liststmtid size is " + liststmtid.size());
                for (int i = 0; i < liststmtid.size(); i++) {
                    if (liststmtid.get(i).equals(stmtid)) {
                        flag = 1;
                        System.out.println(" ID already there and size and flag  is " + liststmtid.size() + "and flag is " + flag);
                    }
                    System.out.println("Insde Smt is:" + liststmtid.get(i).intValue());
                    System.out.println(" inside for loop and  flag  is " + liststmtid.size() + "and flag is " + flag);
                }
                if (flag == 0) {
                    liststmtid.add(stmtid);
                    System.out.println("*************** ID ADDING flag is of size " + liststmtid.size() + "and flag is " + flag);
                }
                System.out.println(" After Mapping Refund liststmtid size is " + liststmtid.size());
                Date bill_duedate = e1.getTempRefundBillCorp().getBillReceiveCorp().getBillDueDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date3 = sdf.format(bill_duedate);
                Date creditdate = e1.getBankStatement().getAmountDate();
                String date4 = sdf.format(creditdate);
                System.out.print("creditdate datwe in bank  " + creditdate);
                System.out.print("bill_duedate " + bill_duedate);
                Date date1 = sdf.parse(date3);
                Date date2 = sdf.parse(date4);
                int interestflag = 0;
                float daysBetween = 0;
//                if (date1.compareTo(date2) < 0) {
//                    interestflag = 1;
//                    long difference = date2.getTime() - date1.getTime();
//                    daysBetween = (difference / (1000 * 60 * 60 * 24));
//                    System.out.println("daysBetween" + daysBetween);
//                }
                if (date1.compareTo(date2) < 0 && e1.getMappedAmount().compareTo(BigDecimal.ONE) >= 0) {

                    long difference = date2.getTime() - date1.getTime();
                    daysBetween = (difference / (1000 * 60 * 60 * 24));
                    System.out.println("daysBetween" + daysBetween);
                    BigDecimal inbg = e1.getMappedAmount().multiply(new BigDecimal(daysBetween)).multiply(listintes.get(0).getInterestRate());
                    if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {
                        interestflag = 1;
                    }
                    System.out.println("inbg" + inbg);
                }
                if (interestflag == 1) {
                    maxintesid = tempintesdao.getMaxInterestid();
                    maxintesid = maxintesid + 1;
                    BigDecimal noofday = new BigDecimal(daysBetween);
                    tempintes.setNoofdays(new BigDecimal(daysBetween));
                    tempintes.setInterestId(new BigDecimal(maxintesid));
                    tempintes.setBillCategory(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillCategory());
                    tempintes.setBillType(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillType());
                    tempintes.setBilledAmount(e1.getTempRefundBillCorp().getBillReceiveCorp().getToalnet());
                    tempintes.setBillingDate(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillingDate());
                    tempintes.setBillingDuedate(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillDueDate());
                    tempintes.setCheckerStatus("Pending");
                    corp.setCorporateId(e1.getTempRefundBillCorp().getCorporates().getCorporateId());
                    tempintes.setCorporates(corp);
                    tempintes.setEntryDate(new Date());
                    tempintes.setInterestBilledamount(e1.getMappedAmount());
                    // listintes = billintedao.getBillInterestRate("DSM", "RECEIVABLE");
                    BigDecimal bgInrate = listintes.get(0).getInterestRate();
                    System.out.println("Interest rate is:" + bgInrate);
                    BigDecimal inbg = e1.getMappedAmount().multiply(noofday).multiply(bgInrate);
                    System.out.println("Interest amount is:" + inbg);
                    tempintes.setInterestAmount(inbg);
                    tempintes.setPaidAmount(e1.getMappedAmount());
                    tempintes.setPaidDate(e1.getBankStatement().getAmountDate());
                    tempintes.setWeekId(e1.getTempRefundBillCorp().getWeekid());
                    tempintes.setBillYear(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillYear());
                    tempintes.setRevisionNo(e1.getTempRefundBillCorp().getBillReceiveCorp().getRevisionNo());
                    tempintesdao.NewTempInterestDetails(tempintes);
                }
//                BigDecimal refundId = e1.getTempRefundBillCorp().getSlno();
                BigDecimal refundId = e1.getSlno();

                mapprefudndao.getUpdatedMappingRefundBankbyCorp(refundId.intValueExact(), "Verified");

                DynReconciliationCorp reconcorp = new DynReconciliationCorp();
                BigDecimal slno = new BigDecimal(0);
                slno = new BigDecimal(reconcorpdao.getMaxslno());
                slno = slno.add(BigDecimal.ONE);
                reconcorp.setSlno(slno);
                reconcorp.setCorporates(e1.getTempRefundBillCorp().getCorporates());
                reconcorp.setWeekId(e1.getTempRefundBillCorp().getBillReceiveCorp().getWeekId());
                reconcorp.setBillEntryDate(e1.getTempRefundBillCorp().getRefundDate());
                reconcorp.setBillType(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillType());
                reconcorp.setBillingDate(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillingDate());
                reconcorp.setRevisionNo(e1.getTempRefundBillCorp().getBillReceiveCorp().getRevisionNo());
                reconcorp.setBillDueDate(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillDueDate());
                reconcorp.setRecTotalnet(e1.getTempRefundBillCorp().getBillReceiveCorp().getToalnet());
                reconcorp.setEntryDate(new Date());
                reconcorp.setBillYear(e1.getTempRefundBillCorp().getBillReceiveCorp().getBillYear());
                reconcorp.setPayFinalamount(e1.getTempRefundBillCorp().getPendingAmount().add(e1.getTempRefundBillCorp().getPaidAmount()));
                reconcorp.setPayPendingamount(e1.getTempRefundBillCorp().getPendingAmount());
                reconcorp.setCrDrDate(e1.getBankStatement().getAmountDate());
                reconcorp.setCrAmount(e1.getBankStatement().getPaidAmount());
                reconcorp.setCrSettledAmount(e1.getMappedAmount());
                reconcorp.setCrAvailable(e1.getBankPendingAmount());
                BigDecimal outstanding = reconcorpdao.getLatestOutstandingbyCorpId(e1.getTempRefundBillCorp().getCorporates().getCorporateId());
                outstanding = outstanding.subtract(e1.getTempRefundBillCorp().getPaidAmount());
                reconcorp.setOutstandingAmount(outstanding);
                reconcorp.setRemarks("Refund Mapping");
                reconcorpdao.NewReconciliationCorp(reconcorp);
            }
            for (int j = 0; j < liststmtid.size(); j++) {
                System.out.println("Smts @@@@@@@@  is:" + liststmtid.get(j).intValue());
            }

            BigDecimal bankmapped = new BigDecimal(0);
            for (int j = 0; j < liststmtid.size(); j++) {
                bankmapped = bankmapped.ZERO;
                System.out.println("Insde Smt is:" + liststmtid.get(j).intValue());
                bankmapped = banstdao.getMappedAmountBankStmtbyStmtID(liststmtid.get(j).intValue());
                System.out.println("Mapped balce from Bankstmt for stmt is rate is:" + bankmapped);
                list2 = tempbanstdao.getTempMapBankStatementbySTMTID(liststmtid.get(j)); //getting max stmtid details
                BigDecimal totlamapped = tempbanstdao.getSumMappedAmtTempMapBankStatementbySTMTID(liststmtid.get(j));
                System.out.println("SumMapped balce from TempMapBankstmt for is:" + totlamapped);
                BigDecimal totalnet = list2.get(0).getTransactionAmount();
                bankmapped = bankmapped.add(totlamapped);
                BigDecimal availbal = totalnet.subtract(bankmapped);
                System.out.println("bankmapped is=" + bankmapped);
                banstdao.getUpdateBankStmtbyCheckerforCorp(liststmtid.get(j), bankmapped, availbal, list2.get(0).getRemarks());
//                flag99 = 1;

            }

//            {
//                for (int j = 0; j < listsmt.size(); j++) {
//                    bankmapped = bankmapped.ZERO;
//                    System.out.println("Insde Smt is:" + listsmt.get(j));
//                    bankmapped = banstdao.getMappedAmountBankStmtbyStmtID(listsmt.get(j).intValue());
//                    System.out.println("Mapped balce from Bankstmt for stmt is rate is:" + bankmapped);
//                    list2 = tempbanstdao.getTempMapBankStatementbySTMTID(listsmt.get(j)); //getting max stmtid details
//                    BigDecimal totlamapped = tempbanstdao.getSumMappedAmtTempMapBankStatementbySTMTID(listsmt.get(j));
//                    System.out.println("SumMapped balce from TempMapBankstmt for is:" + totlamapped);
//                    BigDecimal totalnet = list2.get(0).getTransactionAmount();
//                    bankmapped = bankmapped.add(totlamapped);
//                    BigDecimal availbal = totalnet.subtract(bankmapped);
//                    banstdao.getUpdateBankStmtbyCheckerforCorp(listsmt.get(j), bankmapped, availbal, list2.get(0).getRemarks());
//
//                }
//            }
            tmpmapdao.getUpdatebyCheckerforCorp(Integer.parseInt(corp_ID), "Verified");
            tempbanstdao.getUpdatedTempBankStatementbyCorp(Integer.parseInt(corp_ID), "Verified");
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Succesfully Verified the Transaction ...........");
            return mv1;
        }
        String bdelete = request.getParameter("bdelete");
        if (bdelete != null) {
            TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();

            MappingRefundBankDAO mapreffudbank = new MappingRefundBankDAO();

            String delete_CorpID = request.getParameter("corp_ID");
            List<TempRefundBillCorp> listrefun = null;

            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();

            TempRefundBillCorpDAO tempRefundDao = new TempRefundBillCorpDAO();

            listrefun = tempRefundDao.getPendingTempRefundBillCorp(Integer.parseInt(delete_CorpID));
            if (listrefun != null && listrefun.size() > 0) {
                for (TempRefundBillCorp e : listrefun) {
                    mapreffudbank.getDeleteRefundbyCheckerforCorpID(e.getSlno().intValue());
                }

                tempRefundDao.getDeleteRefundbyMakerforCorpID(Integer.parseInt(delete_CorpID), "Pending");
            }
            tempbillmapdao.getDeletebyMakerforCorpID(Integer.parseInt(delete_CorpID), "Pending");

            tempbakstdao.getDeletedTempMapBankStatementbyCorp(Integer.parseInt(delete_CorpID), "Pending");

            ModelAndView mv1 = new ModelAndView("successMsg");

            String Msg = "Successfully Deleted the mapped pending records!!";

            mv1.addObject("Msg", Msg);
            mv1.addObject("Corpid", delete_CorpID);

            return mv1;
        }
        String corpID = request.getParameter("corpID");
        CorporatesDAO corpDAO = new CorporatesDAO();
        String CorpName = corpDAO.geCorpNamebyId(Integer.parseInt(corpID));
        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
        List<TempMapBankStatement> list123 = null;
        list = tmpmapdao.getTempMappingBillBankbyCorpID("Pending", Integer.parseInt(corpID));
        TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
        list123 = tempbakstdao.getTempMapBankStatementbyStatus("Pending", Integer.parseInt(corpID), "Bills");
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        listrefund = temprefudndao.getAllRefundPayablePendingTempRefundBillCorpByChecker(Integer.parseInt(corpID));
        mv.addObject("listrefund", listrefund);
        mv.addObject("tempmaplist", list);
        mv.addObject("bankList", list123);
        mv.addObject("corpID", corpID);
        mv.addObject("CorpName", CorpName);
        return mv;
    }

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

    public ModelAndView viewInterestCheckerListbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/viewInterestCheckerListbyRLDC");
        String bName = request.getParameter("bName");
        List<TempInterestDetails> list = null;
        TempInterestDetailsDAO tmepindao = new TempInterestDetailsDAO();
        list = tmepindao.getTempInterestDetails("Pending");
        mv.addObject("interestList", list);
        return mv;
    }

    public ModelAndView viewMakerPendingPayableBillbyRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/viewMakerPendingPayableBillbyRLDC");
        String bdelete = request.getParameter("bdelete");
        if (bdelete != null) {
            TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
            String delete_CorpID = request.getParameter("CorpID");
            TempMappingBillBankDAO tempbillmapdao = new TempMappingBillBankDAO();
            tempbillmapdao.getDeletebyMakerforCorpID(Integer.parseInt(delete_CorpID), "Pending");
            tempbakstdao.getDeletedTempMapBankStatementbyCorp(Integer.parseInt(delete_CorpID), "Pending");
            ModelAndView mv1 = new ModelAndView("successMsg");
            String Msg = "Successfully Deleted for the records!!";
            mv1.addObject("Msg", Msg);
            return mv1;
        }
//        List<TempMappingBillBank> list = null;
//        String corpID = request.getParameter("corpID");
//        System.out.println("#########     $$corpID is " + corpID);
//        CorporatesDAO corpDAO = new CorporatesDAO();
//        String CorpName = corpDAO.geCorpNamebyId(Integer.parseInt(corpID));
//        System.out.println("CorpName is " + CorpName);
//        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
//        List<TempMapBankStatement> list123 = null;
//        list = tmpmapdao.getTempMappingBillBankbyCorpID("Pending", Integer.parseInt(corpID));
//        TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
//        // list123 = tempbakstdao.getTempBankStatementbySTMTID(list.get(0).getBankStatement().getStmtId());
//        list123 = tempbakstdao.getTempMapBankStatementbyStatus("Pending", Integer.parseInt(corpID), "Bills");
//        mv.addObject("tempmaplist", list);
//        mv.addObject("bankList", list123);
//        mv.addObject("CorpID", corpID);
//        mv.addObject("CorpName", CorpName);
//        return mv;
        String corpID = request.getParameter("corpID");
        CorporatesDAO corpDAO = new CorporatesDAO();
        String CorpName = corpDAO.geCorpNamebyId(Integer.parseInt(corpID));
        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
        List<TempMapBankStatement> list123 = null;
        List<TempMappingBillBank> list = null;
        list = tmpmapdao.getTempMappingBillBankbyCorpID("Pending", Integer.parseInt(corpID));
        TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
        list123 = tempbakstdao.getTempMapBankStatementbyStatus("Pending", Integer.parseInt(corpID), "Bills");
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> listrefund = null;
        listrefund = temprefudndao.getAllRefundPayablePendingTempRefundBillCorpByChecker(Integer.parseInt(corpID));
        mv.addObject("listrefund", listrefund);
        mv.addObject("tempmaplist", list);
        mv.addObject("bankList", list123);
        mv.addObject("corpID", corpID);
        mv.addObject("CorpName", CorpName);
        return mv;
    }

    public ModelAndView weeklyBill(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/weeklyBill");
        String bName = request.getParameter("bName");
        System.out.println("bName is " + bName);
        if (bName != null) {
            System.out.println("bName NOT NULL & is " + bName);
            String week = request.getParameter("week");
            String year = request.getParameter("year");
            String billCat = request.getParameter("billCat");
            String billType = request.getParameter("billType");
            System.out.println("week is " + week);
            System.out.println("year is " + year);
            System.out.println("billCat is " + billCat);
            System.out.println("billType is " + billType);
            BigDecimal weekId = new BigDecimal(week);
            BigDecimal yearId = new BigDecimal(year);
            if ("DSM".equals(billCat)) {
                System.out.println("\"DSM\".equals(billCat)");
                ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklyDsmBill");
                BillPayableEntityDsmDAO billPayDsmEntityDao = new BillPayableEntityDsmDAO();
                BillReceiveEntityDsmDAO billReceiveEntityDsmDao = new BillReceiveEntityDsmDAO();
                BillDsmDetailsDAO billDsmDao = new BillDsmDetailsDAO();
                List<BillPayableEntityDsm> billPay = null;
                List<BillReceiveEntityDsm> billReceive = null;
                List<BillDsmDetails> billDsmInfo = null;
                if ("Original".equals(billType)) {
                    billDsmInfo = billDsmDao.BillDsmDetailsList(weekId, yearId, BigDecimal.ZERO);
                    System.out.println("billDsmInfo is " + billDsmInfo);
                    if (billDsmInfo != null) {
                        System.out.println(" billDsmInfo size is " + billDsmInfo.size());
                        System.out.println("\"Original\".equals(billType)");
                        billPay = billPayDsmEntityDao.BillPayableCorpDsmlist(weekId, yearId, BigDecimal.ZERO);
                        billReceive = billReceiveEntityDsmDao.BillReceiveEntityDsmList(weekId, yearId, BigDecimal.ZERO);
                        if (billPay != null) {
                            System.out.println("billPay size is " + billPay.size());
                            if (billReceive != null) {
                                System.out.println("billPay size is " + billReceive.size());
                            }
                        }
                    }
                    if (billPay != null || billReceive != null) {
                        System.out.println("Data is available!!");

//                        
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Data not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                } else if ("Revised".equals(billType)) {
                    System.out.println("\"Revised\".equals(billType)");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billDsmDao.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedDsmBill");
                    List<Object[]> billPay2 = new ArrayList<>();
                    List<Object[]> billReceive2 = new ArrayList<>();
                    List<String[]> billPay11 = new ArrayList<String[]>();
                    List<String[]> billReceive11 = new ArrayList<String[]>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billDsmInfo = billDsmDao.BillDsmDetailsList(weekId, yearId, revisionNum);
                        System.out.println("billDsmInfo is " + billDsmInfo);
                        EntityDAO entDao = new EntityDAO();
                        if (billDsmInfo != null) {
                            System.out.println(" billDsmInfo size is " + billDsmInfo.size());
                            billPay2 = billPayDsmEntityDao.getDiffBillPay(weekId, yearId, revisionNum);
                            billReceive2 = billReceiveEntityDsmDao.getDiffBillReceive(weekId, yearId, revisionNum);
                            List<String> entityNameListPay = new LinkedList<String>();
                            List<String> entityNameListRecv = new LinkedList<String>();
                            List<String> entityTypeListPay = new LinkedList<String>();
                            List<String> entityTypeListRecv = new LinkedList<String>();
                            List<String> cappingChargesListPay = new LinkedList<String>();
                            List<String> cappingChargesListRecv = new LinkedList<String>();
                            List<String> additionalChargesListPay = new LinkedList<String>();
                            List<String> additionalChargesListRecv = new LinkedList<String>();
                            List<String> signChargesListPay = new LinkedList<String>();
                            List<String> signChargesListRecv = new LinkedList<String>();
                            List<String> payableChargesListPay = new LinkedList<String>();
                            List<String> receivableChargesListRecv = new LinkedList<String>();
                            List<String> netDsmListPay = new LinkedList<String>();
                            List<String> netDsmListRecv = new LinkedList<String>();
//                            List<String> remarksListPay = new LinkedList<String>();
//                            List<String> remarksListRecv = new LinkedList<String>();
                            if (billPay2 != null) {
                                System.out.println("billPay2 size is " + billPay2.size());
                                for (Object[] result : billPay2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String entityType = entDao.getEntityTypeById(entityId);
                                    String cappingCharges = (String) result[1].toString();
                                    String additionalCharges = (String) result[2].toString();
                                    String signCharges = (String) result[3].toString();
                                    String payableCharges = (String) result[4].toString();
                                    String netDsm = (String) result[5].toString();
//                                    String remarks = (String) result[5].toString();
                                    String recvCharges = (String) result[6].toString();
                                    System.out.println(" entityId is " + entityId + " cappingCharges " + cappingCharges + "  additionalCharges " + additionalCharges + " signCharges " + signCharges + " payableCharges is " + payableCharges + " netDsm is " + netDsm + " entityName is " + entityName + " entityType is " + entityType + "recvCharges IS " + recvCharges);
                                    String[] billPayable = new String[]{entityType, entityName, payableCharges, cappingCharges, additionalCharges, signCharges, netDsm, recvCharges};
                                    billPay11.add(billPayable);
                                    entityNameListPay.add(entityName);
                                    entityTypeListPay.add(entityType);
                                    cappingChargesListPay.add(cappingCharges);
                                    additionalChargesListPay.add(additionalCharges);
                                    signChargesListPay.add(signCharges);
                                    payableChargesListPay.add(payableCharges);
                                    netDsmListPay.add(netDsm);
                                    receivableChargesListRecv.add(recvCharges);
//                                    remarksListPay.add(remarks);
                                    mv3.addObject("entityNameListPay", entityNameListPay);
                                    mv3.addObject("entityTypeListPay", entityTypeListPay);
                                    mv3.addObject("cappingChargesListPay", cappingChargesListPay);
                                    mv3.addObject("additionalChargesListPay", additionalChargesListPay);
                                    mv3.addObject("signChargesListPay", signChargesListPay);
                                    mv3.addObject("payableChargesListPay", payableChargesListPay);
                                    mv3.addObject("netDsmListPay", netDsmListPay);
                                    mv3.addObject("receivableChargesListRecv", receivableChargesListRecv);
                                    mv3.addObject("billPay2", billPay2);
                                }
                            }
//                            if (billReceive2 != null) {
//                                System.out.println("billReceive2 size is " + billReceive2.size());
//                                for (Object[] result : billReceive2) {
//                                    BigDecimal entityId = (BigDecimal) result[0];
//                                    String entityName = entDao.getEntityNameById(entityId);
//                                    String entityType = entDao.getEntityTypeById(entityId);
//                                    String cappingCharges = (String) result[1].toString();
//                                    String additionalCharges = (String) result[2].toString();
//                                    String signCharges = (String) result[3].toString();
//                                    String recvCharges = (String) result[4].toString();
//                                    String netDsm = (String) result[5].toString();
//                                    String remarks = (String) result[5].toString();
//                                    System.out.println(" entityId is " + entityId + " cappingCharges " + cappingCharges + "  additionalCharges " + additionalCharges + " signCharges " + signCharges + " recvCharges is " + recvCharges + " netDsm is " + netDsm + " entityName is " + entityName + " entityType is " + entityType);
//                                    String[] billReceivable = new String[]{entityType, entityName, recvCharges, cappingCharges, additionalCharges, signCharges, netDsm};
//                                    billReceive11.add(billReceivable);
//                                    entityTypeListRecv.add(entityType);
//                                    entityNameListRecv.add(entityName);
//                                    cappingChargesListRecv.add(cappingCharges);
//                                    additionalChargesListRecv.add(additionalCharges);
//                                    signChargesListRecv.add(signCharges);
//                                    receivableChargesListRecv.add(recvCharges);
//                                    netDsmListRecv.add(netDsm);
////                                    remarksListRecv.add(remarks);
//                                    mv3.addObject("entityTypeListRecv", entityTypeListRecv);
//                                    mv3.addObject("entityNameListRecv", entityNameListRecv);
//                                    mv3.addObject("cappingChargesListRecv", cappingChargesListRecv);
//                                    mv3.addObject("additionalChargesListRecv", additionalChargesListRecv);
//                                    mv3.addObject("signChargesListRecv", signChargesListRecv);
//                                    mv3.addObject("receivableChargesListRecv", receivableChargesListRecv);
//                                    mv3.addObject("netDsmListRecv", netDsmListRecv);
////                                    mv3.addObject("remarksListRecv", remarksListRecv);
//                                    mv3.addObject("billReceive2", billReceive2);
//                                }
//                        }
                        }
//                        if (billPay2 != null || billReceive2 != null) {
                        if (billPay2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billDsmInfo", billDsmInfo);
//                mv3.addObject("billReceive11", billReceive11);
                    mv3.addObject("billPay11", billPay11);
                    return mv3;
                } else {
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billDsmDao.getMaxRevisionNum(weekId, yearId);
                    BigDecimal revNo = new BigDecimal(billType);
                    System.out.println(" revisionNum is " + revisionNum);
                    int res;
                    res = revNo.compareTo(revisionNum); // compare bg1 with bg2
                    System.out.println(" revNo is " + revNo);
                    if (res == 1) {
                        ModelAndView mv4 = new ModelAndView("successMsg");
                        String str2 = "Revision Bill R" + revNo + " not available for the selected parameters";
                        System.out.println(str2);
                        mv4.addObject("Msg", str2);
                        return mv4;
                    }
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billDsmInfo = billDsmDao.BillDsmDetailsList(weekId, yearId, revNo);
                        System.out.println("billDsmInfo is " + billDsmInfo);
                        if (billDsmInfo != null) {
                            System.out.println(" billDsmInfo size is " + billDsmInfo.size());
                            billPay = billPayDsmEntityDao.BillPayableCorpDsmlist(weekId, yearId, revNo);
//                        billReceive = billReceiveEntityDsmDao.BillReceiveEntityDsmList(weekId, yearId, revNo);
                            if (billPay != null) {
                                System.out.println("billPay size is " + billPay.size());
                            }
//                        if (billReceive != null) {
//                            System.out.println("billPay size is " + billReceive.size());
//                        }
                        }
//                    if (billPay != null || billReceive != null) {
                        if (billPay != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                }
                mv1.addObject("billDsmInfo", billDsmInfo);
                mv1.addObject("billPay", billPay);
//            mv1.addObject("billReceive", billReceive);
                return mv1;
            }
            if ("RRAS".equals(billCat)) {
                System.out.println("\"RRAS\".equals(billCat)");
                ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklyRrasBill");
                BillPayableEntityRrasDAO billPayRrasEntityDao = new BillPayableEntityRrasDAO();
                BillReceiveEntityRrasDAO billReceiveEntityRrasDao = new BillReceiveEntityRrasDAO();
                BillRrasDetailsDAO billRrasDao = new BillRrasDetailsDAO();
                List<BillPayableEntityRras> billPay = new ArrayList();
                List<BillPayableEntityRras> billPay1 = new ArrayList();
                List<BillReceiveEntityRras> billReceive = new ArrayList();
                List<BillReceiveEntityRras> billReceive1 = new ArrayList();
                List<BillRrasDetails> billRrasInfo = null;
                if ("Original".equals(billType)) {
                    billRrasInfo = billRrasDao.BillRrasDetailsList(weekId, yearId, BigDecimal.ZERO);
                    System.out.println("billRrasInfo is " + billRrasInfo);
                    if (billRrasInfo != null) {
                        System.out.println(" billRrasInfo size is " + billRrasInfo.size());
                        System.out.println("\"Original\".equals(billType)");
                        billPay = billPayRrasEntityDao.BillPayableCorpRraslist(weekId, yearId, BigDecimal.ZERO);
                        billReceive = billReceiveEntityRrasDao.BillReceiveEntityRrasList(weekId, yearId, BigDecimal.ZERO);
                        if (billPay != null) {
                            System.out.println("billPay size is " + billPay.size());
                            List<Object[]> sumListPay = billPayRrasEntityDao.getAllSumBillPayable(weekId, yearId, BigDecimal.ZERO);
                            System.out.println("######sumListPay size is " + sumListPay.size());
                            mv1.addObject("sumListPay", sumListPay);
                        }
                        if (billReceive != null) {
                            System.out.println("billReceive size is " + billReceive.size());
                            List<Object[]> sumListReceive = billReceiveEntityRrasDao.getAllSumBillReceive(weekId, yearId, BigDecimal.ZERO);
                            System.out.println("######sumListReceive size is " + sumListReceive.size());
                            mv1.addObject("sumListReceive", sumListReceive);
                        }
                    }
                    if (billPay != null || billReceive != null) {
                        System.out.println("Data is available!!");
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Data not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                } else if ("Revised".equals(billType)) {
                    System.out.println("\"Revised\".equals(billType)");
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedRrasBill");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billRrasDao.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    List<Object[]> billPay2 = new ArrayList<>();
                    List<Object[]> billReceive2 = new ArrayList<>();
                    List<String[]> billPay11 = new ArrayList<String[]>();
                    List<String[]> billReceive11 = new ArrayList<String[]>();
                    List<Object[]> sumListPay = new ArrayList<>();
                    List<Object[]> sumListReceive = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billRrasInfo = billRrasDao.BillRrasDetailsList(weekId, yearId, revisionNum);
                        System.out.println("billRrasInfo is " + billRrasInfo);
                        if (billRrasInfo != null) {
                            System.out.println(" billRrasInfo size is " + billRrasInfo.size());
                            billPay2 = billPayRrasEntityDao.getDiffBillPay(weekId, yearId, revisionNum);
                            billReceive2 = billReceiveEntityRrasDao.getDiffBillReceive(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
//                              
                            if (billPay2 != null && !(billPay2.isEmpty())) {
                                System.out.println("billPay2 Rras size is " + billPay2.size());
                                for (Object[] result : billPay2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String entityType = entDao.getEntityTypeById(entityId);
                                    String energyVae = (String) result[1].toString();
                                    String varCharg = (String) result[2].toString();
                                    String netRras = (String) result[3].toString();
                                    System.out.println(" entityId is " + entityId + " energyVae " + energyVae + "  varCharg " + varCharg + " netRras " + netRras + " entityName is " + entityName + " entityType is " + entityType);
                                    String[] billPayable = new String[]{entityName, entityType, energyVae, varCharg, netRras};
                                    billPay11.add(billPayable);
                                }
                                List<BigDecimal> sumListPayDiff = new ArrayList<>();
                                sumListPay = billPayRrasEntityDao.getAllSumBillPayable(weekId, yearId, revisionNum);
                                List<Object[]> sumListPay1 = billPayRrasEntityDao.getAllSumBillPayable(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                for (Object[] result : sumListPay) {
                                    BigDecimal SUM_ENERGY_VAE = (BigDecimal) result[0];
                                    BigDecimal SUM_VARIABLE_CHARGES = (BigDecimal) result[1];
                                    BigDecimal SUM_NET_RRAS = (BigDecimal) result[2];
                                    System.out.println("SUM_ENERGY_VAE is " + SUM_ENERGY_VAE + " SUM_VARIABLE_CHARGES " + SUM_VARIABLE_CHARGES + " SUM_NET_RRAS " + SUM_NET_RRAS);
                                    for (Object[] result1 : sumListPay1) {
                                        BigDecimal SUM_ENERGY_VAE1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_VARIABLE_CHARGES1 = (BigDecimal) result1[1];
                                        BigDecimal SUM_NET_RRAS1 = (BigDecimal) result1[2];
                                        System.out.println("SUM_ENERGY_VAE1 is " + SUM_ENERGY_VAE1 + " SUM_VARIABLE_CHARGES1 " + SUM_VARIABLE_CHARGES1 + " SUM_NET_RRAS1 " + SUM_NET_RRAS1);
                                        BigDecimal DIFF_SUM_ENERGY_VAE = SUM_ENERGY_VAE.subtract(SUM_ENERGY_VAE1);
                                        BigDecimal DIFF_SUM_VARIABLE_CHARGES = SUM_VARIABLE_CHARGES.subtract(SUM_VARIABLE_CHARGES1);
                                        BigDecimal DIFF_SUM_NET_RRAS = SUM_NET_RRAS.subtract(SUM_NET_RRAS1);
                                        sumListPayDiff.add(DIFF_SUM_ENERGY_VAE);
                                        sumListPayDiff.add(DIFF_SUM_VARIABLE_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_NET_RRAS);
                                        System.out.println("DIFF_SUM_NET_RRAS is " + DIFF_SUM_NET_RRAS);
                                    }
                                }
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                System.out.println("######sumListPay1 size is " + sumListPay1.size());
                                mv3.addObject("sumListPayDiff", sumListPayDiff);
                                mv3.addObject("sumListPay1", sumListPay1);
                            }
                            if (billReceive2 != null && !(billReceive2.isEmpty())) {
                                System.out.println("billReceive2 Rras size is " + billReceive2.size());
                                List<BigDecimal> sumListRecvDiff = new ArrayList<>();
                                for (Object[] result : billReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String entityType = entDao.getEntityTypeById(entityId);
                                    String energyVae = (String) result[1].toString();
                                    String fixedCharg = (String) result[2].toString();
                                    String varCharg = (String) result[3].toString();
                                    String markUpChrg = (String) result[4].toString();
                                    String netRras = (String) result[5].toString();
                                    System.out.println("entityId is " + entityId + " energyVae " + energyVae + " fixedCharg " + fixedCharg + " varCharg " + varCharg + " markUpChrg " + markUpChrg + " netRras " + netRras);
                                    String[] billReceivable = new String[]{entityName, entityType, energyVae, fixedCharg, varCharg, markUpChrg, netRras};
                                    billReceive11.add(billReceivable);
                                }
                                sumListReceive = billReceiveEntityRrasDao.getAllSumBillReceive(weekId, yearId, revisionNum);
                                System.out.println("######sumListReceive size is " + sumListReceive.size());
                                List<Object[]> sumListReceive1 = billReceiveEntityRrasDao.getAllSumBillReceive(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                System.out.println("######sumListReceive1 size is " + sumListReceive1.size());
                                for (Object[] result : sumListReceive) {
                                    BigDecimal SUM_ENERGY_VAE = (BigDecimal) result[0];
                                    BigDecimal SUM_FIXED_CHARGES = (BigDecimal) result[1];
                                    BigDecimal SUM_VARIABLE_CHARGES = (BigDecimal) result[2];
                                    BigDecimal SUM_MARKUP_CHARGES = (BigDecimal) result[3];
                                    BigDecimal SUM_NET_RRAS = (BigDecimal) result[4];
                                    System.out.println("SUM_ENERGY_VAE is " + SUM_ENERGY_VAE + " SUM_FIXED_CHARGES " + SUM_FIXED_CHARGES + " SUM_VARIABLE_CHARGES " + SUM_VARIABLE_CHARGES + "  SUM_MARKUP_CHARGES " + SUM_MARKUP_CHARGES + " SUM_NET_RRAS " + SUM_NET_RRAS);
                                    for (Object[] result1 : sumListReceive1) {
                                        BigDecimal SUM_ENERGY_VAE1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_FIXED_CHARGES1 = (BigDecimal) result1[1];
                                        BigDecimal SUM_VARIABLE_CHARGES1 = (BigDecimal) result1[2];
                                        BigDecimal SUM_MARKUP_CHARGES1 = (BigDecimal) result1[3];
                                        BigDecimal SUM_NET_RRAS1 = (BigDecimal) result1[4];
                                        System.out.println("SUM_ENERGY_VAE1 is " + SUM_ENERGY_VAE1 + " SUM_FIXED_CHARGES1 " + SUM_FIXED_CHARGES1 + " SUM_VARIABLE_CHARGES1 " + SUM_VARIABLE_CHARGES1 + "  SUM_MARKUP_CHARGES1 " + SUM_MARKUP_CHARGES1 + " SUM_NET_RRAS1 " + SUM_NET_RRAS1);
                                        BigDecimal DIFF_SUM_ENERGY_VAE = SUM_ENERGY_VAE.subtract(SUM_ENERGY_VAE1);
                                        BigDecimal DIFF_SUM_FIXED_CHARGES = SUM_FIXED_CHARGES.subtract(SUM_FIXED_CHARGES1);
                                        BigDecimal DIFF_SUM_VARIABLE_CHARGES = SUM_VARIABLE_CHARGES.subtract(SUM_VARIABLE_CHARGES1);
                                        BigDecimal DIFF_SUM_MARKUP_CHARGES = SUM_MARKUP_CHARGES.subtract(SUM_MARKUP_CHARGES1);
                                        BigDecimal DIFF_SUM_NET_RRAS = SUM_NET_RRAS.subtract(SUM_NET_RRAS1);
                                        sumListRecvDiff.add(DIFF_SUM_ENERGY_VAE);
                                        sumListRecvDiff.add(DIFF_SUM_FIXED_CHARGES);
                                        sumListRecvDiff.add(DIFF_SUM_VARIABLE_CHARGES);
                                        sumListRecvDiff.add(DIFF_SUM_MARKUP_CHARGES);
                                        sumListRecvDiff.add(DIFF_SUM_NET_RRAS);
                                        System.out.println("DIFF_SUM_NET_RRAS is " + DIFF_SUM_NET_RRAS);
                                    }
                                }
                                mv3.addObject("sumListRecvDiff", sumListRecvDiff);
                            }
                        }
                        if (billPay2 != null || billReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billRrasInfo", billRrasInfo);
                    mv3.addObject("billReceive11", billReceive11);
                    mv3.addObject("billPay11", billPay11);
                    return mv3;
                } else {
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billRrasDao.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    BigDecimal revNo = new BigDecimal(billType);
                    System.out.println(" revNo is " + revNo);
                    int res;
                    res = revNo.compareTo(revisionNum); // compare bg1 with bg2
                    if (res == 1) {
                        ModelAndView mv4 = new ModelAndView("successMsg");
                        String str2 = "Revision Bill R" + revNo + " not available for the selected parameters";
                        System.out.println(str2);
                        mv4.addObject("Msg", str2);
                        return mv4;
                    }
                    List<Object[]> billPay2 = new ArrayList<>();
                    List<Object[]> billReceive2 = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billRrasInfo = billRrasDao.BillRrasDetailsList(weekId, yearId, revisionNum);
                        System.out.println("billDsmInfo is " + billRrasInfo);
                        if (billRrasInfo != null) {
                            System.out.println(" billDsmInfo size is " + billRrasInfo.size());
                            billPay = billPayRrasEntityDao.BillPayableCorpRraslist(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
                            billReceive = billReceiveEntityRrasDao.BillReceiveEntityRrasList(weekId, yearId, revisionNum);
                            if (billPay != null) {
                                System.out.println("billPay size is " + billPay.size());
                                List<Object[]> sumListPay = billPayRrasEntityDao.getAllSumBillPayable(weekId, yearId, revisionNum);
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                mv1.addObject("sumListPay", sumListPay);
                            }
                            if (billReceive != null) {
                                System.out.println("billReceive2 size is " + billReceive.size());
                                List<Object[]> sumListReceive = billReceiveEntityRrasDao.getAllSumBillReceive(weekId, yearId, revisionNum);
                                System.out.println("######sumListReceive size is " + sumListReceive.size());
                                mv1.addObject("sumListReceive", sumListReceive);
                            }
                        }
                        if (billPay2 != null || billReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                }
                mv1.addObject("billRrasInfo", billRrasInfo);
                mv1.addObject("billPay", billPay);
                mv1.addObject("billReceive", billReceive);
                return mv1;
            }
            if ("AGC".equals(billCat)) {
                System.out.println("\"AGC\".equals(billCat)");
                ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklyAgcBill");
                BillEntityAgcDAO billEntityAgcDAO = new BillEntityAgcDAO();
                BillAgcDetailsDAO billAgcDao = new BillAgcDetailsDAO();
                List<BillEntityAgc> billPayRecv = new ArrayList();
                List<BillAgcDetails> billAgcInfo = new ArrayList();
                if ("Original".equals(billType)) {
                    billAgcInfo = billAgcDao.BillAgcDetailsList(weekId, yearId, BigDecimal.ZERO);
                    System.out.println("billAgcInfo is " + billAgcInfo);
                    if (billAgcInfo != null) {
                        System.out.println(" billAgcInfo size is " + billAgcInfo.size());
                        System.out.println("\"Original\".equals(billType)");
                        billPayRecv = billEntityAgcDAO.BillCorpAgclist(weekId, yearId, BigDecimal.ZERO);
                        if (billPayRecv != null) {
                            System.out.println("billPayRecv size is " + billPayRecv.size());
                            List<Object[]> sumListPayRecv = billEntityAgcDAO.getAllSumBillPayable(weekId, yearId, BigDecimal.ZERO);
                            System.out.println("######sumListPayRecv size is " + sumListPayRecv.size());
                            mv1.addObject("sumListPayRecv", sumListPayRecv);
                            System.out.println("Data is available!!");
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Data not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                } else if ("Revised".equals(billType)) {
                    System.out.println("\"Revised\".equals(billType)");
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedAgcBill");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billAgcDao.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    List<Object[]> billPayReceive2 = new ArrayList<>();
                    List<String[]> billPayReceive11 = new ArrayList<String[]>();
                    List<Object[]> sumListPay = new ArrayList<>();
                    List<Object[]> sumListReceive = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billAgcInfo = billAgcDao.BillAgcDetailsList(weekId, yearId, revisionNum);
                        System.out.println("billAgcInfo is " + billAgcInfo);
                        if (billAgcInfo != null) {
                            System.out.println(" billAgcInfo size is " + billAgcInfo.size());
                            billPayReceive2 = billEntityAgcDAO.getDiffBillPay(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
//                              
                            if (billPayReceive2 != null && !(billPayReceive2.isEmpty())) {
                                System.out.println("billPayReceive2 AGC size is " + billPayReceive2.size());
                                for (Object[] result : billPayReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    //String entityType = entDao.getEntityTypeById(entityId);
                                    String totalAGCUpDownMwh = (String) result[1].toString();
                                    String markUpCharges = (String) result[2].toString();
                                    String totalNetAgc = (String) result[3].toString();
                                    String agcEnergyCharges = (String) result[4].toString();
                                    String totalPoolCharges = (String) result[5].toString();
                                    System.out.println(" entityName is " + entityName + " totalAGCUpDownMwh " + totalAGCUpDownMwh + "  markUpCharges " + markUpCharges + " totalNetAgc " + totalNetAgc + " agcEnergyCharges is " + agcEnergyCharges + " totalPoolCharges " + totalPoolCharges);
                                    String[] billPayRecvable = new String[]{entityName, totalAGCUpDownMwh, markUpCharges, totalNetAgc, agcEnergyCharges, totalPoolCharges};
                                    billPayReceive11.add(billPayRecvable);
                                }
                                List<BigDecimal> sumListPayDiff = new ArrayList<>();
                                sumListPay = billEntityAgcDAO.getAllSumBillPayable(weekId, yearId, revisionNum);
                                List<Object[]> sumListPay1 = billEntityAgcDAO.getAllSumBillPayable(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                for (Object[] result : sumListPay) {
                                    BigDecimal SUM_TOTAL_AGC_UPDOWN_MWH = (BigDecimal) result[0];
                                    BigDecimal SUM_MARKUP_CHARGES = (BigDecimal) result[1];
                                    BigDecimal SUM_TOTALNET_AGC = (BigDecimal) result[2];
                                    BigDecimal SUM_AGC_ENERGYCHARGES = (BigDecimal) result[3];
                                    BigDecimal SUM_TOTALCHARGES = (BigDecimal) result[4];
                                    System.out.println("SUM_TOTAL_AGC_UPDOWN_MWH is " + SUM_TOTAL_AGC_UPDOWN_MWH + " SUM_MARKUP_CHARGES " + SUM_MARKUP_CHARGES + " SUM_TOTALNET_AGC " + SUM_TOTALNET_AGC + " SUM_AGC_ENERGYCHARGES " + SUM_AGC_ENERGYCHARGES + " SUM_TOTALCHARGES " + SUM_TOTALCHARGES);
                                    for (Object[] result1 : sumListPay1) {
                                        BigDecimal SUM_TOTAL_AGC_UPDOWN_MWH1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_MARKUP_CHARGES1 = (BigDecimal) result1[1];
                                        BigDecimal SUM_TOTALNET_AGC1 = (BigDecimal) result1[2];
                                        BigDecimal SUM_AGC_ENERGYCHARGES1 = (BigDecimal) result1[3];
                                        BigDecimal SUM_TOTALCHARGES1 = (BigDecimal) result1[4];
                                        System.out.println("SUM_TOTAL_AGC_UPDOWN_MWH1 is " + SUM_TOTAL_AGC_UPDOWN_MWH1 + " SUM_MARKUP_CHARGES1 " + SUM_MARKUP_CHARGES1 + " SUM_TOTALNET_AGC1 " + SUM_TOTALNET_AGC1 + " SUM_AGC_ENERGYCHARGES1 " + SUM_AGC_ENERGYCHARGES1 + " SUM_TOTALCHARGES1 " + SUM_TOTALCHARGES1);
                                        BigDecimal DIFF_SUM_TOTAL_AGC_UPDOWN_MWH = SUM_TOTAL_AGC_UPDOWN_MWH.subtract(SUM_TOTAL_AGC_UPDOWN_MWH1);
                                        BigDecimal DIFF_SUM_MARKUP_CHARGES = SUM_MARKUP_CHARGES.subtract(SUM_MARKUP_CHARGES1);
                                        BigDecimal DIFF_SUM_TOTALNET_AGC = SUM_TOTALNET_AGC.subtract(SUM_TOTALNET_AGC1);
                                        BigDecimal DIFF_SUM_AGC_ENERGYCHARGES = SUM_AGC_ENERGYCHARGES.subtract(SUM_AGC_ENERGYCHARGES1);
                                        BigDecimal DIFF_SUM_TOTALCHARGES = SUM_TOTALCHARGES.subtract(SUM_TOTALCHARGES1);
                                        sumListPayDiff.add(DIFF_SUM_TOTAL_AGC_UPDOWN_MWH);
                                        sumListPayDiff.add(DIFF_SUM_MARKUP_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_TOTALNET_AGC);
                                        sumListPayDiff.add(DIFF_SUM_AGC_ENERGYCHARGES);
                                        sumListPayDiff.add(DIFF_SUM_TOTALCHARGES);
                                        System.out.println("DIFF_SUM_TOTALCHARGES is " + DIFF_SUM_TOTALCHARGES);
                                    }
                                }
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                System.out.println("######sumListPay1 size is " + sumListPay1.size());
                                mv3.addObject("sumListPayDiff", sumListPayDiff);
                                mv3.addObject("sumListPay1", sumListPay1);
                            }
                        }
                        if (billPayReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billAgcInfo", billAgcInfo);
                    mv3.addObject("billPayReceive11", billPayReceive11);
                    return mv3;
                } else {
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billAgcDao.getMaxRevisionNum(weekId, yearId);
                    BigDecimal revNo = new BigDecimal(billType);
                    System.out.println(" revisionNum is " + revisionNum);
                    int res;
                    res = revNo.compareTo(revisionNum); // compare bg1 with bg2
                    System.out.println(" revNo is " + revNo);
                    if (res == 1) {
                        ModelAndView mv4 = new ModelAndView("successMsg");
                        String str2 = "Revision Bill R" + revNo + " not available for the selected parameters";
                        System.out.println(str2);
                        mv4.addObject("Msg", str2);
                        return mv4;
                    }
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billAgcInfo = billAgcDao.BillAgcDetailsList(weekId, yearId, revNo);
                        System.out.println("billAgcInfo is " + billAgcInfo);
                        if (billAgcInfo != null) {
                            System.out.println(" billAgcInfo size is " + billAgcInfo.size());
                            billPayRecv = billEntityAgcDAO.BillCorpAgclist(weekId, yearId, revNo);
                            if (billPayRecv != null) {
                                System.out.println("billPayReceive size is " + billPayRecv.size());
                                List<Object[]> sumListPayRecv = billEntityAgcDAO.getAllSumBillPayable(weekId, yearId, revNo);
                                System.out.println("######sumListPayRecv size is " + sumListPayRecv.size());
                                mv1.addObject("sumListPayRecv", sumListPayRecv);
                                System.out.println("Data is available!!");
                            } else {
                                ModelAndView mv2 = new ModelAndView("successMsg");
                                String Msg = "Data not available for the selected parameters!!";
                                mv2.addObject("Msg", Msg);
                                return mv2;
                            }
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                }
                mv1.addObject("billAgcInfo", billAgcInfo);
                mv1.addObject("billPayRecv", billPayRecv);
                return mv1;
            }
            if ("FRAS".equals(billCat)) {
                System.out.println("\"FRAS\".equals(billCat)");
                ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklyFrasBill");
                BillEntityFrasDAO billEntityFrasDAO = new BillEntityFrasDAO();
                BillFrasDetailsDAO billFrasDao = new BillFrasDetailsDAO();
                List<BillEntityFras> billPayRecv = new ArrayList();
                List<BillFrasDetails> billFrasInfo = new ArrayList();
                if ("Original".equals(billType)) {
                    billFrasInfo = billFrasDao.BillFrasDetailsList(weekId, yearId, BigDecimal.ZERO);
                    System.out.println("billFrasInfo is " + billFrasInfo);
                    if (billFrasInfo != null) {
                        System.out.println(" billFrasInfo size is " + billFrasInfo.size());
                        System.out.println("\"Original\".equals(billType)");
                        billPayRecv = billEntityFrasDAO.BillCorpFraslist(weekId, yearId, BigDecimal.ZERO);
                        if (billPayRecv != null) {
                            System.out.println("billPayRecv size is " + billPayRecv.size());
                            List<Object[]> sumListPayRecv = billEntityFrasDAO.getAllSumBill(weekId, yearId, BigDecimal.ZERO);
                            System.out.println("######sumListPayRecv size is " + sumListPayRecv.size());
                            mv1.addObject("sumListPayRecv", sumListPayRecv);
//                            mv1.addObject("billPayRecv", billPayRecv);
                            System.out.println("Data is available!!");
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Data not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                } else if ("Revised".equals(billType)) {
                    System.out.println("\"Revised\".equals(billType)");
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedFrasBill");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billFrasDao.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    List<Object[]> billPayReceive2 = new ArrayList<>();
                    List<String[]> billPayReceive11 = new ArrayList<String[]>();
                    List<Object[]> sumListPay = new ArrayList<>();
                    List<Object[]> sumListReceive = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billFrasInfo = billFrasDao.BillFrasDetailsList(weekId, yearId, revisionNum);
                        System.out.println("billFrasInfo is " + billFrasInfo);
                        if (billFrasInfo != null) {
                            System.out.println(" billAgcInfo size is " + billFrasInfo.size());
                            billPayReceive2 = billEntityFrasDAO.getDiffBillPay(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
//                              
                            if (billPayReceive2 != null && !(billPayReceive2.isEmpty())) {
                                System.out.println("billPayReceive2 AGC size is " + billPayReceive2.size());
                                for (Object[] result : billPayReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String upRegulation = (String) result[1].toString();
                                    String markUpCharges = (String) result[3].toString();
                                    String downRegulation = (String) result[2].toString();
                                    System.out.println(" entityName is " + entityName + " upRegulation " + upRegulation + "  markUpCharges " + markUpCharges + " downRegulation " + downRegulation);
                                    String[] billPayRecvable = new String[]{entityName, upRegulation, downRegulation, markUpCharges};
                                    billPayReceive11.add(billPayRecvable);
                                }
                                List<BigDecimal> sumListPayDiff = new ArrayList<>();
                                sumListPay = billEntityFrasDAO.getAllSumBill(weekId, yearId, revisionNum);
                                List<Object[]> sumListPay1 = billEntityFrasDAO.getAllSumBill(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                for (Object[] result : sumListPay) {
                                    BigDecimal SUM_UP_REGULATION = (BigDecimal) result[0];
                                    BigDecimal SUM_DOWN_REGULATION = (BigDecimal) result[1];
                                    BigDecimal SUM_MARKUP_CHARGES = (BigDecimal) result[2];
                                    System.out.println("SUM_UP_REGULATION is " + SUM_UP_REGULATION + " SUM_MARKUP_CHARGES " + SUM_MARKUP_CHARGES + " SUM_DOWN_REGULATION " + SUM_DOWN_REGULATION);
                                    for (Object[] result1 : sumListPay1) {
                                        BigDecimal SUM_UP_REGULATION1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_MARKUP_CHARGES1 = (BigDecimal) result1[2];
                                        BigDecimal SUM_DOWN_REGULATION1 = (BigDecimal) result1[1];
                                        System.out.println("SUM_UP_REGULATION1 is " + SUM_UP_REGULATION1 + " SUM_MARKUP_CHARGES1 " + SUM_MARKUP_CHARGES1 + " SUM_DOWN_REGULATION1 " + SUM_DOWN_REGULATION1);
                                        BigDecimal DIFF_SUM_UP_REGULATION = SUM_UP_REGULATION.subtract(SUM_UP_REGULATION1);
                                        BigDecimal DIFF_SUM_MARKUP_CHARGES = SUM_MARKUP_CHARGES.subtract(SUM_MARKUP_CHARGES1);
                                        BigDecimal DIFF_SUM_DOWN_REGULATION = SUM_DOWN_REGULATION.subtract(SUM_DOWN_REGULATION1);
                                        sumListPayDiff.add(DIFF_SUM_UP_REGULATION);
                                        sumListPayDiff.add(DIFF_SUM_DOWN_REGULATION);
                                        sumListPayDiff.add(DIFF_SUM_MARKUP_CHARGES);
                                        System.out.println("DIFF_SUM_TOTALCHARGES is " + DIFF_SUM_MARKUP_CHARGES);
                                    }
                                }
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                System.out.println("######sumListPay1 size is " + sumListPay1.size());
                                mv3.addObject("sumListPayDiff", sumListPayDiff);
                                mv3.addObject("sumListPay1", sumListPay1);
                            }
                        }
                        if (billPayReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billFrasInfo", billFrasInfo);
                    mv3.addObject("billPayReceive11", billPayReceive11);
                    return mv3;
                } else {
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billFrasDao.getMaxRevisionNum(weekId, yearId);
                    BigDecimal revNo = new BigDecimal(billType);
                    System.out.println(" revisionNum is " + revisionNum);
                    int res;
                    res = revNo.compareTo(revisionNum); // compare bg1 with bg2
                    System.out.println(" revNo is " + revNo);
                    if (res == 1) {
                        ModelAndView mv4 = new ModelAndView("successMsg");
                        String str2 = "Revision Bill R" + revNo + " not available for the selected parameters";
                        System.out.println(str2);
                        mv4.addObject("Msg", str2);
                        return mv4;
                    }
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billFrasInfo = billFrasDao.BillFrasDetailsList(weekId, yearId, revNo);
                        System.out.println("billAgcInfo is " + billFrasInfo);
                        if (billFrasInfo != null) {
                            System.out.println(" billAgcInfo size is " + billFrasInfo.size());
                            billPayRecv = billEntityFrasDAO.BillCorpFraslist(weekId, yearId, revNo);
                            if (billPayRecv != null) {
                                System.out.println("billPayReceive size is " + billPayRecv.size());
                                List<Object[]> sumListPayRecv = billEntityFrasDAO.getAllSumBill(weekId, yearId, revNo);
                                System.out.println("######sumListPayRecv size is " + sumListPayRecv.size());
                                mv1.addObject("sumListPayRecv", sumListPayRecv);
                                System.out.println("Data is available!!");
                            } else {
                                ModelAndView mv2 = new ModelAndView("successMsg");
                                String Msg = "Data not available for the selected parameters!!";
                                mv2.addObject("Msg", Msg);
                                return mv2;
                            }
                        }
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                }
                mv1.addObject("billFrasInfo", billFrasInfo);
                mv1.addObject("billPayRecv", billPayRecv);
                return mv1;
            }
        }
        return mv;
    }

    public ModelAndView weeklyBill123(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/weeklyBill123");
        String bName = request.getParameter("bName");
        System.out.println("bName is " + bName);
        if (bName != null) {
            System.out.println("bName NOT NULL & is " + bName);
            String week = request.getParameter("week");
            String year = request.getParameter("year");
            String billCat = request.getParameter("billCat");
            String revNo = request.getParameter("revNo");
            System.out.println("week is " + week);
            System.out.println("year is " + year);
            System.out.println("billCat is " + billCat);
            System.out.println("revNo is " + revNo);
            BigDecimal weekId = new BigDecimal(week);
            BigDecimal yearId = new BigDecimal(year);
            //BigDecimal revNum = new BigDecimal(revNo);
            if ("SRAS".equals(billCat)) {
                System.out.println("\"SRAS\".equals(billCat)");

                BillEntityAgcDAO billEntityAGCDAO = new BillEntityAgcDAO();
                BillAgcDetailsDAO billAgcDetailsDAO = new BillAgcDetailsDAO();
                List<BillSrasDetails> billSRASInfo = new ArrayList();
                if ("Revised".equals(revNo)) {
                    System.out.println("\"Revised\".equals(revNo)");
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedSRASBill");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billAgcDetailsDAO.getMaxRevisionNumSRAS(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    List<Object[]> billPayReceive2 = new ArrayList<>();
                    List<String[]> billPayReceive11 = new ArrayList<String[]>();
                    List<Object[]> sumListPay = new ArrayList<>();
                    List<Object[]> sumListReceive = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billSRASInfo = billAgcDetailsDAO.getBillDetailsSRAS(weekId, yearId, revisionNum);
                        System.out.println("billSRASInfo is " + billSRASInfo);
                        if (billSRASInfo != null) {
                            System.out.println(" billSRASInfo size is " + billSRASInfo.size());
                            billPayReceive2 = billEntityAGCDAO.getDiffBillPaySRAS(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
//                              
                            if (billPayReceive2 != null && !(billPayReceive2.isEmpty())) {
                                System.out.println("billPayReceive2 TrasE size is " + billPayReceive2.size());
                                for (Object[] result : billPayReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String totalAgcUpdownMwh = (String) result[1].toString();
                                    String totalAgcDownMwh = (String) result[2].toString();
                                    String markupCharges = (String) result[3].toString();
                                    String totalnetAgc = (String) result[4].toString();
                                    String agcEnergycharges = (String) result[5].toString();
                                    String totalcharges = (String) result[6].toString();

                                    System.out.println(" entityName is " + entityName + " totalAgcUpdownMwh " + totalAgcUpdownMwh + "  totalAgcDownMwh " + totalAgcDownMwh + " markupCharges " + markupCharges + " totalnetAgc " + totalnetAgc + " agcEnergycharges " + agcEnergycharges + " totalcharges " + totalcharges);
                                    String[] billPayRecvable = new String[]{entityName, totalAgcUpdownMwh, totalAgcDownMwh, markupCharges, totalnetAgc, agcEnergycharges, totalcharges};
                                    billPayReceive11.add(billPayRecvable);
                                }
                                List<BigDecimal> sumListPayDiff = new ArrayList<>();
                                sumListPay = billEntityAGCDAO.getAllSumBillSRAS(weekId, yearId, revisionNum);
                                List<Object[]> sumListPay1 = billEntityAGCDAO.getAllSumBillSRAS(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                for (Object[] result : sumListPay) {
                                    BigDecimal SUM_TOTAL_AGC_UPDOWN_MWH = (BigDecimal) result[0];
                                    BigDecimal SUM_TOTAL_AGC_DOWN_MWH = (BigDecimal) result[1];
                                    BigDecimal SUM_MARKUP_CHARGES = (BigDecimal) result[2];
                                    BigDecimal SUM_TOTALNET_AGC = (BigDecimal) result[3];
                                    BigDecimal SUM_AGC_ENERGYCHARGES = (BigDecimal) result[4];
                                    BigDecimal SUM_TOTALCHARGES = (BigDecimal) result[5];
                                    System.out.println("SUM_TOTAL_AGC_UPDOWN_MWH is " + SUM_TOTAL_AGC_UPDOWN_MWH + " SUM_TOTAL_AGC_DOWN_MWH " + SUM_TOTAL_AGC_DOWN_MWH + " SUM_MARKUP_CHARGES " + SUM_MARKUP_CHARGES + " SUM_TOTALNET_AGC " + SUM_TOTALNET_AGC + " SUM_AGC_ENERGYCHARGES " + SUM_AGC_ENERGYCHARGES + " SUM_TOTALCHARGES " + SUM_TOTALCHARGES);
                                    for (Object[] result1 : sumListPay1) {
                                        BigDecimal SUM_TOTAL_AGC_UPDOWN_MWH1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_TOTAL_AGC_DOWN_MWH1 = (BigDecimal) result1[1];
                                        BigDecimal SUM_MARKUP_CHARGES1 = (BigDecimal) result1[2];
                                        BigDecimal SUM_TOTALNET_AGC1 = (BigDecimal) result1[3];
                                        BigDecimal SUM_AGC_ENERGYCHARGES1 = (BigDecimal) result1[4];
                                        BigDecimal SUM_TOTALCHARGES1 = (BigDecimal) result1[5];
                                         System.out.println("SUM_TOTAL_AGC_UPDOWN_MWH1 is " + SUM_TOTAL_AGC_UPDOWN_MWH1 + " SUM_TOTAL_AGC_DOWN_MWH1 " + SUM_TOTAL_AGC_DOWN_MWH1 + " SUM_MARKUP_CHARGES1 " + SUM_MARKUP_CHARGES1 + " SUM_TOTALNET_AGC1 " + SUM_TOTALNET_AGC1 + " SUM_AGC_ENERGYCHARGES1 " + SUM_AGC_ENERGYCHARGES1 + " SUM_TOTALCHARGES1 " + SUM_TOTALCHARGES1);
                                        BigDecimal DIFF_SUM_TOTAL_AGC_UPDOWN_MWH = SUM_TOTAL_AGC_UPDOWN_MWH.subtract(SUM_TOTAL_AGC_UPDOWN_MWH1);
                                        BigDecimal DIFF_SUM_TOTAL_AGC_DOWN_MWH = SUM_TOTAL_AGC_DOWN_MWH.subtract(SUM_TOTAL_AGC_DOWN_MWH1);
                                        BigDecimal DIFF_SUM_MARKUP_CHARGES = SUM_MARKUP_CHARGES.subtract(SUM_MARKUP_CHARGES);
                                        BigDecimal DIFF_SUM_TOTALNET_AGC = SUM_TOTALNET_AGC.subtract(SUM_TOTALNET_AGC1);
                                        BigDecimal DIFF_SUM_AGC_ENERGYCHARGES = SUM_AGC_ENERGYCHARGES.subtract(SUM_AGC_ENERGYCHARGES1);
                                        BigDecimal DIFF_SUM_TOTALCHARGES = SUM_TOTALCHARGES.subtract(SUM_TOTALCHARGES1);
                                        sumListPayDiff.add(DIFF_SUM_TOTAL_AGC_UPDOWN_MWH);
                                        sumListPayDiff.add(DIFF_SUM_TOTAL_AGC_DOWN_MWH);
                                        sumListPayDiff.add(DIFF_SUM_MARKUP_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_TOTALNET_AGC);
                                        sumListPayDiff.add(DIFF_SUM_AGC_ENERGYCHARGES);
                                        sumListPayDiff.add(DIFF_SUM_TOTALCHARGES);
                                        System.out.println("DIFF_SUM_TOTAL_AGC_UPDOWN_MWH is " + DIFF_SUM_TOTAL_AGC_UPDOWN_MWH);
                                    }
                                }
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                System.out.println("######sumListPay1 size is " + sumListPay1.size());
                                mv3.addObject("sumListPayDiff", sumListPayDiff);
                                mv3.addObject("sumListPay1", sumListPay1);
                            }
                        }
                        if (billPayReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }

                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billSRASInfo", billSRASInfo);
                    mv3.addObject("billPayReceive11", billPayReceive11);
                    return mv3;

                } else {
                    BigDecimal revNum = new BigDecimal(revNo);
                    List<BillSrasDetails> billsrasdet = billAgcDetailsDAO.getBillDetailsSRAS(weekId, yearId, revNum);

                    if (billsrasdet != null) {
                        BillEntityAgcDAO billEntityAgcDAO = new BillEntityAgcDAO();
                        List<BillEntitySras> BillEnt = billEntityAgcDAO.getBillEntitySras(weekId, yearId, revNum);
                        ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklySRASBill");

                        mv1.addObject("billsrasdet", billsrasdet);
                        mv1.addObject("BillEnt", BillEnt);
                        return mv1;
                    } else {
                        ModelAndView mv1 = new ModelAndView("successMsg");
                        String Msg = "bill is not available!!";
                        mv1.addObject("Msg", Msg);
                        return mv1;
                    }
                }
            }
            if ("TRASM".equals(billCat)) {
                System.out.println("\"TRASM\".equals(billCat)");

                BillTRASMDetailsDAO billTRASMDetailsDAO = new BillTRASMDetailsDAO();

                List<BillTrasMDetails> billTRASMInfo = new ArrayList();
                BillEntityTRASMDAO billEntityTrasMDAO = new BillEntityTRASMDAO();
                if ("Revised".equals(revNo)) {
                    System.out.println("\"Revised\".equals(revNo)");
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedTrasMBill");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billTRASMDetailsDAO.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    List<Object[]> billPayReceive2 = new ArrayList<>();
                    List<String[]> billPayReceive11 = new ArrayList<String[]>();
                    List<Object[]> sumListPay = new ArrayList<>();
                    List<Object[]> sumListReceive = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billTRASMInfo = billTRASMDetailsDAO.getBillDetailsTrasM(weekId, yearId, revisionNum);
                        System.out.println("billTRASMInfo is " + billTRASMInfo);
                        if (billTRASMInfo != null) {
                            System.out.println(" billTRASMInfo size is " + billTRASMInfo.size());
                            billPayReceive2 = billEntityTrasMDAO.getDiffBillPay(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
//                              
                            if (billPayReceive2 != null && !(billPayReceive2.isEmpty())) {
                                System.out.println("billPayReceive2 TrasE size is " + billPayReceive2.size());
                                for (Object[] result : billPayReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String trasUpClearedAhead = (String) result[1].toString();
                                    String trasUpScheduledAhead = (String) result[2].toString();
                                    String trasUpEnergyChargesAhead = (String) result[3].toString();
                                    String trasUpComChargesAhead = (String) result[4].toString();
                                    String trasUpClearedReal = (String) result[5].toString();
                                    String trasUpScheduledReal = (String) result[6].toString();
                                    String trasUpEnergyChargesReal = (String) result[7].toString();
                                    String trasUpComChargesReal = (String) result[8].toString();
                                    String trasUpTotalCharges = (String) result[9].toString();
                                    String trasDownScheduledAhead = (String) result[10].toString();
                                    String trasDownEnergyChargesAhead = (String) result[11].toString();
                                    String trasDownScheduledReal = (String) result[12].toString();
                                    String trasDownEnergyChargesReal = (String) result[13].toString();
                                    String netTras = (String) result[14].toString();

                                    System.out.println(" entityName is " + entityName + " trasUpClearedAhead " + trasUpClearedAhead + "  trasUpScheduledAhead " + trasUpScheduledAhead + " trasUpEnergyChargesAhead " + trasUpEnergyChargesAhead + " trasUpComChargesAhead " + trasUpComChargesAhead + " trasUpClearedReal " + trasUpClearedReal + " trasUpScheduledReal " + trasUpScheduledReal + " trasUpEnergyChargesReal " + trasUpEnergyChargesReal + " trasUpComChargesReal " + trasUpComChargesReal + " trasUpTotalCharges " + trasUpTotalCharges + " trasDownScheduledAhead " + trasDownScheduledAhead + " trasDownEnergyChargesAhead " + trasDownEnergyChargesAhead + " trasDownScheduledReal " + trasDownScheduledReal + " trasDownEnergyChargesReal " + trasDownEnergyChargesReal + " netTras " + netTras);
                                    String[] billPayRecvable = new String[]{entityName, trasUpClearedAhead, trasUpScheduledAhead, trasUpEnergyChargesAhead, trasUpComChargesAhead, trasUpClearedReal, trasUpScheduledReal, trasUpEnergyChargesReal, trasUpComChargesReal, trasUpTotalCharges, trasDownScheduledAhead, trasDownEnergyChargesAhead, trasDownScheduledReal, trasDownEnergyChargesReal, netTras};
                                    billPayReceive11.add(billPayRecvable);
                                }
                                List<BigDecimal> sumListPayDiff = new ArrayList<>();
                                sumListPay = billEntityTrasMDAO.getAllSumBill(weekId, yearId, revisionNum);
                                List<Object[]> sumListPay1 = billEntityTrasMDAO.getAllSumBill(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                for (Object[] result : sumListPay) {
                                    BigDecimal SUM_TRAS_UP_CLEARED_AHEAD = (BigDecimal) result[0];
                                    BigDecimal SUM_TRAS_UP_SCHEDULED_AHEAD = (BigDecimal) result[1];
                                    BigDecimal SUM_TRAS_UP_ENERGY_CHARGES_AHEAD = (BigDecimal) result[2];
                                    BigDecimal SUM_TRAS_UP_COM_CHARGES_AHEAD = (BigDecimal) result[3];
                                    BigDecimal SUM_TRAS_UP_CLEARED_REAL = (BigDecimal) result[4];
                                    BigDecimal SUM_TRAS_UP_SCHEDULED_REAL = (BigDecimal) result[5];
                                    BigDecimal SUM_TRAS_UP_ENERGY_CHARGES_REAL = (BigDecimal) result[6];
                                    BigDecimal SUM_TRAS_UP_COM_CHARGES_REAL = (BigDecimal) result[7];
                                    BigDecimal SUM_TRAS_UP_TOTAL_CHARGES = (BigDecimal) result[8];
                                    BigDecimal SUM_TRAS_DOWN_SCHEDULED_AHEAD = (BigDecimal) result[9];
                                    BigDecimal SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD = (BigDecimal) result[10];
                                    BigDecimal SUM_TRAS_DOWN_SCHEDULED_REAL = (BigDecimal) result[11];
                                    BigDecimal SUM_TRAS_DOWN_ENERGY_CHARGES_REAL = (BigDecimal) result[12];
                                    BigDecimal SUM_NET_TRAS = (BigDecimal) result[13];
                                    System.out.println("SUM_TRAS_UP_CLEARED_AHEAD is " + SUM_TRAS_UP_CLEARED_AHEAD + " SUM_TRAS_UP_SCHEDULED_AHEAD " + SUM_TRAS_UP_SCHEDULED_AHEAD + " SUM_TRAS_UP_ENERGY_CHARGES_AHEAD " + SUM_TRAS_UP_ENERGY_CHARGES_AHEAD + " SUM_TRAS_UP_COM_CHARGES_AHEAD " + SUM_TRAS_UP_COM_CHARGES_AHEAD + " SUM_TRAS_UP_COM_CHARGES_REAL " + SUM_TRAS_UP_COM_CHARGES_REAL + " SUM_TRAS_UP_CLEARED_REAL " + SUM_TRAS_UP_CLEARED_REAL + " SUM_TRAS_UP_SCHEDULED_REAL " + SUM_TRAS_UP_SCHEDULED_REAL + " SUM_TRAS_UP_ENERGY_CHARGES_REAL " + SUM_TRAS_UP_ENERGY_CHARGES_REAL + " SUM_TRAS_UP_TOTAL_CHARGES " + SUM_TRAS_UP_TOTAL_CHARGES + " SUM_TRAS_DOWN_SCHEDULED_AHEAD " + SUM_TRAS_DOWN_SCHEDULED_AHEAD + " SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD " + SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD + " SUM_TRAS_DOWN_SCHEDULED_REAL " + SUM_TRAS_DOWN_SCHEDULED_REAL + " SUM_TRAS_DOWN_ENERGY_CHARGES_REAL " + SUM_TRAS_DOWN_ENERGY_CHARGES_REAL + " SUM_NET_TRAS " + SUM_NET_TRAS);
                                    for (Object[] result1 : sumListPay1) {
                                        BigDecimal SUM_TRAS_UP_CLEARED_AHEAD1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_TRAS_UP_SCHEDULED_AHEAD1 = (BigDecimal) result1[1];
                                        BigDecimal SUM_TRAS_UP_ENERGY_CHARGES_AHEAD1 = (BigDecimal) result1[2];
                                        BigDecimal SUM_TRAS_UP_COM_CHARGES_AHEAD1 = (BigDecimal) result1[3];
                                        BigDecimal SUM_TRAS_UP_CLEARED_REAL1 = (BigDecimal) result1[4];
                                        BigDecimal SUM_TRAS_UP_SCHEDULED_REAL1 = (BigDecimal) result1[5];
                                        BigDecimal SUM_TRAS_UP_ENERGY_CHARGES_REAL1 = (BigDecimal) result1[6];
                                        BigDecimal SUM_TRAS_UP_COM_CHARGES_REAL1 = (BigDecimal) result1[7];
                                        BigDecimal SUM_TRAS_UP_TOTAL_CHARGES1 = (BigDecimal) result1[8];
                                        BigDecimal SUM_TRAS_DOWN_SCHEDULED_AHEAD1 = (BigDecimal) result1[9];
                                        BigDecimal SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD1 = (BigDecimal) result1[10];
                                        BigDecimal SUM_TRAS_DOWN_SCHEDULED_REAL1 = (BigDecimal) result1[11];
                                        BigDecimal SUM_TRAS_DOWN_ENERGY_CHARGES_REAL1 = (BigDecimal) result1[12];
                                        BigDecimal SUM_NET_TRAS1 = (BigDecimal) result1[13];
                                        System.out.println("SUM_TRAS_UP_CLEARED_AHEAD1 is " + SUM_TRAS_UP_CLEARED_AHEAD1 + " SUM_TRAS_UP_SCHEDULED_AHEAD1 " + SUM_TRAS_UP_SCHEDULED_AHEAD1 + " SUM_TRAS_UP_ENERGY_CHARGES_AHEAD1 " + SUM_TRAS_UP_ENERGY_CHARGES_AHEAD1 + " SUM_TRAS_UP_COM_CHARGES_AHEAD1 " + SUM_TRAS_UP_COM_CHARGES_AHEAD1 + " SUM_TRAS_UP_COM_CHARGES_REAL1 " + SUM_TRAS_UP_COM_CHARGES_REAL1 + " SUM_TRAS_UP_CLEARED_REAL1 " + SUM_TRAS_UP_CLEARED_REAL1 + " SUM_TRAS_UP_SCHEDULED_REAL1 " + SUM_TRAS_UP_SCHEDULED_REAL1 + " SUM_TRAS_UP_ENERGY_CHARGES_REAL1 " + SUM_TRAS_UP_ENERGY_CHARGES_REAL1 + " SUM_TRAS_UP_TOTAL_CHARGES1 " + SUM_TRAS_UP_TOTAL_CHARGES1 + " SUM_TRAS_DOWN_SCHEDULED_AHEAD1 " + SUM_TRAS_DOWN_SCHEDULED_AHEAD1 + " SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD1 " + SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD1 + " SUM_TRAS_DOWN_SCHEDULED_REAL1 " + SUM_TRAS_DOWN_SCHEDULED_REAL1 + " SUM_TRAS_DOWN_ENERGY_CHARGES_REAL1 " + SUM_TRAS_DOWN_ENERGY_CHARGES_REAL1 + " SUM_NET_TRAS1 " + SUM_NET_TRAS1);
                                        BigDecimal DIFF_SUM_TRAS_UP_CLEARED_AHEAD = SUM_TRAS_UP_CLEARED_AHEAD.subtract(SUM_TRAS_UP_CLEARED_AHEAD1);
                                        BigDecimal DIFF_SUM_TRAS_UP_SCHEDULED_AHEAD = SUM_TRAS_UP_SCHEDULED_AHEAD.subtract(SUM_TRAS_UP_SCHEDULED_AHEAD);
                                        BigDecimal DIFF_SUM_TRAS_UP_ENERGY_CHARGES_AHEAD = SUM_TRAS_UP_ENERGY_CHARGES_AHEAD.subtract(SUM_TRAS_UP_ENERGY_CHARGES_AHEAD1);
                                        BigDecimal DIFF_SUM_TRAS_UP_COM_CHARGES_AHEAD = SUM_TRAS_UP_COM_CHARGES_AHEAD.subtract(SUM_TRAS_UP_COM_CHARGES_AHEAD1);
                                        BigDecimal DIFF_SUM_TRAS_UP_CLEARED_REAL = SUM_TRAS_UP_CLEARED_REAL.subtract(SUM_TRAS_UP_CLEARED_REAL1);
                                        BigDecimal DIFF_SUM_TRAS_UP_SCHEDULED_REAL = SUM_TRAS_UP_SCHEDULED_REAL.subtract(SUM_TRAS_UP_SCHEDULED_REAL1);
                                        BigDecimal DIFF_SUM_TRAS_UP_ENERGY_CHARGES_REAL = SUM_TRAS_UP_ENERGY_CHARGES_REAL.subtract(SUM_TRAS_UP_ENERGY_CHARGES_REAL1);
                                        BigDecimal DIFF_SUM_TRAS_UP_COM_CHARGES_REAL = SUM_TRAS_UP_COM_CHARGES_REAL.subtract(SUM_TRAS_UP_COM_CHARGES_REAL1);
                                        BigDecimal DIFF_SUM_TRAS_UP_TOTAL_CHARGES = SUM_TRAS_UP_TOTAL_CHARGES.subtract(SUM_TRAS_UP_TOTAL_CHARGES1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_SCHEDULED_AHEAD = SUM_TRAS_DOWN_SCHEDULED_AHEAD.subtract(SUM_TRAS_DOWN_SCHEDULED_AHEAD1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD = SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD.subtract(SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_SCHEDULED_REAL = SUM_TRAS_DOWN_SCHEDULED_REAL.subtract(SUM_TRAS_DOWN_SCHEDULED_REAL1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_ENERGY_CHARGES_REAL = SUM_TRAS_DOWN_ENERGY_CHARGES_REAL.subtract(SUM_TRAS_DOWN_ENERGY_CHARGES_REAL1);
                                        BigDecimal DIFF_SUM_NET_TRAS = SUM_NET_TRAS.subtract(SUM_NET_TRAS1);

                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_CLEARED_AHEAD);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_SCHEDULED_AHEAD);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_ENERGY_CHARGES_AHEAD);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_COM_CHARGES_AHEAD);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_CLEARED_REAL);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_SCHEDULED_REAL);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_ENERGY_CHARGES_REAL);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_COM_CHARGES_REAL);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_TOTAL_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_SCHEDULED_AHEAD);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_ENERGY_CHARGES_AHEAD);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_SCHEDULED_REAL);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_ENERGY_CHARGES_REAL);
                                        sumListPayDiff.add(DIFF_SUM_NET_TRAS);
                                        // System.out.println("DIFF_SUM_TOTALCHARGES is " + DIFF_SUM_MARKUP_CHARGES);
                                    }
                                }
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                System.out.println("######sumListPay1 size is " + sumListPay1.size());
                                mv3.addObject("sumListPayDiff", sumListPayDiff);
                                mv3.addObject("sumListPay1", sumListPay1);
                            }
                        }
                        if (billPayReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }

                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billTRASMInfo", billTRASMInfo);
                    mv3.addObject("billPayReceive11", billPayReceive11);
                    return mv3;

                } else {
                    BigDecimal revNum = new BigDecimal(revNo);
                    List<BillTrasMDetails> billTRASMdet = billTRASMDetailsDAO.getBillDetailsTrasM(weekId, yearId, revNum);
                    if (billTRASMdet != null) {
                        ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklyTRASMBill");
                        BillEntityTRASMDAO billEntityTRASMDAO = new BillEntityTRASMDAO();
                        List<BillEntityTrasM> BillEntityTRASM = billEntityTRASMDAO.getBillEntityTrasM(weekId, yearId, revNum);

                        mv1.addObject("billTRASMdet", billTRASMdet);
                        mv1.addObject("BillEntityTRASM", BillEntityTRASM);
                        return mv1;
                    } else {
                        ModelAndView mv1 = new ModelAndView("successMsg");
                        String Msg = "bill is not available!!";
                        mv1.addObject("Msg", Msg);
                        return mv1;
                    }
                }
            }
            if ("TRASS".equals(billCat)) {
                System.out.println("\"TRASS\".equals(billCat)");
                BillTRASSDetailsDAO billTRASSDetailsDAO = new BillTRASSDetailsDAO();

                List<BillTrasSDetails> billTRASSInfo = new ArrayList();
                BillEntityTRASSDAO billEntityTrasSDAO = new BillEntityTRASSDAO();

                if ("Revised".equals(revNo)) {
                    System.out.println("\"Revised\".equals(revNo)");
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedTrasSBill");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billTRASSDetailsDAO.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    List<Object[]> billPayReceive2 = new ArrayList<>();
                    List<String[]> billPayReceive11 = new ArrayList<String[]>();
                    List<Object[]> sumListPay = new ArrayList<>();
                    List<Object[]> sumListReceive = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billTRASSInfo = billTRASSDetailsDAO.getBillDetailsTrasS(weekId, yearId, revisionNum);
                        System.out.println("billTRASSInfo is " + billTRASSInfo);
                        if (billTRASSInfo != null) {
                            System.out.println(" billTRASSInfo size is " + billTRASSInfo.size());
                            billPayReceive2 = billEntityTrasSDAO.getDiffBillPay(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
//                              
                            if (billPayReceive2 != null && !(billPayReceive2.isEmpty())) {
                                System.out.println("billPayReceive2 TrasE size is " + billPayReceive2.size());
                                for (Object[] result : billPayReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String trasUpEnergyShort = (String) result[1].toString();
                                    String trasUpCharges = (String) result[2].toString();
                                    String trasDownEnergyShort = (String) result[3].toString();
                                    String trasDownCharges = (String) result[4].toString();
                                    String netTras = (String) result[5].toString();

                                    System.out.println(" entityName is " + entityName + " trasUpEnergyShort " + trasUpEnergyShort + "  trasUpCharges " + trasUpCharges + " trasDownEnergyShort " + trasDownEnergyShort + " trasDownCharges " + trasDownCharges + " netTras " + netTras);
                                    String[] billPayRecvable = new String[]{entityName, trasUpEnergyShort, trasUpCharges, trasDownEnergyShort, trasDownCharges, netTras};
                                    billPayReceive11.add(billPayRecvable);
                                }
                                List<BigDecimal> sumListPayDiff = new ArrayList<>();
                                sumListPay = billEntityTrasSDAO.getAllSumBill(weekId, yearId, revisionNum);
                                List<Object[]> sumListPay1 = billEntityTrasSDAO.getAllSumBill(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                for (Object[] result : sumListPay) {
                                    BigDecimal SUM_TRAS_UP_ENERGY_SHORT = (BigDecimal) result[0];
                                    BigDecimal SUM_TRAS_UP_CHARGES = (BigDecimal) result[1];
                                    BigDecimal SUM_TRAS_DOWN_ENERGY_SHORT = (BigDecimal) result[2];
                                    BigDecimal SUM_TRAS_DOWN_CHARGES = (BigDecimal) result[3];
                                    BigDecimal SUM_NET_TRAS = (BigDecimal) result[4];
                                    System.out.println("SUM_TRAS_UP_ENERGY_SHORT is " + SUM_TRAS_UP_ENERGY_SHORT + " SUM_TRAS_UP_CHARGES " + SUM_TRAS_UP_CHARGES + " SUM_TRAS_DOWN_ENERGY_SHORT " + SUM_TRAS_DOWN_ENERGY_SHORT + " SUM_TRAS_DOWN_CHARGES " + SUM_TRAS_DOWN_CHARGES + " SUM_NET_TRAS " + SUM_NET_TRAS);
                                    for (Object[] result1 : sumListPay1) {
                                        BigDecimal SUM_TRAS_UP_ENERGY_SHORT1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_TRAS_UP_CHARGES1 = (BigDecimal) result1[1];
                                        BigDecimal SUM_TRAS_DOWN_ENERGY_SHORT1 = (BigDecimal) result1[2];
                                        BigDecimal SUM_TRAS_DOWN_CHARGES1 = (BigDecimal) result1[3];
                                        BigDecimal SUM_NET_TRAS1 = (BigDecimal) result1[4];
                                        System.out.println("SUM_TRAS_UP_ENERGY_SHORT1 is " + SUM_TRAS_UP_ENERGY_SHORT1 + " SUM_TRAS_UP_CHARGES1 " + SUM_TRAS_UP_CHARGES1 + " SUM_TRAS_DOWN_ENERGY_SHORT1 " + SUM_TRAS_DOWN_ENERGY_SHORT1 + " SUM_TRAS_DOWN_CHARGES1 " + SUM_TRAS_DOWN_CHARGES1 + " SUM_NET_TRAS1 " + SUM_NET_TRAS1);
                                        BigDecimal DIFF_SUM_TRAS_UP_ENERGY_SHORT = SUM_TRAS_UP_ENERGY_SHORT.subtract(SUM_TRAS_UP_ENERGY_SHORT1);
                                        BigDecimal DIFF_SUM_TRAS_UP_CHARGES = SUM_TRAS_UP_CHARGES.subtract(SUM_TRAS_UP_CHARGES1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_ENERGY_SHORT = SUM_TRAS_DOWN_ENERGY_SHORT.subtract(SUM_TRAS_DOWN_ENERGY_SHORT1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_CHARGES = SUM_TRAS_DOWN_CHARGES.subtract(SUM_TRAS_DOWN_CHARGES1);
                                        BigDecimal DIFF_SUM_NET_TRAS = SUM_NET_TRAS.subtract(SUM_NET_TRAS1);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_ENERGY_SHORT);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_ENERGY_SHORT);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_NET_TRAS);
                                        // System.out.println("DIFF_SUM_TOTALCHARGES is " + DIFF_SUM_MARKUP_CHARGES);
                                    }
                                }
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                System.out.println("######sumListPay1 size is " + sumListPay1.size());
                                mv3.addObject("sumListPayDiff", sumListPayDiff);
                                mv3.addObject("sumListPay1", sumListPay1);
                            }
                        }
                        if (billPayReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }

                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billTRASSInfo", billTRASSInfo);
                    mv3.addObject("billPayReceive11", billPayReceive11);
                    return mv3;

                } else {
                    BigDecimal revNum = new BigDecimal(revNo);

                    List<BillTrasSDetails> billTRASSdet = billTRASSDetailsDAO.getBillDetailsTrasS(weekId, yearId, revNum);
                    if (billTRASSdet != null) {
                        ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklyTRASSBill");
                        BillEntityTRASSDAO billEntityTRASSDAO = new BillEntityTRASSDAO();
                        List<BillEntityTrasS> BillEntityTRASS = billEntityTRASSDAO.getBillEntityTrasS(weekId, yearId, revNum);

                        mv1.addObject("billTRASSdet", billTRASSdet);
                        mv1.addObject("BillEntityTRASS", BillEntityTRASS);
                        return mv1;
                    } else {
                        ModelAndView mv1 = new ModelAndView("successMsg");
                        String Msg = "bill is not available!!";
                        mv1.addObject("Msg", Msg);
                        return mv1;
                    }

                }
            }
            if ("TRASE".equals(billCat)) {
                System.out.println("\"TRASE\".equals(billCat)");
                BillTRASEDetailsDAO billTRASEDetailsDAO = new BillTRASEDetailsDAO();
                List<BillTrasEDetails> billTRASEInfo = new ArrayList();
                BillEntityTRASEDAO billEntityTrasEDAO = new BillEntityTRASEDAO();

                if ("Revised".equals(revNo)) {
                    System.out.println("\"Revised\".equals(revNo)");
                    ModelAndView mv3 = new ModelAndView("BillProcessing/viewWeeklyRevisedTrasEBill");
                    BigDecimal revisionNum = BigDecimal.ZERO;
                    revisionNum = billTRASEDetailsDAO.getMaxRevisionNum(weekId, yearId);
                    System.out.println(" revisionNum is " + revisionNum);
                    List<Object[]> billPayReceive2 = new ArrayList<>();
                    List<String[]> billPayReceive11 = new ArrayList<String[]>();
                    List<Object[]> sumListPay = new ArrayList<>();
                    List<Object[]> sumListReceive = new ArrayList<>();
                    if (revisionNum.compareTo(BigDecimal.ZERO) == 1) {
                        billTRASEInfo = billTRASEDetailsDAO.getBillDetailsTrasE(weekId, yearId, revisionNum);
                        System.out.println("billTRASEInfo is " + billTRASEInfo);
                        if (billTRASEInfo != null) {
                            System.out.println(" billTRASEInfo size is " + billTRASEInfo.size());
                            billPayReceive2 = billEntityTrasEDAO.getDiffBillPay(weekId, yearId, revisionNum);
                            EntityDAO entDao = new EntityDAO();
//                              
                            if (billPayReceive2 != null && !(billPayReceive2.isEmpty())) {
                                System.out.println("billPayReceive2 TrasE size is " + billPayReceive2.size());
                                for (Object[] result : billPayReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String trasUpEnergyEmer = (String) result[1].toString();
                                    String trasUpCharges = (String) result[2].toString();
                                    String trasDownEnergyEmer = (String) result[3].toString();
                                    String trasDownCharges = (String) result[4].toString();
                                    String netTras = (String) result[5].toString();

                                    System.out.println(" entityName is " + entityName + " trasUpEnergyEmer " + trasUpEnergyEmer + "  trasUpCharges " + trasUpCharges + " trasDownEnergyEmer " + trasDownEnergyEmer + " trasDownCharges " + trasDownCharges + " netTras " + netTras);
                                    String[] billPayRecvable = new String[]{entityName, trasUpEnergyEmer, trasUpCharges, trasDownEnergyEmer, trasDownCharges, netTras};
                                    billPayReceive11.add(billPayRecvable);
                                }
                                List<BigDecimal> sumListPayDiff = new ArrayList<>();
                                sumListPay = billEntityTrasEDAO.getAllSumBill(weekId, yearId, revisionNum);
                                List<Object[]> sumListPay1 = billEntityTrasEDAO.getAllSumBill(weekId, yearId, revisionNum.subtract(BigDecimal.ONE));
                                for (Object[] result : sumListPay) {
                                    BigDecimal SUM_TRAS_UP_ENERGY_EMER = (BigDecimal) result[0];
                                    BigDecimal SUM_TRAS_UP_CHARGES = (BigDecimal) result[1];
                                    BigDecimal SUM_TRAS_DOWN_ENERGY_EMER = (BigDecimal) result[2];
                                    BigDecimal SUM_TRAS_DOWN_CHARGES = (BigDecimal) result[3];
                                    BigDecimal SUM_NET_TRAS = (BigDecimal) result[4];
                                    System.out.println("SUM_TRAS_UP_ENERGY_EMER is " + SUM_TRAS_UP_ENERGY_EMER + " SUM_TRAS_UP_CHARGES " + SUM_TRAS_UP_CHARGES + " SUM_TRAS_DOWN_ENERGY_EMER " + SUM_TRAS_DOWN_ENERGY_EMER + " SUM_TRAS_DOWN_CHARGES " + SUM_TRAS_DOWN_CHARGES + " SUM_NET_TRAS " + SUM_NET_TRAS);
                                    for (Object[] result1 : sumListPay1) {
                                        BigDecimal SUM_TRAS_UP_ENERGY_EMER1 = (BigDecimal) result1[0];
                                        BigDecimal SUM_TRAS_UP_CHARGES1 = (BigDecimal) result1[1];
                                        BigDecimal SUM_TRAS_DOWN_ENERGY_EMER1 = (BigDecimal) result1[2];
                                        BigDecimal SUM_TRAS_DOWN_CHARGES1 = (BigDecimal) result1[3];
                                        BigDecimal SUM_NET_TRAS1 = (BigDecimal) result1[4];
                                        System.out.println("SUM_TRAS_UP_ENERGY_EMER1 is " + SUM_TRAS_UP_ENERGY_EMER1 + " SUM_TRAS_UP_CHARGES1 " + SUM_TRAS_UP_CHARGES1 + " SUM_TRAS_DOWN_ENERGY_EMER1 " + SUM_TRAS_DOWN_ENERGY_EMER1 + " SUM_TRAS_DOWN_CHARGES1 " + SUM_TRAS_DOWN_CHARGES1 + " SUM_NET_TRAS1 " + SUM_NET_TRAS1);
                                        BigDecimal DIFF_SUM_TRAS_UP_ENERGY_EMER = SUM_TRAS_UP_ENERGY_EMER.subtract(SUM_TRAS_UP_ENERGY_EMER1);
                                        BigDecimal DIFF_SUM_TRAS_UP_CHARGES = SUM_TRAS_UP_CHARGES.subtract(SUM_TRAS_UP_CHARGES1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_ENERGY_EMER = SUM_TRAS_DOWN_ENERGY_EMER.subtract(SUM_TRAS_DOWN_ENERGY_EMER1);
                                        BigDecimal DIFF_SUM_TRAS_DOWN_CHARGES = SUM_TRAS_DOWN_CHARGES.subtract(SUM_TRAS_DOWN_CHARGES1);
                                        BigDecimal DIFF_SUM_NET_TRAS = SUM_NET_TRAS.subtract(SUM_NET_TRAS1);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_ENERGY_EMER);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_UP_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_ENERGY_EMER);
                                        sumListPayDiff.add(DIFF_SUM_TRAS_DOWN_CHARGES);
                                        sumListPayDiff.add(DIFF_SUM_NET_TRAS);
                                        // System.out.println("DIFF_SUM_TOTALCHARGES is " + DIFF_SUM_MARKUP_CHARGES);
                                    }
                                }
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                System.out.println("######sumListPay1 size is " + sumListPay1.size());
                                mv3.addObject("sumListPayDiff", sumListPayDiff);
                                mv3.addObject("sumListPay1", sumListPay1);
                            }
                        }
                        if (billPayReceive2 != null) {
                            System.out.println("Data is available!!");
                        } else {
                            ModelAndView mv2 = new ModelAndView("successMsg");
                            String Msg = "Data not available for the selected parameters!!";
                            mv2.addObject("Msg", Msg);
                            return mv2;
                        }

                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                    mv3.addObject("billTRASEInfo", billTRASEInfo);
                    mv3.addObject("billPayReceive11", billPayReceive11);
                    return mv3;

                } else {
                    BigDecimal revNum = new BigDecimal(revNo);

                    List<BillTrasEDetails> billTRASEdet = billTRASEDetailsDAO.getBillDetailsTrasE(weekId, yearId, revNum);
                    if (billTRASEdet != null) {
                        ModelAndView mv1 = new ModelAndView("BillProcessing/viewWeeklyTRASEBill");
                        BillEntityTRASEDAO billEntityTRASEDAO = new BillEntityTRASEDAO();
                        List<BillEntityTrasE> BillEntityTRASE = billEntityTRASEDAO.getBillEntityTrasE(weekId, yearId, revNum);

                        mv1.addObject("billTRASEdet", billTRASEdet);
                        mv1.addObject("BillEntityTRASE", BillEntityTRASE);
                        return mv1;

                    } else {
                        ModelAndView mv1 = new ModelAndView("successMsg");
                        String Msg = "bill is not available!!";
                        mv1.addObject("Msg", Msg);
                        return mv1;
                    }
                }
            }
        }
        return mv;
    }

    public ModelAndView importBankStatementTemplate(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        System.out.println("Inside importExcelController");
        ModelAndView mv = new ModelAndView("BankStatement/excelBankStatementUpload");
        return mv;
    }

    public ModelAndView downloadBankStatementTemplate(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView model = null;
        System.out.println(" inside downloadBankStatementTemplate");
        String filename = "bank_statement_excel_file";
// String filepath=Constants.ATC_SDNDA_FILEPATH;
        ConstantsMasterDAO constadao = new ConstantsMasterDAO();
        String filepath = constadao.getFilePathbyName("BANK_TEMPLATE");
        System.out.print("File path " + filepath);
        System.out.println("filepath obtained is" + filepath);
// System.out.println("filepath obtained is"+filepath);
        filepath = filepath + filename + ".xlsx";//"C:\\OnlineABTFiles\\Village Culture.pdf";
        File file = new File(filepath, URLDecoder.decode(filepath, "UTF-8"));
// if(file.exists()){
        response.setContentType("application/xls");
// response.setContentType("image/jpeg");
// response.setHeader("Content-Length", String.valueOf(file.length()));
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
// }
// else{
// ModelAndView mv=new ModelAndView("errorMsg");
// String msg="file is not there in the specified location";
// mv.addObject("msg", msg);
// return mv;
// }
        return null;
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
        List<BillPayableCorp> list = null;
        BillPayableCorpDAO billpadao = new BillPayableCorpDAO();
        list = billpadao.getPendingBillPaybyCorp();
        BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
        List<Object[]> list1 = null;
        list1 = billrecvdao.getPendingDisbursementbyCorp();
        List<BillReceiveCorp> listbillcorp = new ArrayList<>();
        BillReceiveCorp billrecv = null;
        Corporates corp = null;
        CorporatesDAO corpdao = new CorporatesDAO();
        for (Object[] obj : list1) {
            billrecv = new BillReceiveCorp();
            corp = new Corporates();
            billrecv.setBillingDate((Date) obj[0]);
            billrecv.setWeekId((BigDecimal) obj[1]);
            billrecv.setBillType((String) obj[2]);
            BigDecimal bg1 = (BigDecimal) obj[3];
            String corpname = corpdao.geCorpNamebyId(bg1.intValue());
            corp.setCorporateId((int) bg1.intValue());
            corp.setCorporateName(corpname);
            billrecv.setCorporates(corp);
            billrecv.setToalnet((BigDecimal) obj[4]);
            billrecv.setDisburseAmount((BigDecimal) obj[5]);
            billrecv.setPendingAmount((BigDecimal) obj[6]);
            listbillcorp.add(billrecv);
        }
        mv.addObject("billpayList", list);
        mv.addObject("billrecvList", listbillcorp);
        return mv;
    }

    public ModelAndView welcomePendingBarChart(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException, JSONException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        List<Object[]> list3 = new ArrayList<Object[]>();
        BillPayableCorpDAO billpadao = new BillPayableCorpDAO();
        list3 = billpadao.getPendingBillPaybyCorpGroupbyWeekforBarChart();
        JSONArray ja = new JSONArray();
        JSONObject result = new JSONObject();
        ja.put(list3);
        result.put("aaData", ja);
        response.getOutputStream().print(result.toString());
        return null;
    }

    public ModelAndView welcomePendingChart(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("welcomePendingChart");
        BillDsmDetailsDAO billdsmdedao = new BillDsmDetailsDAO();
        BillReceiveCorpDAO billrecvDsmdao = new BillReceiveCorpDAO();
        BillRrasDetailsDAO billrasdao = new BillRrasDetailsDAO();
        BillAgcDetailsDAO billagcdao = new BillAgcDetailsDAO();
        BillFrasDetailsDAO billfrasdao = new BillFrasDetailsDAO();
        BankStatementDAO banksrmtdao = new BankStatementDAO();
        InterestDetailsDAO intedao = new InterestDetailsDAO();
        DisbursedInterestDetailsDAO disinteresdao = new DisbursedInterestDetailsDAO();
        PaymentDisbursementDAO paydisdao = new PaymentDisbursementDAO();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        Date maxdate = new Date();
        Date maxToDate = new Date();
        BillPayableCorpDAO billpaycorpdao = new BillPayableCorpDAO();
        BigDecimal pdweekid = paydisdao.getMaxWeekidPD(year);
        maxdate = banksrmtdao.getMaxStmtFromdateRRAS(year);
        maxToDate = banksrmtdao.getMaxStmtTodate(year);
        if (pdweekid.equals(new BigDecimal(0))) {
            pdweekid = paydisdao.getMaxWeekidPD(year - 1);
        }
        if (maxToDate != null) {

        } else {

            maxdate = banksrmtdao.getMaxStmtFromdateRRAS(year - 1);
            maxToDate = banksrmtdao.getMaxStmtTodate(year - 1);

        }
//        BigDecimal dsmweekid = billdsmdedao.getMaxWeekIDDSM(year);
//        BigDecimal rrasweekid = billrasdao.getMaxWeekIDRRAS(year);
//        BigDecimal agcweekid = billagcdao.getMaxWeekIdAGC(year);
//        BigDecimal frasweekid = billfrasdao.getMaxWeekIdFRAS(year);
//      
//        BigDecimal latestpayabledsm=billdsmdedao.getLatestPayableAmountDSM(); 
//        BigDecimal latestrecvdsm=billrecvDsmdao.getLatestReceivableAmountDSM();
//        BigDecimal latestpayableagc=billagcdao.getLatestPayableAmountAGC("Payable");
//        BigDecimal latestrecevagc=billagcdao.getLatestPayableAmountAGC("Receivable");
//       
//        BigDecimal latestpayafras=new BigDecimal(0);
//        BigDecimal latestrecvfras=new BigDecimal(0);
//        latestpayafras=billfrasdao.getLatestPayableAmountFRAS();
//       
//        if(latestpayafras.compareTo(BigDecimal.ZERO)==1)
//        {
//            latestrecvfras=BigDecimal.ZERO;
//        }
//        else
//        {
//            latestpayafras=BigDecimal.ZERO;
//        }
        BigDecimal latestInterestpayabledsm = intedao.getLatestPayableInterestAmount("DSM");
        BigDecimal latestInterestpayableagc = intedao.getLatestPayableInterestAmount("AGC");
        BigDecimal latestInterestpayablerras = intedao.getLatestPayableInterestAmount("RRAS");
        BigDecimal latestInterestpayablefras = intedao.getLatestPayableInterestAmount("FRAS");
        BigDecimal latestInterestDisbursedsm = disinteresdao.getLatestDisburseInterestAmount("DSM");
        BigDecimal latestInterestDisburseagc = disinteresdao.getLatestDisburseInterestAmount("AGC");
        BigDecimal latestInterestDisburserras = disinteresdao.getLatestDisburseInterestAmount("RRAS");
        BigDecimal latestInterestDisbursefras = disinteresdao.getLatestDisburseInterestAmount("FRAS");
        List<Object[]> list3 = new ArrayList<Object[]>();
        BillReceiveCorpDAO billpadao = new BillReceiveCorpDAO();
        list3 = billpadao.getLatestBillsReceivableAllTypes();
        List<Object[]> listobj = new ArrayList<Object[]>();
        listobj = billpaycorpdao.getLatestBillsPayableAllTypes();
        PoolAccountDetailsDAO pooldao = new PoolAccountDetailsDAO();
        List<PoolAccountDetails> listpool = null;
        listpool = pooldao.getPoolAccountDetails();
        BigDecimal mainpool = listpool.get(0).getMainBalance();
//        BigDecimal latestpayablerras = billrasdao.getLatestPayableAmountRRAS();
//        BigDecimal latestrecvrras = billrasdao.getLatestRecvAmountRRAS();
        mv.addObject("maxdate", maxdate);
        mv.addObject("maxToDate", maxToDate);
        mv.addObject("pdweekid", pdweekid);
        mv.addObject("mainpool", mainpool);

//        mv.addObject("dsmweekid", dsmweekid);
//        mv.addObject("rrasweekid", rrasweekid);
//        mv.addObject("agcweekid", agcweekid);
//        mv.addObject("frasweekid", frasweekid);      
//        mv.addObject("latestpayabledsm",latestpayabledsm);
//        mv.addObject("latestrecvdsm",latestrecvdsm);
//        mv.addObject("latestpayableagc",latestpayableagc);
//        mv.addObject("latestrecevagc",latestrecevagc);
//        mv.addObject("latestpayablerras",latestpayablerras);
//        mv.addObject("latestrecvrras",latestrecvrras);       
//        mv.addObject("latestpayafras",latestpayafras);
//        mv.addObject("latestrecvrras",latestrecvrras);
        mv.addObject("latestInterestpayabledsm", latestInterestpayabledsm);
        mv.addObject("latestInterestpayableagc", latestInterestpayableagc);
        mv.addObject("latestInterestpayablerras", latestInterestpayablerras);
//        mv.addObject("latestrecvrras", latestrecvrras);
        mv.addObject("latestInterestDisbursedsm", latestInterestDisbursedsm);
        mv.addObject("latestInterestDisburseagc", latestInterestDisburseagc);
        mv.addObject("latestInterestDisburserras", latestInterestDisburserras);
        mv.addObject("latestInterestDisbursefras", latestInterestDisbursefras);
        mv.addObject("latestPayableAllBills", listobj);
        mv.addObject("latestReceivableAllBills", list3);
        return mv;
    }

    public ModelAndView billProcessingIndexRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/billProcessingIndexRLDC");
        return mv;
    }

    public ModelAndView billProcessingIndexMaker(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/billProcessingIndexMaker");
        return mv;
    }

    public ModelAndView billProcessingIndexFinance(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/billProcessingIndexFinance");
        return mv;
    }

    public ModelAndView viewPendingWeeklyBill(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewPendingWeeklyBill");
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

    public ModelAndView viewCheckerPayableRefundsList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewCheckerPayableRefundsList");
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        List<Object[]> list = null;
        list = temprefudndao.getAllPendingPayableTempRefundBillCorpforChecker();
        Corporates corp = null;
        List<Corporates> listcorp = new ArrayList<>();
        for (Object[] obj : list) {
            corp = new Corporates();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            listcorp.add(corp);
        }
        mv.addObject("refundList", listcorp);
        return mv;
    }

//   public ModelAndView viewCheckerPayableRefundsDetails(HttpServletRequest request,
//           HttpServletResponse response) throws IOException {
//       HttpSession session1 = request.getSession(false);
//       if (session1 == null) {
//           RedirectView redirectView = new RedirectView();
//           redirectView.setContextRelative(true);
//           redirectView.setUrl("/logout.htm");
//           return new ModelAndView(redirectView);
//       }
//       TempRefundBillCorpDAO temprefudndao=new TempRefundBillCorpDAO();
//       TempBankStatementDAO temobkstmtdao=new TempBankStatementDAO();
//       BankStatementDAO bankstmtdao=new BankStatementDAO();
//       BillReceiveCorpDAO billreceveidao=new BillReceiveCorpDAO();
//      
//       String bconfirm=request.getParameter("bconfirm");
//        List<TempRefundBillCorp> list=null;
//        List<TempBankStatement> list1=null;
//       if(bconfirm!=null)
//       {
//           String corpid=request.getParameter("corpid");
//           list1=temobkstmtdao.getTempBankStatementbyStatus("Pending", Integer.parseInt(corpid));
//          
//           for(TempBankStatement e:list1)
//           {
//               BigDecimal smtid=e.getBankStatement().getStmtId();
//               BigDecimal bg=e.getMappedAmount();               
//               BigDecimal bg1=e.getTransactionBalance();
//               BigDecimal mappedamount=bankstmtdao.getMappedAmountBankStmtbyStmtID(e.getBankStatement().getStmtId().intValue());               
//               mappedamount = mappedamount.add(bg);                       
//               bankstmtdao.getUpdateBankStmtbyCheckerforCorp(smtid, mappedamount, bg1,"Remarks"); 
//               
//           } 
//           
//           list=temprefudndao.getPendingTempRefundBillCorp(Integer.parseInt(corpid));
//           for(TempRefundBillCorp j:list)
//           {
//              
//               int billrecvuniqid=j.getBillReceiveCorp().getUniqueId().intValue();
//               System.out.println("billrecvuniqid  is " + billrecvuniqid);
//               billreceveidao.getUpdateAdjustFlagbyUniqueID(j.getBillReceiveCorp().getUniqueId().intValue(),j.getTotalAmount());
//               temprefudndao.getUpdatePendingTempRefundBillPayableCorp(Integer.parseInt(corpid),j.getBillReceiveCorp().getUniqueId().intValue());
//           }
//                    
//           
//           temobkstmtdao.getUpdatedTempBankStatementbyCorp(Integer.parseInt(corpid), "Verified");
//            ModelAndView mv1 = new ModelAndView("successMsg");
//            mv1.addObject("Msg","Successfully Verified the refund ....");
//            return mv1;
//          
//       }
//       String bcancel=request.getParameter("bcancel");       
//       if(bcancel!=null)
//       {
//           String corpID=request.getParameter("corpID");
//           temprefudndao.getDeletePendingTempRefundBillCorp(Integer.parseInt(corpID));
//            ModelAndView mv1 = new ModelAndView("successMsg");
//            mv1.addObject("Msg","Successfully Deleted the refund records ....");
//            return mv1;
//          
//       }
//       String corpID=request.getParameter("corpID");       
//       ModelAndView mv = new ModelAndView("viewCheckerPayableRefundsDetails");
//       list=temprefudndao.getPendingTempRefundBillCorp(Integer.parseInt(corpID));
//       mv.addObject("refundList",list);
//       mv.addObject("corpid",corpID);
//       return mv;
//   }
//  
//    public ModelAndView viewCorporateRefundPayableList(HttpServletRequest request,
//           HttpServletResponse response) throws IOException {
//       HttpSession session1 = request.getSession(false);
//       if (session1 == null) {
//           RedirectView redirectView = new RedirectView();
//           redirectView.setContextRelative(true);
//           redirectView.setUrl("/logout.htm");
//           return new ModelAndView(redirectView);
//       }
//        CorporatesDAO corpDao = new CorporatesDAO();
//       Corporates corp = new Corporates();
//       TempBankStatementDAO tempbakstdao = new TempBankStatementDAO();
//     
//              
//       String bname=request.getParameter("bname");
//       if(bname!=null)
//       {          
//            
//           BankStatementDAO bankStmtDao = new BankStatementDAO();             
//           ModelAndView mv1 = new ModelAndView("viewBillRefundPayableDetails");
//           String corpid=request.getParameter("corparateID");
//           int corporateid=Integer.parseInt(corpid);
//           String corparatename=null;
//            BankStatementDAO bankstmtdao=new BankStatementDAO();       
//           List<BankStatement> list6 = null;               
//           list6 = bankstmtdao.BankStatementOrderlist(corporateid);   
//           if (list6 != null && !(list6.isEmpty())) {
//              
//               System.out.println("BankSTmt List size is " + list6.size());
//               CorporatesDAO corpdao=new CorporatesDAO();                    
//               corparatename=corpdao.geCorpNamebyId(Integer.parseInt(corpid));          
//                             
//                             
//               /* Getting Refund Records from billReceivebale corp*/
//              
//               BillReceiveCorpDAO billrecvdao=new BillReceiveCorpDAO();               
//               List<BillReceiveCorp> listrefund=null;               
//               listrefund=billrecvdao.getRefundPendingBillReceiveAmountbyCorp(corporateid);                 
//               mv1.addObject("corpName", corparatename);            
//               mv1.addObject("bankList", list6);
//               mv1.addObject("CorpID", corpid);              
//               mv1.addObject("refundList",listrefund);                
//               return mv1;
//           } else {
//               System.out.println("Record not found");
//               ModelAndView mv2 = new ModelAndView("successMsg");
//               String Msg = "Please check-- there is no Verified Bank Transaction for Mapping for " + corparatename + "!";
//               mv2.addObject("Msg", Msg);
//               return mv2;
//           }
//                
//               
//       }
//      
//       
//        String refundconfirm = request.getParameter("refundconfirm");
//       if(refundconfirm !=null)
//       {
//           String refundbankrowcount = request.getParameter("refundbankrowcount");
//           int refuniqueid=0;
//           TempRefundBillCorp temprefbillcorp = new TempRefundBillCorp();
//           TempRefundBillCorpDAO temprefbillcorpdao = new TempRefundBillCorpDAO();
//         
//           String uniqueid1;
//           String refweekid1;
//           String recvrefundid1;
//            String uniqueId[] = request.getParameterValues("refuniqueNo");
//           String refrowcount=request.getParameter("refrowcount");                    
//           String corpid=request.getParameter("corpid");
//           BillReceiveCorp billrecopr=new BillReceiveCorp();
//                         
//           if(Integer.parseInt(refrowcount) >= 0)
//           {
//               for (int i = 1; i <= Integer.parseInt(refrowcount); i++)
//               {
//                   for (int j = 0; j < uniqueId.length; j++) {
//                      
//                       uniqueid1 = request.getParameter("recvuniqueid" + i);
//                      
//                       System.out.println("uniqueid1 count " + uniqueid1 + " " + j);
//                       if(Integer.parseInt(uniqueid1) == Integer.parseInt(uniqueId[j]))
//                       {
//                           refweekid1=request.getParameter("refweekid"+i);
//                           recvrefundid1=request.getParameter("recvrefundid"+i);
//                           System.out.println("recvrefundid1  " + recvrefundid1 );
//                           System.out.println("refweekid1  " + refweekid1 );
//                           int max=temprefbillcorpdao.getMaxUniqueID();
//                           max=max+1;
//                           temprefbillcorp.setSlno(new BigDecimal(max));
//                           corp.setCorporateId(Integer.parseInt(corpid));
//                           billrecopr.setUniqueId(new BigDecimal(uniqueid1));
//                           temprefbillcorp.setBillReceiveCorp(billrecopr);
//                           temprefbillcorp.setRefundDate(new Date());
//                           temprefbillcorp.setCheckerStatus("Pending");
//                           temprefbillcorp.setCorporates(corp);
//                           temprefbillcorp.setTotalAmount(new BigDecimal(recvrefundid1));
//                           temprefbillcorp.setWeekid(new BigDecimal(refweekid1));
//                           temprefbillcorpdao.NewTempRefundBillCorp(temprefbillcorp);
//                          
//                           
//                       }
//                   }
//               }
//             
//           }// of temp refund
//           
//           
//           String refbankrowcount = request.getParameter("refundbankrowcount");
//           System.out.println("refbankrowcount count " + refbankrowcount );
//           BankStatement bankstmy = new BankStatement();
//               int maxid1 = 0;
//               String settleAmtBnk;
//               String remainBal1;
//               String totalamt1;
//               String banktransbal;
//               String bankremarks;
//               TempBankStatement tempbakst = new TempBankStatement();
//               for (int k = 1; k <= Integer.parseInt(refbankrowcount); k++)
//               {
//                   
//                   String refbankstmt1 = request.getParameter("refbankstmt" + k);
//                   String refbankstmt[] = request.getParameterValues("refbankstmt");
//                   if (Integer.parseInt(refbankstmt[0]) == Integer.parseInt(refbankstmt1)) {
//                       bankstmy.setStmtId(new BigDecimal(refbankstmt1));
//                       maxid1 = tempbakstdao.getMaxUniqueID();
//                       maxid1 = maxid1 + 1;
//                       tempbakst.setTempStmtid(new BigDecimal(maxid1));
//                       tempbakst.setBankStatement(bankstmy);
//                       banktransbal = request.getParameter("refremainBal" + k);
//                       settleAmtBnk = request.getParameter("refsettleAmtBnk" + k);
//                       bankremarks = request.getParameter("refremarks" + k);
//                       totalamt1 = request.getParameter("reftotalamt" + k);
//                       tempbakst.setMappedAmount(new BigDecimal(settleAmtBnk));
//                       tempbakst.setRemarks(bankremarks);
//                       tempbakst.setTransactionBalance(new BigDecimal(banktransbal));
//                       tempbakst.setTransactionAmount(new BigDecimal(totalamt1));
//                       tempbakst.setCorporateId(new BigDecimal(corpid));
//                       tempbakst.setCheckerStatus("Pending");
//                       tempbakstdao.NewTempBankStatement(tempbakst);
//                   }
//               }
//          
//            ModelAndView mv3 = new ModelAndView("successMsg");
//               mv3.addObject("Msg","Succesfully Refund submitted for checker");
//               return mv3;
//          
//                     
//       }//end of refund confirm button
//       ModelAndView mv = new ModelAndView("viewRefundPayableList");
//       TempRefundBillCorpDAO temrefunddao=new TempRefundBillCorpDAO();
//       List<TempRefundBillCorp> list1=null;
//       list1=temrefunddao.getAllPendingPayableTempRefundBillCorp();
////       System.out.println("temprefudn listsize " + list1.isEmpty() );
//       BillReceiveCorpDAO billrecvdao=new BillReceiveCorpDAO();
//       List<BillReceiveCorp> list=null;            
//       list=billrecvdao.getAllRefundPendingBillReceiveAmountbyCorp();       
//       mv.addObject("refundList",list);
//       mv.addObject("temprefundList",list1);
//       return mv;
//   }
//    
    public ModelAndView viewCheckerPayableRefundsDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();
        TempMapBankStatementDAO temobkstmtdao = new TempMapBankStatementDAO();
        BankStatementDAO bankstmtdao = new BankStatementDAO();
        BillReceiveCorpDAO billreceveidao = new BillReceiveCorpDAO();
        String bconfirm = request.getParameter("bconfirm");
        List<TempRefundBillCorp> list = null;
        List<TempMapBankStatement> list1 = null;
        if (bconfirm != null) {
            String corpid = request.getParameter("corpid");
            list1 = temobkstmtdao.getTempMapBankStatementbyStatus("Pending", Integer.parseInt(corpid), "Refund");
            for (TempMapBankStatement e : list1) {
                BigDecimal smtid = e.getBankStatement().getStmtId();
                BigDecimal bg = e.getMappedAmount();
                BigDecimal bg1 = e.getTransactionBalance();
                BigDecimal mappedamount = bankstmtdao.getMappedAmountBankStmtbyStmtID(e.getBankStatement().getStmtId().intValue());
                mappedamount = mappedamount.add(bg);
                bankstmtdao.getUpdateBankStmtbyCheckerforCorp(smtid, mappedamount, bg1, "Remarks");
            }
            list = temprefudndao.getPendingTempRefundBillCorp(Integer.parseInt(corpid));
            for (TempRefundBillCorp j : list) {
                int billrecvuniqid = j.getBillReceiveCorp().getUniqueId().intValue();
                System.out.println("billrecvuniqid  is " + billrecvuniqid);
                billreceveidao.getUpdateAdjustFlagbyUniqueID(j.getBillReceiveCorp().getUniqueId().intValue(), j.getTotalAmount());
                temprefudndao.getUpdatePendingTempRefundBillReceivableCorp(Integer.parseInt(corpid), j.getBillReceiveCorp().getUniqueId().intValue());
            }
            temobkstmtdao.getUpdatedTempBankStatementbyCorp(Integer.parseInt(corpid), "Verified");
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Successfully Verified the refund ....");
            return mv1;
        }
        String bcancel = request.getParameter("bcancel");
        if (bcancel != null) {
            String corpID = request.getParameter("corpid");
            list = temprefudndao.getPendingTempRefundBillCorp(Integer.parseInt(corpID));
            for (TempRefundBillCorp j : list) {
                int billrecvuniqid = j.getBillReceiveCorp().getUniqueId().intValue();
                billreceveidao.getUpdateAdjustFlagbyUniqueIDOnDelete(billrecvuniqid);
            }
            temprefudndao.getDeletePendingTempRefundBillCorp(Integer.parseInt(corpID));
            ModelAndView mv1 = new ModelAndView("successMsg");
            mv1.addObject("Msg", "Successfully Deleted the refund records ....");
            return mv1;
        }
        String corpID = request.getParameter("corpID");
        ModelAndView mv = new ModelAndView("viewCheckerPayableRefundsDetails");
        list = temprefudndao.getPendingTempRefundBillCorp(Integer.parseInt(corpID));
        mv.addObject("refundList", list);
        mv.addObject("corpid", corpID);
        return mv;
    }

    public ModelAndView viewCorporateRefundPayableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        CorporatesDAO corpDao = new CorporatesDAO();
        Corporates corp = new Corporates();
        TempMapBankStatementDAO tempbakstdao = new TempMapBankStatementDAO();
        String bname = request.getParameter("bname");
        if (bname != null) {
            BankStatementDAO bankStmtDao = new BankStatementDAO();
            ModelAndView mv1 = new ModelAndView("viewBillRefundPayableDetails");
            String corpid = request.getParameter("corparateID");
            int corporateid = Integer.parseInt(corpid);
            String corparatename = null;
            BankStatementDAO bankstmtdao = new BankStatementDAO();
            List<BankStatement> list6 = null;
            list6 = bankstmtdao.BankStatementOrderlist(corporateid);
            if (list6 != null && !(list6.isEmpty())) {
                System.out.println("BankSTmt List size is " + list6.size());
                CorporatesDAO corpdao = new CorporatesDAO();
                corparatename = corpdao.geCorpNamebyId(Integer.parseInt(corpid));
                /* Getting Refund Records from billReceivebale corp*/
                BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
                List<BillReceiveCorp> listrefund = null;
                listrefund = billrecvdao.getRefundPendingBillReceiveAmountbyCorp(corporateid);
                mv1.addObject("corpName", corparatename);
                mv1.addObject("bankList", list6);
                mv1.addObject("CorpID", corpid);
                mv1.addObject("refundList", listrefund);
                return mv1;
            } else {
                System.out.println("Record not found");
                ModelAndView mv2 = new ModelAndView("successMsg");
                String Msg = "Please check-- there is no Verified Bank Transaction for Mapping for " + corparatename + "!";
                mv2.addObject("Msg", Msg);
                return mv2;
            }
        }
        String refundconfirm = request.getParameter("refundconfirm");
        if (refundconfirm != null) {
            String refundbankrowcount = request.getParameter("refundbankrowcount");
            int refuniqueid = 0;
            TempRefundBillCorp temprefbillcorp = new TempRefundBillCorp();
            TempRefundBillCorpDAO temprefbillcorpdao = new TempRefundBillCorpDAO();
            String uniqueid1;
            String refweekid1;
            String recvrefundid1;
            String uniqueId[] = request.getParameterValues("refuniqueNo");
            String refrowcount = request.getParameter("refrowcount");
            String corpid = request.getParameter("corpid");
            BillReceiveCorp billrecopr = new BillReceiveCorp();
            if (Integer.parseInt(refrowcount) >= 0) {
                for (int i = 1; i <= Integer.parseInt(refrowcount); i++) {
                    for (int j = 0; j < uniqueId.length; j++) {
                        uniqueid1 = request.getParameter("recvuniqueid" + i);
                        System.out.println("uniqueid1 count " + uniqueid1 + " " + j);
                        if (Integer.parseInt(uniqueid1) == Integer.parseInt(uniqueId[j])) {
                            refweekid1 = request.getParameter("refweekid" + i);
                            recvrefundid1 = request.getParameter("recvrefundid" + i);
                            System.out.println("recvrefundid1  " + recvrefundid1);
                            System.out.println("refweekid1  " + refweekid1);
                            int max = temprefbillcorpdao.getMaxUniqueID();
                            max = max + 1;
                            temprefbillcorp.setSlno(new BigDecimal(max));
                            corp.setCorporateId(Integer.parseInt(corpid));
                            billrecopr.setUniqueId(new BigDecimal(uniqueid1));
                            temprefbillcorp.setBillReceiveCorp(billrecopr);
                            temprefbillcorp.setRefundDate(new Date());
                            temprefbillcorp.setCheckerStatus("Pending");
                            temprefbillcorp.setCorporates(corp);
                            temprefbillcorp.setTotalAmount(new BigDecimal(recvrefundid1));
                            temprefbillcorp.setWeekid(new BigDecimal(refweekid1));
                            temprefbillcorpdao.NewTempRefundBillCorp(temprefbillcorp);
                        }
                    }
                }
            }// of temp refund
            String refbankrowcount = request.getParameter("refundbankrowcount");
            System.out.println("refbankrowcount count " + refbankrowcount);
            BankStatement bankstmy = new BankStatement();
            int maxid1 = 0;
            String settleAmtBnk;
            String remainBal1;
            String totalamt1;
            String banktransbal;
            String bankremarks;
            TempMapBankStatement tempbakst = new TempMapBankStatement();
            for (int k = 1; k <= Integer.parseInt(refbankrowcount); k++) {
                String refbankstmt1 = request.getParameter("refbankstmt" + k);
                String refbankstmt[] = request.getParameterValues("refbankstmt");
                if (Integer.parseInt(refbankstmt[0]) == Integer.parseInt(refbankstmt1)) {
                    bankstmy.setStmtId(new BigDecimal(refbankstmt1));
                    maxid1 = tempbakstdao.getMaxUniqueID();
                    maxid1 = maxid1 + 1;
                    tempbakst.setTempStmtid(new BigDecimal(maxid1));
                    tempbakst.setBankStatement(bankstmy);
                    banktransbal = request.getParameter("refremainBal" + k);
                    settleAmtBnk = request.getParameter("refsettleAmtBnk" + k);
                    bankremarks = request.getParameter("refremarks" + k);
                    totalamt1 = request.getParameter("reftotalamt" + k);
                    tempbakst.setMappedAmount(new BigDecimal(settleAmtBnk));
                    tempbakst.setRemarks(bankremarks);
                    tempbakst.setTransactionBalance(new BigDecimal(banktransbal));
                    tempbakst.setTransactionAmount(new BigDecimal(totalamt1));
                    tempbakst.setCorporateId(new BigDecimal(corpid));
                    tempbakst.setCheckerStatus("Pending");
                    tempbakst.setBillCategory("Refund");
                    tempbakstdao.NewTempMapBankStatement(tempbakst);
                }
            }
            ModelAndView mv3 = new ModelAndView("successMsg");
            mv3.addObject("Msg", "Succesfully Refund submitted for checker");
            return mv3;
        }//end of refund confirm button
        ModelAndView mv = new ModelAndView("viewRefundPayableList");
        TempRefundBillCorpDAO temrefunddao = new TempRefundBillCorpDAO();
        List<TempRefundBillCorp> list1 = null;
        list1 = temrefunddao.getAllPendingPayableTempRefundBillCorp();
        BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
        List<Object[]> list = null;
        List<Corporates> listcorp = new ArrayList<>();
        list = billrecvdao.getAllRefundPendingBillReceiveAmountbyCorp();
        Corporates corp1 = null;
        for (Object[] obj : list) {
            corp1 = new Corporates();
            BigDecimal bg = (BigDecimal) obj[0];
            corp.setCorporateId(bg.intValue());
            corp.setCorporateName((String) obj[1]);
            listcorp.add(corp);
        }
        mv.addObject("refundList", listcorp);
        mv.addObject("temprefundList", list1);
        return mv;
    }

    public ModelAndView welcomeSysadminChart(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("welcomeSysadminChart");
        List<BillPayableCorp> list = null;
        List<Object[]> list3 = new ArrayList<Object[]>();
        BillPayableCorpDAO billpadao = new BillPayableCorpDAO();
        list = billpadao.getPendingBillPaybyCorp();
        list3 = billpadao.getPendingBillPaybyCorpGroupbyWeek();
        BillReceiveCorpDAO billrecvdao = new BillReceiveCorpDAO();
        List<Object[]> list1 = null;
        list1 = billrecvdao.getPendingDisbursementbyCorp();
        List<BillReceiveCorp> listbillcorp = new ArrayList<>();
        BillReceiveCorp billrecv = null;
        Corporates corp = null;
        CorporatesDAO corpdao = new CorporatesDAO();
        for (Object[] obj : list1) {
            billrecv = new BillReceiveCorp();
            corp = new Corporates();
            billrecv.setBillingDate((Date) obj[0]);
            billrecv.setWeekId((BigDecimal) obj[1]);
            billrecv.setBillType((String) obj[2]);
            BigDecimal bg1 = (BigDecimal) obj[3];
            String corpname = corpdao.geCorpNamebyId(bg1.intValue());
            corp.setCorporateId((int) bg1.intValue());
            corp.setCorporateName(corpname);
            billrecv.setCorporates(corp);
            billrecv.setToalnet((BigDecimal) obj[4]);
            billrecv.setDisburseAmount((BigDecimal) obj[5]);
            billrecv.setPendingAmount((BigDecimal) obj[6]);
            listbillcorp.add(billrecv);
        }
        mv.addObject("billpayList", list);
        mv.addObject("billrecvList", listbillcorp);
        mv.addObject("billpayweekList", list3);
        return mv;
    }

    public ModelAndView welcomeReceivableBarChart(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException, JSONException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/sessionlogout.htm");
            return new ModelAndView(redirectView);
        }
        List<Object[]> list3 = new ArrayList<Object[]>();
        BillReceiveCorpDAO billpadao = new BillReceiveCorpDAO();
        list3 = billpadao.getReceivableBillRecvbyCorpGroupbyWeekforBarChart();
        JSONArray ja = new JSONArray();
        JSONObject result = new JSONObject();
        ja.put(list3);
        result.put("aaData", ja);
        response.getOutputStream().print(result.toString());
        return null;
    }

    public ModelAndView viewPendingRefundPayableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("viewPendingRefundPayableList");
        List<BillPayableCorp> list = null;
        List<Object[]> list3 = new ArrayList<Object[]>();
        BillPayableCorpDAO billpadao = new BillPayableCorpDAO();
        list = billpadao.getPendingRefundPayablelist();
        mv.addObject("pendrefundpayList", list);
        return mv;
    }

    public ModelAndView viewCheckerPendingPayableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/viewCheckerPendingPayableList");
        TempMappingBillBankDAO tmpmapdao = new TempMappingBillBankDAO();
        TempRefundBillCorpDAO temrefcorpdao = new TempRefundBillCorpDAO();
        List<TempMappingBillBank> list = null;
        list = tmpmapdao.getTempPendingMappingBillBankbyCorpListbyChecker("Pending");
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        for (TempMappingBillBank e : list) {
            myMap.put(e.getCorporates().getCorporateName(), e.getCorporates().getCorporateId());
        }
        List<TempRefundBillCorp> list1 = null;
        list1 = temrefcorpdao.getTempRefundPendingbyCorpListbychecker("Pending");
        if (list1 != null && !(list1.isEmpty())) {
            for (int n = 0; n < list1.size(); n++) {
                int flag = 0;
                for (int y = 0; y < list.size(); y++) {
                    if (list1.get(n).getCorporates().getCorporateId() == (list.get(y).getCorporates().getCorporateId())) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    myMap.put(list1.get(n).getCorporates().getCorporateName(), list1.get(n).getCorporates().getCorporateId());
                }
            }
        }
        System.out.println("list is " + list);
        mv.addObject("corpPendingList", myMap);
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
