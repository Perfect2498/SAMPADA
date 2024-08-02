<%--
    Document   : viewWeeklyRrasBill
    Created on : Jun 20, 2019, 12:14:31 PM
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
        <title>RRAS Bill Display Page</title>
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
                var billDate = $("#billDate").val();
                var fromDate = $("#fromDate").val();
                var toDate = $("#toDate").val();
                var entryDate = $("#entryDate").val();
                var weekId = $("#weekId").val();
                var revisionNo = $("#revisionNo").val();
                var dueDate = $("#dueDate").val();
                var table = $('#payableTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'DSM Bill Week No. ' + weekId + 'Revision No. ' + revisionNo + 'Issue Date:' + billDate + 'Due Date:' + dueDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n         Week No. ' + weekId + '      Bill Issue Date:' + billDate + '    Bill Due Date:' + dueDate + '        Bill Upload Date: ' + entryDate +
                                        '\r\n \r\n DETAILS OF DSM CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
                                        '\r\n \r\n Revision No. ' + revisionNo +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'RRAS Bill Week No. ' + weekId + 'Revision No. ' + revisionNo + 'Issue Date:' + billDate + 'Due Date:' + dueDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n Bill Issue Date:' + billDate + '       Week No. ' + weekId + '        Bill Upload Date: ' + entryDate +
                                        '\r\n \r\n DETAILS OF DSM CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
                                        '\r\n \r\n Revision No. ' + revisionNo +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
                        }

                    ]
                });

                

                var table = $('#recvTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'DSM Bill Week No. ' + weekId + 'Revision No. ' + revisionNo + 'Issue Date:' + billDate + 'Due Date:' + dueDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n Format-AS7: RRAS Settlement Account for the week ' + fromDate + ' TO ' + toDate + '        Bill Upload Date: ' + entryDate +
                                        '\r\n Bill Issue Date:' + billDate + '       Week No. ' + weekId + '        Bill Upload Date: ' + entryDate +
                                        '\r\n \r\n Revision No. ' + revisionNo +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'RRAS Bill Week No. ' + weekId + 'Revision No. ' + revisionNo + 'Issue Date:' + billDate + 'Due Date:' + dueDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return  '\r\n Format-AS7: RRAS Settlement Account for the week ' + fromDate + ' TO ' + toDate +
                                        '\r\n Bill Issue Date:' + billDate + '       Week No. ' + weekId + '        Bill Upload Date: ' + entryDate +
                                        '\r\n \r\n Revision No. ' + revisionNo +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
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
            <c:forEach items="${billRrasInfo}" var="ele">
                <c:set var="count" value="${count+1}" />
                <c:if test="${count eq 1}">


                    <p style="text-align:center;"><u>Format-AS7: RRAS Settlement Account for the week <b><fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" /> Issued on: <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" /></u> </b></p>
            <p style="text-align:center;" ><b>A. Payments to the RRAS Provider(s) from the DSM Pool for UP Regulation for the week <fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" />
                </b></p>
            <p style="text-align:center;"><b>Revision No. ${ele.revisionNo} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bill Upload Date: <fmt:formatDate value="${ele.entryDate}" pattern="dd-MM-yyyy" /></b></p>
        </c:if>
    </c:forEach>
    <p style="text-align:right; margin-right: 18px;"><i>(All figures in Rs.)</i></p>
    <c:set var="serialno" value="${0}"/>
    <table align="center" class="customerTable" id="payableTable" style="width:90%;">
        <thead height:30px;">
               <tr>
                <!--<th>Corporate </th>-->
                <th>S. No.</th>
                <th>RRAS Provider Name</th>
                <th>Energy Scheduled to VAE under RRAS(MWh)</th>
                <th>Fixed Charges(Rs) (A)</th>
                <th>Varible Charges (B)</th>
                <th>Markup as per CERC Order(Rs) (C)</th>
                <th>Total Charges (A+B+C)</th>
                <th>WRLDC Remarks</th>

            </tr>

        </thead>
        <tbody>

            <c:forEach items="${billReceive}" var="ele">
                <tr>
                    <c:set var="serialno" value="${serialno + 1}" />
                    <td><c:out value="${serialno}"/></td>
                    <td>${ele.entites.entittyName}</td>
                    <td>${ele.energyVae}</td>
                    <td>${ele.fixedCharges}</td>
                    <td>${ele.variableCharges}</td>
                    <td>${ele.markupCharges}</td>
                    <td>${ele.netRras}</td>
                    <td>${ele.remarks}</td>
                </tr>
            </c:forEach>

            <c:forEach items="${sumListReceive}" var="objectList">
                <tr style="color: #003366;">
                    <td></td>
                    <td>Total</td>
                    <c:forEach items="${objectList}" var="object">
                        <td>${object.toString()}</td>
                    </c:forEach>
                    <td></td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
    <br/>
    <c:set var="count" value="${0}" />
    <c:forEach items="${billRrasInfo}" var="ele">
        <c:set var="count" value="${count+1}" />
        <c:if test="${count eq 1}">
            <p style="text-align:center;"><b>B. Payments by the RRAS Provider(s) to the DSM Pool for DOWN Regulation for the week <fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" />
                </b></p>
            </c:if>

    </c:forEach>
    <c:set var="serialno" value="${0}"/>
    <table align="center" class="customerTable" id="recvTable" style="width:90%;">
        <thead style="height:30px;">
            <tr>
                <th>S. No. </th>
                <th>RRAS Provider Name</th>
                <th>Energy Scheduled to VAE under RRAS(MWh)</th>
                <th>Total Variable Charges for generation reduced (Rs) (A)</th>
                <th>Varible Charges to be paid to the DSM Pool(Rs)(B=75% of A)</th>
                <th>WRLDC Remarks</th>
            </tr>
        </thead>
        <tbody>


            <c:forEach items="${billPay}" var="ele">
                <tr>
                    <c:set var="serialno" value="${serialno + 1}" />
                    <td><c:out value="${serialno}"/></td>

                    <td>${ele.entites.entittyName}</td>
                    <td>${ele.energyVae}</td>
                    <td>${ele.variableCharges}</td>
                    <td>${ele.netRras}</td>
                    <td>${ele.remarks}</td>
                </tr>
            </c:forEach>

            <c:forEach items="${sumListPay}" var="objectList">
                <tr>
                    <!--                <td colspan="2">Total</td>-->
                    <td></td>
                    <td>Total</td>


                    <c:forEach items="${objectList}" var="object">
                        <td>${object.toString()}</td>
                    </c:forEach>
                    <td></td>

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

</body>
</html>

