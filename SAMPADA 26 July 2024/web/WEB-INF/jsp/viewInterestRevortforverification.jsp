<%-- 
    Document   : viewInterestRevortforverification
    Created on : 11 Mar, 2020, 1:09:36 PM
    Author     : abt
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
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'collection',
                            text: 'Export',
                            buttons: [
                                'excel',
                                'pdf'
                            ]
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



                var table1 = $('#interestChecker1').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "order": [[0, "asc"]],
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'collection',
                            text: 'Export',
                            buttons: [
                                'excel',
                                'pdf'
                            ]
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


            });


            function validate()
            {
                if (confirm('Are you sure to Revert for Interest Mapping Verification ??'))
                {
                    return true;
                }
                else
                {
                    return false;
                }
                return true;
            }


            function validate1()
            {
                if (confirm('Are you sure to Revert for Interest Disbursement Verification ??'))
                {
                    return true;
                }
                else
                {
                    return false;
                }
                return true;
            }


        </script>


        <style>
            input[type="checkbox"]{
                width: 30px; /*Desired width*/
                height: 30px; /*Desired height*/
            }
        </style>





    </head>
    <body style="text-align: center; alignment-adjust: central;width: 95%;">


        <!--<h6 align="center">Note: Please close the excel sheet of Interest Mapping & DIsbursement files</h6>-->

        <form name="interestverify" method="post" action="submitPayInterestdetailforRevert.htm">
            <h3 align="center" style="color:#003366;">View UNCHECKED Interest for Paid Amount  by Pool Member</h3>
            <table align="center" id="interestChecker" style="width:90%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Select</th>
                        <th>Interest ID</th>
                        <th>Week ID</th>
                        <th>Pool Member Name</th>
                        <th>Revision No.</th>
                        <th>Bill Type</th>
                        <th>Billed Amount</th>
                        <th>Billing Due date</th>
                        <th>Bank Paid date</th>
                        <th>No. of Days</th>
                        <th>Interest Billed Amount (Rs.)</th>
                        <th>Interest Amount (Rs.)</th>
                        <th>Unchecked Remarks</th>                        
                    </tr>
                </thead>
                <tbody>
                    <%
                        int cnt = 0;
                        String week = "week";
                        String week1 = null;
                        String poolname = "poolname";
                        String poolname1 = null;
                        String rev = "rev";
                        String rev1 = null;
                        String billtype = "billtype";
                        String billtype1 = null;
                        String billedamt = "billedamt";
                        String billedamt1 = null;
                        String billeduedate = "billeduedate";
                        String billeduedate1 = null;
                        String bankpaiddate = "bankpaiddate";
                        String bankpaiddate1 = null;
                        String noofdays = "noofdays";
                        String noofdays1 = null;
                        String interestbillamt = "interestbillamt";
                        String interestbillamt1 = null;
                        String interestamt = "interestamt";
                        String interestamt1 = null;
                        String uniqueID = "uniqueID";
                        String uniqueID1 = null;
                        String remarks = "remarks";
                        String remarks1 = null;
                    %>
                    <c:forEach items="${revinterestList}" var="ele">
                        <%                           cnt = cnt + 1;
                            week1 = week + cnt;
                            poolname1 = poolname + cnt;
                            rev1 = rev + cnt;
                            billtype1 = billtype + cnt;
                            billedamt1 = billedamt + cnt;
                            billeduedate1 = billeduedate + cnt;
                            bankpaiddate1 = bankpaiddate + cnt;
                            noofdays1 = noofdays + cnt;
                            interestbillamt1 = interestbillamt + cnt;
                            interestamt1 = interestamt + cnt;
                            uniqueID1 = uniqueID + cnt;
                            remarks1 = remarks + cnt;
                        %>
                        <tr>
                            <td><input type="checkbox" checked="true" id="items"  name="items" value="${ele.slno}" />${ele.slno}</td>
                    <input type="text" name="<%=uniqueID1%>" value="${ele.slno}" hidden="yes"/> 
                    <td><input type="text" name="interestid" value="${ele.tempInterestDetails.interestId}" hidden="yes"/>${"I"}${ele.tempInterestDetails.interestId}</td>
                    <td><input type="text" name="<%=week1%>" value="${ele.tempInterestDetails.weekId}" hidden="yes"/> ${ele.tempInterestDetails.weekId}</td>
                    <td><input type="text" name="<%=poolname1%>" value="${ele.tempInterestDetails.corporates.corporateName}" hidden="yes"/>${ele.tempInterestDetails.corporates.corporateName}</td>
                    <td><input type="text" name="<%=rev1%>" value="${ele.tempInterestDetails.revisionNo}" hidden="yes"/>${ele.tempInterestDetails.revisionNo}</td>
                    <td><input type="text" name="<%=billtype1%>" value="${ele.tempInterestDetails.billType}" hidden="yes"/>${ele.tempInterestDetails.billType}</td>
                    <input type="text" name="<%=billedamt1%>" value="${ele.tempInterestDetails.billedAmount}" hidden="yes"/><td>${ele.tempInterestDetails.billedAmount}</td>
                        <fmt:formatDate value="${ele.tempInterestDetails.billingDuedate}" pattern="dd-MM-yyyy" var="billingDuedate" />
                    <td><input type="text" name="<%=billeduedate1%>" value="${billingDuedate}" hidden="yes"/>${billingDuedate}</td>
                        <fmt:formatDate value="${ele.tempInterestDetails.paidDate}" pattern="dd-MM-yyyy" var="paidDate" />
                    <td><input type="text" name="<%=bankpaiddate1%>" value="${paidDate}" hidden="yes"/>${paidDate}</td>
                    <td><input type="text" name="<%=noofdays1%>" value="${ele.tempInterestDetails.noofdays}" hidden="yes"/>${ele.tempInterestDetails.noofdays}</td>
                    <input type="text" name="<%=interestbillamt1%>" value="${ele.tempInterestDetails.interestBilledamount}" hidden="yes"/> <td>${ele.tempInterestDetails.interestBilledamount}</td>
                    <input type="text" name="<%=interestamt1%>" value="${ele.tempInterestDetails.interestAmount}" hidden="yes"/><td>${ele.tempInterestDetails.interestAmount}</td>
                    <td>${ele.remarks}</td>
                    </tr>
                </c:forEach>

                </tbody>
                <tfoot>
                    <tr>
                        <td>Total(Rs.)</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>&nbsp;</td>
                        <!--<td>&nbsp;</td>-->
                    </tr>
                </tfoot>
            </table>
            <br/>
            <p><input type="text" name="rowcount" value="<%=cnt%>" hidden="yes"/></p>
            <p><input class="btn" type="submit" name="payConfirm" value="Send Back" onclick="return validate();" /></p>
            <br/>
        </form>
        <form name="recvinterestverify" method="post" action="submitRecvInterestdetailforRevert.htm">
            <h3 align="center" style="color:#003366;">View UNCHECKED Interest for Disbursed Amount to Pool Member</h3>




            <table align="center" id="interestChecker1" style="width:90%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Select</th>
                        <th>Interest ID</th>
                        <th>Week ID</th>
                        <th>Pool Member Name</th>
                        <th>Revision No.</th>
                        <th>Bill Type</th>
                        <th>Billed Amount</th>                      
                        <th>Billing Due date</th>
                        <th>Disbursed Date</th>
                        <th>No. of Days</th>
                        <th>Interest Billed Amount (Rs.)</th>
                        <th>Interest Amount (Rs.)</th>
                        <th>Unchecked Remarks</th>
                        
                    </tr>
                </thead>
                <tbody>

                    <%
                        int cnt1 = 0;
                        String week2 = "week";
                        String week21 = null;
                        String poolname2 = "poolname";
                        String poolname21 = null;
                        String rev2 = "rev";
                        String rev21 = null;
                        String billtype2 = "billtype";
                        String billtype21 = null;
                        String billedamt2 = "billedamt";
                        String billedamt21 = null;
                        String billeduedate2 = "billeduedate";
                        String billeduedate21 = null;
                        String bankpaiddate2 = "bankpaiddate";
                        String bankpaiddate21 = null;
                        String noofdays2 = "noofdays";
                        String noofdays21 = null;
                        String interestbillamt2 = "interestbillamt";
                        String interestbillamt21 = null;
                        String interestamt2 = "interestamt";
                        String interestamt21 = null;
                        String uniqueID2 = "uniqueID";
                        String uniqueID21 = null;
                        String remarks2 = "remarks";
                        String remarks21 = null;

                    %>


                    <c:forEach items="${revdisbursedList}" var="ele1">
                        <%                            cnt1 = cnt1 + 1;
                            week21 = week2 + cnt1;
                            poolname21 = poolname2 + cnt1;
                            rev21 = rev2 + cnt1;
                            billtype21 = billtype2 + cnt1;
                            billedamt21 = billedamt2 + cnt1;
                            billeduedate21 = billeduedate2 + cnt1;
                            bankpaiddate21 = bankpaiddate2 + cnt1;
                            noofdays21 = noofdays2 + cnt1;
                            interestbillamt21 = interestbillamt2 + cnt1;
                            interestamt21 = interestamt2 + cnt1;
                            uniqueID21 = uniqueID2 + cnt1;
                            remarks21 = remarks2 + cnt1;

                        %>

                        <tr>
                            <td><input type="checkbox" checked="true" id="items1"  name="items1" value="${ele1.slno}" />${ele1.slno}</td>
                    <input type="text" name="<%=uniqueID21%>" value="${ele1.slno}" hidden="yes"/> 
                    <td><input type="text" name="interestid" value="${ele1.tempDisbInterestDetails.interestId}" hidden="yes"/>${"I"}${ele1.tempDisbInterestDetails.interestId}</td>

                    <td><input type="text" name="<%=week21%>" value="${ele1.tempDisbInterestDetails.weekId}" hidden="yes"/> ${ele1.tempDisbInterestDetails.weekId}</td>
                    <td><input type="text" name="<%=poolname21%>" value="${ele1.tempDisbInterestDetails.corporates.corporateName}" hidden="yes"/>${ele1.tempDisbInterestDetails.corporates.corporateName}</td>
                    <td><input type="text" name="<%=rev21%>" value="${ele1.tempDisbInterestDetails.revisionNo}" hidden="yes"/>${ele1.tempDisbInterestDetails.revisionNo}</td>
                    <td><input type="text" name="<%=billtype21%>" value="${ele1.tempDisbInterestDetails.billType}" hidden="yes"/>${ele1.tempDisbInterestDetails.billType}</td>
                    <input type="text" name="<%=billedamt21%>" value="${ele1.tempDisbInterestDetails.billedAmount}" hidden="yes"/><td>${ele1.tempDisbInterestDetails.billedAmount}</td>
                        <fmt:formatDate value="${ele1.tempDisbInterestDetails.billingDuedate}" pattern="dd-MM-yyyy" var="billingDuedate" />
                    <td><input type="text" name="<%=billeduedate21%>" value="${billingDuedate}" hidden="yes"/>${billingDuedate}</td>
                        <fmt:formatDate value="${ele1.tempDisbInterestDetails.disbursedDate}" pattern="dd-MM-yyyy" var="disbursedDate" />
                    <td><input type="text" name="<%=billeduedate21%>" value="${disbursedDate}" hidden="yes"/>${disbursedDate}</td>

                    <td><input type="text" name="<%=noofdays21%>" value="${ele1.tempDisbInterestDetails.noofdays}" hidden="yes"/>${ele1.tempDisbInterestDetails.noofdays}</td>
                    <input type="text" name="<%=interestbillamt21%>" value="${ele1.tempDisbInterestDetails.interestBilledamount}" hidden="yes"/> <td>${ele1.tempDisbInterestDetails.interestBilledamount}</td>
                    <input type="text" name="<%=interestamt21%>" value="${ele1.tempDisbInterestDetails.interestAmount}" hidden="yes"/><td>${ele1.tempDisbInterestDetails.interestAmount}</td>
                    <td>${ele1.remarks}</td>
                    </tr>



                </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td>Total(Rs.)</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>&nbsp;</td>
                        <!--<td>&nbsp;</td>-->

                    </tr>
                </tfoot>
            </table>
            <p><input type="text" name="rowcount1" value="<%=cnt1%>" hidden="yes"/></p>
            <p><input class="btn" type="submit" name="recvConfirm" value="Send Back" onclick="return validate1();" /></p>
            <br/>
            <br/>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
            <br/>
            <br/>
        </form>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <br/>
    </body>

    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>

</html>
