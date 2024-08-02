<%-- 
    Document   : viewcheckerNoInterestPeriodList
    Created on : 15 Sep, 2023, 5:21:01 PM
    Author     : root
--%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

    <head>

        <meta charset="utf-8">

        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">

        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">

        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">

        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>

        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>

        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>

        <script>



            function validate()

            {

                var rowcnt = document.getElementById("count").value;

                if (rowcnt == 0)

                {

                    return false;

                }

                else

                {

                    if (confirm("Are you want to Confirm !!!!!"))

                    {

                        return true;

                    }

                    else

                    {

                        return false;

                    }

                }

            }





            function validate1()

            {

                var rowcnt = document.getElementById("count").value;

                //   alert(count);

                if (rowcnt == 0)

                {

                    return false;

                }

                else

                {

                    if (confirm("Are you want to Delete !!!!!"))

                    {

                        return true;

                    }

                    else

                    {

                        return false;

                    }

                }

            }

        </script>

    </head>

    <body width="80%">

        <form name="checkerForm">



            <h3 align="center" style="color:#003366;" >No Interest Timeline Details</h3>


            <table align="center" style="width:97%;" cellspacing="0" class="customerTable">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Sl No.</th>

                        <th>Bill Type </th>

                        <th>No of days</th>

                        <th>Category</th>

                        <th>From Date</th>

                        <th>Entry Date</th>

                        <th>Status</th>


                   

                    </tr>

                </thead>

                <tbody>



                    <c:forEach items="${noInterestList}" var="ele">



                        <tr>


                            <td>${ele.slno}</td>

                            <td>${ele.billtype}</td>

                            <td>${ele.noofdays}</td>

                            <td>${ele.category}</td>

                            <td>${ele.fromDate}</td>


                            <td>${ele.entryDate}</td>



                            <td>${ele.status}</td>

                        </tr>



                    </c:forEach>

                </tbody>

            </table>


            <p align="center">

                <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bconfirm"  value="Confirm"/>
                <input style="width:100px;" class="btn" type="submit" value="&#10060;Delete" onclick="return validate1();"  name="bdelete" /></p>
        </p>
    </form>
    <p>&nbsp;</p>
    <p>&nbsp;</p>

</body>

</html>
