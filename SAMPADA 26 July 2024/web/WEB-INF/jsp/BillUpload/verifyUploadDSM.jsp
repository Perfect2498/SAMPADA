<%--
    Document   : verifyUploadDSM
    Created on : Jul 1, 2019, 2:30:05 PM
    Author     : shubham
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


        <title>DSM Bill</title>

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
        <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.buttons.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/jquery.dataTables.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/buttons.flash.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/jszip.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/pdfmake.min.js" />">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/vfs_fonts.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/buttons.html5.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/buttons.print.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.fixedHeader.min.js" />">
        </script>


        <script type="text/javascript" >
            $(document).ready(function () {
                var billNo = $("#billNo").val();
                var fromDate = $("#fromDate").val();
                var toDate = $("#toDate").val();
                var weekId = $("#weekId").val();
                var billingDate = $("#billingDate").val();

                $('#InterestreportDatatable').DataTable({
                    fixedHeader: {
                        header: true
                    },
                    "processing": true,
                    paging: false,
                    "searching": false,
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'DSM Bill  billNo.' + billNo + ' Week No. ' + weekId + 'Issue Date:' + billingDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n         Week No. ' + weekId + '      Bill Issue Date:' + billingDate +
                                        '\r\n \r\n DETAILS OF DSM CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'DSM Bill billNo.' + billNo + ' Week No. ' + weekId + 'Issue Date:' + billingDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n         Week No. ' + weekId + '      Bill Issue Date:' + billingDate +
                                        '\r\n \r\n DETAILS OF DSM CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
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
                        $(api.column(1).footer()).html(
                                ' Total '
                                );


                        total = api
                                .column(4)
                                .data()
                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(4, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(4).footer()).html(
                                pageTotal.toFixed(2)
                                );



                        total = api
                                .column(2)
                                .data()
                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(2, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(2).footer()).html(
                                pageTotal.toFixed(2)
                                );



                        total = api
                                .column(3)
                                .data()
                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(3, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {

                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(3).footer()).html(
                                pageTotal.toFixed(2)
                                );


                        total = api
                                .column(5)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(5, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(5).footer()).html(
                                pageTotal.toFixed(2)
                                );

                        total = api
                                .column(6)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(6, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(6).footer()).html(
                                pageTotal.toFixed(2)
                                );


                        total = api
                                .column(7)
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Total over this page
                        pageTotal = api
                                .column(7, {page: 'current'})
                                .data()
                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);
                                }, 0);

                        // Update footer
                        $(api.column(7).footer()).html(
                                pageTotal.toFixed(2)
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

            function validate()
            {

                if (confirm('Are you sure Submit the Entries !'))
                {
                    return true;
                }
                else
                {
                    return false;
                }


                return true;
            }
        </script>

        <script>

            function validate1()
            {

                if (confirm('Do You want to Cancel the Entries !'))
                {
                    return true;
                }
                else
                {
                    return false;
                }


                return true;
            }
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
                ifrm.style.height = getDocHeight(doc) + 1 + "px";
                ifrm.style.visibility = 'visible';

            }

            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 10 + 'px';

            }
        </script>
    </head>
    <body style="width: 95%">
        <form method="post">
            <div>
                <input type="text" name="billNo" id="billNo" value="${billNo}" hidden/>
                <input type="text" name="fromDate" id="fromDate" value="${fromDate}" hidden/>
                <input type="text" name="toDate" id="toDate" value="${toDate}" hidden/>
                <input type="text" name="weekid" id="weekid" value="${weekid}" hidden/>
            </div>
            <h3 align="center" style="color: #269abc;"> Verify the Uploaded DSM Entries</h3>
            <h4 style="color: #d43f3a;">DSM Bill No :&emsp;${tempdsmdetils.get(0).billNo}</h4>
           
            <h4 style="color: #d43f3a;">Bill Issued Date:&emsp;<fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempdsmdetils.get(0).billingDate}" /></h4>
            <h4 style="color: #d43f3a;">Revision No. &emsp;${tempdsmdetils.get(0).revisionNo}</h4>
            <h4 style="color: #d43f3a;">Week No. &emsp;${tempdsmdetils.get(0).weekId}</h4>
            <h4 style="color: #d43f3a;">Bill Due Date &emsp;<fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempdsmdetils.get(0).billDueDate}" /></h4>
            <table style="color:#003366; width: 90%;" align="center"  border="yes">
                <tr><td align="center"><b> Details of DSM Charges for week From <fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempdsmdetils.get(0).weekFromdate}" /> To <fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempdsmdetils.get(0).weekTodate}" /></b> </td></tr>

            </table>
            <table id="InterestreportDatatable" style="color:#003366; width: 90%;" align="center"  border="yes">
                <thead style="min-height: 15px;height: 30px;">
                    <tr>
                        <th>S.No </th>
                        <th>Entity Name</th>
                        <th>DSM Payable Charges</th>
                        <th>DSM ReceivableCharges</th>
                        <th>Capping DSM Charges</th>
                        <th>Addl. DSM Charges</th>
                        <th>Sign Viol. Charges</th>
                        <th>CalculatedNet DSM</th>
                        <th>WRLDC Net DSM</th>

                        <th>WRLDC Remarks</th>

                    </tr>
                </thead>

                <tbody>
                    <c:set var="serialno" value="${0}"/>

                    <c:forEach items="${tempPayList}" var="interest">
                        <tr>

                            <c:set var="serialno" value="${serialno + 1}" />
                            <td><c:out value="${serialno}"/></td>
                            <c:forEach items="${interest}" var="object">

                                <td>${object.toString()}</td>
                            </c:forEach>



                        </tr>
                    </c:forEach>

                    <c:forEach items="${tempRecList}" var="interest">
                        <tr>

                            <c:set var="serialno" value="${serialno + 1}" />
                            <td><c:out value="${serialno}"/></td>
                            <c:forEach items="${interest}" var="object">

                                <td>${object.toString()}</td>
                            </c:forEach>



                        </tr>
                    </c:forEach>


                </tbody>



                <tfoot>
                    <tr>
                        <th></th>
                        <th style="text-align:right">Total:</th>

                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </tfoot>

            </table>
            <table>
                <tr align="center"><td><input type="submit"  name="saveBtn" id="saveBtn" onclick="return validate();" value="&#128190;Save" /></td> <td><input type="submit" name="deleteBtn" id="deleteBtn" onclick="return validate1();" value="&#10006;Cancel" /></td></tr>
            </table>

            <br/>
            <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>
            <br/>
        </form>
        <br/>
        <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>
        <br/>

    </body>
</html>
