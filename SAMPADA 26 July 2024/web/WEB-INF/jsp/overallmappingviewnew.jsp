<%-- 
    Document   : overallmappingviewnew
    Created on : 18 Feb, 2020, 1:59:58 PM
    Author     : abt
--%>


<%@page import="java.util.Date"%>
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
                var type = $("#type").val();
                var billyear = $("#finclyear").val();
                var sumcramt = $("#sumcramt").val();
                var sumintamt = $("#sumintamt").val();
                var poolbef = $("#poolbef").val();
                var poolbefsras = $("#poolbefsras").val();
                var poolbeftras = $("#poolbeftras").val();

                var openpoolbal1 = $("#openpoolbal1").val();
                var poolafter = $("#poolafter").val();
                var poolaftersras = $("#poolaftersras").val();
                var poolaftertras = $("#poolaftertras").val();

                var intbef = $("#intbef").val();
                var intbefsras = $("#intbefsras").val();
                var intbeftras = $("#intbeftras").val();

                var intafter = $('#intafter').val();
                var intaftersras = $('#intaftersras').val();
                var intaftertras = $('#intaftertras').val();

                var totalpool = $('#totalpool').val();
                var nowtotalpool = $('#nowtotalpool').val();


                var table = $('#payableTable').DataTable({
                    responsive: true,
                    paging: true,
                    "pageLength": 25,
                    "lengthMenu": [[-1, 25, 50, 100], ["All", 25, 50, 100]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            orientation: 'landscape',
                            title: 'Market Operation Department',
//                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n\
                                            \r\n\
                                            \r\n\n\
                                            \r\n.                                                                                                                                      PART-1 \n\
                                            \r\n \r\n Ref : WRLDC/MO/' + type + '/' + billyear + '/Disbursal-                                                                                         Date : ' + billDate +
                                        '\r\n \n\
                                            \r\n Sub : Payment from Deviation Pool Account\n\
                                            \r\n We have received the following amounts (Rs. in Rupees) w.r.t DSM/RRAS/AGC CERC Regulation: \n\
                                            \r\n Opening Balance in DSM Pool a/c is =Rs.' + poolbef +
                                        '\r\n Opening Balance in DSM Pool a/c towards Interest is =Rs.' + intbef +
                                        '\r\n Opening Balance in SRAS Pool a/c is =Rs.' + poolbefsras +
                                        '\r\n Opening Balance in SRAS Pool a/c towards  Interest is =Rs.' + intbefsras +
                                        '\r\n Opening Balance in TRAS Pool a/c is =Rs.' + poolbeftras +
                                        '\r\n Opening Balance in TRAS Pool a/c towards  Interest is =Rs.' + intbeftras +
                                        '\r\n Total Opening Balance in  Pool including Interest is =Rs.' + totalpool;
                            },
                            messageBottom: function () {
                                return '\r\n    \n\
                                                \r\n  Total CR Amount in Pool is =' + sumcramt + '    \n\
                                                \r\n  Now TOTAL in Pool is =' + nowtotalpool + '   \n\
                                                \r\n  Amount Available for DSM Interest Disbursal is =' + intafter + '\n\
                                                \r\n  Amount Available for DSM Bill Disbursal is =' + poolafter + '\n\
                                                \r\n  Amount Available for SRAS Interest Disbursal is =' + intaftersras + '\n\
                                                \r\n  Amount Available for SRAS Bill Disbursal is =' + poolaftersras + '\n\
                                                \r\n  Amount Available for TRAS Interest Disbursal is =' + intaftertras + '\n\
                                                \r\n  Amount Available for TRAS Bill Disbursal is =' + poolaftertras + '\n\
                                                \r\n\r\n.                                                                                                                                                                                                                    CONTINUING PART-2';
                            }

                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            orientation: 'landscape',
                            title: 'Market Operation Department',
//                            orientation: 'landscape',
                            messageTop: function () {
                                return  '\r\n\
                                            \r\n\
                                            \r\n\n\
                                            \r\n.                                                                                                                                      PART-1 \n\
                                            \r\n \r\n Ref : WRLDC/MO/' + type + '/' + billyear + '/Disbursal-                                                                                         Date : ' + billDate +
                                        '\r\n \n\
                                            \r\n Sub : Payment from Deviation Pool Account\n\
                                            \r\n We have received the following amounts (Rs. in Rupees) w.r.t DSM/RRAS/AGC CERC Regulation: \n\
                                            \r\n Opening Balance in DSM Pool a/c is =Rs.' + poolbef +
                                        '\r\n Opening Balance in DSM Pool a/c towards Interest is =Rs.' + intbef +
                                        '\r\n Opening Balance in SRAS Pool a/c is =Rs.' + poolbefsras +
                                        '\r\n Opening Balance in SRAS Pool a/c towards  Interest is =Rs.' + intbefsras +
                                        '\r\n Opening Balance in TRAS Pool a/c is =Rs.' + poolbeftras +
                                        '\r\n Opening Balance in TRAS Pool a/c towards  Interest is =Rs.' + intbeftras +
                                        '\r\n Total Opening Balance in  Pool including Interest is =Rs.' + totalpool;
                            },
                            messageBottom: function () {
                                return '\r\n    \n\
                                                \r\n  Total CR Amount in Pool is =' + sumcramt + '    \n\
                                                \r\n  Now TOTAL in Pool is =' + nowtotalpool + '   \n\
                                                \r\n  Amount Available for DSM Interest Disbursal is =' + intafter + '\n\
                                                \r\n  Amount Available for DSM Bill Disbursal is =' + poolafter + '\n\
                                                \r\n  Amount Available for SRAS Interest Disbursal is =' + intaftersras + '\n\
                                                \r\n  Amount Available for SRAS Bill Disbursal is =' + poolaftersras + '\n\
                                                \r\n  Amount Available for TRAS Interest Disbursal is =' + intaftertras + '\n\
                                                \r\n  Amount Available for TRAS Bill Disbursal is =' + poolaftertras + '\n\
                                                \r\n\r\n.                                                                                                                                                                                                                    CONTINUING PART-2';
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
                    }
                });
                table.columns().iterator('column', function (ctx, idx) {
                    $(table.column(idx).header()).append('<span class="sort-icon"/>');
                });
                $('#payableTable tbody')
                        .on('mouseenter', 'td', function () {
                            var colIdx = table.cell(this).index().column;
                            $(table.cells().nodes()).removeClass('highlight');
                            $(table.column(colIdx).nodes()).addClass('highlight');
                        });
            });
        </script>


    </head>
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" 
        <!--<h3 align="center" style="color:#003366;">From Week-id ${week_id1} to Week-id ${week_id2} of year ${yeari}</h3>-->
          <!--<table id="payableTable" align="center" style="min-height:400px;width:80%;" class="table table-striped table-bordered">-->


          <%
              Date date1 = new Date();
          %>
          <fmt:formatDate value="<%=date1%>" pattern="dd-MM-yyyy"  var="date1" />
          <input type="text" name="billDate" id="billDate" value="${date1}" hidden/>
        <input type="text" name="finclyear" id="finclyear" value="${finclyear}" hidden/>
        <input type="text" name="type" id="type" value="DSM" hidden/>
        <input type="text" name="sumcramt" id="sumcramt" value="${sumcramt}" hidden/>
        <input type="text" name="poolbef" id="poolbef" value="${poolbef}" hidden/>
        <input type="text" name="poolbefsras" id="poolbefsras" value="${poolbefsras}" hidden/>
        <input type="text" name="poolbeftras" id="poolbeftras" value="${poolbeftras}" hidden/>


        <input type="text" name="sumintamt" id="sumintamt" value="${sumintamt}" hidden/>
        <input type="text" name="openpoolbal1" id="openpoolbal1" value="${openpoolbal+sumcramt}" hidden/>
        <input type="text" name="poolafter" id="poolafter" value="${poolafter}" hidden/>
        <input type="text" name="poolaftersras" id="poolaftersras" value="${poolaftersras}" hidden/>
        <input type="text" name="poolaftertras" id="poolaftertras" value="${poolaftertras}" hidden/>


        <input type="text" name="intbef" id="intbef" value="${intbef}" hidden />
        <input type="text" name="intbefsras" id="intbefsras" value="${intbefsras}" hidden />
        <input type="text" name="intbeftras" id="intbeftras" value="${intbeftras}" hidden />

        <input type="text" name="intafter" id="intafter" value="${intafter}" hidden/>
        <input type="text" name="intaftersras" id="intaftersras" value="${intaftersras}" hidden/>
        <input type="text" name="intaftertras" id="intaftertras" value="${intaftertras}" hidden/>

        <input type="text" name="nowtotalpool" id="nowtotalpool" value="${poolafter+poolaftersras+poolaftertras + intafter+intaftersras+intaftertras}" hidden/>
        <input type="text" name="totalpool" id="totalpool" value="${poolbef+poolbefsras+poolbeftras + intbef+intbefsras+intbeftras}" hidden/>

        <form>
            <fieldset>
                <legend> <h3>Mapping Details</h3></legend>
                <h3 align="center" style="color:#003366;">Open Balance in DSM Pool a/c is   ${poolbef}</h3>
                <h3 align="center" style="color:#003366;">Open Balance in DSM Interest Pool a/c is   ${intbef}</h3>

                <h3 align="center" style="color:#003366;">Open Balance in SRAS Pool a/c is   ${poolbefsras}</h3>
                <h3 align="center" style="color:#003366;">Open Balance in SRAS Interest Pool a/c is   ${intbefsras}</h3>

                <h3 align="center" style="color:#003366;">Open Balance in TRAS Pool a/c is   ${poolbeftras}</h3>
                <h3 align="center" style="color:#003366;">Open Balance in TRAS Interest Pool a/c is   ${intbeftras}</h3>

                <h3 align="center" style="color:#003366;">Total Opening Balance in  Pool including Interest is ${poolbef+poolbefsras+poolbeftras + intbef+intbefsras+intbeftras}</h3>


                <h3 align="center" style="color:#003366;">Total CR Amount in Pool is  ${sumcramt}</h3>
                <h3 align="center" style="color:#003366;">Now Total in  Pool is   ${poolafter+poolaftersras+poolaftertras + intafter+intaftersras+intaftertras}</h3>


                <h3 align="center" style="color:#003366;">Amount Available for DSM Interest Disbursal is  ${intafter}</h3>
                <h3 align="center" style="color:#003366;">Amount Available for DSM Bill Disbursal is  ${poolafter}</h3>
                <h3 align="center" style="color:#003366;">Amount Available for SRAS Interest Disbursal is  ${intaftersras}</h3>
                <h3 align="center" style="color:#003366;">Amount Available for SRAS Bill Disbursal is  ${poolaftersras}</h3>
                <h3 align="center" style="color:#003366;">Amount Available for TRAS Interest Disbursal is  ${intaftertras}</h3>
                <h3 align="center" style="color:#003366;">Amount Available for TRAS Bill Disbursal is  ${poolaftertras}</h3>


                <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Bank Id</th>
                            <th>Bank CR Date</th>
                            <th>Amount CR in Bank</th>
                            <th>Mapped</th>
                            <th>Available For Mapping</th>
                            <th>Pool Member Name</th>
                            <th>Bill Year</th>
                            <th>WEEK_ID </th>
                            <th>Bill type</th>

                            <th>Rev_No</th>
                            <!--<th>Total Net</th>-->

                            <!--<th>Pending</th>-->
                            <th>Mapping Remarks</th>
                            <!--<th>Bank Id</th>-->
                            <!--                            <th>Id From Date</th>
                                                        <th>Id To Date</th>-->

                            <!--                            <th>Desc</th>
                                                        <th>Bank Remarks</th>
                                                        <th>Category</th>-->
                            <th>Category</th>
                            <th>Bank Account Name</th>

                        </tr>

                    </thead>
                    <tbody>


                        <c:forEach items="${mapbillist}" var="bill">
                            <tr>
                                <td>${bill.bankStatement.stmtId}</td>
                                <fmt:formatDate value="${bill.bankStatement.amountDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td>${bill.bankStatement.paidAmount}</td>
                                <td>${bill.mappedAmount}</td>
                                <td>${bill.pendingBankAmount}</td>
                                <td>${bill.corporates.sname}</td>
                                <td>${finclyear}</td>
                                <!--<td>${bill.billPayableCorp.billYear}</td>-->
                                <td>${bill.weekId}</td>
                                <td>${bill.billType}</td>

                                <td>${bill.billPayableCorp.revisionNo}</td>
                                <!--<td>${bill.billPayableCorp.totalnet}</td>-->

