<%-- 
    Document   : dsndisburseview
    Created on : 20 Mar, 2020, 12:25:54 PM
    Author     : abt
--%>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pool Member Details</title>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
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
                var table = $('#example').DataTable({
                    responsive: true
                });

                new $.fn.dataTable.FixedHeader(table);
            });
        </script>


    </head>
    <body style="background-color:#FBF2EA;" width="95%">
        <br/>
        <p align="center" style="font-size: 40px;color:#003366;"><b>Bank Statement Details</b></p>

        <!--<h2 style="text-align: center;"><b style=""></b></h2><br>-->
        <br/>
        <form>
            <table align="center" id="example" class="customerTable" style="width:81%; ">
                <thead>
                    <tr>
                        <th>Pool Member Name</th>
                        <th>Bank Id</th>
                        <th>CR Amount</th>
                        <th>CR Date</th>
                        <th>Mapped Amount</th>
                        <th>Mapped Balance</th>
                        
                        <th>&nbsp;</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${bnkstmtdsn}" var="list">
                        <tr>
                            <td><c:out value="${list.corporates.corporateName}"/></td>
                            <td><c:out value="${list.stmtId}"/></td>
                            <td><c:out value="${list.paidAmount}"/></td>
                            <td><c:out value="${list.amountDate}"/></td>
                            <td><c:out value="${list.mappedAmount}"/></td>
                            <td><c:out value="${list.mappedBalance}"/></td>
                            <td><a name="stmtId" href="<c:url value='disbursedsnconfirm.htm'> <c:param name="stmtId" value="${list.stmtId}"/> </c:url>" class="btn"><span style='font-size:20px;'>&#9999;</span>Disburse</a></td>

                            </tr>
                    </c:forEach>
            </table>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
        </form>

        <br/>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </body>
</html>
