<%--
    Document   : newLetterofCredit
    Created on : Aug 27, 2019, 8:06:41 AM
    Author     : shubham
--%>
 
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <script type = "text/javascript" >
            function preventBack() {
                window.history.forward();
            }
            setTimeout("preventBack()", 0);
            window.onunload = function () {
                null
            };
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title> <link href="css/mystyle.css" type="text/css" rel="stylesheet" >
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
 
            function validate()
            {
 
                var finyear = document.getElementById("entityname").value;//Added on 22-MARCH-2018
                var consname = document.getElementById("consname").value;//Added on 22-MARCH-2018
                var bankname = document.getElementById("bankname").value;
                var branchname = document.getElementById("branchname").value;
                var finavg = document.getElementById("finavg").value;
                var lcamount = document.getElementById("lcamount").value;
                var startdate = document.getElementById("startdate").value;
                var enddate = document.getElementById("enddate").value;
                var lcfilename = document.getElementById("lcfilename").value;
 
 
                if (finyear == "-1")
                {
                    alert('Please select the Financial Year');
 
                    return false;
                }
 
 
                if (consname == "")
                {
                    alert('Please enter the Constituent name');
 
                    return false;
                }
                if (finavg == "")
                {
                    alert('Please enter the Last Year Fin Avg');
 
                    return false;
                }
 
 
                if (lcfilename == "")
                {
                    alert('Please enter the LC File Name');
 
                    return false;
                }
 
                if (lcamount == "")
                {
                    alert('Please enter the LC Amount');
 
                    return false;
 
                }
 
                if (bankname == "")
                {
                    alert('Please enter the Bank name');
 
                    return false;
                }
                if (branchname == "")
                {
                    alert('Please enter the Branch Name ');
 
                    return false;
                }
                if (enddate == "")
                {
                    alert('Please enter the Expiry Date');
 
                    return false;
                }
 
 
                if (startdate == "")
                {
                    alert('Please enter the Start Date');
 
                    return false;
                }
 
                return true;
               
                 if (confirm('Are you sure submit !'))
                {
                    return true;
                }
                else
                {
                    return false;
                }
 
                return true;
            }
        
         function validate1()
            {
 
                if (confirm('Are you sure Cancel !'))
                {
                    return true;
                }
                else
                {
                    return false;
                }
 
                return true;
            }
        </script>     
