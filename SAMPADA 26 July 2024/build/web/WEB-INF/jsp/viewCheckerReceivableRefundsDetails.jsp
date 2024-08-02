<%--

    Document   : viewCheckerReceivableRefundsDetails

    Created on : Nov 11, 2019, 2:24:09 PM

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

                    //  alert(count);



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

                <p>&nbsp;</p>



                <h3 align="center" style="color:#003366;" >Refund Details </h3>

                <input type="text" name="corpid" value="${corpid}" hidden="yes"/>

                <table align="center" style="width:80%;" class="customerTable">

                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>

                            <th>Billing Date</th>

                            <th>Week-ID</th>

                            <th>Pool Member</th>

                            <th>Total Amount</th>

                            <th>Refund Amount</th>

                            <th>Bill Type </th>

                            <th>Category </th>



                        </tr>

                    </thead>

                    <tbody>

                        <% int cnt = 0;%>

                        <c:forEach items="${temprefundList}" var="ele">

                            <%

                                cnt++;

                            %>



                            <c:if test="${ele.billReceiveCorp.uniqueId != null}">



                                <tr>

                                    <td>${ele.billReceiveCorp.billingDate}</td>

                                    <td>${ele.billReceiveCorp.weekId}</td>

                                    <td>${ele.corporates.corporateName}</td>

                                    <td>${ele.billReceiveCorp.toalnet}</td>

                                    <td>${ele.totalAmount}</td>

                                    <td>${ele.billReceiveCorp.billType}</td>

                                    <td>${ele.billReceiveCorp.billCategory}</td>

                                </tr>

                            </c:if>



                            <c:if test="${ele.billPayableCorp.uniqueId != null}">



                                <tr>

                                    <td>${ele.billPayableCorp.billingDate}</td>

                                    <td>${ele.billPayableCorp.weekId}</td>

                                    <td>${ele.corporates.corporateName}</td>

                                    <td>${ele.billPayableCorp.totalnet}</td>

                                    <td>${ele.totalAmount}</td>

                                    <td>${ele.billPayableCorp.billType}</td>

                                    <td>${ele.billPayableCorp.billCategory}</td>

                                </tr>

                            </c:if>



                        </c:forEach>

                    </tbody>

                </table>

                <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>

                <p align="center">

                    <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bconfirm"  value="Confirm"/>

                    <input style="width:100px;" class="btn" onclick="return validate1();" type="submit" name="bcancel"  value="Delete"/>

                </p>



            </form>
            <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><br/>
        </body>

    </html>