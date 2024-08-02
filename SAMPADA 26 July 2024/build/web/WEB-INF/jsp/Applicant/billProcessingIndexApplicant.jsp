<%-- 
    Document   : billProcessingIndexApplicant
    Created on : Dec 9, 2019, 5:23:18 PM
    Author     : shubham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
    
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
    <body style="background-color:#FBF2EA;">
        <p align="left">Home->Bill Processing</p>
        <div class="container">
            <div class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/applicantIndex.htm" target="content" ><button class="btn" style="width:130px;">Home</button></a>
                      
                     <a href="billProcessingPendingPayableList.htm" target="appcontent" ><button class="btn" style="width:150px;">View Pending</button></a>

                        <div class="dropdown">
                            <button class="btn" style="width:150px;">View Bills</button>
                            <div class="dropdown-content">
                                <a href="weeklyBillforApplicant.htm" target="appcontent">Weekly</a>
<!--                                <a href="underConstruction.htm" target="appcontent" >Closed</a>-->
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="btn" style="width:90px;">Logout</button></a>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <iframe src="billProcessingPendingPayableList.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent" name="appcontent" frameborder="0"></iframe>
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
