<%--

    Document   : viewPendingInterestReceivableList

    Created on : Dec 11, 2019, 1:45:13 PM

    Author     : JaganMohan

--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



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
                       
                    }

                });
                 table.columns().iterator('column', function (ctx, idx) {
                    $(table.column(idx).header()).append('<span class="sort-icon"/>');
                });

                $('#interestChecker tbody')

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

            <h3 align="center" style="color:#003366;">Interest Pending for Disbursal </h3>

            <table align="center" id="interestChecker" style="width:90%;" class="customerTable">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Week ID</th>

                        <th>Pool Member Name</th>

                        <th>Bill Type</th>
                        
                        <th>Revision</th>

                        <th>Billed Amount</th>

                        <th>Billing Due date</th>

                        <th>Bank Paid date</th>

                        <th>No of Days</th>

                        <th>Interest Billed Amount</th>

                        <th>Interest Amount</th>

                        <th>Interest Pending</th>

                    </tr>

                </thead>

                <tbody>

                    <c:forEach items="${pendRecvInterestList}" var="ele">

                        <tr>

                            <td>${ele.weekId}</td>

                            <td>${ele.corporates.corporateName}</td>

                            <td>${ele.billType}</td>
                            
                            <td>${ele.revisionNo}</td>

                            <td>${ele.billedAmount}</td>


                            <fmt:formatDate value="${ele.billingDuedate}" pattern="dd-MM-yyyy" var="billingDuedate" />

                            <td>${billingDuedate}</td>


                            <fmt:formatDate value="${ele.disbursedDate}" pattern="dd-MM-yyyy" var="disbursedDate" />

                            <td>${disbursedDate}</td>


                            <td>${ele.noofdays}</td>

                            <td>${ele.interestBilledamount}</td>

                            <td>${ele.interestAmount}</td>

                            <td>${ele.interestPendingamount}</td>

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
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        
                    </tr>
                </tfoot>
            </table>

            <br/>

            

            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>

            <br/>





        </form>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>

    </body>

 <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>

</html>