<%-- 
    Document   : viewAllPaymentDisbursedbyRLDC
    Created on : 31 Dec, 2019, 12:06:13 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
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

                var table = $('#NotdisbursedTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
                                );



                    }
                });
                var table = $('#disbursedTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
                                );


                    }
                });

                var table = $('#InterestdisbursedTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
                                );



                    }
                });


                var table = $('#InterestnotdisbursedTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
                                );



                    }
                });
                var table = $('#RefunddisbursedTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
                                );



                    }
                });

                var table = $('#RefundnotdisbursedTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
                                );



                    }
                });
                var table = $('#PSDFdisbursedTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
            });
        </script>
    </head>
    <body style="text-align: center; alignment-adjust: central;width: 95%; min-height: 3000px;" id="alldis" onload="setIframeHeight();">
        <h3 align="center" style="color:#003366;" >Payment Disbursed Details </h3>
        <table id="disbursedTable" align="center" style=width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Disburse ID</th>
                    <th>Billing Date</th>
                    <th>Week-ID</th>
                    <!--<th>Disbursed Date</th>-->
                    <th>Commercial Group</th>
                    <th>Software Disburse Date</th>
                    <th>Bill Amount</th>
                    <th>Diff. Amount wrt Rev.</th>
                    <th>Pending Amount</th>
                    <th>Disburse Amount</th>
                    <th>Bill Type</th>
                    <th>Category</th>
                    <th>Rev. No.</th>
                    <th>Remarks</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${paidList}" var="ele">
                    <tr>
                        <td>${"B"}${ele.disburseId}</td>
                        <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" var="billingDate" />
                        <td>${billingDate}</td>
                        <td>${ele.weekId}</td>
                        <td>${ele.corporates.corporateName}</td>
                        <fmt:formatDate value="${ele.disbursalDate}" pattern="dd-MM-yyyy" var="disbursalDate" />
                        <td>${disbursalDate}</td>
                        <td>${ele.totalAmount}</td>
                        <c:if test="${ele.billReceiveCorp.revisionNo =='0' }">
                            <td>${ele.billReceiveCorp.toalnet}</td>
                        </c:if>
                        <c:if test="${ele.billReceiveCorp.revisionNo !='0' }">
                            <td>${ele.billReceiveCorp.revisedpaybale}</td>
                        </c:if>
                        <!--<td>${ele.totalAmount-ele.disburseAmount}</td>-->
                        <td>${ele.billReceiveCorp.pendingAmount}</td>
                        <td>${ele.disburseAmount}</td>
                        <td>${ele.billReceiveCorp.billType}</td>
                        <td>${ele.billReceiveCorp.billCategory}</td>
                        <td>${ele.billReceiveCorp.revisionNo}</td>
                        <td>${ele.remarks}</td>
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
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </tfoot>
        </table>
        <h3 align="center" style="color:#003366;" >Payment Not Disbursed Details </h3>
        <table id="NotdisbursedTable" align="center" style="width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Dummy ID</th>
                    <th>Billing Date</th>
                    <th>Week-ID</th>
                    <th>Commercial Group</th>
                    <th>Disburse Date</th>
                    <th>Bill Amount</th>
                    <th>Diff. Amount wrt Rev.</th>
                    <th>Pending Amount</th>
                    <th>Disburse Amount</th>
                    <th>Bill Type</th>
                    <th>Category</th>
                    <th>Rev. No.</th>
                    <th>Remarks</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${remarksList}" var="ele">
                    <tr>
                        <td>${"B"}${ele.disburseId}</td>
                        <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" var="billingDate" />
                        <td>${billingDate}</td>
                        <td>${ele.weekId}</td>
                        <td>${ele.corporates.corporateName}</td>
                        <fmt:formatDate value="${ele.disbursalDate}" pattern="dd-MM-yyyy" var="disbursalDate" />
                        <td>${disbursalDate}</td>
                        <td>${ele.totalAmount}</td>
                        <c:if test="${ele.billReceiveCorp.revisionNo =='0' }">
                            <td>${ele.billReceiveCorp.toalnet}</td>
                        </c:if>
                        <c:if test="${ele.billReceiveCorp.revisionNo !='0' }">
                            <td>${ele.billReceiveCorp.revisedpaybale}</td>
                        </c:if>
                        <td>${ele.billReceiveCorp.pendingAmount}</td>
                        <td>${ele.disburseAmount}</td>
                        <td>${ele.billReceiveCorp.billType}</td>
                        <td>${ele.billReceiveCorp.billCategory}</td>
                        <td>${ele.billReceiveCorp.revisionNo}</td>
                        <td>${ele.remarks}</td>
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
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </tfoot>
        </table>
        <p>&nbsp;</p>
        <h3 align="center" style="color:#003366;" >Interest Disbursed Details </h3>


        <table id="InterestdisbursedTable" align="center" style="width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Disburse ID</th>
                    <th>Billing Date</th>
                    <th>Week-ID</th>
                    <th>Pool Member</th>
                    <th>Bill Year</th>
                    <th>Software Interest Paid Date</th>
                    <th>Interest Amount</th>
                    <th>Interest Paid Amount</th>
                    <th>Pending Amount</th>
                    <th>Bill Type</th>
                    <th>Revision</th>
                    <th>Category</th>
                    <th>Remarks</th>
                </tr>
            </thead>
            <tbody>


                <c:forEach items="${interestDisburseList}" var="ele">


                    <tr>
                        <td>${"I"}${ele.slno}</td>
                        <td>${ele.disbursedInterestDetails.billingDate}</td>
                        <td>${ele.disbursedInterestDetails.weekId}</td>
                        <td>${ele.disbursedInterestDetails.corporates.corporateName}</td>
                        <td>${ele.disbursedInterestDetails.billYear}</td>
                        <td>${ele.interestPaiddate}</td>
                        <td>${ele.disbursedInterestDetails.interestAmount}</td>
                        <td>${ele.interestPaidamount}</td>
                        <td>${ele.interestPendingamount}</td>
                        <td>${ele.disbursedInterestDetails.billType}</td>
                        <td>${ele.disbursedInterestDetails.revisionNo}</td>
                        <td>${ele.disbursedInterestDetails.billCategory}</td>
                        <td>${ele.remarks}</td>


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
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </tfoot>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p>


        <h3 align="center" style="color:#003366;" >Interest Not Disbursed Details </h3>


        <table id="InterestnotdisbursedTable" align="center" style="width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Dummy ID</th>
                    <th>Billing Date</th>
                    <th>Week-ID</th>
                    <th>Pool Member</th>
                    <th>Bill Year</th>
                    <th>Interest Paid Date</th>
                    <th>Interest Amount</th>
                    <th>Interest Paid Amount</th>
                    <th>Pending Amount</th>
                    <th>Bill Type</th>
                    <th>Revision</th>
                    <th>Category</th>
                    <th>Remarks</th>
                </tr>
            </thead>
            <tbody>


                <c:forEach items="${interestDisburseListremark}" var="ele">


                    <tr>
                        <td>${"I"}${ele.slno}</td>
                        <td>${ele.disbursedInterestDetails.billingDate}</td>
                        <td>${ele.disbursedInterestDetails.weekId}</td>
                        <td>${ele.disbursedInterestDetails.corporates.corporateName}</td>
                        <td>${ele.disbursedInterestDetails.billYear}</td>
                        <td>${ele.interestPaiddate}</td>
                        <td>${ele.disbursedInterestDetails.interestAmount}</td>
                        <td>${ele.interestPaidamount}</td>
                        <td>${ele.interestPendingamount}</td>
                        <td>${ele.disbursedInterestDetails.billType}</td>
                        <td>${ele.disbursedInterestDetails.revisionNo}</td>
                        <td>${ele.disbursedInterestDetails.billCategory}</td>
                        <td>${ele.remarks}</td>

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
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </tfoot>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p><p>&nbsp;</p>

        <p>&nbsp;</p>
        <h3 align="center" style="color:#003366;" >Refund Disbursed Details </h3>


        <table id="RefunddisbursedTable" align="center" style="width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Disburse ID</th>
                    <th>Billing Date</th>
                    <th>Bill Year</th>
                    <th>Week-ID</th>
                    <th>Pool Member</th>
                    <th>Software Refund Disburse Date</th>
                    <th>Bill Amount</th>
                    <th>Diff Amount wrt Rev.</th>
                    <th>Paid Amount</th>
                    <th>Pending Amount</th>
                    <th>Bill Type</th>
                    <th>Revision</th>
                    <th>Category</th>
                    <th>Remarks</th>
                </tr>
            </thead>
            <tbody>


                <c:forEach items="${refundDisburseList}" var="ele">


                    <tr>
                        <td>${"R"}${ele.slno}</td>
                        <td>${ele.billPayableCorp.billingDate}</td>
                        <td>${ele.billPayableCorp.billYear}</td>
                        <td>${ele.billPayableCorp.weekId}</td>
                        <td>${ele.corporates.corporateName}</td>
                        <td>${ele.refundDate}</td>
                        <td>${ele.totalAmount}</td>
                        <c:if test="${ele.billPayableCorp.revisionNo =='0' }">
                            <td>${ele.billPayableCorp.totalAmount}</td>
                        </c:if>
                        <c:if test="${ele.billPayableCorp.revisionNo !='0' }">
                            <td>${ele.billPayableCorp.revisedrefund}</td>
                        </c:if>
                        <!--<td>${ele.totalAmount}</td>-->
                        <td>${ele.paidAmount}</td>
                        <td>${ele.pendingAmount}</td>

                        <td>${ele.billPayableCorp.billType}</td>
                        <td>${ele.billPayableCorp.revisionNo}</td>
                        <td>${ele.billPayableCorp.billCategory}</td>
                        <td>${ele.remarks}</td>

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
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </tfoot>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p>

        <h3 align="center" style="color:#003366;" >Refund Not Disbursed Details </h3>
        <table id="RefundnotdisbursedTable" align="center" style="width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Dummy ID</th>
                    <th>Billing Date</th>
                    <th>Bill Year</th>
                    <th>Week-ID</th>
                    <th>Pool Member</th>
                    <th>Refund Disburse Date</th>
                    <th>Bill Amount</th>
                    <th>Diff. Amount wrt Rev.</th>
                    <th>Paid Amount</th>
                    <th>Pending Amount</th>
                    <th>Bill Type</th>
                    <th>Revision</th>
                    <th>Category</th>
                    <th>Remarks</th>
                </tr>
            </thead>
            <tbody>


                <c:forEach items="${refundDisburseListremark}" var="ele">


                    <tr>
                        <td>${"R"}${ele.slno}</td>
                        <td>${ele.billPayableCorp.billingDate}</td>
                        <td>${ele.billPayableCorp.billYear}</td>
                        <td>${ele.billPayableCorp.weekId}</td>
                        <td>${ele.corporates.corporateName}</td>
                        <td>${ele.refundDate}</td>
                        <td>${ele.totalAmount}</td>
                        <c:if test="${ele.billPayableCorp.revisionNo =='0' }">
                            <td>${ele.billPayableCorp.totalAmount}</td>
                        </c:if>
                        <c:if test="${ele.billPayableCorp.revisionNo !='0' }">
                            <td>${ele.billPayableCorp.revisedrefund}</td>
                        </c:if>
                        <!--<td>${ele.totalAmount}</td>-->
                        <td>${ele.paidAmount}</td>
                        <td>${ele.pendingAmount}</td>

                        <td>${ele.billPayableCorp.billType}</td>
                        <td>${ele.billPayableCorp.revisionNo}</td>
                        <td>${ele.billPayableCorp.billCategory}</td>
                        <td>${ele.remarks}</td>

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
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </tfoot>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <h3 align="center" style="color:#003366;" >PSDF Disbursed Details </h3>


        <table id="PSDFdisbursedTable" align="center" style="width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Disburse ID</th>
                    <th>Software Disbursed Date</th>
                    <th>Month</th>
                    <th>Disbursed PSDF Amount</th>
                    <th>Main Pool Balance After Disbursement</th>
                    <th>Category</th>
                </tr>
            </thead>
            <tbody>


                <c:forEach items="${psdfDisburseList}" var="ele">
                    <tr>
                        <td>${"P"}${ele.slno}</td>
                        <td>${ele.entryDate}</td>
                        <td>${ele.csdfMonth}</td>
                        <td>${ele.csdfAmount}</td>
                        <td>${ele.mainPoolBalance - ele.csdfAmount}</td>
                        <td>${ele.amtCategory}</td>


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

                </tr>
            </tfoot>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>  <p>&nbsp;</p>
    </body>
</html>
