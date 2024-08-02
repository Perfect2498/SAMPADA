<%-- 
    Document   : interestPrecessingbyRLDC
    Created on : 23 Jul, 2020, 5:16:28 PM
    Author     : cdac
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
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
                var table = $('#interestChecker').DataTable({
                    responsive: true,
//                    "pageLength": false,
//                    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],                 
                    paging: false,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [

                    ]
                });

                var $select1 = $(".yearClass");
                for (i = 2000; i <= 2050; i++) {
                    $select1.append($('<option></option>').val(i).html(i));
                }
                var $select = $(".weekClass");
                for (i = 1; i <= 53; i++) {
                    $select.append($('<option></option>').val(i).html(i));
                }
                var $select = $(".revnoClass");
                for (i = 0; i <= 20; i++) {
                    $select.append($('<option></option>').val(i).html(i));
                }
            });
        </script>
    </head>
    <body style="text-align: center; alignment-adjust: central;width: 95%;">
        <!--<h6 align="center">Note: Please close the excel sheet of Interest Mapping & DIsbursement files</h6>-->
        
        <form method="post" action="interestProcessingExport.htm">
            <div align="right">&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;
                <input type="submit" value="Excel Export" style="color: black;">
            </div>
        </form>
        
        
        <form name="interestverify" method="post" action="submitnewInterestforProcessingbyRLDC.htm">
            <h3 align="center" style="color:#003366;">Report of Overall Verified Interest Amounts</h3>

            <!--<p><input class="btn" type="submit" name="payConfirm" value="Confirm" onclick="return validate();" /></p>-->
            <table align="center" class="customerTable" style=" width: 90%;" >

                <tr>
                    <td>Bill Year<span style="color:red;">*</span></td>
                    <td><select name="year" class="yearClass" id="yearId" style="width:200px; height:50px; font-size:30px;" required>
                        </select>
                    </td>
                    <td>Week Id<span style="color:red;">*</span></td>
                    <td><select name="week" class="weekClass" id="weekId"  style="width:200px; height:50px; font-size:30px;" required>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Summary Remarks<span style="color:red;">*</span> (max. 500 chars)</td>
                    <td><input type="text"  name="remarks"  id="remarks" style="width:200px; height:50px; font-size:30px;" maxlength="490" required/>

                    </td>
                    <td>Revision.No<span style="color:red;">*</span></td>
                    <td><select name="revno" class="revnoClass" id="revno"  style="width:200px; height:50px; font-size:30px;" required>
                        </select>
                    </td>
                </tr>

            </table>
            <br/>
            <table align="center" id="interestChecker" class="customerTable">
                <thead style="background-color: #0677A1;color: white;height:30px;">
                    <tr>
                        <th>Pool Member Name</th>
                        <th>Bill Type </th>
                        <th>Payable Interest</th>
                        <th>Receivable Interest</th>
                        <th>Net Interest</th>
                        <th>Generate-Type</th>
                        <th>Remarks (max. 500 characters)</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int cnt = 0;

                        String poolname = "poolname";
                        String poolname1 = null;
                        String billtype = "billtype";
                        String billtype1 = null;
                        String interestpay = "interestpay";
                        String interestpay1 = null;
                        String interestrec = "interestrec";
                        String interestrec1 = null;
                        String interestnet = "interestnet";
                        String interestnet1 = null;
                        String billCat = "billCat";
                        String billCat1 = null;
                        String rem = "rem";
                        String remarkrow1 = null;
                    %>

                    <c:forEach items="${intdetlist}" var="list">

                        <%                           cnt = cnt + 1;

                            poolname1 = poolname + cnt;
                            billtype1 = billtype + cnt;
                            interestpay1 = interestpay + cnt;
                            interestrec1 = interestrec + cnt;
                            interestnet1 = interestnet + cnt;
                            billCat1 = billCat + cnt;
                            remarkrow1 = rem + cnt;
                        %>
                        <tr>
                            <td><input type="text" name="<%=poolname1%>" value="${list.corporates.corporateName}" hidden="yes"/>${list.corporates.corporateName}</td>
                            <td><input type="text"  name="<%=billtype1%>"  value="${list.billType}" hidden="yes"/>${list.billType}</td>

                            <td><input type="text"  name="<%=interestpay1%>"  id="<%=interestpay1%>" style="width:200px; height:50px; font-size:30px;" value="${list.interestAmount}"/></td>
                            <td><input type="text"  name="<%=interestrec1%>"  id="<%=interestrec1%>" style="width:200px; height:50px; font-size:30px;" value="${list.interestBilledamount}"/></td>
                            <td><input type="text"  name="<%=interestnet1%>"  id="<%=interestnet1%>" style="width:200px; height:50px; font-size:30px;" value="${list.interestPaidamount}"/></td>

                                                        
                            <td><select name="<%=billCat1%>"  id="<%=billCat1%>" style="width:200px; height:50px; font-size:30px;" required>
                                    <option value="NET">NET </option>
                                    <option value="SEPERATE">SEPARATE</option>
                                </select>
                            </td>
                            <td><input type="text" name="<%=remarkrow1%>" id="<%=remarkrow1%>" maxlength="490" style="width:200px; height:50px; font-size:25px;"></td>

                        </tr>
                    </c:forEach>


                    <%--<fmt:formatDate value="${ele.billingDuedate}" pattern="dd-MM-yyyy" var="billingDuedate" />--%>
                </tbody>
            </table>
            <br/>
            <p><input type="text" name="rowcount" value="<%=cnt%>" hidden="yes"/></p>
            <p><input class="btn" type="submit" name="payConfirm" style="width:200px; height:50px; font-size:30px;" value="Confirm" onclick="return validate();" /></p>

            <br/>
        </form>

        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <br/>
    </body>

    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
</html>