<script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null;
        };
 
 
        function cmpDates() {
 
//            alert("In cmpDates()");
            var startDate = document.getElementById("startdate").value;
            var endDate = document.getElementById("enddate").value;
//            alert(startDate);
//            alert(endDate);
 
           var split = startDate.split('-');
           var split1 = endDate.split('-');
            
// Month is zero-indexed so subtract one from the month inside the constructor
            var date0 = new Date(split[2], split[1] - 1, split[0]); //Y M D
            var date1 = new Date(split1[2], split1[1] - 1, split1[0]); //Y M D
            var timestamp = date0.getTime();
            var timestamp1 = date1.getTime();
//            alert(timestamp);
//            alert(timestamp1);
           
            
            if ((startDate == "") || (endDate == "")) {
                {
                    alert('Kindly select both datepickers!!');
                }
 
            }
            else
            {
                if (timestamp1 < timestamp) {
 
                    alert("LC End Date should be greater than or equal to LC Start Date");
 
                    $('#enddate').val('');
 
                    return false;
                }
                else
                {
                    return true;
                }
            }
 
        }
 
 
 
 
 
 
 
    </script>
        <script>
            $(function () {
                $.datepicker.setDefaults($.datepicker.regional['nl']);
//                $.datepicker.setDefaults({dateFormat: 'yy-mm-dd'});
                $.datepicker.setDefaults({dateFormat: 'dd-mm-yy'});
 
                $("#startdate").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    yearRange: '-2:+2'
 
                });
                $("#enddate").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    yearRange: '-2:+2'
                });
            });
        </script>
 
        <script>
 
            $(document).ready(function () {
 
$(document).ready(function () {
 
         
                    
                     var table = $('#disburseTable').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'collection',
                            text: 'Export',
                            buttons: [
                                'excel',
                                'pdf'
                            ]
                        }
 
                    ]
 
                });
 
 
                $('input[type="submit"]').button();
                var formOK = true;
                $('#lcfile').bind('change', function () {
                    var file = this.value;
                    var len = file.length;
                    var ext = file.slice(len - 4, len);
                    var size = this.files[0].size;
 
                    if (((ext.toUpperCase() == ".PDF")) && (size <= 1024000) && (size > 0)) {
                        formOK = true;
                    }
                    else {
                        formOK = false;
                        alert("PDF ,JPEG or jpg Files with size less than or equal to 1MB is allowed");
                        $("#lcfile").val('');
                    }
                    return formOK;
                });
 
            });
 
        </script>
 
        
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
    <body >
        <form name="newregister"  method="post" enctype="multipart/form-data">
         
            <h3 align="center" style="color:#003366;" >List of Default Corporates Details </h3>
 
           <table align="center" id="disburseTable" style="min-height:400px;width:80%;" cellspacing="0" class="customerTable">
               <thead style="background-color: #0677A1;color: white;height:30px;">
                   <tr>
                       <th>Default ID</th>
                       <th>Corporate Name</th>
                       <th>Week ID</th>
                       <th>Bill Type</th>
                       <th>Category</th>
                       <th>Bill Amount</th>
                       <th>Year</th>
                       <th>Due Date</th>
                       <th>Entry Date</th>
                   </tr>
               </thead>
               <tbody>
                
                   <c:forEach items="${UserLCList}" var="ele">                    
                       <tr>
                           <td>${ele.slno}</td>                   
                           <td>${ele.corporates.corporateName}</td>
                           <td>${ele.weekid}</td>
                           <td>${ele.billType}</td>
                           <td>${ele.billCategory}</td>
                           <td>${ele.billAmount}</td>
                           <td>${ele.billYear}</td>
                           <td>${ele.billPayableCorp.billDueDate}</td>
                           <td>${ele.entryDate}</td>
                       </tr>
 
 
                   </c:forEach>
               </tbody>
             
           </table>    
            <br>
            <p align="center" style="font-size: 16px;color:#003366;"><b>Letter Of Credit</b></p>          
            <table align="center" class="customerTable" style=" width: 90%;" >
 
                <tr>
                    <td>Enter the Default ID</td>
                    <td><input type="text" name="dafaultid" id="dafaultid" required/></td>
                    <td></td>
                     <td></td>
                </tr>
                <tr>
                    <td>Financial Year<span style="color:red;">*</span></td>
               
                    <td><select id="finyear" name="finyear">
                            <option selected value="-1">Select</option>
                            <option>2017-18</option>
                            <option>2018-19</option>
                            <option>2019-20</option>
                            <option>2020-21</option>
                            <option>2021-22</option> 
                            <option>2022-23</option>
                            <option>2023-24</option>
                            <option>2024-25</option>
                        </select></td>
 
 
                    <td>Constituent Name<span style="color:red;">*</span></td>
                    <!--<td><input type="text" name="consname" id="consname" maxlength="50" autocomplete="off" /></td>-->
                    <td><select name="consname" id="consname">       
                            <c:forEach items="${corpList}" var="pflow">
                                <option value="${pflow.corporateName}"><c:out value="${pflow.corporateName}"/></option>
                            </c:forEach>
                        </select>  </td>
                </tr>
 
                <tr><td>Last Financial Yr Avg<span style="color:red;">*</span></td>       
                    <td><input type="text" name="finavg" id="finavg" maxlength="50" autocomplete="off" /></td>
 
 
 
                    <td>LC Amount<span style="color:red;">*</span></td>
                    <td><input type="text" name="lcamount" id="lcamount" maxlength="50" autocomplete="off" /></td>
 
                </tr>
                <tr>   
 
                <tr>
                    <td>IC Issuing Bank<span style="color:red;">*</span></td>
                    <td> <input type="text" name="bankname" id="bankname" maxlength="100" autocomplete="off" /></td>
 
                    <td>LC Issuing Branch <span style="color:red;">*</span></td>
                    <td> <input type="text" name="branchname" id="branchname" maxlength="100" autocomplete="off"/></td>
                </tr>
                <tr>
                    <td>LC Start Date<span style="color:red;">*</span></td>
                    <td><input type="text" name="startdate" id="startdate" required  autocomplete="off"/></td>
 
                    <td>LC End Date<span style="color:red;">*</span></td>
                    <td><input type="text" name="enddate" id="enddate" required autocomplete="off" onchange="return cmpDates();"/></td>
                </tr>
                <td colspan="2" align="right">Remarks</td>
                <td colspan="2"> <input type="text" name="remarks" id="remarks" maxlength="500" autocomplete="off"/></td>
                <tr>
                </tr>
                <tr>
 
                    <td>Upload LC Copy <span style="color:red;">*</span></td>
                    <td><input type="file" name="lcfile" id="lcfile" required/></td>
                    <td>LC File Name <span style="color:red;">*</span></td>
                    <td><input type="text" name="lcfilename" id="lcfilename"  autocomplete="off" maxlength="100" required/></td>
                </tr>
 
            </table>
         
            <br>
            <div style="text-align: center">
                <p><input type="submit" name="bname" onclick="return validate();" class="btn" value="Submit" onchange="return cmpDates();"/></p>
              
            </div>
            <p>&nbsp;</p>
             <p>&nbsp;</p>
              <p>&nbsp;</p> <p>&nbsp;</p>
        </form>
  <p>&nbsp;</p>
             <p>&nbsp;</p>
              <p>&nbsp;</p> <p>&nbsp;</p>
    </body>
  <p>&nbsp;</p>
             <p>&nbsp;</p>
              <p>&nbsp;</p> <p>&nbsp;</p>
</html>
 
