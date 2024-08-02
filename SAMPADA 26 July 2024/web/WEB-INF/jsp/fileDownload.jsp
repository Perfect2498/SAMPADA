<%-- 
    Document   : fileDownload
    Created on : 19 Mar, 2020, 4:37:04 PM
    Author     : Kaustubh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function getFiles(name)
            {
               document.getElementById('documentset').value = name;
               document.getElementById('form1').submit();
            }
        </script>
        
        <style>
            legend {
            display: block;
            color:#003366;

        }
        fieldset
        {
            /*background-color:#fff7c4;*/
            border: 3px solid #964000;
            max-width:700px;
            padding:16px;
            border-radius: 25px;
            margin-left: 20%;

        }
        .detail {
            font: 700 20px Aparajita ,Kaushan Script,Lato, sans-serif;
            line-height: 1.8;
            color: black;
            width:100%;
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
        
        
        <h1>View Files in Document Sets : <label style="color:red;font-size:22px;">${msg}</label></h1>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N</th>
            <th>Document <br> Set No.</th>
            <th>Nos Documents <br> inside</th>
            <th>Description for Document Set</th>
            <th>Status</th>
            <th>Upload Date by Maker</th>
            <th>Check Date by Checker</th>
            <c:forEach items="${dset_details}" var="sd" varStatus="iteration">
            <tr align="center">
                <td>${iteration.index + 1}</td>
                <td><a id="${filenames[iteration.index]}" onclick="getFiles(this.id)" style="color:blue;cursor:pointer;">${filenames[iteration.index]}</a></td>
                <td>${sd.no_of_docs}</td>
                <td>${sd.description}</td>
                <td>${sd.status}</td>
                <td>${sd.uploadDateByMaker}</td>
                <td>${sd.checkDateByChecker}</td>
            </tr>
            </c:forEach>
        </table>
        <form name="form1" id="form1" action="viewDocuments.htm" method="post" >
            <input type="hidden" value="" id="documentset" name="documentset">
        </form>
        <br>
        <br><br>
        <br><br>
    </body>
</html>
