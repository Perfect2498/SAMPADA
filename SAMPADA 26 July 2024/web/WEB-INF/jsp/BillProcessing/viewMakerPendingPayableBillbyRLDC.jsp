<%--
    Document   : viewMakerPendingPayableBillbyRLDC
    Created on : Jul 11, 2019, 9:23:42 AM
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

                var table = $('#makerTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "bFilter": false,
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
                                .column(3)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(3, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(3).footer()).html(
                                pageTotal
                                );




                    }
                });
                

                var table1 = $('#makerTable1').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "bFilter": false,
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
                                .column(3)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(3, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(3).footer()).html(
                                pageTotal
                                );




                    }
                });
               


                var table2 = $('#bankTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
                                .column(3)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(3, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(3).footer()).html(
                                pageTotal
                                );


                    }
                });
             
            });
        </script>
        <script>


            function validate()
            {
                var rowcnt = document.getElementById("count").value;
                if (rowcnt == 0)
                {
                    return false;
                }
                else
                {
                    if (confirm("Are you want to Delete !!!!!"))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

            }


        </script>
    </head>
    <body style="text-align: center; width: 95%;">


        <form>
            <c:if test="${successMSG!=null}">
                <h3>${successMSG}</h3>
            </c:if>
            <fieldset>
                <h3  style="color:#003366;text-align:center;">View Maker Payment Verification Details for Corporate: ${CorpName}</h3><br/>
                <input type="text" name="CorpID" value="${CorpID}" hidden=yes"/>
                <h3 style="color:#003366;text-align:center;">Mapped Bank Entries</h3>
                <table align="center" id="bankTable" style="width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Entry Id</th>
                            <th>Credit Date</th>
                            <!--<th>Opening Amount</th>-->
                            <th>Credit Amount</th>
                            <th>Settled  Amount</th>
                            <th>Entry Balance</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:forEach items="${bankList}" var="ele">
                            <tr>
                                <td>${ele.bankStatement.stmtId}</td>
                                <td>${ele.bankStatement.amountDate}</td>
                                <!--<td>${ele.bankStatement.openBalance}</td>-->
                                <td>${ele.bankStatement.paidAmount}</td>
                                <td>${ele.mappedAmount}</td>
                                <td>${ele.transactionBalance}</td>
                            </tr>

                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th style="text-align:right">Total:</th>
                            <th>&nbsp;</th>

                            <th>&nbsp;</th>
                            <th></th>
                            <th>&nbsp;</th>

                        </tr>
                    </tfoot>

                </table><br/>
                <h3 style="color:#003366;text-align:center;">Mapped Bill Entries</h3>
                <table id="makerTable" align="center" style="width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Week ID</th>
                            <th>Bill Due date</th>
                            <th>Total Amount</th>
                            <th>Verified Amount</th>
                            <th>Pending Amount</th>
                            <th>Bill Type</th>
                            <!--<th>Category</th>-->
                            <th>Rev. No.</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% int cnt = 0;%>
                        <c:forEach items="${tempmaplist}" var="ele">
                            <tr>
                                <%
                                    cnt++;
                                %>
                                <td>${ele.weekId}</td>
                                <td>${ele.billPayableCorp.billDueDate}</td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.originalAmount}</td>
                                <td>${ele.mappedAmount}</td>
                                <td>${ele.pendingAmount}</td>
                                <td>${ele.billType}</td>
                                <!--<td>${ele.paymentCategory}</td>-->
                                <td>${ele.billPayableCorp.revisionNo}</td>

                            </tr>

                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th style="text-align:right">Total:</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th></th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <!--<th>&nbsp;</th>-->
                            <th>&nbsp;</th>
                        </tr>
                    </tfoot>
                    <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>
                </table>
                <br/>

                <p>&nbsp;</p>
                <p>&nbsp;</p>



                <p align="center">Mapped Refund Details </p>

                <br/>

                <table id="makerTable1" align="center" style="width:81%;" class="customerTable">

                    <thead style="background-color: #0677A1;color: white;height:30px;">

                        <tr>

                            <th>Week ID</th>

                            <th>Bill Due date</th>



                            <th>Total Amount</th>

                            <th>Verified Amount</th>

                            <th>Pending Amount</th>

                            <th>Bill Type</th>

                            <th>Category</th>

                        </tr>

                    </thead>

                    <tbody>

                        <% int cnt1 = 0;%>

                        <c:forEach items="${listrefund}" var="ele">

                            <tr>

                                <%

                                    cnt1++;

                                %>

                                <td>${ele.weekid}</td>

                                <td>${ele.billReceiveCorp.billDueDate}</td>

                                <td>${ele.totalAmount}</td>

                                <td>${ele.paidAmount}</td>

                                <td>${ele.pendingAmount}</td>

                                <td>${ele.billReceiveCorp.billType}</td>

                                <td>${ele.billReceiveCorp.revisionNo}</td>



                            </tr>



                        </c:forEach>

                    </tbody>

                    <tfoot>

                        <tr>

                            <th style="text-align:right">Total:</th>

                            <th>&nbsp;</th>

                            <th>&nbsp;</th>

                            <th></th>

                            <th></th>

                            <th>&nbsp;</th>

                            <th>&nbsp;</th>

                        </tr>

                    </tfoot>

                    <p><input type="text" name="refcount" id="refcount" value="<%=cnt1%>" hidden="yes"/></p>

                </table>

                <p>&nbsp;</p><p>&nbsp;</p>


                <!--<p align="center"><input class="btn" onclick="return validate();" type="submit" value="Delete"  name="bdelete" /></p>-->
            </fieldset>
            <br/>

            <p>&nbsp;</p>
            <p>&nbsp;</p>
        </form>
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
        <br/>

    </body>
</html>
