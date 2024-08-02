
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.ApplicantDAO;
import sampada.DAO.BillAgcDetailsDAO;
import sampada.DAO.BillDsmDetailsDAO;
import sampada.DAO.BillEntityAgcDAO;
import sampada.DAO.BillEntityFrasDAO;
import sampada.DAO.BillFrasDetailsDAO;
import sampada.DAO.BillPayableCorpDAO;
import sampada.DAO.BillPayableEntityDsmDAO;
import sampada.DAO.BillPayableEntityRrasDAO;
import sampada.DAO.BillReceiveEntityDsmDAO;
import sampada.DAO.BillReceiveEntityRrasDAO;
import sampada.DAO.BillRrasDetailsDAO;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.EntityDAO;
import sampada.DAO.UserDetailsDAO;
import static sampada.controller.ReportController.getLastDay;
import sampada.pojo.BillAgcDetails;
import sampada.pojo.BillDsmDetails;
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityFras;
import sampada.pojo.BillFrasDetails;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillPayableEntityRras;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.BillReceiveEntityDsm;
import sampada.pojo.BillReceiveEntityRras;
import sampada.pojo.BillRrasDetails;
import sampada.pojo.Corporates;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.InterestDetails;
import sampada.pojo.PaymentDisbursement;
import sampada.pojo.UserDetails;

/**
 *
 * @author shubham
 */
public class ApplicantController extends MultiActionController {

    public ModelAndView welcomeApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {

            RedirectView redirectView = new RedirectView();

            redirectView.setContextRelative(true);

            redirectView.setUrl("/logout.htm");

            return new ModelAndView(redirectView);

        }

        ModelAndView mv = new ModelAndView("Applicant/welcomeApplicantPage");
        List<BillPayableCorp> list = null;
        ApplicantDAO billpaycorpdao = new ApplicantDAO();
        int Corpid = (int) request.getSession(false).getAttribute("corporateid");
        //  System.out.println("Corporate id in billProcessingPendingPayableList method is: "+Corpid);
        list = billpaycorpdao.getPendingBillPaybyCorpbyCorpId(Corpid);
        mv.addObject("PendningList", list);
        return mv;

    }

    public ModelAndView billProcessingIndexApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("Applicant/billProcessingIndexApplicant");
        return mv;
    }

    public ModelAndView billProcessingPendingPayableList(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("Applicant/billProcessingPendingPayableList");
        List<BillPayableCorp> list = null;
        ApplicantDAO billpaycorpdao = new ApplicantDAO();
        int Corpid = (int) request.getSession(false).getAttribute("corporateid");
        //  System.out.println("Corporate id in billProcessingPendingPayableList method is: "+Corpid);
        list = billpaycorpdao.getPendingBillPaybyCorpbyCorpId(Corpid);
        mv.addObject("PendningList", list);
        return mv;
    }

    public ModelAndView weeklyBillforApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("BillProcessing/weeklyBill");
        int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);//Now it will return "10"  
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
            ApplicantDAO appliDao = new ApplicantDAO();
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
                        billPay = appliDao.BillPayableCorpDsmlistforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
                        billReceive = appliDao.BillReceiveEntityDsmListforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
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
                            billPay2 = appliDao.getDiffBillPayforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                            billReceive2 = appliDao.getDiffBillReceiveforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
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
                                    String remarks = (String) result[5].toString();
                                    System.out.println(" entityId is " + entityId + " cappingCharges " + cappingCharges + "  additionalCharges " + additionalCharges + " signCharges " + signCharges + " payableCharges is " + payableCharges + " netDsm is " + netDsm + " entityName is " + entityName + " entityType is " + entityType);
                                    String[] billPayable = new String[]{entityType, entityName, payableCharges, cappingCharges, additionalCharges, signCharges, netDsm};
                                    billPay11.add(billPayable);
                                    entityNameListPay.add(entityName);
                                    entityTypeListPay.add(entityType);
                                    cappingChargesListPay.add(cappingCharges);
                                    additionalChargesListPay.add(additionalCharges);
                                    signChargesListPay.add(signCharges);
                                    payableChargesListPay.add(payableCharges);
                                    netDsmListPay.add(netDsm);