<!--<td>${bill.pendingAmount}</td>-->
                                <td>${bill.remarks}</td>
                                <!--<td>${bill.bankStatement.stmtId}</td>-->
<!--                                <td>${bill.bankStatement.stmtFromdate}</td>
                                <td>${bill.bankStatement.stmtTodate}</td>-->

<!--                                <td>${bill.bankStatement.entryDesc}</td>
<td>${bill.bankStatement.remarks}</td>
<td>Bill Mapping</td>-->
                                <td>Bill Mapping</td>
                                <td>${bill.corporates.bankAccName}</td>

                            </tr>
                        </c:forEach>


                        <c:forEach items="${mapbillistnew}" var="bill">
                            <tr>
                                <td>${bill.bankStatement.stmtId}</td>
                                <fmt:formatDate value="${bill.bankStatement.amountDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td>${bill.bankStatement.paidAmount}</td>
                                <td>${bill.mappedAmount}</td>
                                <td>${bill.pendingBankAmount}</td>
                                <td>${bill.corporates.sname}</td>
                                <td>${finclyear}</td>
                                <!--<td>${bill.billPayableCorp.billYear}</td>-->
                                <td>${bill.weekId}</td>
                                <td>${bill.billType}</td>

                                <td>${bill.billPayableCorp.revisionNo}</td>
                                <!--<td>${bill.billPayableCorp.totalnet}</td>-->

