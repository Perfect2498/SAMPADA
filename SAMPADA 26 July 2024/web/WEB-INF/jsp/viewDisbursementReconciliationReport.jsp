<%--

   Document   : viewDisbursementReconciliationReport

   Created on : Dec 4, 2019, 4:59:49 PM

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

                alert(rowcnt);





                if (rowcnt == 0)

                {

                    return false;

                }

                else

                {

                    if (confirm("Are you want to Delete !!!!!"))

                    {

                        return false;

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

                var table = $('#billtable').DataTable({
                    responsive: true,
                    "order": [[0, "asc"]],
                    "lengthMenu": [[-1], ["All"]],
                    dom: 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            orientation: 'landscape'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            orientation: 'landscape'
                        }
                    ],
                    "footerCallback": function (row, data, start, end, display) {
                        var api = this.api(), data;

                        // Remove the formatting to get integer data for summation
                        var intVal = function (i) {
                            return typeof i === 'string' ?
                                    i.replace(/[\$,]/g, '') * 1 :
                                    typeof i === 'number' ?
                                    i : 0;
                        };

                        // Total over all pages
                        $(api.column(0).footer()).html(
                                ' Total (Rs.)'
                                );

                        total = api
                                .column(9)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(9, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(9).footer()).html(
                                pageTotal.toFixed(2)
                                );

                        total = api
                                .column(5)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(5, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(5).footer()).html(
                                pageTotal.toFixed(2)
                                );



                    }
                });
            });
        </script>

    </head>

    <body width="80%">

        <form name="disbursecheckerForm" method="post" action="submitDisbursementReconciliationReport.htm">

            <p>&nbsp;</p>

            <h3 align="center" style="color:#003366;" >Bank Disbursement Details </h3>

            <table align="center" id="billtable" style="width:80%;" class="customerTable" cellspacing="0">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Disburse ID</th>

                        <th>Week ID</th>

                        <th>Pool Member</th>

                        <th>Bank Disbursed Date</th>

                        <th>Bank Disburse Time</th>

                        <th>Bank Disburse Amount</th>

                        <th>Bank Receipt Number</th>

                        <th>Bill Type</th>

                        <th>Disbursed Date</th>

                        <th>Disburse Amount</th>

                        <th>Total Amount</th>

                    </tr>

                </thead>

                <tbody>

                    <% int cnt = 0;

                        String disburseid = "disburseid";

                        String disburseid1 = null;

                        String disbursedate = "disbursedate";

                        String disbursedate1 = null;

                        String disburseamt = "disburseamt";

                        String disburseamt1 = null;


                    %>

                    <c:forEach items="${bankList}" var="ele">

                        <%                               cnt++;

                            disburseid1 = disburseid + cnt;

                            disbursedate1 = disbursedate + cnt;

                            disburseamt1 = disburseamt + cnt;

                        %>

                        <c:forEach items="${disburseList}" var="ele1">



                            <c:if test="${ele1.disburseId==ele.disburseId}">

                                <tr>

                                    <td><input type="text" name="<%=disburseid1%>" value="${ele.disburseId}" hidden="yes"/>${"B"}${ele.disburseId}</td>

                                    <td>${ele1.weekId}</td>

                                    <td>${ele.corporates.corporateName}</td>

                                    <td><input type="text" name="<%=disbursedate1%>" value="${ele.amountDate}" hidden="yes"/>${ele.amountDate}</td>

                                    <td>${ele.amountTime}</td>
                            <input type="text" name="<%=disburseamt1%>" value="${ele.paidAmount}" hidden="yes"/>
                            <td>${ele.paidAmount}</td>

                            <td>${ele.receiptNumber}</td>

                            <td>${ele1.billReceiveCorp.billType}</td>

                            <td>${ele1.disbursalDate}</td>

                            <td>${ele1.disburseAmount}</td>

                            <td>${ele1.totalAmount}</td>

                            </tr>

                        </c:if>

                    </c:forEach>

                </c:forEach>

                </tbody>
                <tfoot>
                    <tr>
                        <th style="text-align:right">Total:</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th></th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th></th>
                        <th>&nbsp;</th>
                    </tr>
                </tfoot>
  <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
            </table>





            <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>

            <p align="center">

                <input style="width:100px;" class="btn"  onclick="return validate();" type="submit" name="bconfirm"  value="Confirm"/>

                <!--                   <input style="width:100px;" class="btn" onclick="return validate1();" type="submit" name="bcancel"  value="Delete"/>-->

            </p>

            <p>&nbsp;</p>

            <p>&nbsp;</p>
<p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        </form>
              <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <br><br>
        <br><br>
        <br>
    </body>

</html>