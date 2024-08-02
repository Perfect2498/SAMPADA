<%-- 
    Document   : miscIndexMaker
    Created on : 22 Apr, 2020, 10:47:09 AM
    Author     : abt
--%>

<%-- 
    Document   : miscIndexRLDC
    Created on : 21 Apr, 2020, 2:30:15 PM
    Author     : abt
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
        <title>JSP Page</title>
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
    <body style="background-color:#FBF2EA;" >
        <h3 align="center" style="color:#003366;" ></h3>
        <%
            String loginID = (String) session.getAttribute("loginid");
            String corpName = (String) session.getAttribute("corpName");
        %>
        <table align="right" style="font-size: 20px;">
            <tr><td>Login ID :<%=loginID%></td>  </tr><tr><td>CorpName:<%=corpName%></td></tr>
        </table>
        <div class="container">
            <div  class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/makerIndex.htm" target="content" ><button class="myButton"  style="width:130px;">Home</button></a>
                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">Files</button>
                            <div class="dropdown-content">
                                <a href="fileUpload.htm" target="appcontent" >Upload Files</a>
                                <a href="viewDocumentSets.htm" target="appcontent" >View Files</a>
                            </div>
                        </div>

                        <a href="unmappedpayment.htm" target="appcontent"><button class="myButton" style="width:90px;">Bank Payment</button></a>

                        <div class="dropdown">
                            <button class="myButton" style="width:180px;">Corporate Payment</button>
                            <div class="dropdown-content">
                                <a href="corporaterefund.htm" target="appcontent">Pool Refund</a>
                                <a href="interestrefund.htm" target="appcontent" >Interest Refund</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">Pool to Int</button>
                            <div class="dropdown-content">
                                <a href="pooltoint.htm" target="appcontent">DSM</a>
                                <a href="pooltointagc.htm" target="appcontent">SRAS</a>

                                <a href="pooltointrras.htm" target="appcontent">TRAS</a>

                            </div>
                        </div>
                        
                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">Pool to Pool</button>
                            <div class="dropdown-content">
                                <a href="dsmtosras.htm" target="appcontent">DSM to SRAS</a>
                                <a href="dsmtotras.htm" target="appcontent">DSM to TRAS</a>
                                <a href="srastotras.htm" target="appcontent">SRAS to TRAS</a>

                            </div>
                        </div>
                        <!--<a href="pooltoint.htm" target="appcontent"><button class="myButton" style="width:150px;">Pool_to_Interest</button></a>-->

                        <a href="documentMaker.htm" target="appcontent"><button class="myButton" style="width:90px;">Maker</button></a>
                        <!--<a href="documentChecker.htm" target="appcontent"><button class="myButton" style="width:90px;">Checker</button></a>-->
                        <a href="mischomepage.htm" target="appcontent"><button class="myButton" style="width:90px;">Report</button></a>




                        <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="myButton" style="width:90px;">Logout</button></a>

                    </div>
                </div>
            </div>
            <p style="margin-left: 15%;"><span style='font-size:20px;'>&#128073;&nbsp;</span><b>Home->Misc Module</b></p>
            <div class="row">
                <div class="col-sm-12">
                    <iframe src="mischomepage.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>
                </div>
            </div>
        </div>
        <script>
            // Add active class to the current button (highlight it)
            var header = document.getElementById("myDIV");
            var btns = header.getElementsByClassName("btn");
            for (var i = 0; i < btns.length; i++) {
                btns[i].addEventListener("click", function () {
                    var current = document.getElementsByClassName("active");
                    current[0].className = current[0].className.replace(" active", "");
                    this.className += " active";
                });
            }
        </script>
    </body>

</html>


