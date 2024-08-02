<%--
    Document   : viewWeeklyDsmBill
    Created on : Jun 19, 2019, 9:35:21 AM
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
        <title>DSM Bill Display Page</title>
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
            <p style="text-align:center;" ><c:forEach items="${billDsmInfo}" var="ele"><b>

                        Bill Issue Date: <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Week No.:${ele.weekId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bill Upload Date: <fmt:formatDate value="${ele.entryDate}" pattern="dd-MM-yyyy" /></b></p>
                <p style="text-align:center;">DETAILS OF DSM CHARGES FOR THE WEEK FROM <b><fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> TO <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" /></b></p>
                <p style="text-align:center;"><b>Revision No. ${ele.revisionNo}</b></p>
            </c:forEach>
            <p style="text-align:right; margin-right: 18px;"><i>(All figures in Rs.)</i></p>
            <table align="center" class="customerTable" id="payableTable" style="width:90%;">
                <thead style="height:30px;">
                    <tr>
                        <th>S. No. </th>
                        <th>Entity</th>
                        <th>DSM Charges Payable</th>
                        <th>DSM Charges Receivable</th>
                        <th>Capping DSM Charges</th>
                        <th>Additional DSM Charges</th>
                        <th>Sign Violation Charges</th>
                        <th>Net DSM Payable/Receivable </th>
                        <th>WRLDC Remarks</th>
                    </tr>

                </thead>
                <tbody>
                    <tr><td colspan="9" style="background-color: linen;text-align: center;">INTER REGIONAL</td><td></td><td></td><td></td><td></td></tr>
                    <c:set var="serialno" value="${0}"/>
                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'INTER REGIONAL'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>0</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <c:forEach items="${billReceive}" var="ele">
                        <c:if test="${ele.entites.entityType == 'INTER REGIONAL'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>0</td>
                                <td>${ele.recvCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr><td></td><td></td><td></td><td></td><td style="background-color: linen;text-align: center;">BENEFICIERIES/BUYERS OF WR</td><td></td><td></td><td></td><td></td></tr>
                    <c:set var="serialno" value="${0}"/>
                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'BENEFECIERIES/BUYERS WR'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>0</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>

                    <c:forEach items="${billReceive}" var="ele">
                        <c:if test="${ele.entites.entityType == 'BENEFECIERIES/BUYERS WR'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>0</td>
                                <td>${ele.recvCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr><td></td><td></td><td></td><td></td><td style="background-color: linen;text-align: center;">GENERATING STATIONS(TARIFF DETERMINED BY CERC)</td><td></td><td></td><td></td><td></td></tr>
                    <c:set var="serialno" value="${0}"/>
                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'GENERATING STATIONS(TARIFF DETERMINED BY CERC)'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>0</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <c:forEach items="${billReceive}" var="ele">
                        <c:if test="${ele.entites.entityType == 'GENERATING STATIONS(TARIFF DETERMINED BY CERC)'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>0</td>
                                <td>${ele.recvCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr><td></td><td></td><td></td><td></td><td style="background-color: linen;text-align: center;">OTHER GENERATING STATIONS</td><td></td><td></td><td></td><td></td></tr>
                    <c:set var="serialno" value="${0}"/>
                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'OTHER GENERATING STATIONS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>0</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <c:forEach items="${billReceive}" var="ele">
                        <c:if test="${ele.entites.entityType == 'OTHER GENERATING STATIONS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>0</td>
                                <td>${ele.recvCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr><td></td><td></td><td></td><td></td><td style="background-color: linen;text-align: center;">WIND AND SOLAR GENERATORS</td><td></td><td></td><td></td><td></td></tr>
                    <c:set var="serialno" value="${0}"/>
                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'WIND GENERATORS' || ele.entites.entityType =='SOLAR GENERATORS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>0</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <c:forEach items="${billReceive}" var="ele">
                        <c:if test="${ele.entites.entityType == 'WIND GENERATORS'|| ele.entites.entityType =='SOLAR GENERATORS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>0</td>
                                <td>${ele.recvCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr><td></td><td></td><td></td><td></td><td style="background-color: linen;text-align: center;">INFIRM GENERATORS</td><td></td><td></td><td></td><td></td></tr>
                    <c:set var="serialno" value="${0}"/>

                    <c:forEach items="${billPay}" var="ele">
                        <c:if test="${ele.entites.entityType == 'INFIRM GENERATORS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>${ele.payableCharges}</td>
                                <td>0</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
                    </c:forEach>
                    <c:forEach items="${billReceive}" var="ele">
                        <c:if test="${ele.entites.entityType == 'INFIRM GENERATORS'}">
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                              <!--<td>${ele.corporates.corporateName}</td>-->
                                <td>${ele.entites.entittyName}</td>
                                <td>0</td>
                                <td>${ele.recvCharges}</td>
                                <td>${ele.cappingCharges}</td>
                                <td>${ele.additionalCharges}</td>
                                <td>${ele.signCharges}</td>
                                <td>${ele.netDsm}</td>
                                <td>${ele.remarks}</td>

                            </tr>
                        </c:if>
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

