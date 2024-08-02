<%-- 
    Document   : fileUpload
    Created on : 19 Mar, 2020, 11:48:06 AM
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
        function validate()
        {
            if(!(document.getElementById("new_d_set").value=="") && (document.getElementById("dset_desc").value==""))
            {
                alert("Enter description for new Document Set !!");
                return false;
            }
            
            if((document.getElementById("d_set_no").value=="null" && document.getElementById("new_d_set").value=="") || (!(document.getElementById("d_set_no").value=="null") && !(document.getElementById("new_d_set").value=="")))
            {
                alert("Please select a document set  OR  Create a new one !!");
                return false;
            }
            else
            {
                var r = confirm("Are you sure want to Upload this Document !!");
                   if(r==false)
                   {
                       return false;
                   }
                   else
                   {
                        return true;
                   }
            }
        }
        
        function hidedesc()
        {
            document.getElementById("dset_desc").style.display = "none";
            document.getElementById("txtlabel").style.display = "none";
        }
        
        function showdesc()
        {
            document.getElementById("dset_desc").style.display = "inline";
            document.getElementById("txtlabel").style.display = "inline";
        }
    </script>
        
        <style>
        table { margin: auto; }

        input[type=submit] {

            padding: 10px 14px;
            border: 2px solid #964000;
            font-weight:bold;
            color:#000;
            alignment-adjust:central;
            background-color: #964000;
        }

        input[type=submit]:hover, input[type=submit]:focus {
            background-color: #964000;
            color:white;
        }
        td{
            color:#003366;
        }

    </style>

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
        

        
        <br>
        <fieldset id="fieldset2" style="width: 80%; " >

            <legend style="font-size: 16px;"><b>Upload Files</b></legend>
            <h1 style="color:red;">${warning}</h1>
            <form action="storeFile.htm" method="post" name="form1" enctype="multipart/form-data">
                <table id="reportstable">
                    <tr>
                        <td><b>&nbsp;&nbsp;Select a Document Set :</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>or</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<b>Create a new one :</b><br>
                            <select style="height:35px;width:180px;border-color:black;" name="d_set_no" id="d_set_no" onclick="hidedesc()">
                                        <option value="null"></option>
                                        <c:forEach items="${dsets}" var="sd">
                                            <option value="${sd}">${sd}</option>
                                        </c:forEach>
                                    </select>
                    
                    
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Enter Document Set No. : &nbsp;&nbsp;&nbsp;<input type="text" id="new_d_set" name="new_d_set" pattern="[A-Za-z0-9._ ]{0,25}" placeholder="Add document set no." style="height:35px;" onkeydown="showdesc()"/></td>
                    </tr>
                    <tr>
                        <td><br>
                    </tr>
                    <tr>
                        <td>&nbsp;&nbsp;<label id="txtlabel">Add Description for Document-SET :</label>&nbsp;&nbsp;<input type="text" name="dset_desc" id="dset_desc" placeholder="Add description (max. 300 characters)" style="height:35px;width:400px;font-size:18px;"/></td> 
                    </tr>
                    <tr>
                        <td><br>
                    </tr>
                    <tr><td><b>Upload Document :<b>&nbsp;<input type="file" name="file" required /></td>
                    </tr>
                    <tr>
                        <td><br>
                    </tr>
                    <tr>
                        <td>&nbsp;&nbsp;&nbsp;Add Description for Document :&nbsp;&nbsp;&nbsp;<input type="text" name="doc_desc" required placeholder="Add description (max. 300 characters)" style="height:35px;width:430px;font-size:18px;"/></td>
                    </tr>
          
                </table><br/>
                <tr>
                <div style="text-align:center">

                    <input type="submit" name="bName" align="center" onclick="return validate()" value="Upload" style="font-size:14px;" />

                </div>
                
            </form>
            <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        </fieldset>
        <br><br><br>
        <br><br>
        <br><br>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </body>
    <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
</html>
