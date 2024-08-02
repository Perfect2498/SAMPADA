<%--
    Document   : viewWeeklyDsmBill
    Created on : Jun 19, 2019, 9:35:21 AM
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
        <title>DSM Bill Display Page</title>
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
                var billDate = $("#billDate").val();
                var fromDate = $("#fromDate").val();
                var toDate = $("#toDate").val();
                var entryDate = $("#entryDate").val();
                var weekId = $("#weekId").val();
                var revisionNo = $("#revisionNo").val();
                var dueDate = $("#dueDate").val();

                var table = $('#payableTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'DSM Bill Week No. ' + weekId + 'Revision No. ' + revisionNo + 'Issue Date:' + billDate + 'Due Date:' + dueDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n         Week No. ' + weekId + '      Bill Issue Date:' + billDate + '    Bill Due Date:' + dueDate + '        Bill Upload Date: ' + entryDate +
                                        '\r\n \r\n DETAILS OF DSM CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
                                        '\r\n \r\n Revision No. ' + revisionNo +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'DSM Bill Week No. ' + weekId + 'Revision No. ' + revisionNo + 'Issue Date:' + billDate + 'Due Date:' + dueDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n Bill Issue Date:' + billDate + '       Week No. ' + weekId + '        Bill Upload Date: ' + entryDate +
                                        '\r\n \r\n DETAILS OF DSM CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
                                        '\r\n \r\n Revision No. ' + revisionNo +
                                        '\r\n \r\n (All figures in Rs.)';
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
               

            });
        </script>
    </head>
    <body>
        <form>
            <br/>
            <p style="text-align:center;" ><c:forEach items="${billDsmInfo}" var="ele"><b>
                        <input type="text" name="billDate" id="billDate" value="${ele.billingDate}" hidden/>
                        <input type="text" name="fromDate" id="fromDate" value="${ele.weekFromdate}" hidden/>
                        <input type="text" name="toDate" id="toDate" value="${ele.billingDate}" hidden/>
                        <input type="text" name="entryDate" id="entryDate" value="${ele.entryDate}" hidden/>
                        <input type="text" name="billDate" id="weekId" value="${ele.weekId}" hidden/>
                        <input type="text" name="revisionNo" id="revisionNo" value="${ele.revisionNo}" hidden/>
                        <input type="text" name="dueDate" id="dueDate" value="${ele.billDueDate}" hidden/>


                        Week No.:${ele.weekId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bill Issue Date: <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bill Due Date: <fmt:formatDate value="${ele.billDueDate}" pattern="dd-MM-yyyy" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bill Upload Date: <fmt:formatDate value="${ele.entryDate}" pattern="dd-MM-yyyy" /></b></p>
                <p style="text-align:center;">DETAILS OF DSM CHARGES FOR THE WEEK FROM <b><fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> TO <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" /></b></p>
                <p style="text-align:center;"><b>Revision No. ${ele.revisionNo}</b></p>
            </c:forEach>
            <p style="text-align:right; margin-right: 18px;"><i>(All figures in Rs.)</i></p>
            <table align="center" class="customerTable" id="payableTable" style="width:90%;">
                <thead style="height:30px;">
                    <tr>
                        <th>S. No. </th>
                        <th>Entity</th>
                        <th>DSM Charges Payable</th>
                        <th>DSM Charges Receivable</th>
                        <th>Capping DSM Charges</th>
                        <th>Additional DSM Charges</th>
                        <th>Sign Violation Charges</th>
                        <th>Net DSM Payable/Receivable </th>
                        <th>WRLDC Remarks</th>
                    </tr>

                </thead>
                <tbody>
                    <tr><c:set var="serialno" value="${0}"/>
                        <c:set var="serialno" value="${serialno + 1}" />
                        <td><c:out value="${serialno}"/></td>
                        <td  style="background-color: linen;text-align: center;color:GREEN;font-size: 30;">INTER REGIONAL</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>

                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'INTER REGIONAL'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>${ele.receivableCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>

                    <tr>
                        <c:set var="serialno" value="${serialno + 1}" />
                        <td><c:out value="${serialno}"/></td>

                        <td style="background-color: linen;text-align: center;">BENEFICIERIES/BUYERS OF WR</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>

                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'BENEFECIERIES/BUYERS WR'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>${ele.receivableCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>


                    <tr>

                        <c:set var="serialno" value="${serialno + 1}" />
                        <td><c:out value="${serialno}"/></td>
                        <td style="background-color: linen;text-align: center;">GENERATING STATIONS(TARIFF DETERMINED BY CERC)</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>

                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'GENERATING STATIONS(TARIFF DETERMINED BY CERC)'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>${ele.receivableCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>

                    <tr>
                        <c:set var="serialno" value="${serialno + 1}" />
                        <td><c:out value="${serialno}"/></td>
                        <td style="background-color: linen;text-align: center;">OTHER GENERATING STATIONS</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>

                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'OTHER GENERATING STATIONS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>${ele.receivableCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>

                    <tr>
                        <c:set var="serialno" value="${serialno + 1}" />
                        <td><c:out value="${serialno}"/></td>
                        <td style="background-color: linen;text-align: center;">WIND AND SOLAR GENERATORS</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>

                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'WIND GENERATORS' || ele.entites.entityType =='SOLAR GENERATORS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>${ele.receivableCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>

                    <tr>
                        <c:set var="serialno" value="${serialno + 1}" />
                        <td><c:out value="${serialno}"/></td>
                        <td style="background-color: linen;text-align: center;">INFIRM GENERATORS</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>


                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'INFIRM GENERATORS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>${ele.receivableCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>

                </tbody>
                <tfoot>
                    <tr>
                        <td>Total:</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </tfoot>
            </table>
        </form>

        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <br/>
    </body>
</html>

