<%--

    Document   : viewCorporateRefundReceivableList

    Created on : Nov 6, 2019, 3:05:35 PM

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

        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" >

        <script src="<c:url value="js/jquery-1.9.1.js" />"></script>

        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>

        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>



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

    <body>

        <form>

            <h4 align="center">List of Pool Member for Refund - Disbursement</h4>

            <p align="center">

                <select id="corparateID" name="corparateID" >

                    <option value="-1">select</option>



                    <c:forEach items="${refundList}" var="ele">



                        <c:choose>

                            <c:when test="${empty temprefundList}">

                                <option value="${ele.corporateId}">${ele.corporateName}</option>

                            </c:when>

                            <c:otherwise>

                                <c:forEach items="${temprefundList}" var="ele1">

                                    <c:if test="${ele.corporateId != ele1.corporates.corporateId}">

                                        <option value="${ele.corporateId}">${ele.corporateName}</option>

                                    </c:if>

                                </c:forEach>

                            </c:otherwise>

                        </c:choose>



                    </c:forEach>

                </select>

                <input class="btn" type="submit" name="bname" value="Submit" onclick="return validate();" />

            </p>

            <p>&nbsp;</p> <p>&nbsp;</p>

        </form>
        <p>&nbsp;</p> <p>&nbsp;</p>

    </body>

</html>