<%--

    Document   : billPayableDisplay

    Created on : Jun 18, 2019, 4:43:27 PM

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







        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">

        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">

        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>

        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>

        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>



        <style>

            table.roundedCorners {

                border: 1px solid #96CC39;

                border-radius: 13px;

                border-spacing: 0;

            }

            table.roundedCorners td,

            table.roundedCorners th {

                border: 1px solid #96CC39;

                padding: 5px;

            }

            thead,tr,td,input {

                color:#003366;

                background-color: white;

            }

            input{

                border: 0;

                font-size: 15px;

            }

            p {

                color:black;

            }

            th{

                height:72px;

            }

            input[type=submit] {

                padding: 10px 14px;

                border: 1px solid #f2f2f2;

                font-weight:bold;

                color:#ffffff;

                alignment-adjust:central;

                background-color:#96CC39;

            }

            input[type=submit]:hover, input[type=submit]:focus {

                background-color: #151b54;

            }







            table.dataTable thead span.sort-icon {

                display: inline-block;

                padding-left: 5px;

                width: 16px;

                height: 16px;

                color:wheat;

            }



            table.dataTable thead .sorting span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_both.png') no-repeat center right; }

            table.dataTable thead .sorting_asc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc.png') no-repeat center right; }

            table.dataTable thead .sorting_desc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc.png') no-repeat center right; }



            table.dataTable thead .sorting_asc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc_disabled.png') no-repeat center right; }

            table.dataTable thead .sorting_desc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc_disabled.png') no-repeat center right; }

            .dataTables_filter input { width: 300px }

        </style>

        <script>

            $(document).ready(function () {



                var table = $('#Payment').DataTable({
                    "ordering": true,
                    "bFilter": false,
                    "bPaginate": false,
                    "bInfo": false,
                    columnDefs: [{
                            orderable: false,
                            targets: "no-sort"

                        }]

                });



                table.columns().iterator('column', function (ctx, idx) {

                    $(table.column(idx).header()).append('<span class="sort-icon"/>');

                });



                $('#Payment tbody')

                        .on('mouseenter', 'td', function () {

                            var colIdx = table.cell(this).index().column;



                            $(table.cells().nodes()).removeClass('highlight');

                            $(table.column(colIdx).nodes()).addClass('highlight');

                        });



            });

        </script>



        <script type="text/javascript">

            var le = 0;

            var le1 = 0;

            var sumBillAmt = 0;

            var sumcreditAmt = parseFloat(0);

            var settleBnkAmt = parseFloat(0);

            var creditAmt = 0;

            var updatedCreditAmt = 0;

            $(document).ready(function () {

                $("input.case").click(function () {

                    if ($(this).prop("checked") == true) {

                        calculateCase();

                    }

                    else if ($(this).prop("checked") == false) {

                        alert("Checkbox is unchecked.");

                        calculateCase();

                    }

                });

                $("#resetID").click(function () {

                    $('.case').prop('checked', false);

                    $('.case1').prop('checked', false);

                    $('#Payment').find('tr').each(function () {

                        var row = $(this);

                        $(this).find("td:eq(5) input").val(0);

                        $(this).find("td:eq(6) input").val(0);

                    });

                    $('#Account').find('tr').each(function () {

                        var row = $(this);

                        $(this).find("td:eq(7) input").val(0);

                        $(this).find("td:eq(8) input").val(0);

                    });

                    return false;

                }); //end of reset

////////////////////////////////////BANK STATEMENT///////////////////////

                $("input.case1").click(function () {

                    if ($(this).prop("checked") == true) {

                        calculateCase1();

                    }

                    else if ($(this).prop("checked") == false) {

                        calculateCase1();

                        le1 = document.querySelectorAll('input[name="bankstmt"]:checked').length;

                        alert("Checkbox is unchecked.");

                        if (le1 < parseInt(1)) {

                            $('.case').prop('checked', false);

                            $('.case1').prop('checked', false);

                            alert("You haven't selected any entry from Bank Transaction !!");

                        }

                    }

                });



            });





            function calculateCase() {

                sumBillAmt = 0;

                updatedCreditAmt = parseFloat(creditAmt);

                $('#Account').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        var billAmt = $(this).find("td:eq(6)").html();

                        sumBillAmt = parseFloat(sumBillAmt) + parseFloat(billAmt);

                        le1 = document.querySelectorAll('input[name="bankstmt"]:checked').length;

                        if (le1 > parseInt(1)) {

                            alert("Kindly select only one entry from Bank Transaction !!");

                            $('.case').prop('checked', false);

                            $('.case1').prop('checked', false);

                        }

                        if (le1 < parseInt(1)) {

                            alert("Hello!! You haven't selected any entry from Bank Transaction !!");

                            $('.case').prop('checked', false);

                            $('.case1').prop('checked', false);

                        }

                        if ((le1 === parseInt(1)) && (sumBillAmt <= creditAmt)) {

                            updatedCreditAmt = updatedCreditAmt - billAmt;

                            $(this).find("td:eq(7) input").val(billAmt);

                            $(this).find("td:eq(8) input").val(0);

                        }

                        if ((le1 === parseInt(1)) && (sumBillAmt > creditAmt)) {

                            if (updatedCreditAmt >= billAmt) {

                                updatedCreditAmt = updatedCreditAmt - billAmt;

                                $(this).find("td:eq(7) input").val(billAmt);

                                $(this).find("td:eq(8) input").val(0);

                            }

                            else if ((updatedCreditAmt > 0) && (updatedCreditAmt < billAmt)) {

                                var pendingAmt = billAmt - updatedCreditAmt;
                                pendingAmt = parseFloat(pendingAmt);
                                $(this).find("td:eq(7) input").val(updatedCreditAmt.toFixed(2));

                                $(this).find("td:eq(8) input").val(pendingAmt.toFixed(2));

                                updatedCreditAmt = updatedCreditAmt - billAmt;

                            }

                            else {

                                $(this).find("td:eq(7) input").val(0);

                                $(this).find("td:eq(8) input").val(0);

                                alert("further settlement with this bank entry is not possible!!");

                            }

                        }

                    }

                    else

                    {

                        $(this).find("td:eq(7) input").val(0);

                        $(this).find("td:eq(8) input").val(0);

                    }

                });

                if (parseFloat(creditAmt) >= parseFloat(sumBillAmt)) {

                    var remainBal = parseFloat(creditAmt) - parseFloat(sumBillAmt);
                    remainBal = parseFloat(remainBal);
                     settleBnkAmt = parseFloat(sumBillAmt);

                    $('#Payment').find('tr').each(function () {

                        var row = $(this);

                        if (row.find('input[type="checkbox"]').is(':checked')) {

                            $(this).find("td:eq(5) input").val(settleBnkAmt.toFixed(2));

                            $(this).find("td:eq(6) input").val(remainBal.toFixed(2));

                        }

                    });

                }

                else {

                    alert("Full bill settlement is not feasible");

                    $('#Payment').find('tr').each(function () {

                        var row = $(this);

                        if (row.find('input[type="checkbox"]').is(':checked')) {

                            $(this).find("td:eq(5) input").val(creditAmt);

                            $(this).find("td:eq(6) input").val(0);

                        }

                    });

                }

            }///end of finctuion calculatecase

            function calculateCase1() {

                le1 = document.querySelectorAll('input[name="bankstmt"]:checked').length;

                if (le1 > parseInt(1)) {

                    alert('Cannot selected the second transaction');

                    $(this).attr("checked", false);

                    $('.case').prop('checked', false);

                    $('.case1').prop('checked', false);

                    $('#Payment').find('tr').each(function () {

                        var row = $(this);

                        $(this).find("td:eq(5) input").val(0);

                        $(this).find("td:eq(6) input").val(0);

                    });
                    $('#Account').find('tr').each(function () {

                        var row = $(this);



                        $(this).find("td:eq(7) input").val(0);

                        $(this).find("td:eq(8) input").val(0);



                    });

                    return false;

                }

                $('#Payment').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        var stmtId = parseInt($(this).find("td:eq(1)").html());

                        creditAmt = $(this).find("td:eq(4)").html();

//                        alert(creditAmt);

                    }

                    else

                    {

                        $(this).find("td:eq(5) input").val(0);

                        $(this).find("td:eq(6) input").val(0);

                    }

                });

                $('#Payment').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        var creditdate = $(this).find("td:eq(2)").html();
                        var dateflag = 0;
