<%-- 
    Document   : psdfdetails
    Created on : 9 Mar, 2020, 5:33:52 PM
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
                    "pageLength": 25,
                    "order": [[10, "asc"]],
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
                $('#search-Bill_Month').on('change', function () {

                    table
                            .column(5)
                            .search(this.value)
                            .draw();
                });
//                $('#search-Rev_No').on('change', function () {
//
//                    table
//                            .column(6)
//                            .search(this.value)
//                            .draw();
//                });
//                $('#search-Total_Net').on('change', function () {
//
//                    table
//                            .column(7)
//                            .search(this.value)
//                            .draw();
//                });
                $('#search-Disbursed').on('change', function () {

                    table
                            .column(6)
                            .search(this.value)
                            .draw();
                });
//                $('#search-Pending').on('change', function () {
//
//                    table
//                            .column(9)
//                            .search(this.value)
//                            .draw();
//                });
                $('#search-Disburse_date').on('change', function () {

                    table
                            .column(7)
                            .search(this.value)
                            .draw();
                });
                $('#search-Remarks').on('change', function () {

                    table
                            .column(8)
                            .search(this.value)
                            .draw();
                });
                $('#search-Pool_Bal').on('change', function () {

                    table
                            .column(9)
                            .search(this.value)
                            .draw();
                });
                $('#search-Process_Flow').on('change', function () {

                    table
                            .column(10)
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
            <fieldset>
                <legend> <h3>PSDF Details</h3></legend>


                <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>
                            <td><input type="text" id="search-Disburse_var" placeholder="search Disburse_var"></td>
                            <td><input type="text" id="search-Disburse_Id" placeholder="search Disburse_Id"></td>
                            <td><input type="text" id="search-Pool_Member" placeholder="search Pool_Member"></td>
                            <td><input type="text" id="search-Bill_Year" placeholder="search Bill_Year"></td>
                            <td><input type="text" id="search-Bill_type" placeholder="search Bill_type"></td>
                            <td><input type="text" id="search-WEEK_ID" placeholder="search Bill_Month"></td>
<!--                            <td><input type="text" id="search-Rev_No" placeholder="search Rev_No"></td>
                            <td><input type="text" id="search-Total_Net" placeholder="search Total_Net"></td>-->
                            <td><input type="text" id="search-Disbursed" placeholder="search Disbursed"></td>
                            <!--<td><input type="text" id="search-Pending" placeholder="search Pending"></td>-->
                            <td><input type="text" id="search-Disburse_date" placeholder="search Disburse_date"></td>
                            <td><input type="text" id="search-Remarks" placeholder="search Remarks"></td>
                            <td><input type="text" id="search-Pool_Bal" placeholder="search Pool_Bal"></td>
                            <td><input type="text" id="search-Process_Flow" placeholder="search Process_Flow"></td>



                        </tr>

                        <tr>
                            <th>Disburse var</th>
                            <th>Disburse Id</th>
                            <th>Pool Member Name</th>
                            <th>Bill Year</th>
                            <th>Bill type</th>
                            <th>Bill Month</th>
                            
                            <th>Software Disbursed</th>
                            
                            <th>Software Disburse date</th>
                            <th>Disburse Remarks</th>
                            <th>Pool Bal</th>
                            <th>Process Flow</th>


                        </tr>

                    </thead>
                    <tbody>


                        
                        <c:forEach items="${psdfdetlist}" var="psdf">
                            <tr>
                                <td>${"P"}</td>
                                <td>${psdf.slno}</td>
                                <td>${"PSDF"}</td>
                                <td>${psdf.csdfYear}</td>
                                <td>${psdf.amtCategory}</td>
                                <td>${psdf.csdfMonth}</td>
                                
                                <td>${psdf.csdfAmount}</td>
                                
                                <td>${psdf.entryDate}</td>
                                <td>${psdf.remarks}</td>
                                <td>${psdf.mainPoolBalance-psdf.csdfAmount}</td>
                                <td>${psdf.entryTime}</td>


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
                            <th></th>
                            <th></th>
                            <th style="text-align:right">Total:</th>
                            
                            
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







