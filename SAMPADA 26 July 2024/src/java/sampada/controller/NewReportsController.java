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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.CsdfDetailsDAO;

import sampada.DAO.NewBankStatementDAO;
import sampada.DAO.MappingRefundBankDAO;
import sampada.DAO.NewBillPayableCorpDAO;
import sampada.DAO.NewBillReceiveCorpDAO;
import sampada.DAO.DefaultLocDetailsDAO;
import sampada.DAO.DisbursedInterestDetailsDAO;
import sampada.DAO.InterestDetailsDAO;
import sampada.DAO.MappingBillBankDAO;
import sampada.DAO.MappingInterestBankDAO;
import sampada.DAO.NewMappingInterestBankDAO;
import sampada.DAO.NewReportDAO;
import sampada.DAO.PaymentDisbursementDAO;
import sampada.DAO.PaymentInterestDisbursementDAO;
import sampada.DAO.PoolToIntDAO;
import sampada.DAO.ReportDAO;
import sampada.DAO.TempRefundBillCorpDAO;
import sampada.DAO.miscDisbursementDAO;

import static sampada.controller.ReportController.getLastDay;
import sampada.pojo.BankStatement;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.Corporates;
import sampada.pojo.CsdfDetails;
import sampada.pojo.DefaultLocDetails;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.InterestDetails;
import sampada.pojo.MappingBillBank;
import sampada.pojo.MappingInterestBank;
import sampada.pojo.MappingRefundBank;
import sampada.pojo.MiscDisbursement;
import sampada.pojo.PaymentDisbursement;
import sampada.pojo.PaymentInterestDisbursement;
import sampada.pojo.PoolToInt;
import sampada.pojo.TempRefundBillCorp;

/**
 *
 * @author Kaustubh
 */
public class NewReportsController extends MultiActionController {

    public ModelAndView getMISPooldetail(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("NewReport/getMISPooldetail");

        String date = request.getParameter("monthYear");
        String bsubmit = request.getParameter("generate");
        NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
        NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();

        if (bsubmit != null) {
            System.out.println("Selected date is _______" + date);

            ModelAndView mv1 = new ModelAndView("NewReport/MISPooldetail");
            String datearr[] = date.split("/");
            String year1 = datearr[1];
            String year = null;
            if (Integer.valueOf(datearr[0]) < 4) {
                year = String.valueOf(Integer.valueOf(year1) - 1);
            } else {
                year = year1;
            }
            String startdate = "01-04" + "-" + year;
            String enddate = getLastDay(String.valueOf(Integer.valueOf(year) + 1), datearr[0]) + "-" + datearr[0] + "-" + year1;
            System.out.println("Startdate is " + startdate + " Enddate is " + enddate + " Year is : " + year);

            List<BillPayableCorp> dsmpaylist = bpdao.getDSMpayable(startdate, enddate, year);
            List<BillPayableCorp> rraspaylist = bpdao.getRRASpayable(startdate, enddate, year);
            List<BillPayableCorp> agcpaylist = bpdao.getAGCpayable(startdate, enddate, year);
            List<BillReceiveCorp> dsmreclist = brdao.getDSMreceive(startdate, enddate, year);
            List<BillReceiveCorp> rrasreclist = brdao.getRRASreceive(startdate, enddate, year);
            List<BillReceiveCorp> agcreclist = brdao.getAGCreceive(startdate, enddate, year);

            List<BigDecimal> dsmweeks = bpdao.getUniqueWeekIds("DSM", year);
            List<BigDecimal> rrasweeks = bpdao.getUniqueWeekIds("RRAS", year);
            List<BigDecimal> agcweeks = bpdao.getUniqueWeekIds("AGC", year);

            //for(int i=0; i<dsmweeks.size(); i++)
            //   System.out.println("Week : "+dsmweeks.get(i));
            BigDecimal dsm_maxweek = BigDecimal.ZERO;
            BigDecimal rras_maxweek = BigDecimal.ZERO;
            BigDecimal agc_maxweek = BigDecimal.ZERO;

            if (dsmweeks.size() > 0) {
                dsm_maxweek = dsmweeks.get(dsmweeks.size() - 1);
            }

            if (rrasweeks.size() > 0) {
                rras_maxweek = rrasweeks.get(rrasweeks.size() - 1);
            }

            if (agcweeks.size() > 0) {
                agc_maxweek = agcweeks.get(agcweeks.size() - 1);
            }

            BigDecimal[] dsm_a = new BigDecimal[dsm_maxweek.intValue()];
            BigDecimal[] dsm_b = new BigDecimal[dsm_maxweek.intValue()];
            BigDecimal[] dsm_c = new BigDecimal[dsm_maxweek.intValue()];
            BigDecimal[] dsm_d = new BigDecimal[dsm_maxweek.intValue()];
            BigDecimal[] dsm_e = new BigDecimal[dsm_maxweek.intValue()];
            BigDecimal[] dsm_f = new BigDecimal[dsm_maxweek.intValue()];
            Arrays.fill(dsm_a, BigDecimal.ZERO);
            Arrays.fill(dsm_b, BigDecimal.ZERO);
            Arrays.fill(dsm_c, BigDecimal.ZERO);
            Arrays.fill(dsm_d, BigDecimal.ZERO);
            Arrays.fill(dsm_e, BigDecimal.ZERO);
            Arrays.fill(dsm_f, BigDecimal.ZERO);

            BigDecimal[] rras_a = new BigDecimal[rras_maxweek.intValue()];
            BigDecimal[] rras_b = new BigDecimal[rras_maxweek.intValue()];
            BigDecimal[] rras_c = new BigDecimal[rras_maxweek.intValue()];
            BigDecimal[] rras_d = new BigDecimal[rras_maxweek.intValue()];
            BigDecimal[] rras_e = new BigDecimal[rras_maxweek.intValue()];
            BigDecimal[] rras_f = new BigDecimal[rras_maxweek.intValue()];
            Arrays.fill(rras_a, BigDecimal.ZERO);
            Arrays.fill(rras_b, BigDecimal.ZERO);
            Arrays.fill(rras_c, BigDecimal.ZERO);
            Arrays.fill(rras_d, BigDecimal.ZERO);
            Arrays.fill(rras_e, BigDecimal.ZERO);
            Arrays.fill(rras_f, BigDecimal.ZERO);

            BigDecimal[] agc_a = new BigDecimal[agc_maxweek.intValue()];
            BigDecimal[] agc_b = new BigDecimal[agc_maxweek.intValue()];
            BigDecimal[] agc_c = new BigDecimal[agc_maxweek.intValue()];
            BigDecimal[] agc_d = new BigDecimal[agc_maxweek.intValue()];
            BigDecimal[] agc_e = new BigDecimal[agc_maxweek.intValue()];
            BigDecimal[] agc_f = new BigDecimal[agc_maxweek.intValue()];
            Arrays.fill(agc_a, BigDecimal.ZERO);
            Arrays.fill(agc_b, BigDecimal.ZERO);
            Arrays.fill(agc_c, BigDecimal.ZERO);
            Arrays.fill(agc_d, BigDecimal.ZERO);
            Arrays.fill(agc_e, BigDecimal.ZERO);
            Arrays.fill(agc_f, BigDecimal.ZERO);

            for (int j = 0; j < dsmweeks.size(); j++) {

                BigDecimal w = dsmweeks.get(j);

                for (BillPayableCorp o : dsmpaylist) {
                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && !o.getBillStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        dsm_a[j] = dsm_a[j].add(o.getRevisedpaybale());
                    } else if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0 && o.getWeekId().compareTo(w) == 0) {
                        dsm_a[j] = dsm_a[j].add(o.getTotalnet());
                    }

                    if (o.getWeekId().compareTo(w) == 0 && !o.getBillStatus().equalsIgnoreCase("REFUND")) {
                        dsm_b[j] = dsm_b[j].add(o.getPaidAmount());
                        dsm_c[j] = dsm_c[j].add(o.getPendingAmount());
                    }

                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && o.getBillStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        dsm_d[j] = dsm_d[j].add(o.getRevisedrefund());
                        dsm_e[j] = dsm_e[j].add(o.getAdjustmentAmount());
                        dsm_f[j] = dsm_f[j].add(o.getPendingAmount());
                    }
                }

