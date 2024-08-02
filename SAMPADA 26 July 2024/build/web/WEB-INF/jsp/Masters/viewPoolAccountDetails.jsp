<%--
    Document   : viewPoolAccountDetails
    Created on : Jun 20, 2019, 3:28:07 PM
    Author     : superusr
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
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
        <title>Entity Registration Page</title> <link href="css/mystyle.css" type="text/css" rel="stylesheet" >
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <title>JSP Page</title>
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
    <body style="background-color:#FBF2EA;alignment-adjust: middle;"><br>
        <p align="center" style="font-size: 40px;color:#003366;"><b>Pool Account Details</b></p>

        <!--<p style="text-align: center;"><b style="color: #d58512; font-size: 14px;">Pool Account Details</b></p><br>-->
        <br>
        <form>

            <table align="center" id="example"  class="customerTable" style="width:50%; font-size: 18px;">

                <tbody>

                    <c:forEach items="${poolDetails}" var="list">
                        <tr>
                            <td>Account No.</td>
                            <td>${list.accountNumber}</td>
                        </tr>
                        <tr>
                            <td>Account Name</td>

                            <td>${list.accountName}</td>
                        </tr>
                        <tr>
                            <td>IFSC</td>
                            <td>${list.ifscCode}</td>
                        </tr>
                        <tr>
                            <td>Branch Name</td>
                            <td>${list.branchName}</td>
                        </tr>
                        <tr>
                            <td>Branch Address</td>
                            <td>${list.branchAddress}</td>
                        </tr>
                        <tr>
                            <td>Branch No.</td>
                            <td>${list.branchNumber}</td>
                        </tr>
                        <tr>
                            <td>RLDC Contact Person</td>
                            <td><c:out value="${list.rldcContactperson}"/></td>
                        </tr>
                        <tr>
                            <td>RLDC Contact Number</td>
                            <td><c:out value="${list.rldcContactnumber}"/></td>
                        </tr>
                        <c:set var="TotalBalance" value="${list.mainBalance}"/>
                        <tr>
                            <td>DSM Principal Balance (Rs.)</td>
                            <td><c:out value="${list.mainBalance}"/></td>

                        </tr>

                    </c:forEach>
                    <c:forEach items="${intAccDetails}" var="intPoolDetail" >
                        <c:set var="TotalBalance1" value="${intPoolDetail.mainBalance}"/>
                        <tr>
                            <td>DSM Interest Balance (Rs.)</td>
                            <td><c:out value="${intPoolDetail.mainBalance}"/></td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${listagc}" var="listagc" >
                        <c:set var="TotalBalance2" value="${listagc.mainBalance}"/>
                        <tr>
                            <td>SRAS Principal Balance (Rs.)</td>
                            <td><c:out value="${listagc.mainBalance}"/></td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${listinterestpoolagc}" var="listinterestpoolagc" >
                        <c:set var="TotalBalance3" value="${listinterestpoolagc.mainBalance}"/>
                        <tr>
                            <td>SRAS Interest Balance (Rs.)</td>
                            <td><c:out value="${listinterestpoolagc.mainBalance}"/></td>
                        </tr>
                    </c:forEach>

                    <c:forEach items="${listrras}" var="listrras" >
                        <c:set var="TotalBalance4" value="${listrras.mainBalance}"/>
                        <tr>
                            <td>TRAS Principal Balance (Rs.)</td>
                            <td><c:out value="${listrras.mainBalance}"/></td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${listinterestpoolrras}" var="listinterestpoolrras" >
                        <c:set var="TotalBalance5" value="${listinterestpoolrras.mainBalance}"/>
                        <tr>
                            <td>TRAS Interest Balance (Rs.)</td>
                            <td><c:out value="${listinterestpoolrras.mainBalance}"/></td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <td>Total Balance (Rs.)</td>
                        <c:set var="TotalBalance6" value="${TotalBalance1 +TotalBalance2 +TotalBalance3 +TotalBalance4 +TotalBalance5 + TotalBalance}"/>

                        <td><c:out value="${TotalBalance6}"/></td>
                    </tr>

                </tbody>
            </table>
            <br/>

            <h4 style="color:#003366;text-align: center;"><b>Note:</b> The amount is Verified Bank Statement by the Checker.</h4>
        </form>


        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
    </body>
    <p>&nbsp;</p><p>&nbsp;</p>
</html>