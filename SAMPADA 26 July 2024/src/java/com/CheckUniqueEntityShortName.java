/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.EntityDAO;
import sampada.pojo.Corporates;
import sampada.pojo.Entites;

/**
 *
 * @author cdac
 */
public class CheckUniqueEntityShortName extends HttpServlet {

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

            System.out.println("Inside CheckUniqueEntityShortName Servlet: ");
            result = getExistingEntitySnameList(100, request);
            System.out.println("Result is: " + result);
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
public JSONObject getExistingEntitySnameList(int total, HttpServletRequest request)
            throws SQLException, ClassNotFoundException, ParseException, JSONException {

        System.out.println("Inside getExistingEntitySnameList Method: ");

        JSONObject result = new JSONObject();
        String entityName = request.getParameter("entityName");
        
        System.out.println("entityName is " + entityName);
        EntityDAO entityDao = new EntityDAO();
        
        int flag = 0;
        List<Entites> entitesList = entityDao.Entiteslist();
        for(Entites entity : entitesList){
            if(entityName.equalsIgnoreCase(entity.getEntittyName())){
                flag = 1;
            }
        }
        String msg = null;
        if(flag == 1){
            msg = entityName + " is already existing Entity Short Name!! Try some other Entity Short Name.";
        }
        else{
            msg = entityName + " is Unique Entity Short Name!! Go ahead!";
        }
        result.put("msg", msg);

        return result;
    }
}
