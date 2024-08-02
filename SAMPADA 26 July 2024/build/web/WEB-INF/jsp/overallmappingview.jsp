<%-- 
    Document   : overallmappingview
    Created on : 10 Feb, 2020, 10:51:29 AM
    Author     : abt
--%>


<%@page import="sampada.pojo.MappingInterestBank"%>
<%@page import="sampada.pojo.MappingRefundBank"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.lang.Math"%>
<%@page import="sampada.pojo.MappingBillBank"%>
<%@page import="java.util.Date"%>
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
        <link type="text/css" href="https://cdn.datatables.net/select/1.3.1/css/select.dataTables.min.css" rel="stylesheet" />
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
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
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/fixedcolumns/3.2.1/js/dataTables.fixedColumns.min.js"></script>

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
                    select: {
                      style: 'single'  
                    },
                    scrollY:        "450px",
                    scrollX:        true,
                    scrollCollapse: true,
                    paging:         true,
                    fixedColumns:   {
                        leftColumns: 1
                    },
                    "pageLength": 25,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            title: 'Mapping report'
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            title: 'Mapping report'
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
                                .column(14)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(14, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(14).footer()).html(
                                pageTotal.toFixed(2)
                                );
                        total = api
                                .column(15)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(15, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(15).footer()).html(
                                pageTotal.toFixed(2)
                                );
                        
                        total = api
                                .column(21)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(21, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(21).footer()).html(
                                pageTotal.toFixed(2)
                                );
                    }
                });
                
                var itr = 0;
                
                $("#payableTable tbody tr").on('click',function(event) {
                    if(itr==0) {
                        $("#payableTable tbody tr").removeClass('row_selected');		
                        $(this).addClass('row_selected');
                        itr = 1;
                    }
                    else {
                        $("#payableTable tbody tr").removeClass('row_selected');
                        $(this).addClass('row_selected');
                        itr = 0;
                    }
                });
                
                table.columns().iterator('column', function (ctx, idx) {
                    $(table.column(idx).header()).append('<span class="sort-icon"/>');
                });

