<%--
    Document   : viewWeeklyFrasBill
    Created on : Nov 4, 2019, 2:01:28 PM
    Author     : cdac
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FRAS Bill Display Page</title>
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
                var table = $('#payableTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
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
    <body>
        <form>
            <br/>
            <c:set var="count" value="${0}" />
            <p style="text-align:center;"><b>WRPC MUMBAI</b></p>
            <c:forEach items="${billFrasInfo}" var="ele">
                <c:set var="count" value="${count+1}" />
                <c:if test="${count eq 1}">

                    <p style="text-align:center;" ><b>RRAS Settlement Account for the week <fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" />
                        </b></p>
                    <p style="text-align:center;"><b>E. Payment to the FRAS Provider  from the DSM Pool</b></p>
                    <p style="text-align:center;"><b>Bill Issue Date: <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" />&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;  Bill Upload Date: <fmt:formatDate value="${ele.entryDate}" pattern="dd-MM-yyyy" /></b></p>
                    <p style="text-align:center;"><b>Revision No. ${ele.revisionNo}</b></p>
                </c:if>
            </c:forEach>
            <p style="text-align:right; margin-right: 18px;"><i>(All figures in Rs.)</i></p>
            <c:set var="serialno" value="${0}"/>
            <table align="center" class="customerTable" id="payableTable" style="width:90%;">
                <thead style="height:30px;">
                    <tr>
                        <!--<th>Corporate </th>-->
                        <th>S. No.</th>
                        <th>FRAS Provider Name</th>
                        <th>Up regulation due to FRAS (MWh) (A)</th>
                        <th>Down regulation due to FRAS (MWh)(B)</th>
                        <th>Markup Charges as per CERC Order(Rs) (C =((A + B) * 100))</th>
                        <th>WRLDC Remarks</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${billPayRecv}" var="ele">
                        <tr>
                            <c:set var="serialno" value="${serialno + 1}" />
                            <td><c:out value="${serialno}"/></td>
                            <td>${ele.entites.entittyName}</td>
                            <td>${ele.upRegulation}</td>
                            <td>${ele.downRegulation}</td>
                            <td>${ele.markupCharges}</td>
                            <td>${ele.remarks}</td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${sumListPayRecv}" var="objectList">
                        <tr style="color: #003366;">

                            <td>Total</td>
                            <td>&nbsp;</td>
                            <c:forEach items="${objectList}" var="object">
                                <td>${object.toString()}</td>
                            </c:forEach>
                            <td>&nbsp;</td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br/>
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

        <br/>
    </body>
</html>


