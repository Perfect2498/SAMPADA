<%--
   Document   : sysadminIndex
   Created on : Jun 17, 2019, 2:27:54 PM
   Author     : JaganMohan

--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sysadmin Home Page</title>
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
        <!--       <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
               <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
               <link href="https://fonts.googleapis.com/css?family=Kaushan+Script|Lobster|Rock+Salt|Satisfy&display=swap" rel="stylesheet">-->
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


    </head>
    <body style="margin-left: 5%;background-color:#FBF2EA; width:90%;">


        <div class="container">
            <%
                String loginID = (String) session.getAttribute("loginid");
                String corpName = (String) session.getAttribute("corpName");

            %>
            <table align="right" style="font-size: 20px;">
                <tr><td>Login ID :<%=loginID%></td></tr>
                <tr><td>CorpName:<%=corpName%></td>
                </tr>
            </table>
            <div  class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <a href="master/MasterIndex.htm" target="content" ><button class="myButton"  style="width:90px;">Masters</button></a>


                        <a href="billproc/billProcessingIndexRLDC.htm" target="content" ><button class="myButton"  style="width:180px;">Bill Processing</button></a>


                        <div class="dropdown">
                            <button class="myButton" style="width:180px;">Bank Statement</button>
                            <div class="dropdown-content">
                                <a href="import/importBankStatementTemplate.htm" target="appcontent" style="width:180px;">Upload Bank Statement</a>
                                <a href="import/viewBankStatement.htm" target="appcontent" style="width:180px;">Checker</a>
                                <a href="import/viewCheckedBankStatement.htm" target="appcontent" style="width:180px;">View Bank Statement</a>


                            </div>
                        </div>
                        <a href="disburse/paymentDisbursementIndexRLDC.htm" target="content" ><button class="myButton"  style="width:180px;">Pay Disbursement</button></a>
                        <!--                        <a href="interest/viewInterestVerificationbyRLDC.htm" target="appcontent" ><button class="myButton"  style="width:100px;">Interest</button></a>-->
                        <a href="disburse/miscIndexRLDC.htm" target="content" ><button class="myButton"  style="width:180px;">Misc Module</button></a>

                        <div class="dropdown">
                            <button class="myButton" style="width:120px;">Interest</button>
                            <div class="dropdown-content">
                                <a href="report/newDisbursementReconciliationReport.htm" target="appcontent" style="width:180px;">Debit Confirm</a>
                                <a href="interest/viewInterestVerificationbyRLDC.htm" target="appcontent" >Verification</a>
                                <a href="interest/viewInterestRevert.htm" target="appcontent" >Revert</a>
                                <a href="interest/interestPreocessingbyRLDC.htm" target="appcontent" >Interest Publishing</a>
                                <!--<a href="interest/interestProcessingChecker.htm" target="appcontent">Pending Interest at Checker</a>-->
                                <a href="interest/interestProcessingChecker.htm" target="appcontent">Interest Checker</a>
                            </div>
                        </div>

                        <div class="dropdown">
                            <button class="myButton" style="width:120px;">Reconciliation</button>
                            <div class="dropdown-content">

                                <!--<a href="report/newReconciliationReportforallcorp.htm" target="appcontent" >Pool Members</a>-->

                                <a href="report/newReconciliationReport.htm" target="appcontent" >Pool Members</a>
                                <a href="report/Exceldownloadreconreport.htm" target="appcontent" style="width:180px;">Excel report</a>

                            </div>
                        </div>
                         <div class="dropdown">
                            <button class="myButton" style="width:200px;">Dynamic Reconciliation</button>
                            <div class="dropdown-content">

                                <!--<a href="report/newReconciliationReportforallcorp.htm" target="appcontent" >Pool Members</a>-->

                                <a href="report/dynnewReconciliationReport.htm" target="appcontent" >Pool Members</a>
                                <a href="report/dynExceldownloadreconreport.htm" target="appcontent" style="width:180px;">Excel report</a>

                            </div>
                        </div>
                        <!--                        <a href="report/newReconciliationReport.htm" target="appcontent" ><button class="btn"  style="width:130px;">Reconciliation</button></a>-->


                        <a href="recon/BillAdjustIndexRLDC.htm" target="content" ><button class="myButton"  style="width:150px;">Bill Adjustments</button></a>

                        <a href="report/reportsIndexRLDC.htm" target="content" ><button class="myButton"  style="width:130px;">Reports</button></a>
                        <a href="logout.htm" target="content"><button class="myButton" style="width:90px;">Logout</button></a>
                    </div>
                </div>
            </div>     
            <br/>


            <div class="row">
                <div class="col-sm-12">
                    <iframe src="billproc/welcomePendingChart.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>
                </div>
            </div>
        </div>


        <script>
            // Add active class to the current button (highlight it)
            var header = document.getElementById("myDIV");
            var btns = header.getElementsByClassName("btn");
            for (var i = 0; i < btns.length; i++) {
                btns[i].addEventListener("click", function () {
                    var current = document.getElementsByClassName("active");
                    current[0].className = current[0].className.replace(" active", "");
                    this.className += " active";
                });
            }
        </script>


    </body>



</html>