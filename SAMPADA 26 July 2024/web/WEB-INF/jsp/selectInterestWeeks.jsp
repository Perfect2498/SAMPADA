<%-- 
    Document   : selectInterestWeeks
    Created on : 2 Sep, 2020, 3:57:44 AM
    Author     : Kaustubh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script>
        function check() {
            var d1 = document.getElementById("date1").value;
            var d2 = document.getElementById("date2").value;
            
            var dt1 = new Date(d1).getTime();
            var dt2 = new Date(d2).getTime();
            
            if(dt1>dt2)
            {
                alert("End date should be greater than Start date !!");
                document.getElementById("date1").value = null;
                document.getElementById("date2").value = null;
            }
        }
    </script>
    <style>
        legend 
        { 
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
        
        #s1 {
            background-color: chocolate;
            color: white;
            height: 40px;
            width: 120px;
        }
    </style>
    <body>
        <fieldset>
            <legend><h3>Select dates :</h3></legend>
            <form>
            <div align="center">
                <h2>${note}</h2><br>
                <b>Select transaction start date:</b> <input type="date" name="date1" id="date1" required>
                <br>
                <br>
                <br>
                <b>Select transaction end date:</b> <input type="date" name="date2" id="date2" onchange="check()" required>
                <br>
                <br>
                <div id="selectbill" style="display : none;">
                    <b>Bill Type:</b> <select name="btype" required>
                                        <option value="DSM">DSM</option>
                                        <option value="RRAS">RRAS</option>
                                        <option value="AGC">AGC</option>
                                        <option value="FRAS">FRAS</option>
                                      </select>
                    <br>
                    <br>
                </div>
                <br>
                <input type="submit" name="getInterest" value="Submit" id="s1">
                 
            </div>
            </form>
        </fieldset>
        <br><br>
        <br><br>
        <br>
    </body>
    <script>
        if(${flag}==1)
            document.getElementById("selectbill").style.display = "inline";
    </script>
</html>