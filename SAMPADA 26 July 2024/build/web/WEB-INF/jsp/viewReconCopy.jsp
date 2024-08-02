<%--
    Document   : viewReconCopy
    Created on : Dec 13, 2019, 4:44:42 PM
    Author     : cdac
--%>



<%--

Document : viewReconciliationReport

Created on : Nov 29, 2019, 9:40:20 AM

Author : JaganMohan

--%>





<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>







<!DOCTYPE html>



<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" >

        <script src="<c:url value="js/jquery-1.9.1.js" />"></script>

        <script src="<c:url value="js/jquery-ui.js" />" ></script>

        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>

        <link type="text/css" href="<c:url value="/jquery.ui.datepicker.monthyearpicker.css"/>" rel="stylesheet" />

        <script type="text/javascript" src="<c:url value="/jquery.ui.datepicker.monthyearpicker.js"/>"></script>

        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/bootstrap.min.js" />" type="text/javascript"></script>
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
            $(document).ready(function () {

                var table = $('#reconTable').DataTable({
                    "ordering": true,
                    "bFilter": false,
                    "bPaginate": true,
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
                    "columnDefs": [{
                            orderable: false,
                            targets: "no-sort"
                        }],
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




                    }
                });
                table.columns().iterator('column', function (ctx, idx) {
                    $(table.column(idx).header()).append('<span class="sort-icon"/>');
                });

                $('#reconTable tbody')
                        .on('mouseenter', 'td', function () {
                            var colIdx = table.cell(this).index().column;

                            $(table.cells().nodes()).removeClass('highlight');
                            $(table.column(colIdx).nodes()).addClass('highlight');
                        });

            });

        </script>



    </head>

    <body style="min-height: 500px;color:#003366;">

        <form>

            <h4 align="center">Reconciliation for Pool Member :${corpName}</h4>

            <table align="center" width="100%" border="yes" id="reconTable">
                <thead>


                    <tr><th>Bill Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>Calculation Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>Bank Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th rowspan="2">Outstanding</th></tr>

                    <tr>

                        <th class="no-sort">Week ID</th>

                        <th class="no-sort">Entry Date </th>

                        <th class="no-sort">Bill Type</th>

                        <th class="no-sort">Bill Date</th>

                        <th class="no-sort">Bill Category</th>

                        <th class="no-sort">Bill Duedate</th>

                        <th class="no-sort">Pay</th>

                        <th class="no-sort">Recv</th>

                        <!--                    <th>&nbsp;</th>-->



                        <th class="no-sort">Pay by Party</th>

                        <th class="no-sort">Pay (after settle)</th>

                        <th class="no-sort">Recv by Party</th>

                        <th class="no-sort">Recv (after settle)</th>

                        <!--                    <th>&nbsp;</th>-->





                        <th class="no-sort">Credit Date</th>

                        <th class="no-sort">Credit in Bank</th>

                        <th class="no-sort">Credit settled</th>

                        <th class="no-sort">Credit Available</th>

                        <th class="no-sort">Debit in Bank</th>

                        <th class="no-sort">Debit settled</th>

                        <!--<th>&nbsp;</th>-->



                    </tr>
                </thead>



                <c:set var="payoutstanding" scope="application" value="${0}"/>





                <c:forEach items="${dateList}" var="grpdate1">

                    <fmt:formatDate value="${grpdate1}" pattern="yyyy-MM-dd" var="groupdate" />



                    <c:forEach items="${mapList}" var="ele1">



                        <fmt:formatDate value="${ele1.entryDate}" pattern="yyyy-MM-dd" var="mappeddate" />



                        <c:if test="${groupdate == mappeddate}">

                            <tbody>

                                <tr>

                                    <c:set var="checkerepeated" scope="application" value="${0}"/>

                                    <td>${ele1.billPayableCorp.weekId}</td>

                                    <td>${ele1.entryDate}</td>

                                    <td>${ele1.billPayableCorp.billType}</td>

                                    <td>${ele1.billPayableCorp.billingDate}</td>

                                    <td>${ele1.billPayableCorp.billCategory}</td>

                                    <td>${ele1.billPayableCorp.billDueDate}</td>

                                    <td>${ele1.billPayableCorp.totalnet}</td>

                                    <td>&nbsp;</td>

                                    <!--<td style="width: 70px;">&nbsp;</td>-->


                                    <td>${ele1.mappedAmount}</td>

