<%-- 
    Document   : viewEntityDetailsList
    Created on : Jun 20, 2019, 9:18:29 AM
    Author     : superusr
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Entity Details</title>
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
            $(document).ready(function () {
                var table = $('#example').DataTable({
                    responsive: true,
                    "order": [[0, "asc"]]
                });

                new $.fn.dataTable.FixedHeader(table);
            });

        </script>
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

    </head>
    <body style="background-color:#FBF2EA;" width="80%">

        <!--    <h4 style="text-align: center;color: DarkOrange;"></h4><br>-->
        <br/>
        <form>
            <p align="center" style="font-size: 40px;color:#003366;"><b>Entity Details</b></p>

            <table  align="center" id="example" cellspacing="0" class="customerTable" style="width:72%; ">
                <thead>

                    <tr>
                        <th>Entity Short Name</th>
                        <th>Entity Type</th>
                        <th>Pool Member Short Name</th>
                        <th>Bank </th>
                        <th>Branch name</th>
                        <th>Account No.</th>
                        <th>IFSC</th>
                        <th>Location</th>
                        <th>State</th>
                        <th>Mobile No.</th>
                        <!--<th>Office No.</th>-->
                        <th>&nbsp;</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${entityList}" var="list">
                        <tr>

                            <td><c:out value="${list.entittyName}"/></td>

                            <td><c:out value="${list.entityType}"/></td>
                            <td><c:out value="${list.corporates.corporateName}"/></td>
                            <td><c:out value="${list.bankName}"/></td>
                            <td><c:out value="${list.branchName}"/></td>
                            <td><c:out value="${list.accountNumber}"/></td>
                            <td><c:out value="${list.ifscCode}"/></td>
                            <td><c:out value="${list.entityLocation}"/></td>
                            <td><c:out value="${list.stateName}"/></td>
                            <td><c:out value="${list.mobile}"/></td>
                            <!--<td><c:out value="${list.office}"/></td>-->
                            <td><a name="entityId" href="<c:url value='editSingleEntity.htm'> <c:param name="entityId" value="${list.entityId}"/> </c:url>" class="btn"><span style='font-size:20px;'>&#9999;</span>Edit</a></td>

                            </tr>
                    </c:forEach>
            </table>
            <br/>
        </form>

        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </body>
</html>