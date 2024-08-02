<%--
Document : approveCommercialGroupRegistration
Created on : Jun 20, 2019, 8:02:58 AM
Author : superusr
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
        <script src="js/bootstrap.min.js" ></script>
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
    <body style="min-height: 1500px;">
        <form name="confirmapplnform">
            <h3 style="text-align: center;"><b style="color: #269abc;"> Pool Member Details</b></h3>

            <input type="text" name="commercialname" value="${commercialname}" hidden="yes"/>
            <input type="text" name="scommercialname" value="${scommercialname}" hidden="yes"/>
            <input type="text" name="corpType" value="${corpType}" hidden="yes"/>

            <input type="text" name="address" value="${address}" hidden="yes"/>
            <input type="text" name="location" value="${location}" hidden="yes"/>
            <input type="text" name="state" value="${statename}" hidden="yes"/>
            <input type="text" name="cpdsm" value="${cpdsm}" hidden="yes"/>
            <input type="text" name="cprras" value="${cprras}" hidden="yes"/>
            <input type="text" name="cpcong" value="${cpcong}" hidden="yes"/>
            <input type="text" name="cpreact" value="${cpreact}" hidden="yes"/>
            <input type="text" name="mobile" value="${mobile}" hidden="yes"/>
            <input type="text" name="office" value="${office}" hidden="yes"/>
            <input type="text" name="validity" value="${validity}" hidden="yes"/>
            <input type="text" name="blockremarks" value="${blockremarks}" hidden="yes"/>
            <input type="text" name="subAccNo" value="${subAccNo}" hidden="yes"/>
            <input type="text" name="BankAccName" value="${BankAccName}" hidden="yes"/>
            <table align="center" style="width: 100%; background-color: #f9f9f9;">

                <tr>
                    <th>Pool Member Name</th>
                    <td>${scommercialname}</td>
                    <th>Pool Member Short Name</th>
                    <td>${commercialname}</td>

                </tr>

                <tr>
                    <th>Pool Member Type</th>
                    <td>${corpType}</td>

                    <th>State Name</th>
                    <td>${statename}</td>
                </tr>

                <tr>


                    <th>Address</th>
                    <td>${address}</td>
                    <th>Location</th>
                    <td>${location}</td>
                </tr>




                <tr>
                    <th>Contact Person DSM</th>
                    <td>${cpdsm}</td>
                    <th>Contact Person RRAS</th>
                    <td>${cprras}</td>
                </tr>

                <tr>
                    <th>Contact Person Cong</th>
                    <td>${cpcong}</td>
                    <th>Contact Person Reactive</th>
                    <td>${cpreact}</td>
                </tr>

                <tr>
                    <th>Mobile No.</th>
                    <td>${mobile}</td>
                    <th>Agency Code.</th>
                    <td>${office}</td>
                </tr>

                <tr>
                    <th>Pool Member Validity</th>
                    <td>${validity}</td>
                </tr>
                <tr>
                    <th>Block Remarks for Pool Member</th>
                    <td>${blockremarks}</td>
                </tr>
                <tr>
                    <th colspan="2">Bank Sub Account Number</th>
                    <td colspan="2">${subAccNo}</td>
                </tr>
                <tr>
                    <th colspan="2">Bank Account Name</th>
                    <td colspan="2">${BankAccName}</td>
                </tr>
            </table>

            <br>

            <br>
            <p><input type="submit" name="bname" onclick="return validate();" value="Confirm" />&emsp;<input type="submit" onclick="return validate1();" name="bname" value="Cancel" /></p>
            <br>
        </form>
    </body>
</html>