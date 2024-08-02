<%-- 
    Document   : overalldisbursementviewnew
    Created on : 21 Feb, 2020, 7:17:17 PM
    Author     : abt
--%>






<%@page import="sampada.pojo.PaymentInterestDisbursement"%>
<%@page import="sampada.pojo.CsdfDetails"%>
<%@page import="sampada.pojo.TempRefundBillCorp"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="sampada.pojo.BankStatement"%>
<%@page import="sampada.DAO.BankStatementDAO"%>
<%@page import="java.util.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="sampada.pojo.PaymentDisbursement"%>
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
                        leftColumns: 3
                    },                   
                    "pageLength": 25,
                    "order": [[13, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            title: 'Disbursement Report'
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            title: 'Disbursement Report'
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
                                .column(18)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(18, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(18).footer()).html(
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
                $('#search-Total_Net').on('change', function () {

                    table
                            .column(7)
                            .search(this.value)
                            .draw();
                });
                $('#search-difference').on('change', function () {

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
                $('#search-Process_Flow').on('change', function () {

                    table
                            .column(15)
                            .search(this.value)
                            .draw();
                });
                $('#search-Actual_date').on('change', function () {

                    table
                            .column(16)
                            .search(this.value)
                            .draw();
                });
                $('#search-Due_date').on('change', function () {

                    table
                            .column(17)
                            .search(this.value)
                            .draw();
                });
                $('#search-Interest').on('change', function () {

                    table
                            .column(18)
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
                <legend> <h3>Disbursed Details</h3></legend>

                <div style="overflow-x:auto; white-space: nowrap; max-width: 1500px">
                <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>
                            <td><input type="text" id="search-Disburse_var" placeholder="search Disburse_var" style="width: 90px; visibility: hidden"></td>
                            <td><input type="text" id="search-Disburse_Id" placeholder="search Disburse_Id" style="width: 90px; visibility: hidden"></td>
                            <td><input type="text" id="search-Pool_Member" placeholder="search Pool_Member" style="visibility: hidden"></td>
                            <td><input type="text" id="search-Bill_Year" placeholder="search Bill_Year"></td>
                            <td><input type="text" id="search-Bill_type" placeholder="search Bill_type"></td>
                            <td><input type="text" id="search-WEEK_ID" placeholder="search WEEK_ID"></td>
                            <td><input type="text" id="search-Rev_No" placeholder="search Rev_No"></td>
                            <td><input type="text" id="search-Total_Net" placeholder="search Bill_amount"></td>
                            <td><input type="text" id="search-difference" placeholder="search Difference"></td>
                            <td><input type="text" id="search-Already_Released" placeholder="search Already_Released"></td>
                            <td><input type="text" id="search-Disbursed" placeholder="search Disbursed"></td>
                            <td><input type="text" id="search-Pending" placeholder="search Pending"></td>
                            <td><input type="text" id="search-Disburse_date" placeholder="search Disburse_date"></td>
                            <td><input type="text" id="search-Remarks" placeholder="search Remarks"></td>
                            <td><input type="text" id="search-Pool_Bal" placeholder="search Pool_Bal"></td>
                            <td><input type="text" id="search-Process_Flow" placeholder="search Process_Flow"></td>
                            <td><input type="text" id="search-Actual_date" placeholder="search Actual Disburse_date"></td>
                            <td><input type="text" id="search-Due_date" placeholder="search Disburse Due_date"></td>
                            <td><input type="text" id="search-Interest" placeholder="search Interest"></td>
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
                            <th>Difference wrt Revision</th>
                            <th>Already Released</th>
                            <th>Software Disbursed</th>
                            <th>Pending</th>
                            <th>Software Disburse date</th>
                            <th>Disburse Remarks</th>
                            <th>Pool Bal</th>
                            <th>Process Flow</th>
                            <th>Actual Disburse date</th>
                            <th>Disburse Due date</th>
                            <th>Interest</th>
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
                                <td>${bill.billReceiveCorp.revisedpaybale}</td>
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
                                <td>${bill.poolBal}</td>
                                <td>${bill.entryTime}</td>
                                <%
                                    PaymentDisbursement obj = (PaymentDisbursement) pageContext.getAttribute("bill");
                                    
                                    long no_of_days = 0;
                                    BigDecimal interest = BigDecimal.ZERO;
                                    long diffinMillis;
                                    Date cr_date = null;
                                    Date due_date = obj.getBillReceiveCorp().getBillDueDate();
                                    BigDecimal dis_id = obj.getDisburseId();
                                    BankStatementDAO bkdao = new BankStatementDAO();
                                    BankStatement bkobj = bkdao.getBankStatementbyDisburseId(dis_id,"Bills");
                                    
                                    if(bkobj!=null) {
                                        cr_date = bkobj.getAmountDate();
                                        
                                        if(due_date.compareTo(cr_date)<0){
                                            diffinMillis = Math.abs(cr_date.getTime() - due_date.getTime());
                                            no_of_days = TimeUnit.DAYS.convert(diffinMillis, TimeUnit.MILLISECONDS);   
                                            interest = new BigDecimal(no_of_days).multiply(obj.getDisburseAmount()).multiply(new BigDecimal(0.0004));
                                            interest = interest.setScale(2,BigDecimal.ROUND_HALF_EVEN);
                                        }
                                    }
                                    %>
                                    <td><%=cr_date%></td>
                                    <td><%=due_date%></td>
                                    <td><%=interest%></td>

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
                                <td>${ref.poolBal}</td>
                                <td>${ref.entryTime}</td>
                                <%
                                    TempRefundBillCorp obj2 = (TempRefundBillCorp) pageContext.getAttribute("ref");
                                    
                                    long no_of_days2 = 0;
                                    BigDecimal interest2 = BigDecimal.ZERO;
                                    long diffinMillis2;
                                    Date cr_date2 = null;
                                    Date due_date2 = obj2.getBillPayableCorp().getBillDueDate();
                                    BigDecimal dis_id2 = obj2.getSlno();
                                    BankStatementDAO bkdao2 = new BankStatementDAO();
                                    BankStatement bkobj2 = bkdao2.getBankStatementbyDisburseId(dis_id2,"Refund");
                                    
                                    if(bkobj2!=null) {
                                        cr_date2 = bkobj2.getAmountDate();

                                        if(due_date2.compareTo(cr_date2)<0){
                                            diffinMillis2 = Math.abs(cr_date2.getTime() - due_date2.getTime());
                                            no_of_days2 = TimeUnit.DAYS.convert(diffinMillis2, TimeUnit.MILLISECONDS);   
                                            interest2 = new BigDecimal(no_of_days2).multiply(obj2.getPaidAmount()).multiply(new BigDecimal(0.0004));
                                            interest2 = interest2.setScale(2,BigDecimal.ROUND_HALF_EVEN);
                                        }
                                    }
                                    
                                    %>
                                    <td><%=cr_date2%></td>
                                    <td><%=due_date2%></td>
                                    <td><%=interest2%></td>


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
                                <td>0</td>
                                <td>${int.disbursedInterestDetails.interestAmount-(int.interestPaidamount+int.interestPendingamount)}</td>
                                <td>${int.interestPaidamount}</td>
                                <td>${int.interestPendingamount}</td>
                                <td>${int.entryDate}</td>
                                <td>${int.remarks}</td>
                                <td>${int.intPoolBal}</td>
                                <td>${int.entryTime}</td>
                                <%
                                    PaymentInterestDisbursement obj3 = (PaymentInterestDisbursement) pageContext.getAttribute("int");
                                    
                                    BigDecimal dis_id3 = obj3.getSlno();
                                    Date cr_date3 = null;
                                    BankStatementDAO bkdao3 = new BankStatementDAO();
                                    BankStatement bkobj3 = bkdao3.getBankStatementbyDisburseId(dis_id3,"Interest");
                                    
                                    if(bkobj3!=null)
                                        cr_date3 = bkobj3.getAmountDate();
                                %>
                                <td><%=cr_date3%></td>
                                <td>${int.disbursedInterestDetails.billingDuedate}</td>
                                <td>0</td>

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
                                <td>${psdf.csdfAmount}</td>
                                <td></td>
                                <td></td>
                                <td>${psdf.entryDate}</td>
                                <td>${psdf.remarks}</td>
                                <td>${psdf.mainPoolBalance-psdf.csdfAmount}</td>
                                <td>${psdf.entryTime}</td>
                                <td></td>
                                <td></td>
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

                            
                            <th></th>
                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>
                            <th style="text-align:right">Total:</th>
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







