<%-- 
    Document   : BillAdjustIndexMaker
    Created on : Jan 29, 2021, 2:44:34 PM
    Author     : Administrator
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
        <title>Bill Adjustments</title>
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
    <body>
        <h3 align="center" style="color:#003366;" ></h3>
        <%
            String loginID = (String) session.getAttribute("loginid");
            String corpName = (String) session.getAttribute("corpName");
        %>
        <table align="right" style="font-size: 20px;">
            <tr><td>Login ID :<%=loginID%></td>  </tr><tr><td>CorpName:<%=corpName%>&nbsp;&nbsp;</td></tr>
        </table>
        <div class="container">
            <div  class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/sysadminIndex.htm" target="content" ><button class="myButton"  style="width:130px;">Home</button></a>


                        <a href="${pageContext.request.contextPath}/recon/BillAdjustIndexRLDC.htm" target="content" ><button class="myButton"  style="width:130px;">New Adjust</button></a>
                        <!--<a href="${pageContext.request.contextPath}/recon/adjustchecker.htm" target="appcontent"><button class="myButton" style="width:90px;">Checker</button></a>-->
                        <a href="${pageContext.request.contextPath}/recon/report.htm" target="appcontent"><button class="myButton" style="width:90px;">Report</button></a>

                        <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="myButton" style="width:90px;">Logout</button></a>

                    </div>
                </div>
            </div>
            <p style="margin-left: 15%;"><span style='font-size:20px;'>&#128073;&nbsp;</span><b>Home->Bill Adjustments</b></p>
            <div class="row">
                <div class="col-sm-12">
                    <iframe src="selectBillDetails.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>
                </div>
            </div>
        </div>  
    </body>
</html>
