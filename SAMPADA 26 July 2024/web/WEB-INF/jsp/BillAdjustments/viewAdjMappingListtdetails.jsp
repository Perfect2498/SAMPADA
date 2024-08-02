<%-- 
    Document   : viewAdjMappingListtdetails
    Created on : Jan 20, 2021, 12:10:43 PM
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
            <h3 align="center" style="color:#003366;" >Adjustment Mapping Details </h3>
            <input type="text" name="corpid" value="${corpid}" hidden="yes"/>
            <table id="payableTable" align="center" style="width:81%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Sl No</th>
                        <th>Corporae Id</th>
                        <th>Corporate Name</th>
                        <th>Adjustment Id</th>
                        <th>Bill Id</th>
                        <th>Bill Pending</th>
                        <th>Bill Balance After Adjust</th>
                        <th>Adjust Amount</th>
                        <th>Type</th>                          
                        <th>Week Id</th>
                        <th>Revision No</th>
                        <th>Bill Type</th>
                        <th>Bill Year</th>
                        <th>Status</th>

                    </tr>
                </thead>
                <tbody>
                    <% int cnt = 0;%>
                    <c:forEach items="${adjmaplist}" var="ref">
                        <%
                            cnt++;
                        %>
                        <tr>
                            <td><%=cnt%></td>
                            <!--<td>${ref.slNo}</td>-->
                            <td>${ref.corporates.corporateId}</td>
                            <td>${ref.corporates.corporateName}</td>                            
                            <td>${ref.adjPayment.uniqueId}</td>
                            <c:if test="${ref.billPayableCorpByPayId != null}">
                                <td>${"B"}${ref.billPayableCorpByPayId.uniqueId}</td>
                                <td>${ref.adjustAmt+ref.payBal}</td>  
                                <td>${ref.payBal}</td>
                                <td>${ref.adjustAmt}</td>
                                <td>Payable Side</td>
                                <td>${ref.billPayableCorpByPayId.weekId}</td>  
                                <td>${ref.billPayableCorpByPayId.billType}</td>  
                                <td>${ref.billPayableCorpByPayId.revisionNo}</td>  
                                <td>${ref.billPayableCorpByPayId.billYear}</td>  

                            </c:if>
                            <c:if test="${ref.billReceiveCorpByRecRefId != null}">
                                <td>${"R"}${ref.billReceiveCorpByRecRefId.uniqueId}</td>
                                <td>${ref.adjustAmt+ref.recRefBal}</td>  
                                <td>${ref.recRefBal}</td>
                                <td>${ref.adjustAmt}</td>
                                <td>Payable Side</td>
                                <td>${ref.billReceiveCorpByRecRefId.weekId}</td>
                                <td>${ref.billReceiveCorpByRecRefId.billType}</td>
                                <td>${ref.billReceiveCorpByRecRefId.revisionNo}</td>
                                <td>${ref.billReceiveCorpByRecRefId.billYear}</td>
                            </c:if>
                            <c:if test="${ref.interestDetails != null}">
                                <td>${"I"}${ref.interestDetails.interestId}</td>
                                <td>${ref.adjustAmt+ref.payIntBal}</td>  
                                <td>${ref.payIntBal}</td>
                                <td>${ref.adjustAmt}</td>
                                <td>Payable Side</td>
                                <td>${ref.interestDetails.weekId}</td>
                                <td>${ref.interestDetails.billType}</td>
                                <td>${ref.interestDetails.revisionNo}</td>
                                <td>${ref.interestDetails.billYear}</td>
                            </c:if>
                            <c:if test="${ref.billReceiveCorpByRecvId != null}">
                                <td>${"B"}${ref.billReceiveCorpByRecvId.uniqueId}</td>
                                <td>${ref.adjustAmt+ref.recBal}</td>  
                                <td>${ref.recBal}</td>
                                <td>${ref.adjustAmt}</td>
                                <td>Receivable Side</td>
                                <td>${ref.billReceiveCorpByRecvId.weekId}</td>
                                <td>${ref.billReceiveCorpByRecvId.billType}</td>
                                <td>${ref.billReceiveCorpByRecvId.revisionNo}</td>
                                <td>${ref.billReceiveCorpByRecvId.billYear}</td>
                            </c:if>
                            <c:if test="${ref.billPayableCorpByPayRefId != null}">
                                <td>${"R"}${ref.billPayableCorpByPayRefId.uniqueId}</td>
                                <td>${ref.adjustAmt+ref.payRefBal}</td>  
                                <td>${ref.payRefBal}</td>
                                <td>${ref.adjustAmt}</td>
                                <td>Receivable Side</td>
                                <td>${ref.billPayableCorpByPayRefId.weekId}</td>
                                <td>${ref.billPayableCorpByPayRefId.billType}</td>
                                <td>${ref.billPayableCorpByPayRefId.revisionNo}</td>
                                <td>${ref.billPayableCorpByPayRefId.billYear}</td>
                            </c:if>
                            <c:if test="${ref.disbursedInterestDetails != null}">
                                <td>${"I"}${ref.disbursedInterestDetails.interestId}</td>
                                <td>${ref.adjustAmt+ref.recIntBal}</td>  
                                <td>${ref.recIntBal}</td>
                                <td>${ref.adjustAmt}</td>
                                <td>Receivable Side</td>
                                <td>${ref.disbursedInterestDetails.weekId}</td>
                                <td>${ref.disbursedInterestDetails.billType}</td>
                                <td>${ref.disbursedInterestDetails.revisionNo}</td>
                                <td>${ref.disbursedInterestDetails.billYear}</td>
                            </c:if>

                            <td>${ref.status}</td>                                
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
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>  
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                    </tr>
                </tfoot>
            </table>

            <p>&nbsp;</p>

           
    </form>


    <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>
</body>



</html>