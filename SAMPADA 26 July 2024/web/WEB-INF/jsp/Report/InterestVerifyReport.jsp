<%-- 
    Document   : InterestVerifyReport
    Created on : 9 Sep, 2020, 6:51:29 PM
    Author     : Kaustubh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/css/buttons.dataTables.min.css"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>
        <title>JSP Page</title>
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
                var table = $('#interestChecker').DataTable({
                    responsive: true,
                    "lengthMenu": [[10,25,50,-1], [10,25,50,"All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            title: 'Interest Verification Payable'
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            title: 'Interest Verification Payable'
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
                                pageTotal.toFixed(2)
                                );
                        total = api
                                .column(8)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);
                        // Total over this page
                        pageTotal = api
                                .column(8, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);
                        // Update footer
                        $(api.column(8).footer()).html(
                                pageTotal.toFixed(2)
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

                    }
                });

                var table1 = $('#interestChecker1').DataTable({
                    responsive: true,
                    "order": [[0, "asc"]],
                    "lengthMenu": [[10,25,50,-1], [10,25,50,"All"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            title: 'Interest Verification Disburse'
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            title: 'Interest Verification Disburse'
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
                                pageTotal.toFixed(2)
                                );
                        total = api
                                .column(8)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);
                        // Total over this page
                        pageTotal = api
                                .column(8, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);
                        // Update footer
                        $(api.column(8).footer()).html(
                                pageTotal.toFixed(2)
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

                    }
                });


            });
    </script>
    </head>
    <body>
        <h3 align="center" style="color:#003366;">Verified Interest for Paying Amount by Pool Member</h3>

        <form method="post" action="interestPaidVerifiedExcelExport.htm">
            <div align="right">&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;
                 <input type="text"  name="startdate" value="${startdate}" hidden="yes" style="color: black;">
                <input type="text"  name="enddate" value="${enddate}" hidden="yes" style="color: black;">
                <input type="submit" value="Corporatewise-Excel Export" style="color: black;">
            </div>
        </form>
            <table align="center" id="interestChecker" style="width:95%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Week ID</th>
                        <th>Pool Member Name</th>
                        <th>Revision No.</th>
                        <th>Bill Type</th>
                        <th>Bill Amount</th>
                        <th>Billing Due date</th>
                        <th>Bank Paid date</th>
                        <th>No. of Days</th>
                        <th>Bill Amount for Interest (Rs.)</th>
                        <th>Interest Amount (Rs.)</th>
                        <th>Bill Year</th>
                        <th>Remarks</th>
                        <th>Date of Verification</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${paylist}" var="o1">
                        <tr align="center">
                            <td>${o1.weekId}</td>
                            <td>${o1.corporates.corporateName}</td>
                            <td>${o1.revisionNo}</td>
                            <td>${o1.billType}</td>
                            <td>${o1.billedAmount}</td>
                            <td>${o1.billingDuedate}</td>
                            <td>${o1.paidDate}</td>
                            <td>${o1.noofdays}</td>
                            <td>${o1.interestBilledamount}</td>
                            <td>${o1.interestAmount}</td>
                            <td>${o1.billYear}</td>
                            <td>${o1.remarks}</td>
                            <td>${o1.entryDate}</td>
                        </tr>
                    </c:forEach>
                        
                
                </tbody>
                <tfoot>
                    <tr>
                        <td>Total(Rs.)</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>&nbsp;</td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
            <br>
            <br>
            
            <h3 align="center" style="color:#003366;">Verified Interest for Disbursed Amount to Pool Member</h3>

        <form method="post" action="interestDisburseVerifiedExcelExport.htm">
            <div align="right">&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;
                <input type="text"  name="startdate" value="${startdate}" hidden="yes" style="color: black;">
                <input type="text"  name="enddate" value="${enddate}" hidden="yes" style="color: black;">
                <input type="submit" value="Corporatewise-Excel Export" style="color: black;">
            </div>
        </form>
            <table align="center" id="interestChecker1" style="width:95%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Week ID</th>
                        <th>Pool Member Name</th>
                        <th>Revision No.</th>
                        <th>Bill Type</th>
                        <th>Bill Amount</th>
                        <th>Billing Due date</th>
                        <th>Disbursed Date</th>
                        <th>No. of Days</th>
                        <th>Bill Amount for Interest (Rs.)</th>
                        <th>Interest Amount (Rs.)</th>
                        <th>Bill Year</th>
                        <th>Remarks</th>
                        <th>Date of Verification</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${reclist}" var="obj1">
                        <tr align="center">
                            <td>${obj1.weekId}</td>
                            <td>${obj1.corporates.corporateName}</td>
                            <td>${obj1.revisionNo}</td>
                            <td>${obj1.billType}</td>
                            <td>${obj1.billedAmount}</td>
                            <td>${obj1.billingDuedate}</td>
                            <td>${obj1.disbursedDate}</td>
                            <td>${obj1.noofdays}</td>
                            <td>${obj1.interestBilledamount}</td>
                            <td>${obj1.interestAmount}</td>
                            <td>${obj1.billYear}</td>
                            <td>${obj1.remarks}</td>
                            <td>${obj1.entryDate}</td>
                        </tr>
                    </c:forEach>
                        
                   
                </tbody>
                <tfoot>
                    <tr>
                        <td>Total(Rs.)</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>&nbsp;</td>
                    </tr>
                </tfoot>
            </table>
        
        <br><br>
        <br><br>
        <br><br>
        <br>
    </body>
</html>