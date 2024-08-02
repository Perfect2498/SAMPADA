<%--
    Document   : viewResetPasswordbyApplicant
    Created on : Dec 10, 2019, 11:16:19 AM
    Author     : shubham
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <script src="<c:url value="/js/jquery-1.12.4.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
        <script>
            $(document).ready(function () {
//            $(document).on('focusout','#curpasswd',function(){
//                 alert("hello");
//                            checkForUserPassword($(this).val());
//
//
//                        });

                $("#confpasswd").blur(function (e) {

                    var newpasswd = $("input#newpasswd").val();
                    var confpasswd = $("input#confpasswd").val();

                    if (!(newpasswd == confpasswd))
                    {
                        alert('New Paasword not matching');
                    }
                });

                $("#curpasswd").blur(function (e) {

                    var dataString = $("#resetpasswd").serialize();
                    var curpasswd = $("input#curpasswd").val();
                    dataString = "curpasswd=" + curpasswd;
                    //alert(curpasswd);

                    $.ajax({type: "POST",
                        url: "checkForUserPassword.htm",
                        data: dataString,
                        success: function (result) {


                            //  if(result.toString()!="matched"){
                            // alert(result.toString());
                            //   $("#resetpasswd").find('input').val("");
                            if (result != "matched") {
                                alert("Invalid Current Password");
                                $("input#curpasswd").val("");
                            }
                            else {
                                alert("Current Password is Valid");
                                //alert(result.toString());

                            }
                        },
                        error: function (result)
                        {
                            //$("input#curpasswd").val("");
                            alert('Please Enter Valid Current Password');

                        }

                    });


                });

            });


        </script>
        <style>
            body{
                color: #000080;
            }
        </style>
    </head>
    <body>
        <p style="color:blueviolet;"><c:out value="${msg}"></c:out></p>

        <h3 id="idheader">Change Password</h3><br><br>
        <div class="noborderedtable"><br>
            <form name="resetpasswd">

                <table align="center">
                    <tr>
                        <td>Old Password</td>
                        <td>:</td>
                        <td>&nbsp;</td>
                        <td><input type="password" name="curpasswd" id="curpasswd" autocomplete="off" required/></td>
                    </tr>

                    <tr>
                        <td>New Password</td>
                        <td>:</td>
                        <td>&nbsp;</td>
                        <td><input type="password" name="newpasswd" id="newpasswd" autocomplete="off" required/></td>
                    </tr>

                    <tr>
                        <td>Re-Type New Password</td>
                        <td>:</td>
                        <td>&nbsp;</td>
                        <td><input type="password" name="confpasswd" id="confpasswd" autocomplete="off"  required /></td>
                    </tr>

                </table>

                <p style="text-align:center;"> <input align="center" type="submit" name="bname" id="Update" value="Update" /></p>



            </form>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
        </div>
    </body>
    <script type="text/javascript">
        $(document).ready(function () {
            $('input[type="submit"]').button();

            $("#Update").click(function () {
                if (confirm("You are about to update the Profile Password"))
                    return true;
                else
                    return false;
            });
        });
    </script>
</html>
