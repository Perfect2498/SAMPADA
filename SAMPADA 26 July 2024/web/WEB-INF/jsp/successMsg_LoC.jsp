<%-- 
    Document   : successMsg_LoC 
    Created on : Dec 26, 2019, 1:47:39 PM 
    Author     : shubham 
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
         <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/masterleftmenu.css"/>">
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
    <body style="min-height: 800px;"> 
        <h4 color="#" align="center"> <i>${Msg}</i></h4> 
         
         <h2 color="red" align="center"> <i>Payable amount exceeds 150% of present LC amount for the following Corporates:</i></h2> 
         
      <table id="InterestreportDatatable" style="text-align:center;color:#003366; width: 63%;" align="center"  border="yes"> 
            <thead style="min-height: 15px;height: 30px;"> 
                <tr><th>S. No.</th><th>Corporate Name</th></tr> 
            </thead> 
            <tbody> 
                <c:set var="serialno" value="${0}"/> 
                <c:forEach items="${corporates}" var="entity"> 
                    <tr>    
                        <c:set var="serialno" value="${serialno + 1}" /> 
                        <td><c:out value="${serialno}"/></td> 
                        <td>${entity}</td> 
                    </tr> 
                </c:forEach> 
            </tbody> 

        </table> 
                <br><br> 
                 
                <h2 color="blue" align="center"> <i>LC amount for the following Corporates is not Present in System:</i></h2> 
         
      <table id="InterestreportDatatable" style="text-align:center;color:#003366; width: 63%;" align="center"  border="yes"> 
            <thead style="min-height: 15px;height: 30px;"> 
                <tr><th>S. No.</th><th>Corporate Name</th></tr> 
            </thead> 
            <tbody> 
                <c:set var="serialno" value="${0}"/> 
                <c:forEach items="${corporatesNotPresent}" var="entity"> 
                    <tr>    
                        <c:set var="serialno" value="${serialno + 1}" /> 
                        <td><c:out value="${serialno}"/></td> 
                        <td>${entity}</td> 
                    </tr> 
                </c:forEach> 
            </tbody> 

        </table> 
                 
                 
    </body> 
</html> 
