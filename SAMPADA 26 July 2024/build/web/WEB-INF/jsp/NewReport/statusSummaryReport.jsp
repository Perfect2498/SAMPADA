<%-- 
    Document   : statusSummaryReport
    Created on : 20 Jun, 2020, 8:04:54 PM
    Author     : Kaustubh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Status summary</title>
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
        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet">
            <script> 
         $(document).ready(function() {

             $('#statustable').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Status summary ${date}',
                            orientation: 'portrait',
                            messageTop: function () {
                                return 'Status of DSM, RRAS, FRAS & AGC Charges Payable/Receivable to WR Deviation Pool Account                  As on date: ${date}';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Status summary ${date}',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Status of DSM, RRAS, FRAS & AGC Charges Payable/Receivable to WR Deviation Pool Account                  As on date: ${date}';
                            }
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
                                .column(1)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(1, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(1).footer()).html(
                                pageTotal.toFixed(2)
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
                        
                        total = api
                                .column(6)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(6, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(6).footer()).html(
                                pageTotal.toFixed(2)
                                );
                    }
                } );
         });
    </script>
    <script>

function fnExcelReport()
{
    var tab_text="<html><head><style>table,th,td{border:1px solid black}</style></head>Status summary ${date}	\n					Status of DSM, RRAS, FRAS & AGC Charges Payable/Receivable to WR Deviation Pool Account                  As on date: ${date}				\n		<table><tr>";
    var textRange; var j=0;
    tab = document.getElementById('statustable'); // id of table

    for(j = 0 ; j < tab.rows.length ; j++) 
    {     
        tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
        //tab_text=tab_text+"</tr>";
    }

    tab_text=tab_text+"</table>";
    tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
    tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa=txtArea1.document.execCommand("SaveAs",true,"NLDC_report.xlsx");
    }  
    else                 //other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));  

    return (sa);
}
        </script>
    </head>
    <body>
        <button onclick="fnExcelReport()" style="height: 40px; width: 150px;">Excel Export</button>
        <h2 style="color:black">Status of DSM, RRAS, FRAS & AGC Charges Payable/Receivable to WR Deviation Pool Account &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;As on date: ${date}</h2>
        <br>
        <table class="customerTable" id="statustable" cellspacing="0" align="center" width="70%">
            <thead>
                <tr>
                    <th></th>
                    <th colspan="2">Total dues (Rs.)</th>
                    <th colspan="2">Payments overdue (Rs.)</th>
                    <th colspan="2">Total Interest Due (Rs.)</th>
                </tr>
                <tr>
                   <th>Name of DSM Pool Member</th>
                   <th>Total dues Payable</th>
                   <th>Total dues Receivable</th>
                   <th>Payments overdue Payable</th>
                   <th>Payments overdue Receivable</th>
                   <th>Total Interest Due Payable</th>
                   <th>Total Interest Due Receivable</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${arr}" var="row">
                    <tr>
                        <c:forEach items="${row}" var="ele">
                            <td>${ele}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </tfoot>
        </table>

        <br><br><br>
        <br><br><br>
        <br><br><br>
        <br><br>
    </body>
</html>