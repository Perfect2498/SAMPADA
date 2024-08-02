<%--
    Document   : applicantIndex
    Created on : Dec 9, 2019, 5:29:03 PM
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
   <body style="background-color:#FBF2EA; width:95%;">


       <div class="container">
           <%
               String loginID = (String) session.getAttribute("loginID");
               String corpName = (String) session.getAttribute("deptName");

           %>
           <div class="container">
                <div  class="row" id="myDIV" align="center">
                    <div class="col-sm-12">
                        <div class="btn-group">
                            <a href="applicant/billProcessingIndexApplicant.htm" target="content" ><button class="btn"  style="width:150px;">Bill Processing</button></a>
                            <a href="applicant/paymentDisbursementIndexApplicant.htm" target="content" ><button class="btn"  style="width:200px;">Pay Disbursement</button></a>
                           <a href="applicant/reportsIndexApplicant.htm" target="content" ><button class="btn"  style="width:130px;">Reports</button></a>
                            <a href="applicant/applicantResetPassword.htm" target="appcontent" ><button class="btn" style="width:190px;">Change Password</button></a>
                            <a href="logout.htm" target="content"><button class="btn" style="width:90px;">Logout</button></a>
                        </div>
                    </div>
                </div>
               <table align="right" style="font-size: 20px;">
                    <tr><td>Login ID :${loginid}</td>
                    </tr>
                </table><br/>
               <div class="row">
                    <div class="col-sm-12">
                        <iframe src="applicant/welcomeApplicant.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>
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