<!--<td>${bill.pendingAmount}</td>-->
                                <td>${bill.remarks}</td>
                                <!--<td>${bill.bankStatement.stmtId}</td>-->
<!--                                <td>${bill.bankStatement.stmtFromdate}</td>
                                <td>${bill.bankStatement.stmtTodate}</td>-->

<!--                                <td>${bill.bankStatement.entryDesc}</td>
<td>${bill.bankStatement.remarks}</td>
<td>Bill Mapping</td>-->
                                <td>Bill Mapping</td>
                                <td>${bill.corporates.bankAccName}</td>

                            </tr>
                        </c:forEach>

                        <c:forEach items="${mapreflist}" var="ref">
                            <tr>
                                <td>${ref.bankStatement.stmtId}</td>
                                <fmt:formatDate value="${ref.bankStatement.amountDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td>${ref.bankStatement.paidAmount}</td>
                                <td>${ref.mappedAmount}</td>
                                <td>${ref.bankPendingAmount}</td>
                                <td>${ref.tempRefundBillCorp.corporates.sname}</td>
                                <td>${finclyear}</td>
                                <!--<td>${ref.tempRefundBillCorp.billReceiveCorp.billYear}</td>-->
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.weekId}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.billType}</td>

                                <td>${ref.tempRefundBillCorp.billReceiveCorp.revisionNo}</td>
                                <!--<td>${ref.tempRefundBillCorp.billReceiveCorp.toalnet}</td>-->

