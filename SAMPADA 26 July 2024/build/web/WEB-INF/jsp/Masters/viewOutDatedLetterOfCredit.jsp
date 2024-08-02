<%--
    Document   : viewOutDatedLetterOfCredit
    Created on : Dec 27, 2019, 5:00:08 PM
    Author     : JaganMohan
--%>
 
 
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
 
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
      
<!--       <style>
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
 
 
       </style>-->
       <script>
           $(document).ready(function () {
 
            
                    
                     var table = $('#disburseTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
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
               var rowcnt = document.getElementById("count").value;
               if (rowcnt == 0)
               {
                   return false;
               }
               else
               {
                   if (confirm("Are you want to Confirm !!!!!"))
                   {
                       return true;
                   }
                   else
                   {
                       return false;
                   }
               }
 
 
           }
 
 
 
 
           function validate1()
           {
               var rowcnt = document.getElementById("count").value;
               if (rowcnt == 0)
               {
                   return false;
               }
               else
               {
                   if (confirm("Are you want to Delete !!!!!"))
                   {
                       return true;
                   }
                   else
                   {
                       return false;
                   }
               }
           }
       </script>
   </head>
   <body width="80%">
       <form name="checkerForm">
         
           <h3 align="center" style="color:#003366;" >List of Default Corporates Details </h3>
 
           <table align="center" id="disburseTable" style="min-height:400px;width:80%;" class="customerTable">
               <thead style="background-color: #0677A1;color: white;height:30px;">
                   <tr>
                       <th>Corporate Name</th>
                       <th>Week ID</th>
                       <th>Bill Type</th>
                       <th>Category</th>
                       <th>Bill Amount</th>
                       <th>Year</th>
                       <th>Due Date</th>
                   </tr>
               </thead>
               <tbody>
                
                   <c:forEach items="${UserLCList}" var="ele">                   
                       <tr>
                                               
                           <td>${ele.corporates.corporateName}</td>
                           <td>${ele.weekid}</td>
                           <td>${ele.billType}</td>
                           <td>${ele.billCategory}</td>
                           <td>${ele.billAmount}</td>
                           <td>${ele.billYear}</td>
                           <td>${ele.billPayableCorp.billDueDate}</td>
                       </tr>
 
 
                   </c:forEach>
               </tbody>
             
           </table>        
 
          <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>
       </form>
   </body>
 
</html>
 
