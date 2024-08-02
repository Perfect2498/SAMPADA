<%-- 
    Document   : Annexuredetail
    Created on : 11 Jun, 2020, 8:48:27 PM
    Author     : Kaustubh
--%>

<%@page import="sampada.pojo.BillReceiveCorp"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="sampada.pojo.BillPayableCorp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/css/buttons.dataTables.min.css"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>
    </head>
            <script>

function fnExcelReport()
{
    var tab_text="<html><head><style>table,td{border:1px solid black}</style></head><table><tr>";
    var textRange; var j=0;
    tab = document.getElementById('parenttable'); // id of table

    for(j = 0 ; j < tab.rows.length ; j++) 
    {     
        tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
        //tab_text=tab_text+"</tr>";
    }

    tab_text=tab_text+"</table>";
    tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
    tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa=txtArea1.document.execCommand("SaveAs",true,"Annexure-Pool_Account.xlsx");
    }  
    else                 //other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));  

    return (sa);
}
        </script>
    <script>
            $(document).ready(function() {
               $('#tbl1').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 4',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 4: DSM amount due to be received',
                            orientation: 'landscape'
                        }
                    ]
                } );
                
                $('#tbl2').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 5',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 5: DSM amount due to be paid',
                            orientation: 'landscape'
                        }
                    ]
                } );
                
                $('#tbl3').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 6',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 6: RRAS amount due to be received',
                            orientation: 'landscape'
                        }
                    ]
                } );
                
                $('#tbl4').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 7',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 7: RRAS amount due to be paid',
                            orientation: 'landscape'
                        }
                    ]
                } );
                
                $('#tbl5').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 8',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 8: AGC amount due to be received',
                            orientation: 'landscape'
                        }
                    ]
                } );
                
                $('#tbl6').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 9',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 9: AGC amount due to be paid',
                            orientation: 'landscape'
                        }
                    ]
                } );
                
                $('#tbl7').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 10',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 10: FRAS amount due to be received',
                            orientation: 'landscape'
                        }
                    ]
                } );
                
                $('#tbl8').DataTable( {
                    responsive: true,
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    buttons: [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Annexure 11',
                            orientation: 'portrait'
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Annexure 11: FRAS amount due to be paid',
                            orientation: 'landscape'
                        }
                    ]
                } );
            });
    </script>
    <body>
        <br>
        <pre>                  <button onclick="fnExcelReport()" style="height: 40px; width: 150px;">Excel Export</button></pre>
        <table align="center" id="parenttable">
            <tr>
            <td>
        <h2 style="color: black">Annexure 4: DSM amount due to be received</h2>
        <table class="customerTable" cellspacing="0" id="tbl1" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${DSMpay}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillPayableCorp obj = (BillPayableCorp) pageContext.getAttribute("sd");
                        BigDecimal byear = obj.getBillYear();
                        String year = byear.toPlainString();
                        Integer temp = Integer.valueOf(year.substring(2, 4))+1;
                        String billYear = year+"-"+temp;
                    %>
                    <td><%=billYear%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${DSMpaytotal}</b></td>
            <td></td>
            </tfoot>
        </table>
        <br><br>
        
        <h2 style="color: black">Annexure 5: DSM amount due to be paid</h2>
        <table class="customerTable" cellspacing="0" id="tbl2" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${DSMrec}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillReceiveCorp obj3 = (BillReceiveCorp) pageContext.getAttribute("sd");
                        BigDecimal byear3 = obj3.getBillYear();
                        String year3 = byear3.toPlainString();
                        Integer temp3 = Integer.valueOf(year3.substring(2, 4))+1;
                        String billYear3 = year3+"-"+temp3;
                    %>
                    <td><%=billYear3%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                            
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${DSMrectotal}</b></td>
            <td></td>
            </tfoot>
        </table>
        <br><br>
        
        <h2 style="color: black">Annexure 6: RRAS amount due to be received</h2>
        <table class="customerTable" cellspacing="0" id="tbl3" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${RRASpay}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillPayableCorp obj1 = (BillPayableCorp) pageContext.getAttribute("sd");
                        BigDecimal byear1 = obj1.getBillYear();
                        String year1 = byear1.toPlainString();
                        Integer temp1 = Integer.valueOf(year1.substring(2, 4))+1;
                        String billYear1 = year1+"-"+temp1;
                    %>
                    <td><%=billYear1%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${RRASpaytotal}</b></td>
            <td></td>
            </tfoot>
        </table>
        <br><br>
        
        <h2 style="color: black">Annexure 7: RRAS amount due to be paid</h2>
        <table class="customerTable" cellspacing="0" id="tbl4" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${RRASrec}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillReceiveCorp obj4 = (BillReceiveCorp) pageContext.getAttribute("sd");
                        BigDecimal byear4 = obj4.getBillYear();
                        String year4 = byear4.toPlainString();
                        Integer temp4 = Integer.valueOf(year4.substring(2, 4))+1;
                        String billYear4 = year4+"-"+temp4;
                    %>
                    <td><%=billYear4%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${RRASrectotal}</b></td>
            <td></td>
            </tfoot>
        </table>
        <br><br>
        
        <h2 style="color: black">Annexure 8: AGC amount due to be received</h2>
        <table class="customerTable" cellspacing="0" id="tbl5" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${AGCpay}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillPayableCorp obj2 = (BillPayableCorp) pageContext.getAttribute("sd");
                        BigDecimal byear2 = obj2.getBillYear();
                        String year2 = byear2.toPlainString();
                        Integer temp2 = Integer.valueOf(year2.substring(2, 4))+1;
                        String billYear2 = year2+"-"+temp2;
                    %>
                    <td><%=billYear2%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${AGCpaytotal}</b></td>
            <td></td>
            </tfoot>
        </table>
        <br><br>
        
        <h2 style="color: black">Annexure 9: AGC amount due to be paid</h2>
        <table class="customerTable" cellspacing="0" id="tbl6" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${AGCrec}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillReceiveCorp obj5 = (BillReceiveCorp) pageContext.getAttribute("sd");
                        BigDecimal byear5 = obj5.getBillYear();
                        String year5 = byear5.toPlainString();
                        Integer temp5 = Integer.valueOf(year5.substring(2, 4))+1;
                        String billYear5 = year5+"-"+temp5;
                    %>
                    <td><%=billYear5%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${AGCrectotal}</b></td>
            <td></td>
            </tfoot>
        </table>
        <br><br>
        
        <h2 style="color: black">Annexure 10: FRAS amount due to be received</h2>
        <table class="customerTable" cellspacing="0" id="tbl7" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${FRASpay}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillPayableCorp obj6 = (BillPayableCorp) pageContext.getAttribute("sd");
                        BigDecimal byear6 = obj6.getBillYear();
                        String year6 = byear6.toPlainString();
                        Integer temp6 = Integer.valueOf(year6.substring(2, 4))+1;
                        String billYear6 = year6+"-"+temp6;
                    %>
                    <td><%=billYear6%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${FRASpaytotal}</b></td>
            <td></td>
            </tfoot>
        </table>    
        <br><br>
        
        <h2 style="color: black">Annexure 11: FRAS amount due to be paid</h2>
        <table class="customerTable" cellspacing="0" id="tbl8" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${FRASrec}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillReceiveCorp obj7 = (BillReceiveCorp) pageContext.getAttribute("sd");
                        BigDecimal byear7 = obj7.getBillYear();
                        String year7 = byear7.toPlainString();
                        Integer temp7 = Integer.valueOf(year7.substring(2, 4))+1;
                        String billYear7 = year7+"-"+temp7;
                    %>
                    <td><%=billYear7%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${FRASrectotal}</b></td>
            <td></td>
            </tfoot>
        </table>  
            
            
            
            <br><br>
        
        <h2 style="color: black">Annexure 12: SRAS amount due to be received</h2>
        <table class="customerTable" cellspacing="0" id="tbl7" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${SRASpay}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillPayableCorp obj8 = (BillPayableCorp) pageContext.getAttribute("sd");
                        BigDecimal byear8 = obj8.getBillYear();
                        String year8 = byear8.toPlainString();
                        Integer temp8 = Integer.valueOf(year8.substring(2, 4))+1;
                        String billYear8 = year8+"-"+temp8;
                    %>
                    <td><%=billYear8%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${SRASpaytotal}</b></td>
            <td></td>
            </tfoot>
        </table>    
        <br><br>
        
        <h2 style="color: black">Annexure 13: SRAS amount due to be paid</h2>
        <table class="customerTable" cellspacing="0" id="tbl8" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${SRASrec}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillReceiveCorp obj9 = (BillReceiveCorp) pageContext.getAttribute("sd");
                        BigDecimal byear9 = obj9.getBillYear();
                        String year9 = byear9.toPlainString();
                        Integer temp9 = Integer.valueOf(year9.substring(2, 4))+1;
                        String billYear9 = year9+"-"+temp9;
                    %>
                    <td><%=billYear9%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${SRASrectotal}</b></td>
            <td></td>
            </tfoot>
        </table>  
            
            
            <br><br>
        
        <h2 style="color: black">Annexure 14: TRAS amount due to be received</h2>
        <table class="customerTable" cellspacing="0" id="tbl7" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${TRASpay}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillPayableCorp obj10 = (BillPayableCorp) pageContext.getAttribute("sd");
                        BigDecimal byear10 = obj10.getBillYear();
                        String year10 = byear10.toPlainString();
                        Integer temp10 = Integer.valueOf(year10.substring(2, 4))+1;
                        String billYear10 = year10+"-"+temp10;
                    %>
                    <td><%=billYear10%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${TRASpaytotal}</b></td>
            <td></td>
            </tfoot>
        </table>    
        <br><br>
        
        <h2 style="color: black">Annexure 15: TRAS amount due to be paid</h2>
        <table class="customerTable" cellspacing="0" id="tbl8" align="center">
            <thead>
            <th>Name of Parties</th>
            <th>Bill Year</th>
            <th>Due date</th>
            <th>Amount Rs.</th>
            <th>Remarks</th>
            </thead>
            <tbody>
                <c:forEach items="${TRASrec}" var="sd">
                    <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <%
                        BillReceiveCorp obj11 = (BillReceiveCorp) pageContext.getAttribute("sd");
                        BigDecimal byear11 = obj11.getBillYear();
                        String year11 = byear11.toPlainString();
                        Integer temp11 = Integer.valueOf(year11.substring(2, 4))+1;
                        String billYear11 = year11+"-"+temp11;
                    %>
                    <td><%=billYear11%></td>
                    <td>${sd.billDueDate}</td>
                    <td>${sd.pendingAmount}</td>
                    
                    <c:choose>
                        <c:when test="${sd.revisionNo>0}">
                            <td>WEEK ${sd.weekId} R${sd.revisionNo}</td>
                        </c:when>
                        <c:otherwise>
                            <td>WEEK ${sd.weekId}</td>
                        </c:otherwise>
                    </c:choose>
                    
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <td><b>TOTAL Rs.</b></td>
            <td></td>
            <td></td>
            <td align="center"><b>${TRASrectotal}</b></td>
            <td></td>
            </tfoot>
        </table>  
            
        </td>
        </tr>
        </table>
        <br><br>
        
        <br><br>
        <br><br>
        <br>
    </body>
</html>
