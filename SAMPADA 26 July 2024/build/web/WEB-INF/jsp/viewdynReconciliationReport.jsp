<%-- 
    Document   : viewdynReconciliationReport
    Created on : Feb 5, 2021, 5:27:02 PM
    Author     : Administrator
--%>




<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>







<!DOCTYPE html>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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

        <script>
            $(document).ready(function () {

                var table = $('#reconTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
//                    "order": [[0, "asc"]],
                    "ordering": false,
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
//                            pageSize: 'LEGAL' ,
                            title: 'Reconciliation Report :',
                            footer: true
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            pageSize: 'A3',
                            orientation: 'landscape',
                            title: 'Reconciliation Report :',
                            footer: true
                        }
                    ],
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
        <style>
            .makedisable {
                pointer-events: none;
            }
        </style>
    </head>
    <body style="color:#003366;">
        <form>
            <h4 align="center">Reconciliation for Pool Member :${corpName}</h4>
            <!--<h4 align="center">Former Out Standing Amount is :${outstanding}</h4>-->

            <h4 align="center">Default Sorting Sequence Is As Per Event Date</h4>

            <table width="100%" cellspacing="0" id="reconTable" class="customerTable">
                <thead>
                    <tr><th>Bill Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>Calculation Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>Bank Portion</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th rowspan="2">Outstanding</th><th rowspan="2">REMARKS</th></tr>
                    <tr>
                        <th class="makedisable">Week ID</th>
                        <th class="makedisable">Event Date </th>
                        <th class="makedisable">Bill Type</th>
                        <th class="makedisable">Bill Date</th>
                        <th class="makedisable">Revision No.</th>
                        <th class="makedisable">Bill Year</th>
                        <th class="makedisable">Due + Grace Period</th>
                        <th class="makedisable">Payable asper Bill</th>
                        <th class="makedisable">Receivable as per Bill</th>
                        <!-- <th>&nbsp;</th>-->


                        <th class="makedisable">Pay. Difference wrt Rev.</th>
                        <th class="makedisable">Payable Pending</th>
                        <th class="makedisable">Rec. Difference wrt Rev.</th>
                        <th class="makedisable">Receivable Pending</th>
                        <!-- <th>&nbsp;</th>-->


                        <th class="makedisable">Bank CR/DR DATE</th>
                        <th class="makedisable">CR_AMOUNT</th>
                        <th class="makedisable">CR AMOUNT Settled</th>
                        <th class="makedisable">CR_AVAILABLE after settled</th>
                        <th class="makedisable">DR_AMOUNT</th>
                        <th class="makedisable">DR AMOUNT Settled</th>
                        <!--<th>&nbsp;</th>-->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${dynreconlist}" var="eleref">
                            <tr>

                                <td>${eleref.weekId}</td>
                                <td>${eleref.billEntryDate}</td>
                                <td>${eleref.billType}</td>
                                <td>${eleref.billingDate}</td>
                                <td>${eleref.revisionNo}</td>
                                <td>${eleref.billYear}</td>
                                <td>${eleref.billDueDate}</td>                                
                                <td>${eleref.payTotalnet}</td>
                                <td>${eleref.recTotalnet}</td>
                                <td>${eleref.payFinalamount}</td>
                                <td>${eleref.payPendingamount}</td>
                                <td>${eleref.recFinalamount}</td>
                                <td>${eleref.recPendingamount}</td>
                                <td>${eleref.crDrDate}</td>
                                <td>${eleref.crAmount}</td>
                                <td>${eleref.crSettledAmount}</td>
                                <td>${eleref.crAvailable}</td>
                                <td>${eleref.drAmount}</td>
                                <td>${eleref.drSettledAmount}</td>
                                <td>${eleref.outstandingAmount}</td>
                                <td>${eleref.remarks}</td>
                                
                                
                                
                                
                            </tr>                        
                    </c:forEach>






                </tbody>

            </table>


        </form>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <br/>
    </body>

    <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>
</html>
