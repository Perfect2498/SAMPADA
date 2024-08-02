<%--

    Document   : viewPendingMakerDisbursement

    Created on : Jul 5, 2019, 2:39:51 PM

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

        <script>

            $(document).ready(function () {

               var table = $('#bankStmt').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'collection',
                            text: 'Export',
                            buttons: [
                                'excel',
                                'pdf'



                            ]

                        }

                    ]

                });
                
                var table = $('#refundTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'collection',
                            text: 'Export',
                            buttons: [
                                'excel',
                                'pdf'

                            ]

                        }

                    ]

                });

            });

        </script>



    </head>

    <body style="text-align: center; alignment-adjust: central;width: 95%;">

        <form name="checkerForm">

             <h3 align="center" style="color:#003366;" >Disbursement Details - Maker </h3>



            <table id="bankStmt" align="center" style="width:90%;" class="customerTable">

                <thead style="background-color: #0677A1;color: white;height:72px;">

                    <tr>

                        <th>Billing Date</th>

                        <th>Week-ID</th>

                        <th>Commercial Group</th>

                        <th>Total Amount</th>

                        <th>Pending Amount</th>

                        <th>Disburse Amount</th>

                        <!--<th>Balance Amount</th>-->

                        <th>Disburse Date</th>

                        <th>Bill Type</th>

                        <th>Category</th>

                    </tr>

                </thead>

                <tbody>

                    <% int cnt = 0;%>

                    <c:forEach items="${pendingList}" var="ele">

                        <%

                            cnt++;

                        %>

                        <tr>

                            <td>${ele.billingDate}</td>

                            <td>${ele.weekId}</td>

                            <td>${ele.corporates.corporateName}</td>

                            <td>${ele.totalAmount}</td>

                            <td>${ele.pendingAmount}</td>

                            <td>${ele.disburseAmount}</td>

                            <!--<td>${ele.pendingAmount-ele.disburseAmount}</td>-->

                            <td>${ele.disbursalDate}</td>

                            <td>${ele.billType}</td>

                            <td>${ele.disburseCategory}</td>



                        </tr>



                    </c:forEach>

                </tbody>

            </table>

            <br>



            <h3 align="center" style="color:#003366;">Refund Disbursement Details </h3>



            <table align="center" id="refundTable" style="width:80%;" class="customerTable">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Billing Date</th>

                        <th>Week-ID</th>

                        <th>Commercial Group</th>

                        <th>Total Amount</th>

                        <th>Pending Amount</th>

                        <th>Disburse Amount</th>

                        <th>Disburse Date</th>

                        <th>Bill Type</th>

                        <th>Revision No.</th>

                        <th>Category</th>

                    </tr>

                </thead>

                <tbody>

                    <% int cnt1 = 0;%>

                    <c:forEach items="${refundPendList}" var="ele1">

                        <%

                            cnt1++;

                        %>

                        <tr>

                            <td>${ele1.billPayableCorp.billingDate}</td>

                            <td>${ele1.weekid}</td>

                            <td>${ele1.corporates.corporateName}</td>

                            <td>${ele1.totalAmount}</td>

                            <td>${ele1.pendingAmount}</td>

                            <td>${ele1.paidAmount}</td>

                            <td>${ele1.refundDate}</td>

                            <td>${ele1.billPayableCorp.billType}</td>

                            <td>${ele1.billPayableCorp.revisionNo}</td>

                            <td>${ele1.billPayableCorp.billCategory}</td>

                        </tr>



                    </c:forEach>

                </tbody>

                <tfoot>

                    <tr>

                        <th style="text-align:right">Total:</th>

                        <th>&nbsp;</th>

                        <th>&nbsp;</th>

                        <th>&nbsp;</th>

                        <th></th>

                        <th></th>

                        <th></th>

                        <th>&nbsp;</th>

                        <th>&nbsp;</th>

                        <th>&nbsp;</th>


                    </tr>

                </tfoot>

            </table>

            <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>
     
            <p>&nbsp;</p>
            <p>&nbsp;</p>
        
        </form>
   <p>&nbsp;</p>
            <p>&nbsp;</p>
   <p>&nbsp;</p>
            <p>&nbsp;</p>
    </body>
<p>&nbsp;</p>
   <p>&nbsp;</p>
            <p>&nbsp;</p>
</html>