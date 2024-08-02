<%--
    Document   : weeklyBill
    Created on : Jun 18, 2019, 5:36:27 PM
    Author     : cdac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Weekly Bill Page</title>
        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="/js/jquery.min.js" type="text/javascript"></script>
        <script>
            $(document).ready(function () {
                var $select = $(".weekClass");
                for (i = 1; i <= 53; i++) {
                    $select.append($('<option></option>').val(i).html(i))
                }

                var $select1 = $(".yearClass");
                for (i = 2000; i <= 2050; i++) {
                    $select1.append($('<option></option>').val(i).html(i))
                }
            });

        </script>
        <style>
            table { margin: auto; }

            input[type=submit] {

                padding: 10px 14px;
                border: 2px solid #f2f2f2;
                font-weight:bold;
                color:#ffffff;
                alignment-adjust:central;
                background-color:#964000;
            }

            input[type=submit]:hover, input[type=submit]:focus {
                background-color: #151b54;
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
    <body>


        <form>
            <fieldset>
                <legend> <h3 align="center" style="color:#003366;" >Weekly Bill</h3></legend>

                <table>
                    <tr><td>Select the Week</td>

                        <td><select name="week" class="weekClass" id="weekId" required>

                            </select>
                        </td>
                    </tr>
                    <tr><td>Select the RPC FY-Year</td>

                        <td><select name="year" class="yearClass" id="yearId" required>

                            </select>

                        </td>
                    </tr>
                    <tr><td>Select Bill Category</td>

                        <td><select name="billCat" id="billCatId" required>
                                <option value="DSM">DSM </option>
                                <option value="RRAS">RRAS </option>
                                <option value="AGC">AGC</option>
                                <option value="FRAS">FRAS</option>


                            </select>

                        </td>
                    </tr>
                    <tr><td>Select Bill Type</td>

                        <td><select name="billType" id="billTypeId" required>
                                <option value="Original">Original </option>
                                <option value="Revised">Revised</option>
                                <option value="1">R1 </option>
                                <option value="2">R2 </option>
                                <option value="3">R3 </option>
                                <option value="4">R4 </option>
                                <option value="5">R5 </option>
                                <option value="6">R6 </option>
                                <option value="7">R7 </option>
                                <option value="8">R8 </option>
                                <option value="9">R9 </option>
                            </select>

                        </td>
                    </tr>
                </table>

                <br/>
                <input type="submit" value="Submit"  name="bName" style="margin-left:45%;"/>
            </fieldset>
        </form>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <br/>
    </body>
</html>
