<%-- 
    Document   : reconciliationnew
    Created on : Nov 25, 2019, 1:58:02 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
                font-size: 18px;
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
            table#bankTable {
                
              border-collapse: collapse;
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
                    "pageLength": 25,
//                    "order": [[0, "asc"]],
                     "order": [],
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
        <!--<table id="payableTable" align="center" style="min-height:400px;width:80%;" class="table table-striped table-bordered">-->
       <form>
        <fieldset>
            <legend> <h3>ALL Transactions of ${corpName}</h3></legend>
        
        <table id="payableTable" align="center" style="min-height:400px;width:81%;" class="roundedCorners">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                <th>CASE</th>
                  <th>WEEK ID</th>
                     <th>BILL TYPE</th>
                       <th>REV NO.</th>
                       <th>TOTAL NET</th>  
                  <th>Payable</th>
                  <th>  PENDING PAID AMOUNT</th>
                    <th>Receivable</th>
                      <th>PENDING DISBURSE AMOUNT</th>
                      <th>STMT_ID--TOTAL_CR--MAPPED</th>
<!--                        <th>TOTAL CR</th>
                         <th>MAPPED</th>-->
                         <th>OUT STANDING</th>
                     
                  
                </tr>
            </thead>
            <tbody>
                <c:set var="count" value="0" scope="page" />
                
                <c:set var="outstanding" value="0" scope="page" />
                
                 <c:set var="REFUND" value="REFUND" scope="page" />
                 
             
                 
                <c:forEach items="${paymain}" var="listm">
                    
               
                           <c:forEach begin="0" end="${listm.revisionNo}" varStatus="loop">
                                      
                       
                             
                                     <c:forEach items="${queryList1}" var="list">
                                     
                                         <c:if test="${list.revisionNo== loop.index && listm.weekId==list.weekId && listm.billType==list.billType && listm.billYear==list.billYear}">
                                            <tr>
                                              <c:if test="${list.billStatus != REFUND}">
                                                    

                                               
                                             <td>Payable Mapping</td>
                                             <td>${list.weekId}</td>
                                              <td>${list.billType}</td>
                                               <td>${list.revisionNo}</td>
                                               <td>${list.totalnet}</td>
                                                <td>${list.revisedpaybale}</td>
                                                 <td>${list.pendingAmount}</td>
                                                 <td> </td>
                                                 <td> </td>
                                                <td>  
                                                                <table>
                                                                    <tbody>
                                                                         <c:forEach items="${queryList2}" var="list1">
                                                                            <c:if test="${list.uniqueId==list1.billPayableCorp.uniqueId}">
                                                                                <tr>
                                                                                    <td>${list1.bankStatement.stmtId}</td>
                                                                                    <%--<c:set var="count" value="${list1.bankStatement.paidAmount}" scope="page" />--%>
                                                                                     <td>${list1.bankStatement.paidAmount}</td>
                                                                                        <td>${list1.mappedAmount}</td>
                                                                                </tr>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                    </table>
                                                    </td>
                                                 
                                    <c:set var="count" value="${list.pendingAmount}" scope="page" />
                                    
                                    
                                             </c:if>
                                                    
                                                    
                                                    
                                                     <c:if test="${list.billStatus == REFUND}">
                                              
                                              <td>Payable Refund</td>            
                                             <td>${list.weekId}</td>
                                              <td>${list.billType}</td>
                                               <td>${list.revisionNo}</td>
                                               <td>${list.totalnet}</td>
                                               <td> </td>
                                                <td> </td>
                                                <td>${list.revisedrefund}</td>
                                                <td>${list.revisedrefund-list.adjustmentAmount}</td>
                                                <td>${list.adjustmentAmount} </td>
                                                        
                                                         
                                                <c:set var="count" value="${-(list.revisedrefund-list.adjustmentAmount)}" scope="page" />
                                       
                                         
                                                     </c:if>
                                                         
                                                         
                                                         
                                                         
                                                         
                                                         
                                    <c:set var="outstanding" value="${outstanding+count}" scope="page" />  
                                    <td>${outstanding} </td>  
                                                <c:if test="${loop.index != listm.revisionNo}">
                                                    <c:set var="outstanding" value="${outstanding-count}" scope="page" />

                                                </c:if>
                                     
                                     
                                     
                                        </tr>
                                        
                                        
                                        </c:if>
                                        
                                        
                                  </c:forEach>
                                        
                    </c:forEach>
 
                    
                </c:forEach>
               
<!--                   <tr><td>${outstanding}</td></tr>    -->
                <c:forEach items="${recmain}" var="listm">
                    <c:forEach begin="0" end="${listm.revisionNo}" varStatus="loop">

                                            <c:forEach items="${queryList3}" var="list2">
                                                
                                                <c:if test="${list2.revisionNo== loop.index && listm.weekId==list2.weekId && listm.billType==list2.billType && listm.billYear==list2.billYear}">
                                                
                                                <tr>
                                                    
                                                    
                                          <c:if test="${list2.disburseStatus != REFUND}">
                                              
                                              <td>Receivable Mapping</td>       
                                                 <td>${list2.weekId}</td>
                                                  <td>${list2.billType}</td>
                                                   <td>${list2.revisionNo}</td>
                                                   <td>${list2.toalnet}</td>
                                                     <td> </td>
                                                     <td> </td>
                                                   <td>${list2.revisedpaybale}</td>
                                                     <td>${list2.pendingAmount}</td>                           
                                                     <td>${list2.disburseAmount} </td>

                                           <c:set var="count" value="${list2.pendingAmount}" scope="page" />    
                                           
                                           </c:if>
                                           
                                         <c:if test="${list2.disburseStatus == REFUND}">
                                             
                                             <td>Receivable Refund</td>        
                                             <td>${list2.weekId}</td>
                                                  <td>${list2.billType}</td>
                                                   <td>${list2.revisionNo}</td>
                                                   <td>${list2.toalnet}</td>
                                                   <td>${list2.revisedrefund}</td>
                                                <td>${list2.revisedrefund-list2.adjustmentAmount}</td>
                                                <td> </td>
                                                     <td> </td>
                                                
                                                  <td> ${list2.adjustmentAmount}</td>
                                                                                                    <c:set var="count" value="${-(list.revisedrefund-list.adjustmentAmount)}" scope="page" />
                                                <c:set var="count" value="${-(list2.revisedrefund-list2.adjustmentAmount)}" scope="page" />

                                         </c:if>
                                                    
                                                    
                                    <c:set var="outstanding" value="${outstanding-count}" scope="page" />  
                                    <td>${outstanding} </td>  
                                    <c:if test="${loop.index != listm.revisionNo}">
                                        <c:set var="outstanding" value="${outstanding+count}" scope="page" />
                                     
                                    </c:if>
                                     
                                     
                                      </tr>
                                     
                                              
                                       </c:if>          
                                             
                                         </c:forEach>
                        
                        
                    </c:forEach>
                                       
                 </c:forEach>      
                                              
            <p>&nbsp;</p>  
            <p>&nbsp;</p>
        </tbody>
        
  
    </table>
            
            <table> 
           <tr><td>FINAL OUTSTANDING = </td>
               <td>${outstanding}</td>
           </tr>
            </table>
             </fieldset>
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

