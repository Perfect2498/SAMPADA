<%--
    Document   : editSingleCommercialGroupCopy
    Created on : Sep 6, 2019, 5:33:40 PM
    Author     : cdac
--%>

<%--
Document : editSingleCommercialGroup
Created on : Jun 20, 2019, 9:43:56 AM
Author : superusr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
        <title>New Corporate</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables.bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/fixedHeader.bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/responsive.bootstrap.min.css"/>">


        <script type="text/javascript" language="javascript" src="<c:url value="/js/jquery-3.3.1.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.bootstrap.min.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.fixedHeader.min.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.responsive.min.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/responsive.bootstrap.min.js" />"></script>

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

                var iframe = window.parent.document.getElementById('usrcontent');
                var container = document.getElementById('content1');
                iframe.style.height = container.offsetHeight + 150 + 'px';

            }

            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 30 + 'px';

            }



        </script>
        <script type="text/javascript">
            $(document).ready(function () {

                $(function () {

                    var getValidity = $('input[name="validity"]:checked').val();
                    //alert(getValidity);

                    if (getValidity == 'NO') {
                        $('#Remarks').show();
                    }
                    else {
                        $('#Remarks').hide();
                    }


                });

                $(function () {
                    $('input[name="validity"]').on('click', function () {


                        if ($(this).val() == 'NO') {
                            $('#Remarks').show();
                        }
                        else {
                            $('#Remarks').hide();
                        }

                    });
                });

            });



        </script>
        
    </head>
    <body id="content1" style="min-height: 1200px;background-color:#FBF2EA;"><br>
        <p align="center" style="font-size: 40px;color:#003366;"><b>Edit Pool Member Details</b></p>
        <form>

            <c:forEach items="${corporateInfo}" var="corp">

                <c:set var = "oldState" value = "${corp.getState()}"/>


                <table class="customerTable" align="center" width="50%" border="yes">

                    <tr>
                        <td>Pool Member ID</td>
                        <td><input type="text" name="corporateId" value="${corp.corporateId}" readonly/></td>
                    </tr>

                    <tr>
                        <td>Pool Member Type</td>
                        <td><input type="text" name="corporateType" value="${corp.getCorporateType()}" readonly/></td>
                    </tr>

                    <tr>
                        <td>Entry Date</td>
                        <fmt:formatDate pattern = "dd-MMM-yyyy" value = "${corp.getEntryDate()}" var="theFormattedDate" />
                        <td><input type="text" name="entryDate" value="${theFormattedDate}" readonly/></td>

                    </tr>
                    <tr>
                        <td>Pool Member Name</td>
                        <td><input type="text" name="sname" value="${corp.getSname()}"  required /></td>


                    </tr>
                    <tr>
                        <td>Pool Member Short Name</td>
                        <td><input type="text" name="corporateName" value="${corp.getCorporateName()}" readonly/></td>

                    </tr>


                    <tr>
                        <td>Location</td>
                        <td><input type="text" name="location" value="${corp.getLocation()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>State</td>
                        <td><select name="state">
                                <c:forEach var="item" items="${stateList}">
                                    <option value="${item.stateName}" ${item.stateName == oldState ? 'selected="selected"' : ''}>${item.stateName}</option>
                                </c:forEach>
                            </select></td>

                    </tr>
                    <tr>
                        <td>Address</td>
                        <td><input type="text" name="address" value="${corp.getAddress()}" pattern="(^(?=.*\S).+$)" required/></td>
                    </tr>
                    <tr>
                        <td>DSM Contact</td>
                        <td><input type="text" name="cpdsm" value="${corp.getDsmContact()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)"  required/></td>
                    </tr>
                    <tr>
                        <!--pattern="([^\s][a-zA-Z0-9\s]+$)"-->
                        <td>RRAS Contact</td>
                        <td><input type="text" name="cprras" value="${corp.getRrasContact()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>Cong Contact</td>
                        <td><input type="text" name="cpcong" value="${corp.getCongContact()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>Reactive Contact</td>
                        <td><input type="text" name="cpreact" value="${corp.getRectContact()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>Mobile No.</td>

                        <td><input type="text" name="mobile" value="${corp.getMobile()}" maxlength="10"  pattern="(^\d{10}$)"  required/></td>

                    </tr>

                    <tr>
                        <td>Agency Code</td>

                        <td><input type="text" name="office" value="${corp.getPartyCode()}"   maxlength="20" required/></td>

                    </tr>

                    <tr>
                        <td>Bank Sub Account Number</td>

                        <td><input type="text" name="subAccNo" value="${corp.getSubAccountNumber()}" /></td>

                    </tr>
                    
                    <tr>
                        <td>Bank Account Name</td>

                        <td><input type="text" name="BankAccName" value="${corp.getBankAccName()}" /></td>

                    </tr>
                    <tr>
                        <td>Validity</td>
                        <td>
                            <c:choose>
                                <c:when test="${corp.getValidity() =='YES'}">
                                    <input type="radio" name="validity"  value="YES" checked> YES<br>
                                    <input type="radio" name="validity"  value="NO" > NO<br>

                                    <div id="Remarks" style="display: none">
                                        Remarks:
                                        <input type="text" name="blockremarks" id="blockremarks" value="${corp.getBlockRemarks()}" pattern="(^(?=.*\S).+$)" required/>

                                    </div>


                                    <br />
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" name="validity" id="validity" value="YES" > YES<br>
                                    <input type="radio" name="validity" id="validity" value="NO" checked> NO<br>
                                    <div id="Remarks" style="display: none">
                                        Remarks:
                                        <input type="text" name="blockremarks" id="blockremarks" value="${corp.getBlockRemarks()}" pattern="(^(?=.*\S).+$)" required/>

                                    </div>
                                    <br />
                                </c:otherwise>
                            </c:choose>

                        </td>
                    </tr>

                    <br/>
                </table>
                <br/>
            </c:forEach>
            <br>
            <input type="submit" class="btn" style="margin-left: 45%" value="&#9999;Modify" name="bName" />

        </form>
    </body>
</html>