//                                    remarksListPay.add(remarks);
                                    mv3.addObject("entityNameListPay", entityNameListPay);
                                    mv3.addObject("entityTypeListPay", entityTypeListPay);
                                    mv3.addObject("cappingChargesListPay", cappingChargesListPay);
                                    mv3.addObject("additionalChargesListPay", additionalChargesListPay);
                                    mv3.addObject("signChargesListPay", signChargesListPay);
                                    mv3.addObject("payableChargesListPay", payableChargesListPay);
                                    mv3.addObject("netDsmListPay", netDsmListPay);
                                    mv3.addObject("billPay2", billPay2);
                                }
                            }
                            if (billReceive2 != null) {
                                System.out.println("billReceive2 size is " + billReceive2.size());
                                for (Object[] result : billReceive2) {
                                    BigDecimal entityId = (BigDecimal) result[0];
                                    String entityName = entDao.getEntityNameById(entityId);
                                    String entityType = entDao.getEntityTypeById(entityId);
                                    String cappingCharges = (String) result[1].toString();
                                    String additionalCharges = (String) result[2].toString();
                                    String signCharges = (String) result[3].toString();
                                    String recvCharges = (String) result[4].toString();
                                    String netDsm = (String) result[5].toString();
                                    String remarks = (String) result[5].toString();
                                    System.out.println(" entityId is " + entityId + " cappingCharges " + cappingCharges + "  additionalCharges " + additionalCharges + " signCharges " + signCharges + " recvCharges is " + recvCharges + " netDsm is " + netDsm + " entityName is " + entityName + " entityType is " + entityType);
                                    String[] billReceivable = new String[]{entityType, entityName, recvCharges, cappingCharges, additionalCharges, signCharges, netDsm};
                                    billReceive11.add(billReceivable);
                                    entityTypeListRecv.add(entityType);
                                    entityNameListRecv.add(entityName);
                                    cappingChargesListRecv.add(cappingCharges);
                                    additionalChargesListRecv.add(additionalCharges);
                                    signChargesListRecv.add(signCharges);
                                    receivableChargesListRecv.add(recvCharges);
                                    netDsmListRecv.add(netDsm);
//                                    remarksListRecv.add(remarks);
                                    mv3.addObject("entityTypeListRecv", entityTypeListRecv);
                                    mv3.addObject("entityNameListRecv", entityNameListRecv);
                                    mv3.addObject("cappingChargesListRecv", cappingChargesListRecv);
                                    mv3.addObject("additionalChargesListRecv", additionalChargesListRecv);
                                    mv3.addObject("signChargesListRecv", signChargesListRecv);
                                    mv3.addObject("receivableChargesListRecv", receivableChargesListRecv);
                                    mv3.addObject("netDsmListRecv", netDsmListRecv);
//                                    mv3.addObject("remarksListRecv", remarksListRecv);
                                    mv3.addObject("billReceive2", billReceive2);
                                }
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
                    mv3.addObject("billDsmInfo", billDsmInfo);
                    mv3.addObject("billReceive11", billReceive11);
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
                            billPay = appliDao.BillPayableCorpDsmlistforApplicant(weekId, yearId, revNo, Integer.parseInt(Corpid));
                            billReceive = appliDao.BillReceiveEntityDsmListforApplicant(weekId, yearId, revNo, Integer.parseInt(Corpid));
                            if (billPay != null) {
                                System.out.println("billPay size is " + billPay.size());
                            }
                            if (billReceive != null) {
                                System.out.println("billPay size is " + billReceive.size());
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
                    } else {
                        ModelAndView mv2 = new ModelAndView("successMsg");
                        String Msg = "Revised Bill not available for the selected parameters!!";
                        mv2.addObject("Msg", Msg);
                        return mv2;
                    }
                }
                mv1.addObject("billDsmInfo", billDsmInfo);
                mv1.addObject("billPay", billPay);
                mv1.addObject("billReceive", billReceive);
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
                        billPay = appliDao.BillPayableCorpRraslistforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
                        billReceive = appliDao.BillReceiveEntityRrasListforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
                        if (billPay != null) {
                            System.out.println("billPay size is " + billPay.size());
                            List<Object[]> sumListPay = appliDao.getAllSumBillPayableforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
                            System.out.println("######sumListPay size is " + sumListPay.size());
                            mv1.addObject("sumListPay", sumListPay);
                        }
                        if (billReceive != null) {
                            System.out.println("billReceive size is " + billReceive.size());
                            List<Object[]> sumListReceive = appliDao.getAllSumBillReceiveforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
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
                            billPay2 = appliDao.getDiffBillPayRRASforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                            billReceive2 = appliDao.getDiffBillReceiveRRASforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
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
                                sumListPay = appliDao.getAllSumBillPayableforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                                List<Object[]> sumListPay1 = appliDao.getAllSumBillPayableforApplicant(weekId, yearId, revisionNum.subtract(BigDecimal.ONE), Integer.parseInt(Corpid));
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
                                sumListReceive = appliDao.getAllSumBillReceiveforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                                System.out.println("######sumListReceive size is " + sumListReceive.size());
                                List<Object[]> sumListReceive1 = appliDao.getAllSumBillReceiveforApplicant(weekId, yearId, revisionNum.subtract(BigDecimal.ONE), Integer.parseInt(Corpid));
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
                            billPay = appliDao.BillPayableCorpRraslistforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                            EntityDAO entDao = new EntityDAO();
                            billReceive = appliDao.BillReceiveEntityRrasListforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                            if (billPay != null) {
                                System.out.println("billPay size is " + billPay.size());
                                List<Object[]> sumListPay = appliDao.getAllSumBillPayableforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                                System.out.println("######sumListPay size is " + sumListPay.size());
                                mv1.addObject("sumListPay", sumListPay);
                            }
                            if (billReceive != null) {
                                System.out.println("billReceive2 size is " + billReceive.size());
                                List<Object[]> sumListReceive = appliDao.getAllSumBillReceiveforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
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
                        billPayRecv = appliDao.BillCorpAgclistforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
                        if (billPayRecv != null) {
                            System.out.println("billPayRecv size is " + billPayRecv.size());
                            List<Object[]> sumListPayRecv = appliDao.getAllSumBillPayableAGCforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
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
                            billPayReceive2 = appliDao.getDiffBillPayAgcforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
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
                                sumListPay = appliDao.getAllSumBillPayableAGCforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                                List<Object[]> sumListPay1 = appliDao.getAllSumBillPayableAGCforApplicant(weekId, yearId, revisionNum.subtract(BigDecimal.ONE), Integer.parseInt(Corpid));
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
                            billPayRecv = appliDao.BillCorpAgclistforApplicant(weekId, yearId, revNo, Integer.parseInt(Corpid));
                            if (billPayRecv != null) {
                                System.out.println("billPayReceive size is " + billPayRecv.size());
                                List<Object[]> sumListPayRecv = appliDao.getAllSumBillPayableAGCforApplicant(weekId, yearId, revNo, Integer.parseInt(Corpid));
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
                        billPayRecv = appliDao.BillCorpFraslistforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
                        if (billPayRecv != null) {
                            System.out.println("billPayRecv size is " + billPayRecv.size());
                            List<Object[]> sumListPayRecv = appliDao.getAllSumBillFRASforApplicant(weekId, yearId, BigDecimal.ZERO, Integer.parseInt(Corpid));
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
                            billPayReceive2 = appliDao.getDiffBillPayFRASforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
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
                                sumListPay = appliDao.getAllSumBillFRASforApplicant(weekId, yearId, revisionNum, Integer.parseInt(Corpid));
                                List<Object[]> sumListPay1 = appliDao.getAllSumBillFRASforApplicant(weekId, yearId, revisionNum.subtract(BigDecimal.ONE), Integer.parseInt(Corpid));
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
                            billPayRecv = appliDao.BillCorpFraslistforApplicant(weekId, yearId, revNo, Integer.parseInt(Corpid));
                            if (billPayRecv != null) {
                                System.out.println("billPayReceive size is " + billPayRecv.size());
                                List<Object[]> sumListPayRecv = appliDao.getAllSumBillFRASforApplicant(weekId, yearId, revNo, Integer.parseInt(Corpid));
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

    public ModelAndView paymentDisbursementIndexApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("Applicant/paymentDisbursementIndexApplicant");
        return mv;
    }

    public ModelAndView disbursementPendingListforApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {

            RedirectView redirectView = new RedirectView();

            redirectView.setContextRelative(true);

            redirectView.setUrl("/logout.htm");

            return new ModelAndView(redirectView);

        }

        ModelAndView mv = new ModelAndView("disbursementPendingList");
        int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        
        ApplicantDAO applidao = new ApplicantDAO();

        List<Object[]> list = null;

        list = applidao.getPendingDisbursementbyCorpforApplicant(Integer.parseInt(Corpid));
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

            listbillcorp.add(billrecv);

        }

        mv.addObject("notpaidList", listbillcorp);

        return mv;

    }

    public ModelAndView newPaymentDisbursedforApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("newPaymentDisbursedbyRLDC");
        int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        String bName = request.getParameter("bName");

        if (bName != null) {

            System.out.print("Inside bname button viewdisbursed");
            List<PaymentDisbursement> list1 = null;

            ApplicantDAO paydisdao = new ApplicantDAO();
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(startdate);
            Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(enddate);
            ModelAndView mv1 = new ModelAndView("viewPaymentDisbursedbyRLDC");
            list1 = paydisdao.getDisbursementDetailsbyFromDateTodateforApplicant(date1, date2, Integer.parseInt(Corpid));
            mv1.addObject("paidList", list1);
            return mv1;
        }
        return mv;
    }

    public ModelAndView applicantResetPassword(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mav1 = new ModelAndView("Applicant/viewResetPasswordbyApplicant");

        // String loginid1 = (String) request.getSession(false).getAttribute("loginid");
        String loginid = (String) request.getSession(false).getAttribute("loginid");
// System.out.println("login id obtained is" + loginid);
        if (request.getParameter("bname") != null) {
            String newpasswd = request.getParameter("confpasswd");
            String currpasswd = request.getParameter("curpasswd");
            UserDetailsDAO usedao = new UserDetailsDAO();

         //   DesEncrypter encrypter = new DesEncrypter();
            //    String decryptedpasswd = encrypter.encrypt(newpasswd);
            usedao.updateUserPassword(loginid, newpasswd);
            mav1.addObject("msg", "Password is updated Succesfully ");
        }
        return mav1;
    }

    public ModelAndView checkForUserPassword(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        String sname = null;
        String curpasswd = request.getParameter("curpasswd");
        System.out.print("curpasswd" + curpasswd);
        UserDetailsDAO usrdao = new UserDetailsDAO();
        String loginid = (String) request.getSession(false).getAttribute("loginid");
        List<UserDetails> list;
        UserDetails usersList = null;
//            DesEncrypter encrypter1 = new DesEncrypter();
//           String decryptedpasswd1 = encrypter1.encrypt(curpasswd);
//            System.out.print("decryptedpasswd"+decryptedpasswd1 );
        System.out.println("loginid,curpasswd" + loginid + curpasswd);
        // list= usrdao.checkUserPassword(loginid, curpasswd);
//        System.out.print("elementName"+list.size());
        usersList = usrdao.CheckUserDetails(loginid);
        String decrypted = usersList.getPassword();
      //  DesEncrypter encrypter = new DesEncrypter();
        //   String decrypted = encrypter.decrypt(password);
        System.out.println(" password is" + decrypted);

        if (decrypted.equalsIgnoreCase(curpasswd)) {
            System.out.println("matched");
            response.getOutputStream().print("matched");

        } else {
            System.out.println("Not matched");
            response.getOutputStream().print("Not Matched");

        }

        return null;
    }
    
    public ModelAndView reportsIndexApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("Applicant/reportsIndexApplicant");
        return mv;

    }

     
     public ModelAndView finalreportApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

          int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        String dbType = request.getParameter("dbType");

        String type = request.getParameter("TYPE");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            if (dbType.equals("MONTHLY")) {
                String monthYear = request.getParameter("monthYear");
                System.out.println("monthYear is " + monthYear);
                String[] splitStr = monthYear.split("/");
                System.out.println(splitStr.length);
                String monthName = splitStr[0];
                String year = splitStr[1];
                yeari = Integer.parseInt(year);
                int monthNamei = Integer.parseInt(monthName);
                String first_day = "01";
                String last_day = getLastDay(year, monthName);
                System.out.println("month obtained is " + monthName + "year obtained is " + year);

                String sDate1 = first_day + monthName + year;
                String sDate2 = last_day + monthName + year;

                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("QUARTERLY")) {
                String Yearq = request.getParameter("yearq");
                String quar = request.getParameter("quar");
                yeari = Integer.parseInt(Yearq);
                System.out.println("Yearq is " + Yearq);
                System.out.println("Yeari is " + yeari);
                System.out.println("quar is " + quar);
                String sDate1 = null;
                String sDate2 = null;
                if (quar.equals("quar1")) {
                    sDate1 = "0101" + Yearq;
                    sDate2 = "3103" + Yearq;
                }
                if (quar.equals("quar2")) {
                    sDate1 = "0104" + Yearq;
                    sDate2 = "3006" + Yearq;
                }
                if (quar.equals("quar3")) {
                    sDate1 = "0107" + Yearq;
                    sDate2 = "3009" + Yearq;
                }
                if (quar.equals("quar4")) {
                    sDate1 = "0110" + Yearq;
                    sDate2 = "3112" + Yearq;
                }
                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/monthlyBillTypewiseReport");
            ApplicantDAO billPayCorpDao = new ApplicantDAO();

            List<BillPayableCorp> queryList1 = billPayCorpDao.BillPayableCorplistMonthlyApplicant(new BigDecimal(week_id1), new BigDecimal(week_id2), type, new BigDecimal(yeari), Integer.parseInt(Corpid));
            System.out.println("queryList1 is " + queryList1);

            model.addObject("queryList1", queryList1);

            model.addObject("yeari", yeari);
            model.addObject("week_id1", week_id1);
            model.addObject("week_id2", week_id2);
            model.addObject("type", type);

            //Object[] row = (Object[]) queryList.get(0);
            // System.out.println("Element "+Arrays.toString(row));  
            // int name= Integer.parseInt(String.valueOf(row[1]));
            //  System.out.println("name =" + (name-1));
            return model;

        }

        String title = "Corporate payable final";
        mv.addObject("title", title);
        return mv;

    }

     
     public ModelAndView recfinalreportApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
  int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        String dbType = request.getParameter("dbType");

        String type = request.getParameter("TYPE");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            if (dbType.equals("MONTHLY")) {
                String monthYear = request.getParameter("monthYear");
                System.out.println("monthYear is " + monthYear);
                String[] splitStr = monthYear.split("/");
                System.out.println(splitStr.length);
                String monthName = splitStr[0];
                String year = splitStr[1];
                yeari = Integer.parseInt(year);
                int monthNamei = Integer.parseInt(monthName);
                String first_day = "01";
                String last_day = getLastDay(year, monthName);
                System.out.println("month obtained is " + monthName + "year obtained is " + year);

                String sDate1 = first_day + monthName + year;
                String sDate2 = last_day + monthName + year;

                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("QUARTERLY")) {
                String Yearq = request.getParameter("yearq");
                String quar = request.getParameter("quar");
                yeari = Integer.parseInt(Yearq);
                System.out.println("Yearq is " + Yearq);
                System.out.println("Yeari is " + yeari);
                System.out.println("quar is " + quar);
                String sDate1 = null;
                String sDate2 = null;
                if (quar.equals("quar1")) {
                    sDate1 = "0101" + Yearq;
                    sDate2 = "3103" + Yearq;
                }
                if (quar.equals("quar2")) {
                    sDate1 = "0104" + Yearq;
                    sDate2 = "3006" + Yearq;
                }
                if (quar.equals("quar3")) {
                    sDate1 = "0107" + Yearq;
                    sDate2 = "3009" + Yearq;
                }
                if (quar.equals("quar4")) {
                    sDate1 = "0110" + Yearq;
                    sDate2 = "3112" + Yearq;
                }
                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/monthlyRecvBillTypeWiseReport");
            ApplicantDAO billRecvCoprDao = new ApplicantDAO();

            List<BillReceiveCorp> queryList1 = billRecvCoprDao.BillReceiveCorplistMonthlyApplicant(new BigDecimal(week_id1), new BigDecimal(week_id2), type, new BigDecimal(yeari), Integer.parseInt(Corpid));

            model.addObject("queryList1", queryList1);

            model.addObject("yeari", yeari);
            model.addObject("week_id1", week_id1);
            model.addObject("week_id2", week_id2);
            model.addObject("type", type);

            //Object[] row = (Object[]) queryList.get(0);
            // System.out.println("Element "+Arrays.toString(row));  
            // int name= Integer.parseInt(String.valueOf(row[1]));
            //  System.out.println("name =" + (name-1));
            return model;

        }

        String title = "Disbursement";
        mv.addObject("title", title);
        return mv;

    }

     public ModelAndView getallrefundPayableApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
  int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        String dbType = request.getParameter("dbType");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            if (dbType.equals("MONTHLY")) {
                String monthYear = request.getParameter("monthYear");
                System.out.println("monthYear is " + monthYear);
                String[] splitStr = monthYear.split("/");
                System.out.println(splitStr.length);
                String monthName = splitStr[0];
                String year = splitStr[1];
                yeari = Integer.parseInt(year);
                int monthNamei = Integer.parseInt(monthName);
                String first_day = "01";
                String last_day = getLastDay(year, monthName);
                System.out.println("month obtained is " + monthName + "year obtained is " + year);

                String sDate1 = first_day + monthName + year;
                String sDate2 = last_day + monthName + year;

                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("QUARTERLY")) {
                String Yearq = request.getParameter("yearq");
                String quar = request.getParameter("quar");
                yeari = Integer.parseInt(Yearq);
                System.out.println("Yearq is " + Yearq);
                System.out.println("Yeari is " + yeari);
                System.out.println("quar is " + quar);
                String sDate1 = null;
                String sDate2 = null;
                if (quar.equals("quar1")) {
                    sDate1 = "0101" + Yearq;
                    sDate2 = "3103" + Yearq;
                }
                if (quar.equals("quar2")) {
                    sDate1 = "0104" + Yearq;
                    sDate2 = "3006" + Yearq;
                }
                if (quar.equals("quar3")) {
                    sDate1 = "0107" + Yearq;
                    sDate2 = "3009" + Yearq;
                }
                if (quar.equals("quar4")) {
                    sDate1 = "0110" + Yearq;
                    sDate2 = "3112" + Yearq;
                }
                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/allrefundbills");
            ApplicantDAO rpt = new ApplicantDAO();

            List<BillPayableCorp> queryList1 = rpt.getRefundBillPayableCorplistforApplicant(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari), Integer.parseInt(Corpid));

