<%--

    Document   : viewCheckerPendingPayableBillbyRLDC

    Created on : Jul 11, 2019, 9:24:35 AM

    Author     : JaganMohan

--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>

    <head>

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

            $(document).ready(function () {



                var table = $('#makerTable').DataTable({
                    responsive: true,
                    "bFilter": false,
                    "bPaginate": false,
                    "bInfo": false,
                    "ordering": true,
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

                                .column(3)

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(3, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(3).footer()).html(
                                pageTotal

                                );





                        total = api

                                .column(4)

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(4, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(4).footer()).html(
                                pageTotal

                                );





                    }

                });



                var table = $('#makerTablerefund').DataTable({
                    responsive: true,
                    "bFilter": false,
                    "bPaginate": false,
                    "bInfo": false,
                    "ordering": true,
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

                                .column(3)

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(3, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(3).footer()).html(
                                pageTotal.toFixed(2)

                                );





                        total = api

                                .column(4)

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(4, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(4).footer()).html(
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

                var table1 = $('#bankTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
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

                                .column(4)

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(4, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(4).footer()).html(
                                pageTotal

                                );





                    }





                });

            });

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

    <body style="text-align: center; alignment-adjust: central;width: 95%;">



        <form>

            <fieldset>

                <legend>  <h3 align="center" style="color:#003366;">View Payment Verification Details for Corporate : ${CorpName}</h3></legend>

                <input type="text" name="corp_ID" value="${corpID}" hidden="yes"/>

                <p align="center">Bank Details </p>

                <table align="center" id="bankTable" style="width:81%;" class="customerTable">

                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>

                            <th>Stmt Id</th>

                            <th>Credit Date</th>

                            <th>Opening Amount</th>

                            <th>Credit Amount</th>

                            <th>Settled  Amount</th>

                            <th>Pending Amount</th>

                        </tr>

                    </thead>

                    <tbody>



                        <c:forEach items="${bankList}" var="ele">

                            <tr>

                                <td>${ele.bankStatement.stmtId}</td>

                                <td>${ele.bankStatement.amountDate}</td>

                                <td>${ele.bankStatement.openBalance}</td>

                                <td>${ele.bankStatement.paidAmount}</td>

                                <td>${ele.mappedAmount}</td>

                                <td>${ele.transactionBalance}</td>

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

                            <th>&nbsp;</th>

                        </tr>

                    </tfoot>



                </table>

                <br/>

                <p align="center">Mapped Bills Details </p>

                <table id="makerTable" align="center" style="width:81%;" class="customerTable">

                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>

                            <th>Week ID</th>

                            <th>Bill Due date</th>

                            <!--<th>Commercial Group</th>-->

                            <th>Total Amount</th>

                            <th>Verified Amount</th>

                            <th>Pending Amount</th>

                            <th>Bill Type</th>

                            <th>Category</th>

                        </tr>

                    </thead>

                    <tbody>

                        <% int cnt = 0;%>

                        <c:forEach items="${tempmaplist}" var="ele">

                            <tr>

                                <%

                                    cnt++;

                                %>

                                <td>${ele.weekId}</td>

                                <td>${ele.billPayableCorp.billDueDate}</td>

                                <!--<td>${ele.corporates.corporateName}</td>-->

                                <td>${ele.originalAmount}</td>

                                <td>${ele.mappedAmount}</td>

                                <td>${ele.pendingAmount}</td>

                                <td>${ele.billType}</td>

                                <td>${ele.paymentCategory}</td>



                            </tr>



                        </c:forEach>

                    </tbody>

                    <tfoot>

                        <tr>

                            <th style="text-align:right">Total:</th>

                            <th>&nbsp;</th>

                            <th>&nbsp;</th>

                            <th></th>

                            <th></th>

                            <th>&nbsp;</th>

                            <th>&nbsp;</th>

                        </tr>

                    </tfoot>



                </table>

                <p>&nbsp;</p><p>&nbsp;</p>

                <p align="center">Mapped Refund Details </p>

                <br/>

                <table id="makerTablerefund" align="center" style="width:81%;" class="customerTable">

                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>

                            <th>Week ID</th>

                            <th>Bill Due date</th>



                            <th>Total Amount</th>

                            <th>Out Standing Amount</th>

                            <th>Verified Amount</th>

                            <th>Pending Amount</th>

                            <th>Bill Type</th>

                            <th>Category</th>

                        </tr>

                    </thead>

                    <tbody>



                        <c:forEach items="${listrefund}" var="ele">

                            <tr>

                                <%                                    cnt++;

                                %>

                                <td>${ele.weekid}</td>

                                <td>${ele.billReceiveCorp.billDueDate}</td>

                                <td>${ele.totalAmount}</td>

                                <td>${ele.paidAmount + ele.pendingAmount}</td>

                                <td>${ele.paidAmount}</td>

                                <td>${ele.pendingAmount}</td>

                                <td>${ele.billReceiveCorp.billType}</td>

                                <td>${ele.billReceiveCorp.revisionNo}</td>



                            </tr>



                        </c:forEach>

                    </tbody>

                    <tfoot>

                        <tr>

                            <th style="text-align:right">Total:</th>

                            <th>&nbsp;</th>

                            <th>&nbsp;</th>

                            <th></th>

                            <th></th>

                            <th></th>

                            <th>&nbsp;</th>

                            <th>&nbsp;</th>

                        </tr>

                    </tfoot>

                    <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>

                </table>

                <p>&nbsp;</p><p>&nbsp;</p>



                <p align="center"><input class="btn" type="submit" value="Verify" onclick="return validate();"  name="bverify" />&emsp;<input class="btn" type="submit" value="&#10060;Delete" onclick="return validate1();"  name="bdelete" /></p>

            </fieldset>

        </form>

        <br/>

        <p>&nbsp;</p>

        <p>&nbsp;</p>

        <p>&nbsp;</p>

        <p>&nbsp;</p>

    </body>

</html>