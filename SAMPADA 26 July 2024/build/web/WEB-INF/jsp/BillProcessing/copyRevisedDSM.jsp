<%--
    Document   : viewWeeklyRevisedDsmBill
    Created on : Jun 24, 2019, 9:43:47 AM
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
        <title>Bill Payable Display Page</title>
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
    <body>
        <form>
            <br/>
            <p style="text-align:center;" ><c:forEach items="${billDsmInfo}" var="ele"><b>
                        Bill Issue Date: <fmt:formatDate value="${ele.billingDate}" pattern="dd-MM-yyyy" />&nbsp; Week No.:${ele.weekId}</b></p>
                <p style="text-align:center;">DETAILS OF DSM CHARGES FOR THE WEEK FROM <b><fmt:formatDate value="${ele.weekFromdate}" pattern="dd-MM-yyyy" /></b> TO <b><fmt:formatDate value="${ele.weekTodate}" pattern="dd-MM-yyyy" /></b></p>
            </c:forEach>
            <p style="text-align:right; margin-right: 18px;"><i>(All figures in Rs.)</i></p>
            <table align="center" class="customerTable"  style="width:90%;">
                <thead style="color:darkslategrey;height:30px;">
                    <tr>
                        <th>S. No. </th>
                        <th>Entity</th>
                        <th>DSM Charges Payable</th>
                        <th>DSM Charges Receivable</th>
                        <th>Capping DSM Charges</th>
                        <th>Additional DSM Charges</th>
                        <th>Sign Violation Charges</th>
                        <th>Net DSM Payable/Receivable </th>
                    </tr>

                </thead>
                <tbody>
                    <c:forEach items="${entityTypeListPay}" var="objectList2">
                        <tr><td colspan="8" style="background-color: linen;color:black;text-align: center;">INTER REGIONAL</td></tr>
                        <c:set var="serialno" value="${0}"/>
                        <c:forEach items="${billPay11}" var="objectList1">
                            <c:set var="count" value="${0}" />
                            <tr>
                                <c:set var="serialno" value="${serialno + 1}" />
                                <td><c:out value="${serialno}"/></td>
                                <c:forEach items="${objectList1}" var="object1">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${objectList2.toString() == 'INTER REGIONAL'  }">


                                        </c:if>
                                    </c:if>
                                    <td>${object1.toString()}</td>
                                    <c:if test="${count eq 3  }">
                                        <td>-</td>
                                    </c:if>


                                </c:forEach>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${billReceive11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() == 'INTER REGIONAL'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1  }">
                                        <c:if test="${entityType == 'INTER REGIONAL'}">
                                            <c:if test="${count eq 3  }">

                                            </c:if>
                                            <td>${object.toString()}</td>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>

                        <tr><td colspan="8" style="background-color: linen;color:black;text-align: center;">BENEFICIERIES/BUYERS OF WR</td></tr>
                        <c:set var="serialno" value="${0}"/>
                        <c:forEach items="${billPay11}" var="objectList1">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList1}" var="object1">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'BENEFECIERIES/BUYERS WR'  }">
                                            <c:set var="entityType" value="${object1.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'BENEFECIERIES/BUYERS WR' }">
                                        <td>${object1.toString()}</td>
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>
                        <c:forEach items="${billReceive11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'BENEFECIERIES/BUYERS WR'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'BENEFECIERIES/BUYERS WR' }">
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                        <td>${object.toString()}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>


                        <tr><td colspan="8" style="background-color: linen;color:black;text-align: center;">GENERATING STATIONS(TARIFF DETERMINED BY CERC)</td></tr>
                        <c:set var="serialno" value="${0}"/>
                        <c:forEach items="${billPay11}" var="objectList1">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList1}" var="object1">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object1.toString() == 'GENERATING STATIONS'  }">
                                            <c:set var="entityType" value="${object1.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType == 'GENERATING STATIONS' }">
                                        <td>${object1.toString()}</td>
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>
                        <c:forEach items="${billReceive11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() == 'GENERATING STATIONS'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType == 'GENERATING STATIONS' }">
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                        <td>${object.toString()}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>

                        <tr><td colspan="8" style="background-color: linen;color:black;text-align: center;">OTHER GENERATING STATIONS</td></tr>
                        <c:set var="serialno" value="${0}"/>

                        <c:forEach items="${billPay11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'OTHER GENERATING STATIONS'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'OTHER GENERATING STATIONS' }">
                                        <td>${object.toString()}</td>
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>
                        <c:forEach items="${billReceive11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'OTHER GENERATING STATIONS'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'OTHER GENERATING STATIONS' }">
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                        <td>${object.toString()}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>

                        <tr><td colspan="8" style="background-color: linen;color:black;text-align: center;">WIND AND SOLAR GENERATORS</td></tr>
                        <c:set var="serialno" value="${0}"/>
                        <c:forEach items="${billPay11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'WIND AND SOLAR GENERATORS'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'WIND AND SOLAR GENERATORS' }">
                                        <td>${object.toString()}</td>
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>
                        <c:forEach items="${billReceive11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'WIND AND SOLAR GENERATORS'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'WIND AND SOLAR GENERATORS' }">
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                        <td>${object.toString()}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>


                        <tr><td colspan="8" style="background-color: linen;color:black;text-align: center;">INFIRM GENERATORS</td></tr>
                        <c:set var="serialno" value="${0}"/>
                        <c:forEach items="${billPay11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'INFIRM GENERATORS'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'INFIRM GENERATORS' }">
                                        <td>${object.toString()}</td>
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>
                        <c:forEach items="${billReceive11}" var="objectList">
                            <c:set var="count" value="${0}" />
                            <tr>

                                <c:forEach items="${objectList}" var="object">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 1  }">
                                        <c:if test="${object.toString() eq 'INFIRM GENERATORS'  }">
                                            <c:set var="entityType" value="${object.toString()}" />
                                            <c:set var="serialno" value="${serialno + 1}" />
                                            <td><c:out value="${serialno}"/></td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${count ne 1 && entityType eq 'INFIRM GENERATORS' }">
                                        <c:if test="${count eq 3  }">
                                            <td>-</td>
                                        </c:if>
                                        <td>${object.toString()}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
            <br/></form>
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
        <br/>
    </body>
</html>

