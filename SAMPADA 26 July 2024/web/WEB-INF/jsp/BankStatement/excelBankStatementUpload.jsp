<%--
    Document   : excelBankStatementUpload
    Created on : Jul 5, 2019, 10:21:35 AM
    Author     : shubham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

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

    <!--        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
    <title>JSP Page</title>
    <script>
        $(function () {
            $.datepicker.setDefaults($.datepicker.regional['nl']);
//                $.datepicker.setDefaults({dateFormat: 'yy-mm-dd'});
            $.datepicker.setDefaults({dateFormat: 'dd-mm-yy'});

            $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true
            });
            $("#datepicker1").datepicker({
                changeMonth: true,
                changeYear: true
            });
        });
    </script>
    <script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null;
        };


        function cmpDates() {

//            alert("In cmpDates()");
            var startDate = document.getElementById("datepicker").value;
            var endDate = document.getElementById("datepicker1").value;
//            alert(startDate);
//            alert(endDate);

            var split = startDate.split('-');
            var split1 = endDate.split('-');

// Month is zero-indexed so subtract one from the month inside the constructor
            var date0 = new Date(split[2], split[1] - 1, split[0]); //Y M D
            var date1 = new Date(split1[2], split1[1] - 1, split1[0]); //Y M D
            var timestamp = date0.getTime();
            var timestamp1 = date1.getTime();
//            alert(timestamp);
//            alert(timestamp1);


            if ((startDate == "") || (endDate == "")) {
                {
                    alert('Kindly select both datepickers!!');
                }

            }
            else
            {
                if (timestamp1 < timestamp) {

                    alert("Transaction End Date should be greater than or equal to Transaction Start Date");

                    $('#datepicker1').val('');

                    return false;
                }
                else
                {
                    return true;
                }
            }

        }

    </script>

    <style>
        div.ui-datepicker{
            font-size:14px;
        }
    </style>
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
            max-width:1500px;
            padding:16px;
            border-radius: 25px;
            margin-left: 25%;

        }
    </style>

</head>
<body style="min-height: 600px;" align="center" >

    <p style="color:red;"><c:out value="${msg}"></c:out></p><br><br>

        <fieldset id="fieldset2" style="width: 60%; " >

            <legend style="font-size: 16px;"><b>Upload Bank Statement Sheet</b></legend>
            <p id="msg"></p><fmt:formatDate value="${maxToDate}" pattern="dd-MM-yyyy" var="ToDate" />
        <p> Max Transaction End Date:&nbsp; ${ToDate} </p>
        <table style="width: 40%;">
            <a  href="downloadBankStatementTemplate.htm">Download Bank Statement Template</a>
            <div class="noborderedtable" style="color: #003366;" align="center"><br>

                <form:form action="uploadBankStatement.htm" method="post" enctype="multipart/form-data" onsubmit="return formOK;">

                    <br/>
                    <table id="reportstable">
                        <tr><td>Transaction Start Date</td>
                            <td><input type="text" name="startdate" id="datepicker" required autocomplete="off"/></td></tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr> <td> Transaction End Date</td>
                            <td><input type="text" name="enddate" id="datepicker1" required autocomplete="off" onchange="return cmpDates();"/></td></tr>

                    </table><br/>
                    <div style="text-align:center">
                        <br>
                        <input type="file" name="file" id="uploadexcelfile" required><br><br>
                        <input type="submit" value="Upload" name="upload" id="uploadbutton" align="center" style="font-size:12px;" onclick="return cmpDates();" >
                    </div>
                    <div>
                        <p style="text-align:center;color:#003366;"> Note: <br/>
                            1. All Credit Entries should have Disburse Id as 0 for successful Bank Statement upload.  <br/>
                            2. Bank Statement excel sheet having Date column format dd-mm-yyyy.
                            <!--                            2. Bank Statement upload excel sheet first row Balance(INR) column should always be equal to Pool Account Main Balance + 1st Row Transaction Amount.-->
                        </p>
                    </div>

                </form:form>

                </fieldset>
                <p>&nbsp;</p><p>&nbsp;</p><br/>
                <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
                <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
                <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
                <script type="text/javascript">
                            $(document).ready(function () {
                                $('input[type="submit"]').button();
                                var formOK = true;
                                $('#uploadexcelfile').bind('change', function () {
                                    var file = this.value;
                                    var len = file.length;
                                    var ext = file.slice(len - 4, len);
                                    var size = this.files[0].size;
                                    var filename = $('input[type=file]').val().split('\\').pop();
                                    //var filename = file.substr(0, file.lastIndexOf('.'));
                                    var name = filename.split('.')[0];
                                    //  console.log(filename,$('#file'));
                                    console.log(name);
                                    console.log(ext);

                                    //                                    if ((ext == "xlsx") && (name == "bank_statement_excel_file")) {
                                    if (ext == "xlsx") {

                                        formOK = true;
                                    }
                                    else {
                                        formOK = false;
                                        alert("Please upload Bank Statement Excel File with extention xlsx !!");
                                        $("#uploadexcelfile").val('');
                                    }
                                    return formOK;
                                });
                            });


                </script>

                </body>

                </html>
