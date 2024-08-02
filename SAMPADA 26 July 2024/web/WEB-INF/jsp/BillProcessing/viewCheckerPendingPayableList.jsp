<%--
   Document   : viewCheckerPendingPayableList
   Created on : Jul 11, 2019, 5:25:07 PM
   Author     : JaganMohan

--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
   <head>
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


   </head>
   <body style="text-align: center; alignment-adjust: central;width: 95%;">
       <form>
           <h3 align="center" style="color:#003366;">View Verification of Mapping done by Maker, pending at Checker End</h3>
           <table align="center" id="payableTable" style="width:45%;" class="customerTable">
               <thead style="height:72px;">
                   <tr>
                        <th>Commercial Group Id</th>
                        <th>Commercial Group Name</th>
                    </tr>
               </thead>
               <tbody>
                   <c:forEach var="country" items="${corpPendingList}">
                       <tr>
                           <td><a href="<c:url value='viewCheckerPendingPayableBillbyRLDC.htm'>
                                  <c:param name="corpID" value="${country.value}"/>
                                   </c:url>">${country.value} </a>
                           </td>
                           <td>${country.key}</td>
                       </tr>

                   </c:forEach>
               </tbody>
           </table>
           <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
       </form>
   </body>

</html>