<%-- 
    Document   : disbursenotesheet
    Created on : 26 Feb, 2020, 11:37:12 AM
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
                var poolbef = $("#poolbef").val();
                var poolaft = $("#poolaft").val();
                var poolbefsras = $("#poolbefsras").val();
                var poolaftersras = $("#poolaftersras").val();
                var poolbeftras = $("#poolbeftras").val();
                var poolaftertras = $("#poolaftertras").val();
                var intpool = $("#intpoolaft").val();
                var intpoolbef = $("#intpoolbef").val();
                var intaftersras = $("#intaftersras").val();
                var intbefsras = $("#intbefsras").val();
                var intaftertras = $("#intaftertras").val();
                var intbeftras = $("#intbeftras").val();
                var Billtypebil = $("#Billtypebil").val();
                var totalpool = $("#totalpool").val();
                var billyear = $("#finclyear").val();
                var type = $("#type").val();
                var totalbal = $("#totalbal").val();

                var table = $('#payableTable').DataTable({
                    responsive: true,
                    paging: true,
                    "pageLength": 25,
                    "lengthMenu": [[-1, 25, 50, 100], ["All", 25, 50, 100]],
                    "order": [[13, "desc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            orientation: 'landscape',
                            pageSize: 'LEGAL',
                            title: 'Market Operation Department',
                            messageTop: function () {
                                return     '\r\n \n\
                                            \r\n\n\
                                            \r\n\n\
                                            \r\n.                                                                                                       PART-2\n\
                                            \r\n \r\nRef : WRLDC/MO/' + type + '/' + billyear + '/Disbursal-                                                                                         Date : ' + billDate +
                                        '\r\n \r\nTotal Amount Available in Regulatory Pool account =Rs. ' + totalbal +
                                        '\r\n \r\nAmount Available for DSM Bill Disbursal is =' + poolbef + ' \n\
                                            \r\n \r\nAmount Available for DSM Interest Disbursal is =' + intpoolbef + '   \n\
                                             \r\n \r\nAmount Available for SRAS Bill Disbursal is =' + poolbefsras + '   \n\
                                            \r\n \r\nAmount Available for SRAS Interest Disbursal is =' + intbefsras + '   \n\
                                             \r\n \r\nAmount Available for TRAS Bill Disbursal is =' + poolbeftras + '   \n\
                                             \r\n \r\nAmount Available for TRAS Interest Disbursal is =' + intbeftras + '   \n\
                                            \r\n The amount will be disbursed to the following entities  towards ' + Billtypebil + ' Type. Kindly arrange to issue cheques as per the details given below:-';
                            },
                            messageBottom: function () {
                                return '\r\n \n\
                                         \r\n Balance available in DSM  is' + poolaft + ' \n\
                                            \r\n Net Balance in DSM  a/c towards DSM Interest is ' + intpool + '  \n\
                                            \r\n Balance available in SRAS  is ' + poolaftersras + ' \n\
                                             \r\n Net Balance in SRAS  a/c towards SRAS Interest is ' + intaftersras + '  \n\
                                            \r\n Balance available in TRAS  is ' + poolaftertras + ' \n\
                                             \r\n Net Balance in TRAS  a/c towards TRAS Interest is ' + intaftertras + '  \n\
                                            \r\n Total Balance available in  Pool including interest is  ' + totalpool + ' \n\
                                            \r\n Competent authority may kindly approve the above.\n\
                                            \r\n \r\n MAKER    :________________________                                                                                                                                                     CHECKER :________________________\n\
                                            \r\n \r\n REVIEWED :________________________                                                                                                                                                    APPROVED :________________________\n\
                                            \r\n \r\nFINANCE DEPT :________________________';
                            }

                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            orientation: 'landscape',
                            pageSize: 'LEGAL',
                            title: 'Market Operation Department',
                            messageTop: function () {
                                return      '\r\n \n\
                                            \r\n\n\
                                            \r\n\n\
                                            \r\n.                                                                                                       PART-2\n\
                                            \r\n \r\nRef : WRLDC/MO/' + type + '/' + billyear + '/Disbursal-                                                                                         Date : ' + billDate +
                                        '\r\n \r\nTotal Amount Available in Regulatory Pool account =Rs. ' + totalbal +
                                        '\r\n \r\nAmount Available for DSM Bill Disbursal is =' + poolbef + ' \n\
                                            \r\n \r\nAmount Available for DSM Interest Disbursal is =' + intpoolbef + '   \n\
                                             \r\n \r\nAmount Available for SRAS Bill Disbursal is =' + poolbefsras + '   \n\
                                            \r\n \r\nAmount Available for SRAS Interest Disbursal is =' + intbefsras + '   \n\
                                             \r\n \r\nAmount Available for TRAS Bill Disbursal is =' + poolbeftras + '   \n\
                                             \r\n \r\nAmount Available for TRAS Interest Disbursal is =' + intbeftras + '   \n\
                                            \r\n The amount will be disbursed to the following entities  towards ' + Billtypebil + ' Type. Kindly arrange to issue cheques as per the details given below:-';
                            },
                            messageBottom: function () {
                                return'\r\n \n\
                                         \r\n Balance available in DSM  is' + poolaft + ' \n\
                                            \r\n Net Balance in DSM  a/c towards DSM Interest is ' + intpool + '  \n\
                                            \r\n Balance available in SRAS  is ' + poolaftersras + ' \n\
                                             \r\n Net Balance in SRAS  a/c towards SRAS Interest is ' + intaftersras + '  \n\
                                            \r\n Balance available in TRAS  is ' + poolaftertras + ' \n\
                                             \r\n Net Balance in TRAS  a/c towards TRAS Interest is ' + intaftertras + '  \n\
                                            \r\n Total Balance available in  Pool including interest is  ' + totalpool + ' \n\
                                            \r\n Competent authority may kindly approve the above.\n\
                                            \r\n \r\n MAKER    :________________________                                                                                                                                                     CHECKER :________________________\n\
                                            \r\n \r\n REVIEWED :________________________                                                                                                                                                    APPROVED :________________________\n\
                                            \r\n \r\nFINANCE DEPT :________________________';
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
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" >
        <!--<h3 align="center" style="color:#003366;">From Week-id ${week_id1} to Week-id ${week_id2} of year ${yeari}</h3>-->
        <!--<table id="payableTable" align="center" style="min-height:400px;width:80%;" class="table table-striped table-bordered">-->

        <%
            Date date1 = new Date();
//              String month, datee;
//              Date date1 = new Date();
//              if(date1.getMonth()<9)
//                  month = "0"+String.valueOf(date1.getMonth()+1);
//              else
//                  month = String.valueOf(date1.getMonth()+1);
//              
//              if(date1.getDate()<10)
//                datee = "0"+date1.getDate();
//              else
//                datee = String.valueOf(date1.getDate());
//              
//              String currdate = datee+"-"+month+"-"+String.valueOf(date1.getYear()+1900);
%>

        <fmt:formatDate value="<%=date1%>" pattern="dd-MM-yyyy"  var="date1" />
        <input type="text" name="billDate" id="billDate" value=${date1} hidden/>
        <input type="text" name="poolbef" id="poolbef" value="${poolbef}" hidden/>
        <input type="text" name="poolaft" id="poolaft" value="${poolaft}" hidden/>
        <input type="text" name="poolbefsras" id="poolbefsras" value="${poolbefsras}" hidden/>
        <input type="text" name="poolaftersras" id="poolaftersras" value="${poolaftersras}" hidden/>
        <input type="text" name="poolbeftras" id="poolbeftras" value="${poolbeftras}" hidden/>
        <input type="text" name="poolaftertras" id="poolaftertras" value="${poolaftertras}" hidden/>
        <input type="text" name="intpoolaft" id="intpoolaft" value="${intpoolaft}" hidden/>
        <input type="text" name="intpoolbef" id="intpoolbef" value="${intpoolbef}" hidden/>
        <input type="text" name="intaftersras" id="intaftersras" value="${intaftersras}" hidden/>
        <input type="text" name="intbefsras" id="intbefsras" value="${intbefsras}" hidden/>
         <input type="text" name="intaftertras" id="intaftertras" value="${intaftertras}" hidden/>
        <input type="text" name="intbeftras" id="intbeftras" value="${intbeftras}" hidden/>
        <input type="text" name="Billtypebil" id="Billtypebil" value="${Billtypebil}" hidden/>
        <input type="text" name="totalpool" id="totalpool" value="${poolaft+intpoolaft+poolaftersras+poolaftertras+intaftersras+intaftertras}" hidden/>
        <input type="text" name="finclyear" id="finclyear" value="${finclyear}" hidden/>
        <input type="text" name="type" id="type" value="DSM" hidden/>
        <input type="text" name="totalbal" id="totalbal" value="${poolbef+poolbefsras+poolbeftras+intpoolbef+intbefsras+intbeftras}" hidden/>

        <form>
            <fieldset>
                <legend> <h3>Disbursed Details</h3></legend>


                <h3 align="center" style="color:#003366;">Amount Available for Before DSM Disbursal is   ${poolbef}</h3>
                <h3 align="center" style="color:#003366;">Balance Available for After DSM  Pool is  ${poolaft}</h3>

                <h3 align="center" style="color:#003366;">Amount Available for Before SRAS Disbursal is   ${poolbefsras}</h3>
                <h3 align="center" style="color:#003366;">Balance Available for After SRAS  Pool is  ${poolaftersras}</h3>

                <h3 align="center" style="color:#003366;">Amount Available for Before TRAS Disbursal is   ${poolbeftras}</h3>
                <h3 align="center" style="color:#003366;">Balance Available for After TRAS  Pool is  ${poolaftertras}</h3>

                <h3 align="center" style="color:#003366;">Amount Available for  Before DSM  Interest is  ${intpoolbef}</h3>
                <h3 align="center" style="color:#003366;">Balance Available for  After DSM  Interest is  ${intpoolaft}</h3>

                <h3 align="center" style="color:#003366;">Amount Available for  Before SRAS  Interest is  ${intbefsras}</h3>
                <h3 align="center" style="color:#003366;">Balance Available for  After SRAS  Interest is  ${intaftersras}</h3>

                <h3 align="center" style="color:#003366;">Amount Available for  Before TRAS  Interest is  ${intbeftras}</h3>
                <h3 align="center" style="color:#003366;">Balance Available for  After TRAS  Interest is  ${intaftertras}</h3>



                <h3 align="center" style="color:#003366;">Total Balance Available for DSM Pool including Interest for is   ${poolaft+intpoolaft}</h3>
                <h3 align="center" style="color:#003366;">Bill Type is  ${Billtypebil}</h3>
                <h3 align="center" style="color:#003366;">Fin-Year is  ${finclyear}</h3>


                <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Disburse Id</th>
                            <th>Pool Member Name</th>
                            <th>Bill Year</th>
                            <th>Bill type</th>
                            <th>WEEK_ID </th>
                            <th>Rev_No</th>
                            <th>Bill Amount</th>
                            <th>Diff. Amount wrt Rev.</th>
                            <th>Already Released</th>
                            <th>Disbursed</th>
                            <th>Pending</th>
                            <th>Software Disburse date</th>
                            <th>Disburse Remarks</th>
                            <th>Pool Bal</th>
                            <th>Bank Account Name</th>


                        </tr>

                    </thead>
                    <tbody>


                        <c:forEach items="${disbillist}" var="bill">
                            <tr>

                                <td>${"B"}${bill.disburseId}</td>
                                <td>${bill.corporates.sname}</td>

                                <!--<td>${finclyear}</td>-->
                                <td>${bill.billReceiveCorp.billYear}</td>
                                <td>${bill.billType}</td>
                                <td>${bill.weekId}</td>
                                <td>${bill.billReceiveCorp.revisionNo}</td>
                                <td>${bill.billReceiveCorp.toalnet}</td>
                                <c:choose>
                                    <c:when test="${bill.billReceiveCorp.revisionNo == 0}">
                                        <td>${bill.billReceiveCorp.toalnet}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${bill.billReceiveCorp.revisedpaybale}</td>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${bill.billReceiveCorp.revisionNo == 0}">
                                        <td>${bill.billReceiveCorp.toalnet-(bill.disburseAmount+bill.pendingAmount)}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${bill.billReceiveCorp.revisedpaybale-(bill.disburseAmount+bill.pendingAmount)}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${bill.disburseAmount}</td>
                                <td>${bill.pendingAmount}</td>
                                <td>${bill.disbursalDate}</td>
                                <td>${bill.remarks}</td>
                                <c:choose>
                                    <c:when test="${bill.billType == 'SRAS'}">
                                        <td style="color: #00BFFF">${"SRAS-bal: "}${bill.poolAgcBal}</td>
                                    </c:when>
                                    <c:when test="${bill.billType == 'TRASM' or bill.billType == 'TRASS' or bill.billType == 'TRASE'}">
                                        <td style="color: #00BFFF">${"TRAS-bal: "}${bill.poolRrasBal}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${bill.poolBal}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${bill.corporates.bankAccName}</td>

                            </tr>
                        </c:forEach>

                        <c:forEach items="${disreflist}" var="ref">
                            <tr>
                                <td>${"R"}${ref.slno}</td>
                                <td>${ref.corporates.sname}</td>
                                <!--<td>${finclyear}</td>-->
                                <td>${ref.billPayableCorp.billYear}</td>
                                <td>${ref.billPayableCorp.billType}</td>
                                <td>${ref.billPayableCorp.weekId}</td>
                                <td>${ref.billPayableCorp.revisionNo}</td>
                                <td>${ref.billPayableCorp.totalnet}</td>
                                <td>${ref.billPayableCorp.revisedrefund}</td>
                                <td>${ref.billPayableCorp.revisedrefund-(ref.paidAmount+ref.pendingAmount)}</td>
                                <td>${ref.paidAmount}</td>
                                <td>${ref.pendingAmount}</td>
                                <td>${ref.refundDate}</td>
                                <td>${ref.remarks}</td>
                                <c:choose>
                                    <c:when test="${ref.billPayableCorp.billType == 'SRAS'}">
                                        <td style="color: #00BFFF">${"SRAS-bal: "}${ref.poolAgcBal}</td>
                                    </c:when>
                                    <c:when test="${ref.billPayableCorp.billType == 'TRASM' or ref.billPayableCorp.billType == 'TRASS' or ref.billPayableCorp.billType == 'TRASE'}">
                                        <td style="color: #00BFFF">${"TRAS-bal: "}${ref.poolRrasBal}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${ref.poolBal}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${ref.corporates.bankAccName}</td>


                            </tr>
                        </c:forEach>

                        <c:forEach items="${disintlist}" var="int">
                            <tr>

                                <td>${"I"}${int.slno}</td>
                                <td>${int.disbursedInterestDetails.corporates.sname}</td>
                                <!--<td>${finclyear}</td>-->
                                <td>${int.disbursedInterestDetails.billYear}</td>
                                <td>${int.disbursedInterestDetails.billType}</td>
                                <td>${int.disbursedInterestDetails.weekId}</td>
                                <td>${int.disbursedInterestDetails.revisionNo}</td>
                                <td>${int.disbursedInterestDetails.interestAmount}</td>
                                <td>${int.disbursedInterestDetails.interestAmount}</td>
                                <td>${int.disbursedInterestDetails.interestAmount-(int.interestPaidamount+int.interestPendingamount)}</td>
                                <td>${int.interestPaidamount}</td>
                                <td>${int.interestPendingamount}</td>
                                <td>${int.entryDate}</td>
                                <td>${int.remarks}</td>
                                <c:choose>
                                    <c:when test="${int.disbursedInterestDetails.billType == 'SRAS'}">
                                        <td style="color: #00BFFF">${"INT-SRAS-bal: "}${int.intAgcBal}</td>
                                    </c:when>
                                    <c:when test="${int.disbursedInterestDetails.billType == 'TRASM' or int.disbursedInterestDetails.billType == 'TRASS' or int.disbursedInterestDetails.billType == 'TRASE'}">
                                        <td style="color: #00BFFF">${"INT-TRAS-bal: "}${int.intRrasBal}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${int.intPoolBal}</td>
                                    </c:otherwise>
                                </c:choose> 
                                <td>${int.disbursedInterestDetails.corporates.bankAccName}</td>

                            </tr>
                        </c:forEach>


                        <c:forEach items="${psdfdetlist}" var="psdf">
                            <tr>

                                <td>${"P"}${psdf.slno}</td>
                                <td>${"PSDF"}</td>
                                <!--<td>${finclyear}</td>-->
                                <td>${psdf.csdfYear}</td>
                                <td>${psdf.amtCategory}</td>
                                <td>${psdf.csdfMonth}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>${psdf.csdfAmount}</td>
                                <td></td>
                                <td>${psdf.entryDate}</td>
                                <td>${psdf.remarks}</td>
                                <c:choose>
                                    <c:when test="${psdf.poolAgcBal != null}">

                                        <td style="color: #00BFFF">${"SRAS-bal: "}${psdf.poolAgcBal-psdf.csdfAmount}</td>
                                    </c:when>
                                    <c:when test="${psdf.poolRrasBal != null}">
                                        <td style="color: #00BFFF">${"TRAS-bal: "}${psdf.poolRrasBal-psdf.csdfAmount}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${psdf.mainPoolBalance-psdf.csdfAmount}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${"PSDF"}</td>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${misclist}" var="misc">
                            <tr>

                                <td>${"M"}${misc.uniqueId}</td>
                                <td>${misc.corpName}</td>
                                <td>${finclyear}</td>
                                <!--<td>${psdf.csdfYear}</td>-->
                                <td>${misc.amtCategory}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>${misc.refundAmt}</td>
                                <td></td>
                                <td>${misc.entryDate}</td>
                                <td>${misc.documentSet}${"--"}${misc.remarks}</td>
                                <td>${misc.mainPoolBalance-misc.refundAmt}</td>
                                <td>${misc.corpName}</td>
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
                            <th></th>
                            <th></th>


                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th style="text-align:right">Total:</th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>


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







