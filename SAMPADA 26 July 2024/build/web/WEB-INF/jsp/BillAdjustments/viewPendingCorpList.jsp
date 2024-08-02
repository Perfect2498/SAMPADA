<%-- 
    Document   : viewPendingCorpList
    Created on : Jan 20, 2021, 10:52:40 AM
    Author     : Administrator
--%>





<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>

<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" >

        <script src="<c:url value="js/jquery-1.9.1.js" />"></script>

        <script src="<c:url value="js/jquery-ui.js" />" ></script>

        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>

        <link type="text/css" href="<c:url value="/jquery.ui.datepicker.monthyearpicker.css"/>" rel="stylesheet" />

        <script type="text/javascript" src="<c:url value="/jquery.ui.datepicker.monthyearpicker.js"/>"></script>

        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/bootstrap.min.js" />" type="text/javascript"></script>

        

    </head>

    <body style="min-height: 500px;">

        <form name="intesdisburselist" method="post">

            <h4 align="center">View Adjustment Transactions done by Maker, pending at Checker End</h4>

            <table align="center" width="200" border="yes">

                <tr>

                    <th>Pool Member</th>

                </tr>

                <c:forEach items="${listcorp}" var="ele">

                    <tr>

                        <td><a href="<c:url value='viewCheckerAdjustmentDetails.htm'>

                               <c:param name="corpID" value="${ele.corporateId}"/>

                                </c:url>" >${ele.corporateName}</a>

                        </td>

                    </tr>

                </c:forEach>

            </table>

        </form>

    </body>







</html>