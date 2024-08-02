<%--

   Document   : viewInterestMappingPayableDetails

   Created on : Nov 20, 2019, 10:26:33 AM

   Author     : JaganMohan



--%>











<%@page contentType="text/html" pageEncoding="UTF-8"%>







<!DOCTYPE html>







<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>







<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>







<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>







<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>







<html>

    <head>

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

        <style>

            table.roundedCorners {

                border: 1px solid #96CC39;

                border-radius: 13px;

                border-spacing: 0;

            }

            table.roundedCorners td,

            table.roundedCorners th {

                border: 1px solid #96CC39;

                padding: 5px;

            }

            thead,tr,td,input {

                color:#003366;

                background-color: white;

            }

            input{

                border: 0;

                font-size: 15px;

            }

            p {

                color:black;

            }

            th{

                height:72px;

            }

            input[type=submit] {

                padding: 10px 14px;

                border: 1px solid #f2f2f2;

                font-weight:bold;

                color:#ffffff;

                alignment-adjust:central;

                background-color:#96CC39;

            }

            input[type=submit]:hover, input[type=submit]:focus {

                background-color: #151b54;

            }

        </style>

        <script type="text/javascript">

            var le = 0;

            var le1 = 0;

            var sumBillAmt = parseFloat(0);

            var sumcreditAmt = parseFloat(0);

            var settleBnkAmt = parseFloat(0);

            var creditAmt = 0;

            var updatedCreditAmt = 0;

            $(document).ready(function () {



                $("#interestresetID").click(function () {

                    $('.case3').prop('checked', false);

                    $('.case4').prop('checked', false);

                    $('#RefundPayment').find('tr').each(function () {

                        var row = $(this);

                        $(this).find("td:eq(5) input").val(0);

                        $(this).find("td:eq(6) input").val(0);

                    });

                    $('#RefundAccount').find('tr').each(function () {

                        var row = $(this);

                        $(this).find("td:eq(11) input").val(0);

                        $(this).find("td:eq(12) input").val(0);

                    });

                    return false;

                });//end of reset



                $("input.case3").click(function () {

                    if ($(this).prop("checked") == true) {

                        calculateCase3();

                    }

                    else if ($(this).prop("checked") == false) {

                        calculateCase3();

                        le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;

                        alert("Checkbox is unchecked.");

                        if (le1 < parseInt(1)) {

                            $('.case3').prop('checked', false);

                            $('.case4').prop('checked', false);

                            alert("You haven't selected any entry from Bank Transaction !!");

                        }

                    }

                });



                $("input.case4").click(function () {

                    if ($(this).prop("checked") == true) {

                        calculateCase4();

                    }

                    else if ($(this).prop("checked") == false) {

                        alert("Checkbox is unchecked.");

                        calculateCase4();

                    }

                });







                //               $("input.case3").click(function () {

                //                   if ($(this).prop("checked") == true) {

                //                       le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;

                //

                //                       if (le1 > parseInt(1)) {

                //                           $('.case3').prop('checked', false);

                //                           $('.case4').prop('checked', false);

                //                           alert("You cann't selected more than one entry from Bank Transaction !!");

                //

                //                           $('#RefundPayment').find('tr').each(function () {

                //                               var row = $(this);

                //                               $(this).find("td:eq(5) input").val(0);

                //                               $(this).find("td:eq(6) input").val(0);

                //                           });

                //                           $('#RefundAccount').find('tr').each(function () {

                //                               var row = $(this);

                //                               $(this).find("td:eq(11) input").val(0);

                //                               $(this).find("td:eq(12) input").val(0);

                //                           });

                //                       }

                //

                //

                //                        $('#RefundPayment').find('tr').each(function () {

                //                            var row = $(this);

                //                            if (row.find('input[type="checkbox"]').is(':checked')) {

                //                                var stmtId = parseInt($(this).find("td:eq(1)").html());

                //                                creditAmt = $(this).find("td:eq(4)").html();

                //                                alert(creditAmt);

                //                            }

                //                            else

                //                            {

                //                                $(this).find("td:eq(5) input").val(0);

                //                                $(this).find("td:eq(6) input").val(0);

                //                            }

                //                        });

                //                   }

                //                   else if ($(this).prop("checked") == false) {

                //                       //calculateCase1();

                //                       le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;

                //                       alert("Checkbox is unchecked.");

                //                       if (le1 < parseInt(1)) {

                //                           $('.case3').prop('checked', false);

                //                           $('.case4').prop('checked', false);

                //                           alert("You haven't selected any entry from Bank Transaction !!");

                //                           $('#RefundPayment').find('tr').each(function () {

                //                               var row = $(this);

                //                               $(this).find("td:eq(5) input").val(0);

                //                               $(this).find("td:eq(6) input").val(0);

                //                           });

                //                           $('#RefundAccount').find('tr').each(function () {

                //                               var row = $(this);

                //                               $(this).find("td:eq(11) input").val(0);

                //                               $(this).find("td:eq(12) input").val(0);

                //                           });

                //                       }

                //                   }

                //               });// end of case3





                //               $("input.case4").click(function () {

                //

                //                   if ($(this).prop("checked") == true) {

                //                       calculateRefundCase();

                //                   }

                //                   else if ($(this).prop("checked") == false) {

                //                       alert("Checkbox is unchecked.");

                //                       calculateRefundCase();

                //                   }

                //               });// end of case4



            });





            function calculateCase3() {

                le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;

                if (le1 > parseInt(1)) {

                    alert('Cannot selected the second transaction');

                    $(this).attr("checked", false);

                    $('.case3').prop('checked', false);

                    $('.case4').prop('checked', false);

                    $('#RefundPayment').find('tr').each(function () {

                        var row = $(this);

                        $(this).find("td:eq(5) input").val(0);

                        $(this).find("td:eq(6) input").val(0);

                    });

                    $('#RefundAccount').find('tr').each(function () {

                        var row = $(this);



                        $(this).find("td:eq(11) input").val(0);

                        $(this).find("td:eq(12) input").val(0);



                    });

                    return false;

                }



                $('#RefundPayment').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        var stmtId = parseInt($(this).find("td:eq(1)").html());

                        creditAmt = $(this).find("td:eq(4)").html();

//                        alert(creditAmt);

                    }

                    else

                    {

                        $(this).find("td:eq(5) input").val(0);

                        $(this).find("td:eq(6) input").val(0);

                    }

                });
                
                
                 $('#RefundPayment').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        var creditdate = $(this).find("td:eq(2)").html();
                        var dateflag = 0;
