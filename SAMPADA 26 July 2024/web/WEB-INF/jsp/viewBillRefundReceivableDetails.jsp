<%--

    Document   : viewBillRefundReceivableDetails

    Created on : Nov 7, 2019, 5:24:04 PM

    Author     : JaganMohan

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

        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script|Lobster|Rock+Salt|Satisfy&display=swap" rel="stylesheet">

        <style>

            table, td, th {

                border: 1px solid #ddd;

                text-align: left;

            }

            table {

                border-collapse: collapse;

                width: 100%;

            }

            th{

                font-family:'Kaushan Script', cursive;

                font-size: 16px;

            }

            td {

                font-size: 16px;

            }

            th, td {

                padding: 5px;

                width: 152px;

            }

            input{

                width:50px;

            }

            #corpId{

                width:180px;

            }

        </style>

        <style>

            table.roundedCorners {

                border: 2px solid DarkOrange;

                border-radius: 13px;

                border-spacing: 0;

            }

            table.roundedCorners th {

                border: 2px solid  #964000;

                padding: 10px;

                background-color: #964000;

                color:white;

            }

            table.roundedCorners td

            {

                border: 2px solid DarkOrange;

                padding: 10px;

                background-color: #FBF2EA;

            }

            thead,tr,td {

                color:#003366;

            }

            p {

                color:black;

            }

            input[type=submit] {

                padding: 10px 14px;

                border: 2px solid #f2f2f2;

                font-weight:bold;

                color:#ffffff;

                alignment-adjust:central;

                background-color:#964000;

            }

            input[type=submit]:hover, input[type=submit]:focus {

                background-color: #151b54;

            }

            table.dataTable thead span.sort-icon {

                display: inline-block;

                padding-left: 5px;

                width: 16px;

                height: 16px;

                color:wheat;

            }

            td.highlight {

                background-color: whitesmoke !important;

            }

            table.dataTable thead .sorting span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_both.png') no-repeat center right; }

            table.dataTable thead .sorting_asc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc.png') no-repeat center right; }

            table.dataTable thead .sorting_desc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc.png') no-repeat center right; }

            table.dataTable thead .sorting_asc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc_disabled.png') no-repeat center right; }

            table.dataTable thead .sorting_desc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc_disabled.png') no-repeat center right; }



        </style>

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





                $('input[type="checkbox"]').click(function () {



                    if ($(this).prop("checked") == true) {

                        var customerId = 0;

//                        alert('recvamtjagadsafsdfv');

                        $('#mytable').find('tr').each(function () {

                            var row = $(this);

                            if (row.find('input[type="checkbox"]').is(':checked')) {



                                var recvamt = $(this).find("td:eq(6) input").val();

                                alert(recvamt);

                                var availamt = $('#availamt').val();

                                if ((+availamt) < (+recvamt))

                                {

                                    alert('No Sufficient Amount ');

                                    row.find('input[type="checkbox"]').prop('checked', false);

                                    return false;

                                }

                                customerId = (+customerId) + (+recvamt);

                                $(this).find("td:eq(7) input").val(recvamt);



                            }

                            var mainbal = $('#mainbal').val();

                            mainbal = (+mainbal) - (+customerId);

                            $("#availamt").val(mainbal);

                        });

                    }//if

                    else if ($(this).prop("checked") == false) {

                        $('#bname').attr("disabled", true);

                        alert("Checkbox is unchecked. Please click on Calcualte .....");

                    }

                });





            });


