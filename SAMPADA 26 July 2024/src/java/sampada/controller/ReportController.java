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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.BankStatementDAO;
import sampada.DAO.BillInterestRateDAO;
import sampada.DAO.BillPayableCorpDAO;
import sampada.DAO.BillReceiveCorpDAO;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.CsdfDetailsDAO;
import sampada.DAO.DisbursedInterestDetailsDAO;
import sampada.DAO.DynReconciliationCropDAO;
import sampada.DAO.InterestDetailsDAO;
import sampada.DAO.InterestPoolAccountDetailsDAO;
import sampada.DAO.MappingBillBankDAO;
import sampada.DAO.MappingInterestBankDAO;
import sampada.DAO.MappingRefundBankDAO;
import sampada.DAO.NewBankStatementDAO;
import sampada.DAO.OutstandingYearCorpDAO;
import sampada.DAO.PaymentDisbursementDAO;
import sampada.DAO.PoolToIntDAO;
import sampada.DAO.PoolToPoolDAO;
import sampada.DAO.ReconciliationCropDAO;
import sampada.DAO.ReportDAO;
import sampada.DAO.TempBillAGCDetailsDAO;
import sampada.DAO.TempBillDSMDetailsDAO;
import sampada.DAO.TempBillFRASDetailsDAO;
import sampada.DAO.TempBillRRASDetailsDAO;
import sampada.DAO.TempDisbInterestDetailsDAO;
import sampada.DAO.TempInterestDetailsDAO;
import sampada.DAO.TempRefundBillCorpDAO;
import sampada.DAO.miscDisbursementDAO;
import sampada.pojo.BankStatement;
import sampada.pojo.BillEntityAgc;
import sampada.pojo.BillEntityFras;
import sampada.pojo.BillInterestRate;
import sampada.pojo.BillPayableCorp;
import sampada.pojo.BillPayableEntityDsm;
import sampada.pojo.BillPayableEntityRras;
import sampada.pojo.BillReceiveCorp;
import sampada.pojo.BillReceiveEntityDsm;
import sampada.pojo.BillReceiveEntityRras;
import sampada.pojo.Corporates;
import sampada.pojo.CsdfDetails;
import sampada.pojo.DisbursedInterestDetails;
import sampada.pojo.DynReconciliationCorp;
import sampada.pojo.InterestDetails;
import sampada.pojo.InterestPoolAccountDetails;
import sampada.pojo.MappingBillBank;
import sampada.pojo.MappingInterestBank;
import sampada.pojo.MappingRefundBank;
import sampada.pojo.MiscDisbursement;
import sampada.pojo.OutstandingYearCorp;
import sampada.pojo.PaymentDisbursement;
import sampada.pojo.PaymentInterestDisbursement;
import sampada.pojo.PoolToInt;
import sampada.pojo.PoolToPool;
import sampada.pojo.ReconciliationCorp;
import sampada.pojo.TempDisbInterestDetails;
import sampada.pojo.TempInterestDetails;
import sampada.pojo.TempMapBankStatement;
import sampada.pojo.TempRefundBillCorp;

/**
 *
 * @author JaganMohan
 */
public class ReportController extends MultiActionController {

    public ModelAndView reportsIndexRLDC(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("reportsIndexRLDC");
        return mv;

    }

    public ModelAndView reportsIndexMaker(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("reportsIndexMaker");
        return mv;

    }

    public ModelAndView reportsIndexFinance(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        ModelAndView mv = new ModelAndView("reportsIndexFinance");
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

    public ModelAndView finalreport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
            BillPayableCorpDAO billPayCorpDao = new BillPayableCorpDAO();

            List<BillPayableCorp> queryList1 = billPayCorpDao.BillPayableCorplistMonthly(new BigDecimal(week_id1), new BigDecimal(week_id2), type, new BigDecimal(yeari));
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

    public ModelAndView recfinalreport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
            BillReceiveCorpDAO billRecvCoprDao = new BillReceiveCorpDAO();

            List<BillReceiveCorp> queryList1 = billRecvCoprDao.BillReceiveCorplistMonthly(new BigDecimal(week_id1), new BigDecimal(week_id2), type, new BigDecimal(yeari));

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

    public static String getLastDay(String year, String month) {

        // get a calendar object
        GregorianCalendar calendar = new GregorianCalendar();

        // convert the year and month to integers
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);

        // adjust the month for a zero based index
        monthInt = monthInt - 1;

        // set the date of the calendar to the date provided
        calendar.set(yearInt, monthInt, 1);

        int dayInt = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return Integer.toString(dayInt);
    } // end getLastDay method

    public ModelAndView poolaccount(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("overallmapping");
        String bName = request.getParameter("bName");
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);

            ModelAndView model = new ModelAndView("Report/viewpool");

            BankStatementDAO bnkstmtdao = new BankStatementDAO();
            List<BankStatement> bnkstmt = null;
//            ReportDAO rpt = new ReportDAO();
//            List<Object[]> queryList1 = rpt.getpoolaccount(fdate, ldate, yeari);
//
//            model.addObject("queryList1", queryList1);
//
//            model.addObject("yeari", yeari);
//            model.addObject("fdate", fdate);
//            model.addObject("ldate", ldate);
            bnkstmt = bnkstmtdao.CRBankStatementlistbydates(startDate, endDate);
            model.addObject("bnkstmt", bnkstmt);
            model.addObject("fdate", frmDate);
            model.addObject("ldate", toDate);

            //Object[] row = (Object[]) queryList.get(0);
            // System.out.println("Element "+Arrays.toString(row));  
            // int name= Integer.parseInt(String.valueOf(row[1]));
            //  System.out.println("name =" + (name-1));
            return model;

        }

        return mv;

    }

    public ModelAndView bankdrdetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ModelAndView mv = new ModelAndView("overallmapping");
        String bName = request.getParameter("bName");
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);

            ModelAndView model = new ModelAndView("Report/bankdrdetails");

            BankStatementDAO bnkstmtdao = new BankStatementDAO();
            List<BankStatement> bnkstmtrecon0 = null;
            List<BankStatement> bnkstmtrecon1 = null;

