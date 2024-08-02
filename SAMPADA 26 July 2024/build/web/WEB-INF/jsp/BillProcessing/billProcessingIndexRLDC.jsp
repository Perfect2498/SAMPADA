<%--

Document : billProcessingIndexRLDC

Created on : Jun 18, 2019, 8:35:20 AM

Author : JaganMohan

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >

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
            <%
                String loginID = (String) session.getAttribute("loginid");
                String corpName = (String) session.getAttribute("corpName");

            %>
            <table align="right" style="font-size: 20px;">
                <tr><td>Login ID :<%=loginID%></td>  </tr><tr><td>CorpName:<%=corpName%></td></tr>

            </table>
            <div class="row" id="myDIV" align="center">
                <div class="col-sm-12">
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/sysadminIndex.htm" target="content" ><button class="myButton" style="width:130px;">Home</button></a>
                        <div class="dropdown">
                            <button class="myButton active" style="width:150px;">Bill Upload</button>
                            <div class="dropdown-content" style="width:200px;">
                                <a href="${pageContext.request.contextPath}/import/importDSMTemplate.htm" target="appcontent" >DSM </a>
                                <a href="${pageContext.request.contextPath}/import/importRRASPayableTemplate.htm" target="appcontent" >RRAS Down Regln </a>
                                <a href="${pageContext.request.contextPath}/import/importRRASReceivableTemplate.htm" target="appcontent" >RRAS Up Regln  </a>
                                <a href="${pageContext.request.contextPath}/import/importAGCTemplate.htm" target="appcontent" >AGC </a>
                                <a href="${pageContext.request.contextPath}/import/importFRASTemplate.htm" target="appcontent" >FRAS </a>
                                <a href="${pageContext.request.contextPath}/import/importSRASTemplate.htm" target="appcontent" >SRAS </a>
                                <a href="${pageContext.request.contextPath}/import/importTRASMTemplate.htm" target="appcontent" >TRAS-M </a>
                                <a href="${pageContext.request.contextPath}/import/importTRASSTemplate.htm" target="appcontent" >TRAS-S </a>
                                <a href="${pageContext.request.contextPath}/import/importTRASETemplate.htm" target="appcontent" >TRAS-E </a>


                            </div>
                        </div>

                        <div class="dropdown">
                            <button class="myButton active" style="width:150px;">REV-Bill Upload</button>
                            <div class="dropdown-content" style="width:200px;">
                                <a href="${pageContext.request.contextPath}/import/importREVDSMTemplate.htm" target="appcontent" >DSM </a>
                                <a href="${pageContext.request.contextPath}/import/importREVRRASPayableTemplate.htm" target="appcontent" >RRAS Down Regln </a>
                                <a href="${pageContext.request.contextPath}/import/importREVRRASReceivableTemplate.htm" target="appcontent" >RRAS Up Regln  </a>
                                <a href="${pageContext.request.contextPath}/import/importREVAGCTemplate.htm" target="appcontent" >AGC </a>
                                <a href="${pageContext.request.contextPath}/import/importREVFRASTemplate.htm" target="appcontent" >FRAS </a>
                                <a href="${pageContext.request.contextPath}/import/importREVSRASTemplate.htm" target="appcontent" >SRAS </a>

                            </div>
                        </div>     
                        <div class="dropdown">
                            <button class="myButton" style="width:100px;">Bills</button>
                            <div class="dropdown-content">
                                <a href="billVerification.htm" target="appcontent" >Mapping</a>
                                <a href="viewMakerPendingPayableListbyRLDC.htm" target="appcontent" >Maker</a>
                                <!--                               <a href="viewCorporateRefundPayableList.htm" target="appcontent" >Map Refund</a>-->
                                <a href="interest/viewInterestMappingPayableCorporateList.htm" target="appcontent" >Map Interest</a>                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="myButton" style="width:100px;">Checker</button>
                            <div class="dropdown-content">
                                <a href="viewCheckerPendingPayableList.htm" target="appcontent" >Bill </a>
                                <a href="interest/viewCheckerInterestPayableList.htm" target="appcontent" >Interest</a>
                            </div>
                        </div>
                        <div class="dropdown">
                            <button class="myButton" style="width:100px;">Pending</button>
                            <div class="dropdown-content">
                                <a href="billProcessingPendingPayableList.htm" target="appcontent" >Bill </a>
                                <a href="interest/viewPendingInterestPayableList.htm" target="appcontent" >Interest</a>
                                <!--<a href="viewPendingRefundPayableList.htm" target="appcontent" >Refunds</a>-->
                                <a href="disburse/viewPendingRefundReceivableList.htm" target="appcontent" >Refund</a>

                            </div>
                        </div>

                        <div class="dropdown">
                            <button class="myButton" style="width:120px;">View Bills</button>
                            <div class="dropdown-content">
                                <a href="weeklyBill.htm" target="appcontent">Weekly</a>                              
                                <a href="underConstruction.htm" target="appcontent" >Closed</a>
                                <a href="weeklyBill123.htm" target="appcontent" >Weekly Bills</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/logout.htm" target="content"><button class="myButton" style="width:90px;">Logout</button></a>
                    </div>
                </div>
            </div>
            <p align="left" style="margin-left: 15%;font-style: bold;"><span style='font-size:20px;'>&#128073;&nbsp;</span><b>Home->Bill Processing</b></p>
            <div class="row">
                <div class="col-sm-12">
                    <iframe src="billProcessingPendingPayableList.htm" style="width: 100%;height: auto;" scrolling="no" onload="setIframeHeight(this.id);" id="appcontent" name="appcontent" frameborder="0"></iframe>
                </div>
            </div>
        </div>
        <script>
            // Add active class to the current button (highlight it)
            var header = document.getElementById("myDIV");
            var btns = header.getElementsByClassName("billprocessbtn");
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