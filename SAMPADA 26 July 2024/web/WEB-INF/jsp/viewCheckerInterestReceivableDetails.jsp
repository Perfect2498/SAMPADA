<%--

  Document   : viewCheckerInterestReceivableDetails

  Created on : Nov 20, 2019, 3:17:06 PM

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

            <form name="checkerForm">

             

                <h3 align="center" style="color:#003366;" >Interest Disbursement Details </h3>

                <input type="text" name="corpid" value="${corpid}" hidden="yes"/>

                <table align="center" style="width:97%;" cellspacing="0" class="customerTable">

                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>

                            <th>Week-ID</th>
                            
                            <th>Bill Type </th>
                            
                            <th>Pool Member</th>

                            <th>Billing Date</th>
                            
                            <th>Billing Due Amount</th>
                            
                            <th>No of Days </th>

                            

                            <th>Total Amount</th>

                            

                            <th>Interest Billed Amount</th>

                            <th>Interest Amount</th>
                            
                            <th>Outstanding Amount</th>

                            <th>Interest Paid Amount</th>

                            <th>Interest Pending Amount</th>

                            

                            
                            
                            <th>Remarks </th>

                        </tr>

                    </thead>

                    <tbody>

                        <% int cnt = 0;

                           String interestId="interestId";

                          String interestId1;

                        %>

                        <c:forEach items="${disInterestDisList}" var="ele">

                            <%

                                cnt++;

                                interestId1=interestId+cnt;

                            %>

                            <tr>

                        <input type="text" name="<%=interestId1%>" value="${ele.disbursedInterestDetails.interestId}" hidden="yes"/>

                        <td>${ele.disbursedInterestDetails.weekId}</td>
                        
                        <td>${ele.disbursedInterestDetails.billType}</td>
                        
                        <td>${ele.disbursedInterestDetails.corporates.corporateName}</td>

                        <td>${ele.disbursedInterestDetails.billingDate}</td>
                        
                        <td>${ele.disbursedInterestDetails.billingDuedate}</td>

                        <td>${ele.disbursedInterestDetails.noofdays}</td>

                        <td>${ele.disbursedInterestDetails.billedAmount}</td>

                        

                        <td>${ele.disbursedInterestDetails.interestBilledamount}</td>

                        <td>${ele.disbursedInterestDetails.interestAmount}</td>
                        
                        <td>${ele.interestAmount}</td>

                        <td>${ele.interestPaidamount}</td>

                        <td>${ele.disbursedInterestDetails.interestPendingamount}</td>
                        
                        <td>${ele.remarks}</td>

                        </tr>



                    </c:forEach>

                    </tbody>

                </table>

                <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>

                <p align="center">

                    <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bconfirm"  value="Confirm"/>
                     <input style="width:100px;" class="btn" type="submit" value="&#10060;Delete" onclick="return validate1();"  name="bdelete" /></p>
                </p>
            </form>
                <p>&nbsp;</p>
                  <p>&nbsp;</p>

        </body>

    </html>