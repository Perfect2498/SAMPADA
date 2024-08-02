<%--
    Document   : changePassword
    Created on : 18 Oct, 2019, 1:59:52 PM
    Author     : superusr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
            <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
            <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0" />
            <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css" />" />
            <script src="<c:url value="/js/jquery.min.js"/>" ></script>
            <script src="<c:url value="/js/bootstrap.min.js" />" ></script>
            <script type="text/javascript">

                $(document).ready(function () {
                    $('#bname').attr("disabled", true);

                    $("#oldpasswd").blur(function (e) {
                        e.preventDefault();
                        var dataString = $("#changepassform").serialize();
                        var oldpasswd = $("#oldpasswd").val();
                        if (oldpasswd.length > 0)
                        {

                            dataString = "elementName=" + oldpasswd;
                            $.ajax({type: "POST",
                                url: "checkForOldpasswd.htm",
                                data: dataString,
                                success: function (result) {

                                    if (result != "Available")
                                    {
                                        alert('Please check the Old Password');
                                        $('#bname').attr("disabled", true);

                                    }
                                    else
                                    {
                                        //  alert(result);
                                        $('#bname').attr("disabled", false);

                                    }
                                },
                                error: function (result)
                                {
                                    alert('error');
                                }
                            });
                        } else
                        {
                            alert('Please check the length of Old Password');
                            $('#bname').attr("disabled", true);
                        }
                    });

                });

                function validate()
                {
                    var newpass = document.getElementById('newpasswd').value;
                    var conpasswd = document.getElementById('conpasswd').value;
                    var oldpasswd = document.getElementById('oldpasswd').value;

                    if (oldpasswd == newpass)
                    {

                        alert('please check the new password should not be same as old password');
                        return false;
                    }


                    if (conpasswd == newpass)
                    {
                        return true;
                    }
                    else
                    {
                        alert('please check the new password is not matching');
                        return false;
                    }
                    return true;
                }

            </script>
        </head>
        <body style="min-height: 600px;">
            <form name="changepassform">
                <h3 align="center">Change Password</h3>
                <table class="customers" align="center" style="width: 60%;">
                    <tr><td>Enter Old Password</td><td><input type="text" name="oldpasswd" id="oldpasswd" required/></td></tr>
                    <tr><td>&nbsp;</td></tr>

                    <tr><td>Enter New Password</td><td><input type="text" name="newpasswd" id="newpasswd" required/></td></tr>
                    <tr><td>&nbsp;</td></tr>

                    <tr><td>Confirm New Password</td><td><input type="text" name="conpasswd" id="conpasswd" required/></td></tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td><input type="submit" name="bname" id="bname" value="Submit" onclick="return validate();" /></td><td>&nbsp;</td></tr>
                </table>
                <br><br>
            </form><br><br>
        </body>
    </html>