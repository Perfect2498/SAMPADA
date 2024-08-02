<%-- 
    Document   : unmappedpayment
    Created on : 17 Mar, 2020, 11:12:34 AM
    Author     : Kaustubh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unmapped Table List</title> 

        <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" >
        <script src="<c:url value="js/jquery-1.9.1.js" />"></script>
        <script src="<c:url value="js/jquery-ui.js" />" ></script>
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
        <link type="text/css" href="<c:url value="/jquery.ui.datepicker.monthyearpicker.css"/>" rel="stylesheet" />
        <script type="text/javascript" src="<c:url value="/jquery.ui.datepicker.monthyearpicker.js"/>"></script>
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>
        <script>
            function remove(amount, stmtid, corpid)
            {
                var x = document.getElementById(stmtid.toString()).value;
                if (x == "null")
                {
                    alert("Please select a Reference.");
                    return;
                }

                var r = confirm("Are you sure want to Return/Refund this amount ?");
                if (r == false)
                    return;

                document.getElementById('hiddentext').value = amount;
                document.getElementById('hiddenid').value = stmtid;
                document.getElementById('hiddencorpid').value = corpid;

                document.getElementById('form1').submit();
            }
            function setdset(val)
            {
                document.getElementById('hiddendset').value = val;
            }
        </script>


        <script>
            $(document).ready(function () {
                var table = $('#unmappedTable').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'pdf',
                            footer: true,
                            title: 'Unmapped Table List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Unmapped Table List';
                            }
                        },
                        {
                            extend: 'excel',
                            footer: true,
                            title: 'Unmapped Table List',
                            orientation: 'landscape',
                            messageTop: function () {
                                return 'Unmapped Table List';
                            }
                        }

                    ],

                });
            });
        </script>
        <style>
            .detail {
                font: 700 20px Aparajita ,Kaushan Script,Lato, sans-serif;
                line-height: 1.8;
                color: black;
                width:100%;
            }


            .btn {              
                background-color: #603311;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                width: 150px;
                opacity: 0.9;
                font-family:  Kaushan Script ,sans-serif;
            }

            #returnbutton {
                background-color: #ff6633;
                color: white;
                font-weight: bold;
                padding: 10px 20px;
                margin: 4px 2px;
                cursor: pointer;
            }

            #returnbutton:hover {
                background-color: #ff0000;
                color: white;
                font-weight: bold;
                padding: 10px 20px;
                margin: 4px 2px;
                cursor: pointer;
            }  

            .myButton {
                -moz-box-shadow:inset 0px 1px 0px 0px #00BFFF;
                -webkit-box-shadow:inset 0px 1px 0px 0px #00BFFF;
                box-shadow:inset 0px 1px 0px 0px #00BFFF;
                background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #603311), color-stop(1, #603311));
                background:-moz-linear-gradient(top, #603311 5%, #603311 100%);
                background:-webkit-linear-gradient(top, #603311 5%, #603311 100%);
                background:-o-linear-gradient(top, #603311 5%, #603311 100%);
                background:-ms-linear-gradient(top, #603311 5%, #603311 100%);
                background:linear-gradient(to bottom, #603311 5%, #603311 100%);
                filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#00EEEE', endColorstr='#00EEEE',GradientType=0);
                background-color:#603311;
                -moz-border-radius:3px;
                -webkit-border-radius:3px;
                border-radius:3px;
                border:1px solid #54381e;
                display:inline-block;
                cursor:pointer;
                color:#ffffff;
                font-family:Aparajita;
                font-size:18px;
                padding:6px 24px;
                text-decoration:none;
                text-shadow:0px 1px 0px #4d3534;
                height: 50px;
            }
            .myButton:hover {
                background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #4A777A), color-stop(1, #4A777A));
                background:-moz-linear-gradient(top, #4A777A 5%, #4A777A 100%);
                background:-webkit-linear-gradient(top, #4A777A 5%, #4A777A 100%);
                background:-o-linear-gradient(top, #4A777A 5%, #4A777A 100%);
                background:-ms-linear-gradient(top, #4A777A 5%, #4A777A 100%);
                background:linear-gradient(to bottom, #4A777A 5%, #4A777A 100%);
                filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A777A', endColorstr='#4A777A',GradientType=0);
                background-color:#4A777A;
            }
            .myButton:active {
                position:relative;
                top:1px;
            }
        </style>
    </head>
    <body>
        <%
            String loginID = (String) session.getAttribute("loginid");
            String corpName = (String) session.getAttribute("corpName");
        %>

        <!--        <div class="container">
                    <div  class="row" id="myDIV" align="center">
                        <div class="col-sm-12">
                            <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/sysadminIndex.htm" target="content" ><button class="myButton"  style="width:130px;">Home</button></a>
                    <a href="unmappedpayment.htm" target="content"><button class="myButton">Unmapped amounts</button></a>
                    <a href="corporatepayment.htm" target="content"><button class="myButton">Corporate payment</button></a>
                    <a href="fileUpload.htm" target="content"><button class="myButton">Upload files</button></a>
                    <a href="viewDocumentSets.htm" target="content"><button class="myButton">View files</button></a>
                    <a href="documentMaker.htm" target="content"><button class="myButton">Maker</button></a>
                    <a href="documentChecker.htm" target="content"><button class="myButton">Checker</button></a>
                    <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="myButton" style="width:90px;">Logout</button></a>
                
                    <table align="right" style="font-size: 20px;">
                        <tr class="detail"><td>Login ID :<%=loginID%></td>  </tr><tr class="detail"><td>CorpName:<%=corpName%>&nbsp;&nbsp;&nbsp;</td></tr>
                    </table>
                            </div>
                        </div>  
                    </div>
                </div>-->
        <!--        <p style="margin-left: 15%;color: grey"><span style='font-size:20px;'>&#128073;&nbsp;</span><b>Home-> Payment Disbursement-> Misc. Module</b></p>-->

        <br>
        <h2>${Poolbal}</h2>
        <h2 style="color:red;">${warning}</h2>
        <table id="unmappedTable" border="2" bordercolor="red" cellspacing="3" cellpadding="8" align="center">

            <thead>
            <th style="background-color:burlywood">Statement id</th>
            <th style="background-color:burlywood">Corporate id</th>
            <th style="background-color:burlywood">Corporate Name</th>
            <th style="background-color:burlywood">Statement_Date</th>
            <th style="background-color:burlywood">Paid Amount</th>
            <th style="background-color:burlywood">Unmapped Balance</th>
            <th style="background-color:burlywood">Remarks</th>
            <th style="background-color:burlywood">Set Reference</th>
            <th style="background-color:burlywood">Action</th>
        </thead>
        <tbody>
            <c:forEach items="${data}" var="sd" varStatus="iteration">
                <tr align="center">
                    <td>${sd.stmtId}</td>
                    <td>${sd.corporates.corporateId}</td>
                    <td>${sd.corporates.corporateName}</td>
                    <td>${sd.amountDate}</td>
                    <td>${sd.paidAmount}</td>
                    <td>${sd.mappedBalance}</td>
                    <td>${sd.remarks}</td>
                    <td>
                        <select name="DsetSelect" style="height:25px;width:170px;" id="${sd.stmtId}" onchange="setdset(this.value)">
                            <option value="null">Select DOCUMENT SET</option>
                            <c:forEach items="${folders}" var="f1">
                                <option value="${f1}">${f1}</option>
                            </c:forEach>
                        </select><br><br> 
                    </td>
                    <td><input type="button" id="returnbutton" value="Return payment" onclick="remove(${sd.mappedBalance},${sd.stmtId},${sd.corporates.corporateId})"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form id="form1" action="removeunmapped.htm" method="post">
        <input type="hidden" value="" id="hiddentext" name="amtvalue">
        <input type="hidden" value="" id="hiddenid" name="stmtid">
        <input type="hidden" value="" id="hiddendset" name="Dset">
        <input type="hidden" value="" id="hiddencorpid" name="corpid">
    </form>


    <br><br>
    <br><br>
    <br><br>
    <br><br>
    <br><br>
    <br><br>

</body>
</html>
