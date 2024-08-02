<%-- 
    Document   : viewAllCorpDetailsForAdjustments
    Created on : Jan 5, 2021, 11:50:51 AM
    Author     : shubham
--%>

<%-- 
    Document   : viewAllCorpDetailsForAdjustments
    Created on : Jan 5, 2021, 11:50:51 AM
    Author     : shubham
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
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

                $('input[type="checkbox"]').click(function () {



//            true checkbox
                    if ($(this).prop("checked") == true) {
                        var totalpay = 0;
                        var totalrec = 0;
                        var netvalue = 0;
                        var adjustvalue = 0;
                        $('#mytable').find('tr').each(function () {
                            var row = $(this);
                            if (row.find('input[type="checkbox"]').is(':checked')) {
                                var payamt = $(this).find("td:eq(5) input").val();
                                totalpay = (+totalpay) + (+payamt);
                                totalpay = parseFloat(totalpay).toFixed(2);

                            }
                        });
                        $('#mytablerec').find('tr').each(function () {
                            var row = $(this);
                            if (row.find('input[type="checkbox"]').is(':checked')) {
                                var recamt = $(this).find("td:eq(5) input").val();
                                totalrec = (+totalrec) + (+recamt);
                                totalrec = parseFloat(totalrec).toFixed(2);

                            }
                        });
//                        alert(totalrec);
//                        alert(totalpay);
                        if ((+totalrec) > (+totalpay)) {
//                            alert('rec > pay');
                            $('#mytable').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);


                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            var balrec = totalpay;
                            $('#mytablerec').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    var recrowvalue = $(this).find("td:eq(5) input").val();
                                    balrec = (+balrec) - (+recrowvalue);
//                                    alert(balrec)
                                    if (balrec < 0) {
                                        balrec = (-1) * (+balrec);
                                        $(this).find("td:eq(6) input").val(balrec);
                                        balrec = 0;
                                    }
                                    else {
                                        $(this).find("td:eq(6) input").val(0);
                                    }



                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            adjustvalue = (+totalpay);
                            netvalue = (+totalrec) - (+totalpay);

                        }
                        if ((+totalrec) < (+totalpay)) {
//                            alert('pay > rec');
                            $('#mytablerec').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);


                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            var balpay = totalrec;
                            $('#mytable').find('tr').each(function () {
                                var row = $(this);

                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    var payrowvalue = $(this).find("td:eq(5) input").val();
                                    balpay = (+balpay) - (+payrowvalue);

                                    //alert('balpay=' + balpay);
                                    if (balpay < 0) {
                                        balpay = (-1) * (+balpay);
                                        $(this).find("td:eq(6) input").val(balpay);
                                        balpay = 0;
                                    }
                                    else {
                                        $(this).find("td:eq(6) input").val(0);
                                    }



                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            adjustvalue = (+totalrec);
                            netvalue = (+totalpay) - (+totalrec);
                        }
                        if ((+totalrec) == (+totalpay)) {
                            $('#mytablerec').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);


                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });
                            $('#mytable').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);
                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });
                            adjustvalue = (+totalpay);
                            netvalue = (+totalpay) - (+totalrec);
                        }
                        netvalue = parseFloat(netvalue).toFixed(2);
                        adjustvalue = parseFloat(adjustvalue).toFixed(2);
                        $("#payable").val(totalpay);
                        $("#receivable").val(totalrec);
                        $("#net").val(netvalue);
                        $("#adjust").val(adjustvalue);

                    }


                    //            false checkbox
                    if ($(this).prop("checked") == false) {
                        var totalpay = 0;
                        var totalrec = 0;
                        var netvalue = 0;
                        var adjustvalue = 0;
                        $('#mytable').find('tr').each(function () {
                            var row = $(this);
                            if (row.find('input[type="checkbox"]').is(':checked')) {
                                var payamt = $(this).find("td:eq(5) input").val();
                                totalpay = (+totalpay) + (+payamt);
                                totalpay = parseFloat(totalpay).toFixed(2);

                            }
                        });
                        $('#mytablerec').find('tr').each(function () {
                            var row = $(this);
                            if (row.find('input[type="checkbox"]').is(':checked')) {
                                var recamt = $(this).find("td:eq(5) input").val();
                                totalrec = (+totalrec) + (+recamt);
                                totalrec = parseFloat(totalrec).toFixed(2);

                            }
                        });
//                        alert(totalrec);
//                        alert(totalpay);
                        if ((+totalrec) > (+totalpay)) {
//                            alert('rec > pay');
                            $('#mytable').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);


                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            var balrec = totalpay;
                            $('#mytablerec').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    var recrowvalue = $(this).find("td:eq(5) input").val();
                                    balrec = (+balrec) - (+recrowvalue);
//                                    alert(balrec)
                                    if (balrec < 0) {
                                        balrec = (-1) * (+balrec);
                                        $(this).find("td:eq(6) input").val(balrec);
                                        balrec = 0;
                                    }
                                    else {
                                        $(this).find("td:eq(6) input").val(0);
                                    }



                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            adjustvalue = (+totalpay);
                            netvalue = (+totalrec) - (+totalpay);

                        }
                        if ((+totalrec) < (+totalpay)) {
//                            alert('pay > rec');
                            $('#mytablerec').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);


                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            var balpay = totalrec;
                            $('#mytable').find('tr').each(function () {
                                var row = $(this);

                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    var payrowvalue = $(this).find("td:eq(5) input").val();
                                    balpay = (+balpay) - (+payrowvalue);

                                    //alert('balpay=' + balpay);
                                    if (balpay < 0) {
                                        balpay = (-1) * (+balpay);
                                        $(this).find("td:eq(6) input").val(balpay);
                                        balpay = 0;
                                    }
                                    else {
                                        $(this).find("td:eq(6) input").val(0);
                                    }



                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });


                            adjustvalue = (+totalrec);
                            netvalue = (+totalpay) - (+totalrec);
                        }
                        if ((+totalrec) == (+totalpay)) {
                            $('#mytablerec').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);


                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });
                            $('#mytable').find('tr').each(function () {
                                var row = $(this);
                                if (row.find('input[type="checkbox"]').is(':checked')) {
                                    $(this).find("td:eq(6) input").val(0);
                                }
                                else {
                                    var pend = $(this).find("td:eq(5) input").val();
                                    $(this).find("td:eq(6) input").val(pend);
                                }
                            });
                            adjustvalue = (+totalpay);
                            netvalue = (+totalpay) - (+totalrec);
                        }
                        netvalue = parseFloat(netvalue).toFixed(2);
                        adjustvalue = parseFloat(adjustvalue).toFixed(2);
                        $("#payable").val(totalpay);
                        $("#receivable").val(totalrec);
                        $("#net").val(netvalue);
                        $("#adjust").val(adjustvalue);
                    }


                });
            });


        </script>
        <script>

