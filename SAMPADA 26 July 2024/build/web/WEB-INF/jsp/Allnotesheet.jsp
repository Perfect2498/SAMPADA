<%-- 
    Document   : Allnotesheet
    Created on : 6 Mar, 2020, 1:01:20 PM
    Author     : abt
--%>








<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

                var table = $('#payableTable').DataTable({
                    responsive: true,
                    paging: true,
                    "pageLength": 25,
                    "lengthMenu": [[-1, 25, 50, 100], ["All", 25, 50, 100]],
                    "order": [[16, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true

                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true

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
                                pageTotal.toFixed(2)
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
                                pageTotal.toFixed(2)
                                );
                        total = api
                                .column(11)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(11, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(11).footer()).html(
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


                var table = $('#payableTable').DataTable();
                //$('#search-date').datepicker({"dateFormat":"yy/mm/dd"});



                $('#search-Disburse_var').on('change', function () {

                    table
                            .column(0)
                            .search(this.value)
                            .draw();
                });
                $('#search-Disburse_Id').on('change', function () {

                    table
                            .column(1)
                            .search(this.value)
                            .draw();
                });
                $('#search-Pool_Member').on('change', function () {

                    table
                            .column(2)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bill_Year').on('change', function () {

                    table
                            .column(3)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bill_type').on('change', function () {

                    table
                            .column(4)
                            .search(this.value)
                            .draw();
                });
                $('#search-WEEK_ID').on('change', function () {

                    table
                            .column(5)
                            .search(this.value)
                            .draw();
                });
                $('#search-Rev_No').on('change', function () {

                    table
                            .column(6)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bill_Amount').on('change', function () {

                    table
                            .column(7)
                            .search(this.value)
                            .draw();
                });
                $('#search-Diff_Amount').on('change', function () {

                    table
                            .column(8)
                            .search(this.value)
                            .draw();
                });
                $('#search-Already_Released').on('change', function () {

                    table
                            .column(9)
                            .search(this.value)
                            .draw();
                });
                $('#search-Disbursed').on('change', function () {

                    table
                            .column(10)
                            .search(this.value)
                            .draw();
                });
                $('#search-Pending').on('change', function () {

                    table
                            .column(11)
                            .search(this.value)
                            .draw();
                });
                $('#search-Disburse_date').on('change', function () {

                    table
                            .column(12)
                            .search(this.value)
                            .draw();
                });
                $('#search-Remarks').on('change', function () {

                    table
                            .column(13)
                            .search(this.value)
                            .draw();
                });
                $('#search-Pool_Bal').on('change', function () {

                    table
                            .column(14)
                            .search(this.value)
                            .draw();
                });
                $('#search-Disbursed_In_Bank').on('change', function () {

                    table
                            .column(15)
                            .search(this.value)
                            .draw();
                });
                $('#search-Process_Flow').on('change', function () {

                    table
                            .column(16)
                            .search(this.value)
                            .draw();
                });
                $('#search-BankAccName').on('change', function () {

                    table
                            .column(17)
                            .search(this.value)
                            .draw();
                });


            });
        </script>


    </head>
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" >
        <!--<h3 align="center" style="color:#003366;">From Week-id ${week_id1} to Week-id ${week_id2} of year ${yeari}</h3>-->
        <!--<table id="payableTable" align="center" style="min-height:400px;width:80%;" class="table table-striped table-bordered">-->

        <form>

            <legend style="color: black"> <h3>Note Sheets</h3></legend>


            <input type="text" name="finclyear" id="finclyear" value="${finclyear}" hidden/> 
            <table align="center" style="width:50%;">
                <tr>
                    <td>
                        <table align="center" style="width:50%;">
                            <tr>
                                <td>B-from</td><td><input type="text" name="startbill" value="0" required autocomplete="off"/></td>
                                <td>B-to</td><td><input type="text" name="endbill" value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>R-from</td><td><input type="text" name="startref" value="0" required autocomplete="off"/></td>
                                <td>R-to</td><td><input type="text" name="endref"  value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>I-from</td><td><input type="text" name="startint" value="0" required autocomplete="off"/></td>
                                <td>I-to</td><td><input type="text" name="endint" value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>P-from</td><td><input type="text" name="startpsdf" value="0" required autocomplete="off"/></td>
                                <td>P-to</td><td><input type="text" name="endpsdf"  value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>M-from</td><td><input type="text" name="startmisc" value="0" required autocomplete="off"/></td>
                                <td>M-to</td><td><input type="text" name="endmisc"  value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="submit" name="bsubmit" align="center"  value="Get_Disburse_NOTESHEET"  style="font-size:20px;"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table align="center" style="width:50%;">
                            <tr>
                                <td>STMT-from</td><td><input type="text" name="startbank" value="0" required autocomplete="off"/></td>
                                <td>STMT-to</td><td><input type="text" name="endbank" value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>I-STMT-from</td><td><input type="text" name="startbankint" value="0" required autocomplete="off"/></td>
                                <td>I-STMT-to</td><td><input type="text" name="endbankint" value="0" required autocomplete="off"/></td>
                            </tr>

                            <tr>
                                <td>B-STMT-from</td><td><input type="text" name="startmapbil" value="0" required autocomplete="off"/></td>
                                <td>B-STMT-to</td><td><input type="text" name="endmapbil" value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>R-STMT-from</td><td><input type="text" name="startmapref" value="0" required autocomplete="off"/></td>
                                <td>R-STMT-to</td><td><input type="text" name="endmapref" value="0" required autocomplete="off"/></td>
                            </tr>

                            <tr>
                                <td>P-TO-INT - from</td><td><input type="text" name="startptoint" value="0" required autocomplete="off"/></td>
                                <td>P-TO-INT - to</td><td><input type="text" name="endptoint" value="0" required autocomplete="off"/></td>
                            </tr>
                            
                            <tr>
                                <td>P-TO-P - from</td><td><input type="text" name="startptop" value="0" required autocomplete="off"/></td>
                                <td>P-TO-P - to</td><td><input type="text" name="endptop" value="0" required autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="submit" name="bsubmitbank" align="center"  value="Get_Mapping_NOTESHEET"  style="font-size:20px;"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
        <form>
            <fieldset>
                <legend> <h3>Disbursed and Mapped Details</h3></legend>



                <div style="overflow-x:auto; white-space: nowrap; max-width: 1500px;">
                    <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                        <thead style="background-color: #0677A1;color: white;height:30px;">

                            <tr>
                                <td><input type="text" id="search-Disburse_var" placeholder="search Disburse_var"></td>
                                <td><input type="text" id="search-Disburse_Id" placeholder="search Disburse_Id"></td>
                                <td><input type="text" id="search-Pool_Member" placeholder="search Pool_Member"></td>
                                <td><input type="text" id="search-Bill_Year" placeholder="search Bill_Year"></td>
                                <td><input type="text" id="search-Bill_type" placeholder="search Bill_type"></td>
                                <td><input type="text" id="search-WEEK_ID" placeholder="search WEEK_ID"></td>
                                <td><input type="text" id="search-Rev_No" placeholder="search Rev_No"></td>
                                <td><input type="text" id="search-Bill_Amount" placeholder="search Bill_Amount"></td>
                                <td><input type="text" id="search-Diff_Amount" placeholder="search Diff_Amount"></td>

                                <td><input type="text" id="search-Already_Released" placeholder="search Already_Released"></td>
                                <td><input type="text" id="search-Disbursed" placeholder="search Disbursed"></td>
                                <td><input type="text" id="search-Pending" placeholder="search Pending"></td>
                                <td><input type="text" id="search-Disburse_date" placeholder="search Disburse_date"></td>
                                <td><input type="text" id="search-Remarks" placeholder="search Remarks"></td>
                                <td><input type="text" id="search-Pool_Bal" placeholder="search Pool_Bal"></td>
                                <td><input type="text" id="search-Disbursed_In_Bank" placeholder="search Disbursed_In_Bank"></td>
                                <td><input type="text" id="search-Process_Flow" placeholder="search Process_Flow"></td>
                                <td><input type="text" id="search-BankAccName" placeholder="search BankAccName"></td>



                            </tr>

                            <tr>
                                <th>Disburse var</th>
                                <th>Disburse Id</th>
                                <th>Pool Member Name</th>
                                <th>Bill Year</th>
                                <th>Bill type</th>
                                <th>WEEK_ID </th>
                                <th>Rev_No</th>
                                <th>Bill Amount</th>
                                <th>Diff. Amount wrt Rev.</th>
                                <th>Already Released</th>
                                <th>Software Disbursed</th>
                                <th>Pending</th>
                                <th>Software Disburse date</th>
                                <th>Disburse Remarks</th>
                                <th>Pool Bal</th>
                                <th>Disbursed In Bank</th>
                                <th>Process Flow</th>
                                <th>Bank Account Name</th>


                            </tr>

                        </thead>
                        <tbody>


                            <c:forEach items="${disbillist}" var="bill">
                                <tr>
                                    <td>${"B"}</td>
                                    <td>${bill.disburseId}</td>
                                    <td>${bill.corporates.corporateName}</td>
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
                                    <td style="color: red">${"Not Yet"}</td>
                                    <td>${bill.entryTime}</td>
                                    <td>${bill.corporates.bankAccName}</td>
                                </tr>
                            </c:forEach>

                            <c:forEach items="${disbillistIN}" var="bill">
                                <tr>
                                    <td>${"B"}</td>
                                    <td>${bill.disburseId}</td>
                                    <td>${bill.corporates.corporateName}</td>
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
                                    <td>${"Yes"}</td>
                                    <td>${bill.entryTime}</td>
                                    <td>${bill.corporates.bankAccName}</td>
                                </tr>
                            </c:forEach>

                            <c:forEach items="${disreflist}" var="ref">
                                <tr>
                                    <td>${"R"}</td>
                                    <td>${ref.slno}</td>
                                    <td>${ref.corporates.corporateName}</td>
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
                                    <td style="color: red">${"Not Yet"}</td>
                                    <td>${ref.entryTime}</td>
                                    <td>${ref.corporates.bankAccName}</td>


                                </tr>
                            </c:forEach>

                            <c:forEach items="${disreflistIN}" var="ref">
                                <tr>
                                    <td>${"R"}</td>
                                    <td>${ref.slno}</td>
                                    <td>${ref.corporates.corporateName}</td>
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
                                    <td>${"Yes"}</td>
                                    <td>${ref.entryTime}</td>
                                    <td>${ref.corporates.bankAccName}</td>

                                </tr>
                            </c:forEach>

                            <c:forEach items="${disintlist}" var="int">
                                <tr>
                                    <td>${"I"}</td>
                                    <td>${int.slno}</td>
                                    <td>${int.disbursedInterestDetails.corporates.corporateName}</td>
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
                                    <td style="color: red">${"Not Yet"}</td>
                                    <td>${int.entryTime}</td>
                                    <td>${int.disbursedInterestDetails.corporates.bankAccName}</td>

                                </tr>
                            </c:forEach>
                            <c:forEach items="${disintlistIN}" var="int">
                                <tr>
                                    <td>${"I"}</td>
                                    <td>${int.slno}</td>
                                    <td>${int.disbursedInterestDetails.corporates.corporateName}</td>
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
                                    <td>${"Yes"}</td>
                                    <td>${int.entryTime}</td>
                                    <td>${int.disbursedInterestDetails.corporates.bankAccName}</td>

                                </tr>
                            </c:forEach>
                            <c:forEach items="${psdfdetlist}" var="psdf">
                                <tr>
                                    <td>${"P"}</td>
                                    <td>${psdf.slno}</td>
                                    <td>${"PSDF"}</td>
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
                                    <td style="color: red">${"Not Yet"}</td>
                                    <td>${psdf.entryTime}</td>
                                    <td>${"PSDF"}</td>

                                </tr>
                            </c:forEach>

                            <c:forEach items="${psdfdetlistIN}" var="psdf">
                                <tr>
                                    <td>${"P"}</td>
                                    <td>${psdf.slno}</td>
                                    <td>${"PSDF"}</td>
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
                                    <td>${"Yes"}</td>
                                    <td>${psdf.entryTime}</td>
                                    <td>${"PSDF"}</td>

                                </tr>
                            </c:forEach>

                            <c:forEach items="${misclist}" var="misc">
                                <tr>
                                    <td>${"M"}</td>
                                    <td>${misc.uniqueId}</td>
                                    <td>${misc.corpName}</td>
                                    <td></td>
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
                                    <td style="color: red">${"Not Yet"}</td>
                                    <td>${misc.entryTime}</td>
                                    <td>${misc.corpName}</td>

                                </tr>
                            </c:forEach>
                            <c:forEach items="${misclistIN}" var="misc">
                                <tr>
                                    <td>${"M"}</td>
                                    <td>${misc.uniqueId}</td>
                                    <td>${misc.corpName}</td>
                                    <td></td>
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
                                    <td>${"Yes"}</td>
                                    <td>${misc.entryTime}</td>
                                    <td>${misc.corpName}</td>

                                </tr>
                            </c:forEach>

                            <c:forEach items="${bnkstmt}" var="bnkstmt">
                                <tr>
                                    <td style="color: #398439">${"STMT"}</td>
                                    <td style="color: #398439">${bnkstmt.stmtId}</td>
                                    <td style="color: #398439">${bnkstmt.corporates.corporateName}</td>
                                    <td style="color: #398439"></td>
                                    <td style="color: #398439"></td>
                                    <td style="color: #398439"></td>
                                    <td style="color: #398439"></td>
                                    <td style="color: #398439">${bnkstmt.paidAmount}</td>
                                    <td style="color: #398439"></td>
                                    <td style="color: #398439"></td>
                                    <td style="color: #398439">${bnkstmt.mappedAmount}</td>
                                    <td style="color: #398439">${bnkstmt.mappedBalance}</td>
                                    <td style="color: #398439">${bnkstmt.entryDate}</td>
                                    <td style="color: #398439">${bnkstmt.remarks}</td>
                                    <td style="color: #398439">${bnkstmt.poolBal}</td>
                                    <td style="color: #398439">${"Bank_Cr"}</td>
                                    <td style="color: #398439">${bnkstmt.entryTime}</td>
                                    <td style="color: #398439">${bnkstmt.corporates.bankAccName}</td>

                                </tr>
                            </c:forEach>  

                            <c:forEach items="${mapbilbnk}" var="mapbilbnk">
                                <tr>
                                    <td style="color: #00BFFF">${"B-STMT"}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.uniqueId}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.corporates.corporateName}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.billPayableCorp.billYear}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.billPayableCorp.billType}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.billPayableCorp.weekId}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.billPayableCorp.revisionNo}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.mappedAmount+mapbilbnk.pendingAmount}</td>
                                    <td style="color: #00BFFF"></td>
                                    <td style="color: #00BFFF"></td>
                                    <td style="color: #00BFFF">${mapbilbnk.mappedAmount}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.pendingAmount}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.entryDate}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.remarks}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.poolBal}</td>
                                    <c:choose>
                                        <c:when test="${mapbilbnk.billPayableCorp.billType == 'SRAS'}">
                                            <td style="color: #00BFFF">${"SRAS-bal: "}${mapbilbnk.poolAgcBal}</td>
                                        </c:when>
                                        <c:when test="${mapbilbnk.billPayableCorp.billType == 'TRASM' or mapbilbnk.billPayableCorp.billType == 'TRASS' or mapbilbnk.billPayableCorp.billType == 'TRASE'}">
                                            <td style="color: #00BFFF">${"TRAS-bal: "}${mapbilbnk.poolRrasBal}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td></td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td style="color: #00BFFF">${mapbilbnk.entryTime}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.bankStatement.corporates.bankAccName}</td>

                                </tr>
                            </c:forEach>

                            <c:forEach items="${maprefbnk}" var="mapbilbnk">
                                <tr>
                                    <td style="color: #00BFFF">${"R-STMT"}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.slno}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.tempRefundBillCorp.corporates.corporateName}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.tempRefundBillCorp.billReceiveCorp.billYear}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.tempRefundBillCorp.billReceiveCorp.billType}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.tempRefundBillCorp.billReceiveCorp.weekId}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.tempRefundBillCorp.billReceiveCorp.revisionNo}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.mappedAmount+mapbilbnk.pendingAmount}</td>
                                    <td style="color: #00BFFF"></td>
                                    <td style="color: #00BFFF"></td>
                                    <td style="color: #00BFFF">${mapbilbnk.mappedAmount}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.pendingAmount}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.entryDate}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.remarks}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.poolBal}</td>
                                    <c:choose>
                                        <c:when test="${mapbilbnk.tempRefundBillCorp.billReceiveCorp.billType == 'SRAS'}">
                                            <td style="color: #00BFFF">${"SRAS-bal: "}${mapbilbnk.poolAgcBal}</td>
                                        </c:when>
                                        <c:when test="${mapbilbnk.tempRefundBillCorp.billReceiveCorp.billType == 'TRASM' or mapbilbnk.tempRefundBillCorp.billReceiveCorp.billType == 'TRASS' or mapbilbnk.tempRefundBillCorp.billReceiveCorp.billType == 'TRASE'}">
                                            <td style="color: #00BFFF">${"TRAS-bal: "}${mapbilbnk.poolRrasBal}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td></td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td style="color: #00BFFF">${mapbilbnk.entryTime}</td>
                                    <td style="color: #00BFFF">${mapbilbnk.bankStatement.corporates.bankAccName}</td>

                                </tr>
                            </c:forEach>

                            <c:forEach items="${mapintbnk}" var="mapintbnk">
                                <tr>
                                    <td style="color: #00BFFF">${"I-STMT"}</td>
                                    <td style="color: #00BFFF">${mapintbnk.slno}</td>
                                    <td style="color: #00BFFF">${mapintbnk.bankStatement.corporates.corporateName}</td>
                                    <td style="color: #00BFFF">${mapintbnk.interestDetails.billYear}</td>
                                    <td style="color: #00BFFF">${mapintbnk.interestDetails.billType}</td>
                                    <td style="color: #00BFFF">${mapintbnk.interestDetails.weekId}</td>
                                    <td style="color: #00BFFF">${mapintbnk.interestDetails.revisionNo}</td>
                                    <td style="color: #00BFFF">${mapintbnk.mappedAmount+mapintbnk.pendingAmount}</td>
                                    <td style="color: #00BFFF"></td>
                                    <td style="color: #00BFFF"></td>
                                    <td style="color: #00BFFF">${mapintbnk.mappedAmount}</td>
                                    <td style="color: #00BFFF">${mapintbnk.pendingAmount}</td>
                                    <td style="color: #00BFFF">${mapintbnk.entryDate}</td>
                                    <td style="color: #00BFFF">${mapintbnk.remarks}</td>
                                    <c:choose>
                                        <c:when test="${mapintbnk.interestDetails.billType == 'SRAS'}">
                                            <td style="color: #00BFFF">${mapintbnk.poolBal}</td>
                                            <td style="color: #00BFFF">${"INT-SRAS-bal: "}${mapintbnk.intAgcBal}</td>

                                        </c:when>
                                        <c:when test="${mapintbnk.interestDetails.billType == 'TRASM' or mapintbnk.interestDetails.billType == 'TRASS' or mapintbnk.interestDetails.billType == 'TRASE'}">
                                            <td style="color: #00BFFF">${mapintbnk.poolBal}</td>
                                            <td style="color: #00BFFF">${"INT-TRAS-bal: "}${mapintbnk.intRrasBal}</td>

                                        </c:when>
                                        <c:otherwise>
                                            <td style="color: #00BFFF">${mapintbnk.poolBal}</td>

                                            <td style="color: #00BFFF">${"Int-bal: "}${mapintbnk.after_int_pool}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td style="color: #00BFFF">${mapintbnk.entryTime}</td>
                                    <td style="color: #00BFFF">${mapintbnk.bankStatement.corporates.bankAccName}</td>

                                </tr>
                            </c:forEach>  




                            <c:forEach items="${pooltoint}" var="pooltoint">
                                <tr>
                                    <td style="color: #ff6633">${"P-TO-INT"}</td>
                                    <td style="color: #ff6633">${pooltoint.uniqueId}</td>
                                    <td style="color: #ff6633">${"WRLDC INTERNAL TRANSFER"}</td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633">${pooltoint.refundAmt}</td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633">${pooltoint.refundAmt}</td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633">${pooltoint.entryDate}</td>
                                    <td style="color: #ff6633">${pooltoint.remarks}</td>
                                    <c:choose>
                                        <c:when test="${pooltoint.poolAgcBal != null}">
                                            <td style="color: #ff6633">${"SRAS-bal: "}${pooltoint.poolAgcBal}</td>
                                            <td style="color: #ff6633">${"INT-SRAS-bal: "}${pooltoint.intAgcBal}</td>

                                        </c:when>
                                        <c:when test="${pooltoint.poolRrasBal != null}">
                                            <td style="color: #ff6633">${"TRAS-bal: "}${pooltoint.poolRrasBal}</td>
                                            <td style="color: #ff6633">${"INT-TRAS-bal: "}${pooltoint.intRrasBal}</td>

                                        </c:when>
                                        <c:otherwise>
                                            <td style="color: #ff6633">${pooltoint.mainPoolBalance}</td>
                                            <td style="color: #ff6633">${"Int-bal: "}${pooltoint.intPoolBalance}</td>
                                        </c:otherwise>
                                    </c:choose>

                                    <td style="color: #ff6633">${pooltoint.entryTime}</td>
                                    <td style="color: #ff6633">${"WRLDC INTERNAL TRANSFER"}</td>

                                </tr>
                            </c:forEach> 

                            <c:forEach items="${pooltopool}" var="pooltoint">
                                <tr>
                                    <td style="color: #ff6633">${"P-TO-P"}</td>
                                    <td style="color: #ff6633">${pooltoint.uniqueId}</td>
                                    <td style="color: #ff6633">${"WRLDC INTERNAL TRANSFER"}</td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633">${pooltoint.refundAmt}</td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633">${pooltoint.refundAmt}</td>
                                    <td style="color: #ff6633"></td>
                                    <td style="color: #ff6633">${pooltoint.entryDate}</td>
                                    <td style="color: #ff6633">${pooltoint.remarks}</td>
                                    <c:choose>
                                        <c:when test="${pooltoint.transId == '1'}">
                                            <td style="color: #ff6633">${"DSM-bal: "}${pooltoint.mainPoolBalance}</td>
                                            <td style="color: #ff6633">${"SRAS-bal: "}${pooltoint.poolAgcBal}</td>

                                        </c:when>
                                        <c:when test="${pooltoint.transId == '2'}">
                                            <td style="color: #ff6633">${"DSM-bal: "}${pooltoint.mainPoolBalance}</td>
                                            <td style="color: #ff6633">${"TRAS-bal: "}${pooltoint.poolRrasBal}</td>

                                        </c:when>
                                        <c:otherwise>
                                            <td style="color: #ff6633">${"SRAS-bal: "}${pooltoint.poolAgcBal}</td>
                                            <td style="color: #ff6633">${"TRAS-bal: "}${pooltoint.poolRrasBal}</td>
                                        </c:otherwise>
                                    </c:choose>

                                    <td style="color: #ff6633">${pooltoint.entryTime}</td>
                                    <td style="color: #ff6633">${"WRLDC INTERNAL TRANSFER"}</td>

                                </tr>
                            </c:forEach> 
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
                                <th style="text-align:right">Total:</th>
                                <th></th>
                                <th style="text-align:right">Total:</th>
                                <th style="text-align:right">Total:</th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>


                            </tr>
                        </tfoot>
                    </table>
                </div>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
            </fieldset>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
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
    <p>&nbsp;</p>
    <p>&nbsp;</p>
</html>







