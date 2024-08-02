
<%--
    Document   : viewResetPasswordbySysAdmin
    Created on : Dec 10, 2019, 10:43:19 AM
    Author     : shubham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
    </head>
    <body style="background-color:#FBF2EA;">

        <h3 id="idheader">Reset Password</h3><br>
        <div class="noborderedtable">
            <form>
                <table align="center" width="50%">



                    <tr>
                        <td>Select the Login ID</td>

                        <td>
                            <select name="loginid" id="loginid">
                                <c:forEach items="${UserList}" var="interpay">
                                    <option><c:out value="${interpay.loginId}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>Enter New Password</td>
                        <td><input type="password" name="newpasswd" id="newpasswd"/>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>


                </table>
                <div style="text-align: center;">

                    <input  type="submit" name="bName" id="Update" value="Update" onclick="return Validate();"  style="font-size:12px;"/>
                </div><br><br>
            </form>
        </div>
        <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
        <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
        <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
        <script>
                        $(document).ready(function () {
                            $('#Update').button();

                            $("#Update").click(function () {
                                if (confirm("You are about to reset the password"))
                                    return true;
                                else
                                    return false;
                            });

                        });
                        function Validate() {

                            if (document.getElementById("loginid").value == -1) {

                                alert('Please enter the LoginId');
                                return false;
                            }
                            var passwd = document.getElementById("newpasswd").value;
                            if (passwd == "")
                            {
                                alert('Please enter the password');
                                return false;
                            }


                        }
        </script>
    </body>
</html>