<!--<td>${ref.pendingAmount}</td>-->
                                <td>${ref.remarks}</td>
                                <!--<td>${ref.bankStatement.stmtId}</td>-->
<!--                                <td>${ref.bankStatement.stmtFromdate}</td>
                                <td>${ref.bankStatement.stmtTodate}</td>-->

<!--                                <td>${ref.bankStatement.entryDesc}</td>
<td>${ref.bankStatement.remarks}</td>
<td>Refund Mapping</td>-->
                                <td>Refund Mapping</td>
                                <td>${ref.tempRefundBillCorp.corporates.bankAccName}</td>
                            </tr>
                        </c:forEach>


                        <c:forEach items="${mapreflistnew}" var="ref">
                            <tr>
                                <td>${ref.bankStatement.stmtId}</td>
                                <fmt:formatDate value="${ref.bankStatement.amountDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td>${ref.bankStatement.paidAmount}</td>
                                <td>${ref.mappedAmount}</td>
                                <td>${ref.bankPendingAmount}</td>
                                <td>${ref.tempRefundBillCorp.corporates.sname}</td>
                                <td>${finclyear}</td>
                                <!--<td>${ref.tempRefundBillCorp.billReceiveCorp.billYear}</td>-->
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.weekId}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.billType}</td>

                                <td>${ref.tempRefundBillCorp.billReceiveCorp.revisionNo}</td>
                                <!--<td>${ref.tempRefundBillCorp.billReceiveCorp.toalnet}</td>-->