//
//            function validate()
//            {
//                var x = document.getElementById("items");
////                var x = $('input:checkbox[name=items]:checked').val();
//                alert(x);
//                var y;
//                var table = document.getElementById("mytable");
//                for (var i = 0, row; row = table.rows[i]; i++) {
//                    //iterate through rows
//                    //rows would be accessed using the "row" variable assigned in the for loop
//                    for (var j = 0, col; col = row.cells[j]; j++) {
//                        //iterate through columns
//                        //columns would be accessed using the "col" variable assigned in the for loop
//                        alert(row.cells[0].value);
//                    }
//                }
//
//                alert(x);
//
//                if (x == "")
//
//                {
//
//                    alert('Please selection one item !!!!!!!!');
//
//                    return false;
//
//                }
//
//
//
//                if (confirm('Are you sure to submit'))
//
//                {
//
//                    return true;
//
//                }
//
//                else
//
//                {
//
//                    return false;
//
//                }
//
//
//                return true;
//            }

        </script>


        <script type="text/javascript">

            function validate() {
                //Create an Array.
                var selected = new Array();

                //Reference the Table.
                var tblFruits = document.getElementById("mytable");

                //Reference all the CheckBoxes in Table.
                items

                var chks = tblFruits.getElementsByTagName("INPUT")


                // Loop and push the checked CheckBox value in Array.
                for (var i = 0; i < chks.length; i++) {
                    if (chks[i].checked) {
                        selected.push(chks[i].value);
                    }
                }

                //Display the selected CheckBox values.
                if (selected.length > 0) {
                    alert("Selected values: " + selected.join(","));

                }
                else {
                    alert("Kindly select !!");
                    return false;
                }
                return true;
            }

        </script>






    </head>

    <body width="95%">

        <form name="billPayCorpPendingInfoForm" action="submitCorporateRefundReceivableDetails.htm">





            <h3 align="center" style="color:#003366;" >Pool Account Details</h3>

            <table align="center" id="disburseTable" style="min-height:400px;width:80%;" class="roundedCorners">

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

            <p align="center">Available Balance<input style="width:200px;" type="text" name="availamt" id="availamt" value="${salary}" readonly/>



            <table id="mytable" align="center" style="min-height:400px;width:80%;" class="roundedCorners">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Select</th>

                        <th>Billing Date</th>

                        <th>Week</th>

                        <th>Type</th>

                        <th>Pool Member</th>

                        <th>Total Amount</th>

                        <th>Refund Amount</th>

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


                    %>

                    <c:forEach items="${refundDetails}" var="ele">

                        <tr>

                            <%                                cnt++;

                                uniqueID1 = uniqueID + cnt;

                                weekID1 = weekID + cnt;

                                balamt1 = balamt + cnt;

                                corpID1 = corpID + cnt;

                            %>

                            <td><input type="checkbox" id="items"  name="items" value="${ele.uniqueId}" />${ele.uniqueId}</td>

                    <input type="text"   name="<%=uniqueID1%>" value="${ele.uniqueId}" hidden="yes" />

                    <td><input type="text" id="dateofbill" name="<%=billdate1%>" value="${ele.billingDate}" hidden="yes" />${ele.billingDate}</td>

                    <td><input type="text" name="<%=weekID1%>" value="${ele.weekId}" hidden="yes" />${ele.weekId}</td>

                    <td><input type="text" name="<%=billtype1%>" value="${ele.billType}" hidden="yes" />${ele.billType}</td>

                    <td><input type="text" name="<%=corpID1%>" value="${ele.corporates.corporateId}" hidden="yes" />${ele.corporates.corporateName}



                    </td>

                    <td><input type="text" name="<%=totalamount1%>" value="${ele.totalnet}" hidden="yes" />${ele.totalnet}</td>

                    <td><input type="text" name="<%=balamt1%>"  value="${ele.revisedrefund}" hidden="yes" />${ele.revisedrefund}</td>



                    </tr>

                </c:forEach>

                </tbody>

            </table>

            <p align="center" hidden="yes" ><input type="text" id="rowcount" name="rowcount" class="num" size="6" value="<%=cnt%>" /></p>

            <br/>

            <p align="center"><input type="submit" style="width:100px;" class="btn" onclick="return validate();" id="bname" name="bname" value="Submit" /></p>

            <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>

        </form>

    </body>

</html>

