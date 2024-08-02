<%--
    Document   : corpBnkStmttobill
    Created on : Nov 19, 2019, 11:54:26 AM
    Author     : cdac
--%>

<%--
Document : corpBnkStmttobill
Created on : Nov 18, 2019, 12:25:20 PM
Author : Administrator
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
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer : true 
                          
                        },
                          {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer : true 
                          
                        }
                    ]
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
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" >

        <form>
            <fieldset>
                <legend> <h3 style="text-align:center; color:#003366;">Bank Transactions of ${corpName} from ${frmDate} to ${toDate}</h3></legend>

                <h4 style="text-align:center;color:#003366;"><b>Bank Sub Account Number: &nbsp;&nbsp;${subAccNum}</b></h4>
                <table id="payableTable" align="center" style="min-height:400px;width:81%;" class="customerTable">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Transaction ID</th>
                            <th>Amount Date</th>
                            <th>Amount Time</th>
                             <th>Transaction Description</th>
                            <th>Credited Amount</th>
                            <th>Balance</th>
                           
                            <th>Weekid</th>
                            <th>Payment Category</th>
                            <th>Bill Type </th>
                              <th>Revision No. </th>
                              <th>Net Amount</th>
                            <th>Outstanding Amount</th>
                            <th>Mapped Amount</th>
                            <th>Pending Amount</th>
                            <!--<th>Commercial--weekid--Payment_Category--Bill_type--net--mapped--pending</th>-->
                        </tr>
                    </thead>
                    <tbody>
                        
                        <c:forEach items="${queryList1}" var="list">
                        <tr>
                            
                            <c:forEach items="${list}" var="object">
                      <td>${object.toString()}</td>  
                   </c:forEach> 
                            
                         </tr>
                    </c:forEach>    
                        <%--<c:forEach items="${stmtInfoList}" var="list">--%>
<!--                            <tr>

                                <td>${list.stmtId}</td>
                                <td>${list.amountDate}</td>
                                <td>${list.amountTime}</td>
                                <td>${list.paidAmount}</td>
                                <td>${list.mappedBalance}</td>
                                <td>${list.entryDesc}</td>
                                <td>-->
<!--                                    <table>
                                        <tbody>-->
                                            <%--<c:forEach items="${mappbilllistbystmtid}" var="list1">--%>
                                                <%--<c:if test="${list.stmtId==list1.bankStatement.stmtId}">--%>
<!--                                                    <tr>
                                                        <td>${list1.corporates.corporateName}</td>
                                                        <td>${list1.weekId}</td>
                                                        <td>${list1.paymentCategory}</td>
                                                        <td>${list1.billType}</td>
                                                        <td>${list1.pendingAmount + list1.mappedAmount}</td>
                                                        <td>${list1.mappedAmount}</td>
                                                        <td>${list1.pendingAmount}</td>
                                                    </tr>-->
                                                <%--</c:if>--%>
                                            <%--</c:forEach>--%>
                                        <!--</tbody>-->
                                    <!--</table>-->
                                <!--</td>-->




                            <!--</tr>-->
                        <%--</c:forEach>--%>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    </tbody>
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
