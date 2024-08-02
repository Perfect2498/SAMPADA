<%--
    Document   : viewSelectedDatesVerifiedBankStmt
    Created on : Sep 24, 2019, 2:47:01 PM
    Author     : cdac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            $(document).ready(function () {

                var table = $('#bankStmt').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
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
    </head>
    <body style="text-align: center; alignment-adjust: central;width: 99%;min-height: 500px;">
        <h3 style="color:#003366;">Verified Detailed Bank Statement </h3>
        <form>
            <c:set var="serialno" value="${0}" />
            <input type="date" name="fromDate" value="${frmDate}" hidden/>
            <p style="color:#003366;font-size: 16px;">Transaction Start Date:${frmDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Transaction End Date:${endDate}</p>
            <table align="center" id="bankStmt"  class="customerTable">
                <thead style="height:30px;">
                    <tr>

                        <th>S. No.</th>
                        <th>Commercial Group Name</th>
                        <th>Agency Code</th>
                        <th>Bank Sub Account Number</th>
                        <th>Transaction Date</th>
                        <th>Transaction Time</th>
                        <th>Transaction Desc </th>
                        <th>CR/DR Flag</th>
                        <th>Disburse Id</th>
                        <th>Transaction Amount(Rs)</th>
                        <th>Transaction Available Balance(Rs)</th>

                        <!--<th>Transaction Type</th>-->
                        <!--<th>Opening Balance(Rs) </th>-->
                        <!--<th>Main Balance (Rs)</th>-->
                        <th>Entry DateTime</th>
                        <th>Remarks</th>
                    </tr>

                </thead>
                <tbody>
                    <c:forEach items="${stmtInfoList}" var="objectList">
                        <c:set var="count" value="${0}" />
                        <tr>
                            <c:set var="serialno" value="${serialno + 1}" />
                            <td><c:out value="${serialno}"/></td>
                            <c:forEach items="${objectList}" var="object">
                                <c:set var="count" value="${count+1}" />

                                <c:if test="${count eq 4  }">
                                    <fmt:formatDate value="${object}" pattern="dd-MM-yyyy" var="billDueDate" />

                                    <td>${billDueDate}</td>

                                </c:if>
                                <c:if test="${count eq 11  }">
                                </c:if>
                                <c:if test="${count ne 4 && count ne 11 && count ne 12 }">
                                    <td>${object.toString()}</td>

                                </c:if>
                                <c:if test="${count eq 12  }">
                                    <fmt:formatDate value="${object}" pattern="dd-MM-yyyy hh:mm:ss" var="billDueDate" />

                                    <td>${billDueDate}</td>

                                </c:if>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td>Total:</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td></td><td></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                    </tr>
                </tfoot>
            </table>


        </form>

        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>

    </body>
</html>
