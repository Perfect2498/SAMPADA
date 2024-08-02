


<%--
    Document   : approveEntityRegistration
    Created on : Jun 18, 2019, 5:10:45 PM
    Author     : superusr
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

            <input type="text" name="entityname" value="${entityname}" hidden="yes"/>
            <input type="text" name="corpId" value="${corpId}" hidden="yes"/>
            <input type="text" name="sentityname" value="${sentityname}" hidden="yes"/>
            <input type="text" name="bankname" value="${bankname}" hidden="yes"/>
            <input type="text" name="branchname" value="${branchname}" hidden="yes"/>
            <input type="text" name="accno" value="${accno}" hidden="yes"/>
            <input type="text" name="ifsc" value="${ifsc}" hidden="yes"/>
            <input type="text" name="rtgsneft" value="${rtgsneft}" hidden="yes"/>
            <input type="text" name="entityType" value="${entityType}" hidden="yes"/>
            <input type="text" name="address" value="${address}" hidden="yes"/>
            <input type="text" name="location" value="${location}" hidden="yes"/>
            <input type="text" name="state" value="${statename}" hidden="yes"/>
            <input type="text" name="cpdsm" value="${cpdsm}" hidden="yes"/>

            <input type="text" name="cprras" value="${cprras}" hidden="yes"/>
            <input type="text" name="cpfras" value="${cpfras}" hidden="yes"/>
            <input type="text" name="cpcong" value="${cpcong}" hidden="yes"/>
            <input type="text" name="cpreact" value="${cpreact}" hidden="yes"/>
            <input type="text" name="mobile" value="${mobile}" hidden="yes"/>
            <input type="text" name="office" value="${office}" hidden="yes"/>
            <input type="text" name="rrasconsiderable" value="${rrasconsiderable}" hidden="yes"/>
            <input type="text" name="frasconsiderable" value="${frasconsiderable}" hidden="yes"/>
            <input type="text" name="srasconsiderable" value="${srasconsiderable}" hidden="yes"/>

            <input type="text" name="trasconsiderable" value="${trasconsiderable}" hidden="yes"/>

            <input type="text" name="agcconsiderable" value="${agcconsiderable}" hidden="yes"/>
            <input type="text" name="corpName" value="${corpName}" hidden="yes"/>
            <table align="center" style="width: 90%; background-color: moccasin;">

                <tr>
                    <th>Entity Name</th>
                    <td>${entityname}</td>
                    <th>Entity Short Name</th>
                    <td>${sentityname}</td>
                </tr>
                <tr>
                    <th>Commercial Group Name</th>
                    <td>${corpName}</td>
                    <th>Entity Type</th>
                    <td>${entityType}</td>
                </tr>
                <tr>
                    <th>Bank Name</th>
                    <td>${bankname}</td>
                    <th>Branch Name</th>
                    <td>${branchname}</td>
                </tr>

                <tr>
                    <th>Account No</th>
                    <td>${accno}</td>
                    <th>IFSC Code</th>
                    <td>${ifsc}</td>
                </tr>

                <tr>
                    <th>RTGS/NEFT enabled</th>
                    <td>${rtgsneft}</td>
                    <th>Address</th>
                    <td>${address}</td>
                </tr>

                <tr>
                    <th>Location</th>
                    <td>${location}</td>
                    <th>State Name</th>
                    <td>${statename}</td>
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
                    <th>office No.</th>
                    <td>${office}</td>
                </tr>

                <tr>
                    <th>RRAS Considerable</th>
                    <td>${rrasconsiderable}</td>
                    <th>AGC Considerable</th>
                    <td>${agcconsiderable}</td>
                </tr>
                <tr>
                    <th>FRAS Considerable</th>
                    <td>${frasconsiderable}</td>
                    <th>Contact Person FRAS</th>
                    <td>${cpfras}</td>
                </tr>
                 <tr>
                    <th>SRAS Considerable</th>
                    <td>${srasconsiderable}</td>
                    <th>TRAS Considerable</th>
                    <td>${trasconsiderable}</td>
                </tr>

            </table>

            <br>

            <br>
            <p align="center"><input type="submit" name="bname" onclick="return validate();" value="Confirm" />&emsp;<input type="submit" onclick="return validate1();" name="bname" value="Cancel" /></p>
            <br>
        </form>
    </body>
</html>