//            ReportDAO rpt = new ReportDAO();
//            List<Object[]> queryList1 = rpt.getpoolaccount(fdate, ldate, yeari);
//
//            model.addObject("queryList1", queryList1);
//
//            model.addObject("yeari", yeari);
//            model.addObject("fdate", fdate);
//            model.addObject("ldate", ldate);
            bnkstmtrecon0 = bnkstmtdao.DRBankStatementlistbydatesreconzero(startDate, endDate);
            model.addObject("bnkstmtrecon0", bnkstmtrecon0);

            model.addObject("fdate", frmDate);
            model.addObject("ldate", toDate);
            //Object[] row = (Object[]) queryList.get(0);
            // System.out.println("Element "+Arrays.toString(row));  
            // int name= Integer.parseInt(String.valueOf(row[1]));
            //  System.out.println("name =" + (name-1));
            return model;

        }

        return mv;

    }

    public ModelAndView groups(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/groups");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        ReportDAO rpt = new ReportDAO();
        List<Object[]> queryList1 = rpt.getgroups();

        mv.addObject("queryList1", queryList1);
        return mv;

    }

    public ModelAndView entfinalreport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            if (type.equals("DSM")) {
                ModelAndView model = new ModelAndView("Report/entfinal");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinal(week_id1, week_id2, yeari);
                List<BillPayableEntityDsm> queryList = rpt.getentityfinal(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);

                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                return model;
            }

            if (type.equals("RRAS")) {
                ModelAndView model = new ModelAndView("Report/entfinalrras");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinalrras(week_id1, week_id2, yeari);

                List<BillPayableEntityRras> queryList = rpt.getentityfinalrras(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);

                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                return model;
            }

            if (type.equals("AGC")) {
                ModelAndView model = new ModelAndView("Report/entfinalagc");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinalagc(week_id1, week_id2, yeari);
//                model.addObject("queryList", queryList);
                List<BillEntityAgc> queryList = rpt.getentityfinalagc(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);
                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                String payrrec = "Payable";
                model.addObject("payrrec", payrrec);
                return model;
            }
            if (type.equals("FRAS")) {
                ModelAndView model = new ModelAndView("Report/entfinalfras");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinalfras(week_id1, week_id2, yeari);

                List<BillEntityFras> queryList = rpt.getentityfinalfras(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);

                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                String payrrec = "Payable";
                model.addObject("payrrec", payrrec);
                return model;
            }

            //Object[] row = (Object[]) queryList.get(0);
            // System.out.println("Element "+Arrays.toString(row));  
            // int name= Integer.parseInt(String.valueOf(row[1]));
            //  System.out.println("name =" + (name-1));
        }
        String title = "Entity Payable Final";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView recentfinalreport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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

            if (type.equals("DSM")) {
                ModelAndView model = new ModelAndView("Report/recentfinal");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinalrec(week_id1, week_id2, yeari);
                List<BillReceiveEntityDsm> queryList = rpt.getentityfinalrec(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);

                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                return model;
            }

            if (type.equals("RRAS")) {
                ModelAndView model = new ModelAndView("Report/recentfinalrras");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinalrrasrec(week_id1, week_id2, yeari);
                List<BillReceiveEntityRras> queryList = rpt.getentityfinalrrasrec(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);

                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                return model;
            }

            if (type.equals("AGC")) {
                ModelAndView model = new ModelAndView("Report/entfinalagc");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinalagcrec(week_id1, week_id2, yeari);
                List<BillEntityAgc> queryList = rpt.getentityfinalagcrec(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);

                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                String payrrec = "Receivable";
                model.addObject("payrrec", payrrec);
                return model;
            }

            if (type.equals("FRAS")) {
                ModelAndView model = new ModelAndView("Report/entfinalfras");
                ReportDAO rpt = new ReportDAO();
//                List<Object[]> queryList = rpt.getentityfinalfrasrec(week_id1, week_id2, yeari);
                List<BillEntityFras> queryList = rpt.getentityfinalfrasrec(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
                System.out.println("queryList is " + queryList);
                model.addObject("queryList", queryList);

                model.addObject("yeari", yeari);
                model.addObject("week_id1", week_id1);
                model.addObject("week_id2", week_id2);
                model.addObject("type", type);
                String payrrec = "Receivable";
                model.addObject("payrrec", payrrec);
                return model;
            }
            //Object[] row = (Object[]) queryList.get(0);
            // System.out.println("Element "+Arrays.toString(row));  
            // int name= Integer.parseInt(String.valueOf(row[1]));
            //  System.out.println("name =" + (name-1));

        }
        String title = "Entity Receivable Final";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView irfinal(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/IRfinalreport");
            ReportDAO rpt = new ReportDAO();
//            List<Object[]> queryList1 = rpt.getirfinal(week_id1, week_id2, type, yeari);
            List<BillPayableCorp> queryList1 = rpt.getirfinal(new BigDecimal(week_id1), new BigDecimal(week_id2), type, new BigDecimal(yeari));
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

        String title = "Inter-Regional payable final";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView recirfinal(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corpreport");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
                week_id2 = Integer.parseInt(week);
            }
            if (dbType.equals("ANNUALLY")) {

                String year = request.getParameter("yeara");
                yeari = Integer.parseInt(year);
                week_id1 = 1;
                week_id2 = 53;
            }
            System.out.println("month starts from weekid " + week_id1 + " to " + week_id2);

            ModelAndView model = new ModelAndView("Report/recIRfinalreport");
            ReportDAO rpt = new ReportDAO();
//            List<Object[]> queryList1 = rpt.getirfinalrec(week_id1, week_id2, type, yeari);
            List<BillReceiveCorp> queryList1 = rpt.getirfinalrec(new BigDecimal(week_id1), new BigDecimal(week_id2), type, new BigDecimal(yeari));
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

        String title = "Inter-Regional receivable final";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView mappedbankdetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/allpercorpview");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String dbType = request.getParameter("dbType");

        String corpName = request.getParameter("corpId");

        CorporatesDAO corporatedao = new CorporatesDAO();

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

            int corporateid = corporatedao.getCorpIdbyName(corpName);

            int corpId = corporateid;
            ModelAndView model = new ModelAndView("Report/bankdetailspercorp");
            ReportDAO rpt = new ReportDAO();
            List<Object[]> queryList1 = rpt.getfinalpaypercorp(week_id1, week_id2, corpId, yeari);
//            List<Object[]> queryList2 = rpt.getmappedbankdetails(week_id1, week_id2, corpId, yeari);

            model.addObject("queryList1", queryList1);
//            model.addObject("queryList2", queryList2);
            model.addObject("yeari", yeari);
            model.addObject("week_id1", week_id1);
            model.addObject("week_id2", week_id2);
            model.addObject("corpName", corpName);

            //Object[] row = (Object[]) queryList.get(0);
            // System.out.println("Element "+Arrays.toString(row));  
            // int name= Integer.parseInt(String.valueOf(row[1]));
            //  System.out.println("name =" + (name-1));
            return model;

        }

        List<Corporates> corporateList = corporatedao.Corporateslist();
        List<String> listCorp = new ArrayList<>();

        for (Corporates temp : corporateList) {
            listCorp.add(temp.getCorporateName());
        }

        mv.addObject("corporateList", listCorp);
        String title = "CORPORATE mapped bank details";
        mv.addObject("title", title);

        return mv;

    }

    public ModelAndView allbills(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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

            ModelAndView model = new ModelAndView("Report/allbills");
            ReportDAO rpt = new ReportDAO();

            List<BillPayableCorp> queryList1 = rpt.getallbillpayablereport(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
            System.out.println("queryList1 is " + queryList1);
//        List<Object[]> queryList1 = rpt.getallbillpayablereport();
            List<BillReceiveCorp> queryList2 = rpt.getallbillreceivablereport(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));
            System.out.println("queryList2 is " + queryList2);
//        List<Object[]> queryList2 = rpt.getallbillreceivablereport();

            model.addObject("queryList1", queryList1);
            model.addObject("queryList2", queryList2);
            return model;

        }

        String title = "Commercial Group Bill Status";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView corpYearlyBnkStmt(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
//        ModelAndView mv = new ModelAndView("Report/corpYearlyBnkStmt");
        ModelAndView mv = new ModelAndView("Report/corporateWiseBankStmt");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

//        String corpName = request.getParameter("corpId");
//        System.out.println("corpName is "+corpName);
        ReportDAO rpt = new ReportDAO();
        BankStatementDAO bankDao = new BankStatementDAO();
        String bName = request.getParameter("bName");
        System.out.println("bName " + bName);
        if (bName != null) {
            String corpName = request.getParameter("corpId");
            CorporatesDAO corpdao = new CorporatesDAO();

            int corpid = corpdao.getCorpIdbyName(corpName);
            String subAccNum = corpdao.getBankSubAccNumberbyId(corpid);
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            List<BankStatement> stmtInfoList = rpt.getBankStatementByCorpAndYear(corpid, startDate, endDate);

            if (stmtInfoList != null) {
                System.out.println("In stmtInfoList != null $$$$$$ stmtInfoList size is " + stmtInfoList.size());
                ModelAndView mv1 = new ModelAndView("Report/corpYearlyBnkStmt");

                System.out.println("stmtInfoList is " + stmtInfoList);
                mv1.addObject("stmtInfoList", stmtInfoList);
                mv1.addObject("toDate", toDate);
                mv1.addObject("frmDate", frmDate);

                mv1.addObject("corpName", corpName);
                mv1.addObject("subAccNum", subAccNum);
                return mv1;
            } else {
                ModelAndView mv1 = new ModelAndView("successMsg");
                String Msg = "No Bank Statement to View!! ";
                mv1.addObject("Msg", Msg);
                return mv1;
            }
        }

        CorporatesDAO corporatedao = new CorporatesDAO();
        List<Corporates> corporateList = corporatedao.Corporateslist();
        List<String> listCorp = new ArrayList<>();

        for (Corporates temp : corporateList) {
            listCorp.add(temp.getCorporateName());
        }

        mv.addObject("corporateList", listCorp);
        String title = "Commercial Group Bank Statement";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView corpbanktobilldetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corporateWiseBankStmt");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

// String corpName = request.getParameter("corpId");
// System.out.println("corpName is "+corpName);
        ReportDAO rpt = new ReportDAO();
        BankStatementDAO bankDao = new BankStatementDAO();
        String bName = request.getParameter("bName");
        System.out.println("bName " + bName);
        if (bName != null) {
            String corpName = request.getParameter("corpId");
            CorporatesDAO corpdao = new CorporatesDAO();

            int corpid = corpdao.getCorpIdbyName(corpName);
            System.out.println("corpid is " + corpid);
            String subAccNum = corpdao.getBankSubAccNumberbyId(corpid);
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
            String fdate = format2.format(startDate);
            String ldate = format2.format(endDate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            List<BankStatement> stmtInfoList = rpt.getBankStatementByCorpAndYear(corpid, startDate, endDate);
            List<MappingBillBank> mappbilllistbystmtid = rpt.getdetailsBybankstmt(corpid);

            List<Object[]> queryList1 = rpt.getentitygetBankStatementByCorpAndYearsql(corpid, fdate, ldate);
//if (stmtInfoList != null) {
//System.out.println("In stmtInfoList != null $$$$$$ stmtInfoList size is " + stmtInfoList.size());
//ModelAndView mv1 = new ModelAndView("Report/corpBnkStmttobill");
//
//System.out.println("stmtInfoList is " + stmtInfoList);
//mv1.addObject("stmtInfoList", stmtInfoList);
//mv1.addObject("toDate", toDate);
//mv1.addObject("frmDate", frmDate);
//mv1.addObject("mappbilllistbystmtid",mappbilllistbystmtid);
//mv1.addObject("corpName", corpName);
//mv1.addObject("subAccNum", subAccNum);
//return mv1;
//} 
            if (queryList1 != null) {
                System.out.println("In stmtInfoList != null $$$$$$ stmtInfoList size is " + stmtInfoList.size());
                ModelAndView mv1 = new ModelAndView("Report/corpBnkStmttobill");

                System.out.println("queryList1 is " + queryList1);
                mv1.addObject("queryList1", queryList1);
                mv1.addObject("toDate", toDate);
                mv1.addObject("frmDate", frmDate);
                mv1.addObject("corpName", corpName);
                mv1.addObject("subAccNum", subAccNum);
                return mv1;
            } else {
                ModelAndView mv1 = new ModelAndView("successMsg");
                String Msg = "No Bank Statement to View!! ";
                mv1.addObject("Msg", Msg);
                return mv1;
            }
        }

        CorporatesDAO corporatedao = new CorporatesDAO();
        List<Corporates> corporateList = corporatedao.Corporateslist();
        List<String> listCorp = new ArrayList<>();

        for (Corporates temp : corporateList) {
            listCorp.add(temp.getCorporateName());
        }

        mv.addObject("corporateList", listCorp);
        String title = "View Bank To Bill Mapping";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView getallrefundPayable(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
            ReportDAO rpt = new ReportDAO();

            List<BillPayableCorp> queryList1 = rpt.getRefundBillPayableCorplist(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));

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

    public ModelAndView getallrefundReceivable(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
            ReportDAO rpt = new ReportDAO();

            List<BillReceiveCorp> queryList1 = rpt.getRefundBillReceivableCorplist(new BigDecimal(week_id1), new BigDecimal(week_id2), new BigDecimal(yeari));

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

    public ModelAndView getallinterestPayable(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
            ReportDAO rpt = new ReportDAO();

            List<InterestDetails> queryList1 = rpt.getInterestPayableDetails(new BigDecimal(week_id1), new BigDecimal(week_id2), Integer.toString(yeari));

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

    public ModelAndView getallinterestReceivable(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView("Report/billstatusselect");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

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
                if (week_id1 == 52) {
                    week_id1 = 1;
                }
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
            ReportDAO rpt = new ReportDAO();

            List<DisbursedInterestDetails> queryList1 = rpt.getInterestReceivableDetails(new BigDecimal(week_id1), new BigDecimal(week_id2), Integer.toString(yeari));

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

    public ModelAndView newDisbursementReconciliationReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {

            RedirectView redirectView = new RedirectView();

            redirectView.setContextRelative(true);

            redirectView.setUrl("/logout.htm");

            return new ModelAndView(redirectView);

        }

        ModelAndView mv = new ModelAndView("newDisbursementReconciliationReport");

        String bName = request.getParameter("bName");

        if (bName != null) {

            BankStatementDAO bankstmtdao = new BankStatementDAO();

            List<BankStatement> list = null;

            List<PaymentDisbursement> list1 = new ArrayList<>();

            PaymentDisbursementDAO paydisdao = new PaymentDisbursementDAO();

            String disbursetype = null;

            String startdate = request.getParameter("startdate");

            String enddate = request.getParameter("enddate");

            Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(startdate);

            Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(enddate);

            list = bankstmtdao.getBankStatementbyFromdateTodates(date1, date2);

            disbursetype = request.getParameter("reporttype");

            List<TempRefundBillCorp> listrefund = null;

            System.out.print("ous e is " + disbursetype);

            TempRefundBillCorpDAO temprefudndao = new TempRefundBillCorpDAO();

            if (disbursetype.equalsIgnoreCase("Bills")) {
                System.out.print("vinside e is " + disbursetype);

                list1 = paydisdao.getPaymentDisbursementbyFromdateTodates(date1, date2);

                System.out.print("@@@@@@@@@@ list size is " + list1.size());

                ModelAndView mv1 = new ModelAndView("viewDisbursementReconciliationReport");

                mv1.addObject("bankList", list);

                mv1.addObject("disburseList", list1);

                return mv1;

            }
            if (disbursetype.equalsIgnoreCase("Refund")) {

                list = bankstmtdao.getBankStatementbyFromdateTodatesForRefundDR(date1, date2);

                listrefund = temprefudndao.getRefundBillDisbursementCorpbyFromdateTodates(date1, date2);

                ModelAndView mv1 = new ModelAndView("viewRefundDisbursementReconciliationReport");

                mv1.addObject("bankList", list);

                mv1.addObject("refundList", listrefund);

                return mv1;

            }

        }

        return mv;

    }

    public ModelAndView submitDisbursementReconciliationReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {

            RedirectView redirectView = new RedirectView();

            redirectView.setContextRelative(true);

            redirectView.setUrl("/logout.htm");

            return new ModelAndView(redirectView);

        }

        PaymentDisbursementDAO paydisdao = new PaymentDisbursementDAO();

        List<PaymentDisbursement> paylist = null;

        String count = request.getParameter("count");

        TempDisbInterestDetails tempdisine = new TempDisbInterestDetails();

        TempDisbInterestDetailsDAO tempdusdao = new TempDisbInterestDetailsDAO();

        BillInterestRateDAO billindao = new BillInterestRateDAO();

        List<BillInterestRate> listinte = null;

        System.out.println("row count" + count);

        for (int i = 1; i <= Integer.parseInt(count); i++) {

            String disburseid1 = request.getParameter("disburseid" + i);

            String disbursedate1 = request.getParameter("disbursedate" + i);

            String disburseamt1 = request.getParameter("disburseamt" + i);

            paylist = paydisdao.getDisbursementDetailsbyDisburseID(Integer.parseInt(disburseid1));

            System.out.println("disburseid1" + disburseid1);

            System.out.println("disbursedate1" + disbursedate1);

            System.out.println("disburseamt1" + disburseamt1);

            Date date1 = paylist.get(0).getBillDueDate();

            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(disbursedate1);

            int maxid = 0;

            int interestflag = 0;

            float daysBetween = 0;

//            if (date1.compareTo(date2) < 0) {
//
//                interestflag = 1;
//
//                long difference = date2.getTime() - date1.getTime();
//
//                daysBetween = (difference / (1000 * 60 * 60 * 24));
//
//                System.out.println("daysBetween" + daysBetween);
//
//            }
            if (date1.compareTo(date2) < 0 && paylist.get(0).getDisburseAmount().compareTo(BigDecimal.ONE) >= 0) {

                long difference = date2.getTime() - date1.getTime();
                daysBetween = (difference / (1000 * 60 * 60 * 24));
                System.out.println("daysBetween" + daysBetween);
                listinte = billindao.getBillInterestRate(paylist.get(0).getBillType(), "RECEIVABLE");
                BigDecimal inbg = paylist.get(0).getDisburseAmount().multiply(new BigDecimal(daysBetween)).multiply(listinte.get(0).getInterestRate());
//                if (inbg.compareTo(BigDecimal.valueOf(100)) >= 0) {
                if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {

                    interestflag = 1;
                }
                System.out.println("inbg" + inbg);
            }
            if (interestflag == 1) {

                maxid = 0;

                maxid = tempdusdao.getMaxDisInterestid();

                maxid = maxid + 1;

                BigDecimal bgmax = new BigDecimal(maxid);

                tempdisine.setInterestId(bgmax);

                tempdisine.setWeekId(paylist.get(0).getWeekId());

                tempdisine.setRevisionNo(paylist.get(0).getBillReceiveCorp().getRevisionNo());

                tempdisine.setBillYear(paylist.get(0).getBillReceiveCorp().getBillYear());

                tempdisine.setBillCategory(paylist.get(0).getDisburseCategory());

                tempdisine.setBillType(paylist.get(0).getBillType());

                tempdisine.setBilledAmount(paylist.get(0).getBillReceiveCorp().getToalnet());

                tempdisine.setBillingDate(paylist.get(0).getBillingDate());

                tempdisine.setBillingDuedate(paylist.get(0).getBillDueDate());

                tempdisine.setCheckerStatus("Pending");

                tempdisine.setCorporates(paylist.get(0).getCorporates());

                tempdisine.setDisbursedAmount(paylist.get(0).getDisburseAmount());

                tempdisine.setDisbursedDate(date2);

                tempdisine.setEntryDate(new Date());

                listinte = billindao.getBillInterestRate(paylist.get(0).getBillType(), "RECEIVABLE");

                BigDecimal bginrate = listinte.get(0).getInterestRate();

                BigDecimal inteamt = paylist.get(0).getDisburseAmount().multiply(bginrate);

                BigDecimal days = new BigDecimal(daysBetween);

                inteamt = inteamt.multiply(days);

                tempdisine.setInterestAmount(inteamt);

                tempdisine.setNoofdays(days);

                tempdisine.setInterestBilledamount(paylist.get(0).getDisburseAmount());

                tempdusdao.NewTempDisbInterestDetails(tempdisine);

            }//end of interest

            BankStatementDAO bankstdao = new BankStatementDAO();

            bankstdao.getUpdateReconflagBankStmtforbillsbydisburseID(Integer.parseInt(disburseid1));

        }// end of loop

        ModelAndView mv2 = new ModelAndView("successMsg");

        mv2.addObject("Msg", "Succesfully Interest has been generated for processing");

        return mv2;

    }

    public ModelAndView newReconciliationReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {

            RedirectView redirectView = new RedirectView();

            redirectView.setContextRelative(true);

            redirectView.setUrl("/logout.htm");

            return new ModelAndView(redirectView);

        }

        ModelAndView mv = new ModelAndView("newReconciliationReport");

        String bName = request.getParameter("bName");

        System.out.println("bName " + bName);
        CorporatesDAO corpdao = new CorporatesDAO();
        List<Corporates> corporateList = corpdao.Corporateslist();

        if (bName != null) {

            String corpID = request.getParameter("corparateID");

            String yearq = request.getParameter("yearq");

            BankStatementDAO bankDao = new BankStatementDAO();

            BillPayableCorpDAO billpacorpdao = new BillPayableCorpDAO();

            BillReceiveCorpDAO billdaorecv = new BillReceiveCorpDAO();

            TempRefundBillCorpDAO temprefundbill = new TempRefundBillCorpDAO();

            TempInterestDetailsDAO tempintdet = new TempInterestDetailsDAO();

            CorporatesDAO corporatedao = new CorporatesDAO();

            List<BillPayableCorp> listbillpay = null;
            List<BillPayableCorp> listrefundbillpay = null;
            List<BillReceiveCorp> listbillrec = null;
            List<BillReceiveCorp> listrefundbillrec = null;

            List<BankStatement> listbank = null;

            BankStatementDAO bankstdao = new BankStatementDAO();

            MappingBillBankDAO mapbilldao = new MappingBillBankDAO();

            InterestDetailsDAO intedao = new InterestDetailsDAO();

            MappingInterestBankDAO mapintbnkdao = new MappingInterestBankDAO();

            TempDisbInterestDetailsDAO tempdisintdao = new TempDisbInterestDetailsDAO();

            DisbursedInterestDetailsDAO disintdetdao = new DisbursedInterestDetailsDAO();

            List<PaymentInterestDisbursement> disintdetlis = null;

            List<DisbursedInterestDetails> tempdisintlist = null;

            List<MappingInterestBank> mapintbnklst = null;

            List<MappingBillBank> lismap = null;

            List<TempRefundBillCorp> lisrefundmap = null;

            List<InterestDetails> lispayInterest = null;

            List<Timestamp> list2 = new ArrayList<Timestamp>();

            List<Timestamp> list3 = new ArrayList<Timestamp>();

            List<Timestamp> list4 = new ArrayList<Timestamp>();

            List<Timestamp> list5 = new ArrayList<Timestamp>();

            List<Timestamp> list6 = new ArrayList<Timestamp>();

            List<Timestamp> list7 = new ArrayList<Timestamp>();

            List<Timestamp> list8 = new ArrayList<Timestamp>();

            List<Timestamp> list9 = new ArrayList<Timestamp>();

            List<Timestamp> listnew = new ArrayList<Timestamp>();

            List<BigDecimal> listreptduniqueids = new ArrayList<BigDecimal>();
            List<BigDecimal> listreptdisburseids = new ArrayList<BigDecimal>();
            List<TempRefundBillCorp> temprefund = null;
            List<InterestDetails> tempintdetlis = null;
            List<PaymentDisbursement> listpaydiburse = null;
            List<MappingRefundBank> maprefundbank = null;
            List<Corporates> corplist = null;

            PaymentDisbursementDAO paydisdao = new PaymentDisbursementDAO();
            MappingRefundBankDAO mapprefundbankdao = new MappingRefundBankDAO();
            MappingInterestBankDAO mapintbankdao = new MappingInterestBankDAO();
            TempBillAGCDetailsDAO tempbillagcdetailsdao = new TempBillAGCDetailsDAO();
            TempBillDSMDetailsDAO tempbilldsmdetailsdao = new TempBillDSMDetailsDAO();
            TempBillFRASDetailsDAO tempbillfrasdetailsdao = new TempBillFRASDetailsDAO();
            TempBillRRASDetailsDAO tempbillrrasdetailsdao = new TempBillRRASDetailsDAO();

            String corpname = corporatedao.geCorpNamebyId(Integer.parseInt(corpID));

            int yeari = Integer.parseInt(yearq);

            list2 = mapbilldao.getMappingBillBankbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);

            list3 = bankstdao.getBankStatementbygroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
            list4 = intedao.getInterestDetailsbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
            list5 = billpacorpdao.getBillPayablebyCorpgroupbyBillingDateTimestamp(Integer.parseInt(corpID), yeari);
            list6 = temprefundbill.getRefundbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
            list7 = billdaorecv.getBillReceivablebyCorpgroupbyBillingDateTimestamp(Integer.parseInt(corpID), yeari);
            list8 = tempdisintdao.getdisbInterestDetailsbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
            list9 = mapintbankdao.getInterestmappingbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
//            list8=billdaorecv.getRefundBillReceivablebyCorpgroupbyBillingDateTimestamp(Integer.parseInt(corpID));
            if (list2 != null && !(list2.isEmpty())) {

                for (int m = 0; m < list2.size(); m++) {

                    listnew.add(list2.get(m));

                }

            }

            if (list3 != null && !(list3.isEmpty())) {

                for (int m1 = 0; m1 < list3.size(); m1++) {

                    int flag = 0;

                    for (int y = 0; y < listnew.size(); y++) {

                        if (list3.get(m1).equals(listnew.get(y))) {

                            flag = 1;

                        }

                    }

                    if (flag == 0) {

                        listnew.add(list3.get(m1));

                    }

                }

            }

            if (list4 != null && !(list4.isEmpty())) {

                for (int m1 = 0; m1 < list4.size(); m1++) {

                    int flag = 0;

                    for (int y = 0; y < listnew.size(); y++) {

                        if (list4.get(m1).equals(listnew.get(y))) {

                            flag = 1;

                        }

                    }

                    if (flag == 0) {

                        listnew.add(list4.get(m1));

                    }

                }

            }

            if (list5 != null && !(list5.isEmpty())) {

                for (int m1 = 0; m1 < list5.size(); m1++) {

                    int flag = 0;

                    for (int y = 0; y < listnew.size(); y++) {

                        if (list5.get(m1).equals(listnew.get(y))) {

                            flag = 1;

                        }

                    }

                    if (flag == 0) {

                        listnew.add(list5.get(m1));

                    }

                }

            }
            if (list6 != null && !(list6.isEmpty())) {

                for (int m1 = 0; m1 < list6.size(); m1++) {

                    int flag = 0;

                    for (int y = 0; y < listnew.size(); y++) {

                        if (list6.get(m1).equals(listnew.get(y))) {

                            flag = 1;

                        }

                    }

                    if (flag == 0) {

                        listnew.add(list6.get(m1));

                    }

                }

            }

            if (list7 != null && !(list7.isEmpty())) {

                for (int m1 = 0; m1 < list7.size(); m1++) {

                    int flag = 0;

                    for (int y = 0; y < listnew.size(); y++) {

                        if (list7.get(m1).equals(listnew.get(y))) {

                            flag = 1;

                        }

                    }

                    if (flag == 0) {

                        listnew.add(list7.get(m1));

                    }

                }

            }

            if (list8 != null && !(list8.isEmpty())) {

                for (int m1 = 0; m1 < list8.size(); m1++) {

                    int flag = 0;

                    for (int y = 0; y < listnew.size(); y++) {

                        if (list8.get(m1).equals(listnew.get(y))) {

                            flag = 1;

                        }

                    }

                    if (flag == 0) {

                        listnew.add(list8.get(m1));

                    }

                }

            }

            if (list9 != null && !(list9.isEmpty())) {

                for (int m1 = 0; m1 < list9.size(); m1++) {

                    int flag = 0;

                    for (int y = 0; y < listnew.size(); y++) {

                        if (list9.get(m1).equals(listnew.get(y))) {

                            flag = 1;

                        }

                    }

                    if (flag == 0) {

                        listnew.add(list9.get(m1));

                    }

                }

            }

            Collections.sort(listnew);

            listbillpay = billpacorpdao.getReconPendingBillCorpNameList(Integer.parseInt(corpID), Integer.toString(yeari));
            listbillrec = billdaorecv.getReconPendingBillCorpNameList(Integer.parseInt(corpID), Integer.toString(yeari));

//            listrefundbillpay=billpacorpdao.getReconrefundPendingBillCorpNameList(Integer.parseInt(corpID));
//            listrefundbillrec= billdaorecv.getReconrefundPendingBillCorpNameList(Integer.parseInt(corpID));
            listbank = bankstdao.BankStatementlistbyDR(Integer.parseInt(corpID), Integer.toString(yeari));
            //    System.out.println("listbank is "+listbank.size());
            listpaydiburse = paydisdao.getDisbursementDetailsbyCorpStatus("Confirm", Integer.parseInt(corpID));
            System.out.println("listpaydiburse is " + listpaydiburse.size());
            lismap = mapbilldao.getMappingBillBankDetailsbyCorpID(Integer.parseInt(corpID), Integer.toString(yeari));
            temprefund = temprefundbill.getAllRefundPayabledataTempRefundBillCorp(Integer.parseInt(corpID));
//                lispayInterest = intedao.getInterestPayableDetailsbyCorpforRecon(Integer.parseInt(corpID));
            maprefundbank = mapprefundbankdao.getMappingRefundbyCorpID(Integer.parseInt(corpID), Integer.toString(yeari));
            tempintdetlis = tempintdet.getTempInterestDetailsbyonlyCorpid(Integer.parseInt(corpID), Integer.toString(yeari));
            mapintbnklst = mapintbnkdao.getMappingInterestBankbyonlyCorpID(Integer.parseInt(corpID), Integer.toString(yeari));
            corplist = corpdao.getCorporatesbyCorporateId(Integer.parseInt(corpID));

            BigDecimal outstanding = BigDecimal.ZERO;
            OutstandingYearCorpDAO outstandyearcorpdao = new OutstandingYearCorpDAO();
            List<BigDecimal> outstandlist = outstandyearcorpdao.getoutstandingbycorpidbyyear(yeari, Integer.parseInt(corpID));
            if (outstandlist != null && !(outstandlist.isEmpty())) {
                outstanding = outstandlist.get(0);
            }

            System.out.println("@@@@@@@@@@@@@@@@@ outstanding " + outstanding);
            tempdisintlist = tempdisintdao.getTempdisbInterestDetailsbyonlyCorpid(Integer.parseInt(corpID), Integer.toString(yeari));

            disintdetlis = disintdetdao.getDisbursedInterestDetailsbyonlyCorp(Integer.parseInt(corpID));

            for (int i = 0; i < listnew.size(); i++) {
                System.out.println("@@@@@@@@@@@@@@@@@ dateList " + listnew.get(i));
            }

            System.out.println("@@@@@@@@@@@@@@@@@size of dateList " + listnew.size());
            ModelAndView mv1 = new ModelAndView("viewReconciliationReport");

            mv1.addObject("mapList", lismap);

            mv1.addObject("bankdislist", listbank);

            mv1.addObject("lispayInterest", lispayInterest);

            mv1.addObject("corpName", corpname);

            mv1.addObject("dateList", listnew);
            mv1.addObject("listbillpay", listbillpay);
            mv1.addObject("listbillrec", listbillrec);
            mv1.addObject("maprefundbank", maprefundbank);
            mv1.addObject("tempintdetlis", tempintdetlis);
            mv1.addObject("mapintbnklst", mapintbnklst);

            mv1.addObject("listpaydiburse", listpaydiburse);

            mv1.addObject("temprefund", temprefund);

            mv1.addObject("tempdisintlist", tempdisintlist);

            mv1.addObject("disintdetlis", disintdetlis);
            mv1.addObject("outstanding", outstanding);

            return mv1;

        }

        mv.addObject("corporateList", corporateList);

        return mv;

    }

    public ModelAndView submitRefundDisbursementReconciliationReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {

            RedirectView redirectView = new RedirectView();

            redirectView.setContextRelative(true);

            redirectView.setUrl("/logout.htm");

            return new ModelAndView(redirectView);

        }

        String count = request.getParameter("count");

        TempDisbInterestDetails tempdisine = new TempDisbInterestDetails();

        TempDisbInterestDetailsDAO tempdusdao = new TempDisbInterestDetailsDAO();

        TempRefundBillCorpDAO temrefudndao = new TempRefundBillCorpDAO();

        BillInterestRateDAO billindao = new BillInterestRateDAO();

        List<BillInterestRate> listinte = null;

        List<TempRefundBillCorp> listrefund = null;

        System.out.println("row count" + count);

        for (int i = 1; i <= Integer.parseInt(count); i++) {

            String disburseid1 = request.getParameter("disburseid" + i);

            String disbursedate1 = request.getParameter("disbursedate" + i);

            String disburseamt1 = request.getParameter("disburseamt" + i);

            listrefund = temrefudndao.getTempRefundBillCorpbySLNO(Integer.parseInt(disburseid1));

            System.out.println("disburseid1" + disburseid1);

            System.out.println("disbursedate1" + disbursedate1);

            System.out.println("disburseamt1" + disburseamt1);

            Date date1 = listrefund.get(0).getBillPayableCorp().getBillDueDate();

            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(disbursedate1);

            int maxid = 0;

            int interestflag = 0;

            float daysBetween = 0;

//            if (date1.compareTo(date2) < 0) {
//
//                interestflag = 1;
//
//                long difference = date2.getTime() - date1.getTime();
//
//                daysBetween = (difference / (1000 * 60 * 60 * 24));
//
//                System.out.println("daysBetween" + daysBetween);
//
//            }
            if (date1.compareTo(date2) < 0 && listrefund.get(0).getPaidAmount().compareTo(BigDecimal.ONE) >= 0) {

                long difference = date2.getTime() - date1.getTime();
                daysBetween = (difference / (1000 * 60 * 60 * 24));
                System.out.println("daysBetween" + daysBetween);
                listinte = billindao.getBillInterestRate(listrefund.get(0).getBillPayableCorp().getBillType(), "RECEIVABLE");
                BigDecimal inbg = listrefund.get(0).getPaidAmount().multiply(new BigDecimal(daysBetween)).multiply(listinte.get(0).getInterestRate());
                if (inbg.compareTo(BigDecimal.valueOf(0)) >= 0) {
                    interestflag = 1;
                }
                System.out.println("inbg" + inbg);
            }
            if (interestflag == 1) {

                maxid = 0;

                maxid = tempdusdao.getMaxDisInterestid();

                maxid = maxid + 1;

                BigDecimal bgmax = new BigDecimal(maxid);

                tempdisine.setInterestId(bgmax);

                tempdisine.setWeekId(listrefund.get(0).getWeekid());

                tempdisine.setRevisionNo(listrefund.get(0).getBillPayableCorp().getRevisionNo());

                tempdisine.setBillCategory(listrefund.get(0).getBillPayableCorp().getBillCategory());

                tempdisine.setBillType(listrefund.get(0).getBillPayableCorp().getBillType());

                tempdisine.setBillYear(listrefund.get(0).getBillPayableCorp().getBillYear());

                tempdisine.setBilledAmount(listrefund.get(0).getBillPayableCorp().getTotalnet());

                tempdisine.setBillingDate(listrefund.get(0).getBillPayableCorp().getBillingDate());

                tempdisine.setBillingDuedate(date1);

                tempdisine.setCheckerStatus("Pending");

                tempdisine.setCorporates(listrefund.get(0).getBillPayableCorp().getCorporates());

                tempdisine.setDisbursedAmount(listrefund.get(0).getPaidAmount());

                tempdisine.setDisbursedDate(date2);

                tempdisine.setEntryDate(new Date());

                listinte = billindao.getBillInterestRate(listrefund.get(0).getBillPayableCorp().getBillType(), "RECEIVABLE");

                BigDecimal bginrate = listinte.get(0).getInterestRate();

                BigDecimal inteamt = listrefund.get(0).getPaidAmount().multiply(bginrate);

                BigDecimal days = new BigDecimal(daysBetween);

                inteamt = inteamt.multiply(days);

                tempdisine.setInterestAmount(inteamt);

                tempdisine.setNoofdays(days);

                tempdisine.setInterestBilledamount(listrefund.get(0).getPaidAmount());

                tempdusdao.NewTempDisbInterestDetails(tempdisine);

            }//end of interest

            BankStatementDAO bankstdao = new BankStatementDAO();

            bankstdao.getUpdateReconflagBankStmtforrefundbydisburseID(Integer.parseInt(disburseid1));

        }// end of loop

        ModelAndView mv2 = new ModelAndView("successMsg");

        mv2.addObject("Msg", "Sucessfully Interest Genearted for Interest Procfessing");

        return mv2;

    }

    public ModelAndView newReconciliationReportforallcorp(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);

        if (session1 == null) {

            RedirectView redirectView = new RedirectView();

            redirectView.setContextRelative(true);

            redirectView.setUrl("/logout.htm");

            return new ModelAndView(redirectView);

        }

        ModelAndView mv = new ModelAndView("updaterecon");

        String bName = request.getParameter("bName");

        System.out.println("bName " + bName);

        if (bName != null) {
            BankStatementDAO bankDao = new BankStatementDAO();

            BillPayableCorpDAO billpacorpdao = new BillPayableCorpDAO();

            BillReceiveCorpDAO billdaorecv = new BillReceiveCorpDAO();

            TempRefundBillCorpDAO temprefundbill = new TempRefundBillCorpDAO();

            TempInterestDetailsDAO tempintdet = new TempInterestDetailsDAO();

            CorporatesDAO corporatedao = new CorporatesDAO();

            List<BillPayableCorp> listbillpay = null;
            List<BillPayableCorp> listrefundbillpay = null;
            List<BillReceiveCorp> listbillrec = null;
            List<BillReceiveCorp> listrefundbillrec = null;

            List<BankStatement> listbank = null;

            BankStatementDAO bankstdao = new BankStatementDAO();

            MappingBillBankDAO mapbilldao = new MappingBillBankDAO();

            InterestDetailsDAO intedao = new InterestDetailsDAO();

            MappingInterestBankDAO mapintbnkdao = new MappingInterestBankDAO();

            TempDisbInterestDetailsDAO tempdisintdao = new TempDisbInterestDetailsDAO();

            DisbursedInterestDetailsDAO disintdetdao = new DisbursedInterestDetailsDAO();

            List<PaymentInterestDisbursement> disintdetlis = null;

            List<DisbursedInterestDetails> tempdisintlist = null;

            List<MappingInterestBank> mapintbnklst = null;

            List<MappingBillBank> lismap = null;

            List<TempRefundBillCorp> lisrefundmap = null;

            List<InterestDetails> lispayInterest = null;

            List<Timestamp> list2 = new ArrayList<Timestamp>();

            List<Timestamp> list3 = new ArrayList<Timestamp>();

            List<Timestamp> list4 = new ArrayList<Timestamp>();

            List<Timestamp> list5 = new ArrayList<Timestamp>();

            List<Timestamp> list6 = new ArrayList<Timestamp>();

            List<Timestamp> list7 = new ArrayList<Timestamp>();

            List<Timestamp> list8 = new ArrayList<Timestamp>();

            List<Timestamp> list9 = new ArrayList<Timestamp>();

            List<BigDecimal> listreptduniqueids = new ArrayList<BigDecimal>();
            List<BigDecimal> listreptdisburseids = new ArrayList<BigDecimal>();
            List<TempRefundBillCorp> temprefund = null;
            List<InterestDetails> tempintdetlis = null;
            List<PaymentDisbursement> listpaydiburse = null;
            List<MappingRefundBank> maprefundbank = null;
            List<Corporates> corplist = null;
            CorporatesDAO corpdao = new CorporatesDAO();
            PaymentDisbursementDAO paydisdao = new PaymentDisbursementDAO();
            MappingRefundBankDAO mapprefundbankdao = new MappingRefundBankDAO();
            MappingInterestBankDAO mapintbankdao = new MappingInterestBankDAO();
            TempBillAGCDetailsDAO tempbillagcdetailsdao = new TempBillAGCDetailsDAO();
            TempBillDSMDetailsDAO tempbilldsmdetailsdao = new TempBillDSMDetailsDAO();
            TempBillFRASDetailsDAO tempbillfrasdetailsdao = new TempBillFRASDetailsDAO();
            TempBillRRASDetailsDAO tempbillrrasdetailsdao = new TempBillRRASDetailsDAO();
            ReconciliationCropDAO reconcorpdao = new ReconciliationCropDAO();
            List<Corporates> corporateList = corporatedao.Corporateslist();

            int z = Calendar.getInstance().get(Calendar.YEAR);
            int agcyear = tempbillagcdetailsdao.getminyearofagcbillsbygivenyear(z);
            int dsmyear = tempbilldsmdetailsdao.getminyearofdsmbillsbygivenyear(z);
            int frasyear = tempbillfrasdetailsdao.getminyearoffrasbillsbygivenyear(z);
            int rrasyear = tempbillrrasdetailsdao.getminyearofrrasbillsbygivenyear(z);
            int minyear = z;

            int allyears[] = {agcyear, dsmyear, frasyear, rrasyear};
//             int allyears[] = {2011, 2025, 2019, 1992};
            for (int j : allyears) {
                if (j < minyear) {
                    minyear = j;
                }

            }
            System.out.println("minyear is " + minyear);
            minyear = minyear - 1;
            for (int yeari = minyear; yeari <= z; yeari++) {

                for (Corporates allcorporatelist : corporateList) {

                    List<Timestamp> listnew = new ArrayList<Timestamp>();

                    BigDecimal outstanding = BigDecimal.ZERO;
                    OutstandingYearCorpDAO outstandyearcorpdao = new OutstandingYearCorpDAO();

                    String corpID = Integer.toString(allcorporatelist.getCorporateId());
//                    String corpID ="9";
                    Corporates corp = new Corporates();
                    corp.setCorporateId(allcorporatelist.getCorporateId());
//                    corp.setCorporateId(Integer.parseInt(corpID));
                    List<BigDecimal> outstandlist = outstandyearcorpdao.getoutstandingbycorpidbyyear(yeari, Integer.parseInt(corpID));
                    if (outstandlist != null && !(outstandlist.isEmpty())) {
                        outstanding = outstandlist.get(0);
                    }
                    String corpname = corporatedao.geCorpNamebyId(Integer.parseInt(corpID));

                    reconcorpdao.deleteReconciliationCorpbycorpid(Integer.parseInt(corpID), Integer.toString(yeari));

                    list2 = mapbilldao.getMappingBillBankbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);

                    list3 = bankstdao.getBankStatementbygroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
                    list4 = intedao.getInterestDetailsbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
                    list5 = billpacorpdao.getBillPayablebyCorpgroupbyBillingDateTimestamp(Integer.parseInt(corpID), yeari);
                    list6 = temprefundbill.getRefundbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
                    list7 = billdaorecv.getBillReceivablebyCorpgroupbyBillingDateTimestamp(Integer.parseInt(corpID), yeari);
                    list8 = tempdisintdao.getdisbInterestDetailsbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
                    list9 = mapintbankdao.getInterestmappingbyCorpgroupbyEntryDateTimestamp(Integer.parseInt(corpID), yeari);
//            list8=billdaorecv.getRefundBillReceivablebyCorpgroupbyBillingDateTimestamp(Integer.parseInt(corpID));
                    if (list2 != null && !(list2.isEmpty())) {

                        for (int m = 0; m < list2.size(); m++) {

                            listnew.add(list2.get(m));

                        }

                    }

                    if (list3 != null && !(list3.isEmpty())) {

                        for (int m1 = 0; m1 < list3.size(); m1++) {

                            int flag = 0;

                            for (int y = 0; y < listnew.size(); y++) {

                                if (list3.get(m1).equals(listnew.get(y))) {

                                    flag = 1;

                                }

                            }

                            if (flag == 0) {

                                listnew.add(list3.get(m1));

                            }

                        }

                    }

                    if (list4 != null && !(list4.isEmpty())) {

                        for (int m2 = 0; m2 < list4.size(); m2++) {

                            int flag = 0;

                            for (int y = 0; y < listnew.size(); y++) {

                                if (list4.get(m2).equals(listnew.get(y))) {

                                    flag = 1;

                                }

                            }

                            if (flag == 0) {

                                listnew.add(list4.get(m2));

                            }

                        }

                    }

                    if (list5 != null && !(list5.isEmpty())) {

                        for (int m3 = 0; m3 < list5.size(); m3++) {

                            int flag = 0;

                            for (int y = 0; y < listnew.size(); y++) {

                                if (list5.get(m3).equals(listnew.get(y))) {

                                    flag = 1;

                                }

                            }

                            if (flag == 0) {

                                listnew.add(list5.get(m3));

                            }

                        }

                    }
                    if (list6 != null && !(list6.isEmpty())) {

                        for (int m4 = 0; m4 < list6.size(); m4++) {

                            int flag = 0;

                            for (int y = 0; y < listnew.size(); y++) {

                                if (list6.get(m4).equals(listnew.get(y))) {

                                    flag = 1;

                                }

                            }

                            if (flag == 0) {

                                listnew.add(list6.get(m4));

                            }

                        }

                    }

                    if (list7 != null && !(list7.isEmpty())) {

                        for (int m5 = 0; m5 < list7.size(); m5++) {

                            int flag = 0;

                            for (int y = 0; y < listnew.size(); y++) {

                                if (list7.get(m5).equals(listnew.get(y))) {

                                    flag = 1;

                                }

                            }

                            if (flag == 0) {

                                listnew.add(list7.get(m5));

                            }

                        }

                    }

                    if (list8 != null && !(list8.isEmpty())) {

                        for (int m6 = 0; m6 < list8.size(); m6++) {

                            int flag = 0;

                            for (int y = 0; y < listnew.size(); y++) {

                                if (list8.get(m6).equals(listnew.get(y))) {

                                    flag = 1;

                                }

                            }

                            if (flag == 0) {

                                listnew.add(list8.get(m6));

                            }

                        }

                    }

                    if (list9 != null && !(list9.isEmpty())) {

                        for (int m7 = 0; m7 < list9.size(); m7++) {

                            int flag = 0;

                            for (int y = 0; y < listnew.size(); y++) {

                                if (list9.get(m7).equals(listnew.get(y))) {

                                    flag = 1;

                                }

                            }

                            if (flag == 0) {

                                listnew.add(list9.get(m7));

                            }

                        }

                    }

                    Collections.sort(listnew);

                    listbillpay = billpacorpdao.getReconPendingBillCorpNameList(Integer.parseInt(corpID), Integer.toString(yeari));
                    listbillrec = billdaorecv.getReconPendingBillCorpNameList(Integer.parseInt(corpID), Integer.toString(yeari));

//            listrefundbillpay=billpacorpdao.getReconrefundPendingBillCorpNameList(Integer.parseInt(corpID));
//            listrefundbillrec= billdaorecv.getReconrefundPendingBillCorpNameList(Integer.parseInt(corpID));
                    listbank = bankstdao.BankStatementlistbyDR(Integer.parseInt(corpID), Integer.toString(yeari));
                    //    System.out.println("listbank is "+listbank.size());
//                    listpaydiburse = paydisdao.getDisbursementDetailsbyCorpStatus("Confirm", Integer.parseInt(corpID));
//                    System.out.println("listpaydiburse is " + listpaydiburse.size());
                    lismap = mapbilldao.getMappingBillBankDetailsbyCorpID(Integer.parseInt(corpID), Integer.toString(yeari));
//                    temprefund = temprefundbill.getAllRefundPayabledataTempRefundBillCorp(Integer.parseInt(corpID));
//                lispayInterest = intedao.getInterestPayableDetailsbyCorpforRecon(Integer.parseInt(corpID));
                    maprefundbank = mapprefundbankdao.getMappingRefundbyCorpID(Integer.parseInt(corpID), Integer.toString(yeari));
                    tempintdetlis = tempintdet.getTempInterestDetailsbyonlyCorpid(Integer.parseInt(corpID), Integer.toString(yeari));
                    mapintbnklst = mapintbnkdao.getMappingInterestBankbyonlyCorpID(Integer.parseInt(corpID), Integer.toString(yeari));
                    corplist = corpdao.getCorporatesbyCorporateId(Integer.parseInt(corpID));

                    tempdisintlist = tempdisintdao.getTempdisbInterestDetailsbyonlyCorpid(Integer.parseInt(corpID), Integer.toString(yeari));

//                    disintdetlis = disintdetdao.getDisbursedInterestDetailsbyonlyCorp(Integer.parseInt(corpID));
                    for (int i = 0; i < listnew.size(); i++) {
                        System.out.println("@@@@@@@@@@@@@@@@@ dateList " + listnew.get(i));
                    }

                    System.out.println("@@@@@@@@@@@@@@@@@size of dateList " + listnew.size());

                    Date d = new Date();

                    SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
                    if (listnew != null && !(listnew.isEmpty())) {
                        for (Timestamp grpdate1 : listnew) {
                            System.out.println("Inside datelist");
                            if (listbillpay != null && !(listbillpay.isEmpty())) {
                                for (BillPayableCorp billpay : listbillpay) {
//                                    System.out.println("Inside for loop billpay");

//                            reconcorp = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(billpay.getBillingDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                    if (billpay.getBillingDate().equals(grpdate1)) {
                                        System.out.println("Inside if  billpay");
                                        ReconciliationCorp reconcorp = new ReconciliationCorp();
                                        BigDecimal slno = new BigDecimal(0);
                                        slno = new BigDecimal(reconcorpdao.getMaxslno());
                                        System.out.println("slno is" + slno);
                                        slno = slno.add(BigDecimal.ONE);
                                        reconcorp.setSlno(slno);
                                        reconcorp.setCorporates(corp);
                                        reconcorp.setWeekId(billpay.getWeekId());
                                        reconcorp.setBillEntryDate(billpay.getBillingDate());
                                        reconcorp.setBillType(billpay.getBillType());
                                        reconcorp.setBillingDate(billpay.getBillingDate());
                                        reconcorp.setRevisionNo(billpay.getRevisionNo());
                                        reconcorp.setBillDueDate(billpay.getBillDueDate());
                                        reconcorp.setPayTotalnet(billpay.getTotalnet());
                                        reconcorp.setEntryDate(d);
                                        reconcorp.setBillYear(billpay.getBillYear());
                                        if (billpay.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {
                                            reconcorp.setPayFinalamount(billpay.getTotalnet());
                                            reconcorp.setPayPendingamount(billpay.getTotalnet());
                                            outstanding = billpay.getTotalnet().add(outstanding);
                                            reconcorp.setOutstandingAmount(outstanding);
                                            reconcorp.setRemarks("Payable Pending");
                                        }
                                        if (billpay.getRevisionNo().compareTo(BigDecimal.ZERO) == 1) {

                                            if (billpay.getBillStatus().equals("REFUND")) {
                                                reconcorp.setRecFinalamount(billpay.getRevisedrefund());
                                                reconcorp.setRecPendingamount(billpay.getRevisedrefund());
                                                outstanding = outstanding.subtract(billpay.getRevisedrefund());
                                                reconcorp.setOutstandingAmount(outstanding);
                                                reconcorp.setRemarks("Payable Rev Refund");
                                            } else {
                                                reconcorp.setPayFinalamount(billpay.getRevisedpaybale());
                                                reconcorp.setPayPendingamount(billpay.getRevisedpaybale());
                                                outstanding = outstanding.add(billpay.getRevisedpaybale());
                                                reconcorp.setOutstandingAmount(outstanding);
                                                reconcorp.setRemarks("Payable Rev Pending");
                                            }
                                        }
                                        reconcorpdao.NewReconciliationCorp(reconcorp);
                                    }
                                }
                            }
                            if (listbillrec != null && !(listbillrec.isEmpty())) {
                                for (BillReceiveCorp billrec : listbillrec) {
//                                    System.out.println("Inside for  billrec");
//                            reconcorp = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(billrec.getBillingDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                    if (billrec.getBillingDate().equals(grpdate1)) {
                                        ReconciliationCorp reconcorp = new ReconciliationCorp();
                                        System.out.println("Inside if  billrec");
                                        System.out.println("billrec entry date is =" + billrec.getBillingDate() + "grpdate is =" + grpdate1);

                                        BigDecimal slno = new BigDecimal(0);
                                        slno = new BigDecimal(reconcorpdao.getMaxslno());
                                        slno = slno.add(BigDecimal.ONE);
                                        reconcorp.setSlno(slno);
                                        reconcorp.setCorporates(corp);
                                        reconcorp.setWeekId(billrec.getWeekId());
                                        reconcorp.setBillEntryDate(billrec.getBillingDate());
                                        reconcorp.setBillType(billrec.getBillType());
                                        reconcorp.setBillingDate(billrec.getBillingDate());
                                        reconcorp.setRevisionNo(billrec.getRevisionNo());
                                        reconcorp.setBillDueDate(billrec.getBillDueDate());
                                        reconcorp.setRecTotalnet(billrec.getToalnet());
                                        reconcorp.setEntryDate(d);
                                        reconcorp.setBillYear(billrec.getBillYear());
                                        if (billrec.getRevisionNo().compareTo(BigDecimal.ZERO) == 0) {

                                            reconcorp.setRecFinalamount(billrec.getToalnet());
                                            reconcorp.setRecPendingamount(billrec.getToalnet());
                                            outstanding = outstanding.subtract(billrec.getToalnet());
                                            reconcorp.setOutstandingAmount(outstanding);
                                            reconcorp.setRemarks("Receivable Pending");
                                        }
                                        if (billrec.getRevisionNo().compareTo(BigDecimal.ZERO) == 1) {

                                            if (billrec.getDisburseStatus().equals("REFUND")) {
                                                reconcorp.setPayFinalamount(billrec.getRevisedrefund());
                                                reconcorp.setPayPendingamount(billrec.getRevisedrefund());
                                                outstanding = outstanding.add(billrec.getRevisedrefund());
                                                reconcorp.setOutstandingAmount(outstanding);
                                                reconcorp.setRemarks("Receivable Rev Refund");

                                            } else {
                                                reconcorp.setRecFinalamount(billrec.getRevisedpaybale());
                                                reconcorp.setRecPendingamount(billrec.getRevisedpaybale());
                                                outstanding = outstanding.subtract(billrec.getRevisedpaybale());
                                                reconcorp.setOutstandingAmount(outstanding);
                                                reconcorp.setRemarks("Receivable Rev Pending");
                                            }
                                        }
                                        reconcorpdao.NewReconciliationCorp(reconcorp);
                                    }

                                }
                            }
                            if (lismap != null && !(lismap.isEmpty())) {
                                for (MappingBillBank ele1 : lismap) {
//                                    System.out.println("lismap entry date is =" + ele1.getEntryDate() + "grpdate is =" + grpdate1);
//                            reconcorp = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(ele1.getEntryDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                    if (ele1.getEntryDate().equals(grpdate1)) {
                                        System.out.println("Inside if  lismap");
                                        ReconciliationCorp reconcorp = new ReconciliationCorp();
                                        BigDecimal slno = new BigDecimal(0);
                                        slno = new BigDecimal(reconcorpdao.getMaxslno());
                                        slno = slno.add(BigDecimal.ONE);
                                        reconcorp.setSlno(slno);
                                        reconcorp.setCorporates(corp);
                                        reconcorp.setWeekId(ele1.getBillPayableCorp().getWeekId());
                                        reconcorp.setBillEntryDate(ele1.getEntryDate());
                                        reconcorp.setBillType(ele1.getBillPayableCorp().getBillType());
                                        reconcorp.setBillingDate(ele1.getBillPayableCorp().getBillingDate());
                                        reconcorp.setRevisionNo(ele1.getBillPayableCorp().getRevisionNo());
                                        reconcorp.setBillDueDate(ele1.getBillPayableCorp().getBillDueDate());
                                        reconcorp.setPayTotalnet(ele1.getBillPayableCorp().getTotalnet());
                                        reconcorp.setEntryDate(d);
                                        reconcorp.setBillYear(ele1.getBillPayableCorp().getBillYear());
                                        reconcorp.setPayFinalamount(ele1.getPendingAmount().add(ele1.getMappedAmount()));
                                        reconcorp.setPayPendingamount(ele1.getPendingAmount());
                                        reconcorp.setCrDrDate(ele1.getBankStatement().getAmountDate());
                                        reconcorp.setCrAmount(ele1.getBankStatement().getPaidAmount());
                                        reconcorp.setCrSettledAmount(ele1.getMappedAmount());
                                        reconcorp.setCrAvailable(ele1.getPendingBankAmount());
                                        outstanding = outstanding.subtract(ele1.getMappedAmount());
                                        reconcorp.setOutstandingAmount(outstanding);
                                        reconcorp.setRemarks("payable Mapping");
                                        reconcorpdao.NewReconciliationCorp(reconcorp);
                                    }
                                }
                            }
                            if (maprefundbank != null && !(maprefundbank.isEmpty())) {
                                for (MappingRefundBank eleref : maprefundbank) {
//                            reconcorp = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(eleref.getTempRefundBillCorp().getRefundDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                    if (eleref.getTempRefundBillCorp().getCheckerDate().equals(grpdate1)) {
                                        ReconciliationCorp reconcorp = new ReconciliationCorp();
                                        BigDecimal slno = new BigDecimal(0);
                                        slno = new BigDecimal(reconcorpdao.getMaxslno());
                                        slno = slno.add(BigDecimal.ONE);
                                        reconcorp.setSlno(slno);
                                        reconcorp.setCorporates(corp);
                                        reconcorp.setWeekId(eleref.getTempRefundBillCorp().getBillReceiveCorp().getWeekId());
                                        reconcorp.setBillEntryDate(eleref.getTempRefundBillCorp().getRefundDate());
                                        reconcorp.setBillType(eleref.getTempRefundBillCorp().getBillReceiveCorp().getBillType());
                                        reconcorp.setBillingDate(eleref.getTempRefundBillCorp().getBillReceiveCorp().getBillingDate());
                                        reconcorp.setRevisionNo(eleref.getTempRefundBillCorp().getBillReceiveCorp().getRevisionNo());
                                        reconcorp.setBillDueDate(eleref.getTempRefundBillCorp().getBillReceiveCorp().getBillDueDate());
                                        reconcorp.setRecTotalnet(eleref.getTempRefundBillCorp().getBillReceiveCorp().getToalnet());
                                        reconcorp.setEntryDate(d);
                                        reconcorp.setBillYear(eleref.getTempRefundBillCorp().getBillReceiveCorp().getBillYear());
                                        reconcorp.setPayFinalamount(eleref.getTempRefundBillCorp().getPendingAmount().add(eleref.getTempRefundBillCorp().getPaidAmount()));
                                        reconcorp.setPayPendingamount(eleref.getTempRefundBillCorp().getPendingAmount());
                                        reconcorp.setCrDrDate(eleref.getBankStatement().getAmountDate());
                                        reconcorp.setCrAmount(eleref.getBankStatement().getPaidAmount());
                                        reconcorp.setCrSettledAmount(eleref.getMappedAmount());
                                        reconcorp.setCrAvailable(eleref.getBankPendingAmount());
                                        outstanding = outstanding.subtract(eleref.getTempRefundBillCorp().getPaidAmount());
                                        reconcorp.setOutstandingAmount(outstanding);
                                        reconcorp.setRemarks("Refund Mapping");
                                        reconcorpdao.NewReconciliationCorp(reconcorp);
                                    }
                                }
                            }

                            if (listbank != null && !(listbank.isEmpty())) {
                                for (BankStatement bank : listbank) {
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(bank.getAmountDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                        if (bank.getAmountDate().equals(grpdate1)) {
                                        System.out.println("Inside for  bank dates");
//                                        if (listpaydiburse != null && !(listpaydiburse.isEmpty())) {
//                                            for (PaymentDisbursement paydis : listpaydiburse) {
//                                        reconcorp = null;
                                        if (bank.getDisburseType().equals("Bills")) {
                                            System.out.println("Inside for  bank Bills");
                                            listpaydiburse = paydisdao.getDisbursementDetailsbyDisburseID(bank.getDisburseId().intValue());
                                            if (listpaydiburse != null && !(listpaydiburse.isEmpty())) {
                                                System.out.println("Inside for  bank bill print");
                                                PaymentDisbursement paydis = new PaymentDisbursement();
                                                paydis = listpaydiburse.get(0);
                                                ReconciliationCorp reconcorp = new ReconciliationCorp();
                                                BigDecimal slno = new BigDecimal(0);
                                                slno = new BigDecimal(reconcorpdao.getMaxslno());
                                                slno = slno.add(BigDecimal.ONE);
                                                reconcorp.setSlno(slno);
                                                reconcorp.setCorporates(corp);
                                                reconcorp.setWeekId(paydis.getWeekId());
                                                reconcorp.setBillEntryDate(bank.getAmountDate());
                                                reconcorp.setBillType(paydis.getBillReceiveCorp().getBillType());
                                                reconcorp.setBillingDate(paydis.getBillingDate());
                                                reconcorp.setRevisionNo(paydis.getBillReceiveCorp().getRevisionNo());
                                                reconcorp.setBillDueDate(paydis.getBillDueDate());
                                                reconcorp.setRecTotalnet(paydis.getBillReceiveCorp().getToalnet());
                                                reconcorp.setEntryDate(d);
                                                reconcorp.setBillYear(paydis.getBillReceiveCorp().getBillYear());
                                                reconcorp.setRecFinalamount(paydis.getTotalAmount());
                                                reconcorp.setRecPendingamount(paydis.getPendingAmount());
                                                reconcorp.setCrDrDate(bank.getAmountDate());
                                                reconcorp.setDrAmount(bank.getPaidAmount());
                                                reconcorp.setDrSettledAmount(paydis.getDisburseAmount());
                                                outstanding = outstanding.add(paydis.getDisburseAmount());
                                                reconcorp.setOutstandingAmount(outstanding);
                                                reconcorp.setRemarks("Receivable Disbursed");
                                                reconcorpdao.NewReconciliationCorp(reconcorp);
                                            }
                                        }
//                                            }
//                                        }
//                                        if (temprefund != null && !(temprefund.isEmpty())) {
//                                            for (TempRefundBillCorp temprefundvar : temprefund) {
//                                        reconcorp = null;
                                        if (bank.getDisburseType().equals("Refund")) {
                                            temprefund = temprefundbill.getRefundDetailsbyDisburseID(bank.getDisburseId());
                                            if (temprefund != null && !(temprefund.isEmpty())) {
                                                TempRefundBillCorp temprefundvar = new TempRefundBillCorp();
                                                temprefundvar = temprefund.get(0);
                                                ReconciliationCorp reconcorp = new ReconciliationCorp();

                                                BigDecimal slno = new BigDecimal(0);
                                                slno = new BigDecimal(reconcorpdao.getMaxslno());
                                                slno = slno.add(BigDecimal.ONE);
                                                reconcorp.setSlno(slno);
                                                reconcorp.setCorporates(corp);
                                                reconcorp.setWeekId(temprefundvar.getWeekid());
                                                reconcorp.setBillEntryDate(bank.getAmountDate());
                                                reconcorp.setBillType(temprefundvar.getBillPayableCorp().getBillType());
                                                reconcorp.setBillingDate(temprefundvar.getBillPayableCorp().getBillingDate());
                                                reconcorp.setRevisionNo(temprefundvar.getBillPayableCorp().getRevisionNo());
                                                reconcorp.setBillDueDate(temprefundvar.getBillPayableCorp().getBillDueDate());
                                                reconcorp.setPayTotalnet(temprefundvar.getBillPayableCorp().getTotalnet());
                                                reconcorp.setEntryDate(d);
                                                reconcorp.setBillYear(temprefundvar.getBillPayableCorp().getBillYear());
                                                reconcorp.setRecFinalamount(temprefundvar.getBillPayableCorp().getRevisedrefund());
                                                reconcorp.setRecPendingamount(temprefundvar.getPendingAmount());
                                                reconcorp.setCrDrDate(bank.getAmountDate());
                                                reconcorp.setDrAmount(bank.getPaidAmount());
                                                reconcorp.setDrSettledAmount(temprefundvar.getPaidAmount());
                                                outstanding = outstanding.add(temprefundvar.getPaidAmount());
                                                reconcorp.setOutstandingAmount(outstanding);
                                                reconcorp.setRemarks("Refund Disbursed");
                                                reconcorpdao.NewReconciliationCorp(reconcorp);
                                            }
                                        }
//                                            }
//                                        }

                                    }
                                }
                            }
                            if (tempintdetlis != null && !(tempintdetlis.isEmpty())) {
                                for (InterestDetails eleint : tempintdetlis) {
//                            reconcorp = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(eleint.getEntryDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                    if (eleint.getEntryDate().equals(grpdate1)) {
                                        ReconciliationCorp reconcorp = new ReconciliationCorp();
                                        BigDecimal slno = new BigDecimal(0);
                                        slno = new BigDecimal(reconcorpdao.getMaxslno());
                                        slno = slno.add(BigDecimal.ONE);
                                        reconcorp.setSlno(slno);
                                        reconcorp.setCorporates(corp);
                                        reconcorp.setWeekId(eleint.getWeekId());
                                        reconcorp.setBillEntryDate(eleint.getEntryDate());
                                        reconcorp.setBillType(eleint.getBillType());
                                        reconcorp.setBillingDate(eleint.getBillingDate());
                                        reconcorp.setRevisionNo(eleint.getRevisionNo());
                                        reconcorp.setBillDueDate(eleint.getBillingDuedate());
                                        reconcorp.setPayTotalnet(eleint.getInterestAmount());
                                        reconcorp.setEntryDate(d);
                                        Date dateyear = eleint.getBillingDate();
                                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                        cal.setTime(dateyear);
                                        int year = cal.get(Calendar.YEAR);
                                        reconcorp.setBillYear(eleint.getBillYear());
                                        reconcorp.setPayFinalamount(eleint.getInterestAmount());
                                        reconcorp.setPayPendingamount(eleint.getInterestAmount());
                                        outstanding = eleint.getInterestAmount().add(outstanding);
                                        reconcorp.setOutstandingAmount(outstanding);
                                        reconcorp.setRemarks("Interest Pending");
                                        reconcorpdao.NewReconciliationCorp(reconcorp);
                                    }
                                }
                            }
                            if (mapintbnklst != null && !(mapintbnklst.isEmpty())) {
                                for (MappingInterestBank elemapint : mapintbnklst) {
//                            reconcorp = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(elemapint.getEntryDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                    if (elemapint.getEntryDate().equals(grpdate1)) {
                                        ReconciliationCorp reconcorp = new ReconciliationCorp();
                                        BigDecimal slno = new BigDecimal(0);
                                        slno = new BigDecimal(reconcorpdao.getMaxslno());
                                        slno = slno.add(BigDecimal.ONE);
                                        reconcorp.setSlno(slno);
                                        reconcorp.setCorporates(corp);
                                        reconcorp.setWeekId(elemapint.getInterestDetails().getWeekId());
                                        reconcorp.setBillEntryDate(elemapint.getEntryDate());
                                        reconcorp.setBillType(elemapint.getInterestDetails().getBillType());
                                        reconcorp.setBillingDate(elemapint.getInterestDetails().getBillingDate());
                                        reconcorp.setRevisionNo(elemapint.getInterestDetails().getRevisionNo());
                                        reconcorp.setBillDueDate(elemapint.getInterestDetails().getBillingDuedate());
                                        reconcorp.setPayTotalnet(elemapint.getInterestDetails().getInterestAmount());
                                        reconcorp.setEntryDate(d);
                                        Date dateyear = elemapint.getInterestDetails().getBillingDate();
                                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                        cal.setTime(dateyear);
                                        int year = cal.get(Calendar.YEAR);
                                        reconcorp.setBillYear(elemapint.getInterestDetails().getBillYear());
                                        reconcorp.setPayFinalamount(elemapint.getMappedAmount().add(elemapint.getPendingAmount()));
                                        reconcorp.setPayPendingamount(elemapint.getPendingAmount());
                                        reconcorp.setCrDrDate(elemapint.getBankStatement().getAmountDate());
                                        reconcorp.setCrAmount(elemapint.getBankStatement().getPaidAmount());
                                        reconcorp.setCrSettledAmount(elemapint.getMappedAmount());
                                        reconcorp.setCrAvailable(elemapint.getBankPendingAmount());
                                        outstanding = outstanding.subtract(elemapint.getMappedAmount());
                                        reconcorp.setOutstandingAmount(outstanding);
                                        reconcorp.setRemarks("Interest Mapping");
                                        reconcorpdao.NewReconciliationCorp(reconcorp);
                                    }
                                }
                            }
                            if (tempdisintlist != null && !(tempdisintlist.isEmpty())) {
                                for (DisbursedInterestDetails eleintdis : tempdisintlist) {
//                            reconcorp = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(eleintdis.getEntryDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                        if (eleintdis.getEntryDate().equals(grpdate1)) {
                                        ReconciliationCorp reconcorp = new ReconciliationCorp();
                                        BigDecimal slno = new BigDecimal(0);
                                        slno = new BigDecimal(reconcorpdao.getMaxslno());
                                        slno = slno.add(BigDecimal.ONE);
                                        reconcorp.setSlno(slno);
                                        reconcorp.setCorporates(corp);
                                        reconcorp.setWeekId(eleintdis.getWeekId());
                                        reconcorp.setBillEntryDate(eleintdis.getBillingDate());
                                        reconcorp.setBillType(eleintdis.getBillType());
                                        reconcorp.setBillingDate(eleintdis.getBillingDate());
                                        reconcorp.setRevisionNo(eleintdis.getRevisionNo());
                                        reconcorp.setBillDueDate(eleintdis.getBillingDuedate());
                                        reconcorp.setRecTotalnet(eleintdis.getInterestAmount());
                                        reconcorp.setEntryDate(d);
                                        Date dateyear = eleintdis.getBillingDate();
                                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                        cal.setTime(dateyear);
                                        int year = cal.get(Calendar.YEAR);
                                        reconcorp.setBillYear(eleintdis.getBillYear());
                                        reconcorp.setRecFinalamount(eleintdis.getInterestAmount());
                                        reconcorp.setRecPendingamount(eleintdis.getInterestAmount());
                                        outstanding = outstanding.subtract(eleintdis.getInterestAmount());
                                        reconcorp.setOutstandingAmount(outstanding);
                                        reconcorp.setRemarks("Disbursed Interest Pending");
                                        reconcorpdao.NewReconciliationCorp(reconcorp);
                                    }
                                }
                            }
                            if (listbank != null && !(listbank.isEmpty())) {
                                for (BankStatement bank : listbank) {
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    String date1String = ft.format(grpdate1);
                                    String date2String = ft.format(bank.getAmountDate());

                                    Date date1 = ft.parse(date1String);
                                    Date date2 = ft.parse(date2String);
                                    if (date1.equals(date2)) {
//                                    if (bank.getAmountDate().equals(grpdate1)) {
//                                        if (disintdetlis != null && !(disintdetlis.isEmpty())) {
//                                            for (PaymentInterestDisbursement paydisint : disintdetlis) {
//                                        reconcorp = null;
                                        if (bank.getDisburseType().equals("Interest")) {

                                            disintdetlis = disintdetdao.getPaymentInterestdisbursementbyslno(bank.getDisburseId());
                                            if (disintdetlis != null && !(disintdetlis.isEmpty())) {

                                                PaymentInterestDisbursement paydisint = new PaymentInterestDisbursement();
                                                ReconciliationCorp reconcorp = new ReconciliationCorp();
                                                paydisint = disintdetlis.get(0);

                                                BigDecimal slno = new BigDecimal(0);
                                                slno = new BigDecimal(reconcorpdao.getMaxslno());
                                                slno = slno.add(BigDecimal.ONE);
                                                reconcorp.setSlno(slno);
                                                reconcorp.setCorporates(corp);
                                                reconcorp.setWeekId(paydisint.getDisbursedInterestDetails().getWeekId());
                                                reconcorp.setBillEntryDate(bank.getAmountDate());
                                                reconcorp.setBillType(paydisint.getDisbursedInterestDetails().getBillType());
                                                reconcorp.setBillingDate(paydisint.getDisbursedInterestDetails().getBillingDate());
                                                reconcorp.setRevisionNo(paydisint.getDisbursedInterestDetails().getRevisionNo());
                                                reconcorp.setBillDueDate(paydisint.getDisbursedInterestDetails().getBillingDuedate());
                                                reconcorp.setRecTotalnet(paydisint.getDisbursedInterestDetails().getInterestAmount());
                                                reconcorp.setEntryDate(d);
                                                Date dateyear = paydisint.getDisbursedInterestDetails().getBillingDate();
                                                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                                cal.setTime(dateyear);
                                                int year = cal.get(Calendar.YEAR);
                                                reconcorp.setBillYear(paydisint.getDisbursedInterestDetails().getBillYear());
                                                reconcorp.setRecFinalamount(paydisint.getDisbursedInterestDetails().getInterestAmount());
                                                reconcorp.setRecPendingamount(paydisint.getInterestPendingamount());
                                                reconcorp.setCrDrDate(bank.getAmountDate());
                                                reconcorp.setDrAmount(bank.getPaidAmount());
                                                reconcorp.setDrSettledAmount(paydisint.getInterestPaidamount());
                                                outstanding = outstanding.add(paydisint.getInterestPaidamount());
                                                reconcorp.setOutstandingAmount(outstanding);
                                                reconcorp.setRemarks("Disbursed Interest");
                                                reconcorpdao.NewReconciliationCorp(reconcorp);
                                            }
                                        }
//                                            }
//                                        }
                                    }
                                }
                            }
                        }
                    }
