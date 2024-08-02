<%--
    Document   : MasterIndex
    Created on : Jun 17, 2019, 2:52:06 PM
    Author     : JaganMohan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Master Index Page</title>
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
        <div class="container">
              <%
               String loginID = (String) session.getAttribute("loginid");
               String corpName = (String) session.getAttribute("corpName");

           %>
            <table align="right" style="font-size: 20px;">
                <tr><td>Login ID :<%=loginID%></td>                    
               </tr>
               <tr>
                    <td>CorpName:<%=corpName%></td>
               </tr>
           </table>
            <div  class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/sysadminIndex.htm" target="content" ><button class="btn"  style="width:130px;"><span style='font-size:15px;'>&#127968;</span>Home</button></a>
                        <div class="dropdown">
                            <button class="btn" style="width:150px;">Pool Member</button>
                            <div class="dropdown-content">
                                <a href="newCommercialGroupRegistration.htm" target="appcontent" style="width:180px;">New Registration</a>
                                <a href="viewCommercialDetailsList.htm" target="appcontent" style="width:180px;">View & Modify</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="btn" style="width:120px;">Entity</button>
                            <div class="dropdown-content">
                                <a href="newEntityRegistration.htm" target="appcontent" >New Registration</a>
                                <a href="viewEntityDetailsList.htm" target="appcontent" >View & Modify</a>
                            </div>
                        </div>
                      
                        <div class="dropdown">
                            <button class="btn" style="width:150px;">Letter of Credit </button>
                            <div class="dropdown-content">
                                <a href="newLetterofCredit.htm" target="appcontent" style="width:180px;">New LC</a>
                                <a href="viewLetterOfCredit.htm" target="appcontent" style="width:180px;">View LC</a>
                                <a href="viewOutDatedLetterOfCredit.htm" target="appcontent" style="width:180px;">View Outdated LC</a>
                            </div>
                        </div>
                        
                        
                        
                        <a href="viewPoolAccountDetails.htm" target="appcontent" ><button class="btn"  style="width:150px;">Pool Account</button></a>
                        
                        <div class="dropdown">
                            <button class="btn" style="width:190px;">Misc</button>
                            <div class="dropdown-content">
                                <a href="viewInterestDetails.htm" target="appcontent" >Interest Rate</a>
                                <a href="viewNoInterestPeriodList.htm" target="appcontent" >No Interest Period</a>
                                <a href="checkerconfirmNoInterestPeriodList.htm" target="appcontent" >Checker due dates</a>
                                <a href="viewDisburseBillPriority.htm" target="appcontent" >Disburse Bill Priority</a>
                            </div>
                        </div>
<!--                        <div class="dropdown">
                            <button class="btn" style="width:120px;">DSN</button>
                            <div class="dropdown-content">
                                <a href="uploaddsn.htm" target="appcontent" >Upload</a>
                                <a href="disbursedsn.htm" target="appcontent" >Disburse</a>
                            </div>
                        </div>-->
                        <a href="updatePasswordbySysAdmin.htm" target="appcontent" ><button class="btn" style="width:150px;">Reset Password</button></a>

                        <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="btn" style="width:90px;">Logout</button></a>
                    </div>
                </div>
            </div>
                    <p align="left" style="margin-left: 15%;"><span style='font-size:20px;'>&#128073;&nbsp;</span>Home->Masters</p>
            <div class="row">
                <div class="col-sm-12">
                    <!--                    <iframe src="welcomeMessage.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>    -->
                    <iframe src="viewPoolAccountDetails.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>
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
