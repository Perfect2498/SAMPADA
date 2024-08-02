<%--
    Document   : viewWeeklyRevisedAgcBill
    Created on : Nov 4, 2019, 2:09:01 PM
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
        <title>Revised Agc Bill Display Page</title>
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
            <p style="text-align:center;"><b>WRPC MUMBAI</b></p>
            <c:set var="count" value="${0}" />
            <c:forEach items="${billAgcInfo}" var="ele">
                <c:set var="count" value="${count+1}" />
                <c:if test="${count eq 1}">
                    <p style="text-align:center;" ><b>D. Payments to the AGC Service Provider(s) from the DSM Pool for the week <fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" />
                        </b></p>
                    <p style="text-align:center;"><b>Revision No. ${ele.revisionNo}</b></p>
                    <p style="text-align:center;"><b>Bill Issue Date: <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" /> Week No.:${ele.weekId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bill Upload Date: <fmt:formatDate value="${ele.entryDate}" pattern="dd-MM-yyyy" /></b></p>

                </c:if>

            </c:forEach>
            <p style="text-align:right; margin-right: 18px;"><i>(All figures in Rs.)</i></p>
            <c:set var="serialno" value="${0}"/>
            <table align="center" class="customerTable"  id="payableTable" style="width:90%;">
                <thead height:30px;">
                       <tr>
                        <!--<th>Corporate </th>-->
                        <th>S. No.</th>
                        <th>AGC Provider Name</th>
                        <th>Total AGC Up & Down Regulaqtion (MWh) (based on 5-min schedule) (A)</th>
                        <th>Markup at the rate of 50p/unit for both positive and negative AGC from the DSM Pool (Mwh) (Rs) (B =A*1000*0.5)</th>
                        <th>Total Net AGC (Mwh) (based on 15-Min schedule) (C)</th>
                        <th>AGC Energy Charges (Rs) (D=C*Variable Cost)</th>
                        <th>Total Charges [(Payable) to Pool/ Receivable from Pool] (Rs) (B+D)</th>
                        <!--                    <th>Payable to Pool/Receivable from Pool</th>
                                            <th>WRLDC Remarks</th>-->

                    </tr>

                </thead>
                <tbody>

                    <c:forEach items="${billPayReceive11}" var="objectList">
                        <c:set var="count" value="${0}" />
                        <tr>
                            <c:set var="serialno" value="${serialno + 1}" />
                            <td><c:out value="${serialno}"/></td>
                            <c:forEach items="${objectList}" var="object">
                                <c:set var="count" value="${count + 1}" />
                                <%--<c:if test="${count ne 2}">--%>
                                <td>${object.toString()}</td>
                                <%--</c:if>--%>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    <tr>

                        <td>Total</td>
                        <td></td>
                        <c:forEach items="${sumListPayDiff}" var="objectList">
                            <td>${objectList.toString()}</td>
                        </c:forEach>

                    </tr>

                </tbody>
            </table>
            <br/>
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
    </form>
</body>
</html>



