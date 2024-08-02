<%--
    Document   : login
    Created on : Jun 17, 2019, 9:37:38 AM
    Author     : JaganMohan
#FFFBD0
#003366
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >       
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" >
        <script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/bootstrap.min.js" />" type="text/javascript"></script>
       

    </head>
    <body style="background-color:#FBF2EA;font-size: 14px;">
        <form name="loginform" method="post"  action="loginDetails.htm">

            <div class="container" style="background-color: #44B3C2;width:40%;color:white;border-radius: 25px;border-color: white;">
                <h3 align="center" style="font-family:Kaushan Script;font-size: 19px;">Welcome</h3>
                <p align="center">${msg}</p>
                <table align="center" class="table table-bordered" style="width:100%;font-family:Kaushan Script;font-size: 19px; ">

                    <tr>
                        <td><img src="${pageContext.request.contextPath}//images/user_img.jpg" width="30" height="30" style="border-radius: 50%;"/>User ID*</td>
                        <td><input type="text" name="loginname" placeholder="User ID"  style="color:#003366;" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td><img src="${pageContext.request.contextPath}//images/password_img.jpg" width="30" height="30" />Password*</td>
                        <td><input type="password" name="password" placeholder="Password" style="color:#003366;" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td>Captcha</td>
                        <td><img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
                            <a href="javascript:;"
                               title="change captcha text"
                               onclick="document.getElementById('captcha_id').src = 'captcha.jpg?' + Math.random();
                                       return false">
                                <img src="images/refresh_image.png" width="30" height="30" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size: 19px;">Enter Captcha Code</td>
                        <td><input type="text" style="color: #003366;" id="captcha" name="captcha" /></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input style="background-color: #B3B3D7;font-size: 14px;" type="submit" style="font-size: 19px;" class="myButton" name="bname" value="Sign In"/></td>
                    </tr>
                </table>
            </div>
        </form>
    </body>
</html>
