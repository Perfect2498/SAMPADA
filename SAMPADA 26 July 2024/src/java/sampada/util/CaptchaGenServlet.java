/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jagan1933
 */
public class CaptchaGenServlet extends HttpServlet {

      public static final String FILE_TYPE = "jpeg";
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
        
       response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Max-Age", 0);
 
            String captchaStr="";
             captchaStr=Util.generateCaptchaTextMethod2(4);
       try {
 
                int width=100;      int height=40;
 
                Color bg = new Color(0,255,255);
                Color fg = new Color(0,100,0);
 
                Font font = new Font("Arial", Font.ITALIC, 20);
                BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.OPAQUE);
                Graphics g = cpimg.createGraphics();
 
                g.setFont(font);
                g.setColor(bg);
                g.setPaintMode();
                
                g.fillRect(0, 0, width, height);
                g.setColor(fg);
                g.drawString(captchaStr,10,25);   
 
                HttpSession session = request.getSession(true);
                session.setAttribute("CAPTCHA", captchaStr);
 
               OutputStream outputStream = response.getOutputStream();
 
               ImageIO.write(cpimg, FILE_TYPE, outputStream);
               outputStream.close();
 
            } catch (Exception e) {
                    e.printStackTrace();
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

}
