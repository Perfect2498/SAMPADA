<%-- 
    Document   : viewBankStatement
    Created on : Jul 19, 2019, 12:17:18 PM
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
        <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
        <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
        <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/2.10.6/moment.min.js"></script>
      
        <title>View Bank Statement</title>
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
         <h3 style="alignment-adjust: central; color:#003366;">Non Verified Bank Statement </h3>
         <c:set var="serialno" value="${0}" />
         
          <table align="center" class="customerTable"  style="width:50%;">
            <thead height:30px;">          
                   <tr>
                    <!--<th>Corporate </th>--> 
                    <th>S. No.</th>
                    <th>Transaction Start Date</th>
                </tr>

            </thead>
            <tbody>   

            <c:forEach items="${stmtInfoList}" var="ele">
                <tr>
                <c:set var="serialno" value="${serialno + 1}" />
                <td><c:out value="${serialno}"/></td>
               
                <fmt:formatDate value="${ele}" pattern="dd-MM-yyyy" var="newdatevar" />
                
                <td>
                    <a href="<c:url value='viewBankStmtDetailsByFromDate.htm'> 
                         <c:param name="selecteddate" value="${ele}"/></c:url>" >${newdatevar}</a>
                    </td>
               
                </tr>
            </c:forEach>
        </tbody>  
    </table> 

    <br/><br/>
</body>
</html>
