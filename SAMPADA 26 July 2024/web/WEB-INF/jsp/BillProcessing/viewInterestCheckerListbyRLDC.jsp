<%--
    Document   : viewInterestCheckerListbyRLDC
    Created on : Jul 12, 2019, 12:55:34 PM
    Author     : JaganMohan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
                    ]
                });
              
            });
        </script>


    </head>
    <body style="text-align: center; alignment-adjust: central;width: 95%;">

        <form>

            <h3 align="center" style="color:#003366;">View Interest Payment List by Corporate</h3>

            <table align="center" id="interestChecker" style="width:90%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Week ID</th>
                        <th>Pool Member Name</th>
                        <th>Billed Amount</th>
                        <th>Billing Due date</th>
                        <th>Bank Paid date</th>
                        <th>No of Days</th>
                        <th>Interest Billed Amount</th>
                        <th>Interest Amount</th>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach items="${interestList}" var="ele">
                        <tr>
                            <td>${ele.weekId}</td>
                            <td>${ele.corporates.corporateName}</td>
                            <td>${ele.billedAmount}</td>
                            <td>${ele.billingDuedate}</td>
                            <td>${ele.paidDate}</td>
                            <td>${ele.noofdays}</td>
                            <td>${ele.interestBilledamount}</td>
                            <td>${ele.interestAmount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br/>
            <br/>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        </form>
        <br/>
        <br/>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <br/>

    </body>
</html>
