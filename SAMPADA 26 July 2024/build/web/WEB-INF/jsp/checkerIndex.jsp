<%--
    Document   : checkerIndex
    Created on : Oct 14, 2019, 12:42:04 PM
    Author     : cdac
--%>

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
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
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
    <body style="background-color:#FBF2EA; width:95%;">

        <div class="container">
            <div  class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <div class="dropdown">
                            <button class="btn" style="width:180px;">Payable</button>
                            <div class="dropdown-content">
                                <!--<a href="billproc/viewCheckerPendingPayableList.htm" target="appcontent" ><button class="btn" style="width:150px;">Payable</button></a>-->

                                <a href="billproc/viewCheckerPendingPayableList.htm" target="appcontent" >Bills</a>
                                <a href="interest/viewCheckerInterestPayableList.htm" target="appcontent" style="width:180px;">Interest</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="btn" style="width:180px;">MAster</button>
                            <div class="dropdown-content">

                                <a href="master/checkerconfirmNoInterestPeriodList.htm" target="appcontent" >Checker due dates</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="btn" style="width:180px;">Bank Statement</button>
                            <div class="dropdown-content">
                                <a href="import/viewBankStatement.htm" target="appcontent" >Checker</a>
                                <a href="import/viewCheckedBankStatement.htm" target="appcontent" style="width:180px;">View Bank Statement</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="btn" style="width:180px;">Disburse</button>
                            <div class="dropdown-content">
                                <a href="disburse/viewCheckerDisbursement.htm" target="appcontent" >Bills</a>
                                <a  href="disburse/viewCheckerInterestReceivableDetails.htm" target="appcontent" >Interest</a>
                                <a  href="disburse/viewCheckerCSDF.htm" target="appcontent" >PSDF</a>
                            </div>
                        </div>
                        <a href="disburse/documentChecker.htm" target="appcontent"><button class="btn" style="width:190px;">Misc Module</button></a>


                        <div class="dropdown">
                            <button class="btn" style="width:180px;">Interest</button>
                            <div class="dropdown-content">
                                <!--<a href="interest/interestPreocessingbyRLDC.htm" target="appcontent" >Process Interest</a>-->
                                <a href="interest/interestProcessingChecker.htm" target="appcontent">Check Interest</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/recon/adjustchecker.htm" target="appcontent"><button class="myButton" style="width:190px;">Bill Adjustments</button></a>

                        <!--<a href="interest/viewCheckerInterestPayableList.htm" target="appcontent" ><button class="btn"  style="width:130px;">Interest</button></a>-->
                        <!--<a href="disburse/viewCheckerDisbursement.htm" target="appcontent" ><button class="btn"  style="width:130px;">Disburse</button></a>-->
                        <a href="applicant/applicantResetPassword.htm" target="appcontent" ><button class="btn" style="width:190px;">Change Password</button></a>           
                        <a href="logout.htm" target="content"><button class="btn" style="width:90px;">Logout</button></a>
                    </div>
                </div>
            </div>

            <table align="right" style="font-size: 20px;">
                <tr><td>Login ID :${loginID}</td>
                </tr>
            </table><br/>

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
