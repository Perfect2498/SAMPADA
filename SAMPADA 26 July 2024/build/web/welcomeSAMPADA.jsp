<%--
    Document   : welcomeSAMPADA
    Created on : Jun 17, 2019, 9:31:08 AM
    Author     : JaganMohan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>SAMPADA</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
     
        <style>
            body {
                font: 400 15px Kaushan Script,Lato, sans-serif;
                line-height: 1.8;
                color: #818181;
            }
            .bg-grey {
                background-color: #f6f6f6;
            }
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
                ifrm.style.height = getDocHeight(doc) + 1 + "px";
                ifrm.style.visibility = 'visible';

            }

            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 10 + 'px';

            }
        </script>
    </head>
    <body style="background-color:#FBF2EA;">

        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>

                </div>
                <form name="loginform" method="post" action="login.jsp">
                    <div class="collapse navbar-collapse" id="myNavbar">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="${pageContext.request.contextPath}/aboutus.htm" target="content"><span style='font-size:30px;'>&#9758;</span>ABOUT</a></li>
                            <li><a href="${pageContext.request.contextPath}/viewDownloadHelpFiles.htm" target="content">&#10067;HELP</a></li>
                            <li><a href="${pageContext.request.contextPath}/contact.htm" target="content"><span style='font-size:20px;'>&#9742;</span>CONTACT</a></li>
                            <li><a href="${pageContext.request.contextPath}/loginPage.htm" target="content"><span class="glyphicon glyphicon-log-in"></span>LOGIN</a></li>

                            
                        </ul>
                    </div>
                </form>
            </div>
        </nav>

        <div class="container"  style="margin-top: 50px;width:95%;">
            <div class="row" >
                <div class="col-sm-12">
                    <p align="center" ><img  src="${pageContext.request.contextPath}/images/SAMPADA_Logo.jpg" width="95%" height="150" alt="COPS" style="border-radius: 25px;"></p>
                </div>
            </div>
        </div>
        <div class="container" style="width:100%;">
            <div class="row">
                <div class="col-sm-12">
                    <iframe src="${pageContext.request.contextPath}/aboutus.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id)" id="content"  name="content" frameborder="0"></iframe>
                </div>
            </div>
        </div>

          
        <footer class="container-fluid text-center">
            <p style="font-family:'Kaushan Script';"><span style='font-size:20px;'>&#128394;</span>
<bold>Designed & Developed by C-DAC, Knowledge Park Bangalore.Version 1.1, 16 January 2020</bold></p>
    </footer>


</body>
</html>
