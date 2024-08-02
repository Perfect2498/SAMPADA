<%--
    Document   : viewNoInterestPeriodList
    Created on : Oct 31, 2019, 11:02:34 AM
    Author     : cdac
--%>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Bill No Interest Period Details</title>
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

                var table = $('#example').DataTable({
                    responsive: true,
                    "pageLength": 16,
                    "order": [[3, "dsc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true

                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true

                        }
                    ]
                });
            });

        </script>

    </head>
    <body style="background-color:#FBF2EA;" width="80%">

        <h3 style="text-align: center;color: DarkOrange;"><b>Interest Details</b></h3><br>
        <br/>
        <form method="post">


            <h1>For New Due Dates Timeline</h1>                <input type="submit" class="btn" style="margin-left: 45%" value="&#9999;New Timeline" name="bName" />

            <table align="center" id="example" class="customerTable" cellspacing="0" style="width:45%; ">
                <thead>

                    <tr>
                        <th>Bill Type</th>

                        <th>No Interest Period Excluding Bill Issuance Day(In Days)</th>
                        <th>Category</th>
                        <th>From Date</th>
                        <th>To Date</th>
                        <th>Effective Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${noInterestList}" var="list">
                        <tr align="center">
                            <td><c:out value="${list.billtype}"/></td>
                            <td><c:out value="${list.noofdays}"/></td>
                            <td><c:out value="${list.category}"/></td>
                            <td><c:out value="${list.fromDate}"/></td>
                            <td><c:out value="${list.toDate}"/></td>
                            <td><c:out value="${list.checkerDate}"/></td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br/>


        </form>
        <h3 style="text-align: center;color: DarkOrange;"><b>Conditions For Interest Generation</b></h3><br>
        <table align="center" id="example1" class="customerTable" cellspacing="0" style="width:45%; ">
            <thead>

                <tr>
                    <th>Condition</th>

                    <th>Amount(Rs.)</th>

                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="font-weight: bold">No Interest For Mapped Amount (or) Disbursed Amount is Less Than</td>
                    <td>1</td>
                </tr>
                <tr>
                    <td style="font-weight: bold">No Interest For Calculated Interest Amount is Less Than</td>
                    <td>100</td>
                </tr>
            </tbody>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </body>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
</html>

