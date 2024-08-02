<%--
    Document   : editSingleEntity
    Created on : Jun 20, 2019, 1:52:34 PM
    Author     : superusr
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
        <title>New Entity</title>
        <link rel="stylesheet" type="text/css"  href="<c:url value="/css/bootstrap.min.css" />">
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
        
    </head>
    <body id="content1"  style="min-height: 1200px;background-color:#FBF2EA;"><br>
        <p align="center" style="font-size: 40px;color:#003366;"><b>Edit Entity Details</b></p>
        <form>

            <c:forEach items="${corporateInfo}" var="corp">
                <table class="customerTable"  align="center"  width="50%" border="yes">
                    <input type="text" name="corporateId" value="${corp.corporates.getCorporateId()}" readonly hidden="yes"/>
                    <tr>
                        <td>Entity ID</td>
                        <td><input type="text" name="entityId" value="${corp.getEntityId()}" readonly/></td>
                    </tr>


                    <tr>
                        <td>Entity Name</td>
                        <td><input type="text" name="sname" value="${corp.getShortEntityname()}"  required /></td>


                    </tr>
                    <tr>
                        <td>Entity Short Name</td>
                        <td><input type="text" name="entityName" value="${corp.getEntittyName()}" required/></td>

                    </tr>
                    <tr>
                        <td>Commercial Group Short Name</td>
                        <td><input type="text" name="corpName" value="${corp.corporates.getCorporateName()}" readonly/></td>
                    </tr>

                    <tr>
                        <td>Entity Type</td>

                        <c:set var = "oldEntityType" value = "${corp.getEntityType()}"/>
                        <td><select name="entityType">
                                <c:forEach var="item" items="${entityTypeList}">
                                    <option value="${item.entityType}" ${item.entityType == oldEntityType ? 'selected="selected"' : ''}>${item.entityType}</option>
                                </c:forEach>
                            </select></td>
                <!--<td><input type="text" name="entityType" value="${corp.getEntityType()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>-->
                    </tr>
                    <tr>
                        <td>Bank Name</td>
                        <td><input type="text" name="bankName" value="${corp.getBankName()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>Branch Name</td>
                        <td><input type="text" name="branchName" value="${corp.getBranchName()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>Account no.</td>

                        <td><input type="text" name="accno" value="${corp.getAccountNumber()}" pattern="(^\d{9,18}+$)" required/></td>

<!--                        <td><input type="text" name="accno" value="${corp.getAccountNumber()}" pattern="(^[0-1]{1,1}+$)" required/></td>-->
                    </tr>
                    <tr>
                        <td>IFSC</td>
                        <td><input type="text" name="ifsc" value="${corp.getIfscCode()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>RTGS/NEFT Enabled</td>
                        <c:set var = "oldRTGSFlag" value = "${corp.getRtgsNeftFlag()}"/>
                        <td><select name="rtgsneft">
                                <c:forEach var="item" items="${statusList}">
                                    <option value="${item.status}" ${item.status == oldRTGSFlag ? 'selected="selected"' : ''}>${item.status}</option>
                                </c:forEach>
                            </select></td>
                    </tr>

                    <tr>
                        <td>Location</td>
                        <td><input type="text" name="location" value="${corp.getEntityLocation()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>State</td>
                        <c:set var = "oldState" value = "${corp.getStateName()}"/>
                        <td><select name="state">
                                <c:forEach var="item" items="${stateList}">
                                    <option value="${item.stateName}" ${item.stateName == oldState ? 'selected="selected"' : ''}>${item.stateName}</option>
                                </c:forEach>
                            </select></td>

<!--<td><input type="text" name="state" value="${corp.getStateName()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s]+$)" required/></td>-->
                    </tr>
                    <tr>
                        <td>Address</td>
                        <td><input type="text" name="address" value="${corp.getAddress()}"  required/></td>
                    </tr>
                    <tr>
                        <td>DSM Contact</td>
                        <td><input type="text" name="cpdsm" value="${corp.getDsmContact()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>RRAS Contact</td>
                        <td><input type="text" name="cprras"  value="${corp.getRrasContact()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
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

<!--                        <td><input type="text" name="mobile"  value="${corp.getMobile()}" pattern="(^[0-9]{10}+$)" required/></td>-->
                        <td><input type="text" name="mobile"  value="${corp.getMobile()}" pattern="(^\d{10}+$)"  required/></td>

                    </tr>

                    <tr>
                        <td>Office No.(city code-phone number)</td>

                        <td><input type="text" name="office" value="${corp.getOffice()}" pattern="(^\d{1,20}$)" required/></td>

<!--                        <td><input type="text" name="office" value="${corp.getOffice()}" pattern="(^[0-9]{10}+$)" required/></td>-->
                    </tr>

                    <tr>
                        <td>RRAS Considerable</td>
                        <c:set var = "oldRRASFlag" value = "${corp.getRrasFlag()}"/>
                        <td><select name="rrasconsiderable">
                                <c:forEach var="item" items="${statusList}">
                                    <option value="${item.status}" ${item.status == oldRRASFlag ? 'selected="selected"' : ''}>${item.status}</option>
                                </c:forEach>
                            </select></td>
                    </tr>

                    <tr>
                        <td>AGC Considerable</td>
                        <c:set var = "oldAGCFlag" value = "${corp.getAgcFlag()}"/>
                        <td><select name="agcconsiderable">
                                <c:forEach var="item" items="${statusList}">
                                    <option value="${item.status}" ${item.status == oldAGCFlag ? 'selected="selected"' : ''}>${item.status}</option>
                                </c:forEach>
                            </select></td>
                    </tr>
                    <tr>
                        <td>FRAS Considerable</td>
                        <c:set var = "oldFRASFlag" value = "${corp.getFrasFlag()}"/>
                        <td><select name="frasconsiderable">
                                <c:forEach var="item" items="${statusList}">
                                    <option value="${item.status}" ${item.status == oldFRASFlag ? 'selected="selected"' : ''}>${item.status}</option>
                                </c:forEach>
                            </select></td>
                    </tr>
                    <tr>
                        <td>FRAS Contact</td>
                        <td><input type="text" name="cpfras"  value="${corp.getFrasContact()}" pattern="(^[a-zA-Z][a-zA-Z0-9\s_]+$)" required/></td>
                    </tr>
                    <tr>
                        <td>SRAS Considerable</td>
                        <c:set var = "oldSRASFlag" value = "${corp.getSrasFlag()}"/>
                        <td><select name="srasconsiderable">
                                <c:forEach var="item" items="${statusList}">
                                    <option value="${item.status}" ${item.status == oldSRASFlag ? 'selected="selected"' : ''}>${item.status}</option>
                                </c:forEach>
                            </select></td>
                    </tr>
                    <tr>
                        <td>TRAS Considerable</td>
                        <c:set var = "oldTRASFlag" value = "${corp.getTrasFlag()}"/>
                        <td><select name="trasconsiderable">
                                <c:forEach var="item" items="${statusList}">
                                    <option value="${item.status}" ${item.status == oldTRASFlag ? 'selected="selected"' : ''}>${item.status}</option>
                                </c:forEach>
                            </select></td>
                    </tr>
                    
                    <br/>
                </table>
                
            </c:forEach>
<br/>
            <input type="submit" class="btn" style="margin-left: 45%" value="&#9999;Modify" name="bName" />
<br/>
<p>&nbsp;</p>
<p>&nbsp;</p>

<p>&nbsp;</p>

        </form>
    </body>
</html>