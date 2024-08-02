<%--
    Document   : viewWeeklyRevisedRrasBill
    Created on : Jun 24, 2019, 11:34:42 AM
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
        <title>Revised RRAS Bill Display Page</title>
        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" >
        <script src="<c:url value="js/jquery-1.9.1.js" />"></script>
        <script src="<c:url value="js/jquery-ui.js" />" ></script>
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
        <link type="text/css" href="<c:url value="/jquery.ui.datepicker.monthyearpicker.css"/>" rel="stylesheet" />
        <script type="text/javascript" src="<c:url value="/jquery.ui.datepicker.monthyearpicker.js"/>"></script>
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
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
    <body>
        <form>
            <br/>
            <p style="text-align:center;"><b>WRPC MUMBAI</b></p>
            <c:set var="count" value="${0}" />
            <c:forEach items="${billRrasInfo}" var="ele">
                <c:set var="count" value="${count+1}" />
                <c:if test="${count eq 1}">
                    <p style="text-align:center;"><u>Format-AS7: RRAS Settlement Account for the week <b><fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" /> Issued on: <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" /></u></b></p>
            <p style="text-align:center;" ><b>A. Payments to the RRAS Provider(s) from the DSM Pool for UP Regulation for the week <fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" />
                </b></p>
            </c:if>

    </c:forEach>
    <p style="text-align:right; margin-right: 18px;"><i>(All figures in Rs.)</i></p>
    <c:set var="serialno" value="${0}"/>
    <table align="center" class="customerTable"  style="width:90%;">
        <thead style="height:30px;">
            <tr>
                <!--<th>Corporate </th>-->
                <th>S. No.</th>
                <th>RRAS Provider Name</th>
                <th>Energy Scheduled to VAE under RRAS(MWh)</th>
                <th>Fixed Charges(Rs) (A)</th>
                <th>Varible Charges (B)</th>
                <th>Markup as per CERC Order(Rs) (C)</th>
                <th>Total Charges (A+B+C)</th>
            </tr>

        </thead>
        <tbody>

            <c:forEach items="${billReceive11}" var="objectList">
                <c:set var="count" value="${0}" />
                <tr>
                    <c:set var="serialno" value="${serialno + 1}" />
                    <td><c:out value="${serialno}"/></td>
                    <c:forEach items="${objectList}" var="object">
                        <c:set var="count" value="${count + 1}" />
                        <c:if test="${count ne 2}">
                            <td>${object.toString()}</td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="2">Total</td>
                <c:forEach items="${sumListRecvDiff}" var="objectList">
                    <td>${objectList.toString()}</td>
                </c:forEach>
            </tr>

        </tbody>
    </table>
    <br/>
    <c:set var="count" value="${0}" />
    <c:forEach items="${billRrasInfo}" var="ele">
        <c:set var="count" value="${count+1}" />
        <c:if test="${count eq 1}">
            <p style="text-align:center;"><b>B. Payments by the RRAS Provider(s) to the DSM Pool for DOWN Regulation for the week <fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" />
                </b></p>
            </c:if>

    </c:forEach>
    <c:set var="serialno" value="${0}"/>
    <table align="center" class="customerTable"  style="width:90%;">
        <thead style="height:30px;">
            <tr>
                <th>S. No. </th>
                <th>RRAS Provider Name</th>
                <th>Energy Scheduled to VAE under RRAS(MWh)</th>
                <th>Total Variable Charges for generation reduced (Rs) (A)</th>
                <th>Varible Charges to be paid to the DSM Pool(Rs)(B=75% of A)</th>
            </tr>
        </thead>
        <tbody>


            <c:forEach items="${billPay11}" var="objectList">
                <c:set var="count" value="${0}" />
                <tr>
                    <c:set var="serialno" value="${serialno + 1}" />
                    <td><c:out value="${serialno}"/></td>

                    <c:forEach items="${objectList}" var="object" >
                        <c:set var="count" value="${count + 1}" />
                        <c:if test="${count ne 2}">
                            <td>${object.toString()}</td>
                        </c:if>

                    </c:forEach>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="2">Total</td>
                <c:forEach items="${sumListPayDiff}" var="objectList">
                    <td>${objectList.toString()}</td>
                </c:forEach>
            </tr>


        </tbody>
    </table>
    <br/>
</form>
<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
<br/>
</body>
</html>


