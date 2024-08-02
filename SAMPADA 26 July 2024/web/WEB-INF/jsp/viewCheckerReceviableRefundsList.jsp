
<%--

    Document   : viewCheckerReceviableRefundsList

    Created on : Nov 11, 2019, 12:54:28 PM

    Author     : JaganMohan

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

        <script>

            function validate()

            {

                var corp = document.getElementById("corparateID").value;

                if (corp == -1)

                {

                    alert('Please select the corpoarte ..');

                    return false;

                }

                return true;

            }

        </script>

    </head>

    <body style="min-height: 500px;">

        <form>

            <h4 align="center">List of Pool Member for Refund Checker</h4>

            <table align="center" width="200" border="yes">

                <tr>

                    <th>Pool Member</th>

                </tr>

                <c:forEach items="${temprefundList}" var="ele">

                    <tr>

                        <td><a href="<c:url value='viewCheckerReceivableRefundsDetails.htm'>

                               <c:param name="corpID" value="${ele.corporateId}"/>

                                </c:url>" >${ele.corporateName}</a>

                        </td>

                    </tr>

                </c:forEach>

            </table>

        </form>

    </body>

</html>