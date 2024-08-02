<%-- 
    Document   : getReconciliationReport
    Created on : 15 Jun, 2020, 7:17:25 PM
    Author     : Kaustubh
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" > 
        <script>
            function validate() {
                var year = document.getElementById("s1").value;
                
                if(year=="null") {
                    alert("Select financial year of Report !!");
                    return false;
                }
                else {
                    return true;
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
            </style>
    </head>
    <body>
        <b>Home -> Reports</b>
        <form method="post" action="getReconciliationReport.htm">
         <fieldset>
                <legend><h3>Reconciliation Report</h3></legend>
                
                <div align="center">
                    <pre style="color: black">Select financial year :</pre>
                <select name="s1" id="s1" required>
                    <option value="null">Select year</option>
                    <option value="2019">2019-20</option>
                    <option value="2020">2020-21</option>
                    <option value="2021">2021-22</option>
                    <option value="2022">2022-23</option>
                    <option value="2023">2023-24</option>
                    <option value="2024">2024-25</option>
                    <option value="2025">2025-26</option>
                    <option value="2026">2026-27</option>
                    <option value="2027">2027-28</option>
                    <option value="2028">2028-29</option>
                    <option value="2029">2029-30</option>
                </select>
                </div>
              
<br/><br/>
             
      <input type="submit" name="bsubmit" value="Get" onclick="return validate();" style="margin-left:45%; background-color: #964000"/>

 </fieldset>
    </form>
        
        <br><br>
        <br><br>
        <br>
    </body>
</html>