//getoutst
                    List<OutstandingYearCorp> OutstandingYearCorplist = outstandyearcorpdao.getcheckstatusAllOutstandingYearCorp(new BigDecimal(yeari + 1), Integer.parseInt(corpID));
                    if (OutstandingYearCorplist != null && !(OutstandingYearCorplist.isEmpty())) {
                        outstandyearcorpdao.updateoutstandbycorpbyyear(yeari + 1, Integer.parseInt(corpID), outstanding);
                    } else {
                        OutstandingYearCorp outstandyearcorp = new OutstandingYearCorp();
                        BigDecimal slno = new BigDecimal(0);
                        slno = new BigDecimal(outstandyearcorpdao.getMaxslno());
                        slno = slno.add(BigDecimal.ONE);
                        outstandyearcorp.setSlno(slno);
                        outstandyearcorp.setBillYear(new BigDecimal(yeari + 1));
                        outstandyearcorp.setCorporates(corp);
                        outstandyearcorp.setOutStanding(outstanding);
                        outstandyearcorp.setEntryDate(d);
                        outstandyearcorp.setRemarks("updated");
                        outstandyearcorpdao.NewOutstandingYearCorp(outstandyearcorp);
                    }
                    System.out.println("@@@@@@@@@@@@@@@@@ outstanding final  " + outstanding);
                }
            }

