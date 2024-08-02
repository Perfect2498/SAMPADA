<%-- 
    Document   : approveLetterOfCredit
    Created on : Aug 27, 2019, 8:53:07 AM
    Author     : shubham
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">     
        <link rel="stylesheet" href="css/bootstrap.min.css" >
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"  ></script>
        <style>
            table, td, th {   
                border: 1px solid #ddd;
                text-align: left;
            }

            table {
                border-collapse: collapse;
                width: 90%;
            }

            th, td {
                padding: 15px;
            }

        </style>
        <script>

            function validate()
            {

                if (confirm('Are you sure submit !'))
                {
                    return true;
                }
                else
                {
                    return false;
                }

                return true;
            }

            function validate1()
            {

                if (confirm('Are you sure Cancel !'))
                {
                    return true;
                }
                else
                {
                    return false;
                }

                return true;
            }
        </script>
    </head>
    <body style="min-height: 1050px;">
        <form name="confirmapplnform">
            <h4 align="center"> Entity Details</h4>

            <input type="text" name="finyear" value="${finyear}" hidden="yes"/>
             <input type="text" name="consname" value="${consname}" hidden="yes"/>
            <input type="text" name="finavg" value="${finavg}" hidden="yes"/>         
            <input type="text" name="lcamount" value="${lcamount}" hidden="yes"/>   
              <input type="text" name="branchname" value="${branchname}" hidden="yes"/>   
            <input type="text" name="bankname" value="${bankname}" hidden="yes"/>
            <input type="text" name="startdate" value="${startdate}" hidden="yes"/>
            <input type="text" name="enddate" value="${enddate}" hidden="yes"/>
               <input type="text" name="remarks" value="${remarks}" hidden="yes"/>
                 <input type="text" name="lcfilename" value="${lcfilename}" hidden="yes"/>

            <table align="center" style="width: 90%; background-color: moccasin;">

                <tr>              
                    <th>Financial Year</th>
                    <td>${finyear}</td>
                    <th>Constituent Name</th>
                    <td>${consname}</td>           
                </tr>
                <tr>
                    <th>Last Financial Year Avg</th>
                    <td>${finavg}</td>
                    <th>LC Amount</th>
                    <td>${lcamount}</td>
                </tr>
                <tr>
                    <th>Bank Name</th>
                    <td>${bankname}</td>
                    <th>Branch Name</th>
                    <td>${branchname}</td>
                </tr>

                <tr>
                    <th>Start Date</th>
                    <td>${startdate}</td>
                    <th>End Date</th>
                    <td>${enddate}</td>
                </tr>
                
                 <tr>
                    <th>Remarks</th>
                    <td>${remarks}</td>
                   <th>File Name</th>
                    <td>${lcfilename}</td>
                   
                </tr>

               
            </table>

            <br>

            <br>
            <p align="center"><input type="submit" name="bname" onclick="return validate();" value="Confirm" />&emsp;<input type="submit" onclick="return validate1();" name="bname" value="Cancel" /></p>
            <br>
        </form>
    </body>
</html>

