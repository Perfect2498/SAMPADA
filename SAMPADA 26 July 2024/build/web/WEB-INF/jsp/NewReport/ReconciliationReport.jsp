<%-- 
    Document   : ReconciliationReport
    Created on : 15 Jun, 2020, 7:17:42 PM
    Author     : Kaustubh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    </head>
    <style>
        body {
            height: 3000px;
        }
    </style>
    <script>
         $(document).ready(function() {

             $('#table1').DataTable( {
                    responsive: true,
                    "pageLength": 15,
                    "lengthMenu": [[15, 25, 50, -1], [15, 25, 50, "All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Reconciliation',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Amount collected and disbursed in DSM pool account and transferred to PSDF';
                            },
                            messageBottom: function () {
                                return '\n\nS Usha DGM(MO)                                                                                                 RP Singh CM(F&A)';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Reconciliation',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Amount collected and disbursed in DSM pool account and transferred to PSDF';
                            },
                            messageBottom: function () {
                                return '\n\nS Usha DGM(MO)                                                                                                 RP Singh CM(F&A)';
                            }
                        }
                    ]
                } );
         });
    </script>
    <body>
        <b>Home -> Reports</b>
        <h1 style="color: red">${msg}</h1>
        <h1 style="color: black">Amount collected and disbursed in DSM pool account and transferred to PSDF Apr'${year}-Mar'${year+1}</h1>
        
        <table id="table1" class="customerTable" cellspacing="0" width="98%" align="left">
            <thead>
            <th>Sr No.</th>
            <th>Month</th>
            <th>Payment<br>received in DSM account(Rs)</th>
            <th>Payment<br>Disbursed from DSM account(Rs)</th>
            <th>Interest Received</th>
            <th>Interest Disbused</th>
            <th>Principal Amount transferred to PSDF</th>
            <th>Interest Amount transferred to PSDF</th>
            <th>Surplus/Deficit Account</th>
            <th>Surplus/Deficit Interest</th>
            <th>Total Rs.</th>
            </thead>
            <tbody>
                <tr>
                    <td>0</td>
                    <td>Opening Balance for ${year}-${year+1} (Closing Balance of FY${year-1}-${year})</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td style="background-color: yellow">${openbalacc}</td>
                    <td style="background-color: yellow">${openbalint}</td>
                    <td style="background-color: yellow">${openbalacc+openbalint}</td>
                </tr>
                <c:forEach items="${monthlist}" var="sd" varStatus="iteration">
                    <tr align="center">
                        <td>${iteration.index + 1}</td>
                        <td>${sd}</td>
                        <td>${payrec[iteration.index]}</td>
                        <td>${paydis[iteration.index]}</td>
                        <td>${intrec[iteration.index]}</td>
                        <td>${intdis[iteration.index]}</td>
                        <td>${ptrspsdf[iteration.index]}</td>
                        <td>${itrspsdf[iteration.index]}</td>
                        <td style="background-color: yellow">${monthlyaccsum[iteration.index]}</td>
                        <td style="background-color: yellow">${monthlyintsum[iteration.index]}</td>
                        <td style="background-color: yellow">${monthlyaccsum[iteration.index]+monthlyintsum[iteration.index]}</td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                    <td></td>
                    <td>Total</td>
                    <td>${payrectotal}</td>
                    <td>${paydistotal}</td>
                    <td>${intrectotal}</td>
                    <td>${intdistotal}</td>
                    <td>${ptrspsdftotal}</td>
                    <td>${itrspsdftotal}</td>
                    <td style="background-color: yellow"></td>
                    <td style="background-color: yellow"></td>
                    <td style="background-color: yellow">Closing Balance</td>
            </tfoot>
        </table>

        <br><br><br><br>
        <br><br><br>
    </body>
</html>
