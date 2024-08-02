<%-- 
    Document   : viewMakerPendingPayableListbyRLDC
    Created on : Jul 17, 2019, 9:37:51 AM
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


    </head>
    <body style="text-align: center; alignment-adjust: central;width: 95%;">
        <form>
            <h3 align="center" style="color:#003366;">View Mapping Processed by Maker, available to Checker for Verification</h3>

            <table align="center" style="width:50%;" id="payableTable" class="customerTable">
                <thead style="height:72px;">
                    <tr> 
                        <th>Pool Member Id</th>    
                        <th>Pool Member Name</th>                               
                    </tr>
                </thead>
                <tbody>

                    <c:forEach items="${corpPendingList}" var="innerList">              
                        <tr>
                            <c:set var="count" value="${0}" />
                            <c:forEach items="${innerList}" var="item">
                                <c:choose>
                                    <c:when test="${count eq 0}">
                                        <td><a href="<c:url value='viewMakerPendingPayableBillbyRLDC.htm'> 
                                                   <c:param name="corpID" value="${item}"/>  
                                               </c:url>" >${item}</a> 
                                            <c:set var="count" value="${count+1}"/>
                                        </td>  
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            ${item}
                                        </td>  
                                    </c:otherwise>


                                </c:choose>

                            </c:forEach>

                        </tr>

                    </c:forEach>  

                </tbody>             
            </table>


        </form>
        <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>

    </body>
</html>
