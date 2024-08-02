/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;
import sampada.DAO.ConstantsMasterDAO;
import sampada.DAO.CorporatesDAO;
import sampada.DAO.LoginDAO;
import sampada.DAO.UserDetailsDAO;
import sampada.pojo.UserDetails;
import sampada.util.DesEncrypter;

/**
 *
 * @author JaganMohan
 */
public class loginController extends MultiActionController {

    public ModelAndView loginPage(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

//        String bname=request.getParameter("bname");
//        if(bname!=null)
//        {
//            String loginname=request.getParameter("loginname");
//            ModelAndView mv = new ModelAndView("sysadminIndex");
//            mv.addObject("loginID","Jagan");
//            return mv;
//        }
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }


    public ModelAndView contact(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        ModelAndView mv = new ModelAndView("contact");
        return mv;
    }

    public ModelAndView aboutus(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        ModelAndView mv = new ModelAndView("aboutus");
        return mv;
    }

    public ModelAndView viewDownloadHelpFiles(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String filename = "SAMPADA_USER_MANUAL";
        //String filename=request.getParameter("filename");   
        //  ServletContext context = request.getServletContext();
        //  String filepath = context.getInitParameter("file-upload");   
        // System.out.print("File path "+filepath); 

        ConstantsMasterDAO constadao = new ConstantsMasterDAO();
        String filepath = constadao.getFilePathbyName("HELP_FILE");

        filepath = filepath + filename + ".pdf";//"C:\\OnlineABTFiles\\Village Culture.pdf";
        File file = new File(filepath, URLDecoder.decode(filepath, "UTF-8"));

        response.setContentType("application/pdf");

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

        return null;
    }

    public ModelAndView loginDetails(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ParseException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }

        HttpSession session = request.getSession(true);
        String bname = request.getParameter("bname");
        System.out.println("bname is " + bname);
        if (bname != null) {

            String captcha = request.getParameter("captcha");
            String Imgcaptcha = (String) session.getAttribute("CAPTCHA");
            System.out.print("image scpatha" + Imgcaptcha);
            System.out.print("Manuala  scpatha" + captcha);
            if (!(captcha.equals(Imgcaptcha))) {
                return new ModelAndView(new RedirectView("loginPage.htm"));

            }
            CorporatesDAO crpdao = new CorporatesDAO();
            UserDetailsDAO uddao = new UserDetailsDAO();
            String uname = request.getParameter("loginname");
            String psw = request.getParameter("password");
            System.out.println("uname " + uname);
            //System.out.println("psw "+psw);   
            DesEncrypter encrypter = new DesEncrypter();
            String encryptedPWD = encrypter.encrypt(psw);
            List<UserDetails> list11;
            LoginDAO logindao = new LoginDAO();
            list11 = logindao.checkLoginDetails(uname, encryptedPWD);  
 
            if (list11 == null) {
                ModelAndView mv3 = new ModelAndView("successMsg");
                String msg = "Kindly check database connectivity and Restart Glassfish Server";
                mv3.addObject("Msg", msg);
                return mv3;
            }

            System.out.print("size of login" + list11.size());
            if (list11.size() > 0) {
                int corpID = crpdao.getCorpIdbyUserName(uname);
                String corpname = crpdao.geCorpNamebyId(corpID);
                System.out.println("Corporate corpname is: " + corpname);
                String corporateID = Integer.toString(corpID);
                String usertype = list11.get(0).getUserType();

                if (usertype.equalsIgnoreCase("WRLDC")) {
                    System.out.println("usertype is WRLDC!!");
                    ModelAndView mv2 = new ModelAndView("sysadminIndex");
                    mv2.addObject("loginID", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("loginid", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("corpName", corpname);
                    return mv2;

                }
                if (usertype.equalsIgnoreCase("CORPORATE")) {
                    System.out.println("usertype is CORPORATE!!");
                    ModelAndView mv2 = new ModelAndView("Applicant/applicantIndex");
                    mv2.addObject("loginID", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("loginid", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("corpName", corpname);
                    request.getSession(false).setAttribute("corporateid", corpID);

                    return mv2;

                }

                if (usertype.equalsIgnoreCase("CHECKER")) {
                    System.out.println("usertype is CHECKER!!");
                    ModelAndView mv2 = new ModelAndView("checkerIndex");
                    mv2.addObject("loginID", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("loginid", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("corpName", corpname);

                    return mv2;

                }

                if (usertype.equalsIgnoreCase("MAKER")) {
                    System.out.println("usertype is MAKER!!");
                    ModelAndView mv2 = new ModelAndView("makerIndex");
                    mv2.addObject("loginID", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("loginid", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("corpName", corpname);

                    return mv2;

                }
                if (usertype.equalsIgnoreCase("FINANCE")) {
                    System.out.println("usertype is FINANCE!!");
                    ModelAndView mv2 = new ModelAndView("financeindex");
                    mv2.addObject("loginID", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("loginid", list11.get(0).getLoginId());
                    request.getSession(false).setAttribute("corpName", corpname);

                    return mv2;

                }

            } else {
                ModelAndView mv = new ModelAndView("login");

                mv.addObject("msg", "Authentication failed!!  Please provide valid credentials.");

                return mv;

            }

        }

        return null;

    }

    public ModelAndView sysadminIndex(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String loginname = request.getParameter("loginname");
        ModelAndView mv = new ModelAndView("sysadminIndex");
        mv.addObject("loginID", "Jagan");
        return mv;

    }

    public ModelAndView financeindex(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String loginname = request.getParameter("loginname");
        ModelAndView mv = new ModelAndView("financeindex");
        mv.addObject("loginID", "Jagan");
        return mv;

    }

    public ModelAndView makerIndex(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String loginname = request.getParameter("loginname");
        ModelAndView mv = new ModelAndView("makerIndex");
        mv.addObject("loginID", "Jagan");
        return mv;

    }

    public ModelAndView logout(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        System.out.println("**********session is " + session);
        int trackID = 0;
        if (session != null) {
            System.out.println("##################session NOT NULL and value is " + session);
            //    trackID = (Integer) request.getSession().getAttribute("usertrack_id");  
            //   System.out.println("trackID is "+trackID);
            Date todaydate = new Date();
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            String sTodayDate = formatter2.format(todaydate);
            System.out.println("sTodayDate " + sTodayDate);
            session.invalidate();
        }

        return new ModelAndView(new RedirectView("loginPage.htm"));
    }

    public ModelAndView applicantIndex(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session1 = request.getSession(false);
        if (session1 == null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/logout.htm");
            return new ModelAndView(redirectView);
        }
        String loginname = request.getParameter("loginname");
        ModelAndView mv = new ModelAndView("Applicant/applicantIndex");
        mv.addObject("loginID", "Jagan");
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
