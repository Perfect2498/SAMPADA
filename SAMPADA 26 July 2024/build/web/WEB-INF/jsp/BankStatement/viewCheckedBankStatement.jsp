<%-- 
    Document   : viewCheckedBankStatement
    Created on : Aug 6, 2019, 2:47:21 PM
    Author     : cdac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checked Bank Statement List</title>
     <script>
            function getDocHeight(doc) {
                doc = doc || document;
                var body = doc.body, html = doc.documentElement;
                var height = Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);
                return height;
            }
            function setIframeHeight(id) {
                var ifrm = document.getElementById(id);
                var doc = ifrm.contentDocument ? ifrm.contentDocument : ifrm.contentWindow.document;
                ifrm.style.visibility = 'hidden';
                ifrm.style.height = "10px"; // reset to minimal height in case going from longer to shorter doc
                ifrm.style.height = getDocHeight(doc) + 5 + "px";
                ifrm.style.visibility = 'visible';
                var iframe = window.parent.document.getElementById('content');
                var container = document.getElementById('appcontent');
                iframe.style.height = container.offsetHeight + 50 + 'px';
            }
            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 30 + 'px';
            }
        </script>
    </head>
       <body style="text-align: center; alignment-adjust: central;width: 95%;min-height: 500px;">
         <c:set var="serialno" value="${0}" />
         <h3 style="alignment-adjust: central; color:#003366;">Verified Bank Statement List</h3>
          <table align="center" class="customerTable"  style="width:50%;">
            <thead height:30px;">          
                   <tr>
                    <!--<th>Corporate </th>--> 
                    <th>S. No.</th>
                    <th>From Date</th>
                </tr>

            </thead>
            <tbody>   

            <c:forEach items="${stmtInfoList}" var="ele">
                <tr>
                <c:set var="serialno" value="${serialno + 1}" />
                <td><c:out value="${serialno}"/></td>
               
                <fmt:formatDate value="${ele}" pattern="dd-MM-yyyy" var="newdatevar" />
                
                <td>
                    <a href="<c:url value='viewCheckedBankStmtDetailsByFromDate.htm'> 
                         <c:param name="selecteddate" value="${ele}"/></c:url>" >${newdatevar}</a>
                    </td>
               
                </tr>
            </c:forEach>
        </tbody>  
    </table> 

    <br/><br/>
</body>
</html>