<!--<td>${ref.pendingAmount}</td>-->
                                <td>${ref.remarks}</td>
                                <!--<td>${ref.bankStatement.stmtId}</td>-->
<!--                                <td>${ref.bankStatement.stmtFromdate}</td>
                                <td>${ref.bankStatement.stmtTodate}</td>-->

<!--                                <td>${ref.bankStatement.entryDesc}</td>
<td>${ref.bankStatement.remarks}</td>
<td>Refund Mapping</td>-->
                                <td>Refund Mapping</td>
                                <td>${ref.tempRefundBillCorp.corporates.bankAccName}</td>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${mapintlist}" var="int">
                            <tr>
                                <td>${int.bankStatement.stmtId}</td>
                                <fmt:formatDate value="${int.bankStatement.amountDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td>${int.bankStatement.paidAmount}</td>
                                <td>${int.mappedAmount}</td>
                                <td>${int.bankPendingAmount}</td>
                                <td>${int.interestDetails.corporates.sname}</td>
                                <td>${finclyear}</td>
                                <!--<td>${int.interestDetails.billYear}</td>-->
                                <td>${int.interestDetails.weekId}</td>
                                <td>${int.interestDetails.billType}</td>

                                <td>${int.interestDetails.revisionNo}</td>
                                <!--<td>${int.interestDetails.interestAmount}</td>-->

<!--<td>${int.pendingAmount}</td>-->
                                <td>${int.remarks}</td>
                                <!--<td>${int.bankStatement.stmtId}</td>-->
<!--                                <td>${int.bankStatement.stmtFromdate}</td>
                                <td>${int.bankStatement.stmtTodate}</td>-->

<!--                                <td>${int.bankStatement.entryDesc}</td>
<td>${int.bankStatement.remarks}</td>
<td>Interest Mapping</td>-->
                                <td>Interest Mapping</td>
                                <td>${int.interestDetails.corporates.bankAccName}</td>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${pooltoint}" var="ptoint">
                            <tr>
                                <td>${ptoint.uniqueId}</td>
                                <fmt:formatDate value="${ptoint.entryDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td></td>
                                <td>${ptoint.refundAmt}</td>
                                <td></td>
                                <td>${"WRLDC INTERNAL TRANSFER"}</td>
                                <td>${finclyear}</td>
                                <!--<td>${int.interestDetails.billYear}</td>-->
                                <td></td>
                                <c:choose>
                                    <c:when test="${ptoint.refundAmt >= 0}">
                                        <td>${"POOL TO INT"}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${"INT TO POOL"}</td>
                                    </c:otherwise>
                                </c:choose>


                                <td></td>
                                <!--<td>${int.interestDetails.interestAmount}</td>-->

<!--<td>${int.pendingAmount}</td>-->
                                <td>${ptoint.remarks}</td>
                                <!--<td>${int.bankStatement.stmtId}</td>-->
<!--                                <td>${int.bankStatement.stmtFromdate}</td>
                                <td>${int.bankStatement.stmtTodate}</td>-->

