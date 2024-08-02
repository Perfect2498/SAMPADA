<%-- 
    Document   : interestrefund
    Created on : 22 Apr, 2020, 6:09:00 AM
    Author     : abt
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
            var basePath = '${pageContext.request.contextPath}';</script>
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

            function validatecsdf()
            {
                var mainbal = document.getElementById("mainbal").value;
                var csdfamt = document.getElementById("csdfamt").value;
               var corp = document.getElementById("corparateID").value;
               var doc_sets = document.getElementById("doc_sets").value;
               var csdfremarks = document.getElementById("csdfremarks").value;
                
                
                if (corp == -1)

                {

                    alert('Please select the corpoarte ..');

                    return false;

                }
               
               if (doc_sets == -1)

                {

                    alert('Please select the document set..');

                    return false;

                }
                
                
                if (isNaN(csdfamt))
                {
                    alert('Please enter only digits for Amount');
                    document.getElementById('accno').value = "";
                    return false;
                }
                // alert(mainbal);
                // alert(csdfamt);
                var pend = (+mainbal) - (+csdfamt);
                // alert('Penjddfg'+pend);
                if (csdfamt != "0")
                {


                    if (pend <= 0)
                    {
                        alert('Please check that Disburse value is greater than Main Balance');
                        return false;
                    }
                }
                else
                {
                    alert('Please provide the PSDF Amount');
                    return false;
                }

                if(csdfremarks== ""){
                    
                    alert('Please give the remarks ..');

                    return false;
                    
                }
                if (confirm('Are you want to continue?')) {
                    return true;
                }
                else
                {
                    return false;
                }
                return true;
            }

            
        </script>


        <style>
            #bname[disabled]
            {
                background: black;
            }
        </style>
        <style>
            input[type="checkbox"]{
                width: 30px; /*Desired width*/
                height: 30px; /*Desired height*/
            }
            input[type="radio"]{
                width: 30px; /*Desired width*/
                height: 30px; /*Desired height*/
            }

        </style>
    </head>
    <body  width="95%">
        <form name="billDirbusementInfoForm" method="post">
            <h3 align="center" style="color:#003366;" >Interest Pool Account Details</h3>
            <table align="center" id="disburseTable" style="min-height:100px;width:80%;" class="customerTable">
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
            <br/>

            <br>
            <br>
            <table align="center" style="width:80%;">
                <tr>
                    <td>Corporate</td>
                    <td>Document Set</td>
                    <td>Amount</td>
                    <td>Remarks</td>
                    <td>&nbsp;</td>

                </tr>
                <tr>                  
                    <td>

                        <select name="corparateID" id="corparateID">

                            <option value="-1">Select</option>

                            <c:forEach items="${corporateList}" var="ele">

                                <option  value="${ele.corporateId}">${ele.corporateName}</option>

                            </c:forEach>

                        </select>

                    </td> 
                    <td>

                        <select name="doc_sets" id="doc_sets">

                            <option value="-1">Select</option>

                            <c:forEach items="${doc_sets}" var="ele1">

                                <option  value="${ele1.documentSetNo}">${ele1.documentSetNo}</option>

                            </c:forEach>

                        </select>

                    </td> 
                    
                   
                    <td><input type="text" style="width:150px;" name="csdfamt" id="csdfamt"  value="0"  required /></td>
                    <td><input type="text" style="width:200px;"  name="csdfremarks" id="csdfremarks" required /></td>
                    <td><input width="30" style="width:100px;"  type="submit" name="csdfSubmit" id="csdfSubmit" onclick="return validatecsdf();"/></td>
                </tr>
            </table>


            <br/>
            <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>
        </form>
    </body>



</html>
