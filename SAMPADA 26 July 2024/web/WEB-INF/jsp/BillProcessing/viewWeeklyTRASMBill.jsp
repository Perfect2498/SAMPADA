<%-- 
    Document   : viewWeeklyTRASMBill
    Created on : Jun 19, 2024, 5:18:16 PM
    Author     : JaganMohan
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
        <title>AGC Bill Display Page</title>
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
            <c:forEach items="${billTRASMdet}" var="ele">
                <c:set var="count" value="${count+1}" />
                <c:if test="${count eq 1}">


                    <p style="text-align:center;" ><b>D. Payments to the TRASM Service Provider(s) from the DSM Pool for the week <fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> to <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" />
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
                        <th>S.No </th>
                        <th>SRAS Provider Name</th>



                        <th>TRAS-Up Energy Cleared (Mwh) Ahead (A)</th>
                        <th>TRAS-Up Energy Scheduled (Mwh) Ahead (B)</th>
                        <th>TRAS-Up Energy Charges (Rs.) Ahead (C)</th>
                        <th>TRAS-Up Commitment Charges (Rs.) Ahead (D)</th>
                        <th>TRAS-Up Energy Cleared (Mwh) Real Time (E)</th>
                        <th>TRAS-Up Energy Scheduled (Mwh) Real Time (F)</th>
                        <th> TRAS-Up Energy Charges (Rs.) Real Time (G)</th>
                        <th>TRAS-Up Commitment Charges (Rs.) Real Time (H)</th>
                        <th>Total Charges/Compensation Charges For TRAS-Up (Rs.) (I = C+D+G+H)</th>
                        <th>TRAS-Down Energy Scheduled (Mwh) Ahead (J)</th>
                        <th>TRAS-Down Charges to be paid back to Pool (Rs.) Ahead (K)</th>
                        <th>TRAS-Down Energy Scheduled (Mwh) Real Time (L)</th>
                        <th>TRAS-Down Charges to be paid back to Pool (Rs.) Real Time (M)</th>
                        <th>Net Charges (Rs.) (+) Payable From Pool To AS Provider (-) Receivable By Pool From AS Provider (N = I-K-M)</th>

                        <th>WR-Net Charges (Rs.) (+) Payable From Pool To AS Provider (-) Receivable By Pool From AS Provider (N = I-K-M)</th>
                        <th>WRLDC Remarks</th>

                    </tr>

                </thead>
                <tbody>

                    <c:forEach items="${BillEntityTRASM}" var="ele">
                        <tr>
                            <c:set var="serialno" value="${serialno + 1}" />
                            <td><c:out value="${serialno}"/></td>
                            <td>${ele.entites.entittyName}</td> 
                            
                            <td>${ele.trasUpClearedAhead}</td>
                            <td>${ele.trasUpScheduledAhead}</td>
                            <td>${ele.trasUpEnergyChargesAhead}</td>
                            <td>${ele.trasUpComChargesAhead}</td>
                            <td>${ele.trasUpClearedReal}</td>
                            <td>${ele.trasUpScheduledReal}</td>
                            <td>${ele.trasUpEnergyChargesReal}</td>
                            <td>${ele.trasUpComChargesReal}</td>
                            <td>${ele.trasUpTotalCharges}</td>
                            <td>${ele.trasDownScheduledAhead}</td>
                            <td>${ele.trasDownEnergyChargesAhead}</td>
                            <td>${ele.trasDownScheduledReal}</td>
                            <td>${ele.trasDownEnergyChargesReal}</td>
                            <td>${ele.netTras}</td>
                            <td>${ele.netTras}</td> 
                            <td>${ele.remarks}</td>
                            
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
    </body>
</html>


