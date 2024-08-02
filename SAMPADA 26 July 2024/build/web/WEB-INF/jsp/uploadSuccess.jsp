<%-- 
    Document   : uploadSuccess
    Created on : 19 Mar, 2020, 12:22:04 PM
    Author     : Kaustubh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
                        .detail {
            font: 700 20px Aparajita ,Kaushan Script,Lato, sans-serif;
            line-height: 1.8;
            color: black;
            width:100%;
          }
        
.returnbutton {
    background-color: #ff6633;
    color: white;
    font-weight: bold;
    padding: 10px 20px;
    margin: 4px 2px;
    cursor: pointer;
}

.returnbutton:hover {
    background-color: #ff0000;
    color: white;
    font-weight: bold;
    padding: 10px 20px;
    margin: 4px 2px;
    cursor: pointer;
} 
        
          .btn {              
               background-color: #603311;
               color: white;
               padding: 16px 20px;
               border: none;
               cursor: pointer;
               width: 150px;
               opacity: 0.9;
               font-family:  Kaushan Script ,sans-serif;
             }
  
   .myButton {
	-moz-box-shadow:inset 0px 1px 0px 0px #00BFFF;
	-webkit-box-shadow:inset 0px 1px 0px 0px #00BFFF;
	box-shadow:inset 0px 1px 0px 0px #00BFFF;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #603311), color-stop(1, #603311));
	background:-moz-linear-gradient(top, #603311 5%, #603311 100%);
	background:-webkit-linear-gradient(top, #603311 5%, #603311 100%);
	background:-o-linear-gradient(top, #603311 5%, #603311 100%);
	background:-ms-linear-gradient(top, #603311 5%, #603311 100%);
	background:linear-gradient(to bottom, #603311 5%, #603311 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#00EEEE', endColorstr='#00EEEE',GradientType=0);
	background-color:#603311;
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;
	border:1px solid #54381e;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Aparajita;
	font-size:18px;
	padding:6px 24px;
	text-decoration:none;
	text-shadow:0px 1px 0px #4d3534;
        height: 50px;
}
.myButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #4A777A), color-stop(1, #4A777A));
	background:-moz-linear-gradient(top, #4A777A 5%, #4A777A 100%);
	background:-webkit-linear-gradient(top, #4A777A 5%, #4A777A 100%);
	background:-o-linear-gradient(top, #4A777A 5%, #4A777A 100%);
	background:-ms-linear-gradient(top, #4A777A 5%, #4A777A 100%);
	background:linear-gradient(to bottom, #4A777A 5%, #4A777A 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A777A', endColorstr='#4A777A',GradientType=0);
	background-color:#4A777A;
}
.myButton:active {
	position:relative;
	top:1px;
}
        </style>
    </head>
    <body>
        <%
               String loginID = (String) session.getAttribute("loginid");
               String corpName = (String) session.getAttribute("corpName");
        %>
        
        
        <h1>File Uploaded Successfully !!</h1>
        
        <br><br>
        <br><br>
    </body>
</html>