//                        alert(creditdate);
                        $('#RefundPayment').find('tr').each(function () {

                            var creditdate1 = $(this).find("td:eq(2)").html();
                            if (creditdate1 < creditdate) {
                                dateflag = 1;
//                                alert("Bank Statements with Former Credit Dates are available");
                            }

                        });
                        if (dateflag == 1) {
                            alert("Bank Statements with Former Credit Dates are available");
                        }

                    }


                });

                $('#RefundAccount').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        $(this).find("td:eq(11) input").val(0);

                        $(this).find("td:eq(12) input").val(0);

                    }

                });

            }// end finction calcutecase3



            function calculateCase4() {

                sumBillAmt = 0;

                updatedCreditAmt = parseFloat(creditAmt);

                $('#RefundAccount').find('tr').each(function () {

                    var row = $(this);

                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        var billAmt = $(this).find("td:eq(10)").html();

                        sumBillAmt = parseFloat(sumBillAmt) + parseFloat(billAmt);

                        le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;

                        if (le1 > parseInt(1)) {

                            alert("Kindly select only one entry from Bank Transaction !!");

                            $('.case3').prop('checked', false);

                            $('.case4').prop('checked', false);

                        }

                        if (le1 < parseInt(1)) {

                            alert("Hello!! You haven't selected any entry from Bank Transaction !!");

                            $('.case3').prop('checked', false);

                            $('.case4').prop('checked', false);

                        }

                        if ((le1 === parseInt(1)) && (sumBillAmt <= creditAmt)) {

                            updatedCreditAmt = updatedCreditAmt - billAmt;

                            $(this).find("td:eq(11) input").val(billAmt);

                            $(this).find("td:eq(12) input").val(0);

                        }

                        if ((le1 === parseInt(1)) && (sumBillAmt > creditAmt)) {

                            if (updatedCreditAmt >= billAmt) {

                                updatedCreditAmt = updatedCreditAmt - billAmt;

                                $(this).find("td:eq(11) input").val(billAmt);

                                $(this).find("td:eq(12) input").val(0);

                            }

                            else if ((updatedCreditAmt > 0) && (updatedCreditAmt < billAmt)) {

                                var pendingAmt = billAmt - updatedCreditAmt;

                                $(this).find("td:eq(11) input").val(updatedCreditAmt);

                                $(this).find("td:eq(12) input").val(pendingAmt);

                                updatedCreditAmt = updatedCreditAmt - billAmt;

                            }

                            else {

                                $(this).find("td:eq(11) input").val(0);

                                $(this).find("td:eq(12) input").val(0);

                                alert("further settlement with this bank entry is not possible!!");

                            }

                        }

                    }

                    else

                    {

                        $(this).find("td:eq(11) input").val(0);

                        $(this).find("td:eq(12) input").val(0);

                    }

                });

                if (parseFloat(creditAmt) >= parseFloat(sumBillAmt)) {

                    remainBal = parseFloat(creditAmt) - parseFloat(sumBillAmt);

                    var settleBnkAmt = parseFloat(sumBillAmt);

                    $('#RefundPayment').find('tr').each(function () {

                        var row = $(this);

                        if (row.find('input[type="checkbox"]').is(':checked')) {

                            $(this).find("td:eq(5) input").val(settleBnkAmt);

                            $(this).find("td:eq(6) input").val(remainBal);

                        }

                    });

                }

                else {

                    alert("Full bill settlement is not feasible");

                    $('#RefundPayment').find('tr').each(function () {

                        var row = $(this);

                        if (row.find('input[type="checkbox"]').is(':checked')) {

                            $(this).find("td:eq(5) input").val(creditAmt);

                            $(this).find("td:eq(6) input").val(0);

                        }

                    });

                }

            }///end of finctuion calculatecase4







            //           function calculateRefundCase() {

            //               sumBillAmt = 0;

            //               updatedCreditAmt = parseFloat(creditAmt);

            //               alert("updatedCreditAmt"+updatedCreditAmt);

            //

            //            $('#RefundAccount').find('tr').each(function () {

            //                   var row = $(this);

            //                   if (row.find('input[type="checkbox"]').is(':checked')) {

            //                       var billAmt = $(this).find("td:eq(10)").html();

            //                       sumBillAmt = parseFloat(sumBillAmt) + parseFloat(billAmt);

            //                       alert(sumBillAmt);

            //                       le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;

            //                       if (le1 > parseInt(1)) {

            //                           alert("Kindly select only one entry from Bank Transaction !!");

            //                           $('.case3').prop('checked', false);

            //                           $('.case4').prop('checked', false);

            //

            //                       }

            //                       if (le1 < parseInt(1)) {

            //                           alert("Hello!! You haven't selected any entry from Bank Transaction !!");

            //                           $('.case3').prop('checked', false);

            //                           $('.case4').prop('checked', false);

            //

            //                       }

            //                       if ((le1 === parseInt(1)) && (sumBillAmt <= creditAmt)) {

            //                           updatedCreditAmt = updatedCreditAmt - billAmt;

            //                           $(this).find("td:eq(11) input").val(billAmt);

            //                           $(this).find("td:eq(12) input").val(0);

            //

            //                       }

            //                       if ((le1 === parseInt(1)) && (sumBillAmt > creditAmt)) {

            //                           var pend=(+billAmt)-(+creditAmt);

            //                            $(this).find("td:eq(11) input").val(creditAmt);

            //                            $(this).find("td:eq(12) input").val(pend);

            //                            alert("Full settlement with this bank entry is not possible!!");

            //                            return false;

            //                       }

            //

            //                   }

            //                   else

            //                   {

            //                       $(this).find("td:eq(11) input").val(0);

            //                       $(this).find("td:eq(12) input").val(0);

            //

            //                   }

            //               });

            //               if (parseFloat(creditAmt) >= parseFloat(sumBillAmt)) {

            //                   remainBal = parseFloat(creditAmt) - parseFloat(sumBillAmt);

            //                   creditAmt = (+updatedCreditAmt);

            //                   var settleBnkAmt = parseFloat(sumBillAmt);

            //                   $('#RefundPayment').find('tr').each(function () {

            //                       var row = $(this);

            //                       if (row.find('input[type="checkbox"]').is(':checked')) {

            //                           $(this).find("td:eq(5) input").val(settleBnkAmt);

            //                           $(this).find("td:eq(6) input").val(remainBal);

            //                       }

            //                   });

            //               }

            //               else {

            //                    alert("Full bill settlement is not feasible");

            //                    $('#RefundPayment').find('tr').each(function () {

            //                        var row = $(this);

            //                        if (row.find('input[type="checkbox"]').is(':checked')) {

            //                            $(this).find("td:eq(5) input").val(creditAmt);

            //                            $(this).find("td:eq(6) input").val(0);

            //                        }

            //                    });

            //                }

            //           }///end of finctuion calculateRefundcase









            function validate2() {

                le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;

                le = document.querySelectorAll('input[name="refuniqueNo"]:checked').length;

                if (le1 == 0) {

                    alert(" Kindly select atleast one payment entry from Payment Section!!");

                    return false;

                }

                if (le == 0) {

                    alert(" Kindly select atleast one bill entry from Account Section!!");

                    return false;

                }

                if (confirm('Are you sure to continue ...'))

                {

                    return true;

                }

                else

                {

                    return false;

                }

                return true;

            }
