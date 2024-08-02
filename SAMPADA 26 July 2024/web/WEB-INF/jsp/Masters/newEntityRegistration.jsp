<%--
Document : newEntityRegistration
Created on : Jun 18, 2019, 3:08:53 PM
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
        <title>Entity Registration Page</title> <link href="css/mystyle.css" type="text/css" rel="stylesheet" >
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
                $('#entityname').blur(function () {
                    var entityName = $("#entityname").val();
                    var namenotspace = /^(?!\s+$).+/;
                    var nameformat = /^[a-zA-Z0-9\s]+$/;
                    if (!entityName.match(namenotspace))
                    {
                        //alert('Please enter the entity Short name');

                        $('#entityname').val("");
                        return false;
                    }
//                    if (!entityName.match(nameformat))
//                    {
//                        alert('Special characters are not allowed for entering the Commercial name');
//                        $('#entityname').val("");
//                        return false;
//                    }
//                    alert("Hey!! you have entered the Entity SName!!")
                    alert("entityName " + entityName);
                    $.ajax({
                        url: basePath + "/CheckUniqueEntityShortName",
                        data: {
                            "entityName": entityName
                        },
                        type: "POST",
                        success: function (data) {
                            var dataObject = jQuery.parseJSON(data);
                            alert(dataObject.msg);
                            var n = (dataObject.msg).includes("already");
                            // alert(n);
                            if (n) {
                                $('#entityname').val("");
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
                var nameformat = /^[a-zA-Z0-9\s]+$/;
                var minaccno = /^\d{9,18}$/;
                var namenotspace = /^(?!\s+$).+/;
                var phno1 = /^\d{1,20}$/;
                // var phno1 = /^[0-9]\d{2,4}-\d{6,8}$/;
                var phno = /^\d{10}$/;
                var entityname = document.getElementById("entityname").value;//Added on 22-MARCH-2018
                var sentityname = document.getElementById("sentityname").value;//Added on 22-MARCH-2018
                var bankname = document.getElementById("bankname").value;
                var branchname = document.getElementById("branchname").value;
                var accno = document.getElementById("accno").value;
                var ifsc = document.getElementById("ifsc").value;
                var state = document.getElementById("state").value;
                var cpdsm = document.getElementById("cpdsm").value;
                var cprras = document.getElementById("cprras").value;
                var cpcong = document.getElementById("cpcong").value;
                var cpreact = document.getElementById("cpreact").value;
                var mobile = document.getElementById("mobile").value;
                var office = document.getElementById("office").value;

                var rtgsneft = document.getElementById("rtgsneft").value;
                var rrasconsiderable = document.getElementById("rrasconsiderable").value;
                var agcconsiderable = document.getElementById("agcconsiderable").value;
                var corpId = document.getElementById("corpId").value;
                var entityType = document.getElementById("entityType").value;
                var address = document.getElementById("address").value;
                var location = document.getElementById("location").value;

                var frasconsiderable = document.getElementById("frasconsiderable").value;
                var cpfras = document.getElementById("cpfras").value;
                var srasconsiderable = document.getElementById("srasconsiderable").value;

                var trasconsiderable = document.getElementById("trasconsiderable").value;

                if (sentityname == "")
                {
                    alert('Please enter the Entity name');

                    return false;
                }
                if (!sentityname.match(namenotspace))
                {
                    alert('Please enter the Entity name');
                    document.getElementById('sentityname').value = "";
                    return false;
                }
//                if (!sentityname.match(nameformat))
//                {
//                    alert('Special characters are not allowed for entering the Entity name');
//                    document.getElementById('sentityname').value = "";
//                    return false;
//                }
                if (entityname == "")
                {
                    alert('Please enter the Entity Short name');

                    return false;
                }
                // if (!entityname.match(namenotspace))
                // {
                // alert('Please enter the Entity Short name');
                // document.getElementById('entityname').value = "";
                // return false;
                // }
                if (corpId == "-1")
                {
                    alert('Please select the Commercial Group');

                    return false;
                }
                if (entityType == "-1")
                {
                    alert('Please select the Entity Type');

                    return false;
                }
                if (bankname == "")
                {
                    alert('Please enter the Bank name');

                    return false;
                }
                if (!bankname.match(namenotspace))
                {
                    alert('Please enter the Bank name');
                    document.getElementById('bankname').value = "";
                    return false;
                }
                if (!bankname.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the bank name');
                    document.getElementById('bankname').value = "";
                    return false;
                }
                if (branchname == "")
                {
                    alert('Please enter the Branch Name ');

                    return false;
                }
                if (!branchname.match(namenotspace))
                {
                    alert('Please enter the Branch Name ');
                    document.getElementById('branchname').value = "";
                    return false;
                }
                if (!branchname.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the branch name');
                    document.getElementById('branchname').value = "";
                    return false;
                }
                if (accno == "")
                {
                    alert('Please enter the Account number');
                    document.getElementById('accno').value = "";
                    return false;
                }
                if (!accno.match(namenotspace))
                {
                    alert('Please enter the Account number');
                    document.getElementById('accno').value = "";
                    return false;
                }
                if (isNaN(accno))
                {
                    alert('Please enter only digits for Account number');
                    document.getElementById('accno').value = "";
                    return false;
                }
                if (!accno.match(minaccno))
                {
                    alert('Please enter the valid Account number of min 9 digits to max 18 digits');
                    document.getElementById('accno').value = "";
                    return false;
                }

                if (ifsc == "")
                {
                    alert('Please enter the IFSC code ');

                    return false;
                }
                if (!ifsc.match(namenotspace))
                {
                    alert('Please enter the IFSC code ');
                    document.getElementById('ifsc').value = "";
                    return false;
                }
                if (!ifsc.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the ifsc code');
                    document.getElementById('ifsc').value = "";
                    return false;
                }
                if (rtgsneft == "-1")
                {
                    alert('Please select the RTGS/NEFT enabled');

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
                    alert('Please select the state name');

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
                    alert('Special characters are not allowed for entering the Contact Person DSM name');
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
                    alert('Special characters are not allowed for entering the Contact Person RRAS name');
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
                    alert('Special characters are not allowed for entering the Contact Person Cong name');
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
                    alert('Special characters are not allowed for entering the Contact Person Reactive name');
                    document.getElementById('cpreact').value = "";
                    return false;
                }
                if (mobile == "")
                {
                    alert('Please enter the Mobile Number ');

                    return false;
                    match(phno);
                }
                if (!mobile.match(namenotspace))
                {
                    alert('Please enter the Mobile Number ');
                    document.getElementById('mobile').value = "";
                    return false;
                }
                if (!mobile.match(phno))
                {
                    alert('Mobile number must be ten digit ');
                    document.getElementById('mobile').value = "";
                    return false;

                }
                if (office == "")
                {
                    alert('Please enter the Office Number ');

                    return false;
                    match(phno1);
                }
                if (!office.match(namenotspace))
                {
                    alert('Please enter the Office Number ');
                    document.getElementById('office').value = "";
                    return false;
                }
                if (!office.match(phno1))
                {
                    alert('Office number must should have only numbers');
                    document.getElementById('office').value = "";
                    return false;

                }

                if (rrasconsiderable == "-1")
                {
                    alert('Please select the RRAS Considerable');

                    return false;
                }
                if (agcconsiderable == "-1")
                {
                    alert('Please select the AGC Considerable');

                    return false;
                }
                if (frasconsiderable == "-1")
                {
                    alert('Please select the FRAS Considerable');

                    return false;
                }

                if (cpfras == "")
                {
                    alert('Please enter the Contact Person FRAS ');

                    return false;
                }
                if (!cpfras.match(namenotspace))
                {
                    alert('Please enter the Contact Person FRAS ');
                    document.getElementById('cpfras').value = "";
                    return false;
                }
                if (!cpfras.match(nameformat))
                {
                    alert('Special characters are not allowed for entering the Contact Person FRAS name');
                    document.getElementById('cpfras').value = "";
                    return false;
                }
                if (srasconsiderable == "-1")
                {
                    alert('Please select the SRAS Considerable');

                    return false;
                }
                 if (trasconsiderable == "-1")
                {
                    alert('Please select the TRAS Considerable');

                    return false;
                }

                


                        return true;
            }

        </script>


    </head>
    <body style="min-height: 700px;">
        <form name="newregister" method="post" >
            <!--<h3 style="text-align: center" id="idheader"></h3><br>-->
            <!--<fieldset id="fieldset2">-->
            <p align="center" style="font-size: 40px;color:#003366;"><b>Entity Registration</b></p>
            <!--<legend style="background-color: oldlace;">Entity Details</legend>-->
            <table align="center" class="customerTable" style=" width: 90%;" >

                <tr>
                    <td>Entity Name<span style="color:red;">*</span></td>
                    <td> <input type="text" name="sentityname" id="sentityname" maxlength="100" autocomplete="off" required /></td>
                    <td>Entity Short Name<span style="color:red;">*</span></td>
                    <td><input type="text" name="entityname" id="entityname" maxlength="50" autocomplete="off" required /></td>
                </tr>

                <tr><td>Select the Commercial Group</td>

                    <td><select name="corpId" id="corpId" required>
                            <option selected value="-1">Select</option>
                            <c:forEach items="${corpList}" var="pflow">
                                <option value="${pflow.corporateId}"><c:out value="${pflow.corporateName}"/></option>
                            </c:forEach>
                        </select> </td>


                    <td>Select the Entity Type<span style="color:red;">*</span></td>

                    <td><select id="entityType" name="entityType" required>
                            <option selected value="-1">Select</option>
                            <c:forEach var="item" items="${entityTypeList}">
                                <option value="${item.entityType}">${item.entityType}</option>
                            </c:forEach>
                        </select></td>
                </tr>
                <tr>

                <tr>
                    <td>Bank Name<span style="color:red;">*</span></td>
                    <td> <input type="text" name="bankname" id="bankname" maxlength="100" autocomplete="off" required pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>

                    <td>Branch Name<span style="color:red;">*</span></td>
                    <td> <input type="text" name="branchname" id="branchname" maxlength="100" autocomplete="off" required pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>
                <tr>
                    <td>Bank Account No.<span style="color:red;">*</span></td>
                    <td><input type="text" name="accno" id="accno" required maxlength="100" autocomplete="off"/></td>

                    <td>IFSC<span style="color:red;">*</span></td>
                    <td><input type="text" name="ifsc" id="ifsc" maxlength="20" autocomplete="off" required pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>

                <tr><td>RTGS/NEFT enabled<span style="color:red;">*</span></td>
                    <td><select name="rtgsneft" id="rtgsneft" required >
                            <option selected value="-1">Select</option>
                            <option value="N">N</option>
                            <option value="Y">Y</option>
                        </select></td>
                    <td>Address<span style="color:red;">*</span></td>
                    <td><input type="text" name="address" id="address" required maxlength="100" autocomplete="off"/></td>
                </tr>
                <tr>
                    <td>Location<span style="color:red;">*</span></td>
                    <td><input type="text" name="location" id="location" maxlength="100" autocomplete="off" required pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                    <td>Select the State<span style="color:red;">*</span></td>
                    <td><select id="state" name="state" required>
                            <option selected value="-1">Select</option>
                            <c:forEach var="item" items="${stateList}">
                                <option value="${item.stateName}">${item.stateName}</option>
                            </c:forEach>
                        </select></td>
                </tr>

                <tr>
                    <td>Contact Person DSM<span style="color:red;">*</span></td>
                    <td><input type="text" name="cpdsm" id="cpdsm" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>

                    <td>Contact Person RRAS<span style="color:red;">*</span></td>
                    <td><input type="text" name="cprras" id="cprras" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>

                <tr>
                    <td>Contact Person Cong<span style="color:red;">*</span></td>
                    <td><input type="text" name="cpcong" id="cpcong" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>

                    <td>Contact Person Reactive<span style="color:red;">*</span></td>
                    <td><input type="text" name="cpreact" id="cpreact" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>


                <tr>
                    <td>Mobile No.<span style="color:red;">*</span></td>
                    <td><input type="text" name="mobile" id="mobile" required pattern="(^\d{10}+$)" autocomplete="off" maxlength="10" /></td>

                    <td>Office No.<span style="color:red;">*</span></td>
                    <td><input type="text" name="office" id="office" required pattern="(^\d{1,20}$)" autocomplete="off" maxlength="20"/></td>
                </tr>

                <tr><td>RRAS Considerable<span style="color:red;">*</span></td>
                    <td><select name="rrasconsiderable" id="rrasconsiderable" required>
                            <option selected value="-1">Select</option>
                            <option value="N">N</option>
                            <option value="Y">Y</option>
                        </select>


                    <td>AGC Considerable<span style="color:red;">*</span></td>
                    <td> <select name="agcconsiderable" id="agcconsiderable" required>
                            <option selected value="-1">Select</option>
                            <option value="N">N</option>
                            <option value="Y">Y</option>
                        </select>
                </tr>

                <tr><td>FRAS Considerable<span style="color:red;">*</span></td>
                    <td><select name="frasconsiderable" id="frasconsiderable" required>
                            <option selected value="-1">Select</option>
                            <option value="N">N</option>
                            <option value="Y">Y</option>
                        </select>

                    <td>Contact Person FRAS<span style="color:red;">*</span></td>
                    <td><input type="text" name="cpfras" id="cpfras" required maxlength="100" autocomplete="off" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"/></td>
                </tr>

                <tr><td>SRAS Considerable<span style="color:red;">*</span></td>
                    <td><select name="srasconsiderable" id="srasconsiderable" required>
                            <option selected value="-1">Select</option>
                            <option value="N">N</option>
                            <option value="Y">Y</option>
                        </select>


                    <td>TRAS Considerable<span style="color:red;">*</span></td>
                    <td> <select name="trasconsiderable" id="trasconsiderable" required>
                            <option selected value="-1">Select</option>
                            <option value="N">N</option>
                            <option value="Y">Y</option>
                        </select>
                </tr>
            </table>

            <!--</fieldset>-->
            <br>
            <div style="text-align: center">
                <p><input type="submit" name="bname" onclick="return validate();" class="btn" value="Submit"/></p>
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