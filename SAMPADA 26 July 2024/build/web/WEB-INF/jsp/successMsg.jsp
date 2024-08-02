<%-- 
    Document   : successMsg
    Created on : Jun 17, 2019, 9:54:34 AM
    Author     : JaganMohan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style="min-height: 500px;">
       <h4 color="#" align="center"> <i><span  style='font-size:25px;'>&#128077;</span>${Msg}</i></h4>
       
       <c:if test="${flag==999}">
           <br>
           <table border="1" align="center" cellpadding="10" cellspacing="0">
               <thead>
                <th>Corporate Name</th>
               </thead>
               <tbody>
                   <c:forEach items="${pendingAtCheckerCorps}" var="ele">
                       <tr align="center">
                           <td>${ele}</td>
                       </tr>
                   </c:forEach>
               </tbody>
           </table>
       </c:if>
       
    </body>
</html>
