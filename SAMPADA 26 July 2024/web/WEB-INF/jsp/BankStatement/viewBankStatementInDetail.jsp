<%--
    Document   : viewBankStatementInDetail
    Created on : Jul 22, 2019, 6:08:53 PM
    Author     : cdac
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
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
            $(document).ready(function () {

                var table = $('#bankStmt').DataTable({
                    responsive: true,
                    "pageLength": 10,
                     "lengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
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
                    ],
                    "footerCallback": function (row, data, start, end, display) {
                        var api = this.api(), data;

                        // Remove the formatting to get integer data for summation
                        var intVal = function (i) {
                            return typeof i === 'string' ?
                                    i.replace(/[\$,]/g, '') * 1 :
                                    typeof i === 'number' ?
                                    i : 0;
                        };

                        // Total over all pages
                        $(api.column(0).footer()).html(
                                ' Total (Rs.)'
                                );


                        total = api
                                .column(8)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(8, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(8).footer()).html(
                                pageTotal.toFixed(2)
                                );
                    }
                });

               
               
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
        <script>


            function validate()
            {

                if (confirm("Do you want to Save?"))
                {
                    alert("Bank Statement saved successfully !");
                    return true;
                }
                else
                {
                    return false;
                }

            }


            function validate1()
            {


                if (confirm("Do you want to Delete?"))
                {
                    return true;
                }
                else
                {
                    return false;
                }
                return false;

            }
        </script>

    </head>
    <body style="text-align: center; alignment-adjust: central;width: 95%;min-height: 1500px;">
        <h3 style="color:#003366;">Non-Verified Detailed Bank Statement </h3>
        <form>
            <input type="date" name="fromDate" value="${frmDate}" hidden/>
            <c:set var="serialno" value="${0}" />
            <p style="color:#003366;font-size: 16px;">Transaction Start Date:${frmDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Transaction End Date:${endDate}</p>
           
            <!--nonverifiedrow1-->
            <table align="center" id="bankStmt" class="customerTable"  style="width:90%;">
                <thead style="height:30px;">
                    <tr>
                        <th>S. No.</th>
                        <th>Pool Member Name</th>
                        <th>Bank Sub Account Number</th>
                        <th>Transaction Date</th>
                        <th>Transaction Time</th>
                        <th>Transaction Desc </th>
                        <th>CR/DR Flag</th>
                        <th>Disburse Id</th>
                        <th>Transaction Amount(Rs)</th>
                        <!--<th>Transaction Type</th>-->
                        <!--<th>Opening Balance(Rs) </th>-->
                        <th>Main Balance (Rs)</th>
                        <th>Entry DateTime</th>
                        <th>Remarks</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${stmtInfo}" var="objectList">
                        <c:set var="count" value="${0}" />
                        <tr>
                            <c:set var="serialno" value="${serialno + 1}" />
                            <td><c:out value="${serialno}"/></td>
                            <c:forEach items="${objectList}" var="object">
                                <c:set var="count" value="${count+1}" />

                                <c:if test="${count eq 3  }">
                                    <fmt:formatDate value="${object}" pattern="dd-MM-yyyy" var="billDueDate" />

                                    <td>${billDueDate}</td>

                                </c:if>
                                <c:if test="${count ne 3 && count ne 10 }">
                                    <td>${object.toString()}</td>

                                </c:if>
                                <c:if test="${count eq 10  }">
                                    <fmt:formatDate value="${object}" pattern="dd-MM-yyyy hh:mm:ss" var="billDueDate" />

                                    <td>${billDueDate}</td>

                                </c:if>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td>Total:</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                    </tr>
                </tfoot>
            </table>
            <c:if test="${nonverifiedrow1 !=null}">
            <h2 style="color:#cd0a0a;">Row Numbers Which are Already Exists </h2>
             <table align="center" id="bankStmt" class="customerTable"  style="width:50%;">
                <thead style="height:30px;">
                 <th>Row Numbers</th>
                 </thead>
                <tbody>
                    
                     
                    <c:forEach items="${nonverifiedrow1}" var="nonverlist">
                        <tr>
                    <td>${nonverlist}</td>
                    </tr>
                    </c:forEach>                    
                    
                </tbody>
            </table>
            <h2 style="color:#cd0a0a;">Do you Want to Save</h2>
            </c:if>
            <!--<h3 align="center" style="color:red;">Bank Statement saved Successfully but not Verified !!<br/> Do you want to Delete?</h3>-->
            <p align="center">
                <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bconfirm"  value="Save"/>
                <input style="width:100px;" class="btn" onclick="return validate1();" type="submit" name="bcancel"  value="Delete"/>

            </p>

            <br/>
        </form>
        <br/>
    </body>
</html>

