<%--
    Document   : newjsp
    Created on : Dec 3, 2019, 3:24:50 PM
    Author     : cdac
--%>

<%--

    Document   : newReconciliationReport

    Created on : Nov 29, 2019, 8:30:23 AM

    Author     : JaganMohan

--%>





<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>







<!DOCTYPE html>



<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" >

        <script src="<c:url value="js/jquery-1.9.1.js" />"></script>

        <script src="<c:url value="js/jquery-ui.js" />" ></script>

        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>

        <link type="text/css" href="<c:url value="/jquery.ui.datepicker.monthyearpicker.css"/>" rel="stylesheet" />

        <script type="text/javascript" src="<c:url value="/jquery.ui.datepicker.monthyearpicker.js"/>"></script>

        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />

        <script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>

        <script src="<c:url value="/js/bootstrap.min.js" />" type="text/javascript"></script>


        <script>

            function full()
            {
                var start = 2019;
                var end = new Date().getFullYear();
//                var end = 2029;
                var options = "<option value='' selected>Year</option>";
                for (var year = start; year <= end; year++)
                {
                    options += "<option>" + year + "</option>";
                }
                document.getElementById("yearq").innerHTML = options;
            }
        </script>
        <script>

            function validate()

            {



                var corp = document.getElementById("corparateID").value;
                var yearq = document.getElementById("yearq").value;

                if (yearq == "")
                {
                    alert('Please select the  Year');
                    return false;
                }
                if (corp == -1)

                {

                    alert('Please select the corpoarte ..');

                    return false;

                }
                return true;

            }

        </script>
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

    <body onload="full()" style="min-height: 500px; color:#003366;">

        <fieldset id="fieldset2" style="width: 60%; " >

            <legend style="font-size: 16px;"><b> List of Pool Member for Reconciliation</b></legend>

            <form>

                <!--newReconciliationReportforallcorp-->
                <!--<h4 align="center">List of Pool Member for Reconciliation</h4>-->

                <table align="center" width="450" border="yes">

                    <tr>
                    <tr><td>Select Calendar Year Of Event/Entry:</td>
                        <td><select name="yearq" id="yearq"></select></td></tr>
                    <td>Select the Pool Member </td>

                    <td>

                        <select name="corparateID" id="corparateID">

                            <option value="-1"></option>

                            <c:forEach items="${corporateList}" var="ele">

                                <option  value="${ele.corporateId}">${ele.corporateName}</option>

                            </c:forEach>

                        </select>

                    </td>

                    </tr>



                </table>

                <p align="center"><input type="submit" class="btn" onclick="return validate();" name="bName" value="Submit"/></p>

                <p>&nbsp;</p>

                <p>&nbsp;</p>

            </form>
        </fieldset>
    </body>



</html>
