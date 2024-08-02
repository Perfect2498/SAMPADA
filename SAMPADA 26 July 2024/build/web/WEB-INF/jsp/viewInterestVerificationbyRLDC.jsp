<%--
  Document   : viewInterestVerificationbyRLDC
  Created on : Nov 18, 2019, 2:09:53 PM
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
                    "lengthMenu": [[-1, 10], ["All", 10]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            title: 'Interest Verification Payable'
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            title: 'Interest Verification Payable'
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



                var table1 = $('#interestChecker1').DataTable({
                    responsive: true,
                    "order": [[0, "asc"]],
                    "lengthMenu": [[-1, 10], ["All", 10]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true,
                            title: 'Interest Verification Disburse'
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true,
                            title: 'Interest Verification Disburse'
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


            });


            function validate()
            {
                var checkfklag = 0;
                var rowflag = 0;
                $('#interestChecker').find('tr').each(function () {
                    var row = $(this);
                    if (row.find('input[type="checkbox"]').is(':checked')) {
                        //checkfklag++;
                        rowflag++;
                    }
                    else
                    {
                        var remarks = $(this).find("td:eq(12) input").val();
                        if (remarks == "")
                        {
                            checkfklag = 1;
                        }

                    }
                });
                //  checkfklag=1;
                if ((+checkfklag) == 1)
                {
                    alert("please give remarks for Unchecked transactions!!");
                    return false;
                }
//                if ((+rowflag) == 0)
//                {
//                    alert("KIndly give Checkbox atleast one bill transaction!!");
//                    return false;
//                }
                else
                {
                    if (confirm('Are you sure to Submit for Publishing ??'))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

                return true;
            }


            function validate1()
            {

                var checkfklag = 0;
                var rowflag = 0;
                $('#interestChecker1').find('tr').each(function () {
                    var row = $(this);
                    if (row.find('input[type="checkbox"]').is(':checked')) {
                        //checkfklag++;
                        rowflag++;
                    }
                    else
                    {
                        var remarks = $(this).find("td:eq(12) input").val();
                        if (remarks == "")
                        {
                            checkfklag = 1;
                        }

                    }
                });
                //  checkfklag=1;
                if ((+checkfklag) == 1)
                {
                    alert("please give remarks for Unchecked transactions!!");
                    return false;
                }
//                if ((+rowflag) == 0)
//                {
//                    alert("KIndly give Checkbox atleast one bill transaction!!");
//                    return false;
//                }
                else
                {
                    if (confirm('Are you sure to Submit for Publishing ??'))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
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

        <form method="post" action="interestPaidExcelExport.htm">
            <div align="right">&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;
                <input type="text"  name="startdate" value="${startdate}" hidden="yes" style="color: black;">
                <input type="text"  name="enddate" value="${enddate}" hidden="yes" style="color: black;">

                <input type="submit" value="Corporatewise-Excel Export" style="color: black;">


            </div>
        </form>
        <form name="interestverify" method="post" action="submitPayInterestdetailforProcessingbyRLDC.htm">
             <h3 align="center" style="color:#003366;">From Date ${startdate} to Date ${enddate}</h3>
            <h3 align="center" style="color:#003366;">View Interest for Paid Amount  by Pool Member</h3>

            <table align="center" id="interestChecker" style="width:90%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Select</th>
                        <th>Week ID</th>
                        <th>Pool Member Name</th>
                        <th>Revision No.</th>
                        <th>Bill Type</th>
                        <th>Bill Amount</th>
                        <th>Billing Due date</th>
                        <th>Bank Paid date</th>
                        <th>No. of Days</th>
                        <th>Bill Amount for Interest (Rs.)</th>
                        <th>Interest Amount (Rs.)</th>
                        <th>Bill Year</th>
                        <th>Remarks (max. size 500)</th>
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
                    <c:forEach items="${interestList}" var="ele">
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
                            <td><input type="checkbox" checked="true" id="items"  name="items" value="${ele.interestId}" />${"I"}${ele.interestId}</td>
                    <input type="text" name="<%=uniqueID1%>" value="${ele.interestId}" hidden="yes"/> 

                    <td><input type="text" name="<%=week1%>" value="${ele.weekId}" hidden="yes"/> ${ele.weekId}</td>
                    <td><input type="text" name="<%=poolname1%>" value="${ele.corporates.corporateName}" hidden="yes"/>${ele.corporates.corporateName}</td>
                    <td><input type="text" name="<%=rev1%>" value="${ele.revisionNo}" hidden="yes"/>${ele.revisionNo}</td>
                    <td><input type="text" name="<%=billtype1%>" value="${ele.billType}" hidden="yes"/>${ele.billType}</td>
                    <input type="text" name="<%=billedamt1%>" value="${ele.billedAmount}" hidden="yes"/><td>${ele.billedAmount}</td>
                        <fmt:formatDate value="${ele.billingDuedate}" pattern="dd-MM-yyyy" var="billingDuedate" />
                    <td><input type="text" name="<%=billeduedate1%>" value="${billingDuedate}" hidden="yes"/>${billingDuedate}</td>
                        <fmt:formatDate value="${ele.paidDate}" pattern="dd-MM-yyyy" var="paidDate" />
                    <td><input type="text" name="<%=bankpaiddate1%>" value="${paidDate}" hidden="yes"/>${paidDate}</td>
                    <td><input type="text" name="<%=noofdays1%>" value="${ele.noofdays}" hidden="yes"/>${ele.noofdays}</td>
                    <input type="text" name="<%=interestbillamt1%>" value="${ele.interestBilledamount}" hidden="yes"/> <td>${ele.interestBilledamount}</td>
                    <input type="text" name="<%=interestamt1%>" value="${ele.interestAmount}" hidden="yes"/><td>${ele.interestAmount}</td>
                    <td>${ele.billYear}</td>
                    <td><input type="text"  name="<%=remarks1%>"  id="<%=remarks1%>" style="width:200px; height:50px; font-size:30px;" maxlength="500"/></td>
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
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>&nbsp;</td>
                    </tr>
                </tfoot>
            </table>
            <br/>
            <p><input type="text" name="rowcount" value="<%=cnt%>" hidden="yes"/></p>
            <p><input class="btn" type="submit" name="payConfirm" value="Confirm" onclick="return validate();" /></p>
            <br/>
        </form>


        <form method="post" action="interestDisburseExcelExport.htm">
            <div align="right">&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;
                <input type="text"  name="startdate" value="${startdate}" hidden="yes" style="color: black;">
                <input type="text"  name="enddate" value="${enddate}" hidden="yes" style="color: black;">
                <input type="submit" value="Corporatewise-Excel Export" style="color: black;">
            </div>
        </form>
        <form name="recvinterestverify" method="post" action="submitRecvInterestdetailforProcessingbyRLDC.htm">
            <h3 align="center" style="color:#003366;">From Date ${startdate} to Date ${enddate}</h3>
            <h3 align="center" style="color:#003366;">View Interest for Disbursed Amount to Pool Member</h3>

            <table align="center" id="interestChecker1" style="width:90%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Select</th>
                        <th>Week ID</th>
                        <th>Pool Member Name</th>
                        <th>Revision No.</th>
                        <th>Bill Type</th>
                        <th>Bill Amount</th>                      
                        <th>Billing Due date</th>
                        <th>Disbursed Date</th>
                        <th>No. of Days</th>
                        <th>Bill Amount for Interest (Rs.)</th>
                        <th>Interest Amount (Rs.)</th>
                        <th>Bill Year</th>
                        <th>Remarks (max. size 500)</th>
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


                    <c:forEach items="${disbursedList}" var="ele1">
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
                            <td><input type="checkbox" checked="true"  id="items1"  name="items1" value="${ele1.interestId}" />${"I"}${ele1.interestId}</td>
                    <input type="text" name="<%=uniqueID21%>" value="${ele1.interestId}" hidden="yes"/> 
                    <td><input type="text" name="<%=week21%>" value="${ele1.weekId}" hidden="yes"/> ${ele1.weekId}</td>
                    <td><input type="text" name="<%=poolname21%>" value="${ele1.corporates.corporateName}" hidden="yes"/>${ele1.corporates.corporateName}</td>
                    <td><input type="text" name="<%=rev21%>" value="${ele1.revisionNo}" hidden="yes"/>${ele1.revisionNo}</td>
                    <td><input type="text" name="<%=billtype21%>" value="${ele1.billType}" hidden="yes"/>${ele1.billType}</td>
                    <input type="text" name="<%=billedamt21%>" value="${ele1.billedAmount}" hidden="yes"/><td>${ele1.billedAmount}</td>
                        <fmt:formatDate value="${ele1.billingDuedate}" pattern="dd-MM-yyyy" var="billingDuedate" />
                    <td><input type="text" name="<%=billeduedate21%>" value="${billingDuedate}" hidden="yes"/>${billingDuedate}</td>
                        <fmt:formatDate value="${ele1.disbursedDate}" pattern="dd-MM-yyyy" var="disbursedDate" />
                    <td><input type="text" name="<%=billeduedate21%>" value="${disbursedDate}" hidden="yes"/>${disbursedDate}</td>

                    <td><input type="text" name="<%=noofdays21%>" value="${ele1.noofdays}" hidden="yes"/>${ele1.noofdays}</td>
                    <input type="text" name="<%=interestbillamt21%>" value="${ele1.interestBilledamount}" hidden="yes"/> <td>${ele1.interestBilledamount}</td>
                    <input type="text" name="<%=interestamt21%>" value="${ele1.interestAmount}" hidden="yes"/><td>${ele1.interestAmount}</td>
                    <td>${ele1.billYear}</td>
                    <td><input type="text"  name="<%=remarks21%>"  id="<%=remarks21%>" style="width:200px; height:50px; font-size:30px;" maxlength="500"/></td>
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
                        <td></td>

                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>&nbsp;</td>

                    </tr>
                </tfoot>
            </table>
            <p><input type="text" name="rowcount1" value="<%=cnt1%>" hidden="yes"/></p>
            <p><input class="btn" type="submit" name="recvConfirm" value="Confirm" onclick="return validate1();" /></p>
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