<!--                                <td>${int.bankStatement.entryDesc}</td>
<td>${int.bankStatement.remarks}</td>
<td>Interest Mapping</td>-->

                                <c:choose>
                                    <c:when test="${ptoint.refundAmt >= 0}">
                                        <td>POOL TO INTEREST</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>INTEREST TO POOL</td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${"WRLDC INTERNAL TRANSFER"}</td>
                            </tr>
                        </c:forEach>


                        <c:forEach items="${pooltopool}" var="ptoint">
                            <tr>
                                <td>${ptoint.uniqueId}</td>
                                <fmt:formatDate value="${ptoint.entryDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td></td>
                                <td>${ptoint.refundAmt}</td>
                                <td></td>
                                <td>${"WRLDC INTERNAL TRANSFER"}</td>
                                <td>${finclyear}</td>
                                <td></td>

                                <c:choose>
                                    <c:when test="${ptoint.transId == '1'}">
                                        <c:choose>

                                            <c:when test="${ptoint.refundAmt >= 0}">
                                                <td>${"DSM TO SRAS"}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${"SRAS TO DSM"}</td>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:when>
                                    <c:when test="${ptoint.transId == '2'}">
                                        <c:choose>

                                            <c:when test="${ptoint.refundAmt >= 0}">
                                                <td>${"DSM TO TRAS"}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${"TRAS TO DSM"}</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>

                                            <c:when test="${ptoint.refundAmt >= 0}">
                                                <td>${"SRAS TO TRAS"}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${"TRAS TO SRAS"}</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>



                                <td></td>

                                <td>${ptoint.remarks}</td>


                                <c:choose>
                                    <c:when test="${ptoint.transId == '1'}">
                                        <c:choose>

                                            <c:when test="${ptoint.refundAmt >= 0}">
                                                <td>${"DSM TO SRAS"}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${"SRAS TO DSM"}</td>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:when>
                                    <c:when test="${ptoint.transId == '2'}">
                                        <c:choose>

                                            <c:when test="${ptoint.refundAmt >= 0}">
                                                <td>${"DSM TO TRAS"}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${"TRAS TO DSM"}</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>

                                            <c:when test="${ptoint.refundAmt >= 0}">
                                                <td>${"SRAS TO TRAS"}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${"TRAS TO SRAS"}</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>

                                <td>${"WRLDC INTERNAL TRANSFER"}</td>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${bnkstmt}" var="bnkstmt">
                            <tr>
                                <td>${bnkstmt.stmtId}</td>
                                <fmt:formatDate value="${bnkstmt.amountDate}" pattern="dd-MM-yyyy" var="billingDate" />
                                <td>${billingDate}</td>

                                <td>${bnkstmt.paidAmount}</td>
                                <td>${bnkstmt.mappedAmount}</td>
                                <td>${bnkstmt.mappedBalance}</td>
                                <td>${bnkstmt.corporates.sname}</td>
                                <td></td>
                                <td></td>
                                <td></td>

                                <td></td>
                                <td></td>
                                <!--<td>${int.interestDetails.interestAmount}</td>-->

<!--<td>${int.pendingAmount}</td>-->
                                <td></td>
                                <!--<td>${int.bankStatement.stmtId}</td>-->
<!--                                <td>${int.bankStatement.stmtFromdate}</td>
                                <td>${int.bankStatement.stmtTodate}</td>-->

<!--                                <td>${int.bankStatement.entryDesc}</td>
<td>${int.bankStatement.remarks}</td>
<td>Interest Mapping</td>-->
                                <td>${bnkstmt.corporates.bankAccName}</td>
                            </tr>
                        </c:forEach>

                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th style="text-align:right">Total:</th>
                            <!--<th style="text-align:right">Total:</th>-->
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>


                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <!--                            <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th style="text-align:right">Total:</th>
                                                        <th style="text-align:right">Total:</th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>-->


                        </tr>
                    </tfoot>
                </table>


            </fieldset>
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
    </body>
</html>


