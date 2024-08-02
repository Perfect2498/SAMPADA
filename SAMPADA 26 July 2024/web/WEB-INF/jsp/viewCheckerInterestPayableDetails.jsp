<%--



    Document   : viewCheckerInterestPayableDetails



    Created on : Nov 21, 2019, 2:07:57 PM



    Author     : JaganMohan



--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>

        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>
            <script>
                function validate()
                {
                    var rowcnt = document.getElementById("count").value;
                    if (rowcnt == 0)
                    {
                        return false;
                    }
                    else
                    {
                        if (confirm("Are you want to Confirm !!!!!"))
                        {
                            return true;
                        }
                        else
                        {
                            return false;

                        }
                    }

                }


                function validate1()
                {
                    var rowcnt = document.getElementById("count").value;
                    //   alert(count);
                    if (rowcnt == 0)
                    {
                        return false;
                    }
                    else
                    {
                        if (confirm("Are you want to Delete !!!!!"))
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }

                }
            </script>
        </head>
        <body width="80%">
            <form name="checkerForm" cellspacing="0">
                <h3 align="center" style="color:#003366;" >Interest Mapping  Details </h3>
                <input type="text" name="corpid" value="${corpid}" hidden="yes"/>
                <table align="center" style="width: 90%;" class="customerTable" cellspacing="0">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Week-ID</th>
                            <th>Pool Member</th>
                            <th>Billing Date</th>
                            <th>Billing Due Date</th>
                            <th>Paid Date</th>
                            <th>Total Amount</th>
                            <th>Mapped Amount</th>
                            <th>Mapped Date</th>
                            <th>Interest Billed Amount</th>
                            <th>No of Days</th>
                            <th>Interest Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% int cnt = 0;%>
                        <c:forEach items="${interestCheckerList}" var="ref">
                            <%

                                cnt++;


                            %>
                            <tr>
                                 <td>${ref.interestDetails.weekId}</td>
                                <td>${ref.interestDetails.corporates.corporateName}</td>
                                <td>${ref.interestDetails.billingDate}</td>

                                <td>${ref.interestDetails.billingDuedate}</td>

                                <td>${ref.bankStatement.amountDate}</td>
                                <td>${ref.interestDetails.billedAmount}</td>
                                <td>${ref.mappedAmount}</td>
                                <td>${ref.interestDetails.interestPaiddate}</td>
                                <td>${ref.interestDetails.interestBilledamount}</td>
                                <td>${ref.interestDetails.noofdays}</td>
                                <td>${ref.interestDetails.interestAmount}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <p>&nbsp;</p>

                <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>
                <p align="center">
                    <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bconfirm"  value="Confirm"/> &emsp;
                    <input style="width:100px;" class="btn" type="submit" value="&#10060;Delete" onclick="return validate1();"  name="bdelete" /></p>

                </p>
            </form>


            <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>
        </body>



    </html>