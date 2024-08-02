<%-- 
    Document   : viewDueDatesummary
    Created on : 23 Sep, 2020, 6:13:15 PM
    Author     : Kaustubh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <title>Due Datewise summary</title>
    </head>
    <script>
            $(document).ready(function() {
               $('#sumtable').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[0, "asc"]],
                    dom: 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Due Datewise summary',
                            orientation: 'landscape'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Due Datewise summary',
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
                                .column(2)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(2, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(2).footer()).html(
                                pageTotal.toFixed(2)
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
                    }
                } );
            });
    </script>
    <body>
        <h2 style="color: black">Details for the Period ${date1} to ${date2}</h2>
        <table align="center" class="customerTable" cellspacing="0">
            <tr>
                <th>Total amount credited in Bank (Rs.)</th>
                <th>Total amount debited from Bank (Rs.)</th>
            </tr>
            <tr align="center">
                <td>${totalcredit}</td>
                <td>${totaldebit}</td>
            </tr>
        </table>
        
        <br>
        
        <h2 style="color: black">Total Payable/Receivable amounts as per Due Date for bill type ${billType}.</h2>
        <table align="center" id="sumtable" class="customerTable" cellspacing="0" width="90%">
            <thead>
                <th>Sr. No</th>
                <th>Due Date</th>
                <th>Total Payable (Rs.)</th>
                <th>Total Receivable (Rs.)</th>
            </thead>
            <tbody>
                <c:forEach items="${duedates}" var="sd" varStatus="itr">
                    <tr align="center">
                        <td>${itr.index + 1}</td>
                        <td>${sd}</td>
                        <td>${totalpays[itr.index]}</td>
                        <td>${totalrecs[itr.index]}</td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr align="center">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tfoot>
        </table>
        
        <br><br>
        <br><br>
        <br><br>
        <br>
    </body>
</html>