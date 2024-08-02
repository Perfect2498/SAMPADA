<%-- 
    Document   : InterestPublishReport
    Created on : 9 Sep, 2020, 10:33:18 PM
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
                var table = $('#tab1').DataTable({
                    responsive: true,
                    "lengthMenu": [[10,25,50,-1], [10,25,50,"All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            title: 'Interest Published'
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            title: 'Interest Published'
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
                });
            });
    </script>
    </head>
    <body>
        
        <h3 align="center" style="color:#003366;">Published Interest Details</h3>
        
        <table id="tab1" class="customerTable" align="center">
            <thead>
                <tr>
                    <th>Interest Id</th>
                    <th>Pool Member Name</th>
                    <th>Bill Type</th>
                    <th>Payable Interest</th>
                    <th>Receivable Interest</th>
                    <th>Net Interest</th>
                    <th>Generate Type</th>
                    <th>Remarks</th>
                    <th>Summary Remark</th>
                    <th>Date of Publishing</th>
                    <th>Week Id</th>
                    <th>Bill Year</th>
                    <th>Revision No</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${intd}" var="o1">
                    <tr align="center">
                        <td>I${o1.interestId}</td>
                        <td>${o1.corporates.corporateName}</td>
                        <td>${o1.billType}</td>
                        <td>${o1.payInterest}</td>
                        <td>${o1.recInterest}</td>
                        <c:choose>
                            <c:when test="${o1.recInterest > o1.payInterest}">
                                <td>-${o1.interestAmount}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${o1.interestAmount}</td>
                            </c:otherwise>
                        </c:choose>
                        
                        <td>${o1.billCategory}</td>
                        <td>${o1.remarks2}</td>
                        <td>${o1.remarks}</td>
                        <td>${o1.entryDate}</td>
                        <td>${o1.weekId}</td>
                        <td>${o1.billYear}</td>
                        <td>${o1.revisionNo}</td>
                    </tr>
                </c:forEach>
                    
                <c:forEach items="${dintd}" var="o2">
                    <tr align="center">
                        <td>I${o2.interestId}</td>
                        <td>${o2.corporates.corporateName}</td>
                        <td>${o2.billType}</td>
                        <td>${o2.payInterest}</td>
                        <td>${o2.recInterest}</td>
                        <c:choose>
                            <c:when test="${o2.recInterest > o2.payInterest}">
                                <td>-${o2.interestAmount}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${o2.interestAmount}</td>
                            </c:otherwise>
                        </c:choose>
                        
                        <td>${o2.billCategory}</td>
                        <td>${o2.remarks2}</td>
                        <td>${o2.remarks}</td>
                        <td>${o2.entryDate}</td>
                        <td>${o2.weekId}</td>
                        <td>${o2.billYear}</td>
                        <td>${o2.revisionNo}</td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
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