//                        alert(creditdate);
                        $('#Payment').find('tr').each(function () {

                            var creditdate1 = $(this).find("td:eq(2)").html();
                            if (creditdate1 < creditdate) {
                                dateflag = 1;
//                                alert("Bank Statements with Former Credit Dates are available");
                            }

                        });
                        if (dateflag == 1) {
                            alert("Bank Statements with Former Credit Dates are available");
                        }

                    }


                });

                $('#Account').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        $(this).find("td:eq(7) input").val(0);

                        $(this).find("td:eq(8) input").val(0);

                    }

                });

            }// end finction calcutecase1

            function validate() {

                le1 = document.querySelectorAll('input[name="bankstmt"]:checked').length;

                le = document.querySelectorAll('input[name="uniqueNo"]:checked').length;

                if (le1 == 0) {

                    alert(" Kindly select atleast one payment entry from Payment Section!!");

                    return false;

                }

                if (le == 0) {

                    alert(" Kindly select atleast one bill entry from Account Section!!");

                    return false;

                }

                if (confirm('Are you sure to continue ...'))

                {

                    return true;

                }

                else

                {

                    return false;

                }

                return true;

            }

            function validate1() {

                return false;

            }







        </script>

    </head>

    <body style="width:90%;">

        <form method="post">

            <p style="text-align:center;"><b><input type="text" name="Corporate" value="${corpName}" hidden/>

                    <input type="text" name="corpid" value="${corpid}" hidden/></b></p>



            <p align="center">Interest Pending for Corporate  </p>



            <table id="pendingVerifyCorp" align="center" style="min-height:400px;width:36%;" class="roundedCorners">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Week</th>

                        <th>Type</th>

                        <th>Revision</th>

                        <th>Interest Amount</th>

                        <th>Interest Pending Amount</th>

                    </tr>

                </thead>

                <tbody>

                    <c:choose>

                        <c:when test = "${empty listtempinterest}">



                            <c:forEach items="${listInterest}" var="ele">

                                <tr>

                                    <td>${ele.weekId}</td>

                                    <td>${ele.billType}</td>

                                    <td>${ele.revisionNo}</td>

                                    <td>${ele.interestAmount}</td>

                                    <td>${ele.interestPendingamount}</td>

                                </tr>

                            </c:forEach>

                        </c:when>

                        <c:otherwise>

                            <c:forEach items="${listtempinterest}" var="ele">

                                <tr>

                                    <td>${ele.weekId}</td>

                                    <td>${ele.billType}</td>

                                    <td>${ele.revisionNo}</td>

                                    <td>${ele.interestAmount}</td>

                                    <td>${ele.interestPendingamount}</td>

                                </tr>

                            </c:forEach>

                        </c:otherwise>



                    </c:choose>

                </tbody>

            </table>





            <table id="makerTable" align="center" style="min-height:400px;width:81%;" class="roundedCorners">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th colspan="8">Mapped Refund Entries</th>

                    </tr>

                    <tr>

                        <th>Bill ID</th>

                        <th>Type</th>

                        <th>Category</th>

                        <th>Week</th>

                        <th>Bill Due Date </th>

                        <th>Bill Amount</th>

                        <th>To be Verify Amount</th>

                        <th>Pending Amount</th>

                    </tr>

                </thead>

                <tbody>

                    <c:forEach items="${temprefundList}" var="ele">

                        <tr>

                            <td>${"R"}${ele.billReceiveCorp.uniqueId}</td>

                            <td>${ele.billReceiveCorp.billType}</td>

                            <td>${ele.billReceiveCorp.revisionNo}</td>

                            <td>${ele.weekid}</td>

                            <td>${ele.billReceiveCorp.billDueDate}</td>

                            <td>${ele.totalAmount}</td>

                            <td>${ele.paidAmount}</td>

                            <td>${ele.pendingAmount}</td>

                        </tr>

                    </c:forEach>

                </tbody>

            </table>



            <h3 align="center" style="color:#003366;">Bill & Payment Mapping for ${corpName}</h3>

            <table align="center" class="roundedCorners"  style="min-height: 400px;">

                <thead style="height:30px;">

                    <tr>

                        <th colspan="7">Maker-Checker Pending Verification</th>

                    </tr>

                </thead>

                <tbody>

                    <tr>

                        <td>

                            <table id="makerTable" align="center" style="min-height:400px;width:81%;" class="roundedCorners">

                                <thead style="background-color: #0677A1;color: white;height:30px;">

                                    <tr>

                                        <th colspan="8">Mapped Bill Entries</th>

                                    </tr>

                                    <tr>

                                        <th>Bill ID</th>

                                        <th>Type</th>

                                        <th>Category</th>

                                        <th>Week</th>

                                        <th>Bill Due Date </th>

                                        <th>Bill Amount</th>

                                        <th>To be Verify Amount</th>

                                        <th>Pending Amount</th>

                                    </tr>

                                </thead>

                                <tbody>

                                    <c:forEach items="${tempmaplist}" var="ele">

                                        <tr>

                                            <td>${"B"}${ele.billPayableCorp.uniqueId}</td>

                                            <td>${ele.billType}</td>

                                            <td>${ele.billPayableCorp.revisionNo}</td>

                                            <td>${ele.weekId}</td>

                                            <td>${ele.billPayableCorp.billDueDate}</td>

                                           <!--<td>${ele.corporates.corporateName}</td>-->

                                            <td>${ele.originalAmount}</td>

                                            <td>${ele.mappedAmount}</td>

                                            <td>${ele.pendingAmount}</td>

                                        </tr>

                                    </c:forEach>

                                </tbody>

                            </table>

                        </td>

                        <td>

                            <table align="center" style="min-height:400px;width:81%;" class="roundedCorners">

                                <thead style="background-color: #0677A1;color: white;height:30px;">

                                    <tr>

                                        <th colspan="7">Mapped Bank Entries</th>

                                    </tr>

                                    <tr>

                                        <th>Entry ID</th>

                                        <th>Credit Date</th>

                                        <th>Opening Amount</th>

                                        <th >Credit Amount</th>

                                        <th >To be Settle  Amount</th>

                                        <th>Entry Balance</th>

                                        <th>Remarks</th>

                                    </tr>

                                </thead>

                                <tbody>

                                    <c:forEach items="${tempbankstmtList}" var="ele">

                                        <tr>

                                            <td>${ele.bankStatement.stmtId}</td>

                                            <td>${ele.bankStatement.amountDate}</td>

                                            <td>${ele.bankStatement.openBalance}</td>

                                            <td >${ele.bankStatement.paidAmount}</td>

                                            <td >${ele.mappedAmount}</td>

                                            <td>${ele.transactionBalance}</td>

                                            <td>${ele.remarks}</td>

                                        </tr>

                                    </c:forEach>

                                </tbody>

                            </table>

                        </td>

                    </tr>

                </tbody>

            </table>

            <br/>

            <table align="center" class="roundedCorners"  style="min-height: 400px;">

                <thead style="height:30px;">

                    <tr>

                        <th colspan="9">Payment Verification of ${corpName} </th>

                    </tr>

                </thead>

                <tbody>

                    <tr>

                        <td>

                            <table  id="Account" class="roundedCorners" >

                                <thead>

                                    <tr>

                                        <th colspan="10">Account</th>

                                    </tr>

                                    <tr style="min-height: 100px;">

                                        <th>Select</th>

                                        <th> Type </th>

                                        <th> Category </th>

                                        <th> Week </th>

                                        <th> Bill Due Date </th>

                                        <th> Billed Amount </th>

                                        <th> Bill Outstanding Amount </th>

                                        <th> Settle Amount </th>

                                        <th> Pending Amount </th>

                                    </tr>

                                </thead>

                                <tbody>

                                    <%

                                        int tempflag = 0;

                                        int cnt = 0;

                                        String weekid = "weekid";

                                        String weekid1 = null;

                                        String category = "category";

                                        String category1 = null;

                                        String totalAmt = "totalAmt";

                                        String totalAmt1 = null;

                                        String settleAmt = "settleAmt";

                                        String settleAmt1 = null;

                                        String pendingAmt = "pendingAmt";

                                        String pendingAmt1 = null;

                                        String billtype = "billtype";

                                        String billtype1 = null;

                                        String uniqueid = "uniqueid";

                                        String uniqueid1 = null;

                                        String transactiontype = "transactiontype";

                                        String transactiontype1 = null;

                                    %>

                                    <!--Distinct Bill Dates -->

                                    <c:forEach items="${billdateList}" var="ele">

                                        <fmt:formatDate value="${ele}" pattern="yyyy-MM-dd" var="billdate" />

                                        <c:forEach items="${weekList}" var="objectWeek">

                                            <c:forEach items="${priorityList}" var="priority">



                                                <!--Starting for details in BillPayable List -->

                                                <c:forEach items="${billPayList}" var="objectList">

                                                    <fmt:formatDate value="${objectList.billingDate}" pattern="yyyy-MM-dd" var="billObjdate" />

                                                    <c:if test="${billdate==billObjdate}">

                                                        <c:if test="${objectList.weekId==objectWeek}">

                                                            <!--Checking for details in TempTables after confirm button-->

                                                            <c:if test="${empty tempmapMaxlist}">

                                                                <c:if test="${objectList.revisionNo==0}">

                                                                    <c:if test="${objectList.billType==priority.Priority}">

                                                                        <c:if test="${objectList.pendingAmount!=0}">

                                                                            <tr>

                                                                                <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"B"}${objectList.uniqueId}" size="1" />${"B"}${objectList.uniqueId}</td>

                                                                                <%                                                                                        tempflag = 1;

                                                                                    cnt++;

                                                                                    uniqueid1 = uniqueid + cnt;

                                                                                    settleAmt1 = settleAmt + cnt;

                                                                                    weekid1 = weekid + cnt;

                                                                                    category1 = category + cnt;

                                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                                    totalAmt1 = totalAmt + cnt;

                                                                                    billtype1 = billtype + cnt;

                                                                                    transactiontype1 = transactiontype + cnt;

                                                                                %>

                                                                        <input type="text" name="<%=transactiontype1%>"  value="${"B"}${objectList.uniqueId}" hidden="yes"/>

                                                                        <input type="text" name="<%=uniqueid1%>"  value="${objectList.uniqueId}" hidden="yes"/>

                                                                        <td><input type="text" name="<%=billtype1%>"  value="${objectList.billType}" hidden="yes"/> ${objectList.billType}</td>

                                                                        <td><input type="text" name="<%=category1%>"  value="${objectList.billCategory}" hidden="yes"/>${objectList.revisionNo}</td>

                                                                        <td><input type="text" name="<%=weekid1%>"  value="${objectList.weekId}" hidden="yes"/>${objectList.weekId}</td>

                                                                        <fmt:formatDate value="${objectList.billDueDate}" pattern="yyyy-MM-dd" var="objectListbillDueDate" />
                                                                        <td>${objectListbillDueDate}</td>

                                                                        <td><input type="text" name="<%=totalAmt1%>"  value="${objectList.totalnet}" hidden="yes"/>${objectList.totalnet}</td>

                                                                        <td>${objectList.pendingAmount}</td>

                                                                        <%

                                                                            settleAmt1 = settleAmt + cnt;

                                                                            pendingAmt1 = pendingAmt + cnt;

                                                                        %>

                                                                        <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                                        <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                            </tr>

                                                        </c:if>

                                                    </c:if>

                                                </c:if>

                                                <c:if test="${objectList.revisionNo>0}">

                                                    <c:if test="${objectList.billType==priority.Priority}">

                                                        <c:if test="${objectList.pendingAmount!=0}">

                                                            <tr>

                                                                <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"B"}${objectList.uniqueId}" size="1" />${"B"}${objectList.uniqueId}</td>

                                                                <%

                                                                    tempflag = 1;

                                                                    cnt++;

                                                                    uniqueid1 = uniqueid + cnt;

                                                                    settleAmt1 = settleAmt + cnt;

                                                                    weekid1 = weekid + cnt;

                                                                    category1 = category + cnt;

                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                    totalAmt1 = totalAmt + cnt;

                                                                    billtype1 = billtype + cnt;

                                                                    transactiontype1 = transactiontype + cnt;

                                                                %>

                                                        <input type="text" name="<%=transactiontype1%>"  value="${"B"}${objectList.uniqueId}" hidden="yes"/>

                                                        <input type="text" name="<%=transactiontype1%>"  value="${"B"}${objectList.uniqueId}" hidden="yes"/>

                                                        <input type="text" name="<%=uniqueid1%>"  value="${objectList.uniqueId}" hidden="yes"/>

                                                        <td><input type="text" name="<%=billtype1%>"  value="${objectList.billType}" hidden="yes"/> ${objectList.billType}</td>

                                                        <td><input type="text" name="<%=category1%>"  value="${objectList.billCategory}" hidden="yes"/>${objectList.revisionNo}</td>

                                                        <td><input type="text" name="<%=weekid1%>"  value="${objectList.weekId}" hidden="yes"/>${objectList.weekId}</td>

                                                        <fmt:formatDate value="${objectList.billDueDate}" pattern="yyyy-MM-dd" var="objectListbillDueDate" />
                                                        <td>${objectListbillDueDate}</td>

                                                        <td><input type="text" name="<%=totalAmt1%>"  value="${objectList.totalnet}" hidden="yes"/>${objectList.totalnet}</td>

                                                        <td>${objectList.pendingAmount}</td>

                                                        <%

                                                            settleAmt1 = settleAmt + cnt;

                                                            pendingAmt1 = pendingAmt + cnt;

                                                        %>

                                                        <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                        <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                        </tr>

                                                    </c:if>

                                                </c:if>

                                            </c:if>

                                        </c:if>

                                        <c:forEach var="country" items="${tempmapMaxlist}">

                                            <c:if test="${country.billPayableCorp.uniqueId==objectList.uniqueId && country.pendingAmount > 0}">

                                                <c:if test="${objectList.revisionNo==0}">

                                                    <c:if test="${objectList.billType==priority.Priority}">

                                                        <c:if test="${objectList.pendingAmount!=0}">

                                                            <tr>

                                                                <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"B"}${objectList.uniqueId}" size="1" />${"B"}${objectList.uniqueId}</td>

                                                                <%  cnt++;

                                                                    uniqueid1 = uniqueid + cnt;

                                                                    settleAmt1 = settleAmt + cnt;

                                                                    weekid1 = weekid + cnt;

                                                                    category1 = category + cnt;

                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                    totalAmt1 = totalAmt + cnt;

                                                                    billtype1 = billtype + cnt;

                                                                    transactiontype1 = transactiontype + cnt;

                                                                %>

                                                            <input type="text" name="<%=transactiontype1%>"  value="${"B"}${objectList.uniqueId}" hidden="yes"/>

                                                            <input type="text" name="<%=uniqueid1%>"  value="${objectList.uniqueId}" hidden="yes"/>

                                                            <td><input type="text" name="<%=billtype1%>"  value="${objectList.billType}" hidden="yes"/> ${objectList.billType}</td>

                                                            <td><input type="text" name="<%=category1%>"  value="${objectList.billCategory}" hidden="yes"/>${objectList.revisionNo}</td>

                                                            <td><input type="text" name="<%=weekid1%>"  value="${objectList.weekId}" hidden="yes"/>${objectList.weekId}</td>

                                                            <fmt:formatDate value="${objectList.billDueDate}" pattern="yyyy-MM-dd" var="objectListbillDueDate" />
                                                            <td>${objectListbillDueDate}</td>

                                                            <td><input type="text" name="<%=totalAmt1%>"  value="${objectList.totalnet}" hidden="yes"/>${objectList.totalnet}</td>

                                                            <td>${country.pendingAmount}</td>

                                                            <%

                                                                settleAmt1 = settleAmt + cnt;

                                                                pendingAmt1 = pendingAmt + cnt;

                                                            %>

                                                            <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                            <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                            </tr>

                                                        </c:if>

                                                    </c:if>

                                                </c:if>

                                                <c:if test="${objectList.revisionNo>0}">

                                                    <c:if test="${objectList.billType==priority.Priority}">

                                                        <c:if test="${objectList.pendingAmount!=0}">

                                                            <tr>

                                                                <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"B"}${objectList.uniqueId}" size="1" />${"B"}${objectList.uniqueId}</td>

                                                                <%  cnt++;

                                                                    uniqueid1 = uniqueid + cnt;

                                                                    settleAmt1 = settleAmt + cnt;

                                                                    weekid1 = weekid + cnt;

                                                                    category1 = category + cnt;

                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                    totalAmt1 = totalAmt + cnt;

                                                                    billtype1 = billtype + cnt;

                                                                    transactiontype1 = transactiontype + cnt;

                                                                %>

                                                            <input type="text" name="<%=transactiontype1%>"  value="${"B"}${objectList.uniqueId}" hidden="yes"/>

                                                            <input type="text" name="<%=uniqueid1%>"  value="${objectList.uniqueId}" hidden="yes"/>

                                                            <td><input type="text" name="<%=billtype1%>"  value="${objectList.billType}" hidden="yes"/> ${objectList.billType}</td>

                                                            <td><input type="text" name="<%=category1%>"  value="${objectList.billCategory}" hidden="yes"/>${objectList.revisionNo}</td>

                                                            <td><input type="text" name="<%=weekid1%>"  value="${objectList.weekId}" hidden="yes"/>${objectList.weekId}</td>

                                                            <fmt:formatDate value="${objectList.billDueDate}" pattern="yyyy-MM-dd" var="objectListbillDueDate" />
                                                            <td>${objectListbillDueDate}</td>

                                                            <td><input type="text" name="<%=totalAmt1%>"  value="${objectList.totalnet}" hidden="yes"/>${objectList.totalnet}</td>

                                                            <td>${country.pendingAmount}</td>

                                                            <%

                                                                settleAmt1 = settleAmt + cnt;

                                                                pendingAmt1 = pendingAmt + cnt;

                                                            %>

                                                            <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                            <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                            </tr>

                                                        </c:if>

                                                    </c:if>

                                                </c:if>

                                            </c:if>

                                        </c:forEach>

                                        <%

                                            if (tempflag == 0) {

                                        %>

                                        <c:forEach var="tempobjectList" items="${tempbillPayList}">

                                            <c:if test="${objectList.weekId==tempobjectList.weekId}">

                                                <c:if test="${objectList.revisionNo==0 && objectList.uniqueId == tempobjectList.uniqueId }">

                                                    <c:if test="${objectList.billType==priority.Priority}">

                                                        <c:if test="${objectList.pendingAmount!=0}">

                                                            <tr>

                                                                <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"B"}${objectList.uniqueId}" size="1" />${"B"}${objectList.uniqueId}</td>

                                                                <%  cnt++;

                                                                    uniqueid1 = uniqueid + cnt;

                                                                    settleAmt1 = settleAmt + cnt;

                                                                    weekid1 = weekid + cnt;

                                                                    category1 = category + cnt;

                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                    totalAmt1 = totalAmt + cnt;

                                                                    billtype1 = billtype + cnt;

                                                                    transactiontype1 = transactiontype + cnt;

                                                                %>

                                                            <input type="text" name="<%=transactiontype1%>"  value="${"B"}${objectList.uniqueId}" hidden="yes"/>

                                                            <input type="text" name="<%=uniqueid1%>"  value="${objectList.uniqueId}" hidden="yes"/>

                                                            <td><input type="text" name="<%=billtype1%>"  value="${objectList.billType}" hidden="yes"/> ${objectList.billType}</td>

                                                            <td><input type="text" name="<%=category1%>"  value="${objectList.billCategory}" hidden="yes"/>${objectList.revisionNo}</td>

                                                            <td><input type="text" name="<%=weekid1%>"  value="${objectList.weekId}" hidden="yes"/>${objectList.weekId}</td>

                                                            <fmt:formatDate value="${objectList.billDueDate}" pattern="yyyy-MM-dd" var="objectListbillDueDate" />
                                                            <td>${objectListbillDueDate}</td>

                                                            <td><input type="text" name="<%=totalAmt1%>"  value="${objectList.totalnet}" hidden="yes"/>${objectList.totalnet}</td>

                                                            <td>${objectList.pendingAmount}</td>

                                                            <%

                                                                settleAmt1 = settleAmt + cnt;

                                                                pendingAmt1 = pendingAmt + cnt;

                                                            %>

                                                            <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                            <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                            </tr>

                                                        </c:if>

                                                    </c:if>

                                                </c:if>

                                                <c:if test="${objectList.revisionNo>0 && objectList.uniqueId == tempobjectList.uniqueId}">

                                                    <c:if test="${objectList.billType==priority.Priority}">

                                                        <c:if test="${objectList.pendingAmount!=0}">

                                                            <tr>

                                                                <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"B"}${objectList.uniqueId}" size="1" />${"B"}${objectList.uniqueId}</td>

                                                                <%  cnt++;

                                                                    uniqueid1 = uniqueid + cnt;

                                                                    settleAmt1 = settleAmt + cnt;

                                                                    weekid1 = weekid + cnt;

                                                                    category1 = category + cnt;

                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                    totalAmt1 = totalAmt + cnt;

                                                                    billtype1 = billtype + cnt;

                                                                    transactiontype1 = transactiontype + cnt;

                                                                %>

                                                            <input type="text" name="<%=transactiontype1%>"  value="${"B"}${objectList.uniqueId}" hidden="yes"/>

                                                            <input type="text" name="<%=uniqueid1%>"  value="${objectList.uniqueId}" hidden="yes"/>

                                                            <td><input type="text" name="<%=billtype1%>"  value="${objectList.billType}" hidden="yes"/> ${objectList.billType}</td>

                                                            <td><input type="text" name="<%=category1%>"  value="${objectList.billCategory}" hidden="yes"/>${objectList.revisionNo}</td>

                                                            <td><input type="text" name="<%=weekid1%>"  value="${objectList.weekId}" hidden="yes"/>${objectList.weekId}</td>

                                                            <fmt:formatDate value="${objectList.billDueDate}" pattern="yyyy-MM-dd" var="objectListbillDueDate" />
                                                            <td>${objectListbillDueDate}</td>

                                                            <td><input type="text" name="<%=totalAmt1%>"  value="${objectList.totalnet}" hidden="yes"/>${objectList.totalnet}</td>

                                                            <td>${objectList.pendingAmount}</td>

                                                            <%

                                                                settleAmt1 = settleAmt + cnt;

                                                                pendingAmt1 = pendingAmt + cnt;

                                                            %>

                                                            <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                            <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                            </tr>

                                                        </c:if>

                                                    </c:if>

                                                </c:if>

                                            </c:if>

                                        </c:forEach>

                                        <%

                                            }

                                        %>

                                    </c:if>

                                </c:if>

                            </c:forEach>

                            <!--Strign of Bill Rercevie REFUND -->

                            <% int temprefudnflag = 0;%>

                            <c:forEach items="${refundList}" var="refundlist">

                                <fmt:formatDate value="${refundlist.billingDate}" pattern="yyyy-MM-dd" var="recvObjdate" />



                                <c:if test="${billdate==recvObjdate}">

                                    <c:if test="${objectWeek==refundlist.weekId}">

                                        <c:if test="${priority.Priority==refundlist.billType}">

                                            <c:if test="${empty temprefundList}">



                                                <tr>

                                                    <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"R"}${refundlist.uniqueId}" size="1" />${"R"}${refundlist.uniqueId}</td>

                                                    <%

                                                        temprefudnflag = 1;

                                                        cnt++;

                                                        uniqueid1 = uniqueid + cnt;

                                                        settleAmt1 = settleAmt + cnt;

                                                        weekid1 = weekid + cnt;

                                                        category1 = category + cnt;

                                                        pendingAmt1 = pendingAmt + cnt;

                                                        totalAmt1 = totalAmt + cnt;

                                                        billtype1 = billtype + cnt;

                                                        transactiontype1 = transactiontype + cnt;

                                                    %>

                                                <input type="text" name="<%=transactiontype1%>"  value="${"R"}${refundlist.uniqueId}" hidden="yes"/>

                                                <input type="text" name="<%=uniqueid1%>"  value="${refundlist.uniqueId}" hidden="yes"/>

                                                <td><input type="text" name="<%=billtype1%>"  value="${refundlist.billType}" hidden="yes"/> ${refundlist.billType}</td>

                                                <td><input type="text" name="<%=category1%>"  value="${refundlist.billCategory}" hidden="yes"/>${refundlist.revisionNo}</td>

                                                <td><input type="text" name="<%=weekid1%>"  value="${refundlist.weekId}" hidden="yes"/>${refundlist.weekId}</td>

                                                <fmt:formatDate value="${refundlist.billDueDate}" pattern="yyyy-MM-dd" var="refundlistbillDueDate" />
                                                <td>${refundlistbillDueDate}</td>
                                                <c:set var="minus1" scope="application" value="${-1}"/>
                                                <td><input type="text" name="<%=totalAmt1%>"  value="${minus1*refundlist.toalnet}" hidden="yes"/>${minus1*refundlist.toalnet}</td>

                                                <td>${refundlist.pendingAmount}</td>

                                                <%

                                                    settleAmt1 = settleAmt + cnt;

                                                    pendingAmt1 = pendingAmt + cnt;

                                                %>

                                                <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                </tr>

                                            </c:if>



                                            <c:forEach var="tmpref" items="${temprefundList}">





                                                <c:if test="${tmpref.pendingAmount !=0 && tmpref.billReceiveCorp.uniqueId==refundlist.uniqueId}">



                                                    <fmt:formatDate value="${tmpref.billReceiveCorp.billingDate}" pattern="yyyy-MM-dd" var="temprecvObjdate" />



                                                    <c:if test="${billdate==temprecvObjdate}">

                                                        <c:if test="${objectWeek==tmpref.billReceiveCorp.weekId}">

                                                            <c:if test="${priority.Priority==tmpref.billReceiveCorp.billType}">

                                                                <tr>

                                                                    <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"R"}${refundlist.uniqueId}" size="1" />${"R"}${refundlist.uniqueId}</td>

                                                                    <%  cnt++;

                                                                        uniqueid1 = uniqueid + cnt;

                                                                        settleAmt1 = settleAmt + cnt;

                                                                        weekid1 = weekid + cnt;

                                                                        category1 = category + cnt;

                                                                        pendingAmt1 = pendingAmt + cnt;

                                                                        totalAmt1 = totalAmt + cnt;

                                                                        billtype1 = billtype + cnt;

                                                                        transactiontype1 = transactiontype + cnt;

                                                                    %>

                                                                <input type="text" name="<%=transactiontype1%>"  value="${"R"}${refundlist.uniqueId}" hidden="yes"/>



                                                                <input type="text" name="<%=uniqueid1%>"  value="${refundlist.uniqueId}" hidden="yes"/>

                                                                <td><input type="text" name="<%=billtype1%>"  value="${refundlist.billType}" hidden="yes"/> ${refundlist.billType}</td>

                                                                <td><input type="text" name="<%=category1%>"  value="${refundlist.billCategory}" hidden="yes"/>${refundlist.revisionNo}</td>

                                                                <td><input type="text" name="<%=weekid1%>"  value="${refundlist.weekId}" hidden="yes"/>${refundlist.weekId}</td>

                                                                <fmt:formatDate value="${refundlist.billDueDate}" pattern="yyyy-MM-dd" var="refundlistbillDueDate" />
                                                                <td>${refundlistbillDueDate}</td>
                                                                
                                                                <td><input type="text" name="<%=totalAmt1%>"  value="${refundlist.toalnet}" hidden="yes"/>${refundlist.toalnet}</td>

                                                                <td>${tmpref.pendingAmount}</td>

                                                                <%

                                                                    settleAmt1 = settleAmt + cnt;

                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                %>

                                                                <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                                <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                                </tr>

                                                            </c:if>

                                                        </c:if>

                                                    </c:if>

                                                </c:if>

                                            </c:forEach>



                                            <%

                                                if (temprefudnflag == 0) {

                                            %>



                                            <c:forEach var="listNotINtmpref" items="${listNotINtemprefund}">

                                                <c:if test="${listNotINtmpref.uniqueId==refundlist.uniqueId}">

                                                    <fmt:formatDate value="${listNotINtmpref.billingDate}" pattern="yyyy-MM-dd" var="notrecvObjdate" />



                                                    <c:if test="${billdate==notrecvObjdate}">

                                                        <c:if test="${objectWeek==listNotINtmpref.weekId}">

                                                            <c:if test="${priority.Priority==listNotINtmpref.billType}">

                                                                <tr>

                                                                    <td><input type="checkbox" class="case" name="uniqueNo" id="uniqueNo" value="${"R"}${refundlist.uniqueId}" size="1" />${"R"}${refundlist.uniqueId}</td>

                                                                    <%  cnt++;

                                                                        uniqueid1 = uniqueid + cnt;

                                                                        settleAmt1 = settleAmt + cnt;

                                                                        weekid1 = weekid + cnt;

                                                                        category1 = category + cnt;

                                                                        pendingAmt1 = pendingAmt + cnt;

                                                                        totalAmt1 = totalAmt + cnt;

                                                                        billtype1 = billtype + cnt;

                                                                        transactiontype1 = transactiontype + cnt;

                                                                    %>

                                                                <input type="text" name="<%=transactiontype1%>"  value="${"R"}${refundlist.uniqueId}" hidden="yes"/>



                                                                <input type="text" name="<%=uniqueid1%>"  value="${refundlist.uniqueId}" hidden="yes"/>

                                                                <td><input type="text" name="<%=billtype1%>"  value="${refundlist.billType}" hidden="yes"/> ${refundlist.billType}</td>

                                                                <td><input type="text" name="<%=category1%>"  value="${refundlist.billCategory}" hidden="yes"/>${refundlist.revisionNo}</td>

                                                                <td><input type="text" name="<%=weekid1%>"  value="${refundlist.weekId}" hidden="yes"/>${refundlist.weekId}</td>

                                                                <fmt:formatDate value="${refundlist.billDueDate}" pattern="yyyy-MM-dd" var="refundlistbillDueDate" />
                                                                <td>${refundlistbillDueDate}</td>
                                                                <c:set var="minus1" scope="application" value="${-1}"/> 
                                                                <td><input type="text" name="<%=totalAmt1%>"  value="${minus1*refundlist.toalnet}" hidden="yes"/>${minus1*refundlist.toalnet}</td>

                                                                <td>${listNotINtmpref.pendingAmount}</td>

                                                                <%

                                                                    settleAmt1 = settleAmt + cnt;

                                                                    pendingAmt1 = pendingAmt + cnt;

                                                                %>

                                                                <td><input type="text" name="<%=settleAmt1%>" id="<%=settleAmt1%>" size="8" value="0" readonly/></td>

                                                                <td><input type="text" name="<%=pendingAmt1%>" id="<%=pendingAmt1%>" size="8" value="0" readonly/></td>

                                                                </tr>

                                                            </c:if>

                                                        </c:if>

                                                    </c:if>

                                                </c:if>

                                            </c:forEach>

                                            <%

                                                }

                                            %>



                                        </c:if>

                                    </c:if>

                                </c:if>



                            </c:forEach>

                        </c:forEach>

                    </c:forEach>

                </c:forEach>

                <input type="text" name="rowcount" value="<%=cnt%>" hidden="yes"/>

                </tbody>

            </table>

        </td>

        <td>

            <table  id="Payment" class="roundedCorners">

                <thead>

                    <tr>

                        <th colspan="9">Payment</th>

                    </tr>

                    <tr>

                        <th class="no-sort"> Select </th>

                        <th  style="display:none;">Statement Id</th>

                        <th>Credit Date</th>

                        <th class="no-sort"> Initial Balance </th>

                        <th class="no-sort"> Current Balance</th>

                        <th class="no-sort"> Settle Amount </th>

                        <th class="no-sort"> Remaining Balance </th>

                        <th class="no-sort"> Remarks </th>

                </thead>

                <tbody>

                    <%

                        cnt = 0;

                        String bankstmt = "bankstmt";

                        String bankstmt1 = null;

                        String remainBal = "remainBal";

                        String remainBal1 = null;

                        String settleAmtBnk = "settleAmtBnk";

                        String settleAmtBnk1 = null;

                        String remarks = "remarks";

                        String remarks1 = null;

                        String totalamt = "totalamt";

                        String totalamt1 = null;

                    %>

                    <c:choose>

                        <c:when test = "${empty bankList}">

                            <c:forEach items="${bnkstmtList}" var="ele">

                                <tr>

                                    <%  cnt++;

                                        remainBal1 = remainBal + cnt;

                                        settleAmtBnk1 = settleAmtBnk + cnt;

                                        remarks1 = remarks + cnt;

                                        bankstmt1 = bankstmt + cnt;

                                        totalamt1 = totalamt + cnt;

                                    %>

                                    <td><input type="checkbox" class="case1" name="bankstmt" value="${ele.stmtId}" size="1" />${ele.stmtId}</td>

                                    <td style="display:none;"><input type="text"  name="<%=bankstmt1%>" value="${ele.stmtId}" hidden="yes" /></td>

                                    <td>${ele.amountDate}</td>

                                    <td><input type="text"  name="<%=totalamt1%>" value="${ele.paidAmount}" hidden="yes" />${ele.paidAmount}</td>

                                    <td>${ele.mappedBalance}</td>

                                    <td><input type="text" name="<%=settleAmtBnk1%>" id="<%=settleAmtBnk1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=remainBal1%>" id="<%=remainBal1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=remarks1%>" id="<%=remarks1%>" size="8" value="Remarks" />

                                </tr>

                            </c:forEach>

                        </c:when>

                        <c:otherwise>

                            <c:forEach items="${bnkstmtList}" var="ele">

                                <c:forEach items="${bankList}" var="ele123">

                                    <c:if test="${ele123.transactionBalance > 0 && ele123.bankStatement.stmtId==ele.stmtId}">

                                        <tr>

                                            <%

                                                cnt++;

                                                remainBal1 = remainBal + cnt;

                                                settleAmtBnk1 = settleAmtBnk + cnt;

                                                remarks1 = remarks + cnt;

                                                bankstmt1 = bankstmt + cnt;

                                                totalamt1 = totalamt + cnt;

                                            %>

                                            <td><input type="checkbox" class="case1" name="bankstmt" value="${ele.stmtId}" size="1" />${ele.stmtId}</td>

                                            <td style="display:none;"><input type="text"  name="<%=bankstmt1%>" value="${ele.stmtId}" hidden="yes" /></td>

                                            <td>${ele.amountDate}</td>

                                            <td><input type="text"  name="<%=totalamt1%>" value="${ele.paidAmount}" hidden="yes" />${ele.paidAmount}</td>

                                            <td>${ele123.transactionBalance}</td>

                                            <td><input type="text" name="<%=settleAmtBnk1%>" id="<%=settleAmtBnk1%>" size="8" value="0" readonly/></td>

                                            <td><input type="text" name="<%=remainBal1%>" id="<%=remainBal1%>" size="8" value="0" readonly/></td>

                                            <td><input type="text" name="<%=remarks1%>" id="<%=remarks1%>" size="8" value="Remarks" />

                                        </tr>

                                    </c:if>

                                </c:forEach>

                            </c:forEach>

                            <c:forEach items="${tempBankStmtlist}" var="ele">

                                <tr>

                                    <%  cnt++;

                                        remainBal1 = remainBal + cnt;

                                        settleAmtBnk1 = settleAmtBnk + cnt;

                                        remarks1 = remarks + cnt;

                                        bankstmt1 = bankstmt + cnt;

                                        totalamt1 = totalamt + cnt;

                                    %>

                                    <td><input type="checkbox" class="case1" name="bankstmt" value="${ele.stmtId}" size="1" />${ele.stmtId}</td>

                                    <td style="display:none;"><input type="text"  name="<%=bankstmt1%>" value="${ele.stmtId}" hidden="yes" /></td>

                                    <td>${ele.amountDate}</td>

                                    <td><input type="text"  name="<%=totalamt1%>" value="${ele.paidAmount}" hidden="yes" />${ele.paidAmount}</td>

                                    <td>${ele.mappedBalance}</td>

                                    <td><input type="text" name="<%=settleAmtBnk1%>" id="<%=settleAmtBnk1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=remainBal1%>" id="<%=remainBal1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=remarks1%>" id="<%=remarks1%>" size="8" value="Remarks" />

                                </tr>

                            </c:forEach>

                        </c:otherwise>

                    </c:choose>

                </tbody>

                <input type="text" name="bankrowcount" value="<%=cnt%>" hidden="yes"/>

            </table>

        </td>

    </tr>

</tbody>

</table>

<p align="center">

    <input type="submit" name="confirm"  id="SubmitId" value="&#10010;Confirm"  onclick="return validate();" />

    <input type="submit" id="resetID" value="Reset" onclick="return validate1();"  /></p>

<br/> <br/> <br/> <br/> <br/>





</form>
<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>

</body>

</html>