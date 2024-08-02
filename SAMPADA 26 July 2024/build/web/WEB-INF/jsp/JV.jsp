<%-- 
    Document   : JV
    Created on : 15 Dec, 2020, 8:59:20 PM
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
        <title>DSM BPV</title>
    </head>
    <script>
            $(document).ready(function() {
               $('#bpvtable').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[6, "asc"]],
                    dom: 'lBfrtip',
                    buttons: [
                        {
                            text: 'EXCEL',
                            extend: 'excel',
                            footer: true,
                            title: 'JV report',
                            orientation: 'landscape',
                        },
                        {
                            text: 'PDF',
                            extend: 'pdfHtml5',
                            footer: true,
                            title: 'JV report',
                            orientation: 'landscape',
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
                                .column(7)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(7, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(7).footer()).html(
                                pageTotal.toFixed(2)
                                );
                    }
                } );
            });
    </script>
    <body>
        <h2 style="color: black">JV report</h2>
        
        <table align="center" class="customerTable" id="bpvtable" cellspacing="0" width="97%">
            <thead>
                <tr align="center">
                    <th>Account Code</th>
                    <th>Sub Code</th>
                    <th>Party Code</th>
                    <th>Party name</th>
                    <th>Remarks</th>
                    <th>Bill No</th>
                    <th>Bill Date<br>(dd-MM-yyyy)</th>
                    <th>Amount Dr</th>
                    <th>Amount Cr</th>
                    <th>Fin Year</th>
                    <th>TAN No</th>
                </tr>
            </thead>
            <tbody> 
                <c:forEach items="${master}" var="rows">
                    <tr align="center">
                        <td>810517</td>
                        <td>1</td>
                        <c:forEach items="${rows}" var="ele">
                            <td>${ele}</td>
                        </c:forEach>
                        <td></td>
                        <td></td>
                    </tr>
                    
                </c:forEach>
            </tbody>
            <tfoot>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tfoot>
        </table>
        
        <br><br>
        <br><br>
        <br>
    </body>
</html>