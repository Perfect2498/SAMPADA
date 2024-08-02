<%--
 Document   : viewBillInterestReceivableDetails
 Created on : Nov 21, 2019, 3:51:38 PM
 Author     : cdac







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
            var basePath = '${pageContext.request.contextPath}';
        </script>

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
                        if (mainbal == 0)
                        {
                            //$('#bname').attr("disabled", true);
                            alert('Please Check the Pool Account Balance???');
                        }


                        $('#csdfamt').val(0);


                    }
                });//end of radio selection


                $('input[type="checkbox"]').click(function () {
                    if ($(this).prop("checked") == true) {
                        var customerId = 0;
                        //alert('recvamtjagadsafsdfv');
                        $('#mytable').find('tr').each(function () {
                            var row = $(this);
                            if (row.find('input[type="checkbox"]').is(':checked')) {
                                var recvamt = $(this).find("td:eq(12) input").val();
                                var interestpoolbal = $('#interestpoolbal').val();
                                //alert(recvamt);
                                if ((+interestpoolbal) < (+recvamt))
                                {
                                    alert('No Sufficient Amount ');
                                    gettotal();
                                    return false;
                                }
                                customerId = (+customerId) + (+recvamt);
                                $(this).find("td:eq(13) input").val(recvamt);
                            }
                            var maininterestpoolbal = $('#maininterestpoolbal').val();
                            maininterestpoolbal = (+maininterestpoolbal) - (+customerId);
                            $("#interestpoolbal").val(maininterestpoolbal);




                        });
                    }//if
                    else if ($(this).prop("checked") == false) {
                        $('#bname').attr("disabled", true);
//                        gettotal();
                        alert("Checkbox is unchecked. Please click on Calcualte .....");
                    }


                    $("#csdfamt").val(0);
                });




                function gettotal()
                {
                    disbursetotal = 0;
                    $('#mytable').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked'))
                        {
                            var recvamt = $(this).find("td:eq(12) input").val();
                            disbursetotal = (+disbursetotal) + (+recvamt);
                        }
                    });//end of total diursement amt for checked values
                    var customerId = 0;
                    $('#mytable').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked')) {


                            // $('#bname').attr("disabled", true);
                            var recvamt = $(this).find("td:eq(12) input").val();
                            var maininterestpoolbal = $('#maininterestpoolbal').val();
                            if (disbursetotal > maininterestpoolbal)
                            {
                                var prorata = ((+recvamt) * (+maininterestpoolbal)) / (+disbursetotal);
                                prorata = parseFloat(prorata);
                                $(this).find("td:eq(13) input").val(prorata.toFixed(2));
                                var baln = (+recvamt) - (+prorata.toFixed(2));
                                baln = parseFloat(baln);
                                $(this).find("td:eq(14) input").val(baln.toFixed(2));
                            }
                            else
                            {
                                $(this).find("td:eq(13) input").val(recvamt);
                            }
                        }
                        else
                        {
                            $(this).find("td:eq(13) input").val(0);
                            $(this).find("td:eq(14) input").val(0);
                        }
                    });
                    $("#interestpoolbal").val(0);
                }//end of total



                $('#Calculate').click(function () {
                    $('#bname').attr("disabled", false);
                    var customerId = 0;
                    var maininterestpoolbal = $('#maininterestpoolbal').val();
                    $("#csdfamt").val(0);
                    $('#mytable').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked')) {




                            var recvamt = $(this).find("td:eq(12) input").val();///chnaged
                            var disbamt = $(this).find("td:eq(13) input").val();///chnaged
                            var pendamt = (+recvamt) - (+disbamt);
                            var pendamt = parseFloat(pendamt);
                            $(this).find("td:eq(14) input").val(pendamt.toFixed(2));
                            var totaldisburse = (+disbamt) + (+pendamt);

                            if (totaldisburse > recvamt)
                            {
                                alert('Disbursed amount is greater than actual Balance');
                                $(this).find("td:eq(13) input").val(0);
                                $(this).find("td:eq(14) input").val(0);
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

                                customerId = (+customerId) + (+disbamt);
                                customerId = (+customerId.toFixed(2));
//                                 alert(customerId);
                            }
                        }
                        else
                        {
                            $(this).find("td:eq(13) input").val(0);
                            $(this).find("td:eq(14) input").val(0);
                        }
                        var maininterestpoolbal = $('#maininterestpoolbal').val();


                        if (customerId > maininterestpoolbal)
                        {
                            $("#interestpoolbal").val(0);
                            $(this).find("td:eq(13) input").val(0);
                            $(this).find("td:eq(14) input").val(0);
                            alert('Total Disbursed amount  is greater than actual Balance');
                            $('#bname').attr("disabled", true);
                            return false;
                        }
//                        var maininterestpoolbal = $('#maininterestpoolbal').val();
//                        if (customerId > maininterestpoolbal)
//                        {
//                            $("#interestpoolbal").val(0);
//                            $(this).find("td:eq(13) input").val(0);
//                            $(this).find("td:eq(14) input").val(0);
//                            alert('Total Disbursed amount is greater than actual Balance');
//                            $('#bname').attr("disabled", true);
//                            return false;
//                        }

                        maininterestpoolbal = (+maininterestpoolbal) - (+customerId);
                        $("#interestpoolbal").val(maininterestpoolbal);
                    });
                });//end of calculate






            });
            function validate()
            {
                var y = document.getElementById("items").value;
                //alert(x);
                if (y == "")
                {
                    alert('Please selection one item !!!!!!!!');
                    return false;
                }
                var x = document.getElementById("billedDate").value;
//                alert(x);
                if (x == "")
                {
                    alert('Please selection one Billing Date !!!!!!!!');
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
                                var remarks = $(this).find("td:eq(15) input").val();
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
                        if (confirm('Are you sure to submit'))
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


            function validatecsdf()
            {
                var maininterestpoolbal = document.getElementById("maininterestpoolbal").value;
                var csdfamt = document.getElementById("csdfamt").value;
                var billedDate = document.getElementById("billedDate").value;
              
                if (billedDate == "")
                {
                    alert('Please select the Date');
                    return false;
                }
                if (csdfamt == "")
                {
                    alert('Please provide the PSDF Interest Amount');
                    return false;
                }
                // alert(mainbal);
                // alert(csdfamt);
                var pend = (+maininterestpoolbal) - (+csdfamt);
                // alert('Penjddfg'+pend);
                if (csdfamt != "0")
                {


                    if (pend <= 0)
                    {
                        alert('Please check the value greater than Interest Main Balance');
                        return false;
                    }
                }
                else
                {
                    alert('Please provide the PSDF Interest Amount');
                    return false;
                }


                if (confirm('Are you want to continue?'))
                {
                    return true;
                }
                else
                {
                    return false;
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
    <body  onload="full()" width="95%">
        <form name="billPayCorpPendingInfoForm" method="post" action="submitInterestReceivableDetails.htm">




            <h3 align="center" style="color:#003366;" > Pool Account Details - Interest Disbursement</h3>
            <table align="center" id="disburseTable" style="width:80%;" class="customerTable">
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
            <p align="center">Main Interest Pool Balance<input style="width:200px;" type="text" name="maininterestpoolbal" id="maininterestpoolbal" value="${interestpoolbal}" readonly/>
                Interest Pool Available Balance<input style="width:200px;" type="text" name="interestpoolbal" id="interestpoolbal" value="${interestpoolbal}" readonly/></p>
            <p align="center"> <button id="Calculate"  style="width:100px;height:45px;background-color: DarkOrange;" type="button" >Calculate</button>
                Date of Issuance selected : <input style="width:150px;" type="text" name="billedDate" id="billedDate" readonly/>
                <input type="submit" style="width:100px;" class="btn" onclick="return validate();" id="bname" name="bsubmit" value="Submit" /></p>






            <table id="mytable" align="center" style="min-height:400px;width:100%;" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Select</th>
                        <th>Billing Date</th>
                        <th>Week</th>
                        <th>Type</th>
                        <th>Pool Member</th>
                        <th>Billing Date</th>
                        <th>Billing Due Date</th>
                        <th>Disbursed Date</th>
                        <th>Interest Billed amount</th>
                        <th>No of Days</th>
                        <th>Total Bill Amount</th>
                        <th>Interest Amount</th>
                        <th>Interest Pending</th>
                        <th>Mapped Amount</th>
                        <th>Pending Amount</th>
                        <th>Remarks</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int cnt = 0;
                        String uniqueID = "uniqueID";
                        String uniqueID1 = null;
                        String weekID = "weekID";
                        String weekID1 = null;
                        String totalamount = "totalamount";
                        String totalamount1 = null;
                        String disamount = "disamount";
                        String disamount1 = null;
                        String corpID = "corpID";
                        String corpID1 = null;
                        String billdate = "billdate";
                        String billdate1 = null;
                        String billtype = "billtype";
                        String billtype1 = null;
                        String balamt = "balamt";
                        String balamt1 = null;
                        String mapped = "mapped";
                        String mapped1 = null;
                        String pending = "pending";
                        String pending1 = null;
                        String remarks = "remarks";
                        String remarks1 = null;

                    %>
                    <c:forEach items="${InterestDisDates}" var="objectList">
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
                            <td>&nbsp;</td>
                        </tr>
                        <c:forEach items="${refundDetails}" var="ele">


                            <fmt:formatDate value="${ele.billingDate}" pattern="yyyy-MM-dd" var="disburseDate" />
                            <c:if test="${disburseDate==billdate}">
                                <tr>
                                    <%                                   cnt++;
                                        billdate1 = billdate + cnt;
                                        uniqueID1 = uniqueID + cnt;
                                        weekID1 = weekID + cnt;
                                        balamt1 = balamt + cnt;
                                        corpID1 = corpID + cnt;
                                        mapped1 = mapped + cnt;
                                        pending1 = pending + cnt;
                                        remarks1 = remarks + cnt;

                                    %>
                                    <td><input type="checkbox" id="items"  name="items" value="${ele.interestId}" />${ele.interestId}</td>
                            <input type="text"   name="<%=uniqueID1%>" value="${ele.interestId}" hidden="yes" />
                            <td><input type="text" id="dateofbill" name="<%=billdate1%>" value="${ele.billingDate}" hidden="yes" />&nbsp;</td>
                            <td><input type="text" name="<%=weekID1%>" value="${ele.weekId}" hidden="yes" />${ele.weekId}</td>
                            <td><input type="text" name="<%=billtype1%>" value="${ele.billType}" hidden="yes" />${ele.billType}</td>
                            <td><input type="text" name="<%=corpID1%>" value="${ele.corporates.corporateId}" hidden="yes" />${ele.corporates.corporateName}</td>
                            <td><input type="text" id="dateofbill" name="<%=billdate1%>" value="${disburseDate}" hidden="yes" />${ele.billingDate}</td>


                            <td>${ele.billingDuedate}</td>
                            <td>${ele.disbursedDate}</td>
                            <td>${ele.interestBilledamount}</td>
                            <td>${ele.noofdays}</td>
                            <td><input type="text" name="<%=totalamount1%>" value="${ele.billedAmount}" hidden="yes" />${ele.billedAmount}</td>
                            <td><input type="text" value="${ele.interestAmount}" hidden="yes" />${ele.interestAmount}</td>




                            <td><input type="text" name="<%=balamt1%>"  value="${ele.interestPendingamount}" hidden="yes" />${ele.interestPendingamount}</td>
                            <td><input type="text" name="<%=mapped1%>" value="0"  /></td>
                            <td><input type="text" name="<%=pending1%>"  value="0" readonly /></td>

                            <td><input type="text"  name="<%=remarks1%>"  id="<%=remarks1%>" style="width:150px;" /></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
                </tbody>
            </table>
            <p align="center" hidden="yes" ><input type="text" id="rowcount" name="rowcount" class="num" size="6" value="<%=cnt%>" /></p>
            <br>
        </form>
        <form name="interestdisbusePendingInfoForm" method="post" action="submitPSDFInterestReceivableDetails.htm">


            <input style="width:200px;" type="text" name="maininterestpoolbal" id="maininterestpoolbal" value="${interestpoolbal}" hidden="yes"/>
            <input style="width:200px;" type="text" name="biltype" id="biltype" value="${type}" hidden="yes"/>


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
                            <option>2021</option>
                            <option>2022</option>
                            <option>2023</option>
                            <option>2024</option>
                            <option>2025</option>
                            <option>2026</option>-->
                        </select></td>
                    <td><input type="text"  name="csdfamt" id="csdfamt" required /></td>
                    <td><input type="text"  name="csdfremarks" id="csdfremarks" /></td>
                    <td><input type="submit" name="csdfSubmit" id="csdfSubmit" onclick="return validatecsdf();"/>submit PSDF</td>
                </tr>
            </table>
        </form>
        <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>


    </body>







</html>