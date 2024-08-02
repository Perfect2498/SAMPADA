<%-- 
    Document   : viewfullDSMbills
    Created on : 9 Aug, 2020, 6:58:05 PM
    Author     : Kaustubh
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="sampada.pojo.BillPayableEntityDsm"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script|Lobster|Rock+Salt|Satisfy&display=swap" rel="stylesheet">

        <style>
            table, td, th {  
                border: 1px solid #ddd;
                text-align: left;
            }

            table {
                border-collapse: collapse;
                width: 100%;
            }
            th{
                font-family:'Kaushan Script', cursive;
                font-size: 17px;
            }
            td {
                font-size: 18px;
            }
            th, td {
                padding: 7px;
                width: 152px;
            }

            input{
                width:50px;
            }
            select{
                width:80px;
            }

        </style>
        <style>
             table.roundedCorners { 
                border: 2px solid DarkOrange;
                border-radius: 13px; 
                border-spacing: 0;
            }
             table.roundedCorners th { 
                border: 2px solid  #964000;
                padding: 10px; 
                background-color: #964000;
                color:white;
                
            }
            table.roundedCorners td 
       { 
                border: 2px solid DarkOrange;
                padding: 10px; 
                 background-color: #FBF2EA;
           
            }
            thead,tr,td {
                color:#003366;
            }
            p {
                color:black;
            }
            .btn{
                background-color:DarkOrange;
                color:#603311;
            }
            table.dataTable thead span.sort-icon {
                display: inline-block;
                padding-left: 5px;
                width: 16px;
                height: 16px;
                color:wheat;
            }

             td.highlight {
                background-color: whitesmoke !important;
            }

            table.dataTable thead .sorting span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_both.png') no-repeat center right; }
            table.dataTable thead .sorting_asc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc.png') no-repeat center right; }
            table.dataTable thead .sorting_desc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc.png') no-repeat center right; }

            table.dataTable thead .sorting_asc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc_disabled.png') no-repeat center right; }
            table.dataTable thead .sorting_desc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc_disabled.png') no-repeat center right; }
             .dataTables_filter input { width: 300px }


        </style>
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
                    "pageLength": 10,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[3, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            title: 'Bills Report',
                            footer : true 
                          
                        },
                          {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            title: 'Bills Report',
                            footer : true
                        }
                    ],
                    
                     "footerCallback" : function ( row, data, start, end, display ) { 
            var api = this.api(), data; 

            // Remove the formatting to get integer data for summation 
            var intVal = function ( i ) { 
                return typeof i === 'string' ? 
                    i.replace(/[\$,]/g, '')*1 : 
                    typeof i === 'number' ? 
                        i : 0; 
            }; 

            // Total over all pages 
        $( api.column( 0 ).footer() ).html( 
              ' Total (Rs.)' 
            ); 
                    
   
    
     
      total = api 
                .column( 5 ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Total over this page 
            pageTotal = api 
                .column( 5, { page: 'current'} ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Update footer 
            $( api.column( 5 ).footer() ).html( 
                pageTotal 
            ); 
     
      total = api 
                .column( 6 ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Total over this page 
            pageTotal = api 
                .column( 6, { page: 'current'} ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Update footer 
            $( api.column( 6 ).footer() ).html( 
               pageTotal 
            ); 
     
      total = api 
                .column( 7 ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Total over this page 
            pageTotal = api 
                .column( 7, { page: 'current'} ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Update footer 
            $( api.column( 7 ).footer() ).html( 
               pageTotal 
            ); 
     
      total = api 
                .column( 8 ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Total over this page 
            pageTotal = api 
                .column( 8, { page: 'current'} ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Update footer 
            $( api.column( 8 ).footer() ).html( 
              pageTotal 
            ); 
    
      total = api 
                .column( 9 ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Total over this page 
            pageTotal = api 
                .column( 9, { page: 'current'} ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Update footer 
            $( api.column( 9 ).footer() ).html( 
              pageTotal 
            ); 
    
     total = api 
                .column( 10 ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Total over this page 
            pageTotal = api 
                .column( 10, { page: 'current'} ) 
                .data() 
                .reduce( function (a, b) { 
                    return intVal(a) + intVal(b); 
                }, 0 ); 

            // Update footer 
            $( api.column( 10 ).footer() ).html( 
              pageTotal 
            ); 
   
    }  
                });

                table.columns().iterator('column', function (ctx, idx) {
                    $(table.column(idx).header()).append('<span class="sort-icon"/>');
                });

                $('#payableTable tbody')
                        .on('mouseenter', 'td', function () {
                            var colIdx = table.cell(this).index().column;

                            $(table.cells().nodes()).removeClass('highlight');
                            $(table.column(colIdx).nodes()).addClass('highlight');
                        });

            });
        </script> 
        
        
    </head>
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" > 
        <h3 align="center" style="color:#003366;">From Week-id ${week_id1} to Week-id ${week_id2} of year ${yeari}</h3>
        <h2 style="color:black;">Full DSM Bills NLDC. &nbsp;&nbsp; (All figures in Rs.) &nbsp;&nbsp; As per Latest Revision of Bills.</h2>
       <form>
        <table id="payableTable" align="center" style="min-height:400px;width:81%;" class="roundedCorners" cellpadding="0">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr align="center">
                    <th>S.No.</th>
                    <th>ENTITY NAME</th>
                    <th>BILL YEAR</th>
                    <th>WEEK_ID</th>
                    <th>Rev_NO </th>
                    <th>DSM CHARGES PAYABLE</th>
                    <th>DSM CHARGES RECEIVABLE</th>
                    <th>CAPPING DSM CHARGES </th>
                    <th>ADDL. DSM CHARGES</th>
                    <th>SIGN VIOLATION</th>
                    <th>NET DSM PAYABLE/ RECEIVABLE</th> 
                </tr>
            </thead>
            <tbody align="center">
                 <c:forEach items="${queryList}" var="list" varStatus="iteration">
                        <tr>
                            <td>${iteration.index + 1}</td>
                            <td>${list.entites.entittyName}</td>
                            <%
                                BillPayableEntityDsm obj = (BillPayableEntityDsm) pageContext.getAttribute("list");
                                BigDecimal byear = obj.getBillYear();
                                String year = byear.toPlainString();
                                Integer temp = Integer.valueOf(year.substring(2, 4))+1;
                                String billYear = year+"-"+temp;
                            %>
                             <td><%=billYear%></td> 
                             <td>${list.weekId}</td> 
                            <td>${list.revisionNo}</td>
                            <td>${list.payableCharges}</td>
                            <td>${list.receivableCharges}</td>
                           <td>${list.cappingCharges}</td>
                            <td>${list.additionalCharges}</td> 
                            <td>${list.signCharges}</td> 
                            <td>${list.netDsm}</td> 
                         </tr>
                    </c:forEach>      
            <p>&nbsp;</p>  
            <p>&nbsp;</p>
        </tbody>
         <tfoot> 
        <tr> 
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
             
        </form>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
          <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
      <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
</body>
</html>