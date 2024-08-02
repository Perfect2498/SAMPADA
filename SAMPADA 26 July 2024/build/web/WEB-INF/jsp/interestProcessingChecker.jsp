<%-- 
    Document   : interestProcessingChecker
    Created on : 12 Aug, 2020, 3:36:16 PM
    Author     : Kaustubh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Interest Checker</title>
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
    </head>
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
                var table = $('#itable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true
                        }
                    ]
                });
                
                var table1 = $('#ditable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'excel',
                            text: 'Excel Export',
                            footer: true
                        },
                        {
                            extend: 'pdfHtml5',
                            text: 'Pdf Export',
                            footer: true
                        }
                    ]
                });
            });
        </script>
    <script>
        function val() {
            var res = confirm("Are you sure want to APPROVE these submissions ??");

            if(res==true)
                document.getElementById("form1").submit();
        }

        function val1() {
            var res1 = confirm("Are you sure want to REJECT these submissions ??");

            if(res1==true)
                document.getElementById("form2").submit();
        }
    </script>
    <body>
        <h2 align="center" style="color:#003366;">Submitted Payable Interest Amounts</h2>
        
        <table id="itable" class="customerTable" cellspacing="0" align="center" width="97%">
            <thead>
                <tr>
                    <th>Pool Member Name</th>
                    <th>Week Id</th>
                    <th>Bill Year</th>
                    <th>Bill Type </th>
                    <th>Interest Amount</th>
                    <th>Maker Date</th>
                    <th>Generate-Type</th>
                    <th>Summary Remarks</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${idlist}" var="sd">
                <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <td>${sd.weekId}</td>
                    <td>${sd.billYear}</td>
                    <td>${sd.billType}</td>
                    <td>${sd.interestAmount}</td>
                    <td>${sd.entryDate}</td>
                    <td>${sd.billCategory}</td>
                    <td>${sd.remarks}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        
        <h2 align="center" style="color:#003366;">Submitted Disburse Interest Amounts</h2>
        <table id="ditable" class="customerTable" cellspacing="0" align="center" width="97%">
            <thead>
                <tr>
                    <th>Pool Member Name</th>
                    <th>Week Id</th>
                    <th>Bill Year</th>
                    <th>Bill Type </th>
                    <th>Interest Amount</th>
                    <th>Maker Date</th>
                    <th>Generate-Type</th>
                    <th>Remarks</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${didlist}" var="sd">
                <tr align="center">
                    <td>${sd.corporates.corporateName}</td>
                    <td>${sd.weekId}</td>
                    <td>${sd.billYear}</td>
                    <td>${sd.billType}</td>
                    <td>${sd.interestAmount}</td>
                    <td>${sd.entryDate}</td>
                    <td>${sd.billCategory}</td>
                    <td>${sd.remarks}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
            
        <c:if test="${flag eq 111}">
        
            <div align="center">
                <input type="button" onclick="val();" style="height:40px;width:100px;background-color: #964000;" value="Confirm">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" onclick="val1();" style="height:40px;width:100px;background-color: #964000;" value="Reject">
            </div>
            
        </c:if>
        
        <form id="form1">
            <input type="hidden" name="yes" value="yes">
        </form>
        
        <form id="form2">
            <input type="hidden" name="no" value="no">
        </form>
            
        
        
        <br><br><br>
        <br><br><br>
        <br><br><br>
        <br><br><br>
        <br><br><br>
        <br><br>
    </body>
</html>