//            function validate2() {
//
//                return true;
//
//            }





            function validate3() {

                return false;

            }

        </script>

    </head>

    <body style="width:90%;">

        <form>

            <p style="text-align:center;"><b><input type="text" name="Corporate" value="${corpName}" hidden/>

                    <input type="text" name="corpid" value="${CorpID}" hidden/></b></p>





            <h3 align="center" style="color:#003366;">Bill & Payment Mapping for ${corpName}</h3>

            <table align="center" class="roundedCorners"  style="min-height: 400px;">

                <thead style="height:30px;">

                    <tr>

                        <th colspan="7">Maker-Checker Pending Verification</th>

                    </tr>

                </thead>

                <tbody>

                    <tr>

                        <td>

                            <table id="makerTable" align="center" style="min-height:400px;width:81%;" class="roundedCorners">

                                <thead style="background-color: #0677A1;color: white;height:30px;">

                                    <tr>

                                        <th colspan="8">Mapped Bill Entries</th>

                                    </tr>

                                    <tr>

                                        <th>Bill ID</th>

                                        <th>Type</th>

                                        <th>Category</th>

                                        <th>Week</th>

                                        <th>Bill Due Date </th>

                                        <th>Interest Billed Amount</th>

                                        <th>To be Verify Amount</th>

                                        <th>Pending Amount</th>

                                    </tr>

                                </thead>

                                <tbody>

                                    <c:forEach items="${listinterest}" var="ele">

                                        <tr>

                                            <td>${ele.interestDetails.interestId}</td>

                                            <td>${ele.interestDetails.billType}</td>

                                            <td>${ele.interestDetails.billCategory}</td>

                                            <td>${ele.interestDetails.weekId}</td>

                                            <td>${ele.interestDetails.billingDuedate}</td>

                                            <td>${ele.interestDetails.interestBilledamount}</td>

                                            <td>${ele.interestDetails.interestPaidamount}</td>

                                            <td>${ele.interestDetails.interestPendingamount}</td>

                                        </tr>

                                    </c:forEach>

                                </tbody>

                            </table>

                        </td>

                        <td>

                            <table align="center" style="min-height:400px;width:81%;" class="roundedCorners">

                                <thead style="background-color: #0677A1;color: white;height:30px;">

                                    <tr>

                                        <th colspan="6">Mapped Bank Entries</th>

                                    </tr>

                                    <tr>

                                        <th>Entry ID</th>

                                        <th>Credit Date</th>

                                        <th>Opening Amount</th>

                                        <th >Credit Amount</th>

                                        <th >To be Settle  Amount</th>

                                        <th>Entry Balance</th>

                                    </tr>

                                </thead>

                                <tbody>

                                    <c:forEach items="${listtempmapbankstmt}" var="ele">

                                        <tr>

                                            <td>${ele.bankStatement.stmtId}</td>

                                            <td>${ele.bankStatement.amountDate}</td>

                                            <td>${ele.bankStatement.openBalance}</td>

                                            <td >${ele.bankStatement.paidAmount}</td>

                                            <td >${ele.mappedAmount}</td>

                                            <td>${ele.transactionBalance}</td>

                                        </tr>

                                    </c:forEach>

                                </tbody>

                            </table>

                        </td>

                    </tr>

                </tbody>

            </table>

            <br/>







            <table align="center" class="roundedCorners"  style="min-height: 400px;">

                <thead style="height:30px;">

                    <tr>

                        <th colspan="9">Interest Verification of ${corpName} </th>

                    </tr>

                </thead>

                <tbody>

                    <tr>

                        <td>

                            <table id="RefundAccount" align="center" style="min-height:400px;width:81%;" class="roundedCorners">

                                <thead style="background-color: #0677A1;color: white;height:30px;">

                                    <tr>

                                        <th colspan="13">Interest </th>

                                    </tr>

                                    <%

                                        int recvcnt = 0;

                                        String recvuniqueid = "recvuniqueid";

                                        String recvuniqueid1 = null;

                                        String recvrefundid = "recvrefundid";

                                        String recvrefundid1;

                                        String refweekid = "refweekid";

                                        String refweekid1 = null;

                                        String mappedamt = "mappedamt";

                                        String mappedamt1;

                                    %>

                                    <tr>

                                        <th>&nbsp;</th>

                                        <th>Week ID</th>

                                        <th>Bill Type</th>

                                        <th>Published Date</th>

                                        <th>Billed Due Date</th>

                                        <th>Total Amount</th>

                                        <th>Paid Date</th>

                                        <th>Interest Billed  Amount</th>

                                        <th>No of Days</th>

                                        <th>Interest  Amount</th>

                                        <th>Interest  Pending Amount</th>

                                        <th>Mapped  Amount</th>

                                        <th>Pending  Amount</th>

                                    </tr>

                                </thead>

                                <tbody>





                                    <c:forEach items="${interestPayableList}" var="ref">

                                        <%                                         recvcnt++;

                                            recvuniqueid1 = recvuniqueid + recvcnt;

                                            recvrefundid1 = recvrefundid + recvcnt;

                                            refweekid1 = refweekid + recvcnt;

                                            mappedamt1 = mappedamt + recvcnt;

                                        %>

                                        <tr>

                                            <td><input type="checkbox" class="case4" name="refuniqueNo" id="refuniqueNo" value="${ref.interestId}" size="1" />${ref.interestId}</td>

                                    <input type="text" name="<%=recvuniqueid1%>" id="<%=recvuniqueid1%>" value="${ref.interestId}" hidden="yes"/>



                                    <td><input type="text" name="<%=refweekid1%>"  id="<%=refweekid1%>" value="${ref.weekId}" hidden="yes" />${ref.weekId}</td>

                                    <td>${ref.billType}</td>

                                    <td>${ref.entryDate}</td>

                                    <td>${ref.billingDuedate}</td>

                                    <td>${ref.billedAmount}</td>

                                    <td>${ref.paidDate}</td>

                                    <td>${ref.interestBilledamount}</td>

                                    <td>${ref.noofdays}</td>

                                    <td>${ref.interestAmount}</td>

                                    <td>${ref.interestPendingamount}</td>

                                    <td><input type="text" name="<%=mappedamt1%>" id="<%=mappedamt1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=recvrefundid1%>" id="<%=recvrefundid1%>" size="8" value="0" readonly/></td>

                        </tr>

                    </c:forEach>

                </tbody>

                <input type="text" name="interestrowcount" value="<%=recvcnt%>" hidden="yes"/>

            </table>

        </td>

        <td>









            <table  id="RefundPayment" class="roundedCorners">

                <thead>

                    <tr>

                        <th colspan="9">Interest Bank Payment</th>

                    </tr>

                    <tr>

                        <th> Select </th>

                        <th style="display:none;">Statement Id</th>

                        <th> Credit Date </th>

                        <th> Initial Balance </th>

                        <th> Current Balance</th>

                        <th> Settle Amount </th>

                        <th> Remaining Balance </th>

                        <th> Remarks </th>

                </thead>

                <tbody>

                    <%

                        int refcnt = 0;

                        String refbankstmt = "refbankstmt";

                        String refbankstmt1;

                        String refremainBal = "refremainBal";

                        String refremainBal1 = null;

                        String refsettleAmtBnk = "refsettleAmtBnk";

                        String refsettleAmtBnk1 = null;

                        String refremarks = "refremarks";

                        String refremarks1 = null;

                        String reftotalamt = "reftotalamt";

                        String reftotalamt1 = null;

                    %>





                    <c:choose>

                        <c:when test = "${empty bankList}">

                            <c:forEach items="${bnkstmtList}" var="ele">

                                <tr>

                                    <%  refcnt++;

                                        refremainBal1 = refremainBal + refcnt;

                                        refsettleAmtBnk1 = refsettleAmtBnk + refcnt;

                                        refremarks1 = refremarks + refcnt;

                                        refbankstmt1 = refbankstmt + refcnt;

                                        reftotalamt1 = reftotalamt + refcnt;

                                    %>

                                    <td><input type="checkbox" class="case3" name="refbankstmt" value="${ele.stmtId}" size="1" />${ele.stmtId}</td>

                                    <td style="display:none;"><input type="text"  name="<%=refbankstmt1%>" value="${ele.stmtId}" hidden="yes" /></td>

                                    <td>${ele.amountDate}</td>

                                    <td><input type="text"  name="<%=reftotalamt1%>" value="${ele.paidAmount}" hidden="yes" />${ele.paidAmount}</td>

                                    <td>${ele.mappedBalance}</td>

                                    <td><input type="text" name="<%=refsettleAmtBnk1%>" id="<%=refsettleAmtBnk1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=refremainBal1%>" id="<%=refremainBal1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=refremarks1%>" id="<%=refremarks1%>" size="8" value="Remarks" />

                                </tr>

                            </c:forEach>

                        </c:when>

                        <c:otherwise>

                            <c:forEach items="${bnkstmtList}" var="ele">

                                <c:forEach items="${bankList}" var="ele123">

                                    <c:if test="${ele123.transactionBalance > 0 && ele123.bankStatement.stmtId==ele.stmtId}">

                                        <tr>

                                            <%  refcnt++;

                                                refremainBal1 = refremainBal + refcnt;

                                                refsettleAmtBnk1 = refsettleAmtBnk + refcnt;

                                                refremarks1 = refremarks + refcnt;

                                                refbankstmt1 = refbankstmt + refcnt;

                                                reftotalamt1 = reftotalamt + refcnt;

                                            %>

                                            <td><input type="checkbox" class="case3" name="refbankstmt" value="${ele.stmtId}" size="1" />${ele.stmtId}</td>

                                            <td style="display:none;"><input type="text"  name="<%=refbankstmt1%>" value="${ele.stmtId}" hidden="yes" /></td>

                                            <td>${ele.amountDate}</td>

                                            <td><input type="text"  name="<%=reftotalamt1%>" value="${ele.paidAmount}" hidden="yes" />${ele.paidAmount}</td>

                                            <td>${ele123.transactionBalance}</td>

                                            <td><input type="text" name="<%=refsettleAmtBnk1%>" id="<%=refsettleAmtBnk1%>" size="8" value="0" readonly/></td>

                                            <td><input type="text" name="<%=refremainBal1%>" id="<%=refremainBal1%>" size="8" value="0" readonly/></td>

                                            <td><input type="text" name="<%=refremarks1%>" id="<%=refremarks1%>" size="8" value="Remarks" />

                                        </tr>

                                    </c:if>

                                </c:forEach>

                            </c:forEach>

                            <c:forEach items="${tempBankStmtlist}" var="ele">

                                <tr>

                                    <%  refcnt++;

                                        refremainBal1 = refremainBal + refcnt;

                                        refsettleAmtBnk1 = refsettleAmtBnk + refcnt;

                                        refremarks1 = refremarks + refcnt;

                                        refbankstmt1 = refbankstmt + refcnt;

                                        reftotalamt1 = reftotalamt + refcnt;

                                    %>

                                    <td><input type="checkbox" class="case3" name="refbankstmt" value="${ele.stmtId}" size="1" />${ele.stmtId}</td>

                                    <td style="display:none;"><input type="text"  name="<%=refbankstmt1%>" value="${ele.stmtId}" hidden="yes" /></td>

                                    <td>${ele.amountDate}</td>

                                    <td><input type="text"  name="<%=reftotalamt1%>" value="${ele.paidAmount}" hidden="yes" />${ele.paidAmount}</td>

                                    <td>${ele.mappedBalance}</td>

                                    <td><input type="text" name="<%=refsettleAmtBnk1%>" id="<%=refsettleAmtBnk1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=refremainBal1%>" id="<%=refremainBal1%>" size="8" value="0" readonly/></td>

                                    <td><input type="text" name="<%=refremarks1%>" id="<%=refremarks1%>" size="8" value="Remarks" />

                                </tr>

                            </c:forEach>

                        </c:otherwise>

                    </c:choose>
                </tbody>

                <input type="text" name="interestbankrowcount" value="<%=refcnt%>" hidden="yes"/>

            </table>

        </td>

    </tr>

</table>

<p align="center">

    <input type="submit" name="interestconfirm"  id="SubmitId" value="Confirm"  onclick="return validate2();" />

    <input type="submit" id="interestresetID" value="Reset" onclick="return validate3();"  /></p>

<br/> <br/> <br/> <br/> <br/>

</form>

</body>







</html>