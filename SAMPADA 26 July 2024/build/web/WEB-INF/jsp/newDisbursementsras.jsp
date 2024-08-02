<%-- 
    Document   : newDisbursementsras
    Created on : 31 Jan, 2024, 11:55:19 AM
    Author     : Administrator
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';</script>
        <script>
            function getDocHeight(doc) {
                doc = doc || document;
                var body = doc.body, html = doc.documentElement;
                var height = Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);
                return height;
            }
            function setIframeHeight(id) {
                var ifrm = document.getElementById(id);
                var doc = ifrm.contentDocument ? ifrm.contentDocument : ifrm.contentWindow.document;
                ifrm.style.visibility = 'hidden';
                ifrm.style.height = "10px"; // reset to minimal height in case going from longer to shorter doc
                ifrm.style.height = getDocHeight(doc) + 5 + "px";
                ifrm.style.visibility = 'visible';
                var iframe = window.parent.document.getElementById('content');
                var container = document.getElementById('appcontent');
                iframe.style.height = container.offsetHeight + 50 + 'px';
            }
            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 30 + 'px';
            }
        </script>
        <script>
            $(document).ready(function () {
                var disbursetotal = 0;
                $('#bname').attr("disabled", true);
                $('#mytable tr').each(function () {
                    $(this).find("td:eq(0) input").prop('disabled', true);
                });
                $('input[type="radio"]').click(function () {
                    if (confirm('Are you sure to Disburse with this Date of Issurance (DOI) ?'))
                    {
                        $('#mytable tr').each(function () {
                            $(this).find("td:eq(0) input").prop('disabled', true);
                            $(this).find("td:eq(8) input").val(0);
                            $(this).find("td:eq(9) input").val(0);
                            $(this).find("td:eq(10) input").val("");
                        });
                        $('input:checkbox').prop('checked', false);
                        var selecteddate = $("input[name='doi']:checked").val();
                        $('#mytable tr').each(function () {
                            var dateofbill = $(this).find("td:eq(1) input").val();
                            if (selecteddate == dateofbill)
                            {
                                $(this).find("td:eq(0) input").prop('disabled', false);
                            }
                        });
                        var mainbal = $('#mainbal').val();
                        var selecteddate = $("input[name='doi']:checked").val();
                        $('#billedDate').attr('value', selecteddate);
                        $('#availamt').val(mainbal);
                        $('#diffamt').val(0);
                        if (mainbal == 0)
                        {
                            //$('#bname').attr("disabled", true);
                            alert('Please Check the Pool Account Balance???');
                        }


                        $('#csdfamt').val(0);
                    }
                }); //end of radio selection
                $('input[type="checkbox"]').click(function () {
                    if ($(this).prop("checked") == true) {
                        var customerId = 0;
                        $('#mytable').find('tr').each(function () {
                            var row = $(this);
                            if (row.find('input[type="checkbox"]').is(':checked')) {
                                $('#bname').attr("disabled", true);
                                var dateofbill = $(this).find("td:eq(1) input").val();
                                var billdate = $('input:radio[name=doi]:checked').val();
                                if (dateofbill == billdate)
                                {
                                    var recvamt = $(this).find("td:eq(7) input").val(); ///chnaged
                                    var availamt = $('#availamt').val();
                                    if ((+availamt) < (+recvamt))
                                    {
                                        alert('No Sufficient Amount ');
                                        gettotal();
                                        return false;
                                    }
                                    customerId = (+customerId) + (+recvamt);
                                    $(this).find("td:eq(8) input").val(recvamt);
                                }
                            }
                            var mainbal = $('#mainbal').val();
                            mainbal = (+mainbal) - (+customerId);
                            $("#availamt").val(mainbal);
                            $("#diffamt").val(customerId);
                        });
                    }//if
                    else if ($(this).prop("checked") == false) {
                        $('#bname').attr("disabled", true);
                        alert("Checkbox is unchecked. Please click on Calcualte .....");
                    }
                });
                function gettotal()
                {
                    disbursetotal = 0;
                    $('#mytable').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked'))
                        {
                            var recvamt = $(this).find("td:eq(7) input").val();
                            disbursetotal = (+disbursetotal) + (+recvamt);
                        }
                    }); //end of total diursement amt for checked values
                    var customerId = 0;
                    $('#mytable').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked')) {
                            // $('#bname').attr("disabled", true);
                            var recvamt = $(this).find("td:eq(7) input").val();
                            var mainbal = $('#mainbal').val();
                            var prorata = ((+recvamt) * (+mainbal)) / (+disbursetotal);
                            prorata = parseFloat(prorata);
                            $(this).find("td:eq(8) input").val(prorata.toFixed(2));
                            var baln = (+recvamt) - (+prorata.toFixed(2));
                            $(this).find("td:eq(9) input").val(baln);
                        }
                        else
                        {
                            $(this).find("td:eq(8) input").val(0);
                            $(this).find("td:eq(9) input").val(0);
                        }
                    });
                    $("#availamt").val(0);
                    var mainbal = $('#mainbal').val();
                    $("#diffamt").val(mainbal);
                }//end of total
                $('#Calculate').click(function () {
                    $('#bname').attr("disabled", false);
                    var customerId = 0;
                    $('#mytable').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked')) {
                            var dateofbill = $(this).find("td:eq(1) input").val();
                            var billdate = $('input:radio[name=doi]:checked').val();
                            if (dateofbill == billdate)
                            {
                                var recvamt = $(this).find("td:eq(7) input").val(); ///chnaged
                                var disbamt = $(this).find("td:eq(8) input").val(); ///chnaged
                                recvamt = parseFloat(recvamt).toFixed(2);
                                disbamt = parseFloat(disbamt).toFixed(2);

                                var pendamt = (+recvamt) - (+disbamt);
                                pendamt = parseFloat(pendamt).toFixed(2);

                                //                               alert(pendamt);
                                if (pendamt < 0) {
                                    alert("Pending Amount cannot be negative!! Kindly edit!!")
                                    $('#bname').attr("disabled", true);
                                }
                                $(this).find("td:eq(9) input").val(pendamt);
                                var totaldisburse = (+disbamt) + (+pendamt);
                                totaldisburse = parseFloat(totaldisburse).toFixed(2);

                                //                               alert(totaldisburse);


                                if (totaldisburse > recvamt)
                                {
                                    alert('Disbursed amount is greater than actual Balance');
                                    $(this).find("td:eq(8) input").val(0);
                                    $(this).find("td:eq(9) input").val(0);
                                    $('#bname').attr("disabled", true);
                                    return false;
                                }
                                if (disbamt == 0) {
                                    alert('Disbursed amount is should be greater than Zero');
                                    $('#bname').attr("disabled", true);
                                    return false;
                                }
                                else
                                {
                                    customerId = parseFloat(customerId).toFixed(2);

                                    customerId = (+customerId) + (+disbamt);
                                }




                            }
                        }
                        else
                        {
                            $(this).find("td:eq(8) input").val(0);
                            $(this).find("td:eq(9) input").val(0);
                        }
                        var mainbal = $('#mainbal').val();
                        mainbal = parseFloat(mainbal).toFixed(2);
                        var diff = (+customerId) - (+mainbal);
                        diff = parseFloat(diff).toFixed(2);

                        if (diff > 0.01)
                        {
                            alert('Total disburse amount is == '+customerId);
                            alert('Actual Pool Baalance is =='+mainbal);

                            $("#availamt").val(0);
                            $("#diffamt").val(mainbal);
                            $(this).find("td:eq(8) input").val(0);
                            $(this).find("td:eq(9) input").val(0);
                            alert('Total Disbursed amount is greater than actual Balance');
                            $('#bname').attr("disabled", true);
                            return false;
                        }
                        mainbal = (+mainbal) - (+customerId);
                        mainbal = parseFloat(mainbal).toFixed(2);

                        $("#availamt").val(mainbal);
                        $("#diffamt").val(customerId);
                    });
                }); //end of calculate

            });
            function validatecsdf()
            {
                var mainbal = document.getElementById("mainbal").value;
                var csdfamt = document.getElementById("csdfamt").value;
                var billedDate = document.getElementById("billedDate").value;
                //     var radios = document.getElementsByName("doi");
                //
                //     var formValid = false;
                //    var i = 0;
                //    while (!formValid && i < radios.length) {
                //        if (radios[i].checked) formValid = true;
                //        i++;
                //    }
                //
                //     alert(formValid);
                if (billedDate == "")
                {
                    alert('Please select the Date');
                    return false;
                }
                // alert(mainbal);
                // alert(csdfamt);
                var pend = (+mainbal) - (+csdfamt);
                // alert('Penjddfg'+pend);
                if (csdfamt != "0")
                {


                    if (pend <= 0)
                    {
                        alert('Please check the value greater than Main Balance');
                        return false;
                    }
                }
                else
                {
                    alert('Please provide the PSDF Amount');
                    return false;
                }


                if (confirm('Are you want to continue?')) {
                    return true;
                }
                else
                {
                    return false;
                }
                return true;
            }
            function validate()
            {
                var x = document.getElementById("billedDate").value;
                //alert(x);
                var billdate = $('input:radio[name=doi]:checked').val();
                // alert(billdate);
                if (x == "")
                {
                    alert('Please take action !!!!!!!!');
                    return false;
                }
                else
                {
                    var checkfklag = 0;
                    var rowflag = 0;
                    $('#mytable').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked')) {
                            //checkfklag++;
                            rowflag++;
                        }
                        else
                        {
                            var dateofbill = $(this).find("td:eq(1) input").val();
                            if (x == dateofbill)
                            {
                                var remarks = $(this).find("td:eq(10) input").val();
                                if (remarks == "")
                                {
                                    checkfklag = 1;
                                }
                            }
                        }
                    });
                    //  checkfklag=1;
                    if ((+checkfklag) == 1)
                    {
                        alert("please give remarks for Unchecked transactions!!");
                        return false;
                    }
                    if ((+rowflag) == 0)
                    {
                        alert("KIndly Disburse atleast one bill transaction!!");
                        return false;
                    }
                    else
                    {
                        if (confirm('Thats Great !!!!!!!!'))
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                return true;
            }

            function full()
            {

                var start = 2019;
                var end = new Date().getFullYear();
//                var end = 2029;
                var options = null;
//                var options = "<option value='2019' selected>start</option>";
                for (var year = start; year <= end; year++)
                {
                    options += "<option>" + year + "</option>";
                }
                document.getElementById("csdfYear").innerHTML = options;
            }
        </script>
        <script>
