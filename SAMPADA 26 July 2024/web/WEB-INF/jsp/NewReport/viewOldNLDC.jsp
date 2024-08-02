<%-- 
    Document   : viewOldNLDC
    Created on : 25 Jul, 2020, 11:19:17 AM
    Author     : Kaustubh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Old Format NLDC report</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <link type="text/css" href="https://cdn.datatables.net/select/1.3.1/css/select.dataTables.min.css" rel="stylesheet" />
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/css/buttons.dataTables.min.css"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>
        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet">
        <script type="text/javascript" src="https://cdn.datatables.net/fixedcolumns/3.2.1/js/dataTables.fixedColumns.min.js"></script>
        <script>
            $(document).ready(function () {

                var table = $('#NLDCtable').DataTable({
                    responsive: true,
                    select: {
                        style: 'single'
                    },
                    scrollY: "450px",
                    scrollX: true,
                    scrollCollapse: true,
                    paging: true,
                    fixedColumns: {
                        leftColumns: 1
                    },
                    "pageLength": 60,
                    "lengthMenu": [[-1, 25, 100], ["All", 25, 100]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
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

                        for (var k = 3; k <= 44; k++) {
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

                var itr = 0;

                $("#NLDCtable tbody tr").on('click', function (event) {
                    if (itr == 0) {
                        $("#NLDCtable tbody tr").removeClass('row_selected');
                        $(this).addClass('row_selected');
                        itr = 1;
                    }
                    else {
                        $("#NLDCtable tbody tr").removeClass('row_selected');
                        itr = 0;
                    }
                });
            });
        </script>
        <script>

            function fnExcelReport()
            {
                var tab_text = "<html><head><style>table,th,td{border:1px solid black}</style></head><table><tr>";
                var textRange;
                var j = 0;
                tab = document.getElementById('parenttable'); // id of table

                for (j = 0; j < tab.rows.length; j++)
                {
                    tab_text = tab_text + tab.rows[j].innerHTML + "</tr>";
                    //tab_text=tab_text+"</tr>";
                }

                tab_text = tab_text + "</table>";
                tab_text = tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
                tab_text = tab_text.replace(/<img[^>]*>/gi, ""); // remove if u want images in your table
                tab_text = tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

                var ua = window.navigator.userAgent;
                var msie = ua.indexOf("MSIE ");

                if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
                {
                    txtArea1.document.open("txt/html", "replace");
                    txtArea1.document.write(tab_text);
                    txtArea1.document.close();
                    txtArea1.focus();
                    sa = txtArea1.document.execCommand("SaveAs", true, "NLDC_report.xlsx");
                }
                else                 //other browser not tested on IE 11
                    sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));

                return (sa);
            }
        </script>
        <style>
            th, td { white-space: nowrap; }
            div.dataTables_wrapper {
                width: 100%;
                margin: 0 auto;
            }

            tr.row_selected td{background-color: cyan }
        </style>
    </head>
    <body>
        <button onclick="fnExcelReport()" style="height: 40px; width: 150px;">Excel Export</button>
        <div style="overflow-x:auto; white-space: nowrap; max-width: 1500px">

            <table align="center" id="parenttable">
                <tr>
                    <td>
                        <h2 style="color: black" align="center">Old Format 2.1 NLDC Report</h2>

                        <table id="NLDCtable" class="customerTable">
                            <thead>
                                <tr>
                                    <th style="border-color: black; border-width: 1px" rowspan="3">Sr No.</th>
                                    <th style="border-color: black; border-width: 1px" rowspan="3">Week</th>
                                    <th style="border-color: black; border-width: 1px" rowspan="3">Region</th>
                                    <th colspan="8" scope="colgroup" style="border-color: black; border-width: 1px">DSM Charges for Deviation with reference to Pool</th>
                                    <th colspan="8" scope="colgroup" style="border-color: black; border-width: 1px">SRAS Payment</th>
                                    <th colspan="8" scope="colgroup" style="border-color: black; border-width: 1px">TRAS Payment</th>

                                    <th colspan="8" scope="colgroup" style="border-color: black; border-width: 1px">RRAS Payment</th>
                                    <th colspan="8" scope="colgroup" style="border-color: black; border-width: 1px">AGC Payment</th>
                                    <th colspan="2" scope="colgroup" style="border-color: black; border-width: 1px">Net Rs (+)surplus/(-)deficit<br>in pool (Crore)</th>
                                </tr>
                                <tr>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Receivable by Pool</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Payable by Pool</th>
                                    <th colspan="2" scope="colgroup" style="border-color: black; border-width: 1px">Net (+)Surplus/(-)Deficit</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Receivable from SRAS provider</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Payable to SRAS provider</th>
                                    <th colspan="2" scope="colgroup" style="border-color: black; border-width: 1px">Net (+)Surplus/(-)Deficit</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Receivable from TRAS provider</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Payable to TRAS provider</th>
                                    <th colspan="2" scope="colgroup" style="border-color: black; border-width: 1px">Net (+)Surplus/(-)Deficit</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Payable to RRAS provider</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Receivable from RRAS provider</th>
                                    <th colspan="2" scope="colgroup" style="border-color: black; border-width: 1px">Net (+)Surplus/(-)Deficit</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Payable by Pool</th>
                                    <th colspan="3" scope="colgroup" style="border-color: black; border-width: 1px">Receivable by Pool</th>
                                    <th colspan="2" scope="colgroup" style="border-color: black; border-width: 1px">Net (+)Surplus/(-)Deficit</th>
                                    <th style="border-color: black; border-width: 1px"></th>
                                    <th style="border-color: black; border-width: 1px"></th>
                                </tr>
                                <tr>
                                    <th style="border-color: black; border-width: 1px">Receivable (Crore)(A)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Received (Crore)(B)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (C)=(B)-(A)</th>
                                    <th style="border-color: black; border-width: 1px">Payable (Crore)(D)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Paid (Crore)(E)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (F)=(E)-(D)</th>
                                    <th style="border-color: black; border-width: 1px">Net on accrual basis (+ Surplus/-Deficit) Rs (G)=(A)-(D)</th>
                                    <th style="border-color: black; border-width: 1px">Net on cash basis (+ Surplus/-Deficit) Rs (H)=(B)-(E)</th>
                                    
                                    <th style="border-color: black; border-width: 1px">Receivable (Crore)(AS)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Received (Crore)(BS)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (CS)=(BS)-(AS)</th>
                                    <th style="border-color: black; border-width: 1px">Payable (Crore)(DS)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Paid (Crore)(ES)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (FS)=(ES)-(DS)</th>
                                    <th style="border-color: black; border-width: 1px">Net on accrual basis (+ Surplus/-Deficit) Rs (GS)=(AS)-(DS)</th>
                                    <th style="border-color: black; border-width: 1px">Net on cash basis (+ Surplus/-Deficit) Rs (HS)=(BS)-(ES)</th>
                                    
                                    <th style="border-color: black; border-width: 1px">Receivable (Crore)(AT)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Received (Crore)(BT)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (CT)=(BT)-(AT)</th>
                                    <th style="border-color: black; border-width: 1px">Payable (Crore)(DT)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Paid (Crore)(ET)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (FT)=(ET)-(DT)</th>
                                    <th style="border-color: black; border-width: 1px">Net on accrual basis (+ Surplus/-Deficit) Rs (GT)=(AT)-(DT)</th>
                                    <th style="border-color: black; border-width: 1px">Net on cash basis (+ Surplus/-Deficit) Rs (HT)=(BT)-(ET)</th>
                                    
                                    <th style="border-color: black; border-width: 1px">Payable for Regulation Up (Crore)(I)</th>
                                    <th style="border-color: black; border-width: 1px">Actual paid for Regulation Up (Crore)(J)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (K)=(J)-(I)</th>
                                    <th style="border-color: black; border-width: 1px">Receivable for Regulation Down (Crore)(L)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Received for Regulation Down(Crore)(M)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (N)=(M)-(L)</th>
                                    <th style="border-color: black; border-width: 1px">Net on accrual basis (Crore)(N)=(I)-(L)</th>
                                    <th style="border-color: black; border-width: 1px">Net on cash basis (Crore)(O)=(J)-(M)</th>
                                    <th style="border-color: black; border-width: 1px">Payable for Regulation Up (Crore)(P)</th>
                                    <th style="border-color: black; border-width: 1px">Actual paid for Regulation Up (Crore)(Q)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (R)=(Q)-(P)</th>
                                    <th style="border-color: black; border-width: 1px">Receivable for Regulation Down (Crore)(S)</th>
                                    <th style="border-color: black; border-width: 1px">Actual Received for Regulation Down(Crore)(T)</th>
                                    <th style="border-color: black; border-width: 1px">Net(Crore) (U)=(T)-(S)</th>
                                    <th style="border-color: black; border-width: 1px">Net on accrual basis (Crore)(V)=(P)-(S)</th>
                                    <th style="border-color: black; border-width: 1px">Net on cash basis (Crore)(W)=(Q)-(T)</th>
                                    <th style="border-color: black; border-width: 1px">Net (+)surplus/(-)deficit in pool (Crore) on accrual basis (X)=(G)-(GS)-(GT)-(N)-(V)</th>
                                    <th style="border-color: black; border-width: 1px">Net (+)surplus/(-)deficit in pool (Crore) on Cash basis (Y)=(H)-(HS)-(HT)-(O)-(W)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${weeklist}" var="sd" varStatus="itr">
                                    <tr>
                                        <td>${itr.index+1}</td>
                                        <td>Week ${sd} of ${year}-${(year+1)%100}</td>
                                        <td style="background-color: lightgreen"></td>
                                        <td style="background-color: yellow">${A[itr.index]}</td>
                                        <td style="background-color: yellow">${B[itr.index]}</td>
                                        <td>${B[itr.index] - A[itr.index]}</td>
                                        <td style="background-color: yellow">${D[itr.index]}</td>
                                        <td style="background-color: yellow">${E[itr.index]}</td>
                                        <td>${E[itr.index] - D[itr.index]}</td>
                                        <td>${A[itr.index] - D[itr.index]}</td>
                                        <td>${B[itr.index] - E[itr.index]}</td>
                                        
                                        <td style="background-color: yellow">${AS[itr.index]}</td>
                                        <td style="background-color: yellow">${BS[itr.index]}</td>
                                        <td>${BS[itr.index] - AS[itr.index]}</td>
                                        <td style="background-color: yellow">${DS[itr.index]}</td>
                                        <td style="background-color: yellow">${ES[itr.index]}</td>
                                        <td>${ES[itr.index] - DS[itr.index]}</td>
                                        <td>${AS[itr.index] - DS[itr.index]}</td>
                                        <td>${BS[itr.index] - ES[itr.index]}</td>
                                        
                                        <td style="background-color: yellow">${AT[itr.index]}</td>
                                        <td style="background-color: yellow">${BT[itr.index]}</td>
                                        <td>${BT[itr.index] - AT[itr.index]}</td>
                                        <td style="background-color: yellow">${DT[itr.index]}</td>
                                        <td style="background-color: yellow">${ET[itr.index]}</td>
                                        <td>${ET[itr.index] - DT[itr.index]}</td>
                                        <td>${AT[itr.index] - DT[itr.index]}</td>
                                        <td>${BT[itr.index] - ET[itr.index]}</td>
                                        
                                        
                                        <td style="background-color: yellow">${I[itr.index]}</td>
                                        <td style="background-color: yellow">${J[itr.index]}</td>
                                        <td>${J[itr.index] - I[itr.index]}</td>
                                        <td style="background-color: yellow">${L[itr.index]}</td>
                                        <td style="background-color: yellow">${M[itr.index]}</td>
                                        <td>${M[itr.index] - L[itr.index]}</td>
                                        <td>${I[itr.index] - L[itr.index]}</td>
                                        <td>${J[itr.index] - M[itr.index]}</td>
                                        <td style="background-color: yellow">${P[itr.index]}</td>
                                        <td style="background-color: yellow">${Q[itr.index]}</td>
                                        <td>${Q[itr.index] - P[itr.index]}</td>
                                        <td style="background-color: yellow">${S[itr.index]}</td>
                                        <td style="background-color: yellow">${T[itr.index]}</td>
                                        <td>${T[itr.index] - S[itr.index]}</td>
                                        <td>${P[itr.index] - S[itr.index]}</td>
                                        <td>${Q[itr.index] - T[itr.index]}</td>
                                        <td>${(A[itr.index] - D[itr.index]) - (I[itr.index] - L[itr.index]) - (P[itr.index] - S[itr.index])}</td>
                                        <td>${(B[itr.index] - E[itr.index]) - (J[itr.index] - M[itr.index]) - (Q[itr.index] - T[itr.index])}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>Total</td>
                                    <c:forEach begin="1" end="42">
                                        <td></td>
                                    </c:forEach>
                                </tr>
                            </tfoot>
                        </table>
                    </td>
                </tr>
            </table>
        </div>


        <br><br><br>
        <br><br>
        <br><br>
        <br>
    </body>
</html>