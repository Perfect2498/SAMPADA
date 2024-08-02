<%-- 
    Document   : MISPooldetail
    Created on : 8 Jun, 2020, 11:41:15 AM
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
        <title>JSP Page</title>
        <script>

function fnExcelReport()
{
    var tab_text="<html><head><style>table,td{border:1px solid black}</style></head><table><tr>";
    var textRange; var j=0;
    tab = document.getElementById('parenttable'); // id of table

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
        sa=txtArea1.document.execCommand("SaveAs",true,"MIS-Pool_Account.xlsx");
    }  
    else                 //other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));  

    return (sa);
}
        </script>
        <script>
            $(document).ready(function() {
               $('#tbl1').DataTable( {
                    responsive: true,
                    paging: true,
                    "pageLength": 5,
                    "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
                    "ordering": false,
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'DSM MIS Report',
                            orientation: 'landscape'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'DSM MIS Report',
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
                                'Total'
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
                
                $('#tbl2').DataTable( {
                    responsive: true,
                    paging: true,
                    "pageLength": 5,
                    "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
                    "ordering": false,
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'RRAS MIS Report',
                            orientation: 'landscape'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'RRAS MIS Report',
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
                                'Total'
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
                
                $('#tbl3').DataTable( {
                    responsive: true,
                    paging: true,
                    "pageLength": 5,
                    "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
                    "ordering": false,
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'AGC MIS Report',
                            orientation: 'landscape'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'AGC MIS Report',
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
                                'Total'
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
    </head>
    <body>
        <br>
        <pre>                  <button onclick="fnExcelReport()" style="height: 40px; width: 150px;">Excel Export</button></pre>
        <table align="center" id="parenttable">
            <tr>
            <td>
                <h2 style="color: black" align="center">WRLDC: MIS For The Month of ${month} / ${year1}. As per Latest Revision of Bills.</h2>
                <br>
            <b>DSM</b> (In INR Lakh)
            <table class="customerTable" cellspacing="0" align="center" id="tbl1">
                <thead>
                    <th>Week / F.Y. Year</th>
                    <th>Amount Billed (A)</th>
                    <th>Amount Received (B)</th>
                    <th>Pending Dues<br> (Annexure 4)<br>(C=A-B)</th>
                    <th>Amount to be paid (D)</th>
                    <th>Amount paid (E)</th>
                    <th>Pending Payment<br> (Annexure 5)<br>(F=D-E)</th>
                </thead>
                <tbody align="center">
                    <c:forEach items="${dsmweeks}" var="ele" varStatus="itr">
                        <c:if test="${dsm_c[itr.index]>0 || dsm_f[itr.index]>0}">
                            <tr>
                                <td>Week ${ele} of ${year}-${(year+1)%100}</td>
                                <td>${dsm_a[itr.index]}</td>
                                <td>${dsm_b[itr.index]}</td>
                                <td>${dsm_c[itr.index]}</td>
                                <td>${dsm_d[itr.index]}</td>
                                <td>${dsm_e[itr.index]}</td>
                                <td>${dsm_f[itr.index]}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
                <tfoot align="center">
                    <tr>
                        <td><b>Total</b></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
                <br><br><br>
                
            <b>RRAS</b> (In INR Lakh)
            <table class="customerTable" cellspacing="0" align="center" id="tbl2">
                <thead>
                    <th>Week / F.Y. Year</th>
                    <th>Amount Billed (A)</th>
                    <th>Amount Received (B)</th>
                    <th>Pending Dues<br> (Annexure 6)<br>(C=A-B)</th>
                    <th>Amount to be paid (D)</th>
                    <th>Amount paid (E)</th>
                    <th>Pending Payment<br> (Annexure 7)<br>(F=D-E)</th>
                </thead>
                <tbody align="center">
                    <c:forEach items="${rrasweeks}" var="ele" varStatus="itr">
                        <c:if test="${rras_c[itr.index]>0 || rras_f[itr.index]>0}">
                            <tr>
                                <td>Week ${ele} of ${year}-${(year+1)%100}</td>
                                <td>${rras_a[itr.index]}</td>
                                <td>${rras_b[itr.index]}</td>
                                <td>${rras_c[itr.index]}</td>
                                <td>${rras_d[itr.index]}</td>
                                <td>${rras_e[itr.index]}</td>
                                <td>${rras_f[itr.index]}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
                <tfoot align="center">
                    <tr>
                        <td><b>Total</b></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
                <br><br><br>
            
            <b>AGC</b> (In INR Lakh)
            <table class="customerTable" cellspacing="0" align="center" id="tbl3">
                <thead>
                <th>Week / F.Y. Year</th>
                <th>Amount Billed (A)</th>
                <th>Amount Received (B)</th>
                <th>Pending Dues<br> (Annexure 8)<br>(C=A-B)</th>
                <th>Amount to be paid (D)</th>
                <th>Amount paid (E)</th>
                <th>Pending Payment<br> (Annexure 9)<br>(F=D-E)</th>
                </thead>
                <tbody align="center">
                    <c:forEach items="${agcweeks}" var="ele" varStatus="itr">
                        <c:if test="${agc_c[itr.index]>0 || agc_f[itr.index]>0}">
                            <tr>
                                <td>Week ${ele} of ${year}-${(year+1)%100}</td>
                                <td>${agc_a[itr.index]}</td>
                                <td>${agc_b[itr.index]}</td>
                                <td>${agc_c[itr.index]}</td>
                                <td>${agc_d[itr.index]}</td>
                                <td>${agc_e[itr.index]}</td>
                                <td>${agc_f[itr.index]}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
                <tfoot align="center">
                    <tr>
                        <td><b>Total</b></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
                <br><br><br>
                
            </td>
            </tr>
        </table>
                <br><br><br>
            
                <pre><br><br><br>
        <br><br><br>
        <br><br><br>
        <br><br><br></pre>
    </body>
</html>