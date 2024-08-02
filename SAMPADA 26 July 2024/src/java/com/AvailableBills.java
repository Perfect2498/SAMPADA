/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sampada.DAO.BillAgcDetailsDAO;
import sampada.DAO.BillDsmDetailsDAO;
import sampada.DAO.BillFrasDetailsDAO;
import sampada.DAO.BillRrasDetailsDAO;
import sampada.DAO.BillTRASEDetailsDAO;
import sampada.DAO.BillTRASMDetailsDAO;
import sampada.DAO.BillTRASSDetailsDAO;
import sampada.DAO.TempMappingBillBankDAO;
import sampada.DAO.TempPaymentDisbursementDAO;

/**
 *
 * @author cdac
 */
public class AvailableBills extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            response.setHeader("Cache-control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");
            JSONObject result = new JSONObject();

            try {

                System.out.println("Inside AvailableBills Servlet: ");

                result = getAvailableBillsList(100, request);

                System.out.println("Result is: " + result);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
            } catch (SQLException e) {
                //e.printStackTrace();
            }

//                 response.setContentType("application/json");
//                response.setHeader("Cache-Control", "no-store");
            out.print(result.toString());
        } catch (Exception e) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public JSONObject getAvailableBillsList(int total, HttpServletRequest request)
            throws SQLException, ClassNotFoundException, ParseException, JSONException {

        System.out.println("Inside getAvailableBillsList Method: ");

        JSONObject result = new JSONObject();
        String weekId = request.getParameter("weekId");
        String date1 = request.getParameter("year");
        String billType = request.getParameter("billType");
        System.out.println("$$$$$weekId is " + weekId);
        System.out.println("$$$$$$date1 is " + date1);
        System.out.println("billType is " + billType);
//        String[] splitStr = date1.split("-");
//        System.out.println(splitStr.length);
//        String year = splitStr[0];
        String year = date1;

//        System.out.println("$$$$$$year is " + year);
        if (billType.equalsIgnoreCase("DSM")) {
            BillDsmDetailsDAO billDsmDetailDao = new BillDsmDetailsDAO();
            List<Object[]> availBills = billDsmDetailDao.getAvailDsmBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year));
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "DSM");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "DSM");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
        if (billType.equalsIgnoreCase("RRAS PAYABLE")) {
            BillRrasDetailsDAO billRrasDetailDao = new BillRrasDetailsDAO();
            List<Object[]> availBills = billRrasDetailDao.getAvailRrasBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year), "PAYABLE");
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "RRAS");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "RRAS");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
        if (billType.equalsIgnoreCase("RRAS RECEIVABLE")) {
            BillRrasDetailsDAO billRrasDetailDao = new BillRrasDetailsDAO();
            List<Object[]> availBills = billRrasDetailDao.getAvailRrasBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year), "RECEIVABLE");
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "RRAS");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "RRAS");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
        if (billType.equalsIgnoreCase("AGC")) {
            BillAgcDetailsDAO billAgcDetailsDAO = new BillAgcDetailsDAO();
            List<Object[]> availBills = billAgcDetailsDAO.getAvailAgcBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year));
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "AGC");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "AGC");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
        
         if (billType.equalsIgnoreCase("SRAS")) {
            BillAgcDetailsDAO billAgcDetailsDAO = new BillAgcDetailsDAO();
            List<Object[]> availBills = billAgcDetailsDAO.getAvailSrasBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year));
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "SRAS");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "SRAS");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
         
         if (billType.equalsIgnoreCase("TRASM")) {
            BillTRASMDetailsDAO billTRASMDetailsDAO = new BillTRASMDetailsDAO();
            List<Object[]> availBills = billTRASMDetailsDAO.getAvailTRASMBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year));
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "TRASM");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "TRASM");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
         
         if (billType.equalsIgnoreCase("TRASS")) {
            BillTRASSDetailsDAO billTRASSDetailsDAO = new BillTRASSDetailsDAO();
            List<Object[]> availBills = billTRASSDetailsDAO.getAvailTRASSBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year));
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "TRASS");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "TRASS");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }

           if (billType.equalsIgnoreCase("TRASE")) {
            BillTRASEDetailsDAO billTRASEDetailsDAO = new BillTRASEDetailsDAO();
            List<Object[]> availBills = billTRASEDetailsDAO.getAvailTRASEBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year));
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();
                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "TRASE");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "TRASE");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
        if (billType.equalsIgnoreCase("FRAS")) {
            BillFrasDetailsDAO billFrasDetailDao = new BillFrasDetailsDAO();
            List<Object[]> availBills = billFrasDetailDao.getAvailFrasBillListByWeekYear(new BigDecimal(weekId), new BigDecimal(year));
            System.out.println("availBills " + availBills);
            //if(availBills != null || !(availBills.isEmpty())){
            if (!availBills.isEmpty()) {
                System.out.println("availBills size " + availBills.size());
                List<Object[]> results = availBills;
                JSONArray array = new JSONArray();
                for (Object[] availBill : results) {
                    JSONArray ja = new JSONArray();
                    String week = availBill[0].toString();
                    String revNo = availBill[1].toString();
                    String billYear = availBill[2].toString();
                    String billDue = availBill[3].toString();
                    String billIssue = availBill[4].toString();

                    ja.put(week);
                    ja.put(revNo);
                    ja.put(billYear);
                    ja.put(billIssue);
                    ja.put(billDue);
                    String billUploadDate = availBill[5].toString();
                    ja.put(billUploadDate);
                    array.put(ja);
                    //System.out.println("week is " + week + " revNo is " + revNo + " billYear " + billYear + " billIssue " + billIssue + " billDue " + billDue);

                }

                TempPaymentDisbursementDAO tempPayDisburseDao = new TempPaymentDisbursementDAO();
                int disbursePendingCount = tempPayDisburseDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "FRAS");
                TempMappingBillBankDAO tempMapBillBankDao = new TempMappingBillBankDAO();
                int payPendingCount = tempMapBillBankDao.getPendingCheckerCountByWeekId(Integer.parseInt(weekId), "FRAS");
                //String msg = "Previous bills available for the week and year!!";
                System.out.println("disbursePendingCount is " + disbursePendingCount);
                System.out.println("payPendingCount is " + payPendingCount);
                String msg = null;
                if (disbursePendingCount != 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to clear non verified mappings for the same week!!";

                } else if (disbursePendingCount != 0 && payPendingCount == 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Disbursement Side for the same week!!";

                } else if (disbursePendingCount == 0 && payPendingCount != 0) {
                    msg = "Kindly request Checker to either verify or delete the non-verified mappings in the Payable Side for the same week!!";

                }
                result.put("availBillInfo", array);
                result.put("msg", msg);

            } else {
                String msg = "No previous bills available for the week and year!!";
                result.put("msg", msg);
            }
        }
        return result;
    }

}
