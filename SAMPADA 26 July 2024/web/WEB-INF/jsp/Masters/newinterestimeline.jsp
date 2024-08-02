<%-- 
    Document   : newinterestimeline
    Created on : 8 Sep, 2023, 12:42:02 PM
    Author     : root
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
        var basePath = '${pageContext.request.contextPath}';</script>

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
        });</script>
    <script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null;
        };
        function cmpDates() {

            var startDate = document.getElementById("datepicker").value;
            var split = startDate.split('-');
            var date0 = new Date(split[2], split[1] - 1, split[0]); //Y M D
            var timestamp = date0.getTime();
            if (startDate == "") {
                {
                    alert('Kindly select both datepickers!!');
                }

            }


        }
    </script>
    <script>
        $(document).ready(function () {

            $('#datepicker').change(function (e) {
                var datepicker = $("#datepicker").val();
                var billtype = $("#billtype").val();
                var category = $("#category").val();


                $.ajax({
                    url: "checkFromdateinttimeline.htm",
                    data: {"datepicker": datepicker, "billtype": billtype, "category": category},
                    type: "POST",
                    success: function (data) {

                        if (data == '1'){
                            
                            
                        }else{
                            alert(data);
                            $("#datepicker").val('');
                        }




                    },
                    error: function () {
                        console.log('error');
                    }
                });
            });
        });
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
            max-width:500px;
            padding:16px;
            border-radius: 25px;
            margin-left: 25%;

        }
    </style>

</head>
<body style="min-height: 600px;" align="center" >


    <fieldset id="fieldset2" style="width: 60%; " >

        <form>
            <table id="reportstable">

                <tr>
                    <td></td>
                    <td></td>
                </tr>



                <tr><td>Select Bill Type</td>
                    <td>
                        <select name="billtype" id="billtype">
                            <option value="DSM">DSM</option>
                            <option value="AGC">AGC</option>
                            <option value="RRAS">RRAS</option>
                            <option value="FRAS">FRAS</option>
                            <option value="SRAS">SRAS</option>
                            <option value="TRASM">TRASM</option>
                            <option value="TRASS">TRASS</option>
                            <option value="TRASE">TRASE</option>


                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>


                <tr><td>Select Category</td>
                    <td>
                        <select name="category" id="category">
                            <option value="PAYABLE">PAYABLE</option>
                            <option value="RECEIVABLE">RECEIVABLE</option>                     

                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
                <tr><td>From Date</td>
                    <td><input type="text" name="startdate" id="datepicker" required autocomplete="off"/></td>
                </tr>


                <tr><td>Select No of Due Dates</td>
                    <td>
                        <input type="number" name="noofduedates" id="noofduedates" required>
                    </td>
                </tr>

            </table><br/>
            <tr>
            <div style="text-align:center">

                <input type="submit" name="bName1" align="center"  value="submit"  style="font-size:12px;" />
            </div>
        </form>

    </fieldset>
    <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
    <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>

</body>

</html>