<!--                                <td>${ele1.pendingAmount+ele1.mappedAmount}</td>-->

                                    <td>${ele1.pendingAmount}</td>

                                    <td>&nbsp;</td>

                                    <td>&nbsp;</td>





                                    <!--<td style="width: 70px;">&nbsp;</td>-->

                                    <td>${ele1.bankStatement.amountDate}</td>

                                    <td>${ele1.bankStatement.paidAmount}</td>

                                    <td>${ele1.mappedAmount}</td>

                                    <td>${ele1.pendingBankAmount}</td>

                                    <td>&nbsp;</td>

                                    <td>&nbsp;</td>

                                    <!--<td style="width: 70px;">&nbsp;</td>-->


                                    <c:forEach items="${repeatedlist}" var="repeatedlist1">
                                        <c:if test="${ ele1.uniqueId == repeatedlist1}">
                                            <c:set var="payoutstanding" value="${payoutstanding - ele1.mappedAmount}"/>
                                            <c:set var="checkerepeated" scope="application" value="${1}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${ checkerepeated == 0}">
                                        <c:set var="payoutstanding" value="${payoutstanding + ele1.pendingAmount}"/>
                                    </c:if>



                                    <td>${payoutstanding}</td>

                                </tr>



                            </c:if>





                        </c:forEach>



                        <c:forEach items="${bankdislist}" var="bank">

                            <fmt:formatDate value="${bank.amountDate}" pattern="yyyy-MM-dd" var="disburseddate" />



                            <c:if test="${disburseddate == groupdate}">



                                <tr>

                                    <c:set var="checkerepeated" scope="application" value="${0}"/>

                                    <td>${bank.paymentDisbursement.weekId}</td>

                                    <td>${bank.amountDate}</td>

                                    <td>${bank.paymentDisbursement.billReceiveCorp.billType}</td>

                                    <td>${bank.paymentDisbursement.billingDate}</td>

                                    <td>${bank.paymentDisbursement.disburseCategory}</td>

                                    <td>${bank.paymentDisbursement.billDueDate}</td>

                                    <td>&nbsp;</td>

                                    <td>${bank.paymentDisbursement.billReceiveCorp.toalnet}</td>



                                    <!--<td style="width: 70px;">&nbsp;</td>-->



                                    <td>&nbsp;</td>

                                    <td>&nbsp;</td>

                                    <td>${bank.paymentDisbursement.totalAmount}</td>

                                    <td>${bank.paymentDisbursement.pendingAmount}</td>





                                    <!--<td style="width: 70px;">&nbsp;</td>-->



                                    <td>${bank.amountDate}</td>

                                    <td>&nbsp;</td>

                                    <td>&nbsp;</td>

                                    <td>&nbsp;</td>

                                    <td>${bank.paidAmount}</td>

                                    <td>${bank.paymentDisbursement.disburseAmount}</td>

                                    <!--<td style="width: 70px;">&nbsp;</td>-->

                                    <c:forEach items="${repeatedlistrec}" var="repeatedlistrec1">
                                        <c:if test="${ bank.paymentDisbursement.disburseId == repeatedlistrec1}">
                                            <c:set var="payoutstanding" value="${payoutstanding + bank.paymentDisbursement.disburseAmount}"/>
                                            <c:set var="checkerepeated" scope="application" value="${1}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${ checkerepeated == 0}">
                                        <c:set var="payoutstanding" value="${-(bank.paymentDisbursement.pendingAmount)+payoutstanding}"/>
                                    </c:if>



                                    <td>${payoutstanding}</td>

                                </tr>
                            </tbody>

                        </c:if>

                    </c:forEach>



                </c:forEach>



            </table>

            <p>&nbsp;</p>

            <p>&nbsp;</p>

        </form>
        <p>&nbsp;</p>

        <p>&nbsp;</p>

    </body>



</html>