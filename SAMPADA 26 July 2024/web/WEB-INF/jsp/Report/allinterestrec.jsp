<%--
    Document   : allinterestrec
    Created on : Nov 22, 2019, 6:12:48 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
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

                var table = $('#payableTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer : true 
                          
                        },
                          {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer : true 
                          
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
                                pageTotal
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


                        total = api
                                .column(10)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(10, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(10).footer()).html(
                                pageTotal
                                );

                        total = api
                                .column(11)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);
                        // Total over this page
                        pageTotal = api
                                .column(11, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(11).footer()).html(
                                pageTotal
                                );
                    }




                });
                table.columns().iterator('column', function (ctx, idx) {
                    $(table.column(idx).header()).append('<span class="sort-icon"/>');
                });

                $('#payableTable tbody')
                        .on('mouseenter', 'td', function () {
                            var colIdx = table.cell(this).index().column;

                            $(table.cells().nodes()).removeClass('highlight');
                            $(table.column(colIdx).nodes()).addClass('highlight');
                        });

            });
        </script>


    </head>
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" >
        <h3 align="center" style="color:#003366;">From Week-id ${week_id1} to Week-id ${week_id2} of year ${yeari}</h3>
        <!--<table id="payableTable" align="center" style="min-height:400px;width:80%;" class="table table-striped table-bordered">-->
        <form>
            <fieldset>
                <legend> <h3>Interest Receivable  Report</h3></legend>

                <table id="payableTable" align="center" style="min-height:400px;width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Pool Member Name</th>
                            <th>WEEK_ID</th>
                            <th>Bill type</th>
                            <th>Revision NO.</th>
                            <th>Billed Amount</th>
                            <th>Disbursed Amount</th>




                            <th>Due Date </th>
                            <th>Disbursed Date </th>
                            <th>No. of days </th>
                            <th>Interest Amount</th>
                            <th>Interest Paid</th>
                            <th>Interest Pending</th>
                            <th>Checker Status </th>





                        </tr>

                    </thead>
                    <tbody>

                        <%--<c:set var="Pending" value="Not Paid" scope="page" />--%>
                        <%--<c:set var="Paid" value="Paid" scope="page" />--%>


                        <c:forEach items="${queryList1}" var="list">


                            <tr>
                                <td>${list.corporates.corporateName}</td>
                                <td>${list.weekId}</td>
                                <td>${list.billType}</td>
                                <td>${list.revisionNo}</td>
                                <td>${list.billedAmount}</td>
                                <td>${list.disbursedAmount}</td>
                                <td>${list.billingDuedate}</td>
                                <td>${list.disbursedDate}</td>
                                <td>${list.noofdays}</td>
                                <td>${list.interestAmount}</td>
                                <td>${list.interestPaidamount}</td>
                                <td>${list.interestPendingamount}</td>
                                <td>${list.checkerStatus}</td>










                            </c:forEach>


                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    </tbody>

                    <tfoot>
                        <tr>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>


                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>

                            <th></th>
                            <th></th>
                            <th></th>
                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>
                            <th></th>

                        </tr>
                    </tfoot>
                </table>


            </fieldset>
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
    </body>
</html>

