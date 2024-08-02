<%--
   Document   : viewBillRefundPayableDetails
   Created on : Nov 6, 2019, 12:47:52 PM
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
            var sumBillAmt = 0;
            var sumcreditAmt = parseFloat(0);
            var settleBnkAmt = parseFloat(0);
            var creditAmt = 0;
            var updatedCreditAmt = 0;
            $(document).ready(function () {


                $("#refundresetID").click(function () {
                    $('.case3').prop('checked', false);
                    $('.case4').prop('checked', false);
                    $('#RefundPayment').find('tr').each(function () {
                        var row = $(this);
                        $(this).find("td:eq(5) input").val(0);
                        $(this).find("td:eq(6) input").val(0);
                    });
                    $('#RefundAccount').find('tr').each(function () {
                        var row = $(this);
                        $(this).find("td:eq(7) input").val(0);
                        $(this).find("td:eq(8) input").val(0);
                    });
                    return false;
                });//end of reset




                $("input.case3").click(function () {
                    if ($(this).prop("checked") == true) {
                        le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;
                        if (le1 > parseInt(1)) {
                            $('.case3').prop('checked', false);
                            $('.case4').prop('checked', false);
                            alert("You cann't selected more than one entry from Bank Transaction !!");


                            $('#RefundPayment').find('tr').each(function () {
                                var row = $(this);
                                $(this).find("td:eq(5) input").val(0);
                                $(this).find("td:eq(6) input").val(0);
                            });
                            $('#RefundAccount').find('tr').each(function () {
                                var row = $(this);
                                $(this).find("td:eq(8) input").val(0);
                            });
                        }
                    }
                    else if ($(this).prop("checked") == false) {
                        //calculateCase1();
                        le1 = document.querySelectorAll('input[name="refbankstmt"]:checked').length;
                        alert("Checkbox is unchecked.");
                        if (le1 < parseInt(1)) {
                            $('.case3').prop('checked', false);
                            $('.case4').prop('checked', false);
                            alert("You haven't selected any entry from Bank Transaction !!");
                            $('#RefundPayment').find('tr').each(function () {
                                var row = $(this);
                                $(this).find("td:eq(5) input").val(0);
                                $(this).find("td:eq(6) input").val(0);
                            });
                            $('#RefundAccount').find('tr').each(function () {
                                var row = $(this);
                                $(this).find("td:eq(8) input").val(0);
                            });
                        }
                    }
                });// end of case3




                $("input.case4").click(function () {


                    if ($(this).prop("checked") == true) {
                        calculateRefundCase();
                    }
                    else if ($(this).prop("checked") == false) {
                        alert("Checkbox is unchecked.");
                        calculateRefundCase();
                    }


                });// end of case4




            });




            function calculateRefundCase() {
                sumBillAmt = 0;
                updatedCreditAmt = parseFloat(creditAmt);


                $('#RefundPayment').find('tr').each(function () {
                    var row = $(this);
                    if (row.find('input[type="checkbox"]').is(':checked')) {
                        var stmtId = parseInt($(this).find("td:eq(1)").html());
                        creditAmt = $(this).find("td:eq(4)").html();
                        //alert(creditAmt);
                    }
                    else
                    {
                        $(this).find("td:eq(5) input").val(0);
                        $(this).find("td:eq(6) input").val(0);
                    }
                });






                $('#RefundAccount').find('tr').each(function () {
                    var row = $(this);
                    if (row.find('input[type="checkbox"]').is(':checked')) {
                        var billAmt = $(this).find("td:eq(7)").html();
                        sumBillAmt = parseFloat(sumBillAmt) + parseFloat(billAmt);
                        // alert(sumBillAmt);
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
                            $(this).find("td:eq(8) input").val(billAmt);


                        }
                        if ((le1 === parseInt(1)) && (sumBillAmt > creditAmt)) {
                            $(this).find("td:eq(7) input").val(0);
                            $(this).find("td:eq(8) input").val(0);
                            $('.case3').prop('checked', false);
                            $('.case4').prop('checked', false);
                            alert("Full settlement with this bank entry is not possible!!");
                        }


                    }
                    else
                    {
                        $(this).find("td:eq(8) input").val(0);
                    }
                });
                if (parseFloat(creditAmt) >= parseFloat(sumBillAmt)) {
                    remainBal = parseFloat(creditAmt) - parseFloat(sumBillAmt);
                    creditAmt = (+updatedCreditAmt);
                    var settleBnkAmt = parseFloat(sumBillAmt);
                    $('#RefundPayment').find('tr').each(function () {
                        var row = $(this);
                        if (row.find('input[type="checkbox"]').is(':checked')) {
                            $(this).find("td:eq(5) input").val(settleBnkAmt);
                            $(this).find("td:eq(6) input").val(remainBal);
                        }
                    });
                }


            }///end of finctuion calculateRefundcase






            function validate() {
                le1 = document.querySelectorAll('input[name="bankstmt"]:checked').length;
                le = document.querySelectorAll('input[name="uniqueNo"]:checked').length;
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
            function validate1() {
                return false;
            }


            function validate2() {
                return true;
            }




            function validate3() {
                return false;
            }
        </script>
    </head>
    <body style="width:90%;min-height: 900px;" >
        <form>
            <p style="text-align:center;"><b><input type="text" name="Corporate" value="${corpName}" hidden/>
                    <input type="text" name="corpid" value="${CorpID}" hidden/></b></p>


            <table align="center" class="roundedCorners"  style="min-height: 400px;">
                <thead style="height:30px;">
                    <tr>
                        <th colspan="9">Refund Verification of ${corpName} </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <table id="RefundAccount" align="center" style="min-height:400px;width:81%;" class="roundedCorners">
                                <thead style="background-color: #0677A1;color: white;height:30px;">
                                    <tr>
                                        <th colspan="8">Refund </th>
                                    </tr>
                                    <%
                                        int recvcnt = 0;
                                        String recvuniqueid = "recvuniqueid";
                                        String recvuniqueid1 = null;
                                        String recvrefundid = "recvrefundid";
                                        String recvrefundid1;
                                        String refweekid = "refweekid";
                                        String refweekid1 = null;
                                    %>
                                    <tr>
                                        <th>&nbsp;</th>
                                        <th>Unique ID</th>
                                        <th>Week ID</th>
                                        <th>Bill Type</th>
                                        <th>Revised Date</th>
                                        <th>Net Revised Amount</th>
                                        <th>Disbursed Amount</th>
                                        <th>Refund  Amount</th>
                                        <th>Mapped  Amount</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <c:forEach items="${refundList}" var="ref">
                                        <%                                         recvcnt++;
                                            recvuniqueid1 = recvuniqueid + recvcnt;
                                            recvrefundid1 = recvrefundid + recvcnt;
                                            refweekid1 = refweekid + recvcnt;
                                        %>
                                        <tr>
                                            <td><input type="checkbox" class="case4" name="refuniqueNo" id="refuniqueNo" value="${ref.uniqueId}" size="1" />${ref.uniqueId}</td>
                                            <td><input type="text" name="<%=recvuniqueid1%>" id="<%=recvuniqueid1%>" value="${ref.uniqueId}" hidden="yes"/>
                                                ${ref.uniqueId}</td>
                                            <td><input type="text" name="<%=refweekid1%>"  id="<%=refweekid1%>" value="${ref.weekId}" hidden="yes" />${ref.weekId}</td>
                                            <td>${ref.billType}</td>
                                            <td>${ref.billingDate}</td>
                                            <td>${ref.toalnet}</td>
                                            <td>${ref.disburseAmount}</td>
                                            <td>${ref.revisedrefund}</td>
                                            <td><input type="text" name="<%=recvrefundid1%>" id="<%=recvrefundid1%>" size="8" value="0"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                <input type="text" name="refrowcount" value="<%=recvcnt%>" hidden="yes"/>
                            </table>
                        </td>
                        <td>


                            <table  id="RefundPayment" class="roundedCorners">
                                <thead>
                                    <tr>
                                        <th colspan="9">Refund Payment</th>
                                    </tr>
                                    <tr>
                                        <th> Select </th>
                                        <th style="display:none;">Statement Id</th>
                                        <th>Credit Date </th>
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


                                    <c:forEach items="${bankList}" var="ele">
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




                                </tbody>
                                <input type="text" name="refundbankrowcount" value="<%=refcnt%>" hidden="yes"/>
                            </table>
                        </td>
                    </tr>
            </table>
            <p align="center">
                <input type="submit" name="refundconfirm"  id="SubmitId" value="Refund Confirm"  onclick="return validate2();" />
                <input type="submit" id="refundresetID" value="Refund Reset" onclick="return validate3();"  /></p>
            <br/> <br/> <br/> <br/> <br/>
            <br/>
            <br/>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        </form>
        <br/>
        <br/>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <br/>
    </body>

</html>