<%-- 
    Document   : mischome
    Created on : 22 Apr, 2020, 10:09:48 AM
    Author     : abt
--%>






<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="java.text.DateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar" %> 
<!DOCTYPE html>
<html>
    <head>
        <title>Misc Disburse List</title>
        <script>
            function getFiles(name)
            {
                document.getElementById('documentset').value = name;
                document.getElementById('form1').submit();
            }
        </script>
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
            $(document).ready(function () {

                var table = $('#payableTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'MISC Disbure List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'MISC Disbure List';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'MISC Disbure List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'MISC Disbure List';
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
                                pageTotal.toFixed(2)
                                );
                    }
                });




                var table = $('#payableTable1').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Bank & DSN link List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Bank & DSN link List';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Bank & DSN link List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Bank & DSN link List';
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




                var table = $('#payableTable2').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Bank To Int List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Bank To Int List';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Bank To Int List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Bank To Int List';
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




                var table = $('#payableTable3').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Bank To Int List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Bank To Int List';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Bank To Int List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Bank To Int List';
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

            });
        </script>


    </head>
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%; height: auto" >

        <h3 align="center" style="color:#003366;">Misc Disburse List</h3>
        <table id="payableTable" align="center" style="width:81%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Disburse Id</th>
                    <th>Corporate Idt</th>
                    <th>Corporate Name</th>

                    <th>Document Set</th>
                    <th>Status</th>
                    <th>Remarks</th>
                    <th>Maker Date</th>
                    <th>Checker Date</th>
                    <th>Main/Interest</th>
                    <th>Disburse Amount</th>                    
                    <th>Pool / Int Bal.</th>
                    <th>Disburse Type </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${misclist}" var="ele">

                    <tr>
                        <td>${"M"}${ele.uniqueId}</td>
                        <td>${ele.corpId}</td>
                        <td>${ele.corpName}</td>
                        <td>${ele.documentSet}</td>
                        <td>${ele.status}</td>
                        <td>${ele.remarks}</td>
                        <td>${ele.makerDate}</td>
                        <td>${ele.checkerDate}</td>
                        <td>${ele.amtCategory}</td>
                        <td>${ele.refundAmt}</td>
                        <td>${ele.mainPoolBalance-ele.refundAmt}</td>
                        <c:if test="${ele.stmtId =='-1' }">
                            <td>${"Direct Refund"}</td>
                        </c:if>
                        <c:if test="${ele.stmtId !='-1' }">
                            <td>${"Bank Refund"}</td>
                        </c:if>

                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <th style="text-align:right">Total:</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th>&nbsp;</th>
                </tr>
            </tfoot>
        </table>

        <h3 align="center" style="color:#003366;">Bank & DSN Link List</h3>

        <table id="payableTable1" align="center" style="width:81%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Statement Id</th>
                    <th>Date of Cr.</th>
                    <th>Corporate Name</th>
                    <th>Amount Credited</th>
                    <th>Amount Settled</th>
                    <th>Amount Removed</th>
                    <th>Ref. Document SET</th>  
                    <th>Checker Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${dsnfiledetlist}" var="tx">

                    <tr>
                        <td>${tx.bankStatement.stmtId}</td>
                        <td>${tx.bankStatement.amountDate}</td>
                        <td>${tx.bankStatement.corporates.corporateName}</td>
                        <td>${tx.bankStatement.paidAmount}</td>
                        <td>${tx.bankStatement.mappedAmount}</td>
                        <td>${tx.mappedBalance}</td>                            
                        <!--<td>${tx.fileName}</td>-->
                        <td><a id="${tx.fileName}" onclick="getFiles(this.id)" style="color:blue;cursor:pointer;">${tx.fileName}</a></td>

                        <td>${tx.checkerStatus}</td>



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

        </table>
        <p>&nbsp;</p>
        
        
        
        <h3 align="center" style="color:#003366;">Pool To Pool Disburse List</h3>

        <table id="payableTable3" align="center" style="width:81%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>P-TO-P Id</th>
                    <th>Date of Transfer</th>
                    <th>Corporate Name</th>
                    <th>Amount Transfered</th>
                    <th>Pool Balance</th>
                    <th>Pool Balance</th>
                    <th>Ref. Document SET</th>  
                    <th>Checker Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pooltopool}" var="pintp">

                    <tr>
                        <td>${pintp.uniqueId}</td>
                        <td>${pintp.entryDate}</td>
                        <td>${"WRLDC INTERNAL TRANSFER"}</td>
                        <td>${pintp.refundAmt}</td>



                        <c:choose>
                            <c:when test="${pintp.transId == '1'}">
                                <td style="color: #ff6633">${"DSM-bal: "}${pintp.mainPoolBalance}</td>
                                <td style="color: #ff6633">${"SRAS-bal: "}${pintp.poolAgcBal}</td>

                            </c:when>
                            <c:when test="${pint.transId == '2'}">
                                <td style="color: #ff6633">${"DSM-bal: "}${pintp.mainPoolBalance}</td>
                                <td style="color: #ff6633">${"TRAS-bal: "}${pintp.poolRrasBal}</td>

                            </c:when>
                            <c:otherwise>
                                <td style="color: #ff6633">${"SRAS-bal: "}${pintp.poolAgcBal}</td>
                                <td style="color: #ff6633">${"TRAS-bal: "}${pintp.poolRrasBal}</td>
                            </c:otherwise>
                        </c:choose>


                        <td><a id="${pint.documentSet}" onclick="getFiles(this.id)" style="color:blue;cursor:pointer;">${pint.documentSet}</a></td>

                        <td>${pint.status}</td>



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

        </table>

         <p>&nbsp;</p>
        
        <h3 align="center" style="color:#003366;">Pool To Interest Disburse List</h3>

        <table id="payableTable2" align="center" style="width:81%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>P-TO-INT Id</th>
                    <th>Date of Transfer</th>
                    <th>Corporate Name</th>
                    <th>Amount Transfered</th>
                    <th>Pool Balance</th>
                    <th>Interest Balance</th>
                    <th>Ref. Document SET</th>  
                    <th>Checker Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pooltoint}" var="pint">

                    <tr>
                        <td>${pint.uniqueId}</td>
                        <td>${pint.entryDate}</td>
                        <td>${"WRLDC INTERNAL TRANSFER"}</td>
                        <td>${pint.refundAmt}</td>


                        <c:choose>
                            <c:when test="${pint.poolAgcBal != null}">
                                <td style="color: #ff6633">${"SRAS-bal: "}${pint.poolAgcBal}</td>
                                <td style="color: #ff6633">${"INT-SRAS-bal: "}${pint.intAgcBal}</td>

                            </c:when>
                            <c:when test="${pint.poolRrasBal != null}">
                                <td style="color: #ff6633">${"TRAS-bal: "}${pint.poolRrasBal}</td>
                                <td style="color: #ff6633">${"INT-TRAS-bal: "}${pint.intRrasBal}</td>

                            </c:when>
                            <c:otherwise>
                                <td style="color: #ff6633">${pint.mainPoolBalance}</td>
                                <td style="color: #ff6633">${"Int-bal: "}${pint.intPoolBalance}</td>
                            </c:otherwise>
                        </c:choose>



                        <td><a id="${pint.documentSet}" onclick="getFiles(this.id)" style="color:blue;cursor:pointer;">${pint.documentSet}</a></td>

                        <td>${pint.status}</td>



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

        </table>
        <p>&nbsp;</p>


        <p>&nbsp;</p>

        
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <form name="form1" id="form1" action="viewDocuments.htm" method="post" >
            <input type="hidden" value="" id="documentset" name="documentset">
        </form>
        <p>&nbsp;</p>

        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
    </body>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
    <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>

    <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
</html>




