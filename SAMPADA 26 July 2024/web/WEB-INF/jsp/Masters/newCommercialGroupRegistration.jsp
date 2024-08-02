<%--
Document : newCommercialGroupRegistration
Created on : Jun 20, 2019, 8:02:39 AM
Author : superusr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
    <head>
        <script type = "text/javascript" >
            function preventBack() {
                window.history.forward();
            }
            setTimeout("preventBack()", 0);
            window.onunload = function () {
                null;
            };
        </script>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pool Member Registration Page</title> <link href="css/mystyle.css" type="text/css" rel="stylesheet" >
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <title>JSP Page</title>
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';
        </script>

        <script>

            $(document).ready(function () {
                $('#commercialname').blur(function () {
                    var corpName = $("#commercialname").val();
                    var namenotspace = /^(?!\s+$).+/;
                    var nameformat = /^[a-zA-Z0-9\s]+$/;
                    if (!corpName.match(namenotspace))
                    {
                        // alert('Please enter the Commercial Short name');

                        $('#commercialname').val("");
                        return false;
                    }
//                    if (!corpName.match(nameformat))
//                    {
//                        alert('Special characters are not allowed for entering the Commercial name');
//                        $('#commercialname').val("");
//                        return false;
//                    }
                    alert("Hey!! you have entered the commercial SName!!")
                    alert("corpName " + corpName);
                    $.ajax({
                        url: basePath + "/CheckUniqueCommercialGrpShortName",
                        data: {
                            "corpName": corpName
                        },
                        type: "POST",
                        success: function (data) {
                            var dataObject = jQuery.parseJSON(data);
                            alert(dataObject.msg);
                            var n = (dataObject.msg).includes("already");
                            //alert(n);
                            if (n) {
                                $('#commercialname').val("");
                            }


                        },
                        error: function () {
                            console.log('error');
                        }
                    });

                });

            });

        </script>
        <script>

            function validate()
            {
                var subAccFrmt = /^[a-zA-Z0-9]+$/;
                var nameformat = /^[a-zA-Z0-9\s]+$/;
                var namenotspace = /^(?!\s+$).+/;
                var phno1 = /^\d{1,20}$/;
                // var phno1 = /^[0-9]\d{2,4}-\d{6,8}$/;
                var phno = /^\d{10}$/;
                var commercialname = document.getElementById("commercialname").value;
                var scommercialname = document.getElementById("scommercialname").value;
                var state = document.getElementById("state").value;
                var cpdsm = document.getElementById("cpdsm").value;
                var cprras = document.getElementById("cprras").value;
                var cpcong = document.getElementById("cpcong").value;
                var cpreact = document.getElementById("cpreact").value;
                var mobile = document.getElementById("mobile").value;
                var office = document.getElementById("office").value;
                var address = document.getElementById("address").value;
                var location = document.getElementById("location").value;
                var subAccNo = document.getElementById("subAccNo").value;
                var corpType = document.getElementById("corpType").value;
                var BankAccName = document.getElementById("BankAccName").value;


                if (scommercialname == "")
                {
                    alert('Please enter the Commercial name');

                    return false;
                }
                if (!scommercialname.match(namenotspace))
                {
                    alert('Please enter the Commercial name');
                    document.getElementById('scommercialname').value = "";
                    return false;
                }


//                if (!scommercialname.match(nameformat))
//                {
//                    alert('Special characters are not allowed for entering the Commercial name');
//                    document.getElementById('scommercialname').value = "";
//                    return false;
//                }
                if (commercialname == "")
                {
                    alert('Please enter the Commercial Short name');

                    return false;
                }


                if (!commercialname.match(namenotspace))
                {
                    alert('Please enter the Commercial Short name');

                    return false;
                }

                if (address == "")
                {
                    alert('Please enter the Address');

                    return false;
                }
                if (!address.match(namenotspace))
                {
                    alert('Please enter the Address');
                    document.getElementById('address').value = "";
                    return false;
                }
                if (location == "")
                {
                    alert('Please enter the Location');

                    return false;
                }
                if (!location.match(namenotspace))
                {
                    alert('Please enter the Location');
                    document.getElementById('location').value = "";
                    return false;
                }
                if (!location.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the location name');
                    document.getElementById('location').value = "";
                    return false;
                }
                if (state == "-1")
                {
                    alert('Please select the state name!');

                    return false;
                }
                if (corpType == "-1")
                {
                    alert('Please select the Pool Member Type!!');

                    return false;
                }

                if (cpdsm == "")
                {
                    alert('Please enter the Contact Person DSM ');

                    return false;
                }
                if (!cpdsm.match(namenotspace))
                {
                    alert('Please enter the Contact Person DSM ');
                    document.getElementById('cpdsm').value = "";
                    return false;
                }
                if (!cpdsm.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the Contact Person DSM');
                    document.getElementById('cpdsm').value = "";
                    return false;
                }
                if (cprras == "")
                {
                    alert('Please enter the Contact Person RRAS ');

                    return false;
                }
                if (!cprras.match(namenotspace))
                {
                    alert('Please enter the Contact Person RRAS ');
                    document.getElementById('cprras').value = "";
                    return false;
                }
                if (!cprras.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the Contact Person RRAS');
                    document.getElementById('cprras').value = "";
                    return false;
                }
                if (cpcong == "")
                {
                    alert('Please enter the Contact Person Cong ');

                    return false;
                }
                if (!cpcong.match(namenotspace))
                {
                    alert('Please enter the Contact Person Cong ');
                    document.getElementById('cpcong').value = "";
                    return false;
                }
                if (!cpcong.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the Contact Person Cong');
                    document.getElementById('cpcong').value = "";
                    return false;
                }
                if (cpreact == "")
                {
                    alert('Please enter the Contact Person Reactive ');

                    return false;
                }
                if (!cpreact.match(namenotspace))
                {
                    alert('Please enter the Contact Person Reactive ');
                    document.getElementById('cpreact').value = "";
                    return false;
                }
                if (!cpreact.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the Contact Person Reactive');
                    document.getElementById('cpreact').value = "";
                    return false;
                }
                if (mobile == "")
                {
                    alert('Please enter the Mobile Number ');


                    return false;
                    match(phno)
                }

                if (!mobile.match(phno))
                {
                    alert('Mobile number must be ten digit ');

                    document.getElementById('mobile').value = "";
                    return false;

                }
                if (office == "")
                {
                    alert('Please enter the Agency Code');

                    return false;
                    match(phno1)
                }
                if (!office.match(namenotspace))
                {
                    alert('Please enter the Agency Code ');
                    document.getElementById('office').value = "";
                    return false;
                }
//                if (!office.match(phno1))
//                {
//                    alert('Agency Code should have only numbers.');
//                    document.getElementById('office').value = "";
//                    return false;
//
//                }
                if (subAccNo == "")
                {
                    alert('Please enter the Bank Sub Account Number!');

                    return false;
                }
                if (!subAccNo.match(subAccFrmt))
                {
                    alert('Please enter the Bank Sub Account Number!');
                    document.getElementById('subAccNo').value = "";
                    return false;
                }

                if (BankAccName == "")
                {
                    alert('Please enter the Bank Account name');

                    return false;
                }
                if (!BankAccName.match(namenotspace))
                {
                    alert('Please enter the  Bank Account name');
                    document.getElementById('BankAccName').value = "";
                    return false;
                }
                return true;

            }

        </script>


    </head>
    <body style="min-height: 700px;">
        <form name="newcommercialregister" method="post" >

            <p align="center" style="font-size: 40px;color:#003366;"><b>Pool Member Registration</b></p>
            <table align="center" class="customerTable" style=" width: 90%;">
                <tr><td>Pool Member Name<span style="color:red;">*</span></td>
                    <td> <input type="text" name="scommercialname" id="scommercialname" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                    <td>Pool Member Short Name<span style="color:red;">*</span></td>
                    <td><input type="text" name="commercialname" id="commercialname" required maxlength="50" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>


                </tr>

                <tr>
                    <td>Address<span style="color:red;">*</span></td>
                    <td><input type="text" name="address" id="address" required maxlength="250" autocomplete="off"/></td>

                    <td>Location<span style="color:red;">*</span></td>
                    <td><input type="text" name="location" id="location" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>

                <tr><td>Select the State<span style="color:red;">*</span></td>


                    <td><select id="state" name="state" required>
                            <option selected value="-1">Select</option>
                            <c:forEach var="item" items="${stateList}">
                                <option value="${item.stateName}">${item.stateName}</option>
                            </c:forEach>
                        </select></td>


                    <td>Contact Person DSM<span style="color:red;">*</span></td>
                    <td><input type="text" name="cpdsm" id="cpdsm" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>

                <tr>
                    <td>Contact Person RRAS<span style="color:red;">*</span></td>
                    <td><input type="text" name="cprras" id="cprras" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>

                    <td>Contact Person Cong<span style="color:red;">*</span></td>
                    <td><input type="text" name="cpcong" id="cpcong" maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>

                <tr>
                    <td>Contact Person Reactive<span style="color:red;">*</span></td>
                    <td><input type="text" name="cpreact" id="cpreact" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>

                    <td>Mobile No.<span style="color:red;">*</span></td>
                    <td><input type="text" name="mobile" id="mobile"  maxlength="10" pattern="(^\d{10}+$)" autocomplete="off" required/></td>
                </tr>


                <tr>
                    <td>Agency Code<span style="color:red;">*</span></td>
                    <td><input type="text" name="office" id="office"   maxlength="20" autocomplete="off" required/></td>
                    <td>Bank Sub Account Number<span style="color:red;">*</span></td>
                    <td><input type="text" name="subAccNo" id="subAccNo" maxlength="20" autocomplete="off" pattern="(^[a-zA-Z0-9]+$)"/></td>

                </tr>
                <tr><td >Pool Member Type<span style="color:red;">*</span></td>


                    <td ><select id="corpType" name="corpType" required>
                            <option selected value="-1" >Select</option>
                            <option value="IR">IR</option>
                            <option value="CORP">CORP</option>
                        </select></td>

                    <td>Bank Account Name<span style="color:red;">*</span></td>
                    <td> <input type="text" name="BankAccName" id="BankAccName" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>

                </tr>
            </table>

            <!--</fieldset><br><br>-->
            <br/>
            <div style="text-align: center">
                <p><input type="submit" name="bname" class="btn" onclick="return validate();" value="Submit"/></p>
                <!-- <td><input name="Approve" value="Approve" onclick="return validate();" id="Approve" type="submit" ></td>-->
            </div>
        </form>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p> <p>&nbsp;</p>
    </body>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p> <p>&nbsp;</p>
</html>