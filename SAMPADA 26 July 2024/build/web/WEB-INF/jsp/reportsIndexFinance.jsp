<%-- 
    Document   : reportsIndexFinance
    Created on : 3 Feb, 2020, 2:47:44 PM
    Author     : abt
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >


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
    <body style="background-color:#FBF2EA;">
        <div class="container">
            <div  class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/financeindex.htm" target="content" ><button class="myButton"  style="width:100px;">Home</button></a>
                        <div class="dropdown">
                            <button class="myButton" style="width:130px;">Pool Member</button>
                            <div class="dropdown-content">
                                <a href="finalreport.htm" target="appcontent" style="width:180px;">Commercial Payable</a>

                                <a href="recfinalreport.htm" target="appcontent" style="width:180px;">Commercial Receivable</a>
                                
                                <a href="reports/getCERCreport.htm" target="appcontent" style="width:180px;">CERC report</a>
                                
                                <a href="reports/getBankPayVoucher.htm" target="appcontent" style="width:180px;">BPV report</a>
                                
                                <a href="reports/getBRV.htm" target="appcontent" style="width:180px;">BRV report</a>
                                
                                <a href="reports/getJV.htm" target="appcontent" style="width:180px;">JV report</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="myButton" style="width:130px;">Bank Reports</button>
                            <div class="dropdown-content">
                                <a href="corpYearlyBnkStmt.htm" target="appcontent" style="width:240px;"> Pool Member Bank Statement</a>
                                <a href="poolaccount.htm" target="appcontent" style="width:180px;">Bank Statement CR Details</button></a>
                                <a href="bankdrdetails.htm" target="appcontent" style="width:180px;">Bank Statement DR Details</button></a>

                            </div>
                        </div>

                        <div class="dropdown">
                            <button class="myButton" style="width:130px;">Entity Group</button>
                            <div class="dropdown-content">
                                <a href="entfinalreport.htm" target="appcontent" style="width:180px;">Entitywise Reports</a>
                                <!--<a href="recentfinalreport.htm" target="appcontent" style="width:180px;">Receivable Final-Reports</a>-->
                            </div>
                        </div>

                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">Inter Regional</button>
                            <div class="dropdown-content">
                                <a href="irfinal.htm" target="appcontent" style="width:180px;">Payable-final</a>
                                <a href="recirfinal.htm" target="appcontent" style="width:180px;">Receivable-final</a>

                            </div>
                        </div>

                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">Mapped Details</button>
                            <div class="dropdown-content">
                                <a href="corpbanktobilldetails.htm" target="appcontent"   style="width:130px;"> Bank to Bill</a>

                                <a href="mappedbankdetails.htm" target="appcontent"   style="width:130px;"> Bill to Bank</a>
                                <a href="overallmapping.htm" target="appcontent"   style="width:130px;">Mapping</a>
                                 
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">Disbursed Details</button>
                            <div class="dropdown-content">
                               
                                  
                                  <a href="overalldisbursementnew.htm" target="appcontent"   style="width:130px;">Disbursing</a>
                                  <a href="overallpsdf.htm" target="appcontent"   style="width:130px;">PSDF</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">Note Sheet</button>
                            <div class="dropdown-content">
                                <a href="overallmappingnew.htm" target="appcontent"   style="width:130px;">Mapping_NS</a>
                                <a href="overalldisbursement.htm" target="appcontent"   style="width:130px;">Disbursing_NS</a>
                                <a href="Allnotesheet.htm" target="appcontent"   style="width:130px;">Overall_NS</a>

                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="myButton" style="width:100px;">Misc</button>
                            <div class="dropdown-content">
                                <a href="allbills.htm" target="appcontent" >Bills Status</a>
                                <a href="groups.htm" target="appcontent" >Groups</a>
                                <!--<a href="corpreconciliation.htm" target="appcontent" >Reconciliation</a>-->
                                <a href="getallrefundReceivable.htm" target="appcontent" style="width:180px;">Refund Payable-final</a>
                                <a href="getallrefundPayable.htm" target="appcontent" style="width:180px;">Refund Receivable-final</a>
                                <a href="getallinterestPayable.htm" target="appcontent" style="width:180px;">Interest Payable-final</a>
                                <a href="getallinterestReceivable.htm" target="appcontent" style="width:180px;">Interest Receivable-final</a>
                                <a href="interest/getInterestVerifyReport.htm" target="appcontent" style="width:180px;">Interest Verify Report</a>
                                <a href="interest/getInterestUNVerifyReport.htm" target="appcontent" style="width:180px;">Interest Un-Verify Report</a>

                                <a href="interest/getInterestPublishReport.htm" target="appcontent" style="width:180px;">Interest Publish Report</a>
                                <a href="reports/getDueDatesummary.htm" target="appcontent" style="width:180px;">Due-Datewise summary</a>


                            </div>
                        </div>
                        
                        <div class="dropdown">
                            <button class="myButton" style="width:150px;">New Reports</button>
                            <div class="dropdown-content">
                                <a href="reports/selectprevFYPayableweeks.htm" target="appcontent" style="width:300px;">1.1 Previous FY Payable Weeks</a>
                                <a href="reports/selectLCYear.htm" target="appcontent" style="width:300px;">1.2 DSM LC for FY 20xx</a>
                                <a href="reports/getMISPooldetail.htm" target="appcontent" style="width:300px;">2.1 MIS-Pool Account</a>
                                <a href="reports/getOldNLDC.htm" target="appcontent" style="width:300px;">Old format 2.1 NLDC</a>
                                <a href="reports/getAnnexuredetail.htm" target="appcontent" style="width:300px;">2.2 Annexure-Pool Account</a>
                                
                                <a href="reports/statusSummaryReport.htm" target="appcontent" style="width:300px;">3 Status summary</a>

                                <a href="reports/fullDSMbills.htm" target="appcontent" style="width:300px;">4 Full DSM Bills NLDC</a>
                                <a href="reports/getReconciliationReport.htm" target="appcontent" style="width:300px;">5 Reconciliation Report</a>
                            </div>
                        </div>
                        <!--                        <div class="dropdown">
                                                    <button class="myButton" style="width:150px;">Refund Details</button>
                                                    <div class="dropdown-content">
                                                        <a href="getallrefundPayable.htm" target="appcontent" style="width:180px;">Payable-final</a>
                                                        <a href="getallrefundReceivable.htm" target="appcontent" style="width:180px;">Receivable-final</a>
                        
                                                    </div>
                                                </div>
                                                <div class="dropdown">
                                                    <button class="myButton" style="width:150px;">Interest Details</button>
                                                    <div class="dropdown-content">
                                                        <a href="getallinterestPayable.htm" target="appcontent" style="width:180px;">Payable-final</a>
                                                        <a href="getallinterestReceivable.htm" target="appcontent" style="width:180px;">Receivable-final</a>
                        
                                                    </div>
                                                </div>-->
                        <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="myButton" style="width:90px;">Logout</button></a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <iframe src="finalreport.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent"  name="appcontent" frameborder="0"></iframe>
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