//            List<TempRefundBillCorp> queryList1 = rpt.getVerifiedRefundDetaila();
            System.out.println("queryList1 is " + queryList1);

            model.addObject("queryList1", queryList1);
            model.addObject("week_id1", week_id1);
            model.addObject("week_id2", week_id2);
            model.addObject("yeari", yeari);
            return model;

        }

        String title = "Refund Details";
        mv.addObject("title", title);
        return mv;

    }

     
      public ModelAndView getallrefundReceivableApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
 int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        String dbType = request.getParameter("dbType");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            if (dbType.equals("MONTHLY")) {
                String monthYear = request.getParameter("monthYear");
                System.out.println("monthYear is " + monthYear);
                String[] splitStr = monthYear.split("/");
                System.out.println(splitStr.length);
                String monthName = splitStr[0];
                String year = splitStr[1];
                yeari = Integer.parseInt(year);
                int monthNamei = Integer.parseInt(monthName);
                String first_day = "01";
                String last_day = getLastDay(year, monthName);
                System.out.println("month obtained is " + monthName + "year obtained is " + year);

                String sDate1 = first_day + monthName + year;
                String sDate2 = last_day + monthName + year;

                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("QUARTERLY")) {
                String Yearq = request.getParameter("yearq");
                String quar = request.getParameter("quar");
                yeari = Integer.parseInt(Yearq);
                System.out.println("Yearq is " + Yearq);
                System.out.println("Yeari is " + yeari);
                System.out.println("quar is " + quar);
                String sDate1 = null;
                String sDate2 = null;
                if (quar.equals("quar1")) {
                    sDate1 = "0101" + Yearq;
                    sDate2 = "3103" + Yearq;
                }
                if (quar.equals("quar2")) {
                    sDate1 = "0104" + Yearq;
                    sDate2 = "3006" + Yearq;
                }
                if (quar.equals("quar3")) {
                    sDate1 = "0107" + Yearq;
                    sDate2 = "3009" + Yearq;
                }
                if (quar.equals("quar4")) {
                    sDate1 = "0110" + Yearq;
                    sDate2 = "3112" + Yearq;
                }
                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/allrefundbillsrec");
            ApplicantDAO rpt = new ApplicantDAO();

            List<BillReceiveCorp> queryList1 = rpt.getRefundBillReceivableCorplistforApplicant(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari), Integer.parseInt(Corpid));

