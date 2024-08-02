<%-- 
    Document   : updaterecon
    Created on : 22 Jan, 2020, 3:56:56 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
        <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
        <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/2.10.6/moment.min.js"></script>
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';
        </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

        <title>JSP Page</title>

        <script>
            $(document).ready(function () {
                $("#myform").on("submit", function () {
                    $("#pageloader").fadeIn();
                });//submit
            });//document ready
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
                max-width:500px;
                padding:16px;
                border-radius: 25px;
                margin-left: 25%;

            }
            #pageloader
            {
                background: #666;
                margin-left: 200px;
                display: none;
                height: 50%;
                position: fixed;
                width: 100%;
                left: 666px;
                top: 50px;
                z-index: 9999;
                border: 3px solid #964000;
                max-width:500px;
                padding:16px;
                border-radius: 25px;
                /*margin-left: 25%;*/
            }

            #pageloader img
            {
                left: 50%;
                margin-left: -32px;
                margin-top: -32px;
                position: absolute;
                top: 50%;
            }
        </style>

    </head>
    <body style="min-height: 600px;" align="center" >

        <br><br>

        <fieldset id="fieldset2" style="width: 60%; " >
            <a  href="Exceldownloadreconreport.htm">BACK</a>

            <legend style="font-size: 16px;"><b>Please Submit for Updating Reconciliation</b></legend>
            <form id="myform">
                <div id="pageloader">
                    <img src="http://cdnjs.cloudflare.com/ajax/libs/semantic-ui/0.16.1/images/loader-large.gif" alt="processing..." />
                </div>

                <div style="text-align:center">
                    <!--<p align="center"><input type="submit" class="btn" onclick="return validate();" name="bName" value="Submit"/></p>-->
                    <input type="submit" name="bName" align="center"  value="Submit"  style="font-size:12px;" />
                </div>
        
    
            </form>

        </fieldset>
        <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
        <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
        <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
 <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </body>
 <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
</html>