//            int countrowinrecontable = reconcorpdao.getcountslno();
            List<ReconciliationCorp> reconcorp1 = reconcorpdao.getFullReconreport();
            int r = 1;
            for (ReconciliationCorp e3 : reconcorp1) {
                reconcorpdao.getUpdateslnoforrecontable(e3.getSlno().intValue(), r);
                r++;
            }

            System.out.println("$$$$$$$$$$...........Successfully deleted and stored new...........$$$$$$$$$");
            ModelAndView mv1 = new ModelAndView("downloadreconsheet");

            return mv1;
        }
        return mv;
    }

    public ModelAndView Exceldownloadreconreport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bName");
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            ReconciliationCropDAO reconcorpdao = new ReconciliationCropDAO();
            List<ReconciliationCorp> reconlist = null;
            reconlist = reconcorpdao.getReconreportByCorpAnddatesYear(startDate, endDate);
            if (reconlist != null && !(reconlist.isEmpty())) {

                List<String> corpnamesinrecon = new ArrayList<String>();
                for (ReconciliationCorp reconcorp : reconlist) {
//                    System.out.println("corp id is =" + reconcorp.getCorporates().getCorporateId());

                    int flag = 0;
                    if (corpnamesinrecon != null) {
                        for (int y = 0; y < corpnamesinrecon.size(); y++) {
                            if (reconcorp.getCorporates().getCorporateName().equalsIgnoreCase(corpnamesinrecon.get(y))) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            corpnamesinrecon.add(reconcorp.getCorporates().getCorporateName());
                        }
                    } else {
                        corpnamesinrecon.add(reconcorp.getCorporates().getCorporateName());
                    }

                }
                Collections.sort(corpnamesinrecon);
//                String[] columns = {"SLNO", "BILL_ENTRY_DATE", "ENTRY_DATE", "CORP_NAME", "WEEK_ID", "BILL_TYPE", "BILLING_DATE", "REVISION_NO", "BILL_YEAR", "BILL_DUE_DATE", "PAY_TOTALNET", "REC_TOTALNET", "PAY_FINALAMOUNT", "REC_FINALAMOUNT", "PAY_PENDINGAMOUNT", "REC_PENDINGAMOUNT", "CR_DR_DATE", "CR_AMOUNT", "CR_SETTLED_AMOUNT", "CR_AVAILABLE", "DR_AMOUNT", "DR_SETTLED_AMOUNT", "OUTSTANDING_AMOUNT", "REMARKS"};
                String[] columns = {"SLNO", "Event_DATE", "Recon Run Date", "CORP_NAME", "WEEK_ID", "BILL_TYPE", "BILL Issue DATE", "REVISION_NO", "BILL_YEAR", "Due + Grace Period", "Payable asper Bill", "Receivable as per Bill", "Pay. Difference wrt Rev.", "Rec. Difference wrt Rev.", "Payable Pending", "Receivable Pending", "Bank CR/DR DATE", "CR_AMOUNT", "CR AMOUNT Settled", "CR_AVAILABLE after settled", "DR_AMOUNT", "DR AMOUNT Settled", "OUTSTANDING_AMOUNT (- Receivable / + Payable)", "REMARKS"};

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = null;
                Row headerRow = null;
                for (int i = 0; i < corpnamesinrecon.size(); i++) {
                    sheet = workbook.createSheet(corpnamesinrecon.get(i));
                    headerRow = sheet.createRow(0);
                    Cell cell = null;
                    for (int j = 0; j < columns.length; j++) {
                        cell = headerRow.createCell(j);
                        cell.setCellValue(columns[j]);
                    }
                    System.out.print("corp name  is" + corpnamesinrecon.get(i));
                    int rowflag = 0;
                    for (ReconciliationCorp reconcorp : reconlist) {
                        if (corpnamesinrecon.get(i).equalsIgnoreCase(reconcorp.getCorporates().getCorporateName())) {
                            rowflag = sheet.getLastRowNum();
                            rowflag = rowflag + 1;
                            System.out.print("Last RowNum  is" + rowflag);
                            headerRow = sheet.createRow(rowflag);
                            for (int j = 0; j < columns.length; j++) {
                                switch (j) {

                                    case 0:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(rowflag);
                                        break;
                                    case 1:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillEntryDate().toString());
                                        break;
                                    case 2:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getEntryDate().toString());
                                        break;
                                    case 3:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getCorporates().getCorporateName());
                                        break;
                                    case 4:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getWeekId().toString());
                                        break;
                                    case 5:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillType());
                                        break;
                                    case 6:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillingDate().toString());
                                        break;
                                    case 7:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getRevisionNo().toString());
                                        break;
                                    case 8:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillYear().toString());
                                        break;
                                    case 9:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillDueDate().toString());
                                        break;
                                    case 10:
                                        cell = headerRow.createCell(j);
                                        if (reconcorp.getPayTotalnet() != null) {
                                            cell.setCellValue(reconcorp.getPayTotalnet().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 11:
                                        cell = headerRow.createCell(j);
                                        if (reconcorp.getRecTotalnet() != null) {
                                            cell.setCellValue(reconcorp.getRecTotalnet().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 12:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getPayFinalamount() != null) {
                                            cell.setCellValue(reconcorp.getPayFinalamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 13:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getRecFinalamount() != null) {
                                            cell.setCellValue(reconcorp.getRecFinalamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 14:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getPayPendingamount() != null) {
                                            cell.setCellValue(reconcorp.getPayPendingamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 15:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getRecPendingamount() != null) {
                                            cell.setCellValue(reconcorp.getRecPendingamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 16:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrDrDate() != null) {
                                            cell.setCellValue(reconcorp.getCrDrDate().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 17:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrAmount() != null) {
                                            cell.setCellValue(reconcorp.getCrAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 18:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrSettledAmount() != null) {
                                            cell.setCellValue(reconcorp.getCrSettledAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 19:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrAvailable() != null) {
                                            cell.setCellValue(reconcorp.getCrAvailable().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 20:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getDrAmount() != null) {
                                            cell.setCellValue(reconcorp.getDrAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 21:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getDrSettledAmount() != null) {
                                            cell.setCellValue(reconcorp.getDrSettledAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 22:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getOutstandingAmount().toString());
                                        break;
                                    case 23:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getRemarks());
                                        break;

                                }//end of switvch
                            }//end of for cloumn loop
                        }//if compare names

                    }//recon list forloop

                }//for corpname loop
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String filedate = simpleDateFormat.format(new Date());
                String filename = "Reconciliation_report_by_" + filedate + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
                workbook.close();
                return null;
            } else {
                ModelAndView mv = new ModelAndView("successMsg");
                mv.addObject("Msg", "List is empty, Click refresh to reload data");
                return mv;

            }

        }
        ModelAndView mv = new ModelAndView("downloadreconsheet");

        return mv;
    }

    public ModelAndView overallmapping(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bName");
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            String type = request.getParameter("TYPE");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            MappingBillBankDAO mapbilldao = new MappingBillBankDAO();
            MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
            MappingRefundBankDAO maprefdao = new MappingRefundBankDAO();
            List<MappingBillBank> mapbillist = null;
            List<MappingInterestBank> mapintlist = null;
            List<MappingRefundBank> mapreflist = null;
            mapbillist = mapbilldao.getMappingBillBankDetailsbydates(startDate, endDate);
            mapreflist = maprefdao.getMappingRefundBankbydates(startDate, endDate);
            mapintlist = mapintdao.getMappingInterestBankbydates(startDate, endDate);
            ModelAndView model = new ModelAndView("overallmappingview");

            model.addObject("mapbillist", mapbillist);
            model.addObject("mapreflist", mapreflist);
            model.addObject("mapintlist", mapintlist);

            return model;
        }
        ModelAndView mv = new ModelAndView("overallmapping");

        return mv;
    }

    public ModelAndView overallmappingnew(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bsubmitbank");
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
//            String type = request.getParameter("TYPE");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            MappingBillBankDAO mapbilldao = new MappingBillBankDAO();
            MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
            MappingRefundBankDAO maprefdao = new MappingRefundBankDAO();
            BankStatementDAO bnkstmtdao = new BankStatementDAO();
            List<BankStatement> bnkstmt = null;
            List<MappingBillBank> mapbillist = null;
            List<MappingInterestBank> mapintlist = null;
            List<MappingRefundBank> mapreflist = null;
//            mapbillist = mapbilldao.getMappingBillBankDetailsbydatestype(startDate, endDate, type);
//            mapreflist = maprefdao.getMappingRefundBankbydatestype(startDate, endDate, type);
//            mapintlist = mapintdao.getMappingInterestBankbydatestype(startDate, endDate, type);
            mapbillist = mapbilldao.getMappingBillBankDetailsbydates(startDate, endDate);
            mapreflist = maprefdao.getMappingRefundBankbydates(startDate, endDate);
            mapintlist = mapintdao.getMappingInterestBankbydates(startDate, endDate);
            bnkstmt = bnkstmtdao.BankStatementnotmappedlist(startDate, endDate);
            BigDecimal openpoolbal = bnkstmtdao.getopenbalBankStmtbyverifieddates(frmDate, toDate);
            BigDecimal sumcramt = bnkstmtdao.getCRSumAmountBankStmtbyverifieddates(frmDate, toDate);
            BigDecimal sumintamt = mapintdao.getSummedMappingInterestBankbydates(startDate, endDate);
            if (sumintamt == null) {
                sumintamt = BigDecimal.ZERO;
            }
            if (openpoolbal == null) {
                sumintamt = BigDecimal.ZERO;
            }
            if (sumcramt == null) {
                sumintamt = BigDecimal.ZERO;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String finclyear = null;
            if (month <= 4) {
                finclyear = Integer.toString(year - 1) + "-" + Integer.toString(year);

            } else {
                finclyear = Integer.toString(year) + "-" + Integer.toString(year + 1);
            }

            System.out.println("finclyear is " + finclyear);
            ModelAndView model = new ModelAndView("overallmappingviewnew");

            model.addObject("mapbillist", mapbillist);
            model.addObject("mapreflist", mapreflist);
            model.addObject("mapintlist", mapintlist);
            model.addObject("bnkstmt", bnkstmt);
            model.addObject("finclyear", finclyear);
//            model.addObject("type", type);
            model.addObject("sumcramt", sumcramt);
            model.addObject("sumintamt", sumintamt);
            model.addObject("openpoolbal", openpoolbal);

            return model;
        }
        ModelAndView mv = new ModelAndView("overallmappingnew");

        return mv;
    }

    public ModelAndView overalldisbursement(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bName");
        String bsubmit = request.getParameter("bsubmit");
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            String type = request.getParameter("TYPE");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            DisbursedInterestDetailsDAO disintdao = new DisbursedInterestDetailsDAO();
            TempRefundBillCorpDAO disrefdao = new TempRefundBillCorpDAO();
            miscDisbursementDAO miscdao = new miscDisbursementDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            List<PaymentDisbursement> disbillist = null;
            List<PaymentDisbursement> disbillistIN = null;
            List<PaymentInterestDisbursement> disintlist = null;
            List<PaymentInterestDisbursement> disintlistIN = null;
            List<TempRefundBillCorp> disreflist = null;
            List<TempRefundBillCorp> disreflistIN = null;
            List<CsdfDetails> psdfdetlist = null;
            List<CsdfDetails> psdfdetlistIN = null;
            List<MiscDisbursement> misclist = null;
            List<MiscDisbursement> misclistIN = null;

            disbillist = disbilldao.getbillDisbursementDetailsbyFromDateTodatetypebyrev(startDate, endDate);
            disreflist = disrefdao.getdisRefundBillPayCorpbydatestypebyrev(startDate, endDate);
            disintlist = disintdao.getDisbursedInterestDetailsbydates(startDate, endDate);
            psdfdetlist = csdfdao.getPSDFdetailsbydates(startDate, endDate);
            misclist = miscdao.getmiscdetailsbydates(startDate, endDate);

            disbillistIN = disbilldao.getbillDisbursementDetailsbyFromDateTodatetypebyrevIN(startDate, endDate);
            disreflistIN = disrefdao.getdisRefundBillPayCorpbydatestypebyrevIN(startDate, endDate);
            disintlistIN = disintdao.getDisbursedInterestDetailsbydatesIN(startDate, endDate);
            psdfdetlistIN = csdfdao.getPSDFdetailsbydatesIN(startDate, endDate);
            misclistIN = miscdao.getMiscdetailsbydatesIN(startDate, endDate);

            ModelAndView model = new ModelAndView("overalldisbursementview");

            model.addObject("disbillist", disbillist);
            model.addObject("disreflist", disreflist);
            model.addObject("disintlist", disintlist);
            model.addObject("psdfdetlist", psdfdetlist);
            model.addObject("disbillistIN", disbillistIN);
            model.addObject("disreflistIN", disreflistIN);
            model.addObject("disintlistIN", disintlistIN);
            model.addObject("psdfdetlistIN", psdfdetlistIN);
            model.addObject("misclist", misclist);
            model.addObject("misclistIN", misclistIN);

            return model;

        }
        if (bsubmit != null) {
            String startbill = request.getParameter("startbill");
            String endbill = request.getParameter("endbill");
            String startref = request.getParameter("startref");
            String endref = request.getParameter("endref");
            String startint = request.getParameter("startint");
            String endint = request.getParameter("endint");
            String startpsdf = request.getParameter("startpsdf");
            String endpsdf = request.getParameter("endpsdf");
            String startmisc = request.getParameter("startmisc");
            String endmisc = request.getParameter("endmisc");
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            DisbursedInterestDetailsDAO disintdao = new DisbursedInterestDetailsDAO();
            TempRefundBillCorpDAO disrefdao = new TempRefundBillCorpDAO();
            InterestPoolAccountDetailsDAO intpoolactdetils = new InterestPoolAccountDetailsDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            miscDisbursementDAO miscdao = new miscDisbursementDAO();

            Date d = new Date();
            int year = d.getYear() + 1900;
            if (d.getMonth() < 4) {
                year = year - 1;
            }

            String finclyear = year + "-" + new Integer(year + 1);

            List<InterestPoolAccountDetails> intacdetilslist = intpoolactdetils.getInterestPoolAccountDetails();

            List<PaymentDisbursement> disbillist = null;
            List<PaymentInterestDisbursement> disintlist = null;
            List<TempRefundBillCorp> disreflist = null;
            List<CsdfDetails> psdfdetlist = null;
            List<MiscDisbursement> misclist = null;
            List<BigDecimal> intpoolbalances = new ArrayList<BigDecimal>();

//            BigDecimal intpoolbef = intacdetilslist.get(0).getMainBalance();
//            BigDecimal intpool = intpoolbef1;
            disbillist = disbilldao.getbillDisbursementDetailsbyfromsndtoid(new BigDecimal(startbill), new BigDecimal(endbill));
            disreflist = disrefdao.getdisRefundBillPayCorpbyfromandtoids(new BigDecimal(startref), new BigDecimal(endref));
            disintlist = disintdao.getDisbursedInterestDetailsbyfromandtoids(new BigDecimal(startint), new BigDecimal(endint));
            psdfdetlist = csdfdao.getPSDFdetailsbyfromandtoids(new BigDecimal(startpsdf), new BigDecimal(endpsdf));
            misclist = miscdao.getMiscDisbursementbyfromandtoids(new BigDecimal(startmisc), new BigDecimal(endmisc));

//            if (disintlist != null && !(disintlist.isEmpty())) {
//                for (PaymentInterestDisbursement e : disintlist) {
//                    intpoolbalances.add(e.getIntPoolBal());
//                }
//                Collections.sort(intpoolbalances);
//                intpoolaft = intpoolbalances.get(0);
//                intpool = intpoolaft;
//            } else {
//             
//
//            }
//            BigDecimal poolbefbil = disbilldao.getmaxpoolbal(Integer.parseInt(startbill), Integer.parseInt(endbill));
//            BigDecimal poolbefref = disrefdao.getmaxpoolbalinrefund(Integer.parseInt(startref), Integer.parseInt(endref));
            String Billtypebil = disbilldao.getbilltype(Integer.parseInt(startbill), Integer.parseInt(endbill));

            BigDecimal intpoolbef = BigDecimal.ZERO;
            BigDecimal intpoolaft = BigDecimal.ZERO;

            BigDecimal poolbef = BigDecimal.ZERO;
            BigDecimal poolaft = BigDecimal.ZERO;

            intpoolbef = disbilldao.getmaxINTpoolopenbalfornotesheet(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
            intpoolaft = disbilldao.getminINTpoolopenbalfornotesheet(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
            poolbef = disbilldao.getmaxpoolopenbalfornotesheet(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
            poolaft = disbilldao.getminpoolopenbalfornotesheet(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
            if (poolbef == null) {
                Timestamp entrytimebill = disbilldao.getentrytimeforonlyInterest(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));

                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss.SSS a");
                System.out.println(outputFormat.format(entrytimebill));
                poolbef = disbilldao.getmaxpoolopenbalfornotesheetonlyINT(outputFormat.format(entrytimebill));
                if (poolbef == null) {
                    poolbef = BigDecimal.ZERO;
                }
            }
            if (poolaft == null) {
                Timestamp entrytimebill = disbilldao.getentrytimeforonlyInterest(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));

                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss.SSS a");
                System.out.println(outputFormat.format(entrytimebill));
                poolaft = disbilldao.getmaxpoolopenbalfornotesheetonlyINT(outputFormat.format(entrytimebill));
                if (poolaft == null) {
                    poolaft = BigDecimal.ZERO;
                }
            }
            if (intpoolbef == null) {
                Timestamp entrytimeInt = disbilldao.getentrytimeforonlybills(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));

                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss.SSS a");
                System.out.println(outputFormat.format(entrytimeInt));

                intpoolbef = disbilldao.getinterestbalonlyBILLSbyentrytime(outputFormat.format(entrytimeInt));
                if (intpoolbef == null) {
                    intpoolbef = BigDecimal.ZERO;
                }
            }
            if (intpoolaft == null) {
                Timestamp entrytimeInt = disbilldao.getentrytimeforonlybills(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));

                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss.SSS a");
                System.out.println(outputFormat.format(entrytimeInt));
                intpoolaft = disbilldao.getinterestbalonlyBILLSbyentrytime(outputFormat.format(entrytimeInt));
                if (intpoolaft == null) {
                    intpoolaft = BigDecimal.ZERO;
                }
            }

            ModelAndView model = new ModelAndView("disbursenotesheet");

            model.addObject("disbillist", disbillist);
            model.addObject("disreflist", disreflist);
            model.addObject("disintlist", disintlist);
            model.addObject("psdfdetlist", psdfdetlist);
            model.addObject("poolbef", poolbef);
            model.addObject("poolaft", poolaft);
            model.addObject("intpoolbef", intpoolbef);
            model.addObject("intpoolaft", intpoolaft);
            model.addObject("Billtypebil", Billtypebil);
            model.addObject("misclist", misclist);
            model.addObject("finclyear", finclyear);

            return model;
        }
        ModelAndView mv = new ModelAndView("overalldisbursement");

        return mv;
    }

    public ModelAndView Allnotesheet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bName");
        String bsubmit = request.getParameter("bsubmit");
        String bsubmitbank = request.getParameter("bsubmitbank");
//        String selectdate = request.getParameter("startdate");
//        Date selectDate=new Date();
//        if (selectdate !=null) {
//             selectDate = new SimpleDateFormat("dd-MM-yyyy").parse(selectdate);
//        }
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            String type = request.getParameter("TYPE");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            DisbursedInterestDetailsDAO disintdao = new DisbursedInterestDetailsDAO();
            TempRefundBillCorpDAO disrefdao = new TempRefundBillCorpDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            miscDisbursementDAO miscdao = new miscDisbursementDAO();
            BankStatementDAO bnkstmtdao = new BankStatementDAO();
            MappingInterestBankDAO mapintbnkdao = new MappingInterestBankDAO();
            PoolToIntDAO pooltointdao = new PoolToIntDAO();
            MappingBillBankDAO mapbilbnkdao = new MappingBillBankDAO();
            MappingRefundBankDAO maprefbnkdao = new MappingRefundBankDAO();
            PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();

            List<BankStatement> bnkstmt = null;
            List<PaymentDisbursement> disbillist = null;
            List<PaymentDisbursement> disbillistIN = null;
            List<PaymentInterestDisbursement> disintlist = null;
            List<PaymentInterestDisbursement> disintlistIN = null;
            List<TempRefundBillCorp> disreflist = null;
            List<TempRefundBillCorp> disreflistIN = null;
            List<CsdfDetails> psdfdetlist = null;
            List<CsdfDetails> psdfdetlistIN = null;
            List<MiscDisbursement> misclist = null;
            List<MiscDisbursement> misclistIN = null;
            List<MappingInterestBank> mapintbnk = null;
            List<PoolToInt> pooltoint = null;
            List<MappingBillBank> mapbilbnk = null;
            List<MappingRefundBank> maprefbnk = null;
            List<PoolToPool> pooltopool = null;

            disbillist = disbilldao.getbillDisbursementDetailsbyFromDateTodatetypebyrev(startDate, endDate);
            disreflist = disrefdao.getdisRefundBillPayCorpbydatestypebyrev(startDate, endDate);
            disintlist = disintdao.getDisbursedInterestDetailsbydates(startDate, endDate);
            psdfdetlist = csdfdao.getPSDFdetailsbydates(startDate, endDate);
            misclist = miscdao.getmiscdetailsbydates(startDate, endDate);

            disbillistIN = disbilldao.getbillDisbursementDetailsbyFromDateTodatetypebyrevIN(startDate, endDate);
            disreflistIN = disrefdao.getdisRefundBillPayCorpbydatestypebyrevIN(startDate, endDate);
            disintlistIN = disintdao.getDisbursedInterestDetailsbydatesIN(startDate, endDate);
            psdfdetlistIN = csdfdao.getPSDFdetailsbydatesIN(startDate, endDate);
            misclistIN = miscdao.getMiscdetailsbydatesIN(startDate, endDate);

            bnkstmt = bnkstmtdao.CRBankStatementlistbyentrydates(startDate, endDate);
            mapintbnk = mapintbnkdao.getMappingInterestBankbyentrydates(startDate, endDate);
            pooltoint = pooltointdao.getPoolToIntbydates(startDate, endDate);
            mapbilbnk = mapbilbnkdao.getMappingBillBankbyentrydatesforsrasandtras(startDate, endDate);
            maprefbnk = maprefbnkdao.getMappingRefundBankbyentrydatesforsrasandtras(startDate, endDate);
            pooltopool = pooltopooldao.getPoolToIntbydates(startDate, endDate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            System.out.println("month is " + month);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String finclyear = null;
            if (month <= 2) {
                finclyear = Integer.toString(year - 1) + "-" + Integer.toString(year);

            } else {
                finclyear = Integer.toString(year) + "-" + Integer.toString(year + 1);
            }

            System.out.println("finclyear is " + finclyear);

            ModelAndView model = new ModelAndView("Allnotesheet");

            model.addObject("disbillist", disbillist);
            model.addObject("disreflist", disreflist);
            model.addObject("disintlist", disintlist);
            model.addObject("psdfdetlist", psdfdetlist);
            model.addObject("misclist", misclist);
            model.addObject("disbillistIN", disbillistIN);
            model.addObject("disreflistIN", disreflistIN);
            model.addObject("disintlistIN", disintlistIN);
            model.addObject("psdfdetlistIN", psdfdetlistIN);
            model.addObject("misclistIN", misclistIN);
            model.addObject("bnkstmt", bnkstmt);
            model.addObject("mapintbnk", mapintbnk);
            model.addObject("finclyear", finclyear);
            model.addObject("pooltoint", pooltoint);
            model.addObject("mapbilbnk", mapbilbnk);
            model.addObject("maprefbnk", maprefbnk);
            model.addObject("pooltopool", pooltopool);

            return model;

        }
        if (bsubmit != null) {

            String finclyear = request.getParameter("finclyear");
            String startbill = request.getParameter("startbill");
            String endbill = request.getParameter("endbill");
            String startref = request.getParameter("startref");
            String endref = request.getParameter("endref");
            String startint = request.getParameter("startint");
            String endint = request.getParameter("endint");
            String startpsdf = request.getParameter("startpsdf");
            String endpsdf = request.getParameter("endpsdf");
            String startmisc = request.getParameter("startmisc");
            String endmisc = request.getParameter("endmisc");
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            DisbursedInterestDetailsDAO disintdao = new DisbursedInterestDetailsDAO();
            TempRefundBillCorpDAO disrefdao = new TempRefundBillCorpDAO();
            InterestPoolAccountDetailsDAO intpoolactdetils = new InterestPoolAccountDetailsDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            miscDisbursementDAO miscdao = new miscDisbursementDAO();

            List<InterestPoolAccountDetails> intacdetilslist = intpoolactdetils.getInterestPoolAccountDetails();

            List<PaymentDisbursement> disbillist = null;
            List<PaymentInterestDisbursement> disintlist = null;
            List<TempRefundBillCorp> disreflist = null;
            List<CsdfDetails> psdfdetlist = null;
            List<MiscDisbursement> misclist = null;

            List<BigDecimal> intpoolbalances = new ArrayList<BigDecimal>();

            System.out.println("=====================================================Start is " + startbill + " End is " + endbill);
            disbillist = disbilldao.getbillDisbursementDetailsbyfromsndtoid(new BigDecimal(startbill), new BigDecimal(endbill));
            disreflist = disrefdao.getdisRefundBillPayCorpbyfromandtoids(new BigDecimal(startref), new BigDecimal(endref));
            disintlist = disintdao.getDisbursedInterestDetailsbyfromandtoids(new BigDecimal(startint), new BigDecimal(endint));
            psdfdetlist = csdfdao.getPSDFdetailsbyfromandtoids(new BigDecimal(startpsdf), new BigDecimal(endpsdf));
            misclist = miscdao.getMiscDisbursementbyfromandtoids(new BigDecimal(startmisc), new BigDecimal(endmisc));

            String Billtypebil = disbilldao.getbilltype(Integer.parseInt(startbill), Integer.parseInt(endbill));
            if (disreflist != null) {
                Billtypebil = disreflist.get(0).getBillPayableCorp().getBillType();
            }
            if (disintlist != null) {
                Billtypebil = disintlist.get(0).getDisbursedInterestDetails().getBillType();
            }
            if (psdfdetlist != null) {
                Billtypebil = "Psdf";
            }
            if (misclist != null) {
                Billtypebil = "Misc";
            }
            BigDecimal intpoolbef = BigDecimal.ZERO;
            BigDecimal intpoolaft = BigDecimal.ZERO;

            BigDecimal poolbef = BigDecimal.ZERO;
            BigDecimal poolaft = BigDecimal.ZERO;

            BigDecimal poolbefsras = BigDecimal.ZERO;
            BigDecimal poolaftersras = BigDecimal.ZERO;
            BigDecimal intbefsras = BigDecimal.ZERO;
            BigDecimal intaftersras = BigDecimal.ZERO;
            BigDecimal poolbeftras = BigDecimal.ZERO;
            BigDecimal poolaftertras = BigDecimal.ZERO;
            BigDecimal intbeftras = BigDecimal.ZERO;
            BigDecimal intaftertras = BigDecimal.ZERO;
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss.SSS a");

//            intpoolbef = disbilldao.getmaxINTpoolopenbalfornotesheet(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//            intpoolaft = disbilldao.getminINTpoolopenbalfornotesheet(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//            poolbef = disbilldao.getmaxpoolopenbalfornotesheet(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//            poolaft = disbilldao.getminpoolopenbalfornotesheet(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//            if (poolbef == null) {
//                Timestamp entrytimebill = disbilldao.getentrytimeforonlyInterest(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//                if (entrytimebill == null) {
//                    entrytimebill = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
//                }
//
//                poolbef = disbilldao.getmaxpoolopenbalfornotesheetonlyINT(outputFormat.format(entrytimebill));
//                if (poolbef == null) {
//                    poolbef = BigDecimal.ZERO;
//                }
//            }
//            if (poolaft == null) {
//                Timestamp entrytimebill = disbilldao.getentrytimeforonlyInterest(Integer.parseInt(startint), Integer.parseInt(endint), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//                if (entrytimebill == null) {
//                    entrytimebill = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
//                }
//
//                poolaft = disbilldao.getmaxpoolopenbalfornotesheetonlyINTAFTER(outputFormat.format(entrytimebill));
//                if (poolaft == null) {
//                    poolaft = BigDecimal.ZERO;
//                }
//            }
//            if (intpoolbef == null) {
//                Timestamp entrytimeInt = disbilldao.getentrytimeforonlybills(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//                if (entrytimeInt == null) {
//                    entrytimeInt = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
//                }
//
//                intpoolbef = disbilldao.getinterestbalonlyBILLSbyentrytime(outputFormat.format(entrytimeInt));
//                if (intpoolbef == null) {
//
//                    intpoolbef = BigDecimal.ZERO;
//                }
//            }
//            if (intpoolaft == null) {
//                Timestamp entrytimeInt = disbilldao.getentrytimeforonlybills(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc));
//                if (entrytimeInt == null) {
//                    entrytimeInt = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
//                }
//
//                intpoolaft = disbilldao.getinterestbalonlyBILLSbyentrytimeAFTER(outputFormat.format(entrytimeInt));
//
//                if (intpoolaft == null) {
//                    intpoolaft = BigDecimal.ZERO;
//                }
//            }
            Timestamp entrytimebefore = disbilldao.getentrytimebeforedisburse(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc), Integer.parseInt(startint), Integer.parseInt(endint));
            Timestamp entrytimeafter = disbilldao.getentrytimeafterdisburse(Integer.parseInt(startbill), Integer.parseInt(endbill), Integer.parseInt(startref), Integer.parseInt(endref), Integer.parseInt(startpsdf), Integer.parseInt(endpsdf), Integer.parseInt(startmisc), Integer.parseInt(endmisc), Integer.parseInt(startint), Integer.parseInt(endint));

            if (entrytimebefore == null) {
                entrytimebefore = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
            }
            if (entrytimeafter == null) {
                entrytimeafter = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
            }
            System.out.println("entrytimebefore is =" + entrytimebefore + "entrytimeafter is =" + entrytimeafter);

            poolbef = disbilldao.getmaxpoolopenbalfornotesheetonlyINT(outputFormat.format(entrytimebefore));
            poolaft = disbilldao.getmaxpoolopenbalfornotesheetonlyINTAFTER(outputFormat.format(entrytimeafter));
            intpoolbef = disbilldao.getinterestbalonlyBILLSbyentrytime(outputFormat.format(entrytimebefore));
            intpoolaft = disbilldao.getinterestbalonlyBILLSbyentrytimeAFTER(outputFormat.format(entrytimeafter));

            poolbefsras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTsras(outputFormat.format(entrytimebefore));
            poolaftersras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTsrasAfter(outputFormat.format(entrytimeafter));
            poolbeftras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTtras(outputFormat.format(entrytimebefore));
            poolaftertras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTtrasAfter(outputFormat.format(entrytimeafter));

            intbefsras = disbilldao.getinterestbalonlyBILLSbyentrytimesras(outputFormat.format(entrytimebefore));
            intaftersras = disbilldao.getinterestbalonlyBILLSbyentrytimesrasAfter(outputFormat.format(entrytimeafter));
            intbeftras = disbilldao.getinterestbalonlyBILLSbyentrytimetras(outputFormat.format(entrytimebefore));
            intaftertras = disbilldao.getinterestbalonlyBILLSbyentrytimetrasAfter(outputFormat.format(entrytimeafter));

            System.out.println("poolbef is =" + poolbef + "poolafter is =" + poolaft);
            System.out.println("intbef is =" + intpoolbef + "intafter is =" + intpoolaft);
            System.out.println("poolbefsras is =" + poolbefsras + "poolaftersras is =" + poolaftersras);
            System.out.println("intbefsras is =" + intbefsras + "intaftersras is =" + intaftersras);
            System.out.println("poolbeftras is =" + poolbeftras + "poolaftertras is =" + poolaftertras);
            System.out.println("intbeftras is =" + intbeftras + "intaftertras is =" + intaftertras);

            System.out.println("finclyear is " + finclyear);
            ModelAndView model = new ModelAndView("disbursenotesheet");

            model.addObject("disbillist", disbillist);
            model.addObject("disreflist", disreflist);
            model.addObject("disintlist", disintlist);
            model.addObject("psdfdetlist", psdfdetlist);
            model.addObject("misclist", misclist);

            model.addObject("poolbef", poolbef);
            model.addObject("poolaft", poolaft);
            model.addObject("intpoolbef", intpoolbef);
            model.addObject("intpoolaft", intpoolaft);

            model.addObject("poolbefsras", poolbefsras);
            model.addObject("poolaftersras", poolaftersras);
            model.addObject("intbefsras", intbefsras);
            model.addObject("intaftersras", intaftersras);
            model.addObject("poolbeftras", poolbeftras);
            model.addObject("poolaftertras", poolaftertras);
            model.addObject("intbeftras", intbeftras);
            model.addObject("intaftertras", intaftertras);
            model.addObject("Billtypebil", Billtypebil);
            model.addObject("finclyear", finclyear);

            return model;
        }

        if (bsubmitbank != null) {
            String finclyear = request.getParameter("finclyear");
            String startbank = request.getParameter("startbank");
            String endbank = request.getParameter("endbank");

            String startbankint = request.getParameter("startbankint");
            String endbankint = request.getParameter("endbankint");

            String startptoint = request.getParameter("startptoint");
            String endptoint = request.getParameter("endptoint");

            String startmapbil = request.getParameter("startmapbil");
            String endmapbil = request.getParameter("endmapbil");

            String startmapref = request.getParameter("startmapref");
            String endmapref = request.getParameter("endmapref");

            String startptop = request.getParameter("startptop");
            String endptop = request.getParameter("endptop");

            String startdate = null;
            startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");

            NewBankStatementDAO nwbkdao = new NewBankStatementDAO();
            List<BankStatement> libk = null;

            if (startdate != null) {
                libk = nwbkdao.CRBankStmtbydates(new SimpleDateFormat("dd-MM-yyyy").parse(startdate), new SimpleDateFormat("dd-MM-yyyy").parse(enddate));
            }

            if (libk != null && libk.size() > 0) {
                startbank = libk.get(0).getStmtId().toPlainString();
                endbank = libk.get(libk.size() - 1).getStmtId().toPlainString();
                String finyear = startdate.substring(6, 10);
                Integer fin = new Integer(finyear) + 1;
                finclyear = finyear + "-" + fin;
                System.out.println("finclyear is " + finclyear);
                System.out.println("startbank is " + startbank);
                System.out.println("endbank is " + endbank);
            }

            MappingBillBankDAO mapbilldao = new MappingBillBankDAO();
            MappingInterestBankDAO mapintdao = new MappingInterestBankDAO();
            MappingRefundBankDAO maprefdao = new MappingRefundBankDAO();
            BankStatementDAO bnkstmtdao = new BankStatementDAO();
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            PoolToIntDAO pooltointdao = new PoolToIntDAO();
            PoolToPoolDAO pooltopooldao = new PoolToPoolDAO();
            List<BankStatement> bnkstmt = null;
            List<MappingBillBank> mapbillist = null;
            List<MappingBillBank> mapbillistnew = null;
            List<MappingInterestBank> mapintlist = null;
            List<MappingRefundBank> mapreflist = null;
            List<MappingRefundBank> mapreflistnew = null;
            List<PoolToInt> pooltoint = null;
            List<PoolToPool> pooltopool = null;
            mapbillist = mapbilldao.getMappingBillBankDetailsbybankids(new BigDecimal(startbank), new BigDecimal(endbank));
            mapbillistnew = mapbilldao.getMappingBillBankDetailsbyids(new BigDecimal(startmapbil), new BigDecimal(endmapbil));

            mapreflist = maprefdao.getMappingRefundBankbybankids(new BigDecimal(startbank), new BigDecimal(endbank));
            mapreflistnew = maprefdao.getMappingRefundBankbyids(new BigDecimal(startmapref), new BigDecimal(endmapref));

            mapintlist = mapintdao.getMappingInterestBankbybankids(new BigDecimal(startbankint), new BigDecimal(endbankint));
            bnkstmt = bnkstmtdao.BankStatementnotmappedlistbyids(new BigDecimal(startbank), new BigDecimal(endbank));
            pooltoint = pooltointdao.PoolToIntlistbyids(new BigDecimal(startptoint), new BigDecimal(endptoint));
            pooltopool = pooltopooldao.PoolToIntlistbyids(new BigDecimal(startptop), new BigDecimal(endptop));

            BigDecimal openpoolbal = bnkstmtdao.getopenbalBankStmtbyid(Integer.parseInt(startbank), Integer.parseInt(endbank));
            BigDecimal sumcramt = bnkstmtdao.getCRSumAmountBankStmtbybankids(Integer.parseInt(startbank), Integer.parseInt(endbank));
            BigDecimal sumintamt = mapintdao.getSummedMappingInterestBankbybankids(new BigDecimal(startbank), new BigDecimal(endbank));
            Timestamp bankstartdate = bnkstmtdao.getamountdatebybankids(Integer.parseInt(startbank), Integer.parseInt(endbank));

            if (sumintamt == null) {
                sumintamt = BigDecimal.ZERO;
            }
            if (openpoolbal == null) {
                sumintamt = BigDecimal.ZERO;
            }
            if (sumcramt == null) {
                sumintamt = BigDecimal.ZERO;
            }
            if (bankstartdate == null) {
                Date dat = new Date();
                bankstartdate = new Timestamp(dat.getTime());
            }
            BigDecimal poolbef = BigDecimal.ZERO;
            BigDecimal poolafter = BigDecimal.ZERO;
            BigDecimal intbef = BigDecimal.ZERO;
            BigDecimal intafter = BigDecimal.ZERO;
            BigDecimal poolbefsras = BigDecimal.ZERO;
            BigDecimal poolaftersras = BigDecimal.ZERO;
            BigDecimal intbefsras = BigDecimal.ZERO;
            BigDecimal intaftersras = BigDecimal.ZERO;
            BigDecimal poolbeftras = BigDecimal.ZERO;
            BigDecimal poolaftertras = BigDecimal.ZERO;
            BigDecimal intbeftras = BigDecimal.ZERO;
            BigDecimal intaftertras = BigDecimal.ZERO;

            Timestamp entrytimebeforestmt = bnkstmtdao.getentrytimebeforestmt(Integer.parseInt(startbank), Integer.parseInt(endbank), Integer.parseInt(startbankint), Integer.parseInt(endbankint), Integer.parseInt(startptoint), Integer.parseInt(endptoint), Integer.parseInt(startmapbil), Integer.parseInt(endmapbil), Integer.parseInt(startmapref), Integer.parseInt(endmapref),Integer.parseInt(startptop), Integer.parseInt(endptop));
            Timestamp entrytimeafterstmt = bnkstmtdao.getentrytimeafterstmt(Integer.parseInt(startbank), Integer.parseInt(endbank), Integer.parseInt(startbankint), Integer.parseInt(endbankint), Integer.parseInt(startptoint), Integer.parseInt(endptoint), Integer.parseInt(startmapbil), Integer.parseInt(endmapbil), Integer.parseInt(startmapref), Integer.parseInt(endmapref),Integer.parseInt(startptop), Integer.parseInt(endptop));
            if (entrytimebeforestmt == null) {
                entrytimebeforestmt = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
            }
            if (entrytimeafterstmt == null) {
                entrytimeafterstmt = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());;
            }
            System.out.println("entrytimebeforestmt is =" + entrytimebeforestmt + "entrytimeafterstmt is =" + entrytimeafterstmt);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss.SSS a");
//            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:ss.SSS a");

            poolbef = disbilldao.getmaxpoolopenbalfornotesheetonlyINT(outputFormat.format(entrytimebeforestmt));
            poolafter = disbilldao.getmaxpoolopenbalfornotesheetonlyINTAFTER(outputFormat.format(entrytimeafterstmt));
            intbef = disbilldao.getinterestbalonlyBILLSbyentrytime(outputFormat.format(entrytimebeforestmt));
            intafter = disbilldao.getinterestbalonlyBILLSbyentrytimeAFTER(outputFormat.format(entrytimeafterstmt));

            poolbefsras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTsras(outputFormat.format(entrytimebeforestmt));
            poolaftersras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTsrasAfter(outputFormat.format(entrytimeafterstmt));
            poolbeftras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTtras(outputFormat.format(entrytimebeforestmt));
            poolaftertras = disbilldao.getmaxpoolopenbalfornotesheetonlyINTtrasAfter(outputFormat.format(entrytimeafterstmt));

            intbefsras = disbilldao.getinterestbalonlyBILLSbyentrytimesras(outputFormat.format(entrytimebeforestmt));
            intaftersras = disbilldao.getinterestbalonlyBILLSbyentrytimesrasAfter(outputFormat.format(entrytimeafterstmt));
            intbeftras = disbilldao.getinterestbalonlyBILLSbyentrytimetras(outputFormat.format(entrytimebeforestmt));
            intaftertras = disbilldao.getinterestbalonlyBILLSbyentrytimetrasAfter(outputFormat.format(entrytimeafterstmt));

            System.out.println("poolbef is =" + poolbef + "poolafter is =" + poolafter);
            System.out.println("intbef is =" + intbef + "intafter is =" + intafter);
            System.out.println("poolbefsras is =" + poolbefsras + "poolaftersras is =" + poolaftersras);
            System.out.println("intbefsras is =" + intbefsras + "intaftersras is =" + intaftersras);
            System.out.println("poolbeftras is =" + poolbeftras + "poolaftertras is =" + poolaftertras);
            System.out.println("intbeftras is =" + intbeftras + "intaftertras is =" + intaftertras);

            System.out.println("finclyear is " + finclyear);
            ModelAndView model = new ModelAndView("overallmappingviewnew");

            model.addObject("mapbillist", mapbillist);
            model.addObject("mapreflistnew", mapreflistnew);

            model.addObject("mapreflist", mapreflist);
            model.addObject("mapbillistnew", mapbillistnew);

            model.addObject("mapintlist", mapintlist);
            model.addObject("bnkstmt", bnkstmt);
            model.addObject("finclyear", finclyear);
//            model.addObject("type", type);
            model.addObject("sumcramt", sumcramt);
            model.addObject("sumintamt", sumintamt);
            model.addObject("openpoolbal", openpoolbal);
            model.addObject("poolbef", poolbef);
            model.addObject("poolafter", poolafter);
            model.addObject("intbef", intbef);
            model.addObject("intafter", intafter);
            model.addObject("poolbefsras", poolbefsras);
            model.addObject("poolaftersras", poolaftersras);
            model.addObject("intbefsras", intbefsras);
            model.addObject("intaftersras", intaftersras);
            model.addObject("poolbeftras", poolbeftras);
            model.addObject("poolaftertras", poolaftertras);
            model.addObject("intbeftras", intbeftras);
            model.addObject("intaftertras", intaftertras);
            model.addObject("pooltoint", pooltoint);
            model.addObject("pooltopool", pooltopool);

            return model;

        }

        ModelAndView mv = new ModelAndView("overalldisbursement");

        return mv;
    }

    public ModelAndView overalldisbursementnew(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bName");
        if (bName != null) {

            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            String type = request.getParameter("TYPE");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            DisbursedInterestDetailsDAO disintdao = new DisbursedInterestDetailsDAO();
            TempRefundBillCorpDAO disrefdao = new TempRefundBillCorpDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            List<PaymentDisbursement> disbillist = null;
            List<PaymentInterestDisbursement> disintlist = null;
            List<TempRefundBillCorp> disreflist = null;
            List<CsdfDetails> psdfdetlist = null;

//             disbillist = disbilldao.getbillDisbursementDetailsbyFromDateTodatetypebyrev(startDate, endDate, billType, billCat);
//            disreflist = disrefdao.getdisRefundBillPayCorpbydatestypebyrev(startDate, endDate, billType, billCat);
//            disintlist = disintdao.getDisbursedInterestDetailsbydates(startDate, endDate);
            disbillist = disbilldao.getbillDisbursementDetailsbyFromDateTodate(startDate, endDate);
            disreflist = disrefdao.getdisRefundBillPayCorpbydates(startDate, endDate);
            disintlist = disintdao.getDisbursedInterestDetailsbydates(startDate, endDate);
            psdfdetlist = csdfdao.getPSDFdetailsbyfromandtodates(startDate, endDate);

            ModelAndView model = new ModelAndView("overalldisbursementviewnew");

            model.addObject("disbillist", disbillist);
            model.addObject("disreflist", disreflist);
            model.addObject("disintlist", disintlist);
            model.addObject("psdfdetlist", psdfdetlist);
            return model;

        }
        ModelAndView mv = new ModelAndView("overalldisbursementnew");

        return mv;
    }

    public ModelAndView overallpsdf(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bName");
        if (bName != null) {

            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            String type = request.getParameter("TYPE");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            PaymentDisbursementDAO disbilldao = new PaymentDisbursementDAO();
            DisbursedInterestDetailsDAO disintdao = new DisbursedInterestDetailsDAO();
            TempRefundBillCorpDAO disrefdao = new TempRefundBillCorpDAO();
            CsdfDetailsDAO csdfdao = new CsdfDetailsDAO();
            List<PaymentDisbursement> disbillist = null;
            List<PaymentInterestDisbursement> disintlist = null;
            List<TempRefundBillCorp> disreflist = null;
            List<CsdfDetails> psdfdetlist = null;

//             disbillist = disbilldao.getbillDisbursementDetailsbyFromDateTodatetypebyrev(startDate, endDate, billType, billCat);
//            disreflist = disrefdao.getdisRefundBillPayCorpbydatestypebyrev(startDate, endDate, billType, billCat);
//            disintlist = disintdao.getDisbursedInterestDetailsbydates(startDate, endDate);
//            disbillist = disbilldao.getbillDisbursementDetailsbyFromDateTodate(startDate, endDate);
//            disreflist = disrefdao.getdisRefundBillPayCorpbydates(startDate, endDate);
//            disintlist = disintdao.getDisbursedInterestDetailsbydates(startDate, endDate);
            psdfdetlist = csdfdao.getPSDFdetailsbyfromandtodates(startDate, endDate);

            ModelAndView model = new ModelAndView("psdfdetails");

//            model.addObject("disbillist", disbillist);
//            model.addObject("disreflist", disreflist);
//            model.addObject("disintlist", disintlist);
            model.addObject("psdfdetlist", psdfdetlist);
            return model;

        }
        ModelAndView mv = new ModelAndView("overalldisbursementnew");

        return mv;
    }

    public ModelAndView dynnewReconciliationReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {
        ModelAndView mv = new ModelAndView("Report/corporateWiseBankStmt");
        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        String bName = request.getParameter("bName");
        System.out.println("bName " + bName);
        if (bName != null) {

            String corpName = request.getParameter("corpId");
            CorporatesDAO corpdao = new CorporatesDAO();
            DynReconciliationCropDAO dynrecondao = new DynReconciliationCropDAO();
            int corpid = corpdao.getCorpIdbyName(corpName);
            System.out.println("corpid is " + corpid);
            String subAccNum = corpdao.getBankSubAccNumberbyId(corpid);
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
            String fdate = format2.format(startDate);
            String ldate = format2.format(endDate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("startdate is " + startdate);
            System.out.println("startDate is " + startDate);
            List<DynReconciliationCorp> dynreconlist = dynrecondao.getReconreportByCorpAnddatesYearbycorpid(startDate, endDate, corpid);
            ModelAndView mv1 = new ModelAndView("viewdynReconciliationReport");
//            BigDecimal outstanding = dynreconlist.get(0).getOutstandingAmount();
            mv1.addObject("dynreconlist", dynreconlist);
//            mv1.addObject("outstanding", outstanding);
            mv1.addObject("corpName", corpName);

            return mv1;

        }

        CorporatesDAO corporatedao = new CorporatesDAO();
        List<Corporates> corporateList = corporatedao.Corporateslist();
        List<String> listCorp = new ArrayList<>();

        for (Corporates temp : corporateList) {
            listCorp.add(temp.getCorporateName());
        }

        mv.addObject("corporateList", listCorp);
        String title = "List of Pool Member for Reconciliation";
        mv.addObject("title", title);
        return mv;

    }

    public ModelAndView dynExceldownloadreconreport(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String bName = request.getParameter("bName");
        if (bName != null) {
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
            String frmDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
            System.out.println("frmDate is " + frmDate);
            System.out.println("toDate is " + toDate);
            DynReconciliationCropDAO reconcorpdao = new DynReconciliationCropDAO();
            List<DynReconciliationCorp> reconlist = null;
            reconlist = reconcorpdao.getReconreportByCorpAnddatesYear(startDate, endDate);
            if (reconlist != null && !(reconlist.isEmpty())) {

                List<String> corpnamesinrecon = new ArrayList<String>();
                for (DynReconciliationCorp reconcorp : reconlist) {
//                    System.out.println("corp id is =" + reconcorp.getCorporates().getCorporateId());

                    int flag = 0;
                    if (corpnamesinrecon != null) {
                        for (int y = 0; y < corpnamesinrecon.size(); y++) {
                            if (reconcorp.getCorporates().getCorporateName().equalsIgnoreCase(corpnamesinrecon.get(y))) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            corpnamesinrecon.add(reconcorp.getCorporates().getCorporateName());
                        }
                    } else {
                        corpnamesinrecon.add(reconcorp.getCorporates().getCorporateName());
                    }

                }
                Collections.sort(corpnamesinrecon);
//                String[] columns = {"SLNO", "BILL_ENTRY_DATE", "ENTRY_DATE", "CORP_NAME", "WEEK_ID", "BILL_TYPE", "BILLING_DATE", "REVISION_NO", "BILL_YEAR", "BILL_DUE_DATE", "PAY_TOTALNET", "REC_TOTALNET", "PAY_FINALAMOUNT", "REC_FINALAMOUNT", "PAY_PENDINGAMOUNT", "REC_PENDINGAMOUNT", "CR_DR_DATE", "CR_AMOUNT", "CR_SETTLED_AMOUNT", "CR_AVAILABLE", "DR_AMOUNT", "DR_SETTLED_AMOUNT", "OUTSTANDING_AMOUNT", "REMARKS"};
                String[] columns = {"SLNO", "Event_DATE", "Recon Run Date", "CORP_NAME", "WEEK_ID", "BILL_TYPE", "BILL Issue DATE", "REVISION_NO", "BILL_YEAR", "Due + Grace Period", "Payable asper Bill", "Receivable as per Bill", "Pay. Difference wrt Rev.", "Rec. Difference wrt Rev.", "Payable Pending", "Receivable Pending", "Bank CR/DR DATE", "CR_AMOUNT", "CR AMOUNT Settled", "CR_AVAILABLE after settled", "DR_AMOUNT", "DR AMOUNT Settled", "OUTSTANDING_AMOUNT (- Receivable / + Payable)", "REMARKS"};

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = null;
                Row headerRow = null;
                for (int i = 0; i < corpnamesinrecon.size(); i++) {
                    sheet = workbook.createSheet(corpnamesinrecon.get(i));
                    headerRow = sheet.createRow(0);
                    Cell cell = null;
                    for (int j = 0; j < columns.length; j++) {
                        cell = headerRow.createCell(j);
                        cell.setCellValue(columns[j]);
                    }
                    System.out.print("corp name  is" + corpnamesinrecon.get(i));
                    int rowflag = 0;
                    for (DynReconciliationCorp reconcorp : reconlist) {
                        if (corpnamesinrecon.get(i).equalsIgnoreCase(reconcorp.getCorporates().getCorporateName())) {
                            rowflag = sheet.getLastRowNum();
                            rowflag = rowflag + 1;
                            System.out.print("Last RowNum  is" + rowflag);
                            headerRow = sheet.createRow(rowflag);
                            for (int j = 0; j < columns.length; j++) {
                                switch (j) {

                                    case 0:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(rowflag);
                                        break;
                                    case 1:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillEntryDate().toString());
                                        break;
                                    case 2:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getEntryDate().toString());
                                        break;
                                    case 3:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getCorporates().getCorporateName());
                                        break;
                                    case 4:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getWeekId().toString());
                                        break;
                                    case 5:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillType());
                                        break;
                                    case 6:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillingDate().toString());
                                        break;
                                    case 7:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getRevisionNo().toString());
                                        break;
                                    case 8:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillYear().toString());
                                        break;
                                    case 9:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getBillDueDate().toString());
                                        break;
                                    case 10:
                                        cell = headerRow.createCell(j);
                                        if (reconcorp.getPayTotalnet() != null) {
                                            cell.setCellValue(reconcorp.getPayTotalnet().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 11:
                                        cell = headerRow.createCell(j);
                                        if (reconcorp.getRecTotalnet() != null) {
                                            cell.setCellValue(reconcorp.getRecTotalnet().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 12:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getPayFinalamount() != null) {
                                            cell.setCellValue(reconcorp.getPayFinalamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 13:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getRecFinalamount() != null) {
                                            cell.setCellValue(reconcorp.getRecFinalamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 14:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getPayPendingamount() != null) {
                                            cell.setCellValue(reconcorp.getPayPendingamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 15:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getRecPendingamount() != null) {
                                            cell.setCellValue(reconcorp.getRecPendingamount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 16:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrDrDate() != null) {
                                            cell.setCellValue(reconcorp.getCrDrDate().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 17:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrAmount() != null) {
                                            cell.setCellValue(reconcorp.getCrAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 18:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrSettledAmount() != null) {
                                            cell.setCellValue(reconcorp.getCrSettledAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 19:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getCrAvailable() != null) {
                                            cell.setCellValue(reconcorp.getCrAvailable().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 20:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getDrAmount() != null) {
                                            cell.setCellValue(reconcorp.getDrAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 21:
                                        cell = headerRow.createCell(j);

                                        if (reconcorp.getDrSettledAmount() != null) {
                                            cell.setCellValue(reconcorp.getDrSettledAmount().toString());
                                        } else {
                                            cell.setCellValue(0);
                                        }
                                        break;
                                    case 22:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getOutstandingAmount().toString());
                                        break;
                                    case 23:
                                        cell = headerRow.createCell(j);
                                        cell.setCellValue(reconcorp.getRemarks());
                                        break;

                                }//end of switvch
                            }//end of for cloumn loop
                        }//if compare names

                    }//recon list forloop

                }//for corpname loop
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String filedate = simpleDateFormat.format(new Date());
                String filename = "Reconciliation_report_by_" + filedate + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
                workbook.close();
                return null;
            } else {
                ModelAndView mv = new ModelAndView("successMsg");
                mv.addObject("Msg", "List is empty");
                return mv;

            }

        }
        ModelAndView mv = new ModelAndView("downloaddynreconsheet");

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
