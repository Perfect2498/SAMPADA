<%-- 
    Document   : documentChecker
    Created on : 26 Mar, 2020, 1:23:21 PM
    Author     : Kaustubh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function getFiles(name)
            {
                var r = confirm("Are you sure want to Check this Document Set ?");
                if (r == false)
                    return;
                document.getElementById('f5documentset').value = name;
                document.getElementById('form5').submit();
            }

            function getDocs(name)
            {
                var r = confirm("Are you sure want to Check this Document ?");
                if (r == false)
                    return;
                document.getElementById('f6document').value = name;
                document.getElementById('form6').submit();
            }
            function getFile(value)
            {
                document.getElementById('f9document').value = value;
                document.getElementById('form9').submit();
            }
            function verifybank(uid, stmtid)
            {
                var r = confirm("Are you sure want to Check this Transaction ?");
                if (r == false)
                    return;
                document.getElementById('txuid7').value = uid;
                document.getElementById('stmtid7').value = stmtid;

                document.getElementById('form7').submit();
            }
            function deletebank(uid, stmtid)
            {
                var r = confirm("Are you sure want to Delete this Transaction ?");
                if (r == false)
                    return;
                document.getElementById('txuidb').value = uid;
                document.getElementById('stmtidb').value = stmtid;
                document.getElementById('form8').submit();
            }

            function verifyPayment(uid, stmtid)
            {
                var r = confirm("Are you sure want to Check this Transaction ?");
                if (r == false)
                    return;
                document.getElementById('txuid').value = uid;
                document.getElementById('stmtid').value = stmtid;

                if (stmtid == "")
                    document.getElementById('flag').value = 1;

                document.getElementById('form3').submit();
            }
            
            function verifyTransfer(uid)
            {
                var r = confirm("Are you sure want to Check this Transaction ?");
                if (r == false)
                    return;
                document.getElementById('form101uis').value = uid;
                document.getElementById('form101').submit();
            }
            
            function verifyTransfer1(uid)
            {
                var r = confirm("Are you sure want to Check this Transaction ?");
                if (r == false)
                    return;
                document.getElementById('form101uis1').value = uid;
                document.getElementById('form1011').submit();
            }

            function deletePayment(uid, stmtid)
            {
                var r = confirm("Are you sure want to Delete this Transaction ?");
                if (r == false)
                    return;
                document.getElementById('txuid4').value = uid;
                document.getElementById('stmtid4').value = stmtid;

                document.getElementById('form4').submit();
            }

            function deleteDocs(name)
            {
                var r = confirm("Are you sure want to Delete this Document ?");
                if (r == false)
                    return;
                document.getElementById('document').value = name;
                document.getElementById('form2').submit();
            }

            function deleteFiles(name)
            {
                var r = confirm("Deleting a DOCUMENT SET will also delete FILES inside it. \nAre you sure want to Delete this Document Set ??");
                if (r == false)
                    return;
                document.getElementById('documentset').value = name;
                document.getElementById('form1').submit();
            }
            function deleteTransfer(uid)
            {
                var r = confirm("Are you sure want to Delete this Transfer ?");
                if (r == false)
                    return;
                document.getElementById('f6uid').value = uid;
                document.getElementById('form99').submit();
            }
            
            function deleteTransfer1(uid)
            {
                var r = confirm("Are you sure want to Delete this Transfer ?");
                if (r == false)
                    return;
                document.getElementById('f6uid1').value = uid;
                document.getElementById('form991').submit();
            }
        </script>
        <style>
            .detail {
                font: 700 20px Aparajita ,Kaushan Script,Lato, sans-serif;
                line-height: 1.8;
                color: black;
                width:100%;
            }

            .returnbutton {
                background-color: #ff6633;
                color: white;
                font-weight: bold;
                padding: 10px 20px;
                margin: 4px 2px;
                cursor: pointer;
            }

            .returnbutton:hover {
                background-color: #ff0000;
                color: white;
                font-weight: bold;
                padding: 10px 20px;
                margin: 4px 2px;
                cursor: pointer;
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


        <h2 align="center"><u>Checker Pending List</u></h2>
        <h2>Check Document Sets : <label style="color:red;font-size:22px;">${msg1}</label></h2>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N</th>
            <th>Document <br> Set No.</th>
            <th>Nos Documents <br> inside</th>
            <th>Description for Document Set</th>
            <th>Status</th>
            <th>Upload Date by Maker</th>
            <th>Check Date by Checker</th>
                <c:forEach items="${dset_details}" var="sd" varStatus="iteration">
                <tr align="center">
                    <td>${iteration.index + 1}</td>
                    <td>${sd.documentSetNo}</td>
                    <td>${sd.no_of_docs}</td>
                    <td>${sd.description}</td>
                    <td>${sd.status}</td>
                    <td>${sd.uploadDateByMaker}</td>
                    <td><button id="${sd.documentSetNo}" class="returnbutton" onclick="getFiles(this.id)">Verify</button>&nbsp;&nbsp;&nbsp;
                        <button id="${sd.documentSetNo}" class="returnbutton" onclick="deleteFiles(this.id)">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form name="form1" id="form1" action="makerDeletesSet.htm" method="post" >
            <input type="hidden" value="" id="documentset" name="documentset">
        </form>
        <form name="form5" id="form5" action="checkerVerifiesSet.htm" method="post" >
            <input type="hidden" value="" id="f5documentset" name="documentset">
        </form>
        <br><br>

        <h2>Check Documents : <label style="color:red;font-size:22px;">${msg2}</label></h2>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N</th>
            <th>Document No.</th>
            <th>Description for Document</th>
            <th>Type</th>
            <th>Size</th>
            <th>Status</th>
            <th>Upload Date by Maker</th>
            <th>Check Date by Checker</th>
                <c:forEach items="${doc_details}" var="sd" varStatus="iteration">
                <tr align="center">
                    <td>${iteration.index + 1}</td>
                    <!--<td>${sd.documentNo}</td>-->
                    <td><a id="${sd.filename}" onclick="getFile(this.id)" style="color:blue;cursor:pointer;">${sd.documentNo}</a></td>

                    <td>${sd.description}</td>
                    <td>${sd.filetype}</td>
                    <td>${sd.filesize} kB</td>
                    <td>${sd.status}</td>
                    <td>${sd.uploadDateByMaker}</td>
                    <td><button id="${sd.documentNo}" class="returnbutton" onclick="getDocs(this.id)">Verify</button>&nbsp;&nbsp;&nbsp;
                        <button id="${sd.documentNo}" class="returnbutton" onclick="deleteDocs(this.id)">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form name="form2" id="form2" action="makerDeletesDocument.htm" method="post" >
            <input type="hidden" value="" id="document" name="document">
        </form>
        <form name="form6" id="form6" action="checkerVerifiesDocument.htm" method="post" >
            <input type="hidden" value="" id="f6document" name="document">
        </form>
        <br><br>
        <br><br>
        <hr style="border-color: black">
        <h2>Check Unmapped Bank Statements : </h2>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N.</th>
            <th>Corporate Id</th>
            <th>Statement Id</th>
            <th>Corporate Name</th>
            <th>Balance Amount</th>
            <th>Date of Cr.</th>
            <th>Ref. Document SET</th>
            <th>Action</th>
                <c:forEach items="${unmappedlist}" var="tx" varStatus="iteration">
                <tr>
                    <td>${iteration.index + 1}</td>

                    <td>${tx.bankStatement.corporates.corporateId}</td>
                    <td>${tx.bankStatement.stmtId}</td>
                    <td>${tx.bankStatement.corporates.corporateName}</td>
                    <td>${tx.mappedBalance}</td>
                    <td>${tx.bankStatement.amountDate}</td>
                    <td>${tx.fileName}</td>                    
                    <td><button id="${tx.slno}" class="returnbutton" onclick="verifybank(this.id,${tx.bankStatement.stmtId})">Verify</button>&nbsp;&nbsp;&nbsp;
                        <button id="${tx.slno}" class="returnbutton" onclick="deletebank(this.id,${tx.bankStatement.stmtId})">Cancel</button></td>
                </tr>
            </c:forEach>
        </table>
        <br><br>

        <hr style="border-color: black">
        <h2>Check Bank Payment Refunds : </h2>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N.</th>
            <th>Corporate Id</th>
            <th>Statement Id</th>
            <th>Corporate Name</th>
            <th>Payment Amount</th>
            <th>Date of Cr.</th>
            <th>Ref. Document SET</th>
            <th>Maker Date</th>
            <th>remarks</th>
            <th>Category</th>
            <th>Pool Balance</th>
            <th>Action</th>
                <c:forEach items="${bankpays}" var="tx" varStatus="iteration">
                <tr>
                    <td>${iteration.index + 1}</td>
                    <td>${tx.corpId}</td>
                    <td>${tx.stmtId}</td>
                    <td>${tx.corpName}</td>
                    <td>${tx.refundAmt}</td>
                    <td>${tx.entryDate}</td>
                    <td>${tx.documentSet}</td>
                    <td>${tx.makerDate}</td>
                    <td>${tx.remarks}</td>
                    <td>${tx.amtCategory}</td>
                    <td>${tx.mainPoolBalance}</td>

                    <td><button id="${tx.uniqueId}" class="returnbutton" onclick="verifyPayment(this.id,${tx.stmtId})">Verify</button>&nbsp;&nbsp;&nbsp;
                        <button id="${tx.uniqueId}" class="returnbutton" onclick="deletePayment(this.id,${tx.stmtId})">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br><br>

        <h2>Check Direct Refunds : </h2>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N.</th>
            <th>Disburse Id</th>
            <th>Corporate Id</th>
            <th>Corporate Name</th>
            <th>Payment Amount</th>
            <th>Ref. Document SET</th>
            <th>Maker Date</th>
            <th>remarks</th>

            <th>Category</th>
            <th>Pool Balance</th>
            <th>Action</th>
                <c:forEach items="${directpays}" var="tx" varStatus="iteration">
                <tr>
                    <td>${iteration.index + 1}</td>
                    <td>${"M"}${tx.uniqueId}</td>
                    <td>${tx.corpId}</td>
                    <td>${tx.corpName}</td>
                    <td>${tx.refundAmt}</td>
                    <td>${tx.documentSet}</td>
                    <td>${tx.makerDate}</td>
                    <td>${tx.remarks}</td>
                    <td>${tx.amtCategory}</td>
                    <td>${tx.mainPoolBalance}</td>
                    <td><button id="${tx.uniqueId}" class="returnbutton" onclick="verifyPayment(this.id,${tx.stmtId})">Verify</button>&nbsp;&nbsp;&nbsp;
                        <button id="${tx.uniqueId}" class="returnbutton" onclick="deletePayment(this.id,${tx.stmtId})">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <hr style="border-color: black">
        <br>
        
        <h2>Verify Pool to Interest transfers : </h2>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N.</th>
            <th>Payment Amount</th>
            <th>Ref. Document SET</th>
            <th>Date</th>
            <th>remarks</th>
            <th>TYPE</th>
            <th>Pool Balance</th>
            <th>Interest Balance</th>
            <th>Action</th>
            
            <c:forEach items="${PoolToInt}" var="tx" varStatus="iteration">
                <tr>
                    <td>${iteration.index + 1}</td>
                    <td>${tx.refundAmt}</td>
                    <td>${tx.documentSet}</td>
                    <td>${tx.makerDate}</td>
                    <td>${tx.remarks}</td>
                    <c:if test="${tx.mainPoolBalance != null}">
                        <td>DSM</td>
                        <td>${tx.mainPoolBalance}</td>
                        <td>${tx.intPoolBalance}</td>
                    </c:if>
                    <c:if test="${tx.poolAgcBal != null}">
                        <td>SRAS</td>
                        <td>${tx.poolAgcBal}</td>
                        <td>${tx.intAgcBal}</td>
                    </c:if>
                    <c:if test="${tx.poolRrasBal != null}">
                        <td>TRAS</td>
                        <td>${tx.poolRrasBal}</td>
                        <td>${tx.intRrasBal}</td>
                    </c:if>

                    <td><button id="${tx.uniqueId}" class="returnbutton" onclick="verifyTransfer(this.id)">Verify</button>&nbsp;&nbsp;&nbsp;
                        <button id="${tx.uniqueId}" class="returnbutton" onclick="deleteTransfer(this.id)">Cancel</button></td>
                </tr>
            </c:forEach>
        </table>

        
        <br>
        
        <h2>Verify Pool to Pool transfers : </h2>
        <table border="2" bordercolor="red" cellspacing="3" cellpadding="10" align="center">
            <th>S.N.</th>
            <th>Payment Amount</th>
            <th>Ref. Document SET</th>
            <th>Date</th>
            <th>remarks</th>
            <th>TYPE</th>
            <th>Pool Balance</th>
            <th>Pool Balance</th>
            <th>Action</th>
            
            <c:forEach items="${PoolToPool}" var="tx" varStatus="iteration">
                <tr>
                    <td>${iteration.index + 1}</td>
                    <td>${tx.refundAmt}</td>
                    <td>${tx.documentSet}</td>
                    <td>${tx.makerDate}</td>
                    <td>${tx.remarks}</td>
                    <c:if test="${tx.transId == 1}">
                        <td>DSM to SRAS</td>
                        <td>DSM:${tx.mainPoolBalance}</td>
                        <td>SRAS:${tx.poolAgcBal}</td>
                    </c:if>
                    <c:if test="${tx.transId == 2}">
                        <td>DSM to TRAS</td>
                        <td>${tx.mainPoolBalance}</td>
                        <td>${tx.poolRrasBal}</td>
                    </c:if>
                    <c:if test="${tx.transId == 3}">
                        <td>SRAS to TRAS</td>
                        <td>${tx.poolAgcBal}</td>
                        <td>${tx.poolRrasBal}</td>
                    </c:if>

                    <td><button id="${tx.uniqueId}" class="returnbutton" onclick="verifyTransfer1(this.id)">Verify</button>&nbsp;&nbsp;&nbsp;
                        <button id="${tx.uniqueId}" class="returnbutton" onclick="deleteTransfer1(this.id)">Cancel</button></td>
                </tr>
            </c:forEach>
        </table>

        <form name="form3" id="form3" action="checkerVerifiesPayment.htm" method="post" >
            <input type="hidden" value="" id="txuid" name="txuid">
            <input type="hidden" value="" id="stmtid" name="stmtid">
        </form>
        <form name="form4" id="form4" action="makerDeletesPayment.htm" method="post" >
            <input type="hidden" value="" id="txuid4" name="txuid">
            <input type="hidden" value="" id="stmtid4" name="stmtid">
        </form>
        <form name="form7" id="form7" action="checkerVerifiesbank.htm" method="post" >
            <input type="hidden" value="" id="txuid7" name="txuid">
            <input type="hidden" value="" id="stmtid7" name="stmtid">
        </form>
        <form name="form8" id="form8" action="makerDeletesbank.htm" method="post" >
            <input type="hidden" value="" id="txuidb" name="txuid">
            <input type="hidden" value="" id="stmtidb" name="stmtid">
        </form>

        <form name="form9" id="form9" action="fileDownload.htm" method="post" >
            <input type="hidden" value="" id="f9document" name="document">
        </form>
        
        <form name="form99" id="form99" action="makerDeletesTransfer.htm" method="post" >
            <input type="hidden" value="" id="f6uid" name="f6uid">
        </form>
        <form name="form991" id="form991" action="makerDeletesTransfer1.htm" method="post" >
            <input type="hidden" value="" id="f6uid1" name="f6uid1">
        </form>
        
        <form name="form101" id="form101" action="checkerVerifiesTransfer.htm" method="post" >
            <input type="hidden" value="" id="form101uis" name="form101uis">
        </form>
        <form name="form1011" id="form1011" action="checkerVerifiesTransfer1.htm" method="post" >
            <input type="hidden" value="" id="form101uis1" name="form101uis1">
        </form>
        <br><br>

        <br><br>
        <br><br>
        <br><br>
        <br>
    </body>
</html>
