/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import sampada.DAO.BankStatementDAO;

/**
 *
 * @author cdac
 */
public class CheckBankStmtExistance extends HttpServlet {

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

                System.out.println("Inside CheckBankStmtExistance Servlet: ");

                result = getBankStmtStatus(100, request);

                System.out.println("Result is: " + result);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
            } catch (SQLException e) {
               // e.printStackTrace();
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

    public JSONObject getBankStmtStatus(int total, HttpServletRequest request)
            throws SQLException, ClassNotFoundException, ParseException, JSONException {

        System.out.println("Inside getAvailableBillsList Method: ");

        JSONObject result = new JSONObject();
        String frmDate = request.getParameter("fromDate");
//        String toDate = request.getParameter("toDate");
//        System.out.println("$$$$$frmDate is " + frmDate);
        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(frmDate);
        System.out.println(frmDate + "\t" + date1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date1);
        System.out.println("strDate is " + strDate);
//        System.out.println("$$$$$toDate is " + toDate);
        BankStatementDAO bankDao = new BankStatementDAO();
        int chkFromDate = bankDao.getViabilityOfFromDate(strDate);
        
        if(chkFromDate > 0){
            String msg = "Verified Bank Statement already exist for selected Start Date!!";
                result.put("msg", msg);
        }
        else{
            String msg = "Start Date is Ok!!";
                result.put("msg", msg);
        }
        return result;

    }

}