                for (BillReceiveCorp o : dsmreclist) {
                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && o.getDisburseStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        dsm_a[j] = dsm_a[j].add(o.getRevisedrefund());
                        dsm_b[j] = dsm_b[j].add(o.getAdjustmentAmount());
                        dsm_c[j] = dsm_c[j].add(o.getPendingAmount());
                    }

                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && !o.getDisburseStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        dsm_d[j] = dsm_d[j].add(o.getRevisedpaybale());
                    } else if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0 && o.getWeekId().compareTo(w) == 0) {
                        dsm_d[j] = dsm_d[j].add(o.getToalnet());
                    }

                    if (o.getWeekId().compareTo(w) == 0 && !o.getDisburseStatus().equalsIgnoreCase("REFUND")) {
                        dsm_e[j] = dsm_e[j].add(o.getDisburseAmount());
                        dsm_f[j] = dsm_f[j].add(o.getPendingAmount());
                    }
                }
            }

            for (int j = 0; j < rrasweeks.size(); j++) {

                BigDecimal w = rrasweeks.get(j);

                for (BillPayableCorp o : rraspaylist) {
                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && !o.getBillStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        rras_a[j] = rras_a[j].add(o.getRevisedpaybale());
                    } else if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0 && o.getWeekId().compareTo(w) == 0) {
                        rras_a[j] = rras_a[j].add(o.getTotalnet());
                    }

                    if (o.getWeekId().compareTo(w) == 0 && !o.getBillStatus().equalsIgnoreCase("REFUND")) {
                        rras_b[j] = rras_b[j].add(o.getPaidAmount());
                        rras_c[j] = rras_c[j].add(o.getPendingAmount());
                    }

                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && o.getBillStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        rras_d[j] = rras_d[j].add(o.getRevisedrefund());
                        rras_e[j] = rras_e[j].add(o.getAdjustmentAmount());
                        rras_f[j] = rras_f[j].add(o.getPendingAmount());
                    }
                }

                for (BillReceiveCorp o : rrasreclist) {
                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && o.getDisburseStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        rras_a[j] = rras_a[j].add(o.getRevisedrefund());
                        rras_b[j] = rras_b[j].add(o.getAdjustmentAmount());
                        rras_c[j] = rras_c[j].add(o.getPendingAmount());
                    }

                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && !o.getDisburseStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        rras_d[j] = rras_d[j].add(o.getRevisedpaybale());
                    } else if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0 && o.getWeekId().compareTo(w) == 0) {
                        rras_d[j] = rras_d[j].add(o.getToalnet());
                    }

                    if (o.getWeekId().compareTo(w) == 0 && !o.getDisburseStatus().equalsIgnoreCase("REFUND")) {
                        rras_e[j] = rras_e[j].add(o.getDisburseAmount());
                        rras_f[j] = rras_f[j].add(o.getPendingAmount());
                    }
                }
            }

            for (int j = 0; j < agcweeks.size(); j++) {

                BigDecimal w = agcweeks.get(j);

                for (BillPayableCorp o : agcpaylist) {
                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && !o.getBillStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        agc_a[j] = agc_a[j].add(o.getRevisedpaybale());
                    } else if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0 && o.getWeekId().compareTo(w) == 0) {
                        agc_a[j] = agc_a[j].add(o.getTotalnet());
                    }

                    if (o.getWeekId().compareTo(w) == 0 && !o.getBillStatus().equalsIgnoreCase("REFUND")) {
                        agc_b[j] = agc_b[j].add(o.getPaidAmount());
                        agc_c[j] = agc_c[j].add(o.getPendingAmount());
                    }

                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && o.getBillStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        agc_d[j] = agc_d[j].add(o.getRevisedrefund());
                        agc_e[j] = agc_e[j].add(o.getAdjustmentAmount());
                        agc_f[j] = agc_f[j].add(o.getPendingAmount());
                    }
                }

                for (BillReceiveCorp o : agcreclist) {
                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && o.getDisburseStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        agc_a[j] = agc_a[j].add(o.getRevisedrefund());
                        agc_b[j] = agc_b[j].add(o.getAdjustmentAmount());
                        agc_c[j] = agc_c[j].add(o.getPendingAmount());
                    }

                    if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1 && !o.getDisburseStatus().equalsIgnoreCase("REFUND") && o.getWeekId().compareTo(w) == 0) {
                        agc_d[j] = agc_d[j].add(o.getRevisedpaybale());
                    } else if (o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0 && o.getWeekId().compareTo(w) == 0) {
                        agc_d[j] = agc_d[j].add(o.getToalnet());
                    }

                    if (o.getWeekId().compareTo(w) == 0 && !o.getDisburseStatus().equalsIgnoreCase("REFUND")) {
                        agc_e[j] = agc_e[j].add(o.getDisburseAmount());
                        agc_f[j] = agc_f[j].add(o.getPendingAmount());
                    }
                }
            }

            for (int z = 0; z < dsm_maxweek.intValue(); z++) {
                dsm_a[z] = dsm_a[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                dsm_b[z] = dsm_b[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                dsm_c[z] = dsm_c[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                dsm_d[z] = dsm_d[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                dsm_e[z] = dsm_e[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                dsm_f[z] = dsm_f[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }

            for (int z = 0; z < rras_maxweek.intValue(); z++) {
                rras_a[z] = rras_a[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                rras_b[z] = rras_b[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                rras_c[z] = rras_c[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                rras_d[z] = rras_d[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                rras_e[z] = rras_e[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                rras_f[z] = rras_f[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }

            for (int z = 0; z < agc_maxweek.intValue(); z++) {
                agc_a[z] = agc_a[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                agc_b[z] = agc_b[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                agc_c[z] = agc_c[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                agc_d[z] = agc_d[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                agc_e[z] = agc_e[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                agc_f[z] = agc_f[z].divide(new BigDecimal(100000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }

            mv1.addObject("dsmweeks", dsmweeks);
            mv1.addObject("rrasweeks", rrasweeks);
            mv1.addObject("agcweeks", agcweeks);
            mv1.addObject("year", year);
            mv1.addObject("year1", year1);
            mv1.addObject("month", datearr[0]);
            mv1.addObject("dsm_a", dsm_a);
            mv1.addObject("dsm_b", dsm_b);
            mv1.addObject("dsm_c", dsm_c);
            mv1.addObject("dsm_d", dsm_d);
            mv1.addObject("dsm_e", dsm_e);
            mv1.addObject("dsm_f", dsm_f);
            mv1.addObject("rras_a", rras_a);
            mv1.addObject("rras_b", rras_b);
            mv1.addObject("rras_c", rras_c);
            mv1.addObject("rras_d", rras_d);
            mv1.addObject("rras_e", rras_e);
            mv1.addObject("rras_f", rras_f);
            mv1.addObject("agc_a", agc_a);
            mv1.addObject("agc_b", agc_b);
            mv1.addObject("agc_c", agc_c);
            mv1.addObject("agc_d", agc_d);
            mv1.addObject("agc_e", agc_e);
            mv1.addObject("agc_f", agc_f);

            return mv1;
        }

        return mv;
    }

    public ModelAndView getAnnexuredetail(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("NewReport/Annexuredetail");

        NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
        NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();

        List<BillPayableCorp> allpend1pay = bpdao.getPendingBillPayableByCorp();
        List<BillReceiveCorp> allpend2pay = brdao.getPendingRefundBillReceiveList();

        List<BillPayableCorp> allpend1rec = bpdao.getPendingRefundPaylist();
        List<BillReceiveCorp> allpend2rec = brdao.getPendingRefundRecList();

        List<BillPayableCorp> DSMpay = new ArrayList();
        List<BillPayableCorp> RRASpay = new ArrayList();
        List<BillPayableCorp> AGCpay = new ArrayList();
        List<BillPayableCorp> FRASpay = new ArrayList();
        List<BillPayableCorp> SRASpay = new ArrayList();
        List<BillPayableCorp> TRASpay = new ArrayList();
        List<BillReceiveCorp> DSMrec = new ArrayList();
        List<BillReceiveCorp> RRASrec = new ArrayList();
        List<BillReceiveCorp> AGCrec = new ArrayList();
        List<BillReceiveCorp> FRASrec = new ArrayList();
        List<BillReceiveCorp> SRASrec = new ArrayList();
        List<BillReceiveCorp> TRASrec = new ArrayList();

        BillPayableCorp tempBillpaycorp = null;
        BillReceiveCorp tempBillreccorp = null;

        for (BillPayableCorp bpay : allpend1pay) {
            if (bpay.getBillType().equalsIgnoreCase("DSM")) {
                DSMpay.add(bpay);
            }
            if (bpay.getBillType().equalsIgnoreCase("RRAS")) {
                RRASpay.add(bpay);
            }
            if (bpay.getBillType().equalsIgnoreCase("AGC")) {
                AGCpay.add(bpay);
            }
            if (bpay.getBillType().equalsIgnoreCase("FRAS")) {
                FRASpay.add(bpay);
            }
            if (bpay.getBillType().equalsIgnoreCase("SRAS")) {
                SRASpay.add(bpay);
            }
            if (bpay.getBillType().equalsIgnoreCase("TRASM") || bpay.getBillType().equalsIgnoreCase("TRASS") || bpay.getBillType().equalsIgnoreCase("TRASE")) {
                TRASpay.add(bpay);

            }

        }
        for (BillReceiveCorp bpay : allpend2pay) {
            if (bpay.getBillType().equalsIgnoreCase("DSM")) {
                tempBillpaycorp = new BillPayableCorp();
                tempBillpaycorp.setCorporates(bpay.getCorporates());
                tempBillpaycorp.setBillDueDate(bpay.getBillDueDate());
                tempBillpaycorp.setPendingAmount(bpay.getPendingAmount());
                tempBillpaycorp.setWeekId(bpay.getWeekId());
                tempBillpaycorp.setBillYear(bpay.getBillYear());
                tempBillpaycorp.setRevisionNo(bpay.getRevisionNo());
                DSMpay.add(tempBillpaycorp);
                tempBillpaycorp = null;
            }
            if (bpay.getBillType().equalsIgnoreCase("RRAS")) {
                tempBillpaycorp = new BillPayableCorp();
                tempBillpaycorp.setCorporates(bpay.getCorporates());
                tempBillpaycorp.setBillDueDate(bpay.getBillDueDate());
                tempBillpaycorp.setPendingAmount(bpay.getPendingAmount());
                tempBillpaycorp.setWeekId(bpay.getWeekId());
                tempBillpaycorp.setBillYear(bpay.getBillYear());
                tempBillpaycorp.setRevisionNo(bpay.getRevisionNo());
                RRASpay.add(tempBillpaycorp);
                tempBillpaycorp = null;
            }
            if (bpay.getBillType().equalsIgnoreCase("AGC")) {
                tempBillpaycorp = new BillPayableCorp();
                tempBillpaycorp.setCorporates(bpay.getCorporates());
                tempBillpaycorp.setBillDueDate(bpay.getBillDueDate());
                tempBillpaycorp.setPendingAmount(bpay.getPendingAmount());
                tempBillpaycorp.setWeekId(bpay.getWeekId());
                tempBillpaycorp.setBillYear(bpay.getBillYear());
                tempBillpaycorp.setRevisionNo(bpay.getRevisionNo());
                AGCpay.add(tempBillpaycorp);
                tempBillpaycorp = null;
            }
            if (bpay.getBillType().equalsIgnoreCase("FRAS")) {
                tempBillpaycorp = new BillPayableCorp();
                tempBillpaycorp.setCorporates(bpay.getCorporates());
                tempBillpaycorp.setBillDueDate(bpay.getBillDueDate());
                tempBillpaycorp.setPendingAmount(bpay.getPendingAmount());
                tempBillpaycorp.setWeekId(bpay.getWeekId());
                tempBillpaycorp.setBillYear(bpay.getBillYear());
                tempBillpaycorp.setRevisionNo(bpay.getRevisionNo());
                FRASpay.add(tempBillpaycorp);
                tempBillpaycorp = null;
            }
            if (bpay.getBillType().equalsIgnoreCase("SRAS")) {
                tempBillpaycorp = new BillPayableCorp();
                tempBillpaycorp.setCorporates(bpay.getCorporates());
                tempBillpaycorp.setBillDueDate(bpay.getBillDueDate());
                tempBillpaycorp.setPendingAmount(bpay.getPendingAmount());
                tempBillpaycorp.setWeekId(bpay.getWeekId());
                tempBillpaycorp.setBillYear(bpay.getBillYear());
                tempBillpaycorp.setRevisionNo(bpay.getRevisionNo());
                SRASpay.add(tempBillpaycorp);
                tempBillpaycorp = null;
            }
            if (bpay.getBillType().equalsIgnoreCase("TRASM") || bpay.getBillType().equalsIgnoreCase("TRASS") || bpay.getBillType().equalsIgnoreCase("TRASE")) {
                tempBillpaycorp = new BillPayableCorp();
                tempBillpaycorp.setCorporates(bpay.getCorporates());
                tempBillpaycorp.setBillDueDate(bpay.getBillDueDate());
                tempBillpaycorp.setPendingAmount(bpay.getPendingAmount());
                tempBillpaycorp.setWeekId(bpay.getWeekId());
                tempBillpaycorp.setBillYear(bpay.getBillYear());
                tempBillpaycorp.setRevisionNo(bpay.getRevisionNo());
                TRASpay.add(tempBillpaycorp);
                tempBillpaycorp = null;
            }
        }

        for (BillPayableCorp brec : allpend1rec) {
            if (brec.getBillType().equalsIgnoreCase("DSM")) {
                tempBillreccorp = new BillReceiveCorp();
                tempBillreccorp.setCorporates(brec.getCorporates());
                tempBillreccorp.setBillDueDate(brec.getBillDueDate());
                tempBillreccorp.setPendingAmount(brec.getPendingAmount());
                tempBillreccorp.setWeekId(brec.getWeekId());
                tempBillreccorp.setBillYear(brec.getBillYear());
                tempBillreccorp.setRevisionNo(brec.getRevisionNo());
                DSMrec.add(tempBillreccorp);
                tempBillreccorp = null;
            }
            if (brec.getBillType().equalsIgnoreCase("RRAS")) {
                tempBillreccorp = new BillReceiveCorp();
                tempBillreccorp.setCorporates(brec.getCorporates());
                tempBillreccorp.setBillDueDate(brec.getBillDueDate());
                tempBillreccorp.setPendingAmount(brec.getPendingAmount());
                tempBillreccorp.setWeekId(brec.getWeekId());
                tempBillreccorp.setBillYear(brec.getBillYear());
                tempBillreccorp.setRevisionNo(brec.getRevisionNo());
                RRASrec.add(tempBillreccorp);
                tempBillreccorp = null;
            }
            if (brec.getBillType().equalsIgnoreCase("AGC")) {
                tempBillreccorp = new BillReceiveCorp();
                tempBillreccorp.setCorporates(brec.getCorporates());
                tempBillreccorp.setBillDueDate(brec.getBillDueDate());
                tempBillreccorp.setPendingAmount(brec.getPendingAmount());
                tempBillreccorp.setWeekId(brec.getWeekId());
                tempBillreccorp.setBillYear(brec.getBillYear());
                tempBillreccorp.setRevisionNo(brec.getRevisionNo());
                AGCrec.add(tempBillreccorp);
                tempBillreccorp = null;
            }
            if (brec.getBillType().equalsIgnoreCase("FRAS")) {
                tempBillreccorp = new BillReceiveCorp();
                tempBillreccorp.setCorporates(brec.getCorporates());
                tempBillreccorp.setBillDueDate(brec.getBillDueDate());
                tempBillreccorp.setPendingAmount(brec.getPendingAmount());
                tempBillreccorp.setWeekId(brec.getWeekId());
                tempBillreccorp.setBillYear(brec.getBillYear());
                tempBillreccorp.setRevisionNo(brec.getRevisionNo());
                FRASrec.add(tempBillreccorp);
                tempBillreccorp = null;
            }
            if (brec.getBillType().equalsIgnoreCase("SRAS")) {
                tempBillreccorp = new BillReceiveCorp();
                tempBillreccorp.setCorporates(brec.getCorporates());
                tempBillreccorp.setBillDueDate(brec.getBillDueDate());
                tempBillreccorp.setPendingAmount(brec.getPendingAmount());
                tempBillreccorp.setWeekId(brec.getWeekId());
                tempBillreccorp.setBillYear(brec.getBillYear());
                tempBillreccorp.setRevisionNo(brec.getRevisionNo());
                SRASrec.add(tempBillreccorp);
                tempBillreccorp = null;
            }
            if (brec.getBillType().equalsIgnoreCase("TRASM") || brec.getBillType().equalsIgnoreCase("TRASS") || brec.getBillType().equalsIgnoreCase("TRASE")) {
                tempBillreccorp = new BillReceiveCorp();
                tempBillreccorp.setCorporates(brec.getCorporates());
                tempBillreccorp.setBillDueDate(brec.getBillDueDate());
                tempBillreccorp.setPendingAmount(brec.getPendingAmount());
                tempBillreccorp.setWeekId(brec.getWeekId());
                tempBillreccorp.setBillYear(brec.getBillYear());
                tempBillreccorp.setRevisionNo(brec.getRevisionNo());
                TRASrec.add(tempBillreccorp);
                tempBillreccorp = null;
            }
        }
        for (BillReceiveCorp brec : allpend2rec) {
            if (brec.getBillType().equalsIgnoreCase("DSM")) {
                DSMrec.add(brec);
            }
            if (brec.getBillType().equalsIgnoreCase("RRAS")) {
                RRASrec.add(brec);
            }
            if (brec.getBillType().equalsIgnoreCase("AGC")) {
                AGCrec.add(brec);
            }
            if (brec.getBillType().equalsIgnoreCase("FRAS")) {
                FRASrec.add(brec);
            }
            if (brec.getBillType().equalsIgnoreCase("SRAS")) {
                SRASrec.add(brec);
            }
            if (brec.getBillType().equalsIgnoreCase("TRASM") || brec.getBillType().equalsIgnoreCase("TRASS") || brec.getBillType().equalsIgnoreCase("TRASE")) {
                TRASrec.add(brec);
            }
        }

//        BigDecimal tempweek = null;
//        BigDecimal tempyear = null;
//        BigDecimal temprevno = null;
//        for(int a=0; a<DSMpay.size()-1; a++) {
//            tempweek = DSMpay.get(a).getWeekId(); tempyear = DSMpay.get(a).getBillYear(); temprevno = DSMpay.get(a).getRevisionNo();
//            for(int b=1; b<DSMpay.size(); b++) {
//                if(b<DSMpay.size())
//                if(DSMpay.get(b).getWeekId().compareTo(tempweek)==0 && DSMpay.get(b).getBillYear().compareTo(tempyear)==0 && DSMpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    DSMpay.remove(a);
//                if(b<DSMpay.size())
//                if(DSMpay.get(b).getWeekId().compareTo(tempweek)==0 && DSMpay.get(b).getBillYear().compareTo(tempyear)==0 && DSMpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    DSMpay.remove(b);
//            }
//        }
//        for(int a=0; a<RRASpay.size()-1; a++) {
//            tempweek = RRASpay.get(a).getWeekId(); tempyear = RRASpay.get(a).getBillYear(); temprevno = RRASpay.get(a).getRevisionNo();
//            for(int b=1; b<RRASpay.size(); b++) {
//                if(b<RRASpay.size())
//                if(RRASpay.get(b).getWeekId().compareTo(tempweek)==0 && RRASpay.get(b).getBillYear().compareTo(tempyear)==0 && RRASpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    RRASpay.remove(a);
//                if(b<RRASpay.size())
//                if(RRASpay.get(b).getWeekId().compareTo(tempweek)==0 && RRASpay.get(b).getBillYear().compareTo(tempyear)==0 && RRASpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    RRASpay.remove(b);
//            }
//        }
//        for(int a=0; a<AGCpay.size()-1; a++) {
//            tempweek = AGCpay.get(a).getWeekId(); tempyear = AGCpay.get(a).getBillYear(); temprevno = AGCpay.get(a).getRevisionNo();
//            for(int b=1; b<AGCpay.size(); b++) {
//                if(b<AGCpay.size())
//                if(AGCpay.get(b).getWeekId().compareTo(tempweek)==0 && AGCpay.get(b).getBillYear().compareTo(tempyear)==0 && AGCpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    AGCpay.remove(a);
//                if(b<AGCpay.size())
//                if(AGCpay.get(b).getWeekId().compareTo(tempweek)==0 && AGCpay.get(b).getBillYear().compareTo(tempyear)==0 && AGCpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    AGCpay.remove(b);
//            }
//        }
//        for(int a=0; a<FRASpay.size()-1; a++) {
//            tempweek = FRASpay.get(a).getWeekId(); tempyear = FRASpay.get(a).getBillYear(); temprevno = FRASpay.get(a).getRevisionNo();
//            for(int b=1; b<FRASpay.size(); b++) {
//                if(b<FRASpay.size())
//                if(FRASpay.get(b).getWeekId().compareTo(tempweek)==0 && FRASpay.get(b).getBillYear().compareTo(tempyear)==0 && FRASpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    FRASpay.remove(a);
//                if(b<FRASpay.size())
//                if(FRASpay.get(b).getWeekId().compareTo(tempweek)==0 && FRASpay.get(b).getBillYear().compareTo(tempyear)==0 && FRASpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    FRASpay.remove(b);
//            }
//        }
//        
//        for(int a=0; a<DSMrec.size()-1; a++) {
//            tempweek = DSMrec.get(a).getWeekId(); tempyear = DSMrec.get(a).getBillYear(); temprevno = DSMrec.get(a).getRevisionNo();
//            for(int b=1; b<DSMrec.size(); b++) {
//                if(b<DSMrec.size())
//                if(DSMrec.get(b).getWeekId().compareTo(tempweek)==0 && DSMrec.get(b).getBillYear().compareTo(tempyear)==0 && DSMrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    DSMrec.remove(a);
//                if(b<DSMrec.size())
//                if(DSMrec.get(b).getWeekId().compareTo(tempweek)==0 && DSMrec.get(b).getBillYear().compareTo(tempyear)==0 && DSMrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    DSMrec.remove(b);
//            }
//        }
//        for(int a=0; a<RRASrec.size()-1; a++) {
//            tempweek = RRASrec.get(a).getWeekId(); tempyear = RRASrec.get(a).getBillYear(); temprevno = RRASrec.get(a).getRevisionNo();
//            for(int b=1; b<RRASrec.size(); b++) {
//                if(b<RRASrec.size())
//                if(RRASrec.get(b).getWeekId().compareTo(tempweek)==0 && RRASrec.get(b).getBillYear().compareTo(tempyear)==0 && RRASrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    RRASrec.remove(a);
//                if(b<RRASrec.size())
//                if(RRASrec.get(b).getWeekId().compareTo(tempweek)==0 && RRASrec.get(b).getBillYear().compareTo(tempyear)==0 && RRASrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    RRASrec.remove(b);
//            }
//        }
//        for(int a=0; a<AGCrec.size()-1; a++) {
//            tempweek = AGCrec.get(a).getWeekId(); tempyear = AGCrec.get(a).getBillYear(); temprevno = AGCrec.get(a).getRevisionNo();
//            for(int b=1; b<AGCrec.size(); b++) {
//                if(b<AGCrec.size())
//                if(AGCrec.get(b).getWeekId().compareTo(tempweek)==0 && AGCrec.get(b).getBillYear().compareTo(tempyear)==0 && AGCrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    AGCrec.remove(a);
//                if(b<AGCrec.size())
//                if(AGCrec.get(b).getWeekId().compareTo(tempweek)==0 && AGCrec.get(b).getBillYear().compareTo(tempyear)==0 && AGCrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    AGCrec.remove(b);
//            }
//        }
//        for(int a=0; a<FRASrec.size()-1; a++) {
//            tempweek = FRASrec.get(a).getWeekId(); tempyear = FRASrec.get(a).getBillYear(); temprevno = FRASrec.get(a).getRevisionNo();
//            for(int b=1; b<FRASrec.size(); b++) {
//                if(b<FRASrec.size())
//                if(FRASrec.get(b).getWeekId().compareTo(tempweek)==0 && FRASrec.get(b).getBillYear().compareTo(tempyear)==0 && FRASrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    FRASrec.remove(a);
//                if(b<FRASrec.size())
//                if(FRASrec.get(b).getWeekId().compareTo(tempweek)==0 && FRASrec.get(b).getBillYear().compareTo(tempyear)==0 && FRASrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    FRASrec.remove(b);
//            }
//        }
        BigDecimal DSMpaytotal = BigDecimal.ZERO;
        BigDecimal RRASpaytotal = BigDecimal.ZERO;
        BigDecimal AGCpaytotal = BigDecimal.ZERO;
        BigDecimal FRASpaytotal = BigDecimal.ZERO;
        BigDecimal SRASpaytotal = BigDecimal.ZERO;
        BigDecimal TRASpaytotal = BigDecimal.ZERO;
        BigDecimal DSMrectotal = BigDecimal.ZERO;
        BigDecimal RRASrectotal = BigDecimal.ZERO;
        BigDecimal AGCrectotal = BigDecimal.ZERO;
        BigDecimal FRASrectotal = BigDecimal.ZERO;
        BigDecimal SRASrectotal = BigDecimal.ZERO;
        BigDecimal TRASrectotal = BigDecimal.ZERO;

        for (BillPayableCorp obj : DSMpay) {
            DSMpaytotal = DSMpaytotal.add(obj.getPendingAmount());
        }
        for (BillReceiveCorp obj : DSMrec) {
            DSMrectotal = DSMrectotal.add(obj.getPendingAmount());
        }

        for (BillPayableCorp obj : RRASpay) {
            RRASpaytotal = RRASpaytotal.add(obj.getPendingAmount());
        }
        for (BillReceiveCorp obj : RRASrec) {
            RRASrectotal = RRASrectotal.add(obj.getPendingAmount());
        }

        for (BillPayableCorp obj : AGCpay) {
            AGCpaytotal = AGCpaytotal.add(obj.getPendingAmount());
        }
        for (BillReceiveCorp obj : AGCrec) {
            AGCrectotal = AGCrectotal.add(obj.getPendingAmount());
        }

        for (BillPayableCorp obj : FRASpay) {
            FRASpaytotal = FRASpaytotal.add(obj.getPendingAmount());
        }
        for (BillReceiveCorp obj : FRASrec) {
            FRASrectotal = FRASrectotal.add(obj.getPendingAmount());
        }
        for (BillPayableCorp obj : SRASpay) {
            SRASpaytotal = SRASpaytotal.add(obj.getPendingAmount());
        }
        for (BillReceiveCorp obj : SRASrec) {
            SRASrectotal = SRASrectotal.add(obj.getPendingAmount());
        }
        for (BillPayableCorp obj : TRASpay) {
            TRASpaytotal = TRASpaytotal.add(obj.getPendingAmount());
        }
        for (BillReceiveCorp obj : TRASrec) {
            TRASrectotal = TRASrectotal.add(obj.getPendingAmount());
        }

        mv.addObject("DSMpay", DSMpay);
        mv.addObject("RRASpay", RRASpay);
        mv.addObject("AGCpay", AGCpay);
        mv.addObject("FRASpay", FRASpay);
        mv.addObject("SRASpay", SRASpay);
        mv.addObject("TRASpay", TRASpay);
        mv.addObject("DSMrec", DSMrec);
        mv.addObject("RRASrec", RRASrec);
        mv.addObject("AGCrec", AGCrec);
        mv.addObject("FRASrec", FRASrec);
        mv.addObject("SRASrec", SRASrec);
        mv.addObject("TRASrec", TRASrec);
        mv.addObject("DSMpaytotal", DSMpaytotal);
        mv.addObject("RRASpaytotal", RRASpaytotal);
        mv.addObject("AGCpaytotal", AGCpaytotal);
        mv.addObject("FRASpaytotal", FRASpaytotal);
        mv.addObject("SRASpaytotal", SRASpaytotal);
        mv.addObject("TRASpaytotal", TRASpaytotal);
        mv.addObject("DSMrectotal", DSMrectotal);
        mv.addObject("RRASrectotal", RRASrectotal);
        mv.addObject("AGCrectotal", AGCrectotal);
        mv.addObject("FRASrectotal", FRASrectotal);
        mv.addObject("SRASrectotal", SRASrectotal);
        mv.addObject("TRASrectotal", TRASrectotal);

        return mv;
    }

    public ModelAndView getReconciliationReport(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        ModelAndView mv = new ModelAndView("NewReport/getReconciliationReport");

        String bsubmit = request.getParameter("bsubmit");
        String fyear = request.getParameter("s1");

        if (bsubmit != null) {
            mv = new ModelAndView("NewReport/ReconciliationReport");
            String year = fyear.substring(2, 4);

            String[] months = new SimpleDateFormat().getDateFormatSymbols().getShortMonths();
            String[] monthlist = new String[12];
            Arrays.fill(monthlist, "");

            for (int i = 0; i < months.length - 1; i++) {
                if (months[i].equalsIgnoreCase("Jan") || months[i].equalsIgnoreCase("Feb") || months[i].equalsIgnoreCase("Mar")) {
                    monthlist[i + 9] = months[i] + "'" + Integer.valueOf(Integer.valueOf(year) + 1);
                } else {
                    monthlist[i - 3] = months[i] + "'" + year;
                }
            }
            System.out.println("year =" + year);
            Date dt1 = new SimpleDateFormat("dd-MM-yyyy").parse("01-04-20" + year);
            Date dt2 = new SimpleDateFormat("dd-MM-yyyy").parse("31-03-20" + Integer.valueOf(Integer.valueOf(year) + 1));

            BigDecimal[] payrec = new BigDecimal[12];
            Arrays.fill(payrec, BigDecimal.ZERO);
            BigDecimal[] paydis = new BigDecimal[12];
            Arrays.fill(paydis, BigDecimal.ZERO);
            BigDecimal[] intrec = new BigDecimal[12];
            Arrays.fill(intrec, BigDecimal.ZERO);
            BigDecimal[] intdis = new BigDecimal[12];
            Arrays.fill(intdis, BigDecimal.ZERO);
            BigDecimal[] ptrspsdf = new BigDecimal[12];
            Arrays.fill(ptrspsdf, BigDecimal.ZERO);
            BigDecimal[] itrspsdf = new BigDecimal[12];
            Arrays.fill(itrspsdf, BigDecimal.ZERO);
            BigDecimal[] poolToInts = new BigDecimal[12];
            Arrays.fill(poolToInts, BigDecimal.ZERO);
            BigDecimal payrectotal = BigDecimal.ZERO;
            BigDecimal paydistotal = BigDecimal.ZERO;
            BigDecimal intrectotal = BigDecimal.ZERO;
            BigDecimal intdistotal = BigDecimal.ZERO;
            BigDecimal ptrspsdftotal = BigDecimal.ZERO;
            BigDecimal itrspsdftotal = BigDecimal.ZERO;
            BigDecimal openbalacc = BigDecimal.ZERO;
            BigDecimal openbalint = BigDecimal.ZERO;
            BigDecimal[] monthlyaccsum = new BigDecimal[12];
            Arrays.fill(monthlyaccsum, BigDecimal.ZERO);
            BigDecimal[] monthlyintsum = new BigDecimal[12];
            Arrays.fill(monthlyintsum, BigDecimal.ZERO);

            NewBankStatementDAO bkdao = new NewBankStatementDAO();
            NewMappingInterestBankDAO mappintdao = new NewMappingInterestBankDAO();
            PoolToIntDAO pooltoIntdao = new PoolToIntDAO();
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            MappingInterestBankDAO mapintbnkdao = new MappingInterestBankDAO();
            TempRefundBillCorpDAO disrefdao = new TempRefundBillCorpDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            miscDisbursementDAO miscdao = new miscDisbursementDAO();
            DisbursedInterestDetailsDAO disintdao = new DisbursedInterestDetailsDAO();

            List<BankStatement> crstmts = bkdao.CRBankStatementlistbyentrydates(dt1, dt2);
            List<PaymentDisbursement> drstmts = disbilldao.getbillDisbursementDetailsbyFromDateTodate(dt1, dt2);
            List<MappingInterestBank> intreclist = mapintbnkdao.getMappingInterestBankbyentrydates(dt1, dt2);
            List<TempRefundBillCorp> disreflist = disrefdao.getRefundBillPayCorporExport2(dt1, dt2);
            List<CsdfDetails> psdfbilllist = csdfdao.getPSDFbilldetailsbydates(dt1, dt2);
            List<CsdfDetails> psdfintlist = csdfdao.getPSDFintdetailsbydates(dt1, dt2);
            List<PoolToInt> pooltoInt = pooltoIntdao.getPoolToIntbydates(dt1, dt2);
            List<MiscDisbursement> miscbilllist = miscdao.getmiscbilllistbydates(dt1, dt2);
            List<MiscDisbursement> miscintlist = miscdao.getmiscintlistbydates(dt1, dt2);
            List<PaymentInterestDisbursement> disintlist = disintdao.getDisbursedInterestlistbydates(dt1, dt2);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss.SSS a");

//            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:ss.SSS a");
            openbalacc = disbilldao.getmaxpoolopenbalfornotesheetonlyINT(outputFormat.format(new SimpleDateFormat("dd-MMM-yyyy").parse("01-APR-20" + year).getTime()));

            openbalint = disbilldao.getinterestbalonlyBILLSbyentrytime(outputFormat.format(new SimpleDateFormat("dd-MMM-yyyy").parse("01-APR-20" + year).getTime()));
            System.out.println("dates are =" + dt1 + " and " + dt2);
            if (openbalacc == null) {
                openbalacc = new BigDecimal(0);
            }
            if (openbalint == null) {
                openbalint = new BigDecimal(0);
            }

            if (crstmts != null) {
                for (BankStatement obj : crstmts) {
                    int monthnum = obj.getEntryDate().getMonth();
                    if (monthnum == 0) {
                        payrec[9] = payrec[9].add(obj.getPaidAmount());
                    } else if (monthnum == 1) {
                        payrec[10] = payrec[10].add(obj.getPaidAmount());
                    } else if (monthnum == 2) {
                        payrec[11] = payrec[11].add(obj.getPaidAmount());
                    } else if (monthnum == 3) {
                        payrec[0] = payrec[0].add(obj.getPaidAmount());
                    } else if (monthnum == 4) {
                        payrec[1] = payrec[1].add(obj.getPaidAmount());
                    } else if (monthnum == 5) {
                        payrec[2] = payrec[2].add(obj.getPaidAmount());
                    } else if (monthnum == 6) {
                        payrec[3] = payrec[3].add(obj.getPaidAmount());
                    } else if (monthnum == 7) {
                        payrec[4] = payrec[4].add(obj.getPaidAmount());
                    } else if (monthnum == 8) {
                        payrec[5] = payrec[5].add(obj.getPaidAmount());
                    } else if (monthnum == 9) {
                        payrec[6] = payrec[6].add(obj.getPaidAmount());
                    } else if (monthnum == 10) {
                        payrec[7] = payrec[7].add(obj.getPaidAmount());
                    } else if (monthnum == 11) {
                        payrec[8] = payrec[8].add(obj.getPaidAmount());
                    }
                }
            }

            if (drstmts != null) {
                for (PaymentDisbursement obj : drstmts) {
                    int monthnum = obj.getDisbursalDate().getMonth();
                    if (monthnum == 0) {
                        paydis[9] = paydis[9].add(obj.getDisburseAmount());
                    } else if (monthnum == 1) {
                        paydis[10] = paydis[10].add(obj.getDisburseAmount());
                    } else if (monthnum == 2) {
                        paydis[11] = paydis[11].add(obj.getDisburseAmount());
                    } else if (monthnum == 3) {
                        paydis[0] = paydis[0].add(obj.getDisburseAmount());
                    } else if (monthnum == 4) {
                        paydis[1] = paydis[1].add(obj.getDisburseAmount());
                    } else if (monthnum == 5) {
                        paydis[2] = paydis[2].add(obj.getDisburseAmount());
                    } else if (monthnum == 6) {
                        paydis[3] = paydis[3].add(obj.getDisburseAmount());
                    } else if (monthnum == 7) {
                        paydis[4] = paydis[4].add(obj.getDisburseAmount());
                    } else if (monthnum == 8) {
                        paydis[5] = paydis[5].add(obj.getDisburseAmount());
                    } else if (monthnum == 9) {
                        paydis[6] = paydis[6].add(obj.getDisburseAmount());
                    } else if (monthnum == 10) {
                        paydis[7] = paydis[7].add(obj.getDisburseAmount());
                    } else if (monthnum == 11) {
                        paydis[8] = paydis[8].add(obj.getDisburseAmount());
                    }
                }
            }

            if (disreflist != null) {
                for (TempRefundBillCorp obj : disreflist) {
                    int monthnum = obj.getRefundDate().getMonth();
                    if (monthnum == 0) {
                        paydis[9] = paydis[9].add(obj.getPaidAmount());
                    } else if (monthnum == 1) {
                        paydis[10] = paydis[10].add(obj.getPaidAmount());
                    } else if (monthnum == 2) {
                        paydis[11] = paydis[11].add(obj.getPaidAmount());
                    } else if (monthnum == 3) {
                        paydis[0] = paydis[0].add(obj.getPaidAmount());
                    } else if (monthnum == 4) {
                        paydis[1] = paydis[1].add(obj.getPaidAmount());
                    } else if (monthnum == 5) {
                        paydis[2] = paydis[2].add(obj.getPaidAmount());
                    } else if (monthnum == 6) {
                        paydis[3] = paydis[3].add(obj.getPaidAmount());
                    } else if (monthnum == 7) {
                        paydis[4] = paydis[4].add(obj.getPaidAmount());
                    } else if (monthnum == 8) {
                        paydis[5] = paydis[5].add(obj.getPaidAmount());
                    } else if (monthnum == 9) {
                        paydis[6] = paydis[6].add(obj.getPaidAmount());
                    } else if (monthnum == 10) {
                        paydis[7] = paydis[7].add(obj.getPaidAmount());
                    } else if (monthnum == 11) {
                        paydis[8] = paydis[8].add(obj.getPaidAmount());
                    }
                }
            }

            if (miscbilllist != null) {
                for (MiscDisbursement obj : miscbilllist) {
                    int monthnum = obj.getEntryDate().getMonth();
                    if (monthnum == 0) {
                        paydis[9] = paydis[9].add(obj.getRefundAmt());
                    } else if (monthnum == 1) {
                        paydis[10] = paydis[10].add(obj.getRefundAmt());
                    } else if (monthnum == 2) {
                        paydis[11] = paydis[11].add(obj.getRefundAmt());
                    } else if (monthnum == 3) {
                        paydis[0] = paydis[0].add(obj.getRefundAmt());
                    } else if (monthnum == 4) {
                        paydis[1] = paydis[1].add(obj.getRefundAmt());
                    } else if (monthnum == 5) {
                        paydis[2] = paydis[2].add(obj.getRefundAmt());
                    } else if (monthnum == 6) {
                        paydis[3] = paydis[3].add(obj.getRefundAmt());
                    } else if (monthnum == 7) {
                        paydis[4] = paydis[4].add(obj.getRefundAmt());
                    } else if (monthnum == 8) {
                        paydis[5] = paydis[5].add(obj.getRefundAmt());
                    } else if (monthnum == 9) {
                        paydis[6] = paydis[6].add(obj.getRefundAmt());
                    } else if (monthnum == 10) {
                        paydis[7] = paydis[7].add(obj.getRefundAmt());
                    } else if (monthnum == 11) {
                        paydis[8] = paydis[8].add(obj.getRefundAmt());
                    }
                }
            }
            if (intreclist != null && intreclist.size() > 0) {
                for (MappingInterestBank obj : intreclist) {
                    int monthnum = obj.getEntryDate().getMonth();
                    if (monthnum == 0) {
                        intrec[9] = intrec[9].add(obj.getMappedAmount());
                        payrec[9] = payrec[9].subtract(obj.getMappedAmount());
                    } else if (monthnum == 1) {
                        intrec[10] = intrec[10].add(obj.getMappedAmount());
                        payrec[10] = payrec[10].subtract(obj.getMappedAmount());
                    } else if (monthnum == 2) {
                        intrec[11] = intrec[11].add(obj.getMappedAmount());
                        payrec[11] = payrec[11].subtract(obj.getMappedAmount());
                    } else if (monthnum == 3) {
                        intrec[0] = intrec[0].add(obj.getMappedAmount());
                        payrec[0] = payrec[0].subtract(obj.getMappedAmount());
                    } else if (monthnum == 4) {
                        intrec[1] = intrec[1].add(obj.getMappedAmount());
                        payrec[1] = payrec[1].subtract(obj.getMappedAmount());
                    } else if (monthnum == 5) {
                        intrec[2] = intrec[2].add(obj.getMappedAmount());
                        payrec[2] = payrec[2].subtract(obj.getMappedAmount());
                    } else if (monthnum == 6) {
                        intrec[3] = intrec[3].add(obj.getMappedAmount());
                        payrec[3] = payrec[3].subtract(obj.getMappedAmount());
                    } else if (monthnum == 7) {
                        intrec[4] = intrec[4].add(obj.getMappedAmount());
                        payrec[4] = payrec[4].subtract(obj.getMappedAmount());
                    } else if (monthnum == 8) {
                        intrec[5] = intrec[5].add(obj.getMappedAmount());
                        payrec[5] = payrec[5].subtract(obj.getMappedAmount());
                    } else if (monthnum == 9) {
                        intrec[6] = intrec[6].add(obj.getMappedAmount());
                        payrec[6] = payrec[6].subtract(obj.getMappedAmount());
                    } else if (monthnum == 10) {
                        intrec[7] = intrec[7].add(obj.getMappedAmount());
                        payrec[7] = payrec[7].subtract(obj.getMappedAmount());
                    } else if (monthnum == 11) {
                        intrec[8] = intrec[8].add(obj.getMappedAmount());
                        payrec[8] = payrec[8].subtract(obj.getMappedAmount());
                    }
                }
            }

            if (disintlist != null && disintlist.size() > 0) {
                for (PaymentInterestDisbursement obj : disintlist) {
                    int monthnum = obj.getInterestPaiddate().getMonth();
                    if (monthnum == 0) {
                        intdis[9] = intdis[9].add(obj.getInterestPaidamount());
                    } else if (monthnum == 1) {
                        intdis[10] = intdis[10].add(obj.getInterestPaidamount());
                    } else if (monthnum == 2) {
                        intdis[11] = intdis[11].add(obj.getInterestPaidamount());
                    } else if (monthnum == 3) {
                        intdis[0] = intdis[0].add(obj.getInterestPaidamount());
                    } else if (monthnum == 4) {
                        intdis[1] = intdis[1].add(obj.getInterestPaidamount());
                    } else if (monthnum == 5) {
                        intdis[2] = intdis[2].add(obj.getInterestPaidamount());
                    } else if (monthnum == 6) {
                        intdis[3] = intdis[3].add(obj.getInterestPaidamount());
                    } else if (monthnum == 7) {
                        intdis[4] = intdis[4].add(obj.getInterestPaidamount());
                    } else if (monthnum == 8) {
                        intdis[5] = intdis[5].add(obj.getInterestPaidamount());
                    } else if (monthnum == 9) {
                        intdis[6] = intdis[6].add(obj.getInterestPaidamount());
                    } else if (monthnum == 10) {
                        intdis[7] = intdis[7].add(obj.getInterestPaidamount());
                    } else if (monthnum == 11) {
                        intdis[8] = intdis[8].add(obj.getInterestPaidamount());
                    }
                }
            }

            if (miscintlist != null) {
                for (MiscDisbursement obj : miscintlist) {
                    int monthnum = obj.getEntryDate().getMonth();
                    if (monthnum == 0) {
                        intdis[9] = intdis[9].add(obj.getRefundAmt());
                    } else if (monthnum == 1) {
                        intdis[10] = intdis[10].add(obj.getRefundAmt());
                    } else if (monthnum == 2) {
                        intdis[11] = intdis[11].add(obj.getRefundAmt());
                    } else if (monthnum == 3) {
                        intdis[0] = intdis[0].add(obj.getRefundAmt());
                    } else if (monthnum == 4) {
                        intdis[1] = intdis[1].add(obj.getRefundAmt());
                    } else if (monthnum == 5) {
                        intdis[2] = intdis[2].add(obj.getRefundAmt());
                    } else if (monthnum == 6) {
                        intdis[3] = intdis[3].add(obj.getRefundAmt());
                    } else if (monthnum == 7) {
                        intdis[4] = intdis[4].add(obj.getRefundAmt());
                    } else if (monthnum == 8) {
                        intdis[5] = intdis[5].add(obj.getRefundAmt());
                    } else if (monthnum == 9) {
                        intdis[6] = intdis[6].add(obj.getRefundAmt());
                    } else if (monthnum == 10) {
                        intdis[7] = intdis[7].add(obj.getRefundAmt());
                    } else if (monthnum == 11) {
                        intdis[8] = intdis[8].add(obj.getRefundAmt());
                    }
                }
            }
            if (psdfbilllist != null && psdfbilllist.size() > 0) {
                for (CsdfDetails obj : psdfbilllist) {
                    int monthnum = obj.getEntryDate().getMonth();
                    if (monthnum == 0) {
                        ptrspsdf[9] = ptrspsdf[9].add(obj.getCsdfAmount());
                    } else if (monthnum == 1) {
                        ptrspsdf[10] = ptrspsdf[10].add(obj.getCsdfAmount());
                    } else if (monthnum == 2) {
                        ptrspsdf[11] = ptrspsdf[11].add(obj.getCsdfAmount());
                    } else if (monthnum == 3) {
                        ptrspsdf[0] = ptrspsdf[0].add(obj.getCsdfAmount());
                    } else if (monthnum == 4) {
                        ptrspsdf[1] = ptrspsdf[1].add(obj.getCsdfAmount());
                    } else if (monthnum == 5) {
                        ptrspsdf[2] = ptrspsdf[2].add(obj.getCsdfAmount());
                    } else if (monthnum == 6) {
                        ptrspsdf[3] = ptrspsdf[3].add(obj.getCsdfAmount());
                    } else if (monthnum == 7) {
                        ptrspsdf[4] = ptrspsdf[4].add(obj.getCsdfAmount());
                    } else if (monthnum == 8) {
                        ptrspsdf[5] = ptrspsdf[5].add(obj.getCsdfAmount());
                    } else if (monthnum == 9) {
                        ptrspsdf[6] = ptrspsdf[6].add(obj.getCsdfAmount());
                    } else if (monthnum == 10) {
                        ptrspsdf[7] = ptrspsdf[7].add(obj.getCsdfAmount());
                    } else if (monthnum == 11) {
                        ptrspsdf[8] = ptrspsdf[8].add(obj.getCsdfAmount());
                    }
                }
            }

            if (psdfintlist != null && psdfintlist.size() > 0) {
                for (CsdfDetails obj : psdfintlist) {
                    int monthnum = obj.getEntryDate().getMonth();
                    if (monthnum == 0) {
                        itrspsdf[9] = itrspsdf[9].add(obj.getCsdfAmount());
                    } else if (monthnum == 1) {
                        itrspsdf[10] = itrspsdf[10].add(obj.getCsdfAmount());
                    } else if (monthnum == 2) {
                        itrspsdf[11] = itrspsdf[11].add(obj.getCsdfAmount());
                    } else if (monthnum == 3) {
                        itrspsdf[0] = itrspsdf[0].add(obj.getCsdfAmount());
                    } else if (monthnum == 4) {
                        itrspsdf[1] = itrspsdf[1].add(obj.getCsdfAmount());
                    } else if (monthnum == 5) {
                        itrspsdf[2] = itrspsdf[2].add(obj.getCsdfAmount());
                    } else if (monthnum == 6) {
                        itrspsdf[3] = itrspsdf[3].add(obj.getCsdfAmount());
                    } else if (monthnum == 7) {
                        itrspsdf[4] = itrspsdf[4].add(obj.getCsdfAmount());
                    } else if (monthnum == 8) {
                        itrspsdf[5] = itrspsdf[5].add(obj.getCsdfAmount());
                    } else if (monthnum == 9) {
                        itrspsdf[6] = itrspsdf[6].add(obj.getCsdfAmount());
                    } else if (monthnum == 10) {
                        itrspsdf[7] = itrspsdf[7].add(obj.getCsdfAmount());
                    } else if (monthnum == 11) {
                        itrspsdf[8] = itrspsdf[8].add(obj.getCsdfAmount());
                    }
                }
            }

            if (pooltoInt != null) {
                for (PoolToInt p : pooltoInt) {
                    int monthnum = p.getEntryDate().getMonth();

                    if (monthnum == 0) {
                        poolToInts[9] = poolToInts[9].add(p.getRefundAmt());
                    } else if (monthnum == 1) {
                        poolToInts[10] = poolToInts[10].add(p.getRefundAmt());
                    } else if (monthnum == 2) {
                        poolToInts[11] = poolToInts[11].add(p.getRefundAmt());
                    } else if (monthnum == 3) {
                        poolToInts[0] = poolToInts[0].add(p.getRefundAmt());
                    } else if (monthnum == 4) {
                        poolToInts[1] = poolToInts[1].add(p.getRefundAmt());
                    } else if (monthnum == 5) {
                        poolToInts[2] = poolToInts[2].add(p.getRefundAmt());
                    } else if (monthnum == 6) {
                        poolToInts[3] = poolToInts[3].add(p.getRefundAmt());
                    } else if (monthnum == 7) {
                        poolToInts[4] = poolToInts[4].add(p.getRefundAmt());
                    } else if (monthnum == 8) {
                        poolToInts[5] = poolToInts[5].add(p.getRefundAmt());
                    } else if (monthnum == 9) {
                        poolToInts[6] = poolToInts[6].add(p.getRefundAmt());
                    } else if (monthnum == 10) {
                        poolToInts[7] = poolToInts[7].add(p.getRefundAmt());
                    } else if (monthnum == 11) {
                        poolToInts[8] = poolToInts[8].add(p.getRefundAmt());
                    }
                }
            }

            for (int k = 0; k < payrec.length; k++) {
                payrectotal = payrectotal.add(payrec[k]);
            }

            for (int k = 0; k < paydis.length; k++) {
                paydistotal = paydistotal.add(paydis[k]);
            }

            for (int k = 0; k < intrec.length; k++) {
                intrectotal = intrectotal.add(intrec[k]);
            }

            for (int k = 0; k < intdis.length; k++) {
                intdistotal = intdistotal.add(intdis[k]);
            }

            for (int k = 0; k < ptrspsdf.length; k++) {
                ptrspsdftotal = ptrspsdftotal.add(ptrspsdf[k]);
            }

            for (int k = 0; k < itrspsdf.length; k++) {
                itrspsdftotal = itrspsdftotal.add(itrspsdf[k]);
            }

            for (int x = 0; x < 12; x++) {
                if (x == 0) {
                    if (19 == Integer.parseInt(year)) {
                        openbalacc = BigDecimal.valueOf(204512079);
                    }
                    System.out.println("yaer = " + year + "openbalacc =" + openbalacc);
                    monthlyaccsum[x] = monthlyaccsum[x].add(openbalacc).add(payrec[x]).subtract(paydis[x]).subtract(ptrspsdf[x]).subtract(poolToInts[x]);
                } else {
                    monthlyaccsum[x] = monthlyaccsum[x].add(monthlyaccsum[x - 1]).add(payrec[x]).subtract(paydis[x]).subtract(ptrspsdf[x]).subtract(poolToInts[x]);
                }
            }

            for (int x = 0; x < 12; x++) {
                if (x == 0) {
                    monthlyintsum[x] = monthlyintsum[x].add(openbalint).add(intrec[x]).subtract(intdis[x]).subtract(itrspsdf[x]).add(poolToInts[x]);
                } else {
                    monthlyintsum[x] = monthlyintsum[x].add(monthlyintsum[x - 1]).add(intrec[x]).subtract(intdis[x]).subtract(itrspsdf[x]).add(poolToInts[x]);
                }
            }

            mv.addObject("monthlyintsum", monthlyintsum);
            mv.addObject("monthlyaccsum", monthlyaccsum);
            mv.addObject("openbalacc", openbalacc);
            mv.addObject("openbalint", openbalint);
            mv.addObject("payrectotal", payrectotal);
            mv.addObject("paydistotal", paydistotal);
            mv.addObject("intrectotal", intrectotal);
            mv.addObject("intdistotal", intdistotal);
            mv.addObject("ptrspsdftotal", ptrspsdftotal);
            mv.addObject("itrspsdftotal", itrspsdftotal);
            mv.addObject("itrspsdf", itrspsdf);
            mv.addObject("ptrspsdf", ptrspsdf);
            mv.addObject("intdis", intdis);
            mv.addObject("paydis", paydis);
            mv.addObject("intrec", intrec);
            mv.addObject("payrec", payrec);
            mv.addObject("monthlist", monthlist);
            mv.addObject("year", year);
            return mv;
        }

        return mv;
    }

    public ModelAndView statusSummaryReport(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("NewReport/statusSummaryReport");

        Date date1 = new Date();

        String datee, month;
        if (date1.getMonth() < 9) {
            month = "0" + String.valueOf(date1.getMonth() + 1);
        } else {
            month = String.valueOf(date1.getMonth() + 1);
        }

        if (date1.getDate() < 10) {
            datee = "0" + date1.getDate();
        } else {
            datee = String.valueOf(date1.getDate());
        }

        String date = datee + "/" + month + "/" + String.valueOf(date1.getYear() + 1900);

        String date2 = datee + "-" + month + "-" + String.valueOf(date1.getYear() + 1900);

        NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
        NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();
        InterestDetailsDAO idao = new InterestDetailsDAO();
        DisbursedInterestDetailsDAO idao2 = new DisbursedInterestDetailsDAO();

        List<BillPayableCorp> pay = bpdao.getAlldues(date2);
        List<BillReceiveCorp> rec = brdao.getAlldues(date2);
        List<InterestDetails> idues1 = idao.getInterestdues(date2);
        List<DisbursedInterestDetails> idues2 = idao2.getInterestdues(date2);

//        List<BillPayableCorp> DSMpay = new ArrayList();
//        List<BillPayableCorp> RRASpay = new ArrayList();
//        List<BillPayableCorp> AGCpay = new ArrayList();
//        List<BillPayableCorp> FRASpay = new ArrayList();
//        List<BillReceiveCorp> DSMrec = new ArrayList();
//        List<BillReceiveCorp> RRASrec = new ArrayList();
//        List<BillReceiveCorp> AGCrec = new ArrayList();
//        List<BillReceiveCorp> FRASrec = new ArrayList();
//        for(BillPayableCorp bpay : dues1) {
//            if(bpay.getBillType().equalsIgnoreCase("DSM"))
//                DSMpay.add(bpay);
//            if(bpay.getBillType().equalsIgnoreCase("RRAS"))
//                RRASpay.add(bpay);
//            if(bpay.getBillType().equalsIgnoreCase("AGC"))
//                AGCpay.add(bpay);
//            if(bpay.getBillType().equalsIgnoreCase("FRAS"))
//                FRASpay.add(bpay);
//        }
//        for(BillReceiveCorp bpay : dues2) {
//            if(bpay.getBillType().equalsIgnoreCase("DSM"))
//                DSMrec.add(bpay);
//            if(bpay.getBillType().equalsIgnoreCase("RRAS"))
//                RRASrec.add(bpay);
//            if(bpay.getBillType().equalsIgnoreCase("AGC"))
//                AGCrec.add(bpay);
//            if(bpay.getBillType().equalsIgnoreCase("FRAS"))
//                FRASrec.add(bpay);
//        }
//        BigDecimal tempweek = null;
//        BigDecimal tempyear = null;
//        BigDecimal temprevno = null;
//        for(int a=0; a<DSMpay.size()-1; a++) {
//            tempweek = DSMpay.get(a).getWeekId(); tempyear = DSMpay.get(a).getBillYear(); temprevno = DSMpay.get(a).getRevisionNo();
//            for(int b=1; b<DSMpay.size(); b++) {
//                if(b<DSMpay.size())
//                if(DSMpay.get(b).getWeekId().compareTo(tempweek)==0 && DSMpay.get(b).getBillYear().compareTo(tempyear)==0 && DSMpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    DSMpay.remove(a);
//                if(b<DSMpay.size())
//                if(DSMpay.get(b).getWeekId().compareTo(tempweek)==0 && DSMpay.get(b).getBillYear().compareTo(tempyear)==0 && DSMpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    DSMpay.remove(b);
//            }
//        }
//        for(int a=0; a<RRASpay.size()-1; a++) {
//            tempweek = RRASpay.get(a).getWeekId(); tempyear = RRASpay.get(a).getBillYear(); temprevno = RRASpay.get(a).getRevisionNo();
//            for(int b=1; b<RRASpay.size(); b++) {
//                if(b<RRASpay.size())
//                if(RRASpay.get(b).getWeekId().compareTo(tempweek)==0 && RRASpay.get(b).getBillYear().compareTo(tempyear)==0 && RRASpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    RRASpay.remove(a);
//                if(b<RRASpay.size())
//                if(RRASpay.get(b).getWeekId().compareTo(tempweek)==0 && RRASpay.get(b).getBillYear().compareTo(tempyear)==0 && RRASpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    RRASpay.remove(b);
//            }
//        }
//        for(int a=0; a<AGCpay.size()-1; a++) {
//            tempweek = AGCpay.get(a).getWeekId(); tempyear = AGCpay.get(a).getBillYear(); temprevno = AGCpay.get(a).getRevisionNo();
//            for(int b=1; b<AGCpay.size(); b++) {
//                if(b<AGCpay.size())
//                if(AGCpay.get(b).getWeekId().compareTo(tempweek)==0 && AGCpay.get(b).getBillYear().compareTo(tempyear)==0 && AGCpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    AGCpay.remove(a);
//                if(b<AGCpay.size())
//                if(AGCpay.get(b).getWeekId().compareTo(tempweek)==0 && AGCpay.get(b).getBillYear().compareTo(tempyear)==0 && AGCpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    AGCpay.remove(b);
//            }
//        }
//        for(int a=0; a<FRASpay.size()-1; a++) {
//            tempweek = FRASpay.get(a).getWeekId(); tempyear = FRASpay.get(a).getBillYear(); temprevno = FRASpay.get(a).getRevisionNo();
//            for(int b=1; b<FRASpay.size(); b++) {
//                if(b<FRASpay.size())
//                if(FRASpay.get(b).getWeekId().compareTo(tempweek)==0 && FRASpay.get(b).getBillYear().compareTo(tempyear)==0 && FRASpay.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    FRASpay.remove(a);
//                if(b<FRASpay.size())
//                if(FRASpay.get(b).getWeekId().compareTo(tempweek)==0 && FRASpay.get(b).getBillYear().compareTo(tempyear)==0 && FRASpay.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    FRASpay.remove(b);
//            }
//        }
//        
//        for(int a=0; a<DSMrec.size()-1; a++) {
//            tempweek = DSMrec.get(a).getWeekId(); tempyear = DSMrec.get(a).getBillYear(); temprevno = DSMrec.get(a).getRevisionNo();
//            for(int b=1; b<DSMrec.size(); b++) {
//                if(b<DSMrec.size())
//                if(DSMrec.get(b).getWeekId().compareTo(tempweek)==0 && DSMrec.get(b).getBillYear().compareTo(tempyear)==0 && DSMrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    DSMrec.remove(a);
//                if(b<DSMrec.size())
//                if(DSMrec.get(b).getWeekId().compareTo(tempweek)==0 && DSMrec.get(b).getBillYear().compareTo(tempyear)==0 && DSMrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    DSMrec.remove(b);
//            }
//        }
//        for(int a=0; a<RRASrec.size()-1; a++) {
//            tempweek = RRASrec.get(a).getWeekId(); tempyear = RRASrec.get(a).getBillYear(); temprevno = RRASrec.get(a).getRevisionNo();
//            for(int b=1; b<RRASrec.size(); b++) {
//                if(b<RRASrec.size())
//                if(RRASrec.get(b).getWeekId().compareTo(tempweek)==0 && RRASrec.get(b).getBillYear().compareTo(tempyear)==0 && RRASrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    RRASrec.remove(a);
//                if(b<RRASrec.size())
//                if(RRASrec.get(b).getWeekId().compareTo(tempweek)==0 && RRASrec.get(b).getBillYear().compareTo(tempyear)==0 && RRASrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    RRASrec.remove(b);
//            }
//        }
//        for(int a=0; a<AGCrec.size()-1; a++) {
//            tempweek = AGCrec.get(a).getWeekId(); tempyear = AGCrec.get(a).getBillYear(); temprevno = AGCrec.get(a).getRevisionNo();
//            for(int b=1; b<AGCrec.size(); b++) {
//                if(b<AGCrec.size())
//                if(AGCrec.get(b).getWeekId().compareTo(tempweek)==0 && AGCrec.get(b).getBillYear().compareTo(tempyear)==0 && AGCrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    AGCrec.remove(a);
//                if(b<AGCrec.size())
//                if(AGCrec.get(b).getWeekId().compareTo(tempweek)==0 && AGCrec.get(b).getBillYear().compareTo(tempyear)==0 && AGCrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    AGCrec.remove(b);
//            }
//        }
//        for(int a=0; a<FRASrec.size()-1; a++) {
//            tempweek = FRASrec.get(a).getWeekId(); tempyear = FRASrec.get(a).getBillYear(); temprevno = FRASrec.get(a).getRevisionNo();
//            for(int b=1; b<FRASrec.size(); b++) {
//                if(b<FRASrec.size())
//                if(FRASrec.get(b).getWeekId().compareTo(tempweek)==0 && FRASrec.get(b).getBillYear().compareTo(tempyear)==0 && FRASrec.get(b).getRevisionNo().compareTo(temprevno)==1)
//                    FRASrec.remove(a);
//                if(b<FRASrec.size())
//                if(FRASrec.get(b).getWeekId().compareTo(tempweek)==0 && FRASrec.get(b).getBillYear().compareTo(tempyear)==0 && FRASrec.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                    FRASrec.remove(b);
//            }
//        }
//        dues1 = DSMpay;
//        dues2 = DSMrec;
//        
//        for(BillPayableCorp bp : RRASpay)
//            dues1.add(bp);
//        for(BillPayableCorp bp : AGCpay)
//            dues1.add(bp);
//        for(BillPayableCorp bp : FRASpay)
//            dues1.add(bp);
//        
//        for(BillReceiveCorp br : RRASrec)
//            dues2.add(br);
//        for(BillReceiveCorp br : AGCrec)
//            dues2.add(br);
//        for(BillReceiveCorp br : FRASrec)
//            dues2.add(br);
        List<BillPayableCorp> dues1 = new ArrayList();
        List<BillReceiveCorp> dues2 = new ArrayList();

        for (int i = 0; i < pay.size(); i++) {
            if (pay.get(i).getBillStatus().equalsIgnoreCase("REFUND")) {

                BillReceiveCorp obj = new BillReceiveCorp();
                obj.setDisburseStatus("NOT PAID");
                obj.setPendingAmount(pay.get(i).getPendingAmount());
                obj.setCorporates(pay.get(i).getCorporates());
                obj.setBillDueDate(pay.get(i).getBillDueDate());

                dues2.add(obj);
            } else {
                dues1.add(pay.get(i));
            }
        }
        for (int i = 0; i < rec.size(); i++) {
            if (rec.get(i).getDisburseStatus().equalsIgnoreCase("REFUND")) {

                BillPayableCorp obj = new BillPayableCorp();
                obj.setBillStatus("NOT PAID");
                obj.setPendingAmount(rec.get(i).getPendingAmount());
                obj.setCorporates(rec.get(i).getCorporates());
                obj.setBillDueDate(rec.get(i).getBillDueDate());

                dues1.add(obj);
            } else {
                dues2.add(rec.get(i));
            }
        }

        List<String> corps = new ArrayList();

        for (BillPayableCorp bp : dues1) {
            if (!corps.contains(bp.getCorporates().getCorporateName())) {
                corps.add(bp.getCorporates().getCorporateName());
            }
        }

        for (BillReceiveCorp br : dues2) {
            if (!corps.contains(br.getCorporates().getCorporateName())) {
                corps.add(br.getCorporates().getCorporateName());
            }
        }

        for (InterestDetails id : idues1) {
            if (!corps.contains(id.getCorporates().getCorporateName())) {
                corps.add(id.getCorporates().getCorporateName());
            }
        }

        for (DisbursedInterestDetails i : idues2) {
            if (!corps.contains(i.getCorporates().getCorporateName())) {
                corps.add(i.getCorporates().getCorporateName());
            }
        }

        String[][] arr = new String[corps.size()][7];
        for (int i = 0; i < corps.size(); i++) {
            for (int j = 0; j < 7; j++) {
                arr[i][j] = "0";
            }
        }

        for (int k = 0; k < corps.size(); k++) {

            for (BillPayableCorp bp : dues1) {
                if (bp.getCorporates().getCorporateName().equalsIgnoreCase(corps.get(k))) {
                    arr[k][0] = corps.get(k);
                    arr[k][1] = new BigDecimal(arr[k][1]).add(bp.getPendingAmount()).toPlainString();
                    if (bp.getBillDueDate().compareTo(date1) < 0) {
                        arr[k][3] = new BigDecimal(arr[k][3]).add(bp.getPendingAmount()).toPlainString();
                    }
                }
            }

            for (BillReceiveCorp br : dues2) {
                if (br.getCorporates().getCorporateName().equalsIgnoreCase(corps.get(k))) {
                    arr[k][0] = corps.get(k);
                    arr[k][2] = new BigDecimal(arr[k][2]).add(br.getPendingAmount()).toPlainString();
                    if (br.getBillDueDate().compareTo(date1) < 0) {
                        arr[k][4] = new BigDecimal(arr[k][4]).add(br.getPendingAmount()).toPlainString();
                    }
                }
            }

            for (InterestDetails id : idues1) {
                if (id.getCorporates().getCorporateName().equalsIgnoreCase(corps.get(k))) {
                    arr[k][0] = corps.get(k);
                    arr[k][5] = new BigDecimal(arr[k][5]).add(id.getInterestPendingamount()).toPlainString();
                }
            }

            for (DisbursedInterestDetails did : idues2) {
                if (did.getCorporates().getCorporateName().equalsIgnoreCase(corps.get(k))) {
                    arr[k][0] = corps.get(k);
                    arr[k][6] = new BigDecimal(arr[k][6]).add(did.getInterestPendingamount()).toPlainString();
                }
            }
        }

        mv.addObject("date", date);
        mv.addObject("arr", arr);
        return mv;
    }

    public ModelAndView selectprevFYPayableweeks(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("NewReport/prevFYpayable");

        String dbType = request.getParameter("dbType");
        String type = request.getParameter("TYPE");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            ModelAndView mv1 = new ModelAndView("NewReport/prevFYPayableweeks");

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
                if (quar.equals("quar1")) {
                    week_id1 = 1;
                } else {
                    week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                }
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = week_id1;
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("FROM WEEK " + week_id1 + " TO WEEK " + week_id2 + " YEAR IS " + yeari + " BILL_TYPE IS " + type);

            NewReportDAO rpt = new NewReportDAO();
            List<BillPayableCorp> list1 = rpt.getPayableWeeks2(week_id1, week_id2, type, yeari);
            List<BillReceiveCorp> list2 = rpt.getPayableWeeksInReceive(week_id1, week_id2, type, yeari);

            BillPayableCorp obj = null;
            BigDecimal tempyear = null;
            BigDecimal temprevno = null;
            BigDecimal tempweekno = null;
            BigDecimal temprevisedrefund = null;

            if (list1 != null) {
                for (int a = 0; a < list1.size() - 1; a++) {
                    tempweekno = list1.get(a).getWeekId();
                    tempyear = list1.get(a).getBillYear();
                    temprevno = list1.get(a).getRevisionNo();
                    for (int b = 1; b < list1.size(); b++) {
                        tempweekno = list1.get(a).getWeekId();
                        tempyear = list1.get(a).getBillYear();
                        temprevno = list1.get(a).getRevisionNo();

                        if (b < list1.size()) {
                            if (list1.get(b).getWeekId().compareTo(tempweekno) == 0 && list1.get(b).getBillYear().compareTo(tempyear) == 0 && list1.get(b).getRevisionNo().compareTo(temprevno) == 1) {
                                list1.remove(a);
                            }
                        }

                        tempweekno = list1.get(a).getWeekId();
                        tempyear = list1.get(a).getBillYear();
                        temprevno = list1.get(a).getRevisionNo();

                        if (b < list1.size()) {
                            if (list1.get(b).getWeekId().compareTo(tempweekno) == 0 && list1.get(b).getBillYear().compareTo(tempyear) == 0 && list1.get(b).getRevisionNo().compareTo(temprevno) == -1) {
                                list1.remove(b);
                            }
                        }

                        tempweekno = list1.get(a).getWeekId();
                        tempyear = list1.get(a).getBillYear();
                        temprevno = list1.get(a).getRevisionNo();
                    }
                }
            }
            if (list2 != null) {
                for (int a = 0; a < list2.size() - 1; a++) {
                    tempweekno = list2.get(a).getWeekId();
                    tempyear = list2.get(a).getBillYear();
                    temprevno = list2.get(a).getRevisionNo();
                    for (int b = 1; b < list2.size(); b++) {
                        tempweekno = list2.get(a).getWeekId();
                        tempyear = list2.get(a).getBillYear();
                        temprevno = list2.get(a).getRevisionNo();

                        if (b < list2.size()) {
                            if (list2.get(b).getWeekId().compareTo(tempweekno) == 0 && list2.get(b).getBillYear().compareTo(tempyear) == 0 && list2.get(b).getRevisionNo().compareTo(temprevno) == 1) {
                                list2.remove(a);
                            }
                        }

                        tempweekno = list2.get(a).getWeekId();
                        tempyear = list2.get(a).getBillYear();
                        temprevno = list2.get(a).getRevisionNo();

                        if (b < list2.size()) {
                            if (list2.get(b).getWeekId().compareTo(tempweekno) == 0 && list2.get(b).getBillYear().compareTo(tempyear) == 0 && list2.get(b).getRevisionNo().compareTo(temprevno) == -1) {
                                list2.remove(b);
                            }
                        }

                        tempweekno = list2.get(a).getWeekId();
                        tempyear = list2.get(a).getBillYear();
                        temprevno = list2.get(a).getRevisionNo();
                    }
                }
            }

            if (list2 != null) {
                for (int i = 0; i < list2.size(); i++) {
                    tempyear = list2.get(i).getBillYear();
                    temprevno = list2.get(i).getRevisionNo();
                    tempweekno = list2.get(i).getWeekId();
                    temprevisedrefund = list2.get(i).getRevisedrefund();
                    obj = new BillPayableCorp();
                    obj.setBillYear(tempyear);
                    obj.setRevisionNo(temprevno);
                    obj.setWeekId(tempweekno);
                    obj.setCorporates(list2.get(i).getCorporates());
                    obj.setBillingDate(list2.get(i).getBillingDate());

                    //if(temprevno.compareTo(BigDecimal.ZERO)==0)
                    //obj.setTotalnet(temprevisedrefund);
                    if (list2.get(i).getToalnet().compareTo(BigDecimal.ZERO) == -1) {
                        obj.setTotalnet(new BigDecimal(Math.abs(list2.get(i).getToalnet().longValue())));
                    } else {
                        obj.setTotalnet(BigDecimal.ZERO);
                    }
                    //else
                    obj.setRevisedpaybale(temprevisedrefund);
                    list1.add(obj);
                }
            }

            List<String> corpnames = new ArrayList();

            if (list1 != null) {
                for (int z = 0; z < list1.size(); z++) {
                    if (!corpnames.contains(list1.get(z).getCorporates().getCorporateName())) {
                        corpnames.add(list1.get(z).getCorporates().getCorporateName());
                    }
                }
            }

            Collections.sort(corpnames);

            String[][] arr = new String[corpnames.size()][week_id2 - week_id1 + 2];
            BigDecimal temptotal = BigDecimal.ZERO;
            BigDecimal[] nettotal = new BigDecimal[week_id2 - week_id1 + 2];
            Date[] accdate = new Date[week_id2 - week_id1 + 1];
            Arrays.fill(nettotal, BigDecimal.ZERO);

            for (int a = 0; a < corpnames.size(); a++) {
                arr[a][0] = corpnames.get(a);

                for (int b = week_id1; b <= week_id2; b++) {
                    for (BillPayableCorp ele : list1) {
                        if (ele.getCorporates().getCorporateName().equalsIgnoreCase(corpnames.get(a)) && ele.getWeekId().compareTo(new BigDecimal(b)) == 0) {

                            if (ele.getTotalnet().compareTo(BigDecimal.ZERO) == 1) {
                                if (ele.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                    arr[a][b - week_id1 + 1] = ele.getTotalnet().toPlainString();
                                    temptotal = temptotal.add(ele.getTotalnet());
                                } else {
                                    //arr[a][b-week_id1+1] = ele.getRevisedpaybale().toPlainString();
                                    arr[a][b - week_id1 + 1] = ele.getTotalnet().toPlainString();
                                    //temptotal = temptotal.add(ele.getRevisedpaybale());
                                    temptotal = temptotal.add(ele.getTotalnet());
                                }
                            }

                            accdate[b - week_id1] = ele.getBillingDate();
                        }
                    }
                    if (arr[a][b - week_id1 + 1] == null || !(Float.valueOf(arr[a][b - week_id1 + 1]) > 0)) {
                        arr[a][b - week_id1 + 1] = "FALSE";
                    }
                }
                //arr[a][week_id2-week_id1+2] = temptotal.toPlainString();
                temptotal = BigDecimal.ZERO;
            }

            for (int y = week_id1; y <= week_id2; y++) {
                for (int x = 0; x < corpnames.size(); x++) {
                    if (!arr[x][y - week_id1 + 1].equalsIgnoreCase("FALSE")) {
                        nettotal[y - week_id1] = nettotal[y - week_id1].add(new BigDecimal(arr[x][y - week_id1 + 1]));
                    }
                }
            }

            session1.setAttribute("values", arr);
            session1.setAttribute("weekstart", week_id1);
            session1.setAttribute("weekend", week_id2);
            session1.setAttribute("nettotals", nettotal);
            session1.setAttribute("accdate", accdate);

            mv1.addObject("year", yeari);
            return mv1;
        }

        mv.addObject("title", "1.1 Previous FY Payable Weeks");
        return mv;
    }

    public ModelAndView getprevFYPayableweeks(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("NewReport/getprevFYPayableweeks");

        String[][] arr = (String[][]) session1.getAttribute("values");
        int weekend = (int) session1.getAttribute("weekend");
        int weekstart = (int) session1.getAttribute("weekstart");
        BigDecimal[] nettotal = (BigDecimal[]) session1.getAttribute("nettotals");
        Date[] accdate = (Date[]) session1.getAttribute("accdate");

        mv.addObject("payweeks", arr);
        mv.addObject("weekend", weekend);
        mv.addObject("weekstart", weekstart);
        mv.addObject("nettotals", nettotal);
        mv.addObject("accdate", accdate);

        return mv;
    }

    public ModelAndView selectLCYear(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("NewReport/selectLCFY");

        String dbType = request.getParameter("dbType");
        String type = request.getParameter("TYPE");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            ModelAndView mv1 = new ModelAndView("NewReport/getLCFYdetails");

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
                if (quar.equals("quar1")) {
                    week_id1 = 1;
                } else {
                    week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                }
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = week_id1;
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("FROM WEEK " + week_id1 + " TO WEEK " + week_id2 + " YEAR IS " + yeari + " BILL_TYPE IS " + type);

            NewReportDAO rpt = new NewReportDAO();
            List<BillPayableCorp> list1 = rpt.getPayableWeeks2(week_id1, week_id2, type, yeari);
            List<BillReceiveCorp> list2 = rpt.getPayableWeeksInReceive(week_id1, week_id2, type, yeari);
            //DefaultLocDetailsDAO dlocdao = new DefaultLocDetailsDAO();

            BillPayableCorp obj = null;
            BigDecimal tempyear = null;
            BigDecimal temprevno = null;
            BigDecimal tempweekno = null;
            BigDecimal temprevisedrefund = null;

            if (list1 != null) {
                for (int a = 0; a < list1.size() - 1; a++) {
                    tempweekno = list1.get(a).getWeekId();
                    tempyear = list1.get(a).getBillYear();
                    temprevno = list1.get(a).getRevisionNo();
                    for (int b = 1; b < list1.size(); b++) {
                        tempweekno = list1.get(a).getWeekId();
                        tempyear = list1.get(a).getBillYear();
                        temprevno = list1.get(a).getRevisionNo();

                        if (b < list1.size()) {
                            if (list1.get(b).getWeekId().compareTo(tempweekno) == 0 && list1.get(b).getBillYear().compareTo(tempyear) == 0 && list1.get(b).getRevisionNo().compareTo(temprevno) == 1) {
                                list1.remove(a);
                            }
                        }

                        tempweekno = list1.get(a).getWeekId();
                        tempyear = list1.get(a).getBillYear();
                        temprevno = list1.get(a).getRevisionNo();

                        if (b < list1.size()) {
                            if (list1.get(b).getWeekId().compareTo(tempweekno) == 0 && list1.get(b).getBillYear().compareTo(tempyear) == 0 && list1.get(b).getRevisionNo().compareTo(temprevno) == -1) {
                                list1.remove(b);
                            }
                        }

                        tempweekno = list1.get(a).getWeekId();
                        tempyear = list1.get(a).getBillYear();
                        temprevno = list1.get(a).getRevisionNo();
                    }
                }
            }

            if (list2 != null) {
                for (int a = 0; a < list2.size() - 1; a++) {
                    tempweekno = list2.get(a).getWeekId();
                    tempyear = list2.get(a).getBillYear();
                    temprevno = list2.get(a).getRevisionNo();
                    for (int b = 1; b < list2.size(); b++) {
                        tempweekno = list2.get(a).getWeekId();
                        tempyear = list2.get(a).getBillYear();
                        temprevno = list2.get(a).getRevisionNo();

                        if (b < list2.size()) {
                            if (list2.get(b).getWeekId().compareTo(tempweekno) == 0 && list2.get(b).getBillYear().compareTo(tempyear) == 0 && list2.get(b).getRevisionNo().compareTo(temprevno) == 1) {
                                list2.remove(a);
                            }
                        }

                        tempweekno = list2.get(a).getWeekId();
                        tempyear = list2.get(a).getBillYear();
                        temprevno = list2.get(a).getRevisionNo();

                        if (b < list2.size()) {
                            if (list2.get(b).getWeekId().compareTo(tempweekno) == 0 && list2.get(b).getBillYear().compareTo(tempyear) == 0 && list2.get(b).getRevisionNo().compareTo(temprevno) == -1) {
                                list2.remove(b);
                            }
                        }

                        tempweekno = list2.get(a).getWeekId();
                        tempyear = list2.get(a).getBillYear();
                        temprevno = list2.get(a).getRevisionNo();
                    }
                }
            }

            if (list2 != null) {
                for (int i = 0; i < list2.size(); i++) {
                    tempyear = list2.get(i).getBillYear();
                    temprevno = list2.get(i).getRevisionNo();
                    tempweekno = list2.get(i).getWeekId();
                    temprevisedrefund = list2.get(i).getRevisedrefund();
                    obj = new BillPayableCorp();
                    obj.setBillYear(tempyear);
                    obj.setRevisionNo(temprevno);
                    obj.setWeekId(tempweekno);
                    obj.setCorporates(list2.get(i).getCorporates());
                    obj.setBillingDate(list2.get(i).getBillingDate());

                    //if(temprevno.compareTo(BigDecimal.ZERO)==0)
                    //obj.setTotalnet(temprevisedrefund);
                    if (list2.get(i).getToalnet().compareTo(BigDecimal.ZERO) == -1) {
                        obj.setTotalnet(new BigDecimal(Math.abs(list2.get(i).getToalnet().longValue())));
                    } else {
                        obj.setTotalnet(BigDecimal.ZERO);
                    }
                    //else
                    obj.setRevisedpaybale(temprevisedrefund);
                    list1.add(obj);
                }
            }

            //List<DefaultLocDetails> lclist = dlocdao.getLCDetailsByWeek(week_id1, week_id2, yeari, type);
            List<String> corpnames = new ArrayList();

//            if(lclist!=null)
//            for(int z=0; z<lclist.size(); z++)
//                if(!corpnames.contains(lclist.get(z).getCorporates().getCorporateName()))
//                    corpnames.add(lclist.get(z).getCorporates().getCorporateName());
            if (list1 != null) {
                for (int z = 0; z < list1.size(); z++) {
                    if (!corpnames.contains(list1.get(z).getCorporates().getCorporateName())) {
                        corpnames.add(list1.get(z).getCorporates().getCorporateName());
                    }
                }
            }

            Collections.sort(corpnames);

            String[][] arr = new String[corpnames.size()][week_id2 - week_id1 + 8];
            BigDecimal temptotal = BigDecimal.ZERO;
            BigDecimal tempvar1 = BigDecimal.ZERO;
            BigDecimal[] nettotal = new BigDecimal[week_id2 - week_id1 + 6];
            Date[] accdate = new Date[week_id2 - week_id1 + 1];
            Arrays.fill(nettotal, BigDecimal.ZERO);
            int no_weeks_payable = 0;
            int total_weeks_payable = 0;

            for (int a = 0; a < corpnames.size(); a++) {
                arr[a][0] = corpnames.get(a);
                arr[a][week_id2 - week_id1 + 7] = "";

                for (int b = week_id1; b <= week_id2; b++) {
                    for (BillPayableCorp ele : list1) {
                        if (ele.getCorporates().getCorporateName().equalsIgnoreCase(corpnames.get(a)) && ele.getWeekId().compareTo(new BigDecimal(b)) == 0) {
                            //arr[a][b-week_id1+1] = ele.getBillAmount().toPlainString();
                            //temptotal = temptotal.add(ele.getBillAmount());

                            //---------------
                            if (ele.getTotalnet().compareTo(BigDecimal.ZERO) == 1) {
                                if (ele.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                    arr[a][b - week_id1 + 1] = ele.getTotalnet().toPlainString();
                                    temptotal = temptotal.add(ele.getTotalnet());
                                } else {
                                    arr[a][b - week_id1 + 1] = ele.getTotalnet().toPlainString();
                                    temptotal = temptotal.add(ele.getTotalnet());
                                }

                                ++no_weeks_payable;
                                arr[a][week_id2 - week_id1 + 7] = arr[a][week_id2 - week_id1 + 7] + " " + ele.getWeekId() + ",";
                            }

                            if (accdate[b - week_id1] == null) {
                                accdate[b - week_id1] = ele.getBillingDate();
                            }
                        }
                    }

                    if (arr[a][b - week_id1 + 1] == null || !(Float.valueOf(arr[a][b - week_id1 + 1]) > 0)) {
                        arr[a][b - week_id1 + 1] = "";
                    }
                }

                arr[a][week_id2 - week_id1 + 2] = temptotal.toPlainString();
                arr[a][week_id2 - week_id1 + 3] = String.valueOf(no_weeks_payable);

                tempvar1 = tempvar1.add(temptotal.divide(new BigDecimal(no_weeks_payable), 2, BigDecimal.ROUND_HALF_EVEN));

                arr[a][week_id2 - week_id1 + 4] = tempvar1.toPlainString();
                arr[a][week_id2 - week_id1 + 5] = tempvar1.multiply(new BigDecimal(1.1)).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString();
                arr[a][week_id2 - week_id1 + 6] = tempvar1.multiply(new BigDecimal(1.1)).divide(new BigDecimal(100000), 2, BigDecimal.ROUND_HALF_EVEN).toPlainString();

                temptotal = BigDecimal.ZERO;
                tempvar1 = BigDecimal.ZERO;
                total_weeks_payable += no_weeks_payable;
                no_weeks_payable = 0;
            }

            for (int x = week_id1; x <= week_id2 + 2; x++) {
                for (int y = 0; y < corpnames.size(); y++) {
                    if (arr[y][x - week_id1 + 1] != null && arr[y][x - week_id1 + 1] != "") {
                        nettotal[x - week_id1] = nettotal[x - week_id1].add(new BigDecimal(arr[y][x - week_id1 + 1]));
                    }
                }
            }

            nettotal[week_id2 - week_id1 + 2] = BigDecimal.valueOf(total_weeks_payable);
            nettotal[week_id2 - week_id1 + 3] = nettotal[week_id2 - week_id1 + 1].divide(nettotal[week_id2 - week_id1 + 2], 2, BigDecimal.ROUND_HALF_EVEN);
            nettotal[week_id2 - week_id1 + 4] = nettotal[week_id2 - week_id1 + 3].multiply(new BigDecimal(1.1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            nettotal[week_id2 - week_id1 + 5] = nettotal[week_id2 - week_id1 + 4].divide(new BigDecimal(100000), 2, BigDecimal.ROUND_HALF_EVEN);

            session1.setAttribute("lcweeks", arr);
            session1.setAttribute("weekstart", week_id1);
            session1.setAttribute("weekend", week_id2);
            session1.setAttribute("nettotals", nettotal);
            session1.setAttribute("year", yeari);
            session1.setAttribute("accdate", accdate);

            return mv1;
        }

        mv.addObject("title", "1.2 DSM LC FYwise");
        return mv;
    }

    public ModelAndView LCFYdetails(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("NewReport/LCFYdetails");

        String[][] arr = (String[][]) session1.getAttribute("lcweeks");
        int weekend = (int) session1.getAttribute("weekend");
        int weekstart = (int) session1.getAttribute("weekstart");
        BigDecimal[] nettotal = (BigDecimal[]) session1.getAttribute("nettotals");
        int year = (int) session1.getAttribute("year");
        Date[] accdate = (Date[]) session1.getAttribute("accdate");

        mv.addObject("lcweeks", arr);
        mv.addObject("weekend", weekend);
        mv.addObject("weekstart", weekstart);
        mv.addObject("nettotals", nettotal);
        mv.addObject("year", year);
        mv.addObject("accdate", accdate);

        return mv;
    }

    public ModelAndView getOldNLDC(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("NewReport/getOldNLDC");

        String dbType = request.getParameter("dbType");

        System.out.println("dbType is " + dbType);
        int week_id1 = 0;
        int week_id2 = 0;
        int yeari = 0;

        if (dbType != null) {

            ModelAndView mv1 = new ModelAndView("NewReport/viewOldNLDC");

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
                if (quar.equals("quar1")) {
                    week_id1 = 1;
                } else {
                    week_id1 = cl.get(Calendar.WEEK_OF_YEAR);
                }
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = week_id1;
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("FROM WEEK " + week_id1 + " TO WEEK " + week_id2 + " YEAR IS " + yeari);

            NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
            NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();

            List<BillPayableCorp> allpays = bpdao.getAllBillsPayable(week_id1, week_id2, yeari);
            List<BillReceiveCorp> allrecs = brdao.getAllBillsReceive(week_id1, week_id2, yeari);

            List<BillPayableCorp> DSMpays = new ArrayList();
            List<BillPayableCorp> RRASpays = new ArrayList();
            List<BillPayableCorp> AGCpays = new ArrayList();
            List<BillPayableCorp> SRASpays = new ArrayList();
            List<BillPayableCorp> TRASpays = new ArrayList();
            List<BillReceiveCorp> DSMrecs = new ArrayList();
            List<BillReceiveCorp> RRASrecs = new ArrayList();
            List<BillReceiveCorp> AGCrecs = new ArrayList();
            List<BillReceiveCorp> SRASrecs = new ArrayList();
            List<BillReceiveCorp> TRASrecs = new ArrayList();

            List<BillPayableCorp> allpayable = new ArrayList();
            List<BillReceiveCorp> allreceive = new ArrayList();

            for (BillPayableCorp x1 : allpays) {
                if (x1.getBillType().equalsIgnoreCase("DSM")) {
                    DSMpays.add(x1);
                } else if (x1.getBillType().equalsIgnoreCase("RRAS")) {
                    RRASpays.add(x1);
                } else if (x1.getBillType().equalsIgnoreCase("AGC")) {
                    AGCpays.add(x1);
                } else if (x1.getBillType().equalsIgnoreCase("SRAS")) {
                    SRASpays.add(x1);
                }else if (x1.getBillType().equalsIgnoreCase("TRASM")  || x1.getBillType().equalsIgnoreCase("TRASS") || x1.getBillType().equalsIgnoreCase("TRASE")) {
                    TRASpays.add(x1);
                }
            }
            for (BillReceiveCorp x2 : allrecs) {
                if (x2.getBillType().equalsIgnoreCase("DSM")) {
                    DSMrecs.add(x2);
                } else if (x2.getBillType().equalsIgnoreCase("RRAS")) {
                    RRASrecs.add(x2);
                } else if (x2.getBillType().equalsIgnoreCase("AGC")) {
                    AGCrecs.add(x2);
                }else if (x2.getBillType().equalsIgnoreCase("SRAS")) {
                    SRASrecs.add(x2);
                }else if (x2.getBillType().equalsIgnoreCase("TRASM")  || x2.getBillType().equalsIgnoreCase("TRASS") || x2.getBillType().equalsIgnoreCase("TRASE")) {
                    TRASrecs.add(x2);
                }
            }

//            BigDecimal tempweek = null;
//            BigDecimal tempyear = null;
//            BigDecimal temprevno = null;
            /*for(int a=0; a<DSMpays.size()-1; a++) {
             tempweek = DSMpays.get(a).getWeekId(); tempyear = DSMpays.get(a).getBillYear(); temprevno = DSMpays.get(a).getRevisionNo();
             for(int b=1; b<DSMpays.size(); b++) {
             if(b<DSMpays.size())
             if(DSMpays.get(b).getWeekId().compareTo(tempweek)==0 && DSMpays.get(b).getBillYear().compareTo(tempyear)==0 && DSMpays.get(b).getRevisionNo().compareTo(temprevno)==1)
             DSMpays.remove(a);
             if(b<DSMpays.size())
             if(DSMpays.get(b).getWeekId().compareTo(tempweek)==0 && DSMpays.get(b).getBillYear().compareTo(tempyear)==0 && DSMpays.get(b).getRevisionNo().compareTo(temprevno)==-1)
             DSMpays.remove(b);
             }
             }
             for(int a=0; a<RRASpays.size()-1; a++) {
             tempweek = RRASpays.get(a).getWeekId(); tempyear = RRASpays.get(a).getBillYear(); temprevno = RRASpays.get(a).getRevisionNo();
             for(int b=1; b<RRASpays.size(); b++) {
             if(b<RRASpays.size())
             if(RRASpays.get(b).getWeekId().compareTo(tempweek)==0 && RRASpays.get(b).getBillYear().compareTo(tempyear)==0 && RRASpays.get(b).getRevisionNo().compareTo(temprevno)==1)
             RRASpays.remove(a);
             if(b<RRASpays.size())
             if(RRASpays.get(b).getWeekId().compareTo(tempweek)==0 && RRASpays.get(b).getBillYear().compareTo(tempyear)==0 && RRASpays.get(b).getRevisionNo().compareTo(temprevno)==-1)
             RRASpays.remove(b);
             }
             }
             for(int a=0; a<AGCpays.size()-1; a++) {
             tempweek = AGCpays.get(a).getWeekId(); tempyear = AGCpays.get(a).getBillYear(); temprevno = AGCpays.get(a).getRevisionNo();
             for(int b=1; b<AGCpays.size(); b++) {
             if(b<AGCpays.size())
             if(AGCpays.get(b).getWeekId().compareTo(tempweek)==0 && AGCpays.get(b).getBillYear().compareTo(tempyear)==0 && AGCpays.get(b).getRevisionNo().compareTo(temprevno)==1)
             AGCpays.remove(a);
             if(b<AGCpays.size())
             if(AGCpays.get(b).getWeekId().compareTo(tempweek)==0 && AGCpays.get(b).getBillYear().compareTo(tempyear)==0 && AGCpays.get(b).getRevisionNo().compareTo(temprevno)==-1)
             AGCpays.remove(b);
             }
             }
            
             for(int a=0; a<DSMrecs.size()-1; a++) {
             tempweek = DSMrecs.get(a).getWeekId(); tempyear = DSMrecs.get(a).getBillYear(); temprevno = DSMrecs.get(a).getRevisionNo();
             for(int b=1; b<DSMrecs.size(); b++) {
             if(b<DSMrecs.size())
             if(DSMrecs.get(b).getWeekId().compareTo(tempweek)==0 && DSMrecs.get(b).getBillYear().compareTo(tempyear)==0 && DSMrecs.get(b).getRevisionNo().compareTo(temprevno)==1)
             DSMrecs.remove(a);
             if(b<DSMrecs.size())
             if(DSMrecs.get(b).getWeekId().compareTo(tempweek)==0 && DSMrecs.get(b).getBillYear().compareTo(tempyear)==0 && DSMrecs.get(b).getRevisionNo().compareTo(temprevno)==-1)
             DSMrecs.remove(b);
             }
             }
             for(int a=0; a<RRASrecs.size()-1; a++) {
             tempweek = RRASrecs.get(a).getWeekId(); tempyear = RRASrecs.get(a).getBillYear(); temprevno = RRASrecs.get(a).getRevisionNo();
             for(int b=1; b<RRASrecs.size(); b++) {
             if(b<RRASrecs.size())
             if(RRASrecs.get(b).getWeekId().compareTo(tempweek)==0 && RRASrecs.get(b).getBillYear().compareTo(tempyear)==0 && RRASrecs.get(b).getRevisionNo().compareTo(temprevno)==1)
             RRASrecs.remove(a);
             if(b<RRASrecs.size())
             if(RRASrecs.get(b).getWeekId().compareTo(tempweek)==0 && RRASrecs.get(b).getBillYear().compareTo(tempyear)==0 && RRASrecs.get(b).getRevisionNo().compareTo(temprevno)==-1)
             RRASrecs.remove(b);
             }
             }
             for(int a=0; a<AGCrecs.size()-1; a++) {
             tempweek = AGCrecs.get(a).getWeekId(); tempyear = AGCrecs.get(a).getBillYear(); temprevno = AGCrecs.get(a).getRevisionNo();
             for(int b=1; b<AGCrecs.size(); b++) {
             if(b<AGCrecs.size())
             if(AGCrecs.get(b).getWeekId().compareTo(tempweek)==0 && AGCrecs.get(b).getBillYear().compareTo(tempyear)==0 && AGCrecs.get(b).getRevisionNo().compareTo(temprevno)==1)
             AGCrecs.remove(a);
             if(b<AGCrecs.size())
             if(AGCrecs.get(b).getWeekId().compareTo(tempweek)==0 && AGCrecs.get(b).getBillYear().compareTo(tempyear)==0 && AGCrecs.get(b).getRevisionNo().compareTo(temprevno)==-1)
             AGCrecs.remove(b);
             }
             }*/
            for (BillPayableCorp o1 : DSMpays) {
                allpayable.add(o1);
            }
            for (BillPayableCorp o2 : RRASpays) {
                allpayable.add(o2);
            }
            for (BillPayableCorp o3 : AGCpays) {
                allpayable.add(o3);
            }
            for (BillPayableCorp o4 : SRASpays) {
                allpayable.add(o4);
            }
            for (BillPayableCorp o5 : TRASpays) {
                allpayable.add(o5);
            }

            for (BillReceiveCorp o4 : DSMrecs) {
                allreceive.add(o4);
            }
            for (BillReceiveCorp o5 : RRASrecs) {
                allreceive.add(o5);
            }
            for (BillReceiveCorp o6 : AGCrecs) {
                allreceive.add(o6);
            }
            for (BillReceiveCorp o6 : SRASrecs) {
                allreceive.add(o6);
            }
            for (BillReceiveCorp o6 : TRASrecs) {
                allreceive.add(o6);
            }

            int size = Math.abs(week_id2 - week_id1) + 1;
            System.out.println("Size is " + size);
            BigDecimal[] A = new BigDecimal[size];
            BigDecimal[] B = new BigDecimal[size];
            BigDecimal[] D = new BigDecimal[size];
            BigDecimal[] E = new BigDecimal[size];
            BigDecimal[] I = new BigDecimal[size];
            BigDecimal[] J = new BigDecimal[size];
            BigDecimal[] L = new BigDecimal[size];
            BigDecimal[] M = new BigDecimal[size];
            BigDecimal[] P = new BigDecimal[size];
            BigDecimal[] Q = new BigDecimal[size];
            BigDecimal[] S = new BigDecimal[size];
            BigDecimal[] T = new BigDecimal[size];
            BigDecimal[] AS = new BigDecimal[size];
            BigDecimal[] BS= new BigDecimal[size];
            BigDecimal[] DS = new BigDecimal[size];
            BigDecimal[] ES = new BigDecimal[size];
            BigDecimal[] AT = new BigDecimal[size];
            BigDecimal[] BT = new BigDecimal[size];
            BigDecimal[] DT = new BigDecimal[size];
            BigDecimal[] ET = new BigDecimal[size];
            Arrays.fill(A, BigDecimal.ZERO);
            Arrays.fill(B, BigDecimal.ZERO);
            Arrays.fill(D, BigDecimal.ZERO);
            Arrays.fill(E, BigDecimal.ZERO);
            Arrays.fill(I, BigDecimal.ZERO);
            Arrays.fill(J, BigDecimal.ZERO);
            Arrays.fill(L, BigDecimal.ZERO);
            Arrays.fill(M, BigDecimal.ZERO);
            Arrays.fill(P, BigDecimal.ZERO);
            Arrays.fill(Q, BigDecimal.ZERO);
            Arrays.fill(S, BigDecimal.ZERO);
            Arrays.fill(T, BigDecimal.ZERO);
            Arrays.fill(AS, BigDecimal.ZERO);
            Arrays.fill(BS, BigDecimal.ZERO);
            Arrays.fill(DS, BigDecimal.ZERO);
            Arrays.fill(ES, BigDecimal.ZERO);
            Arrays.fill(AT, BigDecimal.ZERO);
            Arrays.fill(BT, BigDecimal.ZERO);
            Arrays.fill(DT, BigDecimal.ZERO);
            Arrays.fill(ET, BigDecimal.ZERO);

            BigDecimal week = new BigDecimal(week_id1);

            for (int i = 0; i < size; i++) {
                for (BillPayableCorp obj : allpayable) {
                    if (obj.getWeekId().compareTo(week) == 0 && !obj.getBillStatus().equalsIgnoreCase("REFUND")) {

                        if (obj.getBillType().equalsIgnoreCase("DSM")) {
                            if (obj.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                A[i] = A[i].add(obj.getTotalnet());
                            } else {
                                A[i] = A[i].add(obj.getRevisedpaybale());
                            }

                            B[i] = B[i].add(obj.getPaidAmount());
                        }
                        if (obj.getBillType().equalsIgnoreCase("SRAS")) {
                            if (obj.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                AS[i] = AS[i].add(obj.getTotalnet());
                            } else {
                                AS[i] = AS[i].add(obj.getRevisedpaybale());
                            }

                            BS[i] = BS[i].add(obj.getPaidAmount());
                        }
                        if (obj.getBillType().equalsIgnoreCase("TRASM")  || obj.getBillType().equalsIgnoreCase("TRASS") || obj.getBillType().equalsIgnoreCase("TRASE")) {
                            if (obj.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                AT[i] = AT[i].add(obj.getTotalnet());
                            } else {
                                AT[i] = AT[i].add(obj.getRevisedpaybale());
                            }

                            BT[i] = BT[i].add(obj.getPaidAmount());
                        }

                        if (obj.getBillType().equalsIgnoreCase("RRAS")) {
                            if (obj.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                L[i] = L[i].add(obj.getTotalnet());
                            } else {
                                L[i] = L[i].add(obj.getRevisedpaybale());
                            }

                            M[i] = M[i].add(obj.getPaidAmount());
                        }

                        if (obj.getBillType().equalsIgnoreCase("AGC")) {
                            if (obj.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                S[i] = S[i].add(obj.getTotalnet());
                            } else {
                                S[i] = S[i].add(obj.getRevisedpaybale());
                            }

                            T[i] = T[i].add(obj.getPaidAmount());
                        }
                        
                    } else if (obj.getWeekId().compareTo(week) == 0 && obj.getBillStatus().equalsIgnoreCase("REFUND")) {

                        if (obj.getBillType().equalsIgnoreCase("DSM")) {
                            D[i] = D[i].add(obj.getRevisedrefund());
                            E[i] = E[i].add(obj.getAdjustmentAmount());
                        }
                        if (obj.getBillType().equalsIgnoreCase("SRAS")) {
                            DS[i] = DS[i].add(obj.getRevisedrefund());
                            ES[i] = ES[i].add(obj.getAdjustmentAmount());
                        }
                        if (obj.getBillType().equalsIgnoreCase("TRASM")  || obj.getBillType().equalsIgnoreCase("TRASS") || obj.getBillType().equalsIgnoreCase("TRASE")) {
                            DT[i] = DT[i].add(obj.getRevisedrefund());
                            ET[i] = ET[i].add(obj.getAdjustmentAmount());
                        }

                        if (obj.getBillType().equalsIgnoreCase("RRAS")) {
                            I[i] = I[i].add(obj.getRevisedrefund());
                            J[i] = J[i].add(obj.getAdjustmentAmount());
                        }

                        if (obj.getBillType().equalsIgnoreCase("AGC")) {
                            P[i] = P[i].add(obj.getRevisedrefund());
                            Q[i] = Q[i].add(obj.getAdjustmentAmount());
                        }
                    }
                }

                for (BillReceiveCorp obj1 : allreceive) {
                    if (obj1.getWeekId().compareTo(week) == 0 && !obj1.getDisburseStatus().equalsIgnoreCase("REFUND")) {

                        if (obj1.getBillType().equalsIgnoreCase("DSM")) {
                            if (obj1.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                D[i] = D[i].add(obj1.getToalnet());
                            } else {
                                D[i] = D[i].add(obj1.getRevisedpaybale());
                            }

                            E[i] = E[i].add(obj1.getDisburseAmount());
                        }
                        if (obj1.getBillType().equalsIgnoreCase("SRAS")) {
                            if (obj1.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                DS[i] = DS[i].add(obj1.getToalnet());
                            } else {
                                DS[i] = DS[i].add(obj1.getRevisedpaybale());
                            }

                            ES[i] = ES[i].add(obj1.getDisburseAmount());
                        }
                        if (obj1.getBillType().equalsIgnoreCase("TRASM")  || obj1.getBillType().equalsIgnoreCase("TRASS") || obj1.getBillType().equalsIgnoreCase("TRASE")) {
                            if (obj1.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                DT[i] = DT[i].add(obj1.getToalnet());
                            } else {
                                DT[i] = DT[i].add(obj1.getRevisedpaybale());
                            }

                            ET[i] = ET[i].add(obj1.getDisburseAmount());
                        }

                        if (obj1.getBillType().equalsIgnoreCase("RRAS")) {
                            if (obj1.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                I[i] = I[i].add(obj1.getToalnet());
                            } else {
                                I[i] = I[i].add(obj1.getRevisedpaybale());
                            }

                            J[i] = J[i].add(obj1.getDisburseAmount());
                        }

                        if (obj1.getBillType().equalsIgnoreCase("AGC")) {
                            if (obj1.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                P[i] = P[i].add(obj1.getToalnet());
                            } else {
                                P[i] = P[i].add(obj1.getRevisedpaybale());
                            }

                            Q[i] = Q[i].add(obj1.getDisburseAmount());
                        }
                    } else if (obj1.getWeekId().compareTo(week) == 0 && obj1.getDisburseStatus().equalsIgnoreCase("REFUND")) {

                        if (obj1.getBillType().equalsIgnoreCase("DSM")) {
                            A[i] = A[i].add(obj1.getRevisedrefund());
                            B[i] = B[i].add(obj1.getAdjustmentAmount());
                        }
                        if (obj1.getBillType().equalsIgnoreCase("SRAS")) {
                            AS[i] = AS[i].add(obj1.getRevisedrefund());
                            BS[i] = BS[i].add(obj1.getAdjustmentAmount());
                        }
                        if (obj1.getBillType().equalsIgnoreCase("TRASM")  || obj1.getBillType().equalsIgnoreCase("TRASS") || obj1.getBillType().equalsIgnoreCase("TRASE")) {
                            AT[i] = AT[i].add(obj1.getRevisedrefund());
                            BT[i] = BT[i].add(obj1.getAdjustmentAmount());
                        }

                        if (obj1.getBillType().equalsIgnoreCase("RRAS")) {
                            L[i] = L[i].add(obj1.getRevisedrefund());
                            M[i] = M[i].add(obj1.getAdjustmentAmount());
                        }

                        if (obj1.getBillType().equalsIgnoreCase("AGC")) {
                            S[i] = S[i].add(obj1.getRevisedrefund());
                            T[i] = T[i].add(obj1.getAdjustmentAmount());
                        }
                    }
                }

                week = week.add(BigDecimal.ONE);
            }

            for (int a = 0; a < A.length; a++) {
                A[a] = A[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                B[a] = B[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                D[a] = D[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                E[a] = E[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                I[a] = I[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                J[a] = J[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                L[a] = L[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                M[a] = M[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                P[a] = P[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                Q[a] = Q[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                S[a] = S[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                T[a] = T[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                AS[a] = AS[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                BS[a] = BS[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                DS[a] = DS[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                ES[a] = ES[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                AT[a] = AT[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                BT[a] = BT[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                DT[a] = DT[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                ET[a] = ET[a].divide(new BigDecimal(10000000)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }

            List weeklist = new ArrayList();
            for (int z = week_id1; z <= week_id2; z++) {
                weeklist.add(z);
            }

            mv1.addObject("A", A);
            mv1.addObject("B", B);
            mv1.addObject("D", D);
            mv1.addObject("E", E);
            mv1.addObject("I", I);
            mv1.addObject("J", J);
            mv1.addObject("L", L);
            mv1.addObject("M", M);
            mv1.addObject("P", P);
            mv1.addObject("Q", Q);
            mv1.addObject("S", S);
            mv1.addObject("T", T);
            mv1.addObject("AS", AS);
            mv1.addObject("BS", BS);
            mv1.addObject("DS", DS);
            mv1.addObject("ES", ES);
            mv1.addObject("AT", AT);
            mv1.addObject("BT", BT);
            mv1.addObject("DT", DT);
            mv1.addObject("ET", ET);
            mv1.addObject("startweek", week_id1);
            mv1.addObject("endweek", week_id2);
            mv1.addObject("weeklist", weeklist);
            mv1.addObject("year", yeari);
            return mv1;
        }

        mv.addObject("title", "2.1 Old Format NLDC Report");
        return mv;
    }

    public ModelAndView fullDSMbills(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("NewReport/getOldNLDC");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String dbType = request.getParameter("dbType");

        String type = "DSM";

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
                cl.setTime(date2);
                week_id2 = cl.get(Calendar.WEEK_OF_YEAR);
            }
            if (dbType.equals("WEEKLY")) {
                String week = request.getParameter("week");
                String year = request.getParameter("year");
                yeari = Integer.parseInt(year);
                week_id1 = Integer.parseInt(week);
                week_id2 = week_id1;
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("starts from weekid " + week_id1 + " to " + week_id2);

            if (type.equals("DSM")) {
                ModelAndView model = new ModelAndView("NewReport/viewfullDSMbills");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinal(week_id1, week_id2, yeari);
                List<BillPayableEntityDsm> queryList = rpt.getentityfinal(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));

//                BigDecimal tempweek = null;
//                BigDecimal tempyear = null;
//                BigDecimal temprevno = null;
//                for(int a=0; a<queryList.size()-1; a++) {
//                    tempweek = queryList.get(a).getWeekId(); tempyear = queryList.get(a).getBillYear(); temprevno = queryList.get(a).getRevisionNo();
//                    for(int b=1; b<queryList.size(); b++) {
//                        if(b<queryList.size())
//                        if(queryList.get(b).getWeekId().compareTo(tempweek)==0 && queryList.get(b).getBillYear().compareTo(tempyear)==0 && queryList.get(b).getRevisionNo().compareTo(temprevno)==1)
//                            queryList.remove(a);
//                        if(b<queryList.size())
//                        if(queryList.get(b).getWeekId().compareTo(tempweek)==0 && queryList.get(b).getBillYear().compareTo(tempyear)==0 && queryList.get(b).getRevisionNo().compareTo(temprevno)==-1)
//                            queryList.remove(b);
//                    }
//                }
                model.addObject("queryList", queryList);
                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                return model;
            }
        }

        mv.addObject("title", "4 Full DSM Bills NLDC");
        return mv;
    }

    //================================================================================
    public ModelAndView getDueDatesummary(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String getInterest = request.getParameter("getInterest");

        if (getInterest != null) {

            ModelAndView mv = new ModelAndView("Report/viewDueDatesummary");

            String startdate = request.getParameter("date1");
            String enddate = request.getParameter("date2");
            String billType = request.getParameter("btype");

            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];

            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            System.out.println("Date1 & Date2 is : " + date1 + " " + date2);

            NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
            NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();

            List<BillPayableCorp> paybill = bpdao.getBetweenDueDates(date1, date2, billType);
            List<BillReceiveCorp> payref = brdao.getRefundBetweenDueDates(date1, date2, billType);

            List<BillReceiveCorp> recbill = brdao.getBetweenDueDates(date1, date2, billType);
            List<BillPayableCorp> recref = bpdao.getRefundBetweenDueDates(date1, date2, billType);

            List<Date> datelist = new ArrayList();

            System.out.println("paybill " + paybill.size());
            System.out.println("recbill " + recbill.size());
            System.out.println("payref " + payref.size());
            System.out.println("recref " + recref.size());

            if (paybill != null) {
                for (BillPayableCorp o : paybill) {
                    if (!datelist.contains(o.getBillDueDate())) {
                        datelist.add(o.getBillDueDate());
                    }
                }
            }

            if (payref != null) {
                for (BillReceiveCorp o : payref) {
                    if (!datelist.contains(o.getBillDueDate())) {
                        datelist.add(o.getBillDueDate());
                    }
                }
            }

            if (recbill != null) {
                for (BillReceiveCorp o : recbill) {
                    if (!datelist.contains(o.getBillDueDate())) {
                        datelist.add(o.getBillDueDate());
                    }
                }
            }

            if (recref != null) {
                for (BillPayableCorp o : recref) {
                    if (!datelist.contains(o.getBillDueDate())) {
                        datelist.add(o.getBillDueDate());
                    }
                }
            }

            System.out.println("Size of datelist is : " + datelist.size());
            Collections.sort(datelist);

            BigDecimal[] totalpay = new BigDecimal[datelist.size()];
            BigDecimal[] totalrec = new BigDecimal[datelist.size()];
            BigDecimal totalcredit = BigDecimal.ZERO;
            BigDecimal totaldebit = BigDecimal.ZERO;
            Arrays.fill(totalpay, BigDecimal.ZERO);
            Arrays.fill(totalrec, BigDecimal.ZERO);

            for (int i = 0; i < datelist.size(); i++) {
                for (BillPayableCorp o : paybill) {
                    if (datelist.get(i).compareTo(o.getBillDueDate()) == 0 && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                        totalpay[i] = totalpay[i].add(o.getTotalnet());
                    }
                    if (datelist.get(i).compareTo(o.getBillDueDate()) == 0 && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1) {
                        totalpay[i] = totalpay[i].add(o.getRevisedpaybale());
                    }
                }

                for (BillReceiveCorp o : payref) {
                    if (datelist.get(i).compareTo(o.getBillDueDate()) == 0 && o.getDisburseStatus().equalsIgnoreCase("REFUND")) {
                        totalpay[i] = totalpay[i].add(o.getRevisedrefund());
                    }
                }

                for (BillReceiveCorp o : recbill) {
                    if (datelist.get(i).compareTo(o.getBillDueDate()) == 0 && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                        totalrec[i] = totalrec[i].add(o.getToalnet());
                    }
                    if (datelist.get(i).compareTo(o.getBillDueDate()) == 0 && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1) {
                        totalrec[i] = totalrec[i].add(o.getRevisedpaybale());
                    }
                }

                for (BillPayableCorp o : recref) {
                    if (datelist.get(i).compareTo(o.getBillDueDate()) == 0 && o.getBillStatus().equalsIgnoreCase("REFUND")) {
                        totalrec[i] = totalrec[i].add(o.getRevisedrefund());
                    }
                }
            }

            NewBankStatementDAO bkdao = new NewBankStatementDAO();

            List<BankStatement> crlist = bkdao.CRBankStmttlistbydates(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2));
            List<BankStatement> drlist = bkdao.DRBankStmttlistbydatesreconzero(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2));

            if (crlist != null) {
                for (BankStatement e : crlist) {
                    totalcredit = totalcredit.add(e.getPaidAmount());
                }
            }

            if (drlist != null) {
                for (BankStatement e : drlist) {
                    totaldebit = totaldebit.add(e.getPaidAmount());
                }
            }

            mv.addObject("duedates", datelist);
            mv.addObject("totalpays", totalpay);
            mv.addObject("totalrecs", totalrec);
            mv.addObject("totalcredit", totalcredit);
            mv.addObject("totaldebit", totaldebit);
            mv.addObject("date1", date1);
            mv.addObject("date2", date2);
            mv.addObject("billType", billType);
            return mv;
        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        mv1.addObject("flag", 1);
        return mv1;
    }

    public ModelAndView getCERCreport(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String getInterest = request.getParameter("getInterest");
        String btype = "DSM";

        if (getInterest != null) {

            ModelAndView mv = new ModelAndView("NewReport/CERCreport");

            String startdate = request.getParameter("date1");
            String enddate = request.getParameter("date2");

            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];

            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            System.out.println("Date1 & Date2 is : " + date1 + " " + date2);

            NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
            NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();
            MappingBillBankDAO mbbkdao = new MappingBillBankDAO();
            PaymentDisbursementDAO pddao = new PaymentDisbursementDAO();
            TempRefundBillCorpDAO trbdao = new TempRefundBillCorpDAO();

            List<BillPayableCorp> paybill = bpdao.getBetweenDueDates(date1, date2, btype);
            List<BillReceiveCorp> payref = brdao.getRefundBetweenDueDates(date1, date2, btype);

            List<BillReceiveCorp> recbill = brdao.getBetweenDueDates(date1, date2, btype);
            List<BillPayableCorp> recref = bpdao.getRefundBetweenDueDates(date1, date2, btype);

            List<MappingBillBank> mappings = mbbkdao.getMappingBillBankDetailsbydatestype(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2), btype);

            List<BankStatement> disburses = pddao.getbillDisbursementDetailsbyFromDateTodatetypebyBilltype(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2), btype);

            List<BankStatement> mispays = pddao.getMisDisbursesByDate(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2));

            List<TempRefundBillCorp> recsref = trbdao.getdisRefundBillPayCorpbydatestypebyrevINforBtype(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2), btype);
            List<TempRefundBillCorp> paysref = trbdao.getRefundBillCorpbyCorpforExportforBtype(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2), btype);

            List<String> corplist = new ArrayList();

            //System.out.println("paybill "+paybill.size());
            //System.out.println("recbill "+recbill.size());
            //System.out.println("payref "+payref.size());
            //System.out.println("recref "+recref.size());
            if (paybill != null) {
                for (BillPayableCorp o : paybill) {
                    if (!corplist.contains(o.getCorporates().getCorporateName())) {
                        corplist.add(o.getCorporates().getCorporateName());
                    }
                }
            }

            if (payref != null) {
                for (BillReceiveCorp o : payref) {
                    if (!corplist.contains(o.getCorporates().getCorporateName())) {
                        corplist.add(o.getCorporates().getCorporateName());
                    }
                }
            }

            if (recbill != null) {
                for (BillReceiveCorp o : recbill) {
                    if (!corplist.contains(o.getCorporates().getCorporateName())) {
                        corplist.add(o.getCorporates().getCorporateName());
                    }
                }
            }

            if (recref != null) {
                for (BillPayableCorp o : recref) {
                    if (!corplist.contains(o.getCorporates().getCorporateName())) {
                        corplist.add(o.getCorporates().getCorporateName());
                    }
                }
            }

            if (mappings != null) {
                for (MappingBillBank m : mappings) {
                    if (!corplist.contains(m.getCorporates().getCorporateName())) {
                        corplist.add(m.getCorporates().getCorporateName());
                    }
                }
            }

            if (disburses != null) {
                for (BankStatement d : disburses) {
                    if (!corplist.contains(d.getCorporates().getCorporateName())) {
                        corplist.add(d.getCorporates().getCorporateName());
                    }
                }
            }

            if (mispays != null) {
                for (BankStatement mp : mispays) {
                    if (!corplist.contains(mp.getCorporates().getCorporateName())) {
                        corplist.add(mp.getCorporates().getCorporateName());
                    }
                }
            }

            if (recsref != null) {
                for (TempRefundBillCorp rs : recsref) {
                    if (!corplist.contains(rs.getCorporates().getCorporateName())) {
                        corplist.add(rs.getCorporates().getCorporateName());
                    }
                }
            }

            if (paysref != null) {
                for (TempRefundBillCorp ps : paysref) {
                    if (!corplist.contains(ps.getCorporates().getCorporateName())) {
                        corplist.add(ps.getCorporates().getCorporateName());
                    }
                }
            }

            System.out.println("Size of corplist is : " + corplist.size());
            Collections.sort(corplist);

            BigDecimal[] totalpay = new BigDecimal[corplist.size()];
            BigDecimal[] totalrec = new BigDecimal[corplist.size()];
            BigDecimal[] crpayments = new BigDecimal[corplist.size()];
            BigDecimal[] drpayments = new BigDecimal[corplist.size()];
            Arrays.fill(totalpay, BigDecimal.ZERO);
            Arrays.fill(totalrec, BigDecimal.ZERO);
            Arrays.fill(crpayments, BigDecimal.ZERO);
            Arrays.fill(drpayments, BigDecimal.ZERO);

            for (int i = 0; i < corplist.size(); i++) {
                if (paybill != null) {
                    for (BillPayableCorp o : paybill) {
                        if (corplist.get(i).equalsIgnoreCase(o.getCorporates().getCorporateName()) && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                            totalpay[i] = totalpay[i].add(o.getTotalnet());
                        }

                        if (corplist.get(i).equalsIgnoreCase(o.getCorporates().getCorporateName()) && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1) {
                            totalpay[i] = totalpay[i].add(o.getRevisedpaybale());
                        }
                    }
                }

                if (payref != null) {
                    for (BillReceiveCorp o : payref) {
                        if (corplist.get(i).equalsIgnoreCase(o.getCorporates().getCorporateName()) && o.getDisburseStatus().equalsIgnoreCase("REFUND")) {
                            totalpay[i] = totalpay[i].add(o.getRevisedrefund());
                        }
                    }
                }

                if (mappings != null) {
                    for (MappingBillBank b : mappings) {
                        if (corplist.get(i).equalsIgnoreCase(b.getCorporates().getCorporateName())) {
                            crpayments[i] = crpayments[i].add(b.getMappedAmount());
                        }
                    }
                }

                if (paysref != null) {
                    for (TempRefundBillCorp t1 : paysref) {
                        if (corplist.get(i).equalsIgnoreCase(t1.getCorporates().getCorporateName())) {
                            crpayments[i] = crpayments[i].add(t1.getPaidAmount());
                        }
                    }
                }

                if (recbill != null) {
                    for (BillReceiveCorp o : recbill) {
                        if (corplist.get(i).equalsIgnoreCase(o.getCorporates().getCorporateName()) && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                            totalrec[i] = totalrec[i].add(o.getToalnet());
                        }

                        if (corplist.get(i).equalsIgnoreCase(o.getCorporates().getCorporateName()) && o.getRevisionNo().compareTo(BigDecimal.ZERO) == 1) {
                            totalrec[i] = totalrec[i].add(o.getRevisedpaybale());
                        }
                    }
                }

                if (recref != null) {
                    for (BillPayableCorp o : recref) {
                        if (corplist.get(i).equalsIgnoreCase(o.getCorporates().getCorporateName()) && o.getBillStatus().equalsIgnoreCase("REFUND")) {
                            totalrec[i] = totalrec[i].add(o.getRevisedrefund());
                        }
                    }
                }

                if (disburses != null) {
                    for (BankStatement p : disburses) {
                        if (corplist.get(i).equalsIgnoreCase(p.getCorporates().getCorporateName())) {
                            drpayments[i] = drpayments[i].add(p.getPaidAmount());
                        }
                    }
                }

                if (mispays != null) {
                    for (BankStatement m : mispays) {
                        if (corplist.get(i).equalsIgnoreCase(m.getCorporates().getCorporateName())) {
                            drpayments[i] = drpayments[i].add(m.getPaidAmount());
                        }
                    }
                }

                if (recsref != null) {
                    for (TempRefundBillCorp t2 : recsref) {
                        if (corplist.get(i).equalsIgnoreCase(t2.getCorporates().getCorporateName())) {
                            drpayments[i] = drpayments[i].add(t2.getPaidAmount());
                        }
                    }
                }
            }

            mv.addObject("corplist", corplist);
            mv.addObject("totalpays", totalpay);
            mv.addObject("totalrecs", totalrec);
            mv.addObject("crpayments", crpayments);
            mv.addObject("drpayments", drpayments);
            mv.addObject("date1", date1);
            mv.addObject("date2", date2);
            mv.addObject("btype", btype);
            return mv;
        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        return mv1;
    }

    public ModelAndView getBankPayVoucher(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

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

            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];

            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            System.out.println("Date1 & Date2 is : " + date1 + " " + date2);

            PaymentDisbursementDAO pddao = new PaymentDisbursementDAO();
            TempRefundBillCorpDAO trdao = new TempRefundBillCorpDAO();
            PaymentInterestDisbursementDAO piddao = new PaymentInterestDisbursementDAO();
            miscDisbursementDAO miscdao = new miscDisbursementDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            CorporatesDAO corpdao = new CorporatesDAO();

            Date d1 = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
            Date d2 = new SimpleDateFormat("dd-MM-yyyy").parse(date2);

            List<PaymentDisbursement> paybill = pddao.getDisbursementDetailsbyFromDateTodate2(d1, d2);
            List<TempRefundBillCorp> recref = trdao.getRefundBillPayCorporExport2(d1, d2);
            List<PaymentInterestDisbursement> idis = piddao.getPaymentInterestDisbursed(d1, d2);
            List<MiscDisbursement> misclist = miscdao.getmiscDetailsBetweenDates(d1, d2);
            List<CsdfDetails> csdflist = csdfdao.getPSDFdetailsbyfromandtodates(d1, d2);

            List<String[]> master = new ArrayList();

            if (paybill != null && paybill.size() > 0) {
                for (PaymentDisbursement b : paybill) {
                    if (b.getDisburseAmount().compareTo(BigDecimal.ZERO) == 1) {
                        String[] tempstr = new String[6];
                        tempstr[0] = b.getCorporates().getPartyCode();
                        tempstr[1] = b.getCorporates().getCorporateName();
                        tempstr[2] = b.getRemarks();
                        tempstr[3] = b.getBillType() + "/WEEK" + b.getWeekId() + "/R" + b.getBillReceiveCorp().getRevisionNo();

                        Integer day = b.getBillingDate().getDate();
                        String day1 = day.toString();
                        Integer month = b.getBillingDate().getMonth() + 1;
                        String month1 = month.toString();
                        int year = b.getBillingDate().getYear() + 1900;

                        if (day < 10) {
                            day1 = "0" + day;
                        }
                        if (month < 10) {
                            month1 = "0" + month;
                        }

                        tempstr[4] = day1 + "-" + month1 + "-" + year;
                        tempstr[5] = b.getDisburseAmount().toPlainString();
                        master.add(tempstr);
                    }
                }
            }

            if (recref != null && recref.size() > 0) {
                for (TempRefundBillCorp b : recref) {
                    if (b.getPaidAmount().compareTo(BigDecimal.ZERO) == 1) {
                        String[] tempstr = new String[6];
                        tempstr[0] = b.getCorporates().getPartyCode();
                        tempstr[1] = b.getCorporates().getCorporateName();
                        tempstr[2] = b.getRemarks();
                        tempstr[3] = b.getBillPayableCorp().getBillType() + "/WEEK" + b.getWeekid() + "/R" + b.getBillPayableCorp().getRevisionNo();

                        Integer day = b.getBillPayableCorp().getBillingDate().getDate();
                        String day1 = day.toString();
                        Integer month = b.getBillPayableCorp().getBillingDate().getMonth() + 1;
                        String month1 = month.toString();
                        int year = b.getBillPayableCorp().getBillingDate().getYear() + 1900;

                        if (day < 10) {
                            day1 = "0" + day;
                        }
                        if (month < 10) {
                            month1 = "0" + month;
                        }

                        tempstr[4] = day1 + "-" + month1 + "-" + year;
                        tempstr[5] = b.getPaidAmount().toPlainString();
                        master.add(tempstr);
                    }
                }
            }

            if (idis != null && idis.size() > 0) {
                for (PaymentInterestDisbursement pi : idis) {
                    if (pi.getInterestPaidamount().compareTo(BigDecimal.ZERO) == 1) {
                        String[] tempstr = new String[6];
                        tempstr[0] = pi.getDisbursedInterestDetails().getCorporates().getPartyCode();
                        tempstr[1] = pi.getDisbursedInterestDetails().getCorporates().getCorporateName();
                        tempstr[2] = pi.getRemarks();
                        tempstr[3] = "";
                        tempstr[4] = "";
                        tempstr[5] = pi.getInterestPaidamount().toPlainString();
                        master.add(tempstr);
                    }
                }
            }

            if (misclist != null && misclist.size() > 0) {
                for (MiscDisbursement md : misclist) {
                    Corporates c = corpdao.getCorporatesbyCorporateId(md.getCorpId().intValue()).get(0);

                    String[] tempstr = new String[6];
                    tempstr[0] = c.getPartyCode();
                    tempstr[1] = c.getCorporateName();
                    tempstr[2] = md.getRemarks();
                    tempstr[3] = "";
                    tempstr[4] = "";
                    tempstr[5] = md.getRefundAmt().toPlainString();
                    master.add(tempstr);
                }
            }

            if (csdflist != null && csdflist.size() > 0) {
                for (CsdfDetails cd : csdflist) {
                    String[] tempstr = new String[6];
                    tempstr[0] = "N0220";
                    tempstr[1] = "PSDF";
                    tempstr[2] = cd.getRemarks();
                    tempstr[3] = "";
                    tempstr[4] = "";
                    tempstr[5] = cd.getCsdfAmount().toPlainString();
                    master.add(tempstr);
                }
            }

            ModelAndView mv2 = new ModelAndView("DSMBPV");
            mv2.addObject("master", master);
            return mv2;
        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        mv1.addObject("note", "Pl. select software disburse Date.");
        return mv1;
    }

    public ModelAndView getBRV(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

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

            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];

            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            System.out.println("Date1 & Date2 is : " + date1 + " " + date2);

            MappingBillBankDAO mbbkdao = new MappingBillBankDAO();
            MappingRefundBankDAO mrfbkdao = new MappingRefundBankDAO();
            NewBankStatementDAO bkdao = new NewBankStatementDAO();
            MappingInterestBankDAO mibkdao = new MappingInterestBankDAO();

            Date d1 = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
            Date d2 = new SimpleDateFormat("dd-MM-yyyy").parse(date2);

            List<MappingBillBank> recbills = mbbkdao.getMappingBillBankDetailsbydates(d1, d2);
            List<MappingRefundBank> recrefs = mrfbkdao.getMappingRefundBankbydates(d1, d2);
            List<BankStatement> new_bkstmts = bkdao.NewUnmappedBankStmtbydates(d1, d2);
            List<MappingInterestBank> mibk = mibkdao.getMappingInterestBankbydates(d1, d2);

            List<String[]> master = new ArrayList();
            List<BankStatement> unmapped_bklist = new ArrayList();
            List<BigDecimal> ids = new ArrayList();

            if (recbills != null && recbills.size() > 0) {
                for (MappingBillBank mbb : recbills) {
                    String[] tempstr = new String[7];

                    tempstr[0] = mbb.getCorporates().getPartyCode();
                    tempstr[1] = mbb.getCorporates().getCorporateName();

                    if (mbb.getBankStatement().getMappedBalance().compareTo(BigDecimal.ZERO) == 1) {
                        if (!ids.contains(mbb.getBankStatement().getStmtId())) {
                            unmapped_bklist.add(mbb.getBankStatement());
                            ids.add(mbb.getBankStatement().getStmtId());
                        }
                    }

                    Integer dayz = mbb.getBankStatement().getAmountDate().getDate();
                    String day1z = dayz.toString();
                    Integer monthz = mbb.getBankStatement().getAmountDate().getMonth() + 1;
                    String month1z = monthz.toString();
                    int yearz = mbb.getBankStatement().getAmountDate().getYear() + 1900;

                    if (dayz < 10) {
                        day1z = "0" + dayz;
                    }
                    if (monthz < 10) {
                        month1z = "0" + monthz;
                    }

                    tempstr[2] = "Payment received on " + day1z + "-" + month1z + "-" + yearz;
                    tempstr[3] = mbb.getBillType() + "/WEEK" + mbb.getWeekId() + "/R" + mbb.getBillPayableCorp().getRevisionNo();

                    Integer day = mbb.getBillPayableCorp().getBillingDate().getDate();
                    String day1 = day.toString();
                    Integer month = mbb.getBillPayableCorp().getBillingDate().getMonth() + 1;
                    String month1 = month.toString();
                    int year = mbb.getBillPayableCorp().getBillingDate().getYear() + 1900;

                    if (day < 10) {
                        day1 = "0" + day;
                    }
                    if (month < 10) {
                        month1 = "0" + month;
                    }

                    tempstr[4] = day1 + "-" + month1 + "-" + year;
                    tempstr[5] = "";
                    tempstr[6] = mbb.getMappedAmount().toPlainString();

                    master.add(tempstr);
                }
            }

            if (recrefs != null && recrefs.size() > 0) {
                for (MappingRefundBank mrb : recrefs) {
                    String[] tempstr1 = new String[7];

                    tempstr1[0] = mrb.getTempRefundBillCorp().getCorporates().getPartyCode();
                    tempstr1[1] = mrb.getTempRefundBillCorp().getCorporates().getCorporateName();

                    if (mrb.getBankStatement().getMappedBalance().compareTo(BigDecimal.ZERO) == 1) {
                        if (!ids.contains(mrb.getBankStatement().getStmtId())) {
                            unmapped_bklist.add(mrb.getBankStatement());
                            ids.add(mrb.getBankStatement().getStmtId());
                        }
                    }

                    Integer dayz = mrb.getBankStatement().getAmountDate().getDate();
                    String day1z = dayz.toString();
                    Integer monthz = mrb.getBankStatement().getAmountDate().getMonth() + 1;
                    String month1z = monthz.toString();
                    int yearz = mrb.getBankStatement().getAmountDate().getYear() + 1900;

                    if (dayz < 10) {
                        day1z = "0" + dayz;
                    }
                    if (monthz < 10) {
                        month1z = "0" + monthz;
                    }

                    tempstr1[2] = "Payment received on " + day1z + "-" + month1z + "-" + yearz;
                    tempstr1[3] = mrb.getTempRefundBillCorp().getBillReceiveCorp().getBillType() + "/WEEK" + mrb.getTempRefundBillCorp().getWeekid() + "/R" + mrb.getTempRefundBillCorp().getBillReceiveCorp().getRevisionNo();

                    Integer day = mrb.getTempRefundBillCorp().getBillReceiveCorp().getBillingDate().getDate();
                    String day1 = day.toString();
                    Integer month = mrb.getTempRefundBillCorp().getBillReceiveCorp().getBillingDate().getMonth() + 1;
                    String month1 = month.toString();
                    int year = mrb.getTempRefundBillCorp().getBillReceiveCorp().getBillingDate().getYear() + 1900;

                    if (day < 10) {
                        day1 = "0" + day;
                    }
                    if (month < 10) {
                        month1 = "0" + month;
                    }

                    tempstr1[4] = day1 + "-" + month1 + "-" + year;
                    tempstr1[5] = "";
                    tempstr1[6] = mrb.getMappedAmount().toPlainString();
                    master.add(tempstr1);
                }
            }

            if (mibk != null && mibk.size() > 0) {
                for (MappingInterestBank mib : mibk) {
                    String[] tempstr = new String[7];

                    tempstr[0] = mib.getInterestDetails().getCorporates().getPartyCode();
                    tempstr[1] = mib.getInterestDetails().getCorporates().getCorporateName();

                    if (mib.getBankStatement().getMappedBalance().compareTo(BigDecimal.ZERO) == 1) {
                        if (!ids.contains(mib.getBankStatement().getStmtId())) {
                            unmapped_bklist.add(mib.getBankStatement());
                            ids.add(mib.getBankStatement().getStmtId());
                        }
                    }

                    Integer dayz = mib.getBankStatement().getAmountDate().getDate();
                    String day1z = dayz.toString();
                    Integer monthz = mib.getBankStatement().getAmountDate().getMonth() + 1;
                    String month1z = monthz.toString();
                    int yearz = mib.getBankStatement().getAmountDate().getYear() + 1900;

                    if (dayz < 10) {
                        day1z = "0" + dayz;
                    }
                    if (monthz < 10) {
                        month1z = "0" + monthz;
                    }

                    tempstr[2] = "Interest Payment received on " + day1z + "-" + month1z + "-" + yearz;
                    tempstr[3] = "";
                    tempstr[4] = "";
                    tempstr[5] = "";
                    tempstr[6] = mib.getMappedAmount().toPlainString();

                    master.add(tempstr);
                }
            }

            if (unmapped_bklist != null && unmapped_bklist.size() > 0) {
                for (BankStatement b : unmapped_bklist) {
                    String[] tempstr = new String[7];

                    tempstr[0] = b.getCorporates().getPartyCode();
                    tempstr[1] = b.getCorporates().getCorporateName();

                    Integer dayz = b.getAmountDate().getDate();
                    String day1z = dayz.toString();
                    Integer monthz = b.getAmountDate().getMonth() + 1;
                    String month1z = monthz.toString();
                    int yearz = b.getAmountDate().getYear() + 1900;

                    if (dayz < 10) {
                        day1z = "0" + dayz;
                    }
                    if (monthz < 10) {
                        month1z = "0" + monthz;
                    }

                    tempstr[2] = "Payment received on " + day1z + "-" + month1z + "-" + yearz;
                    tempstr[3] = "";
                    tempstr[4] = "";
                    tempstr[5] = "";
                    tempstr[6] = b.getMappedBalance().toPlainString();

                    master.add(tempstr);
                }
            }

            if (new_bkstmts != null && new_bkstmts.size() > 0) {
                for (BankStatement b : new_bkstmts) {
                    String[] tempstr = new String[7];

                    tempstr[0] = b.getCorporates().getPartyCode();
                    tempstr[1] = b.getCorporates().getCorporateName();

                    Integer dayz = b.getAmountDate().getDate();
                    String day1z = dayz.toString();
                    Integer monthz = b.getAmountDate().getMonth() + 1;
                    String month1z = monthz.toString();
                    int yearz = b.getAmountDate().getYear() + 1900;

                    if (dayz < 10) {
                        day1z = "0" + dayz;
                    }
                    if (monthz < 10) {
                        month1z = "0" + monthz;
                    }

                    tempstr[2] = "Payment received on " + day1z + "-" + month1z + "-" + yearz;
                    tempstr[3] = "";
                    tempstr[4] = "";
                    tempstr[5] = "";
                    tempstr[6] = b.getPaidAmount().toPlainString();

                    master.add(tempstr);
                }
            }

            ModelAndView mv2 = new ModelAndView("BRV");
            mv2.addObject("master", master);
            return mv2;
        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        mv1.addObject("note", "Pl. select Amount paid date in Bank.");
        return mv1;
    }

    public ModelAndView getJV(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

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

            String[] temp = startdate.split("-");
            String date1 = temp[2] + "-" + temp[1] + "-" + temp[0];

            String[] temp2 = enddate.split("-");
            String date2 = temp2[2] + "-" + temp2[1] + "-" + temp2[0];

            System.out.println("Date1 & Date2 is : " + date1 + " " + date2);

            NewBillPayableCorpDAO bpdao = new NewBillPayableCorpDAO();
            NewBillReceiveCorpDAO brdao = new NewBillReceiveCorpDAO();
            InterestDetailsDAO iddao = new InterestDetailsDAO();
            DisbursedInterestDetailsDAO diddao = new DisbursedInterestDetailsDAO();
            miscDisbursementDAO miscdao = new miscDisbursementDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            CorporatesDAO corpdao = new CorporatesDAO();

            List<BillPayableCorp> paybill = bpdao.getBetweenDueDates2(date1, date2);
            List<BillReceiveCorp> payref = brdao.getRefundBetweenDueDates2(date1, date2);

            List<BillReceiveCorp> recbill = brdao.getBetweenDueDates2(date1, date2);
            List<BillPayableCorp> recref = bpdao.getRefundBetweenDueDates2(date1, date2);

            List<InterestDetails> idlist = iddao.getInterestdetailsByDate(date1, date2);
            List<DisbursedInterestDetails> didlist = diddao.getDisbursedInterestDetailsByPublishDates(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2));

            List<MiscDisbursement> misclist = miscdao.getmiscDetailsBetweenDates(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2));
            List<CsdfDetails> csdflist = csdfdao.getPSDFdetailsbyfromandtodates(new SimpleDateFormat("dd-MM-yyyy").parse(date1), new SimpleDateFormat("dd-MM-yyyy").parse(date2));

            List<String[]> master = new ArrayList();

            if (paybill != null && paybill.size() > 0) {
                for (BillPayableCorp b : paybill) {
                    String[] tempstr = new String[7];
                    tempstr[0] = b.getCorporates().getPartyCode();
                    tempstr[1] = b.getCorporates().getCorporateName();
                    tempstr[2] = "";
                    tempstr[3] = b.getBillType() + "/WEEK" + b.getWeekId() + "/R" + b.getRevisionNo();

                    Integer day = b.getBillingDate().getDate();
                    String day1 = day.toString();
                    Integer month = b.getBillingDate().getMonth() + 1;
                    String month1 = month.toString();
                    int year = b.getBillingDate().getYear() + 1900;

                    if (day < 10) {
                        day1 = "0" + day;
                    }
                    if (month < 10) {
                        month1 = "0" + month;
                    }

                    tempstr[4] = day1 + "-" + month1 + "-" + year;

                    if (b.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                        tempstr[5] = b.getTotalnet().toPlainString();
                    } else {
                        tempstr[5] = b.getRevisedpaybale().toPlainString();
                    }
                    tempstr[6] = "";

                    master.add(tempstr);
                }
            }

            if (payref != null && payref.size() > 0) {
                for (BillReceiveCorp b : payref) {
                    String[] tempstr = new String[7];
                    tempstr[0] = b.getCorporates().getPartyCode();
                    tempstr[1] = b.getCorporates().getCorporateName();
                    tempstr[2] = "";
                    tempstr[3] = b.getBillType() + "/WEEK" + b.getWeekId() + "/R" + b.getRevisionNo();

                    Integer day = b.getBillingDate().getDate();
                    String day1 = day.toString();
                    Integer month = b.getBillingDate().getMonth() + 1;
                    String month1 = month.toString();
                    int year = b.getBillingDate().getYear() + 1900;

                    if (day < 10) {
                        day1 = "0" + day;
                    }
                    if (month < 10) {
                        month1 = "0" + month;
                    }

                    tempstr[4] = day1 + "-" + month1 + "-" + year;

                    if (b.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                        tempstr[5] = b.getToalnet().toPlainString();
                    } else {
                        tempstr[5] = b.getRevisedrefund().toPlainString();
                    }
                    tempstr[6] = "";

                    master.add(tempstr);
                }
            }

            if (recbill != null && recbill.size() > 0) {
                for (BillReceiveCorp mbb : recbill) {
                    String[] tempstr = new String[7];
                    tempstr[0] = mbb.getCorporates().getPartyCode();
                    tempstr[1] = mbb.getCorporates().getCorporateName();
                    tempstr[2] = "";
                    tempstr[3] = mbb.getBillType() + "/WEEK" + mbb.getWeekId() + "/R" + mbb.getRevisionNo();

                    Integer day = mbb.getBillingDate().getDate();
                    String day1 = day.toString();
                    Integer month = mbb.getBillingDate().getMonth() + 1;
                    String month1 = month.toString();
                    int year = mbb.getBillingDate().getYear() + 1900;

                    if (day < 10) {
                        day1 = "0" + day;
                    }
                    if (month < 10) {
                        month1 = "0" + month;
                    }

                    tempstr[4] = day1 + "-" + month1 + "-" + year;
                    tempstr[5] = "";

                    if (mbb.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                        tempstr[6] = mbb.getToalnet().toPlainString();
                    } else {
                        tempstr[6] = mbb.getRevisedpaybale().toPlainString();
                    }

                    master.add(tempstr);
                }
            }

            if (recref != null && recref.size() > 0) {
                for (BillPayableCorp mrb : recref) {
                    String[] tempstr1 = new String[7];

                    tempstr1[0] = mrb.getCorporates().getPartyCode();
                    tempstr1[1] = mrb.getCorporates().getCorporateName();
                    tempstr1[2] = "";
                    tempstr1[3] = mrb.getBillType() + "/WEEK" + mrb.getWeekId() + "/R" + mrb.getRevisionNo();

                    Integer day = mrb.getBillingDate().getDate();
                    String day1 = day.toString();
                    Integer month = mrb.getBillingDate().getMonth() + 1;
                    String month1 = month.toString();
                    int year = mrb.getBillingDate().getYear() + 1900;

                    if (day < 10) {
                        day1 = "0" + day;
                    }
                    if (month < 10) {
                        month1 = "0" + month;
                    }

                    tempstr1[4] = day1 + "-" + month1 + "-" + year;
                    tempstr1[5] = "";

                    if (mrb.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                        tempstr1[6] = mrb.getTotalnet().toPlainString();
                    } else {
                        tempstr1[6] = mrb.getRevisedrefund().toPlainString();
                    }

                    master.add(tempstr1);
                }
            }

            if (idlist != null && idlist.size() > 0) {
                for (InterestDetails i : idlist) {
                    String[] tempstr1 = new String[7];

                    tempstr1[0] = i.getCorporates().getPartyCode();
                    tempstr1[1] = i.getCorporates().getCorporateName();
                    tempstr1[2] = "";
                    tempstr1[3] = "";
                    tempstr1[4] = "";
                    tempstr1[5] = i.getInterestAmount().toPlainString();
                    tempstr1[6] = "";

                    master.add(tempstr1);
                }
            }

            if (didlist != null && didlist.size() > 0) {
                for (DisbursedInterestDetails di : didlist) {
                    String[] tempstr1 = new String[7];

                    tempstr1[0] = di.getCorporates().getPartyCode();
                    tempstr1[1] = di.getCorporates().getCorporateName();
                    tempstr1[2] = "";
                    tempstr1[3] = "";
                    tempstr1[4] = "";
                    tempstr1[5] = "";
                    tempstr1[6] = di.getInterestAmount().toPlainString();

                    master.add(tempstr1);
                }
            }

            if (misclist != null && misclist.size() > 0) {
                for (MiscDisbursement m : misclist) {
                    Corporates c = corpdao.getCorporatesbyCorporateId(m.getCorpId().intValue()).get(0);

                    String[] tempstr = new String[7];
                    tempstr[0] = c.getPartyCode();
                    tempstr[1] = c.getCorporateName();
                    tempstr[2] = "";
                    tempstr[3] = "";
                    tempstr[4] = "";
                    tempstr[5] = "";
                    tempstr[6] = m.getRefundAmt().toPlainString();
                    master.add(tempstr);
                }
            }

            if (csdflist != null && csdflist.size() > 0) {
                for (CsdfDetails cd : csdflist) {
                    String[] tempstr = new String[7];
                    tempstr[0] = "N0220";
                    tempstr[1] = "PSDF";
                    tempstr[2] = "";
                    tempstr[3] = "";
                    tempstr[4] = "";
                    tempstr[5] = "";
                    tempstr[6] = cd.getCsdfAmount().toPlainString();
                    master.add(tempstr);
                }
            }

            ModelAndView mv2 = new ModelAndView("JV");
            mv2.addObject("master", master);
            return mv2;
        }

        ModelAndView mv1 = new ModelAndView("selectInterestWeeks");
        mv1.addObject("note", "Pl. select Bill account Issue Date.");
        return mv1;
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
