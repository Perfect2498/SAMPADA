<%--

    Document   : viewPendingRefundReceivableList

    Created on : Dec 11, 2019, 2:23:13 PM

    Author     : JaganMohan

--%>









<%@page import="java.time.LocalDate"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>



<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar" %> 


<html>

    <head>

        <meta name="viewport" content="width=device-width, initial-scale=1">

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





                var table = $('#interestChecker').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Pending Payable List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Pending Payable List';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Pending Payable List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Pending Payable List';
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
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
                                pageTotal
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

    <body style="text-align: center; alignment-adjust: central;width: 95%;">





        <form name="pendinterestverify" >



            <h3 align="center" style="color:#003366;">View  Pending Refund by RLDC</h3>





            <table align="center" id="interestChecker" style="width:90%;" class="customerTable">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Billing Date</th>

                        <th>Due Date for Interest</th>
                        <th>Week</th>
                        <th>Bill Type </th>
                        <th>Rev. NO </th>

                        <th>Pool Member</th>

                        <th>Total Amount</th>

                        <th>Refund Amount</th>
                        <th>Paid Amount</th>

                        <th>Pending Amount</th>


                        <%
                            Date date2 = new Date();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date2);
                            cal.add(Calendar.DATE, -1);
                            Date dateBefore30Days = cal.getTime();
                        %>
                        <fmt:formatDate value="<%=dateBefore30Days%>" pattern="dd-MM-yyyy" var="dated_1" />
                        <th>Pro.Interest of ${dated_1}</th>
                            <% Date date1 = new Date();
                            %>
                            <fmt:formatDate value="<%=date1%>" pattern="dd-MM-yyyy" var="date1" />                        
                        <th>Pro.Interest of ${date1} </th>
                        <th>SUB ACCOUNT NUMBER</th>
                    </tr>

                </thead>

                <tbody>

                    <c:forEach items="${pendRecvRefundList}" var="ele">

                        <tr>

                            <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" var="billingDate" />

                            <td>${billingDate}</td>

                            <td>${ele.billDueDate}</td>

                            <td>${ele.weekId}</td>

                            <td>${ele.billType}</td>
                            <td>${ele.revisionNo}</td>

                            <td>${ele.corporates.corporateName}</td>

                            <c:set var="minus1" scope="application" value="${-1}"/>

                            <td>${minus1*ele.toalnet}</td>
                            
                            <td>${ele.revisedrefund}</td>

                            <td>${ele.adjustmentAmount}</td>

                            <td>${ele.pendingAmount}</td>

                            <c:set var="duedate" value="${ele.billDueDate}"/>
                            <c:set var="pendamt" value="${ele.pendingAmount}"/>
                            <%
                                BigDecimal pen = (BigDecimal) pageContext.getAttribute("pendamt");
                                Date duedate = (Date) pageContext.getAttribute("duedate");
                                Date date = new Date();
                                int differenceInDays = (int) ((date.getTime()-duedate.getTime()) / (1000 * 60 * 60 * 24));
                                BigDecimal proint = pen.multiply(new BigDecimal(0.0004)).multiply(new BigDecimal(differenceInDays));
                                BigDecimal prointd1 = pen.multiply(new BigDecimal(0.0004)).multiply(new BigDecimal(differenceInDays - 1));
                                if (proint.compareTo(BigDecimal.ZERO) < 0) {
                                    proint = new BigDecimal(0);
                                }
                                if (prointd1.compareTo(BigDecimal.ZERO) < 0) {
                                    prointd1 = new BigDecimal(0);
                                }


                            %>

                            <c:set var="prointd1" value="<%=prointd1%>"/>
                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${prointd1}" var="prointd12"/>
                            <td>${prointd12}</td>
                            <c:set var="proint" value="<%=proint%>"/>
                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${proint}" var="proint1"/>
                            <td>${proint1}</td>


                            <td>${ele.corporates.subAccountNumber}</td>




                        </tr>

                    </c:forEach>

                </tbody>
                <tfoot>
                    <tr>
                        <th style="text-align:right">Total:</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th>&nbsp;</th>
                    </tr>
                </tfoot>
            </table>


            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>




        </form>
        <br/>



        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>

        <br/>

    </body>



</html>