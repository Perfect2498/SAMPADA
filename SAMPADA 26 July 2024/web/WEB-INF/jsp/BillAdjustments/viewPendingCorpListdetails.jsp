<%-- 
    Document   : viewPendingCorpListdetails
    Created on : Jan 20, 2021, 11:37:48 AM
    Author     : Administrator
--%>






<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
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
            function validate()
            {
                var rowcnt = document.getElementById("count").value;
                if (rowcnt == 0)
                {
                    return false;
                }
                else
                {
                    if (confirm("Are you want to Confirm !!!!!"))
                    {
                        return true;
                    }
                    else
                    {
                        return false;

                    }
                }

            }


            function validate1()
            {
                var rowcnt = document.getElementById("count").value;
                //   alert(count);
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
        <script>
            $(document).ready(function () {

                var table = $('#payableTable').DataTable({
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
                                pageTotal.toFixed(2)
                                );

                        total = api
                                .column(4)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(4, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(4).footer()).html(
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
    <body width="80%">
        <form name="checkerForm" cellspacing="0">
            <h3 align="center" style="color:#003366;" >Adjustment Payment Details  </h3>
            <input type="text" name="corpid" value="${corpid}" hidden="yes"/>
            <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>UNIQUE_ID AND MAPPING_LINK</th>
                        <th>CORP ID</th>
                        <th>CORP NAME</th>
                        <th>TOTAL PAY</th>
                        <th>TOTAL REC</th>
                        <th>ADJUST AMT</th>
                        <th>NET AMT</th>
                        <th>REMARKS</th>
                        <th>ENTRY_DATE</th>
                        <th>STATUS</th>                            
                    </tr>
                </thead>
                <tbody>
                    <% int cnt = 0;%>
                    <c:forEach items="${adjpaylist}" var="ref">
                        <%
                            cnt++;
                        %>
                        <tr>
                            <td><a href="<c:url value='viewAdjustmentMappingDetails.htm'>

                                   <c:param name="uniqueId" value="${ref.uniqueId}"/>

                                    </c:url>" >${ref.uniqueId}</a></td>
                            <td>${ref.corporates.corporateId}</td>
                            <td>${ref.corporates.corporateName}</td>
                            <td>${ref.totalPay}</td>
                            <td>${ref.totalRec}</td>
                            <td>${ref.adjustAmt}</td>
                            <c:if test="${ref.totalPay >= ref.totalRec}">
                                <td>${ref.totalPay-ref.totalRec}</td>
                            </c:if>
                            <c:if test="${ref.totalPay < ref.totalRec}">
                                <td>${ref.totalRec-ref.totalPay}</td>
                            </c:if>
                            <td>${ref.remarks}</td>
                            <td>${ref.entryDate}</td>
                            <td>${ref.status}</td>                                
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
                        <th></th>
                        <th></th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                    </tr>
                </tfoot>
            </table>

            <p>&nbsp;</p>

            <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>
            <p align="center">
                <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bconfirm"  value="Confirm"/> &emsp;
                <input style="width:100px;" class="btn" type="submit" value="&#10060;Delete" onclick="return validate1();"  name="bdelete" /></p>

        </p>
    </form>


    <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>
</body>



</html>