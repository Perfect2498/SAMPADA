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
                    responsive: true,
                    "pageLength": 25,
//                    "order": [[0, "asc"]],
                    "ordering": false,
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
//                            pageSize: 'LEGAL' ,
                            title: 'Reconciliation Report :',
                            footer: true
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            pageSize: 'A3',
                            orientation: 'landscape',
                            title: 'Reconciliation Report :',
                            footer: true
                        }
                    ],
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
        <style>
            .makedisable {
                pointer-events: none;
            }
        </style>
    </head>
    <body style="color:#003366;">
        <form>
            <h4 align="center">Reconciliation for Pool Member :${corpName}</h4>
            <h4 align="center">Former Out Standing Amount is :${outstanding}</h4>

            <h4 align="center">Default Sorting Sequence Is As Per Event Date</h4>

            <table width="100%" cellspacing="0" id="reconTable" class="customerTable">
                <thead>
                    <tr><th>Bill Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>Calculation Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>Bank Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th rowspan="2">Outstanding</th><th rowspan="2">REMARKS</th></tr>
                    <tr>
                        <th class="makedisable">Week ID</th>
                        <th class="makedisable">Event Date </th>
                        <th class="makedisable">Bill Type</th>
                        <th class="makedisable">Bill Date</th>
                        <th class="makedisable">Revision No.</th>
                        <th class="makedisable">Due + Grace Period</th>
                        <th class="makedisable">Payable asper Bill</th>
                        <th class="makedisable">Receivable as per Bill</th>
                        <!-- <th>&nbsp;</th>-->


                        <th class="makedisable">Pay. Difference wrt Rev.</th>
                        <th class="makedisable">Payable Pending</th>
                        <th class="makedisable">Rec. Difference wrt Rev.</th>
                        <th class="makedisable">Receivable Pending</th>
                        <!-- <th>&nbsp;</th>-->


                        <th class="makedisable">Bank CR/DR DATE</th>
                        <th class="makedisable">CR_AMOUNT</th>
                        <th class="makedisable">CR AMOUNT Settled</th>
                        <th class="makedisable">CR_AVAILABLE after settled</th>
                        <th class="makedisable">DR_AMOUNT</th>
                        <th class="makedisable">DR AMOUNT Settled</th>
                        <!--<th>&nbsp;</th>-->
                    </tr>
                </thead>
                <tbody>

                    <c:set var="payoutstanding" scope="application" value="${outstanding}"/>




                    <c:forEach items="${dateList}" var="grpdate1">
                        <fmt:formatDate value="${grpdate1}" pattern="yyyy-MM-dd" var="groupdate" />

                        <c:forEach items="${listbillpay}" var="billpay">
                            <fmt:formatDate value="${billpay.billingDate}" pattern="yyyy-MM-dd" var="billingDate" />
                            <c:if test="${groupdate == billingDate}">
                                <tr>
                                    <td>${billpay.weekId}</td>
                                    <td>${billpay.billingDate}</td>
                                    <td>${billpay.billType}</td>
                                    <td>${billpay.billingDate}</td>
                                    <td>${billpay.revisionNo}</td>
                                    <td>${billpay.billDueDate}</td>
                                    <td>${billpay.totalnet}</td>
                                    <td>&nbsp;</td>
                                    <c:if test="${billpay.revisionNo == 0}">
                                        <td>${billpay.totalnet}</td>
                                        <td>${billpay.totalnet}</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <c:set var="payoutstanding" value="${payoutstanding + billpay.totalnet}"/>
                                        <td>${payoutstanding}</td>
                                        <td>Payable Pending</td>
                                    </c:if>
                                    <c:if test="${billpay.revisionNo != 0}">
                                        <c:choose>
                                            <c:when test="${billpay.billStatus == 'REFUND'}">
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>${billpay.revisedrefund}</td>
                                                <td>${billpay.revisedrefund}</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <c:set var="payoutstanding" value="${payoutstanding - billpay.revisedrefund}"/>
                                                <td>${payoutstanding}</td>
                                                <td>Payable Rev Refund</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${billpay.revisedpaybale}</td>
                                                <td>${billpay.revisedpaybale}</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <c:set var="payoutstanding" value="${payoutstanding + billpay.revisedpaybale}"/>
                                                <td>${payoutstanding}</td>
                                                <td>Payable Rev Pending</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>


                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:forEach items="${listbillrec}" var="billrec">
                            <fmt:formatDate value="${billrec.billingDate}" pattern="yyyy-MM-dd" var="billingDater" />
                            <c:if test="${groupdate == billingDater}">
                                <tr>
                                    <td>${billrec.weekId}</td>
                                    <td>${billrec.billingDate}</td>
                                    <td>${billrec.billType}</td>
                                    <td>${billrec.billingDate}</td>
                                    <td>${billrec.revisionNo}</td>
                                    <td>${billrec.billDueDate}</td>
                                    <td>&nbsp;</td>
                                    <td>${billrec.toalnet}</td>
                                    <c:if test="${billrec.revisionNo == 0}">
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>${billrec.toalnet}</td>
                                        <td>${billrec.toalnet}</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <c:set var="payoutstanding" value="${payoutstanding - billrec.toalnet}"/>
                                        <td>${payoutstanding}</td>
                                        <td>Receivable Pending</td>
                                    </c:if>
                                    <c:if test="${billrec.revisionNo != 0}">
                                        <c:choose>
                                            <c:when test="${billrec.disburseStatus == 'REFUND'}">
                                                <td>${billrec.revisedrefund}</td>
                                                <td>${billrec.revisedrefund}</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <c:set var="payoutstanding" value="${payoutstanding + billrec.revisedrefund}"/>
                                                <td>${payoutstanding}</td>
                                                <td>Receivable Refund</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>${billrec.revisedpaybale}</td>
                                                <td>${billrec.revisedpaybale}</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <c:set var="payoutstanding" value="${payoutstanding - billrec.revisedpaybale}"/>
                                                <td>${payoutstanding}</td>
                                                <td>Receivable Rev Pending</td>
                                                <!--                                                          <tr>
                                                                                    <td>benny</td>
                                                                                </tr>-->
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </tr>
                            </c:if>
                        </c:forEach>



                        <c:forEach items="${mapList}" var="ele1">
                            <fmt:formatDate value="${ele1.entryDate}" pattern="yyyy-MM-dd" var="mappeddate" />
                            <c:if test="${groupdate == mappeddate}">
                                <tr>
                                    <c:set var="checkerepeated" scope="application" value="${0}"/>
                                    <td>${ele1.billPayableCorp.weekId}</td>
                                    <td>${ele1.entryDate}</td>
                                    <td>${ele1.billPayableCorp.billType}</td>
                                    <td>${ele1.billPayableCorp.billingDate}</td>
                                    <td>${ele1.billPayableCorp.revisionNo}</td>
                                    <td>${ele1.billPayableCorp.billDueDate}</td>
                                    <td>${ele1.billPayableCorp.totalnet}</td>
                                    <td>&nbsp;</td>
                                    <td>${ele1.pendingAmount+ele1.mappedAmount}</td>
                                    <td>${ele1.pendingAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>                                  
                                    <td>${ele1.bankStatement.amountDate}</td>
                                    <td>${ele1.bankStatement.paidAmount}</td>
                                    <td>${ele1.mappedAmount}</td>
                                    <td>${ele1.pendingBankAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <c:set var="payoutstanding" value="${payoutstanding - ele1.mappedAmount}"/>
                                    <td>${payoutstanding}</td>
                                    <td>payable Map</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:forEach items="${maprefundbank}" var="eleref">
                            <fmt:formatDate value="${eleref.tempRefundBillCorp.checkerDate}" pattern="yyyy-MM-dd" var="mappedrefdate" />
                            <c:if test="${groupdate == mappedrefdate}">
                                <tr>

                                    <td>${eleref.tempRefundBillCorp.billReceiveCorp.weekId}</td>
                                    <td>${eleref.tempRefundBillCorp.refundDate}</td>
                                    <td>${eleref.tempRefundBillCorp.billReceiveCorp.billType}</td>
                                    <td>${eleref.tempRefundBillCorp.billReceiveCorp.billingDate}</td>
                                    <td>${eleref.tempRefundBillCorp.billReceiveCorp.revisionNo}</td>
                                    <td>${eleref.tempRefundBillCorp.billReceiveCorp.billDueDate}</td>
                                    <td>&nbsp;</td>
                                    <td>${eleref.tempRefundBillCorp.billReceiveCorp.toalnet}</td>


                                    <td>${eleref.tempRefundBillCorp.paidAmount+eleref.tempRefundBillCorp.pendingAmount}</td>
                                    <td>${eleref.tempRefundBillCorp.pendingAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>




                                    <!--<td style="width: 70px;">&nbsp;</td>-->
                                    <td>${eleref.bankStatement.amountDate}</td>
                                    <td>${eleref.bankStatement.paidAmount}</td>
                                    <td>${eleref.mappedAmount}</td>
                                    <td>${eleref.bankPendingAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <!--<td style="width: 70px;">&nbsp;</td>-->



                                    <c:set var="payoutstanding" value="${payoutstanding - eleref.tempRefundBillCorp.paidAmount}"/>
                                    <td>${payoutstanding}</td>
                                    <td>Refund Map</td>
                                </tr>
                            </c:if>
                        </c:forEach>




                        <c:forEach items="${bankdislist}" var="bank">
                            <fmt:formatDate value="${bank.amountDate}" pattern="yyyy-MM-dd" var="disburseddate" />


                            <c:if test="${disburseddate == groupdate}">

                                <c:forEach items="${listpaydiburse}" var="paydis">
                                    <c:if test="${paydis.disburseId == bank.disburseId && bank.disburseType=='Bills'}">
                                        <tr>
                                            <c:set var="checkerepeated" scope="application" value="${0}"/>
                                            <td>${paydis.weekId}</td>
                                            <td>${bank.amountDate}</td>
                                            <td>${paydis.billReceiveCorp.billType}</td>
                                            <td>${paydis.billingDate}</td>
                                            <td>${paydis.billReceiveCorp.revisionNo}</td>
                                            <td>${paydis.billDueDate}</td>
                                            <td>&nbsp;</td>
                                            <td>${paydis.billReceiveCorp.toalnet}</td>


                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>${paydis.totalAmount}</td>
                                            <td>${paydis.pendingAmount}</td>




                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <td>${bank.amountDate}</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>${bank.paidAmount}</td>
                                            <td>${paydis.disburseAmount}</td>
                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <c:set var="payoutstanding" value="${payoutstanding + paydis.disburseAmount}"/>
                                            <td>${payoutstanding}</td>
                                            <td>Receivable Disbursed</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                <c:forEach items="${temprefund}" var="temprefund">
                                    <c:if test="${temprefund.slno == bank.disburseId && bank.disburseType=='Refund'}">
                                        <tr>


                                            <td>${temprefund.weekid}</td>
                                            <td>${bank.amountDate}</td>
                                            <td>${temprefund.billPayableCorp.billType}</td>
                                            <td>${temprefund.billPayableCorp.billingDate}</td>
                                            <td>${temprefund.billPayableCorp.revisionNo}</td>
                                            <td>${temprefund.billPayableCorp.billDueDate}</td>


                                            <td>${temprefund.billPayableCorp.totalnet}</td>
                                            <td>&nbsp;</td>
                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>${temprefund.billPayableCorp.revisedrefund}</td>
                                            <td>${temprefund.pendingAmount}</td>




                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <td>${bank.amountDate}</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>${bank.paidAmount}</td>
                                            <td>${temprefund.paidAmount}</td>
                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <c:set var="payoutstanding" value="${payoutstanding + temprefund.paidAmount}"/>
                                            <td>${payoutstanding}</td>
                                            <td>Refund Disbursed</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                        <c:forEach items="${tempintdetlis}" var="eleint">
                            <fmt:formatDate value="${eleint.entryDate}" pattern="yyyy-MM-dd" var="intgendate" />
                            <c:if test="${groupdate == intgendate}">
                                <tr>
                                    <td>${eleint.weekId}</td>
                                    <td>${eleint.entryDate}</td>
                                    <td>${eleint.billType}</td>
                                    <td>${eleint.billingDate}</td>
                                    <td>${eleint.revisionNo}</td>
                                    <td>${eleint.billingDuedate}</td>
                                    <td>${eleint.interestAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>${eleint.interestAmount}</td>
                                    <td>${eleint.interestAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <c:set var="payoutstanding" value="${payoutstanding + eleint.interestAmount}"/>
                                    <td>${payoutstanding}</td>
                                    <td>Interest Pending</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:forEach items="${mapintbnklst}" var="elemapint">
                            <fmt:formatDate value="${elemapint.interestDetails.interestPaiddate}" pattern="yyyy-MM-dd" var="mappedintdate" />
                            <c:if test="${groupdate == mappedintdate}">
                                <tr>
                                    <td>${elemapint.interestDetails.weekId}</td>
                                    <td>${elemapint.entryDate}</td>
                                    <td>${elemapint.interestDetails.billType}</td>
                                    <td>${elemapint.interestDetails.billingDate}</td>
                                    <td>${elemapint.interestDetails.revisionNo}</td>
                                    <td>${elemapint.interestDetails.billingDuedate}</td>
                                    <td>${elemapint.interestDetails.interestAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>${elemapint.mappedAmount+elemapint.pendingAmount}</td>
                                    <td>${elemapint.pendingAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>                                     
                                    <td>${elemapint.bankStatement.amountDate}</td>
                                    <td>${elemapint.bankStatement.paidAmount}</td>
                                    <td>${elemapint.mappedAmount}</td>
                                    <td>${elemapint.bankPendingAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <c:set var="payoutstanding" value="${payoutstanding - elemapint.mappedAmount}"/>
                                    <td>${payoutstanding}</td>
                                    <td>Interest Mapping</td>
                                </tr>
                            </c:if>
                        </c:forEach>

                        <c:forEach items="${tempdisintlist}" var="eleintdis">
                            <fmt:formatDate value="${eleintdis.entryDate}" pattern="yyyy-MM-dd" var="intdisgendate" />
                            <c:if test="${groupdate == intdisgendate}">
                                <tr>
                                    <td>${eleintdis.weekId}</td>
                                    <td>${eleintdis.entryDate}</td>
                                    <td>${eleintdis.billType}</td>
                                    <td>${eleintdis.billingDate}</td>
                                    <td>${eleintdis.revisionNo}</td>
                                    <td>${eleintdis.billingDuedate}</td>
                                    <td>&nbsp;</td>
                                    <td>${eleintdis.interestAmount}</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>${eleintdis.interestAmount}</td>
                                    <td>${eleintdis.interestAmount}</td>

                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <c:set var="payoutstanding" value="${payoutstanding - eleintdis.interestAmount}"/>
                                    <td>${payoutstanding}</td>
                                    <td>Disbursed Interest Pending</td>
                                </tr>
                            </c:if>
                        </c:forEach>

                        <c:forEach items="${bankdislist}" var="bank1">
                            <fmt:formatDate value="${bank1.amountDate}" pattern="yyyy-MM-dd" var="disburseddate1" />
                            <c:if test="${disburseddate1 == groupdate}">
                                <c:forEach items="${disintdetlis}" var="paydisint">

                                    <c:if test="${paydisint.slno == bank1.disburseId && bank1.disburseType=='Interest'}">
                                        <tr>
                                            <c:set var="checkerepeated" scope="application" value="${0}"/>
                                            <td>${paydisint.disbursedInterestDetails.weekId}</td>
                                            <td>${bank1.amountDate}</td>
                                            <td>${paydisint.disbursedInterestDetails.billType}</td>
                                            <td>${paydisint.disbursedInterestDetails.billingDate}</td>
                                            <td>${paydisint.disbursedInterestDetails.revisionNo}</td>
                                            <td>${paydisint.disbursedInterestDetails.billingDuedate}</td>
                                            <td>&nbsp;</td>
                                            <td>${paydisint.disbursedInterestDetails.interestAmount}</td>


                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>${paydisint.disbursedInterestDetails.interestAmount}</td>
                                            <td>${paydisint.interestPendingamount}</td>




                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <td>${bank1.amountDate}</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>${bank1.paidAmount}</td>
                                            <td>${paydisint.interestPaidamount}</td>
                                            <!--<td style="width: 70px;">&nbsp;</td>-->


                                            <c:set var="payoutstanding" value="${payoutstanding + paydisint.interestPaidamount}"/>
                                            <td>${payoutstanding}</td>
                                            <td>Disbursed Interest</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </c:forEach>

                    </c:forEach>
                </tbody>

            </table>


        </form>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <br/>
    </body>

    <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>
</html>
