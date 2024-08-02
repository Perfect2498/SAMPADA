<%-- 
    Document   : LCFYdetails
    Created on : 15 Jul, 2020, 8:24:28 PM
    Author     : Kaustubh
--%>

<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Previous FY Payable Weeks</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/css/buttons.dataTables.min.css"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>
        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet">
        <script>
            $(document).ready(function () {

            var table = $('#LCFY').DataTable({
                responsive: true,
                "pageLength": 25,
                "order": [[0, "asc"]],
                "dom": 'lBfrtip',
                "buttons": [
                    {
                        extend: 'excel',
                        text: 'Excel Export',
                        title: '1.2 DSM LC for FY ${year}-${year+1}',
                        footer : true,
                        messageTop: function () {
                                return 'DSM LC Calculation for FY ${year}-${year+1}';
                            }
                    },
                    {
                        extend: 'pdfHtml5',
                        text: 'Pdf Export',
                        title: '1.2 DSM LC for FY ${year}-${year+1}',
                        footer : true,
                        messageTop: function () {
                                return 'DSM LC Calculation for FY ${year}-${year+1}';
                            }
                    }
                ],
    });
    });
        </script>
    </head>
    <body>
        <h2 style="color: black" align="center">DSM LC Calculation for FY ${year}-${year+1}. As per Latest Revision of Bills.</h2>
        
        <table id="LCFY" class="customerTable" cellspacing="0" align="left">
            <thead>
                <th>Name of DSM Pool Member</th>
                <c:forEach begin="${weekstart}" end="${weekend}" varStatus="iteration">
                    <th>${iteration.index}th Week</th>
                </c:forEach>
                <th>Total Payable only (In Rs.)</th>
                <th>No. of weeks Payable only</th>
                <th>Avg for Payable weeks only (in Rs.)</th>
                <th>LC=1.10*AVG</th>
                <th>LC (In Lakhs)</th>
                <th>Remarks if any (Default Weeks)</th>
            </thead>
            <tbody>
                <c:forEach items="${lcweeks}" var="rows" varStatus="index">
                    <tr>
                        <c:forEach items="${rows}" var="ele">
                            <td>${ele}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td>Net Total</td>
                    <c:forEach items="${nettotals}" var="sum">
                        <td>${sum}</td>
                    </c:forEach>
                        <td></td>
                        <td style="border: none"></td>
                </tr>
                <tr>
                    <td>Account issued date</td>
                    <c:forEach items="${accdate}" var="dt">
                        <td>${dt}</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td>Due date</td>
                    <c:forEach items="${accdate}" var="dt">
                        <%
                            Date dt2 = (Date) pageContext.getAttribute("dt");
                            Date duedate_ = null;
                            String duedate = "";
                            String month = "";
                            String date = "";
                            
                            if(dt2!=null) {
                                duedate_ = new Date(dt2.getTime() + 864000000);
                                
                                if(duedate_.getMonth()<9)
                                    month = "0"+(duedate_.getMonth()+1)+"-";
                                else
                                    month = (duedate_.getMonth()+1)+"-";
                                
                                if(duedate_.getDate()<10)
                                    date = "0"+(duedate_.getDate());
                                else
                                    date = String.valueOf(duedate_.getDate());
                                
                                duedate = (duedate_.getYear()+1900)+"-"+month+date;
                            }
                        %>
                        <td><%=duedate%></td>
                    </c:forEach>
                </tr>
            </tfoot>
        </table>

    </body>
</html>
