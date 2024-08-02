<%-- 
    Document   : verifyUploadTRASM
    Created on : 29 Aug, 2023, 10:35:33 AM
    Author     : root
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
                            title: 'TRAS-M Bill billNo.' + billNo + ' Week No. ' + weekId + 'Issue Date:' + billingDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n         Week No. ' + weekId + '      Bill Issue Date:' + billingDate +
                                        '\r\n \r\n DETAILS OF TRAS-M CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
                                        '\r\n \r\n (All figures in Rs.)';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'TRAS-M Bill billNo.' + billNo + ' Week No. ' + weekId + 'Issue Date:' + billingDate,
                            orientation: 'landscape',
                            messageTop: function () {
                                return '\r\n         Week No. ' + weekId + '      Bill Issue Date:' + billingDate +
                                        '\r\n \r\n DETAILS OF TRAS-M CHARGES FOR THE WEEK FROM ' + fromDate + '  TO ' + toDate +
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

                        for (var k = 2; k <= 16; k++) {
                            // Total over all pages
                            total = api
                                    .column(k)
                                    .data()
                                    .reduce(function (a, b) {
                                        return intVal(a) + intVal(b);
                                    }, 0);
                            // Total over this page
                            pageTotal = api
                                    .column(k, {page: 'current'})
                                    .data()
                                    .reduce(function (a, b) {
                                        return intVal(a) + intVal(b);
                                    }, 0);
                            // Update footer
                            $(api.column(k).footer()).html(
                                    pageTotal.toFixed(2)
                                    );
                        }
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
                } else
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
                } else
                {
                    return false;
                }


                return true;
            }
        </script>

    </head>
    <body style="min-height: 1500px; width: 95%">
        <form method="post">
            <div>

                <input type="text" name="weekid" id="weekid" value="${weekid}" hidden/>
            </div>
            <h3 align="center" style="color: #269abc;"> Verify the Uploaded Entries</h3>
            <!--<h4>  <div>  <div style="float: left;">AGC Bill No :&emsp;${billNo} </div> <div style="float: right;">Date :&emsp;<fmt:formatDate pattern = "dd-MM-yyyy" value = "${billingDate}" /></div>   </div>   </h4>-->
            <h4 style="color: #d43f3a;">TRAS-M  Bill No :&emsp;${tempagcdetils.get(0).billNo}</h4>

            <h4 style="color: #d43f3a;">Bill Issued Date:&emsp;<fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempagcdetils.get(0).billingDate}" /></h4>
            <h4 style="color: #d43f3a;">Revision No. &emsp;${tempagcdetils.get(0).revisionNo}</h4>
            <h4 style="color: #d43f3a;">Week No. &emsp;${tempagcdetils.get(0).weekId}</h4>
            <h4 style="color: #d43f3a;">Bill Due Date &emsp;<fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempagcdetils.get(0).billDueDate}" /></h4>

            <table style="color:#003366; width: 90%;" align="center"  border="yes">
                <tr><td align="center"><b> Details of TRAS-M Charges for week From <fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempagcdetils.get(0).weekFromdate}" /> To <fmt:formatDate pattern = "dd-MM-yyyy" value = "${tempagcdetils.get(0).weekTodate}" /></b> </td></tr>

            </table>
            <table id="InterestreportDatatable" style="color:#003366; width: 90%;" align="center"  border="yes">
                <thead style="min-height: 15px;height: 30px;">
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
                    <c:set var="serialno" value="${0}"/>

                    <c:forEach items="${tempPayRecList}" var="interest">
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

                        <th style="text-align:right">Total:</th>

                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                         <th></th>
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