//                $('#payableTable tbody')
//                        .on('mouseenter', 'td', function () {
//                            var colIdx = table.cell(this).index().column;
//
//                            $(table.cells().nodes()).removeClass('highlight');
//                            $(table.column(colIdx).nodes()).addClass('highlight');
//                        });

                var table = $('#payableTable').DataTable();
                //$('#search-date').datepicker({"dateFormat":"yy/mm/dd"});

                $('#search-Pool_Member_Name').on('change', function () {

                    table
                            .column(0)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bill_Year').on('change', function () {

                    table
                            .column(1)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bill_type').on('change', function () {

                    table
                            .column(2)
                            .search(this.value)
                            .draw();
                });
                $('#search-WEEK_ID').on('change', function () {

                    table
                            .column(3)
                            .search(this.value)
                            .draw();
                });
                $('#search-Rev_No').on('change', function () {

                    table
                            .column(4)
                            .search(this.value)
                            .draw();
                });
                $('#search-Total_Net').on('change', function () {

                    table
                            .column(5)
                            .search(this.value)
                            .draw();
                });
                $('#search-difference').on('change', function () {

                    table
                            .column(6)
                            .search(this.value)
                            .draw();
                });
                $('#search-Mapped').on('change', function () {

                    table
                            .column(7)
                            .search(this.value)
                            .draw();
                });
                $('#search-Pending').on('change', function () {

                    table
                            .column(8)
                            .search(this.value)
                            .draw();
                });
                $('#search-Mapping_Remarks').on('change', function () {

                    table
                            .column(9)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bank_Id').on('change', function () {

                    table
                            .column(10)
                            .search(this.value)
                            .draw();
                });
                $('#search-Id_From_Date').on('change', function () {

                    table
                            .column(11)
                            .search(this.value)
                            .draw();
                });
                $('#search-Id_To_Date').on('change', function () {

                    table
                            .column(12)
                            .search(this.value)
                            .draw();
                });
                $('#search-Amount_Date').on('change', function () {

                    table
                            .column(13)
                            .search(this.value)
                            .draw();
                });
                $('#search-CR_Amount').on('change', function () {

                    table
                            .column(14)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bank_Balance').on('change', function () {

                    table
                            .column(15)
                            .search(this.value)
                            .draw();
                });
                $('#search-Desc').on('change', function () {

                    table
                            .column(16)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bank_Remarks').on('change', function () {

                    table
                            .column(17)
                            .search(this.value)
                            .draw();
                });
                $('#search-Category').on('change', function () {

                    table
                            .column(18)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bill_Date').on('change', function () {

                    table
                            .column(19)
                            .search(this.value)
                            .draw();
                });
                $('#search-Bill_Due_Date').on('change', function () {

                    table
                            .column(20)
                            .search(this.value)
                            .draw();
                });
                $('#search-Interest').on('change', function () {

                    table
                            .column(21)
                            .search(this.value)
                            .draw();
                });
            });
        </script>
    <style>
        th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: 100%;
            margin: 0 auto;
        }
        
        tr.row_selected td{background-color: cyan }
    </style>
    </head>
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" >
        <!--<h3 align="center" style="color:#003366;">From Week-id ${week_id1} to Week-id ${week_id2} of year ${yeari}</h3>-->
        <!--<table id="payableTable" align="center" style="min-height:400px;width:80%;" class="table table-striped table-bordered">-->
        <form>
            <fieldset>
                <legend> <h3>Mapping Details</h3></legend>
                
                <div style="overflow-x:auto; white-space: nowrap; max-width: 1500px">
                <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>
                            <td></td>
                            <td><input type="text" id="search-Bill_Year" placeholder="search Bill_Year"></td>
                            <td><input type="text" id="search-Bill_type" placeholder="search Bill_type"></td>
                            <td><input type="text" id="search-WEEK_ID" placeholder="search WEEK_ID"></td>
                            <td><input type="text" id="search-Rev_No" placeholder="search Rev_No"></td>                            
                            <td><input type="text" id="search-Total_Net" placeholder="search Bill_amount"></td>
                            <td><input type="text" id="search-difference" placeholder="search Difference"></td>
                            <td><input type="text" id="search-Mapped" placeholder="search Mapped"></td>
                            <td><input type="text" id="search-Pending" placeholder="search Pending"></td>
                            <td><input type="text" id="search-Mapping_Remarks" placeholder="search Mapping_Remarks"></td>
                            <td><input type="text" id="search-Bank_Id" placeholder="search Bank_Id"></td>
                            <td><input type="text" id="search-Id_From_Date" placeholder="search Id_From_Date"></td>
                            <td><input type="text" id="search-Id_To_Date" placeholder="search Id_To_Date"></td>
                            <td><input type="text" id="search-Amount_Date" placeholder="search CR_Date"></td>
                            <td><input type="text" id="search-CR_Amount" placeholder="search CR_Amount"></td>
                            <td><input type="text" id="search-Bank_Balance" placeholder="search Bank_Balance"></td>
                            <td><input type="text" id="search-Desc" placeholder="search Desc"></td>
                            <td><input type="text" id="search-Bank_Remarks" placeholder="search Bank_Remarks"></td>
                            <td><input type="text" id="search-Category" placeholder="search Category"></td>
                            <td><input type="text" id="search-Bill_Date" placeholder="search Bill_Date"></td>
                            <td><input type="text" id="search-Bill_Due_Date" placeholder="search Bill_Due_Date"></td>
                            <td><input type="text" id="search-Interest" placeholder="search Interest"></td>

                        </tr>
                        <tr>
                            <th>Pool Member Name</th>
                            <th>Bill Year</th>
                            <th>Bill type</th>
                            <th>WEEK_ID </th>
                            <th>Rev_No</th>
                            <th>Bill Amount</th>
                            <th>Difference wrt Revision</th>
                            <th>Mapped</th>
                            <th>Pending</th>
                            <th>Mapping Remarks</th>
                            <th>Bank Id</th>
                            <th>Id From Date</th>
                            <th>Id To Date</th>
                            <th>CR Date</th>
                            <th>CR Amount</th>
                            <th>Bank Balance</th>
                            <th>Desc</th>
                            <th>Bank Remarks</th>
                            <th>Category</th>
                            <th>Bill Date</th>
                            <th>Bill Due Date</th>
                            <th>Interest</th>

                        </tr>

                    </thead>
                    <tbody>


                        <c:forEach items="${mapbillist}" var="bill">
                            <tr>
                                <td>${bill.corporates.corporateName}</td>
                                <td>${bill.billPayableCorp.billYear}</td>
                                <td>${bill.billType}</td>
                                <td>${bill.weekId}</td>
                                <td>${bill.billPayableCorp.revisionNo}</td>
                                <td>${bill.billPayableCorp.totalnet}</td>
                                <td>${bill.billPayableCorp.revisedpaybale}</td>
                                <td>${bill.mappedAmount}</td></td>
                                <td>${bill.pendingAmount}</td>
                                <td>${bill.remarks}</td>
                                <td>${bill.bankStatement.stmtId}</td>
                                <td>${bill.bankStatement.stmtFromdate}</td>
                                <td>${bill.bankStatement.stmtTodate}</td>
                                <td>${bill.bankStatement.amountDate}</td>

                                <td>${bill.bankStatement.paidAmount}</td>
                                <td>${bill.pendingBankAmount}</td>
                                <td>${bill.bankStatement.entryDesc}</td>
                                <td>${bill.bankStatement.remarks}</td>
                                <td>Bill Mapping</td>
                                <%
                                    MappingBillBank obj = (MappingBillBank) pageContext.getAttribute("bill");
                                    
                                    long no_of_days = 0;
                                    BigDecimal interest = BigDecimal.ZERO;
                                    long diffinMillis;
                                    Date due_date = obj.getBillPayableCorp().getBillDueDate();
                                    Date cr_date = obj.getBankStatement().getAmountDate();
                                    
                                    if(due_date.compareTo(cr_date)<0){
                                        diffinMillis = Math.abs(cr_date.getTime() - due_date.getTime());
                                        no_of_days = TimeUnit.DAYS.convert(diffinMillis, TimeUnit.MILLISECONDS);   
                                        interest = new BigDecimal(no_of_days).multiply(obj.getMappedAmount()).multiply(new BigDecimal(0.0004));
                                        interest = interest.setScale(2,BigDecimal.ROUND_HALF_EVEN);
                                    }
                                    
                                    %>
                                    <td>${bill.billPayableCorp.billingDate}</td>
                                    <td><%=due_date%></td>
                                    <td><%=interest%></td>

                            </tr>
                        </c:forEach>

                        <c:forEach items="${mapreflist}" var="ref">
                            <tr>
                                <td>${ref.tempRefundBillCorp.corporates.corporateName}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.billYear}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.billType}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.weekId}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.revisionNo}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.toalnet}</td>
                                <td>${ref.tempRefundBillCorp.billReceiveCorp.revisedrefund}</td>
                                <td>${ref.mappedAmount}</td></td>
                                <td>${ref.pendingAmount}</td>
                                <td>${ref.remarks}</td>
                                <td>${ref.bankStatement.stmtId}</td>
                                <td>${ref.bankStatement.stmtFromdate}</td>
                                <td>${ref.bankStatement.stmtTodate}</td>
                                <td>${ref.bankStatement.amountDate}</td>

                                <td>${ref.bankStatement.paidAmount}</td>
                                <td>${ref.bankPendingAmount}</td>
                                <td>${ref.bankStatement.entryDesc}</td>
                                <td>${ref.bankStatement.remarks}</td>
                                <td>Refund Mapping</td>
                                <%
                                    MappingRefundBank obj2 = (MappingRefundBank) pageContext.getAttribute("ref");
                                    
                                    long no_of_days2 = 0;
                                    BigDecimal interest2 = BigDecimal.ZERO;
                                    long diffinMillis2;
                                    Date due_date2 = obj2.getTempRefundBillCorp().getBillReceiveCorp().getBillDueDate();
                                    Date cr_date2 = obj2.getBankStatement().getAmountDate();
                                    
                                    if(due_date2.compareTo(cr_date2)<0){
                                        diffinMillis2 = Math.abs(cr_date2.getTime() - due_date2.getTime());
                                        no_of_days2 = TimeUnit.DAYS.convert(diffinMillis2, TimeUnit.MILLISECONDS);   
                                        interest2 = new BigDecimal(no_of_days2).multiply(obj2.getMappedAmount()).multiply(new BigDecimal(0.0004));
                                        interest2 = interest2.setScale(2,BigDecimal.ROUND_HALF_EVEN);
                                    }
                                    
                                    %>
                                    <td>${ref.tempRefundBillCorp.billReceiveCorp.billingDate}</td>
                                    <td><%=due_date2%></td>
                                    <td><%=interest2%></td>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${mapintlist}" var="int">
                            <tr>
                                <td>${int.interestDetails.corporates.corporateName}</td>
                                <td>${int.interestDetails.billYear}</td>
                                <td>${int.interestDetails.billType}</td>
                                <td>${int.interestDetails.weekId}</td>
                                <td>${int.interestDetails.revisionNo}</td>
                                <td>${int.interestDetails.interestAmount}</td>
                                <td>0</td>
                                <td>${int.mappedAmount}</td></td>
                                <td>${int.pendingAmount}</td>
                                <td>${int.remarks}</td>
                                <td>${int.bankStatement.stmtId}</td>
                                <td>${int.bankStatement.stmtFromdate}</td>
                                <td>${int.bankStatement.stmtTodate}</td>
                                <td>${int.bankStatement.amountDate}</td>

                                <td>${int.bankStatement.paidAmount}</td>
                                <td>${int.bankPendingAmount}</td>
                                <td>${int.bankStatement.entryDesc}</td>
                                <td>${int.bankStatement.remarks}</td>
                                <td>Interest Mapping</td>
                                <td>${int.interestDetails.billingDate}</td>
                                <td>${int.interestDetails.billingDuedate}</td>
                                <td>0</td>
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
                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th style="text-align:right"></th>

                        </tr>
                    </tfoot>
                </table>
                </div>

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

