<%-- 
    Document   : billProcessingPendingPayableList
    Created on : Jul 10, 2019, 8:33:46 AM
    Author     : JaganMohan
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
        
        <table id="payableTable" align="center" style="min-height:400px;width:81%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                  <th>WEEK ID</th>
                        <th>BILL YEAR</th>
                        <th>BILL TYPE</th>
                        <th>REVISION NO</th>
                        <th>TOTAL NET</th>
                        <th>PAID AMOUNT</th>
                         <th>  PENDING AMOUNT</th>
                          <th>   REVISED REFUND</th>
                         
                          
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${queryList1}" var="list">
                        <tr>
                            
                          <c:forEach items="${list}" var="object">
                            <td>${object.toString()}</td>  
                            </c:forEach> 
                            
                         </tr>
                    </c:forEach>
            <p>&nbsp;</p>  
            <p>&nbsp;</p>
        </tbody>
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

