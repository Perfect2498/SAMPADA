<%-- 
    Document   : welcomeSysadminChart
    Created on : Jul 7, 2019, 10:46:41 AM
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
       <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
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
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 50 + 'px';

            }
        </script>
        <script>
            $(document).ready(function () {
                var table = $('#pendingTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "order": [[0, "asc"]]
                });

                var table1 = $('#disburseTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "order": [[0, "asc"]]
                });

            });
        </script> 
        
    </head>
    <body onload="setIframeHeight(this.id);" style="background-color:#FBF2EA; width:95%;">
        <p align="right"><a href="<c:url value='welcomePendingChart.htm'> </c:url>" >Chart View</a></p>
            <p align="center" style="font-size: 18px;color:#003366;"><b>Bills Pending List</b></p>

            <table id="pendingTable" class="customerTable" align="center" style="min-height:400px;width:90%;" >
                <!--<thead style="background-color: #0677A1;color: white;height:30px;">-->
                <thead style="height:30px;">
                    <tr>                    
                        <th>Week</th> 
                        <th>Bill Type</th>
                        <th>Total Amount</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${billpayweekList}" var="ele1">
                    <c:set var="weekNO" scope="session" value="0"/> 
                    <c:set var="weeksum" scope="session" value="0"/> 
                    <c:set var="weekNO" scope="session" value="${ele1}"/>                    
                    <c:forEach items="${billpayList}" var="ele">   
                        <c:if test="${ele.weekId==weekNO}">
                            <c:if test="${ele.billType=='DSM'}">
                                <c:set var="weeksum" scope="session" value="${weeksum+ele.totalnet}"/> 
                            </c:if>
                        </c:if>                        
                    </c:forEach> 
                    <tr>                                 
                        <td>${ele1}</td>   
                        <td>DSM</td>
                        <td>${weeksum}</td>
                    </tr>
                    <c:set var="weeksum" scope="session" value="0"/>
                    <c:forEach items="${billpayList}" var="ele">   
                        <c:if test="${ele.weekId==weekNO}">
                            <c:if test="${ele.billType=='RRAS'}">
                                <c:set var="weeksum" scope="session" value="${weeksum+ele.totalnet}"/> 
                            </c:if>
                        </c:if>                        
                    </c:forEach> 
                    <tr>                                 
                        <td>${ele1}</td>   
                        <td>RRAS</td>
                        <td>${weeksum}</td>
                    </tr>
                    <c:set var="weeksum" scope="session" value="0"/>
                    <c:forEach items="${billpayList}" var="ele">   
                        <c:if test="${ele.weekId==weekNO}">
                            <c:if test="${ele.billType=='AGC'}">
                                <c:set var="weeksum" scope="session" value="${weeksum+ele.totalnet}"/> 
                            </c:if>
                        </c:if>                        
                    </c:forEach> 
                    <tr>                                 
                        <td>${ele1}</td>   
                        <td>AGC</td>
                        <td>${weeksum}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br/>
        <p align="center" style="font-size: 18px;color:#003366;"><b>Payment Disbursement Pending List</b></p>

        <table id="disburseTable" class="customerTable" align="center" style="min-height:400px;width:90%;" >
            <thead style="height:30px;">
                <tr>                    
                    <th>Week</th> 
                    <th>Bill Type</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${billpayweekList}" var="ele1">
                    <c:set var="weekNO" scope="session" value="0"/> 
                    <c:set var="weeksum" scope="session" value="0"/> 
                    <c:set var="weekNO" scope="session" value="${ele1}"/>                    
                    <c:forEach items="${billrecvList}" var="ele">   
                        <c:if test="${ele.weekId==weekNO}">
                            <c:if test="${ele.billType=='DSM'}">
                                <c:set var="weeksum" scope="session" value="${weeksum+ele.toalnet}"/> 
                            </c:if>
                        </c:if>                        
                    </c:forEach> 
                    <tr>                                 
                        <td>${ele1}</td>   
                        <td>DSM</td>
                        <td>${weeksum}</td>
                    </tr>
                    <c:set var="weeksum" scope="session" value="0"/>
                    <c:forEach items="${billrecvList}" var="ele">   
                        <c:if test="${ele.weekId==weekNO}">
                            <c:if test="${ele.billType=='RRAS'}">
                                <c:set var="weeksum" scope="session" value="${weeksum+ele.toalnet}"/> 
                            </c:if>
                        </c:if>                        
                    </c:forEach> 
                    <tr>                                 
                        <td>${ele1}</td>   
                        <td>RRAS</td>
                        <td>${weeksum}</td>
                    </tr>
                    <c:set var="weeksum" scope="session" value="0"/>
                    <c:forEach items="${billrecvList}" var="ele">   
                        <c:if test="${ele.weekId==weekNO}">
                            <c:if test="${ele.billType=='AGC'}">
                                <c:set var="weeksum" scope="session" value="${weeksum+ele.toalnet}"/> 
                            </c:if>
                        </c:if>                        
                    </c:forEach> 
                    <tr>                                 
                        <td>${ele1}</td>   
                        <td>AGC</td>
                        <td>${weeksum}</td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
        <br/> 
        <p>&nbsp;</p>
         <p>&nbsp;</p>
          <p>&nbsp;</p>
    </body>
</html>