//            function calculate(val) {
//                var tot = parseFloat(document.getElementById("payable").value);
//                var sum = tot + parseFloat(val);
//                document.getElementById("payable").value = sum;
//                nettotal();
//            }
//
//            function calculate222(val2) {
//                var tot2 = parseFloat(document.getElementById("receivable").value);
//                var sum2 = tot2 + parseFloat(val2);
//                document.getElementById("receivable").value = sum2;
//                nettotal();
//            }
//
//            function nettotal() {
//                var pay = parseFloat(document.getElementById("payable").value);
//                var rec = parseFloat(document.getElementById("receivable").value);
//
//                if (pay > rec) {
//                    var netvalue = pay - rec;
//                    document.getElementById("net").value = netvalue.toFixed(2);
//                    ;
//                }
//                else {
//                    var netvalue = rec - pay;
//                    document.getElementById("net").value = netvalue.toFixed(2);
//                    ;
//
//
//
//                }
//            }

            function cleartotal() {
                document.getElementById("payable").value = 0.0;
                document.getElementById("receivable").value = 0.0;
            }

            function besure() {


                var rowflag = 0;
                var rowflag1 = 0;
                $('#mytable').find('tr').each(function () {
                    var row = $(this);
                    if (row.find('input[type="checkbox"]').is(':checked')) {
                        rowflag++;
                    }
                });
                $('#mytablerec').find('tr').each(function () {
                    var row = $(this);
                    if (row.find('input[type="checkbox"]').is(':checked')) {

                        rowflag1++;
                    }
                });

                if ((+rowflag) == 0)
                {
                    alert("KIndly select atleast one payable transaction!!");
                    return false;
                }
                if ((+rowflag1) == 0)
                {
                    alert("KIndly select atleast one receivable transaction!!");
                    return false;
                }
                var choice = confirm("Are you sure want to make this adjustment ??");

                if (choice == false)
                    return false
                else
                    return true;
            }
        </script>
    </head>
    <body>
        <h1 align="center" style="color:black;"><u>All Pending Amounts of ${corpname}</u></h1>

        <form method="post" name="form1" >

            <input type="text"   name="corpname" value="${corpname}" hidden="yes" />
            <div align="center">

                <table border="0">
                    <tr>
                        <td style="vertical-align: top;">
                            <table id="mytable" border="3" bordercolor="chocolate" cellpadding="7" cellspacing="0">
                                <caption><h2>Payable side</h2></caption>
                                <thead style="background-color: #CC9752;color: white;height:30px;">
                                    <tr align="center">
                                        <th>Select</th>
                                        <th>Week Id</th>
                                        <th>Bill Type</th>
                                        <th>Revision No</th>
                                        <th>Bill Year</th>
                                        <th>Pending Amount</th>
                                        <th>Pending After Adjust</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        int cnt = 0;
                                        String balamt = "balamt";
                                        String balamt1;
                                        String balamtaft = "balamtaft";
                                        String balamtaft1;
                                        String uniqueID = "uniqueID";
                                        String uniqueID1;

                                    %>
                                    <c:forEach items="${allpend1pay}" var="sd">
                                        <%                                            cnt++;
                                            balamt1 = balamt + cnt;
                                            uniqueID1 = uniqueID + cnt;
                                            balamtaft1 = balamtaft + cnt;
                                        %>
                                        <tr align="center">
                                            <td><input type="checkbox" id="items"  name="items" value="${"B"}${sd.uniqueId}"  style="width: 25px; height: 25px;" onclick="calculate(${sd.pendingAmount})">${"B"}${sd.uniqueId}</td>
                                    <input type="text"   name="<%=uniqueID1%>" value="${"B"}${sd.uniqueId}" hidden="yes" />

                                    <td>${sd.weekId}</td>
                                    <td>${sd.billType}</td>
                                    <td>${sd.revisionNo}</td>
                                    <td>${sd.billYear}</td>
                                    <td><input type="text" name="<%=balamtaft1%>"  value="${sd.pendingAmount}" hidden="yes" />${sd.pendingAmount}</td>
                                    <td><input type="text" id="<%=balamt1%>"  name="<%=balamt1%>"  style="width:100px;" readonly/></td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${allpend2pay}" var="sd">
                        <%

                            cnt++;
                            balamt1 = balamt + cnt;
                            uniqueID1 = uniqueID + cnt;
                            balamtaft1 = balamtaft + cnt;
                        %>
                        <tr align="center">
                            <td><input type="checkbox" id="items"  name="items" value="${"R"}${sd.uniqueId}" style="width: 25px; height: 25px;" onclick="calculate(${sd.pendingAmount})">${"R"}${sd.uniqueId}</td>
                        <input type="text"   name="<%=uniqueID1%>" value="${"R"}${sd.uniqueId}" hidden="yes" />

                        <td>${sd.weekId}</td>
                        <td>${sd.billType}</td>
                        <td>${sd.revisionNo}</td>
                        <td>${sd.billYear}</td>
                        <td><input type="text" name="<%=balamtaft1%>"  value="${sd.pendingAmount}" hidden="yes" />${sd.pendingAmount}</td>
                        <td><input type="text" id="<%=balamt1%>"  name="<%=balamt1%>"  style="width:100px;" readonly/></td>

                        </tr>
                    </c:forEach>
                    <c:forEach items="${intpay}" var="sd">
                        <%
                            cnt++;
                            balamt1 = balamt + cnt;
                            uniqueID1 = uniqueID + cnt;
                            balamtaft1 = balamtaft + cnt;
                        %>
                        <tr align="center">
                            <td><input type="checkbox" id="items"  name="items" value="${"I"}${sd.interestId}" style="width: 25px; height: 25px;" onclick="calculate(${sd.interestPendingamount})">${"I"}${sd.interestId}</td>
                        <input type="text"   name="<%=uniqueID1%>" value="${"I"}${sd.interestId}" hidden="yes" />

                        <td>${sd.weekId}</td>
                        <td>${sd.billType}</td>
                        <td>${sd.revisionNo}</td>
                        <td>${sd.billYear}</td>
                        <td><input type="text" name="<%=balamtaft1%>"   value="${sd.interestPendingamount}" hidden="yes" />${sd.interestPendingamount}</td>
                        <td><input type="text" id="<%=balamt1%>"  name="<%=balamt1%>"  style="width:100px;" readonly/></td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </td>

                <td><h1 style="width:100px;"><hr style="height: 450px;; width: 3px; background-color: black;"></h1></td>

                <td style="vertical-align: top;">
                    <table id="mytablerec" border="3" bordercolor="chocolate" cellpadding="7" cellspacing="0">
                        <caption><h2>Receivable side</h2></caption>
                        <thead style="background-color: #CC9752;color: white;height:30px;">
                            <tr align="center">
                                <th>Select</th>
                                <th>Week Id</th>
                                <th>Bill Type</th>
                                <th>Revision No</th>
                                <th>Bill Year</th>
                                <th>Pending Amount</th>
                                <th>Pending After Adjust</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int cntrec = 0;
                                String balamtrec = "balamtrec";
                                String balamtrec1;
                                String balamtrecaft = "balamtrecaft";
                                String balamtrecaft1;
                                String uniquerecID = "uniquerecID";
                                String uniquerecID1;

                            %>
                            <c:forEach items="${allpend1rec}" var="sd">
                                <%                                    cntrec++;
                                    balamtrec1 = balamtrec + cntrec;
                                    uniquerecID1 = uniquerecID + cntrec;
                                    balamtrecaft1 = balamtrecaft + cntrec;
                                %>
                                <tr align="center">
                                    <td><input type="checkbox" id="itemsrec"  name="itemsrec" value="${"R"}${sd.uniqueId}" style="width: 25px; height: 25px;" onclick="calculate222(${sd.pendingAmount})">${"R"}${sd.uniqueId}</td>
                            <input type="text"   name="<%=uniquerecID1%>" value="${"R"}${sd.uniqueId}" hidden="yes" />

                            <td>${sd.weekId}</td>
                            <td>${sd.billType}</td>
                            <td>${sd.revisionNo}</td>
                            <td>${sd.billYear}</td>
                            <td><input type="text" name="<%=balamtrecaft1%>"  value="${sd.pendingAmount}" hidden="yes" />${sd.pendingAmount}</td>
                            <td><input type="text" id="<%=balamtrec1%>"  name="<%=balamtrec1%>"  style="width:100px;" readonly/></td>

                            </tr>
                        </c:forEach>
                        <c:forEach items="${allpend2rec}" var="sd">
                            <%
                                cntrec++;
                                balamtrec1 = balamtrec + cntrec;
                                uniquerecID1 = uniquerecID + cntrec;
                                 balamtrecaft1 = balamtrecaft + cntrec;
                            %>
                            <tr align="center">
                                <td><input type="checkbox"  id="itemsrec"  name="itemsrec" value="${"B"}${sd.uniqueId}" style="width: 25px; height: 25px;" onclick="calculate222(${sd.pendingAmount})">${"B"}${sd.uniqueId}</td>
                            <input type="text"   name="<%=uniquerecID1%>" value="${"B"}${sd.uniqueId}" hidden="yes" />

                            <td>${sd.weekId}</td>
                            <td>${sd.billType}</td>
                            <td>${sd.revisionNo}</td>
                            <td>${sd.billYear}</td>
                            <td><input type="text"  name="<%=balamtrecaft1%>"   value="${sd.pendingAmount}" hidden="yes" />${sd.pendingAmount}</td>
                            <td><input type="text" id="<%=balamtrec1%>"  name="<%=balamtrec1%>"  style="width:100px;" readonly/></td>

                            </tr>
                        </c:forEach>
                        <c:forEach items="${intrec}" var="sd">
                            <%
                                cntrec++;
                                balamtrec1 = balamtrec + cntrec;
                                uniquerecID1 = uniquerecID + cntrec;
                                balamtrecaft1 = balamtrecaft + cntrec;
                            %>
                            <tr align="center">
                                <td><input type="checkbox" id="itemsrec"  name="itemsrec" value="${"I"}${sd.interestId}" style="width: 25px; height: 25px;" onclick="calculate222(${sd.interestPendingamount})">${"I"}${sd.interestId}</td>
                            <input type="text"   name="<%=uniquerecID1%>" value="${"I"}${sd.interestId}" hidden="yes" />

                            <td>${sd.weekId}</td>
                            <td>${sd.billType}</td>
                            <td>${sd.revisionNo}</td>
                            <td>${sd.billYear}</td>
                            <td><input type="text"  name="<%=balamtrecaft1%>"  value="${sd.interestPendingamount}" hidden="yes" />${sd.interestPendingamount}</td>
                            <td><input type="text" id="<%=balamtrec1%>"  name="<%=balamtrec1%>"  style="width:100px;" readonly/></td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
                </tr>
                </table>
                <br>
                <hr>
                <br>

                <b style="color:black;">Total Payable is :</b> &nbsp;&nbsp;&nbsp;<input type="text" name="payable" id="payable" value="0"><br><br>
                <b style="color:black;">Total Receivable is :</b> <input type="text" name="receivable"  id="receivable" value="0"><br><br>
                <b style="color:black;">Adjust Amount is :</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="adjust" id="adjust" value="0" style="color: red"><br><br>

                <b style="color:black;">Net Amount is :</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="net" id="net" value="0" style="color: red"><br><br>
                <b style="color:black;">Remarks :      </b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="remarks" name="remarks" maxlength="500" placeholder="max. 500 characters" required><br><br>

                <input type="reset" value="Clear All" onclick="cleartotal()">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="submit" name="badj" value="Submit"  onclick="return besure()">

            </div>

            <p align="center" hidden="yes" ><input type="text" id="rowcount" name="rowcount" class="num" size="6" value="<%=cnt%>" /></p>
            <p align="center" hidden="yes" ><input type="text" id="rowcountrec" name="rowcountrec" class="num" size="6" value="<%=cntrec%>" /></p>

            <br>
            <br>

        </form>

        <br><br>
        <br><br>
        <br>
    </body>
</html>