//            List<TempRefundBillCorp> queryList1 = rpt.getVerifiedRefundDetaila();
            System.out.println("queryList1 is " + queryList1);

            model.addObject("queryList1", queryList1);
            model.addObject("week_id1", week_id1);
            model.addObject("week_id2", week_id2);
            model.addObject("yeari", yeari);
            return model;

        }

        String title = "Refund Details";
        mv.addObject("title", title);
        return mv;

    }
      
       public ModelAndView getallinterestPayableApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
 int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        String dbType = request.getParameter("dbType");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            if (dbType.equals("MONTHLY")) {
                String monthYear = request.getParameter("monthYear");
                System.out.println("monthYear is " + monthYear);
                String[] splitStr = monthYear.split("/");
                System.out.println(splitStr.length);
                String monthName = splitStr[0];
                String year = splitStr[1];
                yeari = Integer.parseInt(year);
                int monthNamei = Integer.parseInt(monthName);
                String first_day = "01";
                String last_day = getLastDay(year, monthName);
                System.out.println("month obtained is " + monthName + "year obtained is " + year);

                String sDate1 = first_day + monthName + year;
                String sDate2 = last_day + monthName + year;

                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("QUARTERLY")) {
                String Yearq = request.getParameter("yearq");
                String quar = request.getParameter("quar");
                yeari = Integer.parseInt(Yearq);
                System.out.println("Yearq is " + Yearq);
                System.out.println("Yeari is " + yeari);
                System.out.println("quar is " + quar);
                String sDate1 = null;
                String sDate2 = null;
                if (quar.equals("quar1")) {
                    sDate1 = "0101" + Yearq;
                    sDate2 = "3103" + Yearq;
                }
                if (quar.equals("quar2")) {
                    sDate1 = "0104" + Yearq;
                    sDate2 = "3006" + Yearq;
                }
                if (quar.equals("quar3")) {
                    sDate1 = "0107" + Yearq;
                    sDate2 = "3009" + Yearq;
                }
                if (quar.equals("quar4")) {
                    sDate1 = "0110" + Yearq;
                    sDate2 = "3112" + Yearq;
                }
                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/allinterest");
            ApplicantDAO rpt = new ApplicantDAO();

            List<InterestDetails> queryList1 = rpt.getInterestPayableDetailsforApplicant(new BigDecimal(week_id1), new BigDecimal(week_id2), Integer.toString(yeari),Integer.parseInt(Corpid));

//            List<TempRefundBillCorp> queryList1 = rpt.getVerifiedRefundDetaila();
            System.out.println("queryList1 is " + queryList1);

            model.addObject("queryList1", queryList1);
            model.addObject("week_id1", week_id1);
            model.addObject("week_id2", week_id2);
            model.addObject("yeari", yeari);
            return model;

        }

        String title = "Interest Payable Details";
        mv.addObject("title", title);
        return mv;

    }

  public ModelAndView getallinterestReceivableApplicant(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
 int Corp_id = (int) request.getSession(false).getAttribute("corporateid");
        String Corpid=String.valueOf(Corp_id);
        String dbType = request.getParameter("dbType");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            if (dbType.equals("MONTHLY")) {
                String monthYear = request.getParameter("monthYear");
                System.out.println("monthYear is " + monthYear);
                String[] splitStr = monthYear.split("/");
                System.out.println(splitStr.length);
                String monthName = splitStr[0];
                String year = splitStr[1];
                yeari = Integer.parseInt(year);
                int monthNamei = Integer.parseInt(monthName);
                String first_day = "01";
                String last_day = getLastDay(year, monthName);
                System.out.println("month obtained is " + monthName + "year obtained is " + year);

                String sDate1 = first_day + monthName + year;
                String sDate2 = last_day + monthName + year;

                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("QUARTERLY")) {
                String Yearq = request.getParameter("yearq");
                String quar = request.getParameter("quar");
                yeari = Integer.parseInt(Yearq);
                System.out.println("Yearq is " + Yearq);
                System.out.println("Yeari is " + yeari);
                System.out.println("quar is " + quar);
                String sDate1 = null;
                String sDate2 = null;
                if (quar.equals("quar1")) {
                    sDate1 = "0101" + Yearq;
                    sDate2 = "3103" + Yearq;
                }
                if (quar.equals("quar2")) {
                    sDate1 = "0104" + Yearq;
                    sDate2 = "3006" + Yearq;
                }
                if (quar.equals("quar3")) {
                    sDate1 = "0107" + Yearq;
                    sDate2 = "3009" + Yearq;
                }
                if (quar.equals("quar4")) {
                    sDate1 = "0110" + Yearq;
                    sDate2 = "3112" + Yearq;
                }
                System.out.println("first day of month is" + sDate1);
                System.out.println("last day of month is" + sDate2);
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                Date date2 = new SimpleDateFormat("ddMMyyyy").parse(sDate2);
                System.out.println("first date " + date1);
                System.out.println("last date " + date2);

                Calendar cl = Calendar.getInstance();
                cl.setFirstDayOfWeek(Calendar.SUNDAY);
                cl.setMinimalDaysInFirstWeek(7);

                cl.setTime(date1);
                week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/allinterestrec");
            ApplicantDAO rpt = new ApplicantDAO();

            List<DisbursedInterestDetails> queryList1 = rpt.getInterestReceivableDetailsforApplicant(new BigDecimal(week_id1), new BigDecimal(week_id2), Integer.toString(yeari),Integer.parseInt(Corpid));

//            List<TempRefundBillCorp> queryList1 = rpt.getVerifiedRefundDetaila();
            System.out.println("queryList1 is " + queryList1);

            model.addObject("queryList1", queryList1);
            model.addObject("week_id1", week_id1);
            model.addObject("week_id2", week_id2);
            model.addObject("yeari", yeari);
            return model;

        }

        String title = "Interest Receivable Details";
        mv.addObject("title", title);
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
