<%-- 
    Document   : viewutillist
    Created on : Apr 20, 2017, 10:12:00 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

<!--       <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">-->

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
    <body style="background-color:#FBF2EA;">
        <!--        <div style="color:#8B008B;">
                    Masters &nbsp;>&nbsp;Utilities &nbsp;>&nbsp; <b>View Utility Details</b>
                </div><br>-->
        <br><br>
        <p align="center" style="font-size: 16px;color:#003366;"><b>View Letter Of Credit Details</b></p>
        <!--        <table id="viewUtilityList" align="center" style="width:80%;margin-top:4em;" class="masterstable" >-->
        <table  id="viewUtilityList" border="1" align="center" width="90%" border="yes" class="customerTable">
            <c:forEach items ="${lclist}" var="details">



                <!--                <tr><td colspan="2" align="center" style="background-color: #0099CC; font-size:100%;color:white"><b>View Utilities</b></td></tr>-->
                <tr><td>Constituent </td><td><c:out value="${details.getConstituent()}"/></td></tr>
                <tr><td>Last FY Avrg </td><td><c:out value="${details.getLastFyAvg()}"/></td></tr>
                <tr><td>LC Condition</td><td><c:out value="${details.getLcCondition()}"/></td></tr>
                <tr><td>LC Issuing Bank</td><td><c:out value="${details.getLcIssueBank()}"/></td></tr>
                <tr><td>LC Issuing Branch</td><td><c:out value="${details.getLcIssueBranch()}"/></td></tr>
                <tr><td>LC Amount</td><td><c:out value="${details.getLcAmount()}"/></td></tr>
                <tr><td>Financial Year</td><td><c:out value="${details.getFinYear()}"/></td></tr>
                <tr><td>LC Start Date</td><td><fmt:formatDate pattern="dd-MMM-yyyy" value="${details.getLcFromdate()}"/></td></tr>
                <tr><td>LC End Date</td><td><fmt:formatDate pattern="dd-MMM-yyyy" value="${details.getLcTodate()}"/></td></tr>


                <tr><td>Remarks</td><td><c:out value="${details.getRemarks()}"/></td></tr>
                <tr> 
                    <td>Outdated</td> 
                    <c:if test="${(details.getExpFlag()==1)}"> 

                        <td>Yes</td> 

                    </c:if> 
                    <c:if test="${(details.getExpFlag()==0)}"> 
                        <td>No</td> 
                    </c:if> 
                </tr>


                <tr><td> Lc File</td><td><a href="<c:url value='viewDownloadLcFiles.htm'><c:param name="lcfilename" value="${details.getFileName()}"/></c:url>">${details.getFileName()}</a></td></tr>



            </c:forEach>

        </table>
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
    </body>

</html>