//            
//            $(document).ready(function () {
//                
//                
//                
//                
//                
//                var table = $('#payableTable').DataTable({
//                    "footerCallback": function (row, data, start, end, display) {
//                        var api = this.api(), data;
//
//                        // Remove the formatting to get integer data for summation
//                        var intVal = function (i) {
//                            return typeof i === 'string' ?
//                                    i.replace(/[\$,]/g, '') * 1 :
//                                    typeof i === 'number' ?
//                                    i : 0;
//                        };
//
//                        // Total over all pages
//                        $(api.column(0).footer()).html(
//                                ' Total (Rs.)'
//                                );
//
//
//
//
//                        total = api
//                                .column(6)
//                                .data()
//                                .reduce(function (a, b) {
//                                    return intVal(a) + intVal(b);
//                                }, 0);
//
//                        // Total over this page
//                        pageTotal = api
//                                .column(6, {page: 'current'})
//                                .data()
//                                .reduce(function (a, b) {
//                                    return intVal(a) + intVal(b);
//                                }, 0);
//
//                        // Update footer
//                        $(api.column(6).footer()).html(
//                                pageTotal.toFixed(2)
//                                );
//                    }
//                });

//              

            $('#corpId').change(function (e) {
                var corpName = $("#corpId").val();
                $.ajax({
                    url: basePath + "/CheckPendingPayableBillCorporateWise",
                    data: {
                        "corpName": corpName
                    },
                    type: "POST",
                    success: function (data) {
                        var dataObject = jQuery.parseJSON(data);
                        //alert(dataObject.msg);
                        $("#pendingBillCorp").empty();
                        $("#pendingBillCorp").append(dataObject.msg);
                    },
                    error: function () {
                        console.log('error');
                    }
                });
            });
            $('#payableTable').DataTable({
                "scrollX": true,
                "bInfo": false,
                lengthChange: true,
                searching: true,
                paging: true,
                order: [[1, 'asc']]
            });
            $('#receivaleTable').DataTable({
            "scrollX": true,
                    "bInfo": false,
                    lengthChange: true,
                    searching: true,
                    paging: true,
                    order: [[1, 'asc']]
            });
        //    });
        </script>

        <style>
            #bname[disabled]
            {
                background: black;
            }
        </style>
        <style>
            input[type="checkbox"]{
                width: 30px; /*Desired width*/
                height: 30px; /*Desired height*/
            }
            input[type="radio"]{
                width: 30px; /*Desired width*/
                height: 30px; /*Desired height*/
            }

        </style>
    </head>
    <body onload="full()" width="95%">
        <form name="billDirbusementInfoForm" method="post">
            <h3 align="center" style="color:#003366;" >Pool Account Details</h3>
            <table align="center" id="disburseTable" cellspacing="0" style="min-height:100px;width:80%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Account Number</th>
                        <th>Main Balance</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pooldetails}" var="ele">
                        <tr>
                            <td>${ele.accountNumber}</td>
                            <td>${ele.mainBalance}</td>
                    <input type="text" name="mainbal" id="mainbal" value="${ele.mainBalance}"  hidden/>
                    </tr>
                    <c:set var = "salary" scope = "session" value = "${ele.mainBalance}"/>                   
                </c:forEach>
                </tbody>
            </table>
            <br/>
            <p align="center">Available Balance<input style="width:200px; font-weight: bold" type="text" name="availamt" id="availamt" value="${salary}" readonly/>
                Date of Issuance selected : <input style="width:150px; font-weight: bold" type="text" name="billedDate" id="billedDate" readonly/>
                Disburse Amount<input style="width:200px; font-weight: bold" type="text" name="diffamt" id="diffamt" value="${diffamt}" readonly/>
            </p>
            <br>
            <br>




            <p align="center"> <button id="Calculate"  style="width:100px;height:45px;background-color: DarkOrange;" type="button" >Calculate</button>
                &nbsp; <input type="submit" style="width:100px;" class="btn" onclick="return validate();" id="bname" name="bname" value="Submit" /></p>
            <br>
            <br>
            <!--            <p align="right" id="Calculate" style="height:30px;width:90px;background-color: red;text-align: center;color:white;font-size: 18px;">Calculate</p>-->
            
            <h2 style="color:black;" id="h2text">
                ${warning}
                <c:if test="${warning=='Showing LAST 5 Bill Issuance dates.'}">
                    <input type="button" value="Show All Dates" name="getAll" id="getAll" onclick="document.getElementById('form101').submit();">
                </c:if>
            </h2>
            
            <table id="mytable" align="center" cellspacing="0" style="min-height:400px;width:80%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Select</th>
                        <th>Billing Date</th>
                        <th>Week</th>
                        <th>Type</th>
                        <th>Revision</th>
                        <th>Commercial Group</th>
                        <th>Total Amount</th>
                        <th>Balance</th>
                        <th>Disburse amount</th>
                        <th>Pending Amount</th>
                        <th>Remarks</th>
                        <th>Payable Amount</th>
                        <th>Receivable Amount</th>
                        <th>Bill Year</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int cnt = 0;
                        String uniqueID = "uniqueID";
                        String uniqueID1;
                        String weekID = "weekID";
                        String weekID1;
                        String totalamount = "totalamount";
                        String totalamount1;
                        String disamount = "disamount";
                        String disamount1;
                        String corpID = "corpID";
                        String corpID1;
                        String billdate = "billdate";
                        String billdate1;
                        String billtype = "billtype";
                        String billtype1;
                        String balamt = "balamt";
                        String balamt1;
                        String disamt = "disamt";
                        String disamt1;
                        String remarks = "remarks";
                        String remarks1;
                        String billdue = "billdue";
                        String billdue1 = null;
                        String transactiontype = "transactiontype";
                        String transactiontype1 = null;
                    %>
                    <c:forEach items="${billdateList}" var="objectList">
                        <fmt:formatDate value="${objectList}" pattern="yyyy-MM-dd" var="billdate" />
                        <tr>
                            <td>&nbsp;</td>
                            <td><input type="radio" name="doi" id="doi" value="${billdate}">${billdate}</input></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <c:forEach items="${weekList}" var="objectWeek">
                            <%int dsmflag = 0; %>

                            <c:forEach items="${priorityList}" var="priority">

                                <%int flag = 0; %>
                                <c:forEach items="${disburseList}" var="ele">
                                    <fmt:formatDate value="${ele.billingDate}" pattern="yyyy-MM-dd" var="disburseDate" />
                                    <c:if test="${disburseDate==billdate}">
                                        <c:if test="${ele.weekId==objectWeek}">
                                            <c:if test="${ele.revisionNo==0}">
                                                <c:if test="${ele.billPriority==priority}">
                                                    <c:if test="${ele.pendingAmount!=0}">
                                                        <%
                                                            cnt++;
                                                            billdate1 = billdate + cnt;
                                                            weekID1 = weekID + cnt;
                                                            totalamount1 = totalamount + cnt;
                                                            disamount1 = disamount + cnt;
                                                            corpID1 = corpID + cnt;
                                                            uniqueID1 = uniqueID + cnt;
                                                            billtype1 = billtype + cnt;
                                                            balamt1 = balamt + cnt;
                                                            disamt1 = disamt + cnt;
                                                            remarks1 = remarks + cnt;
                                                            billdue1 = billdue + cnt;
                                                            transactiontype = transactiontype + cnt;
                                                        %>
                                                        <tr>
                                                            <td><input type="checkbox" id="items"  name="items" value="${"D"}${ele.uniqueId}" />${"D"}${ele.uniqueId}</td>
                                                    <input type="text"   name="<%=uniqueID1%>" value="${"D"}${ele.uniqueId}" hidden="yes" />
                                                    <input type="text"   name="<%=billdue1%>" value="${ele.billDueDate}" hidden="yes" />
                                                    <td><input type="text" id="dateofbill" name="<%=billdate1%>" value="${disburseDate}" hidden="yes" /></td>
                                                    <td><input type="text" name="<%=weekID1%>" value="${ele.weekId}" hidden="yes" />${ele.weekId}</td>
                                                    <td><input type="text" name="<%=billtype1%>" value="${ele.billType}" hidden="yes" />${ele.billType}</td>
                                                    <td>${ele.revisionNo}</td>


                                                    <td><input type="text" name="<%=corpID1%>" value="${ele.corporates.corporateId}" hidden="yes" />${ele.corporates.corporateName}
                                                        <c:forEach items="${corpdefaultList}" var="defaultcorp">
                                                            <c:if test="${defaultcorp.corporateId== ele.corporates.corporateId}">
                                                                <span style='font-size:30px;color: RED;'>&#10007;</span>
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td><input type="text" name="<%=totalamount1%>" value="${ele.toalnet}" hidden="yes" />${ele.toalnet}</td>
                                                    <td><input type="text" name="<%=balamt1%>"  value="${ele.pendingAmount}" hidden="yes" />${ele.pendingAmount}</td>
                                                    <td><input type="text" id="<%=disamount1%>"  name="<%=disamount1%>"  style="width:100px;" /></td>
                                                    <td><input type="text"  name="<%=disamt1%>"  id="<%=disamt1%>" style="width:100px;" readonly/></td>
                                                    <td><input type="text"  name="<%=remarks1%>"  id="<%=remarks1%>" style="width:150px;" /></td>




                                                    <%--<c:set var="flag1" scope="session" value="${0}"/>--%>
                                                    <c:set var="flag1"  value="${0}"/>

                                                    <c:set var="totpay"  value="${0}"/>
                                                    <c:set var="totrecv"  value="${0}"/>
                                                    <c:set var="flag2"  value="${0}"/>
                                                    <c:forEach items="${pendingPayablebilllistMap}" var="obj">
                                                        <c:if test="${obj.corporateName==ele.corporates.corporateName && obj.billtype== ele.billType}">
                                                            <c:set var="flag1" value="${1}"/>
                                                            <c:set var="totpay"  value="${totpay + obj.netAmount}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:forEach items="${pendingReceivablebilllistMap}" var="obj1">
                                                        <c:if test="${obj1.corporateName==ele.corporates.corporateName && obj1.billtype== ele.billType}">
                                                            <c:set var="flag2"  value="${1}"/>
                                                            <c:set var="totrecv" value="${totrecv + obj1.netAmount}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:if test="${flag1==0}">
                                                        <td>0</td>
                                                    </c:if>
                                                    <c:if test="${flag1==1}">
                                                        <td>${totpay}</td>
                                                    </c:if>
                                                    <!--<tr><td>${flag1}</td></tr>-->



                                                    <c:if test="${flag2==0}">
                                                        <td>0</td>
                                                    </c:if>
                                                    <c:if test="${flag2==1}">
                                                        <td>${totrecv}</td>
                                                    </c:if>
                                                        <td>${ele.billYear}</td>
                                                    </tr>




                                                </c:if>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${ele.revisionNo >0}">
                                            <c:if test="${ele.billPriority==priority}">
                                                <c:if test="${ele.pendingAmount!=0}">
                                                    <%
                                                        cnt++;
                                                        billdate1 = billdate + cnt;
                                                        weekID1 = weekID + cnt;
                                                        totalamount1 = totalamount + cnt;
                                                        disamount1 = disamount + cnt;
                                                        corpID1 = corpID + cnt;
                                                        uniqueID1 = uniqueID + cnt;
                                                        billtype1 = billtype + cnt;
                                                        balamt1 = balamt + cnt;
                                                        disamt1 = disamt + cnt;
                                                        remarks1 = remarks + cnt;
                                                        billdue1 = billdue + cnt;
                                                    %>
                                                    <tr>
                                                        <td><input type="checkbox" id="items"  name="items" value="${"D"}${ele.uniqueId}" />${"D"}${ele.uniqueId}</td>
                                                    <input type="text"   name="<%=uniqueID1%>" value="${"D"}${ele.uniqueId}" hidden="yes" />
                                                    <input type="text"   name="<%=billdue1%>" value="${ele.billDueDate}" hidden="yes" />
                                                    <td><input type="text" id="dateofbill" name="<%=billdate1%>" value="${disburseDate}" hidden="yes" /></td>
                                                    <td><input type="text" name="<%=weekID1%>" value="${ele.weekId}" hidden="yes" />${ele.weekId}</td>
                                                    <td><input type="text" name="<%=billtype1%>" value="${ele.billType}" hidden="yes" />${ele.billType}</td>
                                                    <td>${ele.revisionNo}</td>
                                                    <td><input type="text" name="<%=corpID1%>" value="${ele.corporates.corporateId}" hidden="yes" />${ele.corporates.corporateName}
                                                        <c:forEach items="${corpdefaultList}" var="defaultcorp">
                                                            <c:if test="${defaultcorp.corporateId== ele.corporates.corporateId}">
                                                                <span style='font-size:30px;color: RED;'>&#10007;</span>
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td><input type="text" name="<%=totalamount1%>" value="${ele.toalnet}" hidden="yes" />${ele.toalnet}</td>
                                                    <td><input type="text" name="<%=balamt1%>"  value="${ele.pendingAmount}" hidden="yes" />${ele.pendingAmount}</td>
                                                    <td><input type="text"  name="<%=disamount1%>" id="<%=disamount1%>" style="width:100px;" /></td>
                                                    <td><input type="text"  name="<%=disamt1%>"  id="<%=disamt1%>" style="width:100px;" readonly/></td>
                                                    <td><input type="text"  name="<%=remarks1%>"  id="<%=remarks1%>" style="width:150px;" /></td>
                                                        <c:set var="flag1"  value="${0}"/>
                                                        <c:set var="totpay" value="${0}"/>
                                                        <c:set var="totrecv" value="${0}"/>
                                                        <c:set var="flag2"  value="${0}"/>
                                                        <c:forEach items="${pendingPayablebilllistMap}" var="obj">
                                                            <c:if test="${obj.corporateName==ele.corporates.corporateName && obj.billtype== ele.billType}">
                                                                <c:set var="flag1" value="${1}"/>
                                                                <c:set var="totpay"  value="${totpay + obj.netAmount}"/>
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:forEach items="${pendingReceivablebilllistMap}" var="obj1">
                                                            <c:if test="${obj1.corporateName==ele.corporates.corporateName && obj1.billtype== ele.billType}">
                                                                <c:set var="flag2"  value="${1}"/>
                                                                <c:set var="totrecv" value="${totrecv + obj1.netAmount}"/>
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:if test="${flag1==0}">
                                                        <td>0</td>
                                                    </c:if>
                                                    <c:if test="${flag1==1}">
                                                        <td>${totpay}</td>
                                                    </c:if>




                                                    <c:if test="${flag2==0}">
                                                        <td>0</td>
                                                    </c:if>
                                                    <c:if test="${flag2==1}">
                                                        <td>${totrecv}</td>
                                                    </c:if>
                                                        <td>${ele.billYear}</td>
                                                    </tr>
                                                </c:if>
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            <!--String of Refund DIsbursenment -->


                            <%

                                if (flag != 1) {
                                    flag = flag + 1;
                            %>

                            <%
                                if (dsmflag == 0) {

                            %>
                            <c:forEach items="${listrefundpayable}" var="refundObj">
                                <fmt:formatDate value="${refundObj.billingDate}" pattern="yyyy-MM-dd" var="disburseDate" />
                                <c:if test="${disburseDate==billdate}">
                                    <c:if test="${refundObj.weekId==objectWeek}">


                                        <c:if test="${fn:substring(priority, 0, 3)==fn:substring(refundObj.billPriority, 0, 3)}">

                                            <c:if test="${fn:substring(priority, 0, 3)=='DSM'}">
                                                <%                                                   dsmflag = 1;

                                                %>
                                            </c:if>

                                            <%                                               cnt++;
                                                billdate1 = billdate + cnt;
                                                weekID1 = weekID + cnt;
                                                totalamount1 = totalamount + cnt;
                                                disamount1 = disamount + cnt;
                                                corpID1 = corpID + cnt;
                                                uniqueID1 = uniqueID + cnt;
                                                billtype1 = billtype + cnt;
                                                balamt1 = balamt + cnt;
                                                disamt1 = disamt + cnt;
                                                remarks1 = remarks + cnt;
                                                billdue1 = billdue + cnt;
                                            %>
                                            <tr>
                                                <td><input type="checkbox" id="items"  name="items" value="${"R"}${refundObj.uniqueId}" />${"R"}${refundObj.uniqueId}</td>
                                            <input type="text"   name="<%=uniqueID1%>" value="${"R"}${refundObj.uniqueId}" hidden="yes" />
                                            <input type="text"   name="<%=billdue1%>" value="${refundObj.billDueDate}" hidden="yes" />
                                            <td><input type="text" id="dateofbill" name="<%=billdate1%>" value="${disburseDate}" hidden="yes" /></td>
                                            <td><input type="text" name="<%=weekID1%>" value="${refundObj.weekId}" hidden="yes" />${refundObj.weekId}</td>
                                            <td><input type="text" name="<%=billtype1%>" value="${refundObj.billType}" hidden="yes" />${refundObj.billType}</td>
                                            <td>${refundObj.revisionNo}</td>
                                            <td><input type="text" name="<%=corpID1%>" value="${refundObj.corporates.corporateId}" hidden="yes" />${refundObj.corporates.corporateName}
                                                <c:forEach items="${corpdefaultList}" var="defaultcorp">
                                                    <c:if test="${defaultcorp.corporateId== refundObj.corporates.corporateId}">
                                                        <span style='font-size:30px;color: RED;'>&#10007;</span>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <c:set var="minus1" scope="application" value="${-1}"/>
                                            <td><input type="text" name="<%=totalamount1%>" value="${refundObj.totalnet}" hidden="yes" />${minus1*refundObj.totalnet}</td>
                                            <td><input type="text" name="<%=balamt1%>"  value="${refundObj.pendingAmount}" hidden="yes" />${refundObj.pendingAmount}</td>
                                            <td><input type="text" id="<%=disamount1%>"  name="<%=disamount1%>"  style="width:100px;" /></td>
                                            <td><input type="text"  name="<%=disamt1%>"  id="<%=disamt1%>" style="width:100px;" readonly/></td>
                                            <td><input type="text"  name="<%=remarks1%>"  id="<%=remarks1%>" style="width:150px;" /></td>


                                            <c:set var="flag1" scope="session" value="${0}"/>
                                            <c:set var="totpay" scope="session" value="${0}"/>
                                            <c:set var="totrecv" scope="session" value="${0}"/>
                                            <c:set var="flag2" scope="session" value="${0}"/>
                                            <c:forEach items="${pendingPayablebilllistMap}" var="obj">
                                                <c:if test="${obj.corporateName==refundObj.corporates.corporateName && obj.billtype== refundObj.billType}">
                                                    <c:set var="flag1" value="${1}"/>
                                                    <c:set var="totpay"  value="${totpay + obj.netAmount}"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:forEach items="${pendingReceivablebilllistMap}" var="obj1">
                                                <c:if test="${obj1.corporateName==refundObj.corporates.corporateName && obj1.billtype== refundObj.billType}">
                                                    <c:set var="flag2"  value="${1}"/>
                                                    <c:set var="totrecv" value="${totrecv + obj1.netAmount}"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${flag1==0}">
                                                <td>0</td>
                                            </c:if>
                                            <c:if test="${flag1==1}">
                                                <td>${totpay}</td>
                                            </c:if>




                                            <c:if test="${flag2==0}">
                                                <td>0</td>
                                            </c:if>
                                            <c:if test="${flag2==1}">
                                                <td>${totrecv}</td>
                                            </c:if>
                                                
                                                <td>${refundObj.billYear}</td>
                                            </tr>


                                        </c:if>


                                    </c:if>
                                </c:if>


                            </c:forEach>
                            <%}
                            %> 
                            <% }
                            %>
                        </c:forEach>
                    </c:forEach>
                </c:forEach>
                </tbody>
                <!--                
                                <tfoot>
                                        <tr>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                
                                            <th style="text-align:right">Total:</th>
                                            
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            
                                            <th></th>
                                            <th></th>
                
                
                                        </tr>
                                    </tfoot>-->
            </table>
            <br>
            <br>
            <c:set var="now" value = "<%= new java.util.Date()%>" />
            <p align="center"><input type="radio" name="doi" id="doi" value="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${now}" />" /><fmt:formatDate pattern = "yyyy-MM-dd" value = "${now}" /></p>
            <table align="center" style="width:80%;">
                <tr>
                    <td>PSDF Month</td>
                    <td>Year</td>
                    <td>Amount</td>
                    <td>Remarks</td>
                    <td>&nbsp;</td>

                </tr>
                <tr>                  
                    <td><select name="csdfmonth" id="csdfmonth" >
                            <option>Jan</option>
                            <option>Feb</option>
                            <option>Mar</option>
                            <option>Apr</option>
                            <option>May</option>
                            <option>Jun</option>
                            <option>Jul</option>
                            <option>Aug</option>
                            <option>Sep</option>
                            <option>Oct</option>
                            <option>Nov</option>
                            <option>Dec</option>
                        </select>
                    </td>                   
                    <td><select name="csdfYear" id="csdfYear" >
                            <!--                            <option>2019</option>
                                                        <option>2020</option>
                                                        <option>2021</option>                         -->
                        </select></td>
                    <td><input type="text" style="width:150px;" name="csdfamt" id="csdfamt" required /></td>
                    <td><input type="text" style="width:200px;"  name="csdfremarks" id="csdfremarks" /></td>
                    <td><input width="30" style="width:100px;"  type="submit" name="csdfSubmit" id="csdfSubmit" onclick="return validatecsdf();"/></td>
                </tr>
            </table>


            <p align="center" hidden="yes" ><input type="text" id="rowcount" name="rowcount" class="num" size="6" value="<%=cnt%>" /></p>
            <br/>
            <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>
        </form>
            
        <form name="form101" id="form101" action="newDisbursementsras.htm" method="post" >
            <input type="hidden" value="showAlllll" id="showAll" name="showAll">
        </form>
            
    </body>



</html>