<%-- 
    Document   : reconciliationIndexRLDC
    Created on : Jun 18, 2019, 8:42:57 AM
    Author     : JaganMohan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
         <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" > 
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
         <script>
           function getDocHeight(doc) {
doc = doc || document;

var body = doc.body, html = doc.documentElement;
var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
return height;

}

function setIframeHeight(id) {
var ifrm = document.getElementById(id);
var doc = ifrm.contentDocument? ifrm.contentDocument: ifrm.contentWindow.document;
ifrm.style.visibility = 'hidden';
ifrm.style.height = "10px"; // reset to minimal height in case going from longer to shorter doc
ifrm.style.height = getDocHeight( doc ) + 5+"px";
ifrm.style.visibility = 'visible';

   var iframe = window.parent.document.getElementById('content');
    var container = document.getElementById('appcontent');
    iframe.style.height = container.offsetHeight + 50+'px';          

}

  function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight +30 + 'px';
    
  }
</script>


 <style>
             .btn{
                background-color: #603311;
                color: #FBF1E9;
             }

            .dropbtn {
                background-color: #603311;
                color: #FBF1E9;
                padding: 5px; 
/*                padding: 10px 13px;*/
                font-size: 12px;
                border: none;
                width:150px;
                cursor: pointer;
            }

            .dropdown {
                position: relative;
                display: inline-block;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #FFA812;
                min-width: 150px;
                min-height: 30px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown-content a {
                color: #603311;
                text-decoration: none;
                display: block;
                height:35px;
                background-color: #FFA812;
            }

            .dropdown-content a:hover {background-color: #603311;
            color: #FFA812;}

            .dropdown:hover .dropdown-content {
                display: block;
            }

            .dropdown:hover .dropbtn {
                background-color: #603311;
            }
            .active, .btn:hover {
                color:#FBF1E9;
                background-color: #603311;
            }
        </style>
    </head>
    <body style="background-color:#FBF2EA;">
         <div class="container">               
               <div  class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/sysadminIndex.htm" target="content" ><button class="btn"  style="width:90px;">Home</button></a>           

                    <a href="master/MasterIndex.htm" target="appcontent" ><button class="btn"  style="width:130px;">Bill Upload</button></a>           
                    <a href="master/MasterIndex.htm" target="appcontent" ><button class="btn"  style="width:130px;">Verification</button></a>           
                    <a href="master/MasterIndex.htm" target="appcontent" ><button class="btn"  style="width:150px;">Revised Upload</button></a>           
                     <div class="dropdown"> 
                        <button class="btn" style="width:150px;">Interest</button> 
                        <div class="dropdown-content"> 
                            <a  href="item/employeeDayItemDetails.htm" target="appcontent">New</a> 
                            <a  href="item/employeeMonthlyReport.htm" target="appcontent" >View</a>  
                            <a  href="item/employeeMonthlyReport.htm" target="appcontent" >Verification</a> 
                        </div> 
                    </div> 
                    <div class="dropdown"> 
                        <button class="btn" style="width:150px;">View Bills</button> 
                        <div class="dropdown-content"> 
                            <a  href="item/employeeDayItemDetails.htm" target="appcontent">Weekly</a> 
                            <a  href="item/employeeMonthlyReport.htm" target="appcontent" >Revised</a>  
                            <a  href="item/employeeMonthlyReport.htm" target="appcontent" >Closed</a> 
                        </div> 
                    </div>
                    <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="btn" style="width:90px;">Logout</button></a>
                  
                
                     </div>
               </div>
           </div>            
            
    <div class="row">
            <div class="col-sm-12"> 
               <iframe src="welcomeMessage.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>    
            </div>
    </div>
</div>
   
      <script>
// Add active class to the current button (highlight it)
var header = document.getElementById("myDIV");
var btns = header.getElementsByClassName("btn");
for (var i = 0; i < btns.length; i++) {
  btns[i].addEventListener("click", function() {
    var current = document.getElementsByClassName("active");
    current[0].className = current[0].className.replace(" active", "");
    this.className += " active";
  });
}
</script>
                
</body>

</html>
