<%-- 
    Document   : login
    Created on : Jun 17, 2019, 9:37:38 AM
    Author     : JaganMohan
#FFFBD0
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
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">           
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" > 
        <script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/bootstrap.min.js" />" type="text/javascript"></script>  
       <link href="https://fonts.googleapis.com/css?family=Kaushan+Script|Lobster|Rock+Salt|Satisfy&display=swap" rel="stylesheet"> 
      
    </head>
    <body style="background-color:#FBF2EA;">
        <form name="loginform" method="post">
            <div class="container" style="background-color: #003366;width:40%;color:white;border-radius: 25px;border-color: white;">
                <h3 align="center">Welcome</h3>
            <table align="center" class="table table-bordered" style="width:100%;font-family:Kaushan Script;">
               
                <tr>
                    <td><img src="${pageContext.request.contextPath}//images/user_img.jpg" width="30" height="30" style="border-radius: 50%;"/></td>
                    <td><input type="text" name="loginname" placeholder="User ID"  />User ID*</td>
                </tr>              
                 <tr>
                     <td><img src="${pageContext.request.contextPath}//images/password_img.jpg" width="30" height="30" /></td>
                    <td><input type="text" name="password" placeholder="Password" />Password*</td>
                </tr>              
                 <tr>
                     <td>&nbsp;</td>
                     <td><input style="background-color: #B3B3D7;" type="submit" class="btn" name="bname" value="Sign In"/></td>
                </tr>
            </table>     
            </div>
        </form>
    </body>